////////////////////////////////////////////////////////////////////////////////
// File:             TableauStrategy.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.klondike;

import java.util.List;

import edu.depauw.csc232.solitaire.model.Rank;
import edu.depauw.csc232.solitaire.ui.Card;
import edu.depauw.csc232.solitaire.ui.CardMover;
import edu.depauw.csc232.solitaire.ui.Packet;
import edu.depauw.csc232.solitaire.ui.Pile;
import edu.depauw.csc232.solitaire.ui.PileStrategy;

/**
 * This PileStrategy encapsulates the rules for a tableau pile in Klondike. Any
 * number of face-up cards may be dragged off of the tableau, and cards may be
 * dropped if they are alternating red/black and decreasing in value.
 * 
 * @author bhoward
 */
class TableauStrategy implements PileStrategy
{
   public TableauStrategy(KlondikeGame game)
   {
      this.game = game;
   }

   @Override
   public boolean checkCanDrag(Pile tableau)
   {
      // Allow drag if not empty
      return !tableau.isEmpty();
   }

   @Override
   public boolean checkCanDrop(Pile tableau, Packet packet)
   {
      // Bottom card of packet must have opposite color and one-less value of
      // top card in tableau, or bottom card is a King and the tableau is empty
      if (tableau.isEmpty()) {
         Card bottom = packet.getBottom();
         return bottom.getRank() == Rank.King;
      }
      else {
         Card top = tableau.getTop();
         Card bottom = packet.getBottom();
         return top.isFaceUp() && (top.isRed() != bottom.isRed())
            && (top.getValue() - 1 == bottom.getValue());
      }
   }

   @Override
   public boolean checkStartDrag(Pile tableau, List<Card> selected)
   {
      // Check that all of the selected cards are face-up
      for (Card card : selected) {
         if (!card.isFaceUp()) {
            return false;
         }
      }
      return true;
   }

   @Override
   public void finishDrag(Pile tableau, Pile target, CardMover mover)
   {
      // Flip over an exposed top card, if any
      if (!tableau.isEmpty()) {
         Card top = tableau.getTop();
         if (!top.isFaceUp()) {
            mover.flipTop(tableau);
         }
      }

      // Check for winning the game after playing a card from this tableau pile
      game.checkWin();
   }

   @Override
   public void handleClick(Pile tableau, int numCards, CardMover mover)
   {
      // Search for a place to move the card; check foundations if only one
      // card, then other tableaus
      if (!tableau.isEmpty()) {
         if (numCards == 1) {
            for (Pile foundation : game.foundations) {
               if (foundation.tryDrag(tableau, 1, mover)) {
                  return;
               }
            }
         }

         for (Pile tableau2 : game.tableaus) {
            if (tableau2 != tableau
               && tableau2.tryDrag(tableau, numCards, mover)) {
               return;
            }
         }
      }
   }

   private final KlondikeGame game;
}
