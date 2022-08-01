package com.astix.rajtraderssfasales;



import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.ResultReceiver;
import android.provider.Settings;
import androidx.annotation.IdRes;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.location.LocationInterface;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobal;
import com.astix.rajtraderssfasales.model.TblDuplicateContact;
import com.astix.rajtraderssfasales.model.TblPDARouteChangeApproval;
import com.astix.rajtraderssfasales.sync.SyncJobService;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.RandomString;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
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
import com.kaopiz.kprogresshud.KProgressHUD;
import com.allana.truetime.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.UUID;
import java.util.regex.Pattern;


public class AddNewStore_DynamicSectionWise extends BaseActivity implements LocationInterface, SearchListCommunicator, OnMapReadyCallback, CategoryCommunicatorCityState, Camera.PictureCallback,InterfaceRetrofitNewStoreNumberValidation {

    public int  flgStopAddingStoreOrWaringResult = 0;
    public int  flgAnyConditionFullFilled = 0;
    LinkedHashMap<String, String> hmapStoreListWithNearByDistanceRange= new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapOutletListForNear = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapOutletListForNearUpdated = new LinkedHashMap<String, String>();
    public Circle circle;
    public  int flagAnyNearByStoresAvailable=0;

    String FusedLat = "0";
    String FusedLong = "0";
    String FusedAccuracy = "0";
    String FusedAddress = "0";
    String NetwLat = "0";
    String NetwLong = "0";
    String NetwAccuracy = "0";
    String NetwAddress = "0";
    String GpsLat = "0";
    String GpsLong = "0";
    String GpsAccuracy = "0";
    String GpsAddress = "0";
    public int flgOrderOrOnlyAdd = 0;
    int IsComposite = 0;
    int flgCheckNewOldStore = 0;
    String StoreNameFromBack = "";
    public String StoreVisitCode="";
    public static String currentBeatName = "All";
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1; // 1 second
    private static final long MIN_TIME_BW_UPDATESNew = 100 * 1; // 1 second
    public static String OwnerName, StoreContactNo, StoreCatType;
    public static int flgRuleTaxVal, flgTransType;
    public static String distID = "0";
    public static String fnAccurateProvider = "";
    public static String fnLati = "0";
    public static String fnLongi = "0";
    public static Double fnAccuracy = 0.0;
    public static String prvsStoreId = "";
    public static String channelOptId;
    public static String address, pincode, city, state, latitudeToSave, longitudeToSave, accuracyToSave;
    public static String storeNameToShow = "";
    public static int StoreTypeTradeChannel = 0;
    public static int flgLocationServicesOnOff = 0;
    public static int flgGPSOnOff = 0;
    public static int flgNetworkOnOff = 0;
    public static int flgFusedOnOff = 0;
    public static int flgInternetOnOffWhileLocationTracking = 0;
    public static int flgRestart = 0;
    public static int flgStoreOrder = 0;
    public static int ServiceWorkerDataComingFlag = 0;
    public static String ServiceWorkerStoreID = "";
    public static String ServiceWorkerResultSet = "";
    public static int battLevel;
    public static String fetchAddress = "";
    public static LinkedHashMap<String, ArrayList<String>> hmapSection_key = new LinkedHashMap<String, ArrayList<String>>();
    public static LinkedHashMap<String, String> hmapDpndtQustGrpId = new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, String> hmapQuesIdandGetAnsCntrlType = new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, String> hmapQuesIdValues = new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, ArrayList<String>> hmapQuesGropKeySection = new LinkedHashMap<String, ArrayList<String>>();
    public static LinkedHashMap<String, String> hmapGroupId_Desc = new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, ArrayList<String>> hmapSctnId_GrpId = new LinkedHashMap<String, ArrayList<String>>();
    public static String date_value = "";
    public static String pickerDate = "";
    public static String imei;
    public static String rID = "0";
    public static String activityFrom = "";
    public static String VisitStartTS = "NA";
    public static String selStoreID = "0";
    public static String FLAG_NEW_UPDATE = "";
    public static String LattitudeFromLauncher = "NA";
    public static String LongitudeFromLauncher = "NA";
    public static String AccuracyFromLauncher = "NA";
    public static String ProviderFromLauncher = "NA";
    public static String GpsLatFromLauncher = "NA";
    public static String GpsLongFromLauncher = "NA";
    public static String GpsAccuracyFromLauncher = "NA";
    public static String NetworkLatFromLauncher = "NA";
    public static String NetworkLongFromLauncher = "NA";
    public static String NetworkAccuracyFromLauncher = "NA";
    public static String FusedLatFromLauncher = "NA";
    public static String FusedLongFromLauncher = "NA";
    public static String FusedAccuracyFromLauncher = "NA";
    public static String fnAddressFromLauncher = "NA";
    public static String AllProvidersLocationFromLauncher = "NA";
    public static String GpsAddressFromLauncher = "NA";
    public static String NetwAddressFromLauncher = "NA";
    public static String FusedAddressFromLauncher = "NA";
    public static String FusedLocationLatitudeWithFirstAttemptFromLauncher = "NA";
    public static String FusedLocationLongitudeWithFirstAttemptFromLauncher = "NA";
    public static String FusedLocationAccuracyWithFirstAttemptFromLauncher = "NA";
    private final long startTime = 15000;
    private final long interval = 200;
    public String flgGSTCapture = "1";
    public String flgGSTCompliance = "0";
    public String GSTNumber = "NA";
    public int flgGSTRecordFromServer = 0;
    public String newfullFileName;
    public String QuestIDForOutChannel = "0";

    public int flgHasQuote = 0;
    public int flgAllowQuotation = 1;
    public int flgSubmitFromQuotation = 0;
    public String OutletID = "NA";
    public String StoreName = "NA", fnlStoreType = "0", fnlOwnerName = "NA", fnlMobileNumber = "NA", fnlAddressName = "NA", fnSalesPersonName = "NA", fnSalesPersonContactNo = "NA", fnStoreCatType = "NA";
    public String StoreName_tag = "NA";
    public int checkSecondTaskStatus = 0;
    public String FusedLocationLatitudeWithFirstAttempt = "0";
    public String FusedLocationLongitudeWithFirstAttempt = "0";
    public String FusedLocationAccuracyWithFirstAttempt = "0";
    public String AllProvidersLocation = "";
    public String FusedLocationLatitude = "0";
    public String FusedLocationLongitude = "0";
    public String FusedLocationProvider = "";
    public String FusedLocationAccuracy = "0";
    public String GPSLocationLatitude = "0";
    public String GPSLocationLongitude = "0";
    public String GPSLocationProvider = "";
    public String GPSLocationAccuracy = "0";
    public String NetworkLocationLatitude = "0";
    public String NetworkLocationLongitude = "0";
    public String NetworkLocationProvider = "";
    public String NetworkLocationAccuracy = "0";

    public Timer timer;

    public ProgressDialog pDialogGetStores;
    public int chkFlgForErrorToCloseApp = 0;
    public LocationManager locationManager;
    public float Current_acc;
    public Location location;
    public ProgressDialog pDialog2STANDBY;
    public LocationListener locationListener;
    public double latitude; // latitude
    public double longitude; // longitude
    public boolean isGPSEnabled = false;
    public boolean isNetworkEnabled = false;
    public PowerManager pm;
    public PowerManager.WakeLock wl;
    public float acc = 0F;
    public String lastKnownLocLatitude = "";
    public String lastKnownLocLongitude = "";
    // public static LinkedHashMap<String, String> hmapOptionId_OptionValue=new LinkedHashMap<String, String>();
    public String accuracy = "0";
    public String locationProvider = "Default";
    public ProgressDialog pDialogSync;
    public int sectionToShowHide = 1;
    String allValuesOfPaymentStageID = "0";
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    LocationRequest mLocationRequest;
    String fusedData;
    TextView txtAddress, txtAccuracy, txtLong, txtLat, txt_internetProb;
    RelativeLayout rl_sectionMap, rl_sectionQuest;
    String VisitEndTS;
    String CustomStringForServiceWorker = "";
    String CustomStoreID = "NA";
  //  LocationVo locVo;
    MapFragment mapFrag;
    GoogleMap googleMap;
    Button btnSubmit;
    int countSubmitClicked = 0;
    String locProvider = "None";
    LinearLayout ll_allData, ll_locmsg;
    AddressResultReceiver mResultReceiver;
    NewStoreForm newStore_Fragment;
    FragmentManager manager;
    FragmentTransaction fragTrans;
    ImageView img_next, img_bookOrder;
    TextView txt_Next;
    LinkedHashMap<String, String> hmapStoreQuestAnsNew = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapStoreAddress = new LinkedHashMap<String, String>();
    LinearLayout ll_next, ll_back, ll_save_Exit, ll_map, ll_refresh, ll_bookOrder;
    int refreshCount = 0;
    RadioGroup rg_yes_no;
    RadioButton rb_yes, rb_no;
    Button btn_refresh;
    TextView txt_rfrshCmnt;
    ImageView img_exit;
    String AddressFromLauncher = "NA";
    String CityFromLauncher = "NA";
    String PincodeFromLauncher = "NA";
    String StateFromLauncher = "NA";
    public int flgVisitCollectionMarkedStatus=0;
    //    DatabaseAssistant DA;
    private boolean mIsServiceStarted = false;
    public static Integer SelectedRouteIDFromStoreSelection = 0;

    Button full_map;
    Context ctx;

