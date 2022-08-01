package com.astix.rajtraderssfasales.mTASModule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allana.truetime.TimeUtils;
import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.AllButtonActivity;
import com.astix.rajtraderssfasales.BaseActivity;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.PreferenceManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MTASWebActivity extends BaseActivity {


    private final static int FILECHOOSER_RESULTCODE = 1;
    private static final String TAG = MTASWebActivity.class.getSimpleName();
    public ValueCallback<Uri[]> uploadMessage;
    private WebView mWebView;
    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;
    private ImageView closeIcon;
    private ValueCallback<Uri> mUploadMessage;
    private String mCM;
    private Context context;
    public static AlertDialog mAlertDialog;

    // private static final String url = "http://103.20.212.194/ltace_dev/frmLoginPDA.aspx?IMEI=";
    private int ADMIN_REQUEST_CODE = 0;

    private DevicePolicyManager mDPM = null;
    private ComponentName mAdminName;
    String attendanceDate = "";
    private PreferenceManager mPreferencesManager;


    @Override
    protected void onResume() {
        super.onResume();
        if(AllButtonActivity.isPerformDayEndFirst==1)
        {
            fnCheckPreviousDayDayEndDone();
        }

    }

    private void checkAttendanceDateChange() {

        AllButtonActivity.isPerformDayEndFirst=0;
        String attendanceDateTime=mDataSource.getAttendanceDateTime();

        if(TextUtils.isEmpty(attendanceDateTime) || attendanceDateTime.equalsIgnoreCase("NA"))
            attendanceDateTime=mPreferencesManager.getStringValue(AppUtils.ATTENDANCETIME,"");
        if(TextUtils.isEmpty(attendanceDateTime) || attendanceDateTime.equalsIgnoreCase("NA"))
            attendanceDateTime= TimeUtils.getNetworkDateTime(this,TimeUtils.DATE_TIME_FORMATForDisplay);
        attendanceDate= TimeUtils.getDateFromDateTime(attendanceDateTime);
        String networkDate= TimeUtils.getNetworkDate(this,TimeUtils.DATE_FORMAT);
        int numberOfDays=TimeUtils.calculateDaysDifferenceBetweenTwoDates(attendanceDate,networkDate);
        // numberOfDays=1;
        if(numberOfDays>0)
            AllButtonActivity.isPerformDayEndFirst=1;

    }
    private boolean fnCheckPreviousDayDayEndDone() {
        if (AllButtonActivity.isPerformDayEndFirst == 1) {
            showAlertDialog(this, "Your day end is still pending for " + attendanceDate + " , Please perform day end first.");
            return false;
        }
        return true;
    }
    public  void showAlertDialog(Context context, String alertdata) {

        if (context == null)
            return;
        try {
            if (mAlertDialog != null)
                mAlertDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.app_name)
                    .setMessage(alertdata)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MTASWebActivity.this, AllButtonActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
            mAlertDialog = builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_web);
        closeIcon = findViewById(R.id.close_img);
        context = this;
        mWebView = findViewById(R.id.web_view_imageStore);
        mToolbar = findViewById(R.id.toolbar);
        TextView titleTV = findViewById(R.id.titleTV);
        titleTV.setText("MTAS");
        mPreferencesManager=PreferenceManager.getInstance(this);
        dbEngine = new AppDataSource(this);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MTASWebActivity.this, AllButtonActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        if (savedInstanceState != null) {
            mWebView.restoreState(savedInstanceState);
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Purchase Order");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkDrawOverlayPermission();
        }*/
        checkAttendanceDateChange();
        initWebView();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {
        Log.v("App", "Package Name: " + getApplicationContext().getPackageName());

        // check if we already  have permission to draw over other apps
        if (!Settings.canDrawOverlays(this)) {
            Log.v("App", "Requesting Permission" + Settings.canDrawOverlays(this));
            // if not construct intent to request permission
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getApplicationContext().getPackageName()));
//     request permission via start activity for result
            startActivityForResult(intent, 101);
        } else {
            Log.v("App", "We already have permission for it.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {


        if (requestCode == 101) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Log.v("App", "We already have permission for it.");
                } else {
                    Log.v("App", "We have not permission for it.");
                }
            }
            return;
        }

        if (resultCode == RESULT_OK && requestCode == ADMIN_REQUEST_CODE) {
            Intent intentCall;
            try {

                long time = System.currentTimeMillis();
                /*if (CommonInfo.FlgRecording == 1) {
                    if (AppUtils.isMyServiceRunning(UploadRecordingsService.class,MTASWebActivity.this))
                        stopService(new Intent(MTASWebActivity.this, UploadRecordingsService.class));

                    CommonInfo.CurrentRecordFileName = "LTACECallRecordFile-" + time;
                    AppUtils.startCallRecord(MTASWebActivity.this.getApplicationContext(), CommonInfo.CurrentRecordFileName);
                }*/

                intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ CommonInfo.ContactNumber));
                startActivity(intentCall);
            } catch (Exception e) {
                e.printStackTrace();
                intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+ CommonInfo.ContactNumber));
                startActivity(intentCall);
            }
            return;
        }else if (resultCode == RESULT_CANCELED && requestCode == ADMIN_REQUEST_CODE){
            Toast.makeText(this, "Please allow permission to make calls.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;

            //Check if response is positive
            if (resultCode == RESULT_OK) {
                if (requestCode == FILECHOOSER_RESULTCODE) {

                    if (null == uploadMessage) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            uploadMessage.onReceiveValue(results);
            uploadMessage = null;
        } else {

            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == mUploadMessage) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);

    }

    private void initWebView() {
        mProgressDialog = ProgressDialog.show(this, "", "Loading Page...", false);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.e("WebAppInterface", "" + url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mProgressDialog != null)
                    mProgressDialog.dismiss();
            }
        });

        WebSettings webSettings = mWebView.getSettings();
        mWebView.setWebChromeClient(new WebChromeClient() {
            // For Lollipop 5.0+ Devices
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams) {

                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                }

                uploadMessage = filePathCallback;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(MTASWebActivity.this.getPackageManager()) != null) {

                    File photoFile = null;

                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCM);
                    } catch (IOException ex) {
                        Log.e(TAG, "Image file creation failed", ex);
                    }
                    if (photoFile != null) {
                        mCM = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("*/*");
                Intent[] intentArray;

                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);

                return true;
            }
        });
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }

        String completeURL = CommonInfo.MTAS_ORDER_WEB_URL + AppUtils.getToken(this);
//        String completeURL = "http://103.20.212.194/downloads/NumberHTML.html";
        mWebView.addJavascriptInterface(new WebAppInterface(), "Android");
        mWebView.loadUrl(completeURL);
    }

    private File createImageFile() throws IOException {

        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
    }
    private AppDataSource dbEngine;
    public class WebAppInterface {
        @JavascriptInterface
        public void callStore(String contactNumber, String StoreId, String TeleCallingId, String flgRecording) {
            Log.e("WebAppInterface", "" + contactNumber+" "+StoreId+" "+TeleCallingId +" "+flgRecording);
            /*CommonInfo.CurrentTime = AppUtils.getCurrentTime();
            TblMTASCallLogs tblMTASCallLogs = new TblMTASCallLogs(
                    contactNumber,
                    "NA",
                    "NA",
                    "NA",
                    0,
                    "NA",
                    Utils.Companion.getToken(context),
                    Integer.parseInt(flgRecording),
                    CommonInfo.StoreIdForRecording,
                    3,
                    Integer.parseInt(TeleCallingId),
                    1,
                    "JAVAScript",
                    CommonInfo.CurrentTime,
                    "NA"
            );
            Log.e("tblMTASCallLogs", tblMTASCallLogs.toString());
            dbEngine.saveCallLogs(tblMTASCallLogs);
//            Toast.makeText(MTASWebActivity.this, "CallStore : " + contactNumber+" "+StoreId+" "+TeleCallingId +" "+flgRecording, Toast.LENGTH_SHORT).show();
        */    if (contactNumber != null) {

          /*      if (StoreId != null)
                    CommonInfo.StoreIdForRecording = StoreId;

                CommonInfo.TeleCallingIdForRecording = Integer.parseInt(TeleCallingId);
                CommonInfo.FlgRecording = Integer.parseInt(flgRecording);
*/
                CommonInfo.ContactNumber = contactNumber;

                try {

                   /* long time = System.currentTimeMillis();
                    if (CommonInfo.FlgRecording == 1) {
                        if (AppUtils.isMyServiceRunning(UploadRecordingsService.class,MTASWebActivity.this))
                            stopService(new Intent(MTASWebActivity.this, UploadRecordingsService.class));

                        CommonInfo.CurrentRecordFileName = "LTACECallRecordFile-" + time;
                        AppUtils.startCallRecord(MTASWebActivity.this.getApplicationContext(), CommonInfo.CurrentRecordFileName);
                    }*/

                    Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + CommonInfo.ContactNumber));
                    if (ActivityCompat.checkSelfPermission(MTASWebActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        Toast.makeText(MTASWebActivity.this, "Please restart the app and allow required permissions", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
//                        startActivity(intentCall);

                        intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + CommonInfo.ContactNumber));
                        startActivity(intentCall);
                    } catch (Exception e) {
                        e.printStackTrace();
                        intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + CommonInfo.ContactNumber));
                        startActivity(intentCall);
                    }
                    /*mDPM = (DevicePolicyManager) MTASWebActivity.this.getSystemService(Context.DEVICE_POLICY_SERVICE);
                    mAdminName = new ComponentName(MTASWebActivity.this, DeviceAdminDemo.class);

                    if (!mDPM.isAdminActive(mAdminName)) {
                        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
                        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Click on Activate button to secure your application.");
                        startActivityForResult(intent, ADMIN_REQUEST_CODE);
                    } else {
                        long time = System.currentTimeMillis();
                        if (CommonInfo.FlgRecording == 1) {
                            if (AppUtils.isMyServiceRunning(UploadRecordingsService.class,MTASWebActivity.this))
                                stopService(new Intent(MTASWebActivity.this, UploadRecordingsService.class));

                            CommonInfo.CurrentRecordFileName = "LTACECallRecordFile-" + time;
                            AppUtils.startCallRecord(MTASWebActivity.this.getApplicationContext(), CommonInfo.CurrentRecordFileName);
                        }

                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + CommonInfo.ContactNumber));
                        if (ActivityCompat.checkSelfPermission(MTASWebActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            Toast.makeText(MTASWebActivity.this, "Please restart the app and allow required permissions", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            startActivity(intentCall);
                        } catch (Exception e) {
                            e.printStackTrace();
                            intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + CommonInfo.ContactNumber));
                            startActivity(intentCall);
                        }
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MTASWebActivity.this, "Problem occurs while click on phone number", Toast.LENGTH_SHORT).show();
            }
//            this@MTASBrowserActivity.redirectMTAS()
        }

    }
}
