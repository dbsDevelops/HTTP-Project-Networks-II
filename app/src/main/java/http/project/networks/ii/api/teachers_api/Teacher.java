package http.project.networks.ii.api.teachers_api;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Teacher class represents a teacher with attributes such as name, pass rate, assigned project, 
 * and last modified time.
 */
public class Teacher {
    protected String name;
    protected float passRate;
    protected Project project;
    public String lastModified;

    /**
     * Constructs a Teacher object with the specified attributes and sets the last modified time to the current time.
     *
     * @param name      the name of the teacher
     * @param passRate  the pass rate of the teacher
     * @param project   the project assigned to the teacher
     */
    public Teacher(String name, float passRate, Project project) {
        this.name = name;
        this.passRate = passRate;
        this.project = project;
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
     * Gets the name of the teacher.
     *
     * @return the name of the teacher
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the teacher and updates the last modified time.
     *
     * @param name the new name of the teacher
     */
    public void setName(String name) {
        this.name = name;
        lastModifiedReset();
    }

    /**
     * Gets the pass rate of the teacher.
     *
     * @return the pass rate of the teacher
     */
    public float getPassRate() {
        return this.passRate;
    }

    /**
     * Sets the pass rate of the teacher and updates the last modified time.
     *
     * @param passRate the new pass rate of the teacher
     */
    public void setPassRate(float passRate) {
        this.passRate = passRate;
        lastModifiedReset();
    }

    /**
     * Gets the project assigned to the teacher.
     *
     * @return the project assigned to the teacher
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * Sets the project assigned to the teacher and updates the last modified time.
     *
     * @param project the new project assigned to the teacher
     */
    public void setProject(Project project) {
        this.project = project;
        lastModifiedReset();
    }

    /**
     * Returns a string representation of the teacher.
     *
     * @return a string containing the teacher's attributes
     */
    @Override
    public String toString() {
        String projectName = this.project == null ? "null" : this.project.getName();
        return "Name: " + this.name + " PassRate: " + this.passRate + " Project Assigned: " + projectName;
    }

    /**
     * Checks if this teacher is equal to another object.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override 
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Teacher)) {
            return false;
        }
        Teacher teacher = (Teacher) obj;
        return this.name.equals(teacher.getName()) && this.passRate == teacher.getPassRate();
    }

    /**
     * Returns a hash code value for the teacher.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, passRate);
    }

    /**
     * Checks if this teacher is equal to another teacher by name and pass rate.
     *
     * @param name     the name to compare
     * @param passRate the pass rate to compare
     * @return true if the name and pass rate are equal, false otherwise
     */
    public boolean equals(String name, float passRate) {
        return this.name.equals(name) && this.passRate == passRate;
    }

    /**
     * Gets the ID of the teacher, which is the name of the teacher.
     *
     * @return the ID of the teacher
     */
    public String getId() {
        return this.name;
    }
}
