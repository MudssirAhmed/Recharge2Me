package recahrge.DataTypes.rechargeFirbase.Paytm;

public class PaytmRefundData {

    String refAmount;
    String refId;
    String refundId;
    String resultMsg;
    String resultStatus;
    String refundStatus;
    String ExpectedDate;

    public PaytmRefundData() { }

    public PaytmRefundData(String refAmount, String refId, String refundId, String resultMsg, String resultStatus, String refundStatus, String ExpectedDate) {
        this.refAmount = refAmount;
        this.refId = refId;
        this.refundId = refundId;
        this.resultMsg = resultMsg;
        this.resultStatus = resultStatus;
        this.ExpectedDate = ExpectedDate;
        this.refundStatus = refundStatus;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getExpectedDate() {
        return ExpectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        ExpectedDate = expectedDate;
    }

    public String getRefAmount() {
        return refAmount;
    }

    public void setRefAmount(String refAmount) {
        this.refAmount = refAmount;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
}
