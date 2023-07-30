package ex05;

import java.util.UUID;

public interface TransactionsList {

    void add(Transaction transaction);

    void deleteById(UUID id);

    Transaction[] toArray();

    int size();
}
