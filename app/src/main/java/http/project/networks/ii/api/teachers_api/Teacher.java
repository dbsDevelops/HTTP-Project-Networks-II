package http.project.networks.ii.api.teachers_api;

import java.time.LocalDateTime;
import java.util.Objects;

public class Teacher {
    protected String name;
    protected float passRate;
    protected Project project;
    private LocalDateTime lastModified;


    public Teacher(String name, float passRate, Project project) {
        this.name = name;
        this.passRate = passRate;
        this.project = project;
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

    public float getPassRate() {
        return this.passRate;
    }

    public void setPassRate(float passRate) {
        this.passRate = passRate;
        this.lastModified = LocalDateTime.now();
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
        this.lastModified = LocalDateTime.now();
    }

    public String toString() {
        String projectName = this.project == null ? "null" : this.project.getName();
        return "Name: " + this.name + " PassRate: " + this.passRate + " Project Assigned: " + projectName;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(name, passRate);
    }

    public boolean equals(String name, float passRate) {
        return this.name.equals(name) && this.passRate == passRate;
    }

    public String getId() {
        return this.name;
    }
}