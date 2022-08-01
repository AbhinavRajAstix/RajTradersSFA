package com.astix.rajtraderssfasales;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.camera.CameraPreview;
import com.astix.rajtraderssfasales.location.LocationInterface;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobal;
import com.astix.rajtraderssfasales.sync.SyncJobService;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.PreferenceManager;
import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.allana.truetime.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.astix.rajtraderssfasales.camera.CameraUtils.findFrontFacingCamera;
import static com.astix.rajtraderssfasales.camera.CameraUtils.hasCamera;

public class DayStartActivity extends BaseActivity implements LocationInterface, OnMapReadyCallback, CompoundButton.OnCheckedChangeListener, InterfaceRetrofit, DatePickerDialog.OnDateSetListener, InterfaceRetrofitAttendanceMarking {
    public String fnameIMG;
    public String UploadingImageName;
    private String FilePathStrings;
    private File[] listFile;
    public  File fileintial;
    public int IMGsyOK = 0;

    TextView fromDate, todate;
    boolean fromDateBool = false;
    boolean toDateBool = false;

    TextView frmDate, todateText;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day;


    public PreferenceManager mPreferencesManager;
    LinearLayout ll_OverAllDistributionCheckin, ll_LeaveDates;
    TextView txt_DBR_Name;
    String reasonId = "0";
    String otherReasonForLate = "NA";
    EditText et_DBR_Name, rsnLatetext;
    ArrayList<String> listTxtBxToShow;
    ArrayAdapter<String> adapterDBR;
    String[] listLocationWrkng = {"Select Working Location", "At Warehouse Location", "Other Location"};
    LinearLayout ll_DBR_Name;
    boolean isLateApplicable = false;
    LinkedHashMap<String, String> hmapReasonIdAndDescr_details;
    String crntServerTime;
    String crntAttndncTime = "9:30 AM";
    String[] reasonLate;
    ArrayAdapter<String> adapterLocation;
    String FnlAddress = "NA", finalPinCode = "0", finalCity = "NA", finalState = "NA", fnAccurateProvider = "NA", AllProvidersLocation = "NA", FusedAddress = "NA", GpsLat = "0", GpsLong = "0", GpsAccuracy = "0", GpsAddress = "NA", NetwLat = "0", NetwLong = "0", NetwAccuracy = "0", NetwAddress = "NA", FusedLat = "0", FusedLong = "0", FusedAccuracy = "0", FusedLocationLatitudeWithFirstAttempt = "0", FusedLocationLongitudeWithFirstAttempt = "0", FusedLocationAccuracyWithFirstAttempt = "0";
    int flgLocationServicesOnOff = 0, flgGPSOnOff = 0, flgNetworkOnOff = 0, flgFusedOnOff = 0, flgInternetOnOffWhileLocationTracking = 0, flgRestart = 0;
    String imei;
    TextView txt_rfrshCmnt;
    LinkedHashMap<String, String> hmapDistLatLng;
    static int flgDaySartWorking = 0;
    public long syncTIMESTAMP;
    ProgressDialog pDialog2;
    String PersonNodeID = "NA";
    String PersonNodeType = "NA";
    String PersonName = "NA";
    String OptionID = "NA";
    String OptionDesc = "NA";

    double distanceCalc = 0.0;


    String cityID = "NA";
    String StateID = "NA";
    String MapAddress = "NA";
    String MapPincode = "NA";
    String MapCity = "NA";
    String MapState = "NA";

    int refreshCount = 0;
    int countSubmitClicked = 0;
    public LocationManager locationManager;
    int intentFrom = 0;
    LinearLayout ll_map, ll_comment;

    EditText et_otherPleaseSpecify;
    public String ReasonId = "0";
    ;
    public String ReasonText = "NA";
    public int chkFlgForErrorToCloseApp = 0;
    String[] reasonNames;


    public String fDate;
    public SimpleDateFormat sdf;
    SharedPreferences sPrefAttandance;

    FragmentManager manager;
    FragmentTransaction fragTrans;
    MapFragment mapFrag;
    String LattitudeFromLauncher = "NA";
    String LongitudeFromLauncher = "NA";
    public String AccuracyFromLauncher = "NA";
    String AddressFromLauncher = "NA";
    LinearLayout ll_start, ll_startAfterDayEndFirst, ll_startAfterDayEndSecond, ll_Working, ll_NoWorking;
    LinearLayout ll_Working_parent, ll_NoWorking_parent, ll_reason;
    Button but_Next;

    TextView txt_DayStarttime;

    LinkedHashMap<String, String> hmapSelectedCheckBoxData = new LinkedHashMap<String, String>();

    LinkedHashMap<Integer, String> hmapReasonIdAndDescrForWorking_details = new LinkedHashMap<Integer, String>();
    LinkedHashMap<Integer, String> hmapReasonIdAndDescrForNotWorking_details = new LinkedHashMap<Integer, String>();
    String reasonIdForMrktVisit;
    public CheckBox[] cb;
    public RadioButton[] rb;


    String[] Distribtr_list;
    String DbrNodeId, DbrNodeType, DbrName;
    ArrayList<String> DbrArray = new ArrayList<String>();


    RadioButton rb_workingYes, rb_workingNo;//=null;
    Spinner spinner_for_filter, spnr_late, spinner_location;


    String DistributorName_Global = "Select Distributor";
    String DistributorId_Global = "0";
    String DistributorNodeType_Global = "0";
    private Button btn_refresh;
    private LinearLayout ll_refresh;
    private RadioGroup rg_yes_no;
    TextView tv_MapLocationCorrectText;

    private RadioButton rb_yes;
    private RadioButton rb_no;


    private LinearLayout cameraPreview;
    private CameraPreview mPreview;
    private Camera mCamera;
    private Button switchCamera;
    private boolean isLighOn = false;
    private Camera.PictureCallback mPicture;
    private Dialog dialog;
    private Activity mActivity;
    private String imageName;
    private Uri uriSavedImage;
    private Button capture, cancelCam, button_takeSelfie;
    private ImageView flashImage;
    public static int battLevel = 0;
    private ImageView photoView;
    private ImageButton photoCancel;
    String SelfieName="NA";
    String SelfieURL="NA";


    public Context ctx;

    //private MyService mMyService;
    public Context getCtx() {
        return ctx;
    }

