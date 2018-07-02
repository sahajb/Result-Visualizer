package com.example.android.resultvisualizer;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.resultvisualizer.Utilities.JsonUtils;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<JSONObject> {

    private EditText getRn;

    private String rn;

    private Button loadResult;

    private ProgressBar pb;

    private LinearLayout parent;

    private boolean b;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        pb = (ProgressBar) findViewById(R.id.pb);
        parent = (LinearLayout) findViewById(R.id.parent);
        getRn = (EditText) findViewById(R.id.getrn);
        getRn.clearFocus();
        getRn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getRn.setHint(hasFocus ? "Ex. 2016/B10/1789" : "");
            }
        });
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        final LoaderManager manager = getLoaderManager();
        loadResult = (Button) findViewById(R.id.loadresult);
        loadResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rn = getRn.getText().toString();
                getRn.setText("");
                clicked(manager);
            }
        });
        b = getIntent().hasExtra(Intent.EXTRA_TEXT);
        if (b) {
            rn = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            clicked(manager);
        }
    }

    private void clicked(LoaderManager manager) {
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if ((networkInfo != null && networkInfo.isConnected()) || JsonUtils.jsonValid()) {
            if (Pattern.compile("(2016)/([A-B][1-9]|10)/([0-9]{2,4})").matcher(rn).matches()) {
                parent.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.VISIBLE);
                Loader<JSONObject> loader=manager.getLoader(0);
                if(loader==null)
                    manager.initLoader(0, null, MainActivity.this);
                else
                    manager.restartLoader(0,null,MainActivity.this);
            } else
                Toast.makeText(getApplicationContext(), "Roll. no. pattern is invalid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Internet connection is not available",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!b) {
            pb.setVisibility(View.GONE);
            parent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
            case R.id.nav_docs:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://exam.dtu.ac.in/result.htm")));
                break;
            case R.id.nav_git:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sahajb")));
                break;
            case R.id.nav_share:
                startActivity(new Intent(MainActivity.this, ShareActivity.class));
                break;
            case R.id.nav_email:
                Intent i = new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_SUBJECT, "Contacting regarding : ");
                startActivity(i.putExtra(Intent.EXTRA_EMAIL, new String[]{"result.visualizer@gmail.com"}));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, final Bundle args) {
        return new JsonLoader(this, rn,preferences.getBoolean("notification",true));
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject jsonObject) {
        if (jsonObject.length() == 0)
            error();
        else {
            JSONObject object = jsonObject.optJSONObject(rn);
            if (object == null)
                invalid();
            else
                valid();
        }
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
    }

    public void valid() {
        startActivity(new Intent(MainActivity.this, ResultActivity.class).putExtra(Intent.EXTRA_TEXT, rn));
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

}