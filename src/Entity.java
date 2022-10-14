import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Entity {

	public Point pos; // position
	public BufferedImage image; //image of the entity
	public double size; //1 is the same size as the image file itself; multiplicative scaling
	
	public Entity (Point npos, double nsize, String nfileName) {
		pos = npos;
		size = nsize;
		loadImage(nfileName);
	}
	
	public void loadImage(String fileName) {
        
    	//loads the image for the player
    	try { image = ImageIO.read(new File("images/" + fileName));
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
	
	public boolean collision(Entity other) {
		return false;
	}
	
}
