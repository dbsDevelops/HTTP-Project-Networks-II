package http.project.networks.ii.api.teachers_api;

import java.time.LocalDateTime;

public class Project {
    protected String name;
    protected String description;
    protected String teacher;
    protected String student;
    protected String grade;
    protected String status;
    private LocalDateTime lastModified;

    public Project(String name, String description, String teacher, String student, String grade, String status) {
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.student = student;
        this.grade = grade;
        this.status = status;
        this.lastModified = LocalDateTime.now();
    }

    void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public LocalDateTime getLastModified() {
        return this.lastModified;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        this.lastModified = LocalDateTime.now();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.lastModified = LocalDateTime.now();
    }

    public String getTeacher() {
        return this.teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
        this.lastModified = LocalDateTime.now();
    }

    public String getStudent() {
        return this.student;
    }

    public void setStudent(String student) {
        this.student = student;
        this.lastModified = LocalDateTime.now();
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        this.lastModified = LocalDateTime.now();
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.lastModified = LocalDateTime.now();
    }

    public String toString() {
        return "Name: " + this.name + " Description: " + this.description + " Teacher: " + this.teacher + " Student: " + this.student + " Grade: " + this.grade + " Status: " + this.status;
    }

    public String getId() {
        return this.name;
    }
    
}
