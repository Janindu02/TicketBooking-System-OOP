public class Vendor implements Runnable {
    private TicketPool ticketPool;
    private int ticketsToAdd;

    public Vendor(TicketPool ticketPool, int ticketsToAdd) {
        this.ticketPool = ticketPool;
        this.ticketsToAdd = ticketsToAdd;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            //Add tickets to the pool
            ticketPool.addTickets(ticketsToAdd);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
