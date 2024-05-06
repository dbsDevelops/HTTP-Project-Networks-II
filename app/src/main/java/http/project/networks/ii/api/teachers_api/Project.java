package http.project.networks.ii.api.teachers_api;

import java.time.LocalDateTime;

public class Project {
    protected String name;
    protected String description;
    protected String teacher;
    protected String student;
    protected String grade;
    protected String status;
    public String lastModified;

    public Project(String name, String description, String teacher, String student, String grade, String status) {
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.student = student;
        this.grade = grade;
        this.status = status;
        lastModifiedReset();
    }

    public void lastModifiedReset(){
        this.lastModified = LocalDateTime.now().toString();
    }

    void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public LocalDateTime getLastModified() {
        LocalDateTime lastModified = LocalDateTime.parse(this.lastModified);
        return lastModified;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        lastModifiedReset();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
        lastModifiedReset();
    }

    public String getTeacher() {
        return this.teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
        lastModifiedReset();
    }

    public String getStudent() {
        return this.student;
    }

    public void setStudent(String student) {
        this.student = student;
        lastModifiedReset();
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        lastModifiedReset();
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
        lastModifiedReset();
    }

    public String toString() {
        return "Name: " + this.name + " Description: " + this.description + " Teacher: " + this.teacher + " Student: " + this.student + " Grade: " + this.grade + " Status: " + this.status;
    }

    public String getId() {
        return this.name;
    }
    
}
