package demobank;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DemoBank{
    final private JFrame frame = new JFrame();
    private Connection con;
    final private JTextField cardNumber;
    final private JPasswordField password;
    final private JButton login;
    final private JLabel pinLabel,cardLabel,icon_label,loader;
    public String enteredPin;
    DemoBank(){
        icon_label = new JLabel();
        icon_label.setIcon(new ImageIcon(getClass().getResource("logo.png")));
        icon_label.setBounds(210,0,200,150);
        frame.add(icon_label);
        
        loader = new JLabel();
        loader.setIcon(new ImageIcon(getClass().getResource("loader.gif")));
        loader.setBounds(280,175,90,90);
        loader.setVisible(false);
        frame.add(loader);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLabel = new JLabel("Enter Last 4-digit of card.");
        cardLabel.setBounds(250,100,200,50);
        frame.add(cardLabel);

        cardNumber = new JTextField();
        cardNumber.setBounds(250,150,150,30);
        frame.add(cardNumber);
        cardNumber.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterBtnEvent();
                }
            }
        });        
        
        pinLabel = new JLabel("Enter your 4-digit pin.");
        pinLabel.setBounds(270,190,200,30);
        frame.add(pinLabel);

        //password
        password = new JPasswordField(4);
        password.setBounds(250,230,150,30);
        password.setToolTipText("Enter Your Pin");
        frame.add(password);
        password.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterBtnEvent();
                }
            }
        });
        
        
        //Buttons
        login = new JButton("Enter");
        login.setBounds(290,280,70,30);
        frame.add(login);
        login.addActionListener((var ae) -> {
            enterBtnEvent();
        });
        
        login.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterBtnEvent();
                }
            }
        });
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("DemoBankIcon.png")));
        frame.setLayout(null);
        frame.setSize(700,500);
        frame.setTitle("Desktop - Demo Bank");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);        
        frame.setResizable(false);
    }

    private void enterBtnEvent(){
        loader.setVisible(true);
            String cardNum = cardNumber.getText();
            char[] char_password = password.getPassword();
            String pass = new String(char_password);
            System.out.println(pass);
            try {
                if(cardNum.equals("") || pass.equals("")){
                    JOptionPane.showMessageDialog(frame,"Fields Should Not Be Empty");
                    loader.setVisible(false);
                }
                else if((cardNum.length())!=4 || (pass.length())!=4){
                    JOptionPane.showMessageDialog(frame,"Entry must only be 4 Digits");
                    loader.setVisible(false);
                }
                else if(doLogin(cardNum,pass)){
                    JOptionPane.showMessageDialog(frame,"Login Successful");
                    loader.setVisible(false);
                    new ControlPanel();
                    con.close();
                    frame.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Unauthorised Access","WARNING",JOptionPane.WARNING_MESSAGE);
                    loader.setVisible(false);
                }
            } catch (IOException | SQLException ex) {
                Logger.getLogger(DemoBank.class.getName()).log(Level.SEVERE, null, ex);
            }
            cardNumber.setText("");
            password.setText("");
    }
    
    private boolean doLogin(String cardNum,String pin) throws IOException{
                    try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:7969/atm", "root", "@Suru_07112000@");
                    String check = "Select * from entry";
                    Statement stmt = con.createStatement();
                    ResultSet set = stmt.executeQuery(check);
                    while(set.next()){
                        if(cardNum.equals(set.getString("card_num")) && pin.equals((set.getString("pin")))){
                            enteredPin = set.getString("pin");
                            Writer b;
                            try (Writer a = new FileWriter("text.txt")) {
                                b = new FileWriter("text2.txt");
                                a.write(enteredPin);
                                b.write(set.getString("card_num"));
                            }
                            b.close();
                            return true;
                        }
                        
                    }
                    
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e);
                }
                System.out.println("false");
        return false;
        
    }
    public static void main(String[] args) {
                try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DemoBank.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        new DemoBank();
    }

}