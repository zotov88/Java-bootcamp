package ex00;

public class Program {

    private static final String ARG = "--count=";
    private static final int SLEEP = 10;
    private static int count = 0;

    public static void main(String[] args) {
        parseCount(args);
        Thread one = new Thread(() -> print("Hen"));
        Thread two = new Thread(() -> print("Egg"));
        one.start();
        two.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        print("Human");
    }

    private static void print(String str) {
        for (int i = 0; i < count; i++) {
            try {
                Thread.sleep(SLEEP);
                System.out.println(str);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void parseCount(String[] args) {
        String input = "";
        if (args.length == 1 && args[0].startsWith(ARG)) {
            input = args[0].substring(ARG.length());
        }
        int num = -1;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.err.println("You must input --count=[positive number]");
            System.exit(-1);
        }
        if (num > -1) {
            count = num;
        } else {
            System.err.println("You must input --count=[positive number]");
            System.exit(-1);
        }
    }
}