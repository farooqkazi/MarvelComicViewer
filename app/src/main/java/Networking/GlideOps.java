package Networking;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.marvelcomicsviewer.R;

/**
 * Created by sidd on 03/06/17.
 */

public class GlideOps {
    private Context mContext;
    private ImageView mImageView;



    public GlideOps(Context mContext, ImageView mImageView) {
        this.mContext = mContext;
        this.mImageView = mImageView;

    }

    public void beginOps(String url, boolean shouldClear){
        if(!shouldClear){
            Glide.with(mContext)
                 .load(url)
                 .into(mImageView);
        }
        else{
            Glide.with(mContext).clear(mImageView);
            mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.deadpoolquestion));
        }
    }

}
