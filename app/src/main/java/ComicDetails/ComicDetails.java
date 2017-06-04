package ComicDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marvelcomicsviewer.R;

import java.util.ArrayList;
import java.util.List;

import Home.ListOfComics;
import Model.Comic;
import Model.Constants;
import Model.LocalDataSource;
import okhttp3.Interceptor;

public class ComicDetails extends AppCompatActivity implements ComicDetailsView{
    private RecyclerView mComicDetails;
    private List<String> mData=new ArrayList<String>();
    private ComicDetailsAdapter mComicDetailsAdapter;
    private ComicDetailsPresenterImpl mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_details);
        mComicDetails = (RecyclerView) findViewById(R.id.rv_comic_details);
        mPresenter = new ComicDetailsPresenterImpl(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mComicDetailsAdapter = new ComicDetailsAdapter(mData);
        mComicDetails.setLayoutManager(new LinearLayoutManager(this));
        mComicDetails.setAdapter(mComicDetailsAdapter);

        int comicId = getIntent().getIntExtra(Constants.ARG_ID, -1);
        if(comicId>=0){
            mPresenter.getChosenComic(comicId);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void populateRecyclerView(List<String> data) {
        mData.clear();
        mData.addAll(data);
        mComicDetailsAdapter.notifyDataSetChanged();
    }

    public class ComicDetailsAdapter extends RecyclerView.Adapter<ComicDetailsAdapter.ViewHolder>{

        private List<String> mData;

        public ComicDetailsAdapter(List<String> data){
            mData = data;
        }
        @Override
        public ComicDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View comicDetailView = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.comic_details_view, parent, false);
            return new ViewHolder(comicDetailView);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public void onBindViewHolder(ComicDetailsAdapter.ViewHolder holder, int position) {
             holder.detail.setText(mData.get(position));
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView detail;
            public ViewHolder(View itemView) {
                super(itemView);
                detail = (TextView) itemView.findViewById(R.id.comic_detail_view_tv_detail);

            }
        }
    }

}
