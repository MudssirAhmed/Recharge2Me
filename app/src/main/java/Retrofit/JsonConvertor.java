package Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonConvertor {

    @GET("mob_details.php")
    Call<MobileDetailsFinder_Data> getMobileF(
            @Query("format") String format,
            @Query("token") String token,
            @Query("mobile") String mobile
    );

}
