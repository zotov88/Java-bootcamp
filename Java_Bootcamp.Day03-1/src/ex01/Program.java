package ex01;

public class Program {

    private static final String ARG = "--count=";
    private static int count = 0;

    public static void main(String[] args) {
        if (args.length == 1 && args[0].startsWith(ARG)) {
            parser(args);
        } else {
            System.err.println("Illegal arguments");
            System.exit(-1);
        }
        ThreadDistributor distributor = new ThreadDistributor();
        Thread one = new Thread(() -> print("Egg", distributor, 0));
        Thread two = new Thread(() -> print("Hen", distributor, 1));
        two.start();
        one.start();
    }

    private static void print(String message,
                              ThreadDistributor distributor,
                              int index) {
        for (int i = 0; i < count; i++) {
            distributor.distributor(message, index);
        }
    }

    private static void parser(String[] args) {
        try {
            count = Integer.parseInt(args[0].substring(ARG.length()));
            if (count < 0) {
                System.err.println("Illegal arguments");
                System.exit(-1);
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
