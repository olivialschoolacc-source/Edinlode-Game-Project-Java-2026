package Code;

import java.awt.*;
import javax.swing.*;

public class DamagePopup extends JLabel {

    private Timer animTimer;
    private float alpha = 1.0f;
    private double currentY;

    public DamagePopup(JFrame frame, double startX, double startY, int damageAmount, boolean isCrit) {
        setText("-" + damageAmount);

        if (isCrit) { // if damage is a critical, number colour orange
            setFont(new Font("Times New Roman", Font.BOLD, 22));
            setForeground(new Color(255, 165, 0));
        } else { // if damage is not a critical and is a normal, number colour red
            setFont(new Font("Times New Roman", Font.BOLD, 22));
            setForeground(Color.RED);
        }
        
        currentY = startY; // spawn Y coordinate
        setBounds((int) startX, (int) currentY, 100, 30); // set the label bounds

        // frame work
        frame.getLayeredPane().add(this, JLayeredPane.POPUP_LAYER);
        frame.repaint();

        // timer starts when damage dealt to the character and spawns number, when timer stops, the number disappears
        animTimer = new Timer(16, e -> {
            currentY -= 1.5;
            alpha -= 0.03f;

            if (alpha <= 0f) {
                alpha = 0f;
                animTimer.stop();
                frame.getLayeredPane().remove(this);
                frame.repaint();
            } else {
                setLocation(getX(), (int) currentY);
                repaint();
            }
        });
        animTimer.setRepeats(true);
        animTimer.start();
    }

    // draw the number onto the screen
    @Override 
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2d);
        g2d.dispose();
    }
}
