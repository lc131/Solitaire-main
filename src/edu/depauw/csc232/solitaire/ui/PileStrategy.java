////////////////////////////////////////////////////////////////////////////////
// File:             PileStrategy.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.ui;

import java.util.List;

/**
 * A PileStrategy permits customization of the behavior of a {@link Pile} in
 * response to various actions (clicks and drag/drops).
 * 
 * @author bhoward
 */
public interface PileStrategy
{
   /**
    * Check whether the given Pile allows packets of one or more cards to be
    * dragged off of it. To avoid lag, this should do minimal computation and
    * return quickly.
    * 
    * @param pile
    * @return true if the Pile allows dragging
    */
   default boolean checkCanDrag(Pile pile)
   {
      return true;
   }

   /**
    * Check whether the given Pile will allow a particular Packet to be dropped
    * on it. To avoid lag, this should do minimal computation and return
    * quickly.
    * 
    * @param pile
    * @param packet
    * @return true if the Pile will allow the Packet to be dropped
    */
   default boolean checkCanDrop(Pile pile, Packet packet)
   {
      return true;
   }

   /**
    * Check whether the selected cards may be dragged off of the Pile.
    * 
    * @param pile
    * @param selected
    * @return true if the given Packet may be dragged away from the Pile
    */
   default boolean checkStartDrag(Pile pile, List<Card> selected)
   {
      return true;
   }

   /**
    * Perform clean-up on the origin and target Piles after a drag is completed.
    * The cards will have already been added to the target by the time this is
    * called. The provided CardMover is available if other cards need to be
    * moved as a result (for example, because a stack has been cleared).
    * 
    * @param origin
    * @param target
    * @param mover
    */
   default void finishDrag(Pile origin, Pile target, CardMover mover)
   {
   }

   /**
    * Respond to a click on some number of cards from the top of the given Pile.
    * If only the top card is selected, numCards will be 1. If the Pile is
    * empty, numCards will be 0. The CardMover is available to perform card
    * moves.
    * 
    * @param pile
    * @param numCards
    * @param mover
    */
   default void handleClick(Pile pile, int numCards, CardMover mover)
   {
   }

   /**
    * @return a PileStrategy where all actions are allowed and nothing extra
    *         happens in response
    */
   static PileStrategy makeDefault()
   {
      return new PileStrategy()
      {
      };
   }
}
