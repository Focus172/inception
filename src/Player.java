
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
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D.Double;


public class Player extends Entity{ ;

    //has x and y position of player
    
    //private int score;
    private BufferedImage image;
    
    //handles continuous movement
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    
    public double yVelocity = 0;
    public double xVelocity = 0;
    
    public final int  maxXVelocity = 50;
    public final int  maxYVelocity = 100;
    
    public boolean grounded = false;
    
    private final double gravity = 6;
    
    private int imageX;
    private int imageY;
    
    public int health = 100;
    
    public Player() {
    	super(new Point.Double(500,500), 1, "player.png", new Model(new int[]{0,10,0,10}, new int[] {0,0,10,10}));

        // initialize the state
        //score = 0;
    
    	imageX = super.image.getWidth();
    	imageY = super.image.getHeight();
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

    public void tick(ArrayList<Obstacle> obstacles) {
    	//called every tick

        //apply gravity
        yVelocity += gravity;
        
        //change health
        if (health > 0) { health--; }
        
        //prevent them from moving through things
        if (pos.y < 0) { pos.y = 0; }
        else if (pos.y >= Board.MAX_Y - imageY) { pos.y = Board.MAX_Y - imageY; yVelocity = 0.0; }
        
        //prevent them from moving through walls
        if (pos.x < 0) { pos.x = 0; xVelocity = 0; }
        else if (pos.x >= Board.MAX_X - imageX) { pos.x = Board.MAX_X - imageX; xVelocity = 0.0; }
        
        
        //if the player wants to move then let them
        if (rightPressed && !leftPressed) { xVelocity += 10.0; }
        else if (!rightPressed && leftPressed) { xVelocity -= 10.0; } 
        
        //if player does not want to move or is indesive then slow them
        if ((rightPressed && leftPressed) || (!rightPressed && !leftPressed)) {
        	 xVelocity = 0.0;
        }
        
       

        
        //setting fastest speed allowed
        if (xVelocity > maxXVelocity) {xVelocity = maxXVelocity;}
        if (xVelocity < -maxXVelocity) {xVelocity = -maxXVelocity;}
        if (yVelocity > maxYVelocity) {yVelocity = maxYVelocity;}
        
        //if you are on the ground then jump
        if (upPressed && (grounded() || grounded) ) { yVelocity = -60; }
        
        //apply the changes
        
        pos.x += xVelocity;
        pos.y += yVelocity;
        
        model.move((int)pos.x, (int)pos.y,1);
        
        boolean intersecting = false;
        for (Obstacle obstacle: obstacles) {
        	if (collision (obstacle)) {
        		intersecting = true;
        	}
        }
        if (intersecting == true) {
        	pos.x = 0;
        }
        
        //pos.translate(xVelocity, yVelocity);
        
    }
    
    private boolean grounded() {
    	//TODO make colition detection to 
    	return pos.y == Board.MAX_Y-imageY;
    }

    public Point.Double getPos() { return pos; }
    public int getHealth() { return health; }
    
}