package com.recharge2mePlay.recharge2me.recharge.models;

import java.util.List;

public class PlanData {

    // {"data":{"SPL":[{"amount":"12","detail":"SMS: 120 SMS","validity":"10 Days","talktime":"NA"},
    private Data data;
    private String resText;
    private String resCode;

    public PlanData(Data data, String resText) {
        this.data = data;
        this.resText = resText;
    }

    public Data getData() {
        return data;
    }

    public String getResText() {
        return resText;
    }

    public String getResCode() {
        return resCode;
    }

    public static class Data {

        // SPL , DATA , FTT , TUP , RMG
        private List<RecTypeSPL> SPL;
        private List<RecTypeData> DATA;
        private List<RecTypeFTT> FTT;
        private List<RecTypeTUP> TUP;
        private List<RecTypeRMG> RMG;

        // Constructor
        public Data(List<RecTypeSPL> SPL, List<RecTypeData> DATA, List<RecTypeFTT> FTT, List<RecTypeTUP> TUP,
                    List<RecTypeRMG> RMG) {
            this.SPL = SPL;
            this.DATA = DATA;
            this.FTT = FTT;
            this.TUP = TUP;
            this.RMG = RMG;
        }

        public List<RecTypeSPL> getSPL() {
            return SPL;
        }

        public List<RecTypeData> getDATA() {
            return DATA;
        }

        public List<RecTypeFTT> getFTT() {
            return FTT;
        }

        public List<RecTypeTUP> getTUP() {
            return TUP;
        }

        public List<RecTypeRMG> getRMG() {
            return RMG;
        }
    }
}
