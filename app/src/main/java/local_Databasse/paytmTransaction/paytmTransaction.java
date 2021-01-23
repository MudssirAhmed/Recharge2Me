package local_Databasse.paytmTransaction;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import recahrge.DataTypes.rechargeFirbase.Paytm.PaytmTransactionData;

@Entity(tableName = "paytm")
public class paytmTransaction {

    @PrimaryKey()
    String ORDERID;

    String amount;
    String operator;
    String number;
    String details;
    String TXNID;
    String TXNAMOUNT;
    String REFAMOUNT;
    String TXNDATE;
    String RESPCODE;
    String RESPMSG;

    public paytmTransaction(String ORDERID, String amount, String operator, String number, String details, String TXNID, String TXNAMOUNT,
                            String REFAMOUNT, String TXNDATE, String RESPCODE, String RESPMSG) {
        this.ORDERID = ORDERID;
        this.amount = amount;
        this.operator = operator;
        this.number = number;
        this.details = details;
        this.TXNID = TXNID;
        this.TXNAMOUNT = TXNAMOUNT;
        this.REFAMOUNT = REFAMOUNT;
        this.TXNDATE = TXNDATE;
        this.RESPCODE = RESPCODE;
        this.RESPMSG = RESPMSG;
    }


}
