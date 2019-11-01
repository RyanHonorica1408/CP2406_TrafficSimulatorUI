package UnitTests;

import Model.Car;
import Model.Road;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

class RoadTest{
    private Road road = new Road(1,2,0,0 ,100,50, Color.BLACK);
    @Test
    void setLightEnd() {
        road.setLightEnd(true);
        assertTrue(road.isLightEnd());
    }

    @Test
    void getNextRoadId() {

        assertEquals(2,road.getNextRoadId());
    }
}
