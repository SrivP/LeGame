import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Player {
    protected int x;
    int cont = 0;
    protected int y;
    private static final int GRAVITY = 1;  // Gravity in pixels per frame
    private static final int JUMP_VELOCITY = -15;  // Jump velocity in pixels per frame
    private static final int MOVE_VELOCITY = 10;  // Move velocity in pixels per frame
    private double vx;
    private JProgressBar healthBar;

    private double vy;
    boolean right;
    private int health;
    boolean left;
    boolean stay;
    boolean jump;

    BufferedImage playerImg = null;


    public Player(int x,int y){

        this.x = x;
        this.y = y;
        double vx = 0;
        double vy = 0;
        System.out.println(x + " py: " + y);
        health = 100;
        healthBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);


    }

    public int getHealth() {
        return health;
    }

    public void inflictDamage(int damage) {
        health -= damage;
    }

    public JProgressBar getHealthBar() {
        return healthBar;
    }





    public void velreset() {
        cont++;
        x += vx;
        y += vy;


        Timer timer = new Timer(50, null);
        timer.start();
        stay = true;
        right = false;
        left = false;
        jump = false;




        // Update sprite position
        vy += GRAVITY;

        // Check for collision with the ground
        if (y + 80 > 620) {
            y = 620 - 80;
            vy = 0;
        }

        // Prevent sprite from going out of the frame
        if (x < 0) {
            x = 0;
        } else if (x + 64 > 1280) {
            x = 1280 - 64;
        }
        if (y < 0) {
            y = 0;
        }
    }




    public void up() {
        vy = JUMP_VELOCITY;
        stay = false;
        jump = true;
    }


    public void right() {
        vx = MOVE_VELOCITY;
        right = true;
        stay = false;
    }
    public void left() {
        vx = -MOVE_VELOCITY;
        left = true;
        stay = false;


    }



    public void myDraw(Graphics g){
        boolean k = false;
        if(stay) {
            k = true;
            playerImg = MyImages.getBasicImg();
        }else if (left) {
            k =true;
            playerImg = MyImages.getNextLeft();
        } else if (jump) {
            k =true;
            playerImg = MyImages.getNextJump();
        } else if(right) {
            playerImg = MyImages.getNextRight();
        }
        g.drawImage(playerImg,x,y,null);




    }

    public boolean collidesWith(Player other) {
        if (x + 55 > other.getX() && x < other.getX() + other.getWidth() &&
                y + 83 > other.getY() && y < other.getY() + other.getHeight()) {
            return true;
        }
        return false;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public int getX() {
        return x;
    }

    public void setX(int nx){
        x = nx;
    }
    public int getY() {
        return y;
    }

    public int getWidth() {
        return 55;
    }

    public int getHeight() {
        return 83;
    }
}


/////////////////////////////////////////////////////////////////////////////

