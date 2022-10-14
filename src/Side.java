import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class Side {
	//Array of obstacles to draw on the side
	private Obstacle[] obstacles;
	//number 0-3 which represents which side of the board this is; starts at left side and goes clockwise
	private int whichSide;
	private Graphics g;
	private ImageObserver observer;
	
	public Side(Obstacle[] obstacles, int whichSide, Graphics ng, ImageObserver nobserver) {
		this.obstacles = obstacles;
		this.whichSide = whichSide;
		g = ng;
		observer = nobserver;
	}
	
	public Obstacle[] getObstacles(){
		return obstacles;
	}
	
	public int getWhichSide(){
		return whichSide;
	}
	
	public void setObstacles(Obstacle[] obstacles) {
		this.obstacles = obstacles;
	}
	
//	public 
	
	public void setWhichSide(int nwhichSide) {
		this.whichSide = nwhichSide;
	}
	
	public void drawObbies() {
		for (Obstacle obby: obstacles) {
			obby.draw(g, observer);
		}
	}
}
