package com.example.alexandru.simplelocationapp3;

import android.app.IntentService;
import android.content.Intent;

import android.provider.SyncStateContract;
import android.support.annotation.Nullable;


import com.google.android.gms.location.ActivityRecognitionResult;


/**
 * Created by Alexandru on 6/10/2017.
 */

public class DetectActivitiesIntentService extends IntentService{

    protected  final  static  String TAG = "detection_is";

    public DetectActivitiesIntentService() {

        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);


        Intent locaIntent = new Intent();



    }
}
