package Home;

/**
 * Created by sidd on 03/06/17.
 */

public interface ListOfComicsPresenter {
    void onItemClicked(int position);
    void getRemoteData(String endpoint);
}
