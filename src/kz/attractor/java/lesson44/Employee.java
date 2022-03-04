package kz.attractor.java.lesson44;

import java.util.List;

public class Employee {
    private String name;
    private String surname;
    private List<Book> booksNow;
    private List<Book> booksBefore;
    private String link;
    private String email;
    private String password;
    private String cookie;

    public Employee(String name, String surname, List<Book> booksNow, List<Book> booksBefore, String link, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.booksNow = booksNow;
        this.booksBefore = booksBefore;
        this.link = link;
        this.email = email;
        this.password = password;
    }

    public List<Book> getBooksNow() {
        return booksNow;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Book> getBooksBefore() {
        return booksBefore;
    }

    public String getLink() {
        return link;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBooksNow(List<Book> booksNow) {
        this.booksNow = booksNow;
    }

    public void setBooksBefore(List<Book> booksBefore) {
        this.booksBefore = booksBefore;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
