import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * 🎮 Game Objects - All the scary-cute characters and items! 🎮
 */

/**
 * ⭐ Star - Twinkling background stars ⭐
 */
class Star {
    float x, y, speed;
    
    public Star(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}

/**
 * 🚀 Player - Your brave triangle ship! 🚀
 */
class Player {
    int x, y;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void draw(Graphics2D g2d) {
        // Draw triangle ship
        g2d.setColor(Color.CYAN);
        int[] xPoints = {x, x - WIDTH/2, x + WIDTH/2};
        int[] yPoints = {y - HEIGHT/2, y + HEIGHT/2, y + HEIGHT/2};
        g2d.fillPolygon(xPoints, yPoints, 3);
        
        // Draw cute eyes
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x - 8, y - 5, 4, 4);
        g2d.fillOval(x + 4, y - 5, 4, 4);
        
        // Draw little smile
        g2d.setStroke(new BasicStroke(2));
        g2d.drawArc(x - 6, y - 2, 12, 8, 0, -180);
    }
    
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT);
    }
}

/**
 * 💥 Bullet - Pew pew projectiles! 💥
 */
class Bullet {
    int x, y, velocityY;
    private static final int WIDTH = 4;
    private static final int HEIGHT = 10;
    
    public Bullet(int x, int y, int velocityY) {
        this.x = x;
        this.y = y;
        this.velocityY = velocityY;
    }
    
    public void update() {
        y += velocityY;
    }
    
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT);
        
        // Add glow effect
        g2d.setColor(new Color(255, 255, 0, 100));
        g2d.fillOval(x - WIDTH, y - HEIGHT, WIDTH*2, HEIGHT*2);
    }
    
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT);
    }
}

/**
 * 👹 Enemy - Scary-cute emoji invaders! 👹
 */
class Enemy {
    int x, y, health, maxHealth;
    String type;
    private float wobbleOffset;
    private Random random = new Random();
    private long lastWobbleUpdate = System.currentTimeMillis();
    private boolean hasRedEyes = false;
    
    public Enemy(int x, int y, String type, int wave) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.maxHealth = 1 + wave / 3; // More health in later waves
        this.health = maxHealth;
        this.wobbleOffset = random.nextFloat() * (float)(2 * Math.PI);
    }
    
    public void update() {
        // Gentle wobbling motion
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastWobbleUpdate > 50) {
            wobbleOffset += 0.1f;
            y += (int)(Math.sin(wobbleOffset) * 0.5);
            lastWobbleUpdate = currentTime;
        }
        
        // Move down slowly
        y += 0.2f;
    }
    
    public void takeDamage() {
        health--;
        if (health < maxHealth) {
            hasRedEyes = true; // Get scary red eyes when damaged!
        }
    }
    
    public boolean isDead() {
        return health <= 0;
    }
    
    public int getPoints() {
        return 100;
    }
    
    public void draw(Graphics2D g2d) {
        // Draw emoji body
        g2d.setFont(new Font("Arial", Font.PLAIN, 32));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(type);
        int textHeight = fm.getHeight();
        
        g2d.setColor(Color.WHITE);
        g2d.drawString(type, x - textWidth/2, y + textHeight/4);
        
        // Draw scary red eyes when damaged
        if (hasRedEyes) {
            g2d.setColor(Color.RED);
            g2d.fillOval(x - 12, y - 8, 6, 6);
            g2d.fillOval(x + 6, y - 8, 6, 6);
            
            // Add red glow
            g2d.setColor(new Color(255, 0, 0, 100));
            g2d.fillOval(x - 15, y - 11, 12, 12);
            g2d.fillOval(x + 3, y - 11, 12, 12);
        }
    }
    
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x - 20, y - 20, 40, 40);
    }
}

/**
 * 👹⭕ Juggernaut - The emotional boss! ⭕👹
 */
class Juggernaut {
    int x, y, health, maxHealth;
    private float wobbleOffset;
    private float eyeWobbleOffset;
    private Random random = new Random();
    private long lastWobbleUpdate = System.currentTimeMillis();
    
