package chaselogic;

import game.exceptions.IllegalParametersException;

import java.util.Random;

public class Field {
    private static final char PLAYER = 'o';
    private static final char EMPTY = '.';
    private static final char ENEMY = 'X';
    private static final char FINISH = 'O';
    private static final char WALL = 'W';

    private static final char PATH = 'V';
    private static int countEnemies;

    private static int finishX;
    private static int finishY;

    private static int countWalls;

    private static int sizeField;
    private static int PlayerX;
    private static int PlayerY;

    private static char[][] field;
    private static final Random random = new Random();

    public static char[][] getField() {
        return field;
    }

    public static int getCountEnemies() {
        return countEnemies;
    }

    public static int getPlayerX() {
        return PlayerX;
    }

    public static int getPlayerY() {
        return PlayerY;
    }

    private Field() {

    }

    public static void createField(int enemies, int walls, int size) {
        if (size <= 0 || enemies < 0 || walls < 0 || (size * size) < (walls + enemies + 2)) {
            throw new IllegalParametersException("Invalid input");
        }
        countEnemies = enemies;
        sizeField = size;
        countWalls = walls;
        generateMap();
        while (!findPath()) {
            generateMap();
        }
        backEmpty();
    }

    private static void backEmpty() {
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                if (field[i][j] == PATH) {
                    field[i][j] = EMPTY;
                }
            }
        }
    }

    private static void generateMap() {
        initField();
        putPlayer();
        putEnemies();
        putFinish();
        putWalls();
    }

    private static boolean findPath() {
        if (!checkPlayerAround(finishY, finishX, PLAYER)) {
            return true;
        }
        if (PlayerY + 1 < sizeField && field[PlayerY + 1][PlayerX] == EMPTY)
            field[PlayerY + 1][PlayerX] = PATH;
        if (PlayerX + 1 < sizeField && field[PlayerY][PlayerX + 1] == EMPTY)
            field[PlayerY][PlayerX + 1] = PATH;
        if (PlayerY - 1 >= 0 && field[PlayerY - 1][PlayerX] == EMPTY)
            field[PlayerY - 1][PlayerX] = PATH;
        if (PlayerX - 1 >= 0 && field[PlayerY][PlayerX - 1] == EMPTY)
            field[PlayerY][PlayerX - 1] = PATH;
        int c = 0;
        while (check() > c) {
            c = check();
            searchValid();
            if (!checkPlayerAround(finishY, finishX, PATH)) {
                return true;
            }
        }
        return false;
    }

    private static void searchValid() {
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                if (i + 1 < sizeField && field[i + 1][j] == PATH && field[i][j] == EMPTY)
                    field[i][j] = PATH;
                if (j + 1 < sizeField && field[i][j + 1] == PATH && field[i][j] == EMPTY)
                    field[i][j] = PATH;
                if (i - 1 > 0 && field[i - 1][j] == PATH && field[i][j] == EMPTY)
                    field[i][j] = PATH;
                if (j - 1 > 0 && field[i][j - 1] == PATH && field[i][j] == EMPTY)
                    field[i][j] = PATH;
            }
        }
    }

    private static int check() {
        int b = 0;
        for (int i = 0; i < sizeField; i++) {
            for (int c = 0; c < sizeField; c++) {
                if (field[i][c] == PATH)
                    b++;
            }
        }
        return b;
    }

    private static void putPlayer() {
        int y = random.nextInt(sizeField);
        int x = random.nextInt(sizeField);
        if (field[y][x] == EMPTY) {
            field[y][x] = PLAYER;
            PlayerY = y;
            PlayerX = x;
            return;
        }
        putPlayer();
    }

    private static void putFinish() {
        int y = random.nextInt(sizeField);
        int x = random.nextInt(sizeField);
        if (field[y][x] == EMPTY) {
            field[y][x] = FINISH;
            finishX = x;
            finishY = y;
            return;
        }
        putFinish();
    }

    private static void putEnemies() {
        int count = 0;
        while (count < countEnemies) {
            int y = random.nextInt(sizeField);
            int x = random.nextInt(sizeField);
            if (field[y][x] == EMPTY && checkPlayerAround(y, x, PLAYER)) {
                field[y][x] = ENEMY;
                count++;
            }
        }
    }

    private static boolean checkPlayerAround(int y, int x, char checkChar) {
        if (y - 1 >= 0 && field[y - 1][x] == checkChar) {
            return false;
        } else if (x - 1 >= 0 && field[y][x - 1] == checkChar) {
            return false;
        } else if (y + 1 < sizeField && field[y + 1][x] == checkChar) {
            return false;
        } else return x + 1 >= sizeField || field[y][x + 1] != checkChar;
    }

    private static void initField() {
        field = new char[sizeField][sizeField];
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                field[i][j] = EMPTY;
            }
        }
    }

    private static void putWalls() {
        int count = 0;
        while (count < countWalls) {
            int y = random.nextInt(sizeField);
            int x = random.nextInt(sizeField);
            if (field[y][x] == EMPTY) {
                field[y][x] = WALL;
                count++;
            }
        }
    }

    public static char getPlayer() {
        return PLAYER;
    }

    public static char getEmpty() {
        return EMPTY;
    }

    public static char getEnemy() {
        return ENEMY;
    }

    public static char getFinish() {
        return FINISH;
    }

    public static char getWall() {
        return WALL;
    }
}
