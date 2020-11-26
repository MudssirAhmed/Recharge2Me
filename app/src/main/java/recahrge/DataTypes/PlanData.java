package recahrge.DataTypes;

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
        private List<recType_SPL> SPL;
        private List<recType_Data> DATA;
        private List<recType_FTT> FTT;
        private List<recType_TUP> TUP;
        private List<recType_RMG> RMG;

        // Constructor
        public Data(List<recType_SPL> SPL, List<recType_Data> DATA, List<recType_FTT> FTT, List<recType_TUP> TUP,
                    List<recType_RMG> RMG) {
            this.SPL = SPL;
            this.DATA = DATA;
            this.FTT = FTT;
            this.TUP = TUP;
            this.RMG = RMG;
        }

        public List<recType_SPL> getSPL() {
            return SPL;
        }

        public List<recType_Data> getDATA() {
            return DATA;
        }

        public List<recType_FTT> getFTT() {
            return FTT;
        }

        public List<recType_TUP> getTUP() {
            return TUP;
        }

        public List<recType_RMG> getRMG() {
            return RMG;
        }
    }
}
