package game;

import chaselogic.Chase;
import game.constants.Textures;
import game.interfaces.MovedAble;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

public class App {

    private App() {
    }

    public static void run(char[][] field, MovedAble player, Paint paint, boolean isProd) {
        Enemy[] enemies = new Enemy[Field.getCountEnemies()];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        int k = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == Textures.ENEMY)
                    enemies[k++] = new Enemy(i, j);
            }
        }
        try {
            paint.drawField(field, isProd);
            while (true) {
                System.out.println("Input: ");
                input = br.readLine();
                if (input.length() != 1)
                    continue;
                if (input.charAt(0) == '9')
                    break;
                if (input.charAt(0) == '\n')
                    continue;
                if (checkPlayerMove(field, player, input.charAt(0))) {
                    paint.drawField(field, isProd);
                    k = 0;
                    for (int i = 0; i < field.length; i++) {
                        for (int j = 0; j < field[i].length; j++) {
                            if (field[i][j] == Textures.ENEMY)
                                enemies[k++] = new Enemy(i, j);
                        }
                    }
                    Thread[] th = new Thread[enemies.length];
                    for (int i = 0; i < enemies.length; i++) {
                        if (!isProd) {
                            System.out.println("8 - enemy step\n9 - exit:");
                            while (true) {
                                String str = br.readLine();
                                if (str.length() != 1)
                                    continue;
                                if (str.charAt(0) == '8')
                                    break;
                                if (str.charAt(0) == '9') {
                                    br.close();
                                    System.exit(0);
                                }
                            }
                        }
                        th[i] = new Chase(field, enemies[i].getY(), enemies[i].getX(), player.getY(), player.getX());
                        th[i].start();
                        if (!isProd) {
                            th[i].join();
                            paint.drawField(field, isProd);
                        }
                    }
                    if (isProd) {
                        for (Thread t : th)
                            t.join();
                    }
                }
                if (!isProd) {
                    System.out.println("Enter move key:");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    public static boolean checkPlayerMove(char[][] area, MovedAble player, char action) {
        switch (action) {
            case 'W':
                return playerMove(player, area, -1, 0);
            case 'A':
                return playerMove(player, area, 0, -1);
            case 'S':
                return playerMove(player, area, 1, 0);
            case 'D':
                return playerMove(player, area, 0, 1);
            default:
                return false;
        }
    }

    public static boolean playerMove(MovedAble player, char[][] field, int y, int x) {
        char ch;
        try {
            ch = field[player.getY() + y][player.getX() + x];
        } catch (Exception ignored) {
            return true;
        }
        if (ch == Textures.ENEMY) {
            System.out.println("You loose");
            System.exit(0);
        }
        if (ch == Textures.WALL) {
            return true;
        }
        if (ch == Textures.FINISH) {
            System.out.println("You win!");
            System.exit(0);
        }
        field[player.getY()][player.getX()] = ch;
        field[player.getY() + y][player.getX() + x] = Textures.PLAYER;
        player.appendX(x);
        player.appendY(y);
        return true;
    }
}
