package Code.UICode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;

import Code.UIDisplay;

public class InventoryUI {

    public JPanel inventoryPanel;
    public JPanel characterPanel;
    private JFrame frame;

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

    public InventoryUI(JFrame frame) {
        this.frame = frame;

        inventoryPanel = new JPanel();
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
        );

        inventoryPanel.setVisible(false);
        frame.add(inventoryPanel);

        inventoryPanel.add(characterPanel);
    }

    public void keyPressed(KeyEvent e) {
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
