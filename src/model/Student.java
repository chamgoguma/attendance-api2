package model;

public record Student (
    int id,
    String name,
    int attendanceCount,
    int absenceCount,
    int lateCount
){
    public Student withAttendance(int newAttendance){
        return new Student(id,name,newAttendance,absenceCount,lateCount);
    }
    public Student withAbsence(int newAbsence){
        return new Student(id, name, attendanceCount, newAbsence,lateCount);
    }
    public Student withLate(int newLate){
        return new Student(id, name, attendanceCount, absenceCount, newLate);
    }
}



