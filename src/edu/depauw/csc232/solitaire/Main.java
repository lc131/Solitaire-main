////////////////////////////////////////////////////////////////////////////////
// File:             Main.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

import edu.depauw.csc232.solitaire.klondike.KlondikeGame;

/**
 * Main class for the solitaire application.
 * 
 * @author bhoward
 */
public class Main
{
   /**
    * Display a window allowing the user to choose a game of solitaire.
    */
   public void start()
   {
      JFrame frame = new JFrame("CSC232 Solitaire");

      Box buttons = Box.createVerticalBox();
      frame.add(buttons);

      JButton klondike = new JButton("Klondike");
      klondike.setAlignmentX(JButton.CENTER_ALIGNMENT);
      klondike.addActionListener(event -> {
         Game game = new KlondikeGame();
         game.start();
      });
      buttons.add(klondike);

      JButton quit = new JButton("Quit");
      quit.setAlignmentX(JButton.CENTER_ALIGNMENT);
      quit.addActionListener(event -> {
         frame.dispose();
      });
      buttons.add(Box.createVerticalGlue());
      buttons.add(quit);

      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setSize(200, 200);
      frame.setVisible(true);
   }

   public static void main(String[] args)
   {
      Main main = new Main();
      main.start();
   }
}
