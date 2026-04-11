#!/bin/bash

# Chemins
SRC="src"
BIN="bin"
LIB="lib/*" # Doit contenir junit-4.x.jar et hamcrest-core.jar

# 1. Nettoyage et Prep
rm -rf $BIN
mkdir -p $BIN

# 2. Compilation globale
echo "--- Compilation ---"
javac -d $BIN -cp "$LIB" $(find $SRC -name "*.java")

if [ $? -ne 0 ]; then
    echo "Erreur compilation."
    exit 1
fi

# 3. Execution Tests
echo "--- Tests JUnit 4 ---"

# Trouve fichiers *Test.java
TEST_FILES=$(find $SRC -name "*Test.java")

for FILE in $TEST_FILES; do
    # Formate path vers package (ex: core/collisions/CollisionLineaireTest)
    REL_PATH=${FILE#$SRC/}
    CLASS_NAME=${REL_PATH%.java}
    DOT_NAME=${CLASS_NAME//\//.}

    echo "Exec: $DOT_NAME"
    java -cp "$BIN:$LIB" org.junit.runner.JUnitCore $DOT_NAME
done
