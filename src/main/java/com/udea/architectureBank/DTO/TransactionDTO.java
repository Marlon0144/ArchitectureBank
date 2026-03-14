package com.udea.architectureBank.DTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {

    private Long id;
    private String senderNumberAccount;
    private String receiverNumberAccount;
    private Double amount;
    private LocalDateTime timestamp;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, String senderNumberAccount, String receiverNumberAccount, Double amount, LocalDateTime timestamp) {
        this.id = id;
        this.senderNumberAccount = senderNumberAccount;
        this.receiverNumberAccount = receiverNumberAccount;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderNumberAccount() {
        return senderNumberAccount;
    }

    public void setSenderNumberAccount(String senderNumberAccount) {
        this.senderNumberAccount = senderNumberAccount;
    }

    public String getReceiverNumberAccount() {
        return receiverNumberAccount;
    }

    public void setReceiverNumberAccount(String receiverNumberAccount) {
        this.receiverNumberAccount = receiverNumberAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
