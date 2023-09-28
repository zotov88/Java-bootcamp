package chaselogic.abstracts;

import chaselogic.interfaces.MovedAble;

public abstract class AbstractMovedAble implements MovedAble {

    private int x;
    private int y;

    public AbstractMovedAble(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void appendX(int x) {
        this.x += x;
    }

    @Override
    public void appendY(int y) {
        this.y += y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
