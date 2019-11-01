package View;

import java.awt.*;

public abstract class TrafficLightView {
    public int x;
    public int y;
    protected int width;
    protected int height;
    private final boolean isGreen;

    protected TrafficLightView(int x, int y, Boolean isGreen){
        this.x = x;
        this.y = y;
        this.isGreen = isGreen;
    }
    public abstract void draw(Graphics g);
}
