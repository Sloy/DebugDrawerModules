package com.sloydev.debugdrawer.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.layer.sdk.LayerClient;
import com.sloydev.debugdrawer.layer.LayerDebugModule;
import com.sloydev.debugdrawer.logs.LogsModule;
import com.sloydev.debugdrawer.preferences.PreferencesModule;

import io.palaima.debugdrawer.view.DebugView;

public class DebugViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DebugView debugView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debugview);

        setupToolBar();


        debugView = findViewById(R.id.debug_view);

        debugView.modules(
                PreferencesModule.Companion.clearAll(),
                new LogsModule(),
                new LayerDebugModule(LayerClient.newInstance(this, "layer:///apps/staging/e9da1048-b6e0-4033-a537-293e4d2c4483"))
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
        toolbar = findViewById(R.id.mainToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        return toolbar;
    }


}
