import javax.swing.*;
import java.awt.*;

public class teste {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Button Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a JButton
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        // Load the image
        ImageIcon icon = new ImageIcon("C:\\Users\\ASUS\\Downloads\\imresizer-1701802315489.jpg");
        Image image = icon.getImage();
       Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_REPLICATE);
       icon = new ImageIcon(newimg);
        // Set the image as the button's icon
        button.setIcon(icon);
        
        // Add the button to the frame
        frame.getContentPane().add(button, BorderLayout.CENTER);
        
        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
