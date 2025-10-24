import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 🔊 Sound Manager - Handles all the scary-cute audio! 🔊
 * 
 * Manages loading and playing of .wav sound files from the sounds/ folder.
 * Features:
 * - Background music with looping
 * - Sound effects with proper volume control
 * - Error handling for missing files
 * - Cleanup for proper resource management
 */
public class SoundManager {
    private Map<String, Clip> soundClips;
    private Clip backgroundMusicClip;
    private boolean backgroundMusicPlaying = false;
    
    // Sound file paths
    private static final String SOUNDS_DIR = "sounds/";
    private static final String BACKGROUND_MUSIC = SOUNDS_DIR + "background.wav";
    private static final String SHOOT_SOUND = SOUNDS_DIR + "shoot.wav";
    private static final String ENEMY_HIT_SOUND = SOUNDS_DIR + "enemy-hit.wav";
    private static final String EXPLOSION_SOUND = SOUNDS_DIR + "explosion.wav";
    private static final String POWERUP_SOUND = SOUNDS_DIR + "powerup.wav";
    private static final String JUGGERNAUT_HIT_SOUND = SOUNDS_DIR + "juggernaut-hit.wav";
    
    public SoundManager() {
        soundClips = new HashMap<>();
        loadSounds();
    }
    
    /**
     * Load all sound files into memory
     */
    private void loadSounds() {
        // Load sound effects
        loadSound("shoot", SHOOT_SOUND);
        loadSound("enemy-hit", ENEMY_HIT_SOUND);
        loadSound("explosion", EXPLOSION_SOUND);
        loadSound("powerup", POWERUP_SOUND);
        loadSound("juggernaut-hit", JUGGERNAUT_HIT_SOUND);
        
        // Load background music separately
        loadBackgroundMusic();
        
        System.out.println("🔊 Sound system initialized!");
        System.out.println("📁 Looking for sound files in: " + new File(SOUNDS_DIR).getAbsolutePath());
    }
    
    /**
     * Load a single sound file
     */
    private void loadSound(String name, String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.out.println("⚠️  Sound file not found: " + filePath);
                return;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            soundClips.put(name, clip);
            
            System.out.println("✅ Loaded sound: " + name + " (" + soundFile.length() + " bytes)");
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("❌ Failed to load sound: " + name + " - " + e.getMessage());
        }
    }
    
    /**
     * Load background music
     */
    private void loadBackgroundMusic() {
        try {
            File musicFile = new File(BACKGROUND_MUSIC);
            if (!musicFile.exists()) {
                System.out.println("⚠️  Background music file not found: " + BACKGROUND_MUSIC);
                return;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            
            System.out.println("🎵 Loaded background music (" + musicFile.length() + " bytes)");
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("❌ Failed to load background music: " + e.getMessage());
        }
    }
    
    /**
     * Play a sound effect
     */
    private void playSound(String name, float volume) {
        Clip clip = soundClips.get(name);
        if (clip != null) {
            try {
                // Reset to beginning
                clip.setFramePosition(0);
                
                // Set volume
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                volumeControl.setValue(Math.max(dB, volumeControl.getMinimum()));
                
                // Play the sound
                clip.start();
                
            } catch (Exception e) {
                System.out.println("❌ Error playing sound " + name + ": " + e.getMessage());
            }
        }
    }
    
    /**
     * 🎯 Play shooting sound (cute pew-pew)
     */
    public void playShootSound() {
        playSound("shoot", 0.3f); // Quiet shooting sound
    }
    
    /**
     * 😢 Play enemy hit sound (adorable pop)
     */
    public void playEnemyHitSound() {
        playSound("enemy-hit", 0.4f);
    }
    
    /**
     * 💥 Play explosion sound (satisfying boom)
     */
    public void playExplosionSound() {
        playSound("explosion", 0.5f);
    }
    
    /**
     * ⚡ Play powerup sound (happy chime)
     */
    public void playPowerupSound() {
        playSound("powerup", 0.6f);
    }
    
    /**
     * 👹 Play juggernaut hit sound (scary roar)
     */
    public void playJuggernautHitSound() {
        playSound("juggernaut-hit", 0.7f);
    }
    
    /**
     * 🎵 Start background music (loops continuously)
     */
    public void playBackgroundMusic() {
        if (backgroundMusicClip != null && !backgroundMusicPlaying) {
            try {
                // Set volume for background music (quieter)
                FloatControl volumeControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(0.2f) / Math.log(10.0) * 20.0); // 20% volume
                volumeControl.setValue(Math.max(dB, volumeControl.getMinimum()));
                
                // Loop continuously
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundMusicPlaying = true;
                
                System.out.println("🎵 Background music started!");
                
            } catch (Exception e) {
                System.out.println("❌ Error starting background music: " + e.getMessage());
            }
        }
    }
    
    /**
     * 🔇 Stop background music
     */
    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicPlaying) {
            backgroundMusicClip.stop();
            backgroundMusicPlaying = false;
            System.out.println("🔇 Background music stopped");
        }
    }
    
    /**
     * 🧹 Clean up all audio resources
     */
    public void cleanup() {
        // Stop and close all sound clips
        for (Clip clip : soundClips.values()) {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
        }
        
        // Stop and close background music
        if (backgroundMusicClip != null) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
        }
        
        soundClips.clear();
        System.out.println("🧹 Sound system cleaned up");
    }
    
    /**
     * 📊 Get sound system status
     */
    public void printStatus() {
        System.out.println("\n🔊 SOUND SYSTEM STATUS:");
        System.out.println("📁 Sounds directory: " + new File(SOUNDS_DIR).getAbsolutePath());
        System.out.println("🎵 Background music: " + (backgroundMusicClip != null ? "✅ Loaded" : "❌ Not loaded"));
        System.out.println("🎯 Sound effects loaded: " + soundClips.size() + "/5");
        
        for (String soundName : soundClips.keySet()) {
            System.out.println("  ✅ " + soundName);
        }
        
        System.out.println("🎶 Background music playing: " + backgroundMusicPlaying);
        System.out.println();
    }
}

