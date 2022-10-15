
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
    
    // keep a timer object that triggers actionPerformed() can be access in other method
    private Timer timer;
    private Player player;
    
    //the obstacle array
    public static Obstacle[] obstacles = new Obstacle[2];
    
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
        
        obstacles[0] = new Obstacle(new Point.Double(100,100), 1, "player.png", new Model(new int[]{0,10,0,10}, new int[]{0,0,10,10}));
        obstacles[1] = new Obstacle(new Point.Double(100,150), 1, "coin.png", new Model(new int[]{0,10,0,10}, new int[]{0,0,10,10}));
        sides[0] = new Side(obstacles, 1);
        //sides[1] = new Side(obstacles, 1);
        
        //obstacles[2] = new Obstacle(new Point.Double(300,600), 1, "player.png", new Model(new int[]{0,64,0,64}, new int[]{0,0,64,64}));

        
        // this timer will call the actionPerformed() method every DELAY ms
        //somehow this references actionPerformed()
        timer = new Timer(DELAY, this);
        timer.start();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.

        // updates each objects each tick
        player.tick();
        sides[0].rotate(1);
        //object.tck();

        //this updates all the graphics by calling the paintComponent() method
        repaint();
    }
    
    private void tickAll() {
    	player.tick();
    	sides[0].rotate(1);
    	repaint();
    }
    
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver 
        // because Component implements the ImageObserver interface, and JPanel 
        // extends from Component. So "this" Board instance, as a Component, can 
        // react to imageUpdate() events triggered by g.drawImage()
 
        // draw our graphics.
        //drawScore(g);
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

    
    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent press) {
        // when player presses key call method of that player
    	//this method will set them pushing the key to true
        player.keyPressed(press);
    }

    @Override
    public void keyReleased(KeyEvent release) {
    	// when player releases key call method of that player
    	// this method will set them pushing the key to false
    	player.keyReleased(release);
    }
    
   
    
}