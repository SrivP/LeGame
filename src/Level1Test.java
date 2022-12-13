import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics;
import java.util.logging.Level;

public class Level1Test extends JPanel {
    private ImageIcon bg = new ImageIcon("bg.png");
    private Player player;
    private Timer myT;
    static boolean right = false;
    boolean left = false;
    static boolean stay = true;

    public Level1Test() {
        MyImages.loadImages();
        player= new Player (10,20);  // new player at the position 10,20

        //addKeyListener(this);
        //myT= new Timer(120,this);
        myT.start();
        setFocusable(true);

    }


}

class MyTestImages{
    private static BufferedImage spriteImg, basicImg;
    private static BufferedImage[] right;
    private static int cnt=0;

    public static void loadImages(){
        try {
            spriteImg = ImageIO.read(new File("edgelord.png"));
        }
        catch(Exception e) {
            System.out.print("Error" + e);
        }
        basicImg=spriteImg.getSubimage(5,197,61,100);

        right= new BufferedImage[3];
        BufferedImage[] left = new BufferedImage[3];

        right[1]= spriteImg.getSubimage(72,198,77,100);
        right[2]= spriteImg.getSubimage(150,198,68,102);
        right[0]= spriteImg.getSubimage(230,198,69,102);
    }

    public static BufferedImage getNextRight(){
        cnt=(cnt+1)%right.length;
        return right[cnt];
    }
    public static BufferedImage getBasicImg(){
        return basicImg;
    }

}
