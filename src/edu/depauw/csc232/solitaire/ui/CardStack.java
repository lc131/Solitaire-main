////////////////////////////////////////////////////////////////////////////////
// File:             CardStack.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.depauw.csc232.solitaire.model.Rank;
import edu.depauw.csc232.solitaire.model.Suit;

/**
 * A CardStack is an object that displays a collection of cards. It keeps track
 * of the position and image to be displayed. It is a common superclass of Pile
 * (stack of cards on a Table) and Packet (stack of cards being dragged). Note
 * that the methods that modify a CardStack are all package-private, so they may
 * not be used from outside the ui package. A
 * {@link edu.depauw.csc232.solitaire.ui.CardMover CardMover} must be used to
 * make changes to a CardStack.
 * 
 * @author bhoward
 */
public abstract class CardStack
{
   /**
    * Construct an initially empty CardStack, where each successive card will be
    * offset by the given amounts horizontally and vertically.
    * 
    * @param horizontal
    * @param vertical
    */
   protected CardStack(int horizontal, int vertical)
   {
      this.cards = new ArrayList<>();
      this.xOFFSET = horizontal * HOFFSET;
      this.yOFFSET = vertical * VOFFSET;
   }

   /**
    * Add one card to this stack.
    * 
    * @param card
    */
   void add(Card card)
   {
      cards.add(card);
      invalidateImage();
   }

   /**
    * Add all of the cards from a collection to this stack.
    * 
    * @param other
    */
   void addAll(List<Card> other)
   {
      cards.addAll(other);
      invalidateImage();
   }

   /**
    * Add all 13 cards of the given suit to this collection, in order from Ace
    * to King.
    * 
    * @param suit
    */
   void addSuit(Suit suit)
   {
      for (Rank rank : Rank.values()) {
         add(new Card(rank, suit));
      }
      invalidateImage();
   }

   /**
    * Deal one card off the top of this stack.
    * 
    * @return the card
    */
   Card deal()
   {
      Card card = cards.remove(cards.size() - 1);
      invalidateImage();
      return card;
   }

   /**
    * @return the bottom card in the stack
    */
   public Card getBottom()
   {
      return cards.get(0);
   }

   protected Image getCachedImage()
   {
      return cachedImage;
   }

   /**
    * Return the card at the given index, counting from the top of this stack at
    * index 1.
    * 
    * @param index
    * @return
    */
   public Card getFromTop(int index)
   {
      return cards.get(cards.size() - index);
   }

   /**
    * @return the image to display for this stack
    * 
    * @param images
    */
   public Image getImage(CardImages images)
   {
      if (cachedImage == null) {
         if (cards.isEmpty()) {
            cachedImage = images.getImage(null);
         }
         else if (xOFFSET == 0 && yOFFSET == 0) {
            cachedImage = images.getImage(getTop());
         }
         else {
            Image top = images.getImage(getTop());
            int width = top.getWidth(null) + xOFFSET * (cards.size() - 1);
            int height = top.getHeight(null) + yOFFSET * (cards.size() - 1);
            cachedImage = new BufferedImage(width, height,
                     BufferedImage.TYPE_INT_ARGB);

            Graphics g = cachedImage.getGraphics();
            for (int i = 0; i < cards.size(); i++) {
               Card card = cards.get(i);
               Image cardImage = images.getImage(card);
               g.drawImage(cardImage, xOFFSET * i, yOFFSET * i, null);
            }
         }
      }

      return cachedImage;
   }

   /**
    * @return the top card in the stack
    */
   public Card getTop()
   {
      return cards.get(cards.size() - 1);
   }

   /**
    * @return the current x-coordinate of the upper-left corner of this stack
    */
   public int getX()
   {
      return x;
   }

   /**
    * @return the current y-coordinate of the upper-left corner of this stack
    */
   public int getY()
   {
      return y;
   }

   /**
    * This should be called whenever the underlying collection of cards has
    * changed.
    */
   protected void invalidateImage()
   {
      cachedImage = null;
   }

   /**
    * @return true if the stack is empty
    */
   public boolean isEmpty()
   {
      return cards.isEmpty();
   }

   /**
    * @param x
    *           the new x-coordinate of the upper-left corner of this stack
    */
   public void setX(int x)
   {
      this.x = x;
   }

   /**
    * @param y
    *           the new y-coordinate of the upper-left corner of this stack
    */
   public void setY(int y)
   {
      this.y = y;
   }

   /**
    * Shuffle the cards in this stack.
    */
   void shuffle()
   {
      Collections.shuffle(cards);
      invalidateImage();
   }

   /**
    * @return the number of cards in the stack
    */
   public int size()
   {
      return cards.size();
   }

   private int x;
   private int y;

   protected final int xOFFSET;
   protected final int yOFFSET;

   protected final List<Card> cards;
   private Image cachedImage;

   protected static final int HOFFSET = 12;

   protected static final int VOFFSET = 18;
}
