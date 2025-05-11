package com.libraryapp;

import java.io.*;
import java.util.*;

public class Library {
    private List<Book> availableBooks = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();
    private LibrarySaver librarySaver = new LibrarySaver();
    public Library() {}
    public Library(LibrarySaver saver) {
        this.librarySaver = saver;
    }
    public List<Book> getAvailableBooks() {
        return availableBooks;
    }
    public List<Reader> getReaders() {
        return readers;
    }
    
    public boolean addBook(Book book){
    	if(book == null) {
    		return false;
    	}
        if(availableBooks.add(book)) {
        	System.out.println("Книгу " + book.getTitle() + " додано до бібліотеки");
        	return true;
        }
        return false;
        
    }
    public Book findAvailableBookByTitle(String title) {
        for (Book b : availableBooks) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                return b;
            }
        }
        return null;
    }
    public boolean removeBook(Book book){
        if(availableBooks.remove(book)) {
            System.out.println("Книгу " + book.getTitle() + " видалено з бібліотеки");
            return true;
        }else {
            System.out.println("Книгу не знайдено в бібліотеці");
            return false;
        }
    }
    public boolean updateBook(String oldTitle, Book updatedBook) {
        for (int i = 0; i < availableBooks.size(); i++) {
            if (availableBooks.get(i).getTitle().equalsIgnoreCase(oldTitle)) {
                availableBooks.set(i, updatedBook);
                System.out.println("Книгу оновлено.");
                return true;
            }
        }
        System.out.println("Книгу не знайдено.");
        return false;
    }

    public void listBooksPublishedBefore(int yearLimit) {
        boolean found = false;
        for (Book book : availableBooks) {
            if (book.isPublishedBefore(yearLimit)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Немає книг, виданих до " + yearLimit + " року");
        }
    }

    public boolean registerReader(Reader reader){
    	if(reader == null) {
    		return false;
    	}
    	for(Reader r : readers) {
    		if(r.equals(reader)) {
    			System.out.println("Читач вже зареєстрований в біліотеці");
    			return false;
    		}
    	}
        readers.add(reader);
        System.out.println("Читача " + reader.getFirstName() + " " + reader.getSecondName() +  " зареєстровано");
        return true;
    }
    public boolean deleteReaderByName(String firstName, String lastName) {
        for (int i = 0; i < readers.size(); i++) {
            Reader r = readers.get(i);
            if (r.getFirstName().equalsIgnoreCase(firstName) &&
                r.getSecondName().equalsIgnoreCase(lastName)) {
                readers.remove(i);
                System.out.println("Читача " + firstName + " " + lastName + " видалено");
                return true;
            }
        }
        System.out.println("Читача не знайдено");
        return false;
    }
    public Reader findReaderByName(String name, String surname) {
        for (Reader r : readers) {
            if (r.getFirstName().equalsIgnoreCase(name) &&
                r.getSecondName().equalsIgnoreCase(surname)) {
                return r;
            }
        }
        return null;
    }

    public boolean updateReader(String oldFirstName, String oldLastName, Reader updatedReader) {
        for (int i = 0; i < readers.size(); i++) {
            Reader r = readers.get(i);
            if (r.getFirstName().equalsIgnoreCase(oldFirstName) &&
                r.getSecondName().equalsIgnoreCase(oldLastName)) {
                readers.set(i, updatedReader);
                System.out.println("Читача оновлено.");
                return true;
            }
        }
        System.out.println("Читача не знайдено.");
        return false;
    }
    public boolean giveBookToReader(Book book, Reader reader){
        if(reader.isBlocked()) {
            System.out.println("Читач заблокований і не може брати книги");
            return false;
        }

        if(!availableBooks.contains(book)){
            System.out.println("Книги " + book.getTitle() + " немає в бібліотеці.");
            return false;
        }

        if(reader.addBook(book)){
            availableBooks.remove(book);
            System.out.println("Книгу " + book.getTitle() + " видано читачу " + reader.getFirstName());
            return true;
        } else{
            System.out.println("Читач досяг ліміту книг.");
            return false;
        }
    }


    public boolean returnBookFromReader(Book book, Reader reader){
        if(reader.removeBook(book)) {
            availableBooks.add(book);
            System.out.println("Читач " + reader.getFirstName() + "повернув книгу " + book.getTitle());
            return true;
        } else{
            System.out.println("Читач не має цієї книги");
            return false;
        }
    }
    
    public void listAvailableBooks() {
        if(availableBooks.isEmpty()) {
            System.out.println("У бібліотеці немає доступних книг");
        } else {
            System.out.println("Список доступних книг у бібліотеці:");
            for (Book book : availableBooks) {
                System.out.println("- " + book.getTitle());
            }
        }
    }
    
    public void changeReaderStatus(Reader rdr) {
    	if(rdr.isBlocked()) {
    		System.out.println(rdr.getFirstName() + "разблокований");
    		rdr.setBlocked(false);
    	}else {
    		System.out.println(rdr.getFirstName() + "заблокований");
    		rdr.setBlocked(true);
    	}
    }

    public void listReaders(){
        if (readers.isEmpty()) {
            System.out.println("У бібліотеці ще немає читачів");
        } else {
            System.out.println("Список зареєстрованих читачів:");
            for (Reader reader : readers) {
            	 System.out.println("- " + reader.getFirstName() + " " + reader.getSecondName() +
                 (reader.isBlocked() ? " (заблокований)" : ""));
                
            }
        }
    }


    public void sortByDate() {
        int n = availableBooks.size();
        for (int j = 1; j < n; j++) {
            boolean isSorted = true;
            for (int i = 0; i < n - j; i++) {
                if (availableBooks.get(i).getYearOfPublication() > availableBooks.get(i + 1).getYearOfPublication()) {
                    Book tmp = availableBooks.get(i);
                    availableBooks.set(i, availableBooks.get(i + 1));
                    availableBooks.set(i + 1, tmp);
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }
    public boolean exportData(String filePath) {
    	return librarySaver.exportLibraryData(availableBooks, readers, filePath);
    }
    public boolean importData(String filePath) {
    	return librarySaver.importLibraryData(availableBooks, readers, filePath);
    }
    

}
