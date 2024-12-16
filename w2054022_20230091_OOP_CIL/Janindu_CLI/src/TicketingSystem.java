import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketingSystem {
    private static TicketPool ticketPool;
    private static Scanner scanner = new Scanner(System.in);
    private static Configuration config;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(4); //Manages vendor and customer threads

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n\t - Welcome to Real-Time Ticketing System - \n");
            System.out.println("Enter 'start', 'config', or 'exit':");
            String command = scanner.nextLine();

            if ("exit".equalsIgnoreCase(command)) {
                System.out.println("Exiting system...");
                executorService.shutdown();
                break;
            }

            switch (command.toLowerCase()) {
                case "start":
                    startSystem();
                    break;
                case "config":
                    displayConfig();
                    break;
                default:
                    System.out.println("Invalid command. Please enter 'start', 'config', or 'exit'.");
            }
        }
    }

    //Method to start the system
    private static void startSystem() {
        System.out.println("Do you want to load an existing configuration? (yes/no)");
        String loadConfig = scanner.nextLine();

        if (loadConfig.equalsIgnoreCase("yes")) {
            //Load configuration from file
            config = Configuration.loadConfig("config.json");
            if (config == null) {
                System.out.println("Error loading configuration. Exiting system...");
                return;
            }
        } else {
            System.out.println("Please enter the following details:");

            int totalTickets = getValidatedInput("Total Tickets: ");
            int ticketReleaseRate = getValidatedInput("Ticket Release Rate: ");
            int customerRetrievalRate = getValidatedInput("Customer Retrieval Rate: ");
            int maxTicketCapacity = getValidatedInput("Max Ticket Capacity: ");

            config = new Configuration();
            config.setTotalTickets(totalTickets);
            config.setTicketReleaseRate(ticketReleaseRate);
            config.setCustomerRetrievalRate(customerRetrievalRate);
            config.setMaxTicketCapacity(maxTicketCapacity);

            config.saveConfig("config.json");
        }

        ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());
        System.out.println("Starting Ticketing System....");

        //Start vendor and customer threads
        executorService.submit(new Vendor(ticketPool, config.getTicketReleaseRate()));
        executorService.submit(new Vendor(ticketPool, config.getTicketReleaseRate()));
        executorService.submit(new Customer(ticketPool, config.getCustomerRetrievalRate()));
        executorService.submit(new Customer(ticketPool, config.getCustomerRetrievalRate()));

        monitorAvailableTickets();
    }


    //Method to monitor and display available tickets
    private static void monitorAvailableTickets() {
        while (!executorService.isShutdown()) {
            System.out.println("Tickets available: " + ticketPool.getAvailableTickets());
            try {
                Thread.sleep(2000); //Display available tickets for every 2 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("All tickets have been sold. System is stopping.");
        executorService.shutdownNow(); //Stop all threads once tickets are sold
    }

    //Method to display the current configuration
    private static void displayConfig() {
        if (config != null) {
            config.displayConfig();
        } else {
            System.out.println("No configuration loaded.");
        }
    }

    //Prompt and validate user inputs for integer values
    private static int getValidatedInput(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
