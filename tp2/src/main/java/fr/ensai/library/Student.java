package fr.ensai.library;

public class Student extends Person {
    private int academicYear;
    private boolean isClassDelegate;

    public Student(String name, int age, int academicYear, boolean isClassDelegate) {
        super(name, age);
        this.academicYear = academicYear;
        this.isClassDelegate = isClassDelegate;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public boolean isClassDelegate() {
        return isClassDelegate;
    }

    @Override
    public String toString() {
        return "Student " + getName() + ", Year " + academicYear + (isClassDelegate ? " (Class Delegate)" : "");
    }
}
