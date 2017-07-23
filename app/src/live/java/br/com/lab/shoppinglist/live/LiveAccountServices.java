package br.com.lab.shoppinglist.live;

import android.accounts.Account;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Subscribe;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

import lab.com.br.shoppinglist.infrastructure.ShoppingListApplication;
import lab.com.br.shoppinglist.infrastructure.Utils;
import lab.com.br.shoppinglist.services.AccountServices;

/**
 * Created by LEONARDO on 22/07/2017.
 */

public class LiveAccountServices extends BaseLiveService {

    public LiveAccountServices(ShoppingListApplication application) {
        super(application);
    }

    @Subscribe
    public void RegisterUser(final AccountServices.RegisterUserRequest request) {
        AccountServices.RegisterUserResponse response = new AccountServices.RegisterUserResponse();
        if (request.userEmail.isEmpty()) {
            response.setPropertyErrors("email", "Please put in your email.");
        }
        if (request.userName.isEmpty()) {
            response.setPropertyErrors("userName", "Please put int yout name.");
        }
        if (response.didSucceed()) {
            request.progressDialog.show();

            SecureRandom random = new SecureRandom();
            final String randomPassword = new BigInteger(32, random).toString();

            auth.createUserWithEmailAndPassword(request.userEmail, randomPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                request.progressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                auth.sendPasswordResetEmail(request.userEmail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!task.isSuccessful()) {
                                                request.progressDialog.dismiss();
                                                Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            } else {
                                                DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl(Utils.FIRE_BASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));
                                                HashMap<String, Object> timeJoined = new HashMap<>();
                                                timeJoined.put("dateJoined", ServerValue.TIMESTAMP);
                                                reference.child("email").setValue(request.userEmail);
                                                reference.child("name").setValue(request.userName);
                                                reference.child("hasLoggedInWithPassword").setValue(false);
                                                reference.child("timeJoinded").setValue(timeJoined);

                                                Toast.makeText(application.getApplicationContext(), "Please Check Your Email", Toast.LENGTH_LONG).show();

                                                request.progressDialog.dismiss();
                                            }
                                        }
                                    });
                            }
                        }
                    });


        }
        bus.post(response);

    }

    @Subscribe
    public void LogInUser(final AccountServices.LogUserInRequest request) {
        AccountServices.LogUserInResponse response = new AccountServices.LogUserInResponse();

        if (request.userEmail.isEmpty()) {
            response.setPropertyErrors("email", "Please enter your email");
        }
        if (request.userPassword.isEmpty()) {
            response.setPropertyErrors("passxword", "Please enter your password");
        }
        if (response.didSucceed()) {
            request.progressDialog.show();
            auth.signInWithEmailAndPassword(request.userEmail, request.userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                request.progressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl(Utils.FIRE_BASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));
                                reference.child("hasLoggedInWithPassword").setValue(true);
                                request.progressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), "User has logged in!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        bus.post(response);
    }

    @Subscribe
    public void FacebookLogIn(final AccountServices.LogUserInFacebookRequest request) {
        request.progressDialog.show();
        AuthCredential authCredential = FacebookAuthProvider.getCredential(request.accessToken.getToken());
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            request.progressDialog.dismiss();
                            Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            final DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl(Utils.FIRE_BASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() == null) {
                                        HashMap<String, Object> timeJoined = new HashMap<>();
                                        timeJoined.put("dateJoined", ServerValue.TIMESTAMP);
                                        reference.child("email").setValue(request.userEmail);
                                        reference.child("name").setValue(request.userName);
                                        reference.child("hasLoggedInWithPassword").setValue(true);
                                        reference.child("timeJoinded").setValue(timeJoined);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    request.progressDialog.dismiss();
                                    Toast.makeText(application.getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                            request.progressDialog.dismiss();
                            Toast.makeText(application.getApplicationContext(), "User has logged in!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
