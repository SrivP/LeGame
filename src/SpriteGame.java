import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;


public class SpriteGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Sprite player = new Sprite();
        Enemy enemy = new Enemy();
        Timer timer = new Timer(50, null);

        // Add key listener to move the player sprite
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.moveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    player.moveUp();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player.moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Fire a projectile when the enter key is pressed
                    player.fireProjectile();
                }
            }
        });

        // Add a timer to update the game state every 50 milliseconds
        timer.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make the enemy follow the player
                enemy.follow(player);
                // Update the positions of the sprites based on their velocities
                if (!player.isFrozen()) {
                    player.updatePosition();
                }
                enemy.updatePosition();

                // Update the positions of the projectiles based on their velocities
                for (Projectile projectile : player.getProjectiles()) {
                    if (projectile.isActive()) {
                        projectile.setX(projectile.getX() + projectile.getVX());
                        projectile.setY(projectile.getY() + projectile.getVY());
                    }
                }

                // Check for collisions
                if (player.collidesWith(enemy)) {
                    player.freeze(500);
                    player.inflictDamage(1);
                    player.getHealthBar().setValue(player.getHealth());
                }
                for (Projectile projectile : player.getProjectiles()) {
                    if (projectile.collidesWith(enemy)) {
                        projectile.setActive(false);
                        enemy.inflictDamage(10);
                        enemy.getHealthBar().setValue(enemy.getHealth());
                    }
                }

                // Check if the player or enemy has been defeated
                if (player.getHealth() <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(frame, "Enemy wins!");
                    frame.dispose();
                } else if (enemy.getHealth() <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(frame, "Player wins!");
                    frame.dispose();
                }

                // Repaint the frame to update the game state
                frame.repaint();
            }
        });
        timer.start();

        frame.setSize(1280, 720);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(player.getHealthBar());
        frame.add(enemy.getHealthBar());
        frame.setVisible(true);

        // Override the paintComponent method of the JFrame to draw the sprites and projectiles
        frame.add(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                player.draw(g);
                enemy.draw(g);
            }
        });
    }
}

class Sprite {
    protected int x;
    protected int y;
    protected int vx;
    protected int vy;
    protected int width;
    protected int height;
    protected Image image;
    protected ArrayList<Projectile> projectiles;
    private int health;
    private JProgressBar healthBar;
    private long freezeStartTime;
    private long freezeDuration;

    public Sprite() {
        x = 0;
        y = 0;
        vx = 0;
        vy = 0;
        width = 0;
        height = 0;
        image = null;
        projectiles = new ArrayList<>();
        health = 100;
        healthBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        freezeStartTime = 0;
        freezeDuration = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVX() {
        return vx;
    }

    public int getVY() {
        return vy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public int getHealth() {
        return health;
    }

    public JProgressBar getHealthBar() {
        return healthBar;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVX(int vx) {
        this.vx = vx;
    }

    public void setVY(int vy) {
        this.vy = vy;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void moveLeft() {
        vx = -5;
    }

    public void moveRight() {
        vx = 5;
    }

    public void moveUp() {
        vy = -5;
    }

    public void moveDown() {
        vy = 5;
    }

    public void fireProjectile() {
        Projectile projectile = new Projectile(x, y);
        projectiles.add(projectile);
    }

    public void updatePosition() {
        x += vx;
        y += vy;
        vx = 0;
        vy = 0;
    }

    public boolean collidesWith(Sprite other) {
        if (x + width > other.getX() && x < other.getX() + other.getWidth() &&
                y + height > other.getY() && y < other.getY() + other.getHeight()) {
            return true;
        }
        return false;
    }

    public void inflictDamage(int damage) {
        health -= damage;
    }

    public void freeze(long duration) {
        freezeStartTime = System.currentTimeMillis();
        freezeDuration = duration;
    }

    public boolean isFrozen() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - freezeStartTime < freezeDuration) {
            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
        for (Projectile projectile : projectiles) {
            projectile.paintComponent(g);
        }
    }
}

class Enemy extends Sprite {
    public Enemy() {
        super();
    }

    // Makes the enemy follow the player sprite
    public void follow(Sprite player) {
        // Calculate the distance between the enemy and player in the x and y directions
        int dx = player.getX() - getX();
        int dy = player.getY() - getY();

        // Set the velocity in the x and y directions to move the enemy towards the player
        setVX(dx / 10);
        setVY(dy / 10);
    }
    // Update the position of the enemy based on its velocity
    public void updatePosition() {
        setX(getX() + getVX());
        setY(getY() + getVY());
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
        for (Projectile projectile : projectiles) {
            projectile.paintComponent(g);
        }
    }

}



// The Projectile class represents a projectile fired by the player sprite.
// It has a position, size, and velocity, and can be either active or inactive.
class Projectile {
    private int x, y, width, height, vx, vy;
    private boolean active;

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
        width = 10;
        height = 10;
        vx = 10;
        vy = 0;
        active = true;
    }

    // Returns the x position of the projectile
    public int getX() {
        return x;
    }

    // Returns the y position of the projectile
    public int getY() {
        return y;
    }

    // Returns the width of the projectile
    public int getWidth() {
        return width;
    }

    // Returns the height of the projectile
    public int getHeight() {
        return height;
    }

    // Returns the velocity in the x direction of the projectile
    public int getVX() {
        return vx;
    }

    // Returns the velocity in the y direction of the projectile
    public int getVY() {
        return vy;
    }

    // Returns true if the projectile is active, false otherwise
    public boolean isActive() {
        return active;
    }

    // Sets the x position of the projectile
    public void setX(int x) {
        this.x = x;
    }

    // Sets the y position of the projectile
    public void setY(int y) {
        this.y = y;
    }

    // Sets the width of the projectile
    public void setWidth(int width) {
        this.width = width;
    }

    // Sets the height of the projectile
    public void setHeight(int height) {
        this.height = height;
    }

    // Sets the velocity in the x direction of the projectile
    public void setVX(int vx) {
        this.vx = vx;
    }

    // Sets the velocity in the y direction of the projectile
    public void setVY(int vy) {
        this.vy = vy;
    }

    // Sets the active status of the projectile
    public void setActive(boolean active) {
        this.active = active;
    }

    // Returns true if this projectile collides with a sprite, false otherwise
    public boolean collidesWith(Sprite sprite) {
        Rectangle thisRect = new Rectangle(x, y, width, height);
        Rectangle spriteRect = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        return thisRect.intersects(spriteRect);
    }

    public void paintComponent(Graphics g) {
        if (active) {
            // Draw the projectile
            g.setColor(Color.BLACK);
            g.fillRect(x, y, width, height);
        }
    }
}