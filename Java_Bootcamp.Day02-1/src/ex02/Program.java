package ex02;

public class Program {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].startsWith("--current-folder=")) {
            new FileManager(args[0]).run();
        }
    }
}
