package Code.UICode;

import javax.swing.*;

import Code.CharacterAttributes;
import Code.CharacterMovement;
import Code.UICode.Dash.DashClass;

public class DashLogic {

    private DashClass dash;
    private CharacterAttributes stats;
    private JLabel qtyLabel;
    private JFrame frame;
    private CharacterMovement character;

    public int dashVal = 1; // max amount of dashes
    
    private double cooldown; // cooldown
    private boolean isCoolingDown = false; // boolean if it is in cooldown

    public DashLogic(DashClass dash, CharacterAttributes stats, JLabel qtyLabel, JFrame frame, CharacterMovement character) {
        
        // call classes
        this.dash = dash;
        this.stats = stats;
        this.qtyLabel = qtyLabel;
        this.frame = frame;
        this.character = character;

        // call function
        updateCooldown();
        updateLabel();
    }

    private void updateCooldown() { // update cooldown

        cooldown = 5000 * (1.0 - stats.cdReduction); // 5 seconds cooldown; cooldown reduction applied
        cooldown = Math.max(0, cooldown);
    }

    public void triggerDashInput() {

        useDash(); // when dash happens, use dash
    }

    private void useDash() { // when dash used,

        if (stats.isUntargetable) {
            return; // if untargetable, pass
        }

        if (dashVal <= 0 || isCoolingDown) {
            return; // if dash is in cooldown and lesser than 0
        }

        dashVal--; // take away one dash when successfully used
        isCoolingDown = true; // set to cooling down
        updateLabel(); // update the quantity label

        int cooldownMs = (int)cooldown;

        if (character != null) { // if character is existent
            int direction = character.activeDirection != 0
                ? character.activeDirection
                : (character.facingLeft ? -1 : 1);
            character.startDash(direction);
        }

        if (cooldownMs <= 0) { // if cooldown finishes
            finishCooldown(); // finish cooldown
        } else { // if cooldown still going
            if (dash != null) { // continue cooldown
                dash.startCooldown(cooldownMs);
            }

            Timer timer = new Timer(cooldownMs, e -> { // cooldown timer
                ((Timer) e.getSource()).stop();
                finishCooldown();
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    private void finishCooldown() { // finish cooldown, add to dash

        isCoolingDown = false;

        if (dashVal < stats.maxDashVal) {
            dashVal++; // dash quantity added
        }

        updateLabel(); // update quantity label
    }

    public void dashClicked() { // check if dash is clicked, not just keyboard
        if (dashVal > 0 && !isCoolingDown) {
            useDash();
        }
    }

    private void updateLabel() { // update the quantity label
        if (qtyLabel != null) {
            qtyLabel.setText(String.valueOf(dashVal));
        }
    }
}