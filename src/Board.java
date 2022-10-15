
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
//import java.util.Random;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, KeyListener {

    // controls the frame rate by setting delay between ticks
    private final int DELAY = 25;
    private final double FPS = 1000.0 / DELAY;
    
    // controls the size of the board
    public static final int MAX_X = 800;
    public static final int MAX_Y = 800;
    
    public static final int HEALTH_BAR_Y = 25;

    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;
    
    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    
    // objects that appear on the game board
    private Player player;
    //private ArrayList<Coin> coins;
    private ArrayList<Obstacle> obstacles;
    
    private Side[] sides;
    

    public Board() {
        // set the game board size
        setPreferredSize(new Dimension(MAX_X, MAX_Y+HEALTH_BAR_Y));
        // set the game board background color
        setBackground(new Color(232, 232, 232));

        //g.fillRect(x, y, length, width);
        
        // initialize the game state
        player = new Player();
        
        sides = new Side[4];
        
        // this timer will call the actionPerformed() method every DELAY ms
        //somehow this references actionPerformed()
        timer = new Timer(DELAY, this);
        timer.start();
        
    }
    
    private int levelNumber = 0;
    private int numberOfLevels = 4;
    private int selctedLevel = 1;
    
    private int levelChangeCooldown = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.
    	
    	

    	
    	
    	
    	if (levelNumber == 0) {
    		//show the level select
    		
    		player.tick(null);
    		//this needs to be redone to not rely on player
    		
    		if (levelChangeCooldown > 0) { levelChangeCooldown--; }
    		
    		
    		if (player.downPressed && !player.upPressed) {
    			if (selctedLevel < numberOfLevels && levelChangeCooldown <= 0) {
    				selctedLevel++;
    				levelChangeCooldown = 1;
    			}
    		}
    		
    		if (player.upPressed && !player.downPressed) {
    			if (selctedLevel > 1 && levelChangeCooldown <= 0) {
    				selctedLevel--;
    				levelChangeCooldown = 1;
    			}
    		}

    		
    		
    		if (player.spacePressed) {
    			levelNumber = selctedLevel;
    		} else {
    			//level number should still be zero
    			repaint();
    		}
    			
    			
    		
    		//when player chooses a level
    		
    		
    		
    		
    		
    		if (levelNumber == 1) {
    			
    			levelNumber = selctedLevel;
    			
    			Obstacle[] obstacles1 = new Obstacle[2];
    			obstacles1[0] = new Obstacle(new Point.Double(400,200), 1, "player.png", new Model(new int[]{400,410,410,400}, new int[]{200,200,210,210}));
    			obstacles1[1] = new Obstacle(new Point.Double(400,150), 1, "coin.png", new Model(new int[]{400,410,410,400}, new int[]{150,150,160,160}));
    			sides[0] = new Side(obstacles1, 0);
    	        
    	        Obstacle[] obstacles2 = new Obstacle[0];
    	        sides[1] = new Side(obstacles2, 1);
    	        
    	        Obstacle[] obstacles3 = new Obstacle[0];
    	        sides[2] = new Side(obstacles3, 2);
    	        
    	        Obstacle[] obstacles4 = new Obstacle[0];
    	        sides[3] = new Side(obstacles4, 3);
    			
    		}
    		
    		else if (levelNumber == 2) {
    			
    			levelNumber = selctedLevel;
    			
    			Obstacle[] obstacles1 = new Obstacle[0];
    			sides[0] = new Side(obstacles1, 0);
    	        
    	        Obstacle[] obstacles2 = new Obstacle[0];
    	        sides[1] = new Side(obstacles2, 1);
    	        
    	        Obstacle[] obstacles3 = new Obstacle[0];
    	        sides[2] = new Side(obstacles3, 2);
    	        
    	        Obstacle[] obstacles4 = new Obstacle[0];
    	        sides[3] = new Side(obstacles4, 3);
    			
    		}
    		
    		
    		
    	} else {
    		// updates each objects each tick
    		tickAll();
            //object.tick();

            //this updates all the graphics by calling the paintComponent() method
            repaint();
    	}  
    }
    
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver 
        // because Component implements the ImageObserver interface, and JPanel 
        // extends from Component. So "this" Board instance, as a Component, can 
        // react to imageUpdate() events triggered by g.drawImage()
        
        
        if (levelNumber == 0) {
        	//paint the level select
        	
        	g.setColor(new Color(30, 201, 139));
        	
        	int thingSize = 50;
        	
        	for (int i = 1; i <= numberOfLevels; i++) {
        		g.setColor(new Color(30, 201, 139));
        		g.fillRect(thingSize, i*thingSize, MAX_X-2*thingSize, thingSize);
        		
        		g.setFont(new Font("Lato", Font.BOLD, thingSize));
            	g.setColor(new Color(0, 0, 0));
            	g.drawString("Level "+i, thingSize, (i+1)*thingSize);
            	
            	if (i == selctedLevel) {
            		g.setColor(new Color(255, 0, 0));
            		g.fillRect(0, i*thingSize, thingSize, thingSize);
            	}
        	}

        	// this smooths out animations on some systems
        	Toolkit.getDefaultToolkit().sync();
        	
        } else {
        
        	// draw our graphics.
        	player.draw(g, this, 0);
        	sides[0].draw(g, this);
        
        	//drawing the health

        	// set the text to be displayed
        	String text = player.health + "/100";
            
        	// we need to cast the Graphics to Graphics2D to draw nicer text

        	// set the text color and font
        
        	//filling green section
        	g.setColor(new Color(30, 201, 139));
        	g.fillRect(0, MAX_Y, (int)((player.health/100.0)*MAX_X), HEALTH_BAR_Y);
        
        	//filling red section
        	g.setColor(new Color(200, 0, 0));
        	g.fillRect((int)((player.health/100.0)*MAX_X), MAX_Y, MAX_X, HEALTH_BAR_Y);
        
        	g.setFont(new Font("Lato", Font.BOLD, 25));
        	g.setColor(new Color(0, 0, 0));
        	g.drawString(text, 0, MAX_Y+HEALTH_BAR_Y);

        	// this smooths out animations on some systems
        	Toolkit.getDefaultToolkit().sync();
        }
    }

    
    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent press) {
        // when player presses key call method of that player
    	// will set pushingKey to true
        player.keyPressed(press);
    }

    @Override
    public void keyReleased(KeyEvent release) {
    	// when player releases key call method of that player
    	// will set the pushingKey to false
    	player.keyReleased(release);
    }
    
    private void tickAll() {
    	
    	sides[0].rotate(1);
    	
    	ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    	for (int i = 0; i < sides.length; i++) {
    		Obstacle[] imported = sides[i].getObstacles();
    		for (Obstacle obstacle: imported) {
    			obstacles.add(obstacle);
    		}
    	}
    	
    	player.tick(obstacles);
    	
    }

}