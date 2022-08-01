package com.astix.rajtraderssfasales;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allana.truetime.TimeUtils;
import com.astix.rajtraderssfasales.location.LocationInterface;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobal;
import com.astix.rajtraderssfasales.model.TblAllValidationStoreContactData;
import com.astix.rajtraderssfasales.model.TblStoreContactNum;
import com.astix.rajtraderssfasales.model.TblStoreListForUpdateMstr;
import com.astix.rajtraderssfasales.model.TblStoreValidationContact;
import com.astix.rajtraderssfasales.rest.ApiClient;
import com.astix.rajtraderssfasales.rest.ApiInterface;
import com.astix.rajtraderssfasales.sync.SyncJobService;
import com.astix.rajtraderssfasales.utils.AppUtils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreEditActivityContactLocation extends BaseActivity implements LocationInterface, View.OnTouchListener {
    public static int flgCallActivityContactLocation = 0;
    public String userDate;
    public String pickerDate;
    public String fullFileName1;
    public InterfaceContactUpdate interfaceContactUpdate;
    Random random;
    String generatedOTPForRetailer = "";
    Dialog dialogRetailerOTP;
    private ImageView exit;
    public int flgReSendOTP = 0;
    public TblStoreListForUpdateMstr tblStoreListMaster;
    String StoreID = "";

    String LattitudeFromLauncher = "NA";
    String LongitudeFromLauncher = "NA";
    public String AccuracyFromLauncher = "NA";

    LocationRetreivingGlobal locationRetreivingGlobal;
    LocationManager locationManager;


    float mDist = 0;

    LinearLayout ll_data;

    String otp = "";


    String generatedOTP = "";


    @BindView(R.id.retailer_ownerName_txt)
    TextView retailer_ownerName_txt;

    @BindView(R.id.reatilerStoreName_txt)
    TextView reatilerStoreName_txt;

    @BindView(R.id.retailer_contact_txt)
    TextView retailer_contact_txt;

    @BindView(R.id.retailer_wrong_contact_reason_txt)
    TextView retailer_wrong_contact_reason_txt;

    @BindView(R.id.tv_lnkToUpdateContact)
    TextView tv_lnkToUpdateContact;

    @BindView(R.id.ll_container_toEdit_Contact)
    LinearLayout ll_container_toEdit_Contact;


    @BindView(R.id.ll_newMobileContainer)
    LinearLayout ll_newMobileContainer;


    @BindView(R.id.mobile_num_et)
    EditText mobile_num_et;

    @BindView(R.id.lnk_for_mobile_otp)
    TextView lnk_for_mobile_otp;
    Context ctx;

    int flgbck = 0;

   /* @BindView(R.id.ll_optContainer)
    LinearLayout ll_optContainer;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_edit_contact_location);
        ButterKnife.bind(this);
        ctx = this;
        // interfaceContactUpdate=new InterfaceContactUpdate() ;

        //  interfaceContactUpdate = (InterfaceContactUpdate)ctx;
        //  interfaceContactUpdate=(InterfaceContactUpdate)getBaseContext();
        exit = (ImageView) findViewById(R.id.img_exit);

        ll_data = (LinearLayout) findViewById(R.id.ll_data);

        StoreID = getIntent().getExtras().getString("storeID");
        if (getIntent().getExtras().containsKey("bck")) {
            flgCallActivityContactLocation = 0;
        }

        pickerDate = getDateInMonthTextFormat();
        userDate = getDateInMonthTextFormat();

        getDataFromDb();


        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);


       /* SpannableString spanString = new SpannableString(tv_lnkToUpdateContact.getText());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(StoreEditActivityContactLocation.this, R.color.colorPrimary)), 0,  spanString.length(), 0);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        *//*spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);*//*
        spanString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, spanString.length(), 0);
        tv_lnkToUpdateContact.setText(spanString);

        tv_lnkToUpdateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_newMobileContainer.setVisibility(View.VISIBLE);
                lnk_for_mobile_otp.setVisibility(View.VISIBLE);

            }
        });
*/

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOnbackButtonClick("Alert", "Your changes will not be saved.Are you sure you want to exit?");
            }
        });


        mobile_num_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!mobile_num_et.getText().toString().isEmpty()) {
                        if (!isNumberValid(mobile_num_et.getText().toString().trim())) {
                            lnk_for_mobile_otp.setVisibility(View.GONE);
                            showAlertSingleButtonInfoForValidation("Please enter 10 digits contact number and number cannot be start with 0, 1, 2, 3 or 4", mobile_num_et);
                            return;
                        } else {
                            lnk_for_mobile_otp.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });


        lnk_for_mobile_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                random = new Random();
                generatedOTPForRetailer = String.format("%04d", random.nextInt(10000));
                Log.i("OTP", generatedOTPForRetailer);


                if (!mobile_num_et.getText().toString().trim().isEmpty()) {
                    if (!isNumberValid(mobile_num_et.getText().toString().trim())) {
                        lnk_for_mobile_otp.setVisibility(View.GONE);
                        showAlertSingleButtonInfoForValidation("Please enter 10 digits contact number and number cannot be start with 0, 1, 2, 3 or 4", mobile_num_et);
                        return;
                    } else {
                        lnk_for_mobile_otp.setEnabled(false);
                        lnk_for_mobile_otp.setBackgroundResource(R.drawable.edittext_with_gray_background);
                        if (generatedOTPForRetailer != null) {
                            mobile_num_et.setEnabled(false);
                            mobile_num_et.setBackgroundResource(R.drawable.edittext_with_gray_background);
                            //   ll_optContainer.setVisibility(View.VISIBLE);
                            sendConfirmationToServer();
                            // openPopUpForRetailerOTPSending();
                   /* startTimer();
                    flgReSendOTP = 0;
                    sendOtp();*/
                        }
                    }

                } else if (mobile_num_et.getText().toString().trim().isEmpty()) {
                    showAlertSingleButtonInfoForValidation("Please provide contact number", mobile_num_et);
                    return;
                }


            }
        });


        ll_newMobileContainer.setVisibility(View.VISIBLE);
        lnk_for_mobile_otp.setVisibility(View.VISIBLE);
    }

    void verifyBtn(EditText e1, EditText e2, EditText e3, EditText e4) {
        generatedOTP = "";
        generatedOTP = generatedOTP + e1.getText().toString().trim() + e2.getText().toString().trim() + e3.getText().toString().trim() + e4.getText().toString().trim();
        if (generatedOTP.length() == 4) {
            verifyOtp();
        } else {
            showAlertSingleButtonError("Enter correct code");
        }
    }

    public void verifyOtp() {
        sendConfirmationToServer();
    }

    private void getDataFromDb() {
        tblStoreListMaster = mDataSource.fnGetStoretblStoreListForUpdateMstr(StoreID);
        if (tblStoreListMaster != null) {
            reatilerStoreName_txt.setText(tblStoreListMaster.getStoreName().toString());
            retailer_ownerName_txt.setText(tblStoreListMaster.getOwnername().toString());
            retailer_contact_txt.setText(tblStoreListMaster.getContactnumber().toString());
            retailer_wrong_contact_reason_txt.setText(tblStoreListMaster.getReasonforupdate().toString());
            if (tblStoreListMaster.getContactnumber() != null && !tblStoreListMaster.getUpdatedContactnumber().equals("NA"))
                mobile_num_et.setText(tblStoreListMaster.getUpdatedContactnumber().toString());

        }
       /* LattitudeFromLauncher=""+tblStoreListMaster.getStoreLatitude();
        LongitudeFromLauncher=""+tblStoreListMaster.getStoreLongitude();*/

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
                        lnk_for_mobile_otp.setVisibility(View.VISIBLE);
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
              /*  Intent intent = new Intent(StoreEditActivityContactLocation.this, StoreSelection.class);
                   startActivity(intent);
                    finish();*/
                fullFileName1 = TimeUtils.getNetworkDateTime(StoreEditActivityContactLocation.this, TimeUtils.DATE_TIME_FORMAT);

              /*  Intent ready4GetLoc = new Intent(StoreEditActivityContactLocation.this, LastVisitDetails.class);

                ready4GetLoc.putExtra("storeID", tblStoreListMaster.getStoreID());
                ready4GetLoc.putExtra("selStoreName", tblStoreListMaster.getStoreName());
                ready4GetLoc.putExtra("token", AppUtils.getToken(StoreEditActivityContactLocation.this));
                ready4GetLoc.putExtra("userDate", userDate);
                ready4GetLoc.putExtra("pickerDate", pickerDate);
                ready4GetLoc.putExtra("startTS", fullFileName1);
                ready4GetLoc.putExtra("bck", 0);
                startActivity(ready4GetLoc);*/
                //   CommonClassForLocationUpdateInteface.getClassForLocationUpdateInteface(StoreEditActivityContactLocation.this);
                if (getIntent().getExtras().containsKey("bck")) {
                    flgCallActivityContactLocation = 0;
                } else {
                    flgCallActivityContactLocation = 1;
                }

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


    public void saveData() {

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
                if (StoreEditActivityContactLocation.this.isFinishing() || StoreEditActivityContactLocation.this.isDestroyed())
                    return "";
            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (StoreEditActivityContactLocation.this.isFinishing() || StoreEditActivityContactLocation.this.isDestroyed())
                    return;
            }


        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;

        }
        return super.onKeyDown(keyCode, event);
    }


    void startTimer(TextView resend_otp_tv, TextView resend_tv, TextView countdown_tv) {
        resend_otp_tv.setTextColor(Color.parseColor("#EDEDED"));
        resend_otp_tv.setEnabled(false);
        resend_tv.setVisibility(View.VISIBLE);
        countdown_tv.setVisibility(View.VISIBLE);

        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;

                countdown_tv.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
            }

            @Override
            public void onFinish() {

                resend_otp_tv.setTextColor(Color.parseColor("#000000"));
                resend_otp_tv.setEnabled(true);
                resend_tv.setVisibility(View.GONE);
                countdown_tv.setVisibility(View.GONE);
            }

        }.start();

    }


    private void sendOtp() {
        if (isOnline()) {

            final ProgressDialog progressHUD = new ProgressDialog(this);
            progressHUD.setTitle("Requesting Code for Retailer...");
            progressHUD.setIndeterminate(false);
            progressHUD.setCancelable(false);
            progressHUD.show();
            TblStoreContactNum data = new TblStoreContactNum();
            data.setContactNo(mobile_num_et.getText().toString());
            //data.setContactNo("9819946569");
            data.setOTP(generatedOTPForRetailer + " is the code for verification of your contact number. Please do share it with Raj Super White representative. -Astix");
            data.setStoreID(StoreID);
            data.setFlgStep(1);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<TblAllValidationStoreContactData> call = apiService.userValidateStoreContact(data);
            call.enqueue(new Callback<TblAllValidationStoreContactData>() {
                @Override
                public void onResponse(Call<TblAllValidationStoreContactData> call, Response<TblAllValidationStoreContactData> response) {
                    if (response.code() == 200) {
                        progressHUD.dismiss();

                        if (flgReSendOTP == 1 || flgReSendOTP == 0) {
                            TblAllValidationStoreContactData tblAllValidationStoreContactData = response.body();
                            List<TblStoreValidationContact> tblStoreValidationContacts = tblAllValidationStoreContactData.getTblStoreValidationContact();

                            if (flgReSendOTP == 0 && tblStoreValidationContacts.get(0).getFlgValidated() == 3) {
                                if(dialogRetailerOTP!=null && dialogRetailerOTP.isShowing())
                                    dialogRetailerOTP.dismiss();
                                lnk_for_mobile_otp.setEnabled(true);
                                mobile_num_et.setEnabled(true);
                                lnk_for_mobile_otp.setBackgroundResource(R.drawable.button_rounded_layout);
                                mobile_num_et.setBackgroundResource(R.drawable.edittext_rounded_black);
                                showAlertSingleButtonInfo("Contact number already mapped to another Retailer.");
                            } else if (flgReSendOTP == 1) {
                                if (tblStoreValidationContacts.get(0).getFlgValidated() == 1) {
                                    showAlertSingleButtonInfo("Successfully Requested for sending Code to Retailer.");
                                } else {
                                    showAlertSingleButtonError("Not able to send Code to Retailer. Please try again or Contact Administrator");
                                }
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<TblAllValidationStoreContactData> call, Throwable t) {
                    t.printStackTrace();
                    progressHUD.dismiss();
                    showAlertSingleButtonError("Some thing went wrong.\nPlease try again or Contact Administrator");
                }
            });
            //TblUserValidation tblUserValidation = apiService.callUserValidation(this, mobileNum, otp, 2);

        } else {
            showAlertSingleButtonError("No internet connection found.Please turn on your internet to proceed further");

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // openCustomKeyBoard(v);
        return true;
    }


    private void sendConfirmationToServer() {
        if (isOnline()) {

            final ProgressDialog progressHUD = new ProgressDialog(this);
            progressHUD.setTitle("Validating Retailer...");
            progressHUD.setIndeterminate(false);
            progressHUD.setCancelable(false);
            progressHUD.show();
            TblStoreContactNum data = new TblStoreContactNum();
            data.setContactNo(mobile_num_et.getText().toString());
            data.setOTP(generatedOTP);
            data.setStoreID(StoreID);
            data.setFlgStep(2);

            mDataSource.fnUpdatetblStoreListForUpdateMstrFlgMappedUpdatedContactnumber(StoreID, mobile_num_et.getText().toString());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<TblAllValidationStoreContactData> call = apiService.userValidateStoreContact(data);
            call.enqueue(new Callback<TblAllValidationStoreContactData>() {
                @Override
                public void onResponse(Call<TblAllValidationStoreContactData> call, Response<TblAllValidationStoreContactData> response) {
                    if (response.code() == 200) {
                        progressHUD.dismiss();
                        TblAllValidationStoreContactData tblAllValidationStoreContactData = response.body();
                        List<TblStoreValidationContact> tblStoreValidationContacts = tblAllValidationStoreContactData.getTblStoreValidationContact();
                        if (tblStoreValidationContacts.get(0).getFlgValidated() == 3) {

                            if(dialogRetailerOTP!=null && dialogRetailerOTP.isShowing())
                                dialogRetailerOTP.dismiss();

                            lnk_for_mobile_otp.setEnabled(true);
                            mobile_num_et.setEnabled(true);
                            lnk_for_mobile_otp.setBackgroundResource(R.drawable.button_rounded_layout);
                            mobile_num_et.setBackgroundResource(R.drawable.edittext_rounded_black);
                            showAlertSingleButtonInfo("Contact number already mapped to another Retailer.");
                        } else if (tblStoreValidationContacts.get(0).getFlgValidated() == 2) {

                            FullSyncDataNow task = new FullSyncDataNow(StoreEditActivityContactLocation.this);
                            task.execute();

                        } else {
                            showAlertSingleButtonError("Invalid Retailer Number! \nPlease Try Again");
                        }
                    }
                }

                @Override
                public void onFailure(Call<TblAllValidationStoreContactData> call, Throwable t) {
                    t.printStackTrace();
                    progressHUD.dismiss();
                    showAlertSingleButtonError("Some thing went wrong.\nPlease try again or Contact Administrator");
                }
            });
            //TblUserValidation tblUserValidation = apiService.callUserValidation(this, mobileNum, otp, 2);

        } else {
            showAlertSingleButtonError("No internet connection found.Please turn on your internet to proceed further");

        }
    }


    public void showAlertSuccessfulyUpdated(String msg) {

        if (dialogRetailerOTP != null && dialogRetailerOTP.isShowing()) {
            dialogRetailerOTP.dismiss();
            lnk_for_mobile_otp.setEnabled(true);
            mobile_num_et.setEnabled(true);
            lnk_for_mobile_otp.setBackgroundResource(R.drawable.button_rounded_layout);
            mobile_num_et.setBackgroundResource(R.drawable.edittext_rounded_black);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.genTermInformation))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_icon)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();




                      /*  Intent intent = new Intent(StoreEditActivityContactLocation.this, StoreSelection.class);
                        startActivity(intent);
                        finish();*/


                        fullFileName1 = TimeUtils.getNetworkDateTime(StoreEditActivityContactLocation.this, TimeUtils.DATE_TIME_FORMAT);

                   /*     Intent ready4GetLoc = new Intent(StoreEditActivityContactLocation.this, LastVisitDetails.class);

                        ready4GetLoc.putExtra("storeID", tblStoreListMaster.getStoreID());
                        ready4GetLoc.putExtra("selStoreName", tblStoreListMaster.getStoreName());
                        ready4GetLoc.putExtra("token", AppUtils.getToken(StoreEditActivityContactLocation.this));
                        ready4GetLoc.putExtra("userDate", userDate);
                        ready4GetLoc.putExtra("pickerDate", pickerDate);
                        ready4GetLoc.putExtra("startTS", fullFileName1);
                        ready4GetLoc.putExtra("bck", 0);
                        startActivity(ready4GetLoc);*/
                        // CommonClassForLocationUpdateInteface.getClassForLocationUpdateInteface(StoreEditActivityContactLocation.this);
                        if (getIntent().getExtras().containsKey("bck")) {
                            flgCallActivityContactLocation = 0;
                        } else {
                            flgCallActivityContactLocation = 1;
                        }
                        finish();

                    }
                }).create().show();
    }


    private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialogGetStores;

        public FullSyncDataNow(Context activity) {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));

            pDialogGetStores.setMessage("Submitting Details...");


            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();

            //curntCol

        }

        @Override

        protected Void doInBackground(Void... params) {

            int
                    Outstat = 3;
            mDataSource.fnUpdatetblStoreListForUpdateMstrFlgMappedOTPContactNumber(StoreID, mobile_num_et.getText().toString().trim(), generatedOTP);
            long syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String StampEndsTime = TimeUtils.getNetworkDateTime(StoreEditActivityContactLocation.this, TimeUtils.DATE_TIME_FORMAT);

            mDataSource.UpdateStoreFlag(StoreID.trim(), 3);

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
            try {

                DayStartActivity.flgDaySartWorking = 0;
                //String presentRoute = mDataSource.GetActiveRouteID();
                Intent mMyServiceIntent = new Intent(StoreEditActivityContactLocation.this, SyncJobService.class);
                mMyServiceIntent.putExtra("storeID", StoreID);
                mMyServiceIntent.putExtra("whereTo", "Regular");//
                mMyServiceIntent.putExtra("StoreVisitCode", "NA");//
                SyncJobService.enqueueWork(StoreEditActivityContactLocation.this, mMyServiceIntent);
                showAlertSuccessfulyUpdated("Contact Number Successfully Updated.");

            } catch (Exception e) {

                e.printStackTrace();
            }

        }
    }


    public void openPopUpForRetailerOTPSending() {

        dialogRetailerOTP = new Dialog(StoreEditActivityContactLocation.this, R.style.AlertDialogDayEndTheme);

        dialogRetailerOTP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogRetailerOTP.setContentView(R.layout.retailer_otp_section);
        dialogRetailerOTP.setTitle("");
        LinearLayout ll1 = dialogRetailerOTP.findViewById(R.id.ll_optContainer);

        ImageView imgCncl = ll1.findViewById(R.id.imgCncl);

        TextView resend_tv = ll1.findViewById(R.id.resend_tv);


        TextView countdown_tv = ll1.findViewById(R.id.countdown_tv);


        EditText et_view_one = ll1.findViewById(R.id.et_one);

        EditText et_view_two = ll1.findViewById(R.id.et_two);

        EditText et_view_three = ll1.findViewById(R.id.et_three);

        EditText et_view_four = ll1.findViewById(R.id.et_four);


        Button verify_btn = ll1.findViewById(R.id.verify_btn);

        TextView resend_otp_tv = ll1.findViewById(R.id.resend_otp_tv);
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyBtn(et_view_one, et_view_two, et_view_three, et_view_four);
            }
        });

        imgCncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialogRetailerOTP!=null && dialogRetailerOTP.isShowing())
                    dialogRetailerOTP.dismiss();
                lnk_for_mobile_otp.setEnabled(true);
                mobile_num_et.setEnabled(true);
                lnk_for_mobile_otp.setBackgroundResource(R.drawable.button_rounded_layout);
                mobile_num_et.setBackgroundResource(R.drawable.edittext_rounded_black);
            }
        });
        et_view_one.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (et_view_one.getText().toString().length() == 1)     //size as per your requirement
                {
                    et_view_two.requestFocus();
                }
            }

        });

        et_view_two.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (et_view_two.getText().toString().length() == 1)     //size as per your requirement
                {
                    et_view_three.requestFocus();
                }
            }

        });

        et_view_three.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                if (et_view_three.getText().toString().length() == 1)     //size as per your requirement
                {
                    et_view_four.requestFocus();
                }
            }

        });

        et_view_four.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (et_view_four.getText().toString().length() == 1)     //size as per your requirement
                {
                    // verify_btn.performClick();
                }
            }

        });

        resend_otp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random = new Random();
                generatedOTPForRetailer = String.format("%04d", random.nextInt(10000));
                Log.i("OTP", generatedOTPForRetailer);

                if (generatedOTPForRetailer != null) {
                    flgReSendOTP = 1;
                    startTimer(resend_otp_tv, resend_tv, countdown_tv);
                    sendOtp();
                }
            }
        });
        startTimer(resend_otp_tv, resend_tv, countdown_tv);
        flgReSendOTP = 0;
        sendOtp();
        dialogRetailerOTP.setCanceledOnTouchOutside(false);


        dialogRetailerOTP.show();
    }
}
