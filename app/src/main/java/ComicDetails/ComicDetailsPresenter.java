package ComicDetails;

import android.content.Context;
import android.widget.ImageView;

import Model.Comic;

/**
 * Created by sidd on 04/06/17.
 */

public interface ComicDetailsPresenter {
    void getChosenComicDetails(int comicId);
    void getImageForBanner(Context context, ImageView imageView, int comicId);
}
