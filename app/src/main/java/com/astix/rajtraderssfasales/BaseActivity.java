package com.astix.rajtraderssfasales;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonFunction;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.allana.truetime.TimeUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.allana.truetime.TimeUtils.getNetworkBeforeAfterDate;
import static com.astix.Common.CommonInfo.RegistrationID;

/**
 * Created by Sunil on 11/30/2017.
 */
// Here is a generic baseAcitvity class example

public class BaseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,InterfaceRetrofitOrderOnMain
{
    private ProgressDialog mProgressDialog;
    public AppDataSource mDataSource;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    TextView tv_ofDatenew;
    int Year, Month, Day ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            AppUtils.makeStatusBarTranslucent(BaseActivity.this, getResources().getColor(R.color.colorPrimaryDark));


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mDataSource = AppDataSource.getInstance(this);
    }

    public String getToken(Context context)
    {
        String imei=AppUtils.getToken(context);
        return imei;
    }

   public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            return true;

        }
        if(keyCode==KeyEvent.KEYCODE_HOME)
        {

        }
        if(keyCode==KeyEvent.KEYCODE_MENU)
        {
            return true;
        }
        if(keyCode== KeyEvent.KEYCODE_SEARCH)
        {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public String getDateInMonthTextFormat()
    {
        long  syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        String curTime =  TimeUtils.getNetworkDateTime(BaseActivity.this, TimeUtils.DATE_FORMAT);;
        return curTime;
    }

    public String getDateAndTimeInSecondForMakingXML()
    {
        long  syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy.HH.mm.ss", Locale.ENGLISH);
        String curTime =  TimeUtils.getNetworkDateTime(BaseActivity.this, TimeUtils.TIME_FORMAT1);;
        return curTime;
    }

    public String getDateAndTimeInSecond()
    {
        long  syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String curTime =  TimeUtils.getNetworkDateTime(BaseActivity.this, TimeUtils.DATE_TIME_FORMAT);;
        return curTime;
    }

    public String getDateAndTimeInMilliSecond()
    {
        long  syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS", Locale.ENGLISH);
        String curTime =  TimeUtils.getNetworkDateTime(BaseActivity.this, "dd-MMM-yyyy HH:mm:ss.SSS");;
        return curTime;
    }


    public boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public void showNoConnAlert()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(this);
        alertDialogNoConn.setTitle(R.string.genTermNoDataConnection);
        alertDialogNoConn.setMessage(R.string.genTermNoDataConnectionFullMsg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }



    //showProgress(getResources().getString(R.string.RetrivingDataMsg));

    protected void showProgress(String msg)
    {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing())
            {
                dismissProgress();
                mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.genTermPleaseWaitNew), msg);
            }
            else
            {
                mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.genTermPleaseWaitNew), msg);
            }
        }
        catch (Exception ex)
        {

        }

    }

    protected void dismissProgress()
    {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }
        catch (Exception ex)
        {

        }
    }

    protected void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }



    //showAlertSingleButtonInfo(getResources().getString(R.string.PleaseSelectDistributor));
    public void showAlertSingleButtonInfo(String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }


    public void showAlertSingleButtonError(String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    public void showInfoSingleButtonError(String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }
    public String[] changeHmapToArrayKey(HashMap hmap)
    {
        String[] stringArray=new String[hmap.size()];
        int index=0;
        if(hmap!=null)
        {
            Set set2 = hmap.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext())
            {
                Map.Entry me2 = (Map.Entry)iterator.next();
                stringArray[index]=me2.getKey().toString();
                index=index+1;
            }
        }
        return stringArray;
    }

    public String[] changeHmapToArrayValue(HashMap hmap)
    {
        String[] stringArray=new String[hmap.size()];
        int index=0;
        if(hmap!=null)
        {
            Set set2 = hmap.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext())
            {
                Map.Entry me2 = (Map.Entry)iterator.next();
                stringArray[index]=me2.getValue().toString();
                index=index+1;
            }
        }
        return stringArray;
    }

    public static String[] checkNumberOfFiles(File dir)
    {
        int NoOfFiles=0;
        String [] Totalfiles = null;

        if (dir.isDirectory())
        {
            String[] children = dir.list();
            NoOfFiles=children.length;
            Totalfiles=new String[children.length];

            for (int i=0; i<children.length; i++)
            {
                Totalfiles[i]=children[i];
            }
        }
        return Totalfiles;
    }
    public static boolean isNumberValid(String s) {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("[5-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }


    public void dayEndCustomAlertForOrderInvoice(final Context ctx,final int flgEnableDate) {

        final Dialog dialog = new Dialog(ctx, R.style.AlertDialogDayEndTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.day_end_custom_alert_order_invoice);
        dialog.setTitle("");

        LinearLayout ll1 = dialog.findViewById(R.id.ll1);

        TextView tv_ofDate= dialog.findViewById(R.id.tv_ofDate);
        TextView ofDate= dialog.findViewById(R.id.ofDate);
        final EditText email_add_et=dialog.findViewById(R.id.email_add_et);
       // isPerformDayEndFirst=1;

        if(flgEnableDate==0) {
            if(AllButtonActivity.isPerformDayEndFirst==1)
                ofDate.setText(getNetworkBeforeAfterDate(ctx,"dd-MMM-yyyy",-1));
            else
                ofDate.setText(getDateInMonthTextFormat());
            ofDate.setEnabled(false);
        }else
        {
          //  tv_ofDate.setText("For Earlier Date :");
            ofDate.setText(getNetworkBeforeAfterDate(ctx,"dd-MMM-yyyy",-1));
            ofDate.setEnabled(true);
        }
        ofDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //fromDateBool=true;
                tv_ofDatenew = (TextView) arg0;
                calendar = Calendar.getInstance();
                Year = calendar.get(Calendar.YEAR) ;
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) ctx, Year, Month, Day-1);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);
                Calendar calendarForSetDate = Calendar.getInstance();
              //  calendarForSetDate.set();
                calendarForSetDate.setTimeInMillis(System.currentTimeMillis()-24*60*60*1000);


                // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
                //YOU can set min or max date using this code
                // datePickerDialog.setMaxDate(Calendar.getInstance());
                // datePickerDialog.setMinDate(calendar);
                datePickerDialog.setMaxDate(calendarForSetDate);

                datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

                datePickerDialog.setTitle("SELECT FROM DATE");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");



            }});



        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        Button btn_cancel_Back = dialog.findViewById(R.id.btn_cancel_Back);

        if(flgEnableDate==0) {
           // if (AllButtonActivity.isPerformDayEndFirst == 1) {

                btn_cancel_Back.setText("Skip");

           // }

        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                try
                {
                    // new GetRouteInfo().execute();

                    String EmailID="";
                    String OrderDate=ofDate.getText().toString();
                    if(!email_add_et.getText().toString().equals(""))
                    {
                        if(!email_add_et.getText().toString().trim().matches(emailPattern))
                        {
                            //	email_add_et.append("\n"  + ". Please fill proper email address.");
                            Toast.makeText(getApplicationContext(), " Please fill proper email address.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            EmailID = email_add_et.getText().toString().trim();

                        }
                    }
                    else if(email_add_et.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(), " Please fill email address.", Toast.LENGTH_SHORT).show();
                        //email_add_et.append("\n"  + ". Please fill email address.");
                        return;
                    }

                    if (dialog != null) {
                        dialog.dismiss();
                    }
                if(flgEnableDate==0)
                    CommonFunction.fnSaveRequestforOrdersOnMail(ctx, AppUtils.getToken(ctx),RegistrationID,"Please wait confirming request.",0,EmailID,OrderDate);
                else
                    CommonFunction.fnSaveRequestforOrdersOnMail(ctx, AppUtils.getToken(ctx),RegistrationID,"Please wait confirming request.",1,EmailID,OrderDate);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        btn_cancel_Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                if(flgEnableDate==0) {
                    if (AllButtonActivity.isPerformDayEndFirst == 1) {

                        AllButtonActivity.isPerformDayEndFirst = 0;

                    }
                    //finishAffinity();
                    AllButtonActivity.flgAlreadyDayendLocationCaptured=0;
                    showDayEndSucess(0, ctx);
                }
            }
        });

        dialog.setCanceledOnTouchOutside(false);


        dialog.show();


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String mon=MONTHS[monthOfYear];

        tv_ofDatenew.setText(dayOfMonth+"-"+mon+"-"+year);
    }


    @Override
    public void successOrderOnMain(int flg,Context ctx) {
        showSyncSuccessRequestforOrdersOnMail(flg,ctx);
    }

    @Override
    public void failureOrderOnMain(int flg,Context ctx) {
        if(flg==0) {
            if (AllButtonActivity.isPerformDayEndFirst == 1) {

                AllButtonActivity.isPerformDayEndFirst = 0;

            }
            //finishAffinity();
            showDayEndSucess(flg, ctx);
        }
    }
    public void showDayEndSucess(final int flg, Context ctx) {
        AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(
                ctx);
        alertDialogSyncError.setTitle(getText(R.string.genTermInformation));
        alertDialogSyncError.setCancelable(false);

            alertDialogSyncError.setMessage("Day End Successfull");


        alertDialogSyncError.setNeutralButton(getText(R.string.AlertDialogOkButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        finishAffinity();



                    }
                });
        alertDialogSyncError.setIcon(R.drawable.info_icon);

        AlertDialog alert = alertDialogSyncError.create();
        alert.show();
        // alertDialogLowbatt.show();
    }
    public void showSyncSuccessRequestforOrdersOnMail(final int flg, Context ctx)
    {
        AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(ctx);
        alertDialogSyncOK.setTitle(getText(R.string.genTermInformation));
        alertDialogSyncOK.setCancelable(false);

        alertDialogSyncOK.setMessage("Request for Orders On Mail Marked Successfully");

        alertDialogSyncOK.setNeutralButton(getText(R.string.AlertDialogOkButton),new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {


                dialog.dismiss();
                AllButtonActivity.flgAlreadyDayendLocationCaptured=0;
                if(flg==0) {
                    if (AllButtonActivity.isPerformDayEndFirst == 1) {

                        AllButtonActivity.isPerformDayEndFirst = 0;

                    }
                    //finishAffinity();
                    showDayEndSucess(flg, ctx);
                }
            }
        });
        alertDialogSyncOK.setIcon(R.drawable.info_ico);

        AlertDialog alert = alertDialogSyncOK.create();
        alert.show();
        // alertDialogLowbatt.show();
    }
}
