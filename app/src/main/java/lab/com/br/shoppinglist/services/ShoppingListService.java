package lab.com.br.shoppinglist.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lab.com.br.shoppinglist.entities.ShoppingList;
import lab.com.br.shoppinglist.infrastructure.ServiceResponse;

/**
 * Created by LEONARDO on 23/07/2017.
 */

public class ShoppingListService {

    private ShoppingListService() {
    }

    public static class AddShoppingListRequest {
        public String shoppingListName;
        public String ownerEmail;
        public String ownerName;

        public AddShoppingListRequest(String shoppingListName, String ownerEmail, String ownerName) {
            this.shoppingListName = shoppingListName;
            this.ownerEmail = ownerEmail;
            this.ownerName = ownerName;
        }
    }

    public static class AddShoppingListResponse extends ServiceResponse {

    }

    public static class DeleteSoppingListRequest {
        public String ownerEmail;
        public String shoppingListId;

        public DeleteSoppingListRequest(String ownerEmail, String shoppingListId) {
            this.ownerEmail = ownerEmail;
            this.shoppingListId = shoppingListId;
        }
    }

    public static class ChangeListNameRequest {
        public String shoppingListId;
        public String shoppingListName;
        public String shoppingListOwnerEmail;

        public ChangeListNameRequest(String shoppingListId, String shoppingListName, String shoppingListOwnerEmail) {
            this.shoppingListId = shoppingListId;
            this.shoppingListName = shoppingListName;
            this.shoppingListOwnerEmail = shoppingListOwnerEmail;
        }
    }

    public static class ChangeListNameResponse extends ServiceResponse {

    }

    public static class GetCurrentShoppingListRequest {
        public DatabaseReference reference;

        public GetCurrentShoppingListRequest(DatabaseReference reference) {
            this.reference = reference;
        }
    }

    public static class GetCurrentShoppingListResponse {
        public ShoppingList shoppingList;
        public ValueEventListener valueEventListener;
    }
}
