package GUI;

import Code.Admin;
import Code.GeneralUser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminLogin {
    private JPanel rootPanel;
    private JLabel titleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    public AdminLogin(ArrayList<GeneralUser> users, JPanel cardPanel, CardLayout cardLayout) {
        rootPanel.setLayout(new BorderLayout());
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.equals("admin") && password.equals("admin2023")) {
                Admin admin = new Admin();
                AdminDashboard adminDashboard = new AdminDashboard(admin, users, cardPanel, cardLayout);
                cardPanel.add(adminDashboard.getRootPanel(), "AdminDashboard");
                cardLayout.show(cardPanel, "AdminDashboard");
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Invalid credentials. Please try again.");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Welcome"));
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
