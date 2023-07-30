package ex04;

import java.util.Scanner;

public class Program {

    private static final int SCALE = 10;
    private static final int LIMIT = 10;
    private static final int MAX_CHAR_VAL = 65535;

    public static void main(String[] argv) {
        String str = new Scanner(System.in).nextLine();
        char[] allChars = str.toCharArray();
        int[] countAllChars = new int[MAX_CHAR_VAL];
        char[] chars = new char[LIMIT];
        int[] repeatChars = new int[LIMIT];
        char ch = 0;
        int count = 0;
        int index = 0;

        for (int i = 0; i < str.length(); i++) {
            countAllChars[allChars[i]]++;
        }
        for (int i = 0; i < LIMIT; i++) {
            for (int j = 0; j < MAX_CHAR_VAL; j++) {
                if (countAllChars[j] > count) {
                    count = countAllChars[j];
                    ch = (char) j;
                    index = j;
                }
            }
            repeatChars[i] = countAllChars[index];
            chars[i] = ch;
            countAllChars[index] = 0;
            ch = 0;
            count = 0;
        }
        if (countAllChars[0] > 999) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        paintGistogram(chars, repeatChars);
    }

    public static void paintGistogram(char[] chars, int[] repeatChars) {
        double k = (double) SCALE / repeatChars[0];
        for (int i = SCALE + 1; i > 0; i--) {
            for (int j = 0; j < LIMIT; j++) {
                if ((int) (repeatChars[j] * k) == i - 1) {
                    if (repeatChars[j] != 0) {
                        System.out.print(repeatChars[j] + "\t");
                    }
                }
                if ((int) (repeatChars[j] * k) >= i) {
                    System.out.print("#\t");
                }
            }
            System.out.println();
        }
        for (int i = 0; i < LIMIT; i++) {
            System.out.print(chars[i] + "\t");
        }
    }
}
