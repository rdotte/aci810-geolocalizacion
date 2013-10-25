package com.example.aci810geolocalizacion;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class MainActivity extends FragmentActivity 
	implements GooglePlayServicesClient.ConnectionCallbacks, 
	GooglePlayServicesClient.OnConnectionFailedListener, 
	LocationListener {
	
	// Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    private static final long UPDATE_INTERVAL = UPDATE_INTERVAL_IN_SECONDS * 1000;
    
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    private static final long FASTEST_INTERVAL = FASTEST_INTERVAL_IN_SECONDS * 1000;
    
    LocationClient locationClient;
    Location location;
    LocationRequest mLocationRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// location
		this.locationClient = new LocationClient(this, this, this);
		
		// Create the LocationRequest object
        this.mLocationRequest = LocationRequest.create();
        // Use high accuracy
        this.mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        this.mLocationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        this.mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
    	// call parent
        super.onStart();
        // Connect the client.
        locationClient.connect();
    }
    
    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
    	// Disconnecting the client invalidates it.
        locationClient.disconnect();
        // call parent
        super.onStop();
    }
	
	// GooglePlayServicesClient.ConnectionCallbacks implementation

	@Override
	public void onConnected(Bundle arg0) {
		// Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        // Initialize location updates
        locationClient.requestLocationUpdates(mLocationRequest, this);
	}
	
	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
	}
	
	// GooglePlayServicesClient.OnConnectionFailedListener implementation
	
	public void onConnectionFailed(ConnectionResult arg0) {
		Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
	}
	
	// com.google.android.gms.location.LocationListener implementation
	
	@Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, "Updated Location", Toast.LENGTH_SHORT).show();
        
        TextView latTextView = (TextView) findViewById(R.id.lat);
        latTextView.setText(Double.toString(location.getLatitude()));
        
        TextView lonTextView = (TextView) findViewById(R.id.lon);
        lonTextView.setText(Double.toString(location.getLongitude()));
    }
}
