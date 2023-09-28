package classes;

import java.time.LocalDate;

public class Cat {

    private String name;
    private Integer age;
    private Boolean isSleep;

    public Cat() {
    }

    public Cat(String name, Integer age, Boolean isSleep) {
        this.name = name;
        this.age = age;
        this.isSleep = isSleep;
    }

    public int getYearOfBirth() {
        LocalDate date = LocalDate.now();
        return date.getYear() - age;
    }

    public boolean toWake() {
        if (isSleep) {
            isSleep = false;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isSleep=" + isSleep +
                '}';
    }
}


