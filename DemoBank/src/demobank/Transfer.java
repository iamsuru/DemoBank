package demobank;
import java.awt.Toolkit;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Transfer extends JFrame{
    final private JLabel label1,label2;
    final private JTextField money,receiver;
    final private JButton send_money;
    private Connection con;
    private ResultSet set;  
    private String uid = "";
    private int senderBalance = 0;
    Transfer(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        label1 = new JLabel("Enter Last 4-digits of Receiver's Card");
        label1.setBounds(103,50,200,30);
        add(label1);
        
        
        receiver = new JTextField(4);
        receiver.setBounds(100,90,200,30);
        add(receiver);
        receiver.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterBtnEvent();
                }
            }
        });        
        
        label2 = new JLabel("Enter Amount to Send");
        label2.setBounds(143,130,200,30);
        add(label2);
        
        
        money = new JTextField(4);
        money.setBounds(100,170,200,30);
        add(money);
        money.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterBtnEvent();
                }
            }
        });        
        
        send_money = new JButton("Transfer Money");
        send_money.setBounds(140,220,120,30);
        add(send_money);
        send_money.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterBtnEvent();
                }
            }
        });        
        send_money.addActionListener((ActionEvent ae) -> {
            enterBtnEvent();
        });
        

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("DemoBankIcon.png")));
        setLayout(null);
        setSize(400,500);
        setTitle("Transfer Money - Demo Bank");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    
        private void enterBtnEvent(){
            if((receiver.getText()).equals("") || (money.getText()).equals("")){
                JOptionPane.showMessageDialog(null,"Fields can not be empty");
            }
            String number = receiver.getText();
            int amount = Integer.parseInt(money.getText());
            
            if(connectToDatabase()!=true){
                connectToDatabase();
            }
            else{
                try {
                    validateUser(number,amount);
                } catch (FileNotFoundException | SQLException | InterruptedException ex) {
                    Logger.getLogger(Transfer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }            
        }

    
        private void validateUser(String number,int amount) throws FileNotFoundException, SQLException, InterruptedException{
            
            //not trying to send money to himself
            File obj = new File("text2.txt");
            Scanner ip = new Scanner(obj);
            while(ip.hasNext()){
                uid = ip.nextLine();
            }
            ip.close();
            if(uid.equals(number)){
                JOptionPane.showMessageDialog(null,"Please Enter Correct Number","WARNING",JOptionPane.WARNING_MESSAGE);
            }
            else{
                if(checkAccountExist(number)==false){
                    JOptionPane.showMessageDialog(null,"No record found with this number.","WARNING",JOptionPane.WARNING_MESSAGE);
                }
                else{ 
                PreparedStatement p = con.prepareStatement("Select amount from entry where card_num = ?");
                p.setString(1,uid);
                set = p.executeQuery();
                while(set.next()){
                    senderBalance = set.getInt("amount");
                }
                if(senderBalance>amount){
                    sendMoney(amount,number);
                }
                else{
                    JOptionPane.showMessageDialog(this,"Insufficient Balance for Transferring Amount\nAccount Balance : Rs. "+ senderBalance);
                }
                }
            }
        }
        
        private boolean checkAccountExist(String number) throws SQLException{
            PreparedStatement pr = con.prepareStatement("Select card_num from entry");
            set = pr.executeQuery();
            while(set.next()){
                if((set.getString("card_num")).equals(number)){
                    return true;
                }
            }
            return false;
            
        }
        
        private void sendMoney(int amount,String number) throws SQLException, InterruptedException, InterruptedException{
            String previous="";
            int previous_number;
            int new_amount = 0;
            PreparedStatement p = con.prepareStatement("Select amount from entry where card_num = ?");
            p.setString(1, number);
            set = p.executeQuery();
            while(set.next()){
                previous = set.getString("amount");
            }
            if(previous==null){
                previous = "0";
            }
            
            previous_number = Integer.parseInt(previous);
            
            new_amount = amount + previous_number;
            //updating receiver's db
            PreparedStatement q = con.prepareStatement("Update entry set amount = ? where card_num = ?");
            q.setInt(1, new_amount);
            q.setString(2, number);
            q.executeUpdate();
            
            PreparedStatement update_Receiver = con.prepareStatement("Insert into transaction values(?,?,CURRENT_TIMESTAMP,?)");
            update_Receiver.setString(1,number);
            update_Receiver.setInt(2,amount);
            update_Receiver.setString(3,"Credit");
            update_Receiver.execute();
            
            
            //updating sender's db
            this.senderBalance = senderBalance - amount;
            PreparedStatement r = con.prepareStatement("Update entry set amount = ? where card_num = ?");
            r.setInt(1, senderBalance);
            r.setString(2, uid);
            r.executeUpdate();
            
            PreparedStatement update_Sender = con.prepareStatement("Insert into transaction values(?,?,CURRENT_TIMESTAMP,?)");
            update_Sender.setString(1,uid);
            update_Sender.setInt(2,amount);
            update_Sender.setString(3,"Debit");
            update_Sender.execute();
            JOptionPane.showMessageDialog(this,"Transaction Successfull");
            con.close();
            dispose();
            ThankYouClass thankYouClass =new ThankYouClass();
                //i need ryt code
                int delay = 5000;
                javax.swing.Timer timer = new javax.swing.Timer( delay, new ActionListener(){
                @Override
                public void actionPerformed( ActionEvent e ){
                    thankYouClass.dispose();
                    new DemoBank();
                }
                } );
                timer.setRepeats( false );
                timer.start();
                
        }
        
        private boolean connectToDatabase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:7969/atm", "root", "@Suru_07112000@");
            if(con.isClosed()!=true){
                return true;
            }
            
        }
        catch(ClassNotFoundException | SQLException e){
            
        }
        return false;
    }
}
