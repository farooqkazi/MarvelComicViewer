package Home;

import android.content.Context;
import android.widget.ImageView;

import Model.Comic;

/**
 * Created by sidd on 03/06/17.
 */

public interface ListOfComicsPresenter {
    void onCreate(Context context);
    void onItemClicked(int position);
    void getRemoteData(String endpoint);
    void tryAndGetLocalData();
    void getImages(Context context, ImageView imageView, Comic comic);
}
