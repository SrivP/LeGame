import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;


public class Menu extends JPanel implements ActionListener {
    private ImageIcon bg = new ImageIcon("dojo1.gif");
    private JButton b1, b2, b3;

    public Menu() {

        b1= new JButton("Play");
        b1.addActionListener(this);
        b2= new JButton("Instructions");
        b3= new JButton("Exit");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0, 65)));// space at the top 0 -> with - we don't care, 65 ->height


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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1)
            Main.cdly.next(Main.c);
    }


}

class Levels extends JPanel implements ActionListener {
    private JButton b0, b1, b2, b3;     //declare 3 buttons
    private ImageIcon bg = new ImageIcon("dungeon.gif");


    public Levels() {
        ImageIcon back = new ImageIcon("back.png");
        ImageIcon lvl1 = new ImageIcon("lvl1.png");
        ImageIcon lvl2 = new ImageIcon("lvl2.png");
        ImageIcon lvl3 = new ImageIcon("lvl3.png");

        b0 = new JButton(back);
        b0.addActionListener(this);
        b0.setContentAreaFilled(false);
        b0.setBorderPainted(false);
        b1 = new JButton(lvl1);
        b1.setContentAreaFilled(false);
        b1.setBorderPainted(false);
        b2 = new JButton(lvl2);
        b2.setContentAreaFilled(false);
        b2.setBorderPainted(false);
        b3 = new JButton(lvl3);
        b3.setContentAreaFilled(false);
        b3.setBorderPainted(false);

        this.setLayout(new GridLayout(0, 4));

        b0.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(b0);
        b1.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(b1);
        b2.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(b2);
        b3.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(b3);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        bg.paintIcon(this, g, 0, 0);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b0)
            Main.cdly.next(Main.c);
    }
}


