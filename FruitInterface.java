/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public interface FruitInterface {

    /**
     * Gets the width of the outline stroke used when painting.
     */
    public double getOutlineWidth();

    /**
     * Sets the width of the outline stroke used when painting.
     */
    public void setOutlineWidth(double newWidth);

    /**
     * Concatenates a rotation transform to the Fruit's affine transform
     */
    public void rotate(double theta);

    /**
     * Concatenates a scale transform to the Fruit's affine transform
     */
    public void scale(double x, double y);

    /**
     * Concatenates a translation transform to the Fruit's affine transform
     */
    public void translate(double tx, double ty);

    /**
     * Returns the Fruit's affine transform that is used when painting
     */
    public AffineTransform getTransform();

    /**
     * Creates a transformed version of the fruit. Used for painting
     * and intersection testing.
     */
    public Area getTransformedShape();

    /**
     * Paints the Fruit to the screen using its current affine
     * transform and paint settings (fill, outline)
     */
    public void draw(Graphics2D g2);

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */
    public boolean intersects(Point2D p1, Point2D p2);

    /**
     * Returns whether the given point is within the Fruit's shape.
     */
    public boolean contains(Point2D p1);

    /**
     * This method assumes that the line represented by the two points
     * intersects the fruit. If not, unpredictable results will occur.
     * Returns two new Fruits, split by the line represented by the
     * two points given.
     */
    public Fruit[] split(Point2D p1, Point2D p2) throws NoninvertibleTransformException;
}
