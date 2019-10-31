import Model.Road;
import javafx.scene.control.skin.TextInputControlSkin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MapCreator extends JPanel {
    int roadCount=0;
    private Road[] roads = new Road[roadCount];
    private JButton roadButton = new JButton("Click to add new Road to Design");
    private JButton maxLengthButton = new JButton("Set to Maximum Length Road");
    private JButton minLengthButton = new JButton("Set to Minimum Length Road");
    private JButton clearButton = new JButton("Click to Clear Map");
    private JButton rotateButton = new JButton("Click to Rotate Road");
    private JPanel designPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    boolean inProcess = false;
    int length =300;
    int road_width = length;
    int road_height =100;
    int roadX=0;
    int roadY=0;


    public MapCreator(int width, int height){
        roadButton.addActionListener(actionEvent -> {
            addStraightRoad();
        });
        clearButton.addActionListener(actionEvent -> {
            if(inProcess){
                clearButton.setEnabled(false);
            } else {
                clearButton.setEnabled(true);
                Graphics roadGraphics = designPanel.getGraphics();
                designPanel.paint(roadGraphics);
            }
        });
        maxLengthButton.addActionListener(actionEvent -> {
            if(road_width == length) {
                this.length = 750;
                road_width = length;
            } else if (road_height == length){
                this.length = 750;
                road_height = length;
            }
        });
        minLengthButton.addActionListener(actionEvent -> {
            if(road_width == length) {
                length = 300;
                road_width = length;
            } else if (road_height == length){
                length = 300;
                road_height = length;
            }
        });
        rotateButton.addActionListener(actionEvent -> {
            if(road_width==length){
                road_width = 100;
                road_height = length;
            } else if (road_height == length){
                road_height=100;
                road_width=length;

            }
        });
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
        buttonPanel.add(roadButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(rotateButton);
        buttonPanel.add(maxLengthButton);
        buttonPanel.add(minLengthButton);
        designPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setBackground(Color.ORANGE);
        designPanel.setPreferredSize(new Dimension((width*3)/4,height));
        buttonPanel.setPreferredSize(new Dimension(width/4,height));
        gridBagConstraints.gridx =0;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridwidth =2;
        gridBagConstraints.gridheight=3;
        gridBagConstraints.weightx =1;
        gridBagConstraints.weighty=1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.addLayoutComponent(designPanel,gridBagConstraints);
        gridBagConstraints.gridx =3;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridwidth =1;
        gridBagConstraints.gridheight=3;
        gridBagConstraints.weightx =0.05;
        gridBagConstraints.weighty=0.2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.addLayoutComponent(buttonPanel,gridBagConstraints);
        setLayout(gridBagLayout);
        setBackground(Color.BLACK);
        initialiseKeys();
        setPreferredSize(new Dimension(width,height));
        this.add(designPanel);
        this.add(buttonPanel);
    }


    private void initialiseKeys(){
        Action upPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadY-=100;
            }
        };
        Action downPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadY+=100;
            }
        };
        Action leftPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadX-=100;
            }
        };
        Action rightPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadX+=100;
            }
        };
        Action spacePressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inProcess = false;
                timer.stop();
                roadButton.setEnabled(true);
                clearButton.setEnabled(true);
                maxLengthButton.setEnabled(true);
                minLengthButton.setEnabled(true);
                rotateButton.setEnabled(true);
                roads[roadCount] = new Road(roadCount+1,roadCount+2,roadX,roadY,road_width,road_height,Color.BLACK);
                roadCount++;
            }
        };
        bindKeyStrokes("up.pressed",KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),upPressed);
        bindKeyStrokes("down.pressed",KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0),downPressed);
        bindKeyStrokes("left.pressed",KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0),leftPressed);
        bindKeyStrokes("right.pressed",KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0),rightPressed);
        bindKeyStrokes("space.pressed",KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0),spacePressed);

    }
    Timer timer = new Timer(1000 / 60, e -> {
        updateMap();
    });

    private void addStraightRoad(){
        clearButton.setEnabled(false);
        maxLengthButton.setEnabled(false);
        minLengthButton.setEnabled(false);
        rotateButton.setEnabled(false);
        inProcess=true;
        if(inProcess){
            roadButton.setEnabled(false);
            timer.start();
        }
    }
    private void updateMap(){
        try{
            for (Road road : roads) {
                Graphics roadGraphics = designPanel.getGraphics();
                roadGraphics.setColor(Color.BLACK);
                roadGraphics.fillRect(road.x, road.y, road.getWidth(), road.getHeight());
                designPanel.paintComponents(roadGraphics);
            }
        } catch(NullPointerException e){
            Graphics roadGraphics = designPanel.getGraphics();
            designPanel.paint(roadGraphics);
            roadGraphics.setColor(Color.BLACK);
            roadGraphics.fillRect(roadX, roadY, road_width, road_height);
            designPanel.paintComponents(roadGraphics);
        }
    }
    private void bindKeyStrokes(String name, KeyStroke keyStroke, Action action){
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);
        ActionMap actionMap = getActionMap();
        inputMap.put(keyStroke,name);
        actionMap.put(name,action);
    }
}
