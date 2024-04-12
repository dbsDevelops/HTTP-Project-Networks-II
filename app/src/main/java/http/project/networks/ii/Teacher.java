package http.project.networks.ii;

public class Teacher {
    public String name;
    public float passRate;

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

    public boolean equals(Teacher teacher) {
        return this.name.equals(teacher.getName()) && this.passRate == teacher.getPassRate();
    }

    public boolean equals(String name, float passRate) {
        return this.name.equals(name) && this.passRate == passRate;
    }
    
}