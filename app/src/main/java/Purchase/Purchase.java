package Purchase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvelcomicsviewer.R;

import java.util.List;

import Home.ListOfComics;
import Model.Comic;
import Util.ComicsListHelper;

public class Purchase extends AppCompatActivity implements PurchaseView {
    private Button mButton;

    private EditText mBudget;
    private RecyclerView mRecyclerView;
    private PurchaseResultAdapter mAdapter;
    private PurchasePresenterImpl mPresenter;

    private static final String AMOUNT_TAG="amount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPresenter = new PurchasePresenterImpl(this);
        mButton = (Button) findViewById(R.id.purchase_btn_find);

        mBudget = (EditText) findViewById(R.id.purchase_et_amount);
        mRecyclerView = (RecyclerView) findViewById(R.id.purchase_rv_results);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mBudget.getText().toString())){
                    processClick(Float.parseFloat(mBudget.getText().toString()));
                }

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(AMOUNT_TAG, Float.parseFloat(mBudget.getText().toString()));

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        processClick(savedInstanceState.getFloat(AMOUNT_TAG));
    }

    private void processClick(float amount){
        mPresenter.getComicsWithinBudget(amount);
    }
    private void setupRecyclerView(List<Comic> result){
        mAdapter = new PurchaseResultAdapter(result);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager)mRecyclerView.getLayoutManager()).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateRecyclerView(List<Comic> result) {
        setupRecyclerView(result);
    }

    public class PurchaseResultAdapter extends RecyclerView.Adapter<PurchaseResultAdapter.ViewHolder>{
        private List<Comic> results;

        public PurchaseResultAdapter(List<Comic> results){
            this.results = results;
        }

        @Override
        public PurchaseResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View purchaseResultView = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.purchase_result_view, parent,false);

            return new ViewHolder(purchaseResultView);
        }

        @Override
        public int getItemCount() {
            return results.size();
        }

        @Override
        public void onBindViewHolder(PurchaseResultAdapter.ViewHolder holder, int position) {
            holder.title.setText(results.get(position).getTitle());
            holder.price.setText(Float.toString(results.get(position).getPrices().get(0).getPrice()));
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView title, price;
            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.purchase_result_tv_title);
                price = (TextView) itemView.findViewById(R.id.purchase_view_tv_price);
            }
        }
    }

}
