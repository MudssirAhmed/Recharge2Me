package recahrge.DataTypes.rechargeFirbase.Paytm;

import recahrge.paytm.PaytmTransactionStatus;

public class PaytmTransactionData {

    //        order.put("ORDERID", paytm_ORDERID);
    //        order.put("RESPCODE", paytm_RESPCODE);
    //        order.put("RESPMSG", paytm_RESPMSG);
    //        order.put("TXNID", paytm_TXNID);
    //        order.put("TXNAMOUNT", paytm_TXNAMOUNT);
    //        order.put("REFAMOUNT", paytm_REFAMOUNT);
    //        order.put("TXNDATE", paytm_TXNDATE);

    String RESPCODE;
    String RESPMSG;
    String TXNID;
    String TXNAMOUNT;
    String REFAMOUNT;
    String TXNDATE ;

    public PaytmTransactionData(){}

    public PaytmTransactionData(String RESPCODE, String RESPMSG, String TXNID, String TXNAMOUNT, String REFAMOUNT, String TXNDATE) {
        this.RESPCODE = RESPCODE;
        this.RESPMSG = RESPMSG;
        this.TXNID = TXNID;
        this.TXNAMOUNT = TXNAMOUNT;
        this.REFAMOUNT = REFAMOUNT;
        this.TXNDATE = TXNDATE;
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

    public String getTXNID() {
        return TXNID;
    }

    public void setTXNID(String TXNID) {
        this.TXNID = TXNID;
    }

    public String getTXNAMOUNT() {
        return TXNAMOUNT;
    }

    public void setTXNAMOUNT(String TXNAMOUNT) {
        this.TXNAMOUNT = TXNAMOUNT;
    }

    public String getREFAMOUNT() {
        return REFAMOUNT;
    }

    public void setREFAMOUNT(String REFAMOUNT) {
        this.REFAMOUNT = REFAMOUNT;
    }

    public String getTXNDATE() {
        return TXNDATE;
    }

    public void setTXNDATE(String TXNDATE) {
        this.TXNDATE = TXNDATE;
    }
}
