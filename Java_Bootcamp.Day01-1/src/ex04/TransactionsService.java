package ex04;

import java.util.UUID;

public class TransactionsService {

    private final UsersArrayList usersList;

    public TransactionsService() {
        usersList = new UsersArrayList();
    }

    public void addUser(User user) {
        usersList.add(user);
    }

    public int getBalance(User user) {
        return getBalance(user.getId());
    }

    public int getBalance(int id) {
        return usersList.getById(id).getBalance();
    }

    public void startTransaction(int senderId, int recipientId, int sum) {
        User sender = usersList.getById(senderId);
        User recipient = usersList.getById(recipientId);
        Transaction transactionD = new Transaction(recipient, sender, TransactionCategory.DEBIT, sum);
        Transaction transactionC = new Transaction(recipient, sender, TransactionCategory.CREDIT, -sum);
        transactionC.setId(transactionD.getId());
        recipient.getTransactionsList().add(transactionD);
        sender.getTransactionsList().add(transactionC);
        recipient.setBalance(recipient.getBalance() + sum);
        sender.setBalance(sender.getBalance() - sum);
    }

    public Transaction[] getUserTransactions(User user) {
        return usersList.getById(user.getId()).getTransactionsList().toArray();
    }

    public void deleteTransaction(UUID transactionId, int userId) {
        usersList.getById(userId).getTransactionsList().deleteById(transactionId);
    }

    private Transaction[] getAllTransactions() {
        TransactionsLinkedList transactionsList = new TransactionsLinkedList();
        for (int i = 0; i < usersList.size(); i++) {
            User user = usersList.getByIndex(i);
            for (int j = 0; j < user.getTransactionsList().size(); j++) {
                transactionsList.add(user.getTransactionsList().toArray()[j]);
            }
        }
        return transactionsList.toArray();
    }

    public Transaction[] checkValidityOfTransactions() {
        Transaction[] transactions = getAllTransactions();
        TransactionsList invalidTransactions = new TransactionsLinkedList();
        for (Transaction transaction1 : transactions) {
            int count = 0;
            for (Transaction transaction2 : transactions) {
                if (transaction1.getId().equals(transaction2.getId())) {
                    count++;
                }
            }
            if (count < 2) {
                invalidTransactions.add(transaction1);
            }
        }
        return invalidTransactions.toArray();
    }
}
