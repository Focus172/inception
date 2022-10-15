import javax.swing.*;

//This is the main method that is called to run the game
class App {
	
	//this runs the game
    public static void main(String[] args) {
        //https://stackoverflow.com/a/22534931/4655368
    	
        //this calls the initWindow() method once
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });
    	
    }
    
    private static void initWindow() {
        // create a window frame and set the title in the toolbar
        JFrame window = new JFrame("Inception");
        
        // when we close the window, stop the app
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the j-panel to draw on.
        // this also initializes the game loop
        Board board = new Board();
        
        //adds the board, keyboard, non-resizable, and 
        //fits components to window, puts it in middle and makes it visible
        window.add(board);
        window.addKeyListener(board);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
    }
    
}