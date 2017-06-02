package Presenter;

import android.text.TextUtils;

import Model.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sidd on 02/06/17.
 */

public class RetrofitGenerator {
    private static OkHttpClient.Builder mOkHttpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder mRetrofitBuilder = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                                                            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit mRetrofit = mRetrofitBuilder.build();

    public static <S> S createService(Class<S> serviceClass){

            AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor();
            if(!mOkHttpClient.interceptors().contains(authenticationInterceptor)){
                mOkHttpClient.addInterceptor(authenticationInterceptor);
            }
            mRetrofitBuilder.client(mOkHttpClient.build());
            mRetrofit = mRetrofitBuilder.build();

        return mRetrofit.create(serviceClass);
    }

}
