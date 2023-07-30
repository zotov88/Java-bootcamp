package ex03;

import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        Transaction one = new Transaction(
                new User("Pavel", 400),
                new User("Sergey", 100),
                TransactionCategory.CREDIT,
                -50
                );
        Transaction two = new Transaction(
                new User("Sergey", 400),
                new User("Pavel", 100),
                TransactionCategory.DEBIT,
                70
        );
        Transaction three = new Transaction(
                new User("Sergey", 400),
                new User("Pavel", 100),
                TransactionCategory.CREDIT,
                -70
        );

        System.out.println(two.getId());

        TransactionsLinkedList list = new TransactionsLinkedList();
        list.add(one);
        list.add(two);
        list.add(three);

        list.deleteById(one.getId());

        System.out.println(list);
        System.out.println(list.size());

        Transaction[] transactions = list.toArray();
        System.out.println(Arrays.toString(transactions));
    }
}
