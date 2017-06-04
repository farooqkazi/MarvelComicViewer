package ComicDetails;

import java.util.ArrayList;
import java.util.List;

import Model.Comic;
import Model.Constants;
import Model.LocalDataSource;

/**
 * Created by sidd on 04/06/17.
 */

public class ComicDetailsPresenterImpl implements ComicDetailsPresenter {
    private ComicDetailsView mComicsDetailsView;
    public ComicDetailsPresenterImpl(ComicDetailsView comicDetailsView){
        mComicsDetailsView = comicDetailsView;
    }
    @Override
    public void getChosenComic(int comicId) {
        Comic chosenComic = LocalDataSource.getInstance().getComicById(comicId);
        if(chosenComic!=null){
            mComicsDetailsView.populateRecyclerView(assembleDetails(chosenComic));
        }
    }

    private List<String> assembleDetails(Comic chosenComic){
        List<String> details = new ArrayList<String>();
        details.add(chosenComic.getDescription()==null? Constants.DESCRIPTION_PLACEHOLDER:chosenComic.getDescription());
        details.add(Integer.toString(chosenComic.getPageCount()));
        return details;
    }
}
