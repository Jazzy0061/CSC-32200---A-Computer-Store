import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;

public class Login {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel guestLogin;
    private JLabel failMessage;

    public LoginFrame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        initComponents();
        pack();
        setSize(getPreferredSize().width, getPreferredSize().height + 30);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        failMessage = new JLabel("Incorrect username or password");
        failMessage.setForeground(Color.RED);
        failMessage.setVisible(false);
        add(failMessage,c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5, 5, 5, 5);
        add(new JLabel("Username:"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        usernameField = new JTextField(15);
        add(usernameField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Password:"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        passwordField = new JPasswordField(15);
        add(passwordField, c);

        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
        guestLogin = new JLabel("<html><u>Log in as Guest</u></html>");
        guestLogin.setForeground(Color.BLUE);
        guestLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        guestLogin.addMouseListener(new GuestLoginMouseListener());
        add(guestLogin, c);
        
        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_START;
        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        add(loginButton, c);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // You can add authentication logic here based on the entered username and password
        	String userInput = usernameField.getText();
        	String passwordInput = new String(passwordField.getPassword());
        	
        	String line = "";
        	HashMap<String, String[]> userData = new HashMap<String, String[]>();
        	try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
	        	while((line = br.readLine()) != null) {
	        		String[] creds = line.split(",");
	        		String user = creds[0];
	        		String type = creds[1];
	        		String password = creds[2];
	        		userData.put(user, new String[] {type, password});
	        	}
        	}
        	catch (IOException o) {
        		o.printStackTrace();
        	}
        	
        	String[] userID = new String[3];
        	for (String user: userData.keySet()) {
        		if (userInput.equals(user) && passwordInput.equals(userData.get(user)[1])) {
        			userID[0] = user;
        			userID[1] = userData.get(user)[0];
        			userID[2] = userData.get(user)[1];
        		}
        	}
        	if (userID[0] != null) {
        		if (userID[1].equals("Owner")) {
            		SwingUtilities.invokeLater(() -> {
            			new OwnerFrame(userID[0]).setVisible(true);
                        dispose();
                    });
            	}
            	else if (userID[1].equals("Employee")) {
            		SwingUtilities.invokeLater(() -> {
            			new EmployeeFrame(userID[0]).setVisible(true);
                        dispose();
                    });
            	}
            	else if (userID[1].equals("Customer")) {
            		SwingUtilities.invokeLater(() -> {
            			new CustomerFrame(userID[0]).setVisible(true);
                        dispose();
                    });
            	}
        	}
        	else {
        		failMessage.setVisible(true);
        		passwordField.setText("");
        	}
        }
    }
    private class GuestLoginMouseListener extends MouseAdapter {
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		SwingUtilities.invokeLater(() -> {
    			new VisitorFrame().setVisible(true);
    			dispose();
    		});
        }
    }
}