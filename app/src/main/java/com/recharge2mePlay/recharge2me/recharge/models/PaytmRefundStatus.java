package com.recharge2mePlay.recharge2me.recharge.models;

public class PaytmRefundStatus {
    //        Paytm_refundStatus.put("orderId", orderID);
    //        Paytm_refundStatus.put("userCreditInitiateStatus", "SUCCESS");
    //        Paytm_refundStatus.put("resultStatus", "TXN_SUCCESS");
    //        Paytm_refundStatus.put("resultCode", "10");
    //        Paytm_refundStatus.put("resultMsg", "Refund Successfull");
    //        Paytm_refundStatus.put("txnTimestamp", "2019-05-01 19:25:41.0");
    //        Paytm_refundStatus.put("acceptRefundTimestamp", "2019-05-01 19:27:25.0");
    //        Paytm_refundStatus.put("acceptRefundStatus", "SUCCESS");
    //        Paytm_refundStatus.put("refundType", "TO_SOURCE");
    //        Paytm_refundStatus.put("userCreditExpectedDate", "2019-05-02");
    //        Paytm_refundStatus.put("refundAmount", "100.00");
    //        Paytm_refundStatus.put("refId", orderID+"_refund");
    //        Paytm_refundStatus.put("txnAmount", "100.00");
    //        Paytm_refundStatus.put("refundId", "PAYTM_REFUND_ID");
    //        Paytm_refundStatus.put("txnId", "PAYTM_TRANSACTION_ID");

    private String orderID;
    private String userCreditInitiateStatus;
    private String resultStatus;
    private String resultCode;
    private String resultMsg;
    private String txnTimestamp;
    private String acceptRefundTimestamp;
    private String acceptRefundStatus;
    private String refundType;
    private String userCreditExpectedDate;
    private String refundAmount;
    private String refId;
    private String txnAmount;
    private String refundId;
    private String txnId;


    public PaytmRefundStatus() {
    }

    public PaytmRefundStatus(String orderID, String userCreditInitiateStatus, String resultStatus, String resultCode, String resultMsg,
                             String txnTimestamp, String acceptRefundTimestamp, String acceptRefundStatus, String refundType,
                             String userCreditExpectedDate, String refundAmount, String refId, String txnAmount, String refundId, String txnId) {
        this.orderID = orderID;
        this.userCreditInitiateStatus = userCreditInitiateStatus;
        this.resultStatus = resultStatus;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.txnTimestamp = txnTimestamp;
        this.acceptRefundTimestamp = acceptRefundTimestamp;
        this.acceptRefundStatus = acceptRefundStatus;
        this.refundType = refundType;
        this.userCreditExpectedDate = userCreditExpectedDate;
        this.refundAmount = refundAmount;
        this.refId = refId;
        this.txnAmount = txnAmount;
        this.refundId = refundId;
        this.txnId = txnId;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setUserCreditInitiateStatus(String userCreditInitiateStatus) {
        this.userCreditInitiateStatus = userCreditInitiateStatus;
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

    public void setTxnTimestamp(String txnTimestamp) {
        this.txnTimestamp = txnTimestamp;
    }

    public void setAcceptRefundTimestamp(String acceptRefundTimestamp) {
        this.acceptRefundTimestamp = acceptRefundTimestamp;
    }

    public void setAcceptRefundStatus(String acceptRefundStatus) {
        this.acceptRefundStatus = acceptRefundStatus;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public void setUserCreditExpectedDate(String userCreditExpectedDate) {
        this.userCreditExpectedDate = userCreditExpectedDate;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getUserCreditInitiateStatus() {
        return userCreditInitiateStatus;
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

    public String getTxnTimestamp() {
        return txnTimestamp;
    }

    public String getAcceptRefundTimestamp() {
        return acceptRefundTimestamp;
    }

    public String getAcceptRefundStatus() {
        return acceptRefundStatus;
    }

    public String getRefundType() {
        return refundType;
    }

    public String getUserCreditExpectedDate() {
        return userCreditExpectedDate;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public String getRefId() {
        return refId;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public String getRefundId() {
        return refundId;
    }

    public String getTxnId() {
        return txnId;
    }
}
