import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;



public class Obstacle extends Entity {
	
	public Obstacle (Point.Double npos, double nsize, String nfileName, Model nmodel) {
		super (npos, nsize, nfileName, nmodel, 0);
	}
	
	public void drawNew(int x, int y, int rotation, Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
		AffineTransform transform = new AffineTransform();
	    transform.rotate(Math.toRadians(rotation), super.image.getWidth() / 2, super.image.getHeight() / 2);
        g.drawImage(
            image, 
            (int)pos.x, // * Board.TILE_SIZE
            (int)pos.y, // * Board.TILE_SIZE
            observer
        );
    }
	
	public void rotate (double degrees, double rotation) {
		Point.Double newPoint = new Point.Double(pos.x,pos.y);
		System.out.println(newPoint.x + ", " + newPoint.y);
		double centerX = (double)Board.MAX_X/2;
		System.out.println(centerX);
		double centerY = (double)Board.MAX_Y/2;
		newPoint.x = centerX + (Math.cos(Math.toRadians(degrees)) * (pos.x - centerX) - (pos.y - centerY) * Math.sin(Math.toRadians(degrees)));
		newPoint.y = centerY + (Math.cos(Math.toRadians(degrees)) * (pos.y - centerY) + (pos.x - centerX) * Math.sin(Math.toRadians(degrees)));
		
		pos = newPoint;
//		model.rotate(degrees);
		model.move(pos.x, pos.y, rotation);
		
	}
	
}
