package com.recharge2mePlay.recharge2me.recharge.models;

public class MobileDetailFinderData {

    private mobileData data;

    public MobileDetailFinderData(mobileData data) {
        this.data = data;
    }

    public mobileData getData() {
        return data;
    }

    public class mobileData{

        private String service;
        private String location;
        private int circleId;
        private int opId;
        private String resText;

        public mobileData(String service, String location, int circleId, int opId, String resText) {
            this.service = service;
            this.location = location;
            this.circleId = circleId;
            this.opId = opId;
            this.resText = resText;
        }

        public String getService() {
            return service;
        }

        public String getLocation() {
            return location;
        }

        public int getCircleId() {
            return circleId;
        }

        public int getOpId() {
            return opId;
        }

        public String getResText() {
            return resText;
        }
    }
}
