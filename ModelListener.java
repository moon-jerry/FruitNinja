/**
 Jerry Yan
 */

/*
 * Trivial interface, must be implemented by all observers (views)
 */
public interface ModelListener {
  public void update();
  public void startOrStop();
  public void PaintTime();
}
