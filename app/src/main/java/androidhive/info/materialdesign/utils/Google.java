package androidhive.info.materialdesign.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidhive.info.materialdesign.GlobalData.ApplicationData;
import androidhive.info.materialdesign.GlobalData.UserData;
import androidhive.info.materialdesign.activity.LoginActivity;

import static com.google.android.gms.plus.Plus.PeopleApi;

/**
 * Created by Alex on 19.05.2015.
 */

public class Google extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 100;
    public GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;
    LoginActivity loginActivity = new LoginActivity();

    UserData userData = new UserData();

    Activity activity = ApplicationData.getActivityId();

    public void getUserAccounts() {

        Account[] accountsAll = AccountManager.get(activity).getAccounts();
        Account google = null;

        List<Account> googleAccounts = new ArrayList<>();
        for (Account account : accountsAll) {
            if (account.type.equals("com.google")) {
                googleAccounts.add(account);
            }
        }

        if (googleAccounts.size() > 1) {
        } else if (googleAccounts.size() == 1) {
            google = googleAccounts.get(0);
        }

        if (google != null) {
            mGoogleApiClient = new GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API)
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
                    .setAccountName(google.name)
                    .build();
            mGoogleApiClient.connect();
        }
    }


    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(activity, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();

            }
        }
    }


    /**
     * Error connectionFailed
     * Only to more 2 Accounts Google
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), activity, 0).show();
            return;
        }
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
            resolveSignInError();
        }
    }

    /**
     * If user connected to Google
     * Get user info
     */
    @Override
    public void onConnected(Bundle arg0) {
        // Get user's information
        getProfileInformation();
        // Open new Activity
        // getUserAuth();

    }


    /**
     * Fetching user's information name, email, profile pic
     */
    private void getProfileInformation() {
        try {
            Person currentPerson = PeopleApi.getCurrentPerson(mGoogleApiClient);
            userData.setFirstName(currentPerson.getName().getGivenName());
            userData.setLastName(currentPerson.getName().getFamilyName());
            userData.setUserName(currentPerson.getDisplayName());
//            userData.setUserID(currentPerson.getId());
//            userData.Sex = String.valueOf(currentPerson.getGender());
//            userData.Location = currentPerson.getCurrentLocation();
//            userData.Age = currentPerson.getBirthday();
            userData.setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
//          userData.Language = currentPerson.getLanguage();
            userData.setUserPhoto(currentPerson.getImage().getUrl());
            new LoadProfileImage().execute(userData.getUserPhoto().substring(0,
                    userData.getUserPhoto().length() - 2) + 80);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    /**
     * Sign-in into google
     */
    public void signInWithGplus() {
        getUserAccounts();
        userData.setUserConnected(true);
    }

    /**
     * Sign-out from google
     */
    public void signOutFromGplus() {
        if (mGoogleApiClient==null) {
            userData.setUserConnected(false);
            loginActivity.logoutUser(activity);
        } else {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            userData.setUserConnected(false);
            loginActivity.logoutUser(activity);
        }
    }

    /**
     * Revoking access from google
     */
    public void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            mGoogleApiClient.connect();
                        }

                    });
        }
    }

    public class LoadProfileImage extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
                userData.setUserPhotoDrawble(mIcon);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            loginActivity.loginUser(activity);
        }
    }

}
