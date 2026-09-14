#!/bin/bash

# Define Colors for Indicators
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

LIB_DIR="src/lib/natives"

echo -e "${BLUE}[OriginLoader]${NC} Starting AMD64 (x86_64) Native Library Download..."

# 1. Create directory if it doesn't exist
if [ ! -d "$LIB_DIR" ]; then
    echo -e "${BLUE}[INFO]${NC} Creating directory: $LIB_DIR"
    mkdir -p "$LIB_DIR"
fi

cd "$LIB_DIR" || { echo -e "${RED}[ERROR]${NC} Could not enter directory"; exit 1; }

# 2. Download official LWJGL 2.9.3 Linux natives JAR
echo -ne "${BLUE}[WAIT]${NC} Downloading LWJGL 2.9.3 Linux platform JAR... "
wget -q "https://repo1.maven.org/maven2/org/lwjgl/lwjgl/lwjgl-platform/2.9.3/lwjgl-platform-2.9.3-natives-linux.jar" -O temp_natives.jar
if [ $? -eq 0 ]; then
    echo -e "${GREEN}[OK]${NC}"
else
    echo -e "${RED}[FAILED]${NC}"
    exit 1
fi

# 3. Extract AMD64 (64-bit) shared libraries
echo -ne "${BLUE}[WAIT]${NC} Extracting x86_64 native libraries... "
unzip -q -o temp_natives.jar liblwjgl64.so libopenal64.so
if [ $? -eq 0 ]; then
    echo -e "${GREEN}[OK]${NC}"
else
    echo -e "${RED}[FAILED]${NC}"
fi

# 4. Clean up temporary archive
rm -f temp_natives.jar

# 5. Create compatibility symlinks if needed by standard names
ln -sf liblwjgl64.so liblwjgl.so
ln -sf libopenal64.so libopenal.so

# 6. Final Verification
echo -e "\n${BLUE}[OriginLoader]${NC} Verifying files in $LIB_DIR:"
ls -lh liblwjgl64.so libopenal64.so

echo -e "\n${GREEN}[SUCCESS]${NC} All AMD64 (x86_64) natives are ready."