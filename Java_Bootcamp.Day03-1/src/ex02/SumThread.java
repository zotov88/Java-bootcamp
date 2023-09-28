package ex02;

public class SumThread extends Thread {

    private final int numOfThread;
    private final int from;
    private final int to;
    private final int[] array;
    private int sum;

    public SumThread(int numOfThread, int from, int to, int[] array) {
        this.numOfThread = numOfThread;
        this.from = from;
        this.to = to;
        this.array = array;
    }

    @Override
    public void run() {
        sum = 0;
        for (int i = this.from; i <= this.to; i++) {
            if (i < array.length)
                sum += array[i];
        }
        System.out.printf("Thread %d: from %d to %d sum is %d\n", numOfThread, from, to, sum);
    }

    public int getSum() {
        return sum;
    }
}
