package Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sidd on 04/06/17.
 */

public class LocalDataSource {
    private Map<Integer, Comic> mComicsCollection=null;
    private List<Comic> sortedByPrice=null;
    private static LocalDataSource INSTANCE;

    private LocalDataSource(){

    }

    public static LocalDataSource getInstance(){
        if(INSTANCE==null){
            INSTANCE = new LocalDataSource();
        }
        return INSTANCE;
    }


    public void storeDataLocally(List<Comic> comicsList){
        if(comicsList!=null&&comicsList.size()>0) {
            int size = (int) Math.ceil(comicsList.size() / 0.75);
            mComicsCollection = new HashMap<Integer, Comic>(size);
            for (Comic c : comicsList) {
                mComicsCollection.put(c.getId(), c);
            }
            sortByPrice(comicsList);
        }
    }

    public Comic getComicById(int id){
        if(mComicsCollection!=null) {
            return mComicsCollection.get(id);
        }
        else{
            return null;
        }
    }

    private void sortByPrice(List<Comic> comicslist){
        if(comicslist!=null) {
            Collections.sort(comicslist, new Comparator<Comic>() {
                @Override
                public int compare(Comic o1, Comic o2) {

                    return Float.compare(o1.getPrices().get(0).getPrice(), o2.getPrices().get(0).getPrice());

                }
            });
            sortedByPrice = new ArrayList<Comic>(comicslist.size());
            sortedByPrice.addAll(comicslist);
        }
    }
    public List<Comic> getComicsSortedByPrice(){
        return sortedByPrice;
    }
}
