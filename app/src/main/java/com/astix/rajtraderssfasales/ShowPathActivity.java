package com.astix.rajtraderssfasales;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.location.LocationInterface;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobal;
import com.github.pengrad.mapscaleview.MapScaleView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ShowPathActivity extends AppCompatActivity implements LocationInterface, OnMapReadyCallback {

    private MapFragment mapFragment;
    String storeID = "";
    String fnlati = "0";
    String fnlongi = "0";
    FloatingActionButton back_icon;
    HashMap<String, String> hmapStoreLatLongDistanceFlgRemap = new HashMap<String, String>();
    public AppDataSource mDataSource;
    String storeName = "";
    GoogleMap googleMap;
    String origin, destination;
    String MY_API_KEY = "AIzaSyCM-6S4IV0BGtjC-FpfFgYR0ShFOY9oBy8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_path);
        mDataSource = new AppDataSource(this);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        back_icon = (FloatingActionButton) findViewById(R.id.back_btn);


        storeID = getIntent().getExtras().getString("StoreID");


        hmapStoreLatLongDistanceFlgRemap = mDataSource.fnGeStoreList(CommonInfo.DistanceRange);


        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        locationRetrievingAndDistanceCalculating();


    }

    public void locationRetrievingAndDistanceCalculating() {
        LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
        llaaa.locationRetrievingAndDistanceCalculating(ShowPathActivity.this, true, false, 20, 0);


    }

    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {
        fnlati = fnLati;
        fnlongi = fnLongi;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        MapScaleView scaleView = (MapScaleView) findViewById(R.id.scaleView);


        LatLng myLoc = new LatLng(Double.valueOf(fnlati), Double.valueOf(fnlongi));

        Location mylocation = new Location("");
        mylocation.setLatitude(Double.valueOf(fnlati));
        mylocation.setLongitude(Double.valueOf(fnlongi));

        if (hmapStoreLatLongDistanceFlgRemap.containsKey(storeID)) {
            String lat = hmapStoreLatLongDistanceFlgRemap.get(storeID).split(Pattern.quote("^"))[0];
            String lon = hmapStoreLatLongDistanceFlgRemap.get(storeID).split(Pattern.quote("^"))[1];
            storeName = hmapStoreLatLongDistanceFlgRemap.get(storeID).split(Pattern.quote("^"))[4];
            LatLng lati = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
            Location store_loc = new Location("");
            store_loc.setLatitude(Double.valueOf(lat));
            store_loc.setLongitude(Double.valueOf(lon));
            double distance = mylocation.distanceTo(store_loc);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(myLoc);
            builder.include(lati);
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.12);
            Marker store = googleMap.addMarker(new MarkerOptions().position(lati).title(storeName).snippet("Distance = " + String.valueOf((int) (Math.ceil(distance)) + "m")));
            store.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 12));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 800, 800, 100));
            String url = getDirectionsUrl(lati, myLoc);
            FetchUrl FetchUrl = new FetchUrl();
            FetchUrl.execute(url);

        }

        CameraPosition cameraPosition = googleMap.getCameraPosition();
// need to pass zoom and latitude
        scaleView.update(cameraPosition.zoom, cameraPosition.target.latitude);
        Marker store12 = googleMap.addMarker(new MarkerOptions().position(myLoc).icon(BitmapDescriptorFactory.fromResource(R.drawable.markerwalkingicon)));


//        origin = "64.711696,12.170481";
//        LatLng origin1=new LatLng(64.711696,12.170481);
//        destination = "34.711696,2.170481";


    }

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        String mode = "mode=walking";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + MY_API_KEY;

        return url;
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DirectionsJSONParser parser = new DirectionsJSONParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            if(result!=null && result.size()>0)
            {
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<>();
                    lineOptions = new PolylineOptions();

                    // Fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // Fetching all the points in i-th route
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    // Adding all the points in the route to LineOptions
                    lineOptions.addAll(points);
                    lineOptions.width(12);
                    lineOptions.color(Color.parseColor("#0685E2"));

                    Log.d("onPostExecute", "onPostExecute lineoptions decoded");

                }

                // Drawing polyline in the Google Map for the i-th route
                if (lineOptions != null) {
                    googleMap.addPolyline(lineOptions);
                } else {
                    Log.d("onPostExecute", "without Polylines drawn");
                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        // Disable back button..............
        return false;
    }
}

