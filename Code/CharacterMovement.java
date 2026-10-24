package Code;

import java.awt.*;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Code.UICode.DashLogic;

public class CharacterMovement {
    
    // Direction
    public boolean facingLeft = false;
    public int activeDirection = 0; // -1 Left, 1 Right, 0 None (Neutral)

    // Position
    public double startPosX = 100, startPosY = 800;
    public double currPosX = 100, currPosY = 800;

    // Velocity (Speed)
    public double velX = 0.0, velY = 0.0;

    // Constants
    public final double ACCEL = 0.7;
    public final double GRAVITY = 1.5;
    public final double JUMP_F = -20.0;
    private static final int DASH_DURATION_MS = 160;

    // Bool
    public boolean mergeDashOverride = false;
    public boolean isMovingRight = false;
    public boolean isMovingLeft = false;
    public boolean isGrounded = false;
    public boolean isSpacePressed = false;
    private boolean isShiftPressed = false;
    
    // Other
    private CharacterDisplay stats;
    private CharacterAttributes charAttStats;
    private DashLogic dashLogic;
    public Damage damageSystem;

    public int knockbackFrames = 0;

    private JFrame frame;
    
    public CharacterMovement(JFrame frame, CharacterDisplay stats, CharacterAttributes charAttStats) {

        // set up classes and call function
        this.frame = frame;
        this.stats = stats;
        this.charAttStats = charAttStats;

        setupKeyboardInput(frame);
    }

    public void setDashLogic(DashLogic dashLogic) { // get the dashLogic to check when it happens
        this.dashLogic = dashLogic;
    }

    public void applyKnockback(int direction) { // apply knockback in the opposite direction character is facing
        velX = direction * 12.0;
        velY = -8.0;
        isGrounded = false;
        knockbackFrames = 12;
    }

    private void setupKeyboardInput(JFrame frame) { // set up the keyboard inputs

        KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager(); // set keyboard focus manager
        KeyEventDispatcher dispatcher = e -> {
            if (getEventWindow(e) != frame) {
                return false;
            }

            if (e.getID() == KeyEvent.KEY_PRESSED) { // check if key is pressed
                switch (e.getKeyCode()) { // switch for the keycode
                    case KeyEvent.VK_SHIFT: // if the KeyCode is shift, dash
                        if (!isShiftPressed) {
                            isShiftPressed = true;
                            if (dashLogic != null) {
                                dashLogic.triggerDashInput(); // call dash
                            }
                        }
                        break;
                    case KeyEvent.VK_SPACE: // if KeyCode is space, jump
                        isSpacePressed = true; // boolean set
                        break;
                    case KeyEvent.VK_LEFT: // if KeyCode is left arrow, move left
                        isMovingLeft = true; // boolean set
                        updateActiveDirection(-1); // direction facing set
                        break;
                    case KeyEvent.VK_A: // left arrow alternative
                        isMovingLeft = true;
                        updateActiveDirection(-1);
                        break;
                    case KeyEvent.VK_RIGHT: // if KeyCode is right arrow, move right
                        isMovingRight = true; // boolean set
                        updateActiveDirection(1); // direction facing set
                        break;
                    case KeyEvent.VK_D: // right arrow alternative
                        isMovingRight = true;
                        updateActiveDirection(1);
                        break;
                    default:
                        break;
                }
            } else if (e.getID() == KeyEvent.KEY_RELEASED) { // check if key released (same as above, just checking when to stop output)
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SHIFT:
                        isShiftPressed = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        isSpacePressed = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        isMovingLeft = false;
                        updateActiveDirection(isMovingRight ? 1 : 0);
                        break;
                    case KeyEvent.VK_A:
                        isMovingLeft = false;
                        updateActiveDirection(isMovingRight ? 1 : 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        isMovingRight = false;
                        updateActiveDirection(isMovingLeft ? -1 : 0);
                        break;
                    case KeyEvent.VK_D:
                        isMovingRight = false;
                        updateActiveDirection(isMovingRight ? -1 : 0);
                        break;
                    default:
                        break;
                }
            }

            return false;
        };

        focusManager.addKeyEventDispatcher(dispatcher); // basic boolean and direction settings (reset)
        frame.addWindowFocusListener(new WindowAdapter() {
            @Override 
            public void windowLostFocus(WindowEvent e) {
                isMovingLeft = false;
                isMovingRight = false;
                activeDirection = 0;
                isSpacePressed = false;
                isShiftPressed = false;

                if (!mergeDashOverride) {
                    velX = 0;
                }
            }

            @Override 
            public void windowClosed(WindowEvent e) { // reset when app closes
                focusManager.removeKeyEventDispatcher(dispatcher);
            }
        });
    }

