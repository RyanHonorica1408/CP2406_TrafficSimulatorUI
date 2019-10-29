package Model;

import View.RoadView;

import java.awt.*;

public class Road extends RoadView {
    private int roadId;
    private int length,width,height;
    private boolean LightStart,LightEnd;
    private Color color;

    public Road(int x, int y, int width, int height, Color color){
        super(x,y,color);
        this.width = width;
        this.height = height;
        this.length = width;

    }
    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isLightStart() {
        return LightStart;
    }

    public void setLightStart(boolean lightStart) {
        LightStart = lightStart;
    }

    public boolean isLightEnd() {
        return LightEnd;
    }

    public void setLightEnd(boolean lightEnd) {
        LightEnd = lightEnd;
    }

    @Override
    public void update(int boundaryWidth, int boundaryHeight) {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x-width/2,y-height/2,width,height);
    }
}