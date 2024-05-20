package http.project.networks.ii.api.teachers_api;

import java.time.LocalDateTime;

/**
 * The Project class represents a project with various attributes such as name, description,
 * teacher, student, grade, status, and last modified time.
 */
public class Project {
    protected String name;
    protected String description;
    protected String teacher;
    protected String student;
    protected String grade;
    protected String status;
    public String lastModified;

    /**
     * Constructs a Project object with the specified attributes and sets the last modified time to the current time.
     *
     * @param name        the name of the project
     * @param description the description of the project
     * @param teacher     the teacher associated with the project
     * @param student     the student associated with the project
     * @param grade       the grade of the project
     * @param status      the status of the project
     */
    public Project(String name, String description, String teacher, String student, String grade, String status) {
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.student = student;
        this.grade = grade;
        this.status = status;
        lastModifiedReset();
    }

    /**
     * Resets the last modified time to the current time.
     */
    public void lastModifiedReset(){
        this.lastModified = LocalDateTime.now().toString();
    }

    /**
     * Sets the last modified time to a specified value.
     *
     * @param lastModified the new last modified time
     */
    void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Gets the last modified time as a LocalDateTime object.
     *
     * @return the last modified time
     */
    public LocalDateTime getLastModified() {
        return LocalDateTime.parse(this.lastModified);
    }

    /**
     * Gets the name of the project.
     *
     * @return the name of the project
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the project and updates the last modified time.
     *
     * @param name the new name of the project
     */
    public void setName(String name) {
        this.name = name;
        lastModifiedReset();
    }

    /**
     * Gets the description of the project.
     *
     * @return the description of the project
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the project and updates the last modified time.
     *
     * @param description the new description of the project
     */
    public void setDescription(String description) {
        this.description = description;
        lastModifiedReset();
    }

    /**
     * Gets the teacher associated with the project.
     *
     * @return the teacher of the project
     */
    public String getTeacher() {
        return this.teacher;
    }

    /**
     * Sets the teacher associated with the project and updates the last modified time.
     *
     * @param teacher the new teacher of the project
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
        lastModifiedReset();
    }

    /**
     * Gets the student associated with the project.
     *
     * @return the student of the project
     */
    public String getStudent() {
        return this.student;
    }

    /**
     * Sets the student associated with the project and updates the last modified time.
     *
     * @param student the new student of the project
     */
    public void setStudent(String student) {
        this.student = student;
        lastModifiedReset();
    }

    /**
     * Gets the grade of the project.
     *
     * @return the grade of the project
     */
    public String getGrade() {
        return this.grade;
    }

    /**
     * Sets the grade of the project and updates the last modified time.
     *
     * @param grade the new grade of the project
     */
    public void setGrade(String grade) {
        this.grade = grade;
        lastModifiedReset();
    }

    /**
     * Gets the status of the project.
     *
     * @return the status of the project
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the status of the project and updates the last modified time.
     *
     * @param status the new status of the project
     */
    public void setStatus(String status) {
        this.status = status;
        lastModifiedReset();
    }

    /**
     * Returns a string representation of the project.
     *
     * @return a string containing the project's attributes
     */
    @Override
    public String toString() {
        return "Name: " + this.name + " Description: " + this.description + " Teacher: " + this.teacher + " Student: " + this.student + " Grade: " + this.grade + " Status: " + this.status;
    }

    /**
     * Gets the ID of the project, which is the name of the project.
     *
     * @return the ID of the project
     */
    public String getId() {
        return this.name;
    }
}
