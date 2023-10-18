package com.recharge2mePlay.recharge2me.webservices;

import com.recharge2mePlay.recharge2me.constants.AppConstants;
import com.recharge2mePlay.recharge2me.recharge.models.MobileDetailFinderData;
import com.recharge2mePlay.recharge2me.recharge.models.MobileRechargePlansResponse;
import com.recharge2mePlay.recharge2me.recharge.models.PlanData;
import com.recharge2mePlay.recharge2me.recharge.models.Pay2AllAuthToken;
import com.recharge2mePlay.recharge2me.recharge.models.Pay2AllProviders;
import com.recharge2mePlay.recharge2me.recharge.models.Pay2AllRecharge;
import com.recharge2mePlay.recharge2me.recharge.models.PaytmToken;
import com.recharge2mePlay.recharge2me.recharge.models.PaytmRechargeTransactionStatus;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // mobile details finder
    @GET("mob_details.php")
    Call<MobileDetailFinderData> getMobileF(
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
    Call<Pay2AllAuthToken> getAuthToken(
            @Query("userName") String userName,
            @Query("password") String password
    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("providers")
    Call<Pay2AllProviders> getAllProviders(@Header("Authorization") String auth);

    @GET("recharge")
    Call<Pay2AllRecharge> doRecharge(
            @Query("token") String Token,
            @Query("number") String Number,
            @Query("amount") String Amount,
            @Query("provider_id") String ProviderId,
            @Query("client_id") String ClientId
    );

    @POST("token") // Production
    Call<PaytmToken> getPaytmTransactionToken(
            @Query("orderId") String orderId,
            @Query("amount") String amount,
            @Query("uid") String uid
    );

    @GET("tokenStaging") // Staging
    Call<PaytmToken> getPaytmTransactionToken_staging(
            @Query("orderId") String orderId,
            @Query("amount") String amount,
            @Query("uid") String uid
    );

    @POST("getTransactionStatus") // Production
    Call<PaytmRechargeTransactionStatus> getPaytmTransactionStatus(
            @Query("orderID") String orderID
    );

    @GET("getTransactionStatusStaging") // Staging
    Call<PaytmRechargeTransactionStatus> getPaytmTransactionStatus_staging(
            @Query("orderID") String orderID
    );

    @GET(AppConstants.GET_MOBILE_RECHARGE_PLANS)
    Call<MobileRechargePlansResponse> getMobileRechargePlans(
        @Query("operatorCode") String operatorCode,
        @Query("circleCode") String circleCode
    );
}
