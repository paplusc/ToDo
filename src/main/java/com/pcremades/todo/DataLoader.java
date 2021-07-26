package com.pcremades.todo;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.util.ResourceUtils;

import com.pcremades.todo.exception.ToDoException;
import com.pcremades.todo.service.ToDoService;

public class DataLoader {

  private DataLoader() {}

  public static List<ToDo> loadToDos(String url) {
    List<?> list;
    try {
      Object obj = parseJsonFromResource(url);
      list = new ArrayList<>((Collection<?>) obj);
    } catch (IOException | ParseException | InterruptedException e) {
      throw new ToDoException(ToDoService.class, "URL", url);
    }
    return castToListOfToDo(list);
  }

  private static Object parseJsonFromResource(String url) throws IOException, InterruptedException, ParseException {
    JSONParser parser;
    if (url.startsWith("http")) {
      String reader = loadHttpToDos(url);
      parser = new JSONParser(reader);
    } else {
      FileReader reader = new FileReader(ResourceUtils.getFile("classpath:" + url));
      parser = new JSONParser(reader);
    }
    return parser.parse();
  }

  private static String loadHttpToDos(String url) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create(url))
               .build();

    HttpResponse<String> response =
               client.send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();
  }

  private static List<ToDo> castToListOfToDo(List<?> list) {
    final List<ToDo> toDoList = new ArrayList<>();
    for (Object todo : list) {
      Integer id = Integer.valueOf(((LinkedHashMap) todo).get("id").toString());
      Integer userId = Integer.valueOf(((LinkedHashMap) todo).get("userId").toString());
      String title = ((LinkedHashMap) todo).get("title").toString();
      Boolean completed = Boolean.valueOf(((LinkedHashMap) todo).get("completed").toString());
      toDoList.add(new ToDo(id, userId, title, completed));
    }
    return toDoList;
  }
}
