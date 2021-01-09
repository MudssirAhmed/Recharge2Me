package recahrge.DataTypes.rechargeFirbase;

public class Pay2All_rechargeFirebase {

    //    "status": 0,
    //    "status_id": 1,
    //    "utr": "APR2012271021230038",
    //    "report_id": "2424166",
    //    "orderid": "lsvxTShbFaQy6n45I6jFL5Lrtor2",
    //    "message": "success"

    private String status;
    private String status_id;
    private String orderid;
    private String utr;
    private String report_id;
    private String message;


    private String amount;
    private String number;
    private String client_id;
    private String rechargeDetails;

    public Pay2All_rechargeFirebase() {
    }

    public Pay2All_rechargeFirebase(String status, String status_id, String orderid, String utr, String report_id, String message,
                                    String amount, String number, String client_id, String rechargeDetails){
        this.status = status;
        this.status_id = status_id;
        this.orderid = orderid;
        this.utr = utr;
        this.report_id = report_id;
        this.message = message;


        this.amount = amount;
        this.number = number;
        this.client_id = client_id;
        this.rechargeDetails = rechargeDetails;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public void setUtr(String utr) {
        this.utr = utr;
    }
    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
    public void setRechargeDetails(String rechargeDetails) {
        this.rechargeDetails = rechargeDetails;
    }


    public String getRechargeDetails() {
        return rechargeDetails;
    }
    public String getStatus() {
        return status;
    }
    public String getStatus_id() {
        return status_id;
    }
    public String getOrderid() {
        return orderid;
    }
    public String getUtr() {
        return utr;
    }
    public String getReport_id() {
        return report_id;
    }
    public String getMessage() {
        return message;
    }
    public String getAmount() {
        return amount;
    }
    public String getNumber() {
        return number;
    }
    public String getClient_id() {
        return client_id;
    }
}
