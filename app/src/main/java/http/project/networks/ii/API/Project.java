package http.project.networks.ii.API;

public class Project {
    protected String name;
    protected String description;
    protected String teacher;
    protected String student;
    protected String grade;
    protected String status;

    public Project(String name, String description, String teacher, String student, String grade, String status) {
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.student = student;
        this.grade = grade;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacher() {
        return this.teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStudent() {
        return this.student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "Name: " + this.name + " Description: " + this.description + " Teacher: " + this.teacher + " Student: " + this.student + " Grade: " + this.grade + " Status: " + this.status;
    }

    public String getId() {
        return this.name;
    }
    
}
