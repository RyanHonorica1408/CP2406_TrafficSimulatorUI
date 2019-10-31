import Model.Car;
import Model.Road;
import Model.TrafficLight;
import View.CarView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {
    private static final Random random = new Random();
    public static void main(String[] args) {
        final int DISPLAY_WIDTH = 1200;
        final int DISPLAY_HEIGHT = 600;
        final int CAR_COUNT = 160;
        final int CAR_LENGTH = 50;
        final int ROAD_COUNT = 4;
        final int TRAFFIC_LIGHT_COUNT =2;
        Car[] cars = new Car[CAR_COUNT];
        Road[] roads = new Road[ROAD_COUNT];
        TrafficLight[] trafficLights = new TrafficLight[TRAFFIC_LIGHT_COUNT];
        //West
        int x = 0;
        int xshift = 0;
        int y;
        int carId =1;
        for(int i =0; i <cars.length/4;i++){
             y=(DISPLAY_HEIGHT/2)-(CAR_LENGTH/2);
            int width = CAR_LENGTH;
            int height = width/2;
            Color color = randomColor();
            if(carId<=1){
            cars[i]= new Car(carId,x,y,width,height,color,2,0);}
            else {
                int next = carId-1;
                if(next <CAR_COUNT/4) {
                    cars[i] = new Car(carId, next, x, y, width, height, color, 2, 0);
                } else {
                    cars[i] = new Car(carId, x, y, width, height, color, 2, 0);
                }
            }
            carId++;
            if(x<=0){
                x-=width*1.5;
            } else if (x>= DISPLAY_WIDTH){
                x-=width*1.5;
            }
            Car car = cars[i];
            car.setGoingWest(true);
            car.setOnRoad(2);
            car.x=DISPLAY_WIDTH+xshift;
            xshift += width*2;
            car.y += height*2;

        }
        // East
        x = 0;
        xshift = 0;
        y=0;
        carId =cars.length/4+1;

        for(int ii = cars.length/4; ii <cars.length/2; ii++){
            y=(DISPLAY_HEIGHT/2)-(CAR_LENGTH/2);
            int width = CAR_LENGTH;
            int height = width/2;
            Color color = randomColor();
            if(carId<=4){
                cars[ii]= new Car(carId,x,y,width,height,color,2,0);}
            else {
                int next = carId-1;
                if((next > CAR_COUNT/4) && (next < CAR_COUNT/2)) {
                    cars[ii] = new Car(carId, next, x, y, width, height, color, 2, 0);
                } else {
                    cars[ii] = new Car(carId, x, y, width, height, color, 2, 0);
                }
            }
            carId++;
            if(x<=0){
                x-=width*1.5;
            } else if (x>= DISPLAY_WIDTH){
                x-=width*1.5;
            }
            Car car = cars[ii];
            car.setGoingEast(true);
            car.setOnRoad(1);
            car.x=0+xshift;
            xshift -= width*2;
            car.y += 0;
        }

        // South
        x = DISPLAY_WIDTH/2+((CAR_LENGTH/2)*5);
        int yshift = 0;
        y=0;
        carId =cars.length/2+1;
        for(int ii = cars.length/2; ii <((cars.length*3)/4); ii++){
//            y=(DISPLAY_HEIGHT/2)+(CAR_LENGTH/2);
            int height = CAR_LENGTH;
            int width = height/2;

            Color color = randomColor();
            if(carId<=4){
                cars[ii]= new Car(carId,x,y,width,height,color,2,0);}
            else {
                int next = carId-1;
                if((next> CAR_COUNT/2)&& (next <(CAR_COUNT*3)/4)) {
                    cars[ii] = new Car(carId, next, x, y, width, height, color, 2, 0);
                } else {
                    cars[ii] = new Car(carId, x, y, width, height, color, 2, 0);
                }
            }
            carId++;
            Car car = cars[ii];
            car.setGoingSouth(true);
            car.setOnRoad(3);
            car.y=0+yshift;
            yshift -= width*2;
        }
        // North
        x = DISPLAY_WIDTH/2+((CAR_LENGTH/2)*3);
        yshift = 0;
        y=0;
        carId =((3*cars.length)/4)+1;
        for(int ji = ((3*cars.length)/4); ji <cars.length; ji++){
            int height = CAR_LENGTH;
            int width = height/2;
            Color color = randomColor();
            if(carId<=4){
                cars[ji]= new Car(carId,x,y,width,height,color,2,0);}
            else {
                int next = carId-1;
                if((next <CAR_COUNT) && (next > (CAR_COUNT*3)/4)) {
                    cars[ji] = new Car(carId, next, x, y, width, height, color, 2, 0);
                } else {
                    cars[ji] = new Car(carId, x, y, width, height, color, 2, 0);
                }
            }
            carId++;
            Car car = cars[ji];
            car.setGoingNorth(true);
            car.setOnRoad(4);
            car.y=DISPLAY_HEIGHT+yshift;
            yshift += width*2;
            car.y += 0;
        }


        int xloc =0;
        for(int j =0; j <roads.length/2; j++){
            int length = 2*DISPLAY_WIDTH/(ROAD_COUNT);
            int roadId = j+1;
            roads[j] = new Road(roadId,0,xloc+(length/2),DISPLAY_HEIGHT/2, length, 2*CAR_LENGTH, Color.BLACK);
            xloc += length;
        }
        int yloc =0;
        xloc = 2*DISPLAY_WIDTH/ROAD_COUNT+(2*CAR_LENGTH);
        int length = 2*DISPLAY_HEIGHT/(ROAD_COUNT);
        for(int jj = roads.length/2; jj < roads.length; jj++){
            int roadId = jj+1;
            roads[jj] = new Road(roadId,0,xloc,yloc+(length/2), 2*CAR_LENGTH, length, Color.BLACK);
            yloc += length;
        }

        int shift=0;

        for(int k = 0; k< trafficLights.length;k++){
            int light_X = roads[k].getLength();
            int light_Y = DISPLAY_HEIGHT/2-(2*CAR_LENGTH);
            trafficLights[k]= new TrafficLight(shift+light_X,light_Y,true);
            trafficLights[k].setOnRoadEnd(true);
            if(k==0) {
                trafficLights[k].setEastWest(true);
                trafficLights[k].setOnRoad(k + 1);
                trafficLights[k].setNextRoad(k + 2);
            } if(k==1){
                TrafficLight trafficLight = trafficLights[k];
                trafficLight.setOnRoad(k + 2);
                trafficLight.setNextRoad(k + 3);
                trafficLight.x = light_X +(roads[trafficLight.getOnRoad()-1].getWidth()*2);
                light_Y = (roads[trafficLight.getOnRoad()-1].getLength())+(roads[trafficLight.getOnRoad()-1].getWidth()*3);
                trafficLight.y =  light_Y;
                trafficLight.setNorthSouth(true);

            }

        }

        JFrame mainFrame = new JFrame("Traffic Simulator Ver 0.1");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Simulator simulator = new Simulator(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        simulator.setRoads(roads);
        simulator.setCars(cars);
        simulator.setTrafficLights(trafficLights);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("Help");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem simulation = new JMenuItem("Begin Current Simulation");
        JMenuItem city_map = new JMenuItem("Create a New City Map");
        exit.addActionListener(e -> { System.exit(0);});
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(actionEvent -> { JOptionPane.showMessageDialog(mainFrame,"iTS OVER 9000!"); });
        simulation.addActionListener(actionEvent -> {
            simulator.animate();
            mainFrame.add(simulator, BorderLayout.CENTER);
            mainFrame.pack();
            mainFrame.setVisible(true);
        });
        MapCreator mapCreator = new MapCreator(DISPLAY_WIDTH,DISPLAY_HEIGHT);
        city_map.addActionListener(actionEvent -> {
            mainFrame.remove(simulator);
            mainFrame.add(mapCreator);
            mainFrame.invalidate();
            mainFrame.validate();
        });
        file.add(simulation);
        file.add(exit);
        edit.add(city_map);
        help.add(about);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setPreferredSize(new Dimension(DISPLAY_WIDTH,DISPLAY_HEIGHT));
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);



//        JFrame mainFrame = new JFrame("Traffic Simulator Ver 0.1");
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JMenuBar menuBar = new JMenuBar();
//        JMenu file = new JMenu("File");
//        JMenu help = new JMenu("Help");
//        JMenuItem exit = new JMenuItem("Exit");
//        exit.addActionListener(e -> { System.exit(0);});
//        JMenuItem about = new JMenuItem("About");
//        about.addActionListener(actionEvent -> { JOptionPane.showMessageDialog(mainFrame,"iTS OVER 9000!"); });
//        file.add(exit);
//        help.add(about);
//        menuBar.add(file);
//        menuBar.add(help);
//        mainFrame.pack();
//        mainFrame.setVisible(true);
//        mainFrame.setLocationRelativeTo(null);
//        mainFrame.setLayout(new BorderLayout());
//        mainFrame.setJMenuBar(menuBar);
//        Simulator simulator = new Simulator(500,500);
//        simulator.paint(null);
//        mainFrame.add(simulator, BorderLayout.CENTER);


//        JButton simulateButton = new JButton("Start Simulation");
//        JPanel simulatorPanel = new JPanel();
//        JPanel simulatorControlPanel = new JPanel();
//        JPanel simulatorStatusPanel = new JPanel();
//        simulatorControlPanel.add(simulateButton);
//
//
//        mainFrame.add(simulatorPanel);
//        mainFrame.add(simulatorControlPanel);
//        mainFrame.add(simulatorStatusPanel);
    }

    private static int randomSize(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    private static Color randomColor() {
        int red = randomSize(0, 255);
        int green = randomSize(0, 255);
        int blue = randomSize(0, 255);
        int alpha = randomSize(100, 255);

        return new Color(red, green, blue, alpha);
    }
}
