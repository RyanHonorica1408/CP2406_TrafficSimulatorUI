package View;

import java.awt.*;

public abstract class RoadView {
    public int x,y;
    public Color color;

    public RoadView(int x, int y, Color colorRoad){
        this.x = x;
        this.y = y;
        color = colorRoad;
    }

    public abstract void update(int boundaryWidth, int boundaryHeight);
    public abstract void draw(Graphics g);
}
