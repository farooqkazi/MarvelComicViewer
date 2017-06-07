package Home;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import Model.LocalDataSource;
import Purchase.Purchase;
import com.example.marvelcomicsviewer.R;

import java.util.ArrayList;
import java.util.List;

import ComicDetails.ComicDetails;
import Model.Comic;
import Model.Constants;
import Util.DateHelper;
import Util.NetworkHelper;

public class ListOfComics extends AppCompatActivity implements ListOfComicsView{
    private ListOfComicsPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private ListOfComicsAdapter mListOfMoviesAdapter;
    private List<Comic> mListOfComics = new ArrayList<Comic>();
    private ProgressBar mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_comics);
        mProgress = (ProgressBar) findViewById(R.id.list_of_comics_pb_progress);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list_of_comics);
        mListOfMoviesAdapter = new ListOfComicsAdapter(mListOfComics);
        mRecyclerView.setAdapter(mListOfMoviesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPresenter = new ListOfComicsPresenterImpl(this);
        mPresenter.onCreate(this);
        getRemoteData();
        FloatingActionButton purchase = (FloatingActionButton) findViewById(R.id.listofhome_fab_buy);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startPurchase = new Intent(ListOfComics.this, Purchase.class);
                startActivity(startPurchase);
            }
        });
    }
    private void getRemoteData(){

        if(NetworkHelper.isOnline(this)){
            mPresenter.getRemoteData(Constants.COMICS_ENDPOINT);
        }
        else{
            showSnackBar(getResources().getString(R.string.nonetworkmessage));
            mPresenter.tryAndGetLocalData();
        }

    }

    private void showSnackBar(String text){
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), text,
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getResources().getString(R.string.snackbaraction), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        TextView snackText = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackText.setMaxLines(5);
        snackbar.show();
    }
    private void showProgressBar(boolean visibility){
        mProgress.setVisibility(visibility?View.VISIBLE:View.GONE);
    }
    @Override
    public void showText(List<Comic> data) {
        showProgressBar(false);
        mListOfComics.clear();
        mListOfComics.addAll(data);
        mListOfMoviesAdapter.notifyDataSetChanged();

    }

    @Override
    public void showTextFromCache(List<Comic> cachedData) {
        showProgressBar(false);
        if(cachedData.size()>0) {
            long timeUpdated = getSharedPreferences(LocalDataSource.PREFERENCES_FILE_NAME,
                    Context.MODE_PRIVATE).getLong(LocalDataSource.TIME_UPDATED_KEY, System.currentTimeMillis());
            String text = String.format(getResources().getString(R.string.cachepresentmessage), DateHelper.getReadableDate(timeUpdated));
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            showText(cachedData);
        }
    }

    public class ListOfComicsAdapter extends RecyclerView.Adapter<ListOfComicsAdapter.ViewHolder>{
        private List<Comic> mData;

        public ListOfComicsAdapter(List<Comic> listOfComics){
            mData = listOfComics;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View comicView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.comic_card, parent, false);
            return new ViewHolder(comicView);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Comic comic = mData.get(position);
            holder.title.setText(comic.getTitle());
            if(mData.size()>0) {
                mPresenter.getImages(ListOfComics.this, holder.poster, comic);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView title;
            private ImageView poster;
            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.comic_card_tv_title);
                poster = (ImageView) itemView.findViewById(R.id.comic_card_iv_poster);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent startDetailsActivity = new Intent(ListOfComics.this, ComicDetails.class);
                        startDetailsActivity.putExtra(Constants.ARG_ID, mData.get(getAdapterPosition()).getId());
                        v.getContext().startActivity(startDetailsActivity);
                    }
                });
            }
        }
    }
}
