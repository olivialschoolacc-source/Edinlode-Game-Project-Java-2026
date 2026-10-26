package Code.UICode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import Code.UIDisplay;
import Code.CharacterDisplay;
import Code.FontLoader;

public class InventoryUI {

    public JPanel inventoryPanel;
    public JPanel characterPanel;
    public JPanel bagPanel;
    private JFrame frame;
    private JPanel characterPreviewPanel;
    private JScrollPane scrollPane;

    private Font customFont = FontLoader.loadCustomFont("/assets/font/GrapeSoda.ttf", 12f);

    // EQUIPMENT SLOTS
    public Map<String, JPanel> itemPanels = new Hashtable<>(); // collection of all item panels

    private final String[] slotNames = {
        "HAT", "CHEST ARMOR", "PANTS", "BOOTS",
        "FULL BODY ARMOR", "CAPE", "AMULET 1", "AMULET 2",
        "RING 1", "RING 2", "MAINHAND WEAPON", "SECONDARY WEAPON",
        "STORAGE", "EXTRA 1", "EXTRA 2", "PET"
    }; // set the titles

    public InventoryUI(JFrame frame) {
        
        this.frame = frame;

        inventoryPanel = new JPanel(); // set the base inventory panel
        inventoryPanel.setBackground(Color.DARK_GRAY);
        inventoryPanel.setBounds(600, 0, 400, frame.getHeight());
        inventoryPanel.setLayout(null);

        int characterPanelMargin = 10;
        characterPanel = new JPanel();
        characterPanel.setBackground(Color.LIGHT_GRAY);
        characterPanel.setBounds(
            characterPanelMargin,
            characterPanelMargin,
            inventoryPanel.getWidth() - (characterPanelMargin * 2) - characterPanelMargin - 5,
            375
        ); // set character panel where it shows equipped items and character related stats

        // call in functions
        spawnItemSlots();
        spawnCharacterPreview();

        // scrollable bag
        bagPanel = new JPanel();
        bagPanel.setBackground(new Color(60, 60, 60));
        bagPanel.setLayout(null);

        int slotSize = 50;
        int spacing = 5;
        int maxColumns = 6;
        int totalSlots = 60;

        int bagWidth = (maxColumns * slotSize) + ((maxColumns + 1) * spacing);
        int rows = (int) Math.ceil((double) totalSlots / maxColumns);
        int bagHeight = (rows * slotSize) + ((rows + 1) * spacing);
        bagPanel.setPreferredSize(new Dimension(bagWidth - 20, bagHeight));

        for (int i = 0; i < totalSlots; i++) {
            int col = i % maxColumns;
            int row = i / maxColumns;

            int x = spacing + col * (slotSize + spacing);
            int y = spacing + row * (slotSize + spacing);

            JPanel bagSlot = new JPanel();
            bagSlot.setBounds(x, y, slotSize, slotSize);
            bagSlot.setBackground(new Color(90, 90, 90));
            bagSlot.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            bagPanel.add(bagSlot);
        }

        int scrollX = characterPanelMargin;
        int scrollY = characterPanel.getY() + characterPanel.getHeight() + 10;
        int scrollWidth = characterPanel.getWidth();
        int scrollHeight = inventoryPanel.getHeight() - scrollY - 20;

        scrollPane = new JScrollPane(bagPanel);
        scrollPane.setBounds(scrollX, scrollY, scrollWidth, scrollHeight);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // debug (?) for smooth scrolling

        inventoryPanel.add(characterPanel);
        inventoryPanel.add(scrollPane);

        inventoryPanel.setVisible(false);
        frame.getLayeredPane().add(inventoryPanel, JLayeredPane.MODAL_LAYER);
        characterPanel.setLayout(null);
    }

    private void spawnItemSlots() {
        int slotSize = 55; // slot sizes width and height (square)
        int spacing = 5; // spacing between each slot
        int startX = 10; // placement starting X
        int startY = 10; // placement starting Y

        int maxColumns = 3; // max no of columns

        for (int i = 0; i < slotNames.length; i++) { // for loop for easy quick placement
            String slotName = slotNames[i];

            // calculate current placement
            int col = i % maxColumns;
            int row = i / maxColumns;

            int x = startX + col * (slotSize + spacing); // slotsize + spacing for correct placing
            int y = startY + row * (slotSize + spacing); // slotsize + spacing for correct placing

            // set the slotPanel where all slots go
            JPanel slotPanel = new JPanel();
            slotPanel.setBounds(x, y, slotSize, slotSize);
            slotPanel.setBackground(new Color(100, 100, 100));
            slotPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            slotPanel.setLayout(null);

            // set the labels for each slot
            JLabel label = new JLabel("<html><center>" + slotName.replace(" ", "<br>") + "</center></html>", SwingConstants.CENTER);
            label.setFont(customFont);
            label.setForeground(Color.WHITE);
            label.setBounds(2, 2, slotSize - 4, slotSize - 4);
            slotPanel.add(label);

            // add panels to screen
            characterPanel.add(slotPanel);
            itemPanels.put(slotName, slotPanel);
        }
    }

    private void spawnCharacterPreview() { // make the enlarged character preview
        int previewWidth = 60; // character width
        int previewHeight = 120; // character height

        int previewX = 245; // where to spawn X
        int previewY = 150; // where to spawn Y

        // create the character (later replaced with actual character design)
        characterPreviewPanel = new JPanel();
        characterPreviewPanel.setBounds(previewX, previewY, previewWidth, previewHeight);
        characterPreviewPanel.setBackground(Color.BLUE);
        
        // label for the character (just a placeholder, later goes username)
        JLabel previewLabel = new JLabel("Character", SwingConstants.CENTER);
        previewLabel.setForeground(Color.WHITE);
        previewLabel.setFont(customFont);
        characterPreviewPanel.add(previewLabel);

        characterPanel.add(characterPreviewPanel); // add to screen
    }

    public void keyPressed(KeyEvent e) { // check if I key pressed; if so, open the inventory panel
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_I) {
            boolean visible = inventoryPanel.isVisible();
            inventoryPanel.setVisible(!visible);

            if (!visible) {
                frame.getLayeredPane().moveToFront(inventoryPanel);
            }

            if (frame != null) {
                frame.revalidate();
                frame.repaint();
            }
        }
    }
}