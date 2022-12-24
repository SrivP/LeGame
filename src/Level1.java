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
        player= new Player (10,20);  // new player at the position 10,20

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
        player.move();// timer events
        repaint();
    }





    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            player.up();
            System.out.println("up");
        }

        else if (code == KeyEvent.VK_DOWN) {
            player.down();
            System.out.println("down");
        }

        else if (code == KeyEvent.VK_LEFT) {
            player.left();
            System.out.println("left");
            right = false;
        } else if (code == KeyEvent.VK_RIGHT) {
            player.right();
            System.out.println("right");
            right = true;
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
    private double velx;
    private double vely;
    boolean right;
    boolean left;
    boolean stay;

    private BufferedImage playerImg = null;


    public Player(int x,int y){
        this.x = x;
        this.y = y;
        double velx = 0;
        double vely = 0;

    }

    public double getVelx() {
        return velx;
    }

    public double getVely() {
        return vely;
    }

    public void velreset() {
        velx = 0;
        vely = 0;
        right = false;
        left = false;
        stay = true;
    }

    public int getX(){
        return x;
    }
    public void move(){
        x+=velx;
        y += vely;
    }


    public void up() {
        velx = 0;
        vely = -100.5;
        right = false;
        left = false;
        stay = true;
    }

    public void down() {
        velx = 0;
        vely = 100.5;
        right = false;
        left = false;
        stay = true;

    }

    public void right() {
        velx = 100.5;
        vely = 0;
        right = true;
        left = false;
        stay = false;
    }
    public void left() {
        velx = -100.5;
        vely = 0;
        right = false;
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
        }

        g.drawImage(playerImg,x,y,null);
    }
}
/////////////////////////////////////////////////////////////////////////////



class MyImages{
    private static BufferedImage spriteImg, basicImg;
    private static BufferedImage[] right;
    private static BufferedImage[] left;

    private static int cnt=0;

    public static void loadImages(){
        try {
            spriteImg = ImageIO.read(new File("Mc2.png"));
        }
        catch(Exception e) {
            System.out.print("Error" + e);
        }
        basicImg=spriteImg.getSubimage(79,759,67,81);

        right= new BufferedImage[3];
        left = new BufferedImage[3];

        right[0]= spriteImg.getSubimage(83,80,63,81);
        right[1]= spriteImg.getSubimage(323,82,63,81);
        right[2]= spriteImg.getSubimage(567,82,63,81);
        left[0] = spriteImg.getSubimage(2250,433,64,80);
        left[1] = spriteImg.getSubimage(2007,434,64,80);
        left[2] = spriteImg.getSubimage(1763,435,64,80);

    }

    public static BufferedImage getNextRight(){
        cnt=(cnt+1) % right.length;
        return right[cnt];
    }

    public static BufferedImage getNextLeft(){
        cnt=(cnt+1) % left.length;
        return left[cnt];
    }
    public static BufferedImage getBasicImg(){
        return basicImg;
    }

}