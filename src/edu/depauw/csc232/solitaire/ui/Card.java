////////////////////////////////////////////////////////////////////////////////
// File:             Card.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.ui;

import edu.depauw.csc232.solitaire.model.Rank;
import edu.depauw.csc232.solitaire.model.Suit;

/**
 * Represent a card from an ordinary 52-card deck, including a rank, a suit, and
 * whether the card is face-up or face-down. The rank and suit are immutable,
 * but the face-up/face-down status may be changed (but only within the ui
 * package).
 * 
 * @author bhoward
 */
public class Card
{
   /**
    * Construct a card with the given rank and suit; it is initially face-down.
    * 
    * @param rank
    * @param suit
    */
   public Card(Rank rank, Suit suit)
   {
      this.rank = rank;
      this.suit = suit;
      this.faceUp = false;
   }

   /**
    * Construct a card with the given rank, suit, and face-up status.
    * 
    * @param rank
    * @param suit
    * @param faceUp
    *           true if the card is initially face-up
    */
   public Card(Rank rank, Suit suit, boolean faceUp)
   {
      this.rank = rank;
      this.suit = suit;
      this.faceUp = faceUp;
   }

   /**
    * Change the state (face-up/face-down) by flipping the card over.
    */
   void flip()
   {
      faceUp = !faceUp;
   }

   public String getAbbrev()
   {
      return "" + rank.getAbbrev() + suit.getAbbrev();
   }

   public Rank getRank()
   {
      return rank;
   }

   public Suit getSuit()
   {
      return suit;
   }

   /**
    * @return the value (1 to 13) corresponding to this card's rank
    */
   public int getValue()
   {
      return rank.getValue();
   }

   public boolean isFaceUp()
   {
      return faceUp;
   }

   /**
    * @return true if this card's suit is red (heart or diamond)
    */
   public boolean isRed()
   {
      return suit.isRed();
   }

   @Override
   public String toString()
   {
      return "Card [rank=" + rank + ", suit=" + suit + ", faceUp=" + faceUp
         + "]";
   }

   private final Rank rank;
   private final Suit suit;
   private boolean faceUp;
}
