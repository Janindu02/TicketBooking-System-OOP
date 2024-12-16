package com.example.demo.model;

import jakarta.persistence.*;





@Entity
@Table(name = "ticket_pools")
public class TicketPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int vendors;
    private int customers;
    private int totalTickets;
    private int ticketReleaseRate;
    private int ticketPurchaseRate;
    private int maxTicketTotal;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getVendors() { return vendors; }
    public void setVendors(int vendors) { this.vendors = vendors; }

    public int getCustomers() { return customers; }
    public void setCustomers(int customers) { this.customers = customers; }

    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public void setTicketReleaseRate(int ticketReleaseRate) { this.ticketReleaseRate = ticketReleaseRate; }

    public int getTicketPurchaseRate() { return ticketPurchaseRate; }
    public void setTicketPurchaseRate(int ticketPurchaseRate) { this.ticketPurchaseRate = ticketPurchaseRate; }

    public int getMaxTicketTotal() { return maxTicketTotal; }
    public void setMaxTicketTotal(int maxTicketTotal) { this.maxTicketTotal = maxTicketTotal; }
}
