package recahrge.DataTypes.rechargeFirbase;

import recahrge.DataTypes.rechargeFirbase.Pay2All.Pay2All_rechargeFirebase;
import recahrge.DataTypes.rechargeFirbase.Paytm.PaytmRefundData;
import recahrge.DataTypes.rechargeFirbase.Paytm.PaytmTransactionData;

public class Order {

    private Pay2All_rechargeFirebase Pay2all;
    private PaytmTransactionData Paytm;
    private PaytmRefundData PaytmRefund;

    private String number;
    private String operator;
    private String pay2allStatus;
    private String paytmRefundStatus;
    private String paytmStatus;
    private String recAmt;
    private String recDet;
    private String date;
    private String orderId;

    public Order() { }

    public Order(Pay2All_rechargeFirebase pay2all, PaytmTransactionData paytm, PaytmRefundData paytmRefund, String number, String orderId,
                 String operator, String pay2allStatus, String paytmRefundStatus, String paytmStatus, String recAmt, String recDet, String date) {
        Pay2all = pay2all;
        Paytm = paytm;
        PaytmRefund = paytmRefund;
        this.number = number;
        this.operator = operator;
        this.pay2allStatus = pay2allStatus;
        this.paytmRefundStatus = paytmRefundStatus;
        this.paytmStatus = paytmStatus;
        this.recAmt = recAmt;
        this.recDet = recDet;
        this.date = date;
        this.orderId  =  orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Pay2All_rechargeFirebase getPay2all() {
        return Pay2all;
    }

    public void setPay2all(Pay2All_rechargeFirebase pay2all) {
        Pay2all = pay2all;
    }

    public PaytmTransactionData getPaytm() {
        return Paytm;
    }

    public void setPaytm(PaytmTransactionData paytm) {
        Paytm = paytm;
    }

    public PaytmRefundData getPaytmRefund() {
        return PaytmRefund;
    }

    public void setPaytmRefund(PaytmRefundData paytmRefund) {
        PaytmRefund = paytmRefund;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPay2allStatus() {
        return pay2allStatus;
    }

    public void setPay2allStatus(String pay2allStatus) {
        this.pay2allStatus = pay2allStatus;
    }

    public String getPaytmRefundStatus() {
        return paytmRefundStatus;
    }

    public void setPaytmRefundStatus(String paytmRefundStatus) {
        this.paytmRefundStatus = paytmRefundStatus;
    }

    public String getPaytmStatus() {
        return paytmStatus;
    }

    public void setPaytmStatus(String paytmStatus) {
        this.paytmStatus = paytmStatus;
    }

    public String getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(String recAmt) {
        this.recAmt = recAmt;
    }

    public String getRecDet() {
        return recDet;
    }

    public void setRecDet(String recDet) {
        this.recDet = recDet;
    }
}
