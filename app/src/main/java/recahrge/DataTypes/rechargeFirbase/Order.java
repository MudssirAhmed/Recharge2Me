package recahrge.DataTypes.rechargeFirbase;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.collect.Ordering;

public class Order {

    private String orderId;
    private String amount;
    private String operator;
    private String number;
    private String details;


    private Pay2All_rechargeFirebase recharge;
    private Pay2All_status status;
    private Paytm_initiateTransaction initiateTransaction;
    private Paytm_transactonStatus transactonStatus;
    private Paytm_refund refund;
    private Paytm_refundStatus refundStatus;

    public Order() {
    }


    public Order(String orderId, String operator, Pay2All_rechargeFirebase recharge, Pay2All_status status, Paytm_initiateTransaction initiateTransaction,
                 Paytm_transactonStatus transactonStatus, Paytm_refund refund, Paytm_refundStatus refundStatus) {
        this.orderId = orderId;
        this.operator = operator;
        this.recharge = recharge;
        this.status = status;
        this.initiateTransaction = initiateTransaction;
        this.transactonStatus = transactonStatus;
        this.refund = refund;
        this.refundStatus = refundStatus;
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
    public Pay2All_rechargeFirebase getRecharge() {
        return recharge;
    }
    public Pay2All_status getStatus() {
        return status;
    }
    public Paytm_initiateTransaction getInitiateTransaction() {
        return initiateTransaction;
    }
    public Paytm_transactonStatus getTransactonStatus() {
        return transactonStatus;
    }
    public Paytm_refund getRefund() {
        return refund;
    }
    public Paytm_refundStatus getRefundStatus() {
        return refundStatus;
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
    public void setRecharge(Pay2All_rechargeFirebase recharge) {
        this.recharge = recharge;
    }
    public void setStatus(Pay2All_status status) {
        this.status = status;
    }
    public void setInitiateTransaction(Paytm_initiateTransaction initiateTransaction) {
        this.initiateTransaction = initiateTransaction;
    }
    public void setTransactonStatus(Paytm_transactonStatus transactonStatus) {
        this.transactonStatus = transactonStatus;
    }
    public void setRefund(Paytm_refund refund) {
        this.refund = refund;
    }
    public void setRefundStatus(Paytm_refundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

}
