import javax.swing.*;
import java.awt.*;


public class Main {
    static JFrame f = new JFrame("Main Menu");
    static Dimension r = f.getSize();

    public static void main (String [] args) {

        Container c = f.getContentPane();
        c.setLayout(new BorderLayout());  // set Layout to Border



        Menu men = new Menu(r);
        f.getContentPane().add(men, BorderLayout.CENTER );
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // make frame closed when x button is pressed
        f.setVisible(true);     // make the frame visible
        f.pack();
        f.setLocationRelativeTo(null);
        f.setSize(1280, 845);





    }
}

