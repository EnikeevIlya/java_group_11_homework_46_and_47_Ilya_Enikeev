package kz.attractor.java.lesson44;

import java.io.IOException;

public class BookService {
    private final BooksDataModel books = new BooksDataModel(JsonSerializer.getBooks());

    public BookService() throws IOException {
//        books.addAll(JsonSerializer.getBooks());
    }

    public BooksDataModel getBooks() {
        return books;
    }

    public Book findBookById(String id) {
        var book = books.getBookList().stream().filter(e -> e.getId() == Integer.parseInt(id)).findFirst();
        try {
            if (book.isEmpty()) {
                throw new Exception("Such a book does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book.get();
    }
}
