package UnitTests;

import Model.Car;
import Model.TrafficLight;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrafficLightTest{
    private TrafficLight trafficLight = new TrafficLight(1,0,0,true);
    @Test
    void goToRed() {
        trafficLight.goToRed();
        assertTrue(trafficLight.isRed());
    }

    @Test
    void randomStop() {
        trafficLight.setGreen(true);
        while(trafficLight.isGreen()){
            trafficLight.randomStop();
        }
        assertTrue(trafficLight.isRed());
    }

}
