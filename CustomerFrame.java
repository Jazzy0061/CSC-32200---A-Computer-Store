import java.awt.*;
import javax.swing.*;

public class CustomerFrame extends JFrame {
	public CustomerFrame(String name) {
		setTitle("Welcome, " + name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        pack();
        setSize(960,540);
        setLocationRelativeTo(null);
    }
	
	private void initComponents() { // Add new components for Customer GUI here
        JLabel welcomeLabel = new JLabel("Customer welcome page", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);
    }
}
