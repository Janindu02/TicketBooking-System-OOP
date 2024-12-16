package com.example.demo.controller;

import com.example.demo.model.TicketPool;
import com.example.demo.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/ticket-pool")
public class TicketPoolController {

    @Autowired
    private TicketPoolService ticketPoolService;

    // save ticket pool configuration
    @PostMapping
    public ResponseEntity<TicketPool> createOrUpdateTicketPool(@RequestBody TicketPool ticketPool) {
        TicketPool savedTicketPool = ticketPoolService.createOrUpdateTicketPool(ticketPool);
        return ResponseEntity.ok(savedTicketPool);
    }

    // to retrieve a ticket pool by its  ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketPool> getTicketPool(@PathVariable Long id) {
        TicketPool ticketPool = ticketPoolService.getTicketPool(id);
        return ResponseEntity.ok(ticketPool);
    }

    //to retrieve aii the ticketPool
    @GetMapping
    public ResponseEntity<List<TicketPool>> getAllTicketPools() {
        List<TicketPool> ticketPools = ticketPoolService.getAllTicketPools();
        return ResponseEntity.ok(ticketPools);
    }
}
