import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {
    public static void main(String [] args) throws MalformedURLException {
        Icon icon = new ImageIcon("Menu.gif");
        JLabel label = new JLabel(icon);

        JFrame f = new JFrame("Animation");
        f.getContentPane().add(label);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
