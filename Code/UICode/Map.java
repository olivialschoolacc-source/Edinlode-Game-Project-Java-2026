package Code.UICode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class Map {

    private JFrame frame;
    private JPanel mapPanel;
    private boolean isMapOpen = false;

    public Map(JFrame frame) {
        
        this.frame = frame;

        mapPanel = new JPanel();
        mapPanel.setBackground(new Color(0,0,0,200));
        mapPanel.setLayout(new GridBagLayout());

        int mapWidth = 600, mapHeight = 500;

        java.net.URL imgUrl = getClass().getResource("/assets/images/map/Edinlode.png");

        if (imgUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imgUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(mapWidth, mapHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel mapLabel = new JLabel(scaledIcon);
            mapPanel.add(mapLabel);
        } else {
            JLabel errorLabel = new JLabel("Map image not found!");
            errorLabel.setForeground(Color.WHITE);
            mapPanel.add(errorLabel);
            System.err.println("Error: Could not load /assets/images/map/Edinlode.png");
        }

        int mapX = 200, mapY = 100;

        mapPanel.setBounds(mapX, mapY, mapWidth, mapHeight);
        mapPanel.setVisible(false);
        
        frame.getLayeredPane().add(mapPanel, JLayeredPane.POPUP_LAYER);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_M) {
            isMapOpen = !isMapOpen;
            mapPanel.setVisible(isMapOpen);

            if (frame != null) {
                frame.revalidate();
                frame.repaint();
            }
        }
    }
}
