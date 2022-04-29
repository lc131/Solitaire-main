////////////////////////////////////////////////////////////////////////////////
// File:             CardMover.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  (list anyone else other than your instructor who helped)
//                   (describe in detail the the ideas and help they provided)
//
// Online sources:   (include Web URLs and description of any information used)
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.ui;

import java.util.ArrayList;
import java.util.List;

import edu.depauw.csc232.solitaire.model.Suit;

/**
 * A <code>CardMover</code> is an object that is allowed to move cards from one
 * Pile to another. It is the only legal way to do so from outside the ui
 * package, because it is also responsible for keeping track of moves so that
 * they may be undone/redone.
 * 
 * @author bhoward
 */
public class CardMover
{
   // package-protected constructor -- should only be created by Table
   CardMover()
   {
      this.moves = new ArrayList<>();
   }

   /**
    * Add all 52 cards of a standard deck to the Pile. Note: this move cannot be
    * undone, so it should only be called on a CardMover that is going to be
    * completed with Table's initialCommit method.
    * 
    * @param pile
    */
   public void addDeck(Pile pile)
   {
      for (Suit suit : Suit.values()) {
         addSuit(suit, pile);
      }
   }

   /**
    * Add all 13 cards of the given Suit to the Pile. Note: this move cannot be
    * undone, so it should only be called on a CardMover that is going to be
    * completed with Table's initialCommit method.
    * 
    * @param suit
    * @param pile
    */
   public void addSuit(Suit suit, Pile pile)
   {
      pile.addSuit(suit);
   }

   private void doFlipMove(int numCards, Pile source, Pile target)
   {
      for (int i = 0; i < numCards; i++) {
         Card card = source.deal();
         card.flip();
         target.add(card);
      }
   }

   private void doPlainMove(int numCards, Pile source, Pile target)
   {
      List<Card> cards = new ArrayList<>();
      for (int i = 0; i < numCards; i++) {
         cards.add(source.deal());
      }
      for (int i = cards.size() - 1; i >= 0; i--) {
         target.add(cards.get(i));
      }
   }

   /**
    * Move the given number of cards from one Pile to the other, flipping them
    * in the process. The order of the cards will also be reversed, so that the
    * top card of "source" will be under the remaining cards that are moved.
    * 
    * @param numCards
    * @param source
    * @param target
    */
   public void flipMove(int numCards, Pile source, Pile target)
   {
      doFlipMove(numCards, source, target);
      moves.add(new FlipMove(numCards, source, target));
   }

   /**
    * Special case for pulling the top card from a pile, flipping it over, and
    * putting it back.
    * 
    * @param pile
    */
   public void flipTop(Pile pile)
   {
      flipMove(1, pile, pile);
   }

   /**
    * @return true if no moves have been recorded by this CardMover
    */
   boolean isEmpty()
   {
      return moves.isEmpty();
   }

   /**
    * Move the given number of cards from one Pile to the other. The order of
    * the cards will be preserved, so that the top card of "source" will become
    * the top card of "target".
    * 
    * @param numCards
    * @param source
    * @param target
    */
   public void move(int numCards, Pile source, Pile target)
   {
      doPlainMove(numCards, source, target);
      moves.add(new PlainMove(numCards, source, target));
   }

   /**
    * Convenience method to move cards already in a Packet without having to put
    * them back in the origin pile.
    * 
    * @param packet
    * @param target
    */
   void move(Packet packet, Pile target)
   {
      for (int i = packet.size(); i > 0; i--) {
         target.add(packet.getFromTop(i));
      }
      moves.add(new PlainMove(packet.size(), packet.getOrigin(), target));
   }

   /**
    * Replay the moves recorded in this object. This should only be called after
    * an undo.
    */
   void redo()
   {
      for (Move move : moves) {
         move.redo();
      }
   }

   /**
    * Shuffle the cards in the given Pile. Note: this move cannot be undone, so
    * it should only be called on a CardMover that is going to be completed with
    * Table's initialCommit method.
    * 
    * @param pile
    */
   public void shuffle(Pile pile)
   {
      pile.shuffle();
   }

   /**
    * Reverse the effect of the moves recorded in this object.
    */
   void undo()
   {
      for (int i = moves.size() - 1; i >= 0; i--) {
         Move move = moves.get(i);
         move.undo();
      }
   }

   private List<Move> moves;

   private class FlipMove implements Move
   {
      FlipMove(int numCards, Pile source, Pile target)
      {
         this.numCards = numCards;
         this.source = source;
         this.target = target;
      }

      public void redo()
      {
         doFlipMove(numCards, source, target);
      }

      public void undo()
      {
         doFlipMove(numCards, target, source);
      }

      private int numCards;

      private Pile source;

      private Pile target;
   }

   private interface Move
   {
      void redo();

      void undo();
   }

   private class PlainMove implements Move
   {
      PlainMove(int numCards, Pile source, Pile target)
      {
         this.numCards = numCards;
         this.source = source;
         this.target = target;
      }

      public void redo()
      {
         doPlainMove(numCards, source, target);
      }

      public void undo()
      {
         doPlainMove(numCards, target, source);
      }

      private int numCards;

      private Pile source;

      private Pile target;
   }
}
