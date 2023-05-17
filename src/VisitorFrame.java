import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VisitorFrame extends JFrame {
	private static final long serialVersionUID = 6026816662042005559L;
	
	private JPanel mainPanel;

	private JPanel productPanel;

    private JSONArray pcData;

    private JSONArray memoryCardsData;

    private JSONArray cpusData;

    private JSONArray monitorsData;

    private JSONArray buildGuidesData;
    
    private JPanel applyPanel;
    
    private JPanel acceptedPanel;
    
    private JButton signUpButton;
    
    private JFrame buildGuidesFrame;


    public VisitorFrame() throws MalformedURLException {
        setTitle("Computer Tuners");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);
        initComponents();
        pack();
        setSize(960, 540);
        setLocationRelativeTo(null);
        setVisible(true);
        applyPanel = createApplyPanel();
        acceptedPanel = createAcceptedAppPanel();
    }

    private void initComponents() throws MalformedURLException {
        loadProductsData();
        loadMemoryCardsData();
        loadDesktopData();
        loadMonitors();
        loadBuildGuides();
        
        mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Welcome, Guest"), BorderLayout.WEST);
        signUpButton = new JButton("Sign up");
        topPanel.add(signUpButton, BorderLayout.EAST);
        signUpButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		switchPanel(applyPanel);
        	}
        });
        JPanel logoPanel = new JPanel();
        ImageIcon logoIcon = new ImageIcon("logo-icon.png"); // Replace "path_to_logo_image.png" with the actual path to your logo image
        logoIcon=new ImageIcon(logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);
        topPanel.add(logoPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        productPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        displayPCs();

        mainPanel.add(productPanel, BorderLayout.CENTER);

        JPanel productPanel = new JPanel(new GridLayout(4, 1, 5, 5));

        JButton memoryCardButton = new JButton("Memory Cards");
        memoryCardButton.addActionListener(e -> displayMemoryCards());
        JButton pcButton = new JButton("PC");
        pcButton.addActionListener(e -> {
            try {
                displayPCs();
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton cpuButton = new JButton("CPU");
        cpuButton.addActionListener(e -> displayCPUs());
        JButton monitorButton = new JButton("Monitors");
        monitorButton.addActionListener(e -> displayMonitors());

        productPanel.add(memoryCardButton);
        productPanel.add(pcButton);
        productPanel.add(cpuButton);
        productPanel.add(monitorButton);

        mainPanel.add(productPanel, BorderLayout.EAST);


        JButton buildGuidesButton = new JButton("Build Guides");
        buildGuidesButton.addActionListener(e -> displayBuildGuides());
        mainPanel.add(buildGuidesButton, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    private void loadProductsData() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("pc_products.json"));
            pcData = (JSONArray) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMemoryCardsData() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("memory_cards.json"));
            memoryCardsData = (JSONArray) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDesktopData() {
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("desktop.json"));
            cpusData = (JSONArray) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMonitors() {
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("monitors.json"));
            monitorsData = (JSONArray) obj;
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBuildGuides() {
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("build_guides.json"));
            buildGuidesData = (JSONArray) obj;
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayPCs() throws MalformedURLException {
        productPanel.removeAll();

        for (Object pcDatum : pcData) {
            JSONObject product = (JSONObject) pcDatum;

            String name = (String) product.get("name");
            double price = (double) product.get("price");
            String imageUrl = (String) product.get("image");
            URL url = new URL(imageUrl);
            dataFeed(name, price, url);
        }

        revalidate();
        repaint();
    }

    private void dataFeed(String name, double price, URL url) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert image != null;
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        JLabel priceLabel = new JLabel("$" + price, SwingConstants.CENTER);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(imageIcon);

        JPanel productItemPanel = new JPanel(new BorderLayout());
        productItemPanel.setBackground(Color.WHITE);
        productItemPanel.add(nameLabel, BorderLayout.NORTH);
        productItemPanel.add(imageLabel, BorderLayout.CENTER);
        productItemPanel.add(priceLabel, BorderLayout.SOUTH);

        productPanel.add(productItemPanel);
    }

    private void displayMemoryCards() {
        feedToArray(memoryCardsData);
    }

    private void displayCPUs() {
        feedToArray(cpusData);
    }

    private void displayMonitors() {
        feedToArray(monitorsData);
    }

    private void feedToArray(JSONArray monitorsData) {
        productPanel.removeAll();

        for (Object monitorsDatum : monitorsData) {
            JSONObject product = (JSONObject) monitorsDatum;

            String name = (String) product.get("name");
            double price = (double) product.get("price");
            String imageUrl = (String) product.get("image");
            URL url = null;
            try {
                url = new URL(imageUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            dataFeed(name, price, url);
        }

        revalidate();
        repaint();
    }
              
            private void displayBuildGuides() {
    try {
        productPanel.removeAll();
        JSONArray buildGuides = buildGuidesData; 

        JPanel buildGuidesPanel = new JPanel(new GridLayout(buildGuides.size(), 1, 10, 10));

        for (int i = 0; i < buildGuides.size(); i++) {
            JSONObject buildGuide = (JSONObject) buildGuides.get(i);
            String category = (String) buildGuide.get("category");

            JPanel guidePanel = new JPanel(new BorderLayout());

            JLabel categoryLabel = new JLabel(category + " Build Guide", SwingConstants.CENTER);
            guidePanel.add(categoryLabel, BorderLayout.NORTH);

            JSONArray products = (JSONArray) buildGuide.get("products");

            for (int j = 0; j < products.size(); j++) {
                JSONObject product = (JSONObject) products.get(j);
                String name = (String) product.get("name");
                double price = (double) product.get("price");
                String imageUrl = (String) product.get("image");
                URL url;
                try {
                    url = new URL(imageUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    continue; // Skip this product if the URL is malformed
                }
                BufferedImage image = null;
                try {
                    image = ImageIO.read(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert image != null;
                ImageIcon imageIcon = new ImageIcon(image);
                imageIcon.setImage(imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                JLabel nameLabel = new JLabel(name);
                JLabel priceLabel = new JLabel("$" + price);
                JLabel imageLabel = new JLabel(imageIcon);

                JPanel productPanel = new JPanel(new BorderLayout());
                productPanel.add(nameLabel, BorderLayout.NORTH);
                productPanel.add(imageLabel, BorderLayout.CENTER);
                productPanel.add(priceLabel, BorderLayout.SOUTH);

                guidePanel.add(productPanel, BorderLayout.CENTER);
            }

            buildGuidesPanel.add(guidePanel);
        }

            // Display the build guides in a scrollable pane
            JScrollPane scrollPane = new JScrollPane(buildGuidesPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            buildGuidesFrame = new JFrame(); 
            buildGuidesFrame.setTitle("Build Guides");
            buildGuidesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            buildGuidesFrame.getContentPane().add(scrollPane);
            buildGuidesFrame.pack();
            buildGuidesFrame.setSize(960, 540);
            buildGuidesFrame.setLocationRelativeTo(this);
            buildGuidesFrame.setVisible(true);

            revalidate();
            repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JPanel createApplyPanel() {
    	JPanel panel = new JPanel(new BorderLayout());
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("What's your first name?"));
        JTextField firstNameField = new JTextField();
        firstNameField.setMaximumSize(new Dimension(300, firstNameField.getPreferredSize().height));
        panel.add(firstNameField);

        panel.add(new JLabel("What's your last name?"));
        JTextField lastNameField = new JTextField();
        lastNameField.setMaximumSize(new Dimension(300, lastNameField.getPreferredSize().height));
        panel.add(lastNameField);

        panel.add(new JLabel("What's your email address?"));
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(300, emailField.getPreferredSize().height));
        panel.add(emailField);
        
        JLabel errorMessage = new JLabel("Please fill out all fields");
        errorMessage.setForeground(Color.RED);
        errorMessage.setVisible(false);
        panel.add(errorMessage);
        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || emailField.getText().isEmpty()) {
                    errorMessage.setVisible(true);
                }
        		else {
        			BufferedWriter bw = null;

        	        try {
        	        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        	        	LocalDateTime now = LocalDateTime.now();
        	        	String timestamp = dtf.format(now);
        	        	
        	            bw = new BufferedWriter(new FileWriter("pending_applications.csv", true));
        	            bw.write(timestamp + "," + firstNameField.getText() + "," + lastNameField.getText() + "," + emailField.getText());
        	            bw.newLine();
        	            firstNameField.setText("");
                    	lastNameField.setText("");
                    	emailField.setText("");
                        switchPanel(acceptedPanel);
        	        } catch (IOException ioe) {
        	            ioe.printStackTrace();
        	        } finally {
        	            try {
        	                if (bw != null)
        	                    bw.close();
        	            } catch (Exception ex) {
        	                System.out.println(ex);
        	            }
        	        }
        		}
        	}
        });
        panel.add(submitButton);
        
        JButton returnButton = new JButton("Return to the main page");
        returnButton.setSize(getPreferredSize().width, getPreferredSize().height);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	firstNameField.setText("");
            	lastNameField.setText("");
            	emailField.setText("");
                switchPanel(mainPanel);
            }
        });
        panel.add(returnButton);
        
        return panel;
    }
    
    private JPanel createAcceptedAppPanel() {
    	JPanel panel = new JPanel(new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
    	
    	gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
    	
    	JLabel acceptLabel = new JLabel("Your application has been successfully submitted."	);
        panel.add(acceptLabel, gbc);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(mainPanel);
            }
        });
        okButton.setSize(getPreferredSize().width, getPreferredSize().height);
        panel.add(okButton, gbc);
        
        return panel;
    }
    
    private void switchPanel(JPanel panel) {
        getContentPane().removeAll();
        add(panel);
        validate();
        repaint();
    }
}
