package com.example.marvelcomicsviewer;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by sidd on 04/06/17.
 */

public class TestHelper {


    public static String getStringFromFile(Context context, String filePath) throws Exception{
        final InputStream inputStream = context.getResources().getAssets().open(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line=bufferedReader.readLine())!=null){
            stringBuilder.append(line);
        }
        bufferedReader.close();
        inputStream.close();
        return stringBuilder.toString();
    }
}
