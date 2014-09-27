/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */

/*
 * Trivial interface, must be implemented by all observers (views)
 */
public interface ModelListener {
  public void update();
  public void startOrStop();
  public void PaintTime();
}
