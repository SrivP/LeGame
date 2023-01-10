import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Rectangle;


import javax.swing.JProgressBar;
public class SpriteGame implements KeyListener {
    // frame and panel for the game
    private JFrame frame;
    private GamePanel panel;

    public static void main(String[] args) {
        new SpriteGame();
    }

    public SpriteGame() {
        // create the frame and panel
        frame = new JFrame();
        panel = new GamePanel();

        // add key listener to the panel
        panel.addKeyListener(this);

        // add the panel to the frame and set the frame's properties
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Java Game");
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(1280, 720);

        // start the game loop in the panel
        panel.start();
    }

    // handle key events
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                panel.getSprite().setVelocity(-5, 0);
                break;
            case KeyEvent.VK_RIGHT:
                panel.getSprite().setVelocity(5, 0);
                break;
            case KeyEvent.VK_UP:
                panel.getSprite().jump();
                break;
            case KeyEvent.VK_DOWN:
                panel.getSprite().setVelocity(0, 5);
                break;
            case KeyEvent.VK_ENTER:
                panel.addProjectile(panel.getSprite().getX(), panel.getSprite().getY());
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {

            case KeyEvent.VK_LEFT:
                // Stop moving left
                if (panel.getSprite().getDx() < 0) {
                    panel.getSprite().setDx(0);
                }
                break;
            case KeyEvent.VK_RIGHT:
                // Stop moving right
                if (panel.getSprite().getDx() > 0) {
                    panel.getSprite().setDx(0);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // show a message dialog to display the winner
    public static void showWinnerDialog(Sprite winner) {
        String message = winner instanceof Enemy ? "Enemy wins!" : "Sprite wins!";
        JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.PLAIN_MESSAGE);
    }
}



class GamePanel extends JPanel {
    // the sprite and enemy
    private Sprite sprite;
    private Enemy enemy;

    // list of projectiles
    private List<Projectile> projectiles;

    public GamePanel() {
        // create the sprite and enemy
        sprite = new Sprite(50, 50, 50, 50, 100);
        enemy = new Enemy(250, 250, 50, 50, 100, sprite);

        // create the list of projectiles
        projectiles = new ArrayList<>();

        // set focusable to true to allow key events
        setFocusable(true);
    }

    // update the game state
    public void update() {
        sprite.update();
        enemy.update();

        // update projectiles
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }

        // check for collision between sprite and enemy
        checkSpriteEnemyCollision(sprite, enemy);


        // check for collision between projectiles and enemy
        for (int i = 0; i < projectiles.size(); i++) {
            checkProjectileCollisions(enemy);
        }

