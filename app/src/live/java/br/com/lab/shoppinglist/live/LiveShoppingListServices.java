package br.com.lab.shoppinglist.live;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

import lab.com.br.shoppinglist.entities.ShoppingList;
import lab.com.br.shoppinglist.infrastructure.ShoppingListApplication;
import lab.com.br.shoppinglist.infrastructure.Utils;
import lab.com.br.shoppinglist.services.ShoppingListService;

/**
 * Created by LEONARDO on 23/07/2017.
 */

public class LiveShoppingListServices extends BaseLiveService {

    public LiveShoppingListServices(ShoppingListApplication application) {
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
}
