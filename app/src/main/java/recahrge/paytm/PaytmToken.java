package recahrge.paytm;

public class PaytmToken {
    //{
    //    "head": {
    //        "responseTimestamp": "1609408307003",
    //        "version": "v1",
    //        "signature": "j+8y4MEFv7+SdBAOoJlMMwp8GTzDhO+rxznjaLMNPkPo8GZ4srhmPu1fA2vqx0FV0I9UkIm1fz5dXsYHgkyrbBPhelgpu5Gc81xXBMZ9nFc="
    //    },
    //    "body": {
    //        "resultInfo": {
    //            "resultStatus": "S",
    //            "resultCode": "0000",
    //            "resultMsg": "Success"
    //        },
    //        "txnToken": "12691cd8fde94cce87f15793f67f209a1609408306886",
    //        "isPromoCodeValid": false,
    //        "authenticated": false
    //    }
    //}

    private Body body;

    public Body getBody() {
        return body;
    }

    public class Body{
        private ResultInfo resultInfo;
        private String txnToken;

        public String getTxnToken() {
            return txnToken;
        }

        public ResultInfo getResultInfo() {
            return resultInfo;
        }

        private class ResultInfo{
            String resultStatus;
            String resultCode;
            String resultMsg;

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
