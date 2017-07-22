package lab.com.br.shoppinglist.dialog;

import android.app.DialogFragment;
import android.os.Bundle;

import com.squareup.otto.Bus;

import lab.com.br.shoppinglist.infrastructure.ShoppingListApplication;

/**
 * Created by LEONARDO on 22/07/2017.
 */

public class BaseDialog extends DialogFragment {
    protected ShoppingListApplication application;
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ShoppingListApplication) getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
