import java.awt.*;
import javax.swing.*;

public class OwnerFrame extends JFrame { 
	public OwnerFrame(String name) {
		setTitle("Welcome, " + name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        pack();
        setSize(960,540);
        setLocationRelativeTo(null);
    }
	
	private void initComponents() { // Add components for Owner GUI here
        JLabel welcomeLabel = new JLabel("Owner welcome page", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);
    }
}
