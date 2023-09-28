package com.recharge2mePlay.recharge2me.recharge.models;

public class PaytmRechargeTransactionStatus {

    //    "body": {
    //        "resultInfo": {
    //            "resultStatus": "TXN_SUCCESS",
    //            "resultCode": "01",
    //            "resultMsg": "Txn Success"
    //        },
    //        "txnId": "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
    //        "txnAmount": "100.00",
    //        "refundAmt": "100.00",
    //        "txnDate": "2019-02-20 12:35:20.0"

    private Body body;

    public Body getBody() {
        return body;
    }

    public class Body{

        private ResultInfo resultInfo;

        private String txnId;
        private String txnAmount;
        private String refundAmt;
        private String txnDate;

        public ResultInfo getResultInfo() {
            return resultInfo;
        }

        public String getTxnId() {
            return txnId;
        }


        public String getTxnAmount() {
            return txnAmount;
        }

        public String getRefundAmt() {
            return refundAmt;
        }

        public String getTxnDate() {
            return txnDate;
        }

        public class ResultInfo{
            private String resultStatus;
            private String resultCode;
            private String resultMsg;

            public String getResultStatus() {
                return resultStatus;
            }

            public String getResultCode() {
                return resultCode;
            }

            public String getResultMsg() {
                return resultMsg;
            }
        }
    }

}
