package Code;

import GUI.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class FitnessTrackerApp {
    public static void main(String[] args) {
        // Load users from the text file or create a new list if the file doesn't exist
        ArrayList<GeneralUser> users = FileHandler.parseUsersFromTextFile("users.txt");
        if (users == null || users.isEmpty()) {
            users = new ArrayList<>();
            // Add a default user for testing
            users.add(new GeneralUser("ExampleName", "U1", "password", "email@example.com", "1234567890", "America", new Date()));
            FileHandler.saveToTextFile("users.txt", users); // Save default user to the file
        }

        // Create Main JFrame
        JFrame mainFrame = new JFrame("Fitness Tracker");
        mainFrame.setSize(1000, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        // Main container with CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Create Welcome Screen
        JPanel welcomeScreen = createWelcomeScreen(cardPanel, cardLayout);

        // Create Signup Screen
        UserSignup signupForm = new UserSignup(users, cardPanel, cardLayout);
        cardPanel.add(signupForm.getRootPanel(), "Signup");

        // Create User Login Screen
        UserLogin userLoginForm = new UserLogin(users, cardPanel, cardLayout);
        cardPanel.add(userLoginForm.getRootPanel(), "UserLogin");

        // Create Admin Login Screen
        AdminLogin adminLoginForm = new AdminLogin(users, cardPanel, cardLayout);
        cardPanel.add(adminLoginForm.getRootPanel(), "AdminLogin");

        // Add Welcome Screen to the cardPanel
        cardPanel.add(welcomeScreen, "Welcome");

        // Add cardPanel to the main frame
        mainFrame.add(cardPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    /**
     * Creates the Welcome Screen with navigation buttons.
     *
     * @param cardPanel   The main CardLayout container.
     * @param cardLayout  The CardLayout for navigation.
     * @return JPanel     The welcome screen panel.
     */
    private static JPanel createWelcomeScreen(JPanel cardPanel, CardLayout cardLayout) {
        JPanel welcomeScreen = new JPanel();
        welcomeScreen.setLayout(null);

        JLabel titleLabel = new JLabel("Welcome to Fitness Tracker", SwingConstants.CENTER);
        titleLabel.setBounds(200, 50, 600, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeScreen.add(titleLabel);

        JButton signupButton = new JButton("Signup User");
        signupButton.setBounds(350, 150, 300, 40);
        welcomeScreen.add(signupButton);

        JButton userLoginButton = new JButton("Login User");
        userLoginButton.setBounds(350, 220, 300, 40);
        welcomeScreen.add(userLoginButton);

        JButton adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setBounds(350, 290, 300, 40);
        welcomeScreen.add(adminLoginButton);

        // Action Listeners for navigation
        signupButton.addActionListener(e -> cardLayout.show(cardPanel, "Signup"));
        userLoginButton.addActionListener(e -> cardLayout.show(cardPanel, "UserLogin"));
        adminLoginButton.addActionListener(e -> cardLayout.show(cardPanel, "AdminLogin"));

        return welcomeScreen;
    }
}
