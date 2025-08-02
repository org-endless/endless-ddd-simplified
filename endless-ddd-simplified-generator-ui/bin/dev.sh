#!/bin/bash
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"

if ! bash "${SCRIPT_DIR}/build-jar.sh"; then
    echo "Error: JAR build failed"
    exit 1
fi

echo
echo
echo "========================================"
echo "EndlessDDD - Development Mode"
echo "========================================"

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
echo "Development [2/2] Starting development application..."
echo "Starting EndlessDDD application for debugging..."
cd "${PROJECT_DIR}" && cargo run --no-default-features

echo
echo "========================================"
echo "Development mode ended"
echo "========================================" 
