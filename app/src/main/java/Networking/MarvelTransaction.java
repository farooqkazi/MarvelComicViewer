package Networking;

import Model.MarvelResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sidd on 02/06/17.
 */

public interface MarvelTransaction {
    @GET("/v1/public/{endpoint}")
    Call<MarvelResponse> getDataFromEndpoint(@Path("endpoint")String endpoint);
}
