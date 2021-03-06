package lab.com.br.shoppinglist.views.ShoppingListViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import lab.com.br.shoppinglist.R;
import lab.com.br.shoppinglist.entities.ShoppingList;

/**
 * Created by LEONARDO on 23/07/2017.
 */

public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_shopping_list_listOwnerName)
    TextView ownerName;

    @BindView(R.id.list_shopping_list_listName)
    TextView listName;

    @BindView(R.id.list_shopping_list_dateCreated)
    TextView dateCreated;

    @BindView(R.id.list_shopping_list_layout)
    public View layout;

    public ShoppingListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(ShoppingList shoppingList) {
        ownerName.setText(shoppingList.getOwnerName());
        listName.setText(shoppingList.getListName());
        if (shoppingList.getDateCreated().get("timestamp") != null) {
            dateCreated.setText(convertTime((Long) shoppingList.getDateCreated().get("timestamp")));
        }
    }

    private String convertTime(Long unixTime) {
        Date dateObject = new Date(unixTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yy kk:mm");
        return simpleDateFormat.format(dateObject);
    }
}
