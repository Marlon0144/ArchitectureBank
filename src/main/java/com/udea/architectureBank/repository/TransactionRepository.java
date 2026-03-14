package com.udea.architectureBank.repository;


import com.udea.architectureBank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderAccountNumberOrderByReceiverAccountNumber(String senderAccountNumber, String receiverAccountNumber);
}
