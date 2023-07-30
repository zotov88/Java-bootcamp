package ex00;

public class Program {
    public static void main(String[] args) {
        User sender = new User( 1,"Petr", 100);
        User recipient = new User(2, "Elena", -100);
        System.out.println(sender);
        System.out.println(recipient);
        Transaction transaction = new Transaction(recipient, sender, TransactionCategory.CREDIT, -1);
        System.out.println(transaction);
    }
}
