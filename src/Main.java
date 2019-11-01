import Controller.MapCreator;
import Controller.Simulator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {
    private static final Random random = new Random();
    public static void main(String[] args) {
        final int DISPLAY_WIDTH = 1200;
        final int DISPLAY_HEIGHT = 600;
        JFrame mainFrame = new JFrame("Traffic Controller.Simulator Ver 0.1");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Simulator simulator = new Simulator(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("Help");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem simulation = new JMenuItem("Begin Current Simulation");
        JMenuItem simulation_reset = new JMenuItem("Reset Current Simulation");
        JMenuItem city_map = new JMenuItem("Create a New City Map");
        exit.addActionListener(e -> System.exit(0));
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(actionEvent -> JOptionPane.showMessageDialog(mainFrame,"iTS OVER 9000!"));
        simulation.addActionListener(actionEvent -> {
            simulator.initialise();
            simulator.animate();
            mainFrame.add(simulator, BorderLayout.CENTER);
            mainFrame.pack();
            mainFrame.setVisible(true);
        });
        simulation_reset.addActionListener(actionEvent -> {
            mainFrame.remove(simulator);
            mainFrame.invalidate();
            mainFrame.validate();
            simulator.initialise();
            simulator.animate();
            mainFrame.add(simulator);
        });
        MapCreator mapCreator = new MapCreator(DISPLAY_WIDTH,DISPLAY_HEIGHT);
        city_map.addActionListener(actionEvent -> {
            mainFrame.remove(simulator);
            mainFrame.add(mapCreator);
            simulator.initialise();
            mainFrame.invalidate();
            mainFrame.validate();
        });
        file.add(simulation);
        file.add(simulation_reset);
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
    }



}
