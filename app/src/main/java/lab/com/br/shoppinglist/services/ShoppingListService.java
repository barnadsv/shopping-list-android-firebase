package lab.com.br.shoppinglist.services;

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
}
