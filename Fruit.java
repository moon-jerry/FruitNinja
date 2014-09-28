/**
 Jerry yan
 */

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Class that represents a Fruit. Can be split into two separate fruits.
 */
public class Fruit implements FruitInterface {
    private Area            fruitShape   = null;
    private Color           fillColor    = null;
    private Color           outlineColor = Color.BLACK;
    private AffineTransform transform    = new AffineTransform();
    private double          outlineWidth = 5;
    public boolean          cutted =false;
    public  int             fallingNumber=0;
    public  int             expandNumber=0;
    public  int             droppingNumber=0;
    public double           counter;
    private BufferedImage image,image2,image3,image4;



    /**
     * A fruit is represented using any arbitrary geometric shape.
     */
    int randomWithRange(int min, int max) //helper function
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
    Fruit (Area fruitShape) {
        this.fruitShape = (Area)fruitShape.clone();
        fallingNumber=randomWithRange(-9,9);
        expandNumber = randomWithRange(2,5);
        droppingNumber = randomWithRange(5,9);
        counter=0;
        try {
            image = ImageIO.read(new File("pic/apple.png"));
            image2 = ImageIO.read(new File("pic/orange.png"));
            image3 = ImageIO.read(new File("pic/wm.png"));
            image4 = ImageIO.read(new File("pic/peach.png"));
        } catch (IOException ex) {
            System.out.print("cant find pic");
        }
        ArrayList<BufferedImage> alist = createImageList();
        image =alist.get(randomWithRange(0,alist.size()-1));
    }

    private ArrayList<BufferedImage> createImageList() {
        ArrayList<BufferedImage> list = new ArrayList<BufferedImage>();
        list.add(image);
        list.add(image2);
        list.add(image3);
        list.add(image4);
        return list;
    }

    /**
     * The color used to paint the interior of the Fruit.
     */
    public Color getFillColor() {
        return fillColor;
    }
    /**
     * The color used to paint the interior of the Fruit.
     */
    public void setFillColor(Color color) {
        fillColor = color;
    }
    /**
     * The color used to paint the outline of the Fruit.
     */
    public Color getOutlineColor() {
        return outlineColor;
    }
    /**
     * The color used to paint the outline of the Fruit.
     */
    public void setOutlineColor(Color color) {
        outlineColor = color;
    }
    
    /**
     * Gets the width of the outline stroke used when painting.
     */
    public double getOutlineWidth() {
        return outlineWidth;
    }

    /**
     * Sets the width of the outline stroke used when painting.
     */
    public void setOutlineWidth(double newWidth) {
        outlineWidth = newWidth;
    }

    /**
     * Concatenates a rotation transform to the Fruit's affine transform
     */
    public void rotate(double theta) {
        transform.rotate(theta);
    }

    /**
     * Concatenates a scale transform to the Fruit's affine transform
     */
    public void scale(double x, double y) {
        transform.scale(x, y);
    }

    /**
     * Concatenates a translation transform to the Fruit's affine transform
     */
    public void translate(double tx, double ty) {
        transform.translate(tx, ty);
    }

    /**
     * Returns the Fruit's affine transform that is used when painting
     */
    public AffineTransform getTransform() {
        return (AffineTransform)transform.clone();
    }

    /**
     * Creates a transformed version of the fruit. Used for painting
     * and intersection testing.
     */
    public Area getTransformedShape() {
        return fruitShape.createTransformedArea(transform);
    }

    /**
     * Paints the Fruit to the screen using its current affine
     * transform and paint settings (fill, outline)
     */
    public void draw(Graphics2D g2) {
        // TODO BEGIN CS349
       // g2.setColor(fillColor);
        //g2.fill(getTransformedShape());
        //System.out.print("fill shape "+ getTransformedShape().getBounds().getX());
       // g2.setClip(0,0,1000,200);
        g2.setClip(getTransformedShape());
        g2.drawImage(image,(int)getTransformedShape().getBounds().getX(),(int)getTransformedShape().getBounds().getY(),fruitShape.getBounds().width+4,fruitShape.getBounds().height+4,null);
        g2.setClip(0,0,1200,40);

       // TODO END CS349

    }

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */
    public boolean intersects(Point2D p1, Point2D p2) {
        // TODO BEGIN CS349

         Line2D aLine= new Line2D.Double(p1.getX(),p1.getY(),p2.getX(),p2.getY());
        return aLine.intersects(this.getTransformedShape().getBounds2D())&&(!this.contains(p1))&&(!this.contains(p2));
         // TODO END CS349
    }

