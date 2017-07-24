package lab.com.br.shoppinglist.activities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import lab.com.br.shoppinglist.R;
import lab.com.br.shoppinglist.dialog.ChangeListNameDialogFragment;
import lab.com.br.shoppinglist.entities.ShoppingList;
import lab.com.br.shoppinglist.infrastructure.Utils;
import lab.com.br.shoppinglist.services.ShoppingListService;

/**
 * Created by LEONARDO on 23/07/2017.
 */

public class ListDetailsActivity extends BaseActivity {

    public static final String SHOPPING_LIST_DETAILS = "SHOPPING_LIST_DETAILS";

    private String mShoppingListId;
    private String mShoppingListName;
    private String mShoppingListOwner;

    private DatabaseReference mShoppingListReference;
    private ValueEventListener mShoppingListListener;
    private ShoppingList mCurrentShoppingList;

    public static Intent newInstance(Context context, ArrayList<String> shoppingListInfo) {
        Intent intent = new Intent(context, ListDetailsActivity.class);
        intent.putStringArrayListExtra(SHOPPING_LIST_DETAILS, shoppingListInfo);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        mShoppingListId = getIntent().getStringArrayListExtra(SHOPPING_LIST_DETAILS).get(0);
        mShoppingListName = getIntent().getStringArrayListExtra(SHOPPING_LIST_DETAILS).get(1);
        mShoppingListOwner = getIntent().getStringArrayListExtra(SHOPPING_LIST_DETAILS).get(2);

        mShoppingListReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Utils.FIRE_BASE_SHOPPING_LIST_REFERENCE + userEmail + "/" + mShoppingListId);
        bus.post(new ShoppingListService.GetCurrentShoppingListRequest(mShoppingListReference));

        getSupportActionBar().setTitle(mShoppingListName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_details, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShoppingListReference.removeEventListener(mShoppingListListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_list_name:
                ArrayList<String> shoppingListInfo = new ArrayList<>();
                shoppingListInfo.add(mShoppingListId);
                shoppingListInfo.add(mShoppingListName);
                DialogFragment dialogFragment = ChangeListNameDialogFragment.newInstance(shoppingListInfo);
                dialogFragment.show(getFragmentManager(), ChangeListNameDialogFragment.class.getSimpleName());
        }
        return true;
    }

    @Subscribe
    public void getCurrentShoppingList(ShoppingListService.GetCurrentShoppingListResponse response) {
        mShoppingListListener = response.valueEventListener;
        mCurrentShoppingList = response.shoppingList;
        getSupportActionBar().setTitle(mCurrentShoppingList.getListName());
    }
}
