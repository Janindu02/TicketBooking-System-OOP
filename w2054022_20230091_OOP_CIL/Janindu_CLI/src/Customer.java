public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int ticketsToBuy;

    public Customer(TicketPool ticketPool, int ticketsToBuy) {
        this.ticketPool = ticketPool;
        this.ticketsToBuy = ticketsToBuy;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            //Retrieve tickets from the pool
            ticketPool.removeTickets(ticketsToBuy);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
