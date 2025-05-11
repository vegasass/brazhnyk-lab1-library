package com.libraryapp;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        boolean exit = false;

        while (!exit) {
            System.out.println("Бібліотека");
            System.out.println("1. Зареєструвати читача");
            System.out.println("2. Додати книгу");
            System.out.println("3. Видати книгу читачу");
            System.out.println("4. Прийняти книгу від читача");
            System.out.println("5. Список книг");
            System.out.println("6. Список читачів");
            System.out.println("7. Показати книги, видані до певного року");
            System.out.println("8. Змінити статус користувача");
            System.out.println("9. Зберегти дані у файл");
            System.out.println("10. Завантажити дані з файлу");
            System.out.println("11. Оновити книгу");
            System.out.println("12. Оновити читача");
            System.out.println("13. Видалити книгу");
            System.out.println("14. Видалити читача");
            System.out.println("15. Вийти");	
            System.out.print("Оберіть опцію: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Ім'я: ");
                    String name = scanner.nextLine();
                    System.out.print("Прізвище: ");
                    String surname = scanner.nextLine();

                    Reader reader = new Reader(name, surname);
                    library.registerReader(reader);
                    break;

                case 2:
                    System.out.print("Назва книги: ");
                    String title = scanner.nextLine();
                    System.out.print("Автор: ");
                    String author = scanner.nextLine();
                    System.out.print("Жанр: ");
                    String genre = scanner.nextLine();
                    System.out.print("Рік публікації: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();

                    Book book = new Book(title, author, genre, year);
                    library.addBook(book);
                    break;

                case 3:
                    System.out.print("Ім’я читача: ");
                    String nameReader = scanner.nextLine();
                    System.out.print("Прізвище: ");
                    String surnameReader = scanner.nextLine();

                    Reader foundReader = library.findReaderByName(nameReader, surnameReader);
                    if (foundReader == null) {
                        System.out.println("Читача не знайдено");
                        break;
                    }

                    System.out.print("Назва книги: ");
                    String bookToGive = scanner.nextLine();
                    Book foundBook = library.findAvailableBookByTitle(bookToGive);
                    if (foundBook == null) {
                        System.out.println("Книгу не знайдено");
                        break;
                    }

                    library.giveBookToReader(foundBook, foundReader);
                    break;

                case 4:
                    System.out.print("Ім’я читача: ");
                    String rName = scanner.nextLine();
                    System.out.print("Прізвище: ");
                    String rSurname = scanner.nextLine();

                    Reader rFound = library.findReaderByName(rName, rSurname);
                    if (rFound == null) {
                        System.out.println("Читача не знайдено");
                        break;
                    }

                    System.out.print("Назва книги: ");
                    String titleToReturn = scanner.nextLine();
                    Book bookToReturn = rFound.findBookByTitle(titleToReturn);
                    if (bookToReturn == null) {
                        System.out.println("Читач не має такої книги");
                        break;
                    }

                    library.returnBookFromReader(bookToReturn, rFound);
                    break;

                case 5:
                    library.listAvailableBooks();
                    break;

                case 6:
                    library.listReaders();
                    break;

                case 7:
                    System.out.print("Введіть рік: ");
                    int yearLimit = scanner.nextInt();
                    scanner.nextLine();
                    library.listBooksPublishedBefore(yearLimit);
                    break;

                case 8:
                    System.out.print("Ім’я читача для зміни статусу: ");
                    String blockName = scanner.nextLine();
                    System.out.print("Прізвище: ");
                    String blockSurname = scanner.nextLine();

                    Reader readerToChange = library.findReaderByName(blockName, blockSurname);
                    if (readerToChange == null) {
                        System.out.println("Читача не знайдено.");
                        break;
                    }

                    library.changeReaderStatus(readerToChange);
                    System.out.println("Статус користувача змінено.");
                    break;

                case 9:
                    System.out.print("Відсортувати за датами книги?: 1 - Так/ 2 - Ні");
                    int result = scanner.nextInt();
                    scanner.nextLine();
                    if(result == 1) {
                        library.sortByDate();
                    }
                    System.out.print("Введіть шлях до файлу для збереження: ");
                    String exportPath = scanner.nextLine();
                    if (library.exportData(exportPath)) {
                        System.out.println("Дані успішно збережені!");
                    } else {
                        System.out.println("Помилка при збереженні даних.");
                    }
                    break;

                case 10:
                    System.out.print("Введіть шлях до файлу для завантаження: ");
                    String importPath = scanner.nextLine();
                    if (library.importData(importPath)) {
                        System.out.println("Дані успішно завантажені!");
                    } else {
                        System.out.println("Помилка при завантаженні даних");
                    }
                    break;

                case 11:
                    System.out.print("Назва книги, яку потрібно оновити: ");
                    String oldBookTitle = scanner.nextLine();

                    Book oldBook = library.findAvailableBookByTitle(oldBookTitle);
                    if (oldBook == null) {
                        System.out.println("Книгу не знайдено.");
                        break;
                    }

                    System.out.println("Введіть нові дані для книги:");
                    System.out.print("Назва: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Автор: ");
                    String newAuthor = scanner.nextLine();
                    System.out.print("Жанр: ");
                    String newGenre = scanner.nextLine();
                    System.out.print("Рік: ");
                    int newYear = scanner.nextInt();
                    scanner.nextLine();

                    Book updatedBook = new Book(newTitle, newAuthor, newGenre, newYear);
                    library.updateBook(oldBookTitle, updatedBook);
                    break;

                case 12:
                    System.out.print("Ім’я читача, якого потрібно оновити: ");
                    String oldFirst = scanner.nextLine();
                    System.out.print("Прізвище: ");
                    String oldLast = scanner.nextLine();

                    Reader existingReader = library.findReaderByName(oldFirst, oldLast);
                    if (existingReader == null) {
                        System.out.println("Читача не знайдено.");
                        break;
                    }

                    System.out.print("Нове ім’я: ");
                    String newFirst = scanner.nextLine();
                    System.out.print("Нове прізвище: ");
                    String newLast = scanner.nextLine();

                    Reader updatedReader = new Reader(newFirst, newLast);
                    updatedReader.setBlocked(existingReader.isBlocked());
                    for (Book b : existingReader.getBooks()) {
                        updatedReader.addBook(b); 
                    }
                    library.updateReader(oldFirst, oldLast, updatedReader);
                    break;
                    

                case 13:
                    System.out.print("Назва книги, яку потрібно видалити: ");
                    String deleteBookTitle = scanner.nextLine();
                    Book deleteBook = library.findAvailableBookByTitle(deleteBookTitle);
                    if (deleteBook == null) {
                        System.out.println("Книгу не знайдено.");
                        break;
                    }
                    library.removeBook(deleteBook);
                    break;

                case 14:
                    System.out.print("Ім’я читача, якого потрібно видалити: ");
                    String deleteFirst = scanner.nextLine();
                    System.out.print("Прізвище: ");
                    String deleteLast = scanner.nextLine();

                    Reader deleteReader = library.findReaderByName(deleteFirst, deleteLast);
                    if (deleteReader == null) {
                        System.out.println("Читача не знайдено.");
                        break;
                    }
                    library.deleteReaderByName(deleteFirst, deleteLast);
                    break;

                case 15:
                    exit = true;
                    break;

                default:
                    System.out.println("Невідома опція. Спробуйте знову.");
            }
        }

        scanner.close();
    }
}
