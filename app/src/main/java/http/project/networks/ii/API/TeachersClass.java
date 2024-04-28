package http.project.networks.ii.API;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TeachersClass {
    protected List<Teacher> teachers;
    protected List<Project> projects;
    String path = "TeachersClass.txt";

    public TeachersClass(List<Teacher> teachers, List<Project> projects) {
        this.teachers = teachers;
        this.projects = projects;
    }

    public TeachersClass() {
        teachers = new java.util.ArrayList<Teacher>();
        projects = new java.util.ArrayList<Project>();
    }

    public List<Teacher> getTeachers() {
        return this.teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public boolean updateTeacher(Teacher teacher) {
        for (Teacher t : teachers) {
            if (t.getName().equals(teacher.getName())) {
                teachers.set(teachers.indexOf(t), teacher);
                try {
                    Files.write(Paths.get(path), "Aquí el texto".getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    public boolean updateProject(Project project) {
        for (Project p : projects) {
            if (p.getName().equals(project.getName())) {
                projects.set(projects.indexOf(p), project);
                return true;
            }
        }
        return false;
    }

    public boolean removeTeacher(String teacherName) {
        Teacher teacherToRemove = null;
        for (Teacher t : teachers) {
            if (t.getName().equals(teacherName)) {
                teacherToRemove = t;
                break;
            }
        }
        if (teacherToRemove != null) {
            teachers.remove(teacherToRemove);
            return true;
        }
        return false;
    }

    public boolean removeProject(String projectName) {
        Project projectToRemove = null;
        for (Project p : projects) {
            if (p.getName().equals(projectName)) {
                projectToRemove = p;
                break;
            }
        }
        if (projectToRemove != null) {
            projects.remove(projectToRemove);
            return true;
        }
        return false;
    }

    // Unchanged methods
    public Teacher getTeacher(int index) {
        return this.teachers.get(index);
    }

    public int getTeacherCount() {
        return this.teachers.size();
    }

    public Project getProject(int index) {
        return this.projects.get(index);
    }

    public int getProjectCount() {
        return this.projects.size();
    }

    public String toString() {
        return "Teachers: " + this.teachers.toString() + "\nProjects: " + this.projects.toString();
    }

    public boolean equals(TeachersClass teachersClass) {
        return this.teachers.equals(teachersClass.getTeachers()) && this.projects.equals(teachersClass.getProjects());
    }

    public boolean equals(List<Teacher> teachers, List<Project> projects) {
        return this.teachers.equals(teachers) && this.projects.equals(projects);
    }

}
