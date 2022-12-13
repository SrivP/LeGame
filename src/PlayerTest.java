import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class PlayerTest extends JPanel{
    public int x, y;
    public int speed;
    KeyHandler h = new KeyHandler();

    public PlayerTest() {
        setDefaultValues();
    }


    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {
        if (h.upPressed) {
            y -= speed;
        }
        else if (h.downPressed) {
            y += speed;
        }
        else if (h.leftPressed) {
            x -= speed;
        }
        else if (h.rightPressed) {
            x += speed;
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }



}

class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, rightPressed, leftPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = true;
            System.out.println("up");
        }
        else if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
            System.out.println("down");
        }
        else if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
            System.out.println("left");
        }
        else if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
            System.out.println("right");
        }
        else {
            System.out.println("Moment");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
            System.out.println("up");
        }
        else if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
            System.out.println("down");
        }
        else if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
            System.out.println("left");
        }
        else if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
            System.out.println("right");
        }
        else {
            System.out.println("Moment2");
        }
    }
}
