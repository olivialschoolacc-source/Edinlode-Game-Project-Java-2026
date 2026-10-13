package Code;

import java.awt.*;
import javax.swing.*;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
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

    private JFrame frame;
    
    public CharacterMovement(JFrame frame, CharacterDisplay stats, CharacterAttributes charAttStats) {

        this.frame = frame;
        this.stats = stats;
        this.charAttStats = charAttStats;

        setupKeyboardInput(frame);

        Timer gameloop = new Timer(16, e -> updateFieldAndCollision());
        gameloop.start();
    }

    public void setDashLogic(DashLogic dashLogic) {
        this.dashLogic = dashLogic;
    }

    private void setupKeyboardInput(JFrame frame) {

        KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        KeyEventDispatcher dispatcher = e -> {
            if (getEventWindow(e) != frame) {
                return false;
            }

            if (e.getID() == KeyEvent.KEY_PRESSED) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SHIFT:
                        if (!isShiftPressed) {
                            isShiftPressed = true;
                            if (dashLogic != null) {
                                dashLogic.triggerDashInput();
                            }
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        isSpacePressed = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        isMovingLeft = true;
                        updateActiveDirection(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        isMovingRight = true;
                        updateActiveDirection(1);
                        break;
                    default:
                        break;
                }
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SHIFT:
                        isShiftPressed = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        isSpacePressed = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        isMovingLeft = false;
                        updateActiveDirection(isMovingRight ? 1: 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        isMovingRight = false;
                        updateActiveDirection(isMovingLeft ? -1 : 0);
                        break;
                    default:
                        break;
                }
            }

            return false;
        };

        focusManager.addKeyEventDispatcher(dispatcher);
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
            public void windowClosed(WindowEvent e) {
                focusManager.removeKeyEventDispatcher(dispatcher);
            }
        });
    }

    private Window getEventWindow(KeyEvent e) {

        if (!(e.getSource() instanceof Component)) {
            return null;
        }
        Component source = (Component) e.getSource();
        return source instanceof Window ? (Window) source : SwingUtilities.getWindowAncestor(source);
    }
    
    private void updateActiveDirection(int direction) {
        activeDirection = direction;
        if (direction != 0) {
            facingLeft = direction < 0;
            stats.changeAppearance();
        }
    }

    public void updateFrame() {

        if (isSpacePressed && isGrounded) {
            jump();
        }

        if (!mergeDashOverride) {
            if (activeDirection != 0) {
                velX += activeDirection * ACCEL;
            } else {
                velX = 0;
            }

            if (velX > charAttStats.maxSpd) velX = charAttStats.maxSpd;
            if (velX < -charAttStats.maxSpd) velX = -charAttStats.maxSpd;
        }

        if (!isGrounded) {
            velY += GRAVITY;
        }

        currPosX += velX;
        currPosY += velY;

        if (currPosY >= 800) {
            currPosY = 800;
            velY = 0;
            isGrounded = true;
        } else {
            isGrounded = false;
        }

        stats.character.setLocation((int)currPosX, (int)currPosY);

        if (frame != null) {
            frame.repaint();
        }
    }

    public void updateFieldAndCollision() {
        updateFrame();

        if (damageSystem != null) {
            damageSystem.checkCollision();
        }
    }

    public void setDamageSystem(Damage damageSystem) {
        this.damageSystem = damageSystem;
    }

    public void startDash(int direction) {
        mergeDashOverride = true;
        velX = direction * charAttStats.maxSpd * 2.5;

        Timer dashTimer = new Timer(DASH_DURATION_MS, e -> {
            ((Timer) e.getSource()).stop();
            mergeDashOverride = false;
            velX = activeDirection == 0 ? 0 : activeDirection * charAttStats.maxSpd;
        });
        dashTimer.setRepeats(false);
        dashTimer.start();
    }

    public void jump() {
        if (isGrounded) {
            velY = JUMP_F;
            isGrounded = false;
        }
    }
}
