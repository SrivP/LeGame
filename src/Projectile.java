import java.awt.*;

public class Projectile {
    private int x, y, width, height, vx, vy;
    private boolean active;

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
        width = 10;
        height = 10;
        active = true;
    }

    // Returns the x position of the projectile
    public int getX() {
        return x;
    }

    // Returns the y position of the projectile
    public int getY() {
        return y;
    }

    // Returns the width of the projectile
    public int getWidth() {
        return width;
    }

    // Returns the height of the projectile
    public int getHeight() {
        return height;
    }

    // Returns the velocity in the x direction of the projectile
    public int getVX() {
        return vx;
    }

    // Returns the velocity in the y direction of the projectile
    public int getVY() {
        return vy;
    }

    // Returns true if the projectile is active, false otherwise
    public boolean isActive() {
        return active;
    }

    // Sets the x position of the projectile
    public void setX(int x) {
        this.x = x;
    }

    // Sets the y position of the projectile
    public void setY(int y) {
        this.y = y;
    }

    // Sets the width of the projectile
    public void setWidth(int width) {
        this.width = width;
    }

    // Sets the height of the projectile
    public void setHeight(int height) {
        this.height = height;
    }

    // Sets the velocity in the x direction of the projectile
    public void setVX(int vx) {
        this.vx = vx;
    }

    // Sets the velocity in the y direction of the projectile
    public void setVY(int vy) {
        this.vy = vy;
    }

    // Sets the active status of the projectile
    public void setActive(boolean active) {
        this.active = active;
    }

    // Returns true if this projectile collides with a sprite, false otherwise
    public boolean collidesWith(Player player) {
        Rectangle thisRect = new Rectangle(x, y, width, height);
        Rectangle spriteRect = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        return thisRect.intersects(spriteRect);
    }

    public void paintComponent(Graphics g) {
        if (active) {
            // Draw the projectile
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public void follow(Player player) {
        // Calculate the distance between the enemy and player in the x and y directions
        int dx = player.getX() - getX();
        int dy = player.getY() - getY();

        // Set the velocity in the x and y directions to move the enemy towards the player
        vx = dx / 10;
        vy = dy / 10;
    }


}