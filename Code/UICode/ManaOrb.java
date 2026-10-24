package Code.UICode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;


import Code.CharacterAttributes;

public class ManaOrb {
    
    public static class ManaOrbClass extends JComponent {

        private CharacterAttributes stats;
        private double wavePhase = 0;

        public ManaOrbClass(CharacterAttributes stats) {

            this.stats = stats;
            setOpaque(false);
        }

        public void updateWaveAnimation() {

            wavePhase += 0.12; // wave calculation value
        }

        @Override 
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            Ellipse2D.Double sphereBounds = new Ellipse2D.Double(2, 2, width - 4, height - 4);

            g2d.setColor(new Color(10, 15, 30, 220)); // background
            g2d.fill(sphereBounds);

            double manaPercent = stats.charCurrMana / stats.charMaxMana; 
            if (manaPercent < 0) manaPercent = 0;

            int fluidTopY = height - (int) (manaPercent * height);

            Polygon liquidWaveShape = new Polygon();

            for (int x = 0; x <= width; x++) {
                int waveOffsetY = (int) (Math.sin(x * 0.08 + wavePhase) * 3.5);

                if (manaPercent <= 0.05 || manaPercent >= 0.95) waveOffsetY = 0;

                liquidWaveShape.addPoint(x, fluidTopY + waveOffsetY);
            }

            liquidWaveShape.addPoint(width, height);
            liquidWaveShape.addPoint(0, height);

            Area fluidClipArea = new Area(sphereBounds);
            fluidClipArea.intersect(new Area(liquidWaveShape));

            GradientPaint liquidGradient = new GradientPaint(
                0, fluidTopY - 5, new Color(0, 150, 255),
                0, height, new Color(5, 15, 80)
            );
            g2d.setPaint(liquidGradient);
            g2d.fill(fluidClipArea);

            g2d.setStroke(new BasicStroke(3.0f));
            g2d.setColor(new Color(95, 105, 115));
            g2d.draw(sphereBounds);

            g2d.setStroke(new BasicStroke(3.0f));
            g2d.setColor(new Color(255, 255, 255, 40));
            g2d.drawOval(4, 4, width - 8, height - 8);
        }
    }
}
