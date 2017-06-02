package Model;

import android.util.Log;

import java.util.List;

import Networking.MarvelTransaction;
import Networking.RetrofitGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sidd on 03/06/17.
 */

public class RemoteDataSource {
    private static final String LOG_TAG="RemoteDataSource";
    private List<Comic> mListOfComics=null;
    private RemoteDataSourceInterface mRemoteDataSourceInterface= null;

    public void getDataFromEndpoint(String endPoint, RemoteDataSourceInterface remoteDataSourceInterface){
        Log.d(LOG_TAG, "In get data from endpoint");
        this.mRemoteDataSourceInterface = remoteDataSourceInterface;
        MarvelTransaction marvelTransaction = RetrofitGenerator.createService(MarvelTransaction.class);
        Call<MarvelResponse> marvelResponse = marvelTransaction.getDataFromEndpoint(endPoint);
        marvelResponse.enqueue(new Callback<MarvelResponse>() {
            @Override
            public void onResponse(Call<MarvelResponse> call, Response<MarvelResponse> response) {

                if(response.isSuccessful()){
                    mListOfComics = response.body().getData().getResults();
                    if(mRemoteDataSourceInterface!=null){
                        mRemoteDataSourceInterface.onRemoteResultObtained(mListOfComics);
                    }
                    Log.d(LOG_TAG, "Response was sucessful"+response.body().toString());
                }
                else{

                    Log.d(LOG_TAG, "Response was not sucessful"+response.raw().body().toString());
                }
            }

            @Override
            public void onFailure(Call<MarvelResponse> call, Throwable t) {
                Log.d(LOG_TAG, t.getMessage());
            }
        });

    }

    public interface RemoteDataSourceInterface{
        public void onRemoteResultObtained(List<Comic> result);
    }
}
