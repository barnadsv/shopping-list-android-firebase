package lab.com.br.shoppinglist.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.squareup.otto.Bus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
//        printKeyHash();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

//    private void printKeyHash() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.i("keyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.e("jk", "Exception(NameNotFoundException) : " + e);
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("mkm", "Exception(NoSuchAlgorithmException) : ", e);
//        }
//    }
}
