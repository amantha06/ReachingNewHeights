import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class HackTJPanel extends JPanel
{
   // Fields
   public JTextField momHeight, dadHeight, momHeight2, dadHeight2, genderInp;
   public int genderNum, maleHeight, femaleHeight, maleHeight2, femaleHeight2;
   public JLabel re, p1;
  
   // Constructor
   public HackTJPanel()
   {
      setLayout(new BorderLayout());
   
      // Top Label
      JLabel title = new JLabel("Reaching New Heights");
      title.setFont(new Font("Serif", Font.BOLD, 36));
      title.setHorizontalAlignment(SwingConstants.CENTER);
      add(title, BorderLayout.NORTH);
      
      // Left Subpanel
      JPanel westSubpanel = new JPanel();
      westSubpanel.setLayout(new GridLayout(7,2));
      
      JLabel male = new JLabel("Enter the father's height (feet):");
      male.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(male);
      
      JLabel male2 = new JLabel("Enter the father's height (inches):");
      male2.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(male2);
   
      dadHeight = new JTextField("", 20);
      dadHeight.setBackground(new Color(137, 207, 240));
      dadHeight.setHorizontalAlignment(SwingConstants.CENTER);   
      westSubpanel.add(dadHeight);
      
      dadHeight2 = new JTextField("", 20);
      dadHeight2.setBackground(new Color(137, 207, 240));
      dadHeight2.setHorizontalAlignment(SwingConstants.CENTER);   
      westSubpanel.add(dadHeight2);
      
   
      JLabel female = new JLabel("Enter the mother's height (feet):");
      female.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(female);
   
      JLabel female2 = new JLabel("Enter the mother's height (inches):");
      female2.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(female2);
   
   
      momHeight = new JTextField("", 20);
      momHeight.setBackground(new Color(255,182,193));
      momHeight.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(momHeight);
   
      momHeight2 = new JTextField("", 20);
      momHeight2.setBackground(new Color(255,182,193));
      momHeight2.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(momHeight2);
      
      
      JLabel gender = new JLabel("Enter your biological sex (M) or (F):");
      gender.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(gender);
      
      p1 = new JLabel();
      westSubpanel.add(p1);
      
      genderInp = new JTextField("", 20);
      genderInp.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(genderInp);
      
      add(westSubpanel, BorderLayout.WEST);
   
      JButton button = new JButton("Calculate");
      button.addActionListener(new CalculateListener());
      button.setHorizontalAlignment(SwingConstants.CENTER);
      add(button, BorderLayout.SOUTH);
   
      
      
      re = new JLabel("");
      re.setFont(new Font("Serif", Font.BOLD, 30));
      re.setHorizontalAlignment(SwingConstants.CENTER);
      add(re, BorderLayout.NORTH);
   
      
      
      ImageIcon icon = new ImageIcon("growth.jpg");
      JLabel pic = new JLabel();
      pic.setIcon(icon);
      pic.setHorizontalAlignment(SwingConstants.RIGHT);
      add(pic, BorderLayout.EAST);
    
      
      if(dadHeight.getText().isEmpty() == momHeight.getText().isEmpty() == genderInp.getText().isEmpty() == false){
         button.setEnabled(true);
      
      }
   }

     
      
      
   // Instance Methods
   private class CalculateListener implements ActionListener 
   {
      public void actionPerformed(ActionEvent e) 
      {
         try{
            maleHeight = Integer.parseInt(dadHeight.getText());
            maleHeight2 = Integer.parseInt(dadHeight2.getText());
            
            femaleHeight = Integer.parseInt(momHeight.getText());
            femaleHeight2 = Integer.parseInt(momHeight2.getText());
         
            if (genderInp.getText().equals("M") || genderInp.getText().equals("m"))
            {
               genderNum = 1; 
            } 
            else if(genderInp.getText().equals("F") || genderInp.getText().equals("f"))
            {
               genderNum = 0;
            }
            else{
               genderNum = -1000; 
               re.setText("ERROR: Please try again");
            }
            //FLSDJFLKJDSLKFJDSLKFJLDSKF
            double fmh = ((double)(maleHeight)*12 + maleHeight2);
            double ffh = ((double)(femaleHeight)*12 + femaleHeight2);
            
            
            URL url = new URL("http://127.0.0.1:12345/predict");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");
         
            String data = "[\n  {\"Father\": " + fmh + ", \"Mother\": " + ffh + ", \"Gender\": " + "" + genderNum + "\n  } \n  \n]";
         
            byte[] out = data.toString().getBytes(StandardCharsets.UTF_8);
         
            http.setDoOutput(true);
            http.getOutputStream().write(out);
            
            Reader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
            
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
               sb.append((char)c);
            String response = sb.toString();
            
            String value = response.replaceAll("[^\\d.]", "");       
            
            double finalNum = Math.round(Double.parseDouble(value)*100.0)/100.0;
            int noDecimalFinalNum = (int)finalNum;
            //System.out.println(noDecimalFinalNum);
                        
            if(genderNum != 0 && genderNum != 1){
               re.setText("ERROR: Please try again");
            }    
            else if(genderNum == 0 || genderNum ==1){    
               double inches = finalNum%12.0;
               int foot = noDecimalFinalNum/12;
               double inch = Math.round(inches*100.0)/100.0;
            
               re.setText("Prediction: " + foot + " feet " + inch + " inches!");
            }
            http.disconnect();
         }
         
         catch(Exception cheese){
            re.setText("ERROR: Please try again");
         }
         
      }
   }
}