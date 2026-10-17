package Code;
import java.awt.*;
import javax.swing.*;

public class Main {
    
    private static CharacterMovement movement;
    private static CharacterAttributes attributes;
    private static CharacterDisplay display;
    private static UIDisplay uiDisplay;

    public static void main(String[] args) {

        attributes = new CharacterAttributes();
        display = new CharacterDisplay();

        Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int userScreenWidth = userScreenSize.width;
        int userScreenHeight = userScreenSize.height;

        JFrame frame = new JFrame("Edinlode");

        frame.setResizable(false);

        frame.setSize(userScreenWidth, userScreenHeight);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(150, 150, 150));

        movement = new CharacterMovement(frame, display, attributes);
        display.characterDisplay(frame, movement);

        uiDisplay = new UIDisplay(frame, attributes, movement);

        Damage damage = new Damage(frame, movement, display, attributes);
        movement.setDamageSystem(damage);

        frame.setVisible(true);

        frame.repaint();

        Timer gameloop = new Timer(16, e -> update());
        gameloop.start();
    }

    private static void update() {
        movement.updateFieldAndCollision();
        attributes.updateValues();
    }
}