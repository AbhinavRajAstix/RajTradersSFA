package com.astix.rajtraderssfasales;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.allana.truetime.TimeUtils;
import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.location.LocationInterface;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobal;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.regex.Pattern;

public class ActualVisitMapActivity extends BaseActivity implements LocationInterface, OnMapReadyCallback {
    public int bck = 0;
    public String selStoreName, storeID, userDate, fullFileName1, pickerDate, imei;
    private String LattitudeFromLauncher = "NA", LongitudeFromLauncher = "NA";
    private TextView tvDistance, tvGeofenceInvalid, txt_rfrshCmnt, tvAccuracy;
    int DistanceBWPoint = 1000, Distance = 0, battLevel = 0;
    double Accuracy = 0;
    int inRange = 0, isLocationRadioChecked = 0;
    private Button btn_refresh, btnActualVisit;
    private LinearLayout ll_refresh;
    private RadioGroup rg_yes_no;
    int refreshCount = 0;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            battLevel = intent.getIntExtra("level", 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_visit_map);
        registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        initViews();

    }

    private void initViews() {
        manager = getFragmentManager();
        ImageView ivBack = findViewById(R.id.ivBack);
        tvAccuracy = findViewById(R.id.tvAccuracy);
        mapFrag = (MapFragment) manager.findFragmentById(R.id.map);
        Intent passedvals = getIntent();
        pickerDate = getDateInMonthTextFormat();
        userDate = getDateInMonthTextFormat();
        fullFileName1 = TimeUtils.getNetworkDateTime(ActualVisitMapActivity.this, TimeUtils.DATE_TIME_FORMAT);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        DistanceBWPoint = mDataSource.fnGetAllowedGeofence();

        rg_yes_no = (RadioGroup) findViewById(R.id.rg_yes_no);
        tvDistance = findViewById(R.id.tvDistance);
        tvGeofenceInvalid = findViewById(R.id.tvGeofenceInvalid);
        txt_rfrshCmnt = findViewById(R.id.txt_rfrshCmnt);
        ll_refresh = findViewById(R.id.ll_refresh);
        btn_refresh = findViewById(R.id.btn_refresh);

        rg_yes_no.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != -1) {
                RadioButton radioButtonVal = (RadioButton) radioGroup.findViewById(i);
                if (radioButtonVal.getId() == R.id.rb_yes) {
                    ll_refresh.setVisibility(View.GONE);

                    isLocationRadioChecked = 1;
                } else if (radioButtonVal.getId() == R.id.rb_no) {
                    ll_refresh.setVisibility(View.VISIBLE);
                    isLocationRadioChecked = 2;
                }
            }
        });

        btn_refresh.setOnClickListener(view -> {
            boolean isGPSok = false;
            boolean isNWok = false;
            isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSok) {
//                    isGPSok = false;
                showSettingsAlert();
            }
            if (!isNWok) {
                isNWok = false;
            }
            if (!isGPSok && !isNWok) {
                try {
                    showSettingsAlert();
                } catch (Exception e) {

                }

                isGPSok = false;
                isNWok = false;
            } else {
                rg_yes_no.clearCheck();
                ll_refresh.setVisibility(View.GONE);
                refreshCount++;
                if (refreshCount == 1) {
                    txt_rfrshCmnt.setText(getString(R.string.second_msg_for_map));
                } else if (refreshCount == 2) {
                    txt_rfrshCmnt.setText(getString(R.string.third_msg_for_map));
                    btn_refresh.setVisibility(View.GONE);
                }

                LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
                llaaa.locationRetrievingAndDistanceCalculating(this, false, true, 20, 0);
            }
        });

        bck = passedvals.getIntExtra("bck", 0);
        imei = AppUtils.getToken(this);
        // aa=(StoreSelection)getIntent().getSerializableExtra("MyClass");
        storeID = passedvals.getStringExtra("storeID");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent storeIntent = new Intent(ActualVisitMapActivity.this, StoreSelection.class);
                storeIntent.putExtra("imei", imei);
                storeIntent.putExtra("userDate", userDate);
                storeIntent.putExtra("pickerDate", pickerDate);

                storeIntent.putExtra("rID", "0");
                startActivity(storeIntent);
                finish();
            }
        });
       /* ivBack.setOnClickListener(view ->


                finish());*/

        if (bck == 1) {
            selStoreName = passedvals.getStringExtra("SN");
        }
        else {
            selStoreName = passedvals.getStringExtra("selStoreName");
        }

        boolean isGPSok = false;
        boolean isNWok = false;
        isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        Button btn_telephonic = findViewById(R.id.btn_telephonic);
        btnActualVisit = findViewById(R.id.btnActualVisit);

        btnActualVisit.setOnClickListener(view -> {
            String startTS = TimeUtils.getNetworkDateTime(ActualVisitMapActivity.this, TimeUtils.DATE_TIME_FORMAT);
            if (rg_yes_no.getCheckedRadioButtonId() != R.id.rb_yes) {
                showAlertDialog("Please confirm your location");
            }/* else if (Distance > DistanceBWPoint) {
                showAlertDialog("Your location is not within the range of Shop. Please perform Telephonic Visit");
            }*/ else {
                mDataSource.UpdateStoreStartVisit(storeID, startTS);

                String passdLevel = battLevel + "%";
                mDataSource.UpdateStoreVisitBatt(storeID, passdLevel);
                mDataSource.updateflgOrderTypeIntblStoreList(storeID, 1);

                Intent ready4GetLoc = new Intent(ActualVisitMapActivity.this, LastVisitDetails.class);

                ready4GetLoc.putExtra("storeID", storeID);
                ready4GetLoc.putExtra("selStoreName", selStoreName);
                ready4GetLoc.putExtra("token", imei);
                ready4GetLoc.putExtra("userDate", userDate);
                ready4GetLoc.putExtra("pickerDate", pickerDate);
                ready4GetLoc.putExtra("startTS", fullFileName1);
                ready4GetLoc.putExtra("inRange", inRange);
                ready4GetLoc.putExtra("isLocationRadioChecked", isLocationRadioChecked);
                ready4GetLoc.putExtra("bck", 0);

                startActivity(ready4GetLoc);
                finish();
            }
        });

        btn_telephonic.setOnClickListener(view -> {
            String startTS = TimeUtils.getNetworkDateTime(ActualVisitMapActivity.this, TimeUtils.DATE_TIME_FORMAT);

            mDataSource.UpdateStoreStartVisit(storeID, startTS);

            String passdLevel = battLevel + "%";
            mDataSource.UpdateStoreVisitBatt(storeID, passdLevel);
            mDataSource.updateflgOrderTypeIntblStoreList(storeID, 0);

            mDataSource.UpdateStoreEndVisit(storeID, startTS);
            Intent ready4GetLoc = new Intent(ActualVisitMapActivity.this, LastVisitDetails.class);

            ready4GetLoc.putExtra("storeID", storeID);
            ready4GetLoc.putExtra("selStoreName", selStoreName);
            ready4GetLoc.putExtra("token", imei);
            ready4GetLoc.putExtra("userDate", userDate);
            ready4GetLoc.putExtra("pickerDate", pickerDate);
            ready4GetLoc.putExtra("startTS", fullFileName1);
            ready4GetLoc.putExtra("bck", 0);

            startActivity(ready4GetLoc);
            finish();
        });

        int isLocationRadioCheckedVal = mDataSource.getVisitisLocationRadioChecked(storeID);
        if (isLocationRadioCheckedVal == 1){
            RadioButton rb_yes = findViewById(R.id.rb_yes);
            rb_yes.setChecked(true);
            isLocationRadioChecked = 1;
        }
        else if (isLocationRadioCheckedVal == 2){
            RadioButton rb_no = findViewById(R.id.rb_no);
            rb_no.setChecked(true);
            isLocationRadioChecked = 2;

        }
        if (!isGPSok && !isNWok) {
            try {
                showSettingsAlert();
            } catch (Exception e) {

            }
        } else {
            locationRetrievingAndDistanceCalculating();
        }



      /*  int ISNewStore = mDataSource.fncheckStoreIsNewOrOld(storeID);
        if(ISNewStore==1)
        {

            Intent ready4GetLoc = new Intent(ActualVisitMapActivity.this, LastVisitDetails.class);

            ready4GetLoc.putExtra("storeID", storeID);
            ready4GetLoc.putExtra("selStoreName", selStoreName);
            ready4GetLoc.putExtra("token", imei);
            ready4GetLoc.putExtra("userDate", userDate);
            ready4GetLoc.putExtra("pickerDate", pickerDate);
            ready4GetLoc.putExtra("startTS", fullFileName1);
            ready4GetLoc.putExtra("inRange", inRange);
            ready4GetLoc.putExtra("isLocationRadioChecked", isLocationRadioChecked);
            ready4GetLoc.putExtra("bck", 0);

            startActivity(ready4GetLoc);
            finish();
        }*/

    }

    public void locationRetrievingAndDistanceCalculating() {
        LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
        llaaa.locationRetrievingAndDistanceCalculating(this, true, true, 20, 1);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(R.string.genTermGPSDisablePleaseEnable);

        alertDialog.setPositiveButton(R.string.AlertDialogOkButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.show();
    }

    public void showAlertDialog(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);

        alertDialog.setPositiveButton(R.string.AlertDialogOkButton, (dialog, which) -> {
        });
        alertDialog.show();
    }

    FragmentManager manager;
    MapFragment mapFrag;

    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {
        String storeLatLong = mDataSource.fnGetALLOutletMstrOfParticularStore(storeID);

        if(finalAccuracy==null)
        {
            finalAccuracy="0";
        }

        CommonInfo.hmapStoreLocationDetails.put("ActualLatitude",String.valueOf(fnLati));
        CommonInfo.hmapStoreLocationDetails.put("ActualLongitude",String.valueOf(fnLongi));
        CommonInfo.hmapStoreLocationDetails.put("Accuracy",String.valueOf(finalAccuracy));
        CommonInfo.hmapStoreLocationDetails.put("LocProvider",String.valueOf(fnAccurateProvider));
        CommonInfo.hmapStoreLocationDetails.put("flgLocationServicesOnOff",""+flgLocationServicesOnOff);
        CommonInfo.hmapStoreLocationDetails.put("flgGPSOnOff",""+flgGPSOnOff);
        CommonInfo.hmapStoreLocationDetails.put("flgNetworkOnOff",""+flgNetworkOnOff);
        CommonInfo.hmapStoreLocationDetails.put("flgFusedOnOff",""+flgFusedOnOff);
        CommonInfo.hmapStoreLocationDetails.put("flgInternetOnOffWhileLocationTracking",""+flgInternetOnOffWhileLocationTracking);
        CommonInfo.hmapStoreLocationDetails.put("VisitTimeCheckStore","01-Jan-1900");


        CommonInfo.hmapStoreLocationDetails.put("fnLati",String.valueOf(fnLati));
        CommonInfo.hmapStoreLocationDetails.put("fnLongi",String.valueOf(fnLongi));
        CommonInfo.hmapStoreLocationDetails.put("fnAccuracy",String.valueOf(finalAccuracy));
        CommonInfo.hmapStoreLocationDetails.put("flgLocNotFound","0");
        CommonInfo.hmapStoreLocationDetails.put("fnAccurateProvider",String.valueOf(fnAccurateProvider));
        CommonInfo.hmapStoreLocationDetails.put("fnAddress",String.valueOf(fnAddress));
        CommonInfo.hmapStoreLocationDetails.put("AllProvidersLocation",String.valueOf(AllProvidersLocation));
        CommonInfo.hmapStoreLocationDetails.put("GpsLat",String.valueOf(GpsLat));
        CommonInfo.hmapStoreLocationDetails.put("GpsLong",String.valueOf(GpsLong));
        CommonInfo.hmapStoreLocationDetails.put("GpsAccuracy",String.valueOf(GpsAccuracy));
        CommonInfo.hmapStoreLocationDetails.put("GpsAddress",String.valueOf(GpsAddress));
        CommonInfo.hmapStoreLocationDetails.put("NetwLat",String.valueOf(NetwLat));
        CommonInfo.hmapStoreLocationDetails.put("NetwLong",String.valueOf(NetwLong));
        CommonInfo.hmapStoreLocationDetails.put("NetwAccuracy",String.valueOf(NetwAccuracy));
        CommonInfo.hmapStoreLocationDetails.put("NetwAddress",String.valueOf(NetwAddress));
        CommonInfo.hmapStoreLocationDetails.put("FusedLat",String.valueOf(FusedLat));
        CommonInfo.hmapStoreLocationDetails.put("FusedLong",String.valueOf(FusedLong));
        CommonInfo.hmapStoreLocationDetails.put("FusedAccuracy",String.valueOf(FusedAccuracy));
        CommonInfo.hmapStoreLocationDetails.put("FusedLocationLatitudeWithFirstAttempt",String.valueOf(FusedLocationLatitudeWithFirstAttempt));
        CommonInfo.hmapStoreLocationDetails.put("FusedLocationLongitudeWithFirstAttempt",String.valueOf(FusedLocationLongitudeWithFirstAttempt));
        CommonInfo.hmapStoreLocationDetails.put("FusedLocationAccuracyWithFirstAttempt",String.valueOf(FusedLocationAccuracyWithFirstAttempt));


        LattitudeFromLauncher = String.valueOf(fnLati);
        LongitudeFromLauncher = String.valueOf(fnLongi);
        int ISNewStore = mDataSource.fncheckStoreIsNewOrOld(storeID);
        if(ISNewStore==1)
        {

            Intent ready4GetLoc = new Intent(ActualVisitMapActivity.this, LastVisitDetails.class);

            ready4GetLoc.putExtra("storeID", storeID);
            ready4GetLoc.putExtra("selStoreName", selStoreName);
            ready4GetLoc.putExtra("token", imei);
            ready4GetLoc.putExtra("userDate", userDate);
            ready4GetLoc.putExtra("pickerDate", pickerDate);
            ready4GetLoc.putExtra("startTS", fullFileName1);
            ready4GetLoc.putExtra("inRange", inRange);
            ready4GetLoc.putExtra("isLocationRadioChecked", isLocationRadioChecked);
            ready4GetLoc.putExtra("bck", 0);

            startActivity(ready4GetLoc);
            finish();
        }
        else {

            if (storeLatLong != null && !storeLatLong.equalsIgnoreCase("")) {
                String StoreLatitude = storeLatLong.split(Pattern.quote("^"))[0];
                String StoreLongitude = storeLatLong.split(Pattern.quote("^"))[1];

//            if (!StoreLatitude.equals("0") && !StoreLongitude.equals("0")) {
                try {
                    Location locationA = new Location("point A");
                    locationA.setLatitude(Double.parseDouble(fnLati));
                    locationA.setLongitude(Double.parseDouble(fnLongi));


                    Location locationB = new Location("point B");
                    locationB.setLatitude(Double.parseDouble(StoreLatitude));
                    locationB.setLongitude(Double.parseDouble(StoreLongitude));

                    float distance = locationA.distanceTo(locationB);
                    Distance = (int) distance;
                    Accuracy = Double.parseDouble(finalAccuracy);
                    tvAccuracy.setText("GPS Accuracy : " + (int) Double.parseDouble(finalAccuracy) + " meters");
                    tvDistance.setText("My Distance From Store : " + (int) distance + " meters");
                    mapFrag.getMapAsync(ActualVisitMapActivity.this);
                    if ((Distance - Accuracy) > DistanceBWPoint) {
                        tvGeofenceInvalid.setVisibility(View.VISIBLE);
                        btnActualVisit.setText("Offsite Visit");
                        inRange = 0;
                    } else {
                        btnActualVisit.setText("OnSite Visit");
                        inRange = 1;
                        tvGeofenceInvalid.setVisibility(View.GONE);
                    }


                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.show(mapFrag);

                } catch (Exception e) {

                }
//            }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (!LattitudeFromLauncher.equals("NA") && !LattitudeFromLauncher.equals("0.0")) {
            googleMap.clear();
            try {
                googleMap.setMyLocationEnabled(false);
            } catch (SecurityException e) {

            }
            String storeLatLong = mDataSource.fnGetALLOutletMstrOfParticularStore(storeID);
           // if (storeLatLong != null && !storeLatLong.equalsIgnoreCase("")) {
                String StoreLatitude = storeLatLong.split(Pattern.quote("^"))[0];
                String StoreLongitude = storeLatLong.split(Pattern.quote("^"))[1];
                String storeName="";
                LatLng lati = new LatLng(Double.valueOf(StoreLatitude), Double.valueOf(StoreLongitude));
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(lati);
                builder.include(lati);
                LatLngBounds bounds = builder.build();
                int height = 150;
                int width = 150;
                BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.pin);

                Bitmap b = bitmapdraw.getBitmap();

                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);


                Location locationB = new Location("point B");
                locationB.setLatitude(Double.parseDouble(StoreLatitude));
                locationB.setLongitude(Double.parseDouble(StoreLongitude));
                Marker store = googleMap.addMarker(new MarkerOptions().position(lati).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
               store.setAlpha(0.5f);
                store.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lati, 15));
               /* googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lati, 12));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 800, 800, 100));*/
            //}


            MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            Marker locationMarker = googleMap.addMarker(marker);
            locationMarker.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.unregisterReceiver(this.mBatInfoReceiver);

    }
}