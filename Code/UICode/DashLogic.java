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

    public int dashVal = 1;
    
    private double cooldown;
    private boolean isCoolingDown = false;

    public DashLogic(DashClass dash, CharacterAttributes stats, JLabel qtyLabel, JFrame frame, CharacterMovement character) {
        
        this.dash = dash;
        this.stats = stats;
        this.qtyLabel = qtyLabel;
        this.frame = frame;
        this.character = character;

        updateCooldown();
        updateLabel();
    }

    private void updateCooldown() {

        cooldown = 5000 * (1.0 - stats.cdReduction);
        cooldown = Math.max(0, cooldown);
    }

    public void triggerDashInput() {

        useDash();
    }

    private void useDash() {

        if (dashVal <= 0 || isCoolingDown) {
            return;
        }

        dashVal--;
        isCoolingDown = true;
        updateLabel();

        int cooldownMs = (int)cooldown;

        if (character != null) {
            int direction = character.activeDirection != 0
                ? character.activeDirection
                : (character.facingLeft ? -1 : 1);
            character.startDash(direction);
        }

        if (cooldownMs <= 0) {
            finishCooldown();
        } else {
            if (dash != null) {
                dash.startCooldown(cooldownMs);
            }

            Timer timer = new Timer(cooldownMs, e -> {
                ((Timer) e.getSource()).stop();
                finishCooldown();
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    private void finishCooldown() {

        isCoolingDown = false;

        if (dashVal < stats.maxDashVal) {
            dashVal++;
        }

        updateLabel();
    }

    public void dashClicked() {
        if (dashVal > 0 && !isCoolingDown) {
            useDash();
        }
    }

    private void updateLabel() {
        if (qtyLabel != null) {
            qtyLabel.setText(String.valueOf(dashVal));
        }
    }
}
