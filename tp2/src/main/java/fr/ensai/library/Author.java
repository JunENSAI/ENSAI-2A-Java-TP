package fr.ensai.library;

import java.util.Objects;
import java.util.Random;

/**
 * Represents an Author.
 */
public class Author extends Person {

    private String nationality;

    /**
     * Constructs a new Author object.
     *
     * @param name the name of the author
     * @param age the age of the author
     * @param nationality the nationality of the author
     */
    public Author(String name, int age, String nationality) {
        super(name, age);
        this.nationality = nationality;
    }

    /**
     * Constructs a new Author object with a random age between 0 and 149.
     *
     * @param name the name of the author
     */
    public Author(String name) {
        super(name, new Random().nextInt(150));
        this.nationality = "Unknown";
    }

    /**
     * Two authors are considered equal if their names are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Author author = (Author) obj;
        return Objects.equals(getName(), author.getName());
    }

    @Override
    public String toString() {
        return "Author " + getName();
    }
}
