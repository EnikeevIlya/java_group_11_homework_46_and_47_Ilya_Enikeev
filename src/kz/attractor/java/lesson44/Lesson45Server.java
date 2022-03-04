package kz.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.RouteHandler;
import kz.attractor.java.server.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Lesson45Server extends Lesson44Server{
    private final BookService bookService = new BookService();

    private List <Employee> users;
    private Employee user;


    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/login",this::loginGet);
        registerPost("/login",this::loginPost);
        registerGet("/register",this::regGet);
        registerPost("/register",this::regPost);
        registerGet("/profile", this::freemarkerHandleRegister);
        registerGet("/books", this::registerBooksHandler);
        registerGet("/book", this::registerBookHandlerGet);
        registerPost("/book", this::takeBookPost);
        registerPost("/return", this::returnBooksPost);
        registerPost("/quit", this::quitPost);
    }

    private void regGet(HttpExchange exchange){
        Path path = makeFilePath("register.html");
        sendFile(exchange,path, ContentType.TEXT_HTML);
    }

    private void loginGet(HttpExchange exchange){
        Path path = makeFilePath("login.html");
        sendFile(exchange,path, ContentType.TEXT_HTML);
    }

    protected void registerPost(String route, RouteHandler handler){
        getRoutes().put("POST " + route,handler);
    }

    private void loginPost(HttpExchange exchange){
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        try {
            logIn(exchange, parsed.get("email"), parsed.get("user-password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void regPost(HttpExchange exchange){
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        try {
            addUser(exchange, parsed.get("username"), parsed.get("surname"), new ArrayList<>(), new ArrayList<>(), "/" + parsed.get("username").toLowerCase() + parsed.get("surname").toLowerCase(), parsed.get("email"), parsed.get("user-password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(HttpExchange exchange, String name, String surname, List<Book> booksNow, List<Book> booksBefore, String link, String email, String password) throws IOException {
        users = new ArrayList<>();
        List<Employee> jsonUsers = JsonSerializer.getUsers();
        if (jsonUsers != null && !jsonUsers.isEmpty()) {
            users = jsonUsers;
        }
        long count = users.stream()
                .filter(user -> user.getEmail().equals(email))
                .count();
        if (count == 0) {
            users.add(new Employee(name, surname , new ArrayList<>(), new ArrayList<>(), link, email, password));
            JsonSerializer.writeUsers(users);
            redirect303(exchange, "/successregistration");
        } else {
            redirect303(exchange, "/failregistration");
        }
    }

    public void logIn(HttpExchange exchange, String email, String password) throws IOException {
        users = new ArrayList<>();
        List<Employee> jsonUsers = JsonSerializer.getUsers();
        if (jsonUsers != null && !jsonUsers.isEmpty()) {
            users = jsonUsers;
        } else {
            redirect303(exchange, "/userdoesnotexist");
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email) && users.get(i).getPassword().equals(password)) {
                String id = UUID.randomUUID().toString();
                user = users.stream()
                        .filter(c -> c.getEmail().equals(email) && c.getPassword().equals(password))
                        .collect(Collectors.toList()).get(0);
                user.setCookie(id);
                setCookie(exchange, Cookie.make("id", id, 600, true));
                redirect303(exchange, "/profile");
                return;
            }
        }
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getEmail().equals(email) && !users.get(i).getPassword().equals(password)) {
                redirect303(exchange, "/userdoesnotexist");
                return;
            }
        }
    }

    private void freemarkerHandleRegister(HttpExchange exchange) throws IOException {
        if (user != null) {
            renderTemplate(exchange, "profile.html", user);
        } else {
            redirect303(exchange, "/login");
        }
    }

    private String getContentType(HttpExchange exchange) {
        return exchange.getRequestHeaders()
                .getOrDefault("Content-type", List.of(""))
                .get(0);
    }

    protected String getBody(HttpExchange exchange){
        InputStream input = exchange.getRequestBody();
        Charset utf8 = StandardCharsets.UTF_8;
        InputStreamReader isr = new InputStreamReader(input,utf8);
        try(BufferedReader reader = new BufferedReader(isr)){
            return reader.lines().collect(Collectors.joining(""));
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    protected void redirect303(HttpExchange exchange,String path){
        try{
            exchange.getResponseHeaders().add("Location",path);
            exchange.sendResponseHeaders(303,0);
            exchange.getResponseBody().close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    protected void setCookie(HttpExchange exchange, Cookie cookie){
        exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
    }

    private void takeBookPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        try {
            takeBook(exchange, user, parsed.get("id"));
        } catch (Exception e) {
            redirect303(exchange, "/login");
        }
    }

    private void takeBook(HttpExchange exchange, Employee user, String id) throws IOException {
        if (!user.getCookie().isEmpty()) {
            var book = bookService.getBooks().getBookList().stream()
                    .filter(e -> e.getId() == Integer.parseInt(id))
                    .findFirst().get();
            List<Book> booksNow = user.getBooksNow();
            List<Book> booksBefore = user.getBooksBefore();
            if(booksNow.size() <= 1){
                book.setStatus("not available");
                booksNow.add(book);
                booksBefore.add(book);
                JsonSerializer.writeBooks(bookService.getBooks().getBookList());
                user.setBooksNow(booksNow);
                user.setBooksBefore(booksBefore);
                JsonSerializer.writeUsers(users);
                redirect303(exchange, "/profile");
            }
            else{
                redirect303(exchange, "/profile");
            }
        }
    }

    private void returnBooks(HttpExchange exchange, Employee user, String id) throws IOException {
        if (!user.getCookie().isEmpty()) {
            var book = user.getBooksNow().stream()
                    .filter(e -> e.getId() == Integer.parseInt(id))
                    .findFirst().get();
            var book2 = bookService.getBooks().getBookList().stream()
                    .filter(e -> e.getId() == Integer.parseInt(id))
                    .findFirst().get();
            book.setStatus("available");
            book2.setStatus("available");
            user.getBooksNow().remove(book);
            JsonSerializer.writeBooks(bookService.getBooks().getBookList());
            JsonSerializer.writeUsers(users);
            redirect303(exchange, "/profile");
        }
    }

    private void registerBookHandlerGet(HttpExchange exchange) {
        String queryParams = getQueryParams(exchange);

        Map<String, String> params = Utils.parseUrlEncoded(queryParams, "&");

        Book book = new Book();
        try {
            book = bookService.findBookById(params.get("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderTemplate(exchange, "book.ftl", book);
    }

    private void registerBooksHandler(HttpExchange exchange) {
        var dataModel = bookService.getBooks();

        renderTemplate(exchange, "books.html", dataModel);
    }

    private void returnBooksPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        try {
            returnBooks(exchange, user, parsed.get("id"));
        } catch (Exception e) {
            redirect303(exchange, "/login");
        }
    }



    private void quitPost(HttpExchange exchange) {
        quit(exchange, user);
    }

    private void quit(HttpExchange exchange, Employee user) {
        setCookie(exchange, Cookie.make("id", user.getCookie(), 0, false));
        user.setCookie(null);
        redirect303(exchange, "/login");
    }

    public List<Employee> getUsers() {
        return users;
    }

    public Employee getUser() {
        return user;
    }
}