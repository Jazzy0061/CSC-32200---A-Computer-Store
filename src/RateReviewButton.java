import javax.swing.*;
import java.awt.*;


public class RateReviewButton extends JFrame {
    JLabel initialRating, ratingVal, review ;
    JTextField txtDeposit; JButton btnDeposit;
    JPanel mainPanel,initialPanel, depositPanel;

    public RateReviewButton(){
        super("Rate Comment and Review");
        mainPanel = new JPanel(new GridLayout(4,3));
        mainPanel.add(configureRating());
        mainPanel.add(configureReview());
        add(mainPanel);
        this.pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    private JPanel configureRating(){
        initialPanel = new JPanel();
        initialRating = new JLabel("Rate Your Experience 1 through 5 ");
        txtDeposit = new JTextField(5);
        ratingVal = new JLabel("1 (Worst) - 5 (Best)");
        initialPanel.add(initialRating);
        initialPanel.add(txtDeposit);
        initialPanel.add(ratingVal);
        return initialPanel;
    }

    private JPanel configureReview(){
        depositPanel = new JPanel();
        review = new JLabel("Leave Review Here");
        txtDeposit = new JTextField(50);
        btnDeposit = new JButton("Submit");
        depositPanel.add(review);
        depositPanel.add(txtDeposit);
        depositPanel.add(btnDeposit);
        return depositPanel;

    }

    public static void main(String[] args) {
        new RateReviewButton();
    }
}