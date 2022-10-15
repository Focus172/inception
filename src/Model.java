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
import java.awt.geom.Area;

public class Model {

	
	public Polygon[] shapes;
	public Polygon[] originalShapes;
	
	public ArrayList<Point> locations; //debug
	
	public Model (Polygon[]nshapes) {
		originalShapes = nshapes;
		shapes = nshapes;
		locations = new ArrayList<Point>();
	}
	
	public Model (int[]xvals, int[]yvals) {
		originalShapes = new Polygon[1];
		originalShapes[0] = new Polygon(xvals, yvals, xvals.length);
		shapes = new Polygon[1];
		shapes[0] = new Polygon(xvals, yvals, xvals.length);
		locations = new ArrayList<Point>();
	}
	
	public void move (double x, double y, double rotation) { //assumes first corner in polygon is upper left hand corner of the image
		
		shapes[0].xpoints[0] = (int) x;
		shapes[0].ypoints[0] = (int) y;
		double cos = Math.cos(Math.toRadians(rotation));
		double sin = Math.sin(Math.toRadians(rotation));
		int originaly = originalShapes[0].ypoints[0];
		int originalx = originalShapes[0].xpoints[0];
		for (int j = 0; j < shapes.length; j++) {
			for (int i = 1; i < originalShapes[j].xpoints.length; i++) {
				double height = originalShapes[j].ypoints[i] - originaly;
				double width = originalShapes[j].xpoints[i] - originalx;
				double radius = Math.sqrt(height*height + width*width);
				System.out.println("radius: " + radius);
				shapes[j].ypoints[i] = (int)(-height * cos + width * sin + y);
				shapes[j].xpoints[i] = (int)(height * sin + width * cos + x);
				System.out.println(height * cos + width * sin + " + " + y);
				System.out.println(height * sin + width * cos + " + " + x);
			}
		}
	}
	
	public boolean collision (Model other) {
		boolean out = false;
		for (Polygon shape : shapes) {
			for (Polygon otherShape: other.shapes) {
				Area area = new Area(shape);
				area.intersect(new Area(otherShape));
				if (area.isEmpty() == false) {
					out = true;
					//System.out.println("CORRECT\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nCORRECT");
				}
			}
		}
		return out;
	}
	
//	public void rotate(double degrees) {
//		for (int i = 0; i < shapes.length; i++) {
//			Polygon current = shapes[i];
//			for (int j = 0; j < current.xpoints.length; j++) {
//				Point.Double point = new Point.Double (current.xpoints[j],current.ypoints[j]);
//				point = transform(point, degrees);
//				current.xpoints[j] = (int) point.x;
//				current.ypoints[j] = (int) point.y;
//				
//			}
//		}
//	}
	
	private Point.Double transform (Point.Double pos, double degrees) {
		Point.Double newPoint = new Point.Double(pos.x,pos.y);
		double centerX = ((double)Board.MAX_X/2);
		double centerY = ((double)Board.MAX_Y/2);
		newPoint.x = centerX + (Math.cos(Math.toRadians(degrees)) * (pos.x - centerX) - (pos.y - centerY) * Math.sin(Math.toRadians(degrees)));
		newPoint.y = centerY + (Math.cos(Math.toRadians(degrees)) * (pos.y - centerY) + (pos.x - centerX) * Math.sin(Math.toRadians(degrees)));
		return newPoint;
	}
	
	public void draw(Graphics g, ImageObserver observer){
		BufferedImage image;
		try { image = ImageIO.read(new File("images/green-circle.png"));
		int[] x = shapes[0].xpoints;
		int[] y = shapes[0].ypoints;
		
//		System.out.println("")
		g.drawImage(
	            image, 
	            x[0], 
	            y[0], 
	            observer
	    );
		g.drawPolygon(shapes[0].xpoints, shapes[0].ypoints, 4);
		
		locations.add(new Point(x[0],y[0]));
		int[] xtests = new int[locations.size()];
		int[] ytests = new int[locations.size()];
		for (int i = 0; i < locations.size(); i++) {
			xtests[i] = locations.get(i).x;
			ytests[i] = locations.get(i).y;
		}
		g.drawPolygon(xtests, ytests, xtests.length);
		
        } catch (IOException e) { e.printStackTrace(); } //System.out.println("Error opening image file: " + exc.getMessage()); };
		
	}
	
	
	
}
