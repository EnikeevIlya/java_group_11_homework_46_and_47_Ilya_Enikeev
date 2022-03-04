package kz.attractor.java.lesson44;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataModel {
    private List<Employee> employees;
    private List<Book> books;
    private List<Book> booksNow;
    private List<Book> booksBefore;

    public DataModel() throws IOException {


        books = JsonSerializer.getBooks();
        employees = JsonSerializer.getUsers();
        booksNow = new ArrayList<>();
        booksBefore = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> getBooksNow() {
        return booksNow;
    }

    public List<Book> getBooksBefore() {
        return booksBefore;
    }
}
