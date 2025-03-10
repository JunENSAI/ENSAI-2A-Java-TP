package fr.ensai.library;

public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    /**
     * Returns the name of this person.
     *
     * @return the name of this person.
     */
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
}
