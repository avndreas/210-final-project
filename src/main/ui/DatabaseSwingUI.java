package ui;

import model.Database;
import model.Entity;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseSwingUI extends JFrame implements ActionListener {
    private JLabel label;
    private JTextField field;

    private static final double X_SCALE = 0.8;
    private static final double Y_SCALE = 0.8;

    private static final String JSON_STORE = "./data/database.json";
    private Database database;
    private static final int SERIES = 1;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JPanel entityPanel;
    private JSplitPane splitPane;

    private ArrayList<JButton> buttonListOfSCPs;

    public DatabaseSwingUI() {
        super("SCP Database");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setPreferredSize(new Dimension((int)(width * X_SCALE), (int)(height * Y_SCALE)));

        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13) );

        setLayout(new FlowLayout());

        entityPanel = new JPanel();

        JScrollPane scrollableList = new JScrollPane(entityPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton btn = new JButton("Change");
        btn.setActionCommand("myButton");
        btn.addActionListener(this); // Sets "this" object as an action listener for btn
        // so that when the btn is clicked,
        // this.actionPerformed(ActionEvent e) will be called.
        // You could also set a different object, if you wanted
        // a different object to respond to the button click
        label = new JLabel("flag");
        field = new JTextField(5);
        add(field);
        add(btn);
        add(label);
        add(scrollableList);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void runDatabase() {
        boolean keepRunning = true;
        String command = null;

        database = new Database(SERIES);
        initializeListOfNames(database);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, pictureScrollPane);

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

    }

    private void displayMenu() {

    }

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            label.setText(field.getText());
        }
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
