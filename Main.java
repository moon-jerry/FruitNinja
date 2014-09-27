/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import javax.swing.*;
import java.awt.*;

public class Main {
  static private Model model;
  static private Model model2;
  static private View view;
  static private TitleView title;

  /*
   * Main entry point for the application
   */
  public static void main(String[] args) {
    // instantiate your model and views
    // add any new views here
    model = new Model();
    model2 = new Model();
    title = new TitleView(model);
    view = new View(model,model2);

    // customize the title and any other top-level settings
    JFrame frame = new JFrame("CS349 A3");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(title, BorderLayout.NORTH);
    frame.add(view, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
  }
}
