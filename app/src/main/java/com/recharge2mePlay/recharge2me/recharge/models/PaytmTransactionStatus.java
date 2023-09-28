package com.recharge2mePlay.recharge2me.recharge.models;

public class PaytmTransactionStatus {
    //        Paytm_transactionStatus.put("resultStatus", "TXN_SUCCESS");
    //        Paytm_transactionStatus.put("resultCode", "01");
    //        Paytm_transactionStatus.put("resultMsg", "Txn Success");
    //        Paytm_transactionStatus.put("txnId", "fe795335ed3049c78a57271075f2199e1526969112097");
    //        Paytm_transactionStatus.put("orderId", orderID);
    //        Paytm_transactionStatus.put("txnAmount", "100.00");
    //        Paytm_transactionStatus.put("refundAmt", "100.00");
    //        Paytm_transactionStatus.put("txnDate", "2019-02-20 12:35:20.0");

    private String resultStatus;
    private String resultCode;
    private String resultMsg;
    private String txnId;
    private String orderId;
    private String txnAmount;
    private String refundAmt;
    private String txnDate;

    public PaytmTransactionStatus() {
    }

    public PaytmTransactionStatus(String resultStatus, String resultCode, String resultMsg, String txnId,
                                  String orderId, String txnAmount, String refundAmt, String txnDate){

        this.resultStatus = resultStatus;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.txnId = txnId;
        this.orderId= orderId;
        this.txnAmount = txnAmount;
        this.refundAmt = refundAmt;
        this.txnDate = txnDate;

    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public void setRefundAmt(String refundAmt) {
        this.refundAmt = refundAmt;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public String getTxnId() {
        return txnId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public String getRefundAmt() {
        return refundAmt;
    }

    public String getTxnDate() {
        return txnDate;
    }
}
