package ComicDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.marvelcomicsviewer.R;

import java.util.ArrayList;
import java.util.List;

import Home.ListOfComics;
import Model.Comic;
import Model.Constants;
import Model.Image;
import Model.LocalDataSource;
import okhttp3.Interceptor;

public class ComicDetails extends AppCompatActivity implements ComicDetailsView{
    private RecyclerView mComicDetails;
    private List<Pair<Integer, String>> mData=new ArrayList<Pair<Integer, String>>();
    private ComicDetailsAdapter mComicDetailsAdapter;
    private ComicDetailsPresenterImpl mPresenter;
    private ImageView mBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_details);
        mBanner = (ImageView) findViewById(R.id.activity_comic_details_iv_banner);
        mComicDetails = (RecyclerView) findViewById(R.id.rv_comic_details);
        mPresenter = new ComicDetailsPresenterImpl(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mComicDetailsAdapter = new ComicDetailsAdapter(mData);
        mComicDetails.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mComicDetails.getContext(),
                ((LinearLayoutManager)mComicDetails.getLayoutManager()).getOrientation());
        mComicDetails.addItemDecoration(dividerItemDecoration);
        mComicDetails.setAdapter(mComicDetailsAdapter);

        int comicId = getIntent().getIntExtra(Constants.ARG_ID, -1);
        if(comicId>=0){
            mPresenter.getChosenComicDetails(comicId);
            setBanner(comicId);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setBanner(int comicId){
        mPresenter.getImageForBanner(this, mBanner, comicId);

    }

    @Override
    public void populateRecyclerView(List<Pair<Integer, String>> data) {
        mData.clear();
        mData.addAll(data);
        mComicDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showImageView(boolean shouldShow) {
        if(shouldShow){
            mBanner.setVisibility(View.VISIBLE);
        }
        else{
            mBanner.setVisibility(View.GONE);
        }
    }

    public class ComicDetailsAdapter extends RecyclerView.Adapter<ComicDetailsAdapter.ViewHolder>{

        private List<Pair<Integer, String>> mData;

        public ComicDetailsAdapter(List<Pair<Integer, String>> data){
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
             holder.detail.setText(mData.get(position).second);
             Glide.with(holder.itemView.getContext())
                  .load(mData.get(position).first)
                   .into(holder.icon);
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView detail;
            private ImageView icon;
            public ViewHolder(View itemView) {
                super(itemView);
                detail = (TextView) itemView.findViewById(R.id.comic_detail_view_tv_detail);
                icon = (ImageView) itemView.findViewById(R.id.comic_detail_view_iv_icon);
            }
        }
    }

}
