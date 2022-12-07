import javax.swing.*;
import java.awt.*;
import java.util.EventListener;


public class Menu extends JPanel implements EventListener {
    ImageIcon bg = new ImageIcon("DoJo.gif");
    private JButton b1, b2, b3;     //declare 3 buttons
    Rectangle frameSize;
    JLabel l;

    public Menu(Dimension r) {
        l = new JLabel();
        b1= new JButton("some button");
        b2= new JButton("one more button");
        b3= new JButton("third button");


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0, 120)));// space at the top 0 -> with - we don't care, 65 ->height


        b1.setAlignmentX(Component.CENTER_ALIGNMENT);	 // set horizontal allignment to center
        this.add(b1);
        this.add(Box.createRigidArea(new Dimension(0, 120 )));// space between components

        b2.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(b2);
        this.add(Box.createRigidArea(new Dimension(0, 120)));// space between components

        b3.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(b3);
        this.add(Box.createRigidArea(new Dimension(10, 120 )));// space at the end


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(20,100,100,100);
        bg.paintIcon(this, g, 0, 0);

    }

}
class MyGamePanel_withBox extends JPanel {
    private JButton b1, b2, b3;     //declare 3 buttons
    private JTextField txt1, txt2, txt3;   //declare 3 Test Fields

    public MyGamePanel_withBox() {    // constructor
        b1= new JButton("some button");
        b2= new JButton("one more button");
        b3= new JButton("third button");

        txt1= new JTextField("not empty txt");
        txt2= new JTextField(20);  // the size is 20 chars
        txt3= new JTextField("enter something");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0, 65)));// space at the top 0 -> with - we don't care, 65 ->height

        this.add(txt1);
        this.add(Box.createRigidArea(new Dimension(10, 45)));// space between components

        b1.setAlignmentX(Component.CENTER_ALIGNMENT);	 // set horizontal allignment to center
        this.add(b1);
        this.add(Box.createRigidArea(new Dimension(0, 45)));// space between components

        b2.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(b2);
        this.add(Box.createRigidArea(new Dimension(0, 45)));// space between components

        b3.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(b3);
        this.add(Box.createRigidArea(new Dimension(10, 135)));// space at the end

    }
}


