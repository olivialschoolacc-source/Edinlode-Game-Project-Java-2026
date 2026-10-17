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

public class UIDisplay {

    private HealthOrbClass healthOrb;
    private ManaOrbClass manaOrb;
    private PotionClass potion;
    private DashClass dash;
    private PotionLogic statsPotionLogic;
    private DashLogic statsDashLogic;

    List<JComponent> uiElements = new ArrayList<>();
    
    public UIDisplay(JFrame frame, CharacterAttributes stats, CharacterMovement character) {

        frame.getContentPane().setLayout(null);

        healthOrb = new HealthOrbClass(stats);
        manaOrb = new ManaOrbClass(stats);
        potion = new PotionClass();
        dash = new DashClass();

        JPanel healthHoverBox = new JPanel();
        JLabel healthLabel = new JLabel();
        healthHoverBox.setBackground(new Color(0, 0, 0, 180));
        healthHoverBox.setSize(80, 25);
        healthLabel.setForeground(Color.WHITE);
        healthLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
        healthHoverBox.add(healthLabel);
        healthHoverBox.setVisible(false);

        JPanel manaHoverBox = new JPanel();
        JLabel manaLabel = new JLabel();
        manaHoverBox.setBackground(new Color(0, 0, 0, 180));
        manaHoverBox.setSize(80, 25);
        manaLabel.setForeground(Color.WHITE);
        manaLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
        manaHoverBox.add(manaLabel);
        manaHoverBox.setVisible(false);

        frame.add(healthHoverBox);
        frame.add(manaHoverBox);

        int orbSize = frame.getHeight() / 8;
        int miniOrbSize = orbSize / 2;
        int bottomMargin = frame.getHeight() / 10;
        int orbPosY = (frame.getHeight() - orbSize - bottomMargin);

        int healthPosX = (frame.getWidth() - orbSize) / 4;
        int manaPosX = (frame.getWidth() - orbSize) / 4 * 3;
        int potionPosX = (frame.getWidth() + orbSize) / 4 - (orbSize / 2), potionPosY = orbPosY - (orbSize / 3);
        int dashPosX = (frame.getWidth() - orbSize) / 4 * 3 + (orbSize / 2) + 10, dashPosY = orbPosY - (orbSize / 3);

        healthOrb.setBounds(healthPosX, orbPosY, orbSize, orbSize);
        manaOrb.setBounds(manaPosX, orbPosY, orbSize, orbSize);
        potion.setBounds(potionPosX, potionPosY, miniOrbSize, miniOrbSize);
        dash.setBounds(dashPosX, dashPosY, miniOrbSize, miniOrbSize);

        uiElements.add(healthOrb);
        uiElements.add(manaOrb);
        uiElements.add(potion);
        uiElements.add(dash);

        JLabel potionQtyLabel = new JLabel();
        potionQtyLabel.setForeground(Color.WHITE);
        potionQtyLabel.setFont(new Font("Times New Roman", Font.BOLD, miniOrbSize / 4));

        JLabel dashQtyLabel = new JLabel();
        dashQtyLabel.setForeground(Color.WHITE);
        dashQtyLabel.setFont(new Font("Times New Roman", Font.BOLD, miniOrbSize / 4));

        this.statsPotionLogic = new PotionLogic(potion, stats, potionQtyLabel, frame);
        potionQtyLabel.setText(String.valueOf(statsPotionLogic.potionVal));
        this.statsDashLogic = new DashLogic(dash, stats, dashQtyLabel, frame, character);
        character.setDashLogic(statsDashLogic);
        dashQtyLabel.setText(String.valueOf(statsDashLogic.dashVal));

        potionQtyLabel.setBounds(potionPosX + (miniOrbSize / 3), potionPosY + (miniOrbSize / 4), miniOrbSize / 2, miniOrbSize / 2);
        dashQtyLabel.setBounds(dashPosX + (miniOrbSize / 3), dashPosY + (miniOrbSize / 4), miniOrbSize / 2, miniOrbSize / 2);

        frame.add(potionQtyLabel);
        frame.add(dashQtyLabel);

        frame.addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e) {
                statsPotionLogic.keyPressed(e);
                frame.repaint();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                statsPotionLogic.keyReleased(e);
                frame.repaint();
            }
        });
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

        for (JComponent component : uiElements) {
            frame.add(component);
        }

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

        healthOrb.addMouseListener(healthHoverAdapter);
        healthOrb.addMouseMotionListener(healthHoverAdapter);

        manaOrb.addMouseListener(manaHoverAdapter);
        manaOrb.addMouseMotionListener(manaHoverAdapter);

        frame.revalidate();
        frame.repaint();
    }
}