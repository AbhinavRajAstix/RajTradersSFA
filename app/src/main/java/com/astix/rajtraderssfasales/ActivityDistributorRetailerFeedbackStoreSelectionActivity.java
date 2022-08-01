package com.astix.rajtraderssfasales;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.allana.truetime.TimeUtils;
import com.astix.Common.CommonInfo;

import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceActivityScoutingShowHideOthersBox;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceServerSuccessEntry;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceServerSuccessRetailerFeedBackEntry;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceYearMonth;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorCompetitorBrandMstr;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorCompetitorCompanyMstr;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorRetailersFeedBackDetails;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblServerEntryStatus;
import com.astix.rajtraderssfasales.camera.CameraPreview;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.location.LocationInterfaceDistributorScouting;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobalDistributorScouting;
import com.astix.rajtraderssfasales.sync.DatabaseAssistant;
import com.astix.rajtraderssfasales.sync.SyncJobService;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.zbra.androidlinq.Linq.stream;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityDistributorRetailerFeedbackStoreSelectionActivity extends BaseActivity implements InterfaceYearMonth, LocationInterfaceDistributorScouting, OnMapReadyCallback, DeletePic , DatePickerDialog.OnDateSetListener, InterfaceServerSuccessEntry, InterfaceServerSuccessRetailerFeedBackEntry, InterfaceActivityScoutingShowHideOthersBox {


    /**
     * Constant used in the location settings dialog.
     */
    public String date;
    public String pickerDate;
   /* int FLGJOINTVISIT=0;

    int VisitType=0;*/
    ProgressDialog mProgressDialogReport;
    int flgRetailerFeedBackStatus = 0;
    int flgFinalSubmit = 0;
    HashMap<Integer,Integer> hashMapRetailerSectionNoAndFeedBackStatus=new HashMap<Integer,Integer>();
    public int SelectedVechileID=0;
    public String SelectedVechileType="Select Vechile";
    boolean boolScreenSectionOne = true;
    int PersonNodeId =0;
    int PersonNodeType =0;
    String GstUserInput = "NA";
    String addressUserInput = "";
    String cityUserInput = "";
    String pinCodeUserInput = "";
    String stateCodeUserInput = "";
    String contactNumberUserInput = "";
    String districtUserInput = "";
    public String fnLati = "0";
    public String fnLongi = "0";
    public Double fnAccuracy = 0.0;
    String flgGSTCapture = "0", flgGSTCompliance = "0";
    public String address, pincode, city, state, latitudeToSave, longitudeToSave, accuracyToSave;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static String imei = "";

    public int ScoutingDistributorNodeType = 180;
    private final LinkedHashMap<String, Integer> hmapCtgry_Imageposition = new LinkedHashMap<>();
    private final LinkedHashMap<String, String> hmapPhotoDetailsForSaving = new LinkedHashMap<>();
    private final LinkedHashMap<String, ArrayList<String>> hmapCtgryPhotoSection = new LinkedHashMap<>();
    private final LinkedHashMap<String, ImageAdapter> hmapImageAdapter = new LinkedHashMap<>();
    public String SpokeIDForImg = "0";
    public Activity mActivity;
    Context mContext;
    public int battLevel = 0;
    public String AccuracyFromLauncher = "NA";
    public String gblRouteSessionID = "";
    public int flgCallSaving = 0;
    DatabaseAssistant DASFA;
    ProgressDialog pDialog2;



    @BindView(R.id.scroll_view)
    ScrollView scroll_view;



    @BindView(R.id.bt_Submit)
    Button bt_Submit;



    @BindView(R.id.ll_MarketReputationCheckWholeSection)
    LinearLayout ll_MarketReputationCheckWholeSection;


    @BindView(R.id.ll_MarketReputationRetailerSection)
    LinearLayout ll_MarketReputationRetailerSection;


    String ScoutingDistributorID = "";
    String ScoutingDistributorRetailerSectionID = "";
    int flgNewScoutingOrOldDistributor = 0;


    int refreshCount = 0;
    ArrayList<String> list_ImgName;
    boolean flgListEmpty = false;
    String LattitudeFromLauncher = "NA";
    String LongitudeFromLauncher = "NA";
    String FnlAddress = "NA", finalPinCode = "NA", finalCity = "NA", finalState = "NA", fnAccurateProvider = "NA", AllProvidersLocation = "NA", FusedAddress = "NA", GpsLat = "NA", GpsLong = "NA", GpsAccuracy = "NA", GpsAddress = "NA", NetwLat = "NA", NetwLong = "NA", NetwAccuracy = "NA", NetwAddress = "NA", FusedLat = "NA", FusedLong = "NA", FusedAccuracy = "NA", FusedLocationLatitudeWithFirstAttempt = "NA", FusedLocationLongitudeWithFirstAttempt = "NA", FusedLocationAccuracyWithFirstAttempt = "NA";
    int flgLocationServicesOnOff = 0, flgGPSOnOff = 0, flgNetworkOnOff = 0, flgFusedOnOff = 0, flgInternetOnOffWhileLocationTracking = 0, flgRestart = 0;


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


    public String GPSAddressOnLoad = "NA";
    public String NetworkAddressOnLoad = "NA";
    public String FusedAddressOnLoad = "NA";
    MapFragment mapFrag;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    int flgGST = -1;

    int flgIntrestedInDistributorShip = -1;

    String cityID = "NA";
    String StateID = "NA";
    String MapAddress = "NA";
    String MapPincode = "NA";
    String MapCity = "NA";
    String MapState = "NA";
    int countSubmitClicked = 0;

    LocationRetreivingGlobalDistributorScouting locationRetreivingGlobal;

    String formattedDate = "";
    StringBuilder validationString;
    String spoke_type = "";
    String spokeTypeText = "";
    LinkedHashMap<Integer, String> hashMapFlags = new LinkedHashMap<>();
    int selectedVillagePosition = 0;
    // private List<TblNotCoveredPVVillageListForPDA> mTblNotCoveredPVVillageListForPDAS;
    private List<String> villageList;
    private List<String> spokeList;
    private boolean cameraFront = false;
    private Dialog dialog;
    private ArrayList<Object> arrImageData;
    private LinearLayout cameraPreview;
    private CameraPreview mPreview;
    private Camera mCamera;
    private Button switchCamera;
    private Camera.PictureCallback mPicture;
    private boolean isLighOn = false;
    private ImageView flashImage;




    private Button capture, cancelCam;
    private float mDist = 0;
    private String imageName;
    private Uri uriSavedImage;
    private String[] mSpokes;
    private String[] mCitys;
    private String[] mDistricts;
    private String[] mTehsils;
    private String[] mStates;
    private final int mSelectedSpoke = -1;
    private final int mSelectedCity = -1;
    private int mSelectedDistrict = -1;
    private int mSelectedTehsil = -1;
    private int mSelectedState = -1;
    //  private List<TblSpokeProfile> tblSpokeProfiles;
    //private List<TblExistingSpokeList> tblExistingSpokeLists;
    // private TblSupplierMstrList tblSpokeProfile;
    //private TblLastPDAActivityDetails tblLastPDAActivityDetails;
    private AppDataSource mDataSource;
    private LocationManager locationManager;
    private FragmentManager manager;
    private int picAddPosition = 0;
    private int removePicPositin = 0;
    private String clickedTagPhoto = "";
    private boolean isGpsModifyAsked = false;
    private int spokeId;
    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {

            battLevel = intent.getIntExtra("level", 0);

        }
    };
    private boolean isLocationConfirm = false;

    private int flgLocationConfirm = 0;
    private boolean isLocationSelected = false;
    private int locationCount = 0;
    private int flgHasVehicle = -1;
    private int vehicleType = 0;
    private int flgForSpokeEdit;
    private int flgForSpokeAddressEdit;
    private int flgForProfileCalledFrom = 0;
    private int selectedAnchorVillageId = -1;
    //private TblNotCoveredPVVillageListForPDA anchorVillage;
    private int selectedExistingSpokeID;
    private int flgIsReplacement;
    private int flgIsAllowedDistanceWarning = 0;
    private int selectedSpokePosition;
    private int flgSpokeTypeEdit;


    private List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsList;

    private List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving;

    public void onDestroy() {
        super.onDestroy();
        // unregister receiver
        mActivity.unregisterReceiver(this.mBatInfoReceiver);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_retailer_feedback_store_selection);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        mActivity = this;
        mContext=this;
        //tblLastPDAActivityDetails=new TblLastPDAActivityDetails();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        DASFA = new DatabaseAssistant(this);
        //spokeId = 1023;

        Intent intent = getIntent();
        ScoutingDistributorID = intent.getStringExtra("ScoutingDistributorID");
        ScoutingDistributorRetailerSectionID = intent.getStringExtra("ScoutingDistributorRetailerSectionID");
        date = intent.getStringExtra("userdate");
        pickerDate = intent.getStringExtra("pickerDate");
        /*FLGJOINTVISIT=intent.getIntExtra(AppUtils.FLGJOINTVISIT,0);
        VisitType=intent.getIntExtra(AppUtils.VisitType,0);
*/

        // spokeTypeText = getIntent().getExtras().getString(AppUtils.SPOKETYPE);
        arrImageData = new ArrayList<>();
        imei = AppUtils.getToken(mActivity);
        mDataSource = AppDataSource.getInstance(this);


            flgNewScoutingOrOldDistributor=1;

        String PersonNodeIdAndNodeType = mDataSource.fngetSalesPersonMstrData();
        if (!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId =Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType =Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        initViews();


        fnCreateRetailerSectionForFeedBack();


        populateDistributorScoutingRetailerDetailsFromServer();

        TimeUtils.checkTimeDifference(this, TimeUtils.DATE_TIME_FORMAT, false);


      //  ll_MarketReputationRetailerSection.setVisibility(View.GONE);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            String rID = mDataSource.GetActiveRouteID();//GetActiveRouteID(CommonInfo.CoverageAreaNodeID, CommonInfo.CoverageAreaNodeType);
            Intent prevP2 = new Intent(ActivityDistributorRetailerFeedbackStoreSelectionActivity.this, StoreSelection.class);
            prevP2.putExtra("token", imei);
            prevP2.putExtra("userDate", date);
            prevP2.putExtra("pickerDate", pickerDate);
            prevP2.putExtra("rID", rID);
          /*  prevP2.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
            prevP2.putExtra(AppUtils.FLGJOINTVISIT, FLGJOINTVISIT);
            prevP2.putExtra(AppUtils.VisitType, VisitType);*/
            startActivity(prevP2);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initViews() {


       // bt_Submit.setVisibility(View.GONE);


        scroll_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getCurrentFocus() instanceof EditText) {
                    EditText editText = (EditText) getCurrentFocus();
                    if (editText.hasFocus())
                        editText.clearFocus();
                }
                return false;
            }
        });



    }


    private void validateAndSaveData() {
        tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving=new ArrayList<TblPotentialDistributorRetailersFeedBackDetails>();
        if(ll_MarketReputationCheckWholeSection!=null && ll_MarketReputationCheckWholeSection.getChildCount()>0) {

            for (int i = 0; i < 1; i++) {
                LinearLayout view = (LinearLayout) ll_MarketReputationCheckWholeSection.getChildAt(i);


                String RetailerName="";
                String RetailerAddress="";
                String RetailerComment="";
                String RetailerContactNumber="";
                String DBRName="";
                EditText txt_RetailerName = view.findViewWithTag("etRetailerName_"+i);
                EditText txt_RetailerAddress = view.findViewWithTag("etRetailerAddress_"+i);
                EditText txt_RetailerComment = view.findViewWithTag("etRetailerComment_"+i);
                RadioGroup rg_RetailerFeedBack = view.findViewWithTag("rgRetailerFeedBack_"+i);
                EditText et_RetailerContactNumber = view.findViewWithTag("etRetailerContactNumber_"+i);
                EditText txt_DistributorName = view.findViewWithTag("etDBRName_"+i);
                if (txt_RetailerName != null && !TextUtils.isEmpty(txt_RetailerName.getText().toString().trim())) {
                    RetailerName=txt_RetailerName.getText().toString().trim().replace("&","-");
                }
                if (txt_RetailerAddress != null && !TextUtils.isEmpty(txt_RetailerAddress.getText().toString().trim())) {
                    RetailerAddress=txt_RetailerAddress.getText().toString().trim().replace("&","-");
                }
                if (txt_RetailerComment != null && !TextUtils.isEmpty(txt_RetailerComment.getText().toString().trim())) {
                    RetailerComment=txt_RetailerComment.getText().toString().trim().replace("&","-");
                }
                if (txt_DistributorName != null && !TextUtils.isEmpty(txt_DistributorName.getText().toString().trim())) {
                    DBRName=txt_DistributorName.getText().toString().trim().replace("&","-");
                }
                if (et_RetailerContactNumber!=null && !TextUtils.isEmpty(et_RetailerContactNumber.getText().toString().trim())) {
                    RetailerContactNumber=et_RetailerContactNumber.getText().toString().trim();
                }
                flgRetailerFeedBackStatus = 0;
                if(hashMapRetailerSectionNoAndFeedBackStatus!=null && hashMapRetailerSectionNoAndFeedBackStatus.containsKey(i))
                {
                    flgRetailerFeedBackStatus=hashMapRetailerSectionNoAndFeedBackStatus.get(i);
                }

                if(TextUtils.isEmpty(RetailerName) && TextUtils.isEmpty(RetailerAddress) && (TextUtils.isEmpty(RetailerComment)  && flgRetailerFeedBackStatus==0)  && TextUtils.isEmpty(RetailerContactNumber))
                {

                }
                else
                {
                    if(!TextUtils.isEmpty(RetailerName) && !TextUtils.isEmpty(RetailerAddress) && (!TextUtils.isEmpty(RetailerComment)  && flgRetailerFeedBackStatus!=0) && !TextUtils.isEmpty(RetailerContactNumber))//  && (!TextUtils.isEmpty(RetailerComment)  && flgRetailerFeedBackStatus!=0)
                    {
                        if (!et_RetailerContactNumber.getText().toString().trim().equals("")) {
                            if (!isNumberValid(et_RetailerContactNumber.getText().toString().trim())) {
                                tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving.clear();
                                showAlertSingleButtonInfo("Please enter correct contact number");
                                return;
                            }
                        }
                        else
                        {
                            TblPotentialDistributorRetailersFeedBackDetails tblPotentialDistributorRetailersFeedBackDetails=new TblPotentialDistributorRetailersFeedBackDetails();
                            tblPotentialDistributorRetailersFeedBackDetails.setRetailerSectionID(ScoutingDistributorRetailerSectionID);

                            tblPotentialDistributorRetailersFeedBackDetails.setNodeType(ScoutingDistributorNodeType);
                            if(flgNewScoutingOrOldDistributor==0)
                                tblPotentialDistributorRetailersFeedBackDetails.setNodeID("0");
                            else
                                tblPotentialDistributorRetailersFeedBackDetails.setNodeID(ScoutingDistributorID);
                            tblPotentialDistributorRetailersFeedBackDetails.setPDAcode(AppUtils.getToken(ActivityDistributorRetailerFeedbackStoreSelectionActivity.this));
                            tblPotentialDistributorRetailersFeedBackDetails.setEntryDate(TimeUtils.getNetworkDate(ActivityDistributorRetailerFeedbackStoreSelectionActivity.this,"dd-MMM-yyyy"));
                            tblPotentialDistributorRetailersFeedBackDetails.setRetailerName(RetailerName);
                            tblPotentialDistributorRetailersFeedBackDetails.setAddress(RetailerAddress);
                            tblPotentialDistributorRetailersFeedBackDetails.setDBRName(DBRName);
                            tblPotentialDistributorRetailersFeedBackDetails.setComment(RetailerComment);
                            tblPotentialDistributorRetailersFeedBackDetails.setContactNumber(RetailerContactNumber);

                            tblPotentialDistributorRetailersFeedBackDetails.setFeedback(flgRetailerFeedBackStatus);
                            tblPotentialDistributorRetailersFeedBackDetails.setFlgFinalSubmit(flgFinalSubmit);
                            tblPotentialDistributorRetailersFeedBackDetails.setSstat(3);
                            // flgFinalSubmit=0;

                            if(flgNewScoutingOrOldDistributor==0)
                                tblPotentialDistributorRetailersFeedBackDetails.setNewDBRCode(ScoutingDistributorID);
                            else
                                tblPotentialDistributorRetailersFeedBackDetails.setNewDBRCode("0");

                            tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving.add(tblPotentialDistributorRetailersFeedBackDetails);
                        }

                    }
                    else
                    {
                        showAlertSingleButtonInfo("Partial filled section is not allowed, Please complete that.");
                        return;
                    }
                }



            }
        }

        saveDataToDatabase();



    }



    @OnClick(R.id.bt_Submit)
    public void SubmitData(View view) {
        flgFinalSubmit=2;
        validateAndSaveData();
    }



    public void SyncNow() {



    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    public void showAlertSingleButtonInfoSubmissionSuceesful(String msg) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        String rID = mDataSource.GetActiveRouteID();//GetActiveRouteID(CommonInfo.CoverageAreaNodeID, CommonInfo.CoverageAreaNodeType);
                        Intent prevP2 = new Intent(ActivityDistributorRetailerFeedbackStoreSelectionActivity.this, StoreSelection.class);
                        prevP2.putExtra("token", imei);
                        prevP2.putExtra("userDate", date);
                        prevP2.putExtra("pickerDate", pickerDate);
                        prevP2.putExtra("rID", rID);
                     /*   prevP2.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
                        prevP2.putExtra(AppUtils.FLGJOINTVISIT, FLGJOINTVISIT);
                        prevP2.putExtra(AppUtils.VisitType, VisitType);*/
                        startActivity(prevP2);
                        finish();
                    }
                }).create().show();
    }



    private void populateDistributorScoutingRetailerDetailsFromServer()
    {
        tblPotentialDistributorRetailersFeedBackDetailsList=new ArrayList<TblPotentialDistributorRetailersFeedBackDetails>();
        tblPotentialDistributorRetailersFeedBackDetailsList=mDataSource.fnGettblDistRetAddressDetailListParticualrDistributorRetailer(ScoutingDistributorID,ScoutingDistributorRetailerSectionID);
        if(tblPotentialDistributorRetailersFeedBackDetailsList!=null && tblPotentialDistributorRetailersFeedBackDetailsList.size()>0)
        {
            if(ll_MarketReputationCheckWholeSection!=null && ll_MarketReputationCheckWholeSection.getChildCount()>0) {

                for (int i = 0; i < 1; i++) {

                    int finalI = i;
                    List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsListLocal=stream(tblPotentialDistributorRetailersFeedBackDetailsList).where(p->p.getRetailerSectionID().equals(ScoutingDistributorRetailerSectionID)).toList();
                    LinearLayout view = (LinearLayout) ll_MarketReputationCheckWholeSection.getChildAt(i);

                    EditText txt_RetailerName = view.findViewWithTag("etRetailerName_"+i);
                    EditText txt_RetailerAddress = view.findViewWithTag("etRetailerAddress_"+i);
                    EditText txt_RetailerComment = view.findViewWithTag("etRetailerComment_"+i);
                    RadioGroup rg_RetailerFeedBack = view.findViewWithTag("rgRetailerFeedBack_"+i);
                    EditText txt_DistributorName = view.findViewWithTag("etDBRName_"+i);
                    EditText et_RetailerContactNumber = view.findViewWithTag("etRetailerContactNumber_"+i);

                    if(tblPotentialDistributorRetailersFeedBackDetailsListLocal!=null && tblPotentialDistributorRetailersFeedBackDetailsListLocal.size()>0) {
                        txt_RetailerName.setText(tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getRetailerName());
                        txt_RetailerAddress.setText(tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getAddress());
                        txt_RetailerComment.setText(tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getComment());
                        txt_DistributorName.setText(tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getDBRName());
                        et_RetailerContactNumber.setText(tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getContactNumber());
                        hashMapRetailerSectionNoAndFeedBackStatus.put(i,tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getFeedback());
                       if(tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getFeedback()==1)
                       {
                           rg_RetailerFeedBack.check(R.id.rb_RetailerFeedBackGood);//.findViewById(R.id.rb_RetailerFeedBackGood).setSelected(true);
                       }
                        if(tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getFeedback()==2)
                        {
                            rg_RetailerFeedBack.check(R.id.rb_RetailerFeedBackBad);//.findViewById(R.id.rb_RetailerFeedBackGood).setSelected(true);
                        }
                    }

                  /*  if(tblPotentialDistributor.getFlgViewOrEdit()==2)
                    {
                        txt_RetailerName.setEnabled(false);
                        txt_RetailerAddress.setEnabled(false);
                        txt_RetailerComment.setEnabled(false);
                        rg_RetailerFeedBack.setEnabled(false);

                        bt_Submit.setVisibility(View.GONE);
                    }*/

                }
            }
        }

    }




    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

            }
        };
        return picture;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    public void showNoConnAlert(final boolean isSyncdCalled) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(this);
        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(R.string.NoDataConnectionFullMsg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!isSyncdCalled) {
                            finish();
                        }

                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

    @Override
    public void delPic(Bitmap bmp, String imageNameToDel) {

    }

    private void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                public void onScanCompleted(String path, Uri uri) {

                }
            });
        } else {
            Log.e("-->", " < 14");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
//                        Log.i(TAG, "User agreed to make required location settings changes.");
//                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
//                        Log.i(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }

    }

    @Override
    public void getProductPhotoDetail(String productIdTag) {
    }



    @Override
    public void onLocationRetrievedDistributorScouting(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress, String District) {
        this.fnLati = String.valueOf(fnLati);
        this.fnLongi = String.valueOf(fnLongi);
        if (finalAccuracy != null)
            this.fnAccuracy = Double.parseDouble(finalAccuracy);
        else
            this.fnAccuracy = 0.0;

        this.address = fnAddress;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.fnAccurateProvider = fnAccurateProvider;
        this.AllProvidersLocation = AllProvidersLocation;
        this.FusedAddressOnLoad = FusedAddress;
        this.GPSLocationLatitude = GpsLat;
        this.GPSLocationLongitude = GpsLong;
        this.GPSLocationAccuracy = GpsAccuracy;
        this.GPSAddressOnLoad = GpsAddress;
        this.NetworkLocationLatitude = NetwLat;
        this.NetworkLocationLongitude = NetwLong;
        this.NetworkLocationAccuracy = NetwAccuracy;
        this.NetworkAddressOnLoad = NetwAddress;
        this.FusedLocationLatitude = FusedLat;
        this.FusedLocationLongitude = FusedLong;
        this.FusedLocationAccuracy = FusedAccuracy;
        this.FusedLocationLatitudeWithFirstAttempt = FusedLocationLatitudeWithFirstAttempt;
        this.FusedLocationLongitudeWithFirstAttempt = FusedLocationLongitudeWithFirstAttempt;
        this.FusedLocationAccuracyWithFirstAttempt = FusedLocationAccuracyWithFirstAttempt;
        this.flgLocationServicesOnOff = flgLocationServicesOnOff;
        this.flgGPSOnOff = flgGPSOnOff;
        this.flgNetworkOnOff = flgNetworkOnOff;
        this.flgFusedOnOff = flgFusedOnOff;
        this.flgInternetOnOffWhileLocationTracking = flgInternetOnOffWhileLocationTracking;



        mDataSource.deleteLocationTable();


    }




    private class bgTasker extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog2 = ProgressDialog.show(ActivityDistributorRetailerFeedbackStoreSelectionActivity.this, getText(R.string.PleaseWaitMsg), getText(R.string.genTermProcessingRequest), true);
            pDialog2.setIndeterminate(true);
            pDialog2.setCancelable(false);
            pDialog2.show();

        }

        @Override
        protected void onCancelled() {
            Log.i("bgTasker", "bgTasker Execution Cancelled");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Log.i("bgTasker", "bgTasker Execution cycle completed");
            if (pDialog2 != null)
                pDialog2.dismiss();
            //Move forward
            long syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);


            //dbengine.open();
            String presentRoute = "0";
            //dbengine.close();
            //syncTIMESTAMP = System.currentTimeMillis();
            //Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy.HH.mm.ss", Locale.ENGLISH);
            //fullFileName1 = df.format(dateobj);
            String newfullFileName = AppUtils.fnGetFileNameToSave(mContext);//  AppUtils.getIMEI(this) + "." + presentRoute + "." + AppUtils.doGetTime("dd.MMM.yyyy.HH.mm.ss");//CommonInfo.imei + "." + presentRoute + "." + df.format(dateobj);


            try {

                File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

                if (!OrderXMLFolder.exists()) {
                    OrderXMLFolder.mkdirs();
                }
                //   mDataSource.updateIAllImageRecords(3);


                // DASFA.export(CommonInfo.DATABASE_NAME, newfullFileName, "0");

         /*   mDataSource.savetbl_XMLfiles(newfullFileName, "3", "1");

            mDataSource.UpdateDistributorRetailerFeedbackAfterSync(5,"3");

            mDataSource.deleteLocationTable();*/


                if (isOnline()) {


                    Intent mMyServiceIntent = new Intent(ActivityDistributorRetailerFeedbackStoreSelectionActivity.this, SyncJobService.class);
                    mMyServiceIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                    mMyServiceIntent.putExtra("OrigZipFileName", newfullFileName);
                    mMyServiceIntent.putExtra("whereTo", "DayStart");//
                    SyncJobService.enqueueWork(mContext, mMyServiceIntent);


                } else {

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            showAlertSingleButtonInfoSubmissionSuceesful("Data Submitted successfully.");
        }
    }

    public void saveDataToDatabase() {

        try {
           mDataSource.insert_tblDistRetAddressDetailSingleRetailer(tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving.get(0));
            new bgTasker().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //System.out.println(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            //System.out.println(e);
        }
    }

    public void fnCreateRetailerSectionForFeedBack() {
        ll_MarketReputationCheckWholeSection.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 1; i++) {
            View view = inflater.inflate(R.layout.distributor_retailer_detailes_store_selection_rows, null);
            hashMapRetailerSectionNoAndFeedBackStatus.put(i,0);
            LinearLayout ll_RetailerInnerSection=view.findViewById(R.id.ll_RetailerInnerSection);
            ll_RetailerInnerSection.setTag("llRetailerInnerSection"+i);

            EditText txt_RetailerName=view.findViewById(R.id.txt_RetailerName);
            txt_RetailerName.setTag("etRetailerName_"+i);

            EditText txt_RetailerAddress=view.findViewById(R.id.txt_RetailerAddress);
            txt_RetailerAddress.setTag("etRetailerAddress_"+i);

            EditText txt_RetailerComment=view.findViewById(R.id.txt_RetailerComment);
            txt_RetailerComment.setTag("etRetailerComment_"+i);

            RadioGroup rg_RetailerFeedBack=view.findViewById(R.id.rg_RetailerFeedBack);
            rg_RetailerFeedBack.setTag("rgRetailerFeedBack_"+i);

            EditText txt_DistributorName=view.findViewById(R.id.txt_DistributorName);
            txt_DistributorName.setTag("etDBRName_"+i);

            EditText et_RetailerContactNumber=view.findViewById(R.id.et_RetailerContactNumber);
            et_RetailerContactNumber.setTag("etRetailerContactNumber_"+i);

          /*  if(tblPotentialDistributor.getFlgViewOrEdit()==2)
            {
                txt_RetailerName.setEnabled(false);
                txt_RetailerAddress.setEnabled(false);
                txt_RetailerComment.setEnabled(false);
                rg_RetailerFeedBack.getChildAt(0).setEnabled(false);
                rg_RetailerFeedBack.getChildAt(1).setEnabled(false);
            }*/

                rg_RetailerFeedBack.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.rb_RetailerFeedBackBad:
                                flgRetailerFeedBackStatus = 2;
                                int secID = Integer.parseInt(rg_RetailerFeedBack.getTag().toString().split(Pattern.quote("_"))[1]);
                                hashMapRetailerSectionNoAndFeedBackStatus.put(secID, 2);
                                break;

                            case R.id.rb_RetailerFeedBackGood:
                                flgRetailerFeedBackStatus = 1;
                                int secID2 = Integer.parseInt(rg_RetailerFeedBack.getTag().toString().split(Pattern.quote("_"))[1]);
                                hashMapRetailerSectionNoAndFeedBackStatus.put(secID2, 1);
                                break;


                        }


                    }
                });


            et_RetailerContactNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (!et_RetailerContactNumber.getText().toString().isEmpty()) {
                            if (!isNumberValid(et_RetailerContactNumber.getText().toString().trim())) {

                                showAlertSingleButtonInfoForValidation("Please enter 10 digits contact number and number cannot be start with 0, 1, 2, 3 or 4", et_RetailerContactNumber);
                                return;
                            }
                        }
                    }
                }
            });

            view.setTag("llRetaailerSectionNo_"+i);
            ll_MarketReputationCheckWholeSection.addView(view);
        }

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String mon = MONTHS[monthOfYear];



    }



    @Override
    public void fnSelectedMonthYear(int Year, int Month) {
       /* Month=Month-1;
        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String mon = MONTHS[Month];*/


    }


    @Override
    public void successRecord(int flgCalledFrom, List<TblServerEntryStatus> tblServerEntryStatusList) {
        if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
        {
            mProgressDialogReport.dismiss();
        }
    }

    @Override
    public void failureRecord(int flgCalledFrom) {
        if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
        {
            mProgressDialogReport.dismiss();
        }
        showRecordsInsertedError();
    }


    @Override
    public void successRetailerAddressFeebackRecord(int flgCalledFrom, List<TblServerEntryStatus> tblServerEntryStatusList) {
        if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
        {
            mProgressDialogReport.dismiss();
        }
    }

    @Override
    public void failureRetailerAddressFeebackRecord(int flgCalledFrom) {
        if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
        {
            mProgressDialogReport.dismiss();
        }
        showRecordsInsertedError();
    }


    public void showRecordsInsertedError() {
        android.app.AlertDialog.Builder alertDialogSyncError = new android.app.AlertDialog.Builder(ActivityDistributorRetailerFeedbackStoreSelectionActivity.this);
        alertDialogSyncError.setTitle(getText(R.string.errorTxt));
        alertDialogSyncError.setCancelable(false);

        alertDialogSyncError.setMessage("Error in submission.\nPlease contact administrator");


        alertDialogSyncError.setNeutralButton(getText(R.string.AlertDialogOkButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                       /* Intent intent = new Intent(ActivityDistributorScouting.this, SocutingDistributorSelection.class);

                        startActivity(intent);
                        finish();*/


                    }
                });
        alertDialogSyncError.setIcon(R.drawable.info_icon);

        android.app.AlertDialog alert = alertDialogSyncError.create();
        alert.show();
        // alertDialogLowbatt.show();
    }

    @Override
    public void showCompanyOtherBox(TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyMstr) {

    }

    @Override
    public void showBrandOtherBox(TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandMstr) {

    }

    public void showAlertSingleButtonInfoForValidation(String msg, EditText editText) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        editText.requestFocus();
                    }
                }).create().show();
    }
}