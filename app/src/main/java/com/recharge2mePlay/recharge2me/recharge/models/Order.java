package com.recharge2mePlay.recharge2me.recharge.models;

public class Order {

    private String orderId;
    private String amount;
    private String operator;
    private String number;
    private String details;
    private String date;


    private Pay2AllRechargeFirebase recharge;
    private Pay2AllStatus status;
    private PaytmInitiateTransaction initiateTransaction;
    private PaytmTransactionStatus transactonStatus;
    private PaytmRefund refund;
    private PaytmRefundStatus refundStatus;

    public Order() {
    }


    public Order(String orderId, String operator, Pay2AllRechargeFirebase recharge, Pay2AllStatus status, PaytmInitiateTransaction initiateTransaction,
                 PaytmTransactionStatus transactonStatus, PaytmRefund refund, PaytmRefundStatus refundStatus) {
        this.orderId = orderId;
        this.operator = operator;
        this.recharge = recharge;
        this.status = status;
        this.initiateTransaction = initiateTransaction;
        this.transactonStatus = transactonStatus;
        this.refund = refund;
        this.refundStatus = refundStatus;
    }


    public String getDate() {
        return date;
    }
    public String getAmount() {
        return amount;
    }
    public String getNumber() {
        return number;
    }
    public String getDetails() {
        return details;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getOperator() {
        return operator;
    }
    public Pay2AllRechargeFirebase getRecharge() {
        return recharge;
    }
    public Pay2AllStatus getStatus() {
        return status;
    }
    public PaytmInitiateTransaction getInitiateTransaction() {
        return initiateTransaction;
    }
    public PaytmTransactionStatus getTransactonStatus() {
        return transactonStatus;
    }
    public PaytmRefund getRefund() {
        return refund;
    }
    public PaytmRefundStatus getRefundStatus() {
        return refundStatus;
    }


    public void setDate(String date) {
        this.date = date;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public void setRecharge(Pay2AllRechargeFirebase recharge) {
        this.recharge = recharge;
    }
    public void setStatus(Pay2AllStatus status) {
        this.status = status;
    }
    public void setInitiateTransaction(PaytmInitiateTransaction initiateTransaction) {
        this.initiateTransaction = initiateTransaction;
    }
    public void setTransactonStatus(PaytmTransactionStatus transactonStatus) {
        this.transactonStatus = transactonStatus;
    }
    public void setRefund(PaytmRefund refund) {
        this.refund = refund;
    }
    public void setRefundStatus(PaytmRefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

}
