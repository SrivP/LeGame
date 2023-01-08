import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




public class Level2 extends JPanel implements ActionListener, KeyListener {
    private ImageIcon bg2 = new ImageIcon("bg2.png");
    private Player player;
    private Boss2 bs2;
    private Timer myT;
    static boolean right = false;
    boolean left = false;
    static boolean stay = true;

    public Level2() {
        MyImages.loadImages();
        player = new Player(0, 600);  // new player at the position 10,20
        bs2 = new Boss2(1000,450);
        addKeyListener(this);
        myT = new Timer(120, this);
        myT.start();
        setFocusable(true);
        add(player.getHealthBar());
        add(bs2.getHealthBar());

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        bg2.paintIcon(this, g, 0, 0);
        player.myDraw(g);
        bs2.myDraw(g);
        for (Projectile projectile : bs2.projectiles) {
            projectile.paintComponent(g);
        }

    }





    public void actionPerformed(ActionEvent e) {
        player.velreset();
        bs2.follow(player);
        bs2.move();

        for (Projectile projectile : bs2.getProjectiles()) {
            projectile.follow(player);
            if (projectile.isActive()) {
                projectile.setX(projectile.getX() + projectile.getVX());
                projectile.setY(projectile.getY() + projectile.getVY());
            }
        }
        for (Projectile projectile : bs2.getProjectiles()) {
            if (projectile.collidesWith(player)) {
                projectile.setActive(false);
                player.inflictDamage(25);
                player.getHealthBar().setValue(player.getHealth());
            }
        }

        repaint();

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            player.up();

            System.out.println("up");
        }
        else if (code == KeyEvent.VK_LEFT) {
            player.left();

            System.out.println("left");
        } else if (code == KeyEvent.VK_RIGHT) {
            player.right();
            System.out.println("right");
        }
        else {

            System.out.println("Yifan");
        }

        repaint();
    }

    public void keyReleased(KeyEvent e) {
        player.velreset();
        bs2.fireProjectile();
        repaint();

    }




    public static void main(String[] args) {
        // frame creation, instance creation, default configs
        JFrame a = new JFrame("Layouts");

        Container c = a.getContentPane();
        c.setLayout(new BorderLayout());

        Level2 boss2= new Level2();

        c.add(boss2, BorderLayout.CENTER);

        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // make frame closed when x button is pressed
        a.setVisible(true);     // make the frame visible
        a.pack();
        a.setResizable(false);
        a.setLocationRelativeTo(null);
        a.setSize(1280, 720);
        while (true) {
            boss2.player.velreset();
            boss2.repaint();
            try {
                Thread.sleep(1000 / 120);
            } catch (InterruptedException ignored) {}
        }


    }//end of main
}//end of class
