package recahrge.DataTypes.rechargeFirbase.Paytm;

import recahrge.paytm.PaytmTransactionStatus;

public class PaytmTransactionData {

    String ORDERID;
    String RESPCODE;
    String RESPMSG;
    String STATUS;
    String TXNAMOUNT;
    String TXNDATETIME;
    String TXNID;

    public PaytmTransactionData(){}

    public PaytmTransactionData(String ORDERID, String RESPCODE, String RESPMSG, String STATUS, String TXNAMOUNT, String TXNDATETIME, String TXNID) {
        this.ORDERID = ORDERID;
        this.RESPCODE = RESPCODE;
        this.RESPMSG = RESPMSG;
        this.STATUS = STATUS;
        this.TXNAMOUNT = TXNAMOUNT;
        this.TXNDATETIME = TXNDATETIME;
        this.TXNID = TXNID;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getRESPCODE() {
        return RESPCODE;
    }

    public void setRESPCODE(String RESPCODE) {
        this.RESPCODE = RESPCODE;
    }

    public String getRESPMSG() {
        return RESPMSG;
    }

    public void setRESPMSG(String RESPMSG) {
        this.RESPMSG = RESPMSG;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getTXNAMOUNT() {
        return TXNAMOUNT;
    }

    public void setTXNAMOUNT(String TXNAMOUNT) {
        this.TXNAMOUNT = TXNAMOUNT;
    }

    public String getTXNDATETIME() {
        return TXNDATETIME;
    }

    public void setTXNDATETIME(String TXNDATETIME) {
        this.TXNDATETIME = TXNDATETIME;
    }

    public String getTXNID() {
        return TXNID;
    }

    public void setTXNID(String TXNID) {
        this.TXNID = TXNID;
    }
}
