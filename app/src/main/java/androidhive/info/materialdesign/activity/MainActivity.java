package androidhive.info.materialdesign.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidhive.info.materialdesign.GlobalData.UserData;
import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.FragmentDrawer;
import androidhive.info.materialdesign.utils.Google;
import androidhive.info.materialdesign.utils.InsideWebViewClient;


public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    LinearLayout loading;
    static UserData userData = new UserData();
    Google google = new Google();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        loading = (LinearLayout) findViewById(R.id.loading);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        WebView web =  (WebView) findViewById(R.id.webview);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new Web());
        web.loadUrl("http://rexsoft.ru/blog/");

        // display the first navigation drawer view on app launch
        displayView(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public void onDrawerItemSelected(View view, int position) {
            displayView(position);
    }

    private void displayView(int position) {
        Intent intent;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
//                intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                title = getString(R.string.Blog);
                break;
            case 1:
                intent = new Intent(this, PortfolioActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case 2:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                title = getString(R.string.About);
                this.finish();
                break;
            case 3:

                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                title = getString(R.string.Login);
                this.finish();

                break;
            default:
                break;
        }

    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    private class Web extends InsideWebViewClient{

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loading.setVisibility(View.GONE);
        }
    }
}
