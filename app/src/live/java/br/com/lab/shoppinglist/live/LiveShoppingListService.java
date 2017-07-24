package br.com.lab.shoppinglist.live;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import lab.com.br.shoppinglist.entities.ShoppingList;
import lab.com.br.shoppinglist.infrastructure.ShoppingListApplication;
import lab.com.br.shoppinglist.infrastructure.Utils;
import lab.com.br.shoppinglist.services.ShoppingListService;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by LEONARDO on 23/07/2017.
 */

public class LiveShoppingListService extends BaseLiveService {

    public LiveShoppingListService(ShoppingListApplication application) {
        super(application);
    }

    @Subscribe
    public void AddShoppingList(ShoppingListService.AddShoppingListRequest request) {
        ShoppingListService.AddShoppingListResponse response = new ShoppingListService.AddShoppingListResponse();

        if (request.shoppingListName.isEmpty()) {
            response.setPropertyErrors("listName","Shopping List must have a name");
        }
        if (response.didSucceed()) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl(Utils.FIRE_BASE_SHOPPING_LIST_REFERENCE + request.ownerEmail).push();
            HashMap<String, Object> timestampedCreated = new HashMap<>();
            timestampedCreated.put("timestamp", ServerValue.TIMESTAMP);
            ShoppingList shoppingList = new ShoppingList(reference.getKey(), request.shoppingListName, Utils.decodeEmail(request.ownerEmail), request.ownerName, timestampedCreated);
            reference.child("id").setValue(shoppingList.getId());
            reference.child("listName").setValue(shoppingList.getListName());
            reference.child("ownerEmail").setValue(shoppingList.getOwnerEmail());
            reference.child("ownerName").setValue(shoppingList.getOwnerName());
            reference.child("dateCreated").setValue(shoppingList.getDateCreated());
            reference.child("dateLastChanged").setValue(shoppingList.getDateLastChanged());
            Toast.makeText(application.getApplicationContext(), "List has been created", Toast.LENGTH_LONG).show();
        }
        bus.post(response);
    }

    @Subscribe
    public void DeleteShoppingList(ShoppingListService.DeleteSoppingListRequest request) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl(Utils.FIRE_BASE_SHOPPING_LIST_REFERENCE + request.ownerEmail + "/" + request.shoppingListId);
        reference.removeValue();
    }

    @Subscribe
    public void ChangeListName(ShoppingListService.ChangeListNameRequest request) {
        ShoppingListService.ChangeListNameResponse response = new ShoppingListService.ChangeListNameResponse();
        if (request.shoppingListName.isEmpty()) {
            response.setPropertyErrors("listName", "Shopping List must have a name");
        }
        if (response.didSucceed()) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl(Utils.FIRE_BASE_SHOPPING_LIST_REFERENCE + request.shoppingListOwnerEmail + "/" + request.shoppingListId);
            HashMap<String, Object> timeLastChanged = new HashMap<>();
            timeLastChanged.put("date", ServerValue.TIMESTAMP);
            Map newListData = new HashMap();
            newListData.put("listName", request.shoppingListName);
            newListData.put("dateLastChanged", timeLastChanged);
            reference.updateChildren(newListData);
        }
        bus.post(response);
    }

    @Subscribe
    public void getCurrentShoppingList(ShoppingListService.GetCurrentShoppingListRequest request) {
        final ShoppingListService.GetCurrentShoppingListResponse response = new ShoppingListService.GetCurrentShoppingListResponse();
        response.valueEventListener = request.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                response.shoppingList = dataSnapshot.getValue(ShoppingList.class);
                if (response.shoppingList != null) {
                    bus.post(response);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
