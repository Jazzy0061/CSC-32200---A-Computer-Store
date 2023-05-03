import java.awt.*;
import javax.swing.*;

public class VisitorFrame extends JFrame {
	public VisitorFrame() {
		setTitle("Welcome, Guest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        pack();
        setSize(960,540);
        setLocationRelativeTo(null);
    }
	
	private void initComponents() { // Add new components for Visitor GUI here
        JLabel welcomeLabel = new JLabel("Visitor welcome page", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);
    }
}
