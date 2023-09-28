package ex02;

import java.util.Random;

public class Program {

    private static final String FIRST_ARG = "--arraySize=";
    private static final String SECOND_ARG = "--threadsCount=";
    private static final int MAX_ELEMENTS = 2_000_000;
    private static int length;
    private static int threads;

    public static void main(String[] args) {

        if (args.length == 2 && args[0].startsWith(FIRST_ARG) && args[1].startsWith(SECOND_ARG)) {
            parser(args);
        } else {
            System.err.println("Illegal arguments");
            System.exit(-1);
        }
        int sum = 0;
        int byThreadSum = 0;
        int[] array = new int[length];

        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(2001) - 1000;
        }
        for (int num : array) {
            sum += num;
        }
        System.out.println("Sum: " + sum);

        int numberOfElementsInOneThread = (int) Math.ceil((double) (length) / (double) (threads));
        int[] sumOfThreads = new int[threads];
        for (int i = 0; i < sumOfThreads.length; i++) {
            int start = i * numberOfElementsInOneThread;
            int end = Math.min((i + 1) * numberOfElementsInOneThread - 1, length - 1);
            SumThread thread = new SumThread(i + 1, start, end, array);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new IllegalArgumentException();
            }
            sumOfThreads[i] = thread.getSum();
            byThreadSum += sumOfThreads[i];
        }
        System.out.println("Sum by threads: " + byThreadSum);
    }

    private static void parser(String[] args) {
        try {
            length = Integer.parseInt(args[0].substring(FIRST_ARG.length()));
            threads = Integer.parseInt(args[1].substring(SECOND_ARG.length()));
            if (length < 0 || length > MAX_ELEMENTS || threads < 0 || threads > length / 2) {
                System.err.println("Illegal arguments");
                System.exit(-1);
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
