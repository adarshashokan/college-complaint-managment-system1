import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/*
 College Complaint Management System
*/

public class CollegeComplaintSystem extends JFrame {

    // Abstraction
    abstract class User {
        protected String name;
        public abstract String showDetails();
    }

    // Inheritance + Encapsulation + Polymorphism 
    class Complaint extends User {
        private int complaintId;
        private String department;
        private String description;

        public Complaint(int complaintId, String name, String department, String description) {
            this.complaintId = complaintId;
            this.name = name;
            this.department = department;
            this.description = description;
        }

        @Override
        public String showDetails() {
            return "ID: " + complaintId +
                   " | Name: " + name +
                   " | Dept: " + department +
                   " | Complaint: " + description;
        }
    }

    // Multithreading 
    class SaveThread extends Thread {
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println("Complaint saved successfully in background");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //  Data Storage 
    ArrayList<Complaint> complaints = new ArrayList<>();

    
    JTextField idField, nameField, deptField;
    JTextArea complaintArea, displayArea;

    // Constructor 
    public CollegeComplaintSystem() {

        setTitle("College Complaint Management System");
        setSize(550, 500);
        setLayout(new FlowLayout());

        idField = new JTextField(10);
        nameField = new JTextField(10);
        deptField = new JTextField(10);
        complaintArea = new JTextArea(3, 20);
        displayArea = new JTextArea(10, 45);
        displayArea.setEditable(false);

        JButton addBtn = new JButton("Submit Complaint");
        JButton viewBtn = new JButton("View Complaints");

        
        add(new JLabel("Complaint ID:"));
        add(idField);

        add(new JLabel("Student Name:"));
        add(nameField);

        add(new JLabel("Department:"));
        add(deptField);

        add(new JLabel("Complaint:"));
        add(new JScrollPane(complaintArea));

        add(addBtn);
        add(viewBtn);

        add(new JScrollPane(displayArea));

        
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String dept = deptField.getText();
                    String desc = complaintArea.getText();

                    if (name.isEmpty() || dept.isEmpty() || desc.isEmpty()) {
                        throw new Exception("All fields are required");
                    }

                    Complaint c = new Complaint(id, name, dept, desc);
                    complaints.add(c);

                    new SaveThread().start(); // multithreading

                    JOptionPane.showMessageDialog(null, "Complaint Submitted Successfully");

                    idField.setText("");
                    nameField.setText("");
                    deptField.setText("");
                    complaintArea.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Complaint ID must be a number");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        viewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("");
                for (Complaint c : complaints) {
                    displayArea.append(c.showDetails() + "\n");
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
 
    public static void main(String[] args) {
        new CollegeComplaintSystem();
    }
}