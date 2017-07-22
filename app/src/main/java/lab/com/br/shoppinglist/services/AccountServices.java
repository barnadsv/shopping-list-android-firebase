package lab.com.br.shoppinglist.services;

import android.app.ProgressDialog;

import lab.com.br.shoppinglist.infrastructure.ServiceResponse;

/**
 * Created by LEONARDO on 22/07/2017.
 */

public class AccountServices {

    private AccountServices() {

    }

    public static class RegisterUserRequest {
        public String userName;
        public String userEmail;
        public ProgressDialog progressDialog;

        public RegisterUserRequest(String userName, String userEmail, ProgressDialog mProgressDialog) {
            this.userName = userName;
            this.userEmail = userEmail;
            this.progressDialog = mProgressDialog;
        }
    }

    public static class RegisterUserResponse extends ServiceResponse {

    }



}
