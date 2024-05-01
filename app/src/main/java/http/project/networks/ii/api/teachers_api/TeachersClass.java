package http.project.networks.ii.api.teachers_api;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;

public class TeachersClass {
    protected List<Teacher> teachers;
    protected List<Project> projects;
    String path = "TeachersClass.txt";
    private LocalDateTime lastModified;


    public TeachersClass(List<Teacher> teachers, List<Project> projects) {
        this.teachers = teachers;
        this.projects = projects;
    }

    public TeachersClass() {
        teachers = new java.util.ArrayList<Teacher>();
        projects = new java.util.ArrayList<Project>();
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public LocalDateTime getLastModified() {
        return this.lastModified;
    }

    public List<Teacher> getTeachers() {
        return this.teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
        this.lastModified = LocalDateTime.now();
    }

    public List<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
        this.lastModified = LocalDateTime.now();
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        this.lastModified = LocalDateTime.now();
    }

    public void addProject(Project project) {
        this.projects.add(project);
        this.lastModified = LocalDateTime.now();
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
                this.lastModified = LocalDateTime.now();
                return true;
            }
        }
        return false;
    }

    public boolean updateProject(Project project) {
        for (Project p : projects) {
            if (p.getName().equals(project.getName())) {
                projects.set(projects.indexOf(p), project);
                this.lastModified = LocalDateTime.now();
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
            this.lastModified = LocalDateTime.now();
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
            this.lastModified = LocalDateTime.now();
            return true;
        }
        return false;
    }

    // Unchanged methods
    public Teacher getTeacher(String name) {
        for (Teacher teacher : this.teachers) {
            if (teacher.getName().equals(name)) {
                return teacher;
            }
        }
        return null;
    }

    public int getTeacherCount() {
        return this.teachers.size();
    }

    public Project getProject(String name) {
        for (Project project : this.projects) {
            if (project.getName().equals(name)) {
                return project;
            }
        }
        return null;
    }

    public int getProjectCount() {
        return this.projects.size();
    }

    public void clear() {
        this.teachers.clear();
        this.projects.clear();
        this.lastModified = LocalDateTime.now();
    }

    public void clearTeachers() {
        this.teachers.clear();
        this.lastModified = LocalDateTime.now();
    }

    public void clearProjects() {
        this.projects.clear();
        this.lastModified = LocalDateTime.now();
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
