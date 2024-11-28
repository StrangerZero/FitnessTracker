package GUI;

import Code.FitnessActivity;
import Code.GeneralUser;
import Code.Goal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDashboard {
    private JPanel rootPanel;
    private JLabel titleLabel;
    private JScrollPane activityScrollPane;
    private JTable activityTable;
    private JButton logActivityButton;
    private JButton manageGoalsButton;
    private JButton generateReportButton;
    private JButton logoutButton;

    private GeneralUser currentUser;

    public UserDashboard(GeneralUser user, JPanel cardPanel, CardLayout cardLayout) {
        this.currentUser = user;
        rootPanel.setLayout(new BorderLayout());

        // Update title with user name
        titleLabel.setText("Welcome, " + currentUser.getName());

        // Button actions
        logActivityButton.addActionListener(e -> openLogActivityDialog());
        manageGoalsButton.addActionListener(e -> openGoalManagementDialog());
        generateReportButton.addActionListener(e -> generateUserReport());
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "Welcome"));

        // Initialize activity table
        setupActivityTable();
        updateActivityTable();
    }

    private void setupActivityTable() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Activity Name", "Duration (hrs)", "Calories Burned", "Date", "Time"}
        );
        activityTable.setModel(model);
    }

    private void updateActivityTable() {
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setRowCount(0); // Clear table
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (FitnessActivity activity : currentUser.getActivities()) {
            model.addRow(new Object[]{
                    activity.getActivityName(),
                    activity.getDuration(),
                    activity.getCaloriesBurned(),
                    sdf.format(activity.getDate()),
                    activity.getTime()
            });
        }
    }

    private void openLogActivityDialog() {
        Window parentWindow = SwingUtilities.getWindowAncestor(rootPanel);
        JDialog logActivityDialog = new JDialog(parentWindow instanceof Frame ? (Frame) parentWindow : null, "Log Activity", true);
        logActivityDialog.setLayout(new GridLayout(6, 2, 5, 5));
        logActivityDialog.setSize(400, 300);

        // Components for dialog
        JTextField nameField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField caloriesField = new JTextField();
        JTextField dateField = new JTextField("yyyy-MM-dd");
        JTextField timeField = new JTextField();

        logActivityDialog.add(new JLabel("Activity Name:"));
        logActivityDialog.add(nameField);
        logActivityDialog.add(new JLabel("Duration (hrs):"));
        logActivityDialog.add(durationField);
        logActivityDialog.add(new JLabel("Calories Burned:"));
        logActivityDialog.add(caloriesField);
        logActivityDialog.add(new JLabel("Date (yyyy-MM-dd):"));
        logActivityDialog.add(dateField);
        logActivityDialog.add(new JLabel("Time:"));
        logActivityDialog.add(timeField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        logActivityDialog.add(saveButton);
        logActivityDialog.add(cancelButton);

        saveButton.addActionListener(e -> {
            try {
                String activityName = nameField.getText();
                double duration = Double.parseDouble(durationField.getText());
                double caloriesBurned = Double.parseDouble(caloriesField.getText());
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText());
                String time = timeField.getText();

                FitnessActivity activity = new FitnessActivity(activityName, duration, caloriesBurned, date, time);
                currentUser.logActivity(activity);

                updateActivityTable();
                JOptionPane.showMessageDialog(rootPanel, "Activity logged successfully!");
                logActivityDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(rootPanel, "Invalid input: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> logActivityDialog.dispose());

        logActivityDialog.setLocationRelativeTo(rootPanel);
        logActivityDialog.setVisible(true);
    }

    private void openGoalManagementDialog() {
        Window parentWindow = SwingUtilities.getWindowAncestor(rootPanel);
        JDialog goalDialog = new JDialog(parentWindow instanceof Frame ? (Frame) parentWindow : null, "Manage Goals", true);
        goalDialog.setLayout(new BorderLayout());
        goalDialog.setSize(600, 400);

        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Goal ID", "Description", "Start Date", "End Date", "Status"}
        );
        JTable goalTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(goalTable);

        for (Goal goal : currentUser.getGoals()) {
            model.addRow(new Object[]{
                    goal.getGoalId(),
                    goal.getDescription(),
                    new SimpleDateFormat("yyyy-MM-dd").format(goal.getStartDate()),
                    new SimpleDateFormat("yyyy-MM-dd").format(goal.getEndDate()),
                    goal.getStatus()
            });
        }

        JPanel buttonPanel = new JPanel();
        JButton addGoalButton = new JButton("Add Goal");
        JButton closeButton = new JButton("Close");
        buttonPanel.add(addGoalButton);
        buttonPanel.add(closeButton);

        goalDialog.add(scrollPane, BorderLayout.CENTER);
        goalDialog.add(buttonPanel, BorderLayout.SOUTH);

        addGoalButton.addActionListener(e -> openAddGoalDialog(model));
        closeButton.addActionListener(e -> goalDialog.dispose());

        goalDialog.setLocationRelativeTo(rootPanel);
        goalDialog.setVisible(true);
    }


    private void openAddGoalDialog(DefaultTableModel model) {
        Window parentWindow = SwingUtilities.getWindowAncestor(rootPanel);
        JDialog addGoalDialog = new JDialog(parentWindow instanceof Frame ? (Frame) parentWindow : null, "Add Goal", true);
        addGoalDialog.setLayout(new GridLayout(5, 2, 5, 5));
        addGoalDialog.setSize(400, 300);

        // Components for dialog
        JTextField goalIdField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField startDateField = new JTextField("yyyy-MM-dd");
        JTextField endDateField = new JTextField("yyyy-MM-dd");

        addGoalDialog.add(new JLabel("Goal ID:"));
        addGoalDialog.add(goalIdField);
        addGoalDialog.add(new JLabel("Description:"));
        addGoalDialog.add(descriptionField);
        addGoalDialog.add(new JLabel("Start Date (yyyy-MM-dd):"));
        addGoalDialog.add(startDateField);
        addGoalDialog.add(new JLabel("End Date (yyyy-MM-dd):"));
        addGoalDialog.add(endDateField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        addGoalDialog.add(saveButton);
        addGoalDialog.add(cancelButton);

        saveButton.addActionListener(e -> {
            try {
                String goalId = goalIdField.getText();
                String description = descriptionField.getText();
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateField.getText());
                Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateField.getText());

                Goal goal = new Goal(goalId, description, startDate, endDate);
                currentUser.setGoal(goal);

                model.addRow(new Object[]{
                        goal.getGoalId(),
                        goal.getDescription(),
                        new SimpleDateFormat("yyyy-MM-dd").format(goal.getStartDate()),
                        new SimpleDateFormat("yyyy-MM-dd").format(goal.getEndDate()),
                        goal.getStatus()
                });

                JOptionPane.showMessageDialog(rootPanel, "Goal added successfully!");
                addGoalDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(rootPanel, "Invalid input: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> addGoalDialog.dispose());

        addGoalDialog.setLocationRelativeTo(rootPanel);
        addGoalDialog.setVisible(true);
    }


    private void generateUserReport() {
        try {
            String fileName = "UserReport_" + currentUser.getName() + ".txt";
            FileWriter writer = new FileWriter(fileName);

            writer.write("===== User Report =====\n");
            writer.write("Name: " + currentUser.getName() + "\n");
            writer.write("ID: " + currentUser.getId() + "\n");
            writer.write("Email: " + currentUser.getEmail() + "\n");
            writer.write("\n--- Activities ---\n");
            for (FitnessActivity activity : currentUser.getActivities()) {
                writer.write(activity.toString() + "\n");
            }
            writer.write("\n--- Goals ---\n");
            for (Goal goal : currentUser.getGoals()) {
                writer.write(goal.toString() + "\n");
            }
            writer.write("========================");
            writer.close();

            JOptionPane.showMessageDialog(rootPanel, "Report generated: " + fileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPanel, "Error generating report: " + e.getMessage());
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
