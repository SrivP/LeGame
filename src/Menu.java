import javax.swing.*;
import java.awt.*;
import java.util.EventListener;


public class Menu extends JPanel implements EventListener {
    ImageIcon imageIcon = new ImageIcon("Menu.gif");
    private JPanel word;
    private JLabel bg;
    JLabel label;
    boolean k;

    public Menu() {
        JLabel bgLabel = new JLabel();
        bgLabel.setIcon(imageIcon);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(),0,0,1280,720,null);
        g.drawRect(20,100,100,100);
    }

}

