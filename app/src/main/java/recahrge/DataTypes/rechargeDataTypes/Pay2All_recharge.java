package recahrge.DataTypes.rechargeDataTypes;

public class Pay2All_recharge {

    private String status;
    private String status_id;
    private String utr;
    private String report_id;
    private String orderid;
    private String message;

    public Pay2All_recharge(String status, String status_id, String utr, String report_id, String orderid, String message) {
        this.status = status;
        this.status_id = status_id;
        this.utr = utr;
        this.report_id = report_id;
        this.orderid = orderid;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getStatus_id() {
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
