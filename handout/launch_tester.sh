#!/bin/bash
# CharacterAnimationPhysicsTester v4.0 Launcher
# Comprehensive Physics & Parallax Background Tuner

cd "$(dirname "$0")"

echo "╔════════════════════════════════════════════════════════════════╗"
echo "║  🎮 Character Animation Physics & Parallax Tester v4.0        ║"
echo "║     Comprehensive Game Tuning Suite                           ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""
echo "Compiling..."
javac -cp src src/CharacterAnimationPhysicsTester.java -d bin
if [ $? -ne 0 ]; then
    echo "❌ Compilation failed!"
    exit 1
fi

echo "✓ Compilation successful!"
echo ""
echo "Launching tester..."
java -cp src:bin CharacterAnimationPhysicsTester

