/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import java.util.ArrayList;
import java.util.Vector;

/*
 * Class the contains a list of fruit to display.
 * Follows MVC pattern, with methods to add observers,
 * and notify them when the fruit list changes.
 */
public class Model  {
  // Observer list
  private Vector<ModelListener> views = new Vector();

  // Fruit that we want to display
  private ArrayList<Fruit> shapes = new ArrayList();

  // MVC methods
  // These likely don't need to change, they're just an implementation of the
  // basic MVC methods to bind view and model together.
  public void addObserver(ModelListener view) {
    views.add(view);
  }

  public void notifyObservers() {
    for (ModelListener v : views) {
      v.update();
    }
  }

  public void checkTimer()
  {
      for (ModelListener v : views) {
          v.startOrStop();
      }
  }

  public void P()
  {
      for (ModelListener v : views) {
          v.PaintTime();
      }
  }


  // Model methods
  // You may need to add more methods here, depending on required functionality.
  // For instance, this sample makes to effort to discard fruit from the list.
  public void add(Fruit s) {
    shapes.add(s);
    notifyObservers();
  }

  public ArrayList<Fruit> getShapes() {
      return (ArrayList<Fruit>)shapes.clone();
  }

  public ArrayList<Fruit> removeF(Fruit i)
  {
      shapes.remove(i);
      return (ArrayList<Fruit>)shapes.clone();
  }
  public void removeALL()
  {
        shapes.clear();
  }
}
