package androidhive.info.materialdesign.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import androidhive.info.materialdesign.GlobalData.ApplicationData;
import androidhive.info.materialdesign.GlobalData.UserData;
import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.utils.Google;

/**
 * Created by Alex on 22.05.2015.
 */
public class LoginActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    LinearLayout loginLayout;
    TextView email, name;
    RoundedImageView userImage;
    UserData userData = new UserData();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginLayout = (LinearLayout) findViewById(R.id.loginUser);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ApplicationData.setActivityId(this);
        final Google google = new Google();

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                google.signInWithGplus();
                findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                findViewById(R.id.progress_wheel).setVisibility(View.VISIBLE);

            }
        });
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                google.signOutFromGplus();
            }
        });

        if (userData.isUserConnected()) {
            loginUser(this);
        }else{
            logoutUser(this);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

     public void loginUser(Activity activity) {
        activity.findViewById(R.id.progress_wheel).setVisibility(View.GONE);
        activity.findViewById(R.id.loginUser).setVisibility(View.VISIBLE);
        email = (TextView) activity.findViewById(R.id.loginEmail);
        email.setText(userData.getEmail());
        name = (TextView) activity.findViewById(R.id.loginName);
        name.setText(userData.getUserName());
        userImage = (RoundedImageView) activity.findViewById(R.id.userImage);
        userImage.setImageBitmap(userData.getUserPhotoDrawble());
    }

    public void logoutUser(Activity activity) {
        activity.findViewById(R.id.loginUser).setVisibility(View.GONE);
        activity.findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
    }


}


