package com.pcremades.todo.service;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.pcremades.todo.ToDo;
import com.pcremades.todo.ToDoException;
import com.pcremades.todo.domain.ToDoEntity;

@Service
@Profile("!dev")
public class ToDoServiceImpl implements ToDoService {

  private final List<ToDo> toDos;

  @Autowired
  public ToDoServiceImpl(@Value("${todo.data:https://jsonplaceholder.typicode.com/todos}") String url) {
    toDos = loadToDos(url);
  }

  @Override
  public ToDo create(ToDo item) {
    item.generateId();
    toDos.add(item);
    return item;
  }

  @Override
  public void delete(Integer id) {
    boolean removed = toDos.removeIf(todo -> todo.id().equals(id));
    if (!removed) {
      throw new ToDoException(ToDoEntity.class, "id", id.toString());
    }
  }

  @Override
  public Collection<ToDo> getAll() {
    return toDos;
  }

  @Override
  public Collection<ToDo> getByStatus(boolean isCompleted) {
    return toDos.stream().filter(todo -> todo.completed() == isCompleted).collect(Collectors.toList());
  }

  @Override
  public Collection<ToDo> getByUserId(int userId) {
    if (userId <= 0) {
      throw new ToDoException(ToDoEntity.class, "userId", String.valueOf(userId));
    }
    return toDos.stream().filter(todo -> todo.userId() == userId).collect(Collectors.toList());
  }

  @Override
  public Map<Boolean, Long> getStats() {
    final long totalCompleted = getByStatus(true).size();
    final long totalUncompleted = getByStatus(false).size();
    return Map.of(true, totalCompleted, false, totalUncompleted);
  }

  @Override
  public List<String> getTitles() {
    return toDos.stream().map(ToDo::title).sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
  }

  private List<ToDo> loadToDos(String url) {
    List<?> list;
    try {
      Object obj = parseJsonFromResource(url);
      list = new ArrayList<>((Collection<?>) obj);
    } catch (IOException | ParseException | InterruptedException e) {
      throw new ToDoException(ToDoService.class, "URL", url);
    }
    return castToListOfToDo(list);
  }

  private Object parseJsonFromResource(String url) throws IOException, InterruptedException, ParseException {
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


  private String loadHttpToDos(String url) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create(url))
               .build();

    HttpResponse<String> response =
               client.send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();
  }

  private List<ToDo> castToListOfToDo(List<?> list) {
    final List<ToDo> toDoList = new ArrayList<>();
    for (Object x : list) {
      Integer id = Integer.valueOf(((LinkedHashMap) x).get("id").toString());
      Integer userId = Integer.valueOf(((LinkedHashMap) x).get("userId").toString());
      String title = ((LinkedHashMap) x).get("title").toString();
      Boolean completed = Boolean.valueOf(((LinkedHashMap) x).get("completed").toString());
      toDoList.add(new ToDo(id, userId, title, completed));
    }
    return toDoList;
  }
}
