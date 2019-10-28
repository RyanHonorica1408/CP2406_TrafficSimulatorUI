import Model.Car;
import Model.Road;
import Model.TrafficLight;

import javax.swing.*;
import java.awt.*;

public class Simulator extends JPanel{
    private Timer timer;
    private Car[] cars;
    private Road[] roads;
    private TrafficLight[] trafficLights;
    private int counter =0;
    Simulator(int width, int height){
        setBackground(Color.GRAY);
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
                car.move();
                car.speedUp(speed);
                car.update(getWidth(), getHeight());
                speed += 1;
                if(car.getCarId()>1) {
                    Car car_next = cars[car.getCarAhead()-1];

                    if (car.x +car.getWidth() >= car_next.x-car_next.getWidth()/2) {
                        car.stop();
                    }
                }

                for(TrafficLight trafficLight : trafficLights){
                    if((trafficLight.isGreen())&&(counter>30)){
                        trafficLight.randomStop();
                        counter =0;
                    } else if ((counter >60)&&(trafficLight.isRed())){
                        trafficLight.setGreen(true);
                        counter =0;
                    }
                    if((trafficLight.isRed()) &&(car.getDistance()>trafficLight.getDistance()*0.7)){
                        car.slowDown(speed/2);
                        car.setGoingEast(true);
                    }
                    if(trafficLight.isRed()){
                        if(car.getDistance()>trafficLight.getDistance()*0.9){
                            if(car.getDistance()<trafficLight.getDistance()){
                                car.stop();
                            }
                        }

                    }

                }
            }
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
