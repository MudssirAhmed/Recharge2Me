package Retrofit;

import java.util.Map;

import Ui_Front_and_Back_end.firebase.DeleteUser;
import recahrge.DataTypes.planDataTypes.PlanData;
import recahrge.DataTypes.Paye2All.Pay2All_authToken;
import recahrge.DataTypes.Paye2All.Pay2All_providers;
import recahrge.DataTypes.Paye2All.Pay2All_recharge;
import recahrge.paytm.PaytmToken;
import recahrge.paytm.PaytmTransactionStatus;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonConvertor {

    // mobile details finder
    @GET("mob_details.php")
    Call<MobileDetailsFinder_Data> getMobileF(
            @Query("format") String format,
            @Query("token") String token,
            @Query("mobile") String mobile
    );

    //https://api.rechapi.com/rech_plan.php?format=#format&token=#token&type=#rechType&cirid=#circleCode&opid=#opid
    @GET("rech_plan.php")
    Call<PlanData> getRechargePlan(
            @Query("format") String format,
            @Query("token") String token,
            @Query("type") String type,
            @Query("cirid") String circleCode,
            @Query("opid") String opid
    );

    // https://api.pay2all.in/token

    @GET("getPay2allToken")
    Call<Pay2All_authToken> getAuthToken(
            @Query("userName") String userName,
            @Query("password") String password
    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("providers")
    Call<Pay2All_providers> getAllProviders(@Header("Authorization") String auth);

    @GET("recharge")
    Call<Pay2All_recharge> doRecharge(
            @Query("number") String number,
            @Query("amount") String amount,
            @Query("providerId") String providerId,
            @Query("clientId") String orderId
    );

    @POST("token") // Production
    Call<PaytmToken> getPaytmTransactionToken(
            @Query("orderId") String orderId,
            @Query("amount") String amount,
            @Query("uid") String uid,

            @Query("recAmt") String recAmt,
            @Query("recDet") String recDet,
            @Query("number") String number,
            @Query("operator") String operator,
            @Query("opId") String opId
            );

    @POST("getTransactionStatus") // Production
    Call<PaytmTransactionStatus> getPaytmTransactionStatus(
            @Query("orderID") String orderID
    );

    @POST("delete")
    Call<DeleteUser> deleteUser(
            @Query("uid") String uid
    );
}
