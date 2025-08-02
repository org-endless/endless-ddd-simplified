#!/bin/bash

echo "========================================"
echo "EndlessDDD - Environment Check Script"
echo "========================================"

MISSING_DEPS=""
NEEDS_INSTALL=""

# Function to install dependencies
install_dependencies() {
    echo
    echo "Starting auto-install dependencies..."
    
    # Detect operating system
    if [[ "${OSTYPE}" == "darwin"* ]]; then
        # macOS
        if [[ "${NEEDS_INSTALL}" == *"java"* ]]; then
            echo "Installing OpenJDK 21..."
            brew install openjdk@21
            # shellcheck disable=SC2016
            echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
            echo 'export JAVA_HOME="/opt/homebrew/opt/openjdk@21"' >> ~/.zshrc
            echo "OpenJDK 21 installation completed"
        fi
        
        if [[ "${NEEDS_INSTALL}" == *"rust"* ]]; then
            echo "Installing Rust..."
            curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y
            # shellcheck disable=SC1090
            source ~/.cargo/env
            echo "Rust installation completed"
            echo "Please re-run this script to verify installation"
            exit 0
        fi
        
        if [[ "${NEEDS_INSTALL}" == *"tauri"* ]]; then
            echo "Installing Tauri CLI..."
            cargo install tauri-cli
            echo "Tauri CLI installation completed"
        fi
        
    elif [[ "${OSTYPE}" == "linux-gnu"* ]]; then
        # Linux
        if [[ "${NEEDS_INSTALL}" == *"java"* ]]; then
            echo "Installing OpenJDK 21..."
            sudo apt update
            sudo apt install -y openjdk-21-jdk
            echo "OpenJDK 21 installation completed"
        fi
        
        if [[ "${NEEDS_INSTALL}" == *"rust"* ]]; then
            echo "Installing Rust..."
            curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y
            # shellcheck disable=SC1090
            source ~/.cargo/env
            echo "Rust installation completed"
            echo "Please re-run this script to verify installation"
            exit 0
        fi
        
        if [[ "${NEEDS_INSTALL}" == *"tauri"* ]]; then
            echo "Installing Tauri CLI..."
            cargo install tauri-cli
            echo "Tauri CLI installation completed"
        fi
    fi
    
    echo
    echo "All dependencies installation completed"
    echo "Please re-run this script to verify installation"
}

# Check Java environment
echo
echo "[1/3] Checking Java environment..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    echo "Java installed - version: ${JAVA_VERSION}"
    
    # Check Java version >=21
    MAJOR_VERSION=$(echo "${JAVA_VERSION}" | cut -d'.' -f1)
    if [ "${MAJOR_VERSION}" -lt 21 ]; then
        echo "Java version too low, need Java 21 or higher"
        NEEDS_INSTALL="${NEEDS_INSTALL} java"
    else
        echo "Java version is OK"
    fi
else
    echo "Java not installed"
    MISSING_DEPS="${MISSING_DEPS} Java"
    NEEDS_INSTALL="${NEEDS_INSTALL} java"
fi

# Check Rust environment
echo
echo "[2/3] Checking Rust environment..."
if command -v rustc &> /dev/null; then
    RUST_VERSION=$(rustc --version | cut -d' ' -f2)
    echo "Rust installed - version: $RUST_VERSION"
else
    echo "Rust not installed"
    MISSING_DEPS="${MISSING_DEPS} Rust"
    NEEDS_INSTALL="${NEEDS_INSTALL} rust"
fi

# Check Tauri CLI
echo
echo "[3/3] Checking Tauri CLI..."
if command -v cargo &> /dev/null && cargo tauri --version &> /dev/null; then
    TAURI_VERSION=$(cargo tauri --version | cut -d' ' -f2)
    echo "Tauri CLI installed - version: ${TAURI_VERSION}"
else
    echo "Tauri CLI not installed"
    MISSING_DEPS="${MISSING_DEPS} Tauri CLI"
    NEEDS_INSTALL="${NEEDS_INSTALL} tauri"
fi

echo
echo "========================================"
echo "Environment check completed"
echo "========================================"

if [ -n "${MISSING_DEPS}" ]; then
    echo "Missing dependencies: ${MISSING_DEPS}"
    echo
    # shellcheck disable=SC2162
    read -p "Auto-install missing dependencies? (y/N): " AUTO_INSTALL
    if [[ ${AUTO_INSTALL} =~ ^[Yy]$ ]]; then
        install_dependencies
    else
        echo "Please install missing dependencies manually and retry"
        exit 1
    fi
else
    echo "All dependencies installed"
    exit 0
fi
