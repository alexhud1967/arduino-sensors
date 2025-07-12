# 👹💕 Scary-Cute Space Invaders - Java Edition 💕👹

A delightfully terrifying **standalone Java desktop game** that perfectly balances **ADORABLE** and **SCARY**!

![Java Game](https://img.shields.io/badge/Java-Desktop%20Game-orange?style=for-the-badge&logo=java)
![Platform](https://img.shields.io/badge/Platform-Linux%20%7C%20Windows%20%7C%20macOS-blue?style=for-the-badge)

## 🚀 **WHAT MAKES THIS SPECIAL?**

This is the **Java desktop version** of our scary-cute Space Invaders! No browser needed - pure native performance!

### 👾 **EMOJI ENEMY ARMY:**
- **👹 Red Demons** - Angry but somehow adorable
- **🎃 Spooky Pumpkins** - Halloween cute-scary vibes  
- **⚡ Lightning Bolts** - Energetic and zippy
- **👾 Classic Aliens** - Retro space invader charm
- **🐙 Cute Octopi** - Tentacled but friendly

### 🎭 **EMOTIONAL JUGGERNAUT BOSS:**
- **PERFECTLY ROUND** with concentric circular rings! ⭕
- **EMOTIONAL FACE** that changes as it gets damaged:
  - 😊 **Healthy**: Happy, innocent smile
  - 😟 **Damaged**: Worried, concerned frown  
  - 😡 **Nearly Dead**: SCARY ANGRY EXPRESSION!
- **WOBBLING ANIMATIONS**: Gentle up/down wobble + independent eye wobbling
- **PROGRESSIVE TERROR**: Eyes get redder as rings are destroyed

### 🔊 **COMPLETE AUDIO SYSTEM:**
- 🎯 **Shooting**: Cute bell-ringing pew-pew sounds (optional - only when holding mouse!)
- 💥 **Explosions**: Satisfying booms when enemies die
- 😢 **Enemy Hits**: Adorable 'pop' squeaks when enemies take damage
- ⚡ **Powerups**: Happy chimes for life/cannon pickups
- 👹 **Juggernaut Roars**: Scary roars that get more intense
- 🎵 **Background Music**: Spooky-cute ambient music (starts immediately!)
- 🔇 **Mute Toggle**: Click top-right to toggle sound on/off

## 🛠️ **SETUP & INSTALLATION:**

### 📋 **Requirements:**
- **Java 11 or higher** (OpenJDK recommended)
- **Linux, Windows, or macOS**
- **Your .wav sound files** (optional but recommended!)

### 🎵 **Sound Files Setup:**
Create a `sounds/` folder next to the Java files and add:

```
sounds/
├── background.wav      - Spooky-cute background music
├── enemy-hit.wav       - Enemy damage sound  
├── explosion.wav       - Enemy death explosion
├── juggernaut-hit.wav  - Boss damage roar
├── powerup.wav         - Powerup collection chime
└── shoot.wav           - Cute shooting sound
```

### 🚀 **Quick Start:**

#### **Option 1: Easy Compilation (Recommended)**
```bash
# Make the script executable and run it
chmod +x compile.sh
./compile.sh
```

The script will:
- ✅ Compile all Java files
- ✅ Check for sound files
- ✅ Create sounds/ directory if needed
- ✅ Start the game automatically!

#### **Option 2: Manual Compilation**
```bash
# Compile all Java files
javac *.java

# Run the game
java ScarySpaceInvaders
```

## 🎮 **HOW TO PLAY:**

### 🎯 **Controls:**
- **Mouse Movement**: Move your triangle ship left/right
- **Automatic Shooting**: Bullets fire constantly every 100ms
- **Hold Left Mouse Button**: Hear optional shooting sounds
- **Click Sound Toggle**: Top-right corner to mute/unmute

### 🎨 **Game Features:**
- **Progressive Difficulty**: Enemies get faster and more numerous
- **Powerup System**: 
  - ❤️ **Hearts**: Extra lives
  - ⭐ **Stars**: Extra cannons (up to 4 additional!)
- **Boss Battles**: Epic juggernaut every 3 waves
- **Score System**: Points for everything you destroy
- **Smooth 60 FPS**: Native Java performance

### 🏆 **Objectives:**
- Survive as many waves as possible
- Collect powerups to increase your firepower
- Defeat the emotional juggernaut bosses
- Achieve the highest score!

## 🎯 **JAVA ADVANTAGES:**

### ⚡ **Performance:**
- **Native desktop performance** - no browser overhead
- **Smooth 60 FPS** gameplay with proper game loop
- **Efficient memory management** with Java garbage collection
- **Responsive controls** with direct input handling

### 🎨 **Graphics:**
- **Java 2D Graphics** with anti-aliasing
- **Smooth animations** with proper interpolation
- **Particle effects** and glow effects
- **Crisp emoji rendering** at any size

### 🔊 **Audio:**
- **Java Sound API** for high-quality audio
- **Immediate background music start** (no browser restrictions!)
- **Proper volume control** for each sound type
- **Efficient sound management** with resource cleanup

### 🛠️ **Technical:**
- **Cross-platform compatibility** (runs anywhere Java runs)
- **Standalone executable** (no dependencies)
- **Clean object-oriented design** with proper separation
- **Easy to modify and extend**

## 📁 **PROJECT STRUCTURE:**

```
scary-cute-space-invaders-java/
├── ScarySpaceInvaders.java    # Main game class & window
├── GameObjects.java           # All game entities (Player, Enemy, etc.)
├── SoundManager.java          # Complete audio system
├── compile.sh                 # Easy compilation script
├── README.md                  # This file
└── sounds/                    # Your .wav audio files
    ├── background.wav
    ├── enemy-hit.wav
    ├── explosion.wav
    ├── juggernaut-hit.wav
    ├── powerup.wav
    └── shoot.wav
```

## 🎵 **SOUND FILE SPECIFICATIONS:**

- **Format**: WAV files (best compatibility)
- **Sample Rate**: 44.1 kHz recommended
- **Bit Depth**: 16-bit recommended
- **Channels**: Mono or Stereo (both work)

**Recommended Durations:**
- **shoot.wav**: 0.1-0.3 seconds (short pew-pew)
- **enemy-hit.wav**: 0.1-0.2 seconds (quick pop)
- **explosion.wav**: 0.3-0.8 seconds (satisfying boom)
- **powerup.wav**: 0.5-1.0 seconds (happy chime)
- **juggernaut-hit.wav**: 0.5-1.5 seconds (scary roar)
- **background.wav**: 30 seconds - 2 minutes (loops automatically)

## 🐛 **TROUBLESHOOTING:**

### **"Command 'javac' not found"**
```bash
# Install Java Development Kit
sudo apt install default-jdk  # Ubuntu/Debian
```

### **"No sound playing"**
- Check that .wav files are in the `sounds/` directory
- Verify file names match exactly (case-sensitive)
- Check console output for audio loading messages

### **"Game window too small/large"**
- The game window is fixed at 800x600 pixels
- Use your system's display scaling if needed

### **"Poor performance"**
- Close other applications to free up memory
- The game is optimized for 60 FPS on modern systems

## 💝 **CREDITS:**

**Created with love by Codegen AI** - where scary meets cute in perfect harmony! 

**Technologies Used:**
- **Java Swing** for windowing and UI
- **Java 2D Graphics** for rendering
- **Java Sound API** for audio
- **Pure emoji magic** for characters! ✨

---

### 🎮 **READY TO PLAY?**

Run `./compile.sh` and prepare for the most adorably terrifying desktop space battle of your life! 

*Warning: May cause uncontrollable smiling followed by nervous laughter* 😅👻

---

**Made with 💖 and a healthy dose of 👹 by the Codegen team!**

**Enjoy your scary-cute Java gaming experience! 🚀🎯👾**

