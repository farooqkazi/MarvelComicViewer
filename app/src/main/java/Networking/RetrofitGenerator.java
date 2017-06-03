package Networking;

import Model.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sidd on 02/06/17.
 */

public class RetrofitGenerator {
    private static OkHttpClient.Builder mOkHttpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder mRetrofitBuilder = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                                                            .addConverterFactory(GsonConverterFactory.create())
                                                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit mRetrofit = mRetrofitBuilder.build();

    public static <S> S createService(Class<S> serviceClass){

            AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor();
            AdditionalParametersInterceptor additionalParametersInterceptor =
                                                new AdditionalParametersInterceptor();
            if(!mOkHttpClient.interceptors().contains(authenticationInterceptor)){
                mOkHttpClient.addInterceptor(authenticationInterceptor);
            }
            if(!mOkHttpClient.interceptors().contains(additionalParametersInterceptor)){
                mOkHttpClient.addInterceptor(additionalParametersInterceptor);
            }
            mRetrofitBuilder.client(mOkHttpClient.build());
            mRetrofit = mRetrofitBuilder.build();

        return mRetrofit.create(serviceClass);
    }

}
