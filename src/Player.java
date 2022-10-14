import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {

    //has x and y position of player
    private Point pos;
    
    private int score;
    private BufferedImage image;
    
    //handles continous movement
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    
    
    public Player() {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(0, 0);
        score = 0;
    }

    private void loadImage() {
        
    	//loads the image for the player
    	try { image = ImageIO.read(new File("images/player.png"));
        } catch (IOException e) { e.printStackTrace(); } //System.out.println("Error opening image file: " + exc.getMessage()); }
    
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.drawImage(
            image, 
            pos.x * Board.TILE_SIZE, 
            pos.y * Board.TILE_SIZE, 
            observer
        );
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
        
        
        if (upPressed) { pos.translate(0, -1); }
        
        if (rightPressed) { pos.translate(1, 0); }
        
        if (downPressed) { pos.translate(0, 1); }
        
        if (leftPressed) { pos.translate(-1, 0); }
        
        
        //implement some type of gravity
        
    }

    public String getScore() { return String.valueOf(score); }
    public Point getPos() { return pos; }
    public void addScore(int amount) { score += amount; }
    
}