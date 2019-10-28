package View;

import Model.TrafficLight;

import java.awt.*;

public abstract class TrafficLightView {
    public int x,y, width,height;
    public boolean isGreen;

    public TrafficLightView(int x, int y, Boolean isGreen){
        this.x = x;
        this.y = y;
        this.isGreen = isGreen;
    }
    public abstract void draw(Graphics g);
}
