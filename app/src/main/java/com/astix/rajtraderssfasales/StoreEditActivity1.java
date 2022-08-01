package com.astix.rajtraderssfasales;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.ExifInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.astix.Common.CommonInfo;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.camera.CameraPreview;
import com.astix.rajtraderssfasales.location.LocationInterface;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobal;
import com.astix.rajtraderssfasales.model.TblStoreListMaster;
import com.astix.rajtraderssfasales.sync.SyncJobService;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.astix.rajtraderssfasales.camera.CameraUtils.findFrontFacingCamera;
import static com.astix.rajtraderssfasales.camera.CameraUtils.hasCamera;
import static com.astix.rajtraderssfasales.utils.AppUtils.doCheckGPSEnable;


public class StoreEditActivity1 extends BaseActivity implements OnMapReadyCallback, LocationInterface {

    private EditText store_name, owner_name, mobile_num, std_code, landline_num, email_add, et_Address,
            etPinCode, gst_number, whatsappNumber;
    private RadioGroup rg_gst, rg_yes_no, rg_whatsapp;
    private ImageView exit;
    private Button click_photo, save, updateLocation_btn, cancelLocation_bt, btn_refresh;
    private List<TblStoreListMaster> tblStoreListMasters = new ArrayList<>();
    public TblStoreListMaster tblStoreListMaster;
    String StoreID = "";
    int flgEditLOcation = 0, flgUpdateLocation = 0;
    String landlinenum, storename, ownername, mobilenum, address, city, state, pincode, gstNumber, WhatsApp_Num, EmailAdd;

    private StringBuilder stringBuilder;

    private TextView full_address_label, full_address, txt_rfrshCmnt, no_internet_msg;

    private LinearLayout address_layout, ll_refresh, ll_gst, ll_whatsapp, ll_CompleteLocationSection;

    String FnlAddress = "NA", finalPinCode = "NA";

    String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


    private boolean isLocationConfirm = false;
    String LattitudeFromLauncher = "NA";
    String LongitudeFromLauncher = "NA";
    public String AccuracyFromLauncher = "NA";
    MapFragment mapFrag;
    int countSubmitClicked = 0;
    int refreshCount = 0;


    LocationRetreivingGlobal locationRetreivingGlobal;
    LocationManager locationManager;

    private Dialog dialog;
    private LinearLayout cameraPreview;
    private Button switchCamera;
    Camera mCamera;
    CameraPreview mPreview;
    private boolean isLighOn;
    private Camera.PictureCallback mPicture;
    private ImageView flashImage;
    private Button capture, cancelCam;

    float mDist = 0;
    ArrayList<Object> arrImageData = new ArrayList<Object>();
    Uri uriSavedImage;
    String uriStringPath = "";
    ArrayList<String> listimagePath = new ArrayList<String>();
    String userName, imageName, imagButtonTag;
    String clickedTagPhoto = "Shop Sign_board";
    public LinkedHashMap<String, Integer> hmapisPhotolicked = new LinkedHashMap<String, Integer>();
    LinkedHashMap<String, String> hmapImageClkdTempIdData = new LinkedHashMap<String, String>();
    LinearLayout ll_data;
    int cityID;
    int BeatID;
    String BeatName = "NA";
    int selectedStateID = 0;
    List<String> cityAndStateList = new ArrayList<>();
    Spinner spinner_state, spinner_city, spinner_RouteList;
    LinkedHashMap<String, String> city_list = new LinkedHashMap<>();
    LinkedHashMap<String, String> state_list = new LinkedHashMap<>();
    LinkedHashMap<String, String> route_list = new LinkedHashMap<>();
    List<String> city_names, state_name, route_names;
    int selectedCityID = 0;
    int selectedBeatID = 0;
    String StateNAME = "";

    int flgGST = 0, flgWhatsapp = 0;

    @BindView(R.id.rb_whatsapp_no)
    RadioButton rb_whatsapp_no;

    @BindView(R.id.rb_whatsapp_yes)
    RadioButton rb_whatsapp_yes;

    @BindView(R.id.rb_yes)
    RadioButton rb_yes;

    @BindView(R.id.rb_not_required)
    RadioButton rb_not_required;


