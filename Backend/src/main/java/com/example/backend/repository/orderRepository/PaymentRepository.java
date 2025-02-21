package com.example.backend.repository.orderRepository;

import com.example.backend.entities.Order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findByIdDdh(int idDdh);
}
