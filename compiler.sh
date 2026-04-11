#!/bin/bash

SRC="src"
BIN="bin"

# 1. Clean
rm -rf $BIN && mkdir -p $BIN

# 2. Liste via filtre inverse
# Trouve tous les .java
# grep -v : exclut les lignes contenant "Test.java" ou "interface/"
FILES=$(find $SRC -name "*.java" | grep -v "Test.java" | grep -v "interface/")

# 3. Compile
if [ -z "$FILES" ]; then
    echo "Rien à compiler."
    exit 0
fi

echo "Fichiers sélectionnés :"
echo "$FILES"

echo "---"
javac -d $BIN $FILES

[ $? -eq 0 ] && echo "Fini : $BIN/" || echo "Échec compilation."
