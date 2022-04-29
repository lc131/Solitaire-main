////////////////////////////////////////////////////////////////////////////////
// File:             package-info.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

/**
 * This package contains the game-specific classes for single-card-draw Klondike
 * solitaire.
 * <ul>
 * <li>{@link edu.depauw.csc232.solitaire.klondike.KlondikeGame KlondikeGame} is
 * an implementation of the {@link edu.depauw.csc232.solitaire.Game Game}
 * interface, which is the primary external interface for creating a new game of
 * Klondike. It collects the various {@link edu.depauw.csc232.solitaire.ui.Pile
 * Piles} that make up the user interface of a game of Klondike. The
 * <b>Stock</b> is the draw pile; cards are drawn one at a time and put onto the
 * <b>Waste</b> pile; when the stock is empty, the waste is turned over and used
 * to replenish the stock. There are seven <b>Tableau</b> piles that initially
 * have one face-up card on top of zero or more face-down cards. Cards may be
 * played from the waste onto the tableau in alternating suit and descending
 * rank (King through Ace). Packets of face-up cards may be moved between the
 * tableau piles as long as the alternating suits and descending ranks are
 * respected. When all of the face-up cards are moved from a tableau pile, the
 * next face-down card (if any) is turned over. A Packet whose bottom card is a
 * King may be placed on an empty tableau. There are four <b>Foundation</b>
 * piles, which are initially empty. Cards may be built on a foundation pile in
 * increasing rank order, starting with an Ace, as long as all of the cards in a
 * pile have the same suit. The game ends when all 52 cards are moved to the
 * foundations.</li>
 * <li>{@link edu.depauw.csc232.solitaire.klondike.StockStrategy StockStrategy},
 * {@link edu.depauw.csc232.solitaire.klondike.WasteStrategy WasteStrategy},
 * {@link edu.depauw.csc232.solitaire.klondike.TableauStrategy TableauStrategy},
 * and {@link edu.depauw.csc232.solitaire.klondike.FoundationStrategy
 * FoundationStrategy} are classes that encode the above rules for clicking,
 * dragging, and dropping from and to the various types of piles.</li>
 * </ul>
 */
package edu.depauw.csc232.solitaire.klondike;
