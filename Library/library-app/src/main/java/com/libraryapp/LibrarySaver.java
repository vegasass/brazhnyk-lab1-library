package com.libraryapp;

import java.io.*;
import java.util.List;

public class LibrarySaver{

    public boolean exportLibraryData(List<Book> books, List<Reader> readers, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                writer.println("BOOK;" + book.getTitle() + ";" + book.getAuthor() + ";" + book.getGenre() + ";" + book.getYearOfPublication());
            }
            for (Reader reader : readers) {
                writer.println("READER;" + reader.getFirstName() + ";" + reader.getSecondName() + ";" + reader.isBlocked());
                for (Book borrowedBook : reader.getBooks()) {
                    writer.println("BORROWED;" + borrowedBook.getTitle() + ";" + borrowedBook.getAuthor() + ";" + borrowedBook.getGenre() + ";" + borrowedBook.getYearOfPublication());
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean importLibraryData(List<Book> books, List<Reader> readers, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            books.clear();
            readers.clear();

            String line;
            Reader currentReader = null;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                switch (parts[0]) {
                    case "BOOK":
                        Book book = new Book(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
                        books.add(book);
                        break;
                    case "READER":
                        currentReader = new Reader(parts[1], parts[2]);
                        if (Boolean.parseBoolean(parts[3])) {
                            currentReader.setBlocked(true);
                        }
                        readers.add(currentReader);
                        break;
                    case "BORROWED":
                        if (currentReader != null) {
                            Book borrowedBook = new Book(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
                            currentReader.addBook(borrowedBook);
                        }
                        break;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
