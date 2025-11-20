package service;
import model.Student;

import javax.swing.plaf.PanelUI;
import java.util.*;
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    public void addStudent(String name){
        var id = students.size() + 1;
        students.add(new Student(id, name, 0, 0, 0));
    }

    public void deleteStudent(int id){
        students.removeIf(s -> s.id() == id);
    }

    public List<Student> findAll(){
        return students;
    }

    public Optional<Student> findById(int id){
        return students.stream().filter(s -> s.id() == id).findFirst();
    }

    public Student update(Student updated){
        for(int i=0; i<students.size(); i++){
            if(students.get(i).id() == updated.id()){
                students.set(i, updated);
                return updated;
            }
        }
        return updated;
    }

    public Student handleLate(Student st){
        int newLate = st.lateCount()+1;

        if(newLate>=3){
            int newAbsence = st.absenceCount()+1;
            newLate-=3;
            return new Student(st.id(),st.name(), st.attendanceCount(),newAbsence,newLate);
        }
        return st.withLate(newLate);
    }
}
