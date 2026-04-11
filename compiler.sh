#!/bin/bash

# Chemins
SRC="src"
BIN="bin"

# 1. Clean
rm -rf $BIN && mkdir -p $BIN

# 2. Liste fichiers (Exclut *Test.java et dossier interface)
# -not -name "*Test.java" : ignore les tests
# -path "$SRC/interface" -prune : ignore l'interface
FILES=$(find $SRC -path "$SRC/interface" -prune -o -name "*.java" -not -name "*Test.java" -print)

# 3. Compilation simple
echo "Compilation du coeur du projet..."
javac -d $BIN $FILES

if [ $? -eq 0 ]; then
    echo "Succès : Fichiers compilés dans $BIN/"
else
    echo "Erreur : Échec de la compilation."
    exit 1
fi
