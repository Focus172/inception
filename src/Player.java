import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.Point2D.Double;


public class Player extends Entity{ ;

    //has x and y position of player
    
    private int score;
    
    //handles continous movement
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    
    
    public Player() {
    	super(new Point.Double(0,0), 1, "player.png", new Model(new Polygon[0]));

        // initialize the state
        
        score = 0;
    }

    
    
    public void keyPressed(KeyEvent e) {
        // every keyboard get has a certain code. get the value of that code from the
        // keyboard event so that we can compare it to KeyEvent constants
        int key = e.getKeyCode();
        
        // depending on which arrow key was pressed, we're going to move the player by
        // one whole tile for this input
        
        if (key == KeyEvent.VK_UP) { upPressed = true; }
        
        if (key == KeyEvent.VK_RIGHT) { rightPressed = true; }
       
        if (key == KeyEvent.VK_DOWN) { downPressed = true; }
        
        if (key == KeyEvent.VK_LEFT) { leftPressed = true; }
        
        
        
       
        /*
        if (key == KeyEvent.VK_UP) {
            pos.translate(0, -1);
        }
        if (key == KeyEvent.VK_RIGHT) {
            pos.translate(1, 0);
        }
        if (key == KeyEvent.VK_DOWN) {
            pos.translate(0, 1);
        }
        if (key == KeyEvent.VK_LEFT) {
            pos.translate(-1, 0);
        }
        */
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
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

    	
        // prevent the player from moving off the edge of the board sideways
    	//this should be changed to handle edges
    	if (pos.x < 0) { pos.x = 0;
        } else if (pos.x >= Board.COLUMNS) { pos.x = Board.COLUMNS - 1; }
        
        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= Board.ROWS) {
            pos.y = Board.ROWS - 1;
        }
        
        
        if (upPressed) { pos.y += -1; }
        
        if (rightPressed) { pos.x += 1; }
        
        if (downPressed) { pos.y += 1; }
        
        if (leftPressed) { pos.x += -1; }
        
        
        //implement some type of gravity
        
    }

    public String getScore() { return String.valueOf(score); }
    public Point.Double getPos() { return pos; }
    public void addScore(int amount) { score += amount; }
    
}