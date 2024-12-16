package com.example.demo.service;

import com.example.demo.model.TicketPool;
import com.example.demo.repository.TicketPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketPoolService {

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    // Creating TicketPool configuration
    public TicketPool createOrUpdateTicketPool(TicketPool ticketPool) {
        return ticketPoolRepository.save(ticketPool);
    }

    public TicketPool getTicketPool(Long id) {
        return ticketPoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TicketPool not found with ID: " + id));
    }

    // to retrieve the all ticketPools
    public List<TicketPool> getAllTicketPools() {
        return ticketPoolRepository.findAll();
    }
}
