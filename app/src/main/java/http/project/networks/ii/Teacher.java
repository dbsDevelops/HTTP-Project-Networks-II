package http.project.networks.ii;

import java.util.Objects;

public class Teacher {
    protected String name;
    protected float passRate;

    public Teacher(String name, float passRate) {
        this.name = name;
        this.passRate = passRate;
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

    public String toString() {
        return this.name + " " + this.passRate;
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

    public Object getId() {
        return this.name;
    }
}