package com.example.alexandru.simplelocationapp3;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {


    protected final String TAG = "TAG";
    protected  GoogleApiClient mGoogleApiClient;
    protected ActivityDetectionBroadcastReceiver mBroadcastReceiver;
    private TextView textViewDetected;
    private Button buttonRequest;
    private Button buttonRemove;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDetected = (TextView) findViewById(R.id.detectedActivities);
        buttonRequest = (Button) findViewById(R.id.request_activity_updates_button);
        buttonRemove = (Button) findViewById(R.id.remove_activity_updates_button);
        mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();
        buildCreateApiClient();
    }

    public void requestActivityUpdatesButtonHandler(View view) {

        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        ActivityRecognition.ActivityRecognitionApi.
                requestActivityUpdates(mGoogleApiClient, 5, getActivityDetectionPendingIntent()).
                setResultCallback(this);

        buttonRequest.setEnabled(false);
        buttonRemove.setEnabled(true);
    }

    public void removeActivityUpdatesButtonHandler(View view) {

        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }

        ActivityRecognition.ActivityRecognitionApi.
                removeActivityUpdates(mGoogleApiClient, getActivityDetectionPendingIntent())
                .setResultCallback(this);


        textViewDetected.setText("");
        buttonRequest.setEnabled(true);
        buttonRemove.setEnabled(false);
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intend = new Intent(this, DetectActivitiesIntentService.class);

        return PendingIntent.getService(this, 0, intend, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private synchronized void buildCreateApiClient()
    {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    private String transformCodeIntoMessage(int type) {

        Resources resource = this.getResources();
        switch (type) {
            case DetectedActivity.IN_VEHICLE:
                return resource.getString(R.string.in_vehicle);
            case DetectedActivity.ON_BICYCLE:
                return resource.getString(R.string.on_bicycle);
            case DetectedActivity.ON_FOOT:
                return resource.getString(R.string.on_foot);
            case DetectedActivity.RUNNING:
                return resource.getString(R.string.running);
            case DetectedActivity.STILL:
                return resource.getString(R.string.still);
            case DetectedActivity.WALKING:
                return resource.getString(R.string.walking);
            case DetectedActivity.TILTING:
                return resource.getString(R.string.tilting);
            case DetectedActivity.UNKNOWN:
                return resource.getString(R.string.unknown);
            default:
                return resource.getString(R.string.unidentifiable_activity);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onPause() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter(Constant.BROADCAST_ACTION));
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG, "connected");


    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "connection Suspended ");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "connection Failed");


    }

    @Override
    public void onResult(@NonNull Status status) {

        if (status.isSuccess()) {
            Log.e(TAG, "SUCCESS");
        } else {
            Log.e(TAG, "Error happen");
        }
    }

    public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {

        protected static final String TAG = "receiver";

        @Override
        public void onReceive(Context context, Intent intent) {

            ArrayList<DetectedActivity> updatedActivities = intent.getParcelableArrayListExtra(Constant.ACTIVITY_EXTRA);
            int size = updatedActivities.size();

            String strStatus = "";
            for (int i = 0; i < size; i++) {
                DetectedActivity temp = updatedActivities.get(i);
                //String activityType = transformCodeIntoMessage(temp.getType());
                //int chance = temp.getConfidence();
                strStatus += transformCodeIntoMessage(temp.getType()) + " " + temp.getConfidence() + "% \n";
                Log.e(TAG, strStatus);

            }
            textViewDetected.setText(strStatus);
        }

    }
}
