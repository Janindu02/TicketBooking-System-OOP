package com.example.demo.repository;

import com.example.demo.model.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
}