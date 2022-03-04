package kz.attractor.java.lesson44;

import java.util.List;

public class BooksDataModel {
    private List<Book> bookList;

    public BooksDataModel(List<Book> books) {
        bookList = books;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
