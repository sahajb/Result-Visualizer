package com.example.android.resultvisualizer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.example.android.resultvisualizer.Utilities.JsonUtils.invalidateJson;
import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonValid;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<JSONObject> {

    private EditText getRn;

    private String rn;

    private ProgressBar pb;

    private LinearLayout parent;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                getRn.clearFocus();
                Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        pb = findViewById(R.id.pb);
        parent = findViewById(R.id.parent);
        getRn = findViewById(R.id.getrn);
        getRn.clearFocus();
        getRn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getRn.setHint(hasFocus ? "Ex. 2016/B10/1789" : "");
                if (hasFocus)
                    Objects.requireNonNull(inputMethodManager).showSoftInput(getRn, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Button loadResult = findViewById(R.id.loadresult);
        loadResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rn = getRn.getText().toString();
                getRn.setText("");
                clicked();
            }
        });
        if (getIntent().hasExtra(Intent.EXTRA_TEXT)) {
            rn = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            getIntent().removeExtra(Intent.EXTRA_TEXT);
            clicked();
        }
    }

    private void clicked() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        NetworkInfo networkInfo = ((ConnectivityManager) Objects.requireNonNull(getSystemService(CONNECTIVITY_SERVICE))).getActiveNetworkInfo();
        if ((networkInfo != null && networkInfo.isConnected()) || jsonValid()) {
            if (Pattern.compile("(2016)/([A-B]([1-9]|10))/([0-9]{2,4})").matcher(rn).matches()) {
                parent.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.VISIBLE);
                Loader<JSONObject> loader = getSupportLoaderManager().getLoader(0);
                if (loader == null)
                    getSupportLoaderManager().initLoader(0, null, MainActivity.this);
                else
                    getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
            } else
                Toast.makeText(getApplicationContext(), "Roll. no. pattern is invalid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Internet connection is not available",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        pb.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing())
            invalidateJson();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
            case R.id.nav_docs:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://exam.dtu.ac.in/result.htm")));
                break;
            case R.id.nav_git:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sahajb/Result-Visualizer")));
                break;
            case R.id.nav_share:
                startActivity(new Intent(MainActivity.this, ShareActivity.class));
                break;
            case R.id.nav_email:
                Intent i = new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_SUBJECT, "Contacting regarding : ");
                startActivity(i.putExtra(Intent.EXTRA_EMAIL, new String[]{"result.visualizer@gmail.com"}));
                break;
            case R.id.nav_help:
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void valid() {
        startActivity(new Intent(MainActivity.this, ResultActivity.class).putExtra(Intent.EXTRA_TEXT, rn).
                putExtra("quality", Integer.parseInt(preferences.getString("quality", "50"))));
    }

    public void invalid() {
        pb.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);
        new AlertDialogFragment().show(getSupportFragmentManager(), "dialog");
    }

    public void error() {
        pb.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "Server error. Please try later.", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, final Bundle args) {
        return new JsonLoader(this, rn, preferences.getBoolean("notification", true));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject jsonObject) {
        if (jsonObject.length() == 0)
            error();
        else {
            if (jsonObject.optJSONObject(rn) == null)
                invalid();
            else
                valid();
        }
        getSupportLoaderManager().destroyLoader(0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {
    }

}