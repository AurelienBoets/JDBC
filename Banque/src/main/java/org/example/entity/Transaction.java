package org.example.entity;

public class Transaction {
    private long id;
    private double amount;
    private TransactionEnum status;

    public Transaction(long id,double amount, TransactionEnum status) {
        this.id=id;
        this.amount = amount;
        this.status = status;
    }

    public Transaction(){

    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Transaction{" +
                "id=" + id +
                ", total=%.2f"  +
                ", status=" + (this.status==TransactionEnum.DEPOSIT ? "Dépôt" : "Retrait") +
                '}',amount);
    }
}
