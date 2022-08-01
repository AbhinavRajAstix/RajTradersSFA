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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allana.truetime.TimeUtils;
import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;

import com.astix.rajtraderssfasales.BaseActivity;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.ImageTypeInterface;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceActivityScoutingShowHideOthersBox;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceServerSuccessEntry;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceServerSuccessRetailerFeedBackEntry;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceYearMonth;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.PhotoInterface;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.PotentialDistributorImageTable;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributor;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorCompetitorBrandMstr;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorCompetitorCompanyMstr;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorRetailersFeedBackDetails;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorVehicleMasterList;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblServerEntryStatus;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.PhotoAdapter;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.PhotoAdapterCheque;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.PhotoAdapterGST;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.PhotoAdapterPANOfFirm;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.PhotoAdapterPANPANPatner2;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.PhotoAdapterPANPartnerDeed;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.PhotoAdapterPANPatner1;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.PhotoAdapterProprietorPAN;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.ScoutingBrandsAdapter;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.ScoutingCompetitorAdapter;
import com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter.ScoutingVechileAdapter;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.UpdateExisitingDistributor.ActivityDistributorUpdation;
import com.astix.rajtraderssfasales.camera.CameraPreview;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.location.LocationInterfaceDistributorScouting;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobalDistributorScouting;
import com.astix.rajtraderssfasales.model.TblSupplierMstrList;
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

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.astix.rajtraderssfasales.camera.CameraUtils.hasCamera;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityDistributorAppointment extends BaseActivity implements InterfaceYearMonth, LocationInterfaceDistributorScouting, OnMapReadyCallback, PhotoInterface, ImageTypeInterface, DatePickerDialog.OnDateSetListener, InterfaceServerSuccessEntry, InterfaceServerSuccessRetailerFeedBackEntry, InterfaceActivityScoutingShowHideOthersBox {


    /**
     * Constant used in the location settings dialog.
     */
    SyncImageData task1;
    public int IMGsyOK = 0;
    private String FilePathStrings;
    public String fnameIMG;

    public String UploadingImageName;

    private File[] listFile;
    public  File fileintial;
    int serverResponseCode = 0;

    PotentialDistributorImageTable imageTable;
    List<PotentialDistributorImageTable>imageTableList =new ArrayList<PotentialDistributorImageTable>();
    List<Integer> flg_IDs = new ArrayList<>();
    int totalCompanyNewCount=0;
    int totalBrandNewCount=0;
    ProgressDialog mProgressDialogReport;
    int flgRetailerFeedBackStatus = 0;
    int flgFinalSubmit = 0;
    HashMap<Integer,Integer> hashMapRetailerSectionNoAndFeedBackStatus=new HashMap<Integer,Integer>();
    public int SelectedVechileID=0;
    public String SelectedVechileType="Select Vehicle";
    public int SelectedOldDistributorID=0;
    public String SelectedOldDistributorName="Select Old Distributor";
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



    String addressGodownUserInput = "";
    String cityGodownUserInput = "";
    String pinGodownCodeUserInput = "";
    String stateGodownCodeUserInput = "";
    String districtGodownUserInput = "";

    public String fnLati = "0";
    public String fnLongi = "0";
    public Double fnAccuracy = 0.0;
    String flgGSTCapture = "0", flgGSTCompliance = "0";
    public String address, pincode, city, state, latitudeToSave, longitudeToSave, accuracyToSave;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static String imei = "";

    public int ScoutingDistributorNodeType = 180;

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




    @BindView(R.id.spinner_VehicleList)
    Spinner spinner_VehicleList;

    @BindView(R.id.spinner_OldDistributor)
    Spinner spinner_OldDistributor;

    @BindView(R.id.et_VecCount)
    EditText et_VecCount;

    String[] VehicleList_names = null;
    String[] DistributorList_names = null;


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


    @BindView(R.id.ll_PANOfFirm_num)
    LinearLayout ll_PANOfFirm_num;

    @BindView(R.id.ll_ProprietorPAN_num)
    LinearLayout ll_ProprietorPAN_num;

    @BindView(R.id.ll_PANPartner1_num)
    LinearLayout ll_PANPartner1_num;

    @BindView(R.id.ll_PANPartner2_num)
    LinearLayout ll_PANPartner2_num;

    @BindView(R.id.ll_PartnerDeed_num)
    LinearLayout ll_PartnerDeed_num;


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

    @BindView(R.id.et_Address)
    EditText et_Address;

    @BindView(R.id.etPinCode)
    EditText et_Pincode;
    @BindView(R.id.et_Village)
    EditText et_Village;
    @BindView(R.id.et_state)
    EditText et_State;

    @BindView(R.id.et_District)
    EditText et_District;


    @BindView(R.id.cbGodownAddressSameAsOffice)
    CheckBox cbGodownAddressSameAsOffice;
    int flgGodownAddressSameAsOffice=-1;

    @BindView(R.id.et_GodwonAddress)
    EditText et_GodwonAddress;

    @BindView(R.id.etGodwonPinCode)
    EditText etGodwonPinCode;
    @BindView(R.id.et_GodwonVillage)
    EditText et_GodwonVillage;
    @BindView(R.id.et_Godwonstate)
    EditText et_Godwonstate;

    @BindView(R.id.et_GodwonDistrict)
    EditText et_GodwonDistrict;


    Calendar calendar;
    int Year, Month, Day;
    boolean bool_NextFollowUpdate = false;

    boolean bool_WokingWithDistributorSince = false;

    DatePickerDialog datePickerDialog;
    RecyclerView rvVechileList, rvListofCompetitors, rvListofBrands;

    ScoutingVechileAdapter scoutingVechileAdapter;
    ScoutingCompetitorAdapter scoutingCompetitorAdapter;
    ScoutingBrandsAdapter scoutingBrandsAdapter;


    int flgIntrestedInDistributorShip = -1;

    String cityID = "NA";
    String StateID = "NA";
    String MapAddress = "NA";
    String MapPincode = "NA";
    String MapCity = "NA";
    String MapState = "NA";
    int countSubmitClicked = 0;

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


    @BindView(R.id.ll_OtherCompanyWholeSection)
    LinearLayout ll_OtherCompanyWholeSection;


    @BindView(R.id.img_AddBrand)
    ImageView img_AddBrand;


    @BindView(R.id.ll_OtherBrandWholeSection)
    LinearLayout ll_OtherBrandWholeSection;


    @BindView(R.id.rg_DistributorReplacement)
    RadioGroup rg_DistributorReplacement;

    @BindView(R.id.ll_FinalSettlementDone)
    LinearLayout ll_FinalSettlementDone;

    @BindView(R.id.ll_OldDistributorDetails)
    LinearLayout ll_OldDistributorDetails;

    @BindView(R.id.rg_FinalSettlementDone)
    RadioGroup rg_FinalSettlementDone;
    int flgDistributorReplacement = -1;
    int flgFinalSettlementDone = -1;

    @BindView(R.id.rg_LocationFormfill)
    RadioGroup rg_LocationFormfill;
    int flgLocationFormfill = -1;



    @BindView(R.id.ll_DistributorStockiest)
    LinearLayout ll_DistributorStockiest;

    @BindView(R.id.rg_DistributorStockiest)
    RadioGroup rg_DistributorStockiest;
    int flgDistributorStockiest = -1;

    @BindView(R.id.ll_OldNewLocation)
    LinearLayout ll_OldNewLocation;
    @BindView(R.id.rg_OldOrNewLocation)
    RadioGroup rg_OldOrNewLocation;
    int flgOldOrNewLocation = -1;

    @BindView(R.id.ll_TownDistributorSubD)
    LinearLayout ll_TownDistributorSubD;
    @BindView(R.id.rg_TownDistributorSubD)
    RadioGroup rg_TownDistributorSubD;
    int flgTownDistributorSubD = -1;

    @BindView(R.id.et_PANNoOfFirm)
    EditText et_PANNoOfFirm;

    @BindView(R.id.rg_FirmType)
    RadioGroup rg_FirmType;
    int flgFirmType = -1;

    /* @BindView(R.id.ll_WholeSectionPANOfProprietor)
     LinearLayout ll_WholeSectionPANOfProprietor;
 */
    @BindView(R.id.et_PANNoOfProprietor)
    EditText et_PANNoOfProprietor;

   /* @BindView(R.id.ll_WholeSectionPANOfPartner1)
    LinearLayout ll_WholeSectionPANOfPartner1;*/

    @BindView(R.id.et_PANNoOfPartner1)
    EditText et_PANNoOfPartner1;

   /* @BindView(R.id.ll_WholeSectionPANOfPartner2)
    LinearLayout ll_WholeSectionPANOfPartner2;*/

    @BindView(R.id.et_PANNoOfPartner2)
    EditText et_PANNoOfPartner2;


    @BindView(R.id.et_PartnerDeed)
    EditText et_PartnerDeed;


    @BindView(R.id.et_BankAccountNo)
    EditText et_BankAccountNo;

    @BindView(R.id.et_BankIFSCCode)
    EditText et_BankIFSCCode;

    @BindView(R.id.et_BankAddress)
    EditText et_BankAddress;

    @BindView(R.id.et_BusinessExpectedTons)
    EditText et_BusinessExpectedTons;

    @BindView(R.id.et_IdealGodownSpaceRequired)
    EditText et_IdealGodownSpaceRequired;

    @BindView(R.id.et_GodownSpaceAgreedUpon)
    EditText et_GodownSpaceAgreedUpon;

    @BindView(R.id.et_InvestmentAgreedUpon)
    EditText et_InvestmentAgreedUpon;

    @BindView(R.id.et_IdealROIAsPerNorms)
    EditText et_IdealROIAsPerNorms;

    @BindView(R.id.et_ExpectedROIByDistributor)
    EditText et_ExpectedROIByDistributor;



    @BindView(R.id.ll_cheque_num)
    LinearLayout ll_cheque_num;

    @BindView(R.id.et_chequeno)
    EditText et_chequeno;

    @BindView(R.id.ll_ProprietorPANOverAllSection)
    LinearLayout ll_ProprietorPANOverAllSection;


    @BindView(R.id.ll_PANPatner1OverAllSection)
    LinearLayout ll_PANPatner1OverAllSection;

    @BindView(R.id.ll_PANPatner2OverAllSection)
    LinearLayout ll_PANPatner2OverAllSection;

    @BindView(R.id.ll_PartnerDeedOverAllSection)
    LinearLayout ll_PartnerDeedOverAllSection;


    private String imageName;
    private Uri uriSavedImage;
    String flgPhototype = "";
    LinkedHashMap<String, List<String>> photo_saving = new LinkedHashMap<>();

    @BindView(R.id.cheque_photo_list)
    RecyclerView cheque_photo_list;

    @BindView(R.id.btn_chequePhoto)
    Button btn_chequePhoto;
    String flgPhotoButton="1";
    List<String> cheque_photo = new ArrayList<>();

    LinkedHashMap<String, List<String>> photo_details_cheque = new LinkedHashMap<>();






    @BindView(R.id.distributorCopy_photo_list)
    RecyclerView distributorCopy_photo_list;

    @BindView(R.id.btn_Distributor_photo)
    Button btn_DistributorPhoto;
    String flgDistributorPhotoButton="1";
    List<String> Distributor_photo = new ArrayList<>();

    LinkedHashMap<String, List<String>> photo_details_distributor = new LinkedHashMap<>();




    @BindView(R.id.selfiwithdistributorCopy_photo_list)
    RecyclerView selfiwithdistributorCopy_photo_list;

    @BindView(R.id.btn_selfiwithDistributor_photo)
    Button btn_selfiwithDistributor_photo;
    String flgSelfiwithdistributorPhotoButton="1";
    List<String> Selfiwithdistributor_photo = new ArrayList<>();

    LinkedHashMap<String, List<String>> photo_details_selfiwithdistributor = new LinkedHashMap<>();





    @BindView(R.id.rg_ID_cheque)
    RadioGroup rg_ID_cheque;
    int flgHasCheque=-1;




    @BindView(R.id.gstCopy_photo_list)
    RecyclerView gstCopy_photo_list;

    @BindView(R.id.btn_gstCopy_photo)
    Button btn_gstCopy_photo;

    @BindView(R.id.btn_PANNoOfFirm_photo)
    Button btn_PANNoOfFirm_photo;


    List<String> gstCopy_photo = new ArrayList<>();

    LinkedHashMap<String, List<String>> photo_details_gstCopy_photo = new LinkedHashMap<>();


    @BindView(R.id.rg_ID_gstCopy)
    RadioGroup rg_ID_gstCopy;
    int flgGstCopy_photo=-1 ;


    @BindView(R.id.rg_ID_PANOfFirm)
    RadioGroup rg_ID_PANOfFirm;
    int flgPANOfFirm =-1;

    @BindView(R.id.PANOfFirm_photo_list)
    RecyclerView PANOfFirm_photo_list;


    @BindView(R.id.ll_ProprietorPANPhoto)
    LinearLayout ll_ProprietorPANPhoto;

    @BindView(R.id.ProprietorPANPhoto_photo_list)
    RecyclerView ProprietorPANPhoto_photo_list;

    @BindView(R.id.btn_ProprietorPANPhoto)
    Button btn_ProprietorPANPhoto;

    List<String> ProprietorPANPhoto_photo = new ArrayList<>();

    LinkedHashMap<String, List<String>> photo_details_ProprietorPANPhoto = new LinkedHashMap<>();


    @BindView(R.id.rg_ID_ProprietorPANPhoto)
    RadioGroup rg_ID_ProprietorPANPhoto;
    int flgProprietorPANPhoto=-1 ;



    @BindView(R.id.ll_PanPhotoPartner1)
    LinearLayout ll_PanPhotoPartner1;

    @BindView(R.id.PanPhotoPartner1_photo_list)
    RecyclerView PanPhotoPartner1_photo_list;

    @BindView(R.id.btn_PanPhotoPartner1)
    Button btn_PanPhotoPartner1;

    List<String> PanPhotoPartner1_photo = new ArrayList<>();

    LinkedHashMap<String, List<String>> photo_details_PanPhotoPartner1 = new LinkedHashMap<>();


    @BindView(R.id.rg_ID_PanPhotoPartner1)
    RadioGroup rg_ID_PanPhotoPartner1;
    int flgPanPhotoPartner1=-1 ;

    @BindView(R.id.ll_PanPhotoPartner2)
    LinearLayout ll_PanPhotoPartner2;

    @BindView(R.id.PanPhotoPartner2_photo_list)
    RecyclerView PanPhotoPartner2_photo_list;

    @BindView(R.id.rg_ID_PanPhotoPartner2)
    RadioGroup rg_ID_PanPhotoPartner2;

    @BindView(R.id.btn_PanPhotoPartner2)
    Button btn_PanPhotoPartner2;

    @BindView(R.id.btn_PartnerDeed)
    Button btn_PartnerDeed;


    int flgPanPhotoPartner2=-1 ;
    List<String> PanPhotoPartner2_photo = new ArrayList<>();

    LinkedHashMap<String, List<String>> photo_details_PanPhotoPartner2 = new LinkedHashMap<>();


    @BindView(R.id.ll_PartnerDeed)
    LinearLayout ll_PartnerDeed;

    @BindView(R.id.PartnerDeed_photo_list)
    RecyclerView PartnerDeed_photo_list;



    List<String> PartnerDeed_photo = new ArrayList<>();

    LinkedHashMap<String, List<String>> photo_details_PartnerDeed = new LinkedHashMap<>();

    List<String> PANNoOfFirm_photo = new ArrayList<>();

    LinkedHashMap<String, List<String>> photo_details_PANNoOfFirm = new LinkedHashMap<>();


    @BindView(R.id.rg_ID_PartnerDeed)
    RadioGroup rg_ID_PartnerDeed;
    int flgPartnerDeed=-1 ;

    @BindView(R.id.et_gstno)
    EditText et_gstno;


    LinearLayout ll_overAllSignatureSection,ll_ASMSignatureSection,linearLayoutASMSign,ll_TSIignatureSection,linearLayoutTSISign,mContentASM,mContentTSI,ll_DistributorSignatureSection,linearLayoutDistributorSign,mContentDistributor;
    RelativeLayout rl_ASMSign_layout,rl_TSISign_layout,rl_DistributorSign_layout;
    Button clearASMSign,getsignASM,clearTSISign,getsignTSI,cancelASMSign,cancelTSISign,clearDistributorSign,getsignDistributor,cancelDistributorSign;
    ImageView transparent_imageASM,transparent_imageTSI,transparent_imageDistributor;
    File file;
    View view;
    View viewASM;
    View viewTSI;
    View viewDistribuotr;
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.ImagesFolder + "/";
    String timeStamp = new SimpleDateFormat("yyyyMMMdd_HHmmss", Locale.ENGLISH).format(new Date());
    signatureASM mSignature;
    signatureTSI mSignatureTSI;
    signatureDistributor mSignatureDistributor;
    Bitmap bitmap;
    Bitmap bitmapTSI;
    Bitmap bitmapDistributor;
    boolean signOrNotASM = false;
    boolean signOrNotTSI = false;
    boolean signOrNotDistributor = false;
    String pic_nameASMSign="";
    String ImgPathASMSign="";
    String pic_nameTSISign="";
    String ImgPathTSISign="";
    String pic_nameDistributorSign="";
    String ImgPathDistributorSign="";

    private Button capture, cancelCam;
    private float mDist = 0;

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

    private List<TblSupplierMstrList> tblSupplierMstrLists;

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
        setContentView(R.layout.activity_distributor_appointment);
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
            Intent intent=new Intent(ActivityDistributorAppointment.this, SocutingDistributorSelection.class);
            startActivity(intent);
            finish();
            // finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

        et_gstno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!et_gstno.getText().toString().isEmpty()) {
                        if (et_gstno.getText().toString().length() != 15) {
                            showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                            return;
                        }
                        String firstTwoStr = et_gstno.getText().toString().substring(0, 2);
                        Pattern p = Pattern.compile("[0-9]{2}");
                        Matcher m = p.matcher(firstTwoStr);
                        if (!m.find() || !m.group().equals(firstTwoStr)) {
                            showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
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
                datePickerDialog = DatePickerDialog.newInstance(ActivityDistributorAppointment.this, Year, Month, Day + 1);

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
                datePickerDialog = DatePickerDialog.newInstance(ActivityDistributorAppointment.this, Year, Month, Day + 1);

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

      /*  scroll_view.setOnTouchListener(new View.OnTouchListener() {
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


        rg_DistributorReplacement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_DistributorReplacement_No:
                        flgDistributorReplacement = 0;
                        ll_FinalSettlementDone.setVisibility(View.GONE);
                        ll_OldDistributorDetails.setVisibility(View.GONE);
                        ll_DistributorStockiest.setVisibility(View.GONE);
                        ll_OldNewLocation.setVisibility(View.GONE);
                        ll_TownDistributorSubD.setVisibility(View.GONE);
                        break;

                    case R.id.rb_DistributorReplacement_Yes:
                        flgDistributorReplacement = 1;
                        ll_FinalSettlementDone.setVisibility(View.VISIBLE);
                        ll_OldDistributorDetails.setVisibility(View.VISIBLE);
                        ll_DistributorStockiest.setVisibility(View.VISIBLE);
                        ll_OldNewLocation.setVisibility(View.VISIBLE);
                        ll_TownDistributorSubD.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        rg_FinalSettlementDone.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_FinalSettlementDone_No:
                        flgFinalSettlementDone = 0;
                        break;

                    case R.id.rb_FinalSettlementDone_Yes:
                        flgFinalSettlementDone = 1;
                        break;
                }
            }
        });

        rg_LocationFormfill.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbDistributor:
                        flgLocationFormfill = 1;
                        break;

                    case R.id.rbGodwon:
                        flgLocationFormfill = 2;
                        break;

                    case R.id.rbOther:
                        flgLocationFormfill = 3;
                        break;
                }
            }
        });

        rg_DistributorStockiest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_DistributorStockiest_Distributor:
                        flgDistributorStockiest = 0;
                        break;

                    case R.id.rb_DistributorStockiest_Stockiest:
                        flgDistributorStockiest = 1;
                        break;
                }
            }
        });

        rg_OldOrNewLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_OldOrNewLocation_Old:
                        flgOldOrNewLocation = 0;
                        break;

                    case R.id.rb_OldOrNewLocation_New:
                        flgOldOrNewLocation = 1;
                        break;
                }
            }
        });

        rg_TownDistributorSubD.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_TownDistributorSubD_Distributor:
                        flgTownDistributorSubD = 0;
                        break;

                    case R.id.rb_TownDistributorSubD_SubD:
                        flgTownDistributorSubD = 1;
                        break;
                }
            }
        });

        rg_FirmType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_FirmType_Proprietorship:
                        flgFirmType = 0;
                        ll_ProprietorPANOverAllSection.setVisibility(View.VISIBLE);
                        ll_ProprietorPANPhoto.setVisibility(View.VISIBLE);
                        ll_PANPartner1_num.setVisibility(View.GONE);
                        ll_PANPartner2_num.setVisibility(View.GONE);
                        ll_ProprietorPAN_num.setVisibility(View.GONE);


                        ll_PanPhotoPartner1.setVisibility(View.GONE);
                        ll_PanPhotoPartner2.setVisibility(View.GONE);
                        ll_PANPatner1OverAllSection.setVisibility(View.GONE);
                        ll_PANPatner2OverAllSection.setVisibility(View.GONE);
                        ll_PartnerDeedOverAllSection.setVisibility(View.GONE);
                        ll_PartnerDeed.setVisibility(View.GONE);
                        ll_PartnerDeed_num.setVisibility(View.GONE);


                        et_PANNoOfPartner1.setText("");

                        et_PANNoOfPartner2.setText("");
                        et_PartnerDeed.setText("");

                        ll_ProprietorPAN_num.setVisibility(View.GONE);
                        if(flgProprietorPANPhoto==0)
                        {
                            ll_ProprietorPAN_num.setVisibility(View.GONE);
                            ProprietorPANPhoto_photo_list.setVisibility(View.GONE);
                            btn_ProprietorPANPhoto.setVisibility(View.GONE);
                        }
                        else if(flgProprietorPANPhoto==1)
                        {
                            ll_ProprietorPAN_num.setVisibility(View.VISIBLE);
                            ProprietorPANPhoto_photo_list.setVisibility(View.VISIBLE);
                            btn_ProprietorPANPhoto.setVisibility(View.VISIBLE);
                        }

                        break;

                    case R.id.rb_DistributorStockiest_Partnership:
                        flgFirmType = 1;
                        ll_ProprietorPANOverAllSection.setVisibility(View.GONE);
                        ll_ProprietorPANPhoto.setVisibility(View.GONE);


                        ll_PANPatner1OverAllSection.setVisibility(View.VISIBLE);
                        ll_PANPatner2OverAllSection.setVisibility(View.VISIBLE);
                        ll_PartnerDeedOverAllSection.setVisibility(View.VISIBLE);
                        ll_PanPhotoPartner1.setVisibility(View.VISIBLE);
                        ll_PanPhotoPartner2.setVisibility(View.VISIBLE);
                        ll_PartnerDeed.setVisibility(View.VISIBLE);
                        ll_PANPartner1_num.setVisibility(View.GONE);
                        ll_PANPartner2_num.setVisibility(View.GONE);
                        ll_PartnerDeed_num.setVisibility(View.GONE);
                        et_PANNoOfProprietor.setText("");


                        if(flgPanPhotoPartner1==0)
                        {

                            ll_PANPartner1_num.setVisibility(View.GONE);
                            btn_PanPhotoPartner1.setVisibility(View.GONE);

                            ll_ProprietorPAN_num.setVisibility(View.GONE);
                            ProprietorPANPhoto_photo_list.setVisibility(View.GONE);
                            btn_ProprietorPANPhoto.setVisibility(View.GONE);
                        }
                        else if(flgPanPhotoPartner1==1)
                        {

                            ll_PANPartner1_num.setVisibility(View.VISIBLE);
                            btn_PanPhotoPartner1.setVisibility(View.VISIBLE);

                            ll_ProprietorPAN_num.setVisibility(View.GONE);
                            ProprietorPANPhoto_photo_list.setVisibility(View.GONE);
                            btn_ProprietorPANPhoto.setVisibility(View.GONE);
                        }


                        if(flgPanPhotoPartner2==0)
                        {

                            ll_PANPartner2_num.setVisibility(View.GONE);
                            btn_PanPhotoPartner2.setVisibility(View.GONE);

                            ll_ProprietorPAN_num.setVisibility(View.GONE);
                            ProprietorPANPhoto_photo_list.setVisibility(View.GONE);
                            btn_ProprietorPANPhoto.setVisibility(View.GONE);
                        }
                        else if(flgPanPhotoPartner2==1)
                        {

                            ll_PANPartner2_num.setVisibility(View.VISIBLE);
                            btn_PanPhotoPartner2.setVisibility(View.VISIBLE);

                            ll_ProprietorPAN_num.setVisibility(View.GONE);
                            ProprietorPANPhoto_photo_list.setVisibility(View.GONE);
                            btn_ProprietorPANPhoto.setVisibility(View.GONE);
                        }


                        if(flgPartnerDeed==0)
                        {

                            ll_PartnerDeed_num.setVisibility(View.GONE);
                            btn_PartnerDeed.setVisibility(View.GONE);

                            ll_ProprietorPAN_num.setVisibility(View.GONE);
                            ProprietorPANPhoto_photo_list.setVisibility(View.GONE);
                            btn_ProprietorPANPhoto.setVisibility(View.GONE);
                        }
                        else if(flgPartnerDeed==1)
                        {

                            ll_PartnerDeed_num.setVisibility(View.VISIBLE);
                            btn_PartnerDeed.setVisibility(View.VISIBLE);

                            ll_ProprietorPAN_num.setVisibility(View.GONE);
                            ProprietorPANPhoto_photo_list.setVisibility(View.GONE);
                            btn_ProprietorPANPhoto.setVisibility(View.GONE);
                        }
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
                locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(ActivityDistributorAppointment.this, true, true, 20, 0);

            }
        });

        ll_FinalSettlementDone.setVisibility(View.GONE);
        ll_DistributorStockiest.setVisibility(View.GONE);
        ll_OldNewLocation.setVisibility(View.GONE);
        ll_TownDistributorSubD.setVisibility(View.GONE);
        ll_OldDistributorDetails.setVisibility(View.GONE);
     /*   ll_WholeSectionPANOfProprietor.setVisibility(View.GONE);
        ll_WholeSectionPANOfPartner1.setVisibility(View.GONE);
        ll_WholeSectionPANOfPartner2.setVisibility(View.GONE);*/

        cheque_photo_list.setLayoutManager(new GridLayoutManager(this, 2));
        rg_ID_cheque.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton radioButtonVal = group.findViewById(checkedId);
                    if (radioButtonVal.getId() == R.id.rb_no_id_cheque) {
                        flgHasCheque = 0;
                        cheque_photo_list.setVisibility(View.GONE);
                        btn_chequePhoto.setVisibility(View.GONE);
                        ll_cheque_num.setVisibility(View.GONE);
                    } else {
                        flgHasCheque = 1;
                        cheque_photo_list.setVisibility(View.VISIBLE);
                        btn_chequePhoto.setVisibility(View.VISIBLE);
                        ll_cheque_num.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        gstCopy_photo_list.setLayoutManager(new GridLayoutManager(this, 2));
        rg_ID_gstCopy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton radioButtonVal = group.findViewById(checkedId);
                    if (radioButtonVal.getId() == R.id.rb_no_id_gstCopy) {
                        flgGstCopy_photo = 0;
                        ll_gst_num.setVisibility(View.GONE);
                        gstCopy_photo_list.setVisibility(View.GONE);
                        btn_gstCopy_photo.setVisibility(View.GONE);
                    } else if (radioButtonVal.getId() == R.id.rb_yes_id_gstCopy){
                        flgGstCopy_photo = 1;
                        ll_gst_num.setVisibility(View.VISIBLE);
                        gstCopy_photo_list.setVisibility(View.VISIBLE);
                        btn_gstCopy_photo.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        PANOfFirm_photo_list.setLayoutManager(new GridLayoutManager(this, 2));
        rg_ID_PANOfFirm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton radioButtonVal = group.findViewById(checkedId);
                    if (radioButtonVal.getId() == R.id.rb_no_id_PANOfFirm) {
                        flgPANOfFirm = 0;
                        ll_PANOfFirm_num.setVisibility(View.GONE);
                        PANOfFirm_photo_list.setVisibility(View.GONE);
                        btn_PANNoOfFirm_photo.setVisibility(View.GONE);
                    } else if (radioButtonVal.getId() == R.id.rb_yes_id_PANOfFirm){
                        flgPANOfFirm = 1;
                        ll_PANOfFirm_num.setVisibility(View.VISIBLE);
                        PANOfFirm_photo_list.setVisibility(View.VISIBLE);
                        btn_PANNoOfFirm_photo.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        ProprietorPANPhoto_photo_list.setLayoutManager(new GridLayoutManager(this, 2));
        rg_ID_ProprietorPANPhoto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton radioButtonVal = group.findViewById(checkedId);
                    if (radioButtonVal.getId() == R.id.rb_no_id_ProprietorPANPhoto) {
                        flgProprietorPANPhoto = 0;
                        ll_ProprietorPAN_num.setVisibility(View.GONE);
                        ProprietorPANPhoto_photo_list.setVisibility(View.GONE);
                        btn_ProprietorPANPhoto.setVisibility(View.GONE);
                    } else if (radioButtonVal.getId() == R.id.rb_yes_id_ProprietorPANPhoto){
                        flgProprietorPANPhoto = 1;
                        ProprietorPANPhoto_photo_list.setVisibility(View.VISIBLE);
                        btn_ProprietorPANPhoto.setVisibility(View.VISIBLE);
                        ll_ProprietorPAN_num.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        PanPhotoPartner1_photo_list.setLayoutManager(new GridLayoutManager(this, 2));
        rg_ID_PanPhotoPartner1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton radioButtonVal = group.findViewById(checkedId);
                    if (radioButtonVal.getId() == R.id.rb_no_id_PanPhotoPartner1) {
                        flgPanPhotoPartner1 = 0;
                        ll_PANPartner1_num.setVisibility(View.GONE);
                        PanPhotoPartner1_photo_list.setVisibility(View.GONE);
                        btn_PanPhotoPartner1.setVisibility(View.GONE);
                    } else if (radioButtonVal.getId() == R.id.rb_yes_id_PanPhotoPartner1){
                        flgPanPhotoPartner1 = 1;
                        ll_PANPartner1_num.setVisibility(View.VISIBLE);
                        PanPhotoPartner1_photo_list.setVisibility(View.VISIBLE);
                        btn_PanPhotoPartner1.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        PanPhotoPartner2_photo_list.setLayoutManager(new GridLayoutManager(this, 2));
        rg_ID_PanPhotoPartner2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton radioButtonVal = group.findViewById(checkedId);
                    if (radioButtonVal.getId() == R.id.rb_no_id_PanPhotoPartner2) {
                        flgPanPhotoPartner2 = 0;
                        ll_PANPartner2_num.setVisibility(View.GONE);
                        PanPhotoPartner2_photo_list.setVisibility(View.GONE);
                        btn_PanPhotoPartner2.setVisibility(View.GONE);
                    } else if (radioButtonVal.getId() == R.id.rb_yes_id_PanPhotoPartner2){
                        flgPanPhotoPartner2 = 1;
                        ll_PANPartner2_num.setVisibility(View.VISIBLE);
                        PanPhotoPartner2_photo_list.setVisibility(View.VISIBLE);
                        btn_PanPhotoPartner2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        PartnerDeed_photo_list.setLayoutManager(new GridLayoutManager(this, 2));
        rg_ID_PartnerDeed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton radioButtonVal = group.findViewById(checkedId);
                    if (radioButtonVal.getId() == R.id.rb_no_id_PartnerDeed) {
                        flgPartnerDeed = 0;
                        ll_PartnerDeed_num.setVisibility(View.GONE);
                        PartnerDeed_photo_list.setVisibility(View.GONE);
                        btn_PartnerDeed.setVisibility(View.GONE);
                    } else if (radioButtonVal.getId() == R.id.rb_yes_id_PartnerDeed){
                        flgPartnerDeed = 1;
                        ll_PartnerDeed_num.setVisibility(View.VISIBLE);
                        PartnerDeed_photo_list.setVisibility(View.VISIBLE);
                        btn_PartnerDeed.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        distributorCopy_photo_list.setLayoutManager(new GridLayoutManager(this, 2));
        selfiwithdistributorCopy_photo_list.setLayoutManager(new GridLayoutManager(this, 2));





        cbGodownAddressSameAsOffice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)
                {
                    flgGodownAddressSameAsOffice=1;
                    if (et_Address != null && !TextUtils.isEmpty(et_Address.getText().toString().trim()))
                        et_GodwonAddress.setText(et_Address.getText().toString().trim());
                    else
                        et_GodwonAddress.setText("");

                    if (et_Village != null && !TextUtils.isEmpty(et_Village.getText().toString().trim()))
                        et_GodwonVillage.setText(et_Village.getText().toString().trim());
                    else
                        et_GodwonVillage.setText("");

                    if (et_District != null && !TextUtils.isEmpty(et_District.getText().toString().trim()))
                        et_GodwonDistrict.setText(et_District.getText().toString().trim());
                    else
                        et_GodwonDistrict.setText("");

                    if (et_Pincode != null && !TextUtils.isEmpty(et_Pincode.getText().toString().trim()))
                        etGodwonPinCode.setText(et_Pincode.getText().toString().trim());
                    else
                        etGodwonPinCode.setText("");

                    if (et_State != null && !TextUtils.isEmpty(et_State.getText().toString().trim()))
                        et_Godwonstate.setText(et_State.getText().toString().trim());
                    else
                        et_Godwonstate.setText("");

                    et_GodwonAddress.setEnabled(false);
                    et_GodwonVillage.setEnabled(false);
                    et_GodwonDistrict.setEnabled(false);
                    etGodwonPinCode.setEnabled(false);
                    et_Godwonstate.setEnabled(false);
                }
                else
                {
                    flgGodownAddressSameAsOffice=0;
                    et_GodwonAddress.setEnabled(true);
                    et_GodwonVillage.setEnabled(true);
                    et_GodwonDistrict.setEnabled(true);
                    etGodwonPinCode.setEnabled(true);
                    et_Godwonstate.setEnabled(true);
                }
            }
        });


        ll_overAllSignatureSection = (LinearLayout) findViewById(R.id.ll_overAllSignatureSection);
        ll_ASMSignatureSection = (LinearLayout) findViewById(R.id.ll_ASMSignatureSection);
        linearLayoutASMSign = (LinearLayout) findViewById(R.id.linearLayoutASMSign);
        ll_TSIignatureSection = (LinearLayout) findViewById(R.id.ll_TSIignatureSection);
        linearLayoutTSISign = (LinearLayout) findViewById(R.id.linearLayoutTSISign);

        ll_DistributorSignatureSection = (LinearLayout) findViewById(R.id.ll_DistributorignatureSection);
        linearLayoutDistributorSign = (LinearLayout) findViewById(R.id.linearLayoutDistributorSign);

        rl_ASMSign_layout = (RelativeLayout) findViewById(R.id.rl_ASMSign_layout);
        rl_TSISign_layout = (RelativeLayout) findViewById(R.id.rl_TSISign_layout);
        rl_DistributorSign_layout = (RelativeLayout) findViewById(R.id.rl_DistributorSign_layout);


        pic_nameASMSign = "IMG_ASM_" + imei + timeStamp+ ".png";
        ImgPathASMSign = DIRECTORY + pic_nameASMSign ;

        pic_nameTSISign = "IMG_TSI_" + imei + timeStamp + ".png";
        ImgPathTSISign = DIRECTORY + pic_nameTSISign;

        pic_nameDistributorSign = "IMG_Distributor_" + imei + timeStamp + ".png";
        ImgPathDistributorSign = DIRECTORY + pic_nameDistributorSign;

        signatureAllCodeASM();
        signatureAllCodeTSI();
        signatureAllCodeDistributor();
        // ll_overAllSignatureSection.setVisibility(View.GONE);

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
                            tblPotentialDistributorRetailersFeedBackDetails.setPDAcode(AppUtils.getToken(ActivityDistributorAppointment.this));
                            tblPotentialDistributorRetailersFeedBackDetails.setEntryDate(TimeUtils.getNetworkDate(ActivityDistributorAppointment.this,"dd-MMM-yyyy"));
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
        CommonFunction.SavePotentialDistributorRetailerFeedBackData reportDownloadAsyncTask = new CommonFunction.SavePotentialDistributorRetailerFeedBackData(ActivityDistributorAppointment.this, mProgressDialogReport, interfaceRetrofitRetailerFeedBack,0, tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving);
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
        if(flgLocationFormfill==-1)
        {
            validationString.append("\n" + (++i) + ". Please Select your current location?");
        }else
        {
            tblPotentialDistributor.setLocationCapturedFrom(flgLocationFormfill);
        }


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


        if(cbGodownAddressSameAsOffice!=null && cbGodownAddressSameAsOffice.isChecked()==false){
            tblPotentialDistributor.setFlgGodownAddressSameAsOffic(0);
        }
        if(cbGodownAddressSameAsOffice!=null && cbGodownAddressSameAsOffice.isChecked()==true){
            tblPotentialDistributor.setFlgGodownAddressSameAsOffic(1);
        }



        //Godwn Addrees Starts Here

        if (!et_GodwonAddress.getText().toString().trim().equals("")) {
            if (et_GodwonAddress.getText().toString().trim().length() > 9) {
                addressGodownUserInput = et_GodwonAddress.getText().toString().trim();
                tblPotentialDistributor.setAddress_Godown(addressGodownUserInput.replace("&","-"));
            } else {
                //showAlertSingleButtonInfo("Address can not be less then 10 characters");
                validationString.append("\n" + (++i) + ".Godown Address can not be less then 10 characters");
                //return;
            }
        } else {
            validationString.append("\n" + (++i) + ". Fill Godown Address.");
           /* showAlertSingleButtonInfo("Please fill Address.");
            return;*/

        }
        if (!et_GodwonVillage.getText().toString().trim().equals("")) {
            if (et_GodwonVillage.getText().toString().trim().length() > 2) {
                cityGodownUserInput = et_GodwonVillage.getText().toString().trim();
                tblPotentialDistributor.setCity_Godown(cityGodownUserInput.replace("&","-"));
            } else {
                validationString.append("\n" + (++i) + ".Gowdon City/Village can not be less then 3 characters.");
/*
                showAlertSingleButtonInfo("City/Village can not be less then 3 characters");

                return;*/
            }
        } else {
            validationString.append("\n" + (++i) + ". Fill Godwon City/Village.");
           /* showAlertSingleButtonInfo("Please fill City/Village.");
            return;*/

        }

        if (!et_GodwonDistrict.getText().toString().trim().equals("")) {
            if (et_GodwonDistrict.getText().toString().trim().length() > 2) {
                districtGodownUserInput = et_GodwonDistrict.getText().toString().trim();
                tblPotentialDistributor.setDistrict_Godown(districtGodownUserInput.replace("&","-"));
            } else {
                validationString.append("\n" + (++i) + ".Godown District can not be less then 3 characters.");
/*
                showAlertSingleButtonInfo("City/Village can not be less then 3 characters");

                return;*/
            }
        } else {
            validationString.append("\n" + (++i) + ". Fill Godown District.");
           /* showAlertSingleButtonInfo("Please fill City/Village.");
            return;*/

        }

        if (!et_Godwonstate.getText().toString().trim().equals("")) {
            if (et_Godwonstate.getText().toString().trim().length() > 2) {
                stateGodownCodeUserInput = et_Godwonstate.getText().toString().trim();
                tblPotentialDistributor.setState_Godown(stateGodownCodeUserInput.replace("&","-"));
            } else {
                validationString.append("\n" + (++i) + ". State can not be less then 3 characters.");

/*
                showAlertSingleButtonInfo("State can not be less then 3 characters.");
                return;
*/
            }
        } else {
            validationString.append("\n" + (++i) + ". Fill Godwon State.");
           /* showAlertSingleButtonInfo("Please fill State.");
            return;*/

        }



        if (!etGodwonPinCode.getText().toString().trim().equals("")) {
            if (etGodwonPinCode.getText().toString().trim().length() == 6) {
                pinGodownCodeUserInput = etGodwonPinCode.getText().toString().trim();
                tblPotentialDistributor.setPincode_Godown(pinGodownCodeUserInput.replace("&","-"));
            } else {
                validationString.append("\n" + (++i) + ". Enter the 6 digits pincode");
                /*showAlertSingleButtonInfo("Please enter the 6 digits pincode");
                return;*/
            }
        } else {
            validationString.append("\n" + (++i) + ". Fill Godon Pin Code.");
            /*showAlertSingleButtonInfo("Please fill Pin Code.");
            return;
*/
        }


        //GodownAddress Ends Here


        if (isLocationConfirm && validationString.toString().equals("")) {
            tblPotentialDistributor.setFlgAppointed(1);
            flgLocationConfirm = 1;
            tblPotentialDistributor.setFlgLocationConfirm(flgLocationConfirm);
            contactNumberUserInput =  et_ContactPerson.getText().toString().trim();//.getOwnerContactNumber();



            if (contactNumberUserInput != null && contactNumberUserInput.length() > 0 && mDataSource.fnGetExistingOwnerNumberFromScoutingDistributor(contactNumberUserInput, ScoutingDistributorID) == 1) {
                String outletOtherOutletHavingSameMobileNumber = mDataSource.fnGetScoutingDistributorNamewithMobileNo(contactNumberUserInput);
                showStoreAlreadyExistsWithSameMobileNumber(outletOtherOutletHavingSameMobileNumber, contactNumberUserInput);

            } else {
                imageTableList.clear();
                for (Map.Entry<String, List<String>> entry : photo_saving.entrySet()) {
                    imageTable = new PotentialDistributorImageTable();
                    imageTable.setImageName(entry.getValue().get(0));
                    imageTable.setImageType(entry.getValue().get(1));
                    // imageTable.setSstat(Integer.parseInt(entry.getValue().get(2)));
                    imageTable.setImageClickedTime(entry.getValue().get(3));
                    imageTable.setNodeID(entry.getValue().get(4));
                    imageTable.setNodeType(ScoutingDistributorNodeType);
                    imageTable.setImagePath(entry.getKey());


                    imageTableList.add(imageTable);
                }

                     /*   ll_MarketReputationRetailerSection.setVisibility(View.VISIBLE);


                        ll_sectionOtherThenRetailerFeedBack.setVisibility(View.GONE);
                        location_layout.setVisibility(View.GONE);
                        bt_Next.setVisibility(View.GONE);
                        bt_Previous.setVisibility(View.VISIBLE);
                        bt_Submit.setVisibility(View.VISIBLE);
                        bt_Save.setVisibility(View.VISIBLE);*/
                if(fnValidateSignatures())
                {
                 /*   viewASM.setDrawingCacheEnabled(true);
                    mSignature.save(viewASM, ImgPathASMSign);*/

                            viewTSI.setDrawingCacheEnabled(true);
                            mSignatureTSI.save(viewTSI, ImgPathTSISign);

                    viewDistribuotr.setDrawingCacheEnabled(true);
                    mSignatureDistributor.save(viewDistribuotr, ImgPathDistributorSign);


                    long syncTIMESTAMP = System.currentTimeMillis();
                    Date dateobj = new Date(syncTIMESTAMP);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                    String clkdTime = df.format(dateobj);

                    //,7=TSISign,8=ASMSign,8=DIstributorSign
                            if(pic_nameTSISign!=null && !TextUtils.isEmpty(pic_nameTSISign)) {
                                imageTable = new PotentialDistributorImageTable();
                                imageTable.setImageName(pic_nameTSISign);
                                imageTable.setImageType("8");
                                // imageTable.setSstat(Integer.parseInt(entry.getValue().get(2)));
                                imageTable.setImageClickedTime(clkdTime);
                                imageTable.setNodeID(ScoutingDistributorID);
                                imageTable.setNodeType(ScoutingDistributorNodeType);
                                imageTable.setImagePath(ImgPathTSISign);
                                imageTableList.add(imageTable);
                            }
                   /* if(pic_nameASMSign!=null && !TextUtils.isEmpty(pic_nameASMSign)) {
                        imageTable = new PotentialDistributorImageTable();
                        imageTable.setImageName(pic_nameASMSign);
                        imageTable.setImageType("9");
                        // imageTable.setSstat(Integer.parseInt(entry.getValue().get(2)));
                        imageTable.setImageClickedTime(clkdTime);
                        imageTable.setNodeID(ScoutingDistributorID);
                        imageTable.setNodeType(ScoutingDistributorNodeType);
                        imageTable.setImagePath(ImgPathASMSign);
                        imageTableList.add(imageTable);
                    }*/

                    if(pic_nameDistributorSign!=null && !TextUtils.isEmpty(pic_nameDistributorSign)) {
                        imageTable = new PotentialDistributorImageTable();
                        imageTable.setImageName(pic_nameDistributorSign);
                        imageTable.setImageType("10");
                        // imageTable.setSstat(Integer.parseInt(entry.getValue().get(2)));
                        imageTable.setImageClickedTime(clkdTime);
                        imageTable.setNodeID(ScoutingDistributorID);
                        imageTable.setNodeType(ScoutingDistributorNodeType);
                        imageTable.setImagePath(ImgPathDistributorSign);
                        imageTableList.add(imageTable);
                    }

                    tblPotentialDistributor.setImageTableList(imageTableList);
                   // if(AppUtils.isInternetAvailable()) {
                    if(AppUtils.isOnline(this)){
                        task1 = new SyncImageData();
                        AppUtils.executeAsyncTask(task1);
                    }else
                    {
                        AppUtils.showNoConnAlert(ActivityDistributorAppointment.this);
                    }

                           /* mProgressDialogReport = new ProgressDialog(mContext);
                            mProgressDialogReport.setTitle("Please wait submitting distributor basic data.");//context.getResources().getString(R.string.Loading));
                            mProgressDialogReport.setMessage(mContext.getResources().getString(R.string.SubmittingDataMsg));
                            mProgressDialogReport.setIndeterminate(true);
                            mProgressDialogReport.setCancelable(false);
                            mProgressDialogReport.show();


                            InterfaceServerSuccessEntry interfaceRetrofit = (InterfaceServerSuccessEntry) mContext;
                            CommonFunction.SavePotentialDistributorData reportDownloadAsyncTask = new CommonFunction.SavePotentialDistributorData(ActivityDistributorAppointment.this, mProgressDialogReport, interfaceRetrofit,0,tblPotentialDistributor);
                            AppUtils.executeAsyncTask(reportDownloadAsyncTask);*/

                }




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
        tblPotentialDistributor.setPDACode(AppUtils.getToken(ActivityDistributorAppointment.this));
        if(flgNewScoutingOrOldDistributor==0)
        {
            tblPotentialDistributor.setNodeID("0");
            tblPotentialDistributor.setNewDBRCode(ScoutingDistributorID);
            tblPotentialDistributor.setNodeType(ScoutingDistributorNodeType);
            tblPotentialDistributor.setCreatedDate(TimeUtils.getNetworkDate(ActivityDistributorAppointment.this,"dd-MMM-yyyy"));
            tblPotentialDistributor.setLatCode(Double.parseDouble(fnLati));
            tblPotentialDistributor.setLongCode(Double.parseDouble(fnLongi));

            tblPotentialDistributor.setEntryPersonNodeID(PersonNodeId);
            tblPotentialDistributor.setEntryPersonNodeType(PersonNodeType);

            if(flgDistributorReplacement==-1)
            {
                showAlertSingleButtonInfo("Please Select Is This Replacement of Old Distributor?");
                return;
            }
            tblPotentialDistributor.setIsOldDistributorReplaced(flgDistributorReplacement);
            if(flgDistributorReplacement==1)
            {
                if(SelectedOldDistributorID==0)
                {
                    showAlertSingleButtonInfo("Please Select Old Distributor Name?");
                    return;
                }
                tblPotentialDistributor.setOldDistriutorID(SelectedOldDistributorID);
                if(flgFinalSettlementDone==-1)
                {
                    showAlertSingleButtonInfo("Please Select Full & Final Settlement of Old Distributor Done?");
                    return;
                }
                tblPotentialDistributor.setIsOldDistributorDFinalPaymentDone(flgFinalSettlementDone);
                if(flgDistributorStockiest==-1)
                {
                    showAlertSingleButtonInfo("Please Select Mention Type?");
                    return;
                }
                tblPotentialDistributor.setFlgDistributorSS(flgDistributorStockiest);

                if(flgOldOrNewLocation==-1)
                {
                    showAlertSingleButtonInfo("Please Select Old New Location?");
                    return;
                }
                tblPotentialDistributor.setIsNewLocation(flgOldOrNewLocation);

                if(flgTownDistributorSubD==-1)
                {
                    showAlertSingleButtonInfo("Please Select This is Distributor or Sub Db Town?");
                    return;
                }
                tblPotentialDistributor.setFlgTownDistributorSubD(flgTownDistributorSubD);

            }


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


            if (et_BankAccountNo.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Bank Account Number.");
                return;
            }
            else
            {
                tblPotentialDistributor.setBankAccountNumber(et_BankAccountNo.getText().toString().trim());
            }

            if (et_BankIFSCCode.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill IFSC Code.");
                return;
            }
            else
            {
                tblPotentialDistributor.setIFSCCode(et_BankIFSCCode.getText().toString().trim());
            }

            if (et_BankAddress.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Bank Address.");
                return;
            }
            else
            {
                tblPotentialDistributor.setBankAddress(et_BankAddress.getText().toString().trim());
            }

            if (et_BusinessExpectedTons.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Business Expected(In Tons).");
                return;
            }
            else
            {
                tblPotentialDistributor.setExpectedBusiness(et_BusinessExpectedTons.getText().toString().trim());
            }

            if (et_IdealGodownSpaceRequired.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Ideal Godown Space Required.");
                return;
            }
            else
            {
                tblPotentialDistributor.setReqGodownSpace(et_IdealGodownSpaceRequired.getText().toString().trim());
            }

            if (et_GodownSpaceAgreedUpon.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Godown Space Agreed Upon.");
                return;
            }
            else
            {
                tblPotentialDistributor.setAgreedGodownSpace(et_GodownSpaceAgreedUpon.getText().toString().trim());
            }

            if (et_InvestmentAgreedUpon.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Investment Agreed Upon.");
                return;
            }
            else
            {
                tblPotentialDistributor.setAgreedInvestment(et_InvestmentAgreedUpon.getText().toString().trim());
            }


            if (et_IdealROIAsPerNorms.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill ROI As Per Norms.");
                return;
            }
            else
            {
                tblPotentialDistributor.setIdealROI(et_IdealROIAsPerNorms.getText().toString().trim());
            }

            if (et_ExpectedROIByDistributor.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please Expected ROI By Distributor.");
                return;
            }
            else
            {
                tblPotentialDistributor.setExpectedROI(et_ExpectedROIByDistributor.getText().toString().trim());
            }


            // tblPotentialDistributor.setFlgGSTTaken(0);


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


            if(flgFirmType==-1)
            {
                showAlertSingleButtonInfo("Please Select Firm Type?");
                return;
            }
            else
            {
                tblPotentialDistributor.setFlgProprietorPartner(flgFirmType);
            }

            if(flgGstCopy_photo==-1)
            {
                showAlertSingleButtonInfo("Please Select GST Copy Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsGSTDetailsSubmitted(flgGstCopy_photo);
            }
            if(flgGstCopy_photo==1)
            {
                if(et_gstno!=null && !TextUtils.isEmpty(et_gstno.getText().toString().trim()))
                {
                    if (et_gstno.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = et_gstno.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {
                        tblPotentialDistributor.setGSTNumber(et_gstno.getText().toString().trim());
                    }

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill GST Number.", et_gstno);
                    return;
                }

                if (flgGstCopy_photo==0 && (gstCopy_photo == null || gstCopy_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload GST Copy Photo.");
                    return;
                }
            }



            //Pan Of Firm
            if(flgPANOfFirm==-1)
            {
                showAlertSingleButtonInfo("Please Select Pan of Firm Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsPANOfFirmSubmitted(flgPANOfFirm);
            }
            if(flgPANOfFirm==1)
            {
                if(et_PANNoOfFirm!=null && !TextUtils.isEmpty(et_PANNoOfFirm.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setPANNoOfFirm(et_PANNoOfFirm.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Pan No of Firm.", et_PANNoOfFirm);
                    return;
                }

                if (flgPANOfFirm==0  && (PANNoOfFirm_photo == null || PANNoOfFirm_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload PAN of Firm Photo.");
                    return;
                }
            }


            if(flgGstCopy_photo==-1)
            {
                showAlertSingleButtonInfo("Please Select GST Copy Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsGSTDetailsSubmitted(flgGstCopy_photo);
            }
            if(flgGstCopy_photo==1)
            {
                if(et_gstno!=null && !TextUtils.isEmpty(et_gstno.getText().toString().trim()))
                {
                    if (et_gstno.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = et_gstno.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {
                        tblPotentialDistributor.setGSTNumber(et_gstno.getText().toString().trim());
                    }

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill GST Number.", et_gstno);
                    return;
                }

                if (flgGstCopy_photo==0 && (gstCopy_photo == null || gstCopy_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload GST Copy Photo.");
                    return;
                }
            }



            //Pan Of Firm
            if(flgPANOfFirm==-1)
            {
                showAlertSingleButtonInfo("Please Select Pan of Firm Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsPANOfFirmSubmitted(flgPANOfFirm);
            }
            if(flgPANOfFirm==1)
            {
                if(et_PANNoOfFirm!=null && !TextUtils.isEmpty(et_PANNoOfFirm.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setPANNoOfFirm(et_PANNoOfFirm.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Pan No of Firm.", et_PANNoOfFirm);
                    return;
                }

                if (flgPANOfFirm==0 && (PANNoOfFirm_photo == null || PANNoOfFirm_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload PAN of Firm Photo.");
                    return;
                }
            }


            //Cheque Selected Or Not
            if(flgHasCheque==-1)
            {
                showAlertSingleButtonInfo("Please Select Cheque Given Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsCheckGiven(flgHasCheque);
            }
            if(flgHasCheque==1)
            {
                if(et_chequeno!=null && !TextUtils.isEmpty(et_chequeno.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setChequeNumber(et_chequeno.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Cheque Number.", et_chequeno);
                    return;
                }

                if (flgHasCheque==0 && (cheque_photo == null || cheque_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload Cheque Photo.");
                    return;
                }
            }



            //Proprietor Selected Or Not
            if(flgProprietorPANPhoto==-1 && ll_ProprietorPANOverAllSection!=null && ll_ProprietorPANOverAllSection.getVisibility()==View.VISIBLE)
            {
                showAlertSingleButtonInfo("Please Select Proprietor PAN Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsProprietorPanSubmited(flgProprietorPANPhoto);
            }
            if(flgProprietorPANPhoto==1 && ll_ProprietorPANOverAllSection!=null && ll_ProprietorPANOverAllSection.getVisibility()==View.VISIBLE)
            {
                if(et_PANNoOfProprietor!=null && !TextUtils.isEmpty(et_PANNoOfProprietor.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setProprietorPanNumber(et_PANNoOfProprietor.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Proprietor PAN Number.", et_PANNoOfProprietor);
                    return;
                }

                if (flgProprietorPANPhoto==0 && (ProprietorPANPhoto_photo == null || ProprietorPANPhoto_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload Proprietor PAN Photo.");
                    return;
                }
            }
            else
            {
                tblPotentialDistributor.setProprietorPanNumber("");
            }


            //PAN Partner 1 Selected Or Not
            if(flgPanPhotoPartner1==-1 && ll_PANPatner1OverAllSection!=null && ll_PANPatner1OverAllSection.getVisibility()==View.VISIBLE)
            {
                showAlertSingleButtonInfo("Please Select Proprietor Partner 1 PAN Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsPartner1PANSubmitted(flgPanPhotoPartner1);
            }
            if(flgPanPhotoPartner1==1 && ll_PANPatner1OverAllSection!=null && ll_PANPatner1OverAllSection.getVisibility()==View.VISIBLE)
            {
                if(et_PANNoOfPartner1!=null && !TextUtils.isEmpty(et_PANNoOfPartner1.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setPanNumber_Partner1(et_PANNoOfPartner1.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Partner 1 PAN Number.", et_PANNoOfPartner1);
                    return;
                }

                if (flgPanPhotoPartner1==0 && (PanPhotoPartner1_photo == null || PanPhotoPartner1_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload Partner 1 PAN Photo.");
                    return;
                }
            }
            else
            {
                tblPotentialDistributor.setPanNumber_Partner1("");
            }



            //PAN Partner 2 Selected Or Not
            if(flgPanPhotoPartner2==-1 && ll_PANPatner2OverAllSection!=null && ll_PANPatner2OverAllSection.getVisibility()==View.VISIBLE)
            {
                showAlertSingleButtonInfo("Please Select Proprietor Partner 2 PAN Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsPartner2PANSubmitted(flgPanPhotoPartner2);
            }
            if(flgPanPhotoPartner2==1 && ll_PANPatner2OverAllSection!=null && ll_PANPatner2OverAllSection.getVisibility()==View.VISIBLE)
            {
                if(et_PANNoOfPartner2!=null && !TextUtils.isEmpty(et_PANNoOfPartner2.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setPanNumber_Partner2(et_PANNoOfPartner2.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Partner 2 PAN Number.", et_PANNoOfPartner2);
                    return;
                }

                if (flgPanPhotoPartner2==0 && (PanPhotoPartner2_photo == null || PanPhotoPartner2_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload Partner 2 PAN Photo.");
                    return;
                }
            }
            else
            {
                tblPotentialDistributor.setPanNumber_Partner2("");
            }



            //PPartner Deed Selected Or Not
            if(flgPartnerDeed==-1 && ll_PartnerDeedOverAllSection!=null && ll_PartnerDeedOverAllSection.getVisibility()==View.VISIBLE)
            {
                showAlertSingleButtonInfo("Please Select Partner Deed Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsPartnerDeedSubmitted(flgPartnerDeed);
            }
            if(flgPartnerDeed==1 && ll_PartnerDeedOverAllSection!=null && ll_PartnerDeedOverAllSection.getVisibility()==View.VISIBLE)
            {
                if(et_PartnerDeed!=null && !TextUtils.isEmpty(et_PartnerDeed.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setPartnerDeedNumber(et_PartnerDeed.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Partner Deed Number.", et_PartnerDeed);
                    return;
                }

                if (flgPartnerDeed==0 && (PartnerDeed_photo == null || PartnerDeed_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload Partner Deed PAN Photo.");
                    return;
                }
            }
            else
            {
                tblPotentialDistributor.setPartnerDeedNumber("");
            }
            validateLocationAndProceed();



        }
        else if(flgNewScoutingOrOldDistributor==1 && tblPotentialDistributor.getFlgViewOrEdit()==1)
        {


            tblPotentialDistributor.setNodeID(ScoutingDistributorID);
            tblPotentialDistributor.setNewDBRCode("0");


            tblPotentialDistributor.setNodeType(ScoutingDistributorNodeType);
            tblPotentialDistributor.setCreatedDate(TimeUtils.getNetworkDate(ActivityDistributorAppointment.this,"dd-MMM-yyyy"));
            tblPotentialDistributor.setLatCode(Double.parseDouble(fnLati));
            tblPotentialDistributor.setLongCode(Double.parseDouble(fnLongi));

            tblPotentialDistributor.setEntryPersonNodeID(PersonNodeId);
            tblPotentialDistributor.setEntryPersonNodeType(PersonNodeType);
            if(flgDistributorReplacement==-1)
            {
                showAlertSingleButtonInfo("Please Select Is This Replacement of Old Distributor?");
                return;
            }
            tblPotentialDistributor.setIsOldDistributorReplaced(flgDistributorReplacement);
            if(flgDistributorReplacement==1)
            {
                if(SelectedOldDistributorID==0)
                {
                    showAlertSingleButtonInfo("Please Select Old Distributor Name?");
                    return;
                }
                tblPotentialDistributor.setOldDistriutorID(SelectedOldDistributorID);
                if(flgFinalSettlementDone==-1)
                {
                    showAlertSingleButtonInfo("Please Select Full & Final Settlement of Old Distributor Done?");
                    return;
                }
                tblPotentialDistributor.setIsOldDistributorDFinalPaymentDone(flgFinalSettlementDone);
                if(flgDistributorStockiest==-1)
                {
                    showAlertSingleButtonInfo("Please Select Mention Type?");
                    return;
                }
                tblPotentialDistributor.setFlgDistributorSS(flgDistributorStockiest);

                if(flgOldOrNewLocation==-1)
                {
                    showAlertSingleButtonInfo("Please Select Old New Location?");
                    return;
                }
                tblPotentialDistributor.setIsNewLocation(flgOldOrNewLocation);

                if(flgTownDistributorSubD==-1)
                {
                    showAlertSingleButtonInfo("Please Select This is Distributor or Sub Db Town?");
                    return;
                }
                tblPotentialDistributor.setFlgTownDistributorSubD(flgTownDistributorSubD);

            }


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


            if (et_BankAccountNo.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Bank Account Number.");
                return;
            }
            else
            {
                tblPotentialDistributor.setBankAccountNumber(et_BankAccountNo.getText().toString().trim());
            }

            if (et_BankIFSCCode.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill IFSC Code.");
                return;
            }
            else
            {
                tblPotentialDistributor.setIFSCCode(et_BankIFSCCode.getText().toString().trim());
            }

            if (et_BankAddress.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Bank Address.");
                return;
            }
            else
            {
                tblPotentialDistributor.setBankAddress(et_BankAddress.getText().toString().trim());
            }

            if (et_BusinessExpectedTons.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Business Expected(In Tons).");
                return;
            }
            else
            {
                tblPotentialDistributor.setExpectedBusiness(et_BusinessExpectedTons.getText().toString().trim());
            }

            if (et_IdealGodownSpaceRequired.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Ideal Godown Space Required.");
                return;
            }
            else
            {
                tblPotentialDistributor.setReqGodownSpace(et_IdealGodownSpaceRequired.getText().toString().trim());
            }

            if (et_GodownSpaceAgreedUpon.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Godown Space Agreed Upon.");
                return;
            }
            else
            {
                tblPotentialDistributor.setAgreedGodownSpace(et_GodownSpaceAgreedUpon.getText().toString().trim());
            }

            if (et_InvestmentAgreedUpon.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill Investment Agreed Upon.");
                return;
            }
            else
            {
                tblPotentialDistributor.setAgreedInvestment(et_InvestmentAgreedUpon.getText().toString().trim());
            }


            if (et_IdealROIAsPerNorms.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please fill ROI As Per Norms.");
                return;
            }
            else
            {
                tblPotentialDistributor.setIdealROI(et_IdealROIAsPerNorms.getText().toString().trim());
            }

            if (et_ExpectedROIByDistributor.getText().toString().trim().isEmpty()) {
                showAlertSingleButtonInfo("Please Expected ROI By Distributor.");
                return;
            }
            else
            {
                tblPotentialDistributor.setExpectedROI(et_ExpectedROIByDistributor.getText().toString().trim());
            }


            // tblPotentialDistributor.setFlgGSTTaken(0);


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


            if(flgFirmType==-1)
            {
                showAlertSingleButtonInfo("Please Select Firm Type?");
                return;
            }
            else
            {
                tblPotentialDistributor.setFlgProprietorPartner(flgFirmType);
            }

            if(flgGstCopy_photo==-1)
            {
                showAlertSingleButtonInfo("Please Select GST Copy Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsGSTDetailsSubmitted(flgGstCopy_photo);
            }
            if(flgGstCopy_photo==1)
            {
                if(et_gstno!=null && !TextUtils.isEmpty(et_gstno.getText().toString().trim()))
                {
                    if (et_gstno.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = et_gstno.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {
                        tblPotentialDistributor.setGSTNumber(et_gstno.getText().toString().trim());
                    }

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill GST Number.", et_gstno);
                    return;
                }

                if (flgGstCopy_photo==0 && (gstCopy_photo == null || gstCopy_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload GST Copy Photo.");
                    return;
                }
            }



            //Pan Of Firm
            if(flgPANOfFirm==-1)
            {
                showAlertSingleButtonInfo("Please Select Pan of Firm Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsPANOfFirmSubmitted(flgPANOfFirm);
            }
            if(flgPANOfFirm==1)
            {
                if(et_PANNoOfFirm!=null && !TextUtils.isEmpty(et_PANNoOfFirm.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setPANNoOfFirm(et_PANNoOfFirm.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Pan No of Firm.", et_PANNoOfFirm);
                    return;
                }

                if (flgPANOfFirm==0 && (PANNoOfFirm_photo == null || PANNoOfFirm_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload PAN of Firm Photo.");
                    return;
                }
            }


            //Cheque Selected Or Not
            if(flgHasCheque==-1)
            {
                showAlertSingleButtonInfo("Please Select Cheque Given Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsCheckGiven(flgHasCheque);
            }
            if(flgHasCheque==1)
            {
                if(et_chequeno!=null && !TextUtils.isEmpty(et_chequeno.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setChequeNumber(et_chequeno.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Cheque Number.", et_chequeno);
                    return;
                }

                if (flgHasCheque==0 && (cheque_photo == null || cheque_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload Cheque Photo.");
                    return;
                }
            }



            //Proprietor Selected Or Not
            if(flgProprietorPANPhoto==-1 && ll_ProprietorPANOverAllSection!=null && ll_ProprietorPANOverAllSection.getVisibility()==View.VISIBLE)
            {
                showAlertSingleButtonInfo("Please Select Proprietor PAN Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsProprietorPanSubmited(flgProprietorPANPhoto);
            }
            if(flgProprietorPANPhoto==1 && ll_ProprietorPANOverAllSection!=null && ll_ProprietorPANOverAllSection.getVisibility()==View.VISIBLE)
            {
                if(et_PANNoOfProprietor!=null && !TextUtils.isEmpty(et_PANNoOfProprietor.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setProprietorPanNumber(et_PANNoOfProprietor.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Proprietor PAN Number.", et_PANNoOfProprietor);
                    return;
                }

                if (flgProprietorPANPhoto==0 && (ProprietorPANPhoto_photo == null || ProprietorPANPhoto_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload Proprietor PAN Photo.");
                    return;
                }
            }
            else
            {
                tblPotentialDistributor.setProprietorPanNumber("");
            }


            //PAN Partner 1 Selected Or Not
            if(flgPanPhotoPartner1==-1 && ll_PANPatner1OverAllSection!=null && ll_PANPatner1OverAllSection.getVisibility()==View.VISIBLE)
            {
                showAlertSingleButtonInfo("Please Select Proprietor Partner 1 PAN Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsPartner1PANSubmitted(flgPanPhotoPartner1);
            }
            if(flgPanPhotoPartner1==1 && ll_PANPatner1OverAllSection!=null && ll_PANPatner1OverAllSection.getVisibility()==View.VISIBLE)
            {
                if(et_PANNoOfPartner1!=null && !TextUtils.isEmpty(et_PANNoOfPartner1.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setPanNumber_Partner1(et_PANNoOfPartner1.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Partner 1 PAN Number.", et_PANNoOfPartner1);
                    return;
                }

                if (flgPanPhotoPartner1==0 && (PanPhotoPartner1_photo == null || PanPhotoPartner1_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload Partner 1 PAN Photo.");
                    return;
                }
            }
            else
            {
                tblPotentialDistributor.setPanNumber_Partner1("");
            }



            //PAN Partner 2 Selected Or Not
            if(flgPanPhotoPartner2==-1 && ll_PANPatner2OverAllSection!=null && ll_PANPatner2OverAllSection.getVisibility()==View.VISIBLE)
            {
                showAlertSingleButtonInfo("Please Select Proprietor Partner 2 PAN Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsPartner2PANSubmitted(flgPanPhotoPartner2);
            }
            if(flgPanPhotoPartner2==1 && ll_PANPatner2OverAllSection!=null && ll_PANPatner2OverAllSection.getVisibility()==View.VISIBLE)
            {
                if(et_PANNoOfPartner2!=null && !TextUtils.isEmpty(et_PANNoOfPartner2.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setPanNumber_Partner2(et_PANNoOfPartner2.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Partner 2 PAN Number.", et_PANNoOfPartner2);
                    return;
                }

                if (flgPanPhotoPartner2==0 && (PanPhotoPartner2_photo == null || PanPhotoPartner2_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload Partner 2 PAN Photo.");
                    return;
                }
            }
            else
            {
                tblPotentialDistributor.setPanNumber_Partner2("");
            }



            //PPartner Deed Selected Or Not
            if(flgPartnerDeed==-1 && ll_PartnerDeedOverAllSection!=null && ll_PartnerDeedOverAllSection.getVisibility()==View.VISIBLE)
            {
                showAlertSingleButtonInfo("Please Select Partner Deed Available Or Not?");
                return;
            }
            else
            {
                tblPotentialDistributor.setIsPartnerDeedSubmitted(flgPartnerDeed);
            }
            if(flgPartnerDeed==1 && ll_PartnerDeedOverAllSection!=null && ll_PartnerDeedOverAllSection.getVisibility()==View.VISIBLE)
            {
                if(et_PartnerDeed!=null && !TextUtils.isEmpty(et_PartnerDeed.getText().toString().trim()))
                {
                    /*if (et_PANNoOfFirm.getText().toString().length() != 15) {
                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);

                        return;
                    }
                    String firstTwoStr = gstno_txt.getText().toString().substring(0, 2);
                    Pattern p = Pattern.compile("[0-9]{2}");
                    Matcher m = p.matcher(firstTwoStr);
                    if (!m.find() || !m.group().equals(firstTwoStr)) {

                        showAlertSingleButtonInfoForValidation("Please enter proper GST Number", et_gstno);
                        return;
                    }
                    else
                    {*/
                    tblPotentialDistributor.setPartnerDeedNumber(et_PartnerDeed.getText().toString().trim());
                    /*}*/

                }
                else
                {
                    showAlertSingleButtonInfoForValidation("Please fill Partner Deed Number.", et_PartnerDeed);
                    return;
                }

                if (flgPartnerDeed==0 && (PartnerDeed_photo == null || PartnerDeed_photo.size() == 0)) {
                    showAlertSingleButtonInfo("Please Click or Upload Partner Deed PAN Photo.");
                    return;
                }
            }
            else
            {
                tblPotentialDistributor.setPartnerDeedNumber("");
            }
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

        Intent intent = new Intent(ActivityDistributorAppointment.this, SocutingDistributorSelection.class);

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


                Intent mMyServiceIntent = new Intent(ActivityDistributorAppointment.this, SyncJobService.class);
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

            // if(flgDistributorReplacement==0)
            if(tblPotentialDistributor.getIsOldDistributorReplaced()==0)
            {
                rg_DistributorReplacement.check(R.id.rb_DistributorReplacement_No);
            }
            if(tblPotentialDistributor.getIsOldDistributorReplaced()==1)
            {
                rg_DistributorReplacement.check(R.id.rb_DistributorReplacement_Yes);


                if(tblPotentialDistributor.getLocationCapturedFrom()==1)
                {
                    flgLocationFormfill=1;
                    rg_LocationFormfill.check(R.id.rbDistributor);
                }
                if(tblPotentialDistributor.getLocationCapturedFrom()==2)
                {
                    flgLocationFormfill=2;
                    rg_LocationFormfill.check(R.id.rbGodwon);
                }
                if(tblPotentialDistributor.getLocationCapturedFrom()==3)
                {
                    flgLocationFormfill=3;
                    rg_LocationFormfill.check(R.id.rbOther);
                }

                if(tblPotentialDistributor.getIsOldDistributorDFinalPaymentDone()==0)
                {
                    rg_FinalSettlementDone.check(R.id.rb_FinalSettlementDone_No);
                }
                if(tblPotentialDistributor.getIsOldDistributorDFinalPaymentDone()==1)
                {
                    rg_FinalSettlementDone.check(R.id.rb_FinalSettlementDone_Yes);
                }

                if(tblPotentialDistributor.getFlgDistributorSS()==0)
                {
                    rg_DistributorStockiest.check(R.id.rb_DistributorStockiest_Distributor);
                }
                if(tblPotentialDistributor.getFlgDistributorSS()==1)
                {
                    rg_DistributorStockiest.check(R.id.rb_DistributorStockiest_Stockiest);
                }

                if(tblPotentialDistributor.getIsNewLocation()==0)
                {
                    rg_OldOrNewLocation.check(R.id.rb_OldOrNewLocation_Old);
                }
                if(tblPotentialDistributor.getIsNewLocation()==1)
                {
                    rg_OldOrNewLocation.check(R.id.rb_OldOrNewLocation_New);
                }

                if(tblPotentialDistributor.getFlgTownDistributorSubD()==0)
                {
                    rg_TownDistributorSubD.check(R.id.rb_TownDistributorSubD_Distributor);
                }
                if(tblPotentialDistributor.getFlgTownDistributorSubD()==1)
                {
                    rg_TownDistributorSubD.check(R.id.rb_TownDistributorSubD_SubD);
                }
            }



            et_Dealer_Firm_Name.setText(tblPotentialDistributor.getDescr());
            et_ContactPerson.setText(tblPotentialDistributor.getContactPersonName());
            et_MobileNo.setText(tblPotentialDistributor.getContactPersonMobileNumber());
            et_teleNo.setText(tblPotentialDistributor.getTelephoneNo());
            et_emailID.setText(tblPotentialDistributor.getContactPersonEMailID());
            et_BankAccountNo.setText(tblPotentialDistributor.getBankAccountNumber());
            et_BankIFSCCode.setText(tblPotentialDistributor.getIFSCCode());
            et_BankAddress.setText(tblPotentialDistributor.getBankAddress());
            et_BusinessExpectedTons.setText(tblPotentialDistributor.getExpectedBusiness());
            et_IdealGodownSpaceRequired.setText(tblPotentialDistributor.getReqGodownSpace());
            et_GodownSpaceAgreedUpon.setText(tblPotentialDistributor.getAgreedGodownSpace());
            et_InvestmentAgreedUpon.setText(tblPotentialDistributor.getAgreedInvestment());
            et_IdealROIAsPerNorms.setText(tblPotentialDistributor.getIdealROI());
            et_ExpectedROIByDistributor.setText(tblPotentialDistributor.getExpectedROI());

            et_gdwnAreaSqFeet.setText(tblPotentialDistributor.getGodownAreaSqFt());
            et_MonthlyTurnOver.setText(tblPotentialDistributor.getMonthlyTurnOver());
            et_InvestmentInCompanyProducts.setText(""+tblPotentialDistributor.getCompanyProductInvestmentLacs());
            et_RetailerCreditLimit.setText(""+tblPotentialDistributor.getRetailerCreditLimit());
            tv_WorkingWithDistributorSince.setText(tblPotentialDistributor.getBusinessSince());
            flgIntrestedInDistributorShip=tblPotentialDistributor.getDistributorReady();


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



            if(tblPotentialDistributor.getOldDistriutorID()!=0)
            {
                int indexOldDistributor = 0;
                boolean isDistributorRequiredToBeSelected=false;
                for(TblSupplierMstrList tblSupplierMstrList:tblSupplierMstrLists)
                {
                    if(tblSupplierMstrList.getNodeID()==tblPotentialDistributor.getOldDistriutorID())
                    {
                        isDistributorRequiredToBeSelected=true;
                        SelectedOldDistributorID=tblSupplierMstrList.getNodeID();
                        SelectedOldDistributorName=tblSupplierMstrList.getDescr();
                        break;
                    }


                    indexOldDistributor++;
                }
                if(isDistributorRequiredToBeSelected)
                {
                    spinner_OldDistributor.setSelection(indexOldDistributor);

                }
                else
                {
                    spinner_OldDistributor.setSelection(0);
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



            et_GodwonAddress.setText(tblPotentialDistributor.getAddress_Godown());
            et_GodwonVillage.setText(tblPotentialDistributor.getCity_Godown());
            etGodwonPinCode.setText(tblPotentialDistributor.getPincode_Godown());
            et_Godwonstate.setText(tblPotentialDistributor.getState_Godown());
            et_GodwonDistrict.setText(tblPotentialDistributor.getDistrict_Godown());

            if(tblPotentialDistributor.getFlgProprietorPartner()==0)
            {
                rg_FirmType.check(R.id.rb_FirmType_Proprietorship);
            }
            if(tblPotentialDistributor.getFlgProprietorPartner()==1)
            {
                rg_FirmType.check(R.id.rb_DistributorStockiest_Partnership);
            }


            if(tblPotentialDistributor.getIsGSTDetailsSubmitted()==0)
            {
                rg_ID_gstCopy.check(R.id.rb_no_id_gstCopy);
            }
            if(tblPotentialDistributor.getIsGSTDetailsSubmitted()==1)
            {
                flgGstCopy_photo=1;
                rg_ID_gstCopy.check(R.id.rb_yes_id_gstCopy);
                et_gstno.setText(tblPotentialDistributor.getGSTNumber());
            }
            if(tblPotentialDistributor.getIsPANOfFirmSubmitted()==0)
            {
                rg_ID_PANOfFirm.check(R.id.rb_no_id_PANOfFirm);
            }
            if(tblPotentialDistributor.getIsPANOfFirmSubmitted()==1)
            {
                flgPANOfFirm=1;
                rg_ID_PANOfFirm.check(R.id.rb_yes_id_PANOfFirm);
                et_PANNoOfFirm.setText(tblPotentialDistributor.getPANNoOfFirm());
            }


            if(tblPotentialDistributor.getIsCheckGiven()==0)
            {
                rg_ID_cheque.check(R.id.rb_no_id_cheque);
            }
            if(tblPotentialDistributor.getIsCheckGiven()==1)
            {
                flgHasCheque=1;
                rg_ID_cheque.check(R.id.rb_yes_id_cheque);
                et_chequeno.setText(tblPotentialDistributor.getChequeNumber());
            }


            if(tblPotentialDistributor.getIsProprietorPanSubmited()==0)
            {
                rg_ID_ProprietorPANPhoto.check(R.id.rb_no_id_ProprietorPANPhoto);
            }
            if(tblPotentialDistributor.getIsProprietorPanSubmited()==1)
            {
                flgProprietorPANPhoto=1;
                rg_ID_ProprietorPANPhoto.check(R.id.rb_yes_id_ProprietorPANPhoto);
                et_PANNoOfProprietor.setText(tblPotentialDistributor.getProprietorPanNumber());
            }


            if(tblPotentialDistributor.getIsPartner1PANSubmitted()==0)
            {
                rg_ID_PanPhotoPartner1.check(R.id.rb_no_id_PanPhotoPartner1);
            }
            if(tblPotentialDistributor.getIsPartner1PANSubmitted()==1)
            {
                flgPanPhotoPartner1=1;
                rg_ID_PanPhotoPartner1.check(R.id.rb_yes_id_PanPhotoPartner1);
                et_PANNoOfPartner1.setText(tblPotentialDistributor.getPanNumber_Partner1());
            }
            if(tblPotentialDistributor.getIsPartner2PANSubmitted()==0)
            {
                rg_ID_PanPhotoPartner2.check(R.id.rb_no_id_PanPhotoPartner2);
            }
            if(tblPotentialDistributor.getIsPartner2PANSubmitted()==1)
            {
                flgPanPhotoPartner2=1;
                rg_ID_PanPhotoPartner2.check(R.id.rb_yes_id_PanPhotoPartner2);
                et_PANNoOfPartner2.setText(tblPotentialDistributor.getPanNumber_Partner2());
            }


            if(tblPotentialDistributor.getIsPartnerDeedSubmitted()==0)
            {
                rg_ID_PartnerDeed.check(R.id.rb_no_id_PartnerDeed);
            }
            if(tblPotentialDistributor.getIsPartnerDeedSubmitted()==1)
            {
                flgPartnerDeed=1;
                rg_ID_PartnerDeed.check(R.id.rb_yes_id_PartnerDeed);
                if(!tblPotentialDistributor.getPartnerDeedNumber().equals("0"))
                    et_PartnerDeed.setText(tblPotentialDistributor.getPartnerDeedNumber());
            }

        }
        if(tblPotentialDistributor.getFlgViewOrEdit()==2)
        {
            spinner_OldDistributor.setEnabled(false);
            rg_DistributorReplacement.setEnabled(false);
            rg_FinalSettlementDone.setEnabled(false);
            rg_LocationFormfill.setEnabled(false);
            rg_DistributorStockiest.setEnabled(false);
            rg_OldOrNewLocation.setEnabled(false);
            rg_TownDistributorSubD.setEnabled(false);
            et_BankAccountNo.setEnabled(false);
            et_BankIFSCCode.setEnabled(false);
            et_BankAddress.setEnabled(false);
            et_BusinessExpectedTons.setEnabled(false);
            et_IdealGodownSpaceRequired.setEnabled(false);
            et_GodownSpaceAgreedUpon.setEnabled(false);
            et_InvestmentAgreedUpon.setEnabled(false);
            et_IdealROIAsPerNorms.setEnabled(false);
            et_ExpectedROIByDistributor.setEnabled(false);

            et_Dealer_Firm_Name.setEnabled(false);
            et_ContactPerson.setEnabled(false);
            et_MobileNo.setEnabled(false);
            et_teleNo.setEnabled(false);
            et_emailID.setEnabled(false);
            et_gstno.setEnabled(false);
            et_gdwnAreaSqFeet.setEnabled(false);
            et_MonthlyTurnOver.setEnabled(false);
            et_AreaCovered.setEnabled(false);
            et_NoOfRetailersCovered.setEnabled(false);
            et_InvestmentInCompanyProducts.setEnabled(false);
            et_RetailerCreditLimit.setEnabled(false);
            tv_WorkingWithDistributorSince.setEnabled(false);

            rg_IntrestedInDistributorShip.setEnabled(false);

            et_GodwonAddress.setEnabled(false);
            et_GodwonVillage.setEnabled(false);
            etGodwonPinCode.setEnabled(false);
            et_Godwonstate.setEnabled(false);
            et_GodwonDistrict.setEnabled(false);

            rg_FirmType.setEnabled(false);
            rg_ID_gstCopy.setEnabled(false);
            et_gstno.setEnabled(false);
            rg_ID_PANOfFirm.setEnabled(false);
            et_PANNoOfFirm.setEnabled(false);
            rg_ID_cheque.setEnabled(false);
            et_chequeno.setEnabled(false);
            rg_ID_ProprietorPANPhoto.setEnabled(false);
            et_PANNoOfProprietor.setEnabled(false);
            rg_ID_PanPhotoPartner1.setEnabled(false);
            et_PANNoOfPartner1.setEnabled(false);
            rg_ID_PanPhotoPartner2.setEnabled(false);
            et_PANNoOfPartner2.setEnabled(false);
            rg_ID_PartnerDeed.setEnabled(false);
            et_PartnerDeed.setEnabled(false);

            btn_chequePhoto.setEnabled(false);
            btn_DistributorPhoto.setEnabled(false);
            btn_selfiwithDistributor_photo.setEnabled(false);
            btn_ProprietorPANPhoto.setEnabled(false);
            btn_PanPhotoPartner1.setEnabled(false);
            btn_PanPhotoPartner2.setEnabled(false);
            btn_PartnerDeed.setEnabled(false);
            btn_gstCopy_photo.setEnabled(false);
            btn_PANNoOfFirm_photo.setEnabled(false);
            ll_overAllSignatureSection.setVisibility(View.GONE);
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


        //   ll_gst_num.setVisibility(View.GONE);
        //gstno_txt.setText(tblSpokeProfile.getGstValue());


        // SpokeIDForImg = "" + tblSpokeProfile.getNodeType();



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


    private int findBackFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                File pictureFile = new File(picturePath);
                File CopiedPictureFile = new File(picturePath);
                try {
                    CopiedPictureFile = copyFile(pictureFile, getOutputMediaFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String pictureName = CopiedPictureFile.getName();
                List<String> details_cheque = new ArrayList<>();
                List<String> details_gstCopyPhoto = new ArrayList<>();
                List<String> details_ProprietorPANPhoto = new ArrayList<>();
                List<String> details_PanPhotoPartner1 = new ArrayList<>();
                List<String> details_PanPhotoPartner2 = new ArrayList<>();
                List<String> details_PartnerDeed = new ArrayList<>();


                if (flgPhotoButton.equals("1")) {
                    cheque_photo.add(picturePath);

                    details_cheque.add(pictureName); //ImageName
                    details_cheque.add("5"); //ImageType //Cheque
                    details_cheque.add("3"); //Sstat
                    details_cheque.add(fnGetClickPhotoDateTime()); //Time
                    details_cheque.add(ScoutingDistributorID);  //StoreID


                    photo_details_cheque.put(picturePath, details_cheque);
                    PhotoAdapterCheque photoAdapter = new PhotoAdapterCheque(ActivityDistributorAppointment.this, cheque_photo, "1");
                    cheque_photo_list.setAdapter(photoAdapter);
                }

                if (flgPhotoButton.equals("2")) {
                    gstCopy_photo.add(pictureFile.getAbsolutePath());

                    details_gstCopyPhoto.add(pictureFile.getName()); //ImageName
                    details_gstCopyPhoto.add("2"); //ImageType //Cheque
                    details_gstCopyPhoto.add("3"); //Sstat
                    details_gstCopyPhoto.add(fnGetClickPhotoDateTime()); //Time
                    details_gstCopyPhoto.add(ScoutingDistributorID);  //StoreID


                    photo_details_gstCopy_photo.put(pictureFile.getAbsolutePath(), details_gstCopyPhoto);

                    PhotoAdapterGST photoAdapter = new PhotoAdapterGST(ActivityDistributorAppointment.this, gstCopy_photo, "2");
                    gstCopy_photo_list.setAdapter(photoAdapter);
                }

                if (flgPhotoButton.equals("3")) {
                    ProprietorPANPhoto_photo.add(pictureFile.getAbsolutePath());

                    details_ProprietorPANPhoto.add(pictureFile.getName()); //ImageName
                    details_ProprietorPANPhoto.add("3"); //ImageType //Cheque
                    details_ProprietorPANPhoto.add("3"); //Sstat
                    details_ProprietorPANPhoto.add(fnGetClickPhotoDateTime()); //Time
                    details_ProprietorPANPhoto.add(ScoutingDistributorID);  //StoreID


                    photo_details_ProprietorPANPhoto.put(pictureFile.getAbsolutePath(), details_ProprietorPANPhoto);

                    PhotoAdapterProprietorPAN photoAdapter = new PhotoAdapterProprietorPAN(ActivityDistributorAppointment.this, ProprietorPANPhoto_photo, "3");
                    ProprietorPANPhoto_photo_list.setAdapter(photoAdapter);
                }

                if (flgPhotoButton.equals("4")) {
                    PanPhotoPartner1_photo.add(pictureFile.getAbsolutePath());

                    details_PanPhotoPartner1.add(pictureFile.getName()); //ImageName
                    details_PanPhotoPartner1.add("4"); //ImageType //Cheque
                    details_PanPhotoPartner1.add("3"); //Sstat
                    details_PanPhotoPartner1.add(fnGetClickPhotoDateTime()); //Time
                    details_PanPhotoPartner1.add(ScoutingDistributorID);  //StoreID


                    photo_details_PanPhotoPartner1.put(pictureFile.getAbsolutePath(), details_PanPhotoPartner1);

                    PhotoAdapterPANPatner1 photoAdapter = new PhotoAdapterPANPatner1(ActivityDistributorAppointment.this, PanPhotoPartner1_photo, "4");
                    PanPhotoPartner1_photo_list.setAdapter(photoAdapter);
                }

                if (flgPhotoButton.equals("5")) {
                    PanPhotoPartner2_photo.add(pictureFile.getAbsolutePath());

                    details_PanPhotoPartner2.add(pictureFile.getName()); //ImageName
                    details_PanPhotoPartner2.add("5"); //ImageType //Cheque
                    details_PanPhotoPartner2.add("3"); //Sstat
                    details_PanPhotoPartner2.add(fnGetClickPhotoDateTime()); //Time
                    details_PanPhotoPartner2.add(ScoutingDistributorID);  //StoreID


                    photo_details_PanPhotoPartner2.put(pictureFile.getAbsolutePath(), details_PanPhotoPartner2);

                    PhotoAdapterPANPANPatner2 photoAdapter = new PhotoAdapterPANPANPatner2(ActivityDistributorAppointment.this, PanPhotoPartner2_photo, "5");
                    PanPhotoPartner2_photo_list.setAdapter(photoAdapter);
                }

                if (flgPhotoButton.equals("6")) {
                    PartnerDeed_photo.add(pictureFile.getAbsolutePath());

                    details_PartnerDeed.add(pictureFile.getName()); //ImageName
                    details_PartnerDeed.add("6"); //ImageType //Cheque
                    details_PartnerDeed.add("3"); //Sstat
                    details_PartnerDeed.add(fnGetClickPhotoDateTime()); //Time
                    details_PartnerDeed.add(ScoutingDistributorID);  //StoreID


                    photo_details_PartnerDeed.put(pictureFile.getAbsolutePath(), details_PartnerDeed);

                    PhotoAdapterPANPartnerDeed photoAdapter = new PhotoAdapterPANPartnerDeed(ActivityDistributorAppointment.this, PartnerDeed_photo, "6");
                    PartnerDeed_photo_list.setAdapter(photoAdapter);
                }



                c.close();
                // Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));


            }
        }
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
        fnLoadOldDistributorList();
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
        mapFrag.getMapAsync(ActivityDistributorAppointment.this);
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

            pDialog2 = ProgressDialog.show(ActivityDistributorAppointment.this, getText(R.string.PleaseWaitMsg), getText(R.string.genTermProcessingRequest), true);
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
    public void fnLoadOldDistributorList()
    {
        tblSupplierMstrLists = new ArrayList<TblSupplierMstrList>();
        tblSupplierMstrLists.clear();
        tblSupplierMstrLists=mDataSource.fnGetSupplierListMaster(1);//tblPotentialDistributor.getNodeID()
        if(tblSupplierMstrLists!=null && tblSupplierMstrLists.size()>0)
        {
            DistributorList_names = new String[tblSupplierMstrLists.size()];
            int index=0;
            for(TblSupplierMstrList tblSupplierMstrList:tblSupplierMstrLists)
            {
                DistributorList_names[index] = tblSupplierMstrList.getDescr().toString();
                index = index + 1;
            }
        }
        ArrayAdapter adapterRouteList = new ArrayAdapter(this, R.layout.spinner_item_route_store_selection, DistributorList_names);
        adapterRouteList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_OldDistributor.setAdapter(adapterRouteList);
        if(tblPotentialDistributor.getFlgViewOrEdit()==2)
        {
            spinner_OldDistributor.setEnabled(false);
        }
        spinner_OldDistributor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                TblSupplierMstrList tblSupplierMstrListList=stream(tblSupplierMstrLists).where(p->p.getDescr().equals(arg0.getItemAtPosition(arg2).toString())).first();

                if(tblSupplierMstrListList.getNodeID()==0)
                {
                    SelectedOldDistributorID=0;
                    SelectedOldDistributorName="Select Old Distributor";

                }
                else
                {
                    SelectedOldDistributorID=tblSupplierMstrListList.getNodeID();
                    SelectedOldDistributorName=tblSupplierMstrListList.getDescr();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        String VisitStartTS = TimeUtils.getNetworkDateTime(ActivityDistributorAppointment.this, TimeUtils.DATE_TIME_FORMAT);

        String cxz;
        cxz = UUID.randomUUID().toString();


        StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

        String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();

        String IMEIid = AppUtils.getToken(ActivityDistributorAppointment.this);
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
        android.app.AlertDialog.Builder alertDialogSyncError = new android.app.AlertDialog.Builder(ActivityDistributorAppointment.this);
        alertDialogSyncError.setTitle(getText(R.string.genTermInformation));
        alertDialogSyncError.setCancelable(false);

        alertDialogSyncError.setMessage("Submitted Successfully");


        alertDialogSyncError.setNeutralButton(getText(R.string.AlertDialogOkButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        Intent intent = new Intent(ActivityDistributorAppointment.this, SocutingDistributorSelection.class);

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
        android.app.AlertDialog.Builder alertDialogSyncError = new android.app.AlertDialog.Builder(ActivityDistributorAppointment.this);
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

    @OnClick(R.id.btn_chequePhoto)
    public void ClickChequePhoto() {
        flgPhotoButton = "1";
        if (cheque_photo.size() == 2) {
            showAlertSingleButtonInfo( "Cannot upload more than 2 photos");
        } else {
            uploadTakeIDProof("1");
        }
    }

    @OnClick(R.id.btn_gstCopy_photo)
    public void ClickGSTCopyPhoto() {
        flgPhotoButton = "2";
        if (gstCopy_photo.size() == 2) {
            showAlertSingleButtonInfo( "Cannot upload more than 2 photos");
        } else {
            uploadTakeIDProof("2");
        }
    }

    @OnClick(R.id.btn_ProprietorPANPhoto)
    public void Click_ProprietorPANPhoto() {
        flgPhotoButton = "3";
        if (gstCopy_photo.size() == 2) {
            showAlertSingleButtonInfo( "Cannot upload more than 2 photos");
        } else {
            uploadTakeIDProof("3");
        }
    }

    @OnClick(R.id.btn_PanPhotoPartner1)
    public void Click_PanPhotoPartner1() {
        flgPhotoButton = "4";
        if (PanPhotoPartner1_photo.size() == 2) {
            showAlertSingleButtonInfo( "Cannot upload more than 2 photos");
        } else {
            uploadTakeIDProof("4");
        }
    }

    @OnClick(R.id.btn_PanPhotoPartner2)
    public void Click_PanPhotoPartner2() {
        flgPhotoButton = "5";
        if (PanPhotoPartner2_photo.size() == 2) {
            showAlertSingleButtonInfo( "Cannot upload more than 2 photos");
        } else {
            uploadTakeIDProof("5");
        }
    }

    @OnClick(R.id.btn_PartnerDeed)
    public void Click_PartnerDeed() {
        flgPhotoButton = "6";
        if (PartnerDeed_photo.size() == 2) {
            showAlertSingleButtonInfo( "Cannot upload more than 2 photos");
        } else {
            uploadTakeIDProof("6");
        }
    }

    @OnClick(R.id.btn_PANNoOfFirm_photo)
    public void Click_PANNoOfFirm() {
        flgPhotoButton = "7";
        if (PANNoOfFirm_photo.size() == 2) {
            showAlertSingleButtonInfo( "Cannot upload more than 2 photos");
        } else {
            uploadTakeIDProof("7");
        }
    }


    @OnClick(R.id.btn_Distributor_photo)
    public void ClickDistributorPhoto() {
        flgDistributorPhotoButton = "1";
        if (Distributor_photo.size() == 2) {
            showAlertSingleButtonInfo( "Cannot upload more than 2 photos");
        } else {
            uploadTakeIDProof("11");
        }
    }

    @OnClick(R.id.btn_selfiwithDistributor_photo)
    public void ClickSelfiWithDistributorPhoto() {
        flgSelfiwithdistributorPhotoButton = "1";
        if (Selfiwithdistributor_photo.size() == 2) {
            showAlertSingleButtonInfo( "Cannot upload more than 2 photos");
        } else {
            uploadTakeIDProof("12");
        }
    }
    //

    public void uploadTakeIDProof(String flgPhototypebtn) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ActivityDistributorAppointment.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    openCamera(flgPhototypebtn);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void openCamera(String flgWhichButtonCliked) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        dialog = new Dialog(ActivityDistributorAppointment.this);
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
            mPreview = new CameraPreview(ActivityDistributorAppointment.this, mCamera);
            cameraPreview.addView(mPreview);
        }
        //onResume code
        if (!hasCamera(ActivityDistributorAppointment.this)) {
            Toast toast = Toast.makeText(ActivityDistributorAppointment.this, getText(R.string.txtNoCamera), Toast.LENGTH_LONG);
            toast.show();
        }

        if (mCamera == null) {
            //if the front facing camera does not exist
            if(flgWhichButtonCliked.equals("12")) {

                if (findBackFacingCamera() < 0) {
                    Toast.makeText(ActivityDistributorAppointment.this, getText(R.string.txtNoBackCamera), Toast.LENGTH_LONG).show();
                    switchCamera.setVisibility(View.GONE);
                }
                else if (findFrontFacingCamera() < 0) {
                    Toast.makeText(ActivityDistributorAppointment.this, getText(R.string.txtNoFrontCamera), Toast.LENGTH_LONG).show();
                    switchCamera.setVisibility(View.GONE);
                }
            }
            else {
                if (findFrontFacingCamera() < 0) {
                    Toast.makeText(ActivityDistributorAppointment.this, getText(R.string.txtNoFrontCamera), Toast.LENGTH_LONG).show();
                    switchCamera.setVisibility(View.GONE);
                }
            }

            //mCamera = Camera.open(findBackFacingCamera());

			/*if(mCamera!=null){
				mCamera.release();
				mCamera=null;
			}*/
            if(flgWhichButtonCliked.equals("12")) {
                try {
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                }
                catch (Exception ex) {
                    mCamera=null;
                    if(mCamera==null){
                        mCamera=Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    }
                }
            }
            else {

                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            }
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

            setCameraDisplayOrientation(ActivityDistributorAppointment.this, Camera.CameraInfo.CAMERA_FACING_BACK, mCamera);
            mPicture = getPictureCallback(flgWhichButtonCliked);
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
                mCamera=null;
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

    private Camera.PictureCallback getPictureCallback(String flgWhichButtonCliked) {
        Camera.PictureCallback picture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //make a new picture file
                File pictureFile = getOutputMediaFile();

                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                isLighOn = false;


                List<String> details_cheque = new ArrayList<>();
                List<String> details_gstCopyPhoto = new ArrayList<>();
                List<String> details_ProprietorPANPhoto = new ArrayList<>();
                List<String> details_PanPhotoPartner1 = new ArrayList<>();
                List<String> details_PanPhotoPartner2 = new ArrayList<>();
                List<String> details_PartnerDeed = new ArrayList<>();
                List<String> details_PANNoOfFirm = new ArrayList<>();

                List<String> details_Distributor = new ArrayList<>();
                List<String> details_Selfiistributor = new ArrayList<>();


                if (pictureFile == null) {
                    return;
                }
                try {
                    //write the file
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();


                    if (flgWhichButtonCliked.equals("1")) {
                        cheque_photo.add(pictureFile.getAbsolutePath());

                        details_cheque.add(pictureFile.getName()); //ImageName
                        details_cheque.add("1"); //ImageType //Cheque
                        details_cheque.add("3"); //Sstat
                        details_cheque.add(fnGetClickPhotoDateTime()); //Time
                        details_cheque.add(ScoutingDistributorID);  //StoreID


                        photo_details_cheque.put(pictureFile.getAbsolutePath(), details_cheque);
                        cheque_photo_list.removeAllViews();
                        PhotoAdapterCheque photoAdapter = new PhotoAdapterCheque(ActivityDistributorAppointment.this, cheque_photo, "1");
                        cheque_photo_list.setAdapter(photoAdapter);
                    }

                    if (flgWhichButtonCliked.equals("2")) {
                        gstCopy_photo.add(pictureFile.getAbsolutePath());

                        details_gstCopyPhoto.add(pictureFile.getName()); //ImageName
                        details_gstCopyPhoto.add("2"); //ImageType //Cheque
                        details_gstCopyPhoto.add("3"); //Sstat
                        details_gstCopyPhoto.add(fnGetClickPhotoDateTime()); //Time
                        details_gstCopyPhoto.add(ScoutingDistributorID);  //StoreID


                        photo_details_gstCopy_photo.put(pictureFile.getAbsolutePath(), details_gstCopyPhoto);

                        PhotoAdapterGST photoAdapter = new PhotoAdapterGST(ActivityDistributorAppointment.this, gstCopy_photo, "2");
                        gstCopy_photo_list.setAdapter(photoAdapter);
                    }

                    if (flgWhichButtonCliked.equals("3")) {
                        ProprietorPANPhoto_photo.add(pictureFile.getAbsolutePath());

                        details_ProprietorPANPhoto.add(pictureFile.getName()); //ImageName
                        details_ProprietorPANPhoto.add("3"); //ImageType //Cheque
                        details_ProprietorPANPhoto.add("3"); //Sstat
                        details_ProprietorPANPhoto.add(fnGetClickPhotoDateTime()); //Time
                        details_ProprietorPANPhoto.add(ScoutingDistributorID);  //StoreID


                        photo_details_ProprietorPANPhoto.put(pictureFile.getAbsolutePath(), details_ProprietorPANPhoto);

                        PhotoAdapterProprietorPAN photoAdapter = new PhotoAdapterProprietorPAN(ActivityDistributorAppointment.this, ProprietorPANPhoto_photo, "3");
                        ProprietorPANPhoto_photo_list.setAdapter(photoAdapter);
                    }

                    if (flgWhichButtonCliked.equals("4")) {
                        PanPhotoPartner1_photo.add(pictureFile.getAbsolutePath());

                        details_PanPhotoPartner1.add(pictureFile.getName()); //ImageName
                        details_PanPhotoPartner1.add("4"); //ImageType //Cheque
                        details_PanPhotoPartner1.add("3"); //Sstat
                        details_PanPhotoPartner1.add(fnGetClickPhotoDateTime()); //Time
                        details_PanPhotoPartner1.add(ScoutingDistributorID);  //StoreID


                        photo_details_PanPhotoPartner1.put(pictureFile.getAbsolutePath(), details_PanPhotoPartner1);

                        PhotoAdapterPANPatner1 photoAdapter = new PhotoAdapterPANPatner1(ActivityDistributorAppointment.this, PanPhotoPartner1_photo, "4");
                        PanPhotoPartner1_photo_list.setAdapter(photoAdapter);
                    }

                    if (flgWhichButtonCliked.equals("5")) {
                        PanPhotoPartner2_photo.add(pictureFile.getAbsolutePath());

                        details_PanPhotoPartner2.add(pictureFile.getName()); //ImageName
                        details_PanPhotoPartner2.add("5"); //ImageType //Cheque
                        details_PanPhotoPartner2.add("3"); //Sstat
                        details_PanPhotoPartner2.add(fnGetClickPhotoDateTime()); //Time
                        details_PanPhotoPartner2.add(ScoutingDistributorID);  //StoreID


                        photo_details_PanPhotoPartner2.put(pictureFile.getAbsolutePath(), details_PanPhotoPartner2);

                        PhotoAdapterPANPANPatner2 photoAdapter = new PhotoAdapterPANPANPatner2(ActivityDistributorAppointment.this, PanPhotoPartner2_photo, "5");
                        PanPhotoPartner2_photo_list.setAdapter(photoAdapter);
                    }

                    if (flgWhichButtonCliked.equals("6")) {
                        PartnerDeed_photo.add(pictureFile.getAbsolutePath());

                        details_PartnerDeed.add(pictureFile.getName()); //ImageName
                        details_PartnerDeed.add("6"); //ImageType //Cheque
                        details_PartnerDeed.add("3"); //Sstat
                        details_PartnerDeed.add(fnGetClickPhotoDateTime()); //Time
                        details_PartnerDeed.add(ScoutingDistributorID);  //StoreID


                        photo_details_PartnerDeed.put(pictureFile.getAbsolutePath(), details_PartnerDeed);

                        PhotoAdapterPANPartnerDeed photoAdapter = new PhotoAdapterPANPartnerDeed(ActivityDistributorAppointment.this, PartnerDeed_photo, "6");
                        PartnerDeed_photo_list.setAdapter(photoAdapter);
                    }

                    if (flgWhichButtonCliked.equals("7")) {
                        PANNoOfFirm_photo.add(pictureFile.getAbsolutePath());

                        details_PANNoOfFirm.add(pictureFile.getName()); //ImageName
                        details_PANNoOfFirm.add("7"); //ImageType //Cheque
                        details_PANNoOfFirm.add("3"); //Sstat
                        details_PANNoOfFirm.add(fnGetClickPhotoDateTime()); //Time
                        details_PANNoOfFirm.add(ScoutingDistributorID);  //StoreID


                        photo_details_PANNoOfFirm.put(pictureFile.getAbsolutePath(), details_PANNoOfFirm);

                        PhotoAdapterPANOfFirm photoAdapter = new PhotoAdapterPANOfFirm(ActivityDistributorAppointment.this, PANNoOfFirm_photo, "7");
                        PANOfFirm_photo_list.setAdapter(photoAdapter);
                    }

                    if (flgWhichButtonCliked.equals("11")) {
                        Distributor_photo.add(pictureFile.getAbsolutePath());

                        details_Distributor.add(pictureFile.getName()); //ImageName
                        details_Distributor.add("11"); //ImageType //Cheque
                        details_Distributor.add("3"); //Sstat
                        details_Distributor.add(fnGetClickPhotoDateTime()); //Time
                        details_Distributor.add(ScoutingDistributorID);  //StoreID


                        photo_details_distributor.put(pictureFile.getAbsolutePath(), details_Distributor);

                        PhotoAdapter photoAdapter = new PhotoAdapter(ActivityDistributorAppointment.this, Distributor_photo, "11");
                        distributorCopy_photo_list.setAdapter(photoAdapter);
                    }

                    if (flgWhichButtonCliked.equals("12")) {
                        Selfiwithdistributor_photo.add(pictureFile.getAbsolutePath());

                        details_Selfiistributor.add(pictureFile.getName()); //ImageName
                        details_Selfiistributor.add("12"); //ImageType //Cheque
                        details_Selfiistributor.add("3"); //Sstat
                        details_Selfiistributor.add(fnGetClickPhotoDateTime()); //Time
                        details_Selfiistributor.add(ScoutingDistributorID);  //StoreID


                        photo_details_selfiwithdistributor.put(pictureFile.getAbsolutePath(), details_Selfiistributor);

                        PhotoAdapter photoAdapter = new PhotoAdapter(ActivityDistributorAppointment.this, Selfiwithdistributor_photo, "12");
                        selfiwithdistributorCopy_photo_list.setAdapter(photoAdapter);
                    }



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

                        // Convert ByteArray to Bitmap::\
                        //
                        long syncTIMESTAMP = System.currentTimeMillis();
                        Date dateobj = new Date(syncTIMESTAMP);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                        // String clkdTime = AppUtils.doGetTime("dd-MMM-yyyy HH:mm:ss");


//
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

    public String fnGetClickPhotoDateTime() {
        String clickedTime = AppUtils.doGetTime("dd-MMM-yyyy HH:mm:ss");
        return clickedTime;
    }
    @Override
    public void imagetype(String flgPhototype) {

        if (flgPhototype.equals("1")) {
            for (String path_cheque : cheque_photo) {
                if (photo_details_cheque.containsKey(path_cheque)) {
                    photo_saving.put(path_cheque, photo_details_cheque.get(path_cheque));
                }
            }


        }

        if (flgPhototype.equals("2")) {
            for (String path_gstCopyPhoto : gstCopy_photo) {
                if (photo_details_gstCopy_photo.containsKey(path_gstCopyPhoto)) {
                    photo_saving.put(path_gstCopyPhoto, photo_details_gstCopy_photo.get(path_gstCopyPhoto));
                }
            }


        }

        if (flgPhototype.equals("3")) {
            for (String path_gProprietorPANPhoto : ProprietorPANPhoto_photo) {
                if (photo_details_ProprietorPANPhoto.containsKey(path_gProprietorPANPhoto)) {
                    photo_saving.put(path_gProprietorPANPhoto, photo_details_ProprietorPANPhoto.get(path_gProprietorPANPhoto));
                }
            }
        }

        if (flgPhototype.equals("4")) {
            for (String path_PanPhotoPartner1 : PanPhotoPartner1_photo) {
                if (photo_details_PanPhotoPartner1.containsKey(path_PanPhotoPartner1)) {
                    photo_saving.put(path_PanPhotoPartner1, photo_details_PanPhotoPartner1.get(path_PanPhotoPartner1));
                }
            }
        }
        if (flgPhototype.equals("5")) {
            for (String path_PanPhotoPartner2 : PanPhotoPartner2_photo) {
                if (photo_details_PanPhotoPartner2.containsKey(path_PanPhotoPartner2)) {
                    photo_saving.put(path_PanPhotoPartner2, photo_details_PanPhotoPartner2.get(path_PanPhotoPartner2));
                }
            }
        }

        if (flgPhototype.equals("6")) {
            for (String path_PartnerDeed : PartnerDeed_photo) {
                if (photo_details_PartnerDeed.containsKey(path_PartnerDeed)) {
                    photo_saving.put(path_PartnerDeed, photo_details_PartnerDeed.get(path_PartnerDeed));
                }
            }
        }

        if (flgPhototype.equals("7")) {
            for (String path_PANNoOfFirm : PANNoOfFirm_photo) {
                if (photo_details_PANNoOfFirm.containsKey(path_PANNoOfFirm)) {
                    photo_saving.put(path_PANNoOfFirm, photo_details_PANNoOfFirm.get(path_PANNoOfFirm));
                }
            }
        }

        if (flgPhototype.equals("11")) {
            for (String path_DistributorPhoto : Selfiwithdistributor_photo) {
                if (photo_details_distributor.containsKey(path_DistributorPhoto)) {
                    photo_saving.put(path_DistributorPhoto, photo_details_PANNoOfFirm.get(path_DistributorPhoto));
                }
            }
        }

        if (flgPhototype.equals("12")) {
            for (String path_SelfiwithDistributor : Selfiwithdistributor_photo) {
                if (photo_details_selfiwithdistributor.containsKey(path_SelfiwithDistributor)) {
                    photo_saving.put(path_SelfiwithDistributor, photo_details_selfiwithdistributor.get(path_SelfiwithDistributor));
                }
            }
        }

    }
    @Override
    public void delPhoto(List<String> photo, String flgPhotoType) {


        if (flgPhotoType.equals("1")) {
            this.cheque_photo = photo;
            this.flgPhototype = flgPhotoType;
        }
        if (flgPhotoType.equals("2")) {
            this.gstCopy_photo = photo;
            this.flgPhototype = flgPhotoType;
        }
        if (flgPhotoType.equals("3")) {
            this.ProprietorPANPhoto_photo = photo;
            this.flgPhototype = flgPhotoType;
        }

        if (flgPhotoType.equals("4")) {
            this.PanPhotoPartner1_photo = photo;
            this.flgPhototype = flgPhotoType;
        }
        if (flgPhotoType.equals("5")) {
            this.PanPhotoPartner2_photo = photo;
            this.flgPhototype = flgPhotoType;
        }
        if (flgPhotoType.equals("6")) {
            this.PartnerDeed_photo = photo;
            this.flgPhototype = flgPhotoType;
        }

        if (flgPhotoType.equals("7")) {
            this.PANNoOfFirm_photo = photo;
            this.flgPhototype = flgPhotoType;
        }


        if (flgPhotoType.equals("11")) {
            this.Distributor_photo = photo;
            this.flgPhototype = flgPhotoType;
        }

        if (flgPhotoType.equals("12")) {
            this.Selfiwithdistributor_photo = photo;
            this.flgPhototype = flgPhotoType;
        }

    }
    public static File copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;
        File PictureFile;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
            PictureFile = destFile;
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }

        return PictureFile;
    }
    public void signatureAllCodeASM() {
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }
        mContentASM = (LinearLayout) findViewById(R.id.linearLayoutASMSign);
        mSignature = new signatureASM(getApplicationContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContentASM.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getsignASM = (Button) findViewById(R.id.getsignASM);
        //by default disable it when , person do sign it will enable
        getsignASM.setEnabled(false);
        clearASMSign = (Button) findViewById(R.id.clearASMSign);
        cancelASMSign = (Button) findViewById(R.id.cancelASMSign);
        viewASM = mContentASM;

        clearASMSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                getsignASM.setEnabled(false);
                signOrNotASM = false;
            }
        });

        getsignASM.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Log.v("log_tag", "Panel Saved");
                viewASM.setDrawingCacheEnabled(true);
                mSignature.save(viewASM, ImgPathASMSign);

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.txtSuccessfullySaved), Toast.LENGTH_SHORT).show();
                // Calling the same class
                //  recreate();
            }
        });

        cancelASMSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Canceled");

                // Calling the same class
                //   recreate();
            }
        });
        transparent_imageASM = (ImageView) findViewById(R.id.transparent_imageASM);
        // scrollViewParentOfMap = (ScrollView) findViewById(R.id.scrollViewParentOfMap);

        transparent_imageASM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scroll_view.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scroll_view.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scroll_view.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });
    }
    public void signatureAllCodeTSI() {
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }
        mContentTSI = (LinearLayout) findViewById(R.id.linearLayoutTSISign);
        mSignatureTSI = new signatureTSI(getApplicationContext(), null);
        mSignatureTSI.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContentTSI.addView(mSignatureTSI, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getsignTSI = (Button) findViewById(R.id.getsignTSI);
        //by default disable it when , person do sign it will enable
        getsignTSI.setEnabled(false);
        clearTSISign = (Button) findViewById(R.id.clearTSISign);
        cancelTSISign = (Button) findViewById(R.id.cancelTSISign);
        viewTSI = mContentTSI;

        clearTSISign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Cleared");
                mSignatureTSI.clear();
                getsignTSI.setEnabled(false);
                signOrNotTSI = false;
            }
        });

        getsignTSI.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Log.v("log_tag", "Panel Saved");
                viewTSI.setDrawingCacheEnabled(true);
                mSignatureTSI.save(viewTSI, ImgPathTSISign);

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.txtSuccessfullySaved), Toast.LENGTH_SHORT).show();
                // Calling the same class
                //  recreate();
            }
        });

        cancelTSISign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Canceled");

                // Calling the same class
                //   recreate();
            }
        });
        transparent_imageTSI = (ImageView) findViewById(R.id.transparent_imageTSI);
        //scrollViewParentOfMap = (ScrollView) findViewById(R.id.scrollViewParentOfMap);

        transparent_imageTSI.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scroll_view.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scroll_view.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scroll_view.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });
    }

    public void signatureAllCodeDistributor() {
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }
        mContentDistributor = (LinearLayout) findViewById(R.id.linearLayoutDistributorSign);
        mSignatureDistributor = new signatureDistributor(getApplicationContext(), null);
        mSignatureDistributor.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContentDistributor.addView(mSignatureDistributor, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getsignDistributor = (Button) findViewById(R.id.getsignDistributor);
        //by default disable it when , person do sign it will enable
        getsignDistributor.setEnabled(false);
        clearDistributorSign = (Button) findViewById(R.id.clearDistributorSign);
        cancelDistributorSign = (Button) findViewById(R.id.cancelDistributorSign);
        viewDistribuotr = mContentDistributor;

        clearDistributorSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Cleared");
                mSignatureDistributor.clear();
                getsignDistributor.setEnabled(false);
                signOrNotDistributor = false;
            }
        });

        getsignDistributor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Log.v("log_tag", "Panel Saved");
                viewDistribuotr.setDrawingCacheEnabled(true);
                mSignatureDistributor.save(viewDistribuotr, ImgPathDistributorSign);

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.txtSuccessfullySaved), Toast.LENGTH_SHORT).show();
                // Calling the same class
                //  recreate();
            }
        });

        cancelDistributorSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Canceled");

                // Calling the same class
                //   recreate();
            }
        });
        transparent_imageDistributor = (ImageView) findViewById(R.id.transparent_imageDistributor);
        //scrollViewParentOfMap = (ScrollView) findViewById(R.id.scrollViewParentOfMap);

        transparent_imageDistributor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scroll_view.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scroll_view.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scroll_view.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });
    }

    public class signatureASM extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signatureASM(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());

            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(mContentASM.getWidth(), mContentASM.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            getsignASM.setEnabled(true);
            signOrNotASM = true;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    public class signatureTSI extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signatureTSI(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());

            if (bitmapTSI == null) {
                bitmapTSI = Bitmap.createBitmap(mContentTSI.getWidth(), mContentTSI.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmapTSI);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(ImgPathTSISign);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmapTSI.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            getsignTSI.setEnabled(true);
            signOrNotTSI = true;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    public class signatureDistributor extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signatureDistributor(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());

            if (bitmapDistributor == null) {
                bitmapDistributor = Bitmap.createBitmap(mContentDistributor.getWidth(), mContentDistributor.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmapDistributor);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(ImgPathDistributorSign);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmapDistributor.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            getsignDistributor.setEnabled(true);
            signOrNotDistributor = true;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    public boolean fnValidateSignatures()
    {
        boolean flgSignaturePresent=true;

       /* if (!signOrNotASM) {
            showAlertForEveryOne(getResources().getString(R.string.txtValidateSignatureRequired) +" ASM");

            flgSignaturePresent= false;
        }*/

            if (!signOrNotTSI) {
                showAlertForEveryOne(getResources().getString(R.string.txtValidateSignatureRequired) + " TSI");

                flgSignaturePresent = false;
            }

        if(flgSignaturePresent) {
            if (!signOrNotDistributor) {
                showAlertForEveryOne(getResources().getString(R.string.txtValidateSignatureRequired) + " Distributor");

                flgSignaturePresent = false;
            }
        }
        return flgSignaturePresent;
    }

    public void showAlertForEveryOne(String msg) {
        android.app.AlertDialog.Builder alertDialogNoConn = new android.app.AlertDialog.Builder(ActivityDistributorAppointment.this);
        alertDialogNoConn.setTitle(getResources().getString(R.string.genTermNoDataConnection));

        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        android.app.AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }


    private class SyncImageData extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {
            }
            else
            {
                // Locate the image folder in your SD Card
                fileintial = new File(Environment.getExternalStorageDirectory()+ File.separator + CommonInfo.ImagesFolder);
                // Create a new folder if no folder named SDImageTutorial exist
                fileintial.mkdirs();
            }
            if (fileintial.isDirectory())
            {
                listFile = fileintial.listFiles();
            }
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            // Sync Add New Stores Images
            try
            {
                //List<PotentialDistributorImageTable>imageTableList
                for(PotentialDistributorImageTable  tblPotentialDistributorImageTableRecord:tblPotentialDistributor.getImageTableList())
                {
                    fnameIMG = tblPotentialDistributorImageTableRecord.getImageName();
                    UploadingImageName=tblPotentialDistributorImageTableRecord.getImageName();

                    boolean isImageExist=false;
                    for (int i = 0; i < listFile.length; i++)
                    {
                        FilePathStrings = listFile[i].getAbsolutePath();
                        if(listFile[i].getName().equals(fnameIMG))
                        {
                            fnameIMG=listFile[i].getAbsolutePath();
                            isImageExist=true;
                            break;
                        }
                    }

                    if(isImageExist)
                    {
                        //Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();

                        String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
                        ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();


                        try
                        {
                            stream.flush();
                        }
                        catch (IOException e1)
                        {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        try
                        {
                            stream.close();
                        }
                        catch (IOException e1)
                        {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        long syncTIMESTAMP = System.currentTimeMillis();
                        Date datefromat = new Date(syncTIMESTAMP);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS",Locale.ENGLISH);
                        String onlyDate=TimeUtils.getNetworkDateTime(getBaseContext(),"dd-MMM-yyyy HH:mm:ss.SSS");



                        try
                        {

                            uploadImage(image_str,UploadingImageName,"NA","0",onlyDate,"0");

                        }catch(Exception e)
                        {
                            IMGsyOK = 1;

                        }
                    }
                }



            }
            catch(Exception e)
            {
                IMGsyOK = 1;

            }



            return null;
        }

        @Override
        protected void onCancelled()
        {
            Log.i("SvcMgr", "Service Execution Cancelled");
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
          /*  if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }*/

            if(IMGsyOK == 1)
            {
                IMGsyOK = 0;

            }
            else
            {

                // db.updateImageRecordsSyncd();



                try
                {

                    InterfaceServerSuccessEntry interfaceRetrofit = (InterfaceServerSuccessEntry) mContext;
                    CommonFunction.SavePotentialDistributorData reportDownloadAsyncTask = new CommonFunction.SavePotentialDistributorData(ActivityDistributorAppointment.this, mProgressDialogReport, interfaceRetrofit,0,tblPotentialDistributor);
                    AppUtils.executeAsyncTask(reportDownloadAsyncTask);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }
    }

    public String compressImage(String imageUri) {
        String filePath = imageUri;//getRealPathFromURI(imageUri);
        Bitmap scaledBitmap=null;
        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 1024.0f;
        float maxWidth = 768.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[768*1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);

        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }

        //Bitmap scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        //bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        //scaledBitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        bmp.compress(Bitmap.CompressFormat.JPEG,80, baos);

        byte [] arr=baos.toByteArray();
        String result= Base64.encodeToString(arr, Base64.DEFAULT);
        return result;


    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }
    private void uploadImage(String image_str,String currentImageFileName,String comment,String stID,String onlyDate,String routeID){

        try {


            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", image_str)
                    .addFormDataPart("FileName",currentImageFileName)
                    .addFormDataPart("comment","NA")
                    .addFormDataPart("storeID",stID)
                    .addFormDataPart("date",onlyDate)
                    .addFormDataPart("routeID",routeID)
                    .build();

            Request request = new Request.Builder()
                    .url(CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameImageSyncPath)
                    .post(requestBody)
                    .build();

            Response response = okHttpClient.newCall(request).execute();


            //  if(serverResponseCode == 200)
            if(response!=null && response.code()==200)
            {

                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +UploadingImageName.toString().trim();

                File fdelete = new File(file_dj_path);
                if (fdelete.exists())
                {
                    if (fdelete.delete())
                    {
                        Log.e("-->", "file Deleted :" + file_dj_path);
                        callBroadCast();
                    }
                    else
                    {
                        Log.e("-->", "file not Deleted :" + file_dj_path);
                    }
                }
						            	/* File file = new File(UploadingImageName.toString().trim());
							         	    file.delete();  */
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}