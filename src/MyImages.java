import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MyImages{
    private static BufferedImage spriteImg, basicImg;
    private static BufferedImage pright;
    private static BufferedImage pleft;
    private static BufferedImage pjump;


    static ImageIcon b2 = new ImageIcon("Boss1.gif");
    static ImageIcon run = new ImageIcon("__Run.gif");

    private static int cnt=0;

    public static void loadImages(){
        try {
            spriteImg = ImageIO.read(new File("McF.gif"));
        }
        catch(Exception e) {
            System.out.print("Error" + e);
        }
        basicImg = spriteImg.getSubimage(2,113,55,83);

        pright = spriteImg.getSubimage(1,26,55,83);
        pleft = spriteImg.getSubimage(181,31,55,83);
        pjump = spriteImg.getSubimage(89,29,55,83);


    }

    public static BufferedImage getNextRight(){
        return pright;
    }
    public static BufferedImage getNextJump(){
        return pjump;
    }

    public static BufferedImage getNextLeft(){
        return pleft;
    }
    public static BufferedImage getBasicImg(){
        return basicImg;
    }


    public static ImageIcon getbStay() {
        return b2;
    }
}
