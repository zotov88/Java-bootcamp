1. Создание целевого каталога
mkdir target

2. Распаковка библиотек в целевой каталог
cd target
jar xf ../lib/jcommander-1.82.jar
jar xf ../lib/JCDP-4.0.2.jar
cd ../

3. Компиляция программы
javac -d target -sourcepath src/java src/java/edu/school21/printer/app/Program.java -classpath lib/\*
или
javac -d target -sourcepath src/java src/java/edu/school21/printer/app/Program.java -classpath "./lib/JCDP-4.0.2.jar:./lib/jcommander-1.82.jar"

4. Перенос нужных файлов в целевой каталог
cp -r src/resources target/resources

5. Создание jar-архива
jar cfm target/printer.jar src/manifest.txt -C target/ .

6. Запуск jar-архива
java -jar target/printer.jar --white=RED --black=GREEN


#Скачивание библиотек
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar -o lib/jcommander-1.82.jar
curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar -o lib/JCDP-4.0.2.jar
