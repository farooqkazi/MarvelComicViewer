package Home;

import java.util.List;

import Model.Comic;

/**
 * Created by sidd on 03/06/17.
 */

public interface ListOfComicsView {
    void showText(List<Comic> data);
    void showTextFromCache(List<Comic> cachedData);
}
