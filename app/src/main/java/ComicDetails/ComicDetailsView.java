package ComicDetails;

import android.util.Pair;

import java.util.List;

/**
 * Created by sidd on 04/06/17.
 */

public interface ComicDetailsView {
    void populateRecyclerView(List<Pair<Integer, String>> data);
    void showImageView(boolean shouldShow);
}
