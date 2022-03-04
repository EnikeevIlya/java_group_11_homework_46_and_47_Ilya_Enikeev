package kz.attractor.java.lesson44;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonSerializer {

    public static List<Employee> getUsers() throws IOException {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Employee>>(){}.getType();
        try (Reader reader = new FileReader("users.json")) {
            List<Employee> users = gson.fromJson(reader, listType);
            return users;
        }
    }

    public static void writeUsers(List<Employee> users) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter("users.json")) {
            String json = gson.toJson(users);
            writer.write(json);
        }
    }

    public static List<Book> getBooks() throws IOException {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Book>>(){}.getType();
        try (Reader reader = new FileReader("books.json")) {
            List<Book> books = gson.fromJson(reader, listType);
            return books;
        }
    }

    public static void writeBooks(List<Book> books) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter("books.json")) {
            String json = gson.toJson(books);
            writer.write(json);
        }
    }
}