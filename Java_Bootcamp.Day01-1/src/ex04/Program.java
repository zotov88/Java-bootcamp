package ex04;

import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        TransactionsService transactionsService = new TransactionsService();
        User one = new User("Ivan", 1000);
        User two = new User("Oleg", 100);
        System.out.println(one);
        System.out.println(two);
        System.out.println("-----------------------------------------------------------------------");

        transactionsService.addUser(one);
        transactionsService.addUser(two);
        transactionsService.startTransaction(one.getId(), two.getId(), 100);
        System.out.println(transactionsService.getBalance(one.getId()));
        System.out.println(transactionsService.getBalance(two.getId()));

        System.out.println(Arrays.toString(one.getTransactionsList().toArray()));
        System.out.println(Arrays.toString(two.getTransactionsList().toArray()));
        System.out.println("-----------------------------------------------------------------------");

        transactionsService.startTransaction(two.getId(), one.getId(), 150);
        System.out.println(transactionsService.getBalance(one.getId()));
        System.out.println(transactionsService.getBalance(two.getId()));
        System.out.println(Arrays.toString(one.getTransactionsList().toArray()));
        System.out.println(Arrays.toString(two.getTransactionsList().toArray()));

        System.out.println("-----------------------------------------------------------------------");
        System.out.println(Arrays.toString(transactionsService.checkValidityOfTransactions()));
        Transaction[] transactions = one.getTransactionsList().toArray();
        System.out.println(one.getTransactionsList().size());
        transactionsService.deleteTransaction(transactions[0].getId(), one.getId());
        System.out.println(one.getTransactionsList().size());
        System.out.println(Arrays.toString(transactionsService.checkValidityOfTransactions()));
    }
}
