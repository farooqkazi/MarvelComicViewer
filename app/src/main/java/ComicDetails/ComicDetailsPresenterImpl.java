package ComicDetails;

import android.util.Pair;

import com.example.marvelcomicsviewer.R;

import java.util.ArrayList;
import java.util.List;

import Model.Comic;
import Model.Constants;
import Model.Creators;
import Model.Item;
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

    private List<Pair<Integer, String>> assembleDetails(Comic chosenComic){
        List<Pair<Integer, String>> data = new ArrayList<Pair<Integer, String>>();

        //List<String> details = new ArrayList<String>();

        data.add(new Pair<Integer, String>(R.drawable.ic_info_black_24dp,
                chosenComic.getDescription()==null? Constants.DESCRIPTION_PLACEHOLDER:chosenComic.getDescription()));

        data.add(new Pair<Integer, String>(R.drawable.ic_content_copy_black_24dp,
                Integer.toString(chosenComic.getPageCount())));

        data.add(new Pair<Integer, String>(R.drawable.ic_monetization_on_black_24dp,
                chosenComic.getPrices().get(0).getType()+":"+Float.toString(chosenComic.getPrices().get(0).getPrice())));
        if(chosenComic.getCreators().getAvailable()>0){
            List<Item> creators = chosenComic.getCreators().getItems();
            StringBuilder stringBuilder=new StringBuilder();
            for(Item item:creators){
                stringBuilder.append(item.getRole()+" : "+item.getName()+"\n");
            }

            data.add(new Pair<Integer, String>(R.drawable.ic_create_black_24dp, stringBuilder.toString()));

        }
        else{
            data.add(new Pair<Integer, String>(R.drawable.ic_create_black_24dp, Constants.CREATORS_PLACEHOLDER));
        }

        return data;
    }
}
