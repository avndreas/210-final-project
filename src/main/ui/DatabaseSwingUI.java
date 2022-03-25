package ui;

import jdk.nashorn.internal.scripts.JO;
import model.Classification;
import model.Database;
import model.Entity;
import model.TextBlock;
import model.exceptions.*;
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
import java.io.FileNotFoundException;
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
    private JTextArea entryTitle;
    private JButton classification;
    private JButton containmentStatus;
    private JScrollPane rightPanelScrollPane;
    private GridBagLayout rightGridBagLayout;
    private GridBagConstraints rightConstraints;
    private GridLayout leftLayout;
    private Entity currentEntityOnRight;
    private BorderLayout borderLayout = new BorderLayout();
    private JPanel borderPanel = new JPanel(borderLayout);

    private int appWidth;
    private int appHeight;
    private Dimension screenSize;

    private final String[] containChoices = {"Contained", "Uncontained"};
    private final Classification[] classChoices = {Classification.SAFE, Classification.EUCLID, Classification.KETER,
            Classification.THAUMIEL, Classification.APOLLYON};

    public DatabaseSwingUI() {
        super("SCP Database");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new BorderLayout());

        leftLayout = new GridLayout(0, 1);
        buttonLayout = new GridLayout(0, 1);

        leftPanel = new JPanel(leftLayout);
        buttonPanel = new JPanel(buttonLayout);

        middlePanelScrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        rightGridBagLayout = new GridBagLayout();
        rightPanel = new JPanel(rightGridBagLayout);
        rightConstraints = new GridBagConstraints();
        rightPanelScrollPane = new JScrollPane(rightPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //borderPanel = new JPanel();
        //borderPanel.add(rightPanelScrollPane);

        rightConstraints = new GridBagConstraints();

        splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, middlePanelScrollPane);
        splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, rightPanelScrollPane);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runDatabase();
    }

    private void runDatabase() {
        database = new Database(SERIES);
        initializeListOfNames();
        //pack();
        displayMenu();
    }

    private void displayMenu() {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        appWidth = (int)(width * X_SCALE);
        appHeight = (int)(height * Y_SCALE);

        initializeLeftPanel();
        initializeRightPanel(database.getSCP(1));

        // fix this if i actually have time
        //leftPanel.setMinimumSize(new Dimension(appWidth / 5, appHeight));
        middlePanelScrollPane.setMinimumSize(new Dimension(appWidth * 1 / 5, appHeight));
        middlePanelScrollPane.setMaximumSize(new Dimension(appWidth * 1 / 5, appHeight));
        rightPanelScrollPane.setMinimumSize(new Dimension(appWidth * 1 / 5, appHeight));

        splitPane1.setDividerLocation((int)(appWidth / 5));
        splitPane2.setDividerLocation((int)(appWidth * 3 / 5));

        this.add(splitPane2, BorderLayout.CENTER);
        this.setSize(new Dimension(appWidth, appHeight));
        this.setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initializeRightPanel(Entity entity) {
        currentEntityOnRight = entity;
        rightPanel.removeAll();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        addButton = new JButton("Add SCP Here");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);

        editButton = new JButton("Edit SCP");
        editButton.setActionCommand("edit");
        editButton.addActionListener(this);

        deleteButton = new JButton("Delete SCP");
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);

        //saveEntityButton = new JButton("Save Edited SCP");
        entryTitle = new JTextArea(entity.getLabel());
        entryTitle.setLineWrap(true);
        entryTitle.setWrapStyleWord(true);
        entryTitle.setEditable(false);
        entryTitle.setBorder(null);
        entryTitle.setFont(new Font("Roboto", Font.BOLD, 30));
        classification = new JButton(entity.getClassification().name());
        if (entity.isContained()) {
            containmentStatus = new JButton("UNCONTAINED");
        } else {
            containmentStatus = new JButton("CONTAINED");
        }

        displayInGridBag(0, 0, 1, 0, 0, addButton);
        displayInGridBag(1, 0, 1, 0, 0, editButton);
        displayInGridBag(3, 0, 1, 0, 0, deleteButton);
        displayInGridBag(0, 1, 5, 0, 40, entryTitle);
        displayInGridBag(0, 2, 2, 0, 0, classification);
        displayInGridBag(3, 2, 2, 0, 0, containmentStatus);

        int initialGridY = 3;

        for (TextBlock t: entity.getEntityInfo()) {
            JTextArea title = new JTextArea(t.getTitle());
            JTextArea body = new JTextArea(t.getBody());
            title.setFont(new Font("Roboto", Font.BOLD, 15));
            title.setLineWrap(true);
            title.setWrapStyleWord(true);
            title.setEditable(false);
            title.setBorder(null);
            body.setLineWrap(true);
            body.setWrapStyleWord(true);
            body.setEditable(false);
            body.setBorder(null);


            displayInGridBag(0, initialGridY, 5, 0, 0, title);
            displayInGridBag(0, initialGridY + 1, 5, 0, 0, body);

            initialGridY = initialGridY + 2;
        }
        revalidate();
        repaint();


        //displayRightPanel(editable);
    }

    private void displayInGridBag(int gridX, int gridY, int gridWidth, int ipadX, int ipadY, Component comp) {
        rightConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightConstraints.gridx = gridX;
        rightConstraints.gridy = gridY;
        rightConstraints.gridwidth = gridWidth;
        rightConstraints.ipadx = ipadX;
        rightConstraints.ipady = ipadY;
        rightPanel.add(comp, rightConstraints);
    }

    private void initializeLeftPanel() {
        leftPanel.removeAll();
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


        JButton saveButton = new JButton("Save Database");
        JButton loadButton = new JButton("Load Database");

        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);

        leftPanel.add(saveButton);
        leftPanel.add(loadButton);
        revalidate();
        repaint();
    }

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        int entityNum;
        Entity entityToDisplay;
        String actionCommand = e.getActionCommand();
        try {
            entityNum = Integer.parseInt(actionCommand);
            System.out.println(entityNum);
            entityToDisplay = database.getSCP(entityNum);
            initializeRightPanel(entityToDisplay);
        } catch (NumberFormatException nfe) {
            switch (actionCommand) {
                case "add":
                    try {
                        addEntity(currentEntityOnRight);
                    } catch (EntityAlreadyExistsException eae) {
                        System.out.println("problem - Entity already exists");
                    }
                    break;
                case "edit":
                    if (currentEntityOnRight.getClassification().name().equals(Classification.UNCLASSIFIED.name())) {
                        System.out.println("problem - Entity does not exist");
                    } else {
                        editEntry(currentEntityOnRight);
                    }
                    break;
                case "delete":
                    deleteSCP();
                    break;
                case "addtextblock":
                    addTextBlock();
                    break;
                case "removetextblock":
                    removeTextBlock();
                    break;
                case "save":
                    saveJson();
                    break;
                case "load":
                    readJson();
                    break;
                default:
                    System.out.println("default");
                    break;
            }
        }
    }

    private void deleteSCP() {
        database.deleteSCP(currentEntityOnRight.getItemNumber());
        initializeListOfNames();
        displayMenu();
    }

    // REFERENCE: CPSC 210 example files
    private void readJson() {
        try {
            database = jsonReader.read();
            initializeListOfNames();
            displayMenu();
        } catch (IOException e) {
            System.out.println("Error reading the file. Current save has not been altered.");
        }

    }

    // REFERENCE: CPSC 210 example files
    private void saveJson() {
        try {
            jsonWriter.open();
            jsonWriter.write(database);
            jsonWriter.close();
            System.out.println("Saved the database to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void addTextBlock() {
        JTextArea title = new JTextArea(1, 30);
        JTextArea body = new JTextArea(10, 30);

        JScrollPane titleScroll = new JScrollPane(title);
        JScrollPane bodyScroll = new JScrollPane(body);

        title.setLineWrap(true);
        body.setLineWrap(true);
        JPanel addTextPanel = new JPanel();
        BoxLayout addTextLayout = new BoxLayout(addTextPanel, BoxLayout.Y_AXIS);
        addTextPanel.setLayout(addTextLayout);
        addTextPanel.add(new JLabel("Title"));
        addTextPanel.add(titleScroll);
        addTextPanel.add(new JLabel("Body"));
        addTextPanel.add(bodyScroll);
        addTextPanel.setSize((int)(screenSize.getWidth() * 0.5), (int)(screenSize.getHeight() * 0.5));

        int result = JOptionPane.showConfirmDialog(null, addTextPanel, "Create Text Block",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            currentEntityOnRight.addEntry(title.getText(), body.getText());
            initializeRightPanel(currentEntityOnRight);
        }
    }

    private void removeTextBlock() {
        JPanel removeTextPanel = new JPanel();
        BoxLayout addTextLayout = new BoxLayout(removeTextPanel, BoxLayout.Y_AXIS);
        removeTextPanel.setLayout(addTextLayout);
        removeTextPanel.setSize((int)(screenSize.getWidth() * 0.5), (int)(screenSize.getHeight() * 0.5));

        ArrayList<String> titleList = new ArrayList<>();

        for (TextBlock t: currentEntityOnRight.getEntityInfo()) {
            titleList.add(t.getTitle());
        }

        JComboBox titleDropDown = new JComboBox(titleList.toArray());

        removeTextPanel.add(titleDropDown);

        int result = JOptionPane.showConfirmDialog(null, removeTextPanel, "Remove Text Block",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            currentEntityOnRight.deleteEntry(titleDropDown.getSelectedIndex());
            initializeRightPanel(currentEntityOnRight);
        }
    }

    private void editEntry(Entity e) {
        System.out.println("got to editEntry");
        JTextField nameInput = new JTextField(e.getName());
        JComboBox<Classification> classMenu = new JComboBox<>(classChoices);
        classMenu.setSelectedItem(e.getClassification());
        JComboBox<String> containMenu = new JComboBox<>(containChoices);

        if (!e.isContained()) {
            containMenu.setSelectedItem(containChoices[0]);
        } else {
            containMenu.setSelectedItem(containChoices[1]);
        }

        GridLayout addPanelLayout = new GridLayout(0,1);
        JPanel editPanel = new JPanel(addPanelLayout);
        editPanel.add(new JLabel("SCP-" + Entity.formatNumLength(e.getItemNumber(), Database.MIN_DIGITS)
                + ": Name"));
        editPanel.add(nameInput);
        editPanel.add(Box.createHorizontalStrut(15));
        editPanel.add(classMenu);
        editPanel.add(Box.createHorizontalStrut(15));
        editPanel.add(containMenu);

        JButton addTextBlock = new JButton("Add New Text Block");
        addTextBlock.setActionCommand("addtextblock");
        addTextBlock.addActionListener(this);

        JButton removeTextBlock = new JButton("Remove a Text Block");
        removeTextBlock.setActionCommand("removetextblock");
        removeTextBlock.addActionListener(this);

        editPanel.add(addTextBlock);
        editPanel.add(removeTextBlock);

        int result = JOptionPane.showConfirmDialog(null, editPanel, "Edit SCP",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            boolean contained = !containMenu.getSelectedItem().equals(containChoices[0]);
            e.setName(nameInput.getText());
            e.setObjectClass((Classification)classMenu.getSelectedItem());
            e.setContained(contained);
            initializeRightPanel(database.getSCP(e.getItemNumber()));
        }
        initializeListOfNames();
    }


    private void addEntity(Entity e) throws EntityAlreadyExistsException {
        if (e.getClassification().name().equals(Classification.UNCLASSIFIED.name())) {
            System.out.println("adds new scp");

            JTextField nameInput = new JTextField(10);
            JComboBox<Classification> classMenu = new JComboBox<>(classChoices);
            JComboBox<String> containMenu = new JComboBox<>(containChoices);
            containMenu.setSelectedItem(containChoices[1]);

            GridLayout addPanelLayout = new GridLayout(0,1);
            JPanel addPanel = new JPanel(addPanelLayout);
            addPanel.add(new JLabel("SCP-" + Entity.formatNumLength(e.getItemNumber(), Database.MIN_DIGITS)));
            addPanel.add(nameInput);
            addPanel.add(Box.createHorizontalStrut(15));
            addPanel.add(classMenu);
            addPanel.add(Box.createHorizontalStrut(15));
            addPanel.add(containMenu);

            int result = JOptionPane.showConfirmDialog(null, addPanel, "Create SCP",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                boolean contained = !containMenu.getSelectedItem().equals(containChoices[0]);
                e.setName(nameInput.getText());
                e.setObjectClass((Classification)classMenu.getSelectedItem());
                e.setContained(contained);
                initializeRightPanel(e);
            }
            initializeListOfNames();

        } else {
            throw new EntityAlreadyExistsException("This SCP already has an entry!");
        }
    }

    public static void main(String[] args) {
        new DatabaseSwingUI();
    }

    private void initializeListOfNames() {
        buttonPanel.removeAll();
        for (Entity e: database.getListOfSCPs()) {
            JButton entityButton = new JButton(e.getLabel());
            entityButton.setActionCommand(Integer.toString(e.getItemNumber()));
            entityButton.addActionListener(this);
            buttonPanel.add(entityButton);
        }
        revalidate();
        repaint();
    }

}
