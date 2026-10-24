package Code.UICode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import Code.UIDisplay;
import Code.CharacterDisplay;

public class InventoryUI {

    public JPanel inventoryPanel;
    public JPanel characterPanel;
    private JFrame frame;
    private JPanel characterPreviewPanel;

    // ITEM PANELS
    public JPanel hatPanel; // 1, for a hat
    public JPanel chestArmorPanel; // 2, for a chest armor
    public JPanel pantsPanel; // 3, for a pair of pants
    public JPanel bootsPanel; // 4, for a pair of boots
    public JPanel fullBodyArmorPanel; // 5, for a armor that includes both chest and leg armor
    public JPanel capePanel; // 6, for an extra coverage layer
    public JPanel amulet1Panel; // 7, for a necklace
    public JPanel amulet2Panel; // 8. for another necklace
    public JPanel ring1Panel; // 9, for a ring
    public JPanel ring2Panel; // 10, for another ring
    public JPanel mainhandWeaponPanel; // 11, for a mainhand weapon (main weapon)
    public JPanel secondaryWeaponPanel; // 12, for a secondary weapon (like orbs)
    public JPanel storagePanel; // 13, for a restockable weapon (like arrows or throwing stars)
    public JPanel extra1Panel; // 14, for an extra (tba)
    public JPanel extra2Panel; // 15, for an extra (tba)
    public JPanel pet; // 16, for a pet (for item pickup)

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
        inventoryPanel.setBounds(frame.getWidth()/4 * 3, 0, frame.getWidth()/4, frame.getHeight());
        inventoryPanel.setLayout(null);

        characterPanel = new JPanel();
        characterPanel.setBackground(Color.LIGHT_GRAY);
        characterPanel.setBounds(
            inventoryPanel.getWidth()/20 * 1,
            inventoryPanel.getHeight()/2/20*1,
            inventoryPanel.getWidth()/20*18,
            inventoryPanel.getHeight()/2/20*18
        ); // set character panel where it shows equipped items and character related stats

        // call in functions
        spawnItemSlots();
        spawnCharacterPreview();

        inventoryPanel.setVisible(false);
        characterPanel.setLayout(null);

        frame.add(inventoryPanel);

        inventoryPanel.add(characterPanel);
    }

    private void spawnItemSlots() {
        int slotSize = 100; // slot sizes width and height (square)
        int spacing = 10; // spacing between each slot
        int startX = 20; // placement starting X
        int startY = 20; // placement starting Y

        int maxColumns = 4; // max no of columns

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
            label.setFont(new Font("Times New Roman", Font.PLAIN, 10));
            label.setForeground(Color.WHITE);
            label.setBounds(2, 2, slotSize - 4, slotSize - 4);
            slotPanel.add(label);

            // add panels to screen
            characterPanel.add(slotPanel);
            itemPanels.put(slotName, slotPanel);
        }
    }

    private void spawnCharacterPreview() { // make the enlarged character preview
        int previewWidth = 120; // character width
        int previewHeight = 240; // character height

        int previewX = 550; // where to spawn X
        int previewY = 245; // where to spawn Y

        // create the character (later replaced with actual character design)
        characterPreviewPanel = new JPanel();
        characterPreviewPanel.setBounds(previewX, previewY, previewWidth, previewHeight);
        characterPreviewPanel.setBackground(Color.BLUE);
        characterPreviewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        // label for the character (just a placeholder, later goes username)
        JLabel previewLabel = new JLabel("Character", SwingConstants.CENTER);
        previewLabel.setForeground(Color.WHITE);
        previewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        characterPreviewPanel.add(previewLabel);

        characterPanel.add(characterPreviewPanel); // add to screen
    }

    public void keyPressed(KeyEvent e) { // check if I key pressed; if so, open the inventory panel
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_I) {
            boolean visible = inventoryPanel.isVisible();
            inventoryPanel.setVisible(!visible);

            if (frame != null) {
                frame.revalidate();
                frame.repaint();
            }
        }
    }
}
