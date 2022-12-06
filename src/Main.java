import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main (String [] args) {
        JFrame f = new JFrame();
        Container c = f.getContentPane();

        Menu men = new Menu();
        c.add(men);


        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // make frame closed when x button is pressed
        f.setVisible(true);     // make the frame visible
        f.setSize(600, 600);  // set the size of the frame





    }
}

