#!/bin/bash

rm -rf target
mkdir target
javac -d target -sourcepath src/java src/java/edu/school21/printer/app/Program.java
cp -R src/resources target
jar cfm target/printer.jar src/manifest.txt -C target/ .
java -jar target/printer.jar . 0

$SHELL
