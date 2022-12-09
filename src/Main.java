import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {

    static Container c;
    static CardLayout cdly;
    Menu menu;
    Levels levels;


    public static void main (String [] args) {

        //Container c = f.getContentPane();
        //c.setLayout(new BorderLayout());  // set Layout to Border
        Main a = new Main();
        a.setTitle("The Dungeons of ICS");


        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // make frame closed when x button is pressed
        a.setVisible(true);     // make the frame visible
        a.pack();
        a.setResizable(false);
        a.setLocationRelativeTo(null);
        a.setSize(1280, 720);
    }
    public Main(){    //constructor
        c=getContentPane();
        cdly=new CardLayout();
        c.setLayout(cdly);

        menu = new Menu();
        menu.b[2].addActionListener(this);
        levels = new Levels();

        c.add("Test", menu);
        c.add("Test2", levels);


    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.b[2]) {
            setVisible(false);
            dispose();
        }
    }
}

