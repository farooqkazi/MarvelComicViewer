package Home;

import android.util.Log;

import java.util.List;

import Model.Comic;
import Model.RemoteDataSource;

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
    public void onRemoteResultObtained(List<Comic> result) {
        if(result!=null){
            listOfComicsView.showText(result.get(0).getTitle());
        }
        else{
            Log.d("Result was null", "Presenter");

        }
    }
}
