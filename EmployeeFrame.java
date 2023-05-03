import java.awt.*;
import javax.swing.*;

public class EmployeeFrame extends JFrame {
	public EmployeeFrame(String name) {
		setTitle("Welcome, " + name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        pack();
        setSize(960,540);
        setLocationRelativeTo(null);
    }
	
	private void initComponents() { // Add components for Employee GUI here
        JLabel welcomeLabel = new JLabel("Employee welcome page", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);
    }
}
