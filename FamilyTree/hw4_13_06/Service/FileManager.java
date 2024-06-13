package hw4_13_06.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import hw4_13_06.FamilyTree.FamilyTree;
import hw4_13_06.Person.Person;

public interface FileManager {
    void saveFamilyTreeToFile(FamilyTree familyTree, File file) throws IOException;
    FamilyTree loadFamilyTreeFromFile(File file) throws IOException, ClassNotFoundException;
    void savePersonsToFile(List<Person> persons, File file) throws IOException;
    List<Person> loadPersonsFromFile(File file) throws IOException, ClassNotFoundException;
}