import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class HackTJ
{
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("Reaching New Heights");
      frame.setSize(750, 400);
      frame.setLocation(20, 20);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new HackTJPanel());
      frame.setVisible(true);
  }
}
