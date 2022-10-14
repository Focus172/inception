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
    
    private int score;
    private BufferedImage image;
    
    //handles continous movement
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    
    public int yVelocity = 0;
    public int xVelocity = 0;
    
    private final int gravity = 4;
    
    private int imageX;
    private int imageY;
    
    private int health = 100;
    
    public Player() {
        // loads the image
        loadImage();

        // initialize the state
        pos = new Point(0, 0);
        score = 0;
    }

    private void loadImage() {
        
    	//loads the image for the player
    	try { image = ImageIO.read(new File("images/player.png"));
        } catch (IOException e) { e.printStackTrace(); } //System.out.println("Error opening image file: " + exc.getMessage()); }
    
    	imageX = image.getWidth();
    	imageY = image.getHeight();
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        
    	//draws the player
    	g.drawImage(
            image, 
            pos.x, 
            pos.y, 
            observer
        );
    	
    	
    	//drawing the health

        // set the text to be displayed
    	String text = health + "/100";
            
    	// we need to cast the Graphics to Graphics2D to draw nicer text
        Graphics2D g2d = (Graphics2D) g;

        
        // set the text color and font
        
        
        g2d.setColor(new Color(30, 201, 139));
        g.fillRect(0, 600, 200, 40);
        
        g2d.setFont(new Font("Lato", Font.BOLD, 25));
        g2d.setColor(new Color(0, 0, 0));
        g2d.drawString(text, 0, 600);

    	
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
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

    	
        // prevent the player from moving off the edge of the board sideways
    	//this should be changed to handle edges
        
    
        System.out.println("yPos: " + pos.y);
        System.out.println("yVelocity: " + yVelocity);
    	
        //if player is jumping then set their yVelocity Up
        
        //this doesnt work
        if (upPressed && pos.y <= Board.MAX_Y-300) { yVelocity -= 200; }
        
        //if the player wants to move then let them
        if (rightPressed && !leftPressed) { xVelocity += 10; }
        else if (!rightPressed && leftPressed) { xVelocity -= 10; } 
        
        //if player does not want to move or is indesisive then slow them
        if ((rightPressed && leftPressed) || (!rightPressed && !leftPressed)) {
        	 xVelocity = 0;
        }
        
        yVelocity += gravity;
        
        if (pos.x < 0) { pos.x = 0; }
        else if (pos.x >= Board.MAX_X) { pos.x = Board.MAX_X - 1; xVelocity = 0;}
        
        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) { pos.y = 0; }
        else if (pos.y >= Board.MAX_Y - imageY) { pos.y = Board.MAX_Y - imageY; yVelocity = 0;}
        
        pos.translate(xVelocity, yVelocity);
        
        //implement some type of gravity
        
    }

    public String getScore() { return String.valueOf(score); }
    public Point getPos() { return pos; }
    public void addScore(int amount) { score += amount; }
    
}