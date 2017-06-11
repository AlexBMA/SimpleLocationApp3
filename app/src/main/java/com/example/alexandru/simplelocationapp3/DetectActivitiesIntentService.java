package com.example.alexandru.simplelocationapp3;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;


/**
 * Created by Alexandru on 6/10/2017.
 */

public class DetectActivitiesIntentService extends IntentService{

    protected  final  static  String TAG = "detection_is";

    public DetectActivitiesIntentService() {

        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        Intent localIntent = new Intent(Constant.BROADCAST_ACTION);

        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();

        Log.e(TAG, "activities detected");

        localIntent.putExtra(Constant.ACTIVITY_EXTRA, detectedActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);


    }
}
