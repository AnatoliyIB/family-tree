package noPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class FamilyTree<T extends Person> implements Serializable, Iterable<T> {
    private List<T> members;

    public FamilyTree() {
        this.members = new ArrayList<>();
    }

    public void addPerson(T person) {
        members.add(person);
    }

    public List<T> getAllPersons() {
        return new ArrayList<>(members);
    }

    // Метод для отображения информации о родителях и детях по указанному идентификатору
    public String displayFamilyInfo(Long personId) {
        T person = findPersonById(personId);

        if (person == null) {
            return "Персона с ID " + personId + " не найдена.";
        }

        StringBuilder info = new StringBuilder();
        info.append("Информация о персоне:\n");
        info.append(displayPersonInfo(person));
        info.append("\n");

        info.append(displayRelativesInfo("Родители:", person.getParents()));
        info.append(displayRelativesInfo("Дети:", person.getChildren()));

        return info.toString();
    }

    // Метод для поиска персоны по идентификатору
    private T findPersonById(Long personId) {
        return members.stream()
                .filter(person -> person.getId().equals(personId))
                .findFirst()
                .orElse(null);
    }

    // Метод для отображения информации о персоне
    private String displayPersonInfo(T person) {
        return "ID: " + person.getId() + "\n" +
                "Фамилия: " + person.getLastName() + "\n" +
                "Имя: " + person.getFirstName() + "\n" +
                "Отчество: " + person.getMiddleName() + "\n" +
                "Дата рождения: " + person.getBirthDate() + "\n" +
                "Пол: " + person.getGender() + "\n";
    }

    // Метод для отображения информации о родственниках
    private String displayRelativesInfo(String relationship, List<T> relatives) {
        StringBuilder info = new StringBuilder();
        info.append(relationship).append("\n");

        if (relatives.isEmpty()) {
            info.append("Нет информации о родственниках.\n");
        } else {
            for (T relative : relatives) {
                info.append(displayPersonInfo(relative)).append("\n");
            }
        }

        return info.toString();
    }

    // Реализация метода iterator() для интерфейса Iterable
    @Override
    public Iterator<T> iterator() {
        return members.iterator();
    }

    // Метод для сортировки по имени
    public void sortByName() {
        Collections.sort(members, Comparator.comparing(Person::getFirstName)
                                             .thenComparing(Person::getLastName));
    }

    // Метод для сортировки по дате рождения
    public void sortByBirthDate() {
        Collections.sort(members, Comparator.comparing(Person::getBirthDate));
    }
}
