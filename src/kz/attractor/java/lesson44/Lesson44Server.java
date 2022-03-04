package kz.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.ResponseCodes;

import java.io.*;

public class Lesson44Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();
    private Lesson45Server lesson45Server;

    public Lesson44Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/employees", (HttpExchange e) -> renderTemplate(e, "employees.html", getDataModel()));
        registerGet("/jonhmcconary", (HttpExchange e) -> renderTemplate(e, "jonhmcconary.html", getDataModel()));
        registerGet("/profile", (HttpExchange e) -> renderTemplate(e, "profile.html", getDataModel()));
        registerGet("/successregistration", (HttpExchange e) -> renderTemplate(e, "successregistration.html", getDataModel()));
        registerGet("/failregistration", (HttpExchange e) -> renderTemplate(e, "failregistration.html", getDataModel()));
        registerGet("/userdoesnotexist", (HttpExchange e) -> renderTemplate(e, "userdoesnotexist.html", getDataModel()));
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            // путь к каталогу в котором у нас хранятся шаблоны
            // это может быть совершенно другой путь, чем тот, откуда сервер берёт файлы
            // которые отправляет пользователю
            cfg.setDirectoryForTemplateLoading(new File("data"));

            // прочие стандартные настройки о них читать тут
            // https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
    try {
        // загружаем шаблон из файла по имени.
        // шаблон должен находится по пути, указанном в конфигурации
        Template temp = freemarker.getTemplate(templateFile);

        // freemarker записывает преобразованный шаблон в объект класса writer
        // а наш сервер отправляет клиенту массивы байт
        // по этому нам надо сделать "мост" между этими двумя системами

        // создаём поток который сохраняет всё, что в него будет записано в байтовый массив
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // создаём объект, который умеет писать в поток и который подходит для freemarker
        try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {

            // обрабатываем шаблон заполняя его данными из модели
            // и записываем результат в объект "записи"
            temp.process(dataModel, writer);
            writer.flush();

            // получаем байтовый поток
            var data = stream.toByteArray();

            // отправляем результат клиенту
            sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
        }
    } catch (IOException | TemplateException e) {
        e.printStackTrace();
    }
}

    private DataModel getDataModel() throws IOException {
        // возвращаем экземпляр тестовой модели-данных
        // которую freemarker будет использовать для наполнения шаблона
        return new DataModel();
    }

    private Employee getEmployeeDataModel(Employee employee){
        return employee;
    }
}
