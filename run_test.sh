#!/bin/bash

# Chemins
SRC="src"
BIN="bin"
JUNIT="/mnt/n7fs/ens/tp_cregut/junit4.jar"
CP="$JUNIT:."

# 1. Clean
rm -rf $BIN && mkdir -p $BIN

# 2. Compile tout SAUF src/interface
echo "Compilation (hors interface)..."
# find exclut le dossier interface
FILES=$(find $SRC -path "$SRC/interface" -prune -o -name "*.java" -print)
javac -d $BIN -cp "$JUNIT:." $FILES

if [ $? -ne 0 ]; then echo "Erreur compilation"; exit 1; fi

# 3. Run tests
echo "Tests..."
TESTS=$(find $SRC -path "$SRC/interface" -prune -o -name "*Test.java" -print)
for FILE in $TESTS; do
    CLASS=$(echo ${FILE#$SRC/} | sed 's/\//./g; s/\.java//')
    echo "Running: $CLASS"
    java -cp "$CP" org.junit.runner.JUnitCore $CLASS
done
