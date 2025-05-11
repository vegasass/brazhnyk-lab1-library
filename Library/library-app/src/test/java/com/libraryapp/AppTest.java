package com.libraryapp;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppTest {

    private Library lb;
    private Book book;
    private Reader rdr;

    @BeforeEach
    void setUp() {
        lb = new Library();
        book = new Book("Test", "Test", "Test", 2012);
        rdr = new Reader("Ivan", "Ivanovich");

        lb.registerReader(rdr);
        lb.addBook(book);
    }

    @Test
    void testExportDataContent() {
    	
        LibrarySaver mockStorage = mock(LibrarySaver.class);
        Book testBook = new Book("Title1", "Author1", "Genre1", 2024);        
        Reader testReader = new Reader("John", "Doe");
        testReader.addBook(new Book("BorrowedTitle", "BorrowedAuthor", "BorrowedGenre", 2000));

        Library library = new Library(mockStorage);                             
        library.getAvailableBooks().add(testBook);                              
        library.getReaders().add(testReader);

        when(mockStorage.exportLibraryData(anyList(), anyList(), anyString()))
                .thenReturn(true);                                              

        boolean result = library.exportData("fakepath.txt");                   

        verify(mockStorage).exportLibraryData(                                
                argThat(books -> books.size() == 1 &&
                        books.get(0).getTitle().equals("Title1")),
                argThat(readers -> readers.size() == 1 &&
                        readers.get(0).getFirstName().equals("John") &&
                        readers.get(0).getBooks().get(0).getTitle().equals("BorrowedTitle")),
                eq("fakepath.txt")
        );

        assertTrue(result);                                                    
    }
    
    @Test
    void testImportDataContentSimple() {
   
        LibrarySaver mockStorage = mock(LibrarySaver.class);
        Library library = new Library(mockStorage);

 
        List<Book> testBooks = new ArrayList<>();
        testBooks.add(new Book("ImportedTitle", "ImportedAuthor", "ImportedGenre", 2010));

        List<Reader> testReaders = new ArrayList<>();
        Reader reader = new Reader("Anna", "Smith");
        reader.addBook(new Book("BorrowedBook", "BAuthor", "BGenre", 2005));
        testReaders.add(reader);

        when(mockStorage.importLibraryData(anyList(), anyList(), eq("importpath.txt"))).thenAnswer(invocation -> {
            List<Book> actualBooks = invocation.getArgument(0);
            List<Reader> actualReaders = invocation.getArgument(1);
            actualBooks.addAll(testBooks);
            actualReaders.addAll(testReaders);
            return true;
        });


        boolean result = library.importData("importpath.txt");


        assertTrue(result);


        assertEquals(1, library.getAvailableBooks().size());
        assertEquals("ImportedTitle", library.getAvailableBooks().get(0).getTitle());

   
        assertEquals(1, library.getReaders().size());
        Reader importedReader = library.getReaders().get(0);
        assertEquals("Anna", importedReader.getFirstName());
        assertEquals(1, importedReader.getBooks().size());
        assertEquals("BorrowedBook", importedReader.getBooks().get(0).getTitle());
    }

    
    @Test
    void testBlockedUser() {
        assertTrue(lb.giveBookToReader(book, rdr));
        assertTrue(lb.returnBookFromReader(book, rdr));

        lb.changeReaderStatus(rdr);
        assertFalse(lb.giveBookToReader(book, rdr));
    }

    @Test
    void testReaderCanGetBookIfNotBlocked() {
        assertTrue(lb.giveBookToReader(book, rdr));
    }

    @Test
    void testReaderCannotTakeNonExistentBook() {
        Book otherBook = new Book("2", "2", "2", 2020);
        assertFalse(lb.giveBookToReader(otherBook, rdr));
    }
    @Test
    void tetCannotGiveNullBook() {
    	Book otherBook = null;
    	lb.addBook(otherBook);
    	assertFalse(lb.addBook(otherBook));
    }
    @Test
    void testReturnBookNotTaken() {  
        assertFalse(lb.returnBookFromReader(book, rdr));
    }
    @Test
    void testIsPublishedBeforeEqualYear() {
        assertFalse(book.isPublishedBefore(2012)); 
    }
    @Test
    void testCannotRegisterSameUserTwice() {
    	assertFalse(lb.registerReader(rdr));
    }
    @Test
    void testRegisterNullReader() {
    	assertFalse(lb.registerReader(null));
    }
    @Test
    void testReturnBookThatWasNotTaken() {
        assertFalse(lb.returnBookFromReader(book, rdr)); 
    }
}
