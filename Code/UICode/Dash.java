package Code.UICode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

import Code.CharacterAttributes;

public class Dash {

    public static class DashClass extends JComponent {

        // set variables
        private float cooldownProgress = 0f;
        private Timer cooldownTimer;

        // call in classes
        private CharacterAttributes stats;
    
        public DashClass(CharacterAttributes stats) {
            setOpaque(false);
            this.stats = stats;
        }

        public void startCooldown(int durationMs) { // start the cooldown
        
            if (cooldownTimer != null && cooldownTimer.isRunning()) {
                cooldownTimer.stop();
            }

            cooldownProgress = 1f;
            long startTime = System.currentTimeMillis();

            cooldownTimer = new Timer(16, e -> {
                long elapsed = System.currentTimeMillis() - startTime;

                cooldownProgress = Math.max(
                    0f,
                    1f - (float) elapsed / durationMs
                );

                repaint();

                if (cooldownProgress <= 0f) {
                    cooldownTimer.stop();
                }
            });

            cooldownTimer.start();
        }

        @Override 
        protected void paintComponent(Graphics g) { // make the dash orb
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            int thickness = 3;
            int diameter = Math.min(width, height) - (thickness * 2) - 8;

            int x = (width - diameter) / 2;
            int y = (height - diameter) / 2;

            Ellipse2D.Double sphereBounds = new Ellipse2D.Double(2, 2, width - 16, height - 16);

            g2d.setColor(new Color(25, 10, 10, 220));
            g2d.fill(sphereBounds);

            g2d.setStroke(new BasicStroke(3.0f));
            g2d.setColor(new Color(255, 255, 255, 40));
            g2d.drawOval(x - 6, y - 6, diameter, diameter);

            if (cooldownProgress > 0f) {

                g2d.setStroke(new BasicStroke(
                    thickness,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
                ));

                g2d.setColor(new Color(0, 150, 0, 220));

                g2d.drawArc(x - 6, y - 6, diameter, diameter, 90, -(int) (360 * cooldownProgress));
            } else {
                g2d.setStroke(new BasicStroke(thickness));
                g2d.setColor(new Color(95, 105, 115));
                g2d.draw(sphereBounds);
            }
        }
    }
}