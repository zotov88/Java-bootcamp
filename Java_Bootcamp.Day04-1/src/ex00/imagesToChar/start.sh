#!/bin/bash

rm -rf target
mkdir target
javac -d target -sourcepath src/java src/java/edu/school21/printer/app/Program.java
java -classpath target edu.school21.printer.app.Program src/resources/img/it.bmp . 0

$SHELL
