#!/bin/bash

# Define Colors for Indicators
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

LIB_DIR="src/lib/natives"

echo -e "${BLUE}[OriginLoader]${NC} Starting ARM64 Native Library Download..."

# 1. Create directory if it doesn't exist
if [ ! -d "$LIB_DIR" ]; then
    echo -e "${BLUE}[INFO]${NC} Creating directory: $LIB_DIR"
    mkdir -p "$LIB_DIR"
fi

cd "$LIB_DIR" || { echo -e "${RED}[ERROR]${NC} Could not enter directory"; exit 1; }

# 2. Download liblwjgl.so
echo -ne "${BLUE}[WAIT]${NC} Downloading liblwjgl.so... "
wget -q https://github.com/JJTech0130/Aarch64-Natives/raw/master/liblwjgl.so -O liblwjgl.so
if [ $? -eq 0 ]; then
    echo -e "${GREEN}[OK]${NC}"
else
    echo -e "${RED}[FAILED]${NC}"
fi

# 3. Download libopenal.so
echo -ne "${BLUE}[WAIT]${NC} Downloading libopenal.so... "
wget -q https://github.com/JJTech0130/Aarch64-Natives/raw/master/libopenal.so -O libopenal.so
if [ $? -eq 0 ]; then
    echo -e "${GREEN}[OK]${NC}"
else
    echo -e "${RED}[FAILED]${NC}"
fi

# 4. Create liblwjgl64.so symlink (for compatibility)
echo -ne "${BLUE}[INFO]${NC} Creating 64-bit compatibility link... "
ln -sf liblwjgl.so liblwjgl64.so
echo -e "${GREEN}[DONE]${NC}"

# 5. Final Verification
echo -e "\n${BLUE}[OriginLoader]${NC} Verifying files in $LIB_DIR:"
ls -lh liblwjgl.so libopenal.so

echo -e "\n${GREEN}[SUCCESS]${NC} All ARM64 natives are ready for Raspberry Pi 5."