package com.astix.rajtraderssfasales.SignUpModule;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.BaseActivity;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.rest.ApiClient;
import com.astix.rajtraderssfasales.rest.ApiInterface;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends BaseActivity {

    @BindView(R.id.mobileET)
    EditText mobileET;

    @BindView(R.id.btn_verify)
    Button btnVerify;

    Random random;

    String generatedOTP = "";

    String validation_code = "";
    int flg_userValidated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_verify)
    public void verifyMobileNumber(){
        if (TextUtils.isEmpty(mobileET.getText().toString().trim())){
            showAlertSingleButtonInfo("Please enter your registered contact number.");
            return;
        }

       /* if (AppUtils.isNumberValid(mobileET.getText().toString().trim())){
            AppUtils.showAlertSingleButtonInfo(this,"Please enter 10 digits contact number and number cannot be start with 0, 1, 2, 3 or 4");
            return;
        }*/

        random = new Random();
        generatedOTP = String.format("%04d", random.nextInt(10000));
        Log.i("OTP", generatedOTP );

        if(generatedOTP != null)
        {
            sendOtp();
        }




    }


    private void sendOtp()
    {
        if(isOnline())
        {
            final ProgressDialog progressHUD = new ProgressDialog(this);
            progressHUD.setTitle("Sending..");
            progressHUD.setIndeterminate(false);
            progressHUD.setCancelable(false);
            progressHUD.show();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            TblContactNum  data = new TblContactNum();
            data.setContactNo(mobileET.getText().toString());
            data.setOTP("");
            data.setFlgStep(1);
            Call<TblAllValidationData> call = apiService.userValidate(data);
            call.enqueue(new Callback<TblAllValidationData>() {
                @Override
                public void onResponse(Call<TblAllValidationData> call, Response<TblAllValidationData> response) {

                    if(response.code() == 200)
                    {
                        if(progressHUD!=null && progressHUD.isShowing())
                        progressHUD.dismiss();
                        TblAllValidationData tblAllValidationData = response.body();
                        List<TblUserValidation> tblUserValidation = tblAllValidationData.getTblUserValidations();

                        validation_code = tblUserValidation.get(0).getPDACode();
                        flg_userValidated = tblUserValidation.get(0).getFlgValidated();
                        if (flg_userValidated == 2) {
                            if(progressHUD!=null && progressHUD.isShowing())
                            progressHUD.dismiss();
                            showAlertSingleButtonError("You are already logged in with the same mobile number. Please Contact admin.");
                        } else if(flg_userValidated == 0  || validation_code == null)
                        {
                            showAlertSingleButtonErrorThis( "Your number is not registered.Please contact admin.");
                        }
                        else
                        {
                            ApiInterface apiService1 = ApiClient.getClient().create(ApiInterface.class);
                            Call call1 = apiService1.sendOtp(mobileET.getText().toString(), generatedOTP + " is the OTP for validation of your contact number for Raj Store Mapping Application.Please do not share the OTP with anyone.");
                            call1.enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    progressHUD.dismiss();
                                    if(response.code() == 200)
                                    {
                                        if(progressHUD!=null && progressHUD.isShowing())
                                        progressHUD.dismiss();
                                        Intent newIntent = new Intent(SignUp.this, OtpActivity.class);
                                        newIntent.putExtra("MobileNumber", mobileET.getText().toString());
                                        newIntent.putExtra("OTP", generatedOTP);
                                        newIntent.putExtra(CommonInfo.VALIDATION_CODE, validation_code);
                                        startActivity(newIntent);
                                        finish();
                                    }

                                    else
                                    {
                                        showAlertSingleButtonError( "Something went wrong. Please try again");
                                    }
                                }



                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    if(progressHUD!=null && progressHUD.isShowing())
                                    progressHUD.dismiss();
                                    showAlertSingleButtonError( "Something went wrong. Please try again");

                                }
                            });
                        }


                    }

                    else {
                        if(progressHUD!=null && progressHUD.isShowing())
                        progressHUD.dismiss();
                        showAlertSingleButtonError( "Something went wrong. Please try again");

                    }



                }

                @Override
                public void onFailure(Call<TblAllValidationData> call, Throwable t) {
                    t.printStackTrace();
                    if(progressHUD!=null && progressHUD.isShowing())
                    progressHUD.dismiss();
                    showAlertSingleButtonError( "Something went wrong. Please try again");
                }
            });
        }

        else
        {
            showAlertSingleButtonError( "No internet connection found.Please turn on your internet to proceed further");
        }
    }

    public void showAlertSingleButtonErrorThis( String msg) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(this.getResources().getString(R.string.AlertDialogHeaderErrorMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.error_ico)
                .setPositiveButton(this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                }).create().show();
    }

}
