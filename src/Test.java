//Card Layout Example
// creats 2 panels: menu and game. Switches between these 2 panels
// when buttons are pressed
//Created by Ms. S June 5, 2014
import java.awt.*;
import java.awt.event.*;


import javax.swing.*;


class Card_Layout_Demo extends JFrame{
    static CardLayout cardsL;
    static Container c;

    MyMenuPanel  menuP;  // object of my customized class MyMenuPanel
    MyGamePanel gameP;   // object of my customized class MyGamePanel



    public Card_Layout_Demo(){    //constructor
        c=getContentPane();
        cardsL=new CardLayout();
        c.setLayout(cardsL);

        menuP = new MyMenuPanel();
        gameP = new MyGamePanel();

        c.add("MenuNickName", menuP);
        c.add("GameNickName", gameP);

    }

    public static void main(String[] args) {
        Card_Layout_Demo a = new Card_Layout_Demo();
        a.setSize(500, 500);
        a.setVisible(true);
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // make frame closed when x button is pressed

    }

} //end of demo class

class MyMenuPanel  extends JPanel implements ActionListener{
    private JButton  goGame;    // variables: button

    public MyMenuPanel(){  // constructor
        goGame=new JButton("   Go to game panel   ");
        goGame.addActionListener(this);
        this.setLayout(new FlowLayout());
        this.add(goGame);     // add the button
        this.setBackground(Color.GREEN);
    }
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==goGame)
            Card_Layout_Demo.cardsL.next(Card_Layout_Demo.c);
        // cardsL is a static variable of Card_Layout_Demo class--> cardLayout
        //c is a static variable of Card_Layout_Demo class--> container

		/* other possible methods
		    cards.next(c);
		    cards.previous(c);
		    cards.first(c);
		    cards.last(c);
		    cards.setVgap(10);
		    cards.setHgap(15);
		    */
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 18));  // set a new font
        g.drawString("This is my menu",100,200);   // display the string starting at the coordinate (100, 200)
    }//end of paint

}// end of MyMenuPanel class

class MyGamePanel  extends JPanel implements ActionListener{
    private JButton  goMenu;    // variables: button

    public MyGamePanel(){  // constructor
        goMenu=new JButton("         Go to menu panel     ");
        goMenu.addActionListener(this);
        this.setLayout(new FlowLayout());
        this.add(goMenu);
        this.setBackground(Color.BLUE);
    }
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==goMenu)
            Card_Layout_Demo.cardsL.next(Card_Layout_Demo.c);

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 18));  // set a new font
        g.drawString("---Game panel---",200,300);   // display the string starting at the coordinate (100, 200)
    }//end of paint

}// end of MyGamePanel class
