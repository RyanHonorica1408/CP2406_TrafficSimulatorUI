import Model.Car;
import Model.Road;
import Model.TrafficLight;

import javax.swing.*;
import java.awt.*;

public class Simulator extends JPanel{
    private Timer timer;
    private int width,height;
    private Car[] cars;
    private Road[] roads;
    private TrafficLight[] trafficLights;
    private int counter =0;
    Simulator(int width, int height){
        setBackground(Color.GRAY);
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width,height));
    }

    public void animate(){
        if (timer != null) {
            timer.stop();
        }
        for(TrafficLight trafficLight: trafficLights){
            trafficLight.setGreen(true);
        }
        timer = new Timer(1000/60, e -> {
            if (cars == null) return;
            float speed = 1;
            for (Car car : cars) {
                if(speed ==0){
                    speed =1;
                }
                if(car.isGoingWest()){
                    for(TrafficLight trafficLight :trafficLights){
                        if(trafficLight.isRed()){
                           car.x =car.x;
                        }
                        if(trafficLight.isGreen()){
                            car.move();
                            car.speedUp(speed/10);
                            car.update(getWidth(), getHeight());
                            if(car.isStopped()){
                                car.speedUp(speed);
                            }
                        }
                        if(trafficLight.isRed() && (car.x > trafficLight.getDistance())){
                            car.slowDown(speed/20);
                            car.move();
                            car.update(getWidth(), getHeight());
                        }
                        if(trafficLight.isRed() && (car.x < trafficLight.getDistance())){
                            car.speedUp(speed/4);
                            car.move();
                            car.update(getWidth(), getHeight());
                        }
                        if((trafficLight.isGreen())&&(counter >=30)){
                            trafficLight.randomStop();
                            counter =0;
                        }
                        if(trafficLight.isRed() && (counter >=60)){
                            trafficLight.setGreen(true);
                            counter =0;
                        }
                        if(trafficLight.isRed() && ( car.x < trafficLight.getDistance()*1.05 ) && (trafficLight.getDistance() < car.x)){
                            car.stop();
                            car.move();
                            car.update(getWidth(),getHeight());
                        }
                        if(car.getCarId() > 1){
                            Car car_next =cars[car.getCarAhead()-1];
                            if((car_next.x+ 2*car_next.getWidth()) >car.x){
                                car.slowDown(speed);
                            }
                            if(car_next.x+car_next.getWidth()>car.x){
                                car.stop();
                                car.x+=car_next.getWidth();
                            }
                            if(car_next.isSlowing()){
                                car.slowDown(speed/20);
                            }
                            if((car_next.x > car.x-car.getWidth()/4) && trafficLight.isRed()){
                                car.stop();
                            }
                        }

                    }
                } else if (car.isGoingEast()){

                }
            }
            speed++;
            counter ++;
            repaint();
        });
        timer.start();
    }

    public void setCars(Car[] Cars) {
        this.cars = Cars;
    }

    public void setRoads(Road[] roads) {
        this.roads = roads;
    }

    public void setTrafficLights(TrafficLight[] trafficLights) {
        this.trafficLights = trafficLights;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Road road: roads){
            g.setColor(Color.BLACK);
            road.draw(g);
        }
        for (Car car : cars) {
            g.setColor(Color.WHITE);
            car.draw(g);
        }
        for(TrafficLight trafficLight : trafficLights){
            g.setColor(Color.LIGHT_GRAY);
            trafficLight.draw(g);
        }
    }

}
