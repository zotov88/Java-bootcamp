package ex05;

import java.util.Scanner;

public class Program {

    private final static int FIRST_MON = 6;
    private final static int FIRST_TUE = 0;
    private final static int FIRST_WED = 1;
    private final static int FIRST_THU = 2;
    private final static int FIRST_FRI = 3;
    private final static int FIRST_SAT = 4;
    private final static int FIRST_SUN = 5;
    private final static int[] VAL_DAYS = new int[]{
            FIRST_MON, FIRST_TUE, FIRST_WED, FIRST_THU, FIRST_FRI, FIRST_SAT, FIRST_SUN
    };
    private final static int MAX_STUDENTS = 10;
    private final static int MAX_CLASSES = 10;
    private final static int WEEK = 7;
    private final static int MONTH = 30;
    private final static int STATUS = 1;
    private final static int TIME_SLOTS = 5;
    private final static String TRIGGER = ".";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name, day, time, status;
        int date;
        String[] students = new String[MAX_STUDENTS];
        String[][] classes = new String[WEEK][TIME_SLOTS];
        String[][][][] attendance = new String[MAX_STUDENTS][MONTH][TIME_SLOTS][STATUS];
        String[] orderOfDays = new String[WEEK];

        for (int i = 0; !(name = sc.nextLine()).equals(TRIGGER); i++) {
            if (name.length() > 10) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }
            students[i] = name;
        }

        int countClasses = 0;
        while (!(time = sc.next()).equals(TRIGGER)) {
            if (countClasses++ > MAX_CLASSES) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }
            day = sc.next();
            if (day.equals("MO")) fillClasses(classes[getFirstDayOfWeek("MO")], time);
            if (day.equals("TU")) fillClasses(classes[getFirstDayOfWeek("TU")], time);
            if (day.equals("WE")) fillClasses(classes[getFirstDayOfWeek("WE")], time);
            if (day.equals("TH")) fillClasses(classes[getFirstDayOfWeek("TH")], time);
            if (day.equals("FR")) fillClasses(classes[getFirstDayOfWeek("FR")], time);
            if (day.equals("SA")) fillClasses(classes[getFirstDayOfWeek("SA")], time);
            if (day.equals("SU")) fillClasses(classes[getFirstDayOfWeek("SU")], time);
        }
        setOrderOfDays(orderOfDays);

        while (!(name = sc.next()).equals(TRIGGER)) {
            time = sc.next();
            date = sc.nextInt();
            status = sc.next();
            fillAttendance(attendance, students, classes, name, time, date, status);
        }

        printHat(classes, orderOfDays);
        printAttendance(attendance, classes, students);
    }

    private static void printAttendance(String[][][][] attendance,
                                        String[][] classes,
                                        String[] students) {
        for (int i = 0; i < MAX_STUDENTS && students[i] != null; i++) {
            System.out.printf("%8s", students[i]);
            printAttendanceForStudent(attendance[i], classes);
        }
    }

    private static void printAttendanceForStudent(String[][][] data,
                                                  String[][] classes) {
        for (int i = 0; i < MONTH; i++) {
            for (int j = 0; classes[i % WEEK][j] != null; j++) {
                if ("HERE".equals(data[i][j][0])) {
                    System.out.printf("        %2d|", 1);
                } else if ("NOT_HERE".equals(data[i][j][0])) {
                    System.out.printf("        %2d|", -1);
                } else {
                    System.out.print("          |");
                }
            }
        }
        System.out.println();
    }

    private static void printHat(String[][] classes,
                                 String[] orderOfDays) {
        for (int i = 0; i < MONTH; i++) {
            printClassesOfDay(classes, orderOfDays, i);
        }
        System.out.println();
    }

    private static void printClassesOfDay(String[][] classes,
                                          String[] orderOfDays,
                                          int date) {
        if (date == 0) System.out.print("\t\t");
        int dayIndex = date % WEEK;
        for (int i = 0; i < TIME_SLOTS && classes[dayIndex][i] != null; i++) {
            System.out.print(classes[dayIndex][i] + ":00 ");
            if (dayIndex == 0) System.out.printf("%s %2d|", orderOfDays[0], (date + 1));
            if (dayIndex == 1) System.out.printf("%s %2d|", orderOfDays[1], (date + 1));
            if (dayIndex == 2) System.out.printf("%s %2d|", orderOfDays[2], (date + 1));
            if (dayIndex == 3) System.out.printf("%s %2d|", orderOfDays[3], (date + 1));
            if (dayIndex == 4) System.out.printf("%s %2d|", orderOfDays[4], (date + 1));
            if (dayIndex == 5) System.out.printf("%s %2d|", orderOfDays[5], (date + 1));
            if (dayIndex == 6) System.out.printf("%s %2d|", orderOfDays[6], (date + 1));
        }
    }

    private static void fillClasses(String[] timeSlots,
                                    String time) {
        for (int i = 0; i < TIME_SLOTS; i++) {
            if (timeSlots[i] == null) {
                timeSlots[i] = time;
                break;
            }
        }
    }

    private static void fillAttendance(String[][][][] attendance,
                                       String[] students,
                                       String[][] classes,
                                       String name,
                                       String time,
                                       int date,
                                       String status) {
        int s = getStudentIndex(students, name);
        int d = getDayIndexByDate(date);
        int k = getTimeSlotIndexByDay(classes, d, time);
        attendance[s][date - 1][k][0] = status;
    }

    private static int getTimeSlotIndexByDay(String[][] classes,
                                             int dayIndex,
                                             String time) {
        int i;
        for (i = 0; i < TIME_SLOTS; i++) {
            if (classes[dayIndex][i].equals(time)) {
                break;
            }
        }
        return i;
    }

    private static int getStudentIndex(String[] students,
                                       String name) {
        int i;
        for (i = 0; i < MAX_STUDENTS; i++) {
            if (students[i].equals(name)) {
                break;
            }
        }
        return i;
    }

    private static int getDayIndexByDate(int date) {
        int i;
        boolean isFind = false;
        for (i = 0; i < WEEK; i++) {
            for (int j = VAL_DAYS[i] + 1; j <= MONTH; j += 7) {
                if (j % date == 0) {
                    isFind = true;
                    break;
                }
            }
            if (isFind) {
                break;
            }
        }
        return VAL_DAYS[i];
    }

    private static int getFirstDayOfWeek(String day) {
        int i = 0;
        if (day.equals("MO")) i = VAL_DAYS[0];
        if (day.equals("TU")) i = VAL_DAYS[1];
        if (day.equals("WE")) i = VAL_DAYS[2];
        if (day.equals("TH")) i = VAL_DAYS[3];
        if (day.equals("FR")) i = VAL_DAYS[4];
        if (day.equals("SA")) i = VAL_DAYS[5];
        if (day.equals("SU")) i = VAL_DAYS[6];
        return i;
    }

    private static void setOrderOfDays(String[] orderOfDays) {
        orderOfDays[VAL_DAYS[0]] = "MO";
        orderOfDays[VAL_DAYS[1]] = "TU";
        orderOfDays[VAL_DAYS[2]] = "WE";
        orderOfDays[VAL_DAYS[3]] = "TH";
        orderOfDays[VAL_DAYS[4]] = "FR";
        orderOfDays[VAL_DAYS[5]] = "SA";
        orderOfDays[VAL_DAYS[6]] = "SU";
    }
}
