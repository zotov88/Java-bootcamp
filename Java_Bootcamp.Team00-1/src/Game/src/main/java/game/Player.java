package game;

import game.abstracts.AbstractMovedAble;

public class Player extends AbstractMovedAble {

    public Player() {
        super(Field.getPlayerX(), Field.getPlayerY());
    }

    public Player(int x, int y) {
        super(x, y);
    }
}