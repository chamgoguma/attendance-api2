import java.lang.invoke.VarHandle;
import java.util.*;

public class AttendanceApp {
    public static void main(String[] args) {

        var sc = new Scanner(System.in);
        var manager = new StudentManager();

        while(true){
            System.out.println("/n===출석관리====");
            System.out.println("1. 학생추가");
            System.out.println("2. 학생삭제");
            System.out.println("3. 전체학생조회");
            System.out.println("4. 출석체크");
            System.out.println("0. 종료");
            System.out.println("번호 선택");
            var menu = sc.nextInt();

            switch (menu){
              case 1-> manager.addStudent(sc);
              case 2-> manager.deleteStudent(sc);
              case 3-> manager.printAll();
              case 4-> manager.attendance(sc);
              case 0-> {
                  System.out.println("프로그램 종료");
                  return;
              }
              default->
                  System.out.println("잘못된 입력");
            }
        }
    }
}

class StudentManager{
    private final List<Student> students = new ArrayList<>();

    void addStudent(Scanner sc){
        System.out.println("학생이름");
        var name = sc.next();
        var id = students.size()+1;

        students.add(new Student(id, name,0,0,0));
        System.out.println(("학생 등록"));
    }

    void deleteStudent(Scanner sc){
        System.out.println("삭제할 학생 id");
        var id = sc.nextInt();

        students.removeIf(s->s.id() == id);
        System.out.println("삭제 완료");
    }

    void printAll(){
        System.out.println("/n=== 학생목록 ===");
        students.forEach(System.out::println);
    }

    void attendance(Scanner sc){
        System.out.println("출석 체크할 학생 id");
        var id =sc.nextInt();

        var studentOpt = students.stream()
                .filter(s->s.id() == id)
                .findFirst();
        if (studentOpt.isEmpty()){
            System.out.println("해당 학생 없음");
            return;
        }

        var st = studentOpt.get();

        System.out.println("1 출석  2 결석 3지각");
        System.out.println("선택");
        var opt = sc.nextInt();

        var updated = switch (opt){
            case 1 -> st.withAttendance(st.attendanceCount()+1);
            case 2 -> st.withAbsence(st.absenceCount()+1);
            case 3 -> st.withLate(st.lateCount()+1);
            default -> {
                System.out.println("잘못된 입력");
                yield st;
            }
        };

        replaceStudnet(updated);
        System.out.println("처리완료");
    }
    private void replaceStudnet(Student updated){
        for (var i =0; i< students.size();i++){
            if(students.get(i).id()==updated.id()){
                students.set(i,updated);
                return;
            }
        }
    }
}

record Student(
        int id,
        String name,
        int attendanceCount,
        int absenceCount,
        int lateCount
){
    Student withAttendance(int newAttendance){
        return new Student(id, name, newAttendance, absenceCount,lateCount);
    }
    Student withAbsence(int newAbsence){
        return new Student(id, name, attendanceCount, newAbsence,lateCount);
    }
    Student withLate(int newLate){
        return new Student(id, name, attendanceCount, absenceCount,newLate);
    }
}