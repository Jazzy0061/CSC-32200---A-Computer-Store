import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomerFrame extends JFrame {
	private static final long serialVersionUID = 6026816662042005559L;
	
	private String name;

	private JPanel productPanel;

    private JSONArray pcData;

    private JSONArray memoryCardsData;

    private JSONArray cpusData;

    private JSONArray monitorsData;

    private JSONArray buildGuidesData;
    
    private JButton viewBalanceButton;


    public CustomerFrame(String name) throws MalformedURLException {
    	this.name=name;
        setTitle("Computer Tuners");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);
        initComponents();
        pack();
        setSize(960, 540);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() throws MalformedURLException {
        loadProductsData();
        loadMemoryCardsData();
        loadDesktopData();
        loadMonitors();
        loadBuildGuides();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Welcome, "+name), BorderLayout.WEST);
        viewBalanceButton = new JButton("View balance");
        topPanel.add(viewBalanceButton, BorderLayout.EAST);
        viewBalanceButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		SwingUtilities.invokeLater(() -> {
        			new MoneyDeposit().setVisible(true);
        		});
        	}
        });
        JPanel logoPanel = new JPanel();
        ImageIcon logoIcon = new ImageIcon("logo-icon.png"); // Replace "path_to_logo_image.png" with the actual path to your logo image
        logoIcon=new ImageIcon(logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);
        topPanel.add(logoPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        productPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        displayPCs();

        add(productPanel, BorderLayout.CENTER);

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

        add(productPanel, BorderLayout.EAST);


        JButton buildGuidesButton = new JButton("Build Guides");
        buildGuidesButton.addActionListener(e -> displayBuildGuides());
        add(buildGuidesButton, BorderLayout.SOUTH);
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
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("build_guides.json")); 
            JSONArray buildGuides = (JSONArray) obj;

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
                    URL url = null;
                    try {
                        url = new URL(imageUrl);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
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

            JFrame buildGuidesFrame = new JFrame();
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
    
    private void switchPanel(JPanel panel) {
        getContentPane().removeAll();
        add(panel);
        validate();
        repaint();
    }
}
