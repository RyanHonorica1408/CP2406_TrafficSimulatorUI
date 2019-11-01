package view;

import java.awt.*;

public abstract class CarView {
    public int x, y;
    protected final Color color;
    protected int xDir;
    protected int yDir;
    public float xSpeed, ySpeed;

    protected CarView(int x, int y, Color color, int xv, int yv) {
        this.x = x;
        this.y = y;
        this.color = color;
        xDir = +1;
        yDir = +1;
        xSpeed = xv;
        ySpeed = yv;
    }

    protected void move() {
        x += (int) xSpeed * xDir;
        y += (int) ySpeed * yDir;
    }

}
