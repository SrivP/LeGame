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
        myT= new Timer(250,this);
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


