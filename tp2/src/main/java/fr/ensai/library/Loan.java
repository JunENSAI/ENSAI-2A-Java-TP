package fr.ensai.library;

import java.util.Date;

public class Loan {
    private Student student;
    private Item item;
    private Date startDate;
    private Date returnDate; // null at creation

    public Loan(Student student, Item item,Date startDate) {
        this.student = student;
        this.item = item;
        this.startDate = startDate;
        this.returnDate = null;
    }

    // Met à jour la date de retour du prêt.
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Student getStudent() {
        return student;
    }

    public Item getItem() {
        return item;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "Item " + item.getTitle() + " borrowed by " + student.getName() + ".";
    }
}
