package com.example.alexandru.simplelocationapp3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.location.ActivityRecognitionResult;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


    protected final String TAG = "TAG";
    protected  GoogleApiClient mGoogleApiClient;
    protected  ActivityRecognitionResult mActivityRecognitionResult;
    protected  ActivityRecognition mActivityRecognition;
    protected  ActivityRecognitionApi mActivityRecognitionApi;
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

        buildCreateApiClient();
    }

    private void buildCreateApiClient()
    {

        mGoogleApiClient =new GoogleApiClient.Builder(getApplicationContext())
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

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
}
