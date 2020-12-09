package Retrofit;

import recahrge.DataTypes.PlanData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

}
