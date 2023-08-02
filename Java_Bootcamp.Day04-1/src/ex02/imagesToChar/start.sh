#!/bin/bash

mkdir target
cd target
jar xf ../lib/jcommander-1.82.jar
jar xf ../lib/JCDP-4.0.2.jar
cd ../
javac -d target -sourcepath src/java src/java/edu/school21/printer/app/Program.java -classpath lib/\*
cp -r src/resources target/resources/
jar cfm target/printer.jar src/manifest.txt -C target .
java -jar target/printer.jar --white=RED --black=GREEN

$SHELL
