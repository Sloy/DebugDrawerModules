package com.sloydev.debugdrawer.app;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.layer.sdk.LayerClient;
import com.sloydev.debugdrawer.layer.LayerDebugModule;
import com.sloydev.debugdrawer.logs.LogsModule;
import com.sloydev.debugdrawer.okhttp.NetworkQualityModule;
import com.sloydev.debugdrawer.preferences.PreferencesModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.palaima.debugdrawer.DebugDrawer;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DebugDrawer debugDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolBar();


        debugDrawer = new DebugDrawer.Builder(this).modules(
                new NetworkQualityModule(this),
                PreferencesModule.Companion.clearAll(),
                new LogsModule(),
                new LayerDebugModule(LayerClient.newInstance(this, "layer:///apps/staging/e9da1048-b6e0-4033-a537-293e4d2c4483"))
        )//.withTheme(R.style.Theme_AppCompat)
                .build();

        List<String> images = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            images.add("https://unsplash.it/200/100?image=" + i);
        }

        ListView listView = (ListView) findViewById(R.id.image_list);
        listView.setAdapter(new ImageAdapter(this, images));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_debug_view_activity) {
            startActivity(new Intent(this, DebugViewActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected Toolbar setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        return toolbar;
    }

    private static final int DISK_CACHE_SIZE = 30 * 1024 * 1024; // 30 MB

    private static OkHttpClient.Builder createOkHttpClientBuilder(Application app) {
        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "okhttp3-cache");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        return new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
    }
}
