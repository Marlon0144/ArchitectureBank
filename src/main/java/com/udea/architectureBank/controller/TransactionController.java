package com.udea.architectureBank.controller;

import com.udea.architectureBank.DTO.TransactionDTO;
import com.udea.architectureBank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar transacciones financieras.
 * Proporciona endpoints para transferencias de dinero y consulta de
 * transacciones de un usuario.
 */
@RestController
@RequestMapping(value = "/api/transactions", produces = "application/json")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /*
     * /**
     * Realiza una transferencia de dinero entre usuarios.
     *
     * @param request {@link TransferRequestDTO} que contiene la información de la
     * transferencia.
     * 
     * @return {@link TransactionDTO} con los detalles de la transacción realizada.
     * /
     * 
     * @PostMapping
     * public TransactionDTO transferMoney(@RequestBody TransferRequestDTO request)
     * {
     * return transactionService.transferMoney(request);
     * }
     */

    @PostMapping
    public ResponseEntity<?> transferMoney(@RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO savedTransaction = transactionService.transferMoney(transactionDTO);
            return ResponseEntity.ok(savedTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{accountNumber}")
    public List<TransactionDTO> getTransactionsByAccount(@PathVariable String accountNumber) {
        return transactionService.getTransactionsForAccount(accountNumber);
    }
}