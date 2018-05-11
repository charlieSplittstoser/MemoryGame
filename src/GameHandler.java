import java.util.ArrayList; 
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * GameHandler
 * Spring Semester 2017
 * CSCI 1125
 * Homework 5
 * @author Charlie Splittstoser
 */
public class GameHandler {

/* Stores the memory cards that are used in the game */  
private ArrayList<MemoryCard> memoryCards = new ArrayList<MemoryCard>();
  
  private int previousCardId = -1;
  private int totalMoves = 0;
  private MemoryCard previousCard;
  private int numMatches = 0;
  private boolean canClick = true;
  
  
  /**
   * Get the memory cards
   * @return the arrayList containing the memory cards
   */
  public ArrayList<MemoryCard> getCards() {
    return memoryCards;
  }
  
  public boolean canClick() {
    return canClick;
  }
  
  public void setPreviousCardId(int id) {
    previousCardId = id;
  }
  
  /**
   *  Flip all the cards upside down 
   */
  public void restartGame() {
    for (int i = 0; i < 16; i++) {
      memoryCards.get(i).flipCardBack();
    }
    totalMoves = 0;
  } 
  
  /**
   *  Remove all cards and re add them 
   */
  public void newGame(GameHandler gameHandler, JPanel panel) {
    for(int i = 15; i >= 0; i--) {
      panel.remove(memoryCards.get(i).getCard());
      memoryCards.remove(i);
    }
    totalMoves = 0;
  }
  
  /* Check to see if all the cards have been paired up */
  public void checkForWin() {
    if (numMatches == 8) {
      JOptionPane.showMessageDialog(null, "Congratulations, you have completed the game in " + 
          totalMoves + " moves!",
          "Winner!", JOptionPane.PLAIN_MESSAGE);
    }
  }
  
  
  /**
   * Flip both cards back and hide their images
   * @param card1 - the first card to flip back
   * @param card2 - the second card to flip back
   */
  private void flipCardsBack(MemoryCard card1, MemoryCard card2) {
    
    canClick = false;
  
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        card1.flipCardBack();
        card2.flipCardBack();
        canClick = true;
      }
    }, 2000); 
  }
  
  
  /**
   * Handles what happens when a pair of cards are chosen.
   * @param memoryCard - The last memory card that was clicked.
   */
  public void processCardPair(MemoryCard memoryCard) {
    if (previousCardId == -1) {
      previousCard = memoryCard;
      previousCardId = memoryCard.getCardId();
    } else {
      totalMoves++;
      if(memoryCard.getCardId() != previousCardId) {
        flipCardsBack(memoryCard, previousCard);
      } else {
        numMatches++;
      }
      previousCardId = -1;
      FrameBuilder.moveCount.setText("" + totalMoves);
      checkForWin();
    }
    
  }
}
