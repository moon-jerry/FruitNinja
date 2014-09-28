/**
Jerry Yan
 */
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/*
 * View to display the Title, and Score
 * Score currently just increments every time we get an update
 * from the model (i.e. a new fruit is added).
 */
public class TitleView extends JPanel implements ModelListener {
  private Model model;
  private JLabel title, score;
  private int count = 0;
  public BufferedImage titleI;
  private long time;
  private SimpleDateFormat sdf;
  private boolean isrunning;


  // Constructor requires model reference
  TitleView (Model model) {
    // register with model so that we get updates
    this.model = model;
    this.model.addObserver(this);
    isrunning=false;
      try {
          titleI = ImageIO.read(new File("pic/title.png"));
      } catch (IOException ex) {
          System.out.print("cant find pic");
      }
      sdf = new SimpleDateFormat("hh:mm:ss.SSS");

    // draw something
    setBorder(BorderFactory.createLineBorder(Color.black));
    setBackground(new Color(60, 33, 22));
    // You may want a better name for this game!
    title = new JLabel();
    score = new JLabel();
      score.setForeground(Color.RED);
      score.setFont(new Font("", Font.TRUETYPE_FONT,20));

    // use border layout so that we can position labels on the left and right
    this.setLayout(new BorderLayout());
    this.add(title,BorderLayout.WEST);
    this.add(score,BorderLayout.EAST);
  }

  // Panel size
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500,55);
  }

  // Update from model
  // This is ONLY really useful for testing that the view notifications work
  // You likely want something more meaningful here.
  @Override
  public void update() {
    count++;
    paint(getGraphics());
  }

  public void PaintTime()
  {
      paint(getGraphics());
  }

  public void startOrStop() {
      if(!isrunning)
      {
        time=System.nanoTime();
        count=0;
        isrunning=true;

      }
      else if(isrunning)
      {
          isrunning=false;
          time=System.nanoTime()-time;
      }

      paint(getGraphics());
  }

  // Paint method
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    //String strDate = sdf.format(System.nanoTime()-time);
      long temp = System.nanoTime()-time;
      long temp2 = System.nanoTime()-time;
      temp=TimeUnit.NANOSECONDS.toSeconds(temp)%60;
      temp2=TimeUnit.NANOSECONDS.toMinutes(temp2);
    if(!isrunning)
         score.setText("Score:  " + count*5 + "  "+"Time Elapsed : "+ TimeUnit.NANOSECONDS.toMinutes(time) + " Min "+TimeUnit.NANOSECONDS.toSeconds(time)%60+" Sec ");
    else if(isrunning)
         score.setText("Score:  " + count*5 + "  " + "Time Elapsed : "+temp2 + " Min "+ temp + " Sec ");
    g.drawImage(titleI,0,0,300,55,null);
  }
}
