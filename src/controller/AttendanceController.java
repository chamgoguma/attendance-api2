package controller;

import model.Student;
import service.StudentService;

import java.util.Scanner;

public class AttendanceController {

    private final StudentService service = new StudentService();
    private final Scanner sc = new Scanner(System.in);

    public void start(){
        while(true){
            System.out.println("\n=== 출석관리 ===");
            System.out.println("1. 학생추가");
            System.out.println("2. 학생삭제");
            System.out.println("3. 전체학생조회");
            System.out.println("4. 출석체크");
            System.out.println("0. 종료");
            System.out.print("번호 선택: ");

            int menu = readInt("번호선택");

            switch(menu){
                case 1 -> addStudent();
                case 2 -> deleteStudent();
                case 3 -> printAll();
                case 4 -> attendance();
                case 0 -> {
                    System.out.println("프로그램 종료");
                    return;
                }
                default -> System.out.println("잘못된 입력");
            }
        }
    }

    private void addStudent(){
        System.out.print("학생 이름: ");
        String name = sc.next().trim();
        if (name.isEmpty()){
            System.out.println("이름이 비어있을 수 없음");
            return;
        }
        service.addStudent(name);
        System.out.println("학생 등록 완료");
    }

    private void deleteStudent(){
        int id = readInt("삭제할 학생 ID");
        service.deleteStudent(id);
        System.out.println("삭제 완료");
    }

    private void printAll(){
        System.out.println("\nID | 이름       | 출석 | 결석 | 지각");
        System.out.println("---------------------------------------");

        service.findAll().forEach(st -> {
            System.out.printf("%2d | %-8s |  %3d |  %3d |  %3d%n",
                    st.id(),
                    st.name(),
                    st.attendanceCount(),
                    st.absenceCount(),
                    st.lateCount()
            );
        });
    }

    private void attendance(){
        int id = readInt("출석 체크할 학생 id");

        var opt = service.findById(id);
        if(opt.isEmpty()){
            System.out.println("해당 학생 없음");
            return;
        }

        var st = opt.get();

        System.out.println("1. 출석  2. 결석  3. 지각");
        int input = readInt("선택");

        Student updated = switch(input){
            case 1 -> st.withAttendance(st.attendanceCount() + 1);
            case 2 -> st.withAbsence(st.absenceCount() + 1);
            case 3 -> service.handleLate(st);
            default -> {
                System.out.println("잘못된 입력");
                yield st;
            }
        };

        service.update(updated);
        System.out.println("출석 처리 완료");
    }

    private int readInt(String message){
        while(true){
            System.out.println(message);

            try{
                return Integer.parseInt(sc.next());
            }catch (NumberFormatException e){
                System.out.println("숫자로 입력");
            }
        }
    }

}

