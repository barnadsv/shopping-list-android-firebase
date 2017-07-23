package lab.com.br.shoppinglist.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.squareup.otto.Bus;

import lab.com.br.shoppinglist.infrastructure.ShoppingListApplication;
import lab.com.br.shoppinglist.infrastructure.Utils;

/**
 * Created by LEONARDO on 22/07/2017.
 */

public class BaseDialog extends DialogFragment {
    protected ShoppingListApplication application;
    protected Bus bus;
    protected String userEmail;
    protected String userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ShoppingListApplication) getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);
        userEmail = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.EMAIL, "");
        userName = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.USERNAME, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
