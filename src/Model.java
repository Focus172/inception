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
import java.awt.geom.Area;

public class Model {

	public Polygon[] shapes;
	
	public Model (Polygon[]nshapes) {
		shapes = nshapes;
	}
	
	public Model (int[]xvals, int[]yvals) {
		shapes = new Polygon[1];
		shapes[0] = new Polygon(xvals, yvals, xvals.length);
	}
	
	public void move (int x, int y) { //only works for players; assumes first corner in polygon is upper left hand corner of the image
		
		for (Polygon shape: shapes) {
			int xoffset = shape.xpoints[0] - x;
			int yoffset = shape.ypoints[0] - y;
			System.out.println("offset x: " + xoffset);
			System.out.println("offset y: " + yoffset);
			for (int i = 0; i < shape.xpoints.length; i++) {
				shape.xpoints[i]-=xoffset;
				shape.ypoints[i]-=yoffset;
				System.out.println("hitbox x: " + shape.xpoints[i]);
				System.out.println("hitbox y: " + shape.ypoints[i]);
			}
		}
	}
	
	public boolean collision (Model other) {
		boolean out = false;
		for (Polygon shape: shapes) {
			for (Polygon otherShape: other.shapes) {
				Area area = new Area(shape);
				area.intersect(new Area(otherShape));
				if (area.isEmpty() == false) {
					out = true;
					System.out.println("CORRECT\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nCORRECT");
				}
				
			}
		}
		return out;
	}
	
	public void rotate(double degrees) {
		for (int i = 0; i < shapes.length; i++) {
			Polygon current = shapes[i];
			for (int j = 0; j < current.xpoints.length; j++) {
				Point.Double point = new Point.Double (current.xpoints[j],current.ypoints[j]);
				point = transform(point, degrees);
			}
		}
	}
	
	private Point.Double transform (Point.Double pos, double degrees) {
		Point.Double newPoint = new Point.Double(pos.x,pos.y);
		double centerX = (double)Board.MAX_X/2;
		double centerY = (double)Board.MAX_Y/2;
		newPoint.x = centerX + (Math.cos(Math.toRadians(degrees)) * (pos.x - centerX) - (pos.y - centerY) * Math.sin(Math.toRadians(degrees)));
		newPoint.y = centerY + (Math.cos(Math.toRadians(degrees)) * (pos.y - centerY) + (pos.x - centerX) * Math.sin(Math.toRadians(degrees)));
		return newPoint;
	}
	
	public void draw(Graphics g, ImageObserver observer){
		BufferedImage image;
		try { image = ImageIO.read(new File("images/green-circle.png"));
		int[] x = shapes[0].xpoints;
		int[] y = shapes[0].ypoints;
		g.drawImage(
	            image, 
	            x[0], 
	            y[0], 
	            observer
	    );
        } catch (IOException e) { e.printStackTrace(); } //System.out.println("Error opening image file: " + exc.getMessage()); };
		
	}
	
	
	
}
