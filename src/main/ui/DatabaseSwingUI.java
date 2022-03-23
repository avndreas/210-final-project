package ui;

import model.Classification;
import model.Database;
import model.Entity;
import model.TextBlock;
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
    //private JPanel middlePanel;
    private JPanel rightPanel;


    private JSplitPane splitPane1;
    private JSplitPane splitPane2;

    //private JLabel leftText;
    private JTextArea leftTextArea;

    // middle scroll panel
    private JScrollPane middlePanelScrollPane;
    private JPanel buttonPanel;
    private GridLayout buttonLayout;

    // right scroll panel
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton saveEntityButton;
    private JTextArea entryTitle;
    private JButton classification;
    private JButton containmentStatus;
    private JButton addNewTextArea;
    private JScrollPane rightPanelScrollPane;
    private GridBagLayout rightGridBagLayout;
    private GridBagConstraints rightConstraints;

    //private LinkedHashMap<Integer, JButton> entityButtonMap;

    private GridLayout leftLayout;

    public DatabaseSwingUI() {
        super("SCP Database");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new BorderLayout());

        leftLayout = new GridLayout(0, 1);
        buttonLayout = new GridLayout(0, 1);

        leftPanel = new JPanel(leftLayout);
        //middlePanel = new JPanel(buttonLayout);
        buttonPanel = new JPanel(buttonLayout);

        middlePanelScrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //right panel
        rightGridBagLayout = new GridBagLayout();
        rightPanel = new JPanel(rightGridBagLayout);
        rightConstraints = new GridBagConstraints();
        rightPanelScrollPane = new JScrollPane(rightPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //entityCatalogueScrollPane = new JScrollPane(middlePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        //        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //Dimension minimumSize = new Dimension(100, 50);
        //leftPanel.setMinimumSize(minimumSize);
        //entityCatalogueScrollPane.setMinimumSize(minimumSize);

        runDatabase();
    }

    private void runDatabase() {
        database = new Database(SERIES);
        initializeListOfNames();

        //JButton btn = new JButton("Change");
        //btn.setActionCommand("myButton");
        //btn.addActionListener(this); // Sets "this" object as an action listener for btn
        // so that when the btn is clicked,
        // this.actionPerformed(ActionEvent e) will be called.
        // You could also set a different object, if you wanted
        // a different object to respond to the button click
        //pack();
        displayMenu();

    }

    private void displayMenu() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        int appWidth = (int)(width * X_SCALE);
        int appHeight = (int)(height * Y_SCALE);


        // middle panel
        //entityListPanel.add(entityCatalogueScrollPane);
        //middlePanel.add(testMid);
        initializeLeftPanel();

        // right panel
        //entityInfoPanel.add(entityCatalogueScrollPane);
        initializeRightPanel(true);
        rightConstraints = new GridBagConstraints();

        splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, middlePanelScrollPane);
        splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, rightPanelScrollPane);

        splitPane1.setDividerLocation((int)(appWidth / 5));
        splitPane2.setDividerLocation((int)(appWidth * 2 / 5 + (appWidth / 5)));

        this.add(splitPane2, BorderLayout.CENTER);


        this.setSize(new Dimension(appWidth, appHeight));



        this.setVisible(true);
        //entityListPanel.setVisible(true);
        //entityInfoPanel.setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initializeRightPanel(Boolean editable) {
        Entity defaultEntity = database.getSCP(1);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        addButton = new JButton("Add SCP Here");

        editButton = new JButton("Edit SCP");
        deleteButton = new JButton("Delete SCP");
        saveEntityButton = new JButton("Save Edited SCP");
        entryTitle = new JTextArea(defaultEntity.getLabel());
        entryTitle.setLineWrap(true);
        entryTitle.setWrapStyleWord(true);
        entryTitle.setEditable(editable);
        entryTitle.setBorder(null);
        entryTitle.setFont(new Font("Roboto", Font.BOLD, 30));
        classification = new JButton(defaultEntity.getClassification().name());
        if (defaultEntity.isContained()) {
            containmentStatus = new JButton("UNCONTAINED");
        } else {
            containmentStatus = new JButton("CONTAINED");
        }



        rightConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightConstraints.gridx = 0;
        rightConstraints.gridy = 0;
        rightPanel.add(addButton, rightConstraints);

        rightConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightConstraints.gridx = 1;
        rightPanel.add(editButton, rightConstraints);

        rightConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightConstraints.gridx = 3;
        rightPanel.add(deleteButton, rightConstraints);

        rightConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightConstraints.gridx = 4;
        rightPanel.add(saveEntityButton, rightConstraints);

        rightConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightConstraints.gridx = 0;
        rightPanel.add(addButton, rightConstraints);

        rightConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightConstraints.gridy = 1;
        rightConstraints.gridwidth = 5;
        rightConstraints.ipady = 40;
        rightPanel.add(entryTitle, rightConstraints);

        rightConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightConstraints.gridy = 2;
        rightConstraints.gridwidth = 2;
        rightConstraints.ipady = 0;
        rightPanel.add(classification, rightConstraints);

        rightConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightConstraints.gridx = 3;
        rightConstraints.gridy = 2;
        rightConstraints.gridwidth = 2;
        rightPanel.add(containmentStatus, rightConstraints);


        int initialGridY = 3;

        for (TextBlock t: defaultEntity.getEntityInfo()) {
            JTextArea title = new JTextArea(t.getTitle());
            JTextArea body = new JTextArea(t.getBody());
            title.setLineWrap(true);
            title.setWrapStyleWord(true);
            title.setEditable(editable);
            title.setBorder(null);
            body.setLineWrap(true);
            body.setWrapStyleWord(true);
            body.setEditable(editable);
            body.setBorder(null);

            rightConstraints.fill = GridBagConstraints.HORIZONTAL;
            rightConstraints.gridx = 0;
            rightConstraints.gridy = initialGridY;
            rightConstraints.gridwidth = 5;
            rightPanel.add(title, rightConstraints);

            rightConstraints.fill = GridBagConstraints.HORIZONTAL;
            rightConstraints.gridx = 0;
            rightConstraints.gridy = initialGridY + 1;
            rightConstraints.gridwidth = 5;
            rightPanel.add(body, rightConstraints);

            initialGridY = initialGridY + 2;
        }

        addNewTextArea = new JButton("Add New Text Area");

        if (editable) {
            rightConstraints.fill = GridBagConstraints.HORIZONTAL;
            rightConstraints.gridx = 0;
            rightConstraints.gridy = initialGridY + 2;
            rightConstraints.gridwidth = 5;
            rightPanel.add(addNewTextArea, rightConstraints);
        }


        //displayRightPanel(editable);
    }

    private void displayRightPanel(Boolean editable) {

    }

    private void initializeLeftPanel() {
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
        leftTextArea.setText("This area will be the info about the SCP Foundation.");
        //leftText.setFont(new Font("Serif", Font.BOLD, 18));
        leftPanel.add(leftTextArea);
        //leftPanel.add(testLeft);
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
            buttonPanel.add(entityButton);
        }

    }

}
