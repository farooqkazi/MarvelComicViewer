package Home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.marvelcomicsviewer.R;

import Model.Constants;
import Model.RemoteDataSource;

public class ListOfComics extends AppCompatActivity implements ListOfComicsView{
    private ListOfComicsPresenter mPresenter;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_comics);
          tv = (TextView) findViewById(R.id.textView);
        mPresenter = new ListOfComicsPresenterImpl(this);
        getRemoteData();
    }
    private void getRemoteData(){
        mPresenter.getRemoteData(Constants.COMICS_ENDPOINT);
        //new RemoteDataSource().getDataFromEndpoint(Constants.COMICS_ENDPOINT);
    }
    @Override
    public void showText(String toShow) {
        tv.setText(toShow);
    }
}
