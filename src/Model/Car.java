package Model;

import View.CarView;
import java.awt.*;

public class Car extends CarView {
    private int carId,carAhead, width, height,onRoad;
    private final int startingX;
    private final int startingY;
    private boolean goingSouth,goingNorth,goingEast,goingWest,isStopped,isSlowing,atEnd;
    private int length;


    public Car(int carId, int x, int y, int width, int height, Color color,int xspeed,int yspeed) {
        super(x, y, color,xspeed,yspeed);
        this.carId = carId;
        this.width = width;
        this.height = height;
        this.startingX =x;
        this.startingY =y;
    }
    public Car(int carId,int carAhead, int x, int y, int width, int height, Color color,int xspeed,int yspeed) {
        super(x, y, color,xspeed,yspeed);
        this.carId = carId;
        this.carAhead = carAhead;
        this.width = width;
        this.height = height;
        this.startingX =x;
        this.startingY =y;
    }
    @Override
    public void update(int boundaryWidth, int boundaryHeight) {
        if ((x - width / 2 < 0 && xDir < 0) || (x + width / 2 > boundaryWidth && xDir > 0)) {
            this.setAtEnd(true);
        }
        if ((y - height / 2 < 0 && yDir < 0) || (y + height / 2 > boundaryHeight && yDir > 0)) {
            this.setAtEnd(true);
        }
    }

    @Override
    public void draw(Graphics g) {
        if(goingEast) {
            g.setColor(color);
            g.fillRect(x - width / 2, y - height / 2, width, height);
            g.setColor(Color.CYAN);
            g.fillRect(x + width / 10, y - height / 2, width / 10, height);
        } else if (goingWest){
            g.setColor(color);
            g.fillRect(x - width / 2, y - height / 2, width, height);
            g.setColor(Color.CYAN);
            g.fillRect(x - width / 10, y - height / 2, width / 10, height);
        }
        if(goingSouth){
            g.setColor(color);
            g.fillRect(x - width / 2, y - height / 2, width, height);
            g.setColor(Color.CYAN);
            g.fillRect(x-width/2, y+height/10, width, height/10);
        }
        if(goingNorth){
            g.setColor(color);
            g.fillRect(x - width / 2, y - height / 2, width, height);
            g.setColor(Color.CYAN);
            g.fillRect(x-width/2, y-height/10, width, height/10);
        }
        if(atEnd){
            g.clearRect(x-width/2,y-height/2,width,height);
            g.setColor(Color.BLACK);
            g.fillRect(x-width/2,y-height/2,width,height);
            g.setColor(Color.BLACK);
            g.fillRect(x+width/10,y-height/2,width/10,height);
        }
    }

    private void speedUp(float new_speed){
        if(goingWest || goingEast) {
            if (this.xSpeed <=6) {
                this.xSpeed += new_speed;
            }
            this.isStopped = false;
            this.isSlowing = false;
        } else if (goingSouth || goingNorth){
            if(this.ySpeed<=6){
                this.ySpeed += new_speed;
            }
            this.isStopped = false;
            this.isSlowing = false;
        }
    }

    private void slowDown(float new_speed){
        if(goingEast || goingWest){
            if(xSpeed>=0){
                this.setSlowing(true);
                this.isStopped = false;
                xSpeed -= new_speed;
                if(xSpeed <=0){
                    this.stop();
                }
            }
        } else if(goingNorth||goingSouth){
            if(ySpeed>=0){
                this.setSlowing(true);
                this.isStopped = false;
                ySpeed -= new_speed;
                if(ySpeed <=0){
                    this.stop();
                }
            }
        }
    }

    private void stop(){
        if(goingEast || goingWest) {
            xSpeed = 0;
            this.setStopped(true);
            this.isSlowing = false;
        } else if (goingNorth || goingSouth){
            ySpeed =0;
            this.setStopped(true);
            this.isSlowing = false;
        }
    }


