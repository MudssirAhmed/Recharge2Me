package com.recharge2mePlay.recharge2me.recharge.models;

public class PaytmRefund {
    //        Paytm_refund.put("txnTimestamp", "2019-09-02 12:31:49.0");
    //        Paytm_refund.put("orderId", orderID);
    //        Paytm_refund.put("refId", orderID+"_refund");
    //        Paytm_refund.put("resultStatus", "PENDING");
    //        Paytm_refund.put("resultCode", "601");
    //        Paytm_refund.put("resultMsg", "Refund request was raised for this transaction. But it is pending state");
    //        Paytm_refund.put("refundId", "PAYTM_REFUND_ID");
    //        Paytm_refund.put("txnId", "PAYTM_TRANSACTION_ID");
    //        Paytm_refund.put("refundAmount", "100.00");

    private String txnTimestamp;
    private String orderId;
    private String refId;
    private String resultStatus;
    private String resultCode;
    private String resultMsg;
    private String refundId;
    private String txnId;
    private String refundAmount;

    public PaytmRefund() {
    }

    public PaytmRefund(String txnTimestamp, String orderId, String refId, String resultStatus, String resultCode,
                       String resultMsg, String refundId, String txnId, String refundAmount){

        this.txnTimestamp = txnTimestamp;
        this.orderId= orderId;
        this.refId = refId;
        this.resultStatus = resultStatus;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.refundId = refundId;
        this.txnId = txnId;
        this.refundAmount = refundAmount;

    }

    public void setTxnTimestamp(String txnTimestamp) {
        this.txnTimestamp = txnTimestamp;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
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

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getTxnTimestamp() {
        return txnTimestamp;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getRefId() {
        return refId;
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
    public String getRefundId() {
        return refundId;
    }
    public String getTxnId() {
        return txnId;
    }
    public String getRefundAmount() {
        return refundAmount;
    }

}
