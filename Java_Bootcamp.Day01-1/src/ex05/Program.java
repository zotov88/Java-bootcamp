package ex05;

public class Program {
    public static void main(String[] args) {
        if (args.length != 0) {
            new Menu().app(args[0]);
        }
    }
}
