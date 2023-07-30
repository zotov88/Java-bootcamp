package ex05;

import java.util.Scanner;
import java.util.UUID;

public class Menu {

    private final TransactionsService service;
    private final Scanner sc;

    public Menu() {
        this.service = new TransactionsService();
        this.sc = new Scanner(System.in);
    }

    public void app(String profile) {
        while (true) {
            if (profile.equals("--profile=dev")) {
                menuDev();
                handlerDev();
            } else if (profile.endsWith("--profile=std")) {
                menu();
                handler();
            } else {
                System.err.println("Illegal argument");
                System.exit(-1);
            }
            System.out.println("---------------------------------------------------------");
        }
    }

    private void menu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. Finish execution");
    }

    private void menuDev() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. DEV - remove a transfer by ID ");
        System.out.println("6. DEV - check transfer validity");
        System.out.println("7. Finish execution");
    }

    private void handler() {
        try {
            int input = Integer.parseInt(sc.nextLine());
            switch (input) {
                case 1:
                    addUser();
                    break;
                case 2:
                    getBalance();
                    break;
                case 3:
                    performTransfer();
                    break;
                case 4:
                    viewAllUserTransactions();
                    break;
                case 5:
                    System.exit(0);
            }
        } catch (NumberFormatException e) {
            System.err.println("Illegal argument");
        }
    }

    private void handlerDev() {
        try {
            int input = Integer.parseInt(sc.nextLine());
            switch (input) {
                case 1:
                    addUser();
                    break;
                case 2:
                    getBalance();
                    break;
                case 3:
                    performTransfer();
                    break;
                case 4:
                    viewAllUserTransactions();
                    break;
                case 5:
                    deleteTransferById();
                    break;
                case 6:
                    checkTransfers();
                    break;
                case 7:
                    System.exit(0);
            }
        } catch (NumberFormatException e) {
            System.err.println("Illegal argument");
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        try {
            String[] data = sc.nextLine().split(" ");
            if (data.length != 2) {
                throw new IllegalArgumentException();
            }
            int sum = Integer.parseInt(data[1]);
            service.addUser(new User(data[0], sum));
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument");
        }
    }

    private void getBalance() {
        System.out.println("Enter a user ID");
        try {
            int id = Integer.parseInt(sc.nextLine());
            System.out.println(
                    service.getUsersList().getById(id).getName() + " - " +
                            service.getUsersList().getById(id).getBalance());
        } catch (NumberFormatException e) {
            System.err.println("Illegal argument");
        }
    }

    private void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String[] data = sc.nextLine().split(" ");
        try {
            if (data.length != 3) {
                throw new IllegalArgumentException();
            }
            int senderId = Integer.parseInt(data[0]);
            int recipientId = Integer.parseInt(data[1]);
            int amount = Integer.parseInt(data[2]);
            service.startTransaction(senderId, recipientId, amount);
            System.out.println("The transfer is completed");
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument");
        }
    }

    private void viewAllUserTransactions() {
        System.out.println("Enter a user ID");
        try {
            int userId = Integer.parseInt(sc.nextLine());
            for (Transaction transaction : service.getUsersList().getById(userId).getTransactionsList().toArray()) {
                String direction = "From";
                User user = transaction.getRecipient();
                if (transaction.getCategory().equals(TransactionCategory.CREDIT)) {
                    direction = "To";
                    user = transaction.getSender();
                }
                System.out.printf("%s %s(id = %d) %d with id = %s\n",
                        direction, user.getName(), user.getId(), transaction.getAmount(), transaction.getId());
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument");
        }
    }

    private void deleteTransferById() {
        System.out.println("Enter a user ID and a transfer ID");
        try {
            String[] data = sc.nextLine().split(" ");
            if (data.length != 2) {
                throw new IllegalArgumentException();
            }
            int userId = Integer.parseInt(data[0]);
            UUID transId = UUID.fromString(data[1]);
            Transaction[] transactions = service.getUsersList().getById(userId).getTransactionsList().toArray();
            Transaction transaction = null;
            for (Transaction tr : transactions) {
                if (tr.getId().equals(transId)) {
                    transaction = tr;
                }
            }
            if (transaction == null) {
                throw new IllegalArgumentException();
            }
            service.deleteTransaction(transId, userId);
            String direction = "From";
            User user = transaction.getRecipient();
            if (transaction.getCategory().equals(TransactionCategory.CREDIT)) {
                direction = "To";
                user = transaction.getSender();
            }
            System.out.printf("Transfer %s %s(id = %d) %d removed\n",
                    direction, user.getName(), user.getId(), transaction.getAmount());
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument");
        }
    }

    private void checkTransfers() {
        System.out.println("Check results:");
        try {
            Transaction[] transactions = service.checkValidityOfTransactions();
            for (Transaction transaction : transactions) {
                System.out.printf("%s(id = %d) has an unacknowledged transfer id = %s from %s(id = %d) for %d\n",
                        transaction.getRecipient().getName(), transaction.getSender().getId(), transaction.getId(),
                        transaction.getSender().getName(), transaction.getSender().getId(), transaction.getAmount());
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument");
        }
    }
}
