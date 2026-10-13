package Code;

import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Damage {

    public int enemyPosX = 600, enemyPosY = 800;
    public int enemyWidth = 40, enemyHeight = 80;

    public double atkSpd = 0.625;
    private double crtChance = 0.25;
    private double crtDmg = 3;
    public int enemyDmg = 5;

    private Random random = new Random();

    public boolean colliding = false;
    private long lastAttackTime = 0;

    private CharacterMovement characterMovement;
    private CharacterDisplay characterDisplay;
    private CharacterAttributes stats;

    public JPanel enemyTester;
    
    public Damage(JFrame frame, CharacterMovement characterMovement, CharacterDisplay characterDisplay, CharacterAttributes stats) {

        this.characterDisplay = characterDisplay;
        this.characterMovement = characterMovement;
        this.stats = stats;

        enemyTester = new JPanel();
        enemyTester.setBackground(Color.MAGENTA);
        enemyTester.setBounds(enemyPosX, enemyPosY, enemyWidth, enemyHeight);

        frame.add(enemyTester);
    }
    
    public void checkCollision() {
        if (characterDisplay.character.getBounds().intersects(enemyTester.getBounds())) {
            colliding = true;
            enemyDMG();
        } else {
            colliding = false;
        }
    }

    public void enemyDMG() {
        if (colliding) {
            attack();
        }
    }

    public void attack() {
        long attackCooldownMs = (long) ((1.0 / atkSpd) * 1000);

        long currTime = System.currentTimeMillis();

        if (currTime - lastAttackTime >= attackCooldownMs) {
            if (random.nextInt(100) < crtChance * 100) {
                stats.charCurrHP -= enemyDmg * crtDmg;
                lastAttackTime = currTime;
            } else {
                stats.charCurrHP -= enemyDmg;
                lastAttackTime = currTime;
            }
            
        }
    }
}
