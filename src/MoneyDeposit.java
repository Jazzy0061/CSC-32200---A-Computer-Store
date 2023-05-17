import javax.swing.*;
import java.awt.*;


public class MoneyDeposit extends JFrame {
    JLabel initialBalance, balanceVal, deposit, newBalanceLbl, newBalanceField;
    JTextField txtDeposit; JButton btnDeposit;
    JPanel mainPanel, initialPanel, depositPanel, newBalance;

    public MoneyDeposit(){
        super("Bank Application");
        mainPanel = new JPanel(new GridLayout(4,1));
        mainPanel.add(configureInitial());
        mainPanel.add(configureDeposit());
        mainPanel.add(configureNew());
        add(mainPanel);
        this.pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel configureInitial(){
        initialPanel = new JPanel();
        initialBalance = new JLabel("Inital Balance: ");
        balanceVal = new JLabel("50.00");
        initialPanel.add(initialBalance);
        initialPanel.add(balanceVal);
        return initialPanel;
    }

    private JPanel configureDeposit(){
        depositPanel = new JPanel();
        deposit = new JLabel("Deposit");
        txtDeposit = new JTextField(10);
        btnDeposit = new JButton("Deposit");
        depositPanel.add(deposit);
        depositPanel.add(txtDeposit);
        depositPanel.add(btnDeposit);
        return depositPanel;

    }

    private JPanel configureNew(){
        newBalance = new JPanel();
        newBalanceLbl = new JLabel("New Balance: ");
        newBalanceField = new JLabel("50.00");
        newBalance.add(newBalanceLbl);
        newBalance.add(newBalanceField);
        return newBalance;
    }
}
