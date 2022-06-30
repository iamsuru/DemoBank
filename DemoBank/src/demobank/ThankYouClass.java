package demobank;
import javax.swing.*;
import java.awt.*;
class ThankYouClass extends JFrame{
    private JLabel label;
    ThankYouClass() throws InterruptedException{
        Font f = new Font("Arial",Font.BOLD,20);
        label = new JLabel("Thank You For Banking with us!");
        label.setBounds(190,200,360,20);
        label.setFont(f);
        label.setForeground(Color.orange);
        add(label);
        

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("DemoBankIcon.png")));
        setLayout(null);
        setSize(700,500);
        setTitle("Greetings!");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}