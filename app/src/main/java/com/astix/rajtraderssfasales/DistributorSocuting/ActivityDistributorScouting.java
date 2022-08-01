package com.astix.rajtraderssfasales.DistributorSocuting;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allana.truetime.TimeUtils;
import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;

import com.astix.rajtraderssfasales.BaseActivity;
import com.astix.rajtraderssfasales.DeletePic;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceActivityScoutingShowHideOthersBox;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceServerSuccessEntry;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceServerSuccessRetailerFeedBackEntry;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceYearMonth;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributor;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorCompetitorBrandMstr;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorCompetitorCompanyMstr;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorRetailersFeedBackDetails;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorVehicleMasterList;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblServerEntryStatus;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.ScoutingBrandsAdapter;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.ScoutingCompetitorAdapter;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.ScoutingVechileAdapter;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.camera.CameraPreview;
import com.astix.rajtraderssfasales.customwidgets.ExpandableHeightGridView;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.location.LocationInterfaceDistributorScouting;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobalDistributorScouting;
import com.astix.rajtraderssfasales.merchandizing.ImageAdapter;
import com.astix.rajtraderssfasales.sync.DatabaseAssistant;
import com.astix.rajtraderssfasales.sync.SyncJobService;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.astix.rajtraderssfasales.camera.CameraUtils.hasCamera;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityDistributorScouting extends BaseActivity implements InterfaceYearMonth, LocationInterfaceDistributorScouting, OnMapReadyCallback, DeletePic, DatePickerDialog.OnDateSetListener, InterfaceServerSuccessEntry, InterfaceServerSuccessRetailerFeedBackEntry, InterfaceActivityScoutingShowHideOthersBox {


    /**
     * Constant used in the location settings dialog.
     */
    int totalCompanyNewCount=0;
    int totalBrandNewCount=0;
    ProgressDialog mProgressDialogReport;
    int flgRetailerFeedBackStatus = 0;
    int flgFinalSubmit = 0;
    HashMap<Integer,Integer> hashMapRetailerSectionNoAndFeedBackStatus=new HashMap<Integer,Integer>();
    public int SelectedVechileID=0;
    public String SelectedVechileType="Select Vehicle";
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

    @BindView(R.id.rg_yes_no)
    RadioGroup rg_yes_no;
    @BindView(R.id.ll_refresh)
    LinearLayout ll_refresh;
    @BindView(R.id.txt_rfrshCmnt)
    TextView txt_rfrshCmnt;
    @BindView(R.id.btn_refresh)
    Button btn_refresh;
    @BindView(R.id.no_internet_msg)
    TextView no_internet_msg;

    @BindView(R.id.scroll_view)
    ScrollView scroll_view;

    @BindView(R.id.et_Address)
    EditText et_Address;


    @BindView(R.id.spinner_VehicleList)
    Spinner spinner_VehicleList;

    @BindView(R.id.et_VecCount)
    EditText et_VecCount;

    String[] VehicleList_names = null;


    @BindView(R.id.btn_camera)
    Button btn_camera;
    @BindView(R.id.recycler_view_images)
    ExpandableHeightGridView expandableHeightGridView;
    @BindView(R.id.bt_Next)
    Button bt_Next;

    @BindView(R.id.bt_Submit)
    Button bt_Submit;

    @BindView(R.id.bt_Save)
    Button bt_Save;

    @BindView(R.id.bt_Previous)
    Button bt_Previous;

    @BindView(R.id.bt_Skip)
    Button bt_Skip;


    @BindView(R.id.ll_MarketReputationCheckWholeSection)
    LinearLayout ll_MarketReputationCheckWholeSection;


    @BindView(R.id.ll_MarketReputationRetailerSection)
    LinearLayout ll_MarketReputationRetailerSection;

    @BindView(R.id.ll_sectionOtherThenRetailerFeedBack)
    LinearLayout ll_sectionOtherThenRetailerFeedBack;

    @BindView(R.id.et_Dealer_Firm_Name)
    EditText et_Dealer_Firm_Name;

    @BindView(R.id.et_ContactPerson)
    EditText et_ContactPerson;

    @BindView(R.id.et_MobileNo)
    EditText et_MobileNo;

    @BindView(R.id.et_teleNo)
    EditText et_teleNo;

    @BindView(R.id.et_emailID)
    EditText et_emailID;

    @BindView(R.id.rg_gst)
    RadioGroup rg_gst;

    @BindView(R.id.et_gstno)
    EditText gstno_txt;

    @BindView(R.id.ll_gst_num)
    LinearLayout ll_gst_num;

    @BindView(R.id.et_AreaCovered)
    EditText et_AreaCovered;

    @BindView(R.id.et_NoOfRetailersCovered)
    EditText et_NoOfRetailersCovered;

    @BindView(R.id.et_gdwnAreaSqFeet)
    EditText et_gdwnAreaSqFeet;

    @BindView(R.id.et_MonthlyTurnOver)
    EditText et_MonthlyTurnOver;

    @BindView(R.id.et_InvestmentInCompanyProducts)
    EditText et_InvestmentInCompanyProducts;

    @BindView(R.id.et_RetailerCreditLimit)
    EditText et_RetailerCreditLimit;

    @BindView(R.id.tv_WorkingWithDistributorSince)
    TextView tv_WorkingWithDistributorSince;

    @BindView(R.id.rg_IntrestedInDistributorShip)
    RadioGroup rg_IntrestedInDistributorShip;

    @BindView(R.id.tv_NextFollowUp_date)
    TextView tv_NextFollowUp_date;

    @BindView(R.id.et_Dispatch)
    EditText et_Dispatch;

    @BindView(R.id.et_Billing)
    EditText et_Billing;

    @BindView(R.id.et_SalesStaff)
    EditText et_SalesStaff;


    @BindView(R.id.et_GoodsServicingDays)
    EditText et_GoodsServicingDays;


    @BindView(R.id.img_AddCompany)
    ImageView img_AddCompany;


    @BindView(R.id.ll_OtherCompanyWholeSection)
    LinearLayout ll_OtherCompanyWholeSection;


    @BindView(R.id.img_AddBrand)
    ImageView img_AddBrand;


    @BindView(R.id.ll_OtherBrandWholeSection)
    LinearLayout ll_OtherBrandWholeSection;


    String ScoutingDistributorID = "";
    int flgNewScoutingOrOldDistributor = 0;
    TblPotentialDistributor tblPotentialDistributor;

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
    @BindView(R.id.etPinCode)
    EditText et_Pincode;
    @BindView(R.id.et_Village)
    EditText et_Village;
    @BindView(R.id.et_state)
    EditText et_State;

    @BindView(R.id.et_District)
    EditText et_District;


    Calendar calendar;
    int Year, Month, Day;
    boolean bool_NextFollowUpdate = false;

    boolean bool_WokingWithDistributorSince = false;

    DatePickerDialog datePickerDialog;
    RecyclerView rvVechileList, rvListofCompetitors, rvListofBrands;

    ScoutingVechileAdapter scoutingVechileAdapter;
    ScoutingCompetitorAdapter scoutingCompetitorAdapter;
    ScoutingBrandsAdapter scoutingBrandsAdapter;
    int flgGST = -1;

    int flgIntrestedInDistributorShip = -1;

    String cityID = "NA";
    String StateID = "NA";
    String MapAddress = "NA";
    String MapPincode = "NA";
    String MapCity = "NA";
    String MapState = "NA";
    int countSubmitClicked = 0;
    ImageAdapter adapterImage = null;
    LocationRetreivingGlobalDistributorScouting locationRetreivingGlobal;
    @BindView(R.id.location_layout)
    RelativeLayout location_layout;
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

    @BindView(R.id.ll_OtherCompetitors)
    LinearLayout ll_OtherCompetitors;

    @BindView(R.id.et_OtherCompetitors)
    EditText et_OtherCompetitors;

    @BindView(R.id.ll_OtherBrands)
    LinearLayout ll_OtherBrands;

    @BindView(R.id.et_OtherBrands)
    EditText et_OtherBrands;

    View.OnClickListener captrureListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setEnabled(false);
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            cancelCam.setEnabled(false);
            flashImage.setEnabled(false);
            if (cameraPreview != null) {
                cameraPreview.setEnabled(false);
            }

            if (mCamera != null) {
                mCamera.takePicture(null, null, mPicture);
            } else {
                dialog.dismiss();
            }
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    };
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

    private List<TblPotentialDistributorVehicleMasterList> tblPotentialDistributorVehicleMasterLists;

    private List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyMstrList;

    private List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandMstrList;

    private List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsList;

    private List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving;



    private static File getOutputMediaFile() {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File("/sdcard/", CommonInfo.ImagesFolder);

        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMMdd_HHmmss.SSS", Locale.ENGLISH).format(new Date());
        File mediaFile;
        //and make a media file:


        mediaFile = new File(mediaStorageDir.getPath() + File.separator + imei + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static InputFilter getEditTextFilter() {
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c)) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c) {
                Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
                Matcher ms = ps.matcher(String.valueOf(c));
                return ms.matches();
            }
        };
    }

    public static void fnAlertWarningForHighAmountBooking(Context context, String msg, String date, String value) {


        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.AlertForWarning))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(context.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
//                        InterFaceCallProceddAfterWarningConfirm intrfc = (InterFaceCallProceddAfterWarningConfirm) context;
//                        intrfc.fnProceed(val);
                    }
                })
//                .setNegativeButton(context.getResources().getString(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i)
//                    {
//                        dialogInterface.dismiss();
//                        val=false;
//
//                    }
//                })
                .create().show();
        // return val;
    }

    public void onDestroy() {
        super.onDestroy();
        // unregister receiver
        mActivity.unregisterReceiver(this.mBatInfoReceiver);
        if (locationRetreivingGlobal != null) {
            locationRetreivingGlobal.stopAllProcesses();
        }

        //this.unregisterReceiver(this.KillME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_scouting);
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
        tblPotentialDistributor = new TblPotentialDistributor();


        // spokeTypeText = getIntent().getExtras().getString(AppUtils.SPOKETYPE);
        arrImageData = new ArrayList<>();
        imei = AppUtils.getToken(mActivity);
        mDataSource = AppDataSource.getInstance(this);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if (ScoutingDistributorID.equals("0")) {
            ScoutingDistributorID = genScoutingDistributorID();
            flgNewScoutingOrOldDistributor=0;
        } else {
            tblPotentialDistributor = mDataSource.fnGetSelectedPotentialDetails(ScoutingDistributorID);
            flgNewScoutingOrOldDistributor=1;
        }

        String PersonNodeIdAndNodeType = mDataSource.fngetSalesPersonMstrData();
        if (!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId =Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType =Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        initViews();





        TimeUtils.checkTimeDifference(this, TimeUtils.DATE_TIME_FORMAT, false);


        ll_MarketReputationRetailerSection.setVisibility(View.GONE);
        ll_sectionOtherThenRetailerFeedBack.setVisibility(View.VISIBLE);
        if(tblPotentialDistributor.getFlgViewOrEdit()==2)
        {
            location_layout.setVisibility(View.GONE);
        }
        else
            location_layout.setVisibility(View.VISIBLE);



        if (!checkGPSAndNetwork()) {
            try {
                if (AppUtils.hasGPSDevice(this)) {
                    locationCount++;
                    isGpsModifyAsked = true;
                    AppUtils.showGPSSettingsAlert(this);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (isGpsModifyAsked) {
                isGpsModifyAsked = false;
            }
            if(flgNewScoutingOrOldDistributor==0 || tblPotentialDistributor.getFlgViewOrEdit()==1) {
                locationRetreivingGlobal = new LocationRetreivingGlobalDistributorScouting();
                locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(this, true, true, 20, 0);

            }
            locationCount++;
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(ActivityDistributorScouting.this, SocutingDistributorSelection.class);
            startActivity(intent);
            finish();
            // finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void fetchPhotoDetails() {
        if (list_ImgName != null && list_ImgName.size() > 0) {
            adapterImage = new ImageAdapter(ActivityDistributorScouting.this);
            clickedTagPhoto = "Gowdown";

            int picPosition = 0;
            for (int i = 0; i < list_ImgName.size(); i++) {
                String imgName = list_ImgName.get(i);
                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imgName;
                File fImageShow = new File(file_dj_path);
                if (fImageShow.exists()) {
                    Bitmap bmp = decodeSampledBitmapFromFile(fImageShow.getAbsolutePath(), 80, 80);
                    adapterImage.add(i, bmp, imgName + "^" + clickedTagPhoto);

                    hmapCtgry_Imageposition.put(clickedTagPhoto, picPosition);
                    picPosition++;
                }
            }
            expandableHeightGridView.setAdapter(adapterImage);

            hmapImageAdapter.put(clickedTagPhoto, adapterImage);

            hmapCtgryPhotoSection.put(clickedTagPhoto, list_ImgName);

        }
    }

    private void initViews() {


        rvVechileList = findViewById(R.id.rvVechileList);
        rvListofCompetitors = findViewById(R.id.rvListofCompetitors);
        rvListofBrands = findViewById(R.id.rvListofBrands);

    /*    et_ContactPerson.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!et_ContactPerson.getText().toString().isEmpty()) {
                        if (!AppUtils.isNumberValid(et_ContactPerson.getText().toString().trim())) {
                            showAlertSingleButtonInfoForValidation("Please enter 10 digits contact number and number cannot be start with 0, 1, 2, 3 or 4.", et_ContactPerson);
                            return;
                        }
                    }
                }
            }
        });*/

        et_MobileNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!et_MobileNo.getText().toString().isEmpty()) {
                        if (!AppUtils.isNumberValid(et_MobileNo.getText().toString().trim())) {
                            showAlertSingleButtonInfoForValidation("Please enter 10 digits contact number and number cannot be start with 0, 1, 2, 3 or 4.", et_MobileNo);
                            return;
                        }
                    }
                }
            }
        });

        gstno_txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!gstno_txt.getText().toString().isEmpty()) {
                        if (gstno_txt.getText().toString().length() != 15) {
                            showAlertSingleButtonInfoForValidation("Please enter proper GST Number", gstno_txt);
                            // gstno_txt.requestFocus();
                            return;
                        }
                        String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                        Pattern p = Pattern.compile("[0-9]{2}");
                        Matcher m = p.matcher(firstTwoStr);
                        if (!m.find() || !m.group().equals(firstTwoStr)) {
                            //gstno_txt.requestFocus();
                            showAlertSingleButtonInfoForValidation("Please enter proper GST Number", gstno_txt);
                        }
                    }
                }
            }
        });

        bt_Next.setVisibility(View.VISIBLE);
        bt_Previous.setVisibility(View.GONE);
        bt_Submit.setVisibility(View.GONE);
        bt_Save.setVisibility(View.GONE);

        tv_WorkingWithDistributorSince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_WokingWithDistributorSince = true;
                /* createDialogWithoutDateField().getShowsDialog();*/

                bool_NextFollowUpdate = true;
                calendar = Calendar.getInstance();
                Year = calendar.get(Calendar.YEAR);
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = DatePickerDialog.newInstance(ActivityDistributorScouting.this, Year, Month, Day + 1);

                MonthYearPickerDialog newFragment = new MonthYearPickerDialog();

                newFragment.show(getSupportFragmentManager(), "DatePicker");

            }
        });


        tv_NextFollowUp_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // createDialogWithoutDateField().show();
                bool_NextFollowUpdate = true;
                calendar = Calendar.getInstance();
                Year = calendar.get(Calendar.YEAR);
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = DatePickerDialog.newInstance(ActivityDistributorScouting.this, Year, Month, Day + 1);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

             /*  int  date = datePickerDialog.getResources()
                        .getIdentifier("android:id/day", null, null);
                if(date != 0){
                    View dayPicker = findViewById(date);
                    if(dayPicker != null){
                        dayPicker.setVisibility(View.GONE);
                    }
                }*/


                Calendar calendarForSetDate = Calendar.getInstance();
                calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

                calendar.setTimeInMillis(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
                //YOU can set min or max date using this code
                // datePickerDialog.setMaxDate(Calendar.getInstance());
                // datePickerDialog.setMinDate(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setMinDate(calendar);

                // datePickerDialog.setMinDate(calendarForSetDate);
                datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

                datePickerDialog.setTitle(getResources().getString(R.string.txtSelectScoutingNextFollowUoDate));
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

            }
        });
        //   scroll_view.requestDisallowInterceptTouchEvent(false);
       /* scroll_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getCurrentFocus() instanceof EditText) {
                    EditText editText = (EditText) getCurrentFocus();
                    if (editText.hasFocus())
                        editText.clearFocus();
                }
                return false;
            }
        });*/


        manager = getFragmentManager();
        mapFrag = (MapFragment) manager.findFragmentById(R.id.map);

        et_Address.setSingleLine(false);
        adapterImage = new ImageAdapter(this);

        expandableHeightGridView.setAdapter(adapterImage);
        expandableHeightGridView.setExpanded(true);
        hmapImageAdapter.put(clickedTagPhoto, adapterImage);

        ll_refresh.setVisibility(View.GONE);
        rg_yes_no.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i != -1) {

                    RadioButton radioButtonVal = radioGroup.findViewById(i);
                    if (radioButtonVal.getId() == R.id.rb_yes) {
                        ll_refresh.setVisibility(View.GONE);
                        isLocationConfirm = true;
                        isLocationSelected = true;
                        flgLocationConfirm = 1;

                        int flgfShowLocationWarning = 0;
                        if (fnLati.equals("NA") || fnLati.equals("NA")) {
                            flgfShowLocationWarning = 1;
                        }
                        if (flgfShowLocationWarning == 0) {
                            if (Double.parseDouble(fnLati) <= 0.0 && Double.parseDouble(fnLongi) <= 0.0) {
                                flgfShowLocationWarning = 1;
                            }
                        }

                        if (flgfShowLocationWarning == 1) {
                            fnWarningMsgInCasLatLong0();
                        }


                    } else if (radioButtonVal.getId() == R.id.rb_no) {
                        ll_refresh.setVisibility(View.VISIBLE);
                        txt_rfrshCmnt.setText(R.string.LocationDistributor);
                        isLocationConfirm = false;
                        isLocationSelected = true;
                        flgLocationConfirm = 0;
                    }
                }

            }
        });

        rg_gst.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_not_req:
                        flgGST = 0;
                        ll_gst_num.setVisibility(View.GONE);
                        break;

                    case R.id.rb_req:
                        flgGST = 1;
                        ll_gst_num.setVisibility(View.VISIBLE);
                        break;

                    case R.id.rb_pen:
                        flgGST = 2;
                        ll_gst_num.setVisibility(View.GONE);
                        break;

                }


            }
        });


        rg_IntrestedInDistributorShip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbIntrestedIn_No:
                        flgIntrestedInDistributorShip = 0;

                        break;

                    case R.id.rbIntrestedIn_Yes:
                        flgIntrestedInDistributorShip = 1;

                        break;

                    case R.id.rbIntrestedIn_MayBe:
                        flgIntrestedInDistributorShip = 2;

                        break;

                }


            }
        });


        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isGPSok = false;
                boolean isNWok = false;
                isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSok) {
                    isGPSok = false;
                }
                if (!isNWok) {
                    isNWok = false;
                }
                rg_yes_no.clearCheck();
                ll_refresh.setVisibility(View.GONE);
                refreshCount++;
                if (refreshCount == 1) {
                    txt_rfrshCmnt.setText(getString(R.string.second_msg_for_map));
                } else if (refreshCount == 2) {
                    txt_rfrshCmnt.setText(getString(R.string.third_msg_for_map));
                    btn_refresh.setVisibility(View.GONE);
                }

                locationRetreivingGlobal = new LocationRetreivingGlobalDistributorScouting();
                locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(ActivityDistributorScouting.this, true, true, 20, 0);

            }
        });
        fetchPhotoDetails();
        clickImage();


    }

    void clickImage() {
        /*String selectedTextID="00";

        selectedTextID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
        */
        if (list_ImgName == null || list_ImgName.size() == 0) {
            adapterImage = new ImageAdapter(ActivityDistributorScouting.this);
            expandableHeightGridView.setAdapter(adapterImage);
            clickedTagPhoto = "Gowdown";
            hmapImageAdapter.put(clickedTagPhoto, adapterImage);
        }
    }

    private void validateAndSaveData() {
        tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving=new ArrayList<TblPotentialDistributorRetailersFeedBackDetails>();
        if(ll_MarketReputationCheckWholeSection!=null && ll_MarketReputationCheckWholeSection.getChildCount()>0) {

            for (int i = 0; i < 3; i++) {
                LinearLayout view = (LinearLayout) ll_MarketReputationCheckWholeSection.getChildAt(i);


                String RetailerName="";
                String RetailerAddress="";
                String RetailerComment="";
                String RetailerContactNumber="";

                EditText txt_RetailerName = view.findViewWithTag("etRetailerName_"+i);
                EditText txt_RetailerAddress = view.findViewWithTag("etRetailerAddress_"+i);
                EditText txt_RetailerComment = view.findViewWithTag("etRetailerComment_"+i);
                EditText et_RetailerContactNumber = view.findViewWithTag("etRetailerContactNumber_"+i);
                RadioGroup rg_RetailerFeedBack = view.findViewWithTag("rgRetailerFeedBack_"+i);
                if (txt_RetailerName != null && !TextUtils.isEmpty(txt_RetailerName.getText().toString().trim())) {
                    RetailerName=txt_RetailerName.getText().toString().trim().replace("&","-");
                }
                if (txt_RetailerAddress != null && !TextUtils.isEmpty(txt_RetailerAddress.getText().toString().trim())) {
                    RetailerAddress=txt_RetailerAddress.getText().toString().trim().replace("&","-");
                }
                if (txt_RetailerComment != null && !TextUtils.isEmpty(txt_RetailerComment.getText().toString().trim())) {
                    RetailerComment=txt_RetailerComment.getText().toString().trim().replace("&","-");
                }
                if (et_RetailerContactNumber!=null && !TextUtils.isEmpty(et_RetailerContactNumber.getText().toString().trim())) {
                    RetailerContactNumber=et_RetailerContactNumber.getText().toString().trim();
                }
                flgRetailerFeedBackStatus = 0;
                if(hashMapRetailerSectionNoAndFeedBackStatus!=null && hashMapRetailerSectionNoAndFeedBackStatus.containsKey(i))
                {
                    flgRetailerFeedBackStatus=hashMapRetailerSectionNoAndFeedBackStatus.get(i);
                }

                if(TextUtils.isEmpty(RetailerName) && TextUtils.isEmpty(RetailerAddress) && TextUtils.isEmpty(RetailerContactNumber))//  && (TextUtils.isEmpty(RetailerComment)  && flgRetailerFeedBackStatus==0)
                {

                }
                else
                {
                    if(!TextUtils.isEmpty(RetailerName) && !TextUtils.isEmpty(RetailerAddress) && !TextUtils.isEmpty(RetailerContactNumber))//  && (!TextUtils.isEmpty(RetailerComment)  && flgRetailerFeedBackStatus!=0)
                    {

                        if (!et_RetailerContactNumber.getText().toString().trim().equals("")) {
                            if (!isNumberValid(et_RetailerContactNumber.getText().toString().trim())) {
                                tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving.clear();
                                showAlertSingleButtonInfo("Please enter correct contact number");
                                return;
                            }
                            TblPotentialDistributorRetailersFeedBackDetails tblPotentialDistributorRetailersFeedBackDetails=new TblPotentialDistributorRetailersFeedBackDetails();
                            tblPotentialDistributorRetailersFeedBackDetails.setRetailerSectionID(""+(i+1));
                            tblPotentialDistributorRetailersFeedBackDetails.setDBRName(tblPotentialDistributor.getDescr());
                            tblPotentialDistributorRetailersFeedBackDetails.setNodeType(ScoutingDistributorNodeType);
                            if(flgNewScoutingOrOldDistributor==0)
                                tblPotentialDistributorRetailersFeedBackDetails.setNodeID("0");
                            else
                                tblPotentialDistributorRetailersFeedBackDetails.setNodeID(ScoutingDistributorID);
                            tblPotentialDistributorRetailersFeedBackDetails.setPDAcode(AppUtils.getToken(ActivityDistributorScouting.this));
                            tblPotentialDistributorRetailersFeedBackDetails.setEntryDate(TimeUtils.getNetworkDate(ActivityDistributorScouting.this,"dd-MMM-yyyy"));
                            tblPotentialDistributorRetailersFeedBackDetails.setRetailerName(RetailerName);
                            tblPotentialDistributorRetailersFeedBackDetails.setAddress(RetailerAddress);
                            tblPotentialDistributorRetailersFeedBackDetails.setComment(RetailerComment);
                            tblPotentialDistributorRetailersFeedBackDetails.setContactNumber(RetailerContactNumber);
                            tblPotentialDistributorRetailersFeedBackDetails.setFeedback(flgRetailerFeedBackStatus);
                            tblPotentialDistributorRetailersFeedBackDetails.setFlgFinalSubmit(flgFinalSubmit);
                            // flgFinalSubmit=0;

                            if(flgNewScoutingOrOldDistributor==0)
                                tblPotentialDistributorRetailersFeedBackDetails.setNewDBRCode(ScoutingDistributorID);
                            else
                                tblPotentialDistributorRetailersFeedBackDetails.setNewDBRCode("0");

                            tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving.add(tblPotentialDistributorRetailersFeedBackDetails);
                        }
                        else
                        {
                            tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving.clear();
                            showAlertSingleButtonInfo("Please enter contact number");
                            return;
                        }

                    }
                    else
                    {
                        tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving.clear();
                        showAlertSingleButtonInfo("Partial filled section is not allowed, Please complete that.");
                        return;
                    }
                }



            }
        }
        mProgressDialogReport=null;
        mProgressDialogReport = new ProgressDialog(mContext);
        mProgressDialogReport.setTitle("Please wait submitting distributor retailer feedback data.");//context.getResources().getString(R.string.Loading));
        mProgressDialogReport.setMessage(mContext.getResources().getString(R.string.SubmittingDataMsg));
        mProgressDialogReport.setIndeterminate(true);
        mProgressDialogReport.setCancelable(false);
        mProgressDialogReport.show();


        InterfaceServerSuccessRetailerFeedBackEntry interfaceRetrofitRetailerFeedBack = (InterfaceServerSuccessRetailerFeedBackEntry) mContext;
        CommonFunction.SavePotentialDistributorRetailerFeedBackData reportDownloadAsyncTask = new CommonFunction.SavePotentialDistributorRetailerFeedBackData(ActivityDistributorScouting.this, mProgressDialogReport, interfaceRetrofitRetailerFeedBack,0, tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving);
        AppUtils.executeAsyncTask(reportDownloadAsyncTask);
      /*  if(tblPotentialDistributorRetailersFeedBackDetailsList!=null && tblPotentialDistributorRetailersFeedBackDetailsList.size()>0)
            tblPotentialDistributor.setTblPotentialDistributorRetailersFeedBackDetailsList(tblPotentialDistributorRetailersFeedBackDetailsList);
        else
        {
            tblPotentialDistributorRetailersFeedBackDetailsList=new ArrayList<TblPotentialDistributorRetailersFeedBackDetails>();
            tblPotentialDistributor.setTblPotentialDistributorRetailersFeedBackDetailsList(tblPotentialDistributorRetailersFeedBackDetailsList);
        }*/
        //  String sddasdad="asdad";
       /* mProgressDialogReport = new ProgressDialog(mContext);
        mProgressDialogReport.setTitle("Please wait submitting data.");//context.getResources().getString(R.string.Loading));
        mProgressDialogReport.setMessage(mContext.getResources().getString(R.string.SubmittingDataMsg));
        mProgressDialogReport.setIndeterminate(true);
        mProgressDialogReport.setCancelable(false);
        mProgressDialogReport.show();

        InterfaceServerSuccessEntry interfaceRetrofit = (InterfaceServerSuccessEntry) mContext;
        CommonFunction.SavePotentialDistributorData reportDownloadAsyncTask = new CommonFunction.SavePotentialDistributorData(ActivityDistributorScouting.this, mProgressDialogReport, interfaceRetrofit,0,tblPotentialDistributor);
        AppUtils.executeAsyncTask(reportDownloadAsyncTask);*/
    }

    private void registerSpoke() {

    }

    private void validateLocationAndProceed() {
        validationString = new StringBuilder();
        int i = 0;
        validationString = validationString.append("");


        if (!et_Address.getText().toString().trim().equals("")) {
            if (et_Address.getText().toString().trim().length() > 9) {
                addressUserInput = et_Address.getText().toString().trim();
                tblPotentialDistributor.setAddress(addressUserInput.replace("&","-"));
            } else {
                //showAlertSingleButtonInfo("Address can not be less then 10 characters");
                validationString.append("\n" + (++i) + ". Address can not be less then 10 characters");
                //return;
            }
        } else {
            validationString.append("\n" + (++i) + ". Fill Address.");
           /* showAlertSingleButtonInfo("Please fill Address.");
            return;*/

        }
        if (!et_Village.getText().toString().trim().equals("")) {
            if (et_Village.getText().toString().trim().length() > 2) {
                cityUserInput = et_Village.getText().toString().trim();
                tblPotentialDistributor.setCity(cityUserInput.replace("&","-"));
            } else {
                validationString.append("\n" + (++i) + ". City/Village can not be less then 3 characters.");
/*
                showAlertSingleButtonInfo("City/Village can not be less then 3 characters");

                return;*/
            }
        } else {
            validationString.append("\n" + (++i) + ". Fill City/Village.");
           /* showAlertSingleButtonInfo("Please fill City/Village.");
            return;*/

        }

        if (!et_District.getText().toString().trim().equals("")) {
            if (et_District.getText().toString().trim().length() > 2) {
                districtUserInput = et_District.getText().toString().trim();
                tblPotentialDistributor.setDistrict(districtUserInput.replace("&","-"));
            } else {
                validationString.append("\n" + (++i) + ". District can not be less then 3 characters.");
/*
                showAlertSingleButtonInfo("City/Village can not be less then 3 characters");

                return;*/
            }
        } else {
            validationString.append("\n" + (++i) + ". Fill District.");
           /* showAlertSingleButtonInfo("Please fill City/Village.");
            return;*/

        }

        if (!et_State.getText().toString().trim().equals("")) {
            if (et_State.getText().toString().trim().length() > 2) {
                stateCodeUserInput = et_State.getText().toString().trim();
                tblPotentialDistributor.setState(stateCodeUserInput.replace("&","-"));
            } else {
                validationString.append("\n" + (++i) + ". State can not be less then 3 characters.");

/*
                showAlertSingleButtonInfo("State can not be less then 3 characters.");
                return;
*/
            }
        } else {
            validationString.append("\n" + (++i) + ". Fill State.");
           /* showAlertSingleButtonInfo("Please fill State.");
            return;*/

        }


        if (!et_Pincode.getText().toString().trim().equals("")) {
            if (et_Pincode.getText().toString().trim().length() == 6) {
                pinCodeUserInput = et_Pincode.getText().toString().trim();
                tblPotentialDistributor.setPincode(pinCodeUserInput.replace("&","-"));
            } else {
                validationString.append("\n" + (++i) + ". Enter the 6 digits pincode");
                /*showAlertSingleButtonInfo("Please enter the 6 digits pincode");
                return;*/
            }
        } else {
            validationString.append("\n" + (++i) + ". Fill Pin Code.");
            /*showAlertSingleButtonInfo("Please fill Pin Code.");
            return;
*/
        }


        if (isLocationConfirm && validationString.toString().equals("")) {
            flgLocationConfirm = 1;
            tblPotentialDistributor.setFlgAppointed(0);
            tblPotentialDistributor.setFlgLocationConfirm(flgLocationConfirm);
            contactNumberUserInput =  et_ContactPerson.getText().toString().trim();//.getOwnerContactNumber();



            if (contactNumberUserInput != null && contactNumberUserInput.length() > 0 && mDataSource.fnGetExistingOwnerNumberFromScoutingDistributor(contactNumberUserInput, ScoutingDistributorID) == 1) {
                String outletOtherOutletHavingSameMobileNumber = mDataSource.fnGetScoutingDistributorNamewithMobileNo(contactNumberUserInput);
                showStoreAlreadyExistsWithSameMobileNumber(outletOtherOutletHavingSameMobileNumber, contactNumberUserInput);

            } else {
                     /*   ll_MarketReputationRetailerSection.setVisibility(View.VISIBLE);


                        ll_sectionOtherThenRetailerFeedBack.setVisibility(View.GONE);
                        location_layout.setVisibility(View.GONE);
                        bt_Next.setVisibility(View.GONE);
                        bt_Previous.setVisibility(View.VISIBLE);
                        bt_Submit.setVisibility(View.VISIBLE);
                        bt_Save.setVisibility(View.VISIBLE);*/



                mProgressDialogReport = new ProgressDialog(mContext);
                mProgressDialogReport.setTitle("Please wait submitting distributor basic data.");//context.getResources().getString(R.string.Loading));
                mProgressDialogReport.setMessage(mContext.getResources().getString(R.string.SubmittingDataMsg));
                mProgressDialogReport.setIndeterminate(true);
                mProgressDialogReport.setCancelable(false);
                mProgressDialogReport.show();


                InterfaceServerSuccessEntry interfaceRetrofit = (InterfaceServerSuccessEntry) mContext;
                CommonFunction.SavePotentialDistributorData reportDownloadAsyncTask = new CommonFunction.SavePotentialDistributorData(ActivityDistributorScouting.this, mProgressDialogReport, interfaceRetrofit,0,tblPotentialDistributor);
                AppUtils.executeAsyncTask(reportDownloadAsyncTask);



/*

                        ll_sectionOtherThenRetailerFeedBack.setVisibility(View.GONE);
                        location_layout.setVisibility(View.GONE);
                        bt_Next.setVisibility(View.GONE);
                        bt_Previous.setVisibility(View.VISIBLE);
                        bt_Submit.setVisibility(View.VISIBLE);*/
                // saveDataToDatabase();
            }



        } else {
            String str = "Following fields are mandatory. Please fill all these fields.\n";
            if (!isLocationConfirm) {
                flgLocationConfirm = 0;
                validationString.append("\n" + (++i) + ". Confirm your location");
            }

            str = str + validationString.toString();
            showAlertSingleButtonInfo(str);

            return;
            /*
            showAlertSingleButtonInfo("Please confirm your location");
            return;*/
        }

       /* tblLastPDAActivityDetails.setLast_dateTime(formattedDate);
        tblLastPDAActivityDetails.setAct_type("1");
        tblLastPDAActivityDetails.setDetails(tblSpokeProfile.getSpokename()+"Registered at");*/

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        formattedDate = dateFormat.format(date);



    }


    @OnClick(R.id.bt_Next)
    public void NextSlide(View view) {
        flgFinalSubmit=1;
        tblPotentialDistributor.setPDACode(AppUtils.getToken(ActivityDistributorScouting.this));
        if(flgNewScoutingOrOldDistributor==0)
        {
            tblPotentialDistributor.setNodeID("0");
            tblPotentialDistributor.setNewDBRCode(ScoutingDistributorID);
            tblPotentialDistributor.setNodeType(ScoutingDistributorNodeType);
            tblPotentialDistributor.setCreatedDate(TimeUtils.getNetworkDate(ActivityDistributorScouting.this,"dd-MMM-yyyy"));
            tblPotentialDistributor.setLatCode(Double.parseDouble(fnLati));
            tblPotentialDistributor.setLongCode(Double.parseDouble(fnLongi));

            tblPotentialDistributor.setEntryPersonNodeID(PersonNodeId);
            tblPotentialDistributor.setEntryPersonNodeType(PersonNodeType);
            tblPotentialDistributor.setIsOldDistributorReplaced(-1);
            tblPotentialDistributor.setOldDistriutorID(0);
            tblPotentialDistributor.setFlgDistributorSS(-1);
            tblPotentialDistributor.setFlgTownDistributorSubD(-1);
            tblPotentialDistributor.setBankAccountNumber("");
            tblPotentialDistributor.setIFSCCode("");
            tblPotentialDistributor.setBankAddress("");
            tblPotentialDistributor.setExpectedBusiness("");
            tblPotentialDistributor.setReqGodownSpace("");
            tblPotentialDistributor.setAgreedGodownSpace("");
            tblPotentialDistributor.setAgreedInvestment("");
            tblPotentialDistributor.setIdealROI("");
            tblPotentialDistributor.setExpectedROI("");
            tblPotentialDistributor.setFlgProprietorPartner(-1);
            tblPotentialDistributor.setIsGSTDetailsSubmitted(-1);
            tblPotentialDistributor.setGSTNumber("");
            tblPotentialDistributor.setIsPANOfFirmSubmitted(-1);
            tblPotentialDistributor.setPANNoOfFirm("");
            tblPotentialDistributor.setIsCheckGiven(-1);
            tblPotentialDistributor.setChequeNumber("");
            tblPotentialDistributor.setIsProprietorPanSubmited(-1);
            tblPotentialDistributor.setProprietorPanNumber("");
            tblPotentialDistributor.setIsPartner1PANSubmitted(-1);
            tblPotentialDistributor.setPanNumber_Partner1("");
            tblPotentialDistributor.setIsPartner2PANSubmitted(-1);
            tblPotentialDistributor.setPanNumber_Partner2("");
            tblPotentialDistributor.setIsPartnerDeedSubmitted(-1);
            tblPotentialDistributor.setPartnerDeedNumber("");


            if (et_Dealer_Firm_Name != null && TextUtils.isEmpty(et_Dealer_Firm_Name.getText().toString().trim())) {

                showAlertSingleButtonInfoForValidation("Dealer Firm Name Required.", et_Dealer_Firm_Name);
                return;

            }
            tblPotentialDistributor.setDescr(et_Dealer_Firm_Name.getText().toString().trim().replace("&", "-"));

            if (et_ContactPerson != null && TextUtils.isEmpty(et_ContactPerson.getText().toString().trim())) {

                showAlertSingleButtonInfoForValidation("Contact Person Name Required.", et_ContactPerson);
                return;
            }
      /*  if (et_ContactPerson != null && !et_ContactPerson.getText().toString().trim().equals("NA")) {

            boolean bl = false;
            if (et_ContactPerson.getText().toString().trim().startsWith("6")) {
                bl = true;
            } else if (et_ContactPerson.getText().toString().trim().startsWith("7")) {
                bl = true;
            } else if (et_ContactPerson.getText().toString().trim().startsWith("8")) {
                bl = true;
            } else if (et_ContactPerson.getText().toString().trim().startsWith("9")) {
                bl = true;
            }
            //(!fnlMobileNumber.startsWith("6") || !fnlMobileNumber.startsWith("7") || !fnlMobileNumber.startsWith("8") || !fnlMobileNumber.startsWith("9")  )
            if (bl == false) {

                showAlertSingleButtonInfoForValidation("Invalid Contact Person Number.\nPlease correct that.", et_ContactPerson);
                return;

            }


        }*/
            tblPotentialDistributor.setContactPersonName(et_ContactPerson.getText().toString().trim());


            if (et_MobileNo != null && TextUtils.isEmpty(et_MobileNo.getText().toString().trim())) {

                showAlertSingleButtonInfoForValidation("Mobile Number Required.", et_MobileNo);
                return;
            }
            if (et_MobileNo != null && et_MobileNo.getText().toString().length()!=10) {

                showAlertSingleButtonInfoForValidation("Mobile Number not valid.", et_MobileNo);
                return;
            }
            if (et_MobileNo != null && !et_MobileNo.getText().toString().trim().equals("NA")) {

                boolean bl = false;
                if (et_MobileNo.getText().toString().trim().startsWith("6")) {
                    bl = true;
                } else if (et_MobileNo.getText().toString().trim().startsWith("7")) {
                    bl = true;
                } else if (et_MobileNo.getText().toString().trim().startsWith("8")) {
                    bl = true;
                } else if (et_MobileNo.getText().toString().trim().startsWith("9")) {
                    bl = true;
                }
                //(!fnlMobileNumber.startsWith("6") || !fnlMobileNumber.startsWith("7") || !fnlMobileNumber.startsWith("8") || !fnlMobileNumber.startsWith("9")  )
                if (bl == false) {

                    showAlertSingleButtonInfoForValidation("Invalid Mobile Number.\nPlease correct that.", et_MobileNo);
                    return;

                }


            }
            tblPotentialDistributor.setContactPersonMobileNumber(et_MobileNo.getText().toString().trim());

            tblPotentialDistributor.setTelephoneNo(et_teleNo.getText().toString().trim());


            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String mail = et_emailID.getText().toString().trim();


            if (!et_emailID.getText().toString().trim().equals("") && !mail.matches(emailPattern)) {

                showAlertSingleButtonInfoForValidation(getResources().getString(R.string.txtValidateEmailID), et_emailID);


                return;
            }

            tblPotentialDistributor.setContactPersonEMailID(et_emailID.getText().toString().trim());

            if (!gstno_txt.getText().toString().isEmpty()) {
                if (gstno_txt.getText().toString().length() != 15) {
                    showAlertSingleButtonInfoForValidation("Please enter proper GST Number", gstno_txt);

                    return;
                }
                String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                Pattern p = Pattern.compile("[0-9]{2}");
                Matcher m = p.matcher(firstTwoStr);
                if (!m.find() || !m.group().equals(firstTwoStr)) {

                    showAlertSingleButtonInfoForValidation("Please enter proper GST Number", gstno_txt);
                    return;
                }
            }
            tblPotentialDistributor.setFlgGSTTaken(0);
            tblPotentialDistributor.setGSTNumber(gstno_txt.getText().toString().trim());

            String OtherCompetitors="";
            if(et_OtherCompetitors!=null && !TextUtils.isEmpty(et_OtherCompetitors.getText().toString().trim()))
            {
                OtherCompetitors=et_OtherCompetitors.getText().toString().trim().replaceAll("&","-");
            }

            List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDetailsList= stream(tblPotentialDistributorCompetitorCompanyMstrList).where(p -> p.getFlgCheck()==1).toList();
            if(tblPotentialDistributorCompetitorCompanyDetailsList!=null && tblPotentialDistributorCompetitorCompanyDetailsList.size()>0) {
                for (TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyDetailsListForOther : tblPotentialDistributorCompetitorCompanyDetailsList) {
                  /*  if(tblPotentialDistributorCompetitorCompanyDetailsListForOther.getFlgOther()==1)
                    {
                        tblPotentialDistributorCompetitorCompanyDetailsListForOther.setOtherCompany(OtherCompetitors);
                        break;
                    }*/

                    if(tblPotentialDistributorCompetitorCompanyDetailsListForOther.getSalesQty() == null || TextUtils.isEmpty(tblPotentialDistributorCompetitorCompanyDetailsListForOther.getSalesQty().toString().trim()) )
                    {
                        showAlertSingleButtonInfo("Please fill sales for :"+tblPotentialDistributorCompetitorCompanyDetailsListForOther.getCompetitorCompany());

                        return;

                    }
                }
            }

            //Saving Other Comany Starts Here For Text Boxes
            if(ll_OtherCompanyWholeSection!=null && ll_OtherCompanyWholeSection.getChildCount()>0) {

                for (int i = 0; i < (totalCompanyNewCount); i++) {
                    LinearLayout viewOtherCompanyRow = (LinearLayout) ll_OtherCompanyWholeSection.getChildAt(i);

                    String CompName="";
                    String CompSales="";

                    EditText et_NewCompanyName = viewOtherCompanyRow.findViewWithTag("etNewCompanyName_"+i);
                    et_NewCompanyName.setTag("etNewCompanyName_" +  i);

                    EditText et_Sales = viewOtherCompanyRow.findViewWithTag("etSales_"+i);
                    et_Sales.setTag("etSales_" + i);


                    if (et_NewCompanyName != null && !TextUtils.isEmpty(et_NewCompanyName.getText().toString().trim())) {
                        CompName=et_NewCompanyName.getText().toString().trim().replace("&","-");
                    }
                    if (et_Sales != null && !TextUtils.isEmpty(et_Sales.getText().toString().trim())) {
                        CompSales=et_Sales.getText().toString().trim().replace("&","-");
                    }

                    if(TextUtils.isEmpty(CompName) && TextUtils.isEmpty(CompSales))
                    {

                    }
                    else
                    {
                        if(!TextUtils.isEmpty(CompName) || !TextUtils.isEmpty(CompSales))
                        {

                            if (et_NewCompanyName.getText().toString().trim().equals("")) {

                                showAlertSingleButtonInfo("Please enter Company Name against sales :"+CompSales);
                                return;
                            }
                            else if (et_Sales.getText().toString().trim().equals("")) {

                                showAlertSingleButtonInfo("Please enter Sales against Company name :"+CompName);
                                return;
                            }
                            else
                            {
                                TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyNewRecordMstr=new TblPotentialDistributorCompetitorCompanyMstr();
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setNodeID(ScoutingDistributorID);

                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setNodeType(ScoutingDistributorNodeType);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setCompetitorCompanyID(0);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setCompetitorCompany(CompName);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setOtherCompany(CompName);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setFlgOther(0);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setOtherCompanyCode(i+1);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setSalesQty(CompSales);
                                tblPotentialDistributorCompetitorCompanyDetailsList.add(tblPotentialDistributorCompetitorCompanyNewRecordMstr);
                            }

                        }

                    }



                }
            }

            //Saving Other Company Ends Here For Text Boxes


            tblPotentialDistributor.setTblPotentialDistributorCompetitorCompanyMstrList(tblPotentialDistributorCompetitorCompanyDetailsList);




            tblPotentialDistributor.setAreaCovered(et_AreaCovered.getText().toString().trim());
            tblPotentialDistributor.setNoOfRetailersCovered(et_NoOfRetailersCovered.getText().toString().trim());
            tblPotentialDistributor.setGodownAreaSqFt(et_gdwnAreaSqFeet.getText().toString().trim());
            tblPotentialDistributor.setMonthlyTurnOver(et_MonthlyTurnOver.getText().toString().trim());

            //Vechile ka baaki hai

            List<TblPotentialDistributorVehicleMasterList> tblPotentialDistributorVehicleMasterSelectedOne=stream(tblPotentialDistributorVehicleMasterLists).where(p->p.getFlgEnable()==1).toList();
            if(tblPotentialDistributorVehicleMasterSelectedOne!=null && tblPotentialDistributorVehicleMasterSelectedOne.size()>0 && tblPotentialDistributorVehicleMasterSelectedOne.get(0).getVehicleTypeID()!=0)
            {
                tblPotentialDistributor.setVehicleTypeID(""+tblPotentialDistributorVehicleMasterSelectedOne.get(0).getVehicleTypeID());
                if (et_VecCount != null && TextUtils.isEmpty(et_VecCount.getText().toString().trim())) {
                    tblPotentialDistributor.setNoOfVehicles(0);

                }
                else
                    tblPotentialDistributor.setNoOfVehicles(Integer.parseInt(et_VecCount.getText().toString().trim()));
            }
            else
            {
                tblPotentialDistributor.setVehicleTypeID("0");
                tblPotentialDistributor.setNoOfVehicles(0);
            }


            String OtherBrands="";
            if(et_OtherBrands!=null && !TextUtils.isEmpty(et_OtherBrands.getText().toString().trim()))
            {
                OtherBrands=et_OtherBrands.getText().toString().trim().replaceAll("&","-");
            }


            List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandList= stream(tblPotentialDistributorCompetitorBrandMstrList).where(p -> p.getFlgCheck()==1).toList();


            /* if(tblPotentialDistributorCompetitorBrandList!=null && tblPotentialDistributorCompetitorBrandList.size()>0) {
                for (TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandMstrForOther : tblPotentialDistributorCompetitorBrandList) {
                   if(tblPotentialDistributorCompetitorBrandMstrForOther.getFlgOther()==1)
                    {
                        tblPotentialDistributorCompetitorBrandMstrForOther.setOtherBrand(OtherBrands);
                        break;
                    }
                     if(tblPotentialDistributorCompetitorBrandMstrForOther.getSalesQty() != null && !TextUtils.isEmpty(tblPotentialDistributorCompetitorBrandMstrForOther.getSalesQty().toString().trim()) && tblPotentialDistributorCompetitorBrandMstrForOther.getSalesQty().equals("0"))
                    {
                        showAlertSingleButtonInfo("Please fill sales for :"+tblPotentialDistributorCompetitorBrandMstrForOther.getCompetitorBrand());

                        return;

                    }
                }
            }*/



            //Saving Other Brands Starts Here For Text Boxes
            if(ll_OtherBrandWholeSection!=null && ll_OtherBrandWholeSection.getChildCount()>0) {

                for (int i = 0; i < (totalBrandNewCount); i++) {
                    LinearLayout viewOtherBrandRow = (LinearLayout) ll_OtherBrandWholeSection.getChildAt(i);

                    String BrandName="";
                    String BrandSales="0";

                    EditText et_NewBrandName = viewOtherBrandRow.findViewWithTag("etNewBrandName_"+i);
                    et_NewBrandName.setTag("etNewBrandName_" +  i);

                    EditText et_BrandSales = viewOtherBrandRow.findViewWithTag("etBrandSales_"+i);
                    et_BrandSales.setTag("etBrandSales_" + i);


                    if (et_NewBrandName != null && !TextUtils.isEmpty(et_NewBrandName.getText().toString().trim())) {
                        BrandName=et_NewBrandName.getText().toString().trim().replace("&","-");
                    }
                   /* if (et_BrandSales != null && !TextUtils.isEmpty(et_BrandSales.getText().toString().trim())) {
                        BrandSales=et_BrandSales.getText().toString().trim().replace("&","-");
                    }*/

                    if(TextUtils.isEmpty(BrandName) && TextUtils.isEmpty(BrandSales))
                    {

                    }
                    else
                    {
                        if(!TextUtils.isEmpty(BrandName) || !TextUtils.isEmpty(BrandSales))
                        {

                           /*  if (et_NewBrandName.getText().toString().trim().equals("")) {

                                showAlertSingleButtonInfo("Please enter Brand Name against sales :"+BrandSales);
                                return;
                            }
                           else if (et_BrandSales.getText().toString().trim().equals("")) {

                                showAlertSingleButtonInfo("Please enter Sales against Brand name :"+BrandName);
                                return;
                            }*/
                           /* else
                            {*/


                            TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandMstrNewRecordMstr=new TblPotentialDistributorCompetitorBrandMstr();
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setNodeID(ScoutingDistributorID);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setNodeType(ScoutingDistributorNodeType);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setCompetitorBrandID(0);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setCompetitorBrand(BrandName);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setOtherBrand(BrandName);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setFlgOther(0);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setOtherBrandCode(i+1);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setSalesQty(BrandSales);
                            tblPotentialDistributorCompetitorBrandList.add(tblPotentialDistributorCompetitorBrandMstrNewRecordMstr);
                            //}

                        }

                    }



                }
            }

            //Saving Other Brands Ends Here For Text Boxes

            tblPotentialDistributor.setTblPotentialDistributorCompetitorBrandMstrList(tblPotentialDistributorCompetitorBrandList);


            if(et_InvestmentInCompanyProducts!=null && !TextUtils.isEmpty(et_InvestmentInCompanyProducts.getText().toString().trim()))
                tblPotentialDistributor.setCompanyProductInvestmentLacs(Integer.parseInt(et_InvestmentInCompanyProducts.getText().toString().trim()));
            else
                tblPotentialDistributor.setCompanyProductInvestmentLacs(0);

            if(et_RetailerCreditLimit!=null && !TextUtils.isEmpty(et_RetailerCreditLimit.getText().toString().trim()))
                tblPotentialDistributor.setRetailerCreditLimit(Integer.parseInt(et_RetailerCreditLimit.getText().toString().trim()));
            else
                tblPotentialDistributor.setRetailerCreditLimit(0);




            if (tv_WorkingWithDistributorSince != null && (TextUtils.isEmpty(tv_WorkingWithDistributorSince.getText().toString().trim()) || tv_WorkingWithDistributorSince.getText().toString().trim().equals("Select Month Year") )) {

                showAlertSingleButtonInfo("Please mention how long have you been working with distributorship.");
                return;
            }
            tblPotentialDistributor.setBusinessSince(tv_WorkingWithDistributorSince.getText().toString().trim());

      /*  if (tv_WorkingWithDistributorSince != null && TextUtils.isEmpty(tv_WorkingWithDistributorSince.getText().toString().trim())) {

            showAlertSingleButtonInfo("Please mention how long have you been working with distributorship.");
            return;
        }*/
            if (flgIntrestedInDistributorShip == -1) {

                showAlertSingleButtonInfo("Please choose intrested in distributorship?");
                return;
            }

            tblPotentialDistributor.setDistributorReady(flgIntrestedInDistributorShip);

            if (tv_NextFollowUp_date != null && (TextUtils.isEmpty(tv_NextFollowUp_date.getText().toString().trim()) || tv_NextFollowUp_date.getText().toString().trim().equals("Select Date") )) {

                showAlertSingleButtonInfo("Please mention Next followup date.");
                return;
            }
            tblPotentialDistributor.setNextFollowupDate(tv_NextFollowUp_date.getText().toString().trim());


            if(et_Dispatch!=null && !TextUtils.isEmpty(et_Dispatch.getText().toString().trim()))
                tblPotentialDistributor.setDBREmployee_Dispatch(Integer.parseInt(et_Dispatch.getText().toString().trim()));
            else
                tblPotentialDistributor.setDBREmployee_Dispatch(0);

            if(et_Billing!=null && !TextUtils.isEmpty(et_Billing.getText().toString().trim()))
                tblPotentialDistributor.setDBREmployee_Billing(Integer.parseInt(et_Billing.getText().toString().trim()));
            else
                tblPotentialDistributor.setDBREmployee_Billing(0);


            if(et_SalesStaff!=null && !TextUtils.isEmpty(et_SalesStaff.getText().toString().trim()))
                tblPotentialDistributor.setDBREmployee_SalesStaff(Integer.parseInt(et_SalesStaff.getText().toString().trim()));
            else
                tblPotentialDistributor.setDBREmployee_SalesStaff(0);


            if(et_GoodsServicingDays!=null && !TextUtils.isEmpty(et_GoodsServicingDays.getText().toString().trim()))
                tblPotentialDistributor.setOrderTATinDays(Integer.parseInt(et_GoodsServicingDays.getText().toString().trim()));
            else
                tblPotentialDistributor.setOrderTATinDays(0);



            validateLocationAndProceed();



        }
        else if(flgNewScoutingOrOldDistributor==1 && tblPotentialDistributor.getFlgViewOrEdit()==1)
        {


            tblPotentialDistributor.setNodeID(ScoutingDistributorID);
            tblPotentialDistributor.setNewDBRCode("0");


            tblPotentialDistributor.setNodeType(ScoutingDistributorNodeType);
            tblPotentialDistributor.setCreatedDate(TimeUtils.getNetworkDate(ActivityDistributorScouting.this,"dd-MMM-yyyy"));
            tblPotentialDistributor.setLatCode(Double.parseDouble(fnLati));
            tblPotentialDistributor.setLongCode(Double.parseDouble(fnLongi));

            tblPotentialDistributor.setEntryPersonNodeID(PersonNodeId);
            tblPotentialDistributor.setEntryPersonNodeType(PersonNodeType);
            tblPotentialDistributor.setIsOldDistributorReplaced(-1);
            tblPotentialDistributor.setOldDistriutorID(0);
            tblPotentialDistributor.setFlgDistributorSS(-1);
            tblPotentialDistributor.setFlgTownDistributorSubD(-1);
            tblPotentialDistributor.setBankAccountNumber("");
            tblPotentialDistributor.setIFSCCode("");
            tblPotentialDistributor.setBankAddress("");
            tblPotentialDistributor.setExpectedBusiness("");
            tblPotentialDistributor.setReqGodownSpace("");
            tblPotentialDistributor.setAgreedGodownSpace("");
            tblPotentialDistributor.setAgreedInvestment("");
            tblPotentialDistributor.setIdealROI("");
            tblPotentialDistributor.setExpectedROI("");
            tblPotentialDistributor.setFlgProprietorPartner(-1);
            tblPotentialDistributor.setIsGSTDetailsSubmitted(-1);
            tblPotentialDistributor.setGSTNumber("");
            tblPotentialDistributor.setIsPANOfFirmSubmitted(-1);
            tblPotentialDistributor.setPANNoOfFirm("");
            tblPotentialDistributor.setIsCheckGiven(-1);
            tblPotentialDistributor.setChequeNumber("");
            tblPotentialDistributor.setIsProprietorPanSubmited(-1);
            tblPotentialDistributor.setProprietorPanNumber("");
            tblPotentialDistributor.setIsPartner1PANSubmitted(-1);
            tblPotentialDistributor.setPanNumber_Partner1("");
            tblPotentialDistributor.setIsPartner2PANSubmitted(-1);
            tblPotentialDistributor.setPanNumber_Partner2("");
            tblPotentialDistributor.setIsPartnerDeedSubmitted(-1);
            tblPotentialDistributor.setPartnerDeedNumber("");

            if (et_Dealer_Firm_Name != null && TextUtils.isEmpty(et_Dealer_Firm_Name.getText().toString().trim())) {

                showAlertSingleButtonInfoForValidation("Dealer Firm Name Required.", et_Dealer_Firm_Name);
                return;

            }
            tblPotentialDistributor.setDescr(et_Dealer_Firm_Name.getText().toString().trim().replace("&", "-"));

            if (et_ContactPerson != null && TextUtils.isEmpty(et_ContactPerson.getText().toString().trim())) {

                showAlertSingleButtonInfoForValidation("Contact Person Name Required.", et_ContactPerson);
                return;
            }
      /*  if (et_ContactPerson != null && !et_ContactPerson.getText().toString().trim().equals("NA")) {

            boolean bl = false;
            if (et_ContactPerson.getText().toString().trim().startsWith("6")) {
                bl = true;
            } else if (et_ContactPerson.getText().toString().trim().startsWith("7")) {
                bl = true;
            } else if (et_ContactPerson.getText().toString().trim().startsWith("8")) {
                bl = true;
            } else if (et_ContactPerson.getText().toString().trim().startsWith("9")) {
                bl = true;
            }
            //(!fnlMobileNumber.startsWith("6") || !fnlMobileNumber.startsWith("7") || !fnlMobileNumber.startsWith("8") || !fnlMobileNumber.startsWith("9")  )
            if (bl == false) {

                showAlertSingleButtonInfoForValidation("Invalid Contact Person Number.\nPlease correct that.", et_ContactPerson);
                return;

            }


        }*/
            tblPotentialDistributor.setContactPersonName(et_ContactPerson.getText().toString().trim());


            if (et_MobileNo != null && TextUtils.isEmpty(et_MobileNo.getText().toString().trim())) {

                showAlertSingleButtonInfoForValidation("Mobile Number Required.", et_MobileNo);
                return;
            }
            if (et_MobileNo != null && et_MobileNo.getText().toString().length()!=10) {

                showAlertSingleButtonInfoForValidation("Mobile Number not valid.", et_MobileNo);
                return;
            }
            if (et_MobileNo != null && !et_MobileNo.getText().toString().trim().equals("NA")) {

                boolean bl = false;
                if (et_MobileNo.getText().toString().trim().startsWith("6")) {
                    bl = true;
                } else if (et_MobileNo.getText().toString().trim().startsWith("7")) {
                    bl = true;
                } else if (et_MobileNo.getText().toString().trim().startsWith("8")) {
                    bl = true;
                } else if (et_MobileNo.getText().toString().trim().startsWith("9")) {
                    bl = true;
                }
                //(!fnlMobileNumber.startsWith("6") || !fnlMobileNumber.startsWith("7") || !fnlMobileNumber.startsWith("8") || !fnlMobileNumber.startsWith("9")  )
                if (bl == false) {

                    showAlertSingleButtonInfoForValidation("Invalid Mobile Number.\nPlease correct that.", et_MobileNo);
                    return;

                }


            }
            tblPotentialDistributor.setContactPersonMobileNumber(et_MobileNo.getText().toString().trim());

            tblPotentialDistributor.setTelephoneNo(et_teleNo.getText().toString().trim());


            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String mail = et_emailID.getText().toString().trim();


            if (!et_emailID.getText().toString().trim().equals("") && !mail.matches(emailPattern)) {

                showAlertSingleButtonInfoForValidation(getResources().getString(R.string.txtValidateEmailID), et_emailID);


                return;
            }

            tblPotentialDistributor.setContactPersonEMailID(et_emailID.getText().toString().trim());

            if (!gstno_txt.getText().toString().isEmpty()) {
                if (gstno_txt.getText().toString().length() != 15) {
                    showAlertSingleButtonInfoForValidation("Please enter proper GST Number", gstno_txt);

                    return;
                }
                String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                Pattern p = Pattern.compile("[0-9]{2}");
                Matcher m = p.matcher(firstTwoStr);
                if (!m.find() || !m.group().equals(firstTwoStr)) {

                    showAlertSingleButtonInfoForValidation("Please enter proper GST Number", gstno_txt);
                    return;
                }
            }
            tblPotentialDistributor.setFlgGSTTaken(0);
            tblPotentialDistributor.setGSTNumber(gstno_txt.getText().toString().trim());

         /*   List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDetailsList= stream(tblPotentialDistributorCompetitorCompanyMstrList).where(p -> p.getFlgCheck()==1).toList();

            tblPotentialDistributor.setTblPotentialDistributorCompetitorCompanyMstrList(tblPotentialDistributorCompetitorCompanyDetailsList);
*/
            String OtherCompetitors="";
            if(et_OtherCompetitors!=null && !TextUtils.isEmpty(et_OtherCompetitors.getText().toString().trim()))
            {
                OtherCompetitors=et_OtherCompetitors.getText().toString().trim().replaceAll("&","-");
            }

            List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDetailsList= stream(tblPotentialDistributorCompetitorCompanyMstrList).where(p -> p.getFlgCheck()==1).toList();
            if(tblPotentialDistributorCompetitorCompanyDetailsList!=null && tblPotentialDistributorCompetitorCompanyDetailsList.size()>0) {
                for (TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyDetailsListForOther : tblPotentialDistributorCompetitorCompanyDetailsList) {
                  /*  if(tblPotentialDistributorCompetitorCompanyDetailsListForOther.getFlgOther()==1)
                    {
                        tblPotentialDistributorCompetitorCompanyDetailsListForOther.setOtherCompany(OtherCompetitors);
                        break;
                    }*/

                    if(tblPotentialDistributorCompetitorCompanyDetailsListForOther.getSalesQty() == null || TextUtils.isEmpty(tblPotentialDistributorCompetitorCompanyDetailsListForOther.getSalesQty().toString().trim()) )
                    {
                        showAlertSingleButtonInfo("Please fill sales for :"+tblPotentialDistributorCompetitorCompanyDetailsListForOther.getCompetitorCompany());

                        return;

                    }
                }
            }

            //Saving Other Comany Starts Here For Text Boxes
            if(ll_OtherCompanyWholeSection!=null && ll_OtherCompanyWholeSection.getChildCount()>0) {

                for (int i = 0; i < (totalCompanyNewCount); i++) {
                    LinearLayout viewOtherCompanyRow = (LinearLayout) ll_OtherCompanyWholeSection.getChildAt(i);

                    String CompName="";
                    String CompSales="";

                    EditText et_NewCompanyName = viewOtherCompanyRow.findViewWithTag("etNewCompanyName_"+i);
                    et_NewCompanyName.setTag("etNewCompanyName_" +  i);

                    EditText et_Sales = viewOtherCompanyRow.findViewWithTag("etSales_"+i);
                    et_Sales.setTag("etSales_" + i);


                    if (et_NewCompanyName != null && !TextUtils.isEmpty(et_NewCompanyName.getText().toString().trim())) {
                        CompName=et_NewCompanyName.getText().toString().trim().replace("&","-");
                    }
                    if (et_Sales != null && !TextUtils.isEmpty(et_Sales.getText().toString().trim())) {
                        CompSales=et_Sales.getText().toString().trim().replace("&","-");
                    }

                    if(TextUtils.isEmpty(CompName) && TextUtils.isEmpty(CompSales))
                    {

                    }
                    else
                    {
                        if(!TextUtils.isEmpty(CompName) || !TextUtils.isEmpty(CompSales))
                        {

                            if (et_NewCompanyName.getText().toString().trim().equals("")) {

                                showAlertSingleButtonInfo("Please enter Company Name against sales :"+CompSales);
                                return;
                            }
                            else if (et_Sales.getText().toString().trim().equals("")) {

                                showAlertSingleButtonInfo("Please enter Sales against Company name :"+CompName);
                                return;
                            }
                            else
                            {
                                TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyNewRecordMstr=new TblPotentialDistributorCompetitorCompanyMstr();
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setNodeID(ScoutingDistributorID);

                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setNodeType(ScoutingDistributorNodeType);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setCompetitorCompanyID(0);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setCompetitorCompany(CompName);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setOtherCompany(CompName);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setFlgOther(0);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setOtherCompanyCode(i+1);
                                tblPotentialDistributorCompetitorCompanyNewRecordMstr.setSalesQty(CompSales);
                                tblPotentialDistributorCompetitorCompanyDetailsList.add(tblPotentialDistributorCompetitorCompanyNewRecordMstr);
                            }

                        }

                    }



                }
            }

            //Saving Other Company Ends Here For Text Boxes
            tblPotentialDistributor.setTblPotentialDistributorCompetitorCompanyMstrList(tblPotentialDistributorCompetitorCompanyDetailsList);

            tblPotentialDistributor.setAreaCovered(et_AreaCovered.getText().toString().trim());
            tblPotentialDistributor.setNoOfRetailersCovered(et_NoOfRetailersCovered.getText().toString().trim());
            tblPotentialDistributor.setGodownAreaSqFt(et_gdwnAreaSqFeet.getText().toString().trim());
            tblPotentialDistributor.setMonthlyTurnOver(et_MonthlyTurnOver.getText().toString().trim());

            //Vechile ka baaki hai

            List<TblPotentialDistributorVehicleMasterList> tblPotentialDistributorVehicleMasterSelectedOne=stream(tblPotentialDistributorVehicleMasterLists).where(p->p.getFlgEnable()==1).toList();
            if(tblPotentialDistributorVehicleMasterSelectedOne!=null && tblPotentialDistributorVehicleMasterSelectedOne.size()>0 && tblPotentialDistributorVehicleMasterSelectedOne.get(0).getVehicleTypeID()!=0)
            {
                tblPotentialDistributor.setVehicleTypeID(""+tblPotentialDistributorVehicleMasterSelectedOne.get(0).getVehicleTypeID());
                if (et_VecCount != null && TextUtils.isEmpty(et_VecCount.getText().toString().trim())) {
                    tblPotentialDistributor.setNoOfVehicles(0);

                }
                else
                    tblPotentialDistributor.setNoOfVehicles(Integer.parseInt(et_VecCount.getText().toString().trim()));
            }
            else
            {
                tblPotentialDistributor.setVehicleTypeID("0");
                tblPotentialDistributor.setNoOfVehicles(0);
            }

            String OtherBrands="";
            if(et_OtherBrands!=null && !TextUtils.isEmpty(et_OtherBrands.getText().toString().trim()))
            {
                OtherBrands=et_OtherBrands.getText().toString().trim().replaceAll("&","-");
            }


            List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandList= stream(tblPotentialDistributorCompetitorBrandMstrList).where(p -> p.getFlgCheck()==1).toList();


            if(tblPotentialDistributorCompetitorBrandList!=null && tblPotentialDistributorCompetitorBrandList.size()>0) {
                for (TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandMstrForOther : tblPotentialDistributorCompetitorBrandList) {
                 /*   if(tblPotentialDistributorCompetitorBrandMstrForOther.getFlgOther()==1)
                    {
                        tblPotentialDistributorCompetitorBrandMstrForOther.setOtherBrand(OtherBrands);
                        break;
                    }*/

                    if(tblPotentialDistributorCompetitorBrandMstrForOther.getSalesQty() != null && !TextUtils.isEmpty(tblPotentialDistributorCompetitorBrandMstrForOther.getSalesQty().toString().trim()) && tblPotentialDistributorCompetitorBrandMstrForOther.getSalesQty().equals("0"))
                    {
                        showAlertSingleButtonInfo("Please fill sales for :"+tblPotentialDistributorCompetitorBrandMstrForOther.getCompetitorBrand());

                        return;

                    }
                }
            }



            //Saving Other Brands Starts Here For Text Boxes
            if(ll_OtherBrandWholeSection!=null && ll_OtherBrandWholeSection.getChildCount()>0) {

                for (int i = 0; i < (totalBrandNewCount); i++) {
                    LinearLayout viewOtherBrandRow = (LinearLayout) ll_OtherBrandWholeSection.getChildAt(i);

                    String BrandName="";
                    String BrandSales="0";

                    EditText et_NewBrandName = viewOtherBrandRow.findViewWithTag("etNewBrandName_"+i);
                    et_NewBrandName.setTag("etNewBrandName_" +  i);

                    EditText et_BrandSales = viewOtherBrandRow.findViewWithTag("etBrandSales_"+i);
                    et_BrandSales.setTag("etBrandSales_" + i);


                    if (et_NewBrandName != null && !TextUtils.isEmpty(et_NewBrandName.getText().toString().trim())) {
                        BrandName=et_NewBrandName.getText().toString().trim().replace("&","-");
                    }
                   /* if (et_BrandSales != null && !TextUtils.isEmpty(et_BrandSales.getText().toString().trim())) {
                        BrandSales=et_BrandSales.getText().toString().trim().replace("&","-");
                    }*/

                    if(TextUtils.isEmpty(BrandName) && TextUtils.isEmpty(BrandSales))
                    {

                    }
                    else
                    {
                        if(!TextUtils.isEmpty(BrandName) || !TextUtils.isEmpty(BrandSales))
                        {

                           /*  if (et_NewBrandName.getText().toString().trim().equals("")) {

                                showAlertSingleButtonInfo("Please enter Brand Name against sales :"+BrandSales);
                                return;
                            }
                           else if (et_BrandSales.getText().toString().trim().equals("")) {

                                showAlertSingleButtonInfo("Please enter Sales against Brand name :"+BrandName);
                                return;
                            }*/
                           /* else
                            {*/


                            TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandMstrNewRecordMstr=new TblPotentialDistributorCompetitorBrandMstr();
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setNodeID(ScoutingDistributorID);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setNodeType(ScoutingDistributorNodeType);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setCompetitorBrandID(0);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setCompetitorBrand(BrandName);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setOtherBrand(BrandName);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setFlgOther(0);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setOtherBrandCode(i+1);
                            tblPotentialDistributorCompetitorBrandMstrNewRecordMstr.setSalesQty(BrandSales);
                            tblPotentialDistributorCompetitorBrandList.add(tblPotentialDistributorCompetitorBrandMstrNewRecordMstr);
                            //}

                        }

                    }



                }
            }

            //Saving Other Brands Ends Here For Text Boxes

            tblPotentialDistributor.setTblPotentialDistributorCompetitorBrandMstrList(tblPotentialDistributorCompetitorBrandList);


            if(et_InvestmentInCompanyProducts!=null && !TextUtils.isEmpty(et_InvestmentInCompanyProducts.getText().toString().trim()))
                tblPotentialDistributor.setCompanyProductInvestmentLacs(Integer.parseInt(et_InvestmentInCompanyProducts.getText().toString().trim()));
            else
                tblPotentialDistributor.setCompanyProductInvestmentLacs(0);

            if(et_RetailerCreditLimit!=null && !TextUtils.isEmpty(et_RetailerCreditLimit.getText().toString().trim()))
                tblPotentialDistributor.setRetailerCreditLimit(Integer.parseInt(et_RetailerCreditLimit.getText().toString().trim()));
            else
                tblPotentialDistributor.setRetailerCreditLimit(0);




            if (tv_WorkingWithDistributorSince != null && (TextUtils.isEmpty(tv_WorkingWithDistributorSince.getText().toString().trim()) || tv_WorkingWithDistributorSince.getText().toString().trim().equals("Select Month Year") )) {

                showAlertSingleButtonInfo("Please mention how long have you been working with distributorship.");
                return;
            }
            tblPotentialDistributor.setBusinessSince(tv_WorkingWithDistributorSince.getText().toString().trim());

      /*  if (tv_WorkingWithDistributorSince != null && TextUtils.isEmpty(tv_WorkingWithDistributorSince.getText().toString().trim())) {

            showAlertSingleButtonInfo("Please mention how long have you been working with distributorship.");
            return;
        }*/
            if (flgIntrestedInDistributorShip == -1) {

                showAlertSingleButtonInfo("Please choose inrested in distributorship?");
                return;
            }

            tblPotentialDistributor.setDistributorReady(flgIntrestedInDistributorShip);

            if (tv_NextFollowUp_date != null && (TextUtils.isEmpty(tv_NextFollowUp_date.getText().toString().trim()) || tv_NextFollowUp_date.getText().toString().trim().equals("Select Date") )) {

                showAlertSingleButtonInfo("Please mention Next followup date.");
                return;
            }
            tblPotentialDistributor.setNextFollowupDate(tv_NextFollowUp_date.getText().toString().trim());


            if(et_Dispatch!=null && !TextUtils.isEmpty(et_Dispatch.getText().toString().trim()))
                tblPotentialDistributor.setDBREmployee_Dispatch(Integer.parseInt(et_Dispatch.getText().toString().trim()));
            else
                tblPotentialDistributor.setDBREmployee_Dispatch(0);

            if(et_Billing!=null && !TextUtils.isEmpty(et_Billing.getText().toString().trim()))
                tblPotentialDistributor.setDBREmployee_Billing(Integer.parseInt(et_Billing.getText().toString().trim()));
            else
                tblPotentialDistributor.setDBREmployee_Billing(0);


            if(et_SalesStaff!=null && !TextUtils.isEmpty(et_SalesStaff.getText().toString().trim()))
                tblPotentialDistributor.setDBREmployee_SalesStaff(Integer.parseInt(et_SalesStaff.getText().toString().trim()));
            else
                tblPotentialDistributor.setDBREmployee_SalesStaff(0);


            if(et_GoodsServicingDays!=null && !TextUtils.isEmpty(et_GoodsServicingDays.getText().toString().trim()))
                tblPotentialDistributor.setOrderTATinDays(Integer.parseInt(et_GoodsServicingDays.getText().toString().trim()));
            else
                tblPotentialDistributor.setOrderTATinDays(0);

            validateLocationAndProceed();
        }
        else
        {
            tblPotentialDistributor.setNodeID(ScoutingDistributorID);
            tblPotentialDistributor.setNewDBRCode("0");
            ll_MarketReputationRetailerSection.setVisibility(View.VISIBLE);


            ll_sectionOtherThenRetailerFeedBack.setVisibility(View.GONE);
            location_layout.setVisibility(View.GONE);
            bt_Next.setVisibility(View.GONE);
            bt_Previous.setVisibility(View.VISIBLE);
            // bt_Submit.setVisibility(View.VISIBLE);//Orginal
            bt_Submit.setVisibility(View.GONE);
            bt_Save.setVisibility(View.VISIBLE);

            if(tblPotentialDistributor.getFlgViewOrEdit()==2)
            {

                bt_Save.setVisibility(View.GONE);
                bt_Submit.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.bt_Skip)
    public void fnSkipPage()
    {

        Intent intent = new Intent(ActivityDistributorScouting.this, SocutingDistributorSelection.class);

        startActivity(intent);
        finish();
    }

    @OnClick(R.id.bt_Previous)
    public void PrevisousSlide(View view) {
        flgFinalSubmit=0;
        ll_MarketReputationRetailerSection.setVisibility(View.GONE);
        ll_sectionOtherThenRetailerFeedBack.setVisibility(View.VISIBLE);
        location_layout.setVisibility(View.VISIBLE);
        bt_Next.setVisibility(View.VISIBLE);
        bt_Previous.setVisibility(View.GONE);
        bt_Submit.setVisibility(View.GONE);
        bt_Save.setVisibility(View.GONE);
    }

    @OnClick(R.id.bt_Submit)
    public void SubmitData(View view) {
        flgFinalSubmit=2;
        validateAndSaveData();
    }

    @OnClick(R.id.bt_Save)
    public void saveData(View view) {
        flgFinalSubmit=1;
        validateAndSaveData();
    }

    public Boolean fnValidatImageGodownPhotoRakenorNot() {
        Boolean flgImageRackPhotoTakeOrNot = false;
        if (hmapPhotoDetailsForSaving != null && (hmapPhotoDetailsForSaving.size() > 0)) {

            flgImageRackPhotoTakeOrNot = true;

        }
        return flgImageRackPhotoTakeOrNot;
    }


    public void SyncNow() {

        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);


        //dbengine.open();
        String presentRoute = "0";
        //dbengine.close();
        //syncTIMESTAMP = System.currentTimeMillis();
        //Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy.HH.mm.ss", Locale.ENGLISH);
        //fullFileName1 = df.format(dateobj);
        String newfullFileName = AppUtils.fnGetFileNameToSave(this);//  AppUtils.getIMEI(this) + "." + presentRoute + "." + AppUtils.doGetTime("dd.MMM.yyyy.HH.mm.ss");//CommonInfo.imei + "." + presentRoute + "." + df.format(dateobj);


        try {

            File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!OrderXMLFolder.exists()) {
                OrderXMLFolder.mkdirs();
            }
            //   mDataSource.updateIAllImageRecords(3);


            DASFA.export(CommonInfo.DATABASE_NAME, newfullFileName, "0");

            mDataSource.savetbl_XMLfiles(newfullFileName, "3", "1");

            //   mDataSource.updateIAllImageRecords(5);
            mDataSource.UpdateStoreVisitWiseTablesAfterSync(5);

            //  mDataSource.UpdateDistributerFlag(""+tblSpokeProfile.getNodeID(), 4);

            //  mDataSource.fnupdateSuplierMstrLocationtrackRemapFlg(""+tblSpokeProfile.getNodeID(),addressUserInput);//,contactNumberUserInput
            mDataSource.deleteLocationTable();


            if (isOnline()) {


                Intent mMyServiceIntent = new Intent(ActivityDistributorScouting.this, SyncJobService.class);
                mMyServiceIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                mMyServiceIntent.putExtra("OrigZipFileName", newfullFileName);
                mMyServiceIntent.putExtra("whereTo", "DayStart");//
                if (!isMyServiceRunning(SyncJobService.class)) {
                    startService(mMyServiceIntent);
                }


            } else {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

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
                       /* Intent intent = new Intent(ActivityDistributorScouting.this, DistributorSelectionMaping.class);
                        startActivity(intent);
                        finish();*/
                    }
                }).create().show();
    }



    private void populateDistributorScoutingPersonData() {


        if(flgNewScoutingOrOldDistributor==1)
        {
            et_Dealer_Firm_Name.setText(tblPotentialDistributor.getDescr());
            et_ContactPerson.setText(tblPotentialDistributor.getContactPersonName());
            et_MobileNo.setText(tblPotentialDistributor.getContactPersonMobileNumber());
            et_teleNo.setText(tblPotentialDistributor.getTelephoneNo());
            et_emailID.setText(tblPotentialDistributor.getContactPersonEMailID());
            gstno_txt.setText(tblPotentialDistributor.getGSTNumber());
            et_gdwnAreaSqFeet.setText(tblPotentialDistributor.getGodownAreaSqFt());
            et_MonthlyTurnOver.setText(tblPotentialDistributor.getMonthlyTurnOver());
            et_InvestmentInCompanyProducts.setText(""+tblPotentialDistributor.getCompanyProductInvestmentLacs());
            et_RetailerCreditLimit.setText(""+tblPotentialDistributor.getRetailerCreditLimit());
            tv_WorkingWithDistributorSince.setText(tblPotentialDistributor.getBusinessSince());
            flgIntrestedInDistributorShip=tblPotentialDistributor.getDistributorReady();
           /* if(tblPotentialDistributor.getDistributorReady()==1)
            {
                flgIntrestedInDistributorShip = 0;

            }*/
            if(flgIntrestedInDistributorShip==0)
            {
                rg_IntrestedInDistributorShip.check(R.id.rbIntrestedIn_No);
                //  rg_IntrestedInDistributorShip.findViewById(R.id.rbIntrestedIn_No).setSelected(true);
            }
            if(flgIntrestedInDistributorShip==1)
            {
                rg_IntrestedInDistributorShip.check(R.id.rbIntrestedIn_Yes);
                //  rg_IntrestedInDistributorShip.findViewById(R.id.rbIntrestedIn_Yes).setSelected(true);
            }
            if(flgIntrestedInDistributorShip==2)
            {
                rg_IntrestedInDistributorShip.check(R.id.rbIntrestedIn_MayBe);
                //  rg_IntrestedInDistributorShip.findViewById(R.id.rbIntrestedIn_MayBe).setSelected(true);
            }

            if(tblPotentialDistributor.getVehicleTypeID()!=null && !tblPotentialDistributor.getVehicleTypeID().equals("0") && !TextUtils.isEmpty(tblPotentialDistributor.getVehicleTypeID()))
            {
                int indexVechile = 0;
                boolean isVechileRequiredToBeSelected=false;
                for(TblPotentialDistributorVehicleMasterList tblPotentialDistributorVehicleMasterList:tblPotentialDistributorVehicleMasterLists)
                {
                    if(tblPotentialDistributorVehicleMasterList.getVehicleTypeID()==Integer.parseInt(tblPotentialDistributor.getVehicleTypeID()))
                    {
                        isVechileRequiredToBeSelected=true;
                        break;
                    }


                    indexVechile++;
                }
                if(isVechileRequiredToBeSelected)
                {
                    spinner_VehicleList.setSelection(indexVechile);
                    et_VecCount.setVisibility(View.VISIBLE);
                    et_VecCount.setText(""+tblPotentialDistributor.getNoOfVehicles());
                }
                else
                {
                    spinner_VehicleList.setSelection(0);
                }



            }

            tv_NextFollowUp_date.setText(tblPotentialDistributor.getNextFollowupDate());
            et_Dispatch.setText(""+tblPotentialDistributor.getDBREmployee_Dispatch());
            et_Billing.setText(""+tblPotentialDistributor.getDBREmployee_Billing());
            et_SalesStaff.setText(""+tblPotentialDistributor.getDBREmployee_SalesStaff());
            et_GoodsServicingDays.setText(""+tblPotentialDistributor.getOrderTATinDays());
            et_NoOfRetailersCovered.setText(""+tblPotentialDistributor.getNoOfRetailersCovered());
            et_AreaCovered.setText(""+tblPotentialDistributor.getAreaCovered());
            et_Address.setText(tblPotentialDistributor.getAddress());
            et_Village.setText(tblPotentialDistributor.getCity());
            et_Pincode.setText(tblPotentialDistributor.getPincode());
            et_State.setText(tblPotentialDistributor.getState());
            et_District.setText(tblPotentialDistributor.getDistrict());


        }
        if(tblPotentialDistributor.getFlgViewOrEdit()==2)
        {
            et_Dealer_Firm_Name.setEnabled(false);
            et_ContactPerson.setEnabled(false);
            et_MobileNo.setEnabled(false);
            et_teleNo.setEnabled(false);
            et_emailID.setEnabled(false);
            gstno_txt.setEnabled(false);
            et_gdwnAreaSqFeet.setEnabled(false);
            et_MonthlyTurnOver.setEnabled(false);
            et_AreaCovered.setEnabled(false);
            et_NoOfRetailersCovered.setEnabled(false);
            et_InvestmentInCompanyProducts.setEnabled(false);
            et_RetailerCreditLimit.setEnabled(false);
            tv_WorkingWithDistributorSince.setEnabled(false);

            rg_IntrestedInDistributorShip.setEnabled(false);


            tv_NextFollowUp_date.setEnabled(false);
            et_Dispatch.setEnabled(false);
            et_Billing.setEnabled(false);
            et_SalesStaff.setEnabled(false);
            et_GoodsServicingDays.setEnabled(false);

            et_Address.setEnabled(false);
            et_Village.setEnabled(false);
            et_Pincode.setEnabled(false);
            et_State.setEnabled(false);
            et_District.setEnabled(false);




        }
        //spokeNameTV.setText(tblSpokeProfile.getDescr());



      /*  if (tblSpokeProfile.getPhoneNo() != null && !tblSpokeProfile.getPhoneNo().equals("NA"))
            et_ContactPerson.setText(tblSpokeProfile.getPhoneNo());*/


        ll_gst_num.setVisibility(View.GONE);
        //gstno_txt.setText(tblSpokeProfile.getGstValue());


        // SpokeIDForImg = "" + tblSpokeProfile.getNodeType();
       /* list_ImgName = mDataSource.getImagesAgainstStoreOrSpoke("" + tblSpokeProfile.getNodeID(), "0", 2);
        if (list_ImgName != null && list_ImgName.size() > 0) {
            adapterImage = new ImageAdapter(ActivityDistributorScouting.this);
            //expandableHeightGridView.setAdapter(adapterImage);
            clickedTagPhoto = "Gowdown";
            hmapImageAdapter.put(clickedTagPhoto, adapterImage);
            fetchPhotoDetails();
        }*/


    }

    private void populateDistributorScoutingRetailerDetailsFromServer()
    {
        tblPotentialDistributorRetailersFeedBackDetailsList=new ArrayList<TblPotentialDistributorRetailersFeedBackDetails>();
        tblPotentialDistributorRetailersFeedBackDetailsList=mDataSource.fnGetPotentialDistributorRetailersFeedBackDetails(ScoutingDistributorID,""+PersonNodeId,PersonNodeType);
        if(tblPotentialDistributorRetailersFeedBackDetailsList!=null && tblPotentialDistributorRetailersFeedBackDetailsList.size()>0)
        {
            if(ll_MarketReputationCheckWholeSection!=null && ll_MarketReputationCheckWholeSection.getChildCount()>0) {

                for (int i = 0; i < 3; i++) {

                    int finalI = i;
                    List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsListLocal=stream(tblPotentialDistributorRetailersFeedBackDetailsList).where(p->p.getRetailerSectionID().equals(""+(finalI+1))).toList();
                    LinearLayout view = (LinearLayout) ll_MarketReputationCheckWholeSection.getChildAt(i);

                    EditText txt_RetailerName = view.findViewWithTag("etRetailerName_"+i);
                    EditText txt_RetailerAddress = view.findViewWithTag("etRetailerAddress_"+i);
                    EditText txt_RetailerComment = view.findViewWithTag("etRetailerComment_"+i);
                    RadioGroup rg_RetailerFeedBack = view.findViewWithTag("rgRetailerFeedBack_"+i);
                    EditText et_RetailerContactNumber=view.findViewWithTag("etRetailerContactNumber_"+i);

                    if(tblPotentialDistributorRetailersFeedBackDetailsListLocal!=null && tblPotentialDistributorRetailersFeedBackDetailsListLocal.size()>0) {
                        txt_RetailerName.setText(tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getRetailerName());
                        txt_RetailerAddress.setText(tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getAddress());
                        txt_RetailerComment.setText(tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getComment());
                        if(!tblPotentialDistributorRetailersFeedBackDetailsListLocal.get(0).getContactNumber().equals("NA"))
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

                    if(tblPotentialDistributor.getFlgViewOrEdit()==2)
                    {
                        txt_RetailerName.setEnabled(false);
                        txt_RetailerAddress.setEnabled(false);
                        txt_RetailerComment.setEnabled(false);
                        rg_RetailerFeedBack.setEnabled(false);
                        bt_Save.setVisibility(View.GONE);
                        bt_Submit.setVisibility(View.GONE);
                    }

                }
            }
        }

    }

    @OnClick(R.id.btn_camera)
    public void onCameraClick() {

        clickedTagPhoto = "Gowdown";
        if (hmapPhotoDetailsForSaving.size() < 2)
            openCustomCamara();
        else {
            //   AppUtils.fnValidateMaxAllowedPhotos(mActivity);
        }
    }

    public void openCustomCamara() {
        if (dialog != null) {
            if (!dialog.isShowing()) {
                openCamera();
            }
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        arrImageData.clear();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        dialog = new Dialog(ActivityDistributorScouting.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        //dialog.setTitle("Calculation");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_cameraview);
        WindowManager.LayoutParams parms = dialog.getWindow().getAttributes();

        parms.height = ViewGroup.LayoutParams.MATCH_PARENT;
        parms.width = ViewGroup.LayoutParams.MATCH_PARENT;
        cameraPreview = dialog.findViewById(R.id.camera_preview);
        switchCamera = dialog.findViewById(R.id.btSwitchCamera);
        if (mCamera != null) {
            mPreview = new CameraPreview(ActivityDistributorScouting.this, mCamera);
            cameraPreview.addView(mPreview);
        }
        //onResume code
        if (!hasCamera(ActivityDistributorScouting.this)) {
            Toast toast = Toast.makeText(ActivityDistributorScouting.this, getText(R.string.txtNoCamera), Toast.LENGTH_LONG);
            toast.show();
        }

        if (mCamera == null) {
            //if the front facing camera does not exist
            if (findFrontFacingCamera() < 0) {
                Toast.makeText(ActivityDistributorScouting.this, getText(R.string.txtNoFrontCamera), Toast.LENGTH_LONG).show();
                switchCamera.setVisibility(View.GONE);
            }

            //mCamera = Camera.open(findBackFacingCamera());

			/*if(mCamera!=null){
				mCamera.release();
				mCamera=null;
			}*/
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
			/*if(mCamera==null){
				mCamera=Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
			}*/
            mPreview = new CameraPreview(this, mCamera);
            cameraPreview.addView(mPreview);
            boolean isParameterSet = false;
            try {

                Camera.Parameters params = mCamera.getParameters();


                List<Camera.Size> sizes = params.getSupportedPictureSizes();
                Camera.Size size = sizes.get(0);
                //Camera.Size size1 = sizes.get(0);
                for (int i = 0; i < sizes.size(); i++) {

                    if (sizes.get(i).width > size.width)
                        size = sizes.get(i);
                }

                //System.out.println(size.width + "mm" + size.height);

                params.setPictureSize(size.width, size.height);
                params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                //	params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
                params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);

                //	params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

                isLighOn = false;
                int minExpCom = params.getMinExposureCompensation();
                int maxExpCom = params.getMaxExposureCompensation();

                if (maxExpCom > 4 && minExpCom < 4) {
                    params.setExposureCompensation(4);
                } else {
                    params.setExposureCompensation(0);
                }

                params.setAutoExposureLock(false);
                params.setAutoWhiteBalanceLock(false);
                //String supportedIsoValues = params.get("iso-values");
                // String newVAlue = params.get("iso");
                //  params.set("iso","1600");
                params.setColorEffect("none");
                params.set("scene-mode", "auto");
                params.setPictureFormat(ImageFormat.JPEG);
                params.setJpegQuality(70);
                params.setRotation(90);

                mCamera.setParameters(params);
                isParameterSet = true;
            } catch (Exception e) {

            }
            if (!isParameterSet) {
                Camera.Parameters params2 = mCamera.getParameters();
                params2.setPictureFormat(ImageFormat.JPEG);
                params2.setJpegQuality(70);
                params2.setRotation(90);

                mCamera.setParameters(params2);
            }

            setCameraDisplayOrientation(ActivityDistributorScouting.this, Camera.CameraInfo.CAMERA_FACING_BACK, mCamera);
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
        }

        capture = dialog.findViewById(R.id.button_capture);

        flashImage = dialog.findViewById(R.id.flashImage);
        flashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLighOn) {
                    // turn off flash
                    Camera.Parameters params = mCamera.getParameters();

                    if (mCamera == null || params == null) {
                        return;
                    }

                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(params);
                    flashImage.setImageResource(R.drawable.flash_off);
                    isLighOn = false;
                } else {
                    // turn on flash
                    Camera.Parameters params = mCamera.getParameters();

                    if (mCamera == null || params == null) {
                        return;
                    }

                    params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

                    flashImage.setImageResource(R.drawable.flash_on);
                    mCamera.setParameters(params);

                    isLighOn = true;
                }
            }
        });

        final Button cancleCamera = dialog.findViewById(R.id.cancleCamera);
        cancelCam = cancleCamera;
        cancleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                v.setEnabled(false);
                capture.setEnabled(false);
                cameraPreview.setEnabled(false);
                flashImage.setEnabled(false);

                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                isLighOn = false;
                dialog.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        capture.setOnClickListener(captrureListener);

        cameraPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Get the pointer ID
                Camera.Parameters params = mCamera.getParameters();
                int action = event.getAction();

                if (event.getPointerCount() > 1) {
                    // handle multi-touch events
                    if (action == MotionEvent.ACTION_POINTER_DOWN) {
                        mDist = getFingerSpacing(event);
                    } else if (action == MotionEvent.ACTION_MOVE
                            && params.isZoomSupported()) {
                        mCamera.cancelAutoFocus();
                        handleZoom(event, params);
                    }
                } else {
                    // handle single touch events
                    if (action == MotionEvent.ACTION_UP) {
                        handleFocus(event, params);
                    }
                }
                return true;
            }
        });

        dialog.show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;
    }

    private void handleZoom(MotionEvent event, Camera.Parameters params) {
        int maxZoom = params.getMaxZoom();
        int zoom = params.getZoom();
        float newDist = getFingerSpacing(event);
        if (newDist > mDist) {
            // zoom in
            if (zoom < maxZoom)
                zoom++;
        } else if (newDist < mDist) {
            // zoom out
            if (zoom > 0)
                zoom--;
        }
        mDist = newDist;
        params.setZoom(zoom);
        mCamera.setParameters(params);
    }
    /*
    public void setSavedImageToScrollView(Bitmap bitmap, String imageName, String valueOfKey, String clickedTagPhoto, boolean isSavedImage) {

        if (hmapCtgry_Imageposition != null && hmapCtgry_Imageposition.size() > 0) {
            if (hmapCtgry_Imageposition.containsKey(clickedTagPhoto)) {
                picAddPosition = hmapCtgry_Imageposition.get(clickedTagPhoto);
            } else {
                picAddPosition = 0;
            }
        } else {
            picAddPosition = 0;
            chkBox_Rtailer.setEnabled(false);
        }
        removePicPositin = picAddPosition;
        ArrayList<String> listClkdPic = new ArrayList<String>();
        if (hmapCtgryPhotoSection != null && hmapCtgryPhotoSection.containsKey(clickedTagPhoto)) {
            listClkdPic = hmapCtgryPhotoSection.get(clickedTagPhoto);
        }

        listClkdPic.add(imageName);
        hmapCtgryPhotoSection.put(clickedTagPhoto, listClkdPic);

        adapterImage.add(picAddPosition, bitmap, imageName + "^" + clickedTagPhoto);
        System.out.println("Picture Adapter" + picAddPosition);
        picAddPosition++;
        hmapCtgry_Imageposition.put(clickedTagPhoto, picAddPosition);
        if (!isSavedImage) {
            hmapPhotoDetailsForSaving.put(imageName, valueOfKey.split(Pattern.quote("~"))[0] + "^" + valueOfKey.split(Pattern.quote("~"))[1] + "^" + clickedTagPhoto);
        }


    }*/

    /*public boolean saveImageSection1() {
        boolean isPicSaved = false;
        dbengine.deletePicSectionImage(storeID, "1");
        if ((hmapPhotoDetailsForSaving != null) && (hmapPhotoDetailsForSaving.size() > 0)) {
            for (Map.Entry<String, String> entry : hmapPhotoDetailsForSaving.entrySet()) {
                String imageNameToSave = entry.getKey();
                String imagePath = entry.getValue().split(Pattern.quote("^"))[0];
                String dateTime = entry.getValue().split(Pattern.quote("^"))[1];
                String clkdTagPic = entry.getValue().split(Pattern.quote("^"))[2];
                //(String StoreID,String imageName,String imagePath,String ImageClicktime,String flgSctnPic,int Sstat)
                dbengine.savePicSectionImage(storeID, imageNameToSave, imagePath, dateTime, clkdTagPic, 1);
                isPicSaved = true;
            }
        }
        return isPicSaved;
    }*/

    public void handleFocus(MotionEvent event, Camera.Parameters params) {
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        List<String> supportedFocusModes = params.getSupportedFocusModes();
        if (supportedFocusModes != null
                && supportedFocusModes
                .contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    // currently set to auto-focus on single touch
                }
            });
        }
    }

    private float getFingerSpacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //make a new picture file
                File pictureFile = getOutputMediaFile();

                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                isLighOn = false;

                if (pictureFile == null) {
                    return;
                }
                try {
                    //write the file
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();

                    arrImageData.add(0, pictureFile);
                    arrImageData.add(1, pictureFile.getName());
                    dialog.dismiss();
                    if (pictureFile != null) {
                        File file = pictureFile;
                        System.out.println("File +++" + pictureFile);
                        imageName = pictureFile.getName();
                        Bitmap bmp = decodeSampledBitmapFromFile(file.getAbsolutePath(), 80, 80);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        uriSavedImage = Uri.fromFile(pictureFile);
                        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        byte[] byteArray = stream.toByteArray();

                        // Convert ByteArray to Bitmap::
                        //
                        long syncTIMESTAMP = System.currentTimeMillis();
                        Date dateobj = new Date(syncTIMESTAMP);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                        String clkdTime = df.format(dateobj);
                        //	String valueOfKey=imagButtonTag+"~"+tempId+"~"+file.getAbsolutePath()+"~"+clkdTime+"~"+"2";
//                        String valueOfKey = clickedTagPhoto + "~" + AddNewStore_DynamicSectionWise.selStoreID + "~" + uriSavedImage.toString() + "~" + clkdTime + "~" + "1";
                        //   helperDb.insertImageInfo(tempId,imagButtonTag, imageName, file.getAbsolutePath(), 2);
                        String valueOfKey = clickedTagPhoto + "~" + SpokeIDForImg + "~" + uriSavedImage.toString() + "~" + clkdTime + "~" + "1";

                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        setSavedImageToScrollView(bitmap, imageName, valueOfKey, clickedTagPhoto);
                        System.out.println("merch data..." + imageName + "~~" + valueOfKey + "~~" + clickedTagPhoto);
//                        if (isMerchndiseClick) {
//                            setSavedImageToScrollView(bitmap, imageName, valueOfKey, clickedTagPhoto);
//                        } else {
//                            setSavedImageByCatByPrice(bitmap, imageName, valueOfKey, clickedTagPhoto, ll_catOrPricePicClicked, true);
//                        }
                    }
//Show dialog here
//...
//Hide dialog here

                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }

                //refresh camera to continue preview--------------------------------------------------------------
                //	mPreview.refreshCamera(mCamera);
                //if want to release camera
                if (mCamera != null) {
                    mCamera.release();
                    mCamera = null;
                }
            }
        };
        return picture;
    }

    private void setSavedImageToScrollView(Bitmap bitmap, String imageName, String valueOfKey, String clickedTagPhoto) {
        if (hmapCtgry_Imageposition != null && hmapCtgry_Imageposition.size() > 0) {
            if (hmapCtgry_Imageposition.containsKey(clickedTagPhoto)) {
                picAddPosition = hmapCtgry_Imageposition.get(clickedTagPhoto);
                if (picAddPosition == -1) {
                    flgListEmpty = false;
                    picAddPosition = 0;
                }
            } else {
                picAddPosition = 0;
            }
        } else {
            picAddPosition = 0;
        }

        removePicPositin = picAddPosition;
        ArrayList<String> listClkdPic = new ArrayList<String>();
        if (hmapCtgryPhotoSection != null && hmapCtgryPhotoSection.containsKey(clickedTagPhoto)) {
            listClkdPic = hmapCtgryPhotoSection.get(clickedTagPhoto);
        }

        listClkdPic.add(imageName);
        hmapCtgryPhotoSection.put(clickedTagPhoto, listClkdPic);
        ImageAdapter adapterImage = hmapImageAdapter.get(clickedTagPhoto);
        adapterImage.add(picAddPosition, bitmap, imageName + "^" + clickedTagPhoto);
        System.out.println("REMOVE AND PIC ADD..." + removePicPositin + "__" + picAddPosition);
        System.out.println("Picture Adapter" + picAddPosition);
        picAddPosition++;
        hmapCtgry_Imageposition.put(clickedTagPhoto, picAddPosition);

        String photoPath = valueOfKey.split(Pattern.quote("~"))[2];
        String clickedDateTime = valueOfKey.split(Pattern.quote("~"))[3];

        //key- imagName
        //value- businessId^CatID^TypeID^PhotoPath^ClikcedDatetime^PhotoTypeFlag^StackNo

        hmapPhotoDetailsForSaving.put(imageName, clickedTagPhoto + "~" + photoPath + "~" + clickedDateTime);
        System.out.println("Hmap Photo..." + imageName + "^" + clickedTagPhoto + "^" + photoPath + "^" + clickedDateTime);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (!fnLati.equals("NA") && !fnLongi.equals("0.0")) {
            googleMap.clear();
            googleMap.setMyLocationEnabled(false);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(fnLati), Double.parseDouble(fnLongi)));
            Marker locationMarker = googleMap.addMarker(marker);
            locationMarker.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(fnLati), Double.parseDouble(fnLongi)), 15));

        } else {
            if (refreshCount == 2) {
                txt_rfrshCmnt.setText(getString(R.string.loc_not_found));
                btn_refresh.setVisibility(View.GONE);
            }

            googleMap.setMyLocationEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.zoomIn());
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    // marker.setTitle(tblSpokeProfile.getDescr().toString().trim());
                }
            });

        }

    }

    private boolean checkGPSAndNetwork() {
        boolean isGPSok = false;
        boolean isNWok = false;
        isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        boolean isWorking = true;

        if (!isGPSok) {
            isGPSok = false;

        }

        if (!isNWok) {
            isNWok = false;
        }

        System.out.println("0000 GPS & Network " + isGPSok + "--" + isNWok);

        if (!isGPSok && !isNWok) {
            isGPSok = false;
            isNWok = false;
            isWorking = false;

        }
        return isWorking;
    }

    @Override
    protected void onResume() {
        super.onResume();

      /*  if (!checkGPSAndNetwork()) {
            try {
                if (AppUtils.hasGPSDevice(this)) {
                    locationCount++;
                    isGpsModifyAsked = true;
                    AppUtils.showGPSSettingsAlert(this);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (isGpsModifyAsked) {
                isGpsModifyAsked = false;
            }
            if(flgNewScoutingOrOldDistributor==0 || tblPotentialDistributor.getFlgViewOrEdit()==1) {
                locationRetreivingGlobal = new LocationRetreivingGlobalDistributorScouting();
                locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(this, true, true, 20, 0);

            }
            locationCount++;
        }*/
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

    public void showSettingsAlert() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage(getText(R.string.genTermGPSDisablePleaseEnable));

        // On pressing Settings button
        alertDialog.setPositiveButton(R.string.AlertDialogOkButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
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
        String imageNameToDelVal = imageNameToDel.split(Pattern.quote("^"))[0];
        String tagPhoto = imageNameToDel.split(Pattern.quote("^"))[1];

        picAddPosition = hmapCtgry_Imageposition.get(tagPhoto);
        if (picAddPosition > 1) {
            removePicPositin = picAddPosition - 1;
        } else {
            removePicPositin = picAddPosition;
        }

        removePicPositin = removePicPositin - 1;
        picAddPosition = picAddPosition - 1;
        System.out.println("REMOVE AND PIC ADD DEL..." + removePicPositin + "__" + picAddPosition);
        hmapCtgry_Imageposition.put(tagPhoto, picAddPosition);
        //	String photoToBeDletedFromPath=dbengine.getPdaPhotoPath(imageNameToDel);

        // dbengine.updatePhotoValidation("0", imageNameToDel);

        ArrayList<String> listClkdPic = new ArrayList<String>();
        if (hmapCtgryPhotoSection != null && hmapCtgryPhotoSection.containsKey(tagPhoto)) {
            listClkdPic = hmapCtgryPhotoSection.get(tagPhoto);
        }

        if (listClkdPic.contains(imageNameToDelVal)) {
            listClkdPic.remove(imageNameToDelVal);
            ImageAdapter adapterImage = hmapImageAdapter.get(tagPhoto);
            adapterImage.remove(bmp);
            hmapCtgryPhotoSection.put(tagPhoto, listClkdPic);
            if (listClkdPic.size() < 1) {
                hmapCtgryPhotoSection.remove(tagPhoto);
            }
        }
        if (listClkdPic.size() == 0) {
            flgListEmpty = true;
        }
        hmapPhotoDetailsForSaving.remove(imageNameToDelVal);
        //  String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+imageNameToDel;
        String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageNameToDelVal;
        mDataSource.validateAndDelPic(2, imageNameToDelVal, SpokeIDForImg, "0");
        File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                callBroadCast();
            } else {
            }
        }
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


    public void fnWarningMsgInCasLatLong0() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(getText(R.string.genTermInformation));
        alertDialog.setIcon(R.drawable.info_icon);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
