package Code.UICode;

import java.awt.*;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.KeyEvent;

public class Map {

    private JFrame frame;
    private JPanel mapPanel;
    private boolean isMapOpen = false;

    // Map continent labels
    private JLabel[] continentLabels = new JLabel[9];
    private final String[] continentNames = {
        "ASTRAKIN", "ATLANTIPES", "BOSTROG", "CELENODE",
        "FELENA", "JESTEMEX", "KIANDLE", "PLOVIQUE", "SHIELDA ISLANDS"
    };

    public Map(JFrame frame) {
        
        this.frame = frame;

        mapPanel = new JPanel();
        mapPanel.setBackground(new Color(0,0,0,200));
        mapPanel.setLayout(null);

        int mapWidth = 600, mapHeight = 500;

        java.net.URL imgUrl = getClass().getResource("/assets/images/map/Edinlode.png");

        if (imgUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imgUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(mapWidth, mapHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel mapLabel = new JLabel(scaledIcon);
            mapLabel.setBounds(0, 0, mapWidth, mapHeight);
            mapPanel.add(mapLabel);
        } else {
            JLabel errorLabel = new JLabel("Map image not found!");
            errorLabel.setForeground(Color.WHITE);
            mapPanel.add(errorLabel);
            System.err.println("Error: Could not load /assets/images/map/Edinlode.png");
        }

        setMapLabels();

        int mapX = 200, mapY = 100;
        mapPanel.setBounds(mapX, mapY, mapWidth, mapHeight);
        mapPanel.setVisible(false);
        
        frame.getLayeredPane().add(mapPanel, JLayeredPane.POPUP_LAYER);
    }

    public void setMapLabels() {
        for (int i = 0; i < continentNames.length; i++) {
            String name = continentNames[i];

            String displayText = name.equals("SHIELDA ISLANDS")
                ? "<html><center>SHIELDA<br>ISLANDS</center></html>"
                : name;

            JLabel label = new JLabel(displayText, SwingConstants.CENTER);

            label.setFont(new Font("Times New Roman", Font.BOLD, 13));
            label.setForeground(Color.WHITE);
            label.setOpaque(true);
            label.setBackground(new Color(0, 0, 0, 120));
            label.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));

            int x = 0, y = 0, width = 100, height = 24;

            switch (name) {
                case "ASTRAKIN":
                    x = 90; y = 350; width = 110; break;
                case "ATLANTIPES":
                    x = 335; y = 450; width = 120; break;
                case "BOSTROG":
                    x = 485; y = 255; width = 90; break;
                case "CELENODE":
                    x = 110; y = 110; width = 100; break;
                case "FELENA":
                    x = 110; y = 190; width = 90; break;
                case "JESTEMEX":
                    x = 350; y = 200; width = 110; break;
                case "KIANDLE":
                    x = 325; y = 300; width = 90; break;
                case "PLOVIQUE":
                    x = 350; y = 130; width = 100; break;
                case "SHIELDA ISLANDS":
                    x = 485; y = 375; width = 110; height = 30; break;
                default:
                    x = 50; y = 50; break;
            }

            label.setBounds(x, y, width, height);

            continentLabels[i] = label;
            mapPanel.add(label);
            mapPanel.setComponentZOrder(label, 0);

            label.addMouseListener(new MouseAdapter() {
            @Override 
            public void mousePressed(MouseEvent e) {
                labelClicked(name);
                frame.repaint();
            }
        });
        }
    }

    public void labelClicked(String name) {
        System.out.println(name + " has been clicked"); // 임시 debug
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
