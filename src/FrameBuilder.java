import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * FrameBuilder
 * Spring Semester 2017
 * CSCI 1125
 * Homework 5
 * @author Charlie Splittstoser
 */
public class FrameBuilder extends JFrame {
  
  private final int FRAME_WIDTH = 550;
  private final int FRAME_HEIGHT = 550;
  
  JMenuBar menuBar;
  JMenu fileMenu;
  JMenu gameMenu;
  JMenuItem exitItem;
  JMenuItem newGameItem;
  JMenuItem restartGameItem;
  JLabel movesLabel;
  public static JTextField moveCount = new JTextField();
  
  /**
   * Builds all of the components and layout managers for the JFrame 
   */
  public FrameBuilder() {
    super("Memory Game");
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);     
    setMinimumSize(new Dimension(550, 550));
    BorderLayout myBorderLayout = new BorderLayout();
    myBorderLayout.setVgap(5);
    setLayout(myBorderLayout);
    
    GameHandler gameHandler = new GameHandler();
    
    GridLayout grid = new GridLayout(4,4);
    JPanel panel = new JPanel(grid);
    panel.setBackground(Color.decode("#5f6363"));
    add(panel, BorderLayout.CENTER);
    
    
    /* Add a window listener that listens for the window to be closed */
    WindowListener exitListener = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        int value = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
            "Exit Confirmation", JOptionPane.YES_NO_OPTION);
          if(value == JOptionPane.YES_OPTION)
            System.exit(0);
      }
    };
  
    addWindowListener(exitListener);
  
    buildMenuBar(gameHandler, panel);
    setJMenuBar(menuBar);
    
    newGame(gameHandler, panel);
    
    /* Build the top panel that displays move count */
    JPanel mypanel = new JPanel();
    mypanel.setLayout(new FlowLayout(0));
    movesLabel = new JLabel("Total Moves: ");
    moveCount.setColumns(2);
    mypanel.add(movesLabel, BorderLayout.WEST);
    mypanel.add(moveCount, BorderLayout.CENTER);
    moveCount.setEditable(false);
    
    add(mypanel, BorderLayout.NORTH);
    
    setVisible(true); 
    
  } //End Constructor
  
  
  /**
   * Creates a new memory game
   * @param gameHandler - holds info about cards
   * @param panel - the panel to add memory cards to
   */
  public void newGame(GameHandler gameHandler, JPanel panel) {
    Random rng = new Random();
    ArrayList<Integer> imageList = new ArrayList<Integer>();
    
    for (int i = 0; i < 8; i++) {
      imageList.add(i);
      imageList.add(i);
    }
    
    for(int i = 0; i < 16; i++) {
     
      /* Assign id and image to cards randomly */
      int imageNumber = rng.nextInt(imageList.size());
      System.out.println("Image " + i + ": " + imageList.get(imageNumber));
      gameHandler.getCards().add(new MemoryCard(imageList.get(imageNumber), gameHandler));
      imageList.remove(imageNumber);
      moveCount.setText("0");
    
      panel.add(gameHandler.getCards().get(i).getCard());
      
    }
  }
  
  /**
   * Adds the menubar to the frame
   * @param gameHandler - holds info about the memory cards
   * @param panel - the panel that will hold the menu bar
   */
  void buildMenuBar(GameHandler gameHandler, JPanel panel) {
    menuBar = new JMenuBar();
    buildFileMenu();
    buildGameMenu(gameHandler, panel);
    menuBar.add(fileMenu);
    menuBar.add(gameMenu);
   }
   
  /**
   * Creates the exit item for the file menu
   */
   void buildFileMenu() {
    // Create an Exit menu item.
    exitItem = new JMenuItem("Exit");
    exitItem.setMnemonic(KeyEvent.VK_X);
    exitItem.addActionListener(new ExitListener());
    fileMenu = new JMenu("File");
    fileMenu.add(exitItem);
   }
   
   
   /**
    * Creates the menu items for the game menu
    * @param gameHandler - holds info about the memory cards
    * @param panel - the panel we are adding the game menu to
    */
   void buildGameMenu(GameHandler gameHandler, JPanel panel) {
     newGameItem = new JMenuItem("New Game");
     newGameItem.addActionListener(new NewGameListener(gameHandler, this, panel));
     restartGameItem = new JMenuItem("Restart Game");
     restartGameItem.addActionListener(new RestartListener(gameHandler));
     gameMenu = new JMenu("Game");
     gameMenu.add(newGameItem);
     gameMenu.add(restartGameItem);
   }
   
   
   
   /**
    * ExitListener - Handles what happens when the exit menu item is selected
    */
   private class ExitListener implements ActionListener {
     @Override
     public void actionPerformed(ActionEvent e) {
       int value = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
           "Exit Confirmation", JOptionPane.YES_NO_OPTION);
         if(value == JOptionPane.YES_OPTION)
           System.exit(0);
     }
   }
   
   /**
    * RestartListener - Handles what happens when the restart menu item is selected
    */
   private class RestartListener implements ActionListener {
     
     private GameHandler gameHandler;
     
     RestartListener(GameHandler gameHandler) {
       this.gameHandler = gameHandler;
     }
     @Override
     public void actionPerformed(ActionEvent e) {
       gameHandler.restartGame();
       moveCount.setText("0");
     }
   }

   
   /**
    * Action listener that handles new game actions
    *
    */
private class NewGameListener implements ActionListener {
     
     private GameHandler gameHandler;
     private FrameBuilder frame;
     private JPanel panel;
     
     /**
      * 
      * @param gameHandler - holds card data
      * @param frame - reference to the JFrame class
      * @param panel - panel to add new memory cards to
      */
     NewGameListener(GameHandler gameHandler, FrameBuilder frame, JPanel panel) {
       this.gameHandler = gameHandler;
       this.frame = frame;
       this.panel = panel;
     }
     @Override
     public void actionPerformed(ActionEvent e) {
       gameHandler.newGame(gameHandler, panel);
       frame.repaint();
       frame.newGame(gameHandler, panel);
       frame.revalidate();
     }
   }

}
