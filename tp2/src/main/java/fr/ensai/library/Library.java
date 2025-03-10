package fr.ensai.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name;
    private List<Book> books;

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }
    
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available. The library is as empty as a futuristic Mars colony waiting for its first settlers!");
        } else {
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }
    }

    public void loadBooksFromCSV(String csvFilePath) {
        String line;
        String csvSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String header = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(csvSplitBy);
                if (bookData.length >= 5) {
                    String isbn = bookData[0].trim();
                    String title = bookData[1].trim();
                    Author author = new Author(bookData[2].trim());
                    int year = Integer.parseInt(bookData[3].trim());
                    int pageCount = Integer.parseInt(bookData[4].trim());
                    Book book = new Book(isbn, title, author, year, pageCount);
                    addBook(book);
                } else {
                    System.out.println("Skipping invalid CSV line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }
    
}
