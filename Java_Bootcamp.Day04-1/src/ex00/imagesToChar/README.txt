1. Создание целевой папки
mkdir target

2. Компиляция
javac -d target -sourcepath src/java src/java/edu/school21/printer/app/Program.java

3. Запуск
java -classpath target edu.school21.printer.app.Program src/resources/img/it.bmp . 0
