package ex01;

public class ThreadDistributor {

    private int id = 0;

    synchronized public void distributor(String message, int id) {
        try {
            while (this.id != id) {
                wait();
            }
            this.id = (id == 1) ? 0 : 1;
            System.out.println(message);
            notify();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
