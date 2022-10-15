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
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.swing.ImageIcon;


public class Entity {

	public Point.Double pos; // position
	public BufferedImage image; //image of the entity
	public double size; //1 is the same size as the image file itself; multiplicative scaling
	public Model model;
	public String fileName;
	public ArrayList<Point> locations; //debug
	
	public Entity (Point.Double npos, double nsize, String nfileName, Model nmodel) {
		pos = npos;
		size = nsize;
		model = nmodel;
		image = loadImage(nfileName);
		fileName = nfileName;
		locations = new ArrayList<Point>();
	}
	
	public BufferedImage loadImage(String fileName) {
        
    	//loads the image for the player
    	try { image = ImageIO.read(new File("images/" + fileName));
        } catch (IOException e) { e.printStackTrace(); } //System.out.println("Error opening image file: " + exc.getMessage()); }
		return image;
    
    }
	
	public void draw(Graphics g, ImageObserver observer, double rotation) {
		BufferedImage copiedImage = loadImage(fileName);
//		double rads = Math.toRadians(-rotation);
//		double sin = Math.abs(Math.sin(rads));
//		double cos = Math.abs(Math.cos(rads));
//		int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
//		int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
//		BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
//		AffineTransform at = new AffineTransform();
//		at.translate(0.5 * w, 0.5 * h);
//		at.rotate(rads);
//		System.out.println(rotation);
//		at.translate(0.5 * (-w), 0.5 * (-h));
//		AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//		rotateOp.filter(image,rotatedImage);
//        g.drawImage(
//            rotatedImage, 
//            (int)pos.x-w, 
//            (int)pos.y-h, 
//            observer
//        );
		
		double rads = Math.toRadians(-rotation);
		double sin = Math.abs(Math.sin(rads));
		double cos = Math.abs(Math.cos(rads));
		int w = (int) Math.floor(image.getWidth());
		int h = (int) Math.floor(image.getHeight());
		ImageIcon icon = new ImageIcon(copiedImage);
		BufferedImage rotatedImage = new BufferedImage(w * 4, h * 4, image.getType());
		AffineTransform at = new AffineTransform();
		at.translate(w * 2, h * 2);
		at.rotate(rads,w * 2,h * 2);
		AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		rotateOp.filter(image,rotatedImage);
		g.drawImage(
			rotatedImage, 
	        (int)(pos.x-(w*2)), 
	        (int)(pos.y-(h*2)), 
	        observer
	    );
        
        model.draw(g, observer);
        
        locations.add(new Point((int)pos.x,(int)pos.y));
		int[] xtests = new int[locations.size()];
		int[] ytests = new int[locations.size()];
		for (int i = 0; i < locations.size(); i++) {
			xtests[i] = locations.get(i).x;
			ytests[i] = locations.get(i).y;
		}
		g.drawPolygon(xtests, ytests, xtests.length);
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
