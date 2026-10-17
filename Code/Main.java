package Code;
import java.awt.*;
import javax.swing.*;

public class Main {
    
    public static void main(String[] args) {

        CharacterAttributes attributes = new CharacterAttributes();
        CharacterDisplay display = new CharacterDisplay();

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

        CharacterMovement movement = new CharacterMovement(frame, display, attributes);
        display.characterDisplay(frame, movement);

        UIDisplay UIdisplay = new UIDisplay(frame, attributes, movement);

        Damage damage = new Damage(frame, movement, display, attributes);
        movement.setDamageSystem(damage);

        frame.setVisible(true);

        frame.repaint();
    }
}
