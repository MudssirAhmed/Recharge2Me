package recahrge.DataTypes.rechargeFirbase.Pay2All;

public class Pay2All_rechargeFirebase {

    //    "status": 0,
    //    "status_id": 1,
    //    "utr": "APR2012271021230038",
    //    "report_id": "2424166",
    //    "orderid": "lsvxTShbFaQy6n45I6jFL5Lrtor2",
    //    "message": "success"

    private int status_id;
    private String orderid;
    private String utr;
    private String report_id;
    private String message;


    public Pay2All_rechargeFirebase() { }

    public Pay2All_rechargeFirebase(int status_id, String orderid, String utr, String report_id, String message) {
        this.status_id = status_id;
        this.orderid = orderid;
        this.utr = utr;
        this.report_id = report_id;
        this.message = message;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUtr() {
        return utr;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
