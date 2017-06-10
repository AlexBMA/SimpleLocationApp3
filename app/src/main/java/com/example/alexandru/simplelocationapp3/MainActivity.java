package com.example.alexandru.simplelocationapp3;

import android.app.PendingIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.location.ActivityRecognitionResult;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


    protected  GoogleApiClient mGoogleApiClient;

    protected  ActivityRecognitionResult mActivityRecognitionResult;
    protected  ActivityRecognition mActivityRecognition;
    protected  ActivityRecognitionApi mActivityRecognitionApi;



    protected final  int I=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
