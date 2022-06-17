package fundamentals.generic;

public class Animal {
    String name;
    Number age;

    public Animal(String name, Number age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getAge() {
        return age;
    }

    public void setAge(Number age) {
        this.age = age;
    }
}
