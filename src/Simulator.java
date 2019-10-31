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
    private int counter1 =0;
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
                for(TrafficLight trafficLight :trafficLights) {
                    if(trafficLight.isEastWest()) {
                        if ((trafficLight.isGreen()) && (counter >= 60)) {
                            trafficLight.randomStop();
                            counter = 0;
                        }
                        if (trafficLight.isRed() && (counter >= 60)) {
                            trafficLight.setGreen(true);
                            counter = 0;
                        }
                        int id = car.getCarId();
                        int cars_length = cars.length;
                        if ((id != 1) && (id != cars_length / 4 + 1) && (id != cars_length / 2 + 1) && (id != (3 * cars_length) / 4 + 1)) {
                            Car car_next = cars[car.getCarAhead() - 1];
                            car.lookAhead(trafficLight, car_next, speed, getWidth(), getHeight(), true);
                        } else {
                            car.lookAhead(trafficLight, null, speed, getWidth(), getHeight(), true);
                        }
                    } else if (trafficLight.isNorthSouth()){
                        if ((trafficLight.isGreen()) && (counter1 >= 30)) {
                            trafficLight.randomStop();
                            counter1 = 0;
                        }
                        if (trafficLight.isRed() && (counter1 >= 60)) {
                            trafficLight.setGreen(true);
                            counter1 = 0;
                        }
                        int id = car.getCarId();
                        int cars_length = cars.length;
                        if ((id != 1) && (id != cars_length / 4 + 1) && (id != cars_length / 2 + 1) && (id != (3 * cars_length) / 4 + 1)) {
                            Car car_next = cars[car.getCarAhead() - 1];
                            car.lookAhead(trafficLight, car_next, speed, getWidth(), getHeight(), true);
                        } else {
                            car.lookAhead(trafficLight, null, speed, getWidth(), getHeight(), true);
                        }

                    }
                }
            }
            speed++;
            counter ++;
            counter1++;
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
