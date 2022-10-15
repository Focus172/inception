import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Entity {

	public Point.Double pos; //Coordinate pair of vectors
	public BufferedImage image; //image of the entity
	public double size; //scaling factor of the image
	public String fileName; //name of the file
	
	public final Model MODEL;
	
	public Entity (Point.Double pos, double size, String fileName, Model model) {
		this.pos = pos;
		this.size = size;
		this.fileName = fileName;
		this.image = loadImage(fileName);
		
		this.MODEL = model;
	}
	
	public BufferedImage loadImage(String fileName) {
        
		//creating storage for image
		BufferedImage retImage = null;
		
    	//loads the image from file system in images folder
    	try { retImage = ImageIO.read(new File("images/" + fileName)); }
    	catch (IOException e) { e.printStackTrace(); } //System.out.println("Error opening image file: " + exc.getMessage()); }
		
    	//returns the image for some use
    	return retImage;
    
    }
	
	public void draw(Graphics g, ImageObserver observer, double rotation) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
		
		//BufferedImage copiedImage = loadImage(fileName);
		
		
		double rads = Math.toRadians(0);
		double sin = Math.abs(Math.sin(rads));
		double cos = Math.abs(Math.cos(rads));
		int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
		int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
		BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
		AffineTransform at = new AffineTransform();
		at.translate(w / 2, h / 2);
		at.rotate(rads,0, 0);
		at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
		final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		rotateOp.filter(image,rotatedImage);
        g.drawImage(
            rotatedImage, 
            (int)pos.x, 
            (int)pos.y, 
            observer
        );
    }
	
	public boolean collision(Entity other) {
		//boolean out = false;
		//Point.Double otherPos = other.getPos();
		//BufferedImage otherImg = other.getImage();
		return MODEL.collision(other.MODEL);
	}
	
	public Point.Double getPos() { return pos; }
	public BufferedImage getImage() { return image; }
	
}
