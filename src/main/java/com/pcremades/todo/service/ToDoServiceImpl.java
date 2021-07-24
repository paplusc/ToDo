package com.pcremades.todo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.pcremades.todo.ToDo;

@Service
@Profile("!dev")
public class ToDoServiceImpl implements ToDoService {

  private List<ToDo> todos;

  @Autowired
  public ToDoServiceImpl(@Value("${todo.data:https://jsonplaceholder.typicode.com/todos}") String url) throws URISyntaxException {
    File reader = getFileFromResource(url);
    System.out.println(reader);
//    try
//    {
//      Object obj = new JSONParser(reader);
//      JSONArray employeeList = (JSONArray) obj;
//      System.out.println(employeeList);
//
//      //Iterate over employee array
//      //employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );
//
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }
  private File getFileFromResource(String fileName) throws URISyntaxException {

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {

      // failed if files have whitespaces or special characters
      //return new File(resource.getFile());

      return new File(resource.toURI());
    }

  }
  @Override
  public ToDo create(ToDo item) {
    return null;
  }

  @Override
  public void delete(Integer id) {

  }

  @Override
  public Collection<ToDo> getAll() {
    return null;
  }

  @Override
  public Collection<ToDo> getByStatus(boolean isCompleted) {
    return null;
  }

  @Override
  public Collection<ToDo> getByUserId(int userId) {
    return null;
  }

  @Override
  public Map<Boolean, Long> getStats() {
    return null;
  }

  @Override
  public List<String> getTitles() {
    return null;
  }
}