    private Window getEventWindow(KeyEvent e) { // get event window

        if (!(e.getSource() instanceof Component)) {
            return null;
        }
        Component source = (Component) e.getSource();
        return source instanceof Window ? (Window) source : SwingUtilities.getWindowAncestor(source);
    }
    
    private void updateActiveDirection(int direction) { // update the active direction
        activeDirection = direction;
        if (direction != 0) { // if direction is 0 (neutral)
            facingLeft = direction < 0;
            stats.changeAppearance();
        }
    }

    public void updateFrame() { // update function for CharacterMovement exclusively; gets called seperately later on in Main.java

        if (isSpacePressed && isGrounded) { // if space is pressed and character is on the ground
            jump(); // call jump (just jump)
        }

        if (!mergeDashOverride) { // dash
            if (knockbackFrames > 0) { // if in knockback
                knockbackFrames--;
            } else { // otherwise
                if (activeDirection != 0) { // if the active direction is not neutral
                    velX += activeDirection * ACCEL; // quickly shift towards the facing direction
                } else { // if not set velocity to 0
                    velX = 0;
                }

                if (velX > charAttStats.maxSpd) velX = charAttStats.maxSpd; // cap the speed to maxSpeed for going right
                if (velX < -charAttStats.maxSpd) velX = -charAttStats.maxSpd; // cap the speed to maxSpeed for going left
            }
        }

        if (!isGrounded) { // if character not on ground (currently jumping)
            velY += GRAVITY; // make character fall by adding the pulling force onto the character's Y position
        }

        currPosX += velX; // set the current position
        currPosY += velY; // set the current position

        if (currPosY >= 800) { // set floor level
            currPosY = 800;
            velY = 0;
            isGrounded = true;
        } else {
            isGrounded = false;
        }

        stats.character.setLocation((int)currPosX, (int)currPosY); // set the character location

        if (frame != null) { // if frame is existent
            frame.repaint(); // redraw (update)
        }
    }

    public void updateFieldAndCollision() { // check for collision
        updateFrame();

        if (damageSystem != null) {
            damageSystem.checkCollision();
        }
    }

    public void setDamageSystem(Damage damageSystem) { // set the damage system
        this.damageSystem = damageSystem;
    }

    public void startDash(int direction) { // start the dash and the dash timer
        mergeDashOverride = true; // check if dashing
        velX = direction * charAttStats.maxSpd * 2.5; // the character's X coordinate

        charAttStats.isUntargetable = true; // make the character untargetable

        Timer dashTimer = new Timer(DASH_DURATION_MS, e -> { // check when dash starts
            ((Timer) e.getSource()).stop();

            charAttStats.isUntargetable = false; // when dashTimer finishes and character is out of dash, reset untargetable

            mergeDashOverride = false;
            velX = activeDirection == 0 ? 0 : activeDirection * charAttStats.maxSpd;
        });
        dashTimer.setRepeats(false);
        dashTimer.start();
    }

    public void jump() { // jump by the max jump force of a character
        if (isGrounded) {
            velY = JUMP_F;
            isGrounded = false;
        }
    }
}