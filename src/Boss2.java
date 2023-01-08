import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Boss2 extends Player {
    private static final int GRAVITY = 15;  // Gravity in pixels per frame
    private static final int JUMP_VELOCITY = -30;  // Jump velocity in pixels per frame
    private static final int MOVE_VELOCITY = 100;  // Move velocity in pixels per frame
    private double vx;
    private double vy;
    protected ArrayList<Projectile> projectiles;
    ImageIcon b2Img = null;

    public Boss2(int x,int y){
        super(x,y);
        projectiles = new ArrayList<>();
        double vx = 0;
        double vy = 0;
        System.out.println(x + " by:" + y);


    }



    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void fireProjectile() {
        Projectile projectile = new Projectile(x, y);
        projectiles.add(projectile);
    }

    public void follow(Player player) {

        // Calculate the distance between the enemy and player in the x and y directions
        int dx = player.getX() - getX();
        int dy = player.getY() - getY();

        // Set the velocity in the x and y directions to move the enemy towards the player
        vx = dx / 10;
        vy = dy / 10;
    }
    public void move () {
        for (int i = 0; i <= cont; i++) {
            Timer k = new Timer(10000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    x += vx;
                    y += vy;
                }
            });
            k.setRepeats(false);
            k.start();


        }
    }





    public void myDraw(Graphics g){
        b2Img = MyImages.getbStay();
        g.drawImage(b2Img.getImage(),getX(),getY(),null);
    }




}
