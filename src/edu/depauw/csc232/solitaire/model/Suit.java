////////////////////////////////////////////////////////////////////////////////
// File:             Suit.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.model;

/**
 * Represent the suit of an ordinary playing card: clubs, diamonds, hearts, or
 * spades. Each suit also holds a one-character abbreviation.
 * 
 * @author bhoward
 */
public enum Suit
{
   Clubs('C'), Diamonds('D'), Hearts('H'), Spades('S');

   Suit(char abbrev)
   {
      this.abbrev = abbrev;
   }

   /**
    * @return the abbreviation for this suit
    */
   public char getAbbrev()
   {
      return abbrev;
   }

   /**
    * @return true if this card is red
    */
   public boolean isRed()
   {
      return this == Diamonds || this == Hearts;
   }

   private final char abbrev;
}
