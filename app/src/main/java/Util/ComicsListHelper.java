package Util;

import java.util.ArrayList;
import java.util.List;

import Model.Comic;
import Model.LocalDataSource;

/**
 * Created by sidd on 05/06/17.
 */

public class ComicsListHelper {

    public static List<Comic> getComicsInPriceRange(float maxAmount){
        List<Comic> sortedComics = LocalDataSource.getInstance().getComicsSortedByPrice();
        List<Comic> result=null;
        if(sortedComics!=null){
            int index = 0;
            while(index<sortedComics.size()&&maxAmount>sortedComics.get(index).getPrices().get(0).getPrice()){

                maxAmount = maxAmount-sortedComics.get(index).getPrices().get(0).getPrice();
                index++;
            }
            result = new ArrayList<Comic>(index+1);
            for(int i=0; i<index; i++){
                if(sortedComics.get(i).getPrices().get(0).getPrice()==0.0){
                    continue;
                }
                result.add(sortedComics.get(i));
            }
        }
        return result;
    }
}