    /**
     * Returns whether the given point is within the Fruit's shape.
     */
    public boolean contains(Point2D p1) {
        return this.getTransformedShape().contains(p1);
    }

    /**
     * This method assumes that the line represented by the two points
     * intersects the fruit. If not, unpredictable results will occur.
     * Returns two new Fruits, split by the line represented by the
     * two points given.
     */

   double range(double f,double c, double input)
   {
       if(input>c)
           return c;
       else if (input <f)
       {

           return f;
       }
       else
           return input;
   }
    public Fruit[] split(Point2D p1, Point2D p2) throws NoninvertibleTransformException {
        Area topArea = null;
        Area bottomArea = null;
        Point2D ori= new Point2D.Double(0,0);
        Double angle = 0.0;
        AffineTransform af= new AffineTransform();

        // TODO BEGIN CS349
        if (p2.getX()<p1.getX()&&p1.getY()<p2.getY()) // situation I
        {
            ori=p2;
            angle= Math.atan((p2.getY()-p1.getY())/(p1.getX()-p2.getX()));
            angle = - range(Math.toRadians(35),100,angle);
        }
        else if (p2.getX()>p1.getX()&&p1.getY()<p2.getY()) //situation II
        {
            ori=p2;
            angle=  Math.atan((p2.getY()-p1.getY())/(p2.getX()-p1.getX()));
            angle = - (Math.toRadians(180) - angle);
        }
        else if (p2.getX()<p1.getX()&&p1.getY()>p2.getY())  //situation III
        {
            ori=p1;
            angle=  Math.atan((p2.getY()-p1.getY())/(p2.getX()-p1.getX()));
            angle = - (Math.toRadians(180) - angle);

        }
        else if (p2.getX()>p1.getX()&&p1.getY()>p2.getY())  //situation IV
        {
            ori=p1;
            angle=  Math.atan((p2.getY()-p1.getY())/(p1.getX()-p2.getX()));
            angle = - range(Math.toRadians(35),100,angle);
        }
        else if(p2.getX()==p1.getX()&&p1.getY()<p2.getY())   //situation V
        {
            ori=p2;
            angle=Math.toRadians(90);
        }
        else if (p2.getX()==p1.getX()&&p1.getY()>p2.getY())   //situation VI
        {
            ori=p1;
            angle=Math.toRadians(90);
        }
        else if (p1.getY()==p2.getY())        //rest situation
        {
            if(p1.getX()>p2.getX())
                ori=p2;
            else
                ori=p1;
            angle=Math.toRadians(0);
        }
        af.rotate(angle,ori.getX(),ori.getY());
        Area stroke= new Area (new Rectangle2D.Double(ori.getX(),ori.getY(),p1.distance(p2)+5,60));
        stroke = stroke.createTransformedArea(af);
        topArea=(Area)this.getTransformedShape().clone();
        bottomArea=(Area)this.getTransformedShape().clone();
        topArea.subtract(stroke);
        bottomArea.subtract(topArea);
        if(topArea.equals(getTransformedShape())||bottomArea.equals(getTransformedShape()))
        {
            cutted=true;
           // System.out.print("im not cutted ");
        }
        Fruit topF= new Fruit(topArea);
        topF.expandNumber = -topF.expandNumber;
        Fruit botF = new Fruit(bottomArea);
        // TODO END CS349
        if (topArea != null && bottomArea != null)
            return new Fruit[] { topF, botF };
        return new Fruit[0];
     }

    public void setF(BufferedImage a)
    {
        image=a;
    }

    public BufferedImage getF()
    {
        return image;
    }

    public void move()
    {
        this.translate(fallingNumber*0.5,0.067741935*2*(counter*counter)/expandNumber-12.43);
        counter+=0.3;
        //System.out.print(counter+" ");
    }
}
