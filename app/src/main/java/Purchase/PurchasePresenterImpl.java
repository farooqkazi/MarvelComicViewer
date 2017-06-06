package Purchase;

import java.util.ArrayList;
import java.util.List;

import Model.Comic;
import Util.ComicsListHelper;

/**
 * Created by sidd on 06/06/17.
 */

public class PurchasePresenterImpl implements PurchasePresenter {
    private PurchaseView purchaseView;
    public PurchasePresenterImpl(PurchaseView purchaseView){
        this.purchaseView = purchaseView;
    }
    @Override
    public void getComicsWithinBudget(float amount) {
        List<Comic> result = ComicsListHelper.getComicsInPriceRange(amount);
        if(result!=null) {
            purchaseView.updateRecyclerView(result);
        }
        else{
            purchaseView.updateRecyclerView(new ArrayList<Comic>());
        }
    }
}
