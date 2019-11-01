package UnitTests;

import Model.Car;
import Model.TrafficLight;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

class CarTest{
    private Car car = new Car(1,0,0,100,50, Color.RED,2,0);
    private Car car_next = new Car(2,0,0,100,50,Color.BLUE,2,0);
    private TrafficLight trafficLight = new TrafficLight(1,50,0,true);

    @org.junit.jupiter.api.Test
    void testLookAhead() {
        car.setGoingEast(true);
        car.setOnRoad(1);
        trafficLight.setOnRoad(1);
        trafficLight.setGreen(true);
        trafficLight.setEastWest(true);
        //Speed Up check
        car.lookAhead(trafficLight,null,car.xSpeed,100,100,false);
        assertEquals(car.xSpeed/=10,car.xSpeed);
        //Stop Check
        trafficLight.setGreen(false);
        car.x = 49;
        car.lookAhead(trafficLight,null,car.xSpeed,100,100,false);
        assertEquals(0, car.xSpeed);
        //Slow Down Check
        car.x = 30;
        car.xSpeed=3;
        car.lookAhead(trafficLight,null,car.xSpeed,100,100,false);
        assertEquals(car.xSpeed/=20, car.xSpeed);
        //Speed Up Past TrafficLight Check
        car.x =52;
        car.lookAhead(trafficLight,null,car.xSpeed,100,100,false);
        assertEquals(car.xSpeed/=4,car.xSpeed);
        //Check if going past next Car
        car.x =0;
        car.lookAhead(trafficLight,car_next,car.xSpeed,100,100,false);
        assertEquals(-100, car.x);
        //Check if next car is Stopped and stop
        car_next.x = 50;
        car.x=24;
        car.lookAhead(trafficLight,car_next,car.xSpeed,100,100,false);
        assertEquals(0,car.xSpeed);
    }



}
