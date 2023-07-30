package ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int number;
        int countOfSimpleIntegers = 0;
        while ((number = sc.nextInt()) != 42) {
            if (isSimple(getSumOfDigit(number))) {
                countOfSimpleIntegers++;
            }
        }
        System.out.println("Count of coffee-request – " + countOfSimpleIntegers);
    }

    private static int getSumOfDigit(int number) {
        int result = 0;
        while (number > 0) {
            result += number % 10;
            number /= 10;
        }
        return result;
    }

    private static boolean isSimple(int sumOfDigit) {
        if (sumOfDigit < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(sumOfDigit); i++) {
            if (sumOfDigit % i == 0) {
                return false;
            }
        }
        return true;
    }

}
