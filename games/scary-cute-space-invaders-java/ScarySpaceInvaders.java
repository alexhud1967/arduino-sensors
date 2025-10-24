import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * 👹💕 Scary-Cute Space Invaders - Java Edition 💕👹
 * 
 * A delightfully terrifying space battle that perfectly balances ADORABLE and SCARY!
 * 
 * Features:
 * - 👾 Emoji enemy army with individual personalities
 * - 🎭 Emotional juggernaut boss (😊 → 😟 → 😡)
 * - 🔊 Complete audio system with your .wav files
 * - 🎯 Mouse controls with constant shooting
 * - ⚡ Powerups and progressive difficulty
 * - 🌟 Smooth 60 FPS Java performance
 */
public class ScarySpaceInvaders extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    private GamePanel gamePanel;
    private SoundManager soundManager;
    
    public ScarySpaceInvaders() {
        setTitle("👹💕 Scary-Cute Space Invaders 💕👹");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Initialize sound system
        soundManager = new SoundManager();
        
        // Create game panel
        gamePanel = new GamePanel(soundManager);
        add(gamePanel);
        
        // Set up window
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        // Start the game
        gamePanel.startGame();
        
        // Add window listener for cleanup
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                soundManager.cleanup();
                System.exit(0);
            }
        });
    }
    
    public static void main(String[] args) {
        // Create and run game on EDT (compatible with all Java versions)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScarySpaceInvaders();
            }
        });
    }
}

/**
 * 🎮 Main Game Panel - Where all the scary-cute magic happens! 🎮
 */
class GamePanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    private static final int FPS = 60;
    
    // Game objects
    private Player player;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private ArrayList<Powerup> powerups;
    private Juggernaut juggernaut;
    private ArrayList<Star> stars;
    
    // Game state
    private Timer gameTimer;
    private SoundManager soundManager;
    private Random random;
    private int score;
    private int lives;
    private int extraCannons;
    private int wave;
    private boolean gameRunning;
    private boolean mousePressed;
    private boolean soundEnabled;
    
    // Timing
    private long lastShootTime;
    private long lastEnemySpawn;
    private long lastPowerupSpawn;
    
    public GamePanel(SoundManager soundManager) {
        this.soundManager = soundManager;
        this.random = new Random();
        
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        // Add listeners
        addMouseListener(this);
        addMouseMotionListener(this);
        
        // Initialize game
        initGame();
    }
    
    private void initGame() {
        // Initialize collections
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        powerups = new ArrayList<>();
        stars = new ArrayList<>();
        
        // Create player
        player = new Player(PANEL_WIDTH / 2, PANEL_HEIGHT - 50);
        
        // Game state
        score = 0;
        lives = 3;
        extraCannons = 0;
        wave = 1;
        gameRunning = true;
        mousePressed = false;
        soundEnabled = true;
        
        // Timing
        lastShootTime = 0;
        lastEnemySpawn = 0;
        lastPowerupSpawn = 0;
        
        // Create starfield
        createStarfield();
        
        // Create initial enemies
        spawnEnemyWave();
    }
    
    public void startGame() {
        // Start background music immediately!
        soundManager.playBackgroundMusic();
        
        // Start game timer
        gameTimer = new Timer(1000 / FPS, this);
        gameTimer.start();
    }
    
    private void createStarfield() {
        stars.clear();
        for (int i = 0; i < 100; i++) {
            stars.add(new Star(
                random.nextInt(PANEL_WIDTH),
                random.nextInt(PANEL_HEIGHT),
                random.nextFloat() * 2 + 1
            ));
        }
    }
    
    private void spawnEnemyWave() {
        enemies.clear();
        
        // Spawn regular enemies
        String[] enemyTypes = {"👹", "🎃", "⚡", "👾", "🐙"};
        int enemiesPerRow = 8;
        int rows = 3 + wave / 3; // More rows as waves progress
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < enemiesPerRow; col++) {
                String type = enemyTypes[random.nextInt(enemyTypes.length)];
                int x = 100 + col * 80;
                int y = 50 + row * 60;
                enemies.add(new Enemy(x, y, type, wave));
            }
        }
        
        // Spawn juggernaut every 3 waves
        if (wave % 3 == 0) {
            juggernaut = new Juggernaut(PANEL_WIDTH / 2, 100, wave);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameRunning) return;
        
        updateGame();
        repaint();
    }
    
    private void updateGame() {
        long currentTime = System.currentTimeMillis();
        
        // Update starfield
        updateStars();
        
        // Constant shooting (every 100ms)
        if (currentTime - lastShootTime > 100) {
            shootBullet();
            lastShootTime = currentTime;
        }
        
        // Update bullets
        updateBullets();
        
        // Update enemies
        updateEnemies();
        
        // Update juggernaut
        if (juggernaut != null && juggernaut.isAlive()) {
            juggernaut.update();
            checkJuggernautCollisions();
        }
        
        // Update powerups
        updatePowerups();
        
        // Spawn new enemies periodically
        if (currentTime - lastEnemySpawn > 3000 + random.nextInt(2000)) {
            spawnRandomEnemy();
            lastEnemySpawn = currentTime;
        }
        
        // Spawn powerups occasionally
        if (currentTime - lastPowerupSpawn > 15000 + random.nextInt(10000)) {
            spawnPowerup();
            lastPowerupSpawn = currentTime;
        }
        
        // Check for wave completion
        if (enemies.isEmpty() && (juggernaut == null || !juggernaut.isAlive())) {
            wave++;
            spawnEnemyWave();
        }
        
        // Check game over
        if (lives <= 0) {
            gameRunning = false;
            soundManager.stopBackgroundMusic();
        }
    }
    
    private void updateStars() {
        for (Star star : stars) {
            star.y += star.speed;
            if (star.y > PANEL_HEIGHT) {
                star.y = 0;
                star.x = random.nextInt(PANEL_WIDTH);
            }
        }
    }
    
    private void shootBullet() {
        // Main cannon
        bullets.add(new Bullet(player.x, player.y - 10, -8));
        
        // Play shooting sound only if mouse is pressed
        if (mousePressed && soundEnabled) {
            soundManager.playShootSound();
        }
        
        // Extra cannons
        for (int i = 0; i < extraCannons; i++) {
            int offset = (i + 1) * 20 * ((i % 2 == 0) ? 1 : -1);
            bullets.add(new Bullet(player.x + offset, player.y - 10, -8));
        }
    }
    
    private void updateBullets() {
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.update();
            
            if (bullet.y < 0 || bullet.y > PANEL_HEIGHT) {
                bulletIter.remove();
                continue;
            }
            
            // Check enemy collisions
            Iterator<Enemy> enemyIter = enemies.iterator();
            while (enemyIter.hasNext()) {
                Enemy enemy = enemyIter.next();
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    enemy.takeDamage();
                    bulletIter.remove();
                    
                    if (soundEnabled) {
                        soundManager.playEnemyHitSound();
                    }
                    
                    if (enemy.isDead()) {
                        score += enemy.getPoints();
                        enemyIter.remove();
                        
                        if (soundEnabled) {
                            soundManager.playExplosionSound();
                        }
                    }
                    break;
                }
            }
        }
    }
    
    private void updateEnemies() {
        for (Enemy enemy : enemies) {
            enemy.update();
            
            // Check collision with player
            if (enemy.getBounds().intersects(player.getBounds())) {
                lives--;
                enemy.takeDamage();
                if (enemy.isDead()) {
                    enemies.remove(enemy);
                }
                break;
            }
        }
    }
    
    private void checkJuggernautCollisions() {
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            if (bullet.getBounds().intersects(juggernaut.getBounds())) {
                juggernaut.takeDamage();
                bulletIter.remove();
                
                if (soundEnabled) {
                    soundManager.playJuggernautHitSound();
                }
                
                if (juggernaut.isDead()) {
                    score += 1000;
                    if (soundEnabled) {
                        soundManager.playExplosionSound();
                    }
                }
                break;
            }
        }
    }
    
    private void updatePowerups() {
        Iterator<Powerup> powerupIter = powerups.iterator();
        while (powerupIter.hasNext()) {
            Powerup powerup = powerupIter.next();
            powerup.update();
            
            if (powerup.y > PANEL_HEIGHT) {
                powerupIter.remove();
                continue;
            }
            
            // Check collision with player
            if (powerup.getBounds().intersects(player.getBounds())) {
                if (powerup.type.equals("❤️")) {
                    lives++;
                } else if (powerup.type.equals("⭐")) {
                    extraCannons = Math.min(extraCannons + 1, 4);
                }
                
                if (soundEnabled) {
                    soundManager.playPowerupSound();
                }
                
                powerupIter.remove();
            }
        }
    }
    
    private void spawnRandomEnemy() {
        String[] enemyTypes = {"👹", "🎃", "⚡", "👾", "🐙"};
        String type = enemyTypes[random.nextInt(enemyTypes.length)];
        int x = random.nextInt(PANEL_WIDTH - 40);
        enemies.add(new Enemy(x, -30, type, wave));
    }
    
    private void spawnPowerup() {
        String[] powerupTypes = {"❤️", "⭐"};
        String type = powerupTypes[random.nextInt(powerupTypes.length)];
        int x = random.nextInt(PANEL_WIDTH - 30);
        powerups.add(new Powerup(x, -30, type));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw starfield
        g2d.setColor(Color.WHITE);
        for (Star star : stars) {
            int size = (int) star.speed;
            g2d.fillOval((int) star.x, (int) star.y, size, size);
        }
        
        // Draw player
        player.draw(g2d);
        
        // Draw bullets
        for (Bullet bullet : bullets) {
            bullet.draw(g2d);
        }
        
        // Draw enemies
        for (Enemy enemy : enemies) {
            enemy.draw(g2d);
        }
        
        // Draw juggernaut
        if (juggernaut != null && juggernaut.isAlive()) {
            juggernaut.draw(g2d);
        }
        
        // Draw powerups
        for (Powerup powerup : powerups) {
            powerup.draw(g2d);
        }
        
        // Draw UI
        drawUI(g2d);
    }
    
    private void drawUI(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        
        g2d.drawString("Score: " + score, 10, 25);
        g2d.drawString("Lives: " + lives, 10, 50);
        g2d.drawString("Extra Cannons: " + extraCannons, 10, 75);
        g2d.drawString("Wave: " + wave, 10, 100);
        
        // Sound toggle button
        String soundText = soundEnabled ? "🔊 Sound ON" : "🔇 Sound OFF";
        g2d.drawString(soundText, PANEL_WIDTH - 150, 25);
        
        if (!gameRunning) {
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            g2d.setColor(Color.RED);
            String gameOver = "GAME OVER";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (PANEL_WIDTH - fm.stringWidth(gameOver)) / 2;
            int y = PANEL_HEIGHT / 2;
            g2d.drawString(gameOver, x, y);
            
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            String finalScore = "Final Score: " + score;
            fm = g2d.getFontMetrics();
            x = (PANEL_WIDTH - fm.stringWidth(finalScore)) / 2;
            g2d.drawString(finalScore, x, y + 50);
        }
    }
    
    // Mouse event handlers
    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        
        // Check if clicking sound toggle area
        if (e.getX() > PANEL_WIDTH - 150 && e.getY() < 30) {
            soundEnabled = !soundEnabled;
            if (soundEnabled) {
                soundManager.playBackgroundMusic();
            } else {
                soundManager.stopBackgroundMusic();
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        player.x = e.getX();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {
        player.x = e.getX();
    }
}
