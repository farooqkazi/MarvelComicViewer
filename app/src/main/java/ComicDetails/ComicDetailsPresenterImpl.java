package ComicDetails;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;

import com.example.marvelcomicsviewer.R;

import java.util.ArrayList;
import java.util.List;

import Model.Comic;
import Model.Constants;
import Model.Creators;
import Model.Image;
import Model.Item;
import Model.LocalDataSource;
import Networking.GlideOps;

/**
 * Created by sidd on 04/06/17.
 */

public class ComicDetailsPresenterImpl implements ComicDetailsPresenter {
    private ComicDetailsView mComicsDetailsView;
    public ComicDetailsPresenterImpl(ComicDetailsView comicDetailsView){
        mComicsDetailsView = comicDetailsView;
    }
    @Override
    public void getChosenComicDetails(int comicId) {
        Comic chosenComic = LocalDataSource.getInstance().getComicById(comicId);
        if(chosenComic!=null){
            mComicsDetailsView.populateRecyclerView(assembleDetails(chosenComic));
        }
    }

    @Override
    public void getImageForBanner(Context context, ImageView imageView, int comicId) {
        Comic chosenComic = LocalDataSource.getInstance().getComicById(comicId);
        if(chosenComic!=null) {
            List<Image> images = chosenComic.getImages();
            GlideOps glideOps = new GlideOps(context, imageView);
            if (images.size() > 0) {
                String url = images.get(0).getPath() + "/" + Constants.IMAGE_SIZE + "." + images.get(0).getExtension();
                glideOps.beginOps(url, false);
                mComicsDetailsView.showImageView(true);
            } else {
                mComicsDetailsView.showImageView(false);
            }
        }
        else{
            mComicsDetailsView.showImageView(false);
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
