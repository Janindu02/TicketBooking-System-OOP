import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private List<Ticket> tickets;
    private int maxCapacity;
    private int totalTicketsToRelease;
    private int ticketsReleased = 0;
    private int ticketsPurchased = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public TicketPool(int maxCapacity, int totalTicketsToRelease) {
        this.maxCapacity = maxCapacity;
        this.totalTicketsToRelease = totalTicketsToRelease;
        this.tickets = new ArrayList<>();
    }

    //Method to add tickets to the pool
    public void addTickets(int ticketsToAdd) {
        lock.lock();
        try {
            while (tickets.size() >= maxCapacity) {
                System.out.println("Ticket pool is full, vendors waiting...");
                notFull.await();
            }

            int ticketsCanAdd = Math.min(ticketsToAdd, totalTicketsToRelease - ticketsReleased);
            for (int i = 0; i < ticketsCanAdd; i++) {
                tickets.add(new Ticket());
                ticketsReleased++;
            }
            System.out.println("Vendor added: " + ticketsCanAdd + ". Total released: " + ticketsReleased);

            notEmpty.signalAll();

            if (ticketsReleased >= totalTicketsToRelease && tickets.isEmpty()) {
                System.out.println("All tickets released and purchased. System stopping...");
                System.exit(0);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    //Method to remove tickets from the pool
    public void removeTickets(int ticketsToRemove) {
        lock.lock();
        try {
            while (tickets.isEmpty()) {
                if (ticketsReleased >= totalTicketsToRelease) {
                    System.out.println("All tickets released and purchased. System stopping...");
                    System.exit(0);
                }
                System.out.println("No tickets available, customers waiting...");
                notEmpty.await();
            }

            int ticketsCanRemove = Math.min(ticketsToRemove, tickets.size());
            for (int i = 0; i < ticketsCanRemove; i++) {
                tickets.remove(tickets.size() - 1);
                ticketsPurchased++;
            }
            System.out.println("Customer purchased: " + ticketsCanRemove + ". Total purchased: " + ticketsPurchased);

            notFull.signalAll();

            if (ticketsReleased >= totalTicketsToRelease && tickets.isEmpty()) {
                System.out.println("All tickets released and purchased. System stopping...");
                System.exit(0);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    //Method for get available tickets
    public int getAvailableTickets() {
        lock.lock();
        try {
            return tickets.size();
        } finally {
            lock.unlock();
        }
    }
}
