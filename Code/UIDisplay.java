package Code;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import Code.UICode.HealthOrb.HealthOrbClass;
import Code.UICode.ManaOrb.ManaOrbClass;
import Code.UICode.Potion.PotionClass;
import Code.UICode.Dash.DashClass;
import Code.UICode.PotionLogic;
import Code.UICode.DashLogic;
import Code.UICode.Potion;
import Code.UICode.InventoryUI;
import Code.UICode.Map;

public class UIDisplay {

    private HealthOrbClass healthOrb;
    private ManaOrbClass manaOrb;
    private PotionClass potion;
    private DashClass dash;
    private PotionLogic statsPotionLogic;
    private DashLogic statsDashLogic;
    private InventoryUI inventory;
    private Map map;

    List<JComponent> uiElements = new ArrayList<>();
    
    public UIDisplay(JFrame frame, CharacterAttributes stats, CharacterMovement character) {

        // set the layout to null for easier placement
        frame.getContentPane().setLayout(null);

        // spawn in the UI classes
        healthOrb = new HealthOrbClass(stats);
        manaOrb = new ManaOrbClass(stats);
        potion = new PotionClass();
        dash = new DashClass(stats);
        inventory = new InventoryUI(frame);
        map = new Map(frame);

        // create health hover box (shows a display of how much health precisely)
        JPanel healthHoverBox = new JPanel();
        JLabel healthLabel = new JLabel();
        healthHoverBox.setBackground(new Color(0, 0, 0, 180));
        healthHoverBox.setSize(80, 25);
        healthLabel.setForeground(Color.WHITE);
        healthLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
        healthHoverBox.add(healthLabel);
        healthHoverBox.setVisible(false);

        // create mana hover box (shows a display of how much mana precisely)
        JPanel manaHoverBox = new JPanel();
        JLabel manaLabel = new JLabel();
        manaHoverBox.setBackground(new Color(0, 0, 0, 180));
        manaHoverBox.setSize(80, 25);
        manaLabel.setForeground(Color.WHITE);
        manaLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
        manaHoverBox.add(manaLabel);
        manaHoverBox.setVisible(false);

        // add components to the frame
        frame.add(healthHoverBox);
        frame.add(manaHoverBox);

        // set the setBounds attributes
        int orbSize = 100; // base orb size (mana and health orbs) to 1/8 of the screen height
        int miniOrbSize = 55; // mini orb size (potion and dash) to half of the base orb size
        int bottomMargin = 60; // the margin from the bottom of the screen (a spare)
        int orbPosY = (frame.getHeight() - orbSize - bottomMargin); // the base orb poisiton Y
        int healthPosX = 150; // the X position of the healthOrb
        int manaPosX = 550; // the X position of the manaOrb
        int potionPosX = healthPosX - miniOrbSize + 10, potionPosY = orbPosY - (orbSize / 4); // the X position of the potion orb
        int dashPosX = manaPosX + orbSize, dashPosY = orbPosY - (orbSize / 4); // the X position of the dash orb

        // setBounds commands for components
        healthOrb.setBounds(healthPosX, orbPosY, orbSize, orbSize);
        manaOrb.setBounds(manaPosX, orbPosY, orbSize, orbSize);
        potion.setBounds(potionPosX, potionPosY, miniOrbSize, miniOrbSize);
        dash.setBounds(dashPosX, dashPosY, miniOrbSize, miniOrbSize);

        // add the UI components to the UIElements list for later easy placement
        uiElements.add(healthOrb);
        uiElements.add(manaOrb);
        uiElements.add(potion);
        uiElements.add(dash);

        // quantity label for the potion orb
        JLabel potionQtyLabel = new JLabel();
        potionQtyLabel.setForeground(Color.WHITE);
        potionQtyLabel.setFont(new Font("Times New Roman", Font.BOLD, miniOrbSize / 4));

        // quantity label for the dash orb
        JLabel dashQtyLabel = new JLabel();
        dashQtyLabel.setForeground(Color.WHITE);
        dashQtyLabel.setFont(new Font("Times New Roman", Font.BOLD, miniOrbSize / 4));

        // set the logics (actual functions for making it work)
        this.statsPotionLogic = new PotionLogic(potion, stats, potionQtyLabel, frame);
        potionQtyLabel.setText(String.valueOf(statsPotionLogic.potionVal));
        this.statsDashLogic = new DashLogic(dash, stats, dashQtyLabel, frame, character);
        character.setDashLogic(statsDashLogic);
        dashQtyLabel.setText(String.valueOf(statsDashLogic.dashVal));

        // setBounds commands for the quantity labels
        potionQtyLabel.setBounds(potionPosX + (miniOrbSize / 3), potionPosY + (miniOrbSize / 4), miniOrbSize / 2, miniOrbSize / 2);
        dashQtyLabel.setBounds(dashPosX + (miniOrbSize / 3), dashPosY + (miniOrbSize / 4), miniOrbSize / 2, miniOrbSize / 2);

        // add quantity labels to frame
        frame.add(potionQtyLabel);
        frame.add(dashQtyLabel);

        // KeyListener for key-controlled UI
        frame.addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e) {
                statsPotionLogic.keyPressed(e);
                inventory.keyPressed(e);
                map.keyPressed(e);
                frame.repaint();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                statsPotionLogic.keyReleased(e);
                frame.repaint();
            }
        });

        // Click listeners for the potion and dash orbs
        potion.addMouseListener(new MouseAdapter() {
            @Override 
            public void mousePressed(MouseEvent e) {
                statsPotionLogic.potionClicked();
                frame.repaint();
            }
        });
        dash.addMouseListener(new MouseAdapter() {
            @Override 
            public void mousePressed(MouseEvent e) {
                statsDashLogic.dashClicked();
                frame.repaint();
            }
        });

        // for loop to add each component in the uiElements list
        for (JComponent component : uiElements) {
            frame.add(component);
        }

        // hover adapter for the health hover labels
        MouseAdapter healthHoverAdapter = new MouseAdapter() {
            @Override 
            public void mouseEntered(MouseEvent e) {
                healthLabel.setText((int)stats.charCurrHP + " / " + (int)stats.charMaxHP);
                healthHoverBox.setVisible(true);
                frame.getContentPane().setComponentZOrder(healthHoverBox, 0);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                healthHoverBox.setVisible(false);
            }

            @Override 
            public void mouseMoved(MouseEvent e) {
                Point mousePos = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), frame);
                healthLabel.setText((int)stats.charCurrHP + " / " + (int)stats.charMaxHP);
                healthHoverBox.setLocation(mousePos.x + 10, mousePos.y - 30);
                frame.getContentPane().setComponentZOrder(healthHoverBox, 0);
            }
        };

        // hover adapter for the mana hover labels
        MouseAdapter manaHoverAdapter = new MouseAdapter() {
            @Override  
            public void mouseEntered(MouseEvent e) {
                manaLabel.setText((int)stats.charCurrMana + " / " + (int)stats.charMaxMana);
                manaHoverBox.setVisible(true);
                frame.getContentPane().setComponentZOrder(manaHoverBox, 0);
            }

            @Override 
            public void mouseExited(MouseEvent e) {
                manaHoverBox.setVisible(false);
            }

            @Override  
            public void mouseMoved(MouseEvent e) {
                Point mousePos = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), frame);
                manaLabel.setText((int)stats.charCurrMana + " / " + (int)stats.charMaxMana);
                manaHoverBox.setLocation(mousePos.x + 10, mousePos.y - 30);
                frame.getContentPane().setComponentZOrder(manaHoverBox, 0);
            }
        };

        // add mouseListeners and mouseMotionListeners to components
        healthOrb.addMouseListener(healthHoverAdapter);
        healthOrb.addMouseMotionListener(healthHoverAdapter);
        manaOrb.addMouseListener(manaHoverAdapter);
        manaOrb.addMouseMotionListener(manaHoverAdapter);

        // reset frame
        frame.revalidate();
        frame.repaint();
    }
}