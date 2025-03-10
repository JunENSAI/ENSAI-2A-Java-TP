package fr.ensai.library;

import java.time.LocalDate;

public class Loan {
    private Student student;
    private Item item;
    private LocalDate startDate;
    private LocalDate returnDate; // null at creation

    public Loan(Student student, Item item, LocalDate startDate) {
        this.student = student;
        this.item = item;
        this.startDate = startDate;
        this.returnDate = null;
    }

    // Met à jour la date de retour du prêt.
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Student getStudent() {
        return student;
    }

    public Item getItem() {
        return item;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "Item " + item.getTitle() + " borrowed by " + student.getName() + ".";
    }
}
