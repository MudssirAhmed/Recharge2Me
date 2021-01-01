package recahrge.DataTypes.Paye2All;

public class Pay2All_recharge {

    private int status;
    private int status_id;
    private String utr;
    private String report_id;
    private String orderid;
    private String message;

    public Pay2All_recharge(int status, int status_id, String utr, String report_id, String orderid, String message) {
        this.status = status;
        this.status_id = status_id;
        this.utr = utr;
        this.report_id = report_id;
        this.orderid = orderid;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public int getStatus_id() {
        return status_id;
    }

    public String getUtr() {
        return utr;
    }

    public String getReport_id() {
        return report_id;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getMessage() {
        return message;
    }
}
