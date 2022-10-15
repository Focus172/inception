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
	
	public boolean collision (Model other) {
		boolean out = false;
		for (Polygon shape : shapes) {
			for (Polygon otherShape: other.shapes) {
				Area area = new Area(shape);
				area.intersect(new Area(otherShape));
				if (area.isEmpty() == true) {
					out = true;
				}
			}
		}
		return out;
	}
	
	public void rotate(int degrees) {
		for (int i = 0; i < shapes.length; i++) {
			Polygon current = shapes[i];
			for (int j = 0; j < current.xpoints.length; j++) {
				Point.Double point = new Point.Double (current.xpoints[j],current.ypoints[j]);
				point = transform(point, degrees);
			}
		}
	}
	
	private Point.Double transform (Point.Double point, int degrees) {
		
		point.x = (Math.cos(Math.toRadians(degrees)) * point.x - point.y * Math.sin(Math.toRadians(degrees)));
		point.y = (Math.cos(Math.toRadians(degrees)) * point.y + point.x * Math.sin(Math.toRadians(degrees)));
		return point;
	}
	
	
	
}