    //private MyService mMyService;
    public Context getCtx() {
        return ctx;
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {


            battLevel = intent.getIntExtra("level", 0);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
       /* if (mIsServiceStarted) {
            mIsServiceStarted = false;
            stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
        }*/
        this.unregisterReceiver(this.mBatInfoReceiver);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            // finish();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newstoredynamicsectionwise);
        ctx = this;
        refreshCount = 0;
        channelOptId = "0";
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        // mDataSource=new AppDataSource(AddNewStore_DynamicSectionWise.this);
     //   locVo = new LocationVo();
        prvsStoreId = "";
        address = "";
        pincode = "";
        city = "";
        state = "";
        latitudeToSave = "";
        longitudeToSave = "";
        accuracyToSave = "";
        FLAG_NEW_UPDATE = "";

        LattitudeFromLauncher = "NA";
        LongitudeFromLauncher = "NA";
        AccuracyFromLauncher = "NA";

        ProviderFromLauncher = "NA";
        GpsLatFromLauncher = "NA";
        GpsLongFromLauncher = "NA";
        GpsAccuracyFromLauncher = "NA";
        NetworkLatFromLauncher = "NA";
        NetworkLongFromLauncher = "NA";
        NetworkAccuracyFromLauncher = "NA";
        FusedLatFromLauncher = "NA";
        FusedLongFromLauncher = "NA";
        FusedAccuracyFromLauncher = "NA";

        fnAddressFromLauncher = "NA";
        AllProvidersLocationFromLauncher = "NA";
        GpsAddressFromLauncher = "NA";
        NetwAddressFromLauncher = "NA";
        FusedAddressFromLauncher = "NA";
        FusedLocationLatitudeWithFirstAttemptFromLauncher = "NA";
        FusedLocationLongitudeWithFirstAttemptFromLauncher = "NA";
        FusedLocationAccuracyWithFirstAttemptFromLauncher = "NA";


        boolean isGPSok = false;
        boolean isNWok = false;
        VisitStartTS = "";
       /* isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        long syncTIMESTAMP1 = System.currentTimeMillis();
        Date dateobj1 = new Date(syncTIMESTAMP1);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        VisitStartTS = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_TIME_FORMAT);
        //int flgCheckNewOldStore=0;
        if (!isGPSok) {
//            isGPSok = false;
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
        }*/
        storeNameToShow = "";
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Intent extras = getIntent();
        FLAG_NEW_UPDATE = extras.getStringExtra("FLAG_NEW_UPDATE");
        activityFrom = extras.getStringExtra("activityFrom");
        if (FLAG_NEW_UPDATE.equals("UPDATE")) {
            selStoreID = extras.getStringExtra("StoreID");
            StoreName = extras.getStringExtra("StoreName");
            storeNameToShow = StoreName;
            // currentBeatName="All";
            currentBeatName = extras.getStringExtra("CurrntRouteName");
        } else {
            currentBeatName = extras.getStringExtra("CurrntRouteName");

            flgCheckNewOldStore = 1;
            selStoreID = extras.getStringExtra("storeID");
            if (selStoreID.equals("0")) {
                flgCheckNewOldStore = 1;
                selStoreID = genTempID();
            }
            StoreNameFromBack = extras.getStringExtra("StoreName");
            if (StoreNameFromBack.equals("NA") || StoreNameFromBack.equals("")) {
                StoreNameFromBack = "";
            }
            StoreName = StoreNameFromBack;


            activityFrom = extras.getStringExtra("activityFrom");
            date_value = extras.getStringExtra("userdate");
            pickerDate = extras.getStringExtra("pickerDate");
            imei = AppUtils.getToken(this);



        }

        rID = extras.getStringExtra("rID");
        SelectedRouteIDFromStoreSelection = Integer.parseInt(rID);
        full_map = (Button)findViewById(R.id.full_map);
       /* Intent extras = getIntent();
        FLAG_NEW_UPDATE=  extras.getStringExtra("FLAG_NEW_UPDATE");
        if(FLAG_NEW_UPDATE.equals("UPDATE"))
        {
            selStoreID=  extras.getStringExtra("StoreID");
            StoreName=    extras.getStringExtra("StoreName");
            storeNameToShow=StoreName;
        }
        else
        {
            flgCheckNewOldStore=1;
            selStoreID=genTempID();

        }


        if(extras !=null)
        {


            date_value="28-Jun-2017";
            pickerDate= "28-Jun-2017";
            //imei="123";
            rID="1";
        }*/


        //Intent extras = getIntent();


        imei = AppUtils.getToken(this);

        checkHighAccuracyLocationMode(AddNewStore_DynamicSectionWise.this);
        ////mDataSource.open();

        //mDataSource.close();

        prvsStoreId = mDataSource.getPreviousStoreId();




		/*  PackageManager m = getPackageManager();
		    if (m.hasSystemFeature(PackageManager.FEATURE_LOCATION)) {

		    	m.setApplicationEnabledSetting(PackageManager.FEATURE_LOCATION,PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 0);
		    }*/
        mResultReceiver = new AddressResultReceiver(new Handler());
        //   ll_allData=(LinearLayout) findViewById(R.id.ll_allData);

        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_next = (LinearLayout) findViewById(R.id.ll_next);
        ll_bookOrder = (LinearLayout) findViewById(R.id.ll_bookOrder);
        ll_save_Exit = (LinearLayout) findViewById(R.id.ll_save_Exit);
        img_next = (ImageView) findViewById(R.id.img_next);
        img_bookOrder = (ImageView) findViewById(R.id.img_bookOrder);
        img_exit = (ImageView) findViewById(R.id.img_exit);
        txt_Next = (TextView) findViewById(R.id.txt_Next);
        ll_map = (LinearLayout) findViewById(R.id.ll_map);
        rg_yes_no = (RadioGroup) findViewById(R.id.rg_yes_no);
        rb_yes = (RadioButton) findViewById(R.id.rb_yes);
        rb_no = (RadioButton) findViewById(R.id.rb_no);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        txt_rfrshCmnt = (TextView) findViewById(R.id.txt_rfrshCmnt);
        ll_refresh = (LinearLayout) findViewById(R.id.ll_refresh);
        //  rl_sectionQuest=(RelativeLayout) findViewById(R.id.rl_sectionQuest);


        fillHmapData();
       // addFragment();
        rg_yes_no.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i != -1) {
                    RadioButton radioButtonVal = (RadioButton) radioGroup.findViewById(i);
                    if (radioButtonVal.getId() == R.id.rb_yes) {
                        ll_refresh.setVisibility(View.GONE);
                    } else if (radioButtonVal.getId() == R.id.rb_no) {
                        ll_refresh.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
        btn_refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


               /* if (mIsServiceStarted)
                {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }*/
                boolean isGPSok = false;
                boolean isNWok = false;
                isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSok && !isNWok) {
                    try {
                        showSettingsAlert();
                    } catch (Exception e) {

                    }
                    isGPSok = false;
                    isNWok = false;
                } else {


                    // mapFrag.getMapAsync(AddNewStore_DynamicSectionWise.this);
                    mapFrag.getView().setVisibility(View.GONE);
                   /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.hide(mapFrag);*/
                    locationRetrievingAndDistanceCalculating();
                }


                refreshCount++;
                if (refreshCount == 1) {
                    txt_rfrshCmnt.setText(getString(R.string.second_msg_for_map));
                } else if (refreshCount == 2) {
                    txt_rfrshCmnt.setText(getString(R.string.third_msg_for_map));
                    btn_refresh.setVisibility(View.GONE);
                }
                rg_yes_no.clearCheck();
                ll_refresh.setVisibility(View.GONE);

            }
        });
        full_map.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startintent = new Intent(AddNewStore_DynamicSectionWise.this, MapActivity.class);
                startActivity(startintent);
            }
        });
        img_exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //mDataSource.fnDeletesaveNewOutletFromOutletMstr(selStoreID);
               /* if (mIsServiceStarted) {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }*/
                getSectionNextOrBack(4, sectionToShowHide);
                NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                StoreName = recFragment.currentStoreName;

            }
        });

        ll_save_Exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //
                getSectionNextOrBack(2, sectionToShowHide);
                NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                StoreName = recFragment.currentStoreName;



              /*  if (mIsServiceStarted) {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }
*/


            }
        });
      /*  btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showSubmitConfirm();

            }
        });*/

        ll_bookOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flgOrderOrOnlyAdd = 0;
                hideSoftKeyboard(v);

                NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                if (recFragment != null) {

                    if (recFragment.validate() && recFragment.validateNameFilled()) {
                        StoreName = recFragment.currentStoreName;
                        showSubmitConfirm(1);

                    }

                }
            }
        });
        ll_next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                hideSoftKeyboard(v);
                flgOrderOrOnlyAdd = 0;
                sectionToShowHide=1;
                ll_bookOrder.setVisibility(View.GONE);
                if (sectionToShowHide < hmapSctnId_GrpId.size() - 1) {
                    boolean isNextMoved = getSectionNextOrBack(0, sectionToShowHide);
                    if (isNextMoved) {
                        if (ll_map.getVisibility() == View.VISIBLE) {
                            ll_map.setVisibility(View.GONE);
                        }
                        sectionToShowHide++;
                        NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                        StoreName = recFragment.currentStoreName;
                        if (sectionToShowHide != 1) {
                            if (ll_back.getVisibility() == View.INVISIBLE) {
                                ll_back.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                } else if (sectionToShowHide > hmapSctnId_GrpId.size() - 1) {


                    //NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                    NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                    if (recFragment != null) {

                      /* if(channelOptId.equals("0-2-80") || channelOptId.equals("0-3-80"))
                       {
                           if(recFragment.validate() && recFragment.validatePaymentStageID())
                           {
                               showSubmitConfirm();
                           }
                       }
                      else
                       {*/
                        ll_bookOrder.setVisibility(View.GONE);
                        if (recFragment.validate() && recFragment.validateNameFilled()) {
                            StoreName = recFragment.currentStoreName;

                            showSubmitConfirm(2);
                            //28.4866451,77.1022041,10.0
                            // 28.4866037     77.1021193   24.066999435424805
                            //  if(!checkLastFinalLoctionIsRepeated("28.4866037","77.1021193","24.066999435424805"))
                             /*if(!checkLastFinalLoctionIsRepeated(LattitudeFromLauncher,LongitudeFromLauncher,AccuracyFromLauncher))
                               {
                                   fnCreateLastKnownFinalLocation(LattitudeFromLauncher,LongitudeFromLauncher,AccuracyFromLauncher);
                                   showSubmitConfirm();
                               }
                               else
                               {
                                   if(countSubmitClicked==0)
                                   {
                                       AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);
                                       // Setting Dialog Title
                                       alertDialog.setTitle("Information");
                                       alertDialog.setIcon(R.drawable.error_info_ico);
                                       alertDialog.setCancelable(false);
                                       // Setting Dialog Message
                                       alertDialog.setMessage("Your current location is same as previous, so please turn off your location services then turn on, it back again.");

                                       // On pressing Settings button
                                       alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                           public void onClick(DialogInterface dialog, int which) {
                                               Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                               startActivity(intent);
                                           }
                                       });

                                       // Showing Alert Message
                                       alertDialog.show();
                                   }
                                   else
                                   {
                                       locationRetrievingAndDistanceCalculating();
                                   }
                                   countSubmitClicked++;
                               }*/


                        }
                        // }

                    }

                 /*   if(recFragment.validate() && recFragment.validatePaymentStageID())
                    {

                       // getSectionNextOrBack(5,sectionToShowHide );


                    }*/


                } else {
                    NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                    StoreName = recFragment.currentStoreName;
                    boolean isNextMoved = getSectionNextOrBack(0, sectionToShowHide);
                    if (isNextMoved) {
                        if (ll_map.getVisibility() == View.VISIBLE) {
                            ll_map.setVisibility(View.GONE);
                        }
                        if (sectionToShowHide == hmapSctnId_GrpId.size() - 1) {
                            sectionToShowHide++;
                            img_next.setImageResource(R.drawable.done);
                            txt_Next.setText(getText(R.string.txtDone));

                            ll_bookOrder.setVisibility(View.GONE);

                        }

                        if (ll_back.getVisibility() == View.INVISIBLE) {
                            ll_back.setVisibility(View.VISIBLE);
                        }


                    }
                }


            }
        });

        ll_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sectionToShowHide--;
                getSectionNextOrBack(1, sectionToShowHide);
                NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                StoreName = recFragment.currentStoreName;


                if (sectionToShowHide == 1) {
                    if (ll_back.getVisibility() == View.VISIBLE) {
                        ll_back.setVisibility(View.INVISIBLE);
                        if (ll_map.getVisibility() == View.GONE) {
                            ll_map.setVisibility(View.VISIBLE);
                        }
                    }

                }
                if (sectionToShowHide < hmapSctnId_GrpId.size()) {
                    img_next.setImageResource(R.drawable.next);
                    txt_Next.setText(getText(R.string.lastvisitdetails_next));
                }

            }
        });
        ll_map.setVisibility(View.VISIBLE);
       /* manager = getFragmentManager();
        mapFrag = (MapFragment) manager.findFragmentById(R.id.map);
        mapFrag.getView().setVisibility(View.VISIBLE);
        mapFrag.getMapAsync(AddNewStore_DynamicSectionWise.this);*/
       /* manager = getFragmentManager();
        mapFrag = (MapFragment) manager.findFragmentById(R.id.map);*/
      /*  mapFrag.getMapAsync(AddNewStore_DynamicSectionWise.this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.show(mapFrag);*/



        locationRetrievingAndDistanceCalculating();
        if (sectionToShowHide == hmapSctnId_GrpId.size()) {

            img_next.setImageResource(R.drawable.done);
            txt_Next.setText(getText(R.string.txtDone));
            ll_bookOrder.setVisibility(View.GONE);
            sectionToShowHide++;

        }
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean getSectionNextOrBack(int isNextPressed, int sectionToShowOrHide) {
        boolean isNextMoved = false;
        NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
        if (null != recFragment) {
            isNextMoved = recFragment.nextOrBackSection(isNextPressed, sectionToShowOrHide);
        }
        return isNextMoved;
    }

    private void addFragment() {

        newStore_Fragment = new NewStoreForm();
        manager = getFragmentManager();
        fragTrans = manager.beginTransaction();
        fragTrans.replace(R.id.fragmentForm, newStore_Fragment, "NewStoreFragment");

        fragTrans.commit();

    }

    private void fillHmapData() {
        new AsyncTask<Void,Void,Void>(){
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog  = ProgressDialog.show(AddNewStore_DynamicSectionWise.this,"","Please Wait...");
            }

            @Override
            protected Void doInBackground(Void... voids) {
                SharedPreferences sPref = getSharedPreferences("LanguagePref", MODE_PRIVATE);

                String allLoctionDetails = mDataSource.getLocationDetails();
                if (mDataSource.fnCheckIfStoreIDExistsIn_tblStoreDeatils(selStoreID) == 0) {
                    LattitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[0];
                    LongitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[1];
                    AccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[2];
                    AddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[3];
                    CityFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[4];
                    PincodeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[5];
                    StateFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[6];

                    ProviderFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[7];
                    GpsLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[8];
                    GpsLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[9];
                    GpsAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[10];
                    NetworkLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[11];
                    NetworkLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[12];
                    NetworkAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[13];
                    FusedLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[14];
                    FusedLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[15];
                    FusedAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[16];

                    AllProvidersLocationFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[17];
                    GpsAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[18];
                    NetwAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[19];
                    FusedAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[20];
                    FusedLocationLatitudeWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[21];
                    FusedLocationLongitudeWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[22];
                    FusedLocationAccuracyWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[23];

                }
                else {


                    ArrayList<String> arrBasisDetailsAgainstStore = mDataSource.fnGetDetails_tblStoreDeatils(selStoreID);


                    allValuesOfPaymentStageID = arrBasisDetailsAgainstStore.get(1);//bydefalt "0"
                    AddressFromLauncher = arrBasisDetailsAgainstStore.get(2);////bydefalt "NA"
                    CityFromLauncher = arrBasisDetailsAgainstStore.get(3);
                    PincodeFromLauncher = arrBasisDetailsAgainstStore.get(4);
                    StateFromLauncher = arrBasisDetailsAgainstStore.get(5);
                    LattitudeFromLauncher = arrBasisDetailsAgainstStore.get(6);
                    LongitudeFromLauncher = arrBasisDetailsAgainstStore.get(7);
                    AccuracyFromLauncher = arrBasisDetailsAgainstStore.get(8);
                }

                address = AddressFromLauncher;
                pincode = PincodeFromLauncher;
                city = CityFromLauncher;
                state = StateFromLauncher;
                latitudeToSave = LattitudeFromLauncher;
                longitudeToSave = LongitudeFromLauncher;
                accuracyToSave = AccuracyFromLauncher;


                hmapQuesIdValues = mDataSource.fnGetQuestionMstr(sPref);    //
                hmapQuesGropKeySection = mDataSource.fnGetQuestionMstrKey(); //
                hmapGroupId_Desc = mDataSource.getGroupDescription();  //
                hmapSctnId_GrpId = mDataSource.fnGetGroupIdMpdWdSectionId(); //
                hmapDpndtQustGrpId = mDataSource.fnGetDependentQuestionMstr(); //
                hmapSection_key = mDataSource.fnGetSection_Key(); //
                //mDataSource.open();
                channelOptId = mDataSource.getChannelGroupIdOptId(); //
                //mDataSource.close();
                //   hmapOptionId_OptionValue=mDataSource.fnGetOptionId_OptionValue();
                QuestIDForOutChannel = mDataSource.fnGetQuestIDForOutChannelFromQuestionMstr(); //

                addFragment();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (progressDialog != null)
                    progressDialog.dismiss();
                manager = getFragmentManager();
                mapFrag = (MapFragment) manager.findFragmentById(R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(AddNewStore_DynamicSectionWise.this);
            }
        }.execute();



    }

    public void showSettingsAlert() {


        AppUtils.doCheckGPSEnable(AddNewStore_DynamicSectionWise.this);
    }


    public void showSubmitConfirm(int flgOrderOrOnlyAddofBtnClick) {

        if(rg_yes_no.getCheckedRadioButtonId() == -1) {
            showAlertSingleButtonError("Please select if your location is correct or not.");
        }
        else {
            flgOrderOrOnlyAdd = flgOrderOrOnlyAddofBtnClick;
            AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);
            alertDialogSubmitConfirm.setTitle(getText(R.string.genTermInformation));
            if (flgOrderOrOnlyAdd == 1) {
                alertDialogSubmitConfirm.setMessage(getText(R.string.SubmitStrInfoandStartOrderBooking));
            }
            if (flgOrderOrOnlyAdd == 2) {
                alertDialogSubmitConfirm.setMessage(getText(R.string.SubmitStrInfo));

            }
            alertDialogSubmitConfirm.setCancelable(false);

            alertDialogSubmitConfirm.setNeutralButton(getText(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which){

                    sectionToShowHide--;

                    dialog.dismiss();
                    flgStopAddingStoreOrWaringResult = 0;
                    flgAnyConditionFullFilled = 0;
                    NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                    if (null != recFragment) {
                        recFragment.saveDynamicQuesAns(true);
                        StoreName=recFragment.currentStoreName;
                        hmapStoreQuestAnsNew = recFragment.hmapAnsValues;
                        hmapStoreAddress = recFragment.hmapAddress;
                       // fnlStoreType = recFragment.currentStoreType;//Original
                        //For Now Change StoreType=1
                        fnlStoreType="1";
                        fnlOwnerName = recFragment.currentOwnerName;
                        fnlMobileNumber = recFragment.currentMobileNumber;
                        fnlAddressName = recFragment.currentAddressName;
                        fnSalesPersonName = recFragment.currentSalesPersonName;
                        fnSalesPersonContactNo = recFragment.currentSalesPersonContactNo;
                        fnStoreCatType = recFragment.currentStoreCatType;
                        if (!TextUtils.isEmpty(recFragment.distBId)) {
                            distID = recFragment.distBId;

                        }

                    }



                    if (!StoreName.equals("NA") && mDataSource.fnGetALLOutletsWithSameNameFromMstr(StoreName.trim()).size() > 0) {
                        LinkedHashMap<String, String> hmapOutletListForNearByName = mDataSource.fnGetALLOutletsWithSameNameFromMstr(StoreName.trim());
                        if (hmapOutletListForNearByName != null) {

                            for (Map.Entry<String, String> entry : hmapOutletListForNearByName.entrySet()) {
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

                                        if (DistanceBWPoint <= 50) {
                                            flgStopAddingStoreOrWaringResult = 2;

                                            break;

                                        }
                                    } catch (Exception e) {

                                    }
                                }
                                // }
                            }

                            if (flgStopAddingStoreOrWaringResult == 2) {
                                flgAnyConditionFullFilled = 1;
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);

                                // Setting Dialog Title
                                alertDialog.setTitle(getText(R.string.genTermInformation));
                                alertDialog.setIcon(R.drawable.error_info_ico);
                                alertDialog.setCancelable(false);
                                // Setting Dialog Message
                                alertDialog.setMessage("Outlet <<" + StoreName.trim() + ">>  already exists  within the 50 meters range, so can not be added.");

                                // On pressing Settings button
                                alertDialog.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        //flgStopAddingStoreOrWaringResult = 0;

                                        dialog.dismiss();

                          /*  SubmitData submitData = new SubmitData();
                            AppUtils.executeAsyncTask(submitData);*/
                                        flgStopAddingStoreOrWaringResult = 2;
                                        // dialog.dismiss();

                                    }
                                });
                                alertDialog.setNegativeButton(getText(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        flgStopAddingStoreOrWaringResult = 2;
                                        dialog.dismiss();





                                    }
                                });

                                // Showing Alert Message
                                alertDialog.show();
                            }
                        }
                    }
                    else
                    {
                        if (!GSTNumber.equals("NA") && mDataSource.fnGetALLOutletsWithSameGSTFromMstr(GSTNumber.trim()).size() > 0) {
                            LinkedHashMap<String, String> hmapOutletListForNearByName = mDataSource.fnGetALLOutletsWithSameGSTFromMstr(GSTNumber.trim());
                            if (hmapOutletListForNearByName != null) {
                                if(hmapOutletListForNearByName.size()>0)
                                {
                                    flgStopAddingStoreOrWaringResult = 2;

                                }


                                if (flgStopAddingStoreOrWaringResult == 2) {
                                    flgAnyConditionFullFilled = 1;
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);

                                    // Setting Dialog Title
                                    alertDialog.setTitle(getText(R.string.genTermInformation));
                                    alertDialog.setIcon(R.drawable.error_info_ico);
                                    alertDialog.setCancelable(false);
                                    // Setting Dialog Message
                                    alertDialog.setMessage("GST <<" + GSTNumber.trim() + ">>  already registered with another store, so can not be added.");

                                    // On pressing Settings button
                                    alertDialog.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            //flgStopAddingStoreOrWaringResult = 0;

                                            dialog.dismiss();

                          /*  SubmitData submitData = new SubmitData();
                            AppUtils.executeAsyncTask(submitData);*/
                                            flgStopAddingStoreOrWaringResult = 2;
                                            // dialog.dismiss();

                                        }
                                    });
                                    alertDialog.setNegativeButton(getText(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            flgStopAddingStoreOrWaringResult = 2;
                                            dialog.dismiss();





                                        }
                                    });

                                    // Showing Alert Message
                                    alertDialog.show();
                                }
                            }
                        }
                    }
                    if (flgStopAddingStoreOrWaringResult == 0) {
                        if (mDataSource.fnGetExistingOutletIDFromStoreMstr(StoreName.trim(), fnlMobileNumber) == 1) {
                            String OutletRouteName = mDataSource.fnGetExistingRouteNameWithSameNameFromStoreMstr(StoreName.trim(), fnlMobileNumber);
                            showStoreAlreadyExists(StoreName.trim(), OutletRouteName);
                            flgStopAddingStoreOrWaringResult = 1;
                            flgAnyConditionFullFilled = 1;

                        }
                    }
                    if (flgStopAddingStoreOrWaringResult == 0) {
                        if(!fnlMobileNumber.equals("NA"))
                        {
                            boolean bl=false;
                            if (fnlMobileNumber.startsWith("6"))
                            {
                                bl=true;
                            }
                            else if (fnlMobileNumber.startsWith("7"))
                            {
                                bl=true;
                            }
                            else if (fnlMobileNumber.startsWith("8"))
                            {
                                bl=true;
                            }
                            else if (fnlMobileNumber.startsWith("9"))
                            {
                                bl=true;
                            }
                            //(!fnlMobileNumber.startsWith("6") || !fnlMobileNumber.startsWith("7") || !fnlMobileNumber.startsWith("8") || !fnlMobileNumber.startsWith("9")  )
                            if(bl==false)
                            {
                                flgStopAddingStoreOrWaringResult = 1;
                                flgAnyConditionFullFilled = 1;
                                showAlertMobileOtherValidation("Invalid Mobile Number.\nPlease correct that");
                            }

                        }

                    }
                    if (flgStopAddingStoreOrWaringResult == 0) {
                        if(fnlMobileNumber.length()!=10)
                        {
                            flgStopAddingStoreOrWaringResult = 1;
                            flgAnyConditionFullFilled = 1;
                            showAlertMobileOtherValidation("Invalid Mobile Number.\nPlease correct that");
                        }

                    }

                    if (flgStopAddingStoreOrWaringResult == 0) {
                        HashMap<Character,Integer> charCountMap= new HashMap<>();

                        char[] strArray = fnlMobileNumber.toCharArray();
                        for(char charItem:strArray) {
                            if(charCountMap.containsKey(charItem)) {
                                charCountMap.put(charItem,charCountMap.get(charItem)+1);
                            } else {
                                charCountMap.put(charItem,1);
                            }
                        }

                       /* if(charCountMap.size()==1 || charCountMap.size()==0) {

                        }*/
                        if(charCountMap.toString().length()==6) {
                            flgStopAddingStoreOrWaringResult = 1;
                            flgAnyConditionFullFilled = 1;
                            showAlertMobileOtherValidation("Invalid Mobile Number.\nPlease correct that");
                        }

                    }
                    /*if (flgStopAddingStoreOrWaringResult == 0) {
                        if (fnlMobileNumber.length() > 0) {
                            if (mDataSource.fnGetExistingMobileNoOutletIDFromStoreMstr(fnlMobileNumber) == 1) {
                                String outletOtherOutletHavingSameMobileNumber = mDataSource.fnGetOutletnamewthMobileNo(fnlMobileNumber);
                                flgStopAddingStoreOrWaringResult = 2;
                                showStoreAlreadyExistsWithSameMobileNumber(outletOtherOutletHavingSameMobileNumber, fnlMobileNumber,0);
                                //flgStopAddingStoreOrWaringResult=1;
                                flgAnyConditionFullFilled = 1;
                            }
                        }
                    }*/



                    if (flgStopAddingStoreOrWaringResult == 0 && flgAnyConditionFullFilled == 0) {
                        SubmitData submitData = new SubmitData();
                        AppUtils.executeAsyncTask(submitData);
                    }


                } /*{

                sectionToShowHide--;

                dialog.dismiss();

                SubmitData submitData = new SubmitData();
                AppUtils.executeAsyncTask(submitData);


            }*/
            });

            alertDialogSubmitConfirm.setNegativeButton(getText(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ll_bookOrder.setVisibility(View.GONE);
                    dialog.dismiss();
                }
            });

            alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);

            AlertDialog alert = alertDialogSubmitConfirm.create();

            alert.show();
        }



    }

    public void showStoreAlreadyExistsWithSameMobileNumber(String StoreName, String storeNumber, final int btnclick) {
        if (flgStopAddingStoreOrWaringResult == 2) {
            flgAnyConditionFullFilled = 1;
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle(getText(R.string.genTermInformation));
            alertDialog.setIcon(R.drawable.error_info_ico);
            alertDialog.setCancelable(false);
            // Setting Dialog Message
//            alertDialog.setMessage("Outlet <<"+StoreName+">> has the same owner number as the current outlet being added. If you are sure  both outlets have the same owner OR number now entered is correct, please click OK to continue OR ELSE click cancel to correct the owner number.");
            alertDialog.setMessage("Duplicate Contact number exists for <<" + StoreName + ">>.Click OK to proceed");

            // On pressing Settings button
            alertDialog.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    flgStopAddingStoreOrWaringResult = 0;
                    flgAnyConditionFullFilled=0;
                    if (flgStopAddingStoreOrWaringResult == 0 && flgAnyConditionFullFilled == 0) {
                        SubmitData submitData = new SubmitData();
                        AppUtils.executeAsyncTask(submitData);
                    }
                    dialog.dismiss();

                }
            });
            alertDialog.setNegativeButton(getText(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    flgStopAddingStoreOrWaringResult = 2;
                    dialog.dismiss();


                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
    }
    public void showStoreAlreadyExists(String StoreName, String OutletRouteName) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(getText(R.string.errorTxt));
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage("Store <<" + StoreName + ">> already exists in the current coverage area in route <<" + OutletRouteName + ">>. If you are sure this is actually a different store with the same name, please modify the name like adding route name after store name and save again.");

        // On pressing Settings button
        alertDialog.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {


        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.state = state;


        LattitudeFromLauncher = fnLati;
        LongitudeFromLauncher = fnLongi;
        AccuracyFromLauncher = String.valueOf(finalAccuracy);
        latitudeToSave = fnLati;
        longitudeToSave = fnLongi;

        ProviderFromLauncher = fnAccurateProvider;
        GpsLatFromLauncher = GpsLat;
        GpsLongFromLauncher = GpsLong;
        GpsAccuracyFromLauncher = GpsAccuracy;
        NetworkLatFromLauncher = NetwLat;
        NetworkLongFromLauncher = NetwLong;
        NetworkAccuracyFromLauncher = NetwAccuracy;
        FusedLatFromLauncher = FusedLat;
        FusedLongFromLauncher = FusedLong;
        FusedAccuracyFromLauncher = FusedAccuracy;

        AllProvidersLocationFromLauncher = AllProvidersLocation;
        GpsAddressFromLauncher = GpsAddress;
        NetwAddressFromLauncher = NetwAddress;
        FusedAddressFromLauncher = FusedAddress;
        FusedLocationLatitudeWithFirstAttemptFromLauncher = FusedLocationLatitudeWithFirstAttempt;
        FusedLocationLongitudeWithFirstAttemptFromLauncher = FusedLocationLongitudeWithFirstAttempt;
        FusedLocationAccuracyWithFirstAttemptFromLauncher = FusedLocationAccuracyWithFirstAttempt;
        //LLLLL


     /*   manager = getFragmentManager();
        mapFrag = (MapFragment) manager.findFragmentById(R.id.map);*/
      //  if (mapFrag != null && mapFrag.getView() != null) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.show(mapFrag);
     //   }

    }




    class SubmitData extends AsyncTask<String, String, String> {

        KProgressHUD hud;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hud = KProgressHUD.create(ctx)
                    .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                    .setLabel("Please wait")
                    .setMaxProgress(100)
                    .show();
            hud.setProgress(90);
        }

        @Override
        protected String doInBackground(String... strings) {
/*
            NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
            if (null != recFragment) {
                recFragment.saveDynamicQuesAns(true);
                hmapStoreQuestAnsNew = recFragment.hmapAnsValues;
                hmapStoreAddress = recFragment.hmapAddress;
                // fnlStoreType = recFragment.currentStoreType;//Original
                //For Now Change StoreType=1
                fnlStoreType="1";
                fnlOwnerName = recFragment.currentOwnerName;
                fnlMobileNumber = recFragment.currentMobileNumber;
                fnlAddressName = recFragment.currentAddressName;
                fnSalesPersonName = recFragment.currentSalesPersonName;
                fnSalesPersonContactNo = recFragment.currentSalesPersonContactNo;
                fnStoreCatType = recFragment.currentStoreCatType;
                if (!TextUtils.isEmpty(recFragment.distBId)) {
                    distID = recFragment.distBId;
                }

            }

            if (flgOrderOrOnlyAdd == 1) {
                getStoreVisitCode();
            }

            if (ProviderFromLauncher.equals("Fused")) {
                fnAddressFromLauncher = FusedAddressFromLauncher;
            } else if (ProviderFromLauncher.equals("Gps")) {
                fnAddressFromLauncher = GpsAddressFromLauncher;
            } else if (ProviderFromLauncher.equals("Network")) {
                fnAddressFromLauncher = NetwAddressFromLauncher;
            }


            mDataSource.saveLatLngToTxtFile(selStoreID,StoreVisitCode,LattitudeFromLauncher, LongitudeFromLauncher, AccuracyFromLauncher, ProviderFromLauncher, GpsLatFromLauncher, GpsLongFromLauncher, GpsAccuracyFromLauncher, NetworkLatFromLauncher, NetworkLongFromLauncher, NetworkAccuracyFromLauncher, FusedLatFromLauncher, FusedLongFromLauncher, FusedAccuracyFromLauncher, 3, "0", fnAddressFromLauncher, AllProvidersLocationFromLauncher, GpsAddressFromLauncher, NetwAddressFromLauncher, FusedAddressFromLauncher, FusedLocationLatitudeWithFirstAttemptFromLauncher
                    , FusedLocationLongitudeWithFirstAttemptFromLauncher, FusedLocationAccuracyWithFirstAttemptFromLauncher);
            String lastprvsStoreId = mDataSource.PrvsStoreMsgShownAndRestrtDone();
            if (!TextUtils.isEmpty(lastprvsStoreId.trim())) {
                mDataSource.updateCurrentStoreId(selStoreID, lastprvsStoreId);
            }
            long syncTIMESTAMP = System.currentTimeMillis();
            Date datefromat = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String VisitEndFinalTS = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_TIME_FORMAT);

            mDataSource.insertRestartStoreInfo(selStoreID, selStoreID, "2", "0", "0", 3, VisitEndFinalTS);


            try {

                addStoreOffline();

            } catch (Exception e) {
                e.printStackTrace();
            }*/

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (hud != null && hud.isShowing())
                hud.dismiss();


            NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
            if (null != recFragment) {
                recFragment.saveDynamicQuesAns(true);
                hmapStoreQuestAnsNew = recFragment.hmapAnsValues;
                hmapStoreAddress = recFragment.hmapAddress;
                // fnlStoreType = recFragment.currentStoreType;//Original
                //For Now Change StoreType=1
                fnlStoreType="1";
                fnlOwnerName = recFragment.currentOwnerName;
                fnlMobileNumber = recFragment.currentMobileNumber;
                fnlAddressName = recFragment.currentAddressName;
                fnSalesPersonName = recFragment.currentSalesPersonName;
                fnSalesPersonContactNo = recFragment.currentSalesPersonContactNo;
                fnStoreCatType = recFragment.currentStoreCatType;
                if (!TextUtils.isEmpty(recFragment.distBId)) {
                    distID = recFragment.distBId;
                }

            }

            if (flgOrderOrOnlyAdd == 1) {
                getStoreVisitCode();
            }

            if (ProviderFromLauncher.equals("Fused")) {
                fnAddressFromLauncher = FusedAddressFromLauncher;
            } else if (ProviderFromLauncher.equals("Gps")) {
                fnAddressFromLauncher = GpsAddressFromLauncher;
            } else if (ProviderFromLauncher.equals("Network")) {
                fnAddressFromLauncher = NetwAddressFromLauncher;
            }


            mDataSource.saveLatLngToTxtFile(selStoreID,StoreVisitCode,LattitudeFromLauncher, LongitudeFromLauncher, AccuracyFromLauncher, ProviderFromLauncher, GpsLatFromLauncher, GpsLongFromLauncher, GpsAccuracyFromLauncher, NetworkLatFromLauncher, NetworkLongFromLauncher, NetworkAccuracyFromLauncher, FusedLatFromLauncher, FusedLongFromLauncher, FusedAccuracyFromLauncher, 3, "0", fnAddressFromLauncher, AllProvidersLocationFromLauncher, GpsAddressFromLauncher, NetwAddressFromLauncher, FusedAddressFromLauncher, FusedLocationLatitudeWithFirstAttemptFromLauncher
                    , FusedLocationLongitudeWithFirstAttemptFromLauncher, FusedLocationAccuracyWithFirstAttemptFromLauncher);
            String lastprvsStoreId = mDataSource.PrvsStoreMsgShownAndRestrtDone();
            if (!TextUtils.isEmpty(lastprvsStoreId.trim())) {
                mDataSource.updateCurrentStoreId(selStoreID, lastprvsStoreId);
            }
            long syncTIMESTAMP = System.currentTimeMillis();
            Date datefromat = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String VisitEndFinalTS = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_TIME_FORMAT);

            mDataSource.insertRestartStoreInfo(selStoreID, selStoreID, "2", "0", "0", 3, VisitEndFinalTS);


            try {

                addStoreOffline();

            } catch (Exception e) {
                e.printStackTrace();
            }
/*

            String presentRoute = mDataSource.GetActiveRouteID();

            String VisitDate = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_FORMAT);

            Intent mMyServiceIntent = new Intent(getCtx(), SyncJobService.class);  //is any of that needed?  idk.
            mMyServiceIntent.putExtra("whereTo", "Regular");//
            mMyServiceIntent.putExtra("storeID", selStoreID);
            mMyServiceIntent.putExtra("routeID", presentRoute);//

            SyncJobService.enqueueWork(getCtx(), mMyServiceIntent);


            if (activityFrom.equals("StoreSelection")) {
                if (flgOrderOrOnlyAdd == 1) {
                    if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                        if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ActualVisitStock.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("StoreVisitCode",StoreVisitCode);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            startActivity(nxtP4);
                            finish();

                        } else {

                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                            //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            nxtP4.putExtra("flgOrderType", 1);
                            startActivity(nxtP4);
                            finish();
                        }
                    } else {
                        Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                        //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                        nxtP4.putExtra("storeID", selStoreID);
                        nxtP4.putExtra("SN", StoreName);
                        nxtP4.putExtra("imei", imei);
                        nxtP4.putExtra("userdate", VisitDate);
                        nxtP4.putExtra("pickerDate", VisitDate);
                        nxtP4.putExtra("flgOrderType", 1);
                        startActivity(nxtP4);
                        finish();
                    }
                } else if (flgOrderOrOnlyAdd == 2) {
                    Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                    ide.putExtra("userDate", date_value);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("imei", imei);
                    ide.putExtra("rID", rID);
                    AddNewStore_DynamicSectionWise.this.startActivity(ide);
                    finish();
                } else {
                    Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                    ide.putExtra("userDate", date_value);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("imei", imei);
                    ide.putExtra("rID", rID);
                    AddNewStore_DynamicSectionWise.this.startActivity(ide);
                    finish();
                }


            } else if (activityFrom.equals("AllButtonActivity")) {

               */
/* Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                intent.putExtra("activityFrom", "AllButtonActivity");
                startActivity(intent);
                finish();*//*


                if (flgOrderOrOnlyAdd == 1) {
                    if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                        if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ActualVisitStock.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("StoreVisitCode",StoreVisitCode);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            startActivity(nxtP4);
                            finish();

                        } else {

                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                            //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            nxtP4.putExtra("flgOrderType", 1);
                            startActivity(nxtP4);
                            finish();
                        }
                    } else {
                        Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                        //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                        nxtP4.putExtra("storeID", selStoreID);
                        nxtP4.putExtra("SN", StoreName);
                        nxtP4.putExtra("imei", imei);
                        nxtP4.putExtra("userdate", VisitDate);
                        nxtP4.putExtra("pickerDate", VisitDate);
                        nxtP4.putExtra("flgOrderType", 1);
                        startActivity(nxtP4);
                        finish();
                    }
                } else if (flgOrderOrOnlyAdd == 2) {
                    Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                    intent.putExtra("activityFrom", "AllButtonActivity");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                    intent.putExtra("activityFrom", "AllButtonActivity");
                    startActivity(intent);
                    finish();
                }

            } else {
                {
                    if (flgOrderOrOnlyAdd == 1) {
                        if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                            if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                                Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ActualVisitStock.class);
                                nxtP4.putExtra("storeID", selStoreID);
                                nxtP4.putExtra("SN", StoreName);
                                nxtP4.putExtra("StoreVisitCode",StoreVisitCode);
                                nxtP4.putExtra("imei", imei);
                                nxtP4.putExtra("userdate", VisitDate);
                                nxtP4.putExtra("pickerDate", VisitDate);
                                startActivity(nxtP4);
                                finish();

                            } else {

                                Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                                //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                                nxtP4.putExtra("storeID", selStoreID);
                                nxtP4.putExtra("SN", StoreName);
                                nxtP4.putExtra("imei", imei);
                                nxtP4.putExtra("userdate", VisitDate);
                                nxtP4.putExtra("pickerDate", VisitDate);
                                nxtP4.putExtra("flgOrderType", 1);
                                startActivity(nxtP4);
                                finish();
                            }
                        } else {
                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                            //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            nxtP4.putExtra("flgOrderType", 1);
                            startActivity(nxtP4);
                            finish();
                        }
                    } else if (flgOrderOrOnlyAdd == 2) {
                        Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                        ide.putExtra("userDate", date_value);
                        ide.putExtra("pickerDate", pickerDate);
                        ide.putExtra("imei", imei);
                        ide.putExtra("rID", rID);
                        AddNewStore_DynamicSectionWise.this.startActivity(ide);
                        finish();
                    } else {
                        Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                        ide.putExtra("userDate", date_value);
                        ide.putExtra("pickerDate", pickerDate);
                        ide.putExtra("imei", imei);
                        ide.putExtra("rID", rID);
                        AddNewStore_DynamicSectionWise.this.startActivity(ide);
                        finish();
                    }



                }
            }
*/

        }
    }


    public void addStoreOffline() {
     //   mDataSource.fnsaveOutletQuestAnsMstrSectionWise(hmapStoreQuestAnsNew, 0, selStoreID);

        long syncTIMESTAMP = System.currentTimeMillis();
        Date datefromat = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);


        String VisitEndFinalTS = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_TIME_FORMAT);
        String VisitDate = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_FORMAT);
        // int DatabaseVersion=CommonInfo.DATABASE_VERSIONID;
        int ApplicationID = CommonInfo.Application_TypeID;
        //getSectionNextOrBack(3,sectionToShowHide );

        String VisitStartTS = df.format(datefromat);
        fnGettingGSTOFflineVal();
        checkHighAccuracyLocationMode(AddNewStore_DynamicSectionWise.this);


        String selectedBeatName = "";
        String selectedDistributorName = "";
        String selectedStoreTypeDesc="";
        String selectedDistributorDisText = "NA";
        String slctdBeatId = "0";
        String slctdBeatNodeType = "0";

        String slctdDistributorID = "0";
        String slctdDistributorNodeType = "0";

        LinkedHashMap<String, String> hmapStoreQuestAnsNew = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hmapStoreAddress = new LinkedHashMap<String, String>();
        int IsDiscountApplicable=0;
        String IsDiscountAllowed="NA";
        NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
        if (null != recFragment) {
         //   recFragment.saveDynamicQuesAns(true);
            hmapStoreQuestAnsNew = recFragment.hmapAnsValues;
            hmapStoreAddress = recFragment.hmapAddress;
            selectedBeatName = recFragment.getSelectedBeatName();
            if (!selectedBeatName.equals("0") && (selectedBeatName.contains("-"))) {
                slctdBeatId = selectedBeatName.split(Pattern.quote("-"))[1];
                slctdBeatNodeType = selectedBeatName.split(Pattern.quote("-"))[2];
            }

            selectedDistributorName= recFragment.getSelectedDistributorName();
            if (!selectedDistributorName.equals("0") && (selectedDistributorName.contains("-"))) {
                slctdDistributorID = selectedDistributorName.split(Pattern.quote("-"))[1];
                slctdDistributorNodeType = selectedDistributorName.split(Pattern.quote("-"))[2];
                IsDiscountApplicable=mDataSource.fnCheckflgDiscountApplicableAgainstDistributor(Integer.parseInt(slctdDistributorID));
                IsDiscountAllowed=mDataSource.fnCheckflgDiscountAllowedAgainstDistributor(Integer.parseInt(slctdDistributorID));
                selectedDistributorDisText=mDataSource.fnCheckflgDiscountAllowedAgainstDistributorDescr(Integer.parseInt(slctdDistributorID));
            }

            // fnlStoreType = recFragment.currentStoreType;//Original
            //For Now Change StoreType=1
            fnlStoreType="1";
            selectedStoreTypeDesc=recFragment.getSelectedStoreTypeDescr();
            fnlAddressName = recFragment.currentAddressName;


            fnlOwnerName = recFragment.currentOwnerName;
            fnlMobileNumber = recFragment.currentMobileNumber;
            fnlAddressName = recFragment.currentAddressName;
            fnSalesPersonName = recFragment.currentSalesPersonName;
            fnSalesPersonContactNo = recFragment.currentSalesPersonContactNo;
            fnStoreCatType = recFragment.currentStoreCatType;

            if (!TextUtils.isEmpty(recFragment.distBId)) {
                distID = recFragment.distBId;
            /*    IsDiscountApplicable=mDataSource.fnCheckflgDiscountApplicableAgainstDistributor(Integer.parseInt(distID));
                IsDiscountAllowed=mDataSource.fnCheckflgDiscountAllowedAgainstDistributor(Integer.parseInt(distID));*/
            }
        }

        if(isOnline())
        {

            try
            {
                // new GetRouteInfo().execute();


                CommonFunction.getNewStoreNumberValidation(AddNewStore_DynamicSectionWise.this,imei, CommonInfo.RegistrationID,"Please wait validating store.",fnlMobileNumber,GSTNumber,selStoreID);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            showAlertDuplicateNumberValidationFromServer("Internet Connection is required");
        }
      /*  if (FLAG_NEW_UPDATE.equals("UPDATE")) {
            //mDataSource.open();
            mDataSource.UpdateStoreReturnphotoFlagSM(selStoreID, StoreName, 0);
            //mDataSource.close();
        } else {
            //mDataSource.open();
            mDataSource.saveTblPreAddedStoresAddStoreDynamic(selStoreID, StoreName, LattitudeFromLauncher, LongitudeFromLauncher, VisitDate, 1, 0, 3,slctdBeatId,slctdBeatNodeType);//,Integer.parseInt(slctdBeatId),Integer.parseInt(slctdBeatNodeType));
            //mDataSource.close();
        }

        //mDataSource.open();
        mDataSource.deletetblstoreMstrOnStoreIDBasis(selStoreID);
        if ((!fnlStoreType.equals("0")) && fnlStoreType.contains("-")) {
            fnlStoreType = fnlStoreType.split(Pattern.quote("-"))[1];
        }
        //RetailerName

        mDataSource.savetblStoreMain(slctdBeatId, selStoreID, StoreName, "NA", "NA", "NA", "NA", fnlAddressName, "NA", "NA", "0", StoreTypeTradeChannel,
                Integer.parseInt(fnlStoreType), 0, 0, 0, "NA", VisitStartTS, imei, "" + battLevel, 3, 1, String.valueOf(LattitudeFromLauncher), String.valueOf(LongitudeFromLauncher), "" + AccuracyFromLauncher, "" + ProviderFromLauncher, 0, FusedAddressFromLauncher, allValuesOfPaymentStageID, flgHasQuote, flgAllowQuotation, flgSubmitFromQuotation, flgGSTCapture, flgGSTCompliance, GSTNumber, flgGSTRecordFromServer, flgLocationServicesOnOff, flgGPSOnOff, flgNetworkOnOff, flgFusedOnOff, flgInternetOnOffWhileLocationTracking, flgRestart, flgStoreOrder, hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"), distID, fnlOwnerName, fnlMobileNumber, fnStoreCatType, flgRuleTaxVal, flgTransType, fnlMobileNumber, fnSalesPersonName, fnSalesPersonContactNo, IsComposite, Integer.parseInt(hmapStoreAddress.get("5")), Integer.parseInt(hmapStoreAddress.get("4")),IsDiscountApplicable, Integer.parseInt(hmapStoreAddress.get("7")),IsDiscountAllowed);

        mDataSource.saveSOAPdataStoreListDetailsInNewTable(selStoreID, hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"), 1);



        StoreSelection.flgChangeRouteOrDayEnd = 0;
        DayStartActivity.flgDaySartWorking = 0;*/


    }
    public void  getStoreVisitCode()
    {

        String mrouteType=mDataSource.FetchRouteType(rID);
        String passdLevel = battLevel + "%";
        StoreVisitCode=genStoreVisitCode();
        mDataSource.fnInsertOrUpdate_tblStoreVisitMstr(StoreVisitCode,selStoreID,3,getDateInMonthTextFormat(),LattitudeFromLauncher,LongitudeFromLauncher,getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond(),ProviderFromLauncher,AccuracyFromLauncher,passdLevel,0,0,1,0,0,0,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,
                flgFusedOnOff,flgInternetOnOffWhileLocationTracking,0,0,
                0,flgVisitCollectionMarkedStatus,address,city,pincode,state,rID,mrouteType, imei,1,1,1,0);//Last Parameter here 1 is flgOrderType means Actual Visit
        mDataSource.fnInsertOrUpdate_tblStoreOrderVisitDayActivity(StoreVisitCode,selStoreID,StoreVisitCode,getDateInMonthTextFormat(),3
                ,getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond(), imei,0.0,0.0,-1);

    }
    public String genStoreVisitCode()
    {
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

      /*  String cxz;
        cxz = UUID.randomUUID().toString();*/
        RandomString cxz = new RandomString();
        String randomString=cxz.nextString();
        //  StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");
        String VisitStartTS ="";// TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);
      /*  if(VisitStartTS.equals("") || VisitStartTS.equals("NA"))
        {*/
        VisitStartTS = df.format(new Date());
        //}
     /*   String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();*/
        imei = AppUtils.getToken(this);
        String IMEIid = imei.substring(9);
        String  sdcxz =  "StoreVisitCode" + "-" +IMEIid +"-"+randomString+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


        return sdcxz;

    }


    public String genStoreTempVisitCode()
    {
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

      /*  String cxz;
        cxz = UUID.randomUUID().toString();*/
        RandomString cxz = new RandomString();
        String randomString=cxz.nextString();
        //  StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");
        String VisitStartTS ="";// TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);
      /*  if(VisitStartTS.equals("") || VisitStartTS.equals("NA"))
        {*/
        VisitStartTS = df.format(new Date());
        //}
     /*   String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();*/
        imei = AppUtils.getToken(this);
        String IMEIid = imei.substring(9);
        String  sdcxz =  "StoreVisitCode" + "-" +IMEIid +"-"+randomString+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


        return sdcxz;

    }
    public void addStoreOnLine() {


        StringTokenizer tokens = new StringTokenizer(String.valueOf(ServiceWorkerResultSet), "^");

        String StoreName = tokens.nextToken().toString().trim();
        String StoreType = tokens.nextToken().toString().trim();
        String StoreLatitude = tokens.nextToken().toString().trim();
        String StoreLongitude = tokens.nextToken().toString().trim();
        String LastVisitDate = tokens.nextToken().toString().trim();
        String LastTransactionDate = tokens.nextToken().toString().trim();
        String dateVAL = tokens.nextToken().toString().trim();
        String AutoIdStore = tokens.nextToken().toString().trim();
        String Sstat = tokens.nextToken().toString().trim();
        String IsClose = tokens.nextToken().toString().trim();
        String IsNextDat = tokens.nextToken().toString().trim();
        String RouteID = tokens.nextToken().toString().trim();
        int flgHasQuoteNew = Integer.parseInt(tokens.nextToken().toString().trim());
        int flgAllowQuotationNew = Integer.parseInt(tokens.nextToken().toString().trim());
        int flgSubmitFromQuotationNew = Integer.parseInt(tokens.nextToken().toString().trim());

        String flgGSTCapture = tokens.nextToken().toString().trim();
        String flgGSTCompliance = tokens.nextToken().toString().trim();
        String GSTNumber = tokens.nextToken().toString().trim();
        String flgGSTRecordFromServer = "1";
        String distID = tokens.nextToken().toString().trim();


        mDataSource.fnDeletesaveNewOutletFromOutletMstr(selStoreID);


        String allValuesOfPaymentStageID = mDataSource.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
        String VisitTypeStatus = "0";
        //mDataSource.open();

        mDataSource.fndeleteNewStoreSalesQuotePaymentDetails(selStoreID);
       /* mDataSource.saveSOAPdataStoreList(ServiceWorkerStoreID,StoreName,StoreType,Double.parseDouble(StoreLatitude),Double.parseDouble(StoreLongitude),LastVisitDate,LastTransactionDate,
                dateVAL.toString().trim(),Integer.parseInt(AutoIdStore), Integer.parseInt(Sstat),Integer.parseInt(IsClose),Integer.parseInt(IsNextDat),Integer.parseInt(RouteID),StoreTypeTradeChannel,fetchAddress,allValuesOfPaymentStageID,flgHasQuoteNew,flgAllowQuotationNew,flgSubmitFromQuotationNew,flgGSTCapture,flgGSTCompliance,GSTNumber,Integer.parseInt(flgGSTRecordFromServer),distID,"0",VisitTypeStatus);*/
        //mDataSource.close();


    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void updateUI() {
        Location loc = mCurrentLocation;
        if (null != mCurrentLocation) {
            String lat = String.valueOf(mCurrentLocation.getLatitude());
            if (lat.contains("E") || lat.contains("e")) {
                lat = convertExponential(mCurrentLocation.getLatitude());
            }
            String lng = String.valueOf(mCurrentLocation.getLongitude());
            if (lng.contains("E") || lng.contains("e")) {
                lng = convertExponential(mCurrentLocation.getLongitude());
            }
            FusedLocationLatitude = lat;
            FusedLocationLongitude = lng;
            FusedLocationProvider = mCurrentLocation.getProvider();
            FusedLocationAccuracy = "" + mCurrentLocation.getAccuracy();
            fusedData = "At Time: " + mLastUpdateTime +
                    "Latitude: " + lat +
                    "Longitude: " + lng +
                    "Accuracy: " + mCurrentLocation.getAccuracy() +
                    "Provider: " + mCurrentLocation.getProvider();

        } else {

        }
    }
    public final double calculateCircleRadiusMeterForMapCircle(int _targetRadiusDip, double _circleCenterLatitude,
                                                               float _currentMapZoom) {
        //That base value seems to work for computing the meter length of a DIP
        double arbitraryValueForDip = 156000D;
        double oneDipDistance = Math.abs(Math.cos(Math.toRadians(_circleCenterLatitude))) * arbitraryValueForDip / Math.pow(2, _currentMapZoom);
        return oneDipDistance * (double) _targetRadiusDip;
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
            hmapOutletListForNear=null;// = mDataSource.fnGetALLOutletMstr();
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

           /* if (hmapOutletListForNearUpdated != null) {
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
            }*/
            //send to storeListpage page
            //From, addr,zipcode,city,state,errorMessageFlag,username,totaltarget,todayTarget
           // flagAnyNearByStoresAvailable = mDataSource.fncheckCountNearByStoreExistsOrNot(CommonInfo.DistanceRange);
            flagAnyNearByStoresAvailable=0;
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

        } else {
            if (refreshCount == 2) {
                txt_rfrshCmnt.setText(getString(R.string.loc_not_found));
                btn_refresh.setVisibility(View.GONE);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);
                    //searching marker id in locationDetailses and getting all the information of a particular marker

                    builder.setTitle("Route : " +  hmapStoreListWithNearByDistanceRange.get(sdfds).split(Pattern.quote("^"))[5]);
                    builder.setMessage("Store : "+hmapStoreListWithNearByDistanceRange.get(sdfds).split(Pattern.quote("^"))[0]);


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

/*    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (!LattitudeFromLauncher.equals("NA") && !LattitudeFromLauncher.equals("0.0")) {
            googleMap.clear();
            googleMap.setMyLocationEnabled(false);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)));
            Marker locationMarker = googleMap.addMarker(marker);
            locationMarker.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));

        } else {
            if (refreshCount == 2) {
                txt_rfrshCmnt.setText(getString(R.string.loc_not_found));
                btn_refresh.setVisibility(View.GONE);
            }
            try {
                googleMap.setMyLocationEnabled(true);
            } catch (SecurityException e) {

            }

            googleMap.moveCamera(CameraUpdateFactory.zoomIn());
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    marker.setTitle(StoreName);
                }
            });

        }

    }*/


    public void showAlertForEveryOne(String msg) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);
        alertDialogNoConn.setTitle(getText(R.string.genTermInformation));
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                long syncTIMESTAMP = System.currentTimeMillis();
                Date datefromat = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                SimpleDateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);


                String VisitEndFinalTS = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_TIME_FORMAT);
                if (!TextUtils.isEmpty(prvsStoreId.trim())) {
                    mDataSource.updateMsgToRestartPopUpShown(prvsStoreId, VisitEndFinalTS);
                } else {

                    mDataSource.updateMsgToRestartPopUpShown(selStoreID, VisitEndFinalTS);
                }
                finish();
            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }


    public void showAlertDuplicateNumberValidationFromServer(String msg) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);
        alertDialogNoConn.setTitle(getText(R.string.genTermInformation));
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }

    public String genTempID() {
        //store ID generation <x>

        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

      /*  String cxz;
        cxz = UUID.randomUUID().toString();*/
        RandomString cxz = new RandomString();
        String randomString=cxz.nextString();
        //  StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");
        String VisitStartTS ="";// TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);
      /*  if(VisitStartTS.equals("") || VisitStartTS.equals("NA"))
        {*/
        VisitStartTS = df.format(new Date());
        //}
     /*   String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();*/
        imei = AppUtils.getToken(this);
        String IMEIid = imei.substring(9);
        String  sdcxz =  IMEIid + "-" + randomString + "-" + VisitStartTS.replace(" ", "").replace(":", "").trim();


        return sdcxz;
        //-_
    }

    public void fetchAddress() {


        /*Intent intent = new Intent(this, GeocodeAddressIntentService.class);
        intent.putExtra("Reciever", mResultReceiver);
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        intent.putExtra("Location", location);
        startService(intent);*/
    }

    @Override
    public void selectedOption(String optId, String optionVal,
                               EditText txtVw, ListView listViewOption, String tagVal,
                               Dialog dialog, TextView textView, ArrayList<String> listStoreIDOrigin) {

        NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
        if (null != recFragment) {
            recFragment.selectedOption(optId, optionVal, txtVw, listViewOption, tagVal, dialog, textView, listStoreIDOrigin);
        }

    }

    @Override
    public void selectedStoreMultiple(String optId, String optionVal,
                                      EditText txtVw, ListView listViewOption, String tagVal,
                                      Dialog dialog, TextView textView, LinearLayout ll_SlctdOpt,
                                      ArrayList<String> listSelectedOpt,
                                      ArrayList<String> listSelectedStoreID, ArrayList<String> listSelectedStoreOrigin) {

        NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
        if (null != recFragment) {
            recFragment.selectedStoreMultiple(optId, optionVal,
                    txtVw, listViewOption, tagVal,
                    dialog, textView, ll_SlctdOpt,
                    listSelectedOpt,
                    listSelectedStoreID, listSelectedStoreOrigin);
        }

    }

    public void alertSubmit() {
        AlertDialog alertDialog = new AlertDialog.Builder(
                AddNewStore_DynamicSectionWise.this).create();

        // Setting Dialog Title
        alertDialog.setTitle(getText(R.string.DataSent));

        // Setting Dialog Message
        alertDialog.setMessage(getText(R.string.DataSucc));

        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog.setButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, LauncherActivity.class);
                intent.putExtra("FROM", "AddNewStore_DynamicSectionWise");
                startActivity(intent);
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void locationRetrievingAndDistanceCalculating() {


       LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
      //  llaaa.locationRetrievingAndDistanceCalculating(this, true, true, 100, 1);
        llaaa.locationRetrievingAndDistanceCalculating(AddNewStore_DynamicSectionWise.this, true, true, 200, 1);

    }

    public String getAddressForDynamic(String latti, String longi) {


        String areaToMerge = "NA";
        Address addressTemp = null;
        String addr = "NA";
        String zipcode = "NA";
        String city = "NA";
        String state = "NA";
        String fullAddress = "";
        StringBuilder FULLADDRESS3 = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

           /* AddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[3];
            CityFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[4];
            PincodeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[5];
            StateFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[6];*/
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);
            if (addresses != null && addresses.size() > 0) {
                if (addresses.get(0).getAddressLine(1) != null) {
                    addr = addresses.get(0).getAddressLine(1);

                }


                if (addresses.get(0).getLocality() != null) {
                    city = addresses.get(0).getLocality();
                    this.city = city;
                }

                if (addresses.get(0).getAdminArea() != null) {
                    state = addresses.get(0).getAdminArea();
                    this.state = state;
                }


                for (int i = 0; i < addresses.size(); i++) {
                    addressTemp = addresses.get(i);
                    if (addressTemp.getPostalCode() != null) {
                        zipcode = addressTemp.getPostalCode();
                        this.pincode = zipcode;
                        break;
                    }


                }
                if (addresses.get(0).getAddressLine(0) != null && addr.equals("NA")) {
                    String countryname = "NA";
                    if (addresses.get(0).getCountryName() != null) {
                        countryname = addresses.get(0).getCountryName();
                    }

                    address = getAddressNewWay(addresses.get(0).getAddressLine(0), city, state, zipcode, countryname);
                    addr = address;
                }


                NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                if (null != recFragment) {
                    recFragment.setFreshAddress();
                }
            } else {
                FULLADDRESS3.append("NA");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return fullAddress = addr + "^" + zipcode + "^" + city + "^" + state;
        }
    }

    public void fnCreateLastKnownGPSLoction(String chekLastGPSLat, String chekLastGPSLong, String chekLastGpsAccuracy) {

        try {

            JSONArray jArray = new JSONArray();
            JSONObject jsonObjMain = new JSONObject();


            JSONObject jOnew = new JSONObject();
            jOnew.put("chekLastGPSLat", chekLastGPSLat);
            jOnew.put("chekLastGPSLong", chekLastGPSLong);
            jOnew.put("chekLastGpsAccuracy", chekLastGpsAccuracy);


            jArray.put(jOnew);
            jsonObjMain.put("GPSLastLocationDetils", jArray);

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.AppLatLngJsonFile);
            if (!jsonTxtFolder.exists()) {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew = "GPSLastLocation.txt";
            File file = new File(jsonTxtFolder, txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory() + "/" + CommonInfo.AppLatLngJsonFile + "/" + txtFileNamenew;


            // If file does not exists, then create it
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            FileWriter fw;
            try {
                fw = new FileWriter(file.getAbsoluteFile());

                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(jsonObjMain.toString());

                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
				 /*  file=contextcopy.getFilesDir();
				//fileOutputStream=contextcopy.openFileOutput("GPSLastLocation.txt", Context.MODE_PRIVATE);
				fileOutputStream.write(jsonObjMain.toString().getBytes());
				fileOutputStream.close();*/
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
    }

    public void fnCreateLastKnownFinalLocation(String chekLastGPSLat, String chekLastGPSLong, String chekLastGpsAccuracy) {

        try {

            JSONArray jArray = new JSONArray();
            JSONObject jsonObjMain = new JSONObject();


            JSONObject jOnew = new JSONObject();
            jOnew.put("chekLastGPSLat", chekLastGPSLat);
            jOnew.put("chekLastGPSLong", chekLastGPSLong);
            jOnew.put("chekLastGpsAccuracy", chekLastGpsAccuracy);


            jArray.put(jOnew);
            jsonObjMain.put("GPSLastLocationDetils", jArray);

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists()) {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew = "FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder, txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory() + "/" + CommonInfo.FinalLatLngJsonFile + "/" + txtFileNamenew;


            // If file does not exists, then create it
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            FileWriter fw;
            try {
                fw = new FileWriter(file.getAbsoluteFile());

                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(jsonObjMain.toString());

                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
				 /*  file=contextcopy.getFilesDir();
				//fileOutputStream=contextcopy.openFileOutput("FinalGPSLastLocation.txt", Context.MODE_PRIVATE);
				fileOutputStream.write(jsonObjMain.toString().getBytes());
				fileOutputStream.close();*/
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
    }

    public boolean checkLastFinalLoctionIsRepeated(String currentLat, String currentLong, String currentAccuracy) {
        boolean repeatedLoction = false;

        try {

            String chekLastGPSLat = "0";
            String chekLastGPSLong = "0";
            String chekLastGpsAccuracy = "0";
            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists()) {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew = "FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder, txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory() + "/" + CommonInfo.FinalLatLngJsonFile + "/" + txtFileNamenew;

            // If file does not exists, then create it
            if (file.exists()) {
                StringBuffer buffer = new StringBuffer();
                String myjson_stampiGPSLastLocation = "";
                StringBuffer sb = new StringBuffer();
                BufferedReader br = null;

                try {
                    br = new BufferedReader(new FileReader(file));

                    String temp;
                    while ((temp = br.readLine()) != null)
                        sb.append(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close(); // stop reading
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                myjson_stampiGPSLastLocation = sb.toString();

                JSONObject jsonObjGPSLast = new JSONObject(myjson_stampiGPSLastLocation);
                JSONArray jsonObjGPSLastInneralues = jsonObjGPSLast.getJSONArray("GPSLastLocationDetils");

                String StringjsonGPSLastnew = jsonObjGPSLastInneralues.getString(0);
                JSONObject jsonObjGPSLastnewwewe = new JSONObject(StringjsonGPSLastnew);

                chekLastGPSLat = jsonObjGPSLastnewwewe.getString("chekLastGPSLat");
                chekLastGPSLong = jsonObjGPSLastnewwewe.getString("chekLastGPSLong");
                chekLastGpsAccuracy = jsonObjGPSLastnewwewe.getString("chekLastGpsAccuracy");

                if (currentLat != null) {
                    if (currentLat.equals(chekLastGPSLat) && currentLong.equals(chekLastGPSLong) && currentAccuracy.equals(chekLastGpsAccuracy)) {
                        repeatedLoction = true;
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repeatedLoction;

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isGPSokCheckInResume = false;
        boolean isNWokCheckInResume = false;
        isGPSokCheckInResume = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWokCheckInResume = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSokCheckInResume && !isNWokCheckInResume) {
            try {
                showSettingsAlert();
            } catch (Exception e) {

            }
            isGPSokCheckInResume = false;
            isNWokCheckInResume = false;
        } else {

         /*if(countSubmitClicked==1)
            {*/

              //  locationRetrievingAndDistanceCalculating();
                countSubmitClicked++;


           // }
               /* if(countSubmitClicked==2)
                {

                        locationRetrievingAndDistanceCalculating();
                        countSubmitClicked++;


                }else if(countSubmitClicked==0)
                {

                locationRetrievingAndDistanceCalculating();
                countSubmitClicked++;

                }*/


        }
        if (flgCheckNewOldStore == 1) {
            flgCheckNewOldStore = 0;
            // setStoreName(StoreNameFromBack);
            NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
            if (null != recFragment) {
                recFragment.setStoreName(StoreNameFromBack);
            }
        }
    }

    public String getAddressOfProviders(String latti, String longi) {

        StringBuilder FULLADDRESS2 = new StringBuilder();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.ENGLISH);


        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);

            if (addresses == null || addresses.size() == 0) {
                FULLADDRESS2 = FULLADDRESS2.append("NA");
            } else {
                for (Address address : addresses) {
                    //  String outputAddress = "";
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        if (i == 1) {
                            FULLADDRESS2.append(address.getAddressLine(i));
                        } else if (i == 2) {
                            FULLADDRESS2.append(",").append(address.getAddressLine(i));
                        }
                    }
                }
		      /* //String address = addresses.get(0).getAddressLine(0);
		       String address = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
		       String city = addresses.get(0).getLocality();
		       String state = addresses.get(0).getAdminArea();
		       String country = addresses.get(0).getCountryName();
		       String postalCode = addresses.get(0).getPostalCode();
		       String knownName = addresses.get(0).getFeatureName();
		       FULLADDRESS=address+","+city+","+state+","+country+","+postalCode;
		      Toast.makeText(contextcopy, "ADDRESS"+address+"city:"+city+"state:"+state+"country:"+country+"postalCode:"+postalCode, Toast.LENGTH_LONG).show();*/

            }

        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        return FULLADDRESS2.toString();

    }

    public void checkHighAccuracyLocationMode(Context context) {
        int locationMode = 0;
        String locationProviders;

        flgLocationServicesOnOff = 0;
        flgGPSOnOff = 0;
        flgNetworkOnOff = 0;
        flgFusedOnOff = 0;
        flgInternetOnOffWhileLocationTracking = 0;

        if (isGooglePlayServicesAvailable()) {
            flgFusedOnOff = 1;
        }
        if (isOnline()) {
            flgInternetOnOffWhileLocationTracking = 1;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Equal or higher than API 19/KitKat
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
                if (locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 1;
                    flgNetworkOnOff = 1;
                    //flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 0;
                    flgNetworkOnOff = 1;
                    // flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 1;
                    flgNetworkOnOff = 0;
                    //flgFusedOnOff=0;
                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            //Lower than API 19
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


            if (TextUtils.isEmpty(locationProviders)) {
                locationMode = Settings.Secure.LOCATION_MODE_OFF;

                flgLocationServicesOnOff = 0;
                flgGPSOnOff = 0;
                flgNetworkOnOff = 0;
                // flgFusedOnOff = 0;
            }
            if (locationProviders.contains(LocationManager.GPS_PROVIDER) && locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                flgLocationServicesOnOff = 1;
                flgGPSOnOff = 1;
                flgNetworkOnOff = 1;
                //flgFusedOnOff = 0;
            } else {
                if (locationProviders.contains(LocationManager.GPS_PROVIDER)) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 1;
                    flgNetworkOnOff = 0;
                    // flgFusedOnOff = 0;
                }
                if (locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 0;
                    flgNetworkOnOff = 1;
                    //flgFusedOnOff = 0;
                }
            }
        }

    }

    public void fnGettingGSTOFflineVal() {
        // Start for getting GST Offline

        String OutletID = "0", QuestID = "0", AnswerType, AnswerValue = "";
        int sectionID = 0;
        int QuestionGroupID = 0;


        for (Entry<String, String> entry : hmapStoreQuestAnsNew.entrySet()) {
            String questId = entry.getKey().split(Pattern.quote("^"))[0].toString();
            AnswerType = entry.getKey().split(Pattern.quote("^"))[1].toString();
            QuestionGroupID = Integer.valueOf(entry.getKey().split(Pattern.quote("^"))[2].toString());
            AnswerValue = entry.getValue();

            String optionValue = "0";

            if (questId.equals("49")) {
                try {
                    //flgGSTCompliance=mDataSource.fnGetGstOptionIDComplianceWhileAddingNewStore(""+AnswerValue);
                    String OptionDescr = mDataSource.fnGetOptionDescrFromtblGetPDAQuestOptionMstr(questId, "" + AnswerValue);
                    if (OptionDescr.equals("Yes")) {
                        flgGSTCompliance = "1";
                    } else if (OptionDescr.equals("Not Required")) {
                        flgGSTCompliance = "0";
                    } else if (OptionDescr.equals("Pending")) {
                        flgGSTCompliance = "2";
                    }

                } catch (Exception e) {

                }
            }
            if (questId.equals("50")) {
                if (!AnswerValue.equals("")) {
                    GSTNumber = AnswerValue;
                }
            }


        }

        // End for gst getting
    }

    public String getAddressNewWay(String ZeroIndexAddress, String city, String State, String pincode, String country) {
        String editedAddress = ZeroIndexAddress;
        if (editedAddress.contains(city)) {
            editedAddress = editedAddress.replace(city, "");

        }
        if (editedAddress.contains(State)) {
            editedAddress = editedAddress.replace(State, "");

        }
        if (editedAddress.contains(pincode)) {
            editedAddress = editedAddress.replace(pincode, "");

        }
        if (editedAddress.contains(country)) {
            editedAddress = editedAddress.replace(country, "");

        }
        if (editedAddress.contains(",")) {
            editedAddress = editedAddress.replace(",", " ");

        }

        return editedAddress;
    }

    @Override
    public void selectedCityState(String selectedCategory, Dialog dialog, int flgCityState) {
        NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
        if (null != recFragment) {
            recFragment.selectedCityState(selectedCategory, dialog, flgCityState);
        }
    }


    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            // TODO Auto-generated method stub
            if (resultCode == 1) {
                final Address address = resultData.getParcelable("RESULT_ADDRESS");
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        String address1 = resultData.getString("RESULT_DATA_KEY");
                        fetchAddress = address1;
                        txtAddress.setText(address1);
                    }
                });
            }
        }
    }


    public String convertExponential(double firstNumber) {
        String secondNumberAsString = String.format("%.10f", firstNumber);
        return secondNumberAsString;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
        if (null != recFragment) {

        }
    }

    @Override
    public void successNewStoreNumberValidation(TblDuplicateContact tblDuplicateContact,String ServerNumber) {
        if(tblDuplicateContact.getFlgDuplicate()==1)
        {
            if(tblDuplicateContact.getExistingPersonname()!=null)
            showAlertDuplicateNumberValidationFromServer("" +ServerNumber +" already exists on Route: "+tblDuplicateContact.getRouteName()+" for retailer: " +tblDuplicateContact.getExistingStoreName()+". To add the retailer, please enter correct contact number");
            else
                showAlertDuplicateNumberValidationFromServer("" +ServerNumber +" already exists on Route: "+tblDuplicateContact.getRouteName()+" for retailer: " +tblDuplicateContact.getExistingStoreName()+". To add the retailer, please enter correct contact number");
        }
        else
        {
            mDataSource.fnsaveOutletQuestAnsMstrSectionWise(hmapStoreQuestAnsNew, 0, selStoreID);

            long syncTIMESTAMP = System.currentTimeMillis();
            Date datefromat = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);


            String VisitEndFinalTS = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_TIME_FORMAT);
            String VisitDate = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_FORMAT);
            // int DatabaseVersion=CommonInfo.DATABASE_VERSIONID;
            int ApplicationID = CommonInfo.Application_TypeID;
            //getSectionNextOrBack(3,sectionToShowHide );

            String VisitStartTS = df.format(datefromat);
            fnGettingGSTOFflineVal();
            checkHighAccuracyLocationMode(AddNewStore_DynamicSectionWise.this);


            String selectedBeatName = "";
            String slctdBeatId = "0";
            String slctdBeatNodeType = "0";
            String selectedDistributorName = "";
            String slctdDistributorID = "0";
            String slctdDistributorNodeType = "0";
            String selectedDistributorDisText="NA";
            String selectedStoreTypeDesc="NA";
            LinkedHashMap<String, String> hmapStoreQuestAnsNew = new LinkedHashMap<String, String>();
            LinkedHashMap<String, String> hmapStoreAddress = new LinkedHashMap<String, String>();
            int IsDiscountApplicable=0;
            String IsDiscountAllowed="NA";
            NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
            if (null != recFragment) {
                recFragment.saveDynamicQuesAns(true);
                hmapStoreQuestAnsNew = recFragment.hmapAnsValues;
                hmapStoreAddress = recFragment.hmapAddress;
                selectedBeatName = recFragment.getSelectedBeatName();



                if (!selectedBeatName.equals("0") && (selectedBeatName.contains("-"))) {
                    slctdBeatId = selectedBeatName.split(Pattern.quote("-"))[1];
                    slctdBeatNodeType = selectedBeatName.split(Pattern.quote("-"))[2];
                }
                selectedDistributorName= recFragment.getSelectedDistributorName();

                if (!selectedDistributorName.equals("0")) {
                    slctdDistributorID = selectedDistributorName.split(Pattern.quote("-"))[1];
                    slctdDistributorNodeType = selectedDistributorName.split(Pattern.quote("-"))[2];
                    IsDiscountApplicable=mDataSource.fnCheckflgDiscountApplicableAgainstDistributor(Integer.parseInt(slctdDistributorID));
                    IsDiscountAllowed=mDataSource.fnCheckflgDiscountAllowedAgainstDistributor(Integer.parseInt(slctdDistributorID));
                    selectedDistributorDisText=mDataSource.fnCheckflgDiscountAllowedAgainstDistributorDescr(Integer.parseInt(slctdDistributorID));
                }
                // fnlStoreType = recFragment.currentStoreType;//Original
                //For Now Change StoreType=1
                fnlStoreType="1";
                selectedStoreTypeDesc=recFragment.getSelectedStoreTypeDescr();
                fnlAddressName = recFragment.currentAddressName;


                fnlOwnerName = recFragment.currentOwnerName;
                fnlMobileNumber = recFragment.currentMobileNumber;
                fnlAddressName = recFragment.currentAddressName;
                fnSalesPersonName = recFragment.currentSalesPersonName;
                fnSalesPersonContactNo = recFragment.currentSalesPersonContactNo;
                fnStoreCatType = recFragment.currentStoreCatType;

                if (!TextUtils.isEmpty(recFragment.distBId)) {
                    distID = recFragment.distBId;

                }
            }


            if (FLAG_NEW_UPDATE.equals("UPDATE")) {
                //mDataSource.open();
                mDataSource.UpdateStoreReturnphotoFlagSM(selStoreID, StoreName, 0);
                //mDataSource.close();
            } else {
                //mDataSource.open();
                mDataSource.saveTblPreAddedStoresAddStoreDynamic(selStoreID, StoreName, LattitudeFromLauncher, LongitudeFromLauncher, VisitDate, 1, 0, 3,slctdBeatId,slctdBeatNodeType);//,Integer.parseInt(slctdBeatId),Integer.parseInt(slctdBeatNodeType));
                //mDataSource.close();
            }

            //mDataSource.open();
            mDataSource.deletetblstoreMstrOnStoreIDBasis(selStoreID);
            if ((!fnlStoreType.equals("0")) && fnlStoreType.contains("-")) {
                fnlStoreType = fnlStoreType.split(Pattern.quote("-"))[1];
            }
            //RetailerName

            mDataSource.savetblStoreMain(slctdBeatId, selStoreID, StoreName, "NA", "NA", "NA", "NA", fnlAddressName, "NA", "NA", "0", StoreTypeTradeChannel,
                    Integer.parseInt(fnlStoreType), 0, 0, 0, "NA", VisitStartTS, imei, "" + battLevel, 3, 1, String.valueOf(LattitudeFromLauncher), String.valueOf(LongitudeFromLauncher), "" + AccuracyFromLauncher, "" + ProviderFromLauncher, 0, FusedAddressFromLauncher, allValuesOfPaymentStageID, flgHasQuote, flgAllowQuotation, flgSubmitFromQuotation, flgGSTCapture, flgGSTCompliance, GSTNumber, flgGSTRecordFromServer, flgLocationServicesOnOff, flgGPSOnOff, flgNetworkOnOff, flgFusedOnOff, flgInternetOnOffWhileLocationTracking, flgRestart, flgStoreOrder, hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"), selectedDistributorName, fnlOwnerName, fnlMobileNumber, fnStoreCatType, flgRuleTaxVal, flgTransType, fnlMobileNumber, fnSalesPersonName, fnSalesPersonContactNo, IsComposite, Integer.parseInt(hmapStoreAddress.get("5")), Integer.parseInt(hmapStoreAddress.get("4")),IsDiscountApplicable, Integer.parseInt(hmapStoreAddress.get("7")),IsDiscountAllowed,selectedDistributorDisText,selectedStoreTypeDesc);

            mDataSource.saveSOAPdataStoreListDetailsInNewTable(selStoreID, hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"), 1);



            StoreSelection.flgChangeRouteOrDayEnd = 0;
            DayStartActivity.flgDaySartWorking = 0;






            //

            String presentRoute = mDataSource.GetActiveRouteID();


            Intent mMyServiceIntent = new Intent(getCtx(), SyncJobService.class);  //is any of that needed?  idk.
            mMyServiceIntent.putExtra("whereTo", "Regular");//
            mMyServiceIntent.putExtra("storeID", selStoreID);
            mMyServiceIntent.putExtra("routeID", presentRoute);//

            SyncJobService.enqueueWork(getCtx(), mMyServiceIntent);


            if (activityFrom.equals("StoreSelection")) {
                if (flgOrderOrOnlyAdd == 1) {
                    if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                        if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ActualVisitStock.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("StoreVisitCode",StoreVisitCode);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            startActivity(nxtP4);
                            finish();

                        } else {

                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                            //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            nxtP4.putExtra("flgOrderType", 1);
                            startActivity(nxtP4);
                            finish();
                        }
                    } else {
                        Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                        //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                        nxtP4.putExtra("storeID", selStoreID);
                        nxtP4.putExtra("SN", StoreName);
                        nxtP4.putExtra("imei", imei);
                        nxtP4.putExtra("userdate", VisitDate);
                        nxtP4.putExtra("pickerDate", VisitDate);
                        nxtP4.putExtra("flgOrderType", 1);
                        startActivity(nxtP4);
                        finish();
                    }
                } else if (flgOrderOrOnlyAdd == 2) {
                    Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                    ide.putExtra("userDate", date_value);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("imei", imei);
                    ide.putExtra("rID", rID);
                    AddNewStore_DynamicSectionWise.this.startActivity(ide);
                    finish();
                } else {
                    Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                    ide.putExtra("userDate", date_value);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("imei", imei);
                    ide.putExtra("rID", rID);
                    AddNewStore_DynamicSectionWise.this.startActivity(ide);
                    finish();
                }


            } else if (activityFrom.equals("AllButtonActivity")) {

               /* Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                intent.putExtra("activityFrom", "AllButtonActivity");
                startActivity(intent);
                finish();*/

                if (flgOrderOrOnlyAdd == 1) {
                    if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                        if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ActualVisitStock.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("StoreVisitCode",StoreVisitCode);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            startActivity(nxtP4);
                            finish();

                        } else {

                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                            //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            nxtP4.putExtra("flgOrderType", 1);
                            startActivity(nxtP4);
                            finish();
                        }
                    } else {
                        Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                        //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                        nxtP4.putExtra("storeID", selStoreID);
                        nxtP4.putExtra("SN", StoreName);
                        nxtP4.putExtra("imei", imei);
                        nxtP4.putExtra("userdate", VisitDate);
                        nxtP4.putExtra("pickerDate", VisitDate);
                        nxtP4.putExtra("flgOrderType", 1);
                        startActivity(nxtP4);
                        finish();
                    }
                } else if (flgOrderOrOnlyAdd == 2) {
                    Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                    intent.putExtra("activityFrom", "AllButtonActivity");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                    intent.putExtra("activityFrom", "AllButtonActivity");
                    startActivity(intent);
                    finish();
                }

            } else {
                {
                    if (flgOrderOrOnlyAdd == 1) {
                        if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                            if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                                Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ActualVisitStock.class);
                                nxtP4.putExtra("storeID", selStoreID);
                                nxtP4.putExtra("SN", StoreName);
                                nxtP4.putExtra("StoreVisitCode",StoreVisitCode);
                                nxtP4.putExtra("imei", imei);
                                nxtP4.putExtra("userdate", VisitDate);
                                nxtP4.putExtra("pickerDate", VisitDate);
                                startActivity(nxtP4);
                                finish();

                            } else {

                                Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                                //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                                nxtP4.putExtra("storeID", selStoreID);
                                nxtP4.putExtra("SN", StoreName);
                                nxtP4.putExtra("imei", imei);
                                nxtP4.putExtra("userdate", VisitDate);
                                nxtP4.putExtra("pickerDate", VisitDate);
                                nxtP4.putExtra("flgOrderType", 1);
                                startActivity(nxtP4);
                                finish();
                            }
                        } else {
                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                            //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            nxtP4.putExtra("flgOrderType", 1);
                            startActivity(nxtP4);
                            finish();
                        }
                    } else if (flgOrderOrOnlyAdd == 2) {
                        Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                        ide.putExtra("userDate", date_value);
                        ide.putExtra("pickerDate", pickerDate);
                        ide.putExtra("imei", imei);
                        ide.putExtra("rID", rID);
                        AddNewStore_DynamicSectionWise.this.startActivity(ide);
                        finish();
                    } else {
                        Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                        ide.putExtra("userDate", date_value);
                        ide.putExtra("pickerDate", pickerDate);
                        ide.putExtra("imei", imei);
                        ide.putExtra("rID", rID);
                        AddNewStore_DynamicSectionWise.this.startActivity(ide);
                        finish();
                    }



                }
            }


        }
    }

    @Override
    public void failureNewStoreNumberValidation() {
        showAlertDuplicateNumberValidationFromServer("Error while validating store mobile number.\nPlease Contact Administrator.");
    }

    public void showAlertMobileOtherValidation(String msg) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);
        alertDialogNoConn.setTitle(getText(R.string.AlertDialogHeaderErrorMsg));
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }
}
