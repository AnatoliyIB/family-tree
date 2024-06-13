package noPackage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final AtomicLong idGenerator = new AtomicLong(0);
    private final Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private Gender gender;
    private Date birthDate;
    private Date deathDate;
    private List<Person> parents;
    private List<Person> children;
    private Person spouse;

    public Person(String lastName, String firstName, String middleName, Date birthDate) {
        this(lastName, firstName, middleName, null, birthDate, null, null, null, null);
    }

    public Person(String lastName, String firstName, String middleName, Gender gender, Date birthDate, 
                  Date deathDate, List<Person> parents, List<Person> children, Person spouse) {
        this.id = idGenerator.incrementAndGet();
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.parents = parents != null ? new ArrayList<>(parents) : new ArrayList<>();
        this.children = children != null ? new ArrayList<>(children) : new ArrayList<>();
        this.spouse = spouse;
    }

    // Геттеры и сеттеры для всех полей, а также методы для работы с родственниками
}
