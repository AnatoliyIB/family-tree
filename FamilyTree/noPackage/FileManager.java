package noPackage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import noPackage.FamilyTree;
import noPackage.Person;

public interface FileManager {
    void saveFamilyTreeToFile(FamilyTree familyTree, File file) throws IOException;
    FamilyTree loadFamilyTreeFromFile(File file) throws IOException, ClassNotFoundException;
    void savePersonsToFile(List<Person> persons, File file) throws IOException;
    List<Person> loadPersonsFromFile(File file) throws IOException, ClassNotFoundException;
}