    public void customHeader() {


        TextView tv_heading = (TextView) findViewById(R.id.tv_heading);
        tv_heading.setText("Day Start");

        ImageView imgVw_next = (ImageView) findViewById(R.id.imgVw_next);

        ImageView imgVw_back = (ImageView) findViewById(R.id.imgVw_back);
        imgVw_next.setVisibility(View.GONE);
        imgVw_back.setVisibility(View.GONE);
        if (intentFrom == 0) {
            imgVw_back.setVisibility(View.GONE);
            imgVw_next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i = new Intent(DayStartActivity.this, AllButtonActivity.class);
                    startActivity(i);
                    finish();

                }
            });
        } else {
            imgVw_next.setVisibility(View.GONE);
            imgVw_back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i = new Intent(DayStartActivity.this, StoreSelection.class);

                    startActivity(i);
                    finish();

                }
            });
        }
    }

    private void getDataFromDatabase() throws IOException {
        reasonIdForMrktVisit = mDataSource.getReasonIdForMarketVisit();
        hmapReasonIdAndDescrForWorking_details = mDataSource.fetch_Reason_List_for_option();
        hmapReasonIdAndDescrForNotWorking_details = mDataSource.fetch_NoWorking_Reason_List();
        listTxtBxToShow = mDataSource.fetch_Text_To_Show();
    }


    private void createCheckBoxForWorking() {
        cb = new CheckBox[hmapReasonIdAndDescrForWorking_details.size()];

        int i = 0;
        for (Map.Entry<Integer, String> entry : hmapReasonIdAndDescrForWorking_details.entrySet()) {
            EditText editText = null;
            cb[i] = new CheckBox(this);
            cb[i].setText(entry.getValue());
            cb[i].setTag(entry.getKey().toString().trim());
            cb[i].setOnCheckedChangeListener(this);

            if (listTxtBxToShow != null && listTxtBxToShow.contains(entry.getKey().toString().trim())) {
                editText = getEditText(entry.getKey().toString().trim() + "_ed");
            }

            ll_Working.addView(cb[i]);
            if (editText != null) {
                ll_Working.addView(editText);
                editText.setVisibility(View.GONE);
            }
            cb[i].setOnCheckedChangeListener(this);
            i = i + 1;
        }

    }

    public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
        String checkedText = cb.getText() + "";
        String checkedID = cb.getTag() + "";

        if (Integer.parseInt(checkedID.trim()) == 6) {
            et_otherPleaseSpecify = (EditText) findViewById(R.id.et_otherPleaseSpecify);
            et_otherPleaseSpecify.setVisibility(View.VISIBLE);
        } else {
            et_otherPleaseSpecify = (EditText) findViewById(R.id.et_otherPleaseSpecify);
            et_otherPleaseSpecify.setVisibility(View.GONE);
        }

        if (isChecked) {

            // for unchecked all the Radio Button in NoT Working
            int i = 0;
            for (Map.Entry<Integer, String> entry : hmapReasonIdAndDescrForNotWorking_details.entrySet()) {
                RadioButton rb = (RadioButton) ll_NoWorking.getChildAt(i);
                rb.setChecked(false);
                i = i + 1;
            }

            if (Integer.parseInt(checkedID.trim()) == 6) {
                et_otherPleaseSpecify = (EditText) findViewById(R.id.et_otherPleaseSpecify);
                et_otherPleaseSpecify.setVisibility(View.VISIBLE);
            } else {
                et_otherPleaseSpecify = (EditText) findViewById(R.id.et_otherPleaseSpecify);
                et_otherPleaseSpecify.setVisibility(View.GONE);
            }
            if (Integer.parseInt(checkedID.trim()) == 6) {
                if (!TextUtils.isEmpty(et_otherPleaseSpecify.getText().toString().trim())) {
                    hmapSelectedCheckBoxData.put(checkedID.trim(), et_otherPleaseSpecify.getText().toString().trim());
                } else {
                    hmapSelectedCheckBoxData.put(checkedID.trim(), "NA");
                }

            } else {
                hmapSelectedCheckBoxData.put(checkedID.trim(), checkedText.trim());
            }

            CommonInfo.DayStartClick = 1;

        } else {
            if (Integer.parseInt(checkedID.trim()) == 6) {
                et_otherPleaseSpecify = (EditText) findViewById(R.id.et_otherPleaseSpecify);
                et_otherPleaseSpecify.setVisibility(View.GONE);
                et_otherPleaseSpecify.setText("");
            }

            hmapSelectedCheckBoxData.remove(checkedID.trim());
            if (hmapSelectedCheckBoxData.size() == 0) {
                CommonInfo.DayStartClick = 0;
            }

        }


    }

    private void createRadioButtonForNotWorking() {
        rb = new RadioButton[hmapReasonIdAndDescrForNotWorking_details.size()];
        int i = 0;
        for (Map.Entry<Integer, String> entry : hmapReasonIdAndDescrForNotWorking_details.entrySet()) {
            rb[i] = new RadioButton(this);
            rb[i].setText(entry.getValue());
            rb[i].setTag(entry.getKey().toString().trim());
            rb[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
                    String checkedText = cb.getText() + "";
                    String checkedID = cb.getTag() + "";
                    if (isChecked) {
                        ReasonText = hmapReasonIdAndDescrForNotWorking_details.get(Integer.parseInt(checkedID.trim()));
                        ReasonId = "" + checkedID;
                        if (checkedID.equals("18")) {
                            ll_LeaveDates.setVisibility(View.VISIBLE);
                        } else {
                            ll_LeaveDates.setVisibility(View.GONE);
                        }

                        // for checked Selected the Radio Button in NoT Working
                        int i = 0;
                        for (Map.Entry<Integer, String> entry : hmapReasonIdAndDescrForNotWorking_details.entrySet()) {
                            RadioButton rb = (RadioButton) ll_NoWorking.getChildAt(i);
                            if (rb.getTag().toString().trim().equals(checkedID.trim())) {
                                rb.setChecked(true);
                            } else {
                                rb.setChecked(false);
                            }

                            i = i + 1;
                        }

                        // for unchecked all the CheckBox in Today Working
                        i = 0;
                        for (Map.Entry<Integer, String> entry : hmapReasonIdAndDescrForWorking_details.entrySet()) {
                            cb = (CheckBox) ll_Working.getChildAt(i);
                            cb.setChecked(false);
                            i = i + 1;
                        }
                        hmapSelectedCheckBoxData.clear();
                        hmapSelectedCheckBoxData.clear();

                        CommonInfo.DayStartClick = 2;
                    } else {

                    }
                }
            });
            ll_NoWorking.addView(rb[i]);
            i = i + 1;
        }


        fromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                fromDateBool = true;
                frmDate = (TextView) arg0;
                calendar = Calendar.getInstance();
                Year = calendar.get(Calendar.YEAR);
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = DatePickerDialog.newInstance(DayStartActivity.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);
                Calendar calendarForSetDate = Calendar.getInstance();
                calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

                // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
                //YOU can set min or max date using this code
                // datePickerDialog.setMaxDate(Calendar.getInstance());
                // datePickerDialog.setMinDate(calendar);
                datePickerDialog.setMinDate(calendarForSetDate);

                datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

                datePickerDialog.setTitle("SELECT FROM DATE");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");


            }
        });


        todate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                toDateBool = true;
                todateText = (TextView) arg0;
                calendar = Calendar.getInstance();
                Year = calendar.get(Calendar.YEAR);
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = DatePickerDialog.newInstance(DayStartActivity.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                Calendar calendarForSetDate = Calendar.getInstance();
                calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

                // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
                //YOU can set min or max date using this code
                // datePickerDialog.setMaxDate(Calendar.getInstance());
                // datePickerDialog.setMinDate(calendar);
                String dtFrom = fromDate.getText().toString().trim();
                if (dtFrom.contains("-")) {
                    String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                    int DayDTFRm = Integer.parseInt(dtFrom.split(Pattern.quote("-"))[0]);
                    int monthDTFRm = getArrayIndex(MONTHS, dtFrom.split(Pattern.quote("-"))[1]);
                    int yearDTFRm = Integer.parseInt(dtFrom.split(Pattern.quote("-"))[2]);
                    Calendar calDTFRM = Calendar.getInstance();
                    calDTFRM.set(yearDTFRm, monthDTFRm, DayDTFRm);
                    if (calDTFRM.getTimeInMillis() > System.currentTimeMillis()) {
                        calendarForSetDate.set(yearDTFRm, monthDTFRm, DayDTFRm);
                    }

                }
                datePickerDialog.setMinDate(calendarForSetDate);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

                datePickerDialog.setTitle("SELECT DATE UPTO");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");


            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (!LattitudeFromLauncher.equals("NA") && !LattitudeFromLauncher.equals("0.0")) {
            googleMap.clear();
            try {
                googleMap.setMyLocationEnabled(false);
            } catch (SecurityException e) {

            }
            if (AccuracyFromLauncher.equals("0.0") || AccuracyFromLauncher.equals("NA") || AccuracyFromLauncher.equals("") || AccuracyFromLauncher == null) {

            } else {

                if (Double.parseDouble(AccuracyFromLauncher) > 100) {
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    Marker locationMarker = googleMap.addMarker(marker);
                    locationMarker.showInfoWindow();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
                  /*  if(!fetchedFromDb.equals("0") && fetchedFromDb!=null){
                        distanceText.setVisibility(View.VISIBLE);
                    }*/

                } else {
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    Marker locationMarker = googleMap.addMarker(marker);
                    locationMarker.showInfoWindow();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
                    // distanceText.setVisibility(View.GONE);
                }

            }


        } else {

            if (refreshCount == 2) {
                txt_rfrshCmnt.setText(getString(R.string.loc_not_found));
                btn_refresh.setVisibility(View.GONE);
            }
            try {
                googleMap.setMyLocationEnabled(false);
            } catch (SecurityException e) {

            }
            // googleMap.addMarker(new MarkerOptions().position(new LatLng(22.7253, 75.8655)).title("Indore"));
            googleMap.moveCamera(CameraUpdateFactory.zoomIn());
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    //  marker.setTitle(StoreName);
                }
            });

        }

    }

    public int getArrayIndex(String[] arr, String value) {

        int k = 0;
        for (int i = 0; i < arr.length; i++) {

            if (arr[i].contains(value)) {
                k = i;
                break;
            }
        }
        return k;
    }

    public void fnGetDistributorList() {

        //mDataSource.open();
        Distribtr_list = mDataSource.getSuplierData();
        hmapDistLatLng = mDataSource.getDistLatLng();
        //String distDefault=mDataSource.getDfltDstbtr();
        //mDataSource.close();
        for (int i = 0; i < Distribtr_list.length; i++) {
            //System.out.println("DISTRIBUTOR........"+Distribtr_list[i]);
            String value = Distribtr_list[i];
            DbrNodeId = value.split(Pattern.quote("^"))[0];
            DbrNodeType = value.split(Pattern.quote("^"))[1];
            DbrName = value.split(Pattern.quote("^"))[2];
            DbrArray.add(DbrName);
        }

        adapterDBR = new ArrayAdapter<String>(DayStartActivity.this, R.layout.initial_spinner_text, DbrArray);
        adapterDBR.setDropDownViewResource(R.layout.spina);

        spinner_for_filter.setAdapter(adapterDBR);


        spinner_for_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView) view;
                String text = tv.getText().toString();

                if (text.equals("Select Distributor")) {
                    DistributorName_Global = "Select Distributor";
                    DistributorId_Global = "0";
                    DistributorNodeType_Global = "0";
                    distanceCalc = 0.0;
                } else {

                    DistributorName_Global = tv.getText().toString();
                    String Distribtor_Detail = mDataSource.fetchSuplierIdByName(text);
                    DistributorId_Global = Distribtor_Detail.split(Pattern.quote("^"))[0];
                    DistributorNodeType_Global = Distribtor_Detail.split(Pattern.quote("^"))[1];
                    String distLatLng = hmapDistLatLng.get(Distribtor_Detail);
                    Location mylocation = new Location("");
                    Location dest_location = new Location("");
                    String lat = distLatLng.split(Pattern.quote("^"))[0];
                    String lon = distLatLng.split(Pattern.quote("^"))[1];
                    dest_location.setLatitude(Double.parseDouble(lat));
                    dest_location.setLongitude(Double.parseDouble(lon));

                    if ((!LattitudeFromLauncher.equals("NA")) && (!LongitudeFromLauncher.equals("NA"))) {
                        mylocation.setLatitude(Double.parseDouble(LattitudeFromLauncher));
                        mylocation.setLongitude(Double.parseDouble(LongitudeFromLauncher));
                        float distance = mylocation.distanceTo(dest_location);//in meters
                        distanceCalc = distance;
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    View.OnClickListener captrureListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setEnabled(false);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    };
    private FrameLayout selfieContainerFL;
    private float mDist = 0;
    private  File getOutputMediaFile() {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), CommonInfo.ImagesFolder);

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
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + AppUtils.getToken(DayStartActivity.this)+"_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public void openCustomCamara(String flgWhichButtonCliked) {
        if (dialog != null) {
            if (!dialog.isShowing()) {
                openCamera(flgWhichButtonCliked);
            }
        } else {
            openCamera(flgWhichButtonCliked);
        }
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


    public static Bitmap normalizeImageForUri(Context context, Uri uri) {
        Bitmap rotatedBitmap = null;

        try {

            ExifInterface exif = new ExifInterface(uri.getPath());

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_180);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            rotatedBitmap = rotateBitmap(bitmap, orientation);
            if (!bitmap.equals(rotatedBitmap)) {
                saveBitmapToFile(context, rotatedBitmap, uri);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
        return rotatedBitmap;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();

            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            Crashlytics.logException(e);
            return null;
        }
    }

    private static void saveBitmapToFile(Context context, Bitmap croppedImage, Uri saveUri) {
        if (saveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = context.getContentResolver().openOutputStream(saveUri);
                if (outputStream != null) {
                    croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Crashlytics.logException(e);
            } finally {
                closeSilently(outputStream);
                croppedImage.recycle();
            }
        }
    }

    private static void closeSilently(@Nullable Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            t.printStackTrace();
            // Do nothings
            Crashlytics.logException(t);
        }
    }

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
                if (pictureFile == null) {
                    return;
                }
                try {
                    //write the file
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();

                    dialog.dismiss();
                    if (pictureFile != null) {
                        File file = pictureFile;
                        System.out.println("File +++" + pictureFile);
                        imageName = pictureFile.getName();
//                        normalizeImageForUri(DayStartActivity.this, Uri.fromFile(pictureFile));
                        Bitmap bmp = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getAbsolutePath()), 120, 120);
