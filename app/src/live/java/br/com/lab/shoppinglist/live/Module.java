package br.com.lab.shoppinglist.live;

import lab.com.br.shoppinglist.infrastructure.ShoppingListApplication;

/**
 * Created by LEONARDO on 22/07/2017.
 */

public class Module {

    public static void Register(ShoppingListApplication application) {
        new LiveAccountServices(application);
    }
}
