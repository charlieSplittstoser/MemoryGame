import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * MemoryCard
 * Spring Semester 2017
 * CSCI 1125
 * Homework 5
 * @author Charlie Splittstoser
 */

public class MemoryCard {

  
  private final String DEFAULT_BACK_COLOR = "#4286f4";
  private final String DEFAULT_FLIPPED_COLOR = "#86c3ef";
  private String cardImageURL = "";
  private JButton card;
  private int cardId;
  private boolean flipped = false;
  
  
  /**
   * Constructor for memory 
   * @param imageNumber
   * @param gameHandler
   */
  public MemoryCard(int imageNumber, GameHandler gameHandler) {
    card = new JButton();
    card.setOpaque(true);
    card.setBackground(Color.decode(DEFAULT_BACK_COLOR));
    card.setBorder(BorderFactory.createLineBorder(Color.decode("#5f6363"), 3));
    card.addActionListener(new ButtonClickListener(gameHandler, this)); 
    cardImageURL = "images/" + imageNumber + ".png";
    cardId = imageNumber;
  }

  
  public JButton getCard() {
    return card;
  }
  
  public int getCardId() {
    return cardId;
  }
  
  
  /**
   * Flip the card back to its background image
   */
  public void flipCardBack() {
    card.setBackground(Color.decode(DEFAULT_BACK_COLOR));
    card.setIcon(null);
    flipped = false;
  }
  
  
  /**
   * Check the flipped variable
   * @return whether or not the card's front is showing
   */
  public boolean isFlipped() {
    return flipped;
  }
  
  
  /**
   * ButtonClickListener
   * Calls card operations when a card is clicked
   *     - i.e. Checking, flipping, etc.
   */
  
  private class ButtonClickListener implements ActionListener {
    
    private GameHandler gameHandler;
    private MemoryCard memoryCard;

    public ButtonClickListener(GameHandler gameHandler, MemoryCard memoryCard) {
        this.gameHandler = gameHandler;
        this.memoryCard = memoryCard;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
     
      /* Flip card to show the front */
      if(!flipped && gameHandler.canClick()) {
        card.setIcon(new ImageIcon(cardImageURL));
        card.setBackground(Color.decode(DEFAULT_FLIPPED_COLOR));
        flipped = true;
        gameHandler.processCardPair(memoryCard);
        
      }
    }
  }
}
