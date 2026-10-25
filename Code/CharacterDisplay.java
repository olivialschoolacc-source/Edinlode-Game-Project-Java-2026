package Code;

import java.awt.*;
import javax.swing.*;

public class CharacterDisplay {

    public JPanel character = new JPanel(); // create the character display
    public JLabel username = new JLabel("USERNAME PLACEHOLDER", SwingConstants.CENTER);
    private CharacterMovement stats; // get the characterMovement stats

    public int characterWidth = 30, characterHeight = 60; // character width and height (px)

    public void characterDisplay(JFrame frame, CharacterMovement stats) {

        this.stats = stats; // set stats

        character.setBounds((int)stats.startPosX, (int)stats.startPosY, characterWidth, characterHeight); // setBounds on the character display
        character.setBackground(Color.BLUE); // set character colour

        username.setForeground(Color.WHITE);
        username.setFont(new Font("Times New Roman", Font.BOLD, 12));
        username.setBounds(0, 0, 100, 20);
        username.setOpaque(true);
        username.setBackground(new Color(0, 0, 0, 120));
        username.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        username.setSize(100, 20);

        frame.getLayeredPane().add(character, JLayeredPane.DEFAULT_LAYER);
        frame.getLayeredPane().add(username, JLayeredPane.DEFAULT_LAYER);
    }

    public void changeAppearance() { // change colour based on the direction the character is facing (debug purposes)

        if (stats.facingLeft) {
            character.setBackground(Color.RED); // if facing left, RED
        } else {
            character.setBackground(Color.BLUE); // if facing right, BLUE
        }
    }
}