/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.border.Border;


/*
 * View of the main play area.
 * Displays pieces of fruit, and allows players to slice them.
 */
public class View extends JPanel implements ModelListener {
    private Model model;
    private Model model2; //to store new fruit
    private final MouseDrag drag;
    private Timer atimer;
    private  ImageIcon  sp,rp,quit;
    int counter=0;
    public BufferedImage backG,hitI,bk,bk2,go,miss;
    private JButton  start;
    boolean hit;
    private int missed;
    private JPanel exitP;




    // Constructor
    View (Model m, Model m2) {
        model = m;
        model2 =m2;
        model.addObserver(this);
        model2.addObserver(this);
        sp = new ImageIcon(getClass().getResource("pic/start.png"));
        rp = new ImageIcon(getClass().getResource("pic/replay.png"));
        quit = new ImageIcon(getClass().getResource("pic/cross.png"));
        Image img = sp.getImage() ;
        Image newimg = img.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH ) ;
        sp = new ImageIcon( newimg );
        img = rp.getImage() ;
        newimg = img.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH ) ;
        rp = new ImageIcon( newimg );
        img = quit.getImage() ;
        newimg = img.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH ) ;
        quit = new ImageIcon( newimg );
        hit=false;
        missed=0;

        start= new JButton(sp);
        this.add(start);

        //layout.putConstraint(SpringLayout.NORTH, start, 50, SpringLayout.NORTH, this);
        //layout.putConstraint(SpringLayout.EAST, start, 50, SpringLayout.EAST, this);

        JButton exit = new JButton(quit);
        JButton replay= new JButton(rp);
        exitP = new JPanel();
        exitP.setLayout(new BoxLayout(exitP,BoxLayout.LINE_AXIS));
        exitP.setPreferredSize(new Dimension(231,110));
        exitP.add(replay);
        exitP.add(exit);
        this.add(exitP);
        exitP.setVisible(false);
        /*
        start.setPreferredSize(new Dimension(50,50));
        JPanel exitP = new JPanel();
        exitP.setPreferredSize(new Dimension(50,50));
        exitP.setLayout(new BorderLayout());
        exitP.add(exit, BorderLayout.CENTER);
        this.add(start,BorderLayout.CENTER);*/

        setBackground(Color.WHITE);
        try {
            backG = ImageIO.read(new File("pic/bg.png"));
            hitI = ImageIO.read(new File("pic/hit.png"));
            bk = ImageIO.read(new File("pic/bk.png"));
            bk2 = ImageIO.read(new File("pic/bk2.png"));
            go = ImageIO.read(new File("pic/go.png"));
            miss = ImageIO.read(new File("pic/miss.png"));

        } catch (IOException ex) {
            System.out.print("cant find pic");
        }






        // drag represents the last drag performed, which we will need to calculate the angle of the slice
        drag = new MouseDrag();
        // add mouse listener
        addMouseListener(mouseListener);

        atimer = new Timer(50, new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                model.P();
                if (counter==0)
                    model.checkTimer();
                //System.out.print(counter%10==0);
                start.setVisible(false);
                if(missed==5)
                {
                    atimer.stop();
                    exitP.setVisible(true);
                    model.checkTimer();
                    repaint();

                }
                if(counter%30==0)
                    hit=false;

                if(counter%20==0){
                    int temp=randomWithRange(50,70);
                Fruit f = new Fruit(new Area(new Ellipse2D.Double(0, 0, temp,temp)));
                f.translate(0.5*getWidth(),getHeight());
                model2.add(f);

                }
                for(Fruit f: model2.getShapes())
                {
                    f.move();
                    if(f.getTransformedShape().getBounds().getY()>800)
                    {
                        model2.removeF(f);
                        missed++;
                    }

                }

                for(Fruit f: model.getShapes())
                {
                f.translate(f.expandNumber*0.7,f.droppingNumber*1.5);
                  if(f.getTransformedShape().getBounds().getY()>800)
                     model.removeF(f);


                }
                counter++;
                repaint();
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atimer.start();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        replay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeALL();
                model2.removeALL();
                atimer.start();
                missed=0;
                exitP.setVisible(false);
                model.checkTimer();
            }
        });
    }

    int randomWithRange(int min, int max) //helper function
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    // Update fired from model
    @Override
    public void update() {
        this.repaint();
    }

    @Override
    public void startOrStop() {
    }

    public void PaintTime()
    {
    }


    // Panel size
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200,750);
    }

    // Paint this panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backG, 0, 0, getWidth(), getHeight(), null);
        if(hit)
           g.drawImage(hitI,1000,50,120,70,null);
        if(!atimer.isRunning()&&missed==5)
        {
            g.drawImage(bk,483*getWidth()/1200,7,118,106,null);
            g.drawImage(bk2,600,7,115,106,null);
            g.drawImage(go,347,211,500,300,null);
        }

        switch (missed)
        {
            case 1:
                g.drawImage(miss,50,50,50,50,null);
                break;
            case 2:
                g.drawImage(miss,50,50,50,50,null);
                g.drawImage(miss,100,50,50,50,null);
                break;
            case 3:
                g.drawImage(miss,50,50,50,50,null);
                g.drawImage(miss,100,50,50,50,null);
                g.drawImage(miss,150,50,50,50,null);
                break;
            case 4:
                g.drawImage(miss,50,50,50,50,null);
                g.drawImage(miss,100,50,50,50,null);
                g.drawImage(miss,150,50,50,50,null);
                g.drawImage(miss,200,50,50,50,null);
                break;
            case 5:
                g.drawImage(miss,50,50,50,50,null);
                g.drawImage(miss,100,50,50,50,null);
                g.drawImage(miss,150,50,50,50,null);
                g.drawImage(miss,200,50,50,50,null);
                g.drawImage(miss,250,50,50,50,null);
                break;
            default:
                break;
        }

        Graphics2D g2 = (Graphics2D) g;

        // draw all pieces of fruit
        // note that fruit is responsible for figuring out where and how to draw itself
        for (Fruit s : model.getShapes()) {
           // System.out.print("topF x"+ s.getTransform().getTranslateX());
           s.draw(g2);
        }
        for (Fruit h : model2.getShapes()) {
            h.draw(g2);
        }
    }

    // Mouse handler
    // This does most of the work: capturing mouse movement, and determining if we intersect a shape
    // Fruit is responsible for determining if it's been sliced and drawing itself, but we still
    // need to figure out what fruit we've intersected.
    private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            drag.start(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            drag.stop(e.getPoint());
           // atimer.stop();
            // you could do something like this to draw a line for testing
            // not a perfect implementation, but works for 99% of the angles drawn
            
           //  int[] x = { (int) drag.getStart().getX(), (int) drag.getEnd().getX(), (int) drag.getEnd().getX(), (int) drag.getStart().getX()};
            // int[] y = { (int) drag.getStart().getY()-1, (int) drag.getEnd().getY()-1, (int) drag.getEnd().getY()+1, (int) drag.getStart().getY()+1};
          //   model.add(new Fruit(new Area(new Polygon(x, y, x.length))));

            // find intersected shapes
            //int offset = 0; // Used to offset new fruits
            for (Fruit s : model2.getShapes()) {
                if (s.intersects(drag.getStart(), drag.getEnd())) {
                    try {
                        Fruit[] newFruits = s.split(drag.getStart(), drag.getEnd());
                        for (Fruit f : newFruits) {
                              if(s.cutted)
                              {
                                  Fruit temp = new Fruit(s.getTransformedShape());
                                  temp.fallingNumber=s.fallingNumber;
                                  temp.expandNumber=s.expandNumber;
                                  temp.counter=s.counter;
                                  temp.setF(s.getF());
                                  model2.add(temp);
                                  break;
                              }

                              else
                              {
                                  f.setF(s.getF());
                                  hit=true;
                                  model.add(f);

                              }

                        }
                        model2.removeF(s);
                    } catch (Exception ex) {
                        System.err.println("Caught error: " + ex.getMessage());
                    }
                } else {
                    //s.setFillColor(Color.BLUE);
                }
            }
        }
    };



    /*
     * Track starting and ending positions for the drag operation
     * Needed to calculate angle of the slice
     */
    private class MouseDrag {
        private Point2D start;
        private Point2D end;

        MouseDrag() { }

        protected void start(Point2D start) { this.start = start; }
        protected void stop(Point2D end) { this.end = end; }

        protected Point2D getStart() { return start; }
        protected Point2D getEnd() { return end; }

    }
}
