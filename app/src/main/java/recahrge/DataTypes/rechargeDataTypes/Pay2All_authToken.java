package recahrge.DataTypes.rechargeDataTypes;

public class Pay2All_authToken {

    private userPay2All user;
    private String access_token;

    public   userPay2All getUser() {
        return user;
    }
    public String getAccess_token() {
        return access_token;
    }

    public static class userPay2All{

//        private String id;
//        private String name;
//        private String email;
//        private String mobile;
        private Balance balance;

        public userPay2All(Balance balance) {
            this.balance = balance;
        }

        public Balance getBalance() {
            return balance;
        }

        public static class Balance {

            private String id;
            private String user_id;
            private String user_balance;
            private String aeps_balance;
            private String created_at;
            private String updated_at;
            private String sms_balance;
            private String pg_balance;

            public Balance(String id, String user_id, String user_balance, String aeps_balance,
                           String created_at, String updated_at, String sms_balance, String pg_balance) {
                this.id = id;
                this.user_id = user_id;
                this.user_balance = user_balance;
                this.aeps_balance = aeps_balance;
                this.created_at = created_at;
                this.updated_at = updated_at;
                this.sms_balance = sms_balance;
                this.pg_balance = pg_balance;
            }

            public String getId() {
                return id;
            }

            public String getUser_id() {
                return user_id;
            }

            public String getUser_balance() {
                return user_balance;
            }

            public String getAeps_balance() {
                return aeps_balance;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public String getSms_balance() {
                return sms_balance;
            }

            public String getPg_balance() {
                return pg_balance;
            }
        }
    }


}
