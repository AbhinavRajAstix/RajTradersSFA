package com.astix.rajtraderssfasales;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import static com.astix.rajtraderssfasales.AddNewStore_DynamicSectionWise.LattitudeFromLauncher;
import static com.astix.rajtraderssfasales.AddNewStore_DynamicSectionWise.LongitudeFromLauncher;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    MapFragment mapFrag;
    GoogleMap googleMap;
    LinkedHashMap<String, String> hmapStoreListWithNearByDistanceRange= new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapOutletListForNear = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapOutletListForNearUpdated = new LinkedHashMap<String, String>();
    public Circle circle;
    public  int flagAnyNearByStoresAvailable=0;
    FragmentManager manager;
    ImageView img_back_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        img_back_Btn = (ImageView)findViewById(R.id.img_back_Btn);

        manager = getFragmentManager();
        mapFrag = (MapFragment) manager.findFragmentById(
                R.id.map);
        mapFrag.getMapAsync(MapActivity.this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.show(mapFrag);

        img_back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        if (!LattitudeFromLauncher.equals("NA") && !LattitudeFromLauncher.equals("0.0")) {
            googleMap.clear();
            googleMap.setMyLocationEnabled(false);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)));

            Marker locationMarker = googleMap.addMarker(marker);
            locationMarker.setTag("0");
            locationMarker.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
            googleMap.setMinZoomPreference(15);
            // googleMap.moveCamera(CameraUpdateFactory.zoomIn());
         /*   googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    marker.setTitle(StoreName);
                }
            });*/
            hmapOutletListForNear = mDataSource.fnGetALLOutletMstr();
            if (hmapOutletListForNear != null) {

                for (Map.Entry<String, String> entry : hmapOutletListForNear.entrySet()) {
                    int DistanceBWPoint = 1000;
                    String outID = entry.getKey().trim();
                    //  String PrevAccuracy = entry.getValue().split(Pattern.quote("^"))[0];
                    String PrevLatitude = entry.getValue().split(Pattern.quote("^"))[0];
                    String PrevLongitude = entry.getValue().split(Pattern.quote("^"))[1];

                    // if (!PrevAccuracy.equals("0"))
                    // {
                    if (!PrevLatitude.equals("0")) {
                        try {
                            Location locationA = new Location("point A");
                            locationA.setLatitude(Double.parseDouble(LattitudeFromLauncher));
                            locationA.setLongitude(Double.parseDouble(LongitudeFromLauncher));

                            Location locationB = new Location("point B");
                            locationB.setLatitude(Double.parseDouble(PrevLatitude));
                            locationB.setLongitude(Double.parseDouble(PrevLongitude));

                            float distance = locationA.distanceTo(locationB);
                            DistanceBWPoint = (int) distance;

                            hmapOutletListForNearUpdated.put(outID, "" + DistanceBWPoint);
                        } catch (Exception e) {

                        }
                    }
                    // }
                }
            }

            if (hmapOutletListForNearUpdated != null) {
                //mDataSource.open();
                for (Map.Entry<String, String> entry : hmapOutletListForNearUpdated.entrySet()) {
                    String outID = entry.getKey().trim();
                    String DistanceNear = entry.getValue().trim();
                    if (outID.equals("853399-a1445e87daf4-NA")) {
                        System.out.println("Shvam Distance = " + DistanceNear);
                    }
                    if (!DistanceNear.equals("")) {
                        //853399-81752acdc662-NA
                        if (outID.equals("853399-a1445e87daf4-NA")) {
                            System.out.println("Shvam Distance = " + DistanceNear);
                        }
                        mDataSource.UpdateStoreDistanceNear(outID, Integer.parseInt(DistanceNear));
                    }
                }
                //mDataSource.close();
            }
            //send to storeListpage page
            //From, addr,zipcode,city,state,errorMessageFlag,username,totaltarget,todayTarget
            flagAnyNearByStoresAvailable = mDataSource.fncheckCountNearByStoreExistsOrNot(CommonInfo.DistanceRange);
            if(flagAnyNearByStoresAvailable==1)
            {
                hmapStoreListWithNearByDistanceRange=mDataSource.fnGeStoreListForAddNewStorePage(100,1);
            }

            if(flagAnyNearByStoresAvailable==1)
            {
                Random rnd = new Random();


                int i = 100;
                for (Map.Entry<String, String> map : hmapStoreListWithNearByDistanceRange.entrySet()) {
                    String lat = map.getValue().split(Pattern.quote("^"))[1];
                    String lon = map.getValue().split(Pattern.quote("^"))[2];
                    MarkerOptions options2 = new MarkerOptions();
                    options2.position(new LatLng(Double.valueOf(lat), Double.valueOf(lon))).snippet("and snippet") .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));


                    Marker marker2 = googleMap.addMarker(options2);
                    marker2.setTag(map.getKey().toString());
                    marker2.showInfoWindow();

                    // googleMap.moveCamera(CameraUpdateFactory.zoomIn());

                    int color = Color.argb(255, rnd.nextInt(256 + i), rnd.nextInt(256 + i), rnd.nextInt(256 + i));



                    CircleOptions circleOptions = new CircleOptions()
                            .center(new LatLng(Double.valueOf(lat), Double.valueOf(lon)))
                            .radius(calculateCircleRadiusMeterForMapCircle(8, new LatLng(Double.valueOf(lat), Double.valueOf(lon)).latitude, googleMap.getCameraPosition().zoom))
                            .fillColor(color)
                            .strokeColor(color)
                            .strokeWidth(0f);

                    circle = googleMap.addCircle(circleOptions);


                    i = i + 100;





                }
            }

        }
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {

        }
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker2) {
                String sdfds = marker2.getTag().toString();
                if (!sdfds.equals("0")) {
                    // marker2.setTitle(hmapStoreListWithNearByDistanceRange.get(sdfds).split(Pattern.quote("^"))[0]);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                    //searching marker id in locationDetailses and getting all the information of a particular marker

                    builder.setTitle("Route : " + hmapStoreListWithNearByDistanceRange.get(sdfds).split(Pattern.quote("^"))[5]);
                    builder.setMessage("Store : "+ hmapStoreListWithNearByDistanceRange.get(sdfds).split(Pattern.quote("^"))[0]);


                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });
                    builder.setNegativeButton(R.string.txtCancle, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return  false;
            }
        });
       /* googleMap.OnMarkerClickListener (new GoogleMap.OnMarkerClickListener () {
            @Override
            public void onInfoWindowClick(Marker marker2) {

            }
        });*/
    }

    public final double calculateCircleRadiusMeterForMapCircle(int _targetRadiusDip, double _circleCenterLatitude,
                                                               float _currentMapZoom) {
        //That base value seems to work for computing the meter length of a DIP
        double arbitraryValueForDip = 156000D;
        double oneDipDistance = Math.abs(Math.cos(Math.toRadians(_circleCenterLatitude))) * arbitraryValueForDip / Math.pow(2, _currentMapZoom);
        return oneDipDistance * (double) _targetRadiusDip;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
