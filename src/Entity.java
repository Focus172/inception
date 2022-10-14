import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Entity {

	public Point.Double pos; // position
	public BufferedImage image; //image of the entity
	public double size; //1 is the same size as the image file itself; multiplicative scaling
	public Model model;
	
	public Entity (Point.Double npos, double nsize, String nfileName, Model nmodel) {
		pos = npos;
		size = nsize;
		model = nmodel;
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
            (int)pos.x, 
            (int)pos.y, 
            observer
        );
    }
	
	public boolean collision(Entity other) {
		boolean out = false;
		Point.Double otherPos = other.getPos();
		BufferedImage otherImg = other.getImage();
		return this.model.collision(other.model);
	}
	
	public Point.Double getPos() {
		return pos;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
}