//                        Bitmap bmp = decodeSampledBitmapFromFile(file.getAbsolutePath(), 80, 80);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        uriSavedImage = Uri.fromFile(pictureFile);
                        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        byte[] byteArray = stream.toByteArray();
                        final int MAX_WIDTH = 768;
                        final int MAX_HEIGHT = 512;
                        int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
                        selfieContainerFL.setVisibility(View.VISIBLE);

                        Glide.with(photoView).load(pictureFile.getAbsolutePath()).error(R.drawable.no_img).into(photoView);
                       // Glide.with(holder.ivProductImg).load(hmapProductImg.get(prductId)).error(R.drawable.no_img).into(holder.ivProductImg);
                      /*  Picasso.get().load(new File(pictureFile.getAbsolutePath())).error(R.drawable.image_not_available).into(photoView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                                Crashlytics.logException(e);
                            }
                        });*/
                        // Picasso.get().load(new File(pictureFile.getAbsolutePath())).resize(size, size).centerInside().into(photoView);
                        photoCancel.bringToFront();
                        // Convert ByteArray to Bitmap::\
                        //
                        long syncTIMESTAMP = System.currentTimeMillis();
                        Date dateobj = new Date(syncTIMESTAMP);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                        // String clkdTime = AppUtils.doGetTime("dd-MMM-yyyy HH:mm:ss");

                       /* tblAttandanceDetail.setSelfieName(imageName);
                        tblAttandanceDetail.setSelfieURL(pictureFile.getAbsolutePath());*/

                        SelfieName=imageName;
                        SelfieURL=pictureFile.getAbsolutePath();

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
           /* mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    // currently set to auto-focus on single touch
                }
            });*/
        }
    }
    private void releaseCameraAndPreview() {
        //cameraPreview = null;
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }


    private float getFingerSpacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void openCamera(String flgWhichButtonCliked) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        dialog = new Dialog(DayStartActivity.this);
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
            releaseCameraAndPreview();

            mPreview = new CameraPreview(DayStartActivity.this, mCamera);
            cameraPreview.addView(mPreview);
        }
        //onResume code
        if (!hasCamera(DayStartActivity.this)) {
            Toast toast = Toast.makeText(DayStartActivity.this, getText(R.string.txtNoCamera), Toast.LENGTH_LONG);
            toast.show();
        }

        if (mCamera == null) {
            //if the front facing camera does not exist
            if (findFrontFacingCamera() < 0) {
                Toast.makeText(DayStartActivity.this, getText(R.string.txtNoFrontCamera), Toast.LENGTH_LONG).show();
                switchCamera.setVisibility(View.GONE);
            }

            //mCamera = Camera.open(findBackFacingCamera());

			/*if(mCamera!=null){
				mCamera.release();
				mCamera=null;
			}*/
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
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
                params.setRotation(270);

                mCamera.setParameters(params);
                isParameterSet = true;
            } catch (Exception e) {

            }
            if (!isParameterSet) {
                Camera.Parameters params2 = mCamera.getParameters();
                params2.setPictureFormat(ImageFormat.JPEG);
                params2.setJpegQuality(70);
                params2.setRotation(270);

                mCamera.setParameters(params2);
            }

            setCameraDisplayOrientation(DayStartActivity.this, Camera.CameraInfo.CAMERA_FACING_FRONT, mCamera);
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

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }


                        }
                    }, 1000);

                    //  params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

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
                releaseCameraAndPreview();
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
    @Override
    protected void onDestroy() {
       // this.unregisterReceiver(this.mBatInfoReceiver);
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daystart);
        ctx = this;


        imei = AppUtils.getToken(DayStartActivity.this);

        Intent intent = getIntent();
        intentFrom = intent.getIntExtra("IntentFrom", 0);
        sPrefAttandance = getSharedPreferences(CommonInfo.AttandancePreference, MODE_PRIVATE);
        mPreferencesManager = PreferenceManager.getInstance(this);
        customHeader();
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setVisibility(View.GONE);
        ll_refresh = (LinearLayout) findViewById(R.id.ll_refresh);
        ll_refresh.setVisibility(View.GONE);
        txt_rfrshCmnt = (TextView) findViewById(R.id.txt_rfrshCmnt);
        rsnLatetext = (EditText) findViewById(R.id.rsnLatetext);
        rsnLatetext.setVisibility(View.GONE);
        rb_yes = (RadioButton) findViewById(R.id.rb_yes);
        rb_no = (RadioButton) findViewById(R.id.rb_no);

        rg_yes_no = (RadioGroup) findViewById(R.id.rg_yes_no);
        rg_yes_no.setVisibility(View.GONE);

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
                llaaa.locationRetrievingAndDistanceCalculating(DayStartActivity.this, false, true, 20, 0);

            }


        });


        try {
            getDataFromDatabase();
        } catch (Exception e) {

        }
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        Date date1 = new Date();
        sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        fDate = TimeUtils.getNetworkDateTime(DayStartActivity.this, TimeUtils.DATE_FORMAT);
        txt_DBR_Name = (TextView) findViewById(R.id.txt_DBR_Name);
        ll_start = (LinearLayout) findViewById(R.id.ll_start);
        ll_start.setVisibility(View.VISIBLE);
        ll_startAfterDayEndFirst = (LinearLayout) findViewById(R.id.ll_startAfterDayEndFirst);
        ll_startAfterDayEndFirst.setVisibility(View.GONE);
        ll_startAfterDayEndSecond = (LinearLayout) findViewById(R.id.ll_startAfterDayEndSecond);
        ll_startAfterDayEndSecond.setVisibility(View.GONE);

        button_takeSelfie = findViewById(R.id.button_takeSelfie);
        button_takeSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomCamara("1");
            }
        });


        photoView = findViewById(R.id.photo_view);
        photoCancel = findViewById(R.id.photo_cancel);
        selfieContainerFL = findViewById(R.id.selfieContainerFL);
        photoCancel.setOnClickListener(v -> {
            selfieContainerFL.setVisibility(View.GONE);
            SelfieName="NA";
            SelfieURL="NA";
        });


        ll_map = (LinearLayout) findViewById(R.id.ll_map);
        ll_map.setVisibility(View.GONE);


        ll_DBR_Name = (LinearLayout) findViewById(R.id.ll_DBR_Name);
        ll_DBR_Name.setVisibility(View.GONE);
        et_DBR_Name = (EditText) findViewById(R.id.et_DBR_Name);
        ll_Working = (LinearLayout) findViewById(R.id.ll_Working);
        ll_Working.setVisibility(View.GONE);
        ll_NoWorking = (LinearLayout) findViewById(R.id.ll_NoWorking);
        ll_NoWorking.setVisibility(View.GONE);


        ll_Working_parent = (LinearLayout) findViewById(R.id.ll_Working_parent);
        ll_Working_parent.setVisibility(View.GONE);

        ll_NoWorking_parent = (LinearLayout) findViewById(R.id.ll_NoWorking_parent);
        ll_NoWorking_parent.setVisibility(View.GONE);

        tv_MapLocationCorrectText = (TextView) findViewById(R.id.tv_MapLocationCorrectText);
        tv_MapLocationCorrectText.setVisibility(View.GONE);


        rb_workingYes = (RadioButton) findViewById(R.id.rb_workingYes);
        rb_workingYes.setVisibility(View.GONE);
        rb_workingNo = (RadioButton) findViewById(R.id.rb_workingNo);
        rb_workingNo.setVisibility(View.GONE);

        ll_LeaveDates = (LinearLayout) findViewById(R.id.ll_LeaveDates);
        fromDate = (TextView) findViewById(R.id.fromDate);
        todate = (TextView) findViewById(R.id.todate);
        fromDate.setEnabled(true);
        todate.setEnabled(true);


        ll_OverAllDistributionCheckin = (LinearLayout) findViewById(R.id.ll_OverAllDistributionCheckin);
        spinner_location = (Spinner) findViewById(R.id.spinner_location);
        adapterLocation = new ArrayAdapter<String>(DayStartActivity.this, android.R.layout.simple_spinner_item, listLocationWrkng);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_location.setAdapter(adapterLocation);
        // spinner_location.setVisibility(View.GONE);
        spinner_for_filter = (Spinner) findViewById(R.id.spinner_for_filter);

        spnr_late = (Spinner) findViewById(R.id.spnr_late);
        if (CommonInfo.hmapAppMasterFlags != null && CommonInfo.hmapAppMasterFlags.containsKey("flgDistributorCheckIn") && CommonInfo.hmapAppMasterFlags.get("flgDistributorCheckIn") == 0) {
            ll_OverAllDistributionCheckin.setVisibility(View.GONE);

        }
        spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinner_location.getSelectedItem().toString().equals("Select Working Location")) {

                    ll_map.setVisibility(View.VISIBLE);
                    ll_Working_parent.setVisibility(View.VISIBLE);
                    ll_NoWorking_parent.setVisibility(View.GONE);
                    ll_map.setVisibility(View.VISIBLE);
                    ll_comment.setVisibility(View.VISIBLE);
                    if (isLateApplicable) {
                        ll_reason.setVisibility(View.VISIBLE);
                    }

                    but_Next.setVisibility(View.VISIBLE);

                    rg_yes_no.setVisibility(View.VISIBLE);

                    btn_refresh.setVisibility(View.VISIBLE);
                    tv_MapLocationCorrectText.setVisibility(View.VISIBLE);

                    if (spinner_location.getSelectedItem().toString().equals("At Warehouse Location")) {
                        spinner_for_filter.setSelection(adapterDBR.getPosition("Select Distributor"));
                        spinner_for_filter.setVisibility(View.VISIBLE);
                        ll_DBR_Name.setVisibility(View.VISIBLE);
                        txt_DBR_Name.setText(getString(R.string.DistbtrName));
                        et_DBR_Name.setText(DbrArray.get(position));
                        CheckBox checkBox = (CheckBox) ll_Working.findViewWithTag("7");
                        if (checkBox != null) {
                            checkBox.setChecked(true);
                        }

                    } else if (spinner_location.getSelectedItem().toString().equals("Other Location")) {
                        spinner_for_filter.setSelection(adapterDBR.getPosition("Select Distributor"));
                        spinner_for_filter.setVisibility(View.GONE);
                        ll_DBR_Name.setVisibility(View.VISIBLE);
                        txt_DBR_Name.setText(getString(R.string.other_loc));
                        CheckBox checkBox = (CheckBox) ll_Working.findViewWithTag("7");
                        if (checkBox != null) {
                            checkBox.setChecked(false);
                        }
                    } else {
                        //DbrArray
                        if (DbrArray.size() > 1) {
                            spinner_for_filter.setSelection(adapterDBR.getPosition(DbrArray.get(1)));
                            DistributorName_Global = DbrArray.get(1);
                            String Distribtor_Detail = mDataSource.fetchSuplierIdByName(DistributorName_Global);
                            DistributorId_Global = Distribtor_Detail.split(Pattern.quote("^"))[0];
                            DistributorNodeType_Global = Distribtor_Detail.split(Pattern.quote("^"))[1];
                        }

                        spinner_for_filter.setVisibility(View.VISIBLE);
                        ll_DBR_Name.setVisibility(View.GONE);
                        CheckBox checkBox = (CheckBox) ll_Working.findViewWithTag("7");
                        if (checkBox != null) {
                            checkBox.setChecked(false);
                        }
                    }
                } else {
                    spinner_for_filter.setSelection(adapterDBR.getPosition("Select Distributor"));
                    spinner_for_filter.setVisibility(View.GONE);
                    ll_map.setVisibility(View.GONE);
                    ll_Working_parent.setVisibility(View.GONE);

                    ll_map.setVisibility(View.GONE);
                    ll_reason.setVisibility(View.GONE);
                    btn_refresh.setVisibility(View.GONE);
                    ll_refresh.setVisibility(View.GONE);
                    rg_yes_no.setVisibility(View.GONE);
                    tv_MapLocationCorrectText.setVisibility(View.GONE);

                    ll_DBR_Name.setVisibility(View.GONE);

                    CheckBox checkBox = (CheckBox) ll_Working.findViewWithTag("7");
                    if (checkBox != null) {
                        checkBox.setChecked(false);
                    }

                    but_Next.setVisibility(View.GONE);

                    DistributorName_Global = "Select Distributor";
                    DistributorId_Global = "0";
                    DistributorNodeType_Global = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnr_late.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ((!spnr_late.getSelectedItem().toString().equals("Select Reason")) && (!spnr_late.getSelectedItem().toString().equals("No Reason"))) {
                    if (listTxtBxToShow.contains(hmapReasonIdAndDescr_details.get(spnr_late.getSelectedItem().toString()))) {
                        rsnLatetext.setVisibility(View.VISIBLE);
                    } else {
                        rsnLatetext.setVisibility(View.GONE);
                    }
                    otherReasonForLate = spnr_late.getSelectedItem().toString();
                    reasonId = hmapReasonIdAndDescr_details.get(spnr_late.getSelectedItem().toString());
                } else {
                    reasonId = "0";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ll_reason = (LinearLayout) findViewById(R.id.ll_reason);
        ll_reason.setVisibility(View.GONE);


        ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
        ll_comment.setVisibility(View.GONE);

        but_Next = (Button) findViewById(R.id.but_Next);
        but_Next.setVisibility(View.GONE);

        if (CommonInfo.hmapAppMasterFlags!=null && CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgDistributorCheckIn") == 1) {
            fnGetDistributorList();
        }

        getReasonDetail();


        Button but_DayStart = (Button) findViewById(R.id.but_DayStart);
        but_DayStart.setOnClickListener(new View.OnClickListener() {
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
                if (!isGPSok && !isNWok) {
                    try {
                        showSettingsAlert();
                    } catch (Exception e) {

                    }

                    isGPSok = false;
                    isNWok = false;
                } else {

                    ll_Working.setVisibility(View.VISIBLE);
                    ll_NoWorking.setVisibility(View.VISIBLE);
                    createCheckBoxForWorking();
                    createRadioButtonForNotWorking();
                    txt_DayStarttime = (TextView) findViewById(R.id.txt_DayStarttime);
                    txt_DayStarttime.setText(getDateAndTimeInSecond());

                    rb_workingYes.setVisibility(View.VISIBLE);
                    rb_workingNo.setVisibility(View.VISIBLE);

                    manager = getFragmentManager();
                    mapFrag = (MapFragment) manager.findFragmentById(R.id.map);
                    mapFrag.getView().setVisibility(View.VISIBLE);
                    // mapFrag.addMarker(new MarkerOptions().position(new LatLng(22.7253, 75.8655)).title("Indore"));
                    // mapFrag.set

                    mapFrag.getMapAsync(DayStartActivity.this);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.show(mapFrag);

                    ll_start = (LinearLayout) findViewById(R.id.ll_start);
                    ll_start.setVisibility(View.GONE);
                    ll_startAfterDayEndFirst = (LinearLayout) findViewById(R.id.ll_startAfterDayEndFirst);
                    ll_startAfterDayEndFirst.setVisibility(View.VISIBLE);
                    ll_startAfterDayEndSecond = (LinearLayout) findViewById(R.id.ll_startAfterDayEndSecond);
                    ll_startAfterDayEndSecond.setVisibility(View.VISIBLE);


                    LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
                    llaaa.locationRetrievingAndDistanceCalculating(DayStartActivity.this, true, true, 20, 1);

                }


            }
        });
        Button but_Exit = (Button) findViewById(R.id.but_Exit);
        but_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });


        but_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                but_Next.setEnabled(false);

                pDialog2 = ProgressDialog.show(DayStartActivity.this,getText(R.string.PleaseWaitMsg),"Loading Attendance to server.", true);
                pDialog2.setIndeterminate(true);
                pDialog2.setCancelable(false);
                pDialog2.show();

                if (rb_workingYes.isChecked() && SelfieName.equals("NA")) {
                    if(pDialog2!=null && pDialog2.isShowing())
                    {
                        pDialog2.dismiss();
                    }
                    android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                    alertDialog.setMessage("Please take selfie.");
                    alertDialog.setIcon(R.drawable.error);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            but_Next.setEnabled(true);
                        }
                    });
                    alertDialog.show();
                    return;
                }


                otherReasonForLate = "NA";
                int flg100MeterValidation = 0;
                if (CommonInfo.hmapAppMasterFlags.containsKey("flg100MeterValidation")) {
                    if (CommonInfo.hmapAppMasterFlags.get("flg100MeterValidation") == 1) {
                        flg100MeterValidation = 1;
                    }
                }

                int flgCheckForLateReason = 0;
                if (CommonInfo.hmapAppMasterFlags.containsKey("flgCheckForLateReason")) {
                    if (CommonInfo.hmapAppMasterFlags.get("flgCheckForLateReason") == 1) {
                        flgCheckForLateReason = 1;
                    }
                }
                if (CommonInfo.hmapAppMasterFlags != null && CommonInfo.hmapAppMasterFlags.containsValue("flgDistributorCheckIn") && CommonInfo.hmapAppMasterFlags.get("flgDistributorCheckIn") == 1) {
                    fnCheckDistributionCheckInMandatory(flg100MeterValidation, flgCheckForLateReason);
                } else {
                    fnCheckDistributionCheckInNonMandatory(flg100MeterValidation, flgCheckForLateReason);
                }



            }
        });

        rb_workingYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int flg100MeterValidation = 0;

                if (CommonInfo.hmapAppMasterFlags.containsKey("flg100MeterValidation")) {
                    if (CommonInfo.hmapAppMasterFlags.get("flg100MeterValidation") == 1) {
                        flg100MeterValidation = 1;
                    }
                }

                int flgCheckForLateReason = 0;
                if (CommonInfo.hmapAppMasterFlags.containsKey("flgCheckForLateReason")) {
                    if (CommonInfo.hmapAppMasterFlags.get("flgCheckForLateReason") == 1) {
                        flgCheckForLateReason = 1;
                    }
                }
                if (rb_workingYes.isChecked()) {
                    button_takeSelfie.setVisibility(View.VISIBLE);
                    if(!SelfieName.equals("NA"))
                    selfieContainerFL.setVisibility(View.VISIBLE);
                    else
                        selfieContainerFL.setVisibility(View.GONE);

                    rb_workingNo.setChecked(false);
                    if (CommonInfo.hmapAppMasterFlags.get("flgDistributorCheckIn") == 0) {
                        ll_OverAllDistributionCheckin.setVisibility(View.GONE);
                    } else {
                        ll_OverAllDistributionCheckin.setVisibility(View.VISIBLE);
                    }
                    if (isLateApplicable && flgCheckForLateReason == 1) {
                        ll_reason.setVisibility(View.VISIBLE);
                    } else {
                        ll_reason.setVisibility(View.GONE);
                    }

                    ll_map.setVisibility(View.VISIBLE);
                    ll_Working_parent.setVisibility(View.VISIBLE);
                    ll_NoWorking_parent.setVisibility(View.GONE);
                    ll_map.setVisibility(View.VISIBLE);
                    ll_comment.setVisibility(View.VISIBLE);
                    but_Next.setVisibility(View.VISIBLE);

                    rg_yes_no.setVisibility(View.VISIBLE);

                    btn_refresh.setVisibility(View.VISIBLE);
                    tv_MapLocationCorrectText.setVisibility(View.VISIBLE);
                   /* String distDefault=mDataSource.getDfltDstbtr();
                    if(DbrArray.size()>1 && DbrArray.size()<3) {
                        spinner_for_filter.setSelection(1);
                        spinner_for_filter.setEnabled(false);
                    }
                    else
                    {
                        if(!TextUtils.isEmpty(distDefault))
                        {
                            spinner_for_filter.setSelection(adapterDBR.getPosition(distDefault));
                        }
                    }*/
                }
            }
        });

        rb_workingNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_workingNo.isChecked()) {
                    button_takeSelfie.setVisibility(View.GONE);
                            selfieContainerFL.setVisibility(View.GONE);
                    for (int i = 0; i < ll_Working.getChildCount(); i++) {
                        View view = ll_Working.getChildAt(i);
                        if (view instanceof CheckBox) {
                            CheckBox chckBox = (CheckBox) view;
                            if (chckBox.isChecked()) {
                                chckBox.setChecked(false);
                            }
                        } else if (view instanceof EditText) {
                            EditText editText = (EditText) view;
                            if (editText != null) {
                                editText.setText("");
                            }
                        }
                    }
                    rb_workingYes.setChecked(false);
                    ll_OverAllDistributionCheckin.setVisibility(View.GONE);
                    ll_map.setVisibility(View.GONE);
                    ll_Working_parent.setVisibility(View.GONE);
                    ll_NoWorking_parent.setVisibility(View.VISIBLE);
                    ll_map.setVisibility(View.GONE);
                    ll_reason.setVisibility(View.GONE);
                    btn_refresh.setVisibility(View.GONE);
                    ll_refresh.setVisibility(View.GONE);
                    rg_yes_no.setVisibility(View.GONE);

                    tv_MapLocationCorrectText.setVisibility(View.GONE);
                    spinner_location.setSelection(adapterLocation.getPosition("Select Working Location"));
                    // spinner_location.setVisibility(View.GONE);

                    ll_comment.setVisibility(View.VISIBLE);

                    but_Next.setText(getText(R.string.txtSubmit));
                    but_Next.setVisibility(View.VISIBLE);

                    DistributorName_Global = "Select Distributor";
                    DistributorId_Global = "0";
                    DistributorNodeType_Global = "0";


                }
            }
        });

        try {
            fnDeleteOlderImagesFromImageFolder();
            fnDeleteOlderXMLFromXMLFolder();
        }
        catch (Exception ex)
        {

        }
    }
    public void fnDeleteOlderImagesFromImageFolder()
    {
        try
        {

            File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.ImagesFolder);

            // check number of files in folder
            String [] AllFilesName= checkNumberOfFiles(del);


            if(AllFilesName!=null && AllFilesName.length>0) {


                for (int vdo = 0; vdo < AllFilesName.length; vdo++) {
                    String fileUri = AllFilesName[vdo];


                    String f1 = Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.ImagesFolder + "/" + fileUri;



                    File fdelete = new File(f1);
                    if (fdelete.exists())
                    {
                        if (fdelete.delete())
                        {
                            callBroadCast();
                        }

                    }
                }
            }
        }
        catch (Exception ex)
        {

        }
    }

    public void fnDeleteOlderXMLFromXMLFolder()
    {
        try
        {

            File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            // check number of files in folder
            String [] AllFilesName= checkNumberOfFiles(del);


            if(AllFilesName!=null && AllFilesName.length>0) {


                for (int vdo = 0; vdo < AllFilesName.length; vdo++) {
                    String fileUri = AllFilesName[vdo];
                    String f1 = Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.OrderXMLFolder + "/" + fileUri;
                    File fdelete = new File(f1);
                    String xmlFileName=fdelete.getName();
                    String urlString="";
                    String urlZipFileName="";
                    if(xmlFileName.contains(".xml"))
                    {
                        urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + xmlFileName;
                        urlZipFileName= CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + xmlFileName.replace(".xml",".zip");
                    }
                    else
                    {
                        urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + xmlFileName+".xml";
                        urlZipFileName= CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + xmlFileName+".zip";

                    }
                    File fdeletexml = new File(urlString);

                    try {
                        File fdeletezip = new File(urlZipFileName);
                        if (fdeletezip.exists())
                        {
                            if (fdeletezip.delete())
                            {
                                callBroadCast();
                            }

                        }
                    }
                    catch (Exception ex)
                    {

                    }


                    if (fdeletexml.exists())
                    {
                        if (fdeletexml.delete())
                        {
                            callBroadCast();
                        }

                    }
                }
            }
        }
        catch (Exception ex)
        {

        }
    }
    public void fnCheckDistributionCheckInNonMandatory(int flg100MeterValidation, int flgCheckForLateReason) {
        if ((rb_workingYes != null && rb_workingYes.isChecked() == true) || (rb_workingNo != null && rb_workingNo.isChecked() == true)) {
            if ((refreshCount == 2) || (rg_yes_no.getCheckedRadioButtonId() == R.id.rb_yes) && ((rb_workingYes != null && rb_workingYes.isChecked() == true))) {

                if (CommonInfo.DayStartClick == 0) {
                    if(pDialog2!=null && pDialog2.isShowing())
                    {

                        pDialog2.dismiss();
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                    alertDialog.setMessage(getResources().getString(R.string.selectAtleastOneOption));
                    alertDialog.setIcon(R.drawable.error);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            but_Next.setEnabled(true);
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    return;
                } else if (hmapSelectedCheckBoxData.containsKey("6") && TextUtils.isEmpty(et_otherPleaseSpecify.getText().toString().trim())) {
                    if(pDialog2!=null && pDialog2.isShowing())
                    {

                        pDialog2.dismiss();
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                    alertDialog.setMessage(getResources().getString(R.string.selectAtleastOneOptionwithedittext));
                    alertDialog.setIcon(R.drawable.error);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            but_Next.setEnabled(true);
                            dialog.dismiss();


                        }
                    });
                    alertDialog.show();
                    return;
                } else {
                    if (hmapSelectedCheckBoxData.containsKey("6")) {
                        hmapSelectedCheckBoxData.remove("6");

                        hmapSelectedCheckBoxData.put("6", et_otherPleaseSpecify.getText().toString().trim());
                    }

                    if (hmapSelectedCheckBoxData.size() > 0) {
                        ReasonId = "";
                        ReasonText = "";
                        for (Map.Entry<String, String> entry : hmapSelectedCheckBoxData.entrySet()) {
                            String key = entry.getKey().toString().trim();
                            String value = entry.getValue().toString().trim();

                            if (ReasonId.equals("")) {
                                ReasonId = key;
                            } else {
                                ReasonId = ReasonId + "$" + key;
                            }

                            if (ReasonText.equals("")) {
                                ReasonText = value;
                            } else {
                                ReasonText = ReasonText + "$" + value;
                            }
                        }


                    }


                    String commentValue = "NA";
                    EditText commenttext = (EditText) findViewById(R.id.commenttext);
                    if (!TextUtils.isEmpty(commenttext.getText().toString().trim())) {
                        commentValue = commenttext.getText().toString().trim();
                    } else {

                    }


                    if (DistributorName_Global.equals("Select Distributor")) {
                        DistributorName_Global = "NA";
                        DistributorId_Global = "0";
                        DistributorNodeType_Global = "0";
                    }

                    String flgGSTCapture = "NA", flgGSTCompliance = "NA", GSTNumber = "NA";
                           /* mDataSource.savetblSuplierMappingData(DistributorId_Global,""+DistributorNodeType_Global,flgGSTCapture,flgGSTCompliance,
                                    GSTNumber,FusedAddress,finalPinCode,finalCity,finalState,LattitudeFromLauncher,LongitudeFromLauncher,
                                    AccuracyFromLauncher,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                                    GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                                    NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                                    FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                                    FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                                    FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart);
*/
                    String LeaveStartDate = "NA";
                    String LeaveEndDate = "NA";
                    if (!TextUtils.isEmpty(fromDate.getText().toString().trim())) {
                        LeaveStartDate = fromDate.getText().toString().trim();
                    }
                    if (!TextUtils.isEmpty(todate.getText().toString().trim())) {
                        LeaveEndDate = todate.getText().toString().trim();
                    }
                    mPreferencesManager.clear();
                    mDataSource.fnDeleteOldDataOnDayStart();
                    mDataSource.updatetblAttandanceDetails("33", "No Working", ReasonId, ReasonText, commentValue, DistributorId_Global, DistributorNodeType_Global, DistributorName_Global, LeaveStartDate, LeaveEndDate,SelfieName,SelfieURL);
                    syncStartAfterSavindData();
                }
            }
            else if (((rg_yes_no.getCheckedRadioButtonId() != R.id.rb_yes)  ) && ((rb_workingYes != null && rb_workingYes.isChecked() == true))) {
                if(pDialog2!=null && pDialog2.isShowing())
                {

                    pDialog2.dismiss();
                }
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
                alertDialog.setMessage(getResources().getString(R.string.verifyLocation));

                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        but_Next.setEnabled(true);
                        dialog.dismiss();


                    }
                });
                alertDialog.show();

            } else if (((rb_workingNo != null && rb_workingNo.isChecked() == true))) {
                if ((rb_workingNo != null && rb_workingNo.isChecked() == true) && ReasonId.equals("18") && (TextUtils.isEmpty(fromDate.getText().toString().trim()) || TextUtils.isEmpty(todate.getText().toString().trim()))) {
                    if(pDialog2!=null && pDialog2.isShowing())
                    {

                        pDialog2.dismiss();
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                    alertDialog.setMessage("Please Valid dates for Privilege Leave");
                    alertDialog.setIcon(R.drawable.error);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            but_Next.setEnabled(true);
                            dialog.dismiss();



                        }
                    });
                    alertDialog.show();
                    return;
                } else if ((rb_workingNo != null && rb_workingNo.isChecked() == true) && ReasonId.equals("18") && (!TextUtils.isEmpty(fromDate.getText().toString().trim()) || !TextUtils.isEmpty(todate.getText().toString().trim()))) {
                    SimpleDateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy");
                    boolean b = false;
                    try {
                        if (dfDate.parse(fromDate.getText().toString().trim()).before(dfDate.parse(todate.getText().toString().trim()))) {
                            b = true;//If start date is before end date
                        } else if (dfDate.parse(fromDate.getText().toString().trim()).equals(dfDate.parse(todate.getText().toString().trim()))) {
                            b = true;//If two dates are equal
                        } else {
                            b = false; //If start date is after the end date
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (b == false) {
                        if(pDialog2!=null && pDialog2.isShowing())
                        {

                            pDialog2.dismiss();
                        }
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                        alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
                        alertDialog.setMessage("From date can not be greater then To Date");

                        alertDialog.setCancelable(false);

                        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                but_Next.setEnabled(true);
                                dialog.dismiss();


                            }
                        });
                        alertDialog.show();
                        return;

                    }
                }
                if (hmapSelectedCheckBoxData.containsKey("6")) {
                    hmapSelectedCheckBoxData.remove("6");

                    hmapSelectedCheckBoxData.put("6", et_otherPleaseSpecify.getText().toString().trim());
                }

                if (hmapSelectedCheckBoxData.size() > 0) {
                    ReasonId = "";
                    ReasonText = "";
                    for (Map.Entry<String, String> entry : hmapSelectedCheckBoxData.entrySet()) {
                        String key = entry.getKey().toString().trim();
                        String value = entry.getValue().toString().trim();

                        if (ReasonId.equals("")) {
                            ReasonId = key;
                        } else {
                            ReasonId = ReasonId + "$" + key;
                        }

                        if (ReasonText.equals("")) {
                            ReasonText = value;
                        } else {
                            ReasonText = ReasonText + "$" + value;
                        }
                    }


                }


                String commentValue = "NA";
                EditText commenttext = (EditText) findViewById(R.id.commenttext);
                if (!TextUtils.isEmpty(commenttext.getText().toString().trim())) {
                    commentValue = commenttext.getText().toString().trim();
                } else {

                }


                if (DistributorName_Global.equals("Select Distributor")) {
                    DistributorName_Global = "NA";
                    DistributorId_Global = "0";
                    DistributorNodeType_Global = "0";
                }

                String flgGSTCapture = "NA", flgGSTCompliance = "NA", GSTNumber = "NA";
                           /* mDataSource.savetblSuplierMappingData(DistributorId_Global,""+DistributorNodeType_Global,flgGSTCapture,flgGSTCompliance,
                                    GSTNumber,FusedAddress,finalPinCode,finalCity,finalState,LattitudeFromLauncher,LongitudeFromLauncher,
                                    AccuracyFromLauncher,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                                    GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                                    NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                                    FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                                    FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                                    FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart);
*/

                String LeaveStartDate = "NA";
                String LeaveEndDate = "NA";
                if (!TextUtils.isEmpty(fromDate.getText().toString().trim())) {
                    LeaveStartDate = fromDate.getText().toString().trim();
                }
                if (!TextUtils.isEmpty(todate.getText().toString().trim())) {
                    LeaveEndDate = todate.getText().toString().trim();
                }
                mPreferencesManager.clear();
                mDataSource.fnDeleteOldDataOnDayStart();
                mDataSource.updatetblAttandanceDetails("33", "No Working", ReasonId, ReasonText, commentValue, DistributorId_Global, DistributorNodeType_Global, DistributorName_Global, LeaveStartDate, LeaveEndDate,SelfieName,SelfieURL);
                syncStartAfterSavindData();
            }
        } else {
            if(pDialog2!=null && pDialog2.isShowing())
            {

                pDialog2.dismiss();
            }
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
            alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
            alertDialog.setMessage(getResources().getString(R.string.verifyLocation));

            alertDialog.setCancelable(false);

            alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    but_Next.setEnabled(true);
                    dialog.dismiss();


                }
            });
            alertDialog.show();
        }
    }

    public void fnCheckDistributionCheckInMandatory(int flg100MeterValidation, int flgCheckForLateReason) {
        if (CommonInfo.hmapAppMasterFlags.get("flgDistributorCheckIn") == 1) {
            if (rb_workingYes.isChecked()) {
                if (parseDateTime(crntServerTime).after(crntDateTime(crntAttndncTime)) && flgCheckForLateReason == 1) {
                    if (reasonId.equals("0")) {
                        if(pDialog2!=null && pDialog2.isShowing())
                        {
                            pDialog2.dismiss();
                        }
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                        alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                        alertDialog.setMessage(getResources().getString(R.string.selectReasonForLate));
                        alertDialog.setIcon(R.drawable.error);
                        alertDialog.setCancelable(false);

                        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                but_Next.setEnabled(true);
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        return;

                    } else if (spnr_late.getSelectedItem().toString().equals("Others.")) {

                        if (TextUtils.isEmpty(rsnLatetext.getText().toString())) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                            alertDialog.setMessage(getResources().getString(R.string.selectOtherReasonForLate));
                            alertDialog.setIcon(R.drawable.error);
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    but_Next.setEnabled(true);
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                            return;

                        } else {
                            otherReasonForLate = rsnLatetext.getText().toString();
                        }
                    } else {
                        otherReasonForLate = spnr_late.getSelectedItem().toString();
                    }
                }
            }
            if ((!spinner_location.getSelectedItem().toString().equals("At Warehouse Location")) && (!spinner_location.getSelectedItem().toString().equals("Other Location"))) {
                if (rb_workingYes.isChecked() && DistributorName_Global.equals("Select Distributor")) {
                    if(pDialog2!=null && pDialog2.isShowing())
                    {
                        pDialog2.dismiss();
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                    alertDialog.setMessage(getResources().getString(R.string.selectDistributorProceeds));
                    alertDialog.setIcon(R.drawable.error);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            but_Next.setEnabled(true);
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    return;
                } else if ((distanceCalc > 100.00) && (refreshCount < 2) && (hmapSelectedCheckBoxData.containsKey(reasonIdForMrktVisit)) && flg100MeterValidation == 1) {
                    if(pDialog2!=null && pDialog2.isShowing())
                    {
                        pDialog2.dismiss();
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                    alertDialog.setMessage(getResources().getString(R.string.txtLocation));
                    alertDialog.setIcon(R.drawable.error);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            but_Next.setEnabled(true);
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    return;
                }

                //  else if((refreshCount==2) || (rg_yes_no.getCheckedRadioButtonId()==R.id.rb_yes))
                if ((rb_workingYes != null && rb_workingYes.isChecked() == true) || (rb_workingNo != null && rb_workingNo.isChecked() == true)) {
                    if ((refreshCount == 2) || (rg_yes_no.getCheckedRadioButtonId() == R.id.rb_yes) && ((rb_workingYes != null && rb_workingYes.isChecked() == true))) {
                        if(pDialog2!=null && pDialog2.isShowing())
                        {
                            pDialog2.dismiss();
                        }
                        if (CommonInfo.DayStartClick == 0) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                            alertDialog.setMessage(getResources().getString(R.string.selectAtleastOneOption));
                            alertDialog.setIcon(R.drawable.error);
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    but_Next.setEnabled(true);
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                            return;
                        } else if (hmapSelectedCheckBoxData.containsKey("6") && TextUtils.isEmpty(et_otherPleaseSpecify.getText().toString().trim())) {
                            if(pDialog2!=null && pDialog2.isShowing())
                            {
                                pDialog2.dismiss();
                            }
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                            alertDialog.setMessage(getResources().getString(R.string.selectAtleastOneOptionwithedittext));
                            alertDialog.setIcon(R.drawable.error);
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    but_Next.setEnabled(true);
                                    dialog.dismiss();


                                }
                            });
                            alertDialog.show();
                            return;
                        } else if ((rb_workingNo != null && rb_workingNo.isChecked() == true) && hmapSelectedCheckBoxData.containsKey("18") && (TextUtils.isEmpty(fromDate.getText().toString().trim()) || TextUtils.isEmpty(todate.getText().toString().trim()))) {
                            if(pDialog2!=null && pDialog2.isShowing())
                            {
                                pDialog2.dismiss();
                            }
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                            alertDialog.setMessage("Please Valid dates for Privilege Leave");
                            alertDialog.setIcon(R.drawable.error);
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    but_Next.setEnabled(true);
                                    dialog.dismiss();


                                }
                            });
                            alertDialog.show();
                            return;
                        } else if ((rb_workingNo != null && rb_workingNo.isChecked() == true) && hmapSelectedCheckBoxData.containsKey("18") && (!TextUtils.isEmpty(fromDate.getText().toString().trim()) || !TextUtils.isEmpty(todate.getText().toString().trim()))) {
                            SimpleDateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy");
                            boolean b = false;
                            try {
                                if (dfDate.parse(fromDate.getText().toString().trim()).before(dfDate.parse(todate.getText().toString().trim()))) {
                                    b = true;//If start date is before end date
                                } else if (dfDate.parse(fromDate.getText().toString().trim()).equals(dfDate.parse(todate.getText().toString().trim()))) {
                                    b = true;//If two dates are equal
                                } else {
                                    b = false; //If start date is after the end date
                                }
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            if (b == true) {

                                return;
                            }
                        } else {
                            if (hmapSelectedCheckBoxData.containsKey("6")) {
                                hmapSelectedCheckBoxData.remove("6");

                                hmapSelectedCheckBoxData.put("6", et_otherPleaseSpecify.getText().toString().trim());
                            }

                            if (hmapSelectedCheckBoxData.size() > 0) {
                                ReasonId = "";
                                ReasonText = "";
                                for (Map.Entry<String, String> entry : hmapSelectedCheckBoxData.entrySet()) {
                                    String key = entry.getKey().toString().trim();
                                    String value = entry.getValue().toString().trim();

                                    if (ReasonId.equals("")) {
                                        ReasonId = key;
                                    } else {
                                        ReasonId = ReasonId + "$" + key;
                                    }

                                    if (ReasonText.equals("")) {
                                        ReasonText = value;
                                    } else {
                                        ReasonText = ReasonText + "$" + value;
                                    }
                                }


                            }


                            String commentValue = "NA";
                            EditText commenttext = (EditText) findViewById(R.id.commenttext);
                            if (!TextUtils.isEmpty(commenttext.getText().toString().trim())) {
                                commentValue = commenttext.getText().toString().trim();
                            } else {

                            }


                            if (DistributorName_Global.equals("Select Distributor")) {
                                DistributorName_Global = "NA";
                                DistributorId_Global = "0";
                                DistributorNodeType_Global = "0";
                            }

                            String flgGSTCapture = "NA", flgGSTCompliance = "NA", GSTNumber = "NA";
                           /* mDataSource.savetblSuplierMappingData(DistributorId_Global,""+DistributorNodeType_Global,flgGSTCapture,flgGSTCompliance,
                                    GSTNumber,FusedAddress,finalPinCode,finalCity,finalState,LattitudeFromLauncher,LongitudeFromLauncher,
                                    AccuracyFromLauncher,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                                    GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                                    NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                                    FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                                    FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                                    FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart);
*/

                            String LeaveStartDate = "NA";
                            String LeaveEndDate = "NA";
                            if (!TextUtils.isEmpty(fromDate.getText().toString().trim())) {
                                LeaveStartDate = fromDate.getText().toString().trim();
                            }
                            if (!TextUtils.isEmpty(todate.getText().toString().trim())) {
                                LeaveEndDate = todate.getText().toString().trim();
                            }
                            mPreferencesManager.clear();
                            mDataSource.fnDeleteOldDataOnDayStart();
                            mDataSource.updatetblAttandanceDetails("33", "No Working", ReasonId, ReasonText, commentValue, DistributorId_Global, DistributorNodeType_Global, DistributorName_Global, LeaveStartDate, LeaveEndDate,SelfieName,SelfieURL);
                            syncStartAfterSavindData();
                        }
                    } else if ((rg_yes_no.getCheckedRadioButtonId() == R.id.rb_workingYes) && ((rb_workingYes != null && rb_workingYes.isChecked() == true))) {
                        if(pDialog2!=null && pDialog2.isShowing())
                        {
                            pDialog2.dismiss();
                        }
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                        alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
                        alertDialog.setMessage(getResources().getString(R.string.verifyLocation));

                        alertDialog.setCancelable(false);

                        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                but_Next.setEnabled(true);
                                dialog.dismiss();


                            }
                        });
                        alertDialog.show();

                    } else if (((rb_workingNo != null && rb_workingNo.isChecked() == true))) {
                        if (hmapSelectedCheckBoxData.containsKey("6")) {
                            hmapSelectedCheckBoxData.remove("6");

                            hmapSelectedCheckBoxData.put("6", et_otherPleaseSpecify.getText().toString().trim());
                        }

                        if (hmapSelectedCheckBoxData.size() > 0) {
                            ReasonId = "";
                            ReasonText = "";
                            for (Map.Entry<String, String> entry : hmapSelectedCheckBoxData.entrySet()) {
                                String key = entry.getKey().toString().trim();
                                String value = entry.getValue().toString().trim();

                                if (ReasonId.equals("")) {
                                    ReasonId = key;
                                } else {
                                    ReasonId = ReasonId + "$" + key;
                                }

                                if (ReasonText.equals("")) {
                                    ReasonText = value;
                                } else {
                                    ReasonText = ReasonText + "$" + value;
                                }
                            }


                        }


                        String commentValue = "NA";
                        EditText commenttext = (EditText) findViewById(R.id.commenttext);
                        if (!TextUtils.isEmpty(commenttext.getText().toString().trim())) {
                            commentValue = commenttext.getText().toString().trim();
                        } else {

                        }


                        if (DistributorName_Global.equals("Select Distributor")) {
                            DistributorName_Global = "NA";
                            DistributorId_Global = "0";
                            DistributorNodeType_Global = "0";
                        }

                        String flgGSTCapture = "NA", flgGSTCompliance = "NA", GSTNumber = "NA";
                           /* mDataSource.savetblSuplierMappingData(DistributorId_Global,""+DistributorNodeType_Global,flgGSTCapture,flgGSTCompliance,
                                    GSTNumber,FusedAddress,finalPinCode,finalCity,finalState,LattitudeFromLauncher,LongitudeFromLauncher,
                                    AccuracyFromLauncher,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                                    GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                                    NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                                    FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                                    FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                                    FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart);
*/

                        String LeaveStartDate = "NA";
                        String LeaveEndDate = "NA";
                        if (!TextUtils.isEmpty(fromDate.getText().toString().trim())) {
                            LeaveStartDate = fromDate.getText().toString().trim();
                        }
                        if (!TextUtils.isEmpty(todate.getText().toString().trim())) {
                            LeaveEndDate = todate.getText().toString().trim();
                        }
                        mPreferencesManager.clear();
                        mDataSource.fnDeleteOldDataOnDayStart();
                        mDataSource.updatetblAttandanceDetails("33", "No Working", ReasonId, ReasonText, commentValue, DistributorId_Global, DistributorNodeType_Global, DistributorName_Global, LeaveStartDate, LeaveEndDate,SelfieName,SelfieURL);
                        syncStartAfterSavindData();
                    }
                } else {
                    if(pDialog2!=null && pDialog2.isShowing())
                    {
                        pDialog2.dismiss();
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
                    alertDialog.setMessage(getResources().getString(R.string.verifyLocation));

                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            but_Next.setEnabled(true);
                            dialog.dismiss();


                        }
                    });
                    alertDialog.show();
                }

            }


        }
    }


    public void syncStartAfterSavindData() {


        File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

        if (!OrderXMLFolder.exists()) {
            OrderXMLFolder.mkdirs();
        }

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
        int whattocall=0;
        if (isOnline()) {
            if ((rb_workingYes != null && rb_workingYes.isChecked() == true)) {
                if (pDialog2 != null && pDialog2.isShowing()) {
                    pDialog2.setTitle("Uploading Selfi and Attendance details to server");

                }
            }
            if (mDataSource.fnChecktblAttandanceDetailsRecord() > 0) {

                String[] fp2s;
                String[] NoOfOutletID= mDataSource.getAllAttendacePhotoDetail();

                try
                {


                    for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
                    {

                        int NoOfImages = mDataSource.getExistingPicNosAttendance();
                        String[] NoOfImgsPath = mDataSource.getImgsPathAttendance();


                        fp2s = new String[2];

                        for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
                        {
                            fp2s[0] = NoOfImgsPath[syCOUNT];
                            fp2s[1] = NoOfOutletID[chkCountstore];

                            // New Way

                            fnameIMG = fp2s[0];
                            UploadingImageName=fp2s[0];


                            String stID = fp2s[1];
                            String currentImageFileName=fnameIMG;

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
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                                String image_str= compressImage(fnameIMG);// BitMapToString(bmp);


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

                                String onlyDate= TimeUtils.getNetworkDateTime(getBaseContext(),"dd-MMM-yyyy HH:mm:ss.SSS");


                                try
                                {
                                    whattocall=1;
                                    uploadImage(image_str,currentImageFileName,"NA",stID,onlyDate,"0");

                                }catch(Exception e)
                                {
                                    IMGsyOK = 1;

                                }
                            }
                            else
                            {

                                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +currentImageFileName.toString().trim();

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

                                //

                            }


                        }


                    }

                    if(whattocall==0) {
                        int NoOfImagesFinal = mDataSource.getExistingPicNosAttendance();
                        if (NoOfImagesFinal == 0) {
                            CommonFunction.fnInsertDayStartAttendanceAPI(DayStartActivity.this, AppUtils.getToken(DayStartActivity.this), "", "Please wait loading Data!", 0);
                        } else {
                            but_Next.setEnabled(true);
                        }
                    }
                }
                catch(Exception e)
                {
                    IMGsyOK = 1;

                }




                                      /*  Intent mMyServiceIntent = new Intent(getCtx(), SyncJobService.class);  //is any of that needed?  idk.
                                        mMyServiceIntent.putExtra("whereTo", "DayStart");//
                                        mMyServiceIntent.putExtra("routeID", "0");//
                                        SyncJobService.enqueueWork(getCtx(), mMyServiceIntent);*/


            } else {
                if(pDialog2!=null && pDialog2.isShowing())
                {

                    pDialog2.dismiss();
                }
                but_Next.setEnabled(true);
                showAlertNoRecordForDayStart();
            }


        } else {
            if(pDialog2!=null && pDialog2.isShowing())
            {

                pDialog2.dismiss();
                but_Next.setEnabled(true);
            }
            showNoConnAlert(true);
        }


             /*   try {

                        new bgTasker().execute().get();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                        //System.out.println(e);
                } catch (ExecutionException e) {
                        e.printStackTrace();
                        //System.out.println(e);
                }*/
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

                CommonFunction.fnInsertDayStartAttendanceAPI(DayStartActivity.this, AppUtils.getToken(DayStartActivity.this), "", "Please wait loading Data!", 0);
						            	/* File file = new File(UploadingImageName.toString().trim());
							         	    file.delete();  */
            }
        }catch (Exception e){
            e.printStackTrace();
            if(pDialog2!=null && pDialog2.isShowing())
            {

                pDialog2.dismiss();
            }
        }
    }
    public void callBroadCast() {
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
        String result= android.util.Base64.encodeToString(arr, Base64.DEFAULT);
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

    @Override
    protected void onResume() {
        super.onResume();
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
        if (!isGPSok && !isNWok) {
            try {
                showSettingsAlert();
            } catch (Exception e) {

            }

            isGPSok = false;
            isNWok = false;
        } else {
                        /*if (isOnline())
                        {
                                //   new CurrentDateTime().execute();
                                CommonFunction.getGetServerTime(DayStartActivity.this, CommonInfo.imei);
                        }
                        else
                        {
                                showNoConnAlert(false);
                        }*/

            CommonInfo.crntServerTimecrntAttndncTime = TimeUtils.getNetworkDateTime(this, TimeUtils.DATE_TIME_FORMATForDisplay);
            // LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
            // llaaa.locationRetrievingAndDistanceCalculating(DayStartActivity.this);
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
        AppUtils.doCheckGPSEnable(DayStartActivity.this);
    }


    public void showNoConnAlert(final boolean isSyncdCalled) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DayStartActivity.this);
        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(R.string.NoDataConnectionFullMsg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        but_Next.setEnabled(true);
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


    public void showAlertNoRecordForDayStart() {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DayStartActivity.this);
        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage("Location not captured properly, please click OK to retry.");
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        but_Next.setEnabled(true);
                        LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
                        llaaa.locationRetrievingAndDistanceCalculating(DayStartActivity.this, true, true, 20, 0);


                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

    public String displayAlertDialog() {
        String str = "cb_NoWorking Selected;";
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_nostore, null);
        final EditText et_Reason = (EditText) alertLayout.findViewById(R.id.et_Reason);
        et_Reason.setVisibility(View.INVISIBLE);

        final Spinner spinner_reason = (Spinner) alertLayout.findViewById(R.id.spinner_reason);

        ArrayAdapter adapterCategory = new ArrayAdapter(DayStartActivity.this, android.R.layout.simple_spinner_item, reasonNames);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_reason.setAdapter(adapterCategory);

        spinner_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String spinnerReasonSelected = spinner_reason.getSelectedItem().toString();
                ReasonText = spinnerReasonSelected;
                int check = mDataSource.fetchFlgToShowTextBox(spinnerReasonSelected);
                ReasonId = mDataSource.fetchReasonIdBasedOnReasonDescr(spinnerReasonSelected);
                if (check == 0) {
                    et_Reason.setVisibility(View.INVISIBLE);
                } else {
                    et_Reason.setVisibility(View.VISIBLE);
                }


                //ReasonId,ReasonText
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.AlertDialogHeaderMsg);
        alert.setView(alertLayout);
        //alert.setIcon(R.drawable.info_ico);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (ReasonText.equals("") || ReasonText.equals("Select Reason")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please select the reason first.");
                    alertDialog.setIcon(R.drawable.error);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            displayAlertDialog();

                        }
                    });
                    alertDialog.show();
                } else {


                   /* Date pdaDate=new Date();
                    SimpleDateFormat sdfPDaDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    String CurDate = sdfPDaDate.format(pdaDate).toString().trim();

                    if(et_Reason.isShown())
                    {

                        if(TextUtils.isEmpty(et_Reason.getText().toString().trim()))
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Please enter the reason.");
                            alertDialog.setIcon(R.drawable.error);
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,int which)
                                {
                                    dialog.dismiss();
                                    displayAlertDialog();

                                }
                            });
                            alertDialog.show();
                        }
                        else
                        {
                            ReasonText = et_Reason.getText().toString();
                            if(isOnline())
                            {
                               GetNoStoreVisitForDay task = new GetNoStoreVisitForDay(DayStartActivity.this);
                                task.execute();
                            }
                            else
                            {
                                mDataSource.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
                                mDataSource.updateCurDatetblNoVisitStoreDetails(fDate);
                                mDataSource.updateSstattblNoVisitStoreDetails(3);


                                String aab=mDataSource.fetchReasonDescr();

                                showNoConnAlert();

                            }



                        }
                    }
                    else
                    {
                        if(isOnline())
                        {
                           GetNoStoreVisitForDay task = new GetNoStoreVisitForDay(DayStartActivity.this);
                            task.execute();
                        }
                        else
                        {
                            mDataSource.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
                            mDataSource.updateCurDatetblNoVisitStoreDetails(fDate);
                            mDataSource.updateSstattblNoVisitStoreDetails(3);
                            showNoConnAlert();

                        }


                    }*/


                }

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
        return str;
    }

    @SuppressLint("NewApi")
    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {
        //System.out.println("SHIVA"+fnLati+","+fnLongi+","+finalAccuracy+","+fnAccurateProvider+","+GpsLat+","+GpsLong+","+GpsAccuracy+","+NetwLat+","+NetwLong+","+NetwAccuracy+","+FusedLat+","+FusedLong+","+FusedAccuracy+","+AllProvidersLocation+","+GpsAddress+","+NetwAddress+","+FusedAddress+","+FusedLocationLatitudeWithFirstAttempt+","+FusedLocationLongitudeWithFirstAttempt+","+FusedLocationAccuracyWithFirstAttempt+","+fnLongi+","+flgLocationServicesOnOff+","+flgGPSOnOff+","+flgNetworkOnOff+","+flgFusedOnOff+","+flgInternetOnOffWhileLocationTracking+","+address+","+pincode+","+city+","+state);
        LattitudeFromLauncher = String.valueOf(fnLati);
        LongitudeFromLauncher = String.valueOf(fnLongi);
        AccuracyFromLauncher = String.valueOf(finalAccuracy);

        this.FnlAddress = FusedAddress;
        this.finalPinCode = pincode;
        this.finalCity = city;
        this.finalState = state;
        this.fnAccurateProvider = fnAccurateProvider;
        this.AllProvidersLocation = AllProvidersLocation;
        this.FusedAddress = FusedAddress;
        this.GpsLat = GpsLat;
        this.GpsLong = GpsLong;
        this.GpsAccuracy = GpsAccuracy;
        this.GpsAddress = GpsAddress;
        this.NetwLat = NetwLat;
        this.NetwLong = NetwLong;
        this.NetwAccuracy = NetwAccuracy;
        this.NetwAddress = NetwAddress;
        this.FusedLat = FusedLat;
        this.FusedLong = FusedLong;
        this.FusedAccuracy = FusedAccuracy;
        this.FusedLocationLatitudeWithFirstAttempt = FusedLocationLatitudeWithFirstAttempt;
        this.FusedLocationLongitudeWithFirstAttempt = FusedLocationLongitudeWithFirstAttempt;
        this.FusedLocationAccuracyWithFirstAttempt = FusedLocationAccuracyWithFirstAttempt;
        this.flgLocationServicesOnOff = flgLocationServicesOnOff;
        this.flgGPSOnOff = flgGPSOnOff;
        this.flgNetworkOnOff = flgNetworkOnOff;
        this.flgFusedOnOff = flgFusedOnOff;
        this.flgInternetOnOffWhileLocationTracking = flgInternetOnOffWhileLocationTracking;
        but_Next.setEnabled(true);
        if (!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy))) {


            fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy));
            manager = getFragmentManager();
            mapFrag = (MapFragment) manager.findFragmentById(R.id.map);
            if(mapFrag!=null) {
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(DayStartActivity.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);
            }
            //mDataSource.open();
            //   mPreferencesManager.setValue(AppUtils.ATTENDANCETIME, TimeUtils.getNetworkDateTime(this, TimeUtils.DATE_TIME_FORMATForDisplay));


            String LeaveStartDate = "NA";
            String LeaveEndDate = "NA";
            if (!TextUtils.isEmpty(fromDate.getText().toString().trim())) {
                LeaveStartDate = fromDate.getText().toString().trim();
            }
            if (!TextUtils.isEmpty(todate.getText().toString().trim())) {
                LeaveEndDate = todate.getText().toString().trim();
            }
            if(txt_DayStarttime==null) {
                try {
                    txt_DayStarttime.setText(TimeUtils.getNetworkDateTime(DayStartActivity.this, TimeUtils.DATE_TIME_FORMAT));
                } catch (Exception ex) {

                }
            }
            mDataSource.savetblAttandanceDetails(txt_DayStarttime.getText().toString().trim(), PersonNodeID, PersonNodeType, PersonName, OptionID, OptionDesc,
                    ReasonId, ReasonText, FusedAddress, finalPinCode, finalCity, finalState, fnLati, fnLongi,
                    finalAccuracy, "0", fnAccurateProvider, AllProvidersLocation, FusedAddress,
                    GpsLat, GpsLong, GpsAccuracy, GpsAddress,
                    NetwLat, NetwLong, NetwAccuracy, NetwAddress,
                    FusedLat, FusedLong, FusedAccuracy, FusedAddress,
                    FusedLocationLatitudeWithFirstAttempt, FusedLocationLongitudeWithFirstAttempt,
                    FusedLocationAccuracyWithFirstAttempt, 3, flgLocationServicesOnOff, flgGPSOnOff, flgNetworkOnOff,
                    flgFusedOnOff, flgInternetOnOffWhileLocationTracking, flgRestart, cityID, StateID, MapAddress, MapCity, MapPincode, MapState, fDate, LeaveStartDate, LeaveEndDate,SelfieName,SelfieURL);

            //mDataSource.close();


            mDataSource.saveTblLocationDetails(fnLati, fnLongi, finalAccuracy, address, city, pincode, state, fnAccurateProvider, GpsLat, GpsLong, GpsAccuracy, NetwLat, NetwLong, NetwAccuracy, FusedLat, FusedLong, FusedAccuracy, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt, FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt);

            //   mDataSource.fnupdateTodayAttenDateTime(mPreferencesManager.getStringValue(AppUtils.ATTENDANCETIME, TimeUtils.getNetworkDateTime(this, TimeUtils.DATE_TIME_FORMATForDisplay)));


        } else {
            countSubmitClicked++;
            if (countSubmitClicked == 1) {
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(DayStartActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
                alertDialog.setIcon(R.drawable.error_info_ico);
                alertDialog.setCancelable(false);
                // Setting Dialog Message
                alertDialog.setMessage(getText(R.string.AlertSameLoc));

                // On pressing Settings button
                alertDialog.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        countSubmitClicked++;
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

                // Showing Alert Message
                if (!isFinishing() && !isDestroyed())
                    alertDialog.show();


            } else {

                LattitudeFromLauncher = String.valueOf(fnLati);
                LongitudeFromLauncher = String.valueOf(fnLongi);
                AccuracyFromLauncher = String.valueOf(finalAccuracy);
                manager = getFragmentManager();
                mapFrag = (MapFragment) manager.findFragmentById(R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(DayStartActivity.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);

                //mDataSource.open();

                String LeaveStartDate = "NA";
                String LeaveEndDate = "NA";
                if (!TextUtils.isEmpty(fromDate.getText().toString().trim())) {
                    LeaveStartDate = fromDate.getText().toString().trim();
                }
                if (!TextUtils.isEmpty(todate.getText().toString().trim())) {
                    LeaveEndDate = todate.getText().toString().trim();
                }
                //  mPreferencesManager.setValue(AppUtils.ATTENDANCETIME, TimeUtils.getNetworkDateTime(this, TimeUtils.DATE_TIME_FORMATForDisplay));
                mDataSource.savetblAttandanceDetails(txt_DayStarttime.getText().toString().trim(), PersonNodeID, PersonNodeType, PersonName, OptionID, OptionDesc,
                        ReasonId, ReasonText, FusedAddress, finalPinCode, finalCity, finalState, fnLati, fnLongi,
                        finalAccuracy, "0", fnAccurateProvider, AllProvidersLocation, FusedAddress,
                        GpsLat, GpsLong, GpsAccuracy, GpsAddress,
                        NetwLat, NetwLong, NetwAccuracy, NetwAddress,
                        FusedLat, FusedLong, FusedAccuracy, FusedAddress,
                        FusedLocationLatitudeWithFirstAttempt, FusedLocationLongitudeWithFirstAttempt,
                        FusedLocationAccuracyWithFirstAttempt, 3, flgLocationServicesOnOff, flgGPSOnOff, flgNetworkOnOff,
                        flgFusedOnOff, flgInternetOnOffWhileLocationTracking, flgRestart, cityID, StateID, MapAddress, MapCity, MapPincode, MapState, fDate, LeaveStartDate, LeaveEndDate,SelfieName,SelfieURL);


                //mDataSource.close();
                mDataSource.deleteLocationTable();
                mDataSource.saveTblLocationDetails(fnLati, fnLongi, finalAccuracy, address, city, pincode, state, fnAccurateProvider, GpsLat, GpsLong, GpsAccuracy, NetwLat, NetwLong, NetwAccuracy, FusedLat, FusedLong, FusedAccuracy, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt, FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt);
                //  mDataSource.fnupdateTodayAttenDateTime(mPreferencesManager.getStringValue(AppUtils.ATTENDANCETIME, TimeUtils.getNetworkDateTime(this, TimeUtils.DATE_TIME_FORMATForDisplay)));

                //mDataSource.close();


            }


        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String mon = MONTHS[monthOfYear];
        if (fromDateBool) {
            frmDate.setText(dayOfMonth + "-" + mon + "-" + year);
        }
        if (toDateBool) {
            todateText.setText(dayOfMonth + "-" + mon + "-" + year);
        }
        toDateBool = false;
        fromDateBool = false;
    }

    @Override
    public void successAttendanceMarking(int flg) {
        if (flg == 1) {

            SharedPreferences.Editor editor = sPrefAttandance.edit();
            editor.clear();
            editor.commit();
            sPrefAttandance.edit().putString("AttandancePref", fDate).commit();
            flgDaySartWorking = 1;
            if(pDialog2!=null && pDialog2.isShowing())
            {

                pDialog2.dismiss();
            }
                if (rb_workingNo.isChecked()) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
                    alertDialog.setMessage("Today not working marked successfully.");

                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sPrefAttandance.edit();
                            editor.clear();
                            editor.commit();

                            finishAffinity();
                            dialog.dismiss();


                        }
                    });
                    alertDialog.show();
                } else {

                    if (isOnline()) {
                        CommonFunction.getAllMasterTableModelData(DayStartActivity.this, AppUtils.getToken(DayStartActivity.this), "", "Please wait loading Data!", 3);
                    } else {
                        showNoConnAlert(false);
                    }
                }


        }
    }

    @Override
    public void failureAttendanceMarking() {
        if(pDialog2!=null && pDialog2.isShowing())
        {

            pDialog2.dismiss();
        }
      //  but_Next.setEnabled(true);
        showAlertNoRecordForDayStart();
    }




    public void SyncNow() {


        try {

            File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!OrderXMLFolder.exists()) {
                OrderXMLFolder.mkdirs();
            }


            if (isOnline()) {
                if (mDataSource.fnChecktblAttandanceDetailsRecord() > 0) {
                    Intent mMyServiceIntent = new Intent(getCtx(), SyncJobService.class);  //is any of that needed?  idk.
                    mMyServiceIntent.putExtra("whereTo", "DayStart");//
                    mMyServiceIntent.putExtra("routeID", "0");//
                    SyncJobService.enqueueWork(getCtx(), mMyServiceIntent);
                } else {
                    showAlertNoRecordForDayStart();
                }


//                Intent mMyServiceIntent = new Intent(getCtx(), MyService.class);
//                mMyServiceIntent.putExtra("whereTo", "DayStart");//
//                mMyServiceIntent.putExtra("routeID", "0");//
//
//                if (!isMyServiceRunning(MyService.class)) {
//                    startService(mMyServiceIntent);
//                }

            } else {
                showNoConnAlert(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private void getReasonDetail() {


        hmapReasonIdAndDescr_details = mDataSource.fetch_Reason_Late();

        int index = 0;
        if (hmapReasonIdAndDescr_details != null) {
            reasonLate = new String[hmapReasonIdAndDescr_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<>(hmapReasonIdAndDescr_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                reasonLate[index] = me2.getKey().toString();
                index = index + 1;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DayStartActivity.this, android.R.layout.simple_spinner_item, reasonLate);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnr_late.setAdapter(adapter);
        }


    }

    @Override
    public void success() {

        mPreferencesManager.setValue(AppUtils.ATTENDANCETIME, TimeUtils.getNetworkDateTime(this, TimeUtils.DATE_TIME_FORMATForDisplay));
        mPreferencesManager.setValue(AppUtils.flgPersonDayStartDayEnd, 0);


            SharedPreferences.Editor editor = sPrefAttandance.edit();
            editor.clear();
            editor.commit();
            sPrefAttandance.edit().putString("AttandancePref", fDate).commit();
            flgDaySartWorking = 1;

            if ((CommonInfo.hmapAppMasterFlags.get("flgTargetShowatStart") == 1)) {
                Intent i = new Intent(DayStartActivity.this, SalesValueTarget.class);
                i.putExtra("IntentFrom", 0);
                startActivity(i);
                finish();
            }
               /* else if ((CommonInfo.hmapAppMasterFlags.get("flgIncentiveShowtStart") == 1)) {
                    Intent i=new Intent(DayStartActivity.this,IncentiveActivity.class);
                    i.putExtra("IntentFrom", 0);
                    startActivity(i);
                    finish();
                }*/
            else if ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 1)) {
                Intent intent = new Intent(DayStartActivity.this, DistributorCheckInSecondActivity.class);
                intent.putExtra("imei", imei);
                intent.putExtra("fDate", fDate);
                intent.putExtra("From", "DayStart");
                startActivity(intent);
                finish();
            } else if ((CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1)) {
                Intent intent = new Intent(DayStartActivity.this, WarehouseCheckInSecondActivity.class);
                intent.putExtra("imei", imei);
                intent.putExtra("fDate", fDate);
                intent.putExtra("From", "DayStart");
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent(DayStartActivity.this, AllButtonActivity.class);
                intent.putExtra("imei", imei);
                DayStartActivity.this.startActivity(intent);
                finish();
            }






//CommonInfo.crntServerTimecrntAttndncTime
               /* if((!TextUtils.isEmpty(CommonInfo.crntServerTimecrntAttndncTime)) && (CommonInfo.crntServerTimecrntAttndncTime!=null))
                {

                        if(CommonInfo.crntServerTimecrntAttndncTime.contains("^"))
                        {
                                crntServerTime= CommonInfo.crntServerTimecrntAttndncTime.split(Pattern.quote("^"))[0];
                                crntAttndncTime= CommonInfo.crntServerTimecrntAttndncTime.split(Pattern.quote("^"))[1];
                        }
                        else
                        {
                                crntServerTime= CommonInfo.crntServerTimecrntAttndncTime;
                        }

                        if(parseDateTime(crntServerTime).after(crntDateTime(crntAttndncTime)) )
                        {
                                isLateApplicable=false;
                *//*if(rb_workingYes.isChecked())
                {
                    ll_reason.setVisibility(View.VISIBLE);
                }*//*

                                //Toast.makeText(DayStartActivity.this,"Time between 9:30 and 10:30",Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                                isLateApplicable=false;
                                //Toast.makeText(DayStartActivity.this,"Time before 9:30",Toast.LENGTH_SHORT).show();
                        }


                }*/
    }

    @Override
    public void failure() {
        showAlertSingleButtonError("Error while fetching data.");
    }

    public Date parseDateTime(String time) {
        String inputPattern = "dd-MMM-yyyy hh:mm:ss aa";
        String outputPattern = "hh:mm aa";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);

        Date date, dateFnl = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
            dateFnl = outputFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Date fnldate = formatter.parse(dateInString);;
        return dateFnl;
    }

    public Date crntDateTime(String time) {
        String inputPattern = "hh:mm aa";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);


        Date date = null;


        try {
            date = inputFormat.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Date fnldate = formatter.parse(dateInString);;
        return date;
    }

    public String getDateAndTimeAMPM() {
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.ENGLISH);
        String curTime = TimeUtils.getNetworkDateTime(DayStartActivity.this, TimeUtils.DATE_TIME_FORMAT);
        return curTime;
    }

    public EditText getEditText(String tagVal) {
        EditText editText = new EditText(DayStartActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);

        params.leftMargin = 50;
        params.rightMargin = 50;
        editText.setBackground(getResources().getDrawable(R.drawable.et_boundary_retrun));
        editText.setLayoutParams(params);
        editText.setTag(tagVal);
        editText.setEms(10);
        return editText;
    }

    public boolean validateEditText() {
        boolean isEdiTextFilled = false;
        if (spinner_location.getSelectedItem().toString().equals("At Warehouse Location")) {
            if (TextUtils.isEmpty(et_DBR_Name.getText().toString().trim())) {
                alertForValidation(getString(R.string.AlertDialogHeaderErrorMsg), getString(R.string.specifyDBRName));
                isEdiTextFilled = true;
                return isEdiTextFilled;
            } else {
                CheckBox checkBox = (CheckBox) ll_Working.findViewWithTag("7");
                if (checkBox != null) {

                    hmapSelectedCheckBoxData.put("7", et_DBR_Name.getText().toString().trim());
                }

            }

        } else if (spinner_location.getSelectedItem().toString().equals("Other Location")) {
            if (TextUtils.isEmpty(et_DBR_Name.getText().toString().trim())) {
                alertForValidation(getString(R.string.AlertDialogHeaderErrorMsg), getString(R.string.specifyLocatione));
                isEdiTextFilled = true;
                return isEdiTextFilled;
            } else {
                hmapSelectedCheckBoxData.put("15", "Other Location" + "^" + et_DBR_Name.getText().toString().trim());
            }
        }
        if (listTxtBxToShow != null) {
            for (int i = 0; i < listTxtBxToShow.size(); i++) {
                CheckBox checkBox = (CheckBox) ll_Working.findViewWithTag(listTxtBxToShow.get(i));
                if (checkBox != null) {
                    if (checkBox.isChecked()) {
                        EditText editText = (EditText) ll_Working.findViewWithTag(listTxtBxToShow.get(i) + "_ed");
                        if (editText != null) {
                            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                                isEdiTextFilled = true;
                                alertForValidation(getString(R.string.AlertDialogHeaderErrorMsg), getString(R.string.selectAtleastOneOptionwithedittext));
                                break;
                            } else {
                                hmapSelectedCheckBoxData.put(listTxtBxToShow.get(i), editText.getText().toString().trim());
                            }
                        }

                    }

                }

            }
        }
        return isEdiTextFilled;
    }

    public void alertForValidation(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });
        alertDialog.show();
    }
}
