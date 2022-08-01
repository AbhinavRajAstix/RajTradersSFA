package com.astix.rajtraderssfasales.SignUpModule;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.BaseActivity;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.SplashScreen;
import com.astix.rajtraderssfasales.rest.ApiClient;
import com.astix.rajtraderssfasales.rest.ApiInterface;


import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends BaseActivity implements View.OnTouchListener{
    //  private CustomKeyboard mCustomKeyboardNum;
    String otp = "";
    String generatedOTP = "";
    String mobileNum = "";
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;
    String validation_code = "";
    TextView resend_otp_tv;
    TextView countdown_tv, resend_tv;
    Button verify_btn;

    Random random;


   /* @BindView(R.id.et_one)
    EditText et_view_one;

    @BindView(R.id.et_two)
    EditText et_view_two ;

    @BindView(R.id.et_three)
    EditText et_view_three;

    @BindView(R.id.et_four)
    EditText et_view_four ;*/


    EditText et_view_one,et_view_two,et_view_three,et_view_four;



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            if(intent.getAction().equalsIgnoreCase("otp"))
            {
                String message = intent.getStringExtra("message");

                Pattern p = Pattern.compile("(\\d{4})");
                Matcher pm = p.matcher(message);


                if(pm.find())
                {
                    generatedOTP = pm.group(0).toString().trim();

                    String et_one, et_two, et_three, et_four;
                    if(otp.split(Pattern.quote(""))[0].equals(""))
                    {
                        et_one = otp.split(Pattern.quote(""))[1];
                        et_two = otp.split(Pattern.quote(""))[2];
                        et_three = otp.split(Pattern.quote(""))[3];
                        et_four = otp.split(Pattern.quote(""))[4];
                    }
                    else {
                        et_one = otp.split(Pattern.quote(""))[0];
                        et_two = otp.split(Pattern.quote(""))[1];
                        et_three = otp.split(Pattern.quote(""))[2];
                        et_four = otp.split(Pattern.quote(""))[3];
                    }





                    et_view_one.setText(et_one);
                    et_view_two.setText(et_two);
                    et_view_three.setText(et_three);
                    et_view_four.setText(et_four);


                    Log.e("SMSotp", pm.group(0).toString());

                    if(!et_view_one.getText().toString().equals("") && !et_view_two.getText().toString().equals("")
                            && !et_view_three.getText().toString().equals("") && !et_view_four.getText().toString().equals(""))
                    {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    verify_btn.performClick();
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }


                            }
                        }, 1000);

                        /*verify_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(otp.equals(generatedOTP))
                                {
                                    verifyOtp();
                                }
                            }
                        });*/
                    }


                        /*new Handler().postDelayed({
                        try {

                            verify_btn.performClick()
                        }catch (e:InterruptedException)
                        {
                            e.printStackTrace()
                        }
                        },1000)*/


                }



            }
        }
    };


    void verifyBtn(){
        generatedOTP = "";
        generatedOTP = generatedOTP+et_view_one.getText().toString().trim()+et_view_two.getText().toString().trim()+et_view_three.getText().toString().trim()+et_view_four.getText().toString().trim();
        if(otp.equals(generatedOTP))
        {
            verifyOtp();
        }else {
            showAlertSingleButtonErrorOTP("Enter correct OTP");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_layout);
        // ButterKnife.bind(this);
        //  mCustomKeyboardNum = new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num);
        if (getIntent().getExtras() != null) {
            mobileNum = getIntent().getExtras().getString("MobileNumber", "");
            otp = getIntent().getExtras().getString("OTP", "");


            validation_code = getIntent().getExtras().getString(CommonInfo.VALIDATION_CODE, "");
        }
        resend_otp_tv = (TextView)findViewById(R.id.resend_otp_tv);
        resend_tv = (TextView)findViewById(R.id.resend_tv);
        countdown_tv = (TextView)findViewById(R.id.countdown_tv);
        verify_btn = (Button)findViewById(R.id.verify_btn);

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyBtn();
            }
        });

        et_view_one=(EditText)findViewById(R.id.et_one);
        et_view_two =(EditText)findViewById(R.id.et_two);

        et_view_three=(EditText)findViewById(R.id.et_three);

        et_view_four =(EditText)findViewById(R.id.et_four);
        startTimer();


        resend_otp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startTimer();
                sendOtp();
            }
        });
        //   et_view_one.setOnTouchListener(OtpActivity.this);
        et_view_one.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(et_view_one.getText().toString().length()==1)     //size as per your requirement
                {
                    et_view_two.requestFocus();
                }
            }

        });

        et_view_two.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(et_view_two.getText().toString().length()==1)     //size as per your requirement
                {
                    et_view_three.requestFocus();
                }
            }

        });

        et_view_three.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                if(et_view_three.getText().toString().length()==1)     //size as per your requirement
                {
                    et_view_four.requestFocus();
                }
            }

        });

        et_view_four.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(et_view_four.getText().toString().length()==1)     //size as per your requirement
                {
                    verify_btn.performClick();
                }
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("otp"));
        /*MySMSBroadCastReceiver broadCastReceiver = new MySMSBroadCastReceiver();
        registerReceiver(broadCastReceiver,new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));*/


    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }




    void showDialogRetry() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Error!");
        alertDialog.setIcon(R.drawable.error_ico);
        alertDialog.setCancelable(false);
        alertDialog.setMessage("There is no internet connnection. Please enable your internet and retry");
        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                sendOtp();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }

    public void verifyOtp()
    {

        sendConfirmationToServer();
/*
        sharedPreferences = getSharedPreferences(CommonInfo.APP_TOKEN_PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(CommonInfo.APP_TOKEN_PREFS_KEY, validation_code);
        editor.apply();*/
     /*   Intent intent = new Intent(OtpActivity.this, SplashScreen.class);
        startActivity(intent);
        finish();*/
        // showAlertSingleButtonInfoOtp(this, "Otp Validated");

    }
    private void sendConfirmationToServer(){
        if(isOnline()) {

            final ProgressDialog progressHUD = new ProgressDialog(this);
            progressHUD.setTitle("Validating OTP...");
            progressHUD.setIndeterminate(false);
            progressHUD.setCancelable(false);
            progressHUD.show();
            TblContactNum  data = new TblContactNum();
            data.setContactNo(mobileNum.toString());
            data.setOTP(generatedOTP);
            data.setFlgStep(2);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call <TblAllValidationData> call=apiService.userValidate(data);
            call.enqueue(new Callback<TblAllValidationData>() {
                @Override
                public void onResponse(Call<TblAllValidationData> call, Response<TblAllValidationData> response) {
                    if(response.code() == 200)
                    {
                        if(progressHUD!=null && progressHUD.isShowing())
                            progressHUD.dismiss();
                        TblAllValidationData tblAllValidationData = response.body();
                        List<TblUserValidation> tblUserValidation = tblAllValidationData.getTblUserValidations();
                      /*  if (tblUserValidation.get(0).getAction() == 0) {
                            showAlertSingleButtonErrorOTP("Oops! Something went wrong. Please check your internet connectivity and try again.");
                        } else {*/
                        if (tblUserValidation.get(0).getFlgValidated() == 1) {

                            sharedPreferences = getSharedPreferences(CommonInfo.APP_TOKEN_PREFS_NAME, MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString(CommonInfo.APP_TOKEN_PREFS_KEY, tblUserValidation.get(0).getPDACode());
                            editor.apply();
                            if(progressHUD!=null && progressHUD.isShowing())
                            progressHUD.dismiss();
                            Intent intent = new Intent(OtpActivity.this, SplashScreen.class);
                            startActivity(intent);
                            finish();
                        } else if (tblUserValidation.get(0).getFlgValidated() == 2) {
                            if(progressHUD!=null && progressHUD.isShowing())
                                progressHUD.dismiss();
                            showAlertSingleButtonErrorOTP("You are already logged in with the same mobile number. Please Contact admin.");
                        } else {
                            if(progressHUD!=null && progressHUD.isShowing())
                                progressHUD.dismiss();
                            showAlertSingleButtonErrorOTP("You are not an authenticated user. Please Contact admin.");
                        }
                        //}
                    }
                }

                @Override
                public void onFailure(Call<TblAllValidationData> call, Throwable t) {
                    t.printStackTrace();
                    if(progressHUD!=null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    showAlertSingleButtonErrorOTP( "Something went wrong. Please try again");
                }
            });
            //TblUserValidation tblUserValidation = apiService.callUserValidation(this, mobileNum, otp, 2);

        }else {
            showAlertSingleButtonErrorOTP("No internet connection found.Please turn on your internet to proceed further");

        }
    }
    public void showAlertSingleButtonInfoOtp(Context context, String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.AlertDialogHeaderMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(context.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(OtpActivity.this, SplashScreen.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            return false;

        }
        return super.onKeyDown(keyCode, event);
    }


    void startTimer()
    {
        resend_otp_tv.setTextColor(Color.parseColor("#EDEDED"));
        resend_otp_tv.setEnabled(false);
        resend_tv.setVisibility(View.VISIBLE);
        countdown_tv.setVisibility(View.VISIBLE);

        new CountDownTimer(120000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;

                countdown_tv.setText(String.valueOf(minutes)+ ":" + String.valueOf(seconds));
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


    private void sendOtp()
    {
       /* if(isOnline())
        {
            final ProgressDialog progressHUD = new ProgressDialog(this);
            progressHUD.setTitle("Sending..");
            progressHUD.setIndeterminate(false);
            progressHUD.setCancelable(false);
            progressHUD.show();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call call = apiService.sendOtp(mobileNum, generatedOTP.toString()+" is the OTP for validation of your contact number for PSR Application.Please do not share the OTP with anyone.");
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    progressHUD.dismiss();

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    t.printStackTrace();
                    progressHUD.dismiss();
                    showAlertSingleButtonErrorOTP( "Something went wrong. Please try again");
                }
            });
        }*/
        if(isOnline())
        {

            random = new Random();
            generatedOTP = String.format("%04d", random.nextInt(10000));
            Log.i("OTP", generatedOTP );

            final ProgressDialog progressHUD = new ProgressDialog(this);
            progressHUD.setTitle("Sending OTP..");
            progressHUD.setIndeterminate(false);
            progressHUD.setCancelable(false);
            progressHUD.show();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call call = apiService.sendOtp(mobileNum, generatedOTP.toString()+" is the OTP for validation of your contact number for PSR Application.Please do not share the OTP with anyone.");
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if(progressHUD!=null && progressHUD.isShowing())
                    progressHUD.dismiss();

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    t.printStackTrace();
                    if(progressHUD!=null && progressHUD.isShowing())
                    progressHUD.dismiss();
                    showAlertSingleButtonErrorOTP( "Something went wrong. Please try again");
                }
            });
           /* int flgSuccess = apiService.sendOTP(this, mobileNum, otp + " is the OTP for validation of your contact number for PSR Application.Please do not share the OTP with anyone.");
            if (flgSuccess == 1){
                progressHUD.dismiss();
            }else {
                progressHUD.dismiss();
                showAlertSingleButtonErrorOTP("Something went wrong. Please try again");
            }*/
        }else {
            showAlertSingleButtonErrorOTP("No internet connection found.Please turn on your internet to proceed further");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // openCustomKeyBoard(v);
        return true;
    }

   /* private void openCustomKeyBoard(View view){
        mCustomKeyboardNum.registerEditText((EditText) view);
        mCustomKeyboardNum.showCustomKeyboard(view);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        mCustomKeyboardNum.hideCustomKeyboard();
    }*/


    // Showing Alert Message
    public void showAlertSingleButtonErrorOTP(String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(OtpActivity.this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.error_ico)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }
}

