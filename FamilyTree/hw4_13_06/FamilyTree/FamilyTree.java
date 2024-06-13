package hw4_13_06.FamilyTree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import hw4_13_06.Person.Person;

public class FamilyTree implements Serializable, Iterable<Person> {
    private List<Person> persons;

    public FamilyTree() {
        this.persons = new ArrayList<>();
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public List<Person> getAllPersons() {
        return new ArrayList<>(persons);
    }

    // Метод для отображения информации о родителях и детях по указанному идентификатору
    public String displayFamilyInfo(Long personId) {
        Person person = findPersonById(personId);

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
    private Person findPersonById(Long personId) {
        return persons.stream()
                .filter(person -> person.getId().equals(personId))
                .findFirst()
                .orElse(null);
    }

    // Метод для отображения информации о персоне
    private String displayPersonInfo(Person person) {
        return "ID: " + person.getId() + "\n" +
                "Фамилия: " + person.getLastName() + "\n" +
                "Имя: " + person.getFirstName() + "\n" +
                "Отчество: " + person.getMiddleName() + "\n" +
                "Дата рождения: " + person.getBirthDate() + "\n" +
                "Пол: " + person.getGender() + "\n";
    }

    // Метод для отображения информации о родственниках
    private String displayRelativesInfo(String relationship, List<Person> relatives) {
        StringBuilder info = new StringBuilder();
        info.append(relationship).append("\n");

        if (relatives.isEmpty()) {
            info.append("Нет информации о родственниках.\n");
        } else {
            for (Person relative : relatives) {
                info.append(displayPersonInfo(relative)).append("\n");
            }
        }

        return info.toString();
    }

    // Реализация метода iterator() для интерфейса Iterable
    @Override
    public Iterator<Person> iterator() {
        return persons.iterator();
    }

    // Метод для сортировки по имени
    public void sortByName() {
        Collections.sort(persons, Comparator.comparing(Person::getFirstName).thenComparing(Person::getLastName));
    }

    // Метод для сортировки по дате рождения
    public void sortByBirthDate() {
        Collections.sort(persons, Comparator.comparing(Person::getBirthDate));
    }
}