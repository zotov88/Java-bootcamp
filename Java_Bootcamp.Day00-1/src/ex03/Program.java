package ex03;

import java.util.*;

public class Program {

    private static int countOfWeek = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String week;
        long grades = 0;
        while (!(week = sc.nextLine()).equals("42")) {
            checkWeek(week);
            int grade = sc.nextInt();
            checkDigit(grade);
            for (int i = 0; i < 4; i++) {
                int tmpGrade = sc.nextInt();
                checkDigit(tmpGrade);
                if (tmpGrade < grade)
                    grade = tmpGrade;
            }
            sc.nextLine();
            long factor = 1;
            int counter = countOfWeek;
            while (--counter > 0) {
                factor *= 10;
            }
            grades = grade * factor + grades;
        }
        for (int i = 1; i <= countOfWeek; i++) {
            long repeat = grades % 10;
            grades /= 10;
            System.out.printf("Week " + i + "\t");
            for (int j = 0; j < repeat; j++) {
                System.out.print("=");
            }
            System.out.println(">");
        }
    }

    private static void checkWeek(String week) {
        if (!week.equals("Week " + ++countOfWeek)) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
    }

    public static void checkDigit(int number) {
        if (number > 9 || number < 1) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }

    }
}