        // check if either the sprite or enemy has zero health
        if (sprite.getHealth() <= 0) {
            SpriteGame.showWinnerDialog(enemy);
            System.exit(0);
        } else if (enemy.getHealth() <= 0) {
            SpriteGame.showWinnerDialog(sprite);
            System.exit(0);
        }
    }
    // check if the sprite intersects with any projectiles
    public void checkProjectileCollisions(Sprite sprite) {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            if (p.intersects(sprite.getBounds())) {
                sprite.inflictDamage(1);
                enemy.setHealth(enemy.getHealth() - 1);
                projectiles.remove(i);
            }
        }
    }
    // check if the sprite and enemy are colliding
    public void checkSpriteEnemyCollision(Sprite sprite, Enemy enemy) {
        if (sprite.intersects(enemy.getBounds())) {
            sprite.freeze(200);
            //sprite.inflictDamage(1);
            enemy.inflictDamage(1);
            sprite.setHealth(sprite.getHealth() - 1);
        }
    }



    // paint the game panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw the sprite
        sprite.draw(g);

        // draw the enemy
        enemy.draw(g);

        // draw the projectiles
        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }

    }


    // add a projectile to the list
    public void addProjectile(int x, int y) {
        projectiles.add(new Projectile(x, y));
    }

    // start the game loop
    public void start() {
        Thread thread = new Thread(() -> {
            while (true) {
                update();
                repaint();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    // getters for the sprite and enemy
    public Sprite getSprite() {
        return sprite;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}




class Sprite {
    // minimum and maximum x and y positions of the sprite
    private static final int MIN_X = 0;
    private static final int MAX_X = 800;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 600;

    // x and y position of the sprite
    protected int x;
    protected int y;

    // width and height of the sprite
    protected int width;
    protected int height;

    // velocity of the sprite in the x and y direction
    protected int dx;
    protected int dy;
    // gravitational acceleration
    protected static final int GRAVITY = 1;


    // health of the sprite
    protected int health;

    // health bar for the sprite
    protected JProgressBar healthBar;

    // time remaining until the sprite is unfrozen
    private long freezeTime;

    public Sprite(int x, int y, int width, int height, int health) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;

        // create the health bar and set its properties
        healthBar = new JProgressBar();
        healthBar.setMaximum(health);
        healthBar.setMinimum(0);
        healthBar.setValue(health);
        healthBar.setStringPainted(true);

        // set the sprite's initial velocity to zero
        dx = 0;
        dy = 0;

        // set the freeze time to zero
        freezeTime = 0;
    }

    // update the sprite's state
    public void update() {
        // check if the sprite is frozen
        if (System.currentTimeMillis() < freezeTime) {
            return;
        }

        dy += GRAVITY;

        // update the sprite's position
        x += dx;
        y += dy;

        // prevent the sprite from going out of the frame
        if (x < MIN_X) {
            x = MIN_X;
        } else if (x > MAX_X - width) {
            x = MAX_X - width;
        }
        if (y < MIN_Y) {
            y = MIN_Y;
        } else if (y > MAX_Y - height) {
            y = MAX_Y - height;
        }

        // update the health bar
        healthBar.setValue(health);
    }

    // draw the sprite
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
        healthBar.setBounds(x, y - 20, width, 20);
        healthBar.paint(g);
    }

    // set the sprite's velocity
    public void setVelocity(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // freeze the sprite for a certain amount of time
    public void freeze(int time) {
        freezeTime = System.currentTimeMillis() + time;
    }

    // inflict damage on the sprite
    public void inflictDamage(int damage) {
        health -= damage;
    }

    public void jump() {
        dy = -20;
    }

    // getters for the sprite's position, size, and health
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHealth() {
        return health;
    }

    // set the sprite's health
    public void setHealth(int health) {
        this.health = health;
        healthBar.setValue(health);
    }

    public JProgressBar getHealthBar() {
        return healthBar;
    }

    // check if the sprite intersects with another rectangle
    public boolean intersects(Rectangle r) {
        return getBounds().intersects(r);
    }

    // get the sprite's bounds
    // get the sprite's bounds
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}

class Enemy extends Sprite {
    private Sprite target;

    public Enemy(int x, int y, int width, int height, int health, Sprite target) {
        super(x, y, width, height, health);
        this.target = target;

        healthBar = new JProgressBar();
        healthBar.setMaximum(health);
        healthBar.setMinimum(0);
        healthBar.setValue(health);
        healthBar.setStringPainted(true);
    }

    // update the enemy's state
    @Override
    public void update() {

        // follow the sprite
        if (x < target.getX()) {
            dx = 1;
        } else if (x > target.getX()) {
            dx = -1;
        }
        if (y < target.getY()) {
            dy = 1;
        } else if (y > target.getY()) {
            dy = -1;
        }

        x += dx;
        y += dy;




        super.update();
        healthBar.setValue(health);

    }

    // draw the enemy
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
        healthBar.setBounds(100, y - 80, width, 20);
        healthBar.paint(g);
    }
}



    class Projectile {
    // x and y position of the projectile
    private int x;
    private int y;

    // width and height of the projectile
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // update the projectile's position
    public void update() {
        x += 5;
    }

    // draw the projectile
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    // check if the projectile intersects with another rectangle
    public boolean intersects(Rectangle r) {
        return getBounds().intersects(r);
    }

    // get the projectile's bounds
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}

