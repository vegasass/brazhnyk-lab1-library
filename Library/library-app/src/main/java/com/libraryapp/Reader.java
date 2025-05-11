package com.libraryapp;
import java.util.List;
import java.util.ArrayList;

public class Reader {
    private String firstName;
    private String secondName;
    private boolean isBlocked = false;
    
    private List<Book> books = new ArrayList<>();
    private int booksCount;
    private final int maxBooks  = 5; 

    public Reader(String firstName, String secondName){
        this.firstName = firstName;
        this.secondName = secondName;
    }


    public boolean addBook(Book book){
    	if(booksCount == maxBooks) {
    		return false;
    	}
    	books.add(book);
    	booksCount++;
    	return true;
    }

    public boolean removeBook(Book book){
    	for (int i = 0; i < books.size(); i++) {
            if (books.get(i).equals(book)) {
                books.remove(i);
                booksCount--;
                return true;
            }
        }
    	System.out.println("У читача книги нема");
        return false;
    }

    public void prinListBooks() {
    	if(booksCount == 0) {
    		System.out.println("Читач ще не брав книгу");
    		return;
    	}
    	System.out.println("Активні книги читача:");
    	for (int i = 0; i < books.size(); i++) {
    		System.out.println(books.get(i).getTitle());
        }
    }
    

    // Getters and setters
    public String getFirstName(){
        return firstName;
    }
    
    public List<Book> getBooks() {
        return books;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName(){
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public boolean isBlocked(){
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public void printInformation(){
        System.out.println(toString());
    }
    public Book findBookByTitle(String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return
               "First name: " + firstName + "\n" +
               "Second name: " + secondName + "\n" +
               "Blocked: " + (isBlocked ? "Yes" : "No");
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Reader reader = (Reader) obj;

        return
               (firstName != null ? firstName.equals(reader.firstName) : reader.firstName == null) &&
               (secondName != null ? secondName.equals(reader.secondName) : reader.secondName == null);
    }

    @Override
    public int hashCode(){
        int result = (firstName == null) ? 0 : firstName.hashCode();
        result = 31 * result + (secondName == null ? 0 : secondName.hashCode());
        result = 31 * result + (isBlocked ? 1 : 0);
        return result;
    }
}
