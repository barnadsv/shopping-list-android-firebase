package lab.com.br.shoppinglist.infrastructure;

/**
 * Created by LEONARDO on 22/07/2017.
 */

public class Utils {

    public static final String FIRE_BARS_BASE_URL = "https://shoppinglist-a0549.firebaseio.com/";
    public static final String FIRE_BASE_USER_REFERENCE = FIRE_BARS_BASE_URL + "users/";
    public static final String FIRE_BASE_SHOPPING_LIST_REFERENCE = FIRE_BARS_BASE_URL + "userShoppingList/";

    public static final String MY_PREFERENCE = "MY_PREFERENCE";
    public static final String EMAIL = "EMAIL";
    public static final String USERNAME = "USERNAME";

    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    public static String decodeEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }

}
