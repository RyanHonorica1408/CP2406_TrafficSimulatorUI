import javafx.scene.control.skin.TextInputControlSkin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MapCreator extends JPanel {
    private JPanel designPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    boolean inProcess = false;
    int roadX=0;
    int roadY=0;

    public MapCreator(int width, int height){
        JButton roadButton = new JButton("Click to add new Road to Design");
        roadButton.addActionListener(actionEvent -> {
            addStraightRoad();
        });
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        buttonPanel.add(roadButton);
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
        gridBagConstraints.weighty=0.5;
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
                roadY-=60;
            }
        };
        Action downPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadY+=60;
            }
        };
        Action leftPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadX-=60;
            }
        };
        Action rightPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadX+=60;
            }
        };
        Action spacePressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inProcess = false;
            }
        };
        bindKeyStrokes("up.pressed",KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),upPressed);
        bindKeyStrokes("down.pressed",KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0),downPressed);
        bindKeyStrokes("left.pressed",KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0),leftPressed);
        bindKeyStrokes("right.pressed",KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0),rightPressed);
        bindKeyStrokes("space.pressed",KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0),spacePressed);

    }
    private void addStraightRoad(){
        inProcess =true;
        Timer timer = new Timer(1000/60, e->{
            updateMap();
        });
        timer.start();

    }
    private void updateMap(){
            Graphics roadGraphics = designPanel.getGraphics();
            roadGraphics.setColor(Color.BLACK);
            roadGraphics.fillRect(roadX,roadY,60,60);
            designPanel.paintComponents(roadGraphics);
    }
    private void bindKeyStrokes(String name, KeyStroke keyStroke, Action action){
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);
        ActionMap actionMap = getActionMap();
        inputMap.put(keyStroke,name);
        actionMap.put(name,action);
    }
}
