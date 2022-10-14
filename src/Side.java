import java.awt.Graphics;

public class Side {
	//Array of obstacles to draw on the side
	private Obstacle[] obstacles;
	//number 0-3 which represents which side of the board this is; starts at left side and goes clockwise
	private int whichSide;
	
	public Side(Obstacle[] obstacles, int whichSide) {
		this.obstacles = obstacles;
		this.whichSide = whichSide;
	}
	
	public Obstacle[] getObstacles{
		return obstacles;
	}
	
	public int getWhichSide{
		return whichSide;
	}
	
	public void setObstacles(Obstacle[] obstacles) {
		this.obstacles = obstacles;
	}
	
	public 
	
	public void setWhichSide(int whichSide) {
		this.whichSide = whichSide;
	}
	
	public boolean draw() {
		for (Obstacle obby: obstacles) {
			obby.draw();
		}
	}
}
