package com.sloydev.debugdrawer.app;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sloydev.debugdrawer.logs.LogsModule;
import com.sloydev.debugdrawer.okhttp.NetworkQualityModule;
import com.sloydev.debugdrawer.preferences.PreferencesModule;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.palaima.debugdrawer.view.DebugView;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class DebugViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DebugView debugView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debugview);

        setupToolBar();


        debugView = (DebugView) findViewById(R.id.debug_view);

        debugView.modules(
                new NetworkQualityModule(this),
                PreferencesModule.Companion.clearAll(),
                new LogsModule()
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        debugView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        debugView.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        debugView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        debugView.onStop();
    }

    protected Toolbar setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        return toolbar;
    }

    private static final int DISK_CACHE_SIZE = 20 * 1024 * 1024; // 20 MB

    private static OkHttpClient.Builder createOkHttpClientBuilder(Application app) {
        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "okhttp3");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        return new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
    }
}
