package demobank;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
public class ControlPanel implements ActionListener{
    
    final private JFrame frame = new JFrame();
    private Connection con;
    private Statement stmt;
    private ResultSet set;
    private String uid;
    final private Color C1 = new Color(51,153,255);
    final private Color C2 = new Color(207,134,25);
    final private Color C3 = new Color(20,133,86);
    final private JLabel label,loader;
    final private JButton chequeBook,miniStatement,pinChange,fastCash,withdrawal,balanceEnquiry,transfer,mobileRegistration;
    ControlPanel(){
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Font f = new Font("Serif",Font.BOLD+Font.ITALIC,25);
        label = new JLabel("Welcome to DemoBank");
        label.setBounds(220,30,250,30);
        label.setFont(f);
        label.setForeground(C1);
        frame.add(label);
        
        loader = new JLabel();
        loader.setIcon(new ImageIcon(getClass().getResource("loader.gif")));
        loader.setBounds(280,175,90,90);
        loader.setVisible(false);
        frame.add(loader);        
        
        chequeBook = new JButton("Cheque Book Request");
        chequeBook.setBounds(50,140,200,40);
        chequeBook.setBackground(C3);
        chequeBook.addActionListener(this);
        frame.add(chequeBook);
        
        
        fastCash = new JButton("Fast Cash");
        fastCash.setBounds(430,140,200,40);
        fastCash.setBackground(C2);
        fastCash.addActionListener(this);
        frame.add(fastCash);
        
        
        withdrawal = new JButton("Withdrawal");
        withdrawal.setBounds(430,210,200,40);
        withdrawal.setBackground(C2);
        withdrawal.addActionListener(this);
        frame.add(withdrawal);
        
        miniStatement = new JButton("Mini Statement");
        miniStatement.setBounds(50,210,200,40);
        miniStatement.setBackground(C3);
        miniStatement.addActionListener(this);
        frame.add(miniStatement);
        
        
        balanceEnquiry = new JButton("Balance Enquiry");
        balanceEnquiry.setBounds(430,280,200,40);
        balanceEnquiry.setBackground(C2);
        balanceEnquiry.addActionListener(this);
        frame.add(balanceEnquiry);
        
        
        pinChange = new JButton("PIN Change");
        pinChange.setBounds(50,280,200,40);
        pinChange.setBackground(C3);
        pinChange.addActionListener(this);
        frame.add(pinChange);
        
        
        transfer = new JButton("Transfer");
        transfer.setBounds(430,350,200,40);
        transfer.setBackground(C2);
        transfer.addActionListener(this);
        frame.add(transfer);
        
        
        mobileRegistration = new JButton("Mobile Registration");
        mobileRegistration.setBounds(50,350,200,40);
        mobileRegistration.setBackground(C3);
        mobileRegistration.addActionListener(this);
        frame.add(mobileRegistration);
        
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("DemoBankIcon.png")));
        frame.setLayout(null);
        frame.setSize(700,500);
        frame.setTitle("Choose Service - Demo Bank");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
    
    
    @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==chequeBook){
                try {
                    applyForChequebook();
                } catch (SQLException | InterruptedException ex) {
                    Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(e.getSource()==fastCash){
                frame.dispose();
                new FastCash();
                
            }
            else if(e.getSource()==withdrawal){
                frame.dispose();
                new WithdrawlAmount();
            }
            else if(e.getSource()==miniStatement){
                try {
                    loader.setVisible(false);
                    new MiniStatement().setVisible(true);
                    frame.dispose();
                } catch (FileNotFoundException | SQLException ex) {
                    Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(e.getSource()==balanceEnquiry){
                if(connectToDatabase()!=true){
                    connectToDatabase();
                }
                else{
                    try {
                        balanceEnquiry();
                    } catch (FileNotFoundException | SQLException | InterruptedException ex) {
                        Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else if(e.getSource()==pinChange){
                if(connectToDatabase()!=true){
                    connectToDatabase();
                }
                else{
                    try {
                        pinChange();
                    } catch (SQLException | InterruptedException ex) {
                        Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else if(e.getSource()==transfer){
                frame.dispose();
                new Transfer();
            }
            else if(e.getSource()==mobileRegistration){
                if(connectToDatabase()!=true){
                    connectToDatabase();
                } else {
                    try {
                        updateMobileNumber();
                    } catch (SQLException | InterruptedException ex) {
                        Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }
        
    private void applyForChequebook() throws SQLException, InterruptedException {
        try{ 
        String data = JOptionPane.showInputDialog(frame,"Enter Your Account Number");
        if(data.isEmpty()){
            JOptionPane.showMessageDialog(frame,"Please Enter Your Account Number");
            applyForChequebook();
        }
        else{
            JOptionPane.showMessageDialog(frame,"Applied Successfully");
            frame.dispose();
            ThankYouClass thankYouClass =new ThankYouClass();
            //i need ryt code
            int delay = 5000;
            Timer timer = new Timer( delay, (ActionEvent e) -> {
                thankYouClass.dispose();
                new DemoBank();
            });
            timer.setRepeats( false );
            timer.start();
            }
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        
    }
    
    private void updateMobileNumber() throws SQLException, InterruptedException{
        String data = JOptionPane.showInputDialog(frame,"Enter Last 4-digits of your Card Number");
        if(data.isEmpty()){
            JOptionPane.showMessageDialog(frame,"Please Enter Last 4-digits of your Card Number");
            updateMobileNumber();
        }
        else{
            if(validateUser(data)){
                String number = JOptionPane.showInputDialog(frame,"Enter Your Mobile Number");
                if(number.isEmpty()){
                    JOptionPane.showMessageDialog(frame,"Please try again");
                    updateMobileNumber();
                }
                else if(number.length()!=10){
                    JOptionPane.showMessageDialog(frame,"Please Enter Valid Mobile Number of 10 digits");
                    updateMobileNumber();
                }
                else{
                    PreparedStatement update = con.prepareStatement("Update entry set mobile_number = ? where card_num = ?");
                    update.setString(1, number);
                    update.setString(2, uid);
                    update.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Mobile Number Registered Successfully");
                    con.close();
                    frame.dispose();
                    ThankYouClass thankYouClass =new ThankYouClass();
                    //i need ryt code
                    int delay = 3000;
                    Timer timer = new Timer( delay, (ActionEvent e) -> {
                        thankYouClass.dispose();
                        new DemoBank();
                    });
                    timer.setRepeats( false );
                    timer.start();
                        }
                    }
            else{
                JOptionPane.showMessageDialog(frame,"Incorrect Card Number");
            }
        }
    }
    
    private void pinChange() throws SQLException, InterruptedException{
        String data = JOptionPane.showInputDialog(frame,"Enter Last 4-digits of your Card Number");
        if(data.isEmpty()){
            JOptionPane.showMessageDialog(frame,"Please Enter Last 4-digits of your Card Number");
            pinChange();
        }
        else{
            if(validateUser(data)){
                String pinNumber = JOptionPane.showInputDialog(frame,"Enter New Pin");
                if(pinNumber.isEmpty()){
                    JOptionPane.showMessageDialog(frame,"Please try again");
                    pinChange();
                }
                else{
                    PreparedStatement update = con.prepareStatement("Update entry set pin = ? where card_num = ?");
                    update.setString(1, pinNumber);
                    update.setString(2, uid);
                    update.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Pin Changed Successfully");
                    con.close();
                    frame.dispose();
                    ThankYouClass thankYouClass = new ThankYouClass();
                    //i need ryt code
                    int delay = 5000;
                    Timer timer = new Timer( delay, (ActionEvent e) -> {
                        thankYouClass.dispose();
                        new DemoBank();
                    });
                    timer.setRepeats( false );
                    timer.start();            
                        }
            }
            else{
                JOptionPane.showMessageDialog(frame, "Card Number is not found");
                frame.dispose();
            }
        }
    }
    
    
    private void balanceEnquiry() throws FileNotFoundException, SQLException, InterruptedException{
        String pinCode="",amount="";
        File obj = new File("text.txt");
        Scanner ip = new Scanner(obj);
        while(ip.hasNext()){
            pinCode = ip.nextLine();
        }
        if(obj.delete()){
            System.out.print("yes");
        }
        else{
            System.out.print("no");
        }
        if(connectToDatabase()!=true){
            connectToDatabase();
        }
        else{
            PreparedStatement p = con.prepareStatement("select amount from entry where pin = ?");
            p.setString(1, pinCode);
            set = p.executeQuery();
            while(set.next()){
                amount=set.getString("amount");
            }
            if(amount == null){
                JOptionPane.showMessageDialog(frame, "Your Current Account Balance is : Rs." + 0);
                frame.dispose();
                ThankYouClass thankYouClass =new ThankYouClass();
                //i need ryt code
                int delay = 5000;
                Timer timer = new Timer( delay, (ActionEvent e) -> {
                    thankYouClass.dispose();
                    new DemoBank();
                });
                timer.setRepeats( false );
                timer.start();
                }
            else{
                JOptionPane.showMessageDialog(frame, "Your Current Account Balance is : Rs." + amount);
                frame.dispose();
                ThankYouClass thankYouClass =new ThankYouClass();
                //i need ryt code
                int delay = 5000;
                Timer timer = new Timer( delay, (ActionEvent e) -> {
                    thankYouClass.dispose();
                    new DemoBank();
                });
                timer.setRepeats( false );
                timer.start();
            }
        }
        ip.close();
    }
    
    
    
    private boolean validateUser(String data)  throws SQLException{
        try{ 
            stmt = con.createStatement();
            set = stmt.executeQuery("Select card_num from entry");
            while(set.next()){
                if(data.equals(set.getString("card_num"))){
                    uid = set.getString(("card_num"));
                    return true;
                }
            }
        }
        catch(NullPointerException e){
        }
        return false;
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

class WithdrawlAmount extends JFrame{
    final private JTextField amount;
    private int senderBalance=0;
    final private JLabel label,label2,label3;
    final private JButton enter;
    private Connection con;
    private String uid;
    private ResultSet set;
    WithdrawlAmount(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        label = new JLabel("Enter Amount in multiples of 100X");
        label.setBounds(250,100,200,30);
        add(label);
        
        amount = new JTextField();
        amount.setBounds(290,140,100,30);
        add(amount);

        amount.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterBtnEvent();
                }
            }
        });         
        
        label2 = new JLabel();
        label2.setBounds(290,220,120,30);
        label2.setForeground(Color.RED);
        add(label2);
        
        
        Font f = new Font("Arial",Font.BOLD,18);
        label3 = new JLabel("");
        label3.setBounds(200,220,300,30);
        label3.setFont(f);
        label3.setForeground(Color.orange);
        add(label3);
        
        
        enter = new JButton("Enter");
        enter.setBounds(310,180,60,30);
        add(enter);
        enter.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterBtnEvent();
                }
            }
        });         
        enter.addActionListener((ActionEvent ae) -> {
            enterBtnEvent();
        });
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("DemoBankIcon.png")));
        setLayout(null);
        setSize(700,500);
        setTitle("Transfer Money - Demo Bank");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
private void enterBtnEvent(){
            String take_amount = amount.getText();
            
            if(take_amount.equals("")){
                JOptionPane.showMessageDialog(rootPane,"Please Enter Amount\nAmount should not be empty");
            }
            int withdraw_amount=Integer.parseInt(take_amount);
            System.out.print(withdraw_amount%100);
            if(withdraw_amount%100!=0){
                JOptionPane.showMessageDialog(rootPane, "Amount should be in multiples of 100X");
            }
            else{
                while(connectToDatabase()!=true){
                    connectToDatabase();
                }
                try {
                    if(checkSufficientBalance(withdraw_amount)!=true){
                        //JOptionPane.showMessageDialog(rootPane, "Insufficient Balance");
                        label3.setText("");
                        label2.setText("Insufficient Balance");
                    }
                    else{
                        WithdrawAmount(withdraw_amount,uid);
                    }
                } catch (FileNotFoundException | SQLException | InterruptedException ex) {
                    Logger.getLogger(WithdrawlAmount.class.getName()).log(Level.SEVERE, null, ex);
                }
            }    
}    
    private boolean checkSufficientBalance(int withdraw_amount) throws FileNotFoundException, SQLException{
        File obj = new File("text2.txt");
        Scanner ip = new Scanner(obj);
        while(ip.hasNext()){
            uid = ip.nextLine();
        }
        
        PreparedStatement p = con.prepareStatement("Select amount from entry where card_num = ?");
                p.setString(1,uid);
                set = p.executeQuery();
                while(set.next()){
                    senderBalance = set.getInt("amount");
                }
                ip.close();
        return senderBalance >= withdraw_amount;
    }
    
    private void WithdrawAmount(int withdraw_amount,String uid) throws SQLException, InterruptedException{
        label2.setText("");
        label3.setText("Your Transaction is Successful!");
        
        int current_amount = 0;
        PreparedStatement p = con.prepareStatement("Select amount from entry where card_num = ?");
        p.setString(1,uid);
        set = p.executeQuery();
        while(set.next()){
            current_amount = set.getInt("amount");
        }
        current_amount = current_amount - withdraw_amount;
        //updating db
        PreparedStatement q = con.prepareStatement("Update entry set amount = ? where card_num = ?");
        q.setInt(1, current_amount);
        q.setString(2, uid);
        q.executeUpdate();
        
        PreparedStatement update = con.prepareStatement("Insert into transaction values(?,?,CURRENT_TIMESTAMP,?)");
        update.setString(1,uid);
        update.setInt(2,withdraw_amount);
        update.setString(3,"Debit");
        update.execute();
        int a = JOptionPane.showConfirmDialog(rootPane, "Do you want to check balance?");
        if(a==JOptionPane.YES_OPTION){        
            label2.setText("");
            label3.setText("                      Rs. " + Integer.toString(current_amount));
            //printing available balance n wait for 3 sec
            int delay = 3000;
            Timer timer = new Timer( delay, (ActionEvent e) -> {
                try {
                    //after 3 sec colosing current frame & opening tq frame
                    dispose();
                    ThankYouClass tq = new ThankYouClass();
                    int delay1 = 3000;
                    Timer timer1 = new Timer(delay1, (ActionEvent e1) -> {
                        //now closing tq frame n opening main frame
                        tq.dispose();
                        new DemoBank();
                    });
                    timer1.setRepeats(false);
                    timer1.start();
                }catch (InterruptedException ex) {
                    Logger.getLogger(WithdrawlAmount.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
                            timer.setRepeats( false );
                            timer.start();            
        }
        else{
            dispose();
                    ThankYouClass tq = new ThankYouClass();
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
            if(con.isClosed()!=true ){
                return true;
            }
            
        }
        catch(ClassNotFoundException | SQLException e){
            
        }
        return false;
    }
    
}
