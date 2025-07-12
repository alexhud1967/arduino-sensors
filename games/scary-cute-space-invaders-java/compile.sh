#!/bin/bash

# 🚀 Scary-Cute Space Invaders - Java Compilation Script 🚀

echo "👹💕 Compiling Scary-Cute Space Invaders... 💕👹"

# Create sounds directory if it doesn't exist
if [ ! -d "sounds" ]; then
    echo "📁 Creating sounds/ directory..."
    mkdir sounds
    echo "⚠️  Please copy your .wav sound files to the sounds/ directory:"
    echo "   sounds/background.wav"
    echo "   sounds/enemy-hit.wav"
    echo "   sounds/explosion.wav"
    echo "   sounds/juggernaut-hit.wav"
    echo "   sounds/powerup.wav"
    echo "   sounds/shoot.wav"
    echo ""
fi

# Compile all Java files
echo "⚙️  Compiling Java files..."
javac *.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo ""
    echo "🎮 To run the game:"
    echo "   java ScarySpaceInvaders"
    echo ""
    echo "🔊 Make sure your sound files are in the sounds/ directory!"
    echo ""
    
    # Check if sound files exist
    echo "📋 Checking for sound files..."
    sound_files=("background.wav" "enemy-hit.wav" "explosion.wav" "juggernaut-hit.wav" "powerup.wav" "shoot.wav")
    missing_files=0
    
    for file in "${sound_files[@]}"; do
        if [ -f "sounds/$file" ]; then
            echo "  ✅ sounds/$file"
        else
            echo "  ❌ sounds/$file (missing)"
            missing_files=$((missing_files + 1))
        fi
    done
    
    if [ $missing_files -eq 0 ]; then
        echo ""
        echo "🎵 All sound files found! Ready to play with full audio!"
        echo ""
        echo "🚀 Starting the game..."
        java ScarySpaceInvaders
    else
        echo ""
        echo "⚠️  $missing_files sound file(s) missing. Game will run but some sounds won't play."
        echo "📁 Copy your .wav files to the sounds/ directory for full audio experience!"
        echo ""
        read -p "🎮 Start game anyway? (y/n): " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            java ScarySpaceInvaders
        fi
    fi
else
    echo "❌ Compilation failed!"
    exit 1
fi

