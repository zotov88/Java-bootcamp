package ex05;

import java.util.Objects;
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private Item first;
    private Item last;
    private int count;

    @Override
    public void add(Transaction transaction) {
        final Item last = this.last;
        Item item = new Item(transaction, null, last);
        if (first == null) {
            first = this.last = item;
        } else {
            this.last.next = item;
            this.last = item;
        }
        count++;
    }

    @Override
    public void deleteById(UUID id) {
        if (Objects.isNull(id)) {
            throw new TransactionNotFoundException("Transaction is null");
        }
        for (Item tmp = first; tmp != null; tmp = tmp.next) {
            if (tmp.transaction != null && id.equals(tmp.transaction.getId())) {
                unlink(tmp);
                return;
            }
        }
        throw new TransactionNotFoundException("Transaction with UUID: " + id + " not found.");
    }

    private void unlink(Item tmp) {
        final Item next = tmp.next;
        final Item prev = tmp.previous;
        if (prev == null) {
            this.first = next;
        } else {
            prev.next = next;
            tmp.previous = null;
        }
        if (next == null) {
            this.last = prev;
        } else {
            next.previous = prev;
            tmp.next = null;
        }
        tmp.transaction = null;
        count--;
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] array = new Transaction[count];
        int i = 0;
        for (Item tmp = first; tmp != null; tmp = tmp.next) {
            array[i++] = tmp.transaction;
        }
        return array;
    }

    private static class Item {
        private Transaction transaction;
        private Item next;
        private Item previous;

        public Item(Transaction transaction, Item next, Item previous) {
            this.transaction = transaction;
            this.next = next;
            this.previous = previous;
        }
    }

    public int size() {
        return count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionsLinkedList{");
        sb.append("first=").append(first);
        sb.append(", last=").append(last);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
