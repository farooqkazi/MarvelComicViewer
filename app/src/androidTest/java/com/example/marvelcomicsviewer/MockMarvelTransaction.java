package com.example.marvelcomicsviewer;

import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import Model.Comic;
import Model.Constants;
import Model.Data;
import Model.MarvelResponse;
import Networking.MarvelTransaction;
import io.reactivex.Observable;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by sidd on 04/06/17.
 */

public class MockMarvelTransaction implements MarvelTransaction{

    static final String MOCK_COMIC_TITLE="Adventures of Javaman";
    private final BehaviorDelegate<MarvelTransaction> delegate;

    public MockMarvelTransaction(BehaviorDelegate<MarvelTransaction> delegate){
        this.delegate = delegate;
    }
    @Override
    public Observable<MarvelResponse> getDataFromEndpoint(@Path("endpoint") String endpoint) {
        MarvelResponse marvelResponse = new MarvelResponse();
        Data data = new Data();
        List<Comic> comicList = new ArrayList<Comic>();
        Comic comic = new Comic();
        comic.setTitle(MOCK_COMIC_TITLE);
        comicList.add(comic);
        data.setResults(comicList);
        marvelResponse.setData(data);
        return delegate.returningResponse(marvelResponse).getDataFromEndpoint(Constants.COMICS_ENDPOINT);
    }
}
