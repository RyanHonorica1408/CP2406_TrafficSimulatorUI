package View;

import java.awt.*;

public abstract class RoadView {
    public final int x;
    public final int y;
    private final Color color;

    public RoadView(int x, int y, Color colorRoad){
        this.x = x;
        this.y = y;
        color = colorRoad;
    }

    public abstract void update(int boundaryWidth, int boundaryHeight);
    public abstract void draw(Graphics g);
}
