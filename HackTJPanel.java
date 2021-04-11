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
   public JTextField momHeight, dadHeight, genderInp;
   public int genderNum, maleHeight, femaleHeight;
   public JLabel re;
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
      westSubpanel.setLayout(new GridLayout(7,1));
      
      JLabel male = new JLabel("Enter the father's height (inches):");
      male.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(male);
   
      dadHeight = new JTextField("", 20);
      dadHeight.setBackground(new Color(137, 207, 240));
      dadHeight.setHorizontalAlignment(SwingConstants.CENTER);   
      westSubpanel.add(dadHeight);
   
   
      JLabel female = new JLabel("Enter the mother's height (inches):");
      female.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(female);
   
      momHeight = new JTextField("", 20);
      momHeight.setBackground(new Color(255,182,193));
      momHeight.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(momHeight);
   
      JLabel gender = new JLabel("Enter your gender (M) or (F):");
      gender.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(gender);
   
      genderInp = new JTextField("", 20);
      genderInp.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(genderInp);
      
      add(westSubpanel, BorderLayout.WEST);
   
      JButton button = new JButton("Calculate");
      button.addActionListener(new CalculateListener());
      genderInp.setHorizontalAlignment(SwingConstants.CENTER);
      add(button, BorderLayout.SOUTH);
   
      
      
      re = new JLabel("");
      re.setFont(new Font("Serif", Font.BOLD, 30));
      re.setHorizontalAlignment(SwingConstants.CENTER);
      add(re, BorderLayout.NORTH);
   
         
      ImageIcon icon = new ImageIcon("growth.jpg");
      JLabel pic = new JLabel();
      pic.setIcon(icon);
      pic.setHorizontalAlignment(SwingConstants.RIGHT);
      add(pic, BorderLayout.CENTER);
   
      
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
            femaleHeight = Integer.parseInt(momHeight.getText());
         
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
           
            URL url = new URL("http://127.0.0.1:12345/predict");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");
         
            String data = "[\n  {\"Father\": " + maleHeight + ", \"Mother\": " + femaleHeight + ", \"Gender\": " + "" + genderNum + "\n  } \n  \n]";
         
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
                        
            if(genderNum != 0 && genderNum != 1){
               re.setText("ERROR: Please try again");
            }    
            else if(genderNum == 0 || genderNum ==1){          
               re.setText("Prediction: " + finalNum + " inches!");
            }
         
            http.disconnect();
         }
         
         catch(Exception cheese){
            re.setText("ERROR: Please try again");
         }
         
      }
   }
}