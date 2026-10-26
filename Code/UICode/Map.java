package Code.UICode;

import java.awt.*;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import Code.CharacterAttributes;
import Code.FontLoader;

public class Map {

    private JFrame frame;
    private JPanel mapPanel;
    private boolean isMapOpen = false;

    private CharacterAttributes charAttStats;
    private Font customFont = FontLoader.loadCustomFont("/assets/font/GrapeSoda.ttf", 20f);

    // Map continent labels
    private JLabel[] continentLabels = new JLabel[9];
    private final String[] continentNames = {
        "ASTRAKIN", "ATLANTIPES", "BOSTROG", "CELENODE",
        "FELENA", "JESTEMEX", "KIANDLE", "PLOVIQUE", "SHIELDA ISLANDS"
    };

    private String mapScreen = "EDINLODE";
    private String previousMapScreen = "EDINLODE";

    public Map(JFrame frame, CharacterAttributes charAttStats) {
        
        this.frame = frame;
        this.charAttStats = charAttStats;

        mapPanel = new JPanel();
        mapPanel.setBackground(new Color(0,0,0,200));
        mapPanel.setLayout(null);

        mapPanel.addMouseListener(new MouseAdapter() {
            @Override 
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && !mapScreen.equals("EDINLODE")) {
                    mapScreen = previousMapScreen;
                    previousMapScreen = "EDINLODE";
                    mapPanel.removeAll();
                    loadCurrentMapScreen();
                    mapPanel.revalidate();
                    mapPanel.repaint();
                }
            }
        });

        int mapWidth = 600, mapHeight = 500;

        loadCurrentMapScreen();

        setMapLabels();

        int mapX = 200, mapY = 100;
        mapPanel.setBounds(mapX, mapY, mapWidth, mapHeight);
        mapPanel.setVisible(false);
        
        frame.getLayeredPane().add(mapPanel, JLayeredPane.POPUP_LAYER);
    }

    public void setMapLabels() {

        if ("EDINLODE".equals(mapScreen)) {
            for (int i = 0; i < continentNames.length; i++) {
                String name = continentNames[i];

                String displayText = name.equals("SHIELDA ISLANDS")
                    ? "<html><center>SHIELDA<br>ISLANDS</center></html>"
                    : name;

                JLabel label = new JLabel(displayText, SwingConstants.CENTER);

                label.setFont(customFont);
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
                        x = 485; y = 375; width = 110; height = 50; break;
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
        } else {
            return;
        }
        
    }

    public void loadCurrentMapScreen() {
        int mapWidth = 600, mapHeight = 500;
        String imagePath;

        if ("EDINLODE".equals(mapScreen)) {
            imagePath = "/assets/images/map/Edinlode.png";
        } else {
            imagePath = "/assets/images/map/" + mapScreen.toLowerCase() + "/" + mapScreen.toLowerCase() + ".png";
        }
        
        java.net.URL imgURL = getClass().getResource(imagePath);

        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL);
            Image scaledImage = originalIcon.getImage().getScaledInstance(mapWidth, mapHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel mapLabel = new JLabel(scaledIcon);
            mapLabel.setBounds(0, 0, mapWidth, mapHeight);
            mapPanel.add(mapLabel);
        } else {
            JLabel errorLabel = new JLabel("Map image not found: " + imagePath);
            errorLabel.setForeground(Color.WHITE);
            errorLabel.setBounds(20, 20, 300, 30);
            mapPanel.add(errorLabel);
            System.err.println("Error: Could not load " + imagePath);
        }

        if ("EDINLODE".equals(mapScreen)) {
            setMapLabels();
        } else {
            JLabel hintLabel = new JLabel("Right-click anywhere to return to previous map", SwingConstants.CENTER);
            Font smallFont = customFont.deriveFont(16f);
            hintLabel.setFont(customFont);
            hintLabel.setForeground(Color.WHITE);
            hintLabel.setBounds(100, 20, 400, 20);
            hintLabel.setOpaque(true);
            hintLabel.setBackground(new Color(0, 0, 0, 120));
            mapPanel.add(hintLabel);
            mapPanel.setComponentZOrder(hintLabel, 0);
        }
    }

    public void labelClicked(String name) {
        System.out.println(name + " has been clicked"); // 임시 debug

        previousMapScreen = mapScreen;
        mapScreen = name;
        mapPanel.removeAll();
        loadCurrentMapScreen();

        mapPanel.revalidate();
        mapPanel.repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_M) {
            isMapOpen = !isMapOpen;

            if (isMapOpen) {
                if (charAttStats != null && charAttStats.currentLocation != null && !charAttStats.currentLocation.isEmpty()) {
                    mapScreen = charAttStats.currentLocation.toUpperCase();
                    previousMapScreen = "EDINLODE";
                } else {
                    mapScreen = "EDINLODE";
                    previousMapScreen = mapScreen;
                }
                previousMapScreen = "EDINLODE";
                mapPanel.removeAll();
                loadCurrentMapScreen();
            }

            mapPanel.setVisible(isMapOpen);

            if (frame != null) {
                frame.revalidate();
                frame.repaint();
            }
        }
    }
}