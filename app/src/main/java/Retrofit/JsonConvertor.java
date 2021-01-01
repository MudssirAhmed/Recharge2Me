package Retrofit;

import java.util.Map;

import recahrge.DataTypes.planDataTypes.PlanData;
import recahrge.DataTypes.Paye2All.Pay2All_authToken;
import recahrge.DataTypes.Paye2All.Pay2All_providers;
import recahrge.DataTypes.Paye2All.Pay2All_recharge;
import recahrge.paytm.PaytmToken;
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

    @POST("token")
    @FormUrlEncoded
    Call<Pay2All_authToken> getAuthToken(@FieldMap Map<String,String> params);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("providers")
    Call<Pay2All_providers> getAllProviders(@Header("Authorization") String auth);

    @GET("recharge")
    Call<Pay2All_recharge> doRecharge(
            @Query("token") String Token,
            @Query("number") String Number,
            @Query("amount") String Amount,
            @Query("provider_id") String ProviderId,
            @Query("client_id") String ClientId
    );

    @GET("getToken")
    Call<PaytmToken> getPaytmTransactionToken(
            @Query("O_id") String O_id,
            @Query("amount") String amount
    );
}
