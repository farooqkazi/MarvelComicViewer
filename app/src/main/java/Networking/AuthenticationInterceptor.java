package Networking;

import android.util.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Model.Constants;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sidd on 02/06/17.
 */

public class AuthenticationInterceptor implements Interceptor {

    public AuthenticationInterceptor(){

    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String combinedString = timestamp+Constants.PRIVATE_KEY+Constants.PUBLIC_KEY;
        String hashedAuthParameter = createMD5Hash(combinedString);
        if(hashedAuthParameter!=null) {
            Log.i("Generated hash", "Yes");
            Request originalRequest = chain.request();
            HttpUrl originalHttpUrl = originalRequest.url();
            HttpUrl modifiedUrl = originalHttpUrl.newBuilder().addQueryParameter(Constants.PARAM_TIMESTAMP, timestamp)
                    .addQueryParameter(Constants.PARAM_APIKEY, Constants.PUBLIC_KEY)
                    .addQueryParameter(Constants.PARAM_HASH, hashedAuthParameter)
                    .build();

            Log.d("url", modifiedUrl.toString());
            Request.Builder requestBuilder = originalRequest.newBuilder().url(modifiedUrl);
            return chain.proceed(requestBuilder.build());
        }
        else{
            return null;
        }
    }

    private String createMD5Hash(String toBeHashed){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(Constants.ALGORITHM_TYPE);
            messageDigest.update(toBeHashed.getBytes(Charset.forName("US-ASCII")),0,toBeHashed.length());
            byte[] digestArray = messageDigest.digest();
            BigInteger bi = new BigInteger(1, digestArray);
            return String.format("%0"+(digestArray.length<<1)+"x",bi);

        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
}
