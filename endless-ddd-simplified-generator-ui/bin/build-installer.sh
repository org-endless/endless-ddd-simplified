#!/bin/bash
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"

if ! bash "${SCRIPT_DIR}/build-jar.sh"; then
    echo "Error: JAR build failed"
    exit 1
fi
echo "========================================"
echo "EndlessDDD - Production Build Script"
echo "========================================"

echo
echo
echo "[1/3] Cleaning build artifacts..."
echo
echo "Development [1/2] Cleaning build artifacts..."
echo "Cleaning lib and config directories..."
if [ -f "${PROJECT_DIR}/target/release/lib" ]; then
    rm "${PROJECT_DIR}/target/release/lib/*"
fi
if [ -f "${PROJECT_DIR}/target/release/bundle" ]; then
    rm "${PROJECT_DIR}/target/release/bundle/*"
fi
if [ -f "${PROJECT_DIR}/target/release/config" ]; then
    rm "${PROJECT_DIR}/target/release/config/*"
fi
if [ -f "${PROJECT_DIR}/Cargo.lock" ]; then
    rm "${PROJECT_DIR}/Cargo.lock"
fi

echo
echo "[2/3] Building installer..."
echo "Using Tauri to build production version..."
if ! cd "${PROJECT_DIR}" && cargo tauri build; then
    echo "Error: Tauri build failed"
    exit 1
fi

# Show results
echo
echo "[3/3] Build completed"
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
