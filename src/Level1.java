import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics;
import java.util.logging.Level;

public class Level1 extends JPanel implements ActionListener, KeyListener{
    private ImageIcon bg = new ImageIcon("bg.png");
    private Player player;
    private Timer myT;
    static boolean right = false;
    boolean left = false;
    static boolean stay = true;

    public Level1() {
        MyImages.loadImages();
        player= new Player (1270,520);  // new player at the position 10,20

        addKeyListener(this);
        myT= new Timer(120,this);
        myT.start();
        setFocusable(true);

    }




    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        bg.paintIcon(this, g, 0, 0);
        player.myDraw(g);

    }

    public void actionPerformed(ActionEvent e) {
        player.velreset();// timer events
        repaint();
    }





    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            player.up();
            System.out.println("up");
        }
        else if (code == KeyEvent.VK_LEFT) {
            player.left();
            System.out.println("left");
        } else if (code == KeyEvent.VK_RIGHT) {
            player.right();
            System.out.println("right");
        }
        else {
            System.out.println("Yifan");
        }

        repaint();
    }

    public void keyReleased(KeyEvent e) {
        player.velreset();
    }

    public void mousePressed( MouseEvent e ){   }
    public void mouseReleased( MouseEvent e ){   }
    public void mouseEntered( MouseEvent e ) {   }
    public void mouseExited( MouseEvent e )  {   }



    public void keyTyped(KeyEvent e) {}




}

class Player {
    private int x, y;
    private static final int GRAVITY = 15;  // Gravity in pixels per frame
    private static final int JUMP_VELOCITY = -30;  // Jump velocity in pixels per frame
    private static final int MOVE_VELOCITY = 100;  // Move velocity in pixels per frame
    private double vx;
    private double vy;
    boolean right;
    boolean left;
    boolean stay;
    boolean jump;

    private BufferedImage playerImg = null;


    public Player(int x,int y){
        this.x = x;
        this.y = y;
        double vx = 0;
        double vy = 0;

    }


    public void velreset() {
        x += vx;
        y += vy;
        vx = 0;
        vy = 0;
        right = false;
        left = false;
        stay = true;
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
        if(stay)
            playerImg = MyImages.getBasicImg();
        else if(right)
            playerImg=	MyImages.getNextRight();
        else if (left) {
            playerImg = MyImages.getNextLeft();
        } else if (jump) {
            playerImg = MyImages.getNextJump();
        }

        g.drawImage(playerImg,x,y,null);
    }
}
/////////////////////////////////////////////////////////////////////////////



class MyImages{
    private static BufferedImage spriteImg, basicImg;
    private static BufferedImage[] right;
    private static BufferedImage[] left;
    private static BufferedImage[] jump;

    private static int cnt=0;

    public static void loadImages(){
        try {
            spriteImg = ImageIO.read(new File("Mc2.png"));
        }
        catch(Exception e) {
            System.out.print("Error" + e);
        }
        basicImg=spriteImg.getSubimage(569,431,45,83);

        right= new BufferedImage[3];
        left = new BufferedImage[3];
        jump = new BufferedImage[3];

        right[0]= spriteImg.getSubimage(83,80,63,81);
        right[1]= spriteImg.getSubimage(323,82,63,81);
        right[2]= spriteImg.getSubimage(567,82,63,81);
        left[0] = spriteImg.getSubimage(2250,433,64,80);
        left[1] = spriteImg.getSubimage(2007,434,64,80);
        left[2] = spriteImg.getSubimage(1763,435,64,80);
        jump[0] = spriteImg.getSubimage(86,589,55,84);
        jump[1] = spriteImg.getSubimage(327,591,55,84);
        jump[2] = spriteImg.getSubimage(572,594,55,84);
    }

    public static BufferedImage getNextRight(){
        cnt=(cnt+1) % right.length;
        return right[cnt];
    }
    public static BufferedImage getNextJump(){
        cnt=(cnt+1) % jump.length;
        return jump[cnt];
    }

    public static BufferedImage getNextLeft(){
        cnt=(cnt+1) % left.length;
        return left[cnt];
    }
    public static BufferedImage getBasicImg(){
        return basicImg;
    }

}