
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
        //coins = populateCoins();
        sides = new Side[4];
//        obstacles = populateObstacles();
        
        Obstacle[] obstacles = new Obstacle[2];
        obstacles[0] = new Obstacle(new Point.Double(15,15), 1, "player.png", new Model(new int[]{0,10,0,10}, new int[]{0,0,10,10}));
        obstacles[1] = new Obstacle(new Point.Double(15,50), 1, "player.png", new Model(new int[]{0,10,0,10}, new int[]{0,0,10,10}));
        sides[0] = new Side(obstacles, 1);
        
        
        
        obstacles[1] = new Obstacle(new Point.Double(15,50), 1, "player.png", new Model(new int[]{0,10,0,10}, new int[]{0,0,10,10}));
        sides[1] = new Side(obstacles, 1);

        
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
    
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver 
        // because Component implements the ImageObserver interface, and JPanel 
        // extends from Component. So "this" Board instance, as a Component, can 
        // react to imageUpdate() events triggered by g.drawImage()
 
        // draw our graphics.
        //drawScore(g);
        player.draw(g, this);
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
    
    private void tickAll() {
    	player.tick();
    	
    }

    /*
    private void drawScore(Graphics g) {
        // set the text to be displayed
        String text = "$" + player.getScore();
        // we need to cast the Graphics to Graphics2D to draw nicer text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        // set the text color and font
        g2d.setColor(new Color(30, 201, 139));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));
        // draw the score in the bottom center of the screen
        // https://stackoverflow.com/a/27740330/4655368
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // the text will be contained within this rectangle.
        // here I've sized it to be the entire bottom row of board tiles
        Rectangle rect = new Rectangle(0, TILE_SIZE * (MAX_Y - 1), TILE_SIZE * MAX_X, TILE_SIZE);
        // determine the x coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // determine the y coordinate for the text
        // (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // draw the string
        g2d.drawString(text, x, y);
    }
    */

    /*
    private ArrayList<Coin> populateCoins() {
        ArrayList<Coin> coinList = new ArrayList<>();
        Random rand = new Random();

        // create the given number of coins in random positions on the board.
        // note that there is not check here to prevent two coins from occupying the same
        // spot, nor to prevent coins from spawning in the same spot as the player
        for (int i = 0; i < NUM_COINS; i++) {
            int coinX = rand.nextInt(MAX_X);
            int coinY = rand.nextInt(MAX_Y);
            coinList.add(new Coin(coinX, coinY));
        }

        return coinList;
    }
    
    private ArrayList<Coin> populateObstacles() {
        ArrayList<Coin> obstacleList = new ArrayList<>();
        Random rand = new Random();

        // create the given number of coins in random positions on the board.
        // note that there is not check here to prevent two coins from occupying the same
        // spot, nor to prevent coins from spawning in the same spot as the player
        for (int i = 0; i < NUM_COINS; i++) {
            int topLeft = rand.nextInt();
            int coinY = rand.nextInt(ROWS);
//            obstacleList.add(new Coin(coinX, coinY));
        }

        return obstacleList;
    }


    private void collectCoins() {
        // allow player to pickup coins
        ArrayList<Coin> collectedCoins = new ArrayList<>();
        for (Coin coin : coins) {
            // if the player is on the same tile as a coin, collect it
            if (player.getPos().equals(coin.getPos())) {
                // give the player some points for picking this up
                player.addScore(100);
                collectedCoins.add(coin);
            }
        }
        // remove collected coins from the board
        coins.removeAll(collectedCoins);
    }
    */

}