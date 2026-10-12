package Code;
import java.awt.*;
import javax.swing.*;

public class Main {
    
    public static void main(String[] args) {

        Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int userScreenWidth = userScreenSize.width;
        int userScreenHeight = userScreenSize.height;

        JFrame frame = new JFrame("Edinlode");

        frame.setResizable(false);

        frame.setSize(userScreenWidth, userScreenHeight);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        frame.setVisible(true);
    }
}
