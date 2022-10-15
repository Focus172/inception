import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.lang.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


public class Entity {

	public Point.Double pos; // position
	public BufferedImage image; //image of the entity
	public double size; //1 is the same size as the image file itself; multiplicative scaling
	public Model model;
	public String fileName;
	
	public Entity (Point.Double npos, double nsize, String nfileName, Model nmodel) {
		pos = npos;
		size = nsize;
		model = nmodel;
		image = loadImage(nfileName);
		fileName = nfileName;
	}
	
	public BufferedImage loadImage(String fileName) {
        
    	//loads the image for the player
    	try { image = ImageIO.read(new File("images/" + fileName));
        } catch (IOException e) { e.printStackTrace(); } //System.out.println("Error opening image file: " + exc.getMessage()); }
		return image;
    
    }
	
	public void draw(Graphics g, ImageObserver observer, double rotation) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
		BufferedImage copiedImage = loadImage(fileName);
		double rads = Math.toRadians(-rotation);
		double sin = Math.abs(Math.sin(rads));
		double cos = Math.abs(Math.cos(rads));
		int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
		int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
		BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
		AffineTransform at = new AffineTransform();
		at.translate(0.5 * w, 0.5 * h);
		at.rotate(rads);
		System.out.println(rotation);
		at.translate(0.5 * (-w), 0.5 * (-h));
		AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		rotateOp.filter(image,rotatedImage);
        g.drawImage(
            rotatedImage, 
            (int)pos.x-w, 
            (int)pos.y-h, 
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
