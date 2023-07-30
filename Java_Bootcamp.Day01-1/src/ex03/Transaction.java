package ex03;

import java.util.UUID;

public class Transaction {

    private UUID id;
    private final User recipient;
    private final User sender;
    private final TransactionCategory category;
    private Integer amount;

    public Transaction(User recipient,
                       User sender,
                       TransactionCategory category,
                       Integer amount) {
        this.id = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        setAmount(amount);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        if (category == TransactionCategory.DEBIT && amount <= 0 ||
                category == TransactionCategory.CREDIT && amount >= 0) {
            System.out.println("Wrong transaction");
            System.exit(-1);
        }
        if (category == TransactionCategory.CREDIT && sender.getBalance() < -amount ||
                category == TransactionCategory.DEBIT && sender.getBalance() < amount) {
            System.out.println(sender.getName() + " has insufficient funds");
            System.exit(-2);
        }
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", recipient=" + recipient +
                ", sender=" + sender +
                ", category=" + category +
                ", amount=" + amount +
                '}';
    }
}
