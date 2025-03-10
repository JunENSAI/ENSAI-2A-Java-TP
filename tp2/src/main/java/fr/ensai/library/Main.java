package fr.ensai.library;

public class Main {
    public static void main(String[] args) {
        Library myLibrary = new Library("Librairie Martin");

        String csvFilePath = "./src/main/resources/books.csv";
        myLibrary.loadBooksFromCSV(csvFilePath);

        // Création et ajout de magazines
        Magazine magazine1 = new Magazine("1234-5678", "How to make a friend", 2023, 50, 1);
        Magazine magazine2 = new Magazine("8765-4321", "Chess with GM", 2023, 40, 2);
        myLibrary.addItem(magazine1);
        myLibrary.addItem(magazine2);

        myLibrary.displayItems();

        Student student = new Student("Alice", 20, 2, false);

        if (myLibrary.loanItem(magazine1, student)) {
            System.out.println("Loan successful for " + magazine1.getTitle());
        } else {
            System.out.println("Loan failed for " + magazine1.getTitle());
        }

        myLibrary.displayActiveLoans();

        if (myLibrary.renderItem(magazine1)) {
            System.out.println("Item returned: " + magazine1.getTitle());
        } else {
            System.out.println("Return failed for " + magazine1.getTitle());
        }

        myLibrary.displayActiveLoans();
    }
}

