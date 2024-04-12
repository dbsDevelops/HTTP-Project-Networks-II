package http.project.networks.ii;

import java.util.List;

public class TeachersClass {
    public List<Teacher> teachers;

    public TeachersClass(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public TeachersClass() {
        teachers = new java.util.ArrayList<Teacher>();
    }

    public List<Teacher> getTeachers() {
        return this.teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
    }

    public void removeTeacher(int index) {
        this.teachers.remove(index);
    }

    public Teacher getTeacher(int index) {
        return this.teachers.get(index);
    }

    public int getTeacherCount() {
        return this.teachers.size();
    }

    public String toString() {
        return this.teachers.toString();
    }

    public boolean equals(TeachersClass teachersClass) {
        return this.teachers.equals(teachersClass.getTeachers());
    }

    public boolean equals(List<Teacher> teachers) {
        return this.teachers.equals(teachers);
    }

    



    
}
