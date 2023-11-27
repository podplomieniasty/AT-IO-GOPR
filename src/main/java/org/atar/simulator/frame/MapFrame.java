package org.atar.simulator.frame;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class MapFrame extends JFrame {

    private JLabel mapContainer;

    public MapFrame() {

        imgIconInit();
        mapFrameInit();
        addImgToFrame();
        updateImgInFrame();

    }

    private void imgIconInit() {
        this.mapContainer = new JLabel();
        this.mapContainer.setSize(640, 480);
        this.mapContainer.setOpaque(false);
    }
    private void mapFrameInit() {
        this.setTitle("Symulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024, 768);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addImgToFrame() {
        this.add(this.mapContainer);
    }

    private void updateImgInFrame() {
        ImageIcon i = new ImageIcon("sample.png");
        Image ig = i.getImage().getScaledInstance(640, 480, Image.SCALE_DEFAULT);
        i = new ImageIcon(ig);
        mapContainer.setIcon(i);
        mapContainer.setVisible(true);
    }


    public void toggleVisibility() {
        this.setVisible(!this.isVisible());
    }
}
