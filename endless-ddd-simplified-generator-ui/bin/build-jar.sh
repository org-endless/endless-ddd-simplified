#!/bin/bash

if ! bash "$(dirname "$0")/check-env.sh"; then
    echo "Error: Environment check failed, please install missing dependencies first"
    exit 1
fi
echo
echo
echo "========================================"
echo "EndlessDDD - JAR Build Script"
echo "========================================"
echo
echo "Build JAR [1/3] Building JAR file..."
echo "Using Maven wrapper to build Spring Boot application..."
cd "../"

# Detect OS and use appropriate Maven wrapper
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" || "$OSTYPE" == "win32" ]]; then
    # Windows
    if ! ./mvnw.cmd clean package -DskipTests; then
        echo "Error: Maven build failed"
        exit 1
    fi
else
    # Unix-like systems
    if ! ./mvnw clean package -DskipTests; then
        echo "Error: Maven build failed"
        exit 1
    fi
fi

echo
echo "Build JAR [2/3] Copying JAR file and configuration..."
cd "endless-ddd-simplified-generator/endless-ddd-simplified-generator-core/target"
if [ ! -f "endless-ddd-simplified-generator.jar" ]; then
    echo "Error: JAR file not generated"
    exit 1
fi

cd "../../../endless-ddd-simplified-generator-ui"
echo "Cleaning target directories..."
if [ -d "lib" ]; then
    echo "Cleaning lib directory..."
    rm -rf "lib"
fi
if [ -d "config" ]; then
    echo "Cleaning config directory..."
    rm -rf "config"
fi

echo "Creating directories..."
mkdir -p "lib"
mkdir -p "config"

if ! cp "../endless-ddd-simplified-generator/endless-ddd-simplified-generator-core/target/endless-ddd-simplified-generator.jar" "lib/"; then
    echo "Error: JAR file copy failed"
    exit 1
fi

echo "Copying configuration files..."

if ! cp "../endless-ddd-simplified-generator/endless-ddd-simplified-generator-core/config/application.yaml" "config/" 2>/dev/null; then
    echo "Warning: application.yaml copy failed, using default"
fi

if ! cp "../endless-ddd-simplified-generator/endless-ddd-simplified-generator-core/config/log4j2-prod.xml" "config/" 2>/dev/null; then
    echo "Warning: log4j2-prod.xml copy failed"
fi

if ! cp "../endless-ddd-simplified-generator/endless-ddd-simplified-generator-core/config/application-prod.yaml" "config/" 2>/dev/null; then
    echo "Warning: application-prod.yaml copy failed"
fi

echo
echo "[3/3] Modifying Spring Boot configuration for production..."
CONFIG_FILE="config/application.yaml"

if [ -f "$CONFIG_FILE" ]; then
    echo "Updating application.yaml to use prod profile..."
    # Use awk to replace dev with prod
    awk '{gsub(/active: dev/, "active: prod"); print}' "$CONFIG_FILE" > "${CONFIG_FILE}.tmp" && mv "${CONFIG_FILE}.tmp" "$CONFIG_FILE"
    echo "Successfully updated application.yaml to use prod profile"
else
    echo "Warning: application.yaml not found, skipping profile update"
fi

echo
echo "========================================"
echo "JAR build completed successfully"
echo "========================================"
echo "JAR file location: lib/endless-ddd-simplified-generator.jar"
echo "Config files location: config/"
echo "Spring Boot profile: prod"
echo
exit 0