    public Juggernaut(int x, int y, int wave) {
        this.x = x;
        this.y = y;
        this.maxHealth = 15 + wave * 5; // Stronger in later waves
        this.health = maxHealth;
        this.wobbleOffset = 0;
        this.eyeWobbleOffset = 0;
    }
    
    public void update() {
        // Gentle up/down wobbling
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastWobbleUpdate > 50) {
            wobbleOffset += 0.05f;
            eyeWobbleOffset += 0.08f; // Eyes wobble independently
            y += (int)(Math.sin(wobbleOffset) * 1.5);
            lastWobbleUpdate = currentTime;
        }
        
        // Slow horizontal movement
        x += (int)(Math.sin(wobbleOffset * 0.3) * 2);
        
        // Keep in bounds
        if (x < 100) x = 100;
        if (x > 700) x = 700;
    }
    
    public void takeDamage() {
        health--;
    }
    
    public boolean isDead() {
        return health <= 0;
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public void draw(Graphics2D g2d) {
        int rings = (health * 5) / maxHealth; // 0-5 rings based on health
        
        // Draw concentric circles (rings)
        g2d.setStroke(new BasicStroke(3));
        for (int i = 0; i < rings; i++) {
            int radius = 20 + i * 15;
            g2d.setColor(new Color(100 + i * 30, 50, 50));
            g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);
        }
        
        // Draw main body
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(x - 40, y - 40, 80, 80);
        
        // Draw face based on health
        String face = getFaceExpression();
        g2d.setFont(new Font("Arial", Font.PLAIN, 48));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(face);
        int textHeight = fm.getHeight();
        
        g2d.setColor(Color.WHITE);
        g2d.drawString(face, x - textWidth/2, y + textHeight/4);
        
        // Draw wobbling eyes with increasing redness
        float healthPercent = (float)health / maxHealth;
        int redIntensity = (int)(255 * (1 - healthPercent));
        g2d.setColor(new Color(redIntensity, 0, 0));
        
        int eyeOffset = (int)(Math.sin(eyeWobbleOffset) * 3);
        g2d.fillOval(x - 20 + eyeOffset, y - 15, 8, 8);
        g2d.fillOval(x + 12 - eyeOffset, y - 15, 8, 8);
        
        // Add red glow when very damaged
        if (healthPercent < 0.3f) {
            g2d.setColor(new Color(255, 0, 0, 150));
            g2d.fillOval(x - 50, y - 50, 100, 100);
        }
    }
    
    private String getFaceExpression() {
        float healthPercent = (float)health / maxHealth;
        
        if (healthPercent > 0.7f) {
            return "😊"; // Happy when healthy
        } else if (healthPercent > 0.3f) {
            return "😟"; // Worried when damaged
        } else {
            return "😡"; // ANGRY when nearly dead!
        }
    }
    
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x - 40, y - 40, 80, 80);
    }
}

/**
 * ⚡ Powerup - Helpful items! ⚡
 */
class Powerup {
    int x, y;
    String type;
    private float wobbleOffset;
    private Random random = new Random();
    
    public Powerup(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.wobbleOffset = random.nextFloat() * (float)(2 * Math.PI);
    }
    
    public void update() {
        y += 2; // Fall down
        
        // Gentle wobbling
        wobbleOffset += 0.1f;
        x += (int)(Math.sin(wobbleOffset) * 0.5);
    }
    
    public void draw(Graphics2D g2d) {
        // Draw powerup emoji
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(type);
        int textHeight = fm.getHeight();
        
        g2d.setColor(Color.WHITE);
        g2d.drawString(type, x - textWidth/2, y + textHeight/4);
        
        // Add glow effect
        g2d.setColor(new Color(255, 255, 0, 100));
        g2d.fillOval(x - 20, y - 20, 40, 40);
    }
    
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x - 15, y - 15, 30, 30);
    }
}

