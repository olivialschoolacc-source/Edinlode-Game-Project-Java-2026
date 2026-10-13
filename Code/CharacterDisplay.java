package Code;

import java.awt.*;
import javax.swing.*;

public class CharacterDisplay {

    public JPanel character = new JPanel();
    private CharacterMovement stats;

    public int characterWidth = 40, characterHeight = 80;

    public void characterDisplay(JFrame frame, CharacterMovement stats) {

        this.stats = stats;

        character.setBounds((int)stats.startPosX, (int)stats.startPosY, characterWidth, characterHeight);
        character.setBackground(Color.BLUE);

        frame.add(character);
    }

    public void changeAppearance() {

        if (stats.facingLeft) {
            character.setBackground(Color.RED);
        } else {
            character.setBackground(Color.BLUE);
        }
    }
}