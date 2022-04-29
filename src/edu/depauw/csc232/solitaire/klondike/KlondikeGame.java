////////////////////////////////////////////////////////////////////////////////
// File:             KlondikeGame.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   https://en.wikipedia.org/wiki/Klondike_(solitaire)
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.klondike;

import edu.depauw.csc232.solitaire.Game;
import edu.depauw.csc232.solitaire.ui.CardMover;
import edu.depauw.csc232.solitaire.ui.CardStack;
import edu.depauw.csc232.solitaire.ui.GameFrame;
import edu.depauw.csc232.solitaire.ui.Pile;
import edu.depauw.csc232.solitaire.ui.PileStrategy;
import edu.depauw.csc232.solitaire.ui.Table;

/**
 * An implementation of the single-card-draw version of Klondike solitaire.
 * 
 * @author bhoward
 */
public class KlondikeGame implements Game
{
   /**
    * Construct the empty piles to be placed on the game table, and assign their
    * interaction strategies.
    */
   public KlondikeGame()
   {
      PileStrategy wasteStrategy = new WasteStrategy(this);
      waste = Pile.makeSquared(wasteStrategy);

      PileStrategy stockStrategy = new StockStrategy(this);
      stock = Pile.makeSquared(stockStrategy);

      PileStrategy tableauStrategy = new TableauStrategy(this);
      tableaus = new Pile[NUMBER_OF_TABLEAUS];
      for (int i = 0; i < NUMBER_OF_TABLEAUS; i++) {
         tableaus[i] = Pile.makeVertical(tableauStrategy);
      }

      PileStrategy foundationStrategy = new FoundationStrategy(this);
      foundations = new Pile[NUMBER_OF_FOUNDATIONS];
      for (int i = 0; i < NUMBER_OF_FOUNDATIONS; i++) {
         foundations[i] = Pile.makeSquared(foundationStrategy);
      }
   }

   /**
    * Check for the winning condition: all foundations piles are full.
    */
   public void checkWin()
   {
      for (CardStack foundation : foundations) {
         if (foundation.size() != 13) {
            return;
         }
      }

      // All foundations have 13 cards -- we win!
      frame.showWin();
   }

   /**
    * Deal a deck of cards into the correct initial piles. All card moves need
    * to be performed through a CardMover object.
    * 
    * @param mover
    */
   public void dealGame(CardMover mover)
   {
      mover.addDeck(stock);
      mover.shuffle(stock);

      for (int i = 0; i < NUMBER_OF_TABLEAUS; i++) {
         mover.move(i + 1, stock, tableaus[i]);

         // Flip the top cards in the tableau
         mover.flipTop(tableaus[i]);
      }
   }

   /**
    * Place the UI elements on the given Table.
    * 
    * @param table
    */
   private void layoutUI(Table table)
   {
      waste.setX(110);
      waste.setY(10);
      table.addPile(waste);

      stock.setX(10);
      stock.setY(10);
      table.addPile(stock);

      for (int i = 0; i < NUMBER_OF_TABLEAUS; i++) {
         tableaus[i].setX(10 + 100 * i);
         tableaus[i].setY(160);
         table.addPile(tableaus[i]);
      }

      for (int i = 0; i < NUMBER_OF_FOUNDATIONS; i++) {
         foundations[i].setX(310 + 100 * i);
         foundations[i].setY(10);
         table.addPile(foundations[i]);
      }
   }

   /**
    * Create and display a GameFrame initialized to play a game of Klondike.
    */
   public void start()
   {
      frame = new GameFrame("Klondike", 700, 600, table -> {
         table.dealGame(this);
         layoutUI(table);
      });

      frame.display();
   }

   /**
    * Main method for testing the Klondike game by itself.
    * 
    * @param args
    */
   public static void main(String[] args)
   {
      Game game = new KlondikeGame();
      game.start();
   }

   // No point in encapsulating these with getters...
   public final Pile waste;

   public final Pile stock;

   public final Pile[] tableaus;

   public final Pile[] foundations;

   private GameFrame frame;

   private static final int NUMBER_OF_FOUNDATIONS = 4;

   private static final int NUMBER_OF_TABLEAUS = 7;
}
