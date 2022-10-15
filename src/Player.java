import java.awt.event.KeyEvent;
import java.awt.Point;
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D.Double;


public class Player extends Entity { ;

    //has x and y position of player
    
    //private int score;
    
    //handles continuous movement
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public boolean spacePressed = false;
    
    public double yVelocity = 0;
    public double xVelocity = 0;
    
    public final int maxXVelocity = 50;
    public final int maxYVelocity = 100;
    
    public boolean grounded = false;
    
    private final double gravity = 6;
    
    private final int IMAGE_X;
    private final int IMAGE_Y;
    
    public int health = 100;
    
    public Player() {
    	
    	super(new Point.Double(0,0), 1, "player.png", new Model(new int[]{0,64,0,64}, new int[]{0,0,64,64}));


    	//gets some of the private instance variables for later use
    	IMAGE_X = image.getWidth();
    	IMAGE_Y = image.getHeight();
    }
    
    public void keyPressed(KeyEvent e) {
    	//gets the code of the key being pressed
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) { upPressed = true; }
        if (key == KeyEvent.VK_RIGHT) { rightPressed = true; }
        if (key == KeyEvent.VK_DOWN) { downPressed = true; }
        if (key == KeyEvent.VK_LEFT) { leftPressed = true; }
        if (key == KeyEvent.VK_SPACE) { spacePressed = true; }
        
    }
    
    public void keyReleased(KeyEvent e) {
    	//gets the code of the key being pressed
    	int key = e.getKeyCode();
    	
    	if (key == KeyEvent.VK_UP) { upPressed = false; }
        if (key == KeyEvent.VK_RIGHT) { rightPressed = false; }  
        if (key == KeyEvent.VK_DOWN) { downPressed = false; }
        if (key == KeyEvent.VK_LEFT) { leftPressed = false; }
        if (key == KeyEvent.VK_SPACE) { spacePressed = false; }
        
    }

    public void tick(ArrayList<Obstacle> obstacles) {
    	//called every tick

        //apply gravity
        yVelocity += gravity;
        
        //change health
        if (health > 0) { health--; }
        
        //prevent them from moving through things
        if (pos.y < 0) { pos.y = 0; }
        else if (pos.y >= Board.MAX_Y - IMAGE_Y) { pos.y = Board.MAX_Y - IMAGE_Y; yVelocity = 0.0; }
        
        //prevent them from moving through walls
        if (pos.x < 0) { pos.x = 0; xVelocity = 0; }
        else if (pos.x >= Board.MAX_X - IMAGE_X) { pos.x = Board.MAX_X - IMAGE_X; xVelocity = 0.0; }
        
        
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
        if (upPressed && (grounded()) ) { yVelocity = -60; }
        
        //apply the changes
        
        pos.x += xVelocity;
        pos.y += yVelocity;
        
        model.move((int)pos.x, (int)pos.y,1);
        
        boolean intersecting = false;
        Entity intersector;
        
        if (obstacles != null) {
        for (int i = 0; i < obstacles.size(); i++) {
        	if (collision(obstacles.get(i)) != -1) {
        		intersecting = true;
        		intersector = obstacles.get(i);
        	}
        }
        while (intersecting == true) {
        	int basex = (int) pos.x;
        	int basey = (int) pos.y;
        	for (int i = 0; i < 8; i++) {
        		
        	}
        }
        }
        
        
        //pos.translate(xVelocity, yVelocity);
        
    }
    
    private boolean grounded() {
    	boolean isGrounded = false;
    	for (Entity obj : Board.obstacles) {
    		if (obj.collision(this)) {
    			isGrounded = true;
    		}
    	}
    	
    	if (pos.y == Board.MAX_Y-IMAGE_Y) {
    		isGrounded = true;
    	}
    	
    	//TODO make colition detection to 
    	return isGrounded;
    }

    public Point.Double getPos() { return pos; }
    public int getHealth() { return health; }
    
}