package ex03;

import java.io.IOException;

public class Program {

    private static Downloader[] threads;

    public static void main(String[] args) {
        int threadsCount;
        if ((threadsCount = getCountOfThreads(args)) < 1) {
            System.out.println("Invalid threadsCount");
            System.exit(-1);
        }
        try {
            createThreads(threadsCount);
            startThreads(threadsCount);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getCountOfThreads(String[] args) {
        int countOfThreads = 0;
        if (args.length == 1 && args[0].startsWith("--threadsCount=")) {
            try {
                countOfThreads = Integer.parseInt(args[0].substring("--threadsCount=".length()));
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid threadsCount");
            System.exit(-1);
        }
        return countOfThreads;
    }

    private static void createThreads(int threadsCount) throws IOException {
        UrlHandler urls = new UrlHandler();
        threads = new Downloader[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new Downloader(urls, i + 1);
        }
    }

    private static void startThreads(int threadsCount) {
        for (int i = 0; i < threadsCount; i++) {
            threads[i].start();
        }
    }
}
