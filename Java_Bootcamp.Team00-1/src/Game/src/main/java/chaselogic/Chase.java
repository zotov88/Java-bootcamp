package chaselogic;

import game.Enemy;
import game.Field;
import game.Player;
import game.Point;

public class Chase extends Thread {

    private final Player player;
    private final Enemy enemy;
    private final char[][] field;
    private boolean isTurn = true;

    public Chase(char[][] field, int enemyY, int enemyX, int playerY, int playerX) {
        this.enemy = new Enemy(enemyY, enemyX);
        this.field = field;
        this.player = new Player(playerX, playerY);
    }

    @Override
    public void run() {
        int[][] tmpFiled = new int[field.length][field[0].length];
        synchronized (field) {
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    if (field[i][j] == Field.getPlayer())
                        tmpFiled[i][j] = -2;
                    else if (field[i][j] == Field.getEmpty())
                        tmpFiled[i][j] = 0;
                    else
                        tmpFiled[i][j] = -1;
                }
            }
        }
        tmpFiled[enemy.getY()][enemy.getX()] = 1;
        int d = 1;
        isTurn = true;
        while (isTurn) {
            isTurn = false;
            for (int i = 0; i < tmpFiled.length; i++) {
                for (int j = 0; j < tmpFiled[i].length; j++) {
                    if (tmpFiled[i][j] == d) {
                        if (tryEnemyMove(tmpFiled, i + 1, j, d)
                                || tryEnemyMove(tmpFiled, i + -1, j, d)
                                || tryEnemyMove(tmpFiled, i, j + 1, d)
                                || tryEnemyMove(tmpFiled, i, j + -1, d)) {
                            enemyToPlayer(tmpFiled, field, enemy, player);
                            return;
                        }
                    }
                }
            }
            d++;
        }
    }

    public synchronized void enemyToPlayer(int[][] copy, char[][] area, Enemy enemy, Player player) {
        char ch;
        Point point;
        point = getLessNearPoint(copy, player.getY(), player.getX());
        while (copy[point.getY()][point.getX()] > 2) {
            point = getLessNearPoint(copy, point.getY(), point.getX());
        }
        if (area[point.getY()][point.getX()] == Field.getEnemy()) {
            return;
        }
        if (copy[point.getY()][point.getX()] == copy[player.getY()][player.getX()]) {
            synchronized (System.out) {
                System.out.println("You lose");
                System.exit(0);
            }
        }
        ch = area[point.getY()][point.getX()];
        area[point.getY()][point.getX()] = area[enemy.getY()][enemy.getX()];
        area[enemy.getY()][enemy.getX()] = ch;
    }

    public Point getLessNearPoint(int[][] copy, int y, int x) {
        Point point = new Point();
        point.setY(y);
        point.setX(x);
        int min = 0;

        try {
            if (copy[y + 1][x] > 1) {
                point.setX(x);
                point.setY(y + 1);
                min = copy[y + 1][x];
            }
        } catch (Exception ignored) {
        }
        try {
            if (copy[y - 1][x] > 1 && (min == 0 || copy[y - 1][x] < min)) {
                point.setX(x);
                point.setY(y - 1);
                min = copy[y - 1][x];
            }
        } catch (Exception ignored) {
        }
        try {
            if (copy[y][x + 1] > 1 && (min == 0 || copy[y][x + 1] < min)) {
                point.setX(x + 1);
                point.setY(y);
                min = copy[y][x + 1];
            }
        } catch (Exception ignored) {
        }
        try {
            if (copy[y][x - 1] > 1 && (min == 0 || copy[y][x - 1] < min)) {
                point.setX(x - 1);
                point.setY(y);
            }
        } catch (Exception ignored) {
        }
        return point;
    }

    public boolean tryEnemyMove(int[][] copy, int i, int j, int d) {
        try {
            if (copy[i][j] == -2) {
                copy[i][j] = d + 1;
                return true;
            }
            if (copy[i][j] != -1 && (copy[i][j] == 0 || copy[i][j] > d + 1)) {
                copy[i][j] = d + 1;
                isTurn = true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}
