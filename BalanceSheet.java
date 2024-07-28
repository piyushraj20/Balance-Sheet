import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BalanceSheet extends JFrame {
    private DefaultListModel<String> expenditureListModel;
    private JList<String> expenditureList;
    private JTextField incomeField, descriptionField, amountField;
    private JLabel balanceLabel;
    private double totalIncome = 0;
    private double totalExpenditure = 0;

    public BalanceSheet() {
        setTitle("Balance Sheet");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        
        JPanel incomePanel = new JPanel();
        incomePanel.setLayout(new GridLayout(2, 2));
        JLabel incomeLabel = new JLabel("Income:");
        incomeField = new JTextField();
        incomePanel.add(incomeLabel);
        incomePanel.add(incomeField);
        panel.add(incomePanel, BorderLayout.NORTH);


        JPanel expenditurePanel = new JPanel();
        expenditurePanel.setLayout(new GridLayout(3, 2));
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        expenditurePanel.add(descriptionLabel);
        expenditurePanel.add(descriptionField);

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();
        expenditurePanel.add(amountLabel);
        expenditurePanel.add(amountField);

        JButton addExpenditureButton = new JButton("Add Expenditure");
        addExpenditureButton.addActionListener(new AddExpenditureListener());
        expenditurePanel.add(addExpenditureButton);
        panel.add(expenditurePanel, BorderLayout.CENTER);

        expenditureListModel = new DefaultListModel<>();
        expenditureList = new JList<>(expenditureListModel);
        JScrollPane scrollPane = new JScrollPane(expenditureList);
        panel.add(scrollPane, BorderLayout.WEST);

        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new GridLayout(1, 2));
        JButton calculateButton = new JButton("Calculate Balance");
        calculateButton.addActionListener(new CalculateBalanceListener());
        balancePanel.add(calculateButton);
        balanceLabel = new JLabel("Balance: ");
        balancePanel.add(balanceLabel);
        panel.add(balancePanel, BorderLayout.SOUTH);

        JButton editExpenditureButton = new JButton("Edit Expenditure");
        editExpenditureButton.addActionListener(new EditExpenditureListener());
        expenditurePanel.add(editExpenditureButton);

        add(panel);
        setVisible(true);
    }

    private class AddExpenditureListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String description = descriptionField.getText();
            String amountString = amountField.getText();
            if (!description.trim().isEmpty() && !amountString.trim().isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountString);
                    totalExpenditure += amount;
                    expenditureListModel.addElement(description + ": $" + amount);
                    descriptionField.setText("");
                    amountField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for amount.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter both description and amount for expenditure.");
            }
        }
    }

    private class EditExpenditureListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = expenditureList.getSelectedIndex();
            if (selectedIndex != -1) {
                String editedDescription = JOptionPane.showInputDialog(null, "Enter new description:");
                String editedAmountString = JOptionPane.showInputDialog(null, "Enter new amount:");
                try {
                    double editedAmount = Double.parseDouble(editedAmountString);
                    String originalItem = expenditureListModel.getElementAt(selectedIndex);
                    expenditureListModel.set(selectedIndex, editedDescription + ": $" + editedAmount);
                    String[] parts = originalItem.split(": \\$");
                    double originalAmount = Double.parseDouble(parts[1]);
                    totalExpenditure = totalExpenditure - originalAmount + editedAmount;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for amount.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an expenditure to edit.");
            }
        }
    }

    private class CalculateBalanceListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double income = Double.parseDouble(incomeField.getText());
                totalIncome = income;
                double balance = totalIncome - totalExpenditure;
                balanceLabel.setText("Balance: $" + balance);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for income.");
            }
        }
    }

    public static void main(String[] args) {
        new BalanceSheet();
    }
}
