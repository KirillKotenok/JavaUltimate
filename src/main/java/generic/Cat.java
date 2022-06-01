package generic;

public class Cat extends Animal {
    String name;
    Number age;

    public Cat(String name, Number age) {
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

