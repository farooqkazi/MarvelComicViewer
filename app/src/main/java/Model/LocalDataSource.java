package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sidd on 04/06/17.
 */

public class LocalDataSource {
    private Map<Integer, Comic> mComicsCollection=null;
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
        int size = (int) Math.ceil(comicsList.size()/0.75);
        mComicsCollection = new HashMap<Integer, Comic>(size);
        for(Comic c:comicsList){
            mComicsCollection.put(c.getId(), c);
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
}
