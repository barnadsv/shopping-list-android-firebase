package br.com.lab.shoppinglist.live;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.otto.Bus;

import lab.com.br.shoppinglist.activities.BaseActivity;
import lab.com.br.shoppinglist.infrastructure.ShoppingListApplication;

/**
 * Created by LEONARDO on 22/07/2017.
 */

public class BaseLiveService {

    protected Bus bus;
    protected ShoppingListApplication application;
    protected FirebaseAuth auth;

    public BaseLiveService(ShoppingListApplication application) {
        this.application = application;
        bus = application.getBus();
        bus.register(this);
        auth = FirebaseAuth.getInstance();
    }
}
