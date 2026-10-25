package Code;

import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Damage {

    // temporary enemy positions and size
    public int enemyPosX = 700, enemyPosY = 500;
    public int enemyWidth = 30, enemyHeight = 60;

    // temporary enemy stats
    public double atkSpd = 0.625; // attack speed
    private double crtChance = 0.5; // chritical chance percentage
    private double crtDmg = 3; // multiplier
    public int enemyDmg = 5; // base damage

    // call in Random to make the crit chance work
    private Random random = new Random();

    public boolean colliding = false; // check if character is colliding (overlapping) with the enemy coordinates
    private long lastAttackTime = 0; // check the last attack time to countdown until next attack (attack speed)

    // call in classes
    private CharacterMovement characterMovement;
    private CharacterDisplay characterDisplay;
    private CharacterAttributes stats;

    // create the temporary enemy display
    public JPanel enemyTester;
    
    public Damage(JFrame frame, CharacterMovement characterMovement, CharacterDisplay characterDisplay, CharacterAttributes stats) {

        // bring in characterDisplay and characterMovement classes as well as the stats
        this.characterDisplay = characterDisplay;
        this.characterMovement = characterMovement;
        this.stats = stats;

        // generate temporary enemy display
        enemyTester = new JPanel();
        enemyTester.setBackground(Color.MAGENTA);
        enemyTester.setBounds(enemyPosX, enemyPosY, enemyWidth, enemyHeight);

        // add to frame
        frame.add(enemyTester);
    }
    
    public void checkCollision() { // check if collision happens, return boolean for colliding depending on result
        if (characterDisplay.character.getBounds().intersects(enemyTester.getBounds())) {
            colliding = true;
            enemyDMG();
        } else {
            colliding = false;
        }
    }

    public void enemyDMG() { // check if character and enemy colliding, if so, enemy attacks
        if (colliding) {
            attack();
        }
    }

    public void attack() { // attack command
        long attackCooldownMs = (long) ((1.0 / atkSpd) * 1000); // attack speed cooldown
        long currTime = System.currentTimeMillis(); // current time

        if (currTime - lastAttackTime >= attackCooldownMs) { // if the value of how long it took since last attack is greater than or equal to the attack cooldown
            if (stats.isUntargetable) { // if character is in dash and is untargetable
                return; // pass
            }

            double hpBefore = stats.charCurrHP; // check for the HP before the attack
            boolean isCrit = false; // check if the attack is a critical hit
            
            if (random.nextInt(100) < crtChance * 100) { // out of a hundred, if a randomly chosen number is selected by the critical chance
                stats.charCurrHP -= enemyDmg * crtDmg; // multiplier used on the normal damage amount
                isCrit = true; // set boolean
            } else { // if critical failed
                stats.charCurrHP -= enemyDmg; // normal attack
            }
            lastAttackTime = currTime; // reset last attack time

            // display the damage popup
            int damageDealt = (int) (hpBefore - stats.charCurrHP);
            if (damageDealt > 0) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(enemyTester);
                if (parentFrame != null) {
                    double charX = characterDisplay.character.getX();
                    double charY = characterDisplay.character.getY();
                    new DamagePopup(parentFrame, charX + 10, charY - 20, damageDealt, isCrit);
                }
            }

            // apply knockback to the character that has just been damaged in the opposite direction it's facing
            int kbDirection = (characterDisplay.character.getX() < enemyTester.getX()) ? -1 : 1;
            characterMovement.applyKnockback(kbDirection);
        }
    }
} 