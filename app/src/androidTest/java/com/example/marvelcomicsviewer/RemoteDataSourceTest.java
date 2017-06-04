package com.example.marvelcomicsviewer;



import android.provider.Settings;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import Model.Constants;
import Model.MarvelResponse;
import Networking.MarvelTransaction;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Created by sidd on 04/06/17.
 */
@RunWith(AndroidJUnit4.class)
public class RemoteDataSourceTest {
    private MockRetrofit mMockRetrofit;
    private Retrofit mRetrofit;
    @Before
    public void setup(){
        mRetrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                        .client(new OkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
        NetworkBehavior networkBehavior = NetworkBehavior.create();
        mMockRetrofit = new MockRetrofit.Builder(mRetrofit).networkBehavior(networkBehavior).build();
    }
    @Test
    public void testDataRetrieval(){
        BehaviorDelegate<MarvelTransaction> delegate = mMockRetrofit.create(MarvelTransaction.class);
        MarvelTransaction mockMarvelTransaction = new MockMarvelTransaction(delegate);
        Observable<MarvelResponse> resultObservable =  mockMarvelTransaction.getDataFromEndpoint(Constants.COMICS_ENDPOINT);
        final Object waitForSyncObject = new Object();
        resultObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MarvelResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull MarvelResponse marvelResponse) {
                Assert.assertThat(marvelResponse.getData().getResults().get(0).getTitle(),
                        org.hamcrest.Matchers.is(MockMarvelTransaction.MOCK_COMIC_TITLE));
                synchronized (waitForSyncObject){
                    waitForSyncObject.notify();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        synchronized (waitForSyncObject){
            try{
                waitForSyncObject.wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
