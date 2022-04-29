////////////////////////////////////////////////////////////////////////////////
// File:             StockStrategy.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.klondike;

import edu.depauw.csc232.solitaire.ui.CardMover;
import edu.depauw.csc232.solitaire.ui.Packet;
import edu.depauw.csc232.solitaire.ui.Pile;
import edu.depauw.csc232.solitaire.ui.PileStrategy;

/**
 * This PileStrategy encapsulates the rules for the stock pile in Klondike. The
 * only interaction allowed is to click on the stock, which deals one card into
 * the waste pile. If the stock is empty, then the waste pile is turned over to
 * replenish the stock.
 * 
 * @author bhoward
 */
class StockStrategy implements PileStrategy
{
   public StockStrategy(KlondikeGame game)
   {
      this.game = game;
   }

   @Override
   public boolean checkCanDrag(Pile stock)
   {
      // Not allowed to drag cards off of the stock
      return false;
   }

   @Override
   public boolean checkCanDrop(Pile stock, Packet packet)
   {
      // Not allowed to drop a packet on the stock
      return false;
   }

   @Override
   public void handleClick(Pile stock, int numCards, CardMover mover)
   {
      Pile waste = game.waste;

      if (stock.isEmpty()) {
         // If the stock is empty, turn over all of the cards from the waste
         // pile and refresh the stock
         mover.flipMove(waste.size(), waste, stock);
      }
      else {
         // If the stock is non-empty, deal one card face-up onto the waste pile
         mover.flipMove(1, stock, waste);
      }
   }

   private final KlondikeGame game;
}
