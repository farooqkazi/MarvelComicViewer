package View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.marvelcomicsviewer.R;

public class ListOfComics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_comics);
        final TextView tv = (TextView) findViewById(R.id.textView);

    }
}
