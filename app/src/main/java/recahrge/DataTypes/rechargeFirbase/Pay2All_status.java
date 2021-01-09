package recahrge.DataTypes.rechargeFirbase;

public class Pay2All_status {
        //    "status": 1,
        //    "status_id": 1,
        //    "report_id": 2438098,
        //    "number": "8126126759",
        //    "amount": 399,
        //    "profit": 1.99500000000000010658141036401502788066864013671875,
        //    "utr": "1578055287",
        //    "client_id": "lsvxTShbFaQy6n45I6jFL5Lrtor2"

    private String status;
    private String status_id;
    private String report_id;
    private String number;
    private String amount;
    private String utr;
    private String client_id;

    public Pay2All_status() {
    }

    public Pay2All_status(String status, String status_id, String report_id, String number, String amount, String utr, String client_id){
        this.status = status;
        this.status_id = status_id;
        this.report_id = report_id;
        this.amount = amount;
        this.number = number;
        this.utr = utr;
        this.client_id = client_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getStatus() {
        return status;
    }
    public String getStatus_id() {
        return status_id;
    }
    public String getReport_id() {
        return report_id;
    }
    public String getNumber() {
        return number;
    }
    public String getAmount() {
        return amount;
    }
    public String getUtr() {
        return utr;
    }
    public String getClient_id() {
        return client_id;
    }


}