    public void lookAhead(TrafficLight trafficLight, Car car_next, float speed,int boundaryWidth, int boundaryHeight,boolean isIntersection) {
        float roadShift = 0;
        if (isIntersection) {
            roadShift = this.width * 4;
        }
        if ((trafficLight.getOnRoad() == this.getOnRoad()) || (trafficLight.getNextRoad() == this.getOnRoad())) {
            //Going West code
            if (goingWest) {
                if (trafficLight.isGreen()) {
                    this.move();
                    this.speedUp(speed / 10);
                    this.update(boundaryWidth, boundaryHeight);
                    if (this.isStopped()) {
                        this.speedUp(speed);
                    }
                }
                if (trafficLight.isRed() && (this.x > trafficLight.getDistance() + roadShift)) {
                    this.slowDown(speed / 20);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                if (trafficLight.isRed() && (this.x < trafficLight.getDistance() + roadShift)) {
                    this.speedUp(speed / 4);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }

                if (trafficLight.isRed() && (this.x < roadShift + trafficLight.getDistance() * 1.02) && (trafficLight.getDistance() + roadShift < this.x)) {
                    this.stop();
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                try {
                    if ((car_next.x + 2 * car_next.getWidth()) > this.x) {
                        this.slowDown(speed);
                    }
                    if (car_next.x + car_next.getWidth() > this.x) {
                        this.stop();
                        this.x += car_next.getWidth();
                    }
                    if ((car_next.x > this.x - this.getWidth() / 4) && trafficLight.isRed()) {
                        this.stop();
                    }
                    if (trafficLight.isRed() && (car_next.isStopped() || car_next.isSlowing()) && (car_next.x < this.x - 2 * this.width)) {
                        this.speedUp(speed / 2);
                    }
                } catch (NullPointerException e) {
                    System.out.println(e);

                }
                //Going East Code
            }
            if (this.isGoingEast()) {
                if (trafficLight.isGreen()) {
                    this.move();
                    this.speedUp(speed / 10);
                    this.update(boundaryWidth, boundaryHeight);
                    if (this.isStopped()) {
                        this.speedUp(speed);
                    }

                }
                if (trafficLight.isRed() && (this.x < trafficLight.getDistance())) {
                    this.slowDown(speed / 20);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                if (trafficLight.isRed() && (this.x > trafficLight.getDistance())) {
                    this.speedUp(speed / 4);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                if (trafficLight.isRed() && (trafficLight.getDistance() * 0.98 <= this.x) && (this.x < trafficLight.getDistance())) {
                    this.stop();
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                try {
                    if (this.x > car_next.x - 2 * car_next.getWidth()) {
                        this.slowDown(speed);
                    }
                    if (this.x + this.width > car_next.x) {
                        this.stop();
                        this.x -= car_next.getWidth();
                    } else if ((car_next.x < this.x + this.getWidth() / 4) && trafficLight.isRed()) {
                        this.stop();
                    }
                    if (trafficLight.isRed() && (car_next.isStopped() || car_next.isSlowing()) && (car_next.x > this.x + 2 * this.width)) {
                        this.speedUp(speed / 2);
                    }

                } catch (NullPointerException e) {
                    System.out.println(e);
                }
                //Going South Code
            } else if (this.goingSouth) {
                if (trafficLight.isGreen()) {
                    this.speedUp(speed/10);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                    if (this.isStopped()) {
                        this.speedUp(speed);
                    }
                }
                if(trafficLight.isRed() && (this.y < trafficLight.getDistance())) {
                    this.slowDown(speed / 20);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                } if(trafficLight.isRed() && (this.y > trafficLight.getDistance()-2*roadShift)){
                    this.speedUp(speed / 4);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                } else if (trafficLight.isRed() && ((trafficLight.getDistance() * 0.95)-(2*roadShift) < this.y) && (this.y < trafficLight.getDistance())) {
                    this.stop();
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }


                try {
                    if (this.y > car_next.y - 2*car_next.length) {
                        this.slowDown(speed);
                    }
                    if (this.y + this.length > car_next.y) {
                        this.stop();
                        this.y -= car_next.length;
                    }
                    if (trafficLight.isRed() && (car_next.isStopped() || car_next.isSlowing()) && (car_next.y > this.y + 2 * this.length)) {
                        this.speedUp(speed / 2);
                    }
                }
                catch (NullPointerException e) {
                    System.out.println(e);
                }
//Going North Code
            } else if (this.goingNorth) {
                if (trafficLight.isGreen()) {
                    this.speedUp(speed/10);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                    if (this.isStopped()) {
                        this.speedUp(speed);
                    }
                }
                if(trafficLight.isRed() && (this.y > trafficLight.getDistance())){
                    this.slowDown(speed/20);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                } else if (trafficLight.isRed() && ((trafficLight.getDistance() * 1.01)< this.y) && (this.y > trafficLight.getDistance())) {
                    this.stop();
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                if(trafficLight.isRed() && (this.y < trafficLight.getDistance())){
                    this.speedUp(speed / 4);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }

                try {
                    if (this.y < car_next.y + 2*car_next.length) {
                        this.slowDown(speed);
                    }
                    if (this.y - this.length < car_next.y) {
                        this.stop();
                        this.y += car_next.length;
                    }
                    if (trafficLight.isRed() && (car_next.isStopped() || car_next.isSlowing()) && (car_next.y < this.y - 2 * this.length)) {
                        this.speedUp(speed / 4);
                    }
                }
                catch (NullPointerException e) {
                    System.out.println(e);
                }
            }
        }
    }



    public int getDistance(){
        return (x-startingX);
    }

    public boolean isGoingSouth() {
        return goingSouth;
    }

    public void setGoingSouth(boolean goingSouth) {
        this.goingSouth = goingSouth;
        this.length = this.height;
        xDir=0;
        yDir=1;
    }

    public boolean isGoingNorth() {
        return goingNorth;
    }

    public void setGoingNorth(boolean goingNorth) {
        this.goingNorth = goingNorth;
        this.length = this.height;
        xDir=0;
        yDir=-1;

    }

    private boolean isGoingEast() {
        return goingEast;
    }

    public void setGoingEast(boolean goingEast) {
        this.goingEast = goingEast;
        this.length = this.width;
        xDir=1;
        yDir=0;
    }

    public boolean isGoingWest() {
        return goingWest;
    }

    public void setGoingWest(boolean goingWest) {
        this.goingWest = goingWest;
        this.length = this.width;
        xDir=-1;
        yDir=0;
    }

    public int getStartingX() {
        return startingX;
    }

    public int getStartingY() {
        return startingY;
    }

    private boolean isStopped() {
        return isStopped;
    }

    private void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    private boolean isSlowing() {
        return isSlowing;
    }

    private void setSlowing(boolean slowing) {
        isSlowing = slowing;
    }

    public boolean isAtEnd() {
        return atEnd;
    }

    private void setAtEnd(boolean atEnd) {
        this.atEnd = atEnd;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getCarAhead() {
        return carAhead;
    }

    public void setCarAhead(int carAhead) {
        this.carAhead = carAhead;
    }

    private int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int getOnRoad() {
        return onRoad;
    }

    public void setOnRoad(int onRoad) {
        this.onRoad = onRoad;
    }
}
