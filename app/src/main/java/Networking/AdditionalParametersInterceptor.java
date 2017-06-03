package Networking;

import java.io.IOException;

import Model.Constants;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sidd on 03/06/17.
 */

public class AdditionalParametersInterceptor implements Interceptor {

    public AdditionalParametersInterceptor(){

    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();
        HttpUrl modifiedUrl = originalHttpUrl.newBuilder()
                                .removeAllQueryParameters(Constants.PARAM_LIMIT)
                                .addQueryParameter(Constants.PARAM_LIMIT, Constants.COMICS_LIMIT)
                                .build();
        Request.Builder requestBuilder = originalRequest.newBuilder().url(modifiedUrl);
        return chain.proceed(requestBuilder.build());
    }
}
