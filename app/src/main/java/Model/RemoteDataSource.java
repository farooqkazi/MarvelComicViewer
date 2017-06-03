package Model;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.List;

import Networking.MarvelTransaction;
import Networking.RetrofitGenerator;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
        Observable<MarvelResponse> marvelResponse = marvelTransaction.getDataFromEndpoint(endPoint);
        marvelResponse.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(getSubscriber());
        /*
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
                    try {
                        Log.d(LOG_TAG, "Response was not sucessful" + response.errorBody().string());
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MarvelResponse> call, Throwable t) {
                Log.d(LOG_TAG, t.getMessage());
            }
        });
        */

    }
    private Observer<MarvelResponse> getSubscriber(){
        return new Observer<MarvelResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull MarvelResponse marvelResponse) {
                Log.d("Observable", marvelResponse.getData().getResults().get(0).getTitle());
                if(mRemoteDataSourceInterface!=null){
                    mRemoteDataSourceInterface.onRemoteResultObtained(marvelResponse.getData().getResults());
                }
                storeDataLocally(marvelResponse.getData().getResults());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if(mRemoteDataSourceInterface!=null){
                    mRemoteDataSourceInterface.onRemoteError(e);
                }
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void storeDataLocally(List<Comic> data){
        LocalDataSource.getInstance().storeDataLocally(data);
    }
    public interface RemoteDataSourceInterface{
        public void onRemoteResultObtained(List<Comic> result);
        public void onRemoteError(Throwable t);
    }
}
