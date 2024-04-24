package http.project.networks.ii;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TeachersClass {
    protected List<Teacher> teachers;
    String path = "TeachersClass.txt";
  
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

    public void updateTeacher(Teacher teacher) {
        teachers.set(teachers.indexOf(teacher), teacher);
        try {
            Files.write(Paths.get(path), "Aquí el texto".getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    public void removeTeacher(String teacher) {
        Teacher teacherToRemove = null;
        for (Teacher t : teachers) {
            if (t.getName().equals(teacher)) {
                teacherToRemove = t;
                break;
            }
        }
        if (teacherToRemove != null) {
            teachers.remove(teacherToRemove);
        }
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
