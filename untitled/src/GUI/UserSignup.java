package GUI;

import Code.FileHandler;
import Code.GeneralUser;
import Code.User;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserSignup {
    private JPanel rootPanel;
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField dobField;
    private JButton signupButton;
    private JButton backButton;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel phoneLabel;
    private JLabel addressLabel;
    private JLabel dobLabel;

    public UserSignup(ArrayList<GeneralUser> users, JPanel cardPanel, CardLayout cardLayout) {
        rootPanel.setLayout(new BorderLayout());
        // Signup Button Action Listener
        signupButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String phone = phoneField.getText().trim();
                String address = addressField.getText().trim();
                Date dob = new SimpleDateFormat("dd-MM-yyyy").parse(dobField.getText().trim());

                // Validate Inputs
                if (!User.isValidName(name)) {
                    throw new IllegalArgumentException("Invalid Name: Must be at least 3 characters.");
                }
                if (!User.isValidEmail(email)) {
                    throw new IllegalArgumentException("Invalid Email Format.");
                }
                if (!User.isValidPassword(password)) {
                    throw new IllegalArgumentException("Invalid Password: Must be at least 6 characters.");
                }
                if (!User.isValidPhoneNumber(phone)) {
                    throw new IllegalArgumentException("Invalid Phone Number: Must be 10 digits.");
                }

                // Create New User
                GeneralUser newUser = new GeneralUser(
                        name,
                        "U" + (users.size() + 1), // Generate a unique ID
                        password,
                        email,
                        phone,
                        address,
                        dob
                );

                // Add User to List
                users.add(newUser);

                // Save Updated User List to File
                FileHandler.saveToTextFile("users.txt", users);

                JOptionPane.showMessageDialog(rootPanel, "Signup Successful! User ID: " + newUser.getId());
                cardLayout.show(cardPanel, "Welcome"); // Navigate to Welcome Screen
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(rootPanel, "Error: " + ex.getMessage());
            }
        });

        // Back Button Action Listener
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Welcome"));
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
