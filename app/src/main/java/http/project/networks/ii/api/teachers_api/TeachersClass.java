package http.project.networks.ii.api.teachers_api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The TeachersClass represents a collection of teachers and projects with methods to manage them.
 */
public class TeachersClass {
    protected List<Teacher> teachers;
    protected List<Project> projects;
    protected String path = "TeachersClass.txt";
    public String lastModified;

    /**
     * Constructs a TeachersClass with the specified lists of teachers and projects.
     *
     * @param teachers the list of teachers
     * @param projects the list of projects
     */
    public TeachersClass(List<Teacher> teachers, List<Project> projects) {
        this.teachers = teachers;
        this.projects = projects;
        lastModifiedReset();
    }

    /**
     * Constructs an empty TeachersClass.
     */
    public TeachersClass() {
        teachers = new java.util.ArrayList<>();
        projects = new java.util.ArrayList<>();
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
    public void setLastModified(String lastModified) {
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
     * Gets the list of teachers.
     *
     * @return the list of teachers
     */
    public List<Teacher> getTeachers() {
        return this.teachers;
    }

    /**
     * Sets the list of teachers and updates the last modified time.
     *
     * @param teachers the new list of teachers
     */
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
        lastModifiedReset();
    }

    /**
     * Gets the list of projects.
     *
     * @return the list of projects
     */
    public List<Project> getProjects() {
        return this.projects;
    }

    /**
     * Sets the list of projects and updates the last modified time.
     *
     * @param projects the new list of projects
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
        lastModifiedReset();
    }

    /**
     * Adds a teacher to the list and updates the last modified time.
     *
     * @param teacher the teacher to add
     */
    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        lastModifiedReset();
    }

    /**
     * Adds a project to the list and updates the last modified time.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        this.projects.add(project);
        lastModifiedReset();
    }

    /**
     * Updates a teacher in the list and updates the last modified time.
     *
     * @param teacher the teacher to update
     * @return true if the teacher was updated, false otherwise
     */
    public boolean updateTeacher(Teacher teacher) {
        for (Teacher t : teachers) {
            if (t.getName().equals(teacher.getName())) {
                teachers.set(teachers.indexOf(t), teacher);
                lastModifiedReset();
                return true;
            }
        }
        return false;
    }

    /**
     * Updates a project in the list and updates the last modified time.
     *
     * @param project the project to update
     * @return true if the project was updated, false otherwise
     */
    public boolean updateProject(Project project) {
        for (Project p : projects) {
            if (p.getName().equals(project.getName())) {
                projects.set(projects.indexOf(p), project);
                lastModifiedReset();
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a teacher from the list by name and updates the last modified time.
     *
     * @param teacherName the name of the teacher to remove
     * @return true if the teacher was removed, false otherwise
     */
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
            lastModifiedReset();
            return true;
        }
        return false;
    }

    /**
     * Removes a project from the list by name and updates the last modified time.
     *
     * @param projectName the name of the project to remove
     * @return true if the project was removed, false otherwise
     */
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
            lastModifiedReset();
            return true;
        }
        return false;
    }

    /**
     * Gets a teacher by name.
     *
     * @param name the name of the teacher
     * @return the teacher with the specified name, or null if not found
     */
    public Teacher getTeacher(String name) {
        for (Teacher teacher : this.teachers) {
            if (teacher.getName().equals(name)) {
                return teacher;
            }
        }
        return null;
    }

    /**
     * Gets the count of teachers.
     *
     * @return the number of teachers
     */
    public int getTeacherCount() {
        return this.teachers.size();
    }

    /**
     * Gets a project by name.
     *
     * @param name the name of the project
     * @return the project with the specified name, or null if not found
     */
    public Project getProject(String name) {
        for (Project project : this.projects) {
            if (project.getName().equals(name)) {
                return project;
            }
        }
        return null;
    }

    /**
     * Gets the count of projects.
     *
     * @return the number of projects
     */
    public int getProjectCount() {
        return this.projects.size();
    }

    /**
     * Clears all teachers and projects, and updates the last modified time.
     */
    public void clear() {
        this.teachers.clear();
        this.projects.clear();
        lastModifiedReset();
    }

    /**
     * Clears all teachers and updates the last modified time.
     */
    public void clearTeachers() {
        this.teachers.clear();
        lastModifiedReset();
    }

    /**
     * Clears all projects and updates the last modified time.
     */
    public void clearProjects() {
        this.projects.clear();
        lastModifiedReset();
    }

    /**
     * Returns a string representation of the teachers and projects.
     *
     * @return a string containing the teachers and projects
     */
    @Override
    public String toString() {
        return "Teachers: " + this.teachers.toString() + "\nProjects: " + this.projects.toString();
    }

    /**
     * Checks if this TeachersClass is equal to another TeachersClass.
     *
     * @param teachersClass the TeachersClass to compare
     * @return true if the teachers and projects are equal, false otherwise
     */
    public boolean equals(TeachersClass teachersClass) {
        return this.teachers.equals(teachersClass.getTeachers()) && this.projects.equals(teachersClass.getProjects());
    }

    /**
     * Checks if this TeachersClass is equal to the specified lists of teachers and projects.
     *
     * @param teachers the list of teachers to compare
     * @param projects the list of projects to compare
     * @return true if the teachers and projects are equal, false otherwise
     */
    public boolean equals(List<Teacher> teachers, List<Project> projects) {
        return this.teachers.equals(teachers) && this.projects.equals(projects);
    }
}
