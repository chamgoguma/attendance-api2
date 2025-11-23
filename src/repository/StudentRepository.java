package repository;
import model.Student;

import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StudentRepository {
    private static final String FILE_NAME = "students.json";
    private final Gson gson = new Gson();

    //save
    public void save(List<Student> students) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(students, writer);
        } catch (IOException e) {
            System.out.println("파일저장오류" + e.getMessage());
        }
    }

    //load
    public List<Student> load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_NAME)) {
            List<Student> list =gson.fromJson(reader, new TypeToken<List<Student>>() {
            }.getType());
            return (list !=null)?list : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("파일 읽기 오류" + e.getMessage());
            return new ArrayList<>();
        }
    }
}