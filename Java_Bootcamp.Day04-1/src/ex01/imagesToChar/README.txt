1. Создание целевой папки
mkdir target

2. Компиляция
javac -d target -sourcepath src/java/ src/java/edu/school21/printer/app/Program.java

3. Перенос ресурсов
cp -R src/resources target

4. Создание архива
jar cfm target/printer.jar src/manifest.txt -C target/ .

5. Запуск
java -jar target/printer.jar . 0
