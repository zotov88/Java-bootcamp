package ex02;

public class Program {
    public static void main(String[] args) {
        User one = new User("Pavel", 1000);
        User two = new User("Semen", 0);
        User three = new User("Anna", 666);
        User four = new User("Anton", 777);
        User five = new User("Pavel", 888);
        User six = new User("Ola", 1);
        User seven = new User("Victor", 1212);
        User eight = new User("Alex", 21);
        User nine = new User("Artem", 90);
        User ten = new User("Bob", 10000);

        UsersArrayList list = new UsersArrayList();
        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);
        list.add(five);
        list.add(six);
        list.add(seven);
        list.add(eight);
        list.add(nine);
        list.add(ten);

        System.out.println(list);

        System.out.println(list.getByIndex(5));
        System.out.println(list.getById(10));

        list.add(new User("Vova", 200));

        System.out.println(list);

    }
}
