package com.libraryapp;
public class Book {
    private String title;
    private String author;
    private String genre;
    private int yearOfPublication;

    public Book(String title, String author, String genre, int yearOfPublication) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.yearOfPublication = yearOfPublication;
    }
    
    public boolean isPublishedBefore(int year) {
        return this.yearOfPublication < year;
    }
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
    
    public void printInformation() {
    	System.out.println(toString());
    }
    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Genre: " + genre + "\n" +
                "Year: " + yearOfPublication;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book newBook = (Book) obj;

        return yearOfPublication == newBook.yearOfPublication &&
                (title != null ? title.equals(newBook.title) : newBook.title == null) &&
                (author != null ? author.equals(newBook.author) : newBook.author == null) &&
                (genre != null ? genre.equals(newBook.genre) : newBook.genre == null);
    }
    @Override 
    public int hashCode() {
        int result = (title == null) ? 0 : title.hashCode();
        result = 31 * result + (author == null ? 0 : author.hashCode());
        result = 31 * result + (genre == null ? 0 : genre.hashCode());
        result = 31 * result + yearOfPublication;
        return result;
    }

}
