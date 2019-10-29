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
        final int DISPLAY_HEIGHT = 960;
        final int CAR_COUNT = 4;
        final int CAR_LENGTH = 50;
        final int ROAD_COUNT = 2;
        final int TRAFFIC_LIGHT_COUNT =1;
        Car[] cars = new Car[CAR_COUNT];
        Road[] roads = new Road[ROAD_COUNT];
        TrafficLight[] trafficLights = new TrafficLight[TRAFFIC_LIGHT_COUNT];
        int x = 0;
        int y;
        int carId =1;
        for(int i =0; i <cars.length;i++){
             y=(DISPLAY_HEIGHT/2)-(CAR_LENGTH/2);
            int width = CAR_LENGTH;
            int height = width/2;
            Color color = randomColor();
            if(carId<=1){
            cars[i]= new Car(carId,x,y,width,height,color,2,0);}
            else {
                int next = carId-1;
                if(next <CAR_COUNT) {
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
            car.x=DISPLAY_WIDTH-width*2;
            car.y += height*2;
        }
        int xloc =0;
        for(int j =0; j <roads.length; j++){
            int length = DISPLAY_WIDTH/ROAD_COUNT;

            roads[j] = new Road(xloc+(length/2),DISPLAY_HEIGHT/2, length, 2*CAR_LENGTH, Color.BLACK);
            xloc += length;
        }
        int shift=0;
        for(int k = 0; k< trafficLights.length;k++){
            int light_X = roads[k].getLength();
            trafficLights[k]= new TrafficLight(shift+light_X,DISPLAY_HEIGHT/2-(2*CAR_LENGTH),true);
            trafficLights[k].setOnRoad(k+1);
            shift +=light_X;
        }

        JFrame mainFrame = new JFrame("Traffic Simulator Ver 0.1");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Simulator simulator = new Simulator(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        simulator.setRoads(roads);
        simulator.setCars(cars);
        simulator.setTrafficLights(trafficLights);


        mainFrame.add(simulator, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        simulator.animate();


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