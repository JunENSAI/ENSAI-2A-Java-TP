package fr.ensai.library;

public class Main {
    public static void main(String[] args) {
        Library myLibrary = new Library("Librairie");

        String csvFilePath = "./src/main/resources/books.csv";
        myLibrary.loadBooksFromCSV(csvFilePath);

        myLibrary.displayBooks();
    }
}
