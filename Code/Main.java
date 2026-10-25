package Code;
import java.awt.*;
import javax.swing.*;

public class Main {
    
    private static CharacterMovement movement;
    private static CharacterAttributes attributes;
    private static CharacterDisplay display;
    private static UIDisplay uiDisplay;

    public static void main(String[] args) {

        // bring in the CharacterAttributes and CharacterDisplay classes
        attributes = new CharacterAttributes();
        display = new CharacterDisplay();

        // set the screen dimension
        // Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize(); // get the user's computer screen size
        // int userScreenWidth = userScreenSize.width; // set to fullscreen
        // int userScreenHeight = userScreenSize.height; // set to fullscreen

        int screenWidth = 1000, screenHeight = 800;

        // Create JFrame
        JFrame frame = new JFrame("Edinlode");

        // extra attributes for the JFrame
        frame.setResizable(false); // cannot resize, always fullscreen
        frame.setSize(screenWidth, screenHeight); // set the size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // make it exit when X pressed
        frame.setLocationRelativeTo(null); // window spawn location null
        frame.setLayout(null); // set layout to null
        frame.getContentPane().setBackground(new Color(150, 150, 150)); // set the background colour

        // call in CharacterMovement class and set the characterDisplay call to spawn character
        movement = new CharacterMovement(frame, display, attributes);
        display.characterDisplay(frame, movement);

        // bring in the UIDisplay class
        uiDisplay = new UIDisplay(frame, attributes, movement);

        // call in the Damage class
        Damage damage = new Damage(frame, movement, display, attributes);
        movement.setDamageSystem(damage); // make the damage system work

        // frame finalizing
        frame.setVisible(true);
        frame.repaint();

        // gameloop timer
        Timer gameloop = new Timer(16, e -> update());
        gameloop.start();
    }

    private static void update() { // update method
        movement.updateFieldAndCollision();
        attributes.updateValues();
    }
}