package Code;

import java.awt.*;
import javax.swing.*;

public class CharacterDisplay {

    public JPanel character = new JPanel(); // create the character display
    private CharacterMovement stats; // get the characterMovement stats

    public int characterWidth = 30, characterHeight = 60; // character width and height (px)

    public void characterDisplay(JFrame frame, CharacterMovement stats) {

        this.stats = stats; // set stats

        character.setBounds((int)stats.startPosX, (int)stats.startPosY, characterWidth, characterHeight); // setBounds on the character display
        character.setBackground(Color.BLUE); // set character colour

        frame.add(character); // add character to frame
    }

    public void changeAppearance() { // change colour based on the direction the character is facing (debug purposes)

        if (stats.facingLeft) {
            character.setBackground(Color.RED); // if facing left, RED
        } else {
            character.setBackground(Color.BLUE); // if facing right, BLUE
        }
    }
}