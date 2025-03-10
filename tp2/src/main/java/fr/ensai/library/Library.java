package fr.ensai.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Library {
    private String name;
    private List<Item> items;
    private List<Loan> activeLoans;
    private List<Loan> completedLoans;

    public Library(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.activeLoans = new ArrayList<>();
        this.completedLoans = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    // Affiche tous les items de la bibliothèque.
    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("No items available. The library is as empty as a futuristic Mars colony waiting for its first settlers!");
        } else {
            for (Item item : items) {
                System.out.println(item.toString());
            }
        }
    }

    public void loadBooksFromCSV(String csvFilePath) {
        String line;
        String csvSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(csvSplitBy);
                if (bookData.length >= 5) {
                    String isbn = bookData[0].trim();
                    String title = bookData[1].trim();
                    Author author = new Author(bookData[2].trim());
                    int year = Integer.parseInt(bookData[3].trim());
                    int pageCount = Integer.parseInt(bookData[4].trim());
                    Book book = new Book(isbn, title, author, year, pageCount);
                    addItem(book);
                } else {
                    System.out.println("Skipping invalid CSV line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public void addLoan(Loan loan) {
        activeLoans.add(loan);
    }

    public void completeLoan(Loan loan) {
        if (activeLoans.remove(loan)) {
            completedLoans.add(loan);
        }
    }

    public List<Loan> getActiveLoans() {
        return activeLoans;
    }

    // Affiche la liste des prêts actifs.
    public void displayActiveLoans() {
        System.out.println("Active Loans:");
        if (activeLoans.isEmpty()) {
            System.out.println("No active loans.");
        } else {
            for (Loan loan : activeLoans) {
                System.out.println(loan.toString());
            }
        }
    }

    // Rechercher un prêt actif pour un item donné.
    public Loan findActiveLoanForItem(Item item) {
        for (Loan loan : activeLoans) {
            if (loan.getItem().equals(item)) {
                return loan;
            }
        }
        return null;
    }

    // Retourne tous les livres d'un auteur donné.
    public ArrayList<Book> getBooksByAuthor(Author author) {
        ArrayList<Book> booksByAuthor = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Book) {
                Book book = (Book) item;
                if (book.getAuthor().equals(author)) {
                    booksByAuthor.add(book);
                }
            }
        }
        return booksByAuthor;
    }

    public boolean loanItem(Item item, Student student) {
        if (findActiveLoanForItem(item) != null) {
            return false;
        }
        Loan newLoan = new Loan(student, item, new Date());
        addLoan(newLoan);
        return true;
    }

    public boolean renderItem(Item item) {
        Loan loan = findActiveLoanForItem(item);
        if (loan != null) {
            loan.setReturnDate(new Date());
            completeLoan(loan);
            return true;
        }
        return false;
    }
}
