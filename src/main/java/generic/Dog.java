package generic;

public class Dog extends Animal {
    String name;
    Number age;

    public Dog(String name, Number age) {
        super(name, age);
        this.name = name;
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Number getAge() {
        return age;
    }

    @Override
    public void setAge(Number age) {
        this.age = age;
    }
}
