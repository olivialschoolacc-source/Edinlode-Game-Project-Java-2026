package Code.UICode;

import javax.swing.*;
import java.awt.event.KeyEvent;

import Code.CharacterAttributes;
import Code.UICode.Potion.PotionClass;

public class PotionLogic {
    
    private PotionClass potion;
    private CharacterAttributes stats;
    private JLabel qtyLabel;
    private JFrame frame;

    public int potionVal = 5;

    private boolean isXPressed = false;

    public PotionLogic(PotionClass potion, CharacterAttributes stats, JLabel qtyLabel, JFrame frame) {

        this.potion = potion;
        this.stats = stats;
        this.frame = frame;
        this.qtyLabel = qtyLabel;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_X) {
            if (isXPressed) {
                return;
            }
            
            if (potionVal > 0 && stats.charCurrHP < stats.charMaxHP) {
                isXPressed = true;
                potionVal -= 1;
                stats.charCurrHP += stats.charMaxHP / 5;

                if (stats.charCurrHP > stats.charMaxHP) {
                    stats.charCurrHP = stats.charMaxHP;
                }
                if (qtyLabel != null) {
                    qtyLabel.setText(String.valueOf(potionVal));
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_X) {
            isXPressed = false;
        }
    }

    public void potionClicked() {
        if (potionVal > 0 && stats.charCurrHP < stats.charMaxHP) {
            isXPressed = true;
            potionVal -= 1;
            stats.charCurrHP += stats.charMaxHP / 5;
            
            if (stats.charCurrHP > stats.charMaxHP) {
                stats.charCurrHP = stats.charMaxHP;
            }
            if (qtyLabel != null) {
                qtyLabel.setText(String.valueOf(potionVal));
            }
        }
    }
}
