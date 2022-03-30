package ui;

import jdk.nashorn.internal.scripts.JO;
import model.Classification;
import model.Database;
import model.Entity;
import model.TextBlock;
import model.exceptions.*;
import model.observer.EventLog;
import model.observer.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;

// Swing GUI for the SCP Database
public class DatabaseSwingUI extends JFrame implements ActionListener {
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
            Classification.THAUMIEL, Classification.NEUTRALIZED, Classification.APOLLYON, Classification.ARCHON};

    private String introText = "The SCP foundation is one of the largest community projects on the internet. Many "
            + "people work together to create an extremely entertaining world of horror. \n"
            + "An SCP is an entity, it could be an animal, humanoid, or object, which possesses anomalous "
            + "properties. An entry with no data appears as [ACCESS DENIED]. \n"
            + "SCPs come in seven different classifications, each pertaining to a different complexity of"
            + " possibility of containment, as well as danger level. \n"
            + "For more info, visit https://scp-wiki.wikidot.com/";

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: Initializes the UI and relevant layouts, panels, constraints, and JSON objects.
    public DatabaseSwingUI() {
        super("SCP Database");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        closeOp();

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

        rightConstraints = new GridBagConstraints();

        splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, middlePanelScrollPane);
        splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, rightPanelScrollPane);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runDatabase();
    }

    private void closeOp() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog log = EventLog.getInstance();
                for (Event ev : log) {
                    System.out.println(ev.toString() + "\n\n");
                }
                System.exit(0);
            }
        });
    }


    // REQUIRES:
    // MODIFIES: this, database
    // EFFECTS: Initializes the new database, calls other initialization methods.
    private void runDatabase() {
        database = new Database(SERIES);
        initializeListOfNames();
        displayMenu();
    }

    // REQUIRES:
    // MODIFIES: this, main panel
    // EFFECTS: Displays the main window, sets up panes and dividers, calls to initialize the panels
    private void displayMenu() {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        appWidth = (int)(width * X_SCALE);
        appHeight = (int)(height * Y_SCALE);

        initializeLeftPanel();
        initializeRightPanel(database.getSCP(1));

        middlePanelScrollPane.setMinimumSize(new Dimension(appWidth / 5, appHeight));
        middlePanelScrollPane.setMaximumSize(new Dimension(appWidth / 5, appHeight));
        rightPanelScrollPane.setMinimumSize(new Dimension(appWidth / 5, appHeight));

        splitPane1.setDividerLocation((int)(appWidth / 5));
        splitPane2.setDividerLocation((int)(appWidth * 3 / 5));

        this.add(splitPane2, BorderLayout.CENTER);
        this.setSize(new Dimension(appWidth, appHeight));
        this.setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // REQUIRES:
    // MODIFIES: this, rightPanel, currentEntityOnRight
    // EFFECTS: Initializes and places everything on the right panel
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

        setUpEntryTitle(entity);

        classification = new JButton(entity.getClassification().name());

        containmentStatus = entity.isContained() ? new JButton("UNCONTAINED") : new JButton("CONTAINED");

        addElementsToGridBag();
        loadTextBlocks(entity, 3);
        revalidate();
        repaint();
    }

    // REQUIRES:
    // MODIFIES: this, rightConstraints
    // EFFECTS: Calls displayGridInBag for every element that needs to be added
    private void addElementsToGridBag() {
        displayInGridBag(0, 0, 1, 0, 0, addButton);
        displayInGridBag(1, 0, 1, 0, 0, editButton);
        displayInGridBag(3, 0, 1, 0, 0, deleteButton);
        displayInGridBag(0, 1, 5, 0, 40, entryTitle);
        displayInGridBag(0, 2, 2, 0, 0, classification);
        displayInGridBag(3, 2, 2, 0, 0, containmentStatus);

    }

    // REQUIRES:
    // MODIFIES: this, entryTitle
    // EFFECTS: Sets up the title of an entry in properly formatted text
    private void setUpEntryTitle(Entity entity) {
        entryTitle = new JTextArea(entity.getLabel());
        entryTitle.setLineWrap(true);
        entryTitle.setWrapStyleWord(true);
        entryTitle.setEditable(false);
        entryTitle.setBorder(null);
        entryTitle.setFont(new Font("Roboto", Font.BOLD, 30));
    }

    private void loadTextBlocks(Entity entity, int initialGridY) {
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
    }

    // REQUIRES: gridX, gridY, gridWidth, ipadX, ipadY >= 0
    // MODIFIES: this, rightConstraints
    // EFFECTS: Given necessary parameters, adds a component to rightConstraints at a position with padding and size
    private void displayInGridBag(int gridX, int gridY, int gridWidth, int ipadX, int ipadY, Component comp) {
        rightConstraints.fill = GridBagConstraints.HORIZONTAL;
        rightConstraints.gridx = gridX;
        rightConstraints.gridy = gridY;
        rightConstraints.gridwidth = gridWidth;
        rightConstraints.ipadx = ipadX;
        rightConstraints.ipady = ipadY;
        rightPanel.add(comp, rightConstraints);
    }

    // REQUIRES:
    // MODIFIES: this, leftPanel
    // EFFECTS: Initializes and places everything on the left panel.
    private void initializeLeftPanel() {
        leftPanel.removeAll();

        loadSiteImage();

        leftTextArea = new JTextArea();
        leftTextArea.setLineWrap(true);
        leftTextArea.setWrapStyleWord(true);
        leftTextArea.setEditable(false);
        leftTextArea.setBorder(null);
        leftTextArea.setText(introText);
        leftPanel.add(leftTextArea);

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

    // REQUIRES:
    // MODIFIES: leftPanel
    // EFFECTS: Loads the site logo and places it in the left panel
    private void loadSiteImage() {
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
    }

    // REQUIRES:
    // MODIFIES: this, rightPanel
    // EFFECTS: Processes button clicks
    public void actionPerformed(ActionEvent e) {
        int entityNum;
        Entity entityToDisplay;
        String actionCommand = e.getActionCommand();
        try {
            entityNum = Integer.parseInt(actionCommand);
            entityToDisplay = database.getSCP(entityNum);
            initializeRightPanel(entityToDisplay);
        } catch (NumberFormatException nfe) {
            nonIndexAction(actionCommand);
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: Processes which button was clicked, and calls appropriate action method
    private void nonIndexAction(String actionCommand) {
        switch (actionCommand) {
            case "add":
                addSCP();
                break;
            case "edit":
                editSCP();
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
        }
    }

    // REQUIRES:
    // MODIFIES: database
    // EFFECTS: Adds the current SCP on the right panel to the database
    private void addSCP() {
        try {
            addEntity(currentEntityOnRight);
        } catch (EntityAlreadyExistsException eae) {
            System.out.println("problem - Entity already exists");
        }
    }

    // REQUIRES:
    // MODIFIES: currentEntityOnRight
    // EFFECTS: Checks if there is an existing SCP in the focused slot, and if so then calls the editEntry method
    private void editSCP() {
        if (currentEntityOnRight.getClassification().name().equals(Classification.UNCLASSIFIED.name())) {
            System.out.println("problem - Entity does not exist");
        } else {
            editEntry(currentEntityOnRight);
        }
    }

    // REQUIRES:
    // MODIFIES: this, database
    // EFFECTS: Deletes the focused SCP from the database, then refreshes the UI.
    private void deleteSCP() {
        database.deleteSCP(currentEntityOnRight.getItemNumber());
        initializeListOfNames();
        displayMenu();
    }

    // REFERENCE: CPSC 210 example files
    // REQUIRES:
    // MODIFIES: this, database
    // EFFECTS: Reads the json file and updates the database and UI
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
    // REQUIRES:
    // MODIFIES: jsonWriter
    // EFFECTS: Writes all current database data to the file at JSON_STORE as .json
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

    // REQUIRES:
    // MODIFIES: this, currentEntityOnRight
    // EFFECTS: Creates the popup that lets the user input a new TextBlock, adds it to the entity then refreshes the UI
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

    // REQUIRES:
    // MODIFIES: this, currentEntityOnRight
    // EFFECTS: Removes a specified TextBlock from currentEntityOnRight
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

    // REQUIRES: e is not a blank entity
    // MODIFIES: this, e
    // EFFECTS: Edits the given entity at the user's specification, allows them to add TextBlocks
    @SuppressWarnings("methodlength")
    // I honestly don't know how to shorten this any more than it already is I really tried
    private void editEntry(Entity e) {
        JTextField nameInput = new JTextField(e.getName());
        JComboBox<Classification> classMenu = new JComboBox<>(classChoices);
        classMenu.setSelectedItem(e.getClassification());
        JComboBox<String> containMenu = new JComboBox<>(containChoices);

        containMenu.setSelectedItem((!e.isContained()) ? containChoices[0] : containChoices[1]);

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
            processEdit(e, classMenu, containMenu, nameInput);
        }
        initializeListOfNames();
    }

    // REQUIRES:
    // MODIFIES: focused entity
    // EFFECTS: Processes the edit and applies it to the entity
    private void processEdit(Entity e, JComboBox<Classification> classMenu, JComboBox<String> containMenu,
                             JTextField nameInput) {
        boolean contained = !containMenu.getSelectedItem().equals(containChoices[0]);
        e.setName(nameInput.getText());
        e.setObjectClass((Classification)classMenu.getSelectedItem());
        e.setContained(contained);
        initializeRightPanel(database.getSCP(e.getItemNumber()));
    }

    // REQUIRES:
    // MODIFIES: this, e
    // EFFECTS: Checks if an entity can be added (doesn't already exist) and calls processEntity to add it, refreshes UI
    private void addEntity(Entity e) throws EntityAlreadyExistsException {
        if (e.getClassification().name().equals(Classification.UNCLASSIFIED.name())) {
            JTextField nameInput = new JTextField(10);
            JComboBox<Classification> classMenu = new JComboBox<>(classChoices);
            JComboBox<String> containMenu = new JComboBox<>(containChoices);
            containMenu.setSelectedItem(containChoices[1]);
            GridLayout addPanelLayout = new GridLayout(0,1);
            JPanel addPanel = new JPanel(addPanelLayout);
            addPanel.add(new JLabel("SCP-" + Entity.formatNumLength(e.getItemNumber(), Database.MIN_DIGITS)
                        + ": name"));
            addPanel.add(nameInput);
            addPanel.add(Box.createHorizontalStrut(15));
            addPanel.add(classMenu);
            addPanel.add(Box.createHorizontalStrut(15));
            addPanel.add(containMenu);

            int result = JOptionPane.showConfirmDialog(null, addPanel, "Create SCP",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                database.addSCP(e);
                processEntity(e, classMenu, containMenu, nameInput);
            }
            initializeListOfNames();
        } else {
            throw new EntityAlreadyExistsException("This SCP already has an entry!");
        }
    }

    // REQUIRES:
    // MODIFIES: this, e
    // EFFECTS: Edits the entity itself to the specifications, then re-initializes the right panel to update the info
    private void processEntity(Entity e, JComboBox<Classification> classMenu, JComboBox<String> containMenu,
                               JTextField nameInput) {
        boolean contained = !containMenu.getSelectedItem().equals(containChoices[0]);
        e.setName(nameInput.getText());
        e.setObjectClass((Classification)classMenu.getSelectedItem());
        e.setContained(contained);
        initializeRightPanel(e);
    }

    // REQUIRES:
    // MODIFIES: this, buttonPanel
    // EFFECTS: Lists every entity in the database as a button in a list on the middle panel
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
