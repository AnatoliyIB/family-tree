package noPackage;

import noPackage.FamilyTree;
import noPackage.FamilyTreeFileManager;
import noPackage.Person;
import noPackage.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Person> allPersons = createPersonList();
        FamilyTree<Person> familyTree = new FamilyTree<>();
        for (Person person : allPersons) {
            familyTree.addPerson(person);
        }

        FileManager<FamilyTree<Person>> fileManager = new FamilyTreeFileManager<>();

        JFrame frame = new JFrame("Family Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JComboBox<String> personComboBox = new JComboBox<>();
        for (Person person : allPersons) {
            personComboBox.addItem(person.getId() + ": " + person.getFirstName() + " " + person.getLastName());
        }

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JButton showInfoButton = new JButton("Show Info");
        showInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPerson = (String) personComboBox.getSelectedItem();
                if (selectedPerson != null) {
                    Long personId = Long.parseLong(selectedPerson.split(":")[0]);
                    String info = familyTree.displayFamilyInfo(personId);
                    displayArea.setText(info);
                }
            }
        });

        JButton saveButton = new JButton("Save to File");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog(frame, "Enter file name (with extension):");
                if (fileName != null && !fileName.trim().isEmpty()) {
                    File file = new File(fileName);
                    try {
                        fileManager.saveFamilyTreeToFile(familyTree, file);
                        JOptionPane.showMessageDialog(frame, "Family tree saved successfully.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error saving family tree: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton loadButton = new JButton("Load from File");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        FamilyTree<Person> loadedFamilyTree = fileManager.loadFamilyTreeFromFile(selectedFile);
                        List<Person> loadedPersons = loadedFamilyTree.getAllPersons();
                        allPersons.clear();
                        allPersons.addAll(loadedPersons);
                        personComboBox.removeAllItems();
                        for (Person person : allPersons) {
                            personComboBox.addItem(person.getId() + ": " + person.getFirstName() + " " + person.getLastName());
                        }
                        JOptionPane.showMessageDialog(frame, "Family tree loaded successfully.");
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(frame, "Error loading family tree: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton sortByNameButton = new JButton("Sort by Name");
        sortByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                familyTree.sortByName();
                updateComboBox(personComboBox, familyTree);
            }
        });

        JButton sortByBirthDateButton = new JButton("Sort by Birth Date");
        sortByBirthDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                familyTree.sortByBirthDate();
                updateComboBox(personComboBox, familyTree);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(showInfoButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(sortByNameButton);
        buttonPanel.add(sortByBirthDateButton);

        panel.add(personComboBox, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static List<Person> createPersonList() {
        List<Person> allPersons = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Person person1 = new Person("Ivanov", "Ivan", "Ivanovich", dateFormat.parse("1980-01-01"));
            Person person2 = new Person("Petrov", "Petr", "Petrovich", dateFormat.parse("1990-02-02"));
            Person person3 = new Person("Sidorov", "Sidr", "Sidorovich", dateFormat.parse("2000-03-03"));

            allPersons.add(person1);
            allPersons.add(person2);
            allPersons.add(person3);

            person1.addChild(person2);
            person2.addParent(person1);

            person2.addChild(person3);
            person3.addParent(person2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return allPersons;
    }

    private static void updateComboBox(JComboBox<String> comboBox, FamilyTree<Person> familyTree) {
        comboBox.removeAllItems();
        for (Person person : familyTree) {
            comboBox.addItem(person.getId() + ": " + person.getFirstName() + " " + person.getLastName());
        }
    }
}