//            alertDialog.setMessage("Outlet <<"+StoreName+">> has the same owner number as the current store being added. If you are sure  both stores have the same owner OR number now entered is correct, please click OK to continue OR ELSE click cancel to correct the owner number.");
        alertDialog.setMessage("Not able to capture the location properly! , but you can proceed or may try later.");

        // On pressing Settings button
        alertDialog.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }


    public void showStoreAlreadyExistsWithSameMobileNumber(String StoreName, String storeNumber) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(getText(R.string.genTermInformation));
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
//            alertDialog.setMessage("Outlet <<"+StoreName+">> has the same owner number as the current store being added. If you are sure  both stores have the same owner OR number now entered is correct, please click OK to continue OR ELSE click cancel to correct the owner number.");
        alertDialog.setMessage("Duplicate Contact number exists for <<" + StoreName + ">>.Please correct it before proceeding");

        // On pressing Settings button
        alertDialog.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
     /*   alertDialog.setNegativeButton(getText(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });*/
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationRetrievedDistributorScouting(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress, String District) {
        fnCreateRetailerSectionForFeedBack();
        fnLoadVechileListToRecyclerView();
        fnLoadCompetitorListToRecyclerView();
        fnLoadBrandListToRecyclerView();


        populateDistributorScoutingPersonData();
        populateDistributorScoutingRetailerDetailsFromServer();
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


        manager = getFragmentManager();
        mapFrag = (MapFragment) manager.findFragmentById(
                R.id.map);
        mapFrag.getView().setVisibility(View.VISIBLE);
        mapFrag.getMapAsync(ActivityDistributorScouting.this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.show(mapFrag);


        //mDataSource.open();
        mDataSource.deleteLocationTable();

        if (address != null && !address.equals("NA"))
            et_Address.setText(address);
        else
            et_Address.setText("");

        if (city != null && !city.equals("NA"))
            et_Village.setText(city);
        else
            et_Village.setText("");

        if (District != null && !District.equals("NA"))
            et_District.setText(District);
        else
            et_District.setText("");

        if (pincode != null && !pincode.equals("NA"))
            et_Pincode.setText(pincode);
        else
            et_Pincode.setText("");


        if (state != null && !state.equals("NA"))
            et_State.setText(state);
        else
            et_State.setText("");
    }




    private class bgTasker extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            try {
                SyncNow();
            } catch (Exception e) {
                String esdsd = e.getMessage();
            } finally {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog2 = ProgressDialog.show(ActivityDistributorScouting.this, getText(R.string.PleaseWaitMsg), getText(R.string.genTermProcessingRequest), true);
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
            showAlertSingleButtonInfoSubmissionSuceesful("Data Submitted successfully.");
        }
    }

    public void saveDataToDatabase() {

      /*  mDataSource.savetblSuplierMappingData(""+tblSpokeProfile.getNodeID(),""+tblSpokeProfile.getNodeType(),flgGSTCapture,flgGSTCompliance,
                GstUserInput,address,pincode,city,state,fnLati,fnLongi,
                ""+fnAccuracy,"1",fnAccurateProvider,AllProvidersLocation,address,
                GPSLocationLatitude,GPSLocationLongitude,GPSLocationAccuracy,GPSAddressOnLoad,
                NetworkLocationLatitude,NetworkLocationLongitude,NetworkLocationAccuracy,NetworkAddressOnLoad,
                FusedLocationLatitude,FusedLocationLongitude,FusedLocationAccuracy,FusedAddressOnLoad,
                FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart,flgLocationConfirm,addressUserInput,cityUserInput,pinCodeUserInput,stateCodeUserInput,contactNumberUserInput);
*/


        try {

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
        for (int i = 0; i < 3; i++) {
            View view = inflater.inflate(R.layout.distributor_scouting_retailer_detailes_rows, null);
            hashMapRetailerSectionNoAndFeedBackStatus.put(i,0);
            LinearLayout ll_RetailerInnerSection=view.findViewById(R.id.ll_RetailerInnerSection);
            ll_RetailerInnerSection.setTag("llRetailerInnerSection"+i);

            EditText txt_RetailerName=view.findViewById(R.id.txt_RetailerName);
            txt_RetailerName.setTag("etRetailerName_"+i);

            EditText txt_RetailerAddress=view.findViewById(R.id.txt_RetailerAddress);
            txt_RetailerAddress.setTag("etRetailerAddress_"+i);

            EditText et_RetailerContactNumber=view.findViewById(R.id.et_RetailerContactNumber);
            et_RetailerContactNumber.setTag("etRetailerContactNumber_"+i);

            EditText txt_RetailerComment=view.findViewById(R.id.txt_RetailerComment);
            txt_RetailerComment.setTag("etRetailerComment_"+i);

            RadioGroup rg_RetailerFeedBack=view.findViewById(R.id.rg_RetailerFeedBack);
            rg_RetailerFeedBack.setTag("rgRetailerFeedBack_"+i);
            if(tblPotentialDistributor.getFlgViewOrEdit()==2)
            {
                txt_RetailerName.setEnabled(false);
                txt_RetailerAddress.setEnabled(false);
                txt_RetailerComment.setEnabled(false);
                rg_RetailerFeedBack.getChildAt(0).setEnabled(false);
                rg_RetailerFeedBack.getChildAt(1).setEnabled(false);
                et_RetailerContactNumber.setEnabled(false);
            }
            if(tblPotentialDistributor.getFlgViewOrEdit()==0 || tblPotentialDistributor.getFlgViewOrEdit()==1) {

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
            }
            view.setTag("llRetaailerSectionNo_"+i);
            ll_MarketReputationCheckWholeSection.addView(view);
        }

    }

    public void fnLoadVechileListToRecyclerView() {
        tblPotentialDistributorVehicleMasterLists = new ArrayList<TblPotentialDistributorVehicleMasterList>();
        tblPotentialDistributorVehicleMasterLists.clear();
        tblPotentialDistributorVehicleMasterLists=mDataSource.fnGetPotentialDistributorVechileList(tblPotentialDistributor.getNodeID());
        if(tblPotentialDistributorVehicleMasterLists!=null && tblPotentialDistributorVehicleMasterLists.size()>0)
        {
            VehicleList_names = new String[tblPotentialDistributorVehicleMasterLists.size()];
            int index=0;
            for(TblPotentialDistributorVehicleMasterList tblPotentialDistributorVehicleMaster:tblPotentialDistributorVehicleMasterLists)
            {
                VehicleList_names[index] = tblPotentialDistributorVehicleMaster.getVehicleType().toString();
                index = index + 1;
            }
        }
        ArrayAdapter adapterRouteList = new ArrayAdapter(this, R.layout.spinner_item_route_store_selection, VehicleList_names);
        adapterRouteList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_VehicleList.setAdapter(adapterRouteList);
        if(tblPotentialDistributor.getFlgViewOrEdit()==2)
        {
            spinner_VehicleList.setEnabled(false);
        }
        spinner_VehicleList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                TblPotentialDistributorVehicleMasterList tblPotentialDistributorVehicleMaster=stream(tblPotentialDistributorVehicleMasterLists).where(p->p.getVehicleType().equals(arg0.getItemAtPosition(arg2).toString())).first();

                if(tblPotentialDistributorVehicleMaster.getVehicleTypeID()==0)
                {
                    SelectedVechileID=0;
                    SelectedVechileType="Select Vehicle";
                    et_VecCount.setVisibility(View.GONE);
                    tblPotentialDistributorVehicleMaster.setFlgEnable(0);
                }
                else
                {
                    SelectedVechileID=tblPotentialDistributorVehicleMaster.getVehicleTypeID();
                    SelectedVechileType=tblPotentialDistributorVehicleMaster.getVehicleType();
                    tblPotentialDistributorVehicleMaster.setFlgEnable(0);
                    et_VecCount.setVisibility(View.VISIBLE);
                }

                for(TblPotentialDistributorVehicleMasterList tblPotentialDistributorVehicleMasterListsLoop:tblPotentialDistributorVehicleMasterLists)
                {
                    if(tblPotentialDistributorVehicleMasterListsLoop.getVehicleTypeID()==tblPotentialDistributorVehicleMaster.getVehicleTypeID()) {
                        tblPotentialDistributorVehicleMasterListsLoop.setFlgEnable(1);

                    }
                    else
                    {
                        tblPotentialDistributorVehicleMasterListsLoop.setFlgEnable(0);
                        tblPotentialDistributorVehicleMasterListsLoop.setNoOfVechile("0");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       /* TblPotentialDistributorVehicleMasterList tblPotentialDistributorVehicleMaster1 = new TblPotentialDistributorVehicleMasterList();
        tblPotentialDistributorVehicleMaster1.setVehicleTypeID(1);
        tblPotentialDistributorVehicleMaster1.setVehicleType("Two Wheeler");
        tblPotentialDistributorVehicleMasterLists.add(tblPotentialDistributorVehicleMaster1);

        TblPotentialDistributorVehicleMasterList tblPotentialDistributorVehicleMaster2 = new TblPotentialDistributorVehicleMasterList();
        tblPotentialDistributorVehicleMaster2.setVehicleTypeID(2);
        tblPotentialDistributorVehicleMaster2.setVehicleType("Four Wheeler");
        tblPotentialDistributorVehicleMasterLists.add(tblPotentialDistributorVehicleMaster2);
        //LayoutInflater inflater = getLayoutInflater();
        if (tblPotentialDistributorVehicleMasterLists != null && tblPotentialDistributorVehicleMasterLists.size() > 0) {
            rvVechileList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            scoutingVechileAdapter = new ScoutingVechileAdapter(this, tblPotentialDistributorVehicleMasterLists,tblPotentialDistributor.getFlgViewOrEdit());
            rvVechileList.setAdapter(scoutingVechileAdapter);
        }*/
    }

    public void fnLoadCompetitorListToRecyclerView() {
        tblPotentialDistributorCompetitorCompanyMstrList = new ArrayList<TblPotentialDistributorCompetitorCompanyMstr>();
        tblPotentialDistributorCompetitorCompanyMstrList.clear();
        tblPotentialDistributorCompetitorCompanyMstrList = mDataSource.fnGetPotentialDistributorCompetitorCompanyMstr();
        List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDetailsList = new ArrayList<TblPotentialDistributorCompetitorCompanyMstr>();
        tblPotentialDistributorCompetitorCompanyDetailsList = mDataSource.fnGetPotentialDistributorCompetitorCompanyDetails(ScoutingDistributorID);

        List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDetailsListFromMaster = new ArrayList<TblPotentialDistributorCompetitorCompanyMstr>();
        tblPotentialDistributorCompetitorCompanyDetailsListFromMaster=stream(tblPotentialDistributorCompetitorCompanyDetailsList).where(p->p.getOtherCompanyCode()==0).toList();
        //From Master Table Code Starts Here

        if (tblPotentialDistributorCompetitorCompanyMstrList != null && tblPotentialDistributorCompetitorCompanyMstrList.size() > 0) {
            for (TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyMstr : tblPotentialDistributorCompetitorCompanyMstrList) {
                tblPotentialDistributorCompetitorCompanyMstr.setNodeID(ScoutingDistributorID);
                tblPotentialDistributorCompetitorCompanyMstr.setNodeType(ScoutingDistributorNodeType);
                if (tblPotentialDistributorCompetitorCompanyDetailsListFromMaster != null && tblPotentialDistributorCompetitorCompanyDetailsListFromMaster.size() > 0) {

                    List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDeatilsInner = stream(tblPotentialDistributorCompetitorCompanyDetailsListFromMaster).where(p -> p.getCompetitorCompanyID() == tblPotentialDistributorCompetitorCompanyMstr.getCompetitorCompanyID()).toList();
                    if (tblPotentialDistributorCompetitorCompanyDeatilsInner != null && tblPotentialDistributorCompetitorCompanyDeatilsInner.size() > 0) {
                        tblPotentialDistributorCompetitorCompanyMstr.setFlgCheck(1);
                        tblPotentialDistributorCompetitorCompanyMstr.setOtherCompanyCode(0);
                        tblPotentialDistributorCompetitorCompanyMstr.setOtherCompany(tblPotentialDistributorCompetitorCompanyDeatilsInner.get(0).getOtherCompany());
                        tblPotentialDistributorCompetitorCompanyMstr.setSalesQty(tblPotentialDistributorCompetitorCompanyDeatilsInner.get(0).getSalesQty());
                    }

/*
                    List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDeatilsInnerForOther = stream(tblPotentialDistributorCompetitorCompanyDetailsListFromMaster).where(p -> p.getCompetitorCompanyID() == tblPotentialDistributorCompetitorCompanyMstr.getCompetitorCompanyID() && tblPotentialDistributorCompetitorCompanyMstr.getFlgOther()==1).toList();
                    if (tblPotentialDistributorCompetitorCompanyDeatilsInnerForOther != null && tblPotentialDistributorCompetitorCompanyDeatilsInnerForOther.size() > 0) {

                        tblPotentialDistributorCompetitorCompanyDeatilsInnerForOther.get(0).setFlgOther(tblPotentialDistributorCompetitorCompanyMstr.getFlgOther());

                    }
                    if(tblPotentialDistributorCompetitorCompanyDeatilsInner != null && tblPotentialDistributorCompetitorCompanyDeatilsInner.size() > 0 && tblPotentialDistributorCompetitorCompanyDeatilsInner.get(0).getFlgOther()==1)
                    {
                        ll_OtherCompetitors.setVisibility(View.VISIBLE);
                        et_OtherCompetitors.setText(tblPotentialDistributorCompetitorCompanyDeatilsInner.get(0).getOtherCompany());
                    }*/
                }
            }


        }
        if (tblPotentialDistributorCompetitorCompanyMstrList != null && tblPotentialDistributorCompetitorCompanyMstrList.size() > 0) {
            rvListofCompetitors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            scoutingCompetitorAdapter = new ScoutingCompetitorAdapter(this, tblPotentialDistributorCompetitorCompanyMstrList,tblPotentialDistributor.getFlgViewOrEdit());
            rvListofCompetitors.setAdapter(scoutingCompetitorAdapter);
        }

        //From Non Master Table Code Ends Here


        List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster = new ArrayList<TblPotentialDistributorCompetitorCompanyMstr>();
        tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster=stream(tblPotentialDistributorCompetitorCompanyDetailsList).where(p->p.getOtherCompanyCode()!=1 && p.getCompetitorCompanyID()==0).toList();
        // totalCompanyNewCount=tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster.size();
        //From Master Table Code Starts Here

    /*    if (tblPotentialDistributorCompetitorCompanyMstrList != null && tblPotentialDistributorCompetitorCompanyMstrList.size() > 0) {
            for (TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyMstr : tblPotentialDistributorCompetitorCompanyMstrList) {
                tblPotentialDistributorCompetitorCompanyMstr.setNodeID(ScoutingDistributorID);
                tblPotentialDistributorCompetitorCompanyMstr.setNodeType(ScoutingDistributorNodeType);
                if (tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster != null && tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster.size() > 0) {

                    List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDeatilsInner = stream(tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster).where(p -> p.getCompetitorCompanyID() == tblPotentialDistributorCompetitorCompanyMstr.getCompetitorCompanyID()).toList();
                    if (tblPotentialDistributorCompetitorCompanyDeatilsInner != null && tblPotentialDistributorCompetitorCompanyDeatilsInner.size() > 0) {
                        tblPotentialDistributorCompetitorCompanyMstr.setFlgCheck(1);
                        tblPotentialDistributorCompetitorCompanyMstr.setCompanyType(1);

                        tblPotentialDistributorCompetitorCompanyMstr.setOtherBrand(tblPotentialDistributorCompetitorCompanyDeatilsInner.get(0).getOtherCompany());
                    }


                   *//* List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDeatilsInnerForOther = stream(tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster).where(p -> p.getCompetitorCompanyID() == tblPotentialDistributorCompetitorCompanyMstr.getCompetitorCompanyID() && tblPotentialDistributorCompetitorCompanyMstr.getFlgOther()==1).toList();
                    if (tblPotentialDistributorCompetitorCompanyDeatilsInnerForOther != null && tblPotentialDistributorCompetitorCompanyDeatilsInnerForOther.size() > 0) {

                        tblPotentialDistributorCompetitorCompanyDeatilsInnerForOther.get(0).setFlgOther(tblPotentialDistributorCompetitorCompanyMstr.getFlgOther());

                    }
                    if(tblPotentialDistributorCompetitorCompanyDeatilsInner != null && tblPotentialDistributorCompetitorCompanyDeatilsInner.size() > 0 && tblPotentialDistributorCompetitorCompanyDeatilsInner.get(0).getFlgOther()==1)
                    {
                        ll_OtherCompetitors.setVisibility(View.VISIBLE);
                        et_OtherCompetitors.setText(tblPotentialDistributorCompetitorCompanyDeatilsInner.get(0).getOtherCompany());
                    }*//*
                }
            }


        }
*/
        initAddCompanyDefault(tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster);
        //From Not Master Records Code Ends Here
    }


    public void initAddCompanyDefault( List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster)
    {
        if(tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster!=null && tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster.size()>0) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyDetailsListNotFromMasterRecord : tblPotentialDistributorCompetitorCompanyDetailsListNotFromMaster) {
                View view = inflater.inflate(R.layout.distributor_scouting_new_company_rows, null);

                EditText et_NewCompanyName = view.findViewById(R.id.et_NewCompanyName);
                et_NewCompanyName.setTag("etNewCompanyName_" +  totalCompanyNewCount);

                EditText et_Sales = view.findViewById(R.id.et_Sales);
                et_Sales.setTag("etSales_" + totalCompanyNewCount);

                et_NewCompanyName.setText(tblPotentialDistributorCompetitorCompanyDetailsListNotFromMasterRecord.getCompetitorCompany());
                et_Sales.setText(tblPotentialDistributorCompetitorCompanyDetailsListNotFromMasterRecord.getSalesQty());
                if (tblPotentialDistributor.getFlgViewOrEdit() == 2) {
                    et_NewCompanyName.setEnabled(false);
                    et_Sales.setEnabled(false);
                }
                view.setTag("llNewCompanySectionNo_" + totalCompanyNewCount);
                ll_OtherCompanyWholeSection.addView(view);
                totalCompanyNewCount++;

            }
        }
        img_AddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAddNewCompany();
            }
        });
    }
    public void initAddNewCompany( )
    {


        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.distributor_scouting_new_company_rows, null);

        EditText et_NewCompanyName = view.findViewById(R.id.et_NewCompanyName);
        et_NewCompanyName.setTag("etNewCompanyName_" + totalCompanyNewCount);

        EditText et_Sales = view.findViewById(R.id.et_Sales);
        et_Sales.setTag("etSales_" + totalCompanyNewCount);

        view.setTag("llNewCompanySectionNo_" + totalCompanyNewCount);
        ll_OtherCompanyWholeSection.addView(view);

        totalCompanyNewCount++;
    }




    public void fnLoadBrandListToRecyclerView() {

        tblPotentialDistributorCompetitorBrandMstrList = new ArrayList<TblPotentialDistributorCompetitorBrandMstr>();
        tblPotentialDistributorCompetitorBrandMstrList.clear();


        tblPotentialDistributorCompetitorBrandMstrList = mDataSource.fnGetPotentialDistributorCompetitorBrandMstr();
        List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandDetailList = new ArrayList<TblPotentialDistributorCompetitorBrandMstr>();
        tblPotentialDistributorCompetitorBrandDetailList = mDataSource.fnGetPotentialDistributorCompetitorBrandDetails(ScoutingDistributorID);

        List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandDetailListFromMaster = new ArrayList<TblPotentialDistributorCompetitorBrandMstr>();
        tblPotentialDistributorCompetitorBrandDetailListFromMaster=stream(tblPotentialDistributorCompetitorBrandDetailList).where(p->p.getOtherBrandCode()==0).toList();

        if (tblPotentialDistributorCompetitorBrandMstrList != null && tblPotentialDistributorCompetitorBrandMstrList.size() > 0) {
            for (TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandMstr : tblPotentialDistributorCompetitorBrandMstrList) {
                tblPotentialDistributorCompetitorBrandMstr.setNodeID(ScoutingDistributorID);
                tblPotentialDistributorCompetitorBrandMstr.setNodeType(ScoutingDistributorNodeType);
                if (tblPotentialDistributorCompetitorBrandDetailListFromMaster != null && tblPotentialDistributorCompetitorBrandDetailListFromMaster.size() > 0) {

                    List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandDetailsInner = stream(tblPotentialDistributorCompetitorBrandDetailListFromMaster).where(p -> p.getCompetitorBrandID() == tblPotentialDistributorCompetitorBrandMstr.getCompetitorBrandID()).toList();
                    if (tblPotentialDistributorCompetitorBrandDetailsInner != null && tblPotentialDistributorCompetitorBrandDetailsInner.size() > 0) {
                        tblPotentialDistributorCompetitorBrandMstr.setFlgCheck(1);

                        tblPotentialDistributorCompetitorBrandMstr.setOtherBrand(tblPotentialDistributorCompetitorBrandDetailsInner.get(0).getOtherBrand());

                        tblPotentialDistributorCompetitorBrandMstr.setOtherBrandCode(0);
                        tblPotentialDistributorCompetitorBrandMstr.setSalesQty(tblPotentialDistributorCompetitorBrandDetailsInner.get(0).getSalesQty());
                    }

                  /*  List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandDetailsInnerForOther = stream(tblPotentialDistributorCompetitorBrandDetailListFromMaster).where(p -> p.getCompetitorBrandID() == tblPotentialDistributorCompetitorBrandMstr.getCompetitorBrandID() && tblPotentialDistributorCompetitorBrandMstr.getFlgOther()==1).toList();
                    if (tblPotentialDistributorCompetitorBrandDetailsInnerForOther != null && tblPotentialDistributorCompetitorBrandDetailsInnerForOther.size() > 0) {

                        tblPotentialDistributorCompetitorBrandDetailsInnerForOther.get(0).setFlgOther(tblPotentialDistributorCompetitorBrandMstr.getFlgOther());

                    }
                    if(tblPotentialDistributorCompetitorBrandDetailsInnerForOther != null && tblPotentialDistributorCompetitorBrandDetailsInnerForOther.size() > 0 && tblPotentialDistributorCompetitorBrandDetailsInnerForOther.get(0).getFlgOther()==1)
                    {
                        ll_OtherBrands.setVisibility(View.VISIBLE);
                        et_OtherBrands.setText(tblPotentialDistributorCompetitorBrandDetailsInner.get(0).getOtherBrand());
                    }*/
                }
            }


        }



        if (tblPotentialDistributorCompetitorBrandMstrList != null && tblPotentialDistributorCompetitorBrandMstrList.size() > 0) {
            rvListofBrands.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            scoutingBrandsAdapter = new ScoutingBrandsAdapter(this, tblPotentialDistributorCompetitorBrandMstrList,tblPotentialDistributor.getFlgViewOrEdit());
            rvListofBrands.setAdapter(scoutingBrandsAdapter);
        }

        List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandDetailListNotFromMaster = new ArrayList<TblPotentialDistributorCompetitorBrandMstr>();
        tblPotentialDistributorCompetitorBrandDetailListNotFromMaster=stream(tblPotentialDistributorCompetitorBrandDetailList).where(p->p.getOtherBrandCode()!=1 && p.getCompetitorBrandID()==0).toList();
        //totalBrandNewCount=tblPotentialDistributorCompetitorBrandDetailListNotFromMaster.size();

        initAddBrandDefault(tblPotentialDistributorCompetitorBrandDetailListNotFromMaster);
    }
    public void initAddBrandDefault( List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandDetailListNotFromMaster)
    {
        if(tblPotentialDistributorCompetitorBrandDetailListNotFromMaster!=null && tblPotentialDistributorCompetitorBrandDetailListNotFromMaster.size()>0) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandDetailListNotFromMasterRecord : tblPotentialDistributorCompetitorBrandDetailListNotFromMaster) {
                View view = inflater.inflate(R.layout.distributor_scouting_new_brand_rows, null);

                EditText et_NewBrandName = view.findViewById(R.id.et_NewBrandName);
                et_NewBrandName.setTag("etNewBrandName_" +  totalBrandNewCount);

                EditText et_BrandSales = view.findViewById(R.id.et_BrandSales);
                et_BrandSales.setTag("etBrandSales_" + totalBrandNewCount);

                et_NewBrandName.setText(tblPotentialDistributorCompetitorBrandDetailListNotFromMasterRecord.getOtherBrand());
                et_BrandSales.setText(tblPotentialDistributorCompetitorBrandDetailListNotFromMasterRecord.getSalesQty());
                if (tblPotentialDistributor.getFlgViewOrEdit() == 2) {
                    et_NewBrandName.setEnabled(false);
                    et_BrandSales.setEnabled(false);
                }
                view.setTag("llNewBrandSectionNo_" + totalBrandNewCount);
                ll_OtherBrandWholeSection.addView(view);
                totalBrandNewCount++;
            }
        }
        img_AddBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAddNewBrand();
            }
        });
    }
    public void initAddNewBrand( )
    {


        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.distributor_scouting_new_brand_rows, null);

        EditText et_NewBrandName = view.findViewById(R.id.et_NewBrandName);
        et_NewBrandName.setTag("etNewBrandName_" + totalBrandNewCount);

        EditText et_BrandSales = view.findViewById(R.id.et_BrandSales);
        et_BrandSales.setTag("etBrandSales_" + totalBrandNewCount);

        view.setTag("llNewBrandSectionNo_" + totalBrandNewCount);
        ll_OtherBrandWholeSection.addView(view);

        totalBrandNewCount++;
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String mon = MONTHS[monthOfYear];

        if (bool_NextFollowUpdate) {
            tv_NextFollowUp_date.setText(dayOfMonth + "/" + mon + "/" + year);
        }
        bool_NextFollowUpdate = false;


    }


    private DatePickerDialog createDialogWithoutDateField() {
        DatePickerDialog dpd = new DatePickerDialog();
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return dpd;
    }

    @Override
    public void fnSelectedMonthYear(int Year, int Month) {
       /* Month=Month-1;
        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String mon = MONTHS[Month];*/

        if (bool_WokingWithDistributorSince) {
            String monthText=""+Month;
            if(monthText.length()==1)
            {
                monthText="0"+monthText;
            }
            String MonthYearText=monthText + "" + Year;
            // tv_WorkingWithDistributorSince.setText(Month + "/" + Year);
            tv_WorkingWithDistributorSince.setText(MonthYearText);
        }
        bool_WokingWithDistributorSince = false;
    }


    public String genScoutingDistributorID() {
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String VisitStartTS = TimeUtils.getNetworkDateTime(ActivityDistributorScouting.this, TimeUtils.DATE_TIME_FORMAT);

        String cxz;
        cxz = UUID.randomUUID().toString();


        StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

        String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();

        String IMEIid = AppUtils.getToken(ActivityDistributorScouting.this);
        cxz = "ScoutingDistributorID" + "-" + IMEIid + "-" + cxz + "-" + VisitStartTS.replace(" ", "").replace(":", "").trim();


        return cxz;

    }
    @Override
    public void successRecord(int flgCalledFrom, List<TblServerEntryStatus> tblServerEntryStatusList) {
        if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
        {
            mProgressDialogReport.dismiss();
        }





        if(tblServerEntryStatusList!=null && tblServerEntryStatusList.size()>0)
        {
            TblServerEntryStatus tblServerEntryStatus=tblServerEntryStatusList.get(0);
            if(tblServerEntryStatus.getFlgStatus()==1)
            {
                ll_MarketReputationRetailerSection.setVisibility(View.VISIBLE);

                ll_sectionOtherThenRetailerFeedBack.setVisibility(View.GONE);
                location_layout.setVisibility(View.GONE);
                bt_Next.setVisibility(View.GONE);
                bt_Previous.setVisibility(View.VISIBLE);
                // bt_Submit.setVisibility(View.VISIBLE);//Orginal
                bt_Submit.setVisibility(View.GONE);
                bt_Save.setVisibility(View.VISIBLE);
                //showRecordsInsertedSuccessfully();
            }
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
        for(TblPotentialDistributorRetailersFeedBackDetails  tblPotentialDistributorRetailersFeedBackDetailsLocalRecord:tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving)
        {
            try {
                tblPotentialDistributorRetailersFeedBackDetailsLocalRecord.setPDAcode(AppUtils.getToken(mContext));
                tblPotentialDistributorRetailersFeedBackDetailsLocalRecord.setSstat(0);
                mDataSource.insert_tblDistRetAddressDetailSingleRetailer(tblPotentialDistributorRetailersFeedBackDetailsLocalRecord);
            }
            catch (Exception c)
            {
                String xczxczc="xzczc";
            }
        }

        if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
        {
            mProgressDialogReport.dismiss();
        }





        if(tblServerEntryStatusList!=null && tblServerEntryStatusList.size()>0)
        {
            TblServerEntryStatus tblServerEntryStatus=tblServerEntryStatusList.get(0);
            if(tblServerEntryStatus.getFlgStatus()==1)
            {


                showRecordsInsertedSuccessfully();
            }
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

    public void showRecordsInsertedSuccessfully() {
        android.app.AlertDialog.Builder alertDialogSyncError = new android.app.AlertDialog.Builder(ActivityDistributorScouting.this);
        alertDialogSyncError.setTitle(getText(R.string.genTermInformation));
        alertDialogSyncError.setCancelable(false);

        alertDialogSyncError.setMessage("Submitted Successfully");


        alertDialogSyncError.setNeutralButton(getText(R.string.AlertDialogOkButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        Intent intent = new Intent(ActivityDistributorScouting.this, SocutingDistributorSelection.class);

                        startActivity(intent);
                        finish();


                    }
                });
        alertDialogSyncError.setIcon(R.drawable.info_icon);

        android.app.AlertDialog alert = alertDialogSyncError.create();
        alert.show();
        // alertDialogLowbatt.show();
    }

    public void showRecordsInsertedError() {
        android.app.AlertDialog.Builder alertDialogSyncError = new android.app.AlertDialog.Builder(ActivityDistributorScouting.this);
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
        if(tblPotentialDistributorCompetitorCompanyMstr.getFlgOther()==1 && tblPotentialDistributorCompetitorCompanyMstr.getFlgCheck()==1)
        {
            ll_OtherCompetitors.setVisibility(View.VISIBLE);
        }
        if(tblPotentialDistributorCompetitorCompanyMstr.getFlgOther()==1 && tblPotentialDistributorCompetitorCompanyMstr.getFlgCheck()==0)
        {
            ll_OtherCompetitors.setVisibility(View.GONE);
            et_OtherCompetitors.setText("");

        }
    }

    @Override
    public void showBrandOtherBox(TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandMstr) {
        if(tblPotentialDistributorCompetitorBrandMstr.getFlgOther()==1 && tblPotentialDistributorCompetitorBrandMstr.getFlgCheck()==1)
        {
            ll_OtherBrands.setVisibility(View.VISIBLE);
        }
        if(tblPotentialDistributorCompetitorBrandMstr.getFlgOther()==1 && tblPotentialDistributorCompetitorBrandMstr.getFlgCheck()==0)
        {
            ll_OtherBrands.setVisibility(View.GONE);
            et_OtherBrands.setText("");

        }
    }


}