package lab.com.br.shoppinglist.services;

import android.app.ProgressDialog;

import com.facebook.AccessToken;

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

    public static class LogUserInRequest {
        public String userEmail;
        public String userPassword;
        public ProgressDialog progressDialog;

        public LogUserInRequest(String userEmail, String userPassword, ProgressDialog progressDialog) {
            this.userEmail = userEmail;
            this.userPassword = userPassword;
            this.progressDialog = progressDialog;
        }
    }

    public static class LogUserInResponse extends ServiceResponse {

    }

    public static class LogUserInFacebookRequest {
        public AccessToken accessToken;
        public String userEmail;
        public String userName;
        public ProgressDialog progressDialog;

        public LogUserInFacebookRequest(AccessToken accessToken, String userEmail, String userName, ProgressDialog progressDialog) {
            this.accessToken = accessToken;
            this.userEmail = userEmail;
            this.userName = userName;
            this.progressDialog = progressDialog;
        }
    }

}
