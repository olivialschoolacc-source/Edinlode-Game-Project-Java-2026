package Code.UICode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Potion {
    
    public static class PotionClass extends JComponent {

        public PotionClass() {
            setOpaque(false);
        }

        @Override 
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();

            Ellipse2D.Double sphereBounds = new Ellipse2D.Double(2, 2, width - 16, height - 16);

            g2d.setColor(new Color(25, 10, 10, 220));
            g2d.fill(sphereBounds);

            g2d.setStroke(new BasicStroke(3.0f));
            g2d.setColor(new Color(255, 255, 255, 40));
            g2d.drawOval(4, 4, width - 20, height - 20);
        }
    }
}
