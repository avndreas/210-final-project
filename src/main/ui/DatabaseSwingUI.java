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

    private ArrayList<JButton> buttonListOfSCPs;

    private GridLayout leftLayout;

    public DatabaseSwingUI() {
        super("SCP Database");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13) );
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
        initializeListOfNames(database);


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
        JButton testLeft = new JButton("Left button");
        JButton testMid = new JButton("Middle button");
        JButton testRight = new JButton("Right button");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        int appWidth = (int)(width * X_SCALE);
        int appHeight = (int)(height * Y_SCALE);

        // left panel
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



        leftPanel.add(testLeft);

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

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        //if (e.getActionCommand().equals("myButton")) {
        //    label.setText(field.getText());
        //}
    }

    public static void main(String[] args) {
        new DatabaseSwingUI();
    }

    private void initializeListOfNames(Database database) {
        for (Entity e: database.getListOfSCPs()) {
            JButton ent = new JButton(e.getName());
            buttonListOfSCPs.add(ent);
        }

    }

}
