package Home;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import Model.Comic;
import Model.Constants;
import Model.Image;
import Model.RemoteDataSource;
import Networking.GlideOps;

/**
 * Created by sidd on 03/06/17.
 */

public class ListOfComicsPresenterImpl implements ListOfComicsPresenter, RemoteDataSource.RemoteDataSourceInterface {
    private ListOfComicsView listOfComicsView;
    public ListOfComicsPresenterImpl(ListOfComicsView view){
        this.listOfComicsView = view;
    }
    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void getRemoteData(String endPoint) {
        new RemoteDataSource().getDataFromEndpoint(endPoint, this);

    }

    @Override
    public void getImages(Context context, ImageView imageView, Comic comic) {
        List<Image> images = comic.getImages();
        GlideOps glideOps = new GlideOps(context, imageView);
        if(images.size()>0){
            String url = images.get(0).getPath()+"/"+ Constants.IMAGE_SIZE+"."+images.get(0).getExtension();
            glideOps.beginOps(url, false);
            Log.d("GlideUrl", url);
        }
        else{
            glideOps.beginOps(null, true);
        }
    }

    @Override
    public void onRemoteResultObtained(List<Comic> result) {
        if(result!=null){
            listOfComicsView.showText(result);
        }
        else{
            Log.d("Result was null", "Presenter");

        }
    }
}
