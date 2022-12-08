import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements ActionListener {
    private ImageIcon bg = new ImageIcon("dojo1.gif");
    private JButton[] b = new JButton[3];  //declare 3 buttons

    public Menu() {
        b[0] = new JButton("Play");
        b[0].addActionListener(this);
        b[1] = new JButton("Instructions");
        b[2] = new JButton("Exit");

        this.setLayout(new GridLayout(3, 0, 30, 30));

        for (int i = 0; i < b.length; i++) {
            b[i].setAlignmentY(Component.CENTER_ALIGNMENT);
            this.add(b[i]);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        bg.paintIcon(this, g, 0, 0);

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b[0])
            Main.cdly.next(Main.c);
    }


}

class Levels extends JPanel implements ActionListener {
    private JButton[] b = new JButton[4];  //declare 3 buttons
    private ImageIcon bg = new ImageIcon("dungeon.gif");


    public Levels() {
        ImageIcon back = new ImageIcon("back.png");
        ImageIcon lvl1 = new ImageIcon("lvl1.png");
        ImageIcon lvl2 = new ImageIcon("lvl2.png");
        ImageIcon lvl3 = new ImageIcon("lvl3.png");

        this.setLayout(new GridLayout(0, 4));

        b[0] = new JButton(back);
        b[0].addActionListener(this);
        b[1] = new JButton(lvl1);
        b[2] = new JButton(lvl2);
        b[3] = new JButton(lvl3);

        for (int i = 0; i < b.length; i++) {
            b[i].setContentAreaFilled(false);
            b[i].setBorderPainted(false);
            b[i].setAlignmentY(Component.CENTER_ALIGNMENT);
            this.add(b[i]);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        bg.paintIcon(this, g, 0, 0);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b[0])
            Main.cdly.next(Main.c);
    }
}


