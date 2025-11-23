package service;
import model.Student;
import repository.StudentRepository;

import java.util.*;
public class StudentService {

    private List<Student> students = new ArrayList<>();
    private final StudentRepository repo = new StudentRepository();

    public StudentService() {
        students = repo.load();
    }

    public void addStudent(String name) {
        var id = students.size() + 1;
        students.add(new Student(id, name, 0, 0, 0));
        repo.save(students);
    }

    public void deleteStudent(int id) {
        boolean removed = students.removeIf(s -> s.id() == id);
        if(removed){
            repo.save(students);
        }else{
            System.out.println("삭제할 학생이 없음");
        }
    }

    public List<Student> findAll() {
        return students;
    }

    public Optional<Student> findById(int id) {
        return students.stream().filter(s -> s.id() == id).findFirst();
    }

    public Student update(Student updated) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).id() == updated.id()) {
                students.set(i, updated);
                repo.save(students);
                return updated;
            }
        }
        return updated;

    }

    public Student handleLate(Student st) {
        int newLate = st.lateCount() + 1;

        if (newLate >= 3) {
            int newAbsence = st.absenceCount() + 1;
            newLate -= 3;
            return new Student(st.id(), st.name(), st.attendanceCount(), newAbsence, newLate);
        }
        return st.withLate(newLate);
    }
}
