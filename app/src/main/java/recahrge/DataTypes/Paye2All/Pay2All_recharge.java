package recahrge.DataTypes.Paye2All;

import java.util.Date;

public class Pay2All_recharge {

    //    "data": {
    //        "status": "",
    //        "status_id": "",
    //        "utr": "",
    //        "report_id": "",
    //        "orderid": "",
    //        "message": ""
    //    },
    //    "Error": "Low ballance"

    private Data data;
    private String Error;

    public Data getData() {
        return data;
    }

    public String getError() {
        return Error;
    }


    public class Data{
        private int status;
        private int status_id;
        private String utr;
        private String report_id;
        private String orderid;
        private String message;

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


}
