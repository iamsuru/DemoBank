package demobank;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
public class MiniStatement extends javax.swing.JFrame {

    private Connection con;
    private ResultSet set;
    private String uid = "";
    public MiniStatement() throws FileNotFoundException, SQLException {
        initComponents();
        while(connectToDatabase()!=true){
            connectToDatabase();
        }
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("DemoBankIcon.png")));
        setResizable(false);
        setLocationRelativeTo(null);
        updateTable();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        exit_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No.", "Time", "Amount", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(0).setPreferredWidth(10);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(3).setPreferredWidth(30);
        }

        exit_button.setText("Exit");
        exit_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            try {
                exit_buttonActionPerformed(evt);
            } catch (InterruptedException ex) {
                Logger.getLogger(MiniStatement.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(exit_button, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(exit_button)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exit_buttonActionPerformed(java.awt.event.ActionEvent evt) throws InterruptedException {//GEN-FIRST:event_exit_buttonActionPerformed
        dispose();
        ThankYouClass thankYouClass =new ThankYouClass();
            int delay = 3000;
            javax.swing.Timer timer = new javax.swing.Timer( delay, (ActionEvent e) -> {
                thankYouClass.dispose();
                new DemoBank();
        });
            timer.setRepeats( false );
            timer.start();
    }//GEN-LAST:event_exit_buttonActionPerformed

    
    private void updateTable() throws FileNotFoundException,SQLException{
        File obj = new File("text2.txt");
        Scanner ip = new Scanner(obj);
        int i = 0;
        while(ip.hasNext()){
            uid = ip.nextLine();
        }
        ip.close();
        PreparedStatement p = con.prepareStatement("select * from transaction where card_num=? order by tran_datetime desc limit 5;");
        p.setString(1,uid);
        set = p.executeQuery();
        while(set.next()){
            String time = set.getString("tran_datetime");
            String amount = set.getString("tran_amount");
            String type = set.getString("tran_type");
            ++i;
            String data[] = {Integer.toString(i),time,amount,type};
            DefaultTableModel tblmodel = (DefaultTableModel)table.getModel();
            tblmodel.addRow(data);
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
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exit_button;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}