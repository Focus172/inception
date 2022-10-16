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
    
    public final int  maxXVelocity = 20;
    public final int  maxYVelocity = 20;
    
    public boolean grounded = false;
    
    private final double gravity = 6;
    
    private final int IMAGE_X;
    private final int IMAGE_Y;
    
    public static int[] xvals = new int[]{0,0,50,50};
	public static int[] yvals = new int[] {0,50,50,0};
    
    private int width;
    private int height;
    
    private Point.Double prev;
    
    public int health = 100;
    
    public int timeSinceCol;
    
    public Player() {
    	
    	super(new Point.Double(500,500), 1, "idlePlayer.png", new Model(xvals, yvals), 0);

        // initialize the state
        //score = 0;
    	
    	prev = new Point.Double(500,500);
    	
    	width = xvals[2]-xvals[0];
    	height = yvals[2]-yvals[0];
    
    	IMAGE_X = super.image.getWidth();
    	IMAGE_Y = super.image.getHeight();
    	
    	timeSinceCol = 0;
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
        	 xVelocity *= 0.9;
        }
        
        //setting fastest speed allowed
        if (xVelocity > maxXVelocity) {xVelocity = maxXVelocity;}
        if (xVelocity < -maxXVelocity) {xVelocity = -maxXVelocity;}
        if (yVelocity > maxYVelocity) {yVelocity = maxYVelocity;}
        
        //if you are on the ground then jump
        if (upPressed && grounded(obstacles) ) { yVelocity = -60; }
        
        if (Math.abs(pos.x - prev.x) > 80) {
        	pos.x = prev.x;
        }
        if (Math.abs(pos.y - prev.y) > 80) {
        	pos.y = prev.y;
        }
        
        prev.x = pos.x;
        prev.y = pos.y;
        
        //apply the changes
        
        pos.x += xVelocity;
        pos.y += yVelocity;
        
        model.move((int)pos.x, (int)pos.y,1);
        
        if (timeSinceCol > 0) {
            timeSinceCol--;
        }
        
        boolean intersecting = false;
        Entity intersector = null;
        if (obstacles != null) {
        for (int i = 0; i < obstacles.size(); i++) {
        	if (collision(obstacles.get(i)) != -1) {
        		intersecting = true;
        		intersector = obstacles.get(i);
        	}
        }
        if (intersector != null && timeSinceCol == 0) {
        	int counter = 1;
        	int basex = (int) pos.x + width;
        	int basey = (int) pos.y + height;
        	Point.Double point = new Point.Double (pos.x, pos.y);
        	boolean[][] collisions = new boolean[3][3];
        	for (int i = 0; i < 3; i++) {
        		for (int j = 0; j < 3; j++) {
        			collisions[i][j] = true;
        		}
        	}
        	
        	double xpull = 0;
            double ypull = 0;
            double pulls = 0;
        	
            while (pulls == 0 && counter < 10000) {
            	point.y -= counter;
            	point.x -= counter;
            	for (int i = 0; i < 3; i++) {
//            		System.out.println("counter: " + counter);
            		collisions[i][0] = (intersector.contains(point));
            		point.x += counter;
            		collisions[i][1] = (intersector.contains(point));
            		point.x += counter;
            		collisions[i][2] = (intersector.contains(point));
            		point.x += counter * (-2);
            		point.y += counter;
            	}
            	if (collisions[0][0]) {
                	pulls++;
                	xpull-=0.7071;
                	ypull-=0.7071;
                	System.out.println("0,0");
                }
                if (collisions[0][1]) {
                	pulls++;
                	ypull-=1;
                	System.out.println("0,1");
                }
                if (collisions[0][2]) {
                	pulls++;
                	xpull+=0.7071;
                	ypull-=0.7071;
                	System.out.println("0,2");
                }
                if (collisions[1][0]) {
                	pulls++;
                	xpull-=1;
                	System.out.println("1,0");
                }
                // no 1,1
                if (collisions[1][2]) {
                	pulls++;
                	xpull+=1;
                	System.out.println("1,2");
                }
                if (collisions[2][0]) {
                	pulls++;
                	xpull-=0.7071;
                	ypull+=0.7071;
                	System.out.println("2,0");
                }
                if (collisions[2][1]) {
                	pulls++;
                	ypull+=1;
                	System.out.println("2,1");
                }
                if (collisions[2][2]) {
                	pulls++;
                	xpull+=0.7071;
                	ypull+=0.7071;
                	System.out.println("2,2");
                }
            	point.x = basex;
            	point.y = basey;
            	counter += 1;
            }
            
            
            
            
            
            if (counter < 9000) {
            	System.out.println(counter);
            	xpull /= pulls;
                
                ypull /= pulls;
                
                System.out.println("xpulls: " + xpull);
                System.out.println("ypulls: " + ypull);
                double angle;
                if (xpull!=0) {
                	double slope = (double)ypull/(double)xpull;
                	if (slope != 0) {
                		System.out.println("slope: " + slope);
                    	slope = -1.0/slope;
                    	angle = Math.atan(slope);
                    	System.out.println("angle1 " + angle);
                    }
                    else {
                    	angle = Math.toRadians(90);
                    	System.out.println("angle2 " + angle);
                    }
                }
                else {
                	angle = 0;
                	System.out.println("angle3 " + angle);
                }
                
                
                
                
                
                
                double hyp = Math.sqrt(xpull*xpull + ypull*ypull);
                
                
                System.out.println("angle " + angle);
                
                
                
                xVelocity = xVelocity*Math.cos(2*angle);
                yVelocity = yVelocity*Math.cos(2*angle + Math.toRadians(180));
            }
            
            else {
            	xVelocity = xVelocity*(-1);
                yVelocity = yVelocity*(-1);
            }
            
            
            
            
            pos.x = prev.x + xVelocity * 2;
            pos.y = prev.y + yVelocity * 2;
            
            
//            System.out.println("matrix: ");
//            System.out.println(collisions.toString());
            
            timeSinceCol = 5;
        }
        }
        
        System.out.println(pos.x + ", " + pos.y);
        
        //pos.translate(xVelocity, yVelocity);
        
    }
    
    private boolean grounded(ArrayList<Obstacle> obstacles) {
    	boolean isGrounded = false;
    	for (Entity obj : obstacles) {
    		if (obj.collision(this) != -1) {
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