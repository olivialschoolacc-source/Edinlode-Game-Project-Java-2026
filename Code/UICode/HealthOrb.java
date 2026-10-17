package Code.UICode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;


import Code.CharacterAttributes;

public class HealthOrb {
    
    public static class HealthOrbClass extends JComponent {

        private CharacterAttributes stats;
        private double wavePhase = 0;
        private double displayHealthPercent = -1.0;
        private double previousHealthPercent = -1.0;   
        private double healDisplayPercent = -1.0;
        private double healTargetPercent = -1.0;

        public HealthOrbClass(CharacterAttributes stats) {

            this.stats = stats;
            setOpaque(false);
        }

        public void updateWaveAnimation() {

            wavePhase += 0.12;
        }

        @Override 
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            Ellipse2D.Double sphereBounds = new Ellipse2D.Double(2, 2, width - 4, height - 4);

            g2d.setColor(new Color(25, 10, 10, 220));
            g2d.fill(sphereBounds);

            double healthPercent = stats.charCurrHP / stats.charMaxHP;

            healthPercent = Math.max(0, Math.min(1, healthPercent));

            if (previousHealthPercent < 0) {
                previousHealthPercent = healthPercent;
                displayHealthPercent = healthPercent;
                healDisplayPercent = healthPercent;
                healTargetPercent = healthPercent;
            }

            if (healthPercent < previousHealthPercent) {
                displayHealthPercent = Math.max(
                    displayHealthPercent, previousHealthPercent
                );

                healDisplayPercent = healthPercent;
                healTargetPercent = healthPercent;
            }

            if (healthPercent > previousHealthPercent) {
                healDisplayPercent = previousHealthPercent;
                healTargetPercent = healthPercent;
            }

            if (displayHealthPercent > healthPercent) {
                displayHealthPercent -= 0.015;

                if (displayHealthPercent < healthPercent) {
                    displayHealthPercent = healthPercent;
                }
            }

            if (healDisplayPercent < healTargetPercent) {
                healDisplayPercent += 0.015;

                if (healDisplayPercent >= healTargetPercent) {
                    healDisplayPercent = healTargetPercent;
                }
            }

            previousHealthPercent = healthPercent;

            int trailTopY = height - (int) (displayHealthPercent * height);
            Polygon trailWaveShape = new Polygon();
            for (int x = 0; x <= width; x++) {
                int waveOffsetY = (int) (Math.sin(x * 0.08 + wavePhase) * 3.5);
                if (displayHealthPercent <= 0.05 || displayHealthPercent >= 0.95) waveOffsetY = 0;
                trailWaveShape.addPoint(x, trailTopY + waveOffsetY);
            }
            trailWaveShape.addPoint(width, height);
            trailWaveShape.addPoint(0, height);

            Area trailClipArea = new Area(sphereBounds);
            trailClipArea.intersect(new Area(trailWaveShape));

            GradientPaint trailGradient = new GradientPaint(
                0, trailTopY - 5, new Color(255, 140, 0),
                0, height, new Color(120, 50, 0)
            );
            g2d.setPaint(trailGradient);
            g2d.fill(trailClipArea);

            int fluidTopY = height - (int) (healthPercent * height);
            Polygon liquidWaveShape = new Polygon();
            for (int x = 0; x <= width; x++) {
                int waveOffsetY = (int) (Math.sin(x * 0.08 + wavePhase) * 3.5);
                if (healthPercent <= 0.05 || healthPercent >= 0.95) waveOffsetY = 0;
                liquidWaveShape.addPoint(x, fluidTopY + waveOffsetY);
            }
            liquidWaveShape.addPoint(width, height);
            liquidWaveShape.addPoint(0, height);

            Area fluidClipArea = new Area(sphereBounds);
            fluidClipArea.intersect(new Area(liquidWaveShape));

            GradientPaint liquidGradient = new GradientPaint(
                0, fluidTopY - 5, new Color(230, 20, 20),
                0, height, new Color(80, 5, 5)
            );
            g2d.setPaint(liquidGradient);
            g2d.fill(fluidClipArea);

            int healTopY = height - (int) (healDisplayPercent * height);
            int healBottomY = height - (int) (healTargetPercent * height);

            Polygon healWaveShape = new Polygon();

            for (int x = 0; x <= width; x++) {
                int waveOffsetY = (int) (
                    Math.sin(x * 0.08 + wavePhase) * 3.5
                );

                healWaveShape.addPoint(x, healTopY + waveOffsetY);
            }

            for (int x = width; x >= 0; x--) {
                int waveOffsetY = (int) (
                    Math.sin(x * 0.08 + wavePhase) * 3.5
                );

                healWaveShape.addPoint(x, healBottomY + waveOffsetY);
            }

            Area healClipArea = new Area(sphereBounds);
            healClipArea.intersect(new Area(healWaveShape));

            GradientPaint healGradient = new GradientPaint(
                0, healTopY - 5, new Color(140, 0, 0),
                0, healBottomY, new Color(50, 0, 0)
            );

            g2d.setPaint(healGradient);
            g2d.fill(healClipArea);

            g2d.setStroke(new BasicStroke(3.0f));
            g2d.setColor(new Color(110, 95, 75));
            g2d.draw(sphereBounds);

            g2d.setStroke(new BasicStroke(3.0f));
            g2d.setColor(new Color(255, 255, 255, 40));
            g2d.drawOval(4, 4, width - 8, height - 8);
        }
    }
}