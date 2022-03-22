package ui;

import model.Database;
import model.Entity;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DatabaseSwingUI extends JFrame implements ActionListener {
    //private JLabel label;
    //private JTextField field;

    private static final double X_SCALE = 0.8;
    private static final double Y_SCALE = 0.8;

    private static final String JSON_STORE = "./data/database.json";
    private Database database;
    private static final int SERIES = 1;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JPanel leftPanel;
    private JPanel middlePanel;
    private JPanel rightPanel;
    private JScrollPane entityCatalogueScrollPane;

    private JSplitPane splitPane1;
    private JSplitPane splitPane2;

    private JLabel leftText;
    private JTextArea leftTextArea;

    private ArrayList<JButton> buttonListOfSCPs;
    private LinkedHashMap<Integer, JButton> entityButtonMap;

    private GridLayout leftLayout;

    public DatabaseSwingUI() {
        super("SCP Database");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new BorderLayout());

        leftPanel = new JPanel();
        middlePanel = new JPanel();
        rightPanel = new JPanel();


        buttonListOfSCPs = new ArrayList<>();

        //entityCatalogueScrollPane = new JScrollPane(middlePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        //        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);




        //Dimension minimumSize = new Dimension(100, 50);
        //leftPanel.setMinimumSize(minimumSize);
        //entityCatalogueScrollPane.setMinimumSize(minimumSize);

        runDatabase();
    }

    private void runDatabase() {
        //boolean keepRunning = true;
        //String command = null;

        database = new Database(SERIES);
        initializeListOfNames();


        //JButton btn = new JButton("Change");
        //btn.setActionCommand("myButton");
        //btn.addActionListener(this); // Sets "this" object as an action listener for btn

        // so that when the btn is clicked,
        // this.actionPerformed(ActionEvent e) will be called.
        // You could also set a different object, if you wanted
        // a different object to respond to the button click
        //label = new JLabel("flag");
        //field = new JTextField(5);
        //add(field);
        //add(btn);
        //add(label);
        //leftPanel.add(entityCatalogueScrollPane);
        //pack();




        /*
        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }

        }

         */

        displayMenu();

    }

    private void displayMenu() {



        JButton testMid = new JButton("Middle button");
        JButton testRight = new JButton("Right button");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        int appWidth = (int)(width * X_SCALE);
        int appHeight = (int)(height * Y_SCALE);

        initalizeLeftPanel();

        // middle panel
        //entityListPanel.add(entityCatalogueScrollPane);
        middlePanel.add(testMid);

        // right panel
        //entityInfoPanel.add(entityCatalogueScrollPane);
        rightPanel.add(testRight);

        splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, middlePanel);
        splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, rightPanel);

        splitPane1.setDividerLocation((int)(appWidth / 3));
        splitPane2.setDividerLocation((int)(appWidth * 2 / 3));

        this.add(splitPane2, BorderLayout.CENTER);


        this.setSize(new Dimension(appWidth, appHeight));



        this.setVisible(true);
        //entityListPanel.setVisible(true);
        //entityInfoPanel.setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initalizeLeftPanel() {
        JButton testLeft = new JButton("Left button");

        BufferedImage siteLogo;
        try {
            siteLogo = ImageIO.read(new File("data/images/SCP_Logo.png"));
            Image resizedLogo = siteLogo.getScaledInstance((int)(siteLogo.getWidth() * 0.5),
                    (int)(siteLogo.getHeight() * 0.5), Image.SCALE_DEFAULT);
            JLabel banner = new JLabel(new ImageIcon(resizedLogo));
            leftPanel.add(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }

        leftTextArea = new JTextArea();
        leftTextArea.setLineWrap(true);
        leftTextArea.setWrapStyleWord(true);
        leftTextArea.setEditable(false);
        leftTextArea.setBorder(null);
        leftTextArea.setText("What the fuck did you just fucking say about me, you little bitch? I'll have you know I graduated top of my class in the Navy Seals, and I've been involved in numerous secret raids on Al-Quaeda, and I have over 300 confirmed kills. I am trained in gorilla warfare and I'm the top sniper in the entire US armed forces. You are nothing to me but just another target. I will wipe you the fuck out with precision the likes of which has never been seen before on this Earth, mark my fucking words. You think you can get away with saying that shit to me over the Internet? Think again, fucker. As we speak I am contacting my secret network of spies across the USA and your IP is being traced right now so you better prepare for the storm, maggot. The storm that wipes out the pathetic little thing you call your life. You're fucking dead, kid. I can be anywhere, anytime, and I can kill you in over seven hundred ways, and that's just with my bare hands. Not only am I extensively trained in unarmed combat, but I have access to the entire arsenal of the United States Marine Corps and I will use it to its full extent to wipe your miserable ass off the face of the continent, you little shit. If only you could have known what unholy retribution your little \\\"clever\\\" comment was about to bring down upon you, maybe you would have held your fucking tongue. But you couldn't, you didn't, and now you're paying the price, you goddamn idiot. I will shit fury all over you and you will drown in it. You're fucking dead, kiddo.");

        //leftText.setFont(new Font("Serif", Font.BOLD, 18));
        leftText = new JLabel("<html>" + "What the fuck did you just fucking say about me, you little bitch? I'll have you know I graduated top of my class in the Navy Seals, and I've been involved in numerous secret raids on Al-Quaeda, and I have over 300 confirmed kills. I am trained in gorilla warfare and I'm the top sniper in the entire US armed forces. You are nothing to me but just another target. I will wipe you the fuck out with precision the likes of which has never been seen before on this Earth, mark my fucking words. You think you can get away with saying that shit to me over the Internet? Think again, fucker. As we speak I am contacting my secret network of spies across the USA and your IP is being traced right now so you better prepare for the storm, maggot. The storm that wipes out the pathetic little thing you call your life. You're fucking dead, kid. I can be anywhere, anytime, and I can kill you in over seven hundred ways, and that's just with my bare hands. Not only am I extensively trained in unarmed combat, but I have access to the entire arsenal of the United States Marine Corps and I will use it to its full extent to wipe your miserable ass off the face of the continent, you little shit. If only you could have known what unholy retribution your little \"clever\" comment was about to bring down upon you, maybe you would have held your fucking tongue. But you couldn't, you didn't, and now you're paying the price, you goddamn idiot. I will shit fury all over you and you will drown in it. You're fucking dead, kiddo." + "</html>");

        leftPanel.add(leftTextArea);
        leftPanel.add(testLeft);
    }

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        //if (e.getActionCommand().equals("myButton")) {
        //    label.setText(field.getText());
        //}
    }

    public static void main(String[] args) {
        new DatabaseSwingUI();
    }

    private void initializeListOfNames() {
        for (Entity e: database.getListOfSCPs()) {
            JButton entityButton = new JButton(e.getLabel());
            entityButton.setActionCommand(Integer.toString(e.getItemNumber()));
            //entityButtonMap.put(e.getItemNumber(), entityButton);
            buttonListOfSCPs.add(entityButton);
        }

    }

}
