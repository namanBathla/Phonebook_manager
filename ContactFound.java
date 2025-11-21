import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Random;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;


public class ContactFound implements ActionListener {

    // --------- Declaring the components (Globally) ----------

    Connection con;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    JTextField foundName = new JTextField("This is the name found");
    JTextField foundMobile = new JTextField("This is the Number found");
    JTextField foundEmail = new JTextField("This is the Email found");

    JButton okButton = new JButton();

    private void initFrame(){
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));

        // ---------------- object of random class --------------
        Random random = new Random();
        // int x = obj.nextInt(250, 300);
        // int y = obj.nextInt(250, 300);
        // ---------- Setting random location of frame ------
        // ----- so that it does not overlap the main window -----
        frame.setLocation(random.nextInt(400,500), random.nextInt(250,300));
        frame.setResizable(false);
        frame.getRootPane().setDefaultButton(okButton);
    }


    private void initPanel(){
        panel.setPreferredSize(new Dimension(400, 300));
        panel.setBackground(new Color(37,37,38));
        panel.setLayout(null);
    }

    private void initButtons() {
        okButton.setText("OK");
        okButton.setBounds(145, 210, 120, 40);
        okButton.setBackground(new Color(75, 0, 130));
        okButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        okButton.setFocusable(false);
        okButton.setFont(new Font("Calibri", Font.PLAIN, 25));
        okButton.setForeground(Color.WHITE);
    }

    private void initTextFields(){

        foundName.setBounds(50, 50, 300, 40);
        foundName.setFont(new Font("Calibri", Font.PLAIN, 23));
        // foundName.setBorder(BorderFactory.createLineBorder(new Color(75, 0, 130), 3));
        foundName.setForeground(new Color(75, 0, 130));
        foundName.setHorizontalAlignment(JTextField.CENTER);
        foundName.setEditable(false);
        foundName.setFocusable(false);
        
        foundMobile.setBounds(50, 90, 300, 40);
        foundMobile.setFont(new Font("Calibri", Font.PLAIN, 23));
        // foundMobile.setBorder(BorderFactory.createLineBorder(new Color(75, 0, 130), 3));
        foundMobile.setForeground(new Color(75, 0, 130));
        foundMobile.setHorizontalAlignment(JTextField.CENTER);
        foundMobile.setEditable(false);
        foundMobile.setFocusable(false);
        
        foundEmail.setBounds(50, 130, 300, 40);
        foundEmail.setFont(new Font("Calibri", Font.PLAIN, 23));
        // foundEmail.setBorder(BorderFactory.createLineBorder(new Color(75, 0, 130), 3));
        foundEmail.setForeground(new Color(75, 0, 130));
        foundEmail.setHorizontalAlignment(JTextField.CENTER);
        foundEmail.setEditable(false);
        foundEmail.setFocusable(false);
    }


    public void establishConnectionWithDB() {
        try {

//            Class.forName("com.mysql.jdbc.Driver");   // Loading the class
            // step 1: Initialize connection object
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook", "root", "Sql@1234");

//            closing the connection;
//            con.close();
        }
        catch (Exception e) {
            System.out.println("\n" + e + "\n");
        }
    }

    private void findContactFromDB(String contactName){
        establishConnectionWithDB();
        frame.setTitle(contactName);
        try {
            PreparedStatement ps = con.prepareStatement("Select * from contacts where name = ?");
            ps.setString(1, contactName);

            ResultSet rs = ps.executeQuery();
            rs.next();
            foundName.setText(rs.getString(1));
            foundMobile.setText(rs.getString(2));
            foundEmail.setText(rs.getString(3));
            con.close();
        }
        catch(Exception e){
            System.out.println("Exception Occurred: " + e);
        }
    }


    private void addElementsToFrame(){
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void addElementsToPanel(){
        panel.add(foundName);
        panel.add(foundMobile);
        panel.add(foundEmail);

        panel.add(okButton);
    }

    private void addActionListenerToButtons() {
        okButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == okButton) {
            frame.dispose();
        }
    }


        ContactFound(String contactName){
        findContactFromDB(contactName);   // loading from DB takes time

        initFrame();
        initPanel();

        initTextFields();
        initButtons();
        addActionListenerToButtons();

        addElementsToPanel();
        addElementsToFrame();

    }


    public static void main(String[] args){
        new ContactFound("jashan");
    }

}



