package lab.com.br.shoppinglist.infrastructure;

import android.app.Application;

import com.squareup.otto.Bus;

import br.com.lab.shoppinglist.live.Module;

/**
 * Created by LEONARDO on 22/07/2017.
 */

public class ShoppingListApplication extends Application {

    private Bus bus;

    public ShoppingListApplication() {
        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Module.Register(this);
    }

    public Bus getBus() {
        return bus;
    }
}
