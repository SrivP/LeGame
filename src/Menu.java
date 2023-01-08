import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel implements ActionListener {
    private ImageIcon bg = new ImageIcon("dojo1.gif");
    public JButton[] b = new JButton[3];  //declare 3 buttons
    private JLabel[] l = new JLabel[3];

    public Menu() {
        l[0] = new JLabel("The");
        l[1] = new JLabel("Dungeons of");
        l[2] = new JLabel("ICS");
        l[0].setHorizontalAlignment(JLabel.RIGHT);
        l[1].setHorizontalAlignment(JLabel.CENTER);
        l[2].setHorizontalAlignment(JLabel.LEFT);
        for (int i = 0; i < l.length; i++) {
            l[i].setFont(new Font("Sans Serif", Font.BOLD, 48));
            l[i].setAlignmentY(Component.CENTER_ALIGNMENT);
            l[i].setForeground(new java.awt.Color(255, 255, 255));
            this.add(l[i]);
        }

        ImageIcon play = new ImageIcon("play.png");
        ImageIcon instructions = new ImageIcon("instructions.png");
        ImageIcon quit = new ImageIcon("quit.png");

        b[0] = new JButton(play);
        b[0].addActionListener(this);
        b[1] = new JButton(instructions);
        b[2] = new JButton(quit);

        this.setLayout(new GridLayout(2, 3));

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

    public JButton getQuit() {
        return b[2];
    }

    public void setB(JButton[] b) {
        this.b = b;
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
        b[1].addActionListener(this);
        b[2] = new JButton(lvl2);
        b[2].addActionListener(this);
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
        if (e.getSource() == b[1])
            Main.cdly.next(Main.c);
        if (e.getSource() == b[2])
            Main.cdly.next(Main.c);
    }
}


