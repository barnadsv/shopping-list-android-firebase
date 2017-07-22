package lab.com.br.shoppinglist.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

import lab.com.br.shoppinglist.infrastructure.ShoppingListApplication;

/**
 * Created by LEONARDO on 22/07/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected ShoppingListApplication application;
    protected Bus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ShoppingListApplication) getApplication();
        bus = application.getBus();
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
