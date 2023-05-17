import javax.swing.*;
import java.net.MalformedURLException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Login {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

class LoginFrame extends JFrame {
	private static final long serialVersionUID = -529063064709586618L;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel guestLogin;
    private JLabel failMessage;

    public LoginFrame() {
        setTitle("Computer Tuners");
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
        ImageIcon logoIcon = new ImageIcon(new ImageIcon("logo-icon.png").getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
        JLabel logoLabel = new JLabel(logoIcon);
        add(logoLabel, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        failMessage = new JLabel("Incorrect username or password.");
        failMessage.setForeground(Color.RED);
        failMessage.setVisible(false);
        add(failMessage,c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5, 5, 5, 5);
        usernameLabel = new JLabel("Username:");
        add(usernameLabel, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        usernameField = new JTextField(20);
        add(usernameField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_END;
        passwordLabel = new JLabel("Password:");
        add(passwordLabel, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        passwordField = new JPasswordField(20);
        passwordField.addKeyListener(new PasswordFieldKeyListener());
        add(passwordField, c);

        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_START;
        guestLogin = new JLabel("<html><u>Log in as Guest</u></html>");
        guestLogin.setForeground(Color.BLUE);
        guestLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        guestLogin.addMouseListener(new GuestLoginMouseListener());
        add(guestLogin, c);
        
        c.gridx = 1;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        add(loginButton, c);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	authenticateLogin();
        }
    }
    private class PasswordFieldKeyListener extends KeyAdapter {
    	@Override
    	public void keyPressed(KeyEvent e) {
    		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    			authenticateLogin();
    		}
    	}
    }
    private class GuestLoginMouseListener extends MouseAdapter {
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		remove(usernameField);
    		remove(passwordField);
    		remove(usernameLabel);
    		remove(passwordLabel);
    		remove(loginButton);
    		remove(guestLogin);
    		revalidate();
    		repaint();
			failMessage.setForeground(new Color(0,153,0));
			failMessage.setText("Logging in...");
			failMessage.setVisible(true);
			
    		SwingUtilities.invokeLater(() -> {
    			try {
                    new VisitorFrame().setVisible(true);
                } catch (MalformedURLException err) {
                    throw new RuntimeException(err);
                }
    			dispose();
    		});
        }
    }
    
    private void authenticateLogin() {
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
    			remove(usernameField);
        		remove(passwordField);
        		remove(usernameLabel);
        		remove(passwordLabel);
        		remove(loginButton);
        		remove(guestLogin);
        		revalidate();
        		repaint();
    			failMessage.setForeground(new Color(0,153,0));
    			failMessage.setText("Logging in...");
    			failMessage.setVisible(true);
    			
        		SwingUtilities.invokeLater(() -> {
        			try {
                        new OwnerFrame(userID[0]).setVisible(true);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    dispose();
                });
        	}
        	else if (userID[1].equals("Employee")) {
        		remove(usernameField);
        		remove(passwordField);
        		remove(usernameLabel);
        		remove(passwordLabel);
        		remove(loginButton);
        		remove(guestLogin);
        		revalidate();
        		repaint();
    			failMessage.setForeground(new Color(0,153,0));
    			failMessage.setText("Logging in...");
    			failMessage.setVisible(true);
    			
        		SwingUtilities.invokeLater(() -> {
        			try {
                        new EmployeeFrame(userID[0]).setVisible(true);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    dispose();
                });
        	}
        	else if (userID[1].equals("Customer")) {
        		remove(usernameField);
        		remove(passwordField);
        		remove(usernameLabel);
        		remove(passwordLabel);
        		remove(loginButton);
        		remove(guestLogin);
        		revalidate();
        		repaint();
    			failMessage.setForeground(new Color(0,153,0));
    			failMessage.setText("Logging in...");
    			failMessage.setVisible(true);
    			
        		SwingUtilities.invokeLater(() -> {
        			try {
                        new CustomerFrame(userID[0]).setVisible(true);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    dispose();
                });
        	}
        	else if (userID[1].equals("BannedCustomer")) {
        		remove(usernameField);
        		remove(passwordField);
        		remove(usernameLabel);
        		remove(passwordLabel);
        		remove(loginButton);
        		revalidate();
        		repaint();
        		failMessage.setText("<html>Due to multiple violations, your account has<br>been locked. For more information, please<br>see an employee.</html>");
        		failMessage.setVisible(true);
        	}
    	}
    	else {
    		failMessage.setText("Incorrect username or password.");
    		failMessage.setVisible(true);
    		passwordField.setText("");
    	}
    }
}