package Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marvelcomicsviewer.R;

import java.util.ArrayList;
import java.util.List;

import ComicDetails.ComicDetails;
import Model.Comic;
import Model.Constants;
import Model.RemoteDataSource;

public class ListOfComics extends AppCompatActivity implements ListOfComicsView{
    private ListOfComicsPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private ListOfComicsAdapter mListOfMoviesAdapter;
    private List<Comic> mListOfComics = new ArrayList<Comic>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_comics);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list_of_comics);
        mListOfMoviesAdapter = new ListOfComicsAdapter(mListOfComics);
        mRecyclerView.setAdapter(mListOfMoviesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPresenter = new ListOfComicsPresenterImpl(this);
        getRemoteData();
    }
    private void getRemoteData(){
        mPresenter.getRemoteData(Constants.COMICS_ENDPOINT);
        //new RemoteDataSource().getDataFromEndpoint(Constants.COMICS_ENDPOINT);
    }


    @Override
    public void showText(List<Comic> data) {
        mListOfComics.clear();
        mListOfComics.addAll(data);
        mListOfMoviesAdapter.notifyDataSetChanged();
        Log.d("in main", data.get(0).getTitle());
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
