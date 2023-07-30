package ex01;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        boolean isSimple = true;
        int num = new Scanner(System.in).nextInt();
        if (num < 2) {
            System.err.print("Illegal Argument");
            System.exit(-1);
        }
        int i;
        for (i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                isSimple = false;
                break;
            }
        }
        System.out.println(isSimple + " " + --i);
    }
}
