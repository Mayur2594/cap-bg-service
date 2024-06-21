package com.lninf.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

public class BackgroundService {

    private static final String TAG = "BackgroundFlutterService";

    @Override
    public void onCreate() {
        super.onCreate();

        FlutterEngine flutterEngine = new FlutterEngine(this);
        flutterEngine.getDartExecutor().executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        );

        FlutterEngineCache
            .getInstance()
            .put("background_engine", flutterEngine);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service onStartCommand");
        // Your background task code here
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FlutterEngineCache.getInstance().remove("background_engine");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
