package GUI;

import Code.FileHandler;
import Code.GeneralUser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserLogin {
    private JPanel rootPanel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;

    private final ArrayList<GeneralUser> users;

    public UserLogin(ArrayList<GeneralUser> finalUsers, JPanel cardPanel, CardLayout cardLayout) {
        this.users = FileHandler.parseUsersFromTextFile("users.txt");

        // Ensure rootPanel is not null
        if (rootPanel == null) {
            System.out.println("rootPanel is not initialized properly!");
            return;
        }

        // Login Button Action Listener
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            GeneralUser loggedInUser = validateUser(email, password);
            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(rootPanel, "Login Successful!");

                // Navigate to UserDashboard
                UserDashboard dashboard = new UserDashboard(loggedInUser, cardPanel, cardLayout);
                cardPanel.add(dashboard.getRootPanel(), "UserDashboard");
                cardLayout.show(cardPanel, "UserDashboard");
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Invalid Credentials!");
            }
        });

        // Back Button Action Listener
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Welcome"));
    }


    private GeneralUser validateUser(String email, String password) {
        for (GeneralUser user : users) {
            System.out.println("Checking user: " + user.getEmail() + " against " + email);
            System.out.println("User email bytes: " + java.util.Arrays.toString(user.getEmail().getBytes()));
            System.out.println("Input email bytes: " + java.util.Arrays.toString(email.getBytes()));
            System.out.println("User password bytes: " + java.util.Arrays.toString(user.getPassword().getBytes()));
            System.out.println("Input password bytes: " + java.util.Arrays.toString(password.getBytes()));

            if (user.getEmail().trim().equalsIgnoreCase(email.trim()) &&
                    user.getPassword().trim().equals(password.trim())) {
                return user; // Login successful
            }
        }
        System.out.println("No matching user found for email: " + email);
        return null; // Login failed
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
