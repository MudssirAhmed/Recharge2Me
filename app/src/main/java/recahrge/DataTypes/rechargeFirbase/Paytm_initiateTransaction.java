package recahrge.DataTypes.rechargeFirbase;

public class Paytm_initiateTransaction {
    //        Paytm_initiateTransaction.put("resultInfo", "S");
    //        Paytm_initiateTransaction.put("resultCode", "0000");
    //        Paytm_initiateTransaction.put("resultMsg", "Success");
    //        Paytm_initiateTransaction.put("txnToken", "fe795335ed3049c78a57271075f2199e1526969112097");

    private String resultInfo;
    private String resultCode;
    private String resultMsg;
    private String txnToken;

    public Paytm_initiateTransaction() {
    }

    public Paytm_initiateTransaction(String resultInfo, String resultCode, String resultMsg, String txnToken){
        this.resultInfo = resultInfo;
        this.resultCode = resultCode;
        this. resultMsg = resultMsg;
        this.txnToken = txnToken;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public void setTxnToken(String txnToken) {
        this.txnToken = txnToken;
    }

    public String getResultInfo() {
        return resultInfo;
    }
    public String getResultCode() {
        return resultCode;
    }
    public String getResultMsg() {
        return resultMsg;
    }
    public String getTxnToken() {
        return txnToken;
    }
}
