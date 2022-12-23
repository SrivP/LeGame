import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpritePhysicsImp2 extends JPanel implements KeyListener {
    private static final int SPRITE_WIDTH = 50;  // Sprite width in pixels
    private static final int SPRITE_HEIGHT = 50;  // Sprite height in pixels
    private static final int ENEMY_WIDTH = 50;  // Enemy width in pixels
    private static final int ENEMY_HEIGHT = 50;  // Enemy height in pixels
    private static final int GRAVITY = 1;  // Gravity in pixels per frame
    private static final int JUMP_VELOCITY = -20;  // Jump velocity in pixels per frame
    private static final int MOVE_VELOCITY = 5;  // Move velocity in pixels per frame

    private static final int ENEMY_MOVE_VELOCITY = 3;  // Enemy move velocity in pixels per frame
    private static final int PROJECTILE_WIDTH = 10;  // Projectile width in pixels
    private static final int PROJECTILE_HEIGHT = 10;  // Projectile height in pixels
    private static final int PROJECTILE_VELOCITY = 6;  // Projectile velocity in pixels per frame
    private static final int PROJECTILE_SHOOT_CHANCE = 1;  // Chance for enemy to shoot a projectile (out of 100)
    private long lastJumpTime;
    private Random random;  // Random number generator

    private List<Projectile> projectiles;  // List of projectiles

    private int x;  // Sprite x-coordinate
    private int y;  // Sprite y-coordinate
    private int vy;  // Sprite y-velocity
    private int vx;  // Sprite x-velocity
    private int health;  // Sprite health
    private JProgressBar healthBar;  // Sprite health bar

    private int enemyX;  // Enemy x-coordinate
    private int enemyY;  // Enemy y-coordinate
    private int enemyVx;  // Enemy x-velocity
    private int enemyVy;  // Enemy y-velocity

    public SpritePhysicsImp2() {
        projectiles = new ArrayList<>();
        random = new Random();
        lastJumpTime = 0;
        x = 0;
        y = 0;
        vy = 0;
        vx = 0;
        health = 100;
        healthBar = new JProgressBar(0, 100);
        healthBar.setValue(health);
        add(healthBar);

        enemyX = 300;
        enemyY = 0;
        enemyVx = 0;
        enemyVy = 0;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
        g.fillRect(x, y, SPRITE_WIDTH, SPRITE_HEIGHT);
        g.setColor(Color.RED);
        g.fillRect(enemyX, enemyY, ENEMY_WIDTH, ENEMY_HEIGHT);
        // Draw all projectiles
        g.setColor(Color.BLACK);
        for (Projectile projectile : projectiles) {
            g.fillRect(projectile.getX(), projectile.getY(), PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                // Check if it has been at least one second since the last jump
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastJumpTime >= 1000) {
                    // Jump (nerfed so you cant hold it)
                    vy = JUMP_VELOCITY;
                    lastJumpTime = currentTime;
                }
                break;
            case KeyEvent.VK_LEFT:
                // Move left
                vx = -MOVE_VELOCITY;
                break;
            case KeyEvent.VK_RIGHT:
                // Move right
                vx = MOVE_VELOCITY;
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {

            case KeyEvent.VK_LEFT:
                // Stop moving left
                if (vx < 0) {
                    vx = 0;
                }
                break;
            case KeyEvent.VK_RIGHT:
                // Stop moving right
                if (vx > 0) {
                    vx = 0;
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    public void update() {
        // Update sprite position
        x += vx;
        y += vy;
        vy += GRAVITY;

        // Check for collision with the ground
        if (y + SPRITE_HEIGHT > getHeight()) {
            y = getHeight() - SPRITE_HEIGHT;
            vy = 0;
        }

        // Prevent sprite from going out of the frame
        if (x < 0) {
            x = 0;
        } else if (x + SPRITE_WIDTH > getWidth()) {
            x = getWidth() - SPRITE_WIDTH;
        }
        if (y < 0) {
            y = 0;
        } else if (y + SPRITE_HEIGHT > getHeight()) {
            y = getHeight() - SPRITE_HEIGHT;
        }

        // Update enemy position
        if (x < enemyX) {
            enemyVx = -ENEMY_MOVE_VELOCITY;
        } else if (x > enemyX) {
            enemyVx = ENEMY_MOVE_VELOCITY;
        }
        if (y < enemyY) {
            enemyVy = -ENEMY_MOVE_VELOCITY;
        } else if (y > enemyY) {
            enemyVy = ENEMY_MOVE_VELOCITY;
        }
        enemyX += enemyVx;
        enemyY += enemyVy;

        if (random.nextInt(100) < PROJECTILE_SHOOT_CHANCE) {
            shootProjectile();
        }

        // Check for collision with enemy
        if (x < enemyX + ENEMY_WIDTH && x + SPRITE_WIDTH > enemyX &&
                y < enemyY + ENEMY_HEIGHT && y + SPRITE_HEIGHT > enemyY) {
            health -= 1;
            healthBar.setValue(health);
        }
        for (Projectile projectile : projectiles) {
            projectile.update();
        }
        // Check for collisions between projectiles and sprite
        Rectangle spriteRect = new Rectangle(x, y, SPRITE_WIDTH, SPRITE_HEIGHT);
        List<Projectile> toRemove = new ArrayList<>();  // List of projectiles to remove
        for (Projectile projectile : projectiles) {
            Rectangle projectileRect = new Rectangle(projectile.getX(),projectile.getY(), PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
            if (spriteRect.intersects(projectileRect)) {
// Collision detected, remove projectile and decrease sprite health
                toRemove.add(projectile);
                health -= 10;
                healthBar.setValue(health);
            }
        }
// Remove all projectiles in the toRemove list
        projectiles.removeAll(toRemove);
    }

    public void shootProjectile() {
        // Calculate x and y distances between enemy and player
        int dx = x - enemyX;
        int dy = y - enemyY;
        // Calculate the angle between the enemy and player
        double angle = Math.atan2(dy, dx);
        // Calculate x and y velocities based on the angle and the projectile velocity
        int vx = (int) (PROJECTILE_VELOCITY * Math.cos(angle));
        int vy = (int) (PROJECTILE_VELOCITY * Math.sin(angle));
        // Create a new projectile and add it to the list
        Projectile projectile = new Projectile(enemyX, enemyY, vx, vy);
        projectiles.add(projectile);
    }

    // Projectile class
    private class Projectile {
        private int x;  // Projectile x-coordinate
        private int y;  // Projectile y-coordinate
        private int vx;  // Projectile x-velocity
        private int vy;  // Projectile y-velocity

        public Projectile(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void update() {
            // Update projectile position
            x += vx;
            y += vy;
        }
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Sprite Movement Example");
        SpritePhysicsImp2 panel = new SpritePhysicsImp2();
        frame.add(panel);
        frame.setSize(1280, 720);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(panel);

        // Game loop
        while (true) {
            panel.update();
            panel.repaint();
            try {
                Thread.sleep(1000 / 60);  // 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}