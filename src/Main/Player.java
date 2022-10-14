package Main;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {

    //has x and y position of player
    private Point pos;
    
    private BufferedImage image;
    
    //handles continuous movement
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    
    public int yVelocity = 0;
    public int xVelocity = 0;
    
    public final int  maxXVelocity = 50;
    public final int  maxYVelocity = 100;
    
    public boolean grounded = false;
    
    private final int gravity = 6;
    
    private int imageX;
    private int imageY;
    
    public int health = 100;
    
    public Player() {
        // loads the image
        loadImage();

        // initialize the state
        pos = new Point(0, 0);
    }

    private void loadImage() {
        
    	//loads the image for the player
    	try { image = ImageIO.read(new File("images/player.png"));
        } catch (IOException e) { e.printStackTrace(); } //System.out.println("Error opening image file: " + exc.getMessage()); }
    
    	imageX = image.getWidth();
    	imageY = image.getHeight();
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double
        
    	//draws the player
    	g.drawImage(
            image, 
            pos.x, 
            pos.y, 
            observer
        );
    	
    }
    
    public void keyPressed(KeyEvent e) {
    	//gets the code of the key being pressed
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) { upPressed = true; }
        if (key == KeyEvent.VK_RIGHT) { rightPressed = true; }
        if (key == KeyEvent.VK_DOWN) { downPressed = true; }
        if (key == KeyEvent.VK_LEFT) { leftPressed = true; }
        
    }
    
    public void keyReleased(KeyEvent e) {
    	//gets the code of the key being pressed
    	int key = e.getKeyCode();
    	
    	if (key == KeyEvent.VK_UP) { upPressed = false; }
        if (key == KeyEvent.VK_RIGHT) { rightPressed = false; }  
        if (key == KeyEvent.VK_DOWN) { downPressed = false; }
        if (key == KeyEvent.VK_LEFT) { leftPressed = false; }
        
    }

    public void tick() {
    	//called every tick

        //apply gravity
        yVelocity += gravity;
        
        //change health
        if (health > 0) { health--; }
        
        //prevent them from moving through things
        if (pos.y < 0) { pos.y = 0; }
        else if (pos.y >= Board.MAX_Y - imageY) { pos.y = Board.MAX_Y - imageY; yVelocity = 0; }
        
        //prevent them from moving through walls
        if (pos.x < 0) { pos.x = 0; xVelocity = 0; }
        else if (pos.x >= Board.MAX_X - imageX) { pos.x = Board.MAX_X - imageX; xVelocity = 0; }
        
        
        //if the player wants to move then let them
        if (rightPressed && !leftPressed) { xVelocity += 10; }
        else if (!rightPressed && leftPressed) { xVelocity -= 10; } 
        
        //if player does not want to move or is indesive then slow them
        if ((rightPressed && leftPressed) || (!rightPressed && !leftPressed)) {
        	 xVelocity = 0;
        }
        
       
        
        
        
        //setting fastest speed allowed
        if (xVelocity > maxXVelocity) {xVelocity = maxXVelocity;}
        if (xVelocity < -maxXVelocity) {xVelocity = -maxXVelocity;}
        if (yVelocity > maxYVelocity) {yVelocity = maxYVelocity;}
        
        //if you are on the ground then jump
        if (upPressed && (grounded() || grounded) ) { yVelocity = -60; }
        
        //apply the changes
        pos.translate(xVelocity, yVelocity);
        
    }
    
    private boolean grounded() {
    	//TODO make colition detection to 
    	return pos.y == Board.MAX_Y-imageY;
    }

    public int getHealth() { return health; }
    public Point getPos() { return pos; }
    
}