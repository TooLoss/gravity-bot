#!/bin/bash

# Chemins fixes
SRC="src"
BIN="bin"
JUNIT="/mnt/n7fs/ens/tp_cregut/junit4.jar"
CP="$BIN:$JUNIT:."

# 1. Clean
rm -rf $BIN && mkdir -p $BIN

# 2. Compile tout (src vers bin)
echo "Compilation..."
javac -d $BIN -cp "$JUNIT:." $(find $SRC -name "*.java")

if [ $? -ne 0 ]; then echo "Erreur compilation"; exit 1; fi

# 3. Run tests
echo "Tests..."
for FILE in $(find $SRC -name "*Test.java"); do
    # src/core/collisions/MyTest.java -> core.collisions.MyTest
    CLASS=$(echo ${FILE#$SRC/} | sed 's/\//./g; s/\.java//')
    
    echo "Running: $CLASS"
    java -cp "$CP" org.junit.runner.JUnitCore $CLASS
done
