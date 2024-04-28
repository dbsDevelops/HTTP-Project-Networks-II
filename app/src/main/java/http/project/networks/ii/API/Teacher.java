package http.project.networks.ii.API;

import java.util.Objects;

public class Teacher {
    protected String name;
    protected float passRate;
    protected Project project;

    public Teacher(String name, float passRate, Project project) {
        this.name = name;
        this.passRate = passRate;
        this.project = project;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPassRate() {
        return this.passRate;
    }

    public void setPassRate(float passRate) {
        this.passRate = passRate;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
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