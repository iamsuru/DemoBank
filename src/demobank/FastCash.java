package demobank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
class FastCash extends JFrame implements MouseListener{
    final private JLabel l_100,l_200,l_500,l_1000,l_2000,l_3000,l_5000,l_10000,label_show,label_show_amount;
    private Connection con;
    private String uid = "";
    private ResultSet set;
    FastCash(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Font f = new Font("Serif",Font.PLAIN,20);
        l_100 = new JLabel("Rs. 100/-");
        l_100.setBounds(50,30,100,20);
        l_100.setFont(f);
        add(l_100);
        l_100.addMouseListener(this);
        
        l_200 = new JLabel("Rs. 200/-");
        l_200.setBounds(50,70,100,20);
        l_200.setFont(f);
        add(l_200);
        l_200.addMouseListener(this);

        l_500 = new JLabel("Rs. 500/-");
        l_500.setBounds(50,110,100,20);
        l_500.setFont(f);
        add(l_500);
        l_500.addMouseListener(this);

        l_1000 = new JLabel("Rs. 1000/-");
        l_1000.setBounds(50,150,100,20);
        l_1000.setFont(f);
        add(l_1000);
        l_1000.addMouseListener(this);

        l_2000 = new JLabel("Rs. 2000/-");
        l_2000.setBounds(250,30,100,20);
        l_2000.setFont(f);
        add(l_2000);
        l_2000.addMouseListener(this);

        l_3000 = new JLabel("Rs. 3000/-");
        l_3000.setBounds(250,70,100,20);
        l_3000.setFont(f);
        add(l_3000);
        l_3000.addMouseListener(this);

        l_5000 = new JLabel("Rs. 5000/-");
        l_5000.setBounds(250,110,100,20);
        l_5000.setFont(f);
        add(l_5000);
        l_5000.addMouseListener(this);

        l_10000 = new JLabel("Rs. 10,000/-");
        l_10000.setBounds(250,150,100,20);
        l_10000.setFont(f);
        add(l_10000);
        l_10000.addMouseListener(this);
        
        
        label_show = new JLabel("");
        label_show.setBounds(70,60,260,20);
        label_show.setFont(f);
        label_show.setForeground(Color.green);
        add(label_show);
        
        label_show_amount = new JLabel("");
        label_show_amount.setBounds(120,110,260,20);
        label_show_amount.setForeground(Color.black);
        add(label_show_amount);
        
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("DemoBankIcon.png")));
        setLayout(null);
        setSize(400,250);
        setTitle("Transfer Money - Demo Bank");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);       
    }
    


    @Override
    public void mouseClicked(MouseEvent e) {
        try{
        File obj = new File("text2.txt");
        Scanner ip = new Scanner(obj);
        while(ip.hasNext()){
            uid = ip.nextLine();
        }
        ip.close();
        }
        catch(FileNotFoundException ae){
            System.out.println(ae);
        }
        while(connectToDatabase()!=true){
            connectToDatabase();
        }
        if(e.getSource()==l_100){
            int money = 100;
            try {
                if(checkSufficientBalance(money,uid)!=true){
                    JOptionPane.showMessageDialog(rootPane, "Insufficient Balance");
                }
                else{
                    withdrawAndUpdate(money,uid);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FastCash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getSource()==l_200){
            int money = 200;
            try {
                if(checkSufficientBalance(money,uid)!=true){
                    JOptionPane.showMessageDialog(rootPane, "Insufficient Balance");
                }
                else{
                    withdrawAndUpdate(money,uid);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FastCash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getSource()==l_500){
            int money = 500;
            try {
                if(checkSufficientBalance(money,uid)!=true){
                    JOptionPane.showMessageDialog(rootPane, "Insufficient Balance");
                }
                else{
                    withdrawAndUpdate(money,uid);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FastCash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getSource()==l_1000){
            int money = 1000;
            try {
                if(checkSufficientBalance(money,uid)!=true){
                    JOptionPane.showMessageDialog(rootPane, "Insufficient Balance");
                }
                else{
                    withdrawAndUpdate(money,uid);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FastCash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getSource()==l_2000){
            int money = 2000;
            try {
                if(checkSufficientBalance(money,uid)!=true){
                    JOptionPane.showMessageDialog(rootPane, "Insufficient Balance");
                }
                else{
                    withdrawAndUpdate(money,uid);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FastCash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getSource()==l_3000){
            int money = 3000;
            try {
                if(checkSufficientBalance(money,uid)!=true){
                    JOptionPane.showMessageDialog(rootPane, "Insufficient Balance");
                }
                else{
                    withdrawAndUpdate(money,uid);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FastCash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getSource()==l_5000){
            int money = 5000;
            try {
                if(checkSufficientBalance(money,uid)!=true){
                    JOptionPane.showMessageDialog(rootPane, "Insufficient Balance");
                }
                else{
                    withdrawAndUpdate(money,uid);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FastCash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getSource()==l_10000){
            int money = 10000;
            try {
                if(checkSufficientBalance(money,uid)!=true){
                    JOptionPane.showMessageDialog(rootPane, "Insufficient Balance");
                }
                else{
                    withdrawAndUpdate(money,uid);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FastCash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
        private boolean checkSufficientBalance(int withdraw_amount,String uid) throws SQLException{
            int senderBalance = 0;
            PreparedStatement p = con.prepareStatement("Select amount from entry where card_num = ?");
                p.setString(1,uid);
                set = p.executeQuery();
                while(set.next()){
                    senderBalance = set.getInt("amount");
                }
        return senderBalance >= withdraw_amount;
    }

    private void withdrawAndUpdate(int amount,String uid) throws SQLException{
        
        l_100.setText("");
        l_200.setText("");
        l_500.setText("");
        l_1000.setText("");
        l_2000.setText("");
        l_3000.setText("");
        l_5000.setText("");
        l_10000.setText("");
        
        
        label_show.setText("Your Transaction is Successful!");
        int current_amount = 0;
        PreparedStatement p = con.prepareStatement("Select amount from entry where card_num = ?");
        p.setString(1,uid);
        set = p.executeQuery();
        while(set.next()){
            current_amount = set.getInt("amount");
        }
        current_amount = current_amount - amount;
        //updating db
        PreparedStatement q = con.prepareStatement("Update entry set amount = ? where card_num = ?");
        q.setInt(1, current_amount);
        q.setString(2, uid);
        q.executeUpdate();
        
        PreparedStatement update = con.prepareStatement("Insert into transaction values(?,?,CURRENT_TIMESTAMP,?)");
        update.setString(1,uid);
        update.setInt(2,amount);
        update.setString(3,"Debit");
        update.execute();
        int a = JOptionPane.showConfirmDialog(rootPane, "Do you want to check balance?");
        if(a==JOptionPane.YES_OPTION){
            label_show_amount.setText("Available Balance : Rs. " + Integer.toString(current_amount));
            //printing available balance n wait for 3 sec
            int delay = 3000;
            Timer timer = new Timer( delay, (ActionEvent e) -> {
                //after 3 sec colosing current frame & opening tq frame
                dispose();
                ThankYou tq = new ThankYou();
                int delay1 = 3000;
                Timer timer1 = new Timer(delay1, (ActionEvent e1) -> {
                    //now closing tq frame n opening main frame
                    tq.dispose();
                    new DemoBank();
                });
                timer1.setRepeats(false);
                timer1.start();
            });
                            timer.setRepeats( false );
                            timer.start();
        }
        else{
            dispose();
                ThankYou tq = new ThankYou();
                            int delay = 3000;
                            Timer timer = new Timer( delay, (ActionEvent e) -> {
                                //now closing tq frame n opening main frame
                                tq.dispose();
                                new DemoBank();
            });
                            timer.setRepeats( false );
                            timer.start();                            
        }
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
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

class ThankYou extends JFrame{
    private JLabel label;
    ThankYou(){
        Font f = new Font("Arial",Font.BOLD,15);
        label = new JLabel("Thank You For Banking with us!");
        label.setBounds(70,80,260,20);
        label.setFont(f);
        label.setForeground(Color.orange);
        add(label);
        
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("appIcon.png")));
        setLayout(null);
        setSize(400,250);
        setTitle("Transfer Money - Demo Bank");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        
    }
    
}