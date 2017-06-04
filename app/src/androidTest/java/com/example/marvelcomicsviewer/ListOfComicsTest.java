package com.example.marvelcomicsviewer;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import Home.ListOfComics;
import Model.Constants;
import io.reactivex.observables.ConnectableObservable;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by sidd on 04/06/17.
 */
@RunWith(AndroidJUnit4.class)
public class ListOfComicsTest {
    public static final String COMIC_ONE_TITLE="Adventures of Javaman";
    public static final String COMIC_TWO_TITLE="Javaman, Meet Reactive Kid";
    @Rule
    public ActivityTestRule<ListOfComics> mActivityRule = new ActivityTestRule<ListOfComics>(
            ListOfComics.class, true, false); //false for lazy init

    private MockWebServer mMockWebServer;

    @Before
    public void setup(){
        mMockWebServer = new MockWebServer();
        try {
            mMockWebServer.start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Constants.BASE_URL = mMockWebServer.url("/").toString();
        Log.d("Constants URL", Constants.BASE_URL);
    }

    @Test
    public void testDisplayOfMockData() throws Exception{
        String fileName = "200_response.json";
        try{
            mMockWebServer.enqueue(new MockResponse().setResponseCode(200)
                    .setBody(TestHelper.getStringFromFile(InstrumentationRegistry.getContext(), fileName)));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        Espresso.onView(ViewMatchers.withId(R.id.rv_list_of_comics)).check(new RecyclerViewItemCountAssertion(2));

        Espresso.onView(ViewMatchers.withId(R.id.rv_list_of_comics)).check(ViewAssertions
                .matches(atPosition(0, ViewMatchers.hasDescendant(ViewMatchers.withText(ListOfComicsTest.COMIC_ONE_TITLE)))));

        Espresso.onView(ViewMatchers.withId(R.id.rv_list_of_comics)).check(ViewAssertions
                .matches(atPosition(1, ViewMatchers.hasDescendant(ViewMatchers.withText(ListOfComicsTest.COMIC_TWO_TITLE)))));

    }

    @After
    public void tearDown(){
        try {
            mMockWebServer.shutdown();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> matcher){
        if(matcher!=null){
            return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

                @Override
                public void describeTo(Description description) {
                    description.appendText("Has item at position: "+position+":");
                    matcher.describeTo(description);
                }

                @Override
                protected boolean matchesSafely(RecyclerView item) {
                    RecyclerView.ViewHolder viewHolder = item.findViewHolderForAdapterPosition(position);
                    if(viewHolder==null){
                        return false;
                    }
                    return matcher.matches(viewHolder.itemView);
                }
            };
        }
        return null;
    }
    public class RecyclerViewItemCountAssertion implements ViewAssertion{
        private final int EXPECTED_ITEMS;

        public RecyclerViewItemCountAssertion(int count){
            EXPECTED_ITEMS = count;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if(noViewFoundException!=null){
                throw noViewFoundException;
            }
            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = ((RecyclerView) view).getAdapter();
            Assert.assertThat(adapter.getItemCount(), org.hamcrest.Matchers.is(EXPECTED_ITEMS));
        }
    }
}
