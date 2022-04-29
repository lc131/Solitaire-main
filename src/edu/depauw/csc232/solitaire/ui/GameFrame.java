////////////////////////////////////////////////////////////////////////////////
// File:             GameFrame.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.ui;

import java.awt.Color;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GameFrame extends JFrame
{
   /**
    * Construct a window to display a card game table, with the given title,
    * dimensions, and layout function.
    * 
    * @param title
    * @param width
    * @param height
    * @param layout
    */
   public GameFrame(String title, int width, int height, Consumer<Table> layout)
   {
      super(title);
      setSize(width, height);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      this.layout = layout;

      CardImages images = new CardImages("/cards/", ".png");
      table = new Table(images);
      table.setBackground(DARK_GREEN);
      add(table);
   }

   /**
    * Call this to finish laying out the game and display the frame on the
    * screen.
    */
   public void display()
   {
      layout.accept(table);
      setVisible(true);
   }

   /**
    * Display a winning message over this frame.
    */
   public void showWin()
   {
      // Use invokeLater so that we can finish processing the current event
      SwingUtilities.invokeLater(() -> {
         JOptionPane.showMessageDialog(this, "You win!", "Solitaire",
                  JOptionPane.INFORMATION_MESSAGE);
      });
   }

   private final Table table;

   private final Consumer<Table> layout;

   private static final Color DARK_GREEN = new Color(0, 128, 0);
}
