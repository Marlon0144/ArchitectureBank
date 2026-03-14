package com.udea.architectureBank.service;


import com.udea.architectureBank.DTO.TransactionDTO;
import com.udea.architectureBank.entity.Customer;
import com.udea.architectureBank.entity.Transaction;
import com.udea.architectureBank.repository.CustomerRepository;
import com.udea.architectureBank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository; // Para validar cuentas

    public TransactionDTO transferMoney(TransactionDTO transactionDTO) {
        // Validar que los números de cuenta no sean nulos
        if (transactionDTO.getSenderNumberAccount() == null || transactionDTO.getReceiverNumberAccount() == null) {
            throw new IllegalArgumentException("Los números de cuenta del remitente y receptor son obligatorios.");
        }

        // Buscar los clientes por número de cuenta
        Customer sender = customerRepository.findByAccountNumber(transactionDTO.getSenderNumberAccount())
                .orElseThrow(() -> new IllegalArgumentException("La cuenta del remitente no existe."));

        Customer receiver = customerRepository.findByAccountNumber(transactionDTO.getReceiverNumberAccount())
                .orElseThrow(() -> new IllegalArgumentException("La cuenta del receptor no existe."));

        // Validar que el remitente tenga saldo suficiente
        if (sender.getBalance() < transactionDTO.getAmount()) {
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta del remitente.");
        }

        // Realizar la transferencia
        sender.setBalance(sender.getBalance() - transactionDTO.getAmount());
        receiver.setBalance(receiver.getBalance() + transactionDTO.getAmount());

        // Guardar los cambios en las cuentas
        customerRepository.save(sender);
        customerRepository.save(receiver);

        // Crear y guardar la transacción
        Transaction transaction = new Transaction();
        transaction.setSenderAccountNumber(sender.getAccountNumber());
        transaction.setReceiverAccountNumber(receiver.getAccountNumber());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTimestamp(java.time.LocalDateTime.now());
        transaction = transactionRepository.save(transaction);

        // Devolver la transacción creada como DTO
        TransactionDTO savedTransaction = new TransactionDTO();
        savedTransaction.setId(transaction.getId());
        savedTransaction.setSenderNumberAccount(transaction.getSenderAccountNumber());
        savedTransaction.setReceiverNumberAccount(transaction.getReceiverAccountNumber());
        savedTransaction.setAmount(transaction.getAmount());

        return savedTransaction;
    }

    public List<TransactionDTO> getTransactionsForAccount(String accountNumber) {
        List<Transaction> transactions = transactionRepository.findBySenderAccountNumberOrderByReceiverAccountNumber(accountNumber, accountNumber);
        return transactions.stream().map(transaction -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setId(transaction.getId());
            dto.setSenderNumberAccount(transaction.getSenderAccountNumber());
            dto.setReceiverNumberAccount(transaction.getReceiverAccountNumber());
            dto.setAmount(transaction.getAmount());
            dto.setTimestamp(transaction.getTimestamp());
            return dto;
        }).collect(Collectors.toList());
    }
}
