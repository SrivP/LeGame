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

public class SpriteGame extends JPanel implements KeyListener {
    private static final int GRAVITY = 1;  // Gravity in pixels per frame
    private static final int JUMP_VELOCITY = -20;  // Jump velocity in pixels per frame
    private static final int MOVE_VELOCITY = 5;  // Move velocity in pixels per frame

    private static final int ENEMY_MOVE_VELOCITY = 3;  // Enemy move velocity in pixels per frame
    private static final int PROJECTILE_WIDTH = 10;  // Projectile width in pixels
    private static final int PROJECTILE_HEIGHT = 10;  // Projectile height in pixels
    private static final int PROJECTILE_VELOCITY = 6;  // Projectile velocity in pixels per frame
    private static final int PROJECTILE_SHOOT_CHANCE = 1;  // Chance for enemy to shoot a projectile (out of 100)

    private List<Projectile2> projectiles;  // List of projectiles

    private Player2 sprite;  // Player sprite
    private Enemy1 enemy;  // Enemy sprite
    private Random random;  // Random number generator

    public SpriteGame() {
        projectiles = new ArrayList<>();
        sprite = new Player2(0, 0, 100);
        enemy = new Enemy1(300, 0, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
        g.fillRect(sprite.getX(), sprite.getY(), sprite.WIDTH, sprite.HEIGHT);
        g.setColor(Color.RED);
        g.fillRect(enemy.getX(), enemy.getY(), enemy.WIDTH, enemy.HEIGHT);
        // Draw all projectiles
        g.setColor(Color.BLACK);
        for (Projectile2 projectile : projectiles) {
            g.fillRect(projectile.getX(), projectile.getY(), PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                // Check if it has been at least one second since the
                long currentTime = System.currentTimeMillis();
                if (currentTime - sprite.getLastJumpTime() > 500) {
                    sprite.setVy(JUMP_VELOCITY);
                    sprite.setLastJumpTime(currentTime);
                }
                break;
            case KeyEvent.VK_LEFT:
                sprite.setVx(-MOVE_VELOCITY);
                break;
            case KeyEvent.VK_RIGHT:
                sprite.setVx(MOVE_VELOCITY);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                sprite.setVx(0);
                break;
            case KeyEvent.VK_RIGHT:
                sprite.setVx(0);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void update() {
        // Update sprite position
        sprite.setVy(sprite.getVy() + GRAVITY);
        sprite.setX(sprite.getX() + sprite.getVx());
        sprite.setY(sprite.getY() + sprite.getVy());
        // Check if sprite has landed on the ground
        if (sprite.getY() + sprite.HEIGHT > getHeight()) {
            sprite.setY(getHeight() - sprite.HEIGHT);
            sprite.setVy(0);
        }
        // Update enemy position
        enemy.setVx(enemy.getVx() + ENEMY_MOVE_VELOCITY);
        enemy.setX(enemy.getX() + enemy.getVx());
        // Check if enemy has reached the edge of the screen
        if (enemy.getX() + enemy.WIDTH > getWidth() || enemy.getX() < 0) {
            enemy.setVx(-enemy.getVx());
        }
        // Check if enemy should shoot a projectile
        if (random.nextInt(100) < PROJECTILE_SHOOT_CHANCE) {
            int projectileX = enemy.getX() + enemy.WIDTH / 2 - PROJECTILE_WIDTH / 2;
            int projectileY = enemy.getY() + enemy.HEIGHT;
            Projectile2 projectile = new Projectile2(projectileX, projectileY, PROJECTILE_VELOCITY);
            projectiles.add(projectile);
        }
        // Update projectile positions
        for (Projectile2 projectile : projectiles) {
            projectile.setY(projectile.getY() + projectile.getVy());
        }
        // Check for collision between sprite and enemy
        Rectangle spriteRect = new Rectangle(sprite.getX(), sprite.getY(), sprite.WIDTH, sprite.HEIGHT);
        Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), enemy.WIDTH, enemy.HEIGHT);
        if (spriteRect.intersects(enemyRect)) {
            sprite.setHealth(sprite.getHealth() - 10);
            sprite.getHealthBar().setValue(sprite.getHealth());
        }
        // Check for collision between sprite and projectiles
        for (Projectile2 projectile : projectiles) {
            Rectangle projectileRect = new Rectangle(projectile.getX(), projectile.getY(), PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
            if (spriteRect.intersects(projectileRect)) {
                sprite.setHealth(sprite.getHealth() - 10);
                sprite.getHealthBar().setValue(sprite.getHealth());
                projectile.setVy(0);
            }
        }
        // Remove off-screen projectiles
        projectiles.removeIf(projectile -> projectile.getY() > getHeight());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sprite Physics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SpritePhysicsImp2 panel = new SpritePhysicsImp2();
        frame.add(panel);
        frame.addKeyListener(panel);
        frame.setSize(1280, 720);
        frame.setVisible(true);

        while (true) {
            panel.update();
            panel.repaint();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {}
        }
    }
}

class Player2 {
    public final int WIDTH = 50;  // Sprite width in pixels
    public final int HEIGHT = 50;  // Sprite height in pixels
    private int x;  // Player x-coordinate
    private int y;  // Player y-coordinate
    private int vy;  // Player y-velocity
    private int vx;  // Player x-velocity
    private int health;  // Player health
    private JProgressBar healthBar;  // Player health bar
    private long lastJumpTime;  // Timestamp of last jump
    private Random random;  // Random number generator

    public Player2(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.healthBar = new JProgressBar(0, 100);
        this.healthBar.setValue(health);
        this.lastJumpTime = 0;
        this.random = new Random();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public JProgressBar getHealthBar() {
        return healthBar;
    }

    public long getLastJumpTime() {
        return lastJumpTime;
    }

    public void setLastJumpTime(long lastJumpTime) {
        this.lastJumpTime = lastJumpTime;
    }

    public Random getRandom() {
        return random;
    }
}

class Enemy1 extends Player2 {
    public final int WIDTH = 50;  // Enemy width in pixels
    public final int HEIGHT = 50;  // Enemy height in pixels
    public Enemy1(int x, int y, int health) {
        super(x, y, health);
    }
}

class Projectile2 {
    private int x;  // Projectile x-coordinate
    private int y;  // Projectile y-coordinate
    private int vy;  // Projectile y-velocity

    public Projectile2(int x, int y, int vy) {
        this.x = x;
        this.y = y;
        this.vy = vy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVy() {
        return vy;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
}


