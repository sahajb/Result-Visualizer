package com.example.android.resultvisualizer;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.regex.Pattern;

import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonObjFromFile;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<JSONObject> {

    private EditText getRn;

    private Button loadResult;

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
        getRn = (EditText) findViewById(R.id.getrn);
        getRn.clearFocus();
        getRn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getRn.setHint(hasFocus ? "Ex. 2016/B10/1789" : "");
            }
        });
        loadResult = (Button) findViewById(R.id.loadresult);
        loadResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Pattern.compile("(2016)/([A-B][1-9]|10)/([0-9]{2,4})").matcher(getRn.getText().toString()).matches())
                    getLoaderManager().initLoader(0, null, MainActivity.this);
                else
                    Toast.makeText(getApplicationContext(), "Roll. no. pattern is invalid", Toast.LENGTH_SHORT).show();
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (id == R.id.nav_docs) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://exam.dtu.ac.in/result.htm")));
        } else if (id == R.id.nav_git) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sahajb")));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this, ShareActivity.class));
        } else if (id == R.id.nav_email) {
            Intent i = new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto:"));
            i.putExtra(Intent.EXTRA_SUBJECT, "Contacting regarding : ");
            startActivity(i.putExtra(Intent.EXTRA_EMAIL, new String[]{"result.visualizer@gmail.com"}));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<JSONObject> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<JSONObject>(this) {
            JSONObject object;

            @Override
            protected void onStartLoading() {
                if (object != null)
                    deliverResult(object);
                else
                    forceLoad();
            }

            @Override
            public JSONObject loadInBackground() {
                return jsonObjFromFile(getApplicationContext()).optJSONObject(getRn.getText().toString());
            }

            @Override
            public void deliverResult(JSONObject jsonObject) {
                object = jsonObject;
                super.deliverResult(jsonObject);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject object) {
        if (object == null) {
            Toast.makeText(getApplicationContext(), "Please enter a valid Roll. No.", Toast.LENGTH_SHORT).show();
            getRn.setText("");
        } else
            startActivity(new Intent(MainActivity.this, ResultActivity.class).putExtra(Intent.EXTRA_TEXT,
                    getRn.getText().toString()));
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {

    }

}