    @BindView(R.id.rb_pending)
    RadioButton rb_pending;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_edit1);
        ButterKnife.bind(this);
        doCheckGPSEnable(this);
        store_name = (EditText) findViewById(R.id.store_name_et);
        owner_name = (EditText) findViewById(R.id.owner_name_et);
        mobile_num = (EditText) findViewById(R.id.mobile_num_et);
        std_code = (EditText) findViewById(R.id.stdcode_et);
        landline_num = (EditText) findViewById(R.id.landline_et);
        email_add = (EditText) findViewById(R.id.email_add_et);
        rg_gst = (RadioGroup) findViewById(R.id.radio_gst);
        exit = (ImageView) findViewById(R.id.img_exit);
        click_photo = (Button) findViewById(R.id.click_photo_btn);
        save = (Button) findViewById(R.id.save_btn);
        et_Address = (EditText) findViewById(R.id.et_Address);

        etPinCode = (EditText) findViewById(R.id.etPinCode);
        updateLocation_btn = (Button) findViewById(R.id.updateLocation_bt);
        cancelLocation_bt = (Button) findViewById(R.id.cancellLocation_bt);
        address_layout = (LinearLayout) findViewById(R.id.address_layout);
        txt_rfrshCmnt = (TextView) findViewById(R.id.txt_rfrshCmnt);
        ll_refresh = (LinearLayout) findViewById(R.id.ll_refresh);
        ll_CompleteLocationSection = (LinearLayout) findViewById(R.id.ll_CompleteLocationSection);
        ll_data = (LinearLayout) findViewById(R.id.ll_data);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);

        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_state = (Spinner) findViewById(R.id.spinner_state);
        spinner_RouteList = (Spinner) findViewById(R.id.spinner_RouteList);
        gst_number = (EditText) findViewById(R.id.gst_num_et);
        rg_yes_no = (RadioGroup) findViewById(R.id.rg_yes_no);
        ll_gst = (LinearLayout) findViewById(R.id.ll_gst);
        ll_whatsapp = (LinearLayout) findViewById(R.id.ll_whatsapp);
        rg_whatsapp = (RadioGroup) findViewById(R.id.rg_whatsapp);
        whatsappNumber = (EditText) findViewById(R.id.whatsapp_mobile_num_et);
        no_internet_msg = (TextView) findViewById(R.id.no_internet_msg);


        StoreID = getIntent().getExtras().getString("storeID");
        getDataFromDb();
        city_names = new ArrayList<>();
        state_name = new ArrayList<>();
        route_names = new ArrayList<>();
        getCityDetails();
        getStateDetails();
        getRouteDetails();

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        store_name.setText(tblStoreListMaster.getStoreName());
        owner_name.setText(tblStoreListMaster.getOwnerName());
        mobile_num.setText(tblStoreListMaster.getStoreContactNo());
        flgEditLOcation = tblStoreListMaster.getFlgLocationEdit();
        // if(tblStoreListMaster.getFlgGSTCapture()==1) {
        if (tblStoreListMaster.getTaxNumber() != null && !tblStoreListMaster.getTaxNumber().equals("") && !tblStoreListMaster.getTaxNumber().equals("0") && !tblStoreListMaster.getTaxNumber().equals("NA") && !tblStoreListMaster.getTaxNumber().equals("null")) {
            ll_gst.setVisibility(View.VISIBLE);
            gst_number.setVisibility(View.VISIBLE);
            gst_number.setText(tblStoreListMaster.getGSTNumber());
            rb_yes.setChecked(true);
        } else {
            ll_gst.setVisibility(View.GONE);
            gst_number.setVisibility(View.GONE);
            gst_number.setText("");
        }
       /* }
        else  if(tblStoreListMaster.getFlgGSTCapture()==0 || tblStoreListMaster.getFlgGSTCapture()==2)
        {
            if(tblStoreListMaster.getFlgGSTCapture()==0)
            {
                rb_not_required.setChecked(true);
            }
            if(tblStoreListMaster.getFlgGSTCapture()==2)
            {
                rb_pending.setChecked(true);
            }

            gst_number.setVisibility(View.GONE);
            ll_gst.setVisibility(View.GONE);
        }*/
        LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
        llaaa.locationRetrievingAndDistanceCalculating(this, false, true, 20, 0);

        if (tblStoreListMaster.getWhatsappNumber() == null) {
            ll_whatsapp.setVisibility(View.GONE);
        } else {
            if (tblStoreListMasters.get(0).getWhatsappNumber().equals(tblStoreListMasters.get(0).getStoreContactNo())) {
                rb_whatsapp_yes.setChecked(true);
                ll_whatsapp.setVisibility(View.GONE);
            } else {
                rb_whatsapp_no.setChecked(true);
                ll_whatsapp.setVisibility(View.VISIBLE);
                whatsappNumber.setText(tblStoreListMasters.get(0).getWhatsappNumber());
            }
        }

        if (tblStoreListMasters.get(0).getLandlineNum() == null || tblStoreListMasters.get(0).getLandlineNum().equals("0") || tblStoreListMasters.get(0).getLandlineNum().equals("")) {
            std_code.setText("");
            landline_num.setText("");
        } else {
            if (tblStoreListMasters.get(0).getLandlineNum().charAt(0) == 0) {
                std_code.setText(tblStoreListMasters.get(0).getLandlineNum().substring(0, 4));
                landline_num.setText(tblStoreListMasters.get(0).getLandlineNum().substring(4));
            } else {
                std_code.setText(tblStoreListMasters.get(0).getLandlineNum().substring(0, 3));
                landline_num.setText(tblStoreListMasters.get(0).getLandlineNum().substring(3));
            }


        }
        if (tblStoreListMasters.get(0).getEmailID() == null || tblStoreListMasters.get(0).getEmailID().equals("") || tblStoreListMasters.get(0).getEmailID().equals("null")) {
            email_add.setText("");
        } else {
            email_add.setText(tblStoreListMasters.get(0).getEmailID());
        }

        manager = getFragmentManager();
        mapFrag = (MapFragment) manager.findFragmentById(R.id.map);
        if (mapFrag != null && mapFrag.getView() != null)
            mapFrag.getView().setVisibility(View.VISIBLE);
        mapFrag.getMapAsync(StoreEditActivity1.this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.show(mapFrag);

        if (flgEditLOcation == 0) {
            et_Address.setText(tblStoreListMaster.getStoreAddress() + "," + tblStoreListMaster.getStoreCity() + "," + tblStoreListMaster.getStoreState() + ","
                    + tblStoreListMaster.getStorePinCode());


        } else {

            // et_Village.setText(tblStoreListMaster.getStoreCity());

            et_Address.setText(tblStoreListMasters.get(0).getStoreAddress());
            etPinCode.setText(tblStoreListMasters.get(0).getStorePinCode());

            //et_state.setText(tblStoreListMasters.get(0).getStoreState());
            /*full_address.setText(tblStoreListMaster.getStoreAddress() + "," + tblStoreListMaster.getStoreCity() + "," + tblStoreListMaster.getStoreState() + ","
                    + tblStoreListMaster.getStorePinCode());*/
        }


        ArrayAdapter RouteAdapter = new ArrayAdapter(StoreEditActivity1.this, android.R.layout.simple_spinner_item, route_names);
        RouteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_RouteList.setAdapter(RouteAdapter);
        int index = 0;
        if (route_list != null && route_list.size() > 0) {

            String SelectedRouteDefaultActive = "0";
            Set set2 = route_list.entrySet();
            Iterator iterator = set2.iterator();
            boolean isRouteSelected = false;
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                if (BeatName.equals(me2.getKey())) {
                    isRouteSelected = true;

                    break;
                }
                index = index + 1;
            }
            if (isRouteSelected) {
                spinner_RouteList.setSelection(index + 1);
            } else {
                spinner_RouteList.setSelection(0);
            }
        }


        ArrayAdapter cityAdapter = new ArrayAdapter(StoreEditActivity1.this, android.R.layout.simple_spinner_item, city_names);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(cityAdapter);

        final ArrayAdapter stateAdapter = new ArrayAdapter(StoreEditActivity1.this, android.R.layout.simple_spinner_item, state_name);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(stateAdapter);
        if (cityAndStateList != null && cityAndStateList.size() > 0) {
            selectedCityID = cityID;
            StateNAME = cityAndStateList.get(1);
            spinner_city.setSelection(cityAdapter.getPosition(cityAndStateList.get(0)));
            spinner_state.setSelection(stateAdapter.getPosition(cityAndStateList.get(1)));
        }

        et_Address.setEnabled(false);
        spinner_state.setEnabled(false);
        spinner_city.setEnabled(false);
        etPinCode.setEnabled(false);
        spinner_RouteList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedBeatID = Integer.parseInt(route_list.get(route_names.get(position)));

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        click_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCamera();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOnbackButtonClick("Alert", "Your changes will not be saved.Are you sure you want to exit?");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        mobile_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!mobile_num.getText().toString().isEmpty()) {
                        if (!isNumberValid(mobile_num.getText().toString().trim())) {

                            showAlertSingleButtonInfoForValidation("Please enter 10 digits contact number and number cannot be start with 0, 1, 2, 3 or 4", mobile_num);
                            return;
                        }
                    }
                }
            }
        });

        whatsappNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!whatsappNumber.getText().toString().isEmpty()) {
                        if (!isNumberValid(whatsappNumber.getText().toString().trim())) {

                            showAlertSingleButtonInfoForValidation("Please enter 10 digits contact number and number cannot be start with 0, 1, 2, 3 or 4", whatsappNumber);
                            return;
                        }
                    }
                }
            }
        });


        owner_name.setFilters(new InputFilter[]{getEditTextFilter()});
        gst_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!gst_number.getText().toString().isEmpty()) {
                        if (gst_number.getText().toString().length() != 15) {
                            showAlertSingleButtonInfoForValidation("Please enter proper GST Number", gst_number);
                            // gstno_txt.requestFocus();
                            return;
                        } else {
                            String firstTwoStr = gst_number.getText().toString().substring(0, 2);
                            Pattern p = Pattern.compile("[0-9]{2}");
                            Matcher m = p.matcher(firstTwoStr);
                            if (!m.find() || !m.group().equals(firstTwoStr)) {
                                //gstno_txt.requestFocus();
                                showAlertSingleButtonInfoForValidation("Please enter proper GST Number", gst_number);
                            }
                        }

                    }
                }
            }
        });
       /* et_state.setFilters(new InputFilter[]{getEditTextFilter()});
        et_Village.setFilters(new InputFilter[]{getEditTextFilter()});*/
        if (flgEditLOcation == 0) {
            updateLocation_btn.setVisibility(View.GONE);
            et_Address.setEnabled(false);
            etPinCode.setEnabled(false);

        } else {
            updateLocation_btn.setVisibility(View.VISIBLE);


        }


        rg_gst.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_not_required:
                        flgGST = 0;
                        gst_number.setVisibility(View.GONE);
                        ll_gst.setVisibility(View.GONE);
                        break;

                    case R.id.rb_yes:
                        flgGST = 1;
                        ll_gst.setVisibility(View.VISIBLE);
                        gst_number.setVisibility(View.VISIBLE);
                        break;

                    case R.id.rb_pending:
                        flgGST = 2;
                        ll_gst.setVisibility(View.GONE);
                        gst_number.setVisibility(View.GONE);
                        break;

                }


            }
        });

        rg_whatsapp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_whatsapp_no:
                        flgWhatsapp = 0;

                        ll_whatsapp.setVisibility(View.VISIBLE);
                        break;

                    case R.id.rb_whatsapp_yes:
                        flgWhatsapp = 1;
                        ll_whatsapp.setVisibility(View.GONE);
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

                locationRetreivingGlobal = new LocationRetreivingGlobal();
                locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(StoreEditActivity1.this, true, true, 20, 0);

            }
        });

        updateLocation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation_btn.setVisibility(View.GONE);
                cancelLocation_bt.setVisibility(View.VISIBLE);
                ll_CompleteLocationSection.setVisibility(View.VISIBLE);
                flgUpdateLocation = 1;
                et_Address.setEnabled(true);
                spinner_city.setEnabled(true);
                spinner_state.setEnabled(true);
                etPinCode.setEnabled(true);

                spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            selectedCityID = Integer.parseInt(city_list.get(city_names.get(position)));
                            if (selectedCityID == -1 || selectedCityID == 0) {
                                spinner_state.setEnabled(false);
                            } else {
                                spinner_state.setEnabled(true);
                            }

                            StateNAME = mDataSource.getStateName(selectedCityID);

                            spinner_state.setSelection(stateAdapter.getPosition(StateNAME));
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                locationRetreivingGlobal = new LocationRetreivingGlobal();
                locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(StoreEditActivity1.this, true, true, 20, 0);


            }

        });

        cancelLocation_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation_btn.setVisibility(View.VISIBLE);
                cancelLocation_bt.setVisibility(View.GONE);
                ll_CompleteLocationSection.setVisibility(View.GONE);
                flgUpdateLocation = 0;
                et_Address.setEnabled(false);
                spinner_city.setEnabled(false);
                spinner_state.setEnabled(false);
                etPinCode.setEnabled(false);
            }
        });

        rg_yes_no.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i != -1) {
                    RadioButton radioButtonVal = radioGroup.findViewById(i);
                    if (radioButtonVal.getId() == R.id.rb_yes_loc) {
                        ll_refresh.setVisibility(View.GONE);
                        isLocationConfirm = true;
                        isLocationSelected = true;
                    } else if (radioButtonVal.getId() == R.id.rb_no_loc) {
                        ll_refresh.setVisibility(View.VISIBLE);
                        txt_rfrshCmnt.setText(R.string.RefreshLoc);
                        isLocationConfirm = false;
                        isLocationSelected = true;
                    }
                }
            }
        });


    }

    private void getDataFromDb() {
        tblStoreListMasters = mDataSource.getStoreListDetails(StoreID);
        tblStoreListMaster = tblStoreListMasters.get(0);
        if (tblStoreListMaster.getCityID() != 0)
            cityID = tblStoreListMaster.getCityID();

        if (tblStoreListMaster.getRouteID() != 0)
            BeatID = tblStoreListMaster.getRouteID();

        BeatName = mDataSource.GetRouteNameBasedOnRouteID("" + BeatID);

        selectedStateID = tblStoreListMaster.getStateID();
        cityAndStateList = mDataSource.getCityAndStateName(cityID);
        city_list = mDataSource.fngetCityList();
        state_list = mDataSource.fngetDistinctState();
        route_list = mDataSource.fngetRouteList();
        LattitudeFromLauncher = "" + tblStoreListMaster.getStoreLatitude();
        LongitudeFromLauncher = "" + tblStoreListMaster.getStoreLongitude();

    }

    private void getStateDetails() {


        int index = 0;
        state_name.clear();
        state_name.add(0, "Select State");
        if (state_list != null && state_list.size() > 0) {
            for (Map.Entry<String, String> entry : state_list.entrySet()) {
                if (!entry.getKey().equals("Select")) {

                    state_name.add(entry.getKey().toString());
                }
            }

        }


    }

    private void getCityDetails() {


        int index = 0;
        city_names.clear();
        city_names.add(0, "Select City");
        if (city_list != null && city_list.size() > 0) {
            for (Map.Entry<String, String> entry : city_list.entrySet()) {
                city_names.add(entry.getKey().toString());
            }
        }


    }

    private void getRouteDetails() {


        int index = 0;
        route_names.clear();
        route_names.add(0, "Select Beat");
        if (route_list != null && route_list.size() > 0) {
            for (Map.Entry<String, String> entry : route_list.entrySet()) {
                route_names.add(entry.getKey().toString());
            }
        }


    }

    public void showAlertSingleButtonInfoForValidation(String msg, final EditText editText) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


    public void alertOnbackButtonClick(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(getResources().getString(R.string.txtExitwithoutSave), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(StoreEditActivity1.this, StoreSelection.class);
                startActivity(intent);
                finish();


            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton(getResources().getString(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
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
                MarkerOptions marker = new MarkerOptions().position(new LatLng(tblStoreListMasters.get(0).getStoreLatitude(), tblStoreListMasters.get(0).getStoreLongitude())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                Marker locationMarker = googleMap.addMarker(marker);
                locationMarker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
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


    public void saveData() {
        stringBuilder = new StringBuilder();
        int i = 0;
        if (!store_name.getText().toString().trim().equals("")
                && !owner_name.getText().toString().trim().equals("")
                && !mobile_num.getText().toString().trim().equals("") && selectedBeatID == 0) {//&& hmapisPhotolicked != null && hmapisPhotolicked.size() > 0

            if ((ll_CompleteLocationSection.getVisibility() == View.VISIBLE && isLocationConfirm) ||
                    (ll_CompleteLocationSection.getVisibility() == View.GONE && isLocationConfirm) ||
                    (ll_CompleteLocationSection.getVisibility() == View.GONE && !isLocationConfirm)) {

                  /*  for (Map.Entry<String, Integer> entry : hmapisPhotolicked.entrySet()) {
                        if (entry.getValue() == 0) {
                            stringBuilder.append("\n" + (++i) + ". Please click at least one shop sign-board photo");
                        }
                    }*/


                if (flgUpdateLocation == 1) {
                    if (!et_Address.getText().toString().trim().equals("")
                            && !etPinCode.getText().toString().trim().equals("") && selectedCityID != -1) {
                        if (!et_Address.getText().toString().trim().equals("")) {
                            if (et_Address.getText().toString().trim().length() > 9) {
                                address = et_Address.getText().toString();
                            } else {
                      /*  showAlertSingleButtonInfo("Address can not be less then 10 characters");
                        return;*/
                                stringBuilder.append("\n" + (++i) + ". Address can not be less then 10 characters.");
                            }
                        }

                        if (selectedCityID == 0) {
                            stringBuilder.append("\n" + (++i) + ". Please fill City.");
                        }

                        if (!etPinCode.getText().toString().trim().equals("")) {
                            if (etPinCode.getText().toString().trim().length() == 6) {
                                pincode = etPinCode.getText().toString();
                            } else {
                                stringBuilder.append("\n" + (++i) + ". Enter the 6 digits pincode.");

                            }
                        }

                        /*if (!et_Village.getText().toString().trim().equals("")) {
                            if (et_Village.getText().toString().trim().length() > 2) {
                                city = et_Village.getText().toString();
                            } else {
                                stringBuilder.append("\n" + (++i) + ". City/Village can not be less then 3 characters.");

                            }
                        }
                        if (!et_state.getText().toString().trim().equals("")) {
                            state = et_state.getText().toString();
                        }*/
                    } else {


                        if (et_Address.getText().toString().trim().equals("")) {
                            stringBuilder.append("\n" + (++i) + ". Fill Address.");
//                showAlertSingleButtonInfo("fill Address.");
                        }
                      /*  if (et_state.getText().toString().trim().equals("")) {
                            stringBuilder.append("\n" + (++i) + ". Fill State.");
                            //showAlertSingleButtonInfo("Please fill State.");
                        }
                        if (et_Village.getText().toString().trim().equals("")) {
                            stringBuilder.append("\n" + (++i) + ". Fill City/Village.");
                        }*/
                        if (etPinCode.getText().toString().trim().equals("")) {
                            stringBuilder.append("\n" + (++i) + ". Fill Pin code.");
                        }
                    }
                }

                if (flgGST == 1) {
                    if (gst_number.getText().toString().isEmpty() || gst_number.getText().toString().equals("NA")) {
                        showAlertSingleButtonInfo("Please enter GST Number");
                        return;
                    } else {
                        gstNumber = gst_number.getText().toString();
                    }
                } else {
                    gstNumber = "";
                }


                if (!landline_num.getText().toString().trim().equals("") || !std_code.getText().toString().trim().equals("")) {
                    if (!TextUtils.isEmpty(landline_num.getText().toString()) || !TextUtils.isEmpty(std_code.getText().toString())) {
                        int totalMinLengthOfPhn = 11;
                        int totalMaxLengthOfPhn = 15;
                        int std_code1 = 0;
                        if (!TextUtils.isEmpty(std_code.getText().toString()))
                            std_code1 = std_code.getText().length();

                        int landline = 0;

                        if (!TextUtils.isEmpty(landline_num.getText().toString()))
                            landline = landline_num.getText().length();

                        if ((std_code1 + landline) < totalMinLengthOfPhn || (std_code1 + landline) > totalMaxLengthOfPhn) {
                            stringBuilder.append("\n" + (++i) + ". Please fill proper Landline Number.");
                        } else {
                            landlinenum = std_code.getText().toString().trim() + landline_num.getText().toString().trim();
                        }
                    } else {
                        landlinenum = std_code.getText().toString().trim() + landline_num.getText().toString().trim();
                    }
                } else {
                    landlinenum = std_code.getText().toString().trim() + landline_num.getText().toString().trim();
                }

                if (!email_add.getText().toString().equals("")) {
                    if (!email_add.getText().toString().trim().matches(emailPattern)) {
                        stringBuilder.append("\n" + (++i) + ". Please fill proper email address.");
                    } else {
                        EmailAdd = email_add.getText().toString().trim();
                    }
                }

                if (!mobile_num.getText().toString().trim().equals("")) {
                    if (!isNumberValid(mobile_num.getText().toString().trim())) {
                        showAlertSingleButtonInfo("Please enter correct contact number");
                        return;
                    } else {
                        mobilenum = mobile_num.getText().toString().trim();
                        if (flgWhatsapp == 0) {
                            WhatsApp_Num = whatsappNumber.getText().toString();
                        } else {
                            WhatsApp_Num = mobilenum;
                        }
                    }
                } else
                    mobilenum = "";


                if (!owner_name.getText().toString().trim().equals(""))
                    ownername = owner_name.getText().toString().trim();
                else
                    ownername = "";

                storename = store_name.getText().toString().trim();


                if (!stringBuilder.toString().equals("")) {
                    String str = "Following fields are mandatory. Please fill all these fields.\n";
                    // validationString.append("\n Confirm your location");
                    str = str + stringBuilder.toString();
                    showAlertSingleButtonInfo(str);
                    return;
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(StoreEditActivity1.this);
                    builder.setTitle("Alert")
                            .setMessage("Are you sure you want to submit the edited details?")
                            .setCancelable(false)
                            .setIcon(R.drawable.info_ico)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();

                                    try {
                                        StoreEditActivity1.FullSyncDataNow task = new StoreEditActivity1.FullSyncDataNow(StoreEditActivity1.this);
                                        task.execute();
                                    } catch (Exception ex) {

                                    }

                                }

                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                }

            } else {
                showAlertSingleButtonInfo("Please confirm your location");
            }

        } else {
            if (selectedBeatID == 0) {
                stringBuilder.append("\n" + (++i) + ". Please Select Beat.");
            }
            if (store_name.getText().toString().trim().equals("")) {
                stringBuilder.append("\n" + (++i) + ". Fill Store Name.");
                //    showAlertSingleButtonInfo("Please fill Store Name.");
            }

               /* if(hmapisPhotolicked == null|| hmapisPhotolicked.size() == 0) {
                    stringBuilder.append("\n" + (++i) + ". Please click at least one photo of sign board");
                }*/


            if (owner_name.getText().toString().trim().equals("")) {
                stringBuilder.append("\n" + (++i) + ". Fill Owner Name");
            }

            if (mobile_num.getText().toString().trim().equals("")) {
                stringBuilder.append("\n" + (++i) + ". Type Owner's Contact Number");
            }

            if (!landline_num.getText().toString().trim().equals("") || !std_code.getText().toString().trim().equals("")) {
                if (!TextUtils.isEmpty(landline_num.getText().toString()) || !TextUtils.isEmpty(std_code.getText().toString())) {
                    int totalMinLengthOfPhn = 11;
                    int totalMaxLengthOfPhn = 15;
                    int std_code1 = 0;
                    if (!TextUtils.isEmpty(std_code.getText().toString()))
                        std_code1 = std_code.getText().length();

                    int landline = 0;

                    if (!TextUtils.isEmpty(landline_num.getText().toString()))
                        landline = landline_num.getText().length();
                    if ((std_code1 + landline) < totalMinLengthOfPhn || (std_code1 + landline) > totalMaxLengthOfPhn) {
                        stringBuilder.append("\n" + (++i) + ". Please fill proper Landline Number.");
                    }
                }
            }

            if (!isLocationConfirm && flgUpdateLocation == 1) {
                showAlertSingleButtonInfo("Please confirm your location");
            }

            if (flgUpdateLocation == 1) {
                if (!et_Address.getText().toString().trim().equals("")
                        && !etPinCode.getText().toString().trim().equals("") && selectedCityID != -1) {
                    if (!et_Address.getText().toString().trim().equals("")) {
                        if (et_Address.getText().toString().trim().length() > 9) {
                            address = et_Address.getText().toString();
                        } else {
                      /*  showAlertSingleButtonInfo("Address can not be less then 10 characters");
                        return;*/
                            stringBuilder.append("\n" + (++i) + ". Address can not be less then 10 characters.");
                        }
                    }


                    if (!etPinCode.getText().toString().trim().equals("")) {
                        if (etPinCode.getText().toString().trim().length() == 6) {
                            pincode = etPinCode.getText().toString();
                        } else {
                            stringBuilder.append("\n" + (++i) + ". Enter the 6 digits pincode.");

                        }
                    }

                    /*    if (!et_Village.getText().toString().trim().equals("")) {
                            if (et_Village.getText().toString().trim().length() > 2) {
                                city = et_Village.getText().toString();
                            } else {
                                stringBuilder.append("\n" + (++i) + ". City/Village can not be less then 3 characters.");

                            }
                        }
                        if (!et_state.getText().toString().trim().equals("")) {
                            state = et_state.getText().toString();
                        }*/
                } else {
                    if (et_Address.getText().toString().trim().equals("")) {
                        stringBuilder.append("\n" + (++i) + ". Fill Address.");
//                showAlertSingleButtonInfo("fill Address.");
                    }
                      /*  if (et_state.getText().toString().trim().equals("")) {
                            stringBuilder.append("\n" + (++i) + ". Fill State.");
                            //showAlertSingleButtonInfo("Please fill State.");
                        }
                        if (et_Village.getText().toString().trim().equals("")) {
                            stringBuilder.append("\n" + (++i) + ". Fill City/Village.");
                        }*/
                    if (selectedCityID == 0 || selectedCityID == -1) {
                        stringBuilder.append("\n" + (++i) + ". Please fill City.");
                    }
                    if (etPinCode.getText().toString().trim().equals("")) {
                        stringBuilder.append("\n" + (++i) + ". Fill Pin code.");
                    }
                }
            }


            if (!stringBuilder.toString().equals("")) {
                String str = "Following fields are mandatory. Please fill all these fields.\n";
                // validationString.append("\n Confirm your location");
                str = str + stringBuilder.toString();
                showAlertSingleButtonInfo(str);
                return;
            } else {


                if (flgGST == 1) {
                    if (gst_number.getText().toString().isEmpty()) {
                        showAlertSingleButtonInfo("Please enter GST Number");
                        return;
                    } else {
                        gstNumber = gst_number.getText().toString();
                    }
                } else {
                    gstNumber = "";
                }


                if (!landline_num.getText().toString().trim().equals("") || !std_code.getText().toString().trim().equals("")) {
                    if (!TextUtils.isEmpty(landline_num.getText().toString()) || !TextUtils.isEmpty(std_code.getText().toString())) {
                        int totalMinLengthOfPhn = 11;
                        int totalMaxLengthOfPhn = 15;
                        int std_code1 = 0;
                        if (!TextUtils.isEmpty(std_code.getText().toString()))
                            std_code1 = std_code.getText().length();

                        int landline = 0;

                        if (!TextUtils.isEmpty(landline_num.getText().toString()))
                            landline = landline_num.getText().length();

                        if ((std_code1 + landline) < totalMinLengthOfPhn || (std_code1 + landline) > totalMaxLengthOfPhn) {
                            stringBuilder.append("\n" + (++i) + ". Please fill proper Landline Number.");
                        } else {
                            landlinenum = std_code.getText().toString().trim() + landline_num.getText().toString().trim();
                        }
                    } else {
                        landlinenum = std_code.getText().toString().trim() + landline_num.getText().toString().trim();
                    }
                } else {
                    landlinenum = std_code.getText().toString().trim() + landline_num.getText().toString().trim();
                }

                if (!email_add.getText().toString().equals("")) {
                    if (!email_add.getText().toString().trim().matches(emailPattern)) {
                        stringBuilder.append("\n" + (++i) + ". Please fill proper email address.");
                    } else {
                        EmailAdd = email_add.getText().toString().trim();
                    }
                }

                if (!mobile_num.getText().toString().trim().equals("")) {
                    if (!isNumberValid(mobile_num.getText().toString().trim())) {
                        showAlertSingleButtonInfo("Please enter correct contact number");
                        return;
                    } else {
                        mobilenum = mobile_num.getText().toString().trim();
                        if (flgWhatsapp == 0) {
                            WhatsApp_Num = whatsappNumber.getText().toString();
                        } else {
                            WhatsApp_Num = mobilenum;
                        }
                    }
                } else
                    mobilenum = "";


                if (!owner_name.getText().toString().trim().equals(""))
                    ownername = owner_name.getText().toString().trim();
                else
                    ownername = "";

                storename = store_name.getText().toString().trim();


                if (!stringBuilder.toString().equals("")) {
                    String str = "Following fields are mandatory. Please fill all these fields.\n";
                    // validationString.append("\n Confirm your location");
                    str = str + stringBuilder.toString();
                    showAlertSingleButtonInfo(str);
                    return;
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(StoreEditActivity1.this);
                    builder.setTitle("Alert")
                            .setMessage("Are you sure you want to submit the edited details?")
                            .setCancelable(false)
                            .setIcon(R.drawable.info_ico)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();

                                    try {
                                        StoreEditActivity1.FullSyncDataNow task = new StoreEditActivity1.FullSyncDataNow(StoreEditActivity1.this);
                                        task.execute();
                                    } catch (Exception ex) {

                                    }

                                }

                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                }


            }
        }


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

    View.OnClickListener captureListener = new View.OnClickListener() {
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

    private void openCamera() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        dialog = new Dialog(StoreEditActivity1.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        //dialog.setTitle("Calculation");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_cameraview);
        WindowManager.LayoutParams parms = dialog.getWindow().getAttributes();

        parms.height = parms.MATCH_PARENT;
        parms.width = parms.MATCH_PARENT;
        cameraPreview = (LinearLayout) dialog.findViewById(R.id.camera_preview);
        switchCamera = (Button) dialog.findViewById(R.id.btSwitchCamera);
        if (mCamera != null) {
            mPreview = new CameraPreview(StoreEditActivity1.this, mCamera);
            cameraPreview.addView(mPreview);
        }
        //onResume code
        if (!hasCamera(this)) {
            Toast toast = Toast.makeText(StoreEditActivity1.this, getText(R.string.txtNoCamera), Toast.LENGTH_LONG);
            toast.show();
        }

        if (mCamera == null) {
            //if the front facing camera does not exist
            if (findFrontFacingCamera() < 0) {
                Toast.makeText(StoreEditActivity1.this, getText(R.string.txtNoFrontCamera), Toast.LENGTH_LONG).show();
                switchCamera.setVisibility(View.GONE);
            }

            //mCamera = Camera.open(findBackFacingCamera());

            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }

            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            if (mCamera == null) {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            }

            mPreview = new CameraPreview(StoreEditActivity1.this, mCamera);
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
                e.printStackTrace();

            }
            if (!isParameterSet) {
                Camera.Parameters params2 = mCamera.getParameters();
                params2.setPictureFormat(ImageFormat.JPEG);
                params2.setJpegQuality(70);
                params2.setRotation(90);

                mCamera.setParameters(params2);
            }

            setCameraDisplayOrientation(StoreEditActivity1.this, Camera.CameraInfo.CAMERA_FACING_BACK, mCamera);
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
        }

        capture = (Button) dialog.findViewById(R.id.button_capture);

        flashImage = (ImageView) dialog.findViewById(R.id.flashImage);
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

        final Button cancleCamera = (Button) dialog.findViewById(R.id.cancleCamera);
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

        capture.setOnClickListener(captureListener);

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

    private float getFingerSpacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
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

    public static Bitmap normalizeImageForUri(Context context, Uri uri) {
        Bitmap rotatedBitmap = null;

        try {

            ExifInterface exif = new ExifInterface(uri.getPath());

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
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
                    croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException e) {
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
            Crashlytics.logException(t);
            // Do nothing
        }
    }

    private File getOutputMediaFile() {
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
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        File mediaFile;
        //and make a media file:
        //	mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + AppUtils.getToken(StoreEditActivity1.this) + timeStamp + ".jpg");

        return mediaFile;
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

                    //Toast toast = Toast.makeText(getActivity(), "Picture saved: " + pictureFile.getName(), Toast.LENGTH_LONG);
                    //toast.show();
                    //put data here

                    arrImageData.add(0, pictureFile);
                    arrImageData.add(1, pictureFile.getName());
                    dialog.dismiss();
                    if (pictureFile != null) {
                        File file = pictureFile;
                        System.out.println("File +++" + pictureFile);
                        imageName = pictureFile.getName();
                        normalizeImageForUri(StoreEditActivity1.this, Uri.fromFile(pictureFile));
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
                        String clkdTime = df.format(dateobj);
                        //	String valueOfKey=imagButtonTag+"~"+tempId+"~"+file.getAbsolutePath()+"~"+clkdTime+"~"+"2";
                        String valueOfKey = clickedTagPhoto + "~" + StoreID + "~" + uriSavedImage.toString() + "~" + clkdTime + "~" + "1";
                        //   helperDb.insertImageInfo(tempId,imagButtonTag, imageName, file.getAbsolutePath(), 2);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                                byteArray.length);

                        //
                        setSavedImageToScrollView(bitmap, imageName, valueOfKey, clickedTagPhoto);
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

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
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

    public void setSavedImageToScrollView(Bitmap bitmap, String imageValidName, String valueOfKey, final String tagOfClkdPic) {
        //clickedTagPhoto

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View viewStoreLocDetail = inflater.inflate(R.layout.store_loc_display, null);


        final RelativeLayout rl_photo = (RelativeLayout) viewStoreLocDetail.findViewById(R.id.rl_photo);
        final ImageView img_thumbnail = (ImageView) viewStoreLocDetail.findViewById(R.id.img_thumbnail);
        img_thumbnail.setImageBitmap(bitmap);
        img_thumbnail.setTag(imageValidName);
        if (hmapisPhotolicked != null) {
            if (hmapisPhotolicked.containsKey(tagOfClkdPic)) {
                int count = hmapisPhotolicked.get(tagOfClkdPic);
                count = count + 1;
                hmapisPhotolicked.put(tagOfClkdPic, count);
            } else {
                hmapisPhotolicked.put(tagOfClkdPic, 1);
            }
        } else {
            hmapisPhotolicked.put(tagOfClkdPic, 1);
        }

        hmapImageClkdTempIdData.put(imageValidName, valueOfKey);
        final ImageView imgCncl = (ImageView) viewStoreLocDetail.findViewById(R.id.imgCncl);

        img_thumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    String filePathName = "";
                    if (hmapImageClkdTempIdData.get(view.getTag().toString()).split(Pattern.quote("~"))[2].contains("file:")) {
                        filePathName = hmapImageClkdTempIdData.get(view.getTag().toString()).split(Pattern.quote("~"))[2].replace("file:", "");
                    } else {
                        filePathName = hmapImageClkdTempIdData.get(view.getTag().toString()).split(Pattern.quote("~"))[2];

                    }
                    File file = new File(filePathName);
                    Uri intentUri = FileProvider.getUriForFile(getBaseContext(), getApplicationContext().getPackageName() + ".provider", file);
                    intent.setDataAndType(intentUri, "image/*");
                    startActivity(intent);

                } else {
                    Uri intentUri = Uri.parse(hmapImageClkdTempIdData.get(view.getTag().toString()).split(Pattern.quote("~"))[2]);

                    intent.setDataAndType(intentUri, "image/*");
                    startActivity(intent);
                }


            }
        });

        imgCncl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hmapImageClkdTempIdData.remove(img_thumbnail.getTag());
                // dbegine.deleteImageData(img_thumbnail.getTag().toString(),storeId);
                if (hmapisPhotolicked != null) {
                    if (hmapisPhotolicked.containsKey(tagOfClkdPic)) {
                        int count = hmapisPhotolicked.get(tagOfClkdPic);
                        count = count - 1;
                        hmapisPhotolicked.put(tagOfClkdPic, count);
                    }

                }
                mDataSource.deleteImageData(img_thumbnail.getTag().toString(), StoreID);
                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + img_thumbnail.getTag().toString();
                File fdelete = new File(file_dj_path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {

                        callBroadCast();
                    } else {

                    }
                }
                ViewGroup parent = (ViewGroup) rl_photo.getParent();
                parent.removeView(rl_photo);
            }
        });


        LinearLayout ll_ImageToSet = (LinearLayout) findViewById(R.id.ll_data);
        if (ll_ImageToSet != null) {
            ll_ImageToSet.addView(viewStoreLocDetail);
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
            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {
        LocationUpdateAsync locationUpdateAsync = new LocationUpdateAsync(fnLati, fnLongi, finalAccuracy, fnAccurateProvider, GpsLat, GpsLong, GpsAccuracy, NetwLat, NetwLong, NetwAccuracy, FusedLat, FusedLong, FusedAccuracy, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt, FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt, flgLocationServicesOnOff, flgGPSOnOff, flgNetworkOnOff, flgFusedOnOff,
                flgInternetOnOffWhileLocationTracking, address, pincode, city, state, fnAddress);
        AppUtils.executeAsyncTask(locationUpdateAsync);
    }

    private FragmentManager manager;
    private boolean isLocationSelected = false;

    private class LocationUpdateAsync extends AsyncTask<String, String, String> {

        String fnLati;
        String fnLongi;
        String finalAccuracy;
        String fnAccurateProvider;
        String GpsLat;
        String GpsLong;
        String GpsAccuracy;
        String NetwLat;
        String NetwLong;
        String NetwAccuracy;
        String FusedLat;
        String FusedLong;
        String FusedAccuracy;
        String AllProvidersLocation;
        String GpsAddress;
        String NetwAddress;
        String FusedAddress;
        String FusedLocationLatitudeWithFirstAttempt;
        String FusedLocationLongitudeWithFirstAttempt;
        String FusedLocationAccuracyWithFirstAttempt;
        int flgLocationServicesOnOff;
        int flgGPSOnOff;
        int flgNetworkOnOff;
        int flgFusedOnOff;
        int flgInternetOnOffWhileLocationTracking;
        String address;
        String pincode;
        String city;
        String state;
        String fnAddress;

        public LocationUpdateAsync(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff,
                                   int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {

            this.fnLati = fnLati;
            this.fnLongi = fnLongi;
            this.finalAccuracy = finalAccuracy;
            this.fnAccurateProvider = fnAccurateProvider;
            this.GpsLat = GpsLat;
            this.GpsLong = GpsLong;
            this.GpsAccuracy = GpsAccuracy;
            this.NetwLat = NetwLat;
            this.NetwLong = NetwLong;
            this.NetwAccuracy = NetwAccuracy;
            this.FusedLat = FusedLat;
            this.FusedLong = FusedLong;
            this.FusedAccuracy = FusedAccuracy;
            this.AllProvidersLocation = AllProvidersLocation;
            this.GpsAddress = GpsAddress;
            this.NetwAddress = NetwAddress;
            this.FusedAddress = FusedAddress;
            this.FusedLocationLatitudeWithFirstAttempt = FusedLocationLatitudeWithFirstAttempt;
            this.FusedLocationLongitudeWithFirstAttempt = FusedLocationLongitudeWithFirstAttempt;
            this.FusedLocationAccuracyWithFirstAttempt = FusedLocationAccuracyWithFirstAttempt;
            this.flgLocationServicesOnOff = flgLocationServicesOnOff;
            this.flgGPSOnOff = flgGPSOnOff;
            this.flgNetworkOnOff = flgNetworkOnOff;
            this.flgFusedOnOff = flgFusedOnOff;
            this.flgInternetOnOffWhileLocationTracking = flgInternetOnOffWhileLocationTracking;
            this.address = address.trim();
            this.pincode = pincode;
            this.city = city;
            this.state = state;
            this.fnAddress = address.trim();


            mDataSource.saveLatLngToTxtFile(StoreID, "NA", String.valueOf(fnLati), String.valueOf(fnLongi), "" + finalAccuracy, fnAccurateProvider, GpsLat, GpsLong, GpsAccuracy, NetwLat, NetwLong, NetwAccuracy, FusedLat, FusedLong, FusedAccuracy, 3, "0", fnAddress, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt
                    , FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt);


        }

        @Override
        protected String doInBackground(String... strings) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (StoreEditActivity1.this.isFinishing() || StoreEditActivity1.this.isDestroyed())
                    return "";
            }

            if (!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy))) {
                LattitudeFromLauncher = String.valueOf(fnLati);
                LongitudeFromLauncher = String.valueOf(fnLongi);
                AccuracyFromLauncher = String.valueOf(finalAccuracy);

                StoreEditActivity1.this.FnlAddress = address;
                StoreEditActivity1.this.finalPinCode = pincode;


            } else {
                countSubmitClicked++;
                if (countSubmitClicked <= 2 && isLocationSelected && !isLocationConfirm) {
                    LattitudeFromLauncher = String.valueOf(fnLati);
                    LongitudeFromLauncher = String.valueOf(fnLongi);
                    AccuracyFromLauncher = String.valueOf(finalAccuracy);

                    StoreEditActivity1.this.FnlAddress = address;
                    StoreEditActivity1.this.finalPinCode = pincode;

                } else {


                    LattitudeFromLauncher = String.valueOf(fnLati);
                    LongitudeFromLauncher = String.valueOf(fnLongi);
                    AccuracyFromLauncher = String.valueOf(finalAccuracy);

                    StoreEditActivity1.this.FnlAddress = address;
                    StoreEditActivity1.this.finalPinCode = pincode;

                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (StoreEditActivity1.this.isFinishing() || StoreEditActivity1.this.isDestroyed())
                    return;
            }

            if (!isOnline()) {
                no_internet_msg.setVisibility(View.VISIBLE);
            } else {
                no_internet_msg.setVisibility(View.GONE);
            }

            if (!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy))) {

                //fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy));
                manager = getFragmentManager();
                mapFrag = (MapFragment) manager.findFragmentById(R.id.map);
                if (mapFrag != null && mapFrag.getView() != null)
                    mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(StoreEditActivity1.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);
                //dbengine.open();

                if (finalPinCode != null && !finalPinCode.equals("NA")) {
                    etPinCode.setText(pincode);
                }
                if (address != null && !address.equals("NA")) {
                    et_Address.setText(address);
                }


            } else {
                countSubmitClicked++;
                if (countSubmitClicked <= 2 && isLocationSelected && !isLocationConfirm) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(StoreEditActivity1.this);

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
                    alertDialog.show();


                } else {

                    manager = getFragmentManager();
                    mapFrag = (MapFragment) manager.findFragmentById(R.id.map);
                    if (mapFrag.getView() != null)
                        mapFrag.getView().setVisibility(View.VISIBLE);
                    mapFrag.getMapAsync(StoreEditActivity1.this);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.show(mapFrag);
                    //dbengine.close();

                    if (pincode != null && !pincode.equals("NA")) {
                        etPinCode.setText(pincode);
                    }

                    if (address != null && !address.equals("NA")) {
                        et_Address.setText(address);
                    }


                }


            }
        }


    }

    public boolean checkLastFinalLoctionIsRepeated(String currentLat, String currentLong, String currentAccuracy) {
        boolean repeatedLoction = false;

        try {

            String chekLastGPSLat = "0";
            String chekLastGPSLong = "0";
            String chekLastGpsAccuracy = "0";
            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), "HellFinalLatLong");
            if (!jsonTxtFolder.exists()) {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew = "FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder, txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory() + "/" + "HellFinalLatLong" + "/" + txtFileNamenew;

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

    /* private class bgTasker extends AsyncTask<Integer, Void, Integer> {


         @Override
         protected Integer doInBackground(Integer... params) {

             try {
                 SyncNow();
             } catch (Exception e) {
                 String esdsd = e.getMessage();
             } finally {
             }
             return params[0];
         }

         @Override
         protected void onPreExecute() {
             super.onPreExecute();

             pDialog2 = ProgressDialog.show(AddRetailerActivity.this, getText(R.string.PleaseWaitMsg), getText(R.string.genTermProcessingRequest), true);
             pDialog2.setIndeterminate(true);
             pDialog2.setCancelable(false);
             pDialog2.show();

         }

         @Override
         protected void onCancelled() {
             Log.i("bgTasker", "bgTasker Execution Cancelled");
         }

         @Override
         protected void onPostExecute(Integer result) {
             super.onPostExecute(result);

             Log.i("bgTasker", "bgTasker Execution cycle completed");
             if (pDialog2 != null)
                 pDialog2.dismiss();
             //Move forward
             showAlertSingleButtonInfoSubmissionSuceesful("Data Submitted successfully.", result);
         }
     }*/
    private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialogGetStores;

        public FullSyncDataNow(StoreEditActivity1 activity) {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));

            pDialogGetStores.setMessage("Submitting Store Updates Details");

            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();

            //curntCol

        }

        @Override

        protected Void doInBackground(Void... params) {


            try {


                if (flgUpdateLocation == 0) {
                    mDataSource.updateStoreEditData(StoreID, storename, ownername, mobilenum, tblStoreListMasters.get(0).getStoreAddress(), tblStoreListMasters.get(0).getCityID(),
                            tblStoreListMasters.get(0).getStoreState(), tblStoreListMasters.get(0).getStorePinCode(), gst_number.getText().toString(), landlinenum,
                            WhatsApp_Num, "" + tblStoreListMasters.get(0).getStoreLatitude(), "" + tblStoreListMasters.get(0).getStoreLatitude(), EmailAdd, flgUpdateLocation, selectedBeatID, flgGST);
                } else {
                    if (LattitudeFromLauncher.equals("NA")) {
                        mDataSource.updateStoreEditData(StoreID, storename, ownername, mobilenum, address, selectedCityID, StateNAME, pincode,
                                gst_number.getText().toString(), landlinenum, WhatsApp_Num, "" + tblStoreListMasters.get(0).getStoreLatitude(),
                                "" + tblStoreListMasters.get(0).getStoreLatitude(), EmailAdd, flgUpdateLocation, selectedBeatID, flgGST);
                    } else {
                        mDataSource.updateStoreEditData(StoreID, storename, ownername, mobilenum, address, selectedCityID, StateNAME, pincode,
                                gst_number.getText().toString(), landlinenum, WhatsApp_Num, LattitudeFromLauncher, LongitudeFromLauncher, EmailAdd, flgUpdateLocation, selectedBeatID, flgGST);
                    }
                }
                if (hmapImageClkdTempIdData != null && hmapImageClkdTempIdData.size() > 0) {
                    for (Map.Entry<String, String> entry : hmapImageClkdTempIdData.entrySet()) {
                        String clickTime = entry.getValue().split(Pattern.quote("~"))[3];
                        String ImageName = entry.getKey();
                        String ImagePath = entry.getValue().split(Pattern.quote("~"))[2];
                        mDataSource.UpdateImagesAgainstStore(StoreID, clickTime, ImageName, ImagePath, 3, 11, 2, "NA");
                    }
                }
           /*    Intent newIntent = new Intent(StoreEditActivity1.this, StoreSelection.class);
               startActivity(newIntent);*/
                //AppUtils.executeAsyncTask(new bgTasker());


            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println(e);
            }

            Intent mMyServiceIntent = new Intent(StoreEditActivity1.this, SyncJobService.class);  //is any of that needed?  idk.
            String presentRoute = mDataSource.GetActiveRouteID();
            mMyServiceIntent.putExtra("whereTo", "Regular");//
            mMyServiceIntent.putExtra("routeID", presentRoute);//
            mMyServiceIntent.putExtra("storeID", StoreID);
            SyncJobService.enqueueWork(StoreEditActivity1.this, mMyServiceIntent);

            return null;
        }

        @Override
        protected void onCancelled() {

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialogGetStores.isShowing()) {
                pDialogGetStores.dismiss();
            }


            navigateScreen();

        }
    }


    public void navigateScreen() {
        Intent newIntent = new Intent(StoreEditActivity1.this, StoreSelection.class);
        startActivity(newIntent);
    }


}
