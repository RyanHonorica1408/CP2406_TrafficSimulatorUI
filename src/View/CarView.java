package View;

import java.awt.*;

public abstract class CarView {
    public int x,y;
    public Color color;
    public int xDir, yDir;
    public float xSpeed, ySpeed;

    public CarView(int x, int y, Color color,int xv,int yv) {
        this.x = x;
        this.y = y;
        this.color = color;
        xDir = +1;
        yDir = +1;
        xSpeed = xv;
        ySpeed = yv;
    }

    public void move(){
        x += (int) xSpeed*xDir;
        y += (int) ySpeed*yDir;
    }

    public abstract void update(int boundaryWidth, int boundaryHeight);
    public abstract void draw(Graphics g);
}
