package lab.com.br.shoppinglist.activities;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lab.com.br.shoppinglist.R;
import lab.com.br.shoppinglist.dialog.AddListDialogFragment;
import lab.com.br.shoppinglist.dialog.DeleteListDialogFragment;
import lab.com.br.shoppinglist.entities.ShoppingList;
import lab.com.br.shoppinglist.infrastructure.Utils;
import lab.com.br.shoppinglist.views.ShoppingListViews.ShoppingListViewHolder;

public class MainActivity extends BaseActivity {

    @BindView(R.id.activity_main_fab)
    FloatingActionButton floatingActionButton;

    RecyclerView recyclerView;

    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.activity_main_listRecyclerView);
        String toolbarName;

        if (userName.contains(" ")) {
            toolbarName = userName.substring(0, userName.indexOf(" ")) +  "'s Shopping List";
        } else {
            toolbarName = userName + "'s Shopping List";
        }

        getSupportActionBar().setTitle(toolbarName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseReference shoppingListReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Utils.FIRE_BASE_SHOPPING_LIST_REFERENCE + userEmail);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sortOrder = sharedPreferences.getString(Utils.LIST_ORDER_PREFERENCE, Utils.ORDER_BY_KEY);
        Query sortQuery;

        if (sortOrder.equals(Utils.ORDER_BY_KEY)) {
            sortQuery = shoppingListReference.orderByKey();
        } else {
            sortQuery = shoppingListReference.orderByChild(sortOrder);
        }

        adapter = new FirebaseRecyclerAdapter<ShoppingList, ShoppingListViewHolder>(ShoppingList.class, R.layout.list_shopping_list, ShoppingListViewHolder.class, sortQuery) {
            @Override
            protected void populateViewHolder(ShoppingListViewHolder shoppingListViewHolder, final ShoppingList shoppingList, int position) {
                shoppingListViewHolder.populate(shoppingList);
                shoppingListViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> shoppingListInfo = new ArrayList<>();
                        shoppingListInfo.add(shoppingList.getId());
                        shoppingListInfo.add(shoppingList.getListName());
                        shoppingListInfo.add(shoppingList.getOwnerEmail());
                        startActivity(ListDetailsActivity.newInstance(getApplicationContext(), shoppingListInfo));
                    }
                });

                shoppingListViewHolder.layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (userEmail.equals(Utils.encodeEmail(shoppingList.getOwnerEmail()))) {
                            DialogFragment dialogFragment = DeleteListDialogFragment.newInstance(shoppingList.getId(), true);
                            dialogFragment.show(getFragmentManager(), DeleteListDialogFragment.class.getSimpleName());
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Only the owner can delete a list", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    }
                });


            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferences sharedPreferences = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Utils.EMAIL, null).apply();
                editor.putString(Utils.USERNAME, null).apply();
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;
            case R.id.action_sort:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.activity_main_fab)
    public void setFloatingActionButton() {
        DialogFragment dialogFragment = AddListDialogFragment.newInstance();
        dialogFragment.show(getFragmentManager(), AddListDialogFragment.class.getSimpleName());
    }
}
