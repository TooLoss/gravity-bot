#!/bin/bash
SRC="src"
BIN="bin"

# Clean
rm -rf $BIN && mkdir -p $BIN

# Find logic
FILES=$(find $SRC -name "*.java" | grep -v "Test.java" | grep -v "interface/")

# Compile action
if [ -n "$FILES" ]; then
    # -d: output dir | -sourcepath: resolve dependencies
    javac -d $BIN -sourcepath $SRC $FILES
    [ $? -eq 0 ] && echo "Success: $BIN/" || echo "Compile failed."
else
    echo "No files."
fi
