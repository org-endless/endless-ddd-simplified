#!/bin/bash

echo "========================================"
echo "EndlessDDD - Production Build Script"
echo "========================================"

# Clean build artifacts
echo
echo "[1/4] Cleaning build artifacts..."
cd "$(dirname "$0")/../../.."
echo "Cleaning Maven build artifacts..."
if [ -d "endless-ddd-simplified-starter/target" ]; then
    rm -rf "endless-ddd-simplified-starter/target"
fi
if [ -d "endless-ddd-simplified-generator/endless-ddd-simplified-generator-common/target" ]; then
    rm -rf "endless-ddd-simplified-generator/endless-ddd-simplified-generator-common/target"
fi
if [ -d "endless-ddd-simplified-generator/endless-ddd-simplified-generator-components/target" ]; then
    rm -rf "endless-ddd-simplified-generator/endless-ddd-simplified-generator-components/target"
fi
if [ -d "endless-ddd-simplified-generator/endless-ddd-simplified-generator-core/target" ]; then
    rm -rf "endless-ddd-simplified-generator/endless-ddd-simplified-generator-core/target"
fi
cd endless-ddd-simplified-generator-ui
echo "Cleaning Rust build artifacts..."
if [ -d "target" ]; then
    rm -rf target
fi
if [ -f "Cargo.lock" ]; then
    rm Cargo.lock
fi

# Build JAR
echo
echo "[2/4] Building JAR file..."
if ! bash "$(dirname "$0")/build-jar.sh"; then
    echo "Error: JAR build failed"
    exit 1
fi

# Build installer
echo
echo "[3/4] Building installer..."
echo "Using Tauri to build production version..."
if ! cargo tauri build; then
    echo "Error: Tauri build failed"
    exit 1
fi

# Show results
echo
echo "[4/4] Build completed"
echo "========================================"
echo "Production build completed successfully"
echo "========================================"
echo
echo "Build results:"
echo "- Executable: target/release/endless-ddd"
echo "- Installer: target/release/bundle/"
echo
echo "Installer locations:"
for file in target/release/bundle/msi/*.msi; do
    if [ -f "$file" ]; then
        echo "- $file"
    fi
done
for file in target/release/bundle/deb/*.deb; do
    if [ -f "$file" ]; then
        echo "- $file"
    fi
done
for file in target/release/bundle/dmg/*.dmg; do
    if [ -f "$file" ]; then
        echo "- $file"
    fi
done
echo 