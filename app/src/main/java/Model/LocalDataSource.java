package Model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private Context context;
    private static final String CACHE_FILE_NAME = "datacache";
    public static final String PREFERENCES_FILE_NAME="localdatapreference";
    public static final String TIME_UPDATED_KEY="timeupdated";
    private LocalDataSource(){

    }

    public void init(Context context){
        this.context = context.getApplicationContext();
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
            persistDataLocally(comicsList);
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

    private void persistDataLocally(List<Comic> comicsData){
        if(context!=null) {
            Gson gson = new Gson();
            Data data = new Data();
            data.setResults(comicsData);
            String toStore = gson.toJson(data);
            FileOutputStream fileOutputStream = null;

            try {
                fileOutputStream = context.openFileOutput(CACHE_FILE_NAME, Context.MODE_PRIVATE);
                fileOutputStream.write(toStore.getBytes());
                context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
                        .edit().putLong(TIME_UPDATED_KEY, System.currentTimeMillis()).apply();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public List<Comic> getStoredData(){
        Data data=null;
        if(context!=null){
            try {
                FileInputStream fileInputStream = context.openFileInput(CACHE_FILE_NAME);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                Gson gson = new Gson();
                data = gson.fromJson(stringBuilder.toString(),Data.class);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        return (data==null?null:data.getResults());
    }
    public List<Comic> getComicsSortedByPrice(){
        return sortedByPrice;
    }
}
