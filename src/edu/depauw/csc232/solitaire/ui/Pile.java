////////////////////////////////////////////////////////////////////////////////
// File:             Pile.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.ui;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * A Pile is a CardStack located on a game Table. It has a PileStrategy to be
 * able to respond to mouse clicks and drag/drop events.
 * 
 * @author bhoward
 */
public class Pile extends CardStack
{
   private Pile(PileStrategy strategy, int horizontal, int vertical)
   {
      super(horizontal, vertical);
      this.strategy = strategy;
   }

   /**
    * Return whether the given pile may be dragged. This could be used to change
    * the mouse appearance, for example. To avoid lag, this should do minimal
    * computation and return quickly.
    * 
    * @param event
    *           the mouseDragged event being checked
    * @return true if this pile will respond to being dragged
    */
   boolean canDrag(MouseEvent event)
   {
      // true if there is at least one card to be dragged, and the strategy says
      // OK
      return !cards.isEmpty() && strategy.checkCanDrag(this);
   }

   /**
    * Return whether the given packet may be dropped on this. To avoid lag, this
    * should do minimal computation and return quickly.
    * 
    * @param packet
    *           the candidate for dropping on this pile
    * @param event
    *           the mouseMoved event being checked
    * @return true if this pile will accept the drop
    */
   boolean canDrop(Packet packet, MouseEvent event)
   {
      return strategy.checkCanDrop(this, packet);
   }

   /**
    * Perform any necessary post-drag cleanup.
    * 
    * @param target
    * @param mover
    * @param event
    */
   void finishDrag(Pile target, CardMover mover, MouseEvent event)
   {
      strategy.finishDrag(this, target, mover);
   }

   /**
    * Respond to a mouse click on this pile.
    * 
    * @param mover
    *           the CardMover that is allowed to move cards between Piles for
    *           this click
    * @param event
    *           the mouseClicked event
    */
   void handleClick(CardMover mover, MouseEvent event)
   {
      int n = identifyCard(event);
      int numCards = (n == -1) ? 0 : (size() - n);

      strategy.handleClick(this, numCards, mover);
   }

   private int identifyCard(MouseEvent event)
   {
      // Determine the index of the selected card
      int dx = event.getX() - getX();
      int dy = event.getY() - getY();

      int top = cards.size() - 1;
      int xSelect = (xOFFSET == 0) ? top : Math.min(dx / xOFFSET, top);
      int ySelect = (yOFFSET == 0) ? top : Math.min(dy / yOFFSET, top);

      return Math.min(xSelect, ySelect);
   }

   /**
    * Return a Packet that will be dragged with the mouse for a potential drop.
    * Return null to abort the drag.
    * 
    * @param event
    *           the initial mouseDragged event recognized as a drag
    * @return the packet to be dragged; null to abort
    */
   Packet startDrag(MouseEvent event)
   {
      int n = identifyCard(event);

      // Get a sublist of the selected cards to see if they may be dragged
      List<Card> selected = cards.subList(n, cards.size());

      // Check that the collection is OK to drag
      if (!strategy.checkStartDrag(this, selected)) {
         return null;
      }

      // Create a new collection with the selected cards; remove from this Pile
      Packet packet = new Packet(this, selected, xOFFSET / HOFFSET,
               yOFFSET / VOFFSET);

      packet.setX(getX() + n * xOFFSET);
      packet.setY(getY() + n * yOFFSET);
      return packet;
   }

   /**
    * Programmatically try to drag the given number of cards from the origin to
    * this Pile. Use the given CardMover to move the cards.
    * 
    * @param origin
    * @param numCards
    * @param mover
    * @return true if successful
    */
   public boolean tryDrag(Pile origin, int numCards, CardMover mover)
   {
      int n = origin.size();
      PileStrategy os = origin.strategy;

      if (os.checkCanDrag(origin) && n >= numCards) {
         List<Card> cards = origin.cards.subList(n - numCards, n);

         if (os.checkStartDrag(origin, cards)) {
            Packet packet = new Packet(origin, cards, 0, 0);

            if (strategy.checkCanDrop(this, packet)) {
               mover.move(packet, this);
               os.finishDrag(origin, this, mover);
               return true;
            }
            else {
               origin.addAll(packet.cards);
            }
         }
      }

      return false;
   }

   /**
    * Return whether this pile is under the location of the given mouse event.
    * 
    * @param event
    *           the mouse event
    * @return true if the event applies to this pile
    */
   boolean underMouse(MouseEvent event)
   {
      // check whether the event is over this pile's image
      Image image = getCachedImage();
      int x = getX();
      int y = getY();
      int ex = event.getX();
      int ey = event.getY();
      return image != null && x <= ex && ex < x + image.getWidth(null)
         && y <= ey && ey < y + image.getHeight(null);
   }

   /**
    * Construct a horizontal Pile for the given strategy.
    * 
    * @param strategy
    * @return a new horizontal Pile
    */
   public static Pile makeHorizontal(PileStrategy strategy)
   {
      return new Pile(strategy, 1, 0);
   }

   /**
    * Construct a squared-up Pile for the given strategy.
    * 
    * @param strategy
    * @return a new squared-up Pile
    */
   public static Pile makeSquared(PileStrategy strategy)
   {
      return new Pile(strategy, 0, 0);
   }

   /**
    * Construct a vertical Pile for the given strategy.
    * 
    * @param strategy
    * @return a new vertical Pile
    */
   public static Pile makeVertical(PileStrategy strategy)
   {
      return new Pile(strategy, 0, 1);
   }

   private final PileStrategy strategy;
}
