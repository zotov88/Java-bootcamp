package ex02;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class FileManager {

    private String path;
    private String input;
    private final Scanner sc;

    public FileManager(final String path) {
        this.path = getPath(path);
        sc = new Scanner(System.in);
    }

    private String getPath(String path) {
        path = path.substring(17);
        if (new File(path).isDirectory()) {
            return path;
        }
        throw new IllegalArgumentException("Incorrect path");
    }

    public void run() {
        while (!(input = sc.nextLine()).equals("exit")) {
            if (input.startsWith("mv ")) mv();
            if (input.equals("ls")) ls();
            if (input.startsWith("cd ")) cd();
        }
        sc.close();
    }

    private void mv() {
        String[] data = input.split(" ");
        if (data.length == 3) {
            renameOrMoving(data[1], data[2]);
        }
    }

    private void renameOrMoving(String fileName, String newPath) {
        File file = new File(path + File.separator + fileName);
        File newFile;
        if (newPath.startsWith("../")) {
            newFile = new File(getNewPath(newPath) + File.separator + fileName);
        } else if (newPath.contains("/")) {
            newFile = new File(newPath + File.separator + fileName);
            if (!newFile.isDirectory()) {
                newFile = new File(path + File.separator + newPath + File.separator + fileName);
            }
        } else {
            newFile = new File(path + File.separator + newPath);
            if (newFile.isDirectory()) {
                newFile = new File(path + File.separator + newPath + File.separator + fileName);
            }
        }
        file.renameTo(newFile);
    }

    private String getNewPath(String newPath) {
        String tmpPath = path;
        int count = 0;
        while (newPath.startsWith("..")) {
            newPath = newPath.substring(newPath.length() == 2 ? 2 : 3);
            count++;
        }
        while (count-- > 0) {
            int index = tmpPath.lastIndexOf("/");
            tmpPath = tmpPath.substring(0, index);
        }
        if (newPath.length() > 0) {
            tmpPath += File.separator + newPath;
        }
        return tmpPath;
    }

    private void ls() {
        File file = new File(path);
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                System.out.printf("%s %d KB\n", f.getName(), f.length() / 1024);
            }
        }
    }

    private void cd() {
        String[] data = input.split(" ");
        if (data.length == 2) {
            if (data[1].startsWith("..")) {
                path = getNewPath(data[1]);
            } else {
                if (data[1].contains("/") && new File(data[1]).isDirectory()) {
                    path = data[1];
                } else if (new File(path + File.separator + data[1]).isDirectory()) {
                    path += File.separator + data[1];
                }
            }
        }
        System.out.println(path);
    }

}
