#!/bin/bash

echo "========================================"
echo "EndlessDDD - Development Mode"
echo "========================================"

# Check environment
echo "[1/5] Checking development environment..."
if ! bash "$(dirname "$0")/check-env.sh"; then
    echo "Error: Environment check failed, please install missing dependencies first"
    exit 1
fi

# Build JAR
echo
echo "[2/5] Building JAR file..."
if ! bash "$(dirname "$0")/build-jar.sh"; then
    echo "Error: JAR build failed"
    exit 1
fi

# Clean Rust cache
echo
echo "[3/5] Cleaning Rust cache..."
cd "$(dirname "$0")/.."
if [ -d "target" ]; then
    echo "Cleaning Rust build cache..."
    cargo clean
fi

# Compile application
echo
echo "[4/5] Compiling Tauri application..."
if [ ! -f "lib/endless-ddd-simplified-generator.jar" ]; then
    echo "Error: JAR file not found, please run build-jar.sh first"
    exit 1
fi

echo "Compiling Rust application..."
if ! cargo build --no-default-features; then
    echo "Error: Rust compilation failed"
    exit 1
fi

# Start application
echo
echo "[5/5] Starting development application..."
echo "Starting EndlessDDD application for debugging..."
echo
echo "Tips:"
echo "- Application will open desktop window automatically"
echo "- Click 'Environment Test' to test system status"
echo "- Click 'Project Wizard' to create new project"
echo "- Closing window will automatically clean Spring Boot process"
echo
read -p "Press Enter to start application..."

echo "Starting application..."
cargo run --no-default-features

echo
echo "========================================"
echo "Development mode ended"
echo "========================================" 