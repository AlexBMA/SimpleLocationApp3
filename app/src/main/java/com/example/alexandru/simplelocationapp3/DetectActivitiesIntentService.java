package com.example.alexandru.simplelocationapp3;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

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



    }
}
