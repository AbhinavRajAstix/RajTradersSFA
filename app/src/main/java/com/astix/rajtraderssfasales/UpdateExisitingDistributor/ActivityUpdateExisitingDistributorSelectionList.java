package com.astix.rajtraderssfasales.UpdateExisitingDistributor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.allana.truetime.TimeUtils;
import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;

import com.astix.rajtraderssfasales.AddedOutletSummaryReportActivity;
import com.astix.rajtraderssfasales.AllButtonActivity;
import com.astix.rajtraderssfasales.BaseActivity;
import com.astix.rajtraderssfasales.CheckDatabaseData;
import com.astix.rajtraderssfasales.CheckDatabaseDataOrderBooking;
import com.astix.rajtraderssfasales.Date_Wise_Activity;
import com.astix.rajtraderssfasales.DetailReportSummaryActivity;
import com.astix.rajtraderssfasales.DistributorSocuting.ActivityDistributorAppointment;
import com.astix.rajtraderssfasales.DistributorSocuting.ActivityDistributorScouting;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceDeleteOrViewScoutingDistributor;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceServerSuccessDistributorDeletion;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributor;
import com.astix.rajtraderssfasales.ImageUploadAsyncTask;
import com.astix.rajtraderssfasales.ImageUploadFromFolderAsyncTask;
import com.astix.rajtraderssfasales.InterfaceRetrofit;
import com.astix.rajtraderssfasales.InterfaceRetrofitStoreSeleted;
import com.astix.rajtraderssfasales.InvoiceStoreSelection;
import com.astix.rajtraderssfasales.LauncherActivity;
import com.astix.rajtraderssfasales.MultipleInterfaceRetrofit;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.RemainingStockStatusReport;
import com.astix.rajtraderssfasales.SyncMaster;
import com.astix.rajtraderssfasales.TargetVsAchievedActivity;
import com.astix.rajtraderssfasales.ViewAddedStore;
import com.astix.rajtraderssfasales.XMLFileUploadAsyncTask;
import com.astix.rajtraderssfasales.XMLFileUploadFromFolderAsyncTask;
import com.astix.rajtraderssfasales.database.DBHelper;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobal;
import com.astix.rajtraderssfasales.sync.DatabaseAssistant;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.PreferenceManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static br.com.zbra.androidlinq.Linq.stream;

//import com.astix.sfatju.R;

public class ActivityUpdateExisitingDistributorSelectionList extends BaseActivity implements InterfaceRetrofit, MultipleInterfaceRetrofit, InterfaceRetrofitStoreSeleted, InterfaceDeleteOrViewScoutingDistributor, InterfaceServerSuccessDistributorDeletion {
    String ScoutingDistributorID="0";
    ProgressDialog mProgressDialogReport;

    ProgressDialog mProgressDialogDeleteDistributor;
    ArrayList<String> stIDs;
    ArrayList<String> stNames;
    public String rID = "0";
    private PreferenceManager mPreferencesManager;
    InputStream inputStream;
    int serverResponseCode = 0;
    public int isPerformDayEndFirst=0;
    String attendanceDate = "";
    public androidx.appcompat.app.AlertDialog mAlertDialog;

    RecyclerView rv_main;
    Button btnStartVisitScouting,btn_AddScoutingDistributor,btn_DistributorAppointment;

    public static final String DATASUBDIRECTORYForText = CommonInfo.TextFileFolder;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1; // 1 second
    private static final String TAG = "LocationActivity";
    public static ScheduledExecutorService schPHSTATS;

    static ScheduledExecutorService scheduler;
    private final Context mContext = this;

    public String[] xmlForWeb = new String[1];
    public int syncFLAG = 0;
    public String currSysDate;
    public int chkFlgForErrorToCloseApp = 0;
    public String passDate;
    public SimpleDateFormat sdf;
    public String fDate;
    public String userDate;
    public String pickerDate;
    public String imei;



    public long syncTIMESTAMP;
    public String fullFileName1;



    public PowerManager pm;
    public PowerManager.WakeLock wl;

    boolean serviceException = false;
    Dialog dialog;

    RelativeLayout relativeLayout1;
    int battLevel = 0;


    boolean bool = true;
    DatabaseAssistant DA;
    ImageView img_side_popUp;

    int whatTask = 0;
    String whereTo = "11";
    ArrayList mSelectedItems = new ArrayList();
    static int flgChangeRouteOrDayEnd = 0;
    public int valDayEndOrChangeRoute = 0; //

    List<TblPotentialDistributor> tblPotentialDistributorList;
    ExistingSelectionListAdapter socutingSelectionListAdapter;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {

            battLevel = intent.getIntExtra("level", 0);

        }
    };

    public static String[] checkNumberOfFiles(File dir) {
        int NoOfFiles = 0;
        String[] Totalfiles = null;

        if (dir.isDirectory()) {
            String[] children = dir.list();
            NoOfFiles = children.length;
            Totalfiles = new String[children.length];

            for (int i = 0; i < children.length; i++) {
                Totalfiles[i] = children[i];
            }
        }
        return Totalfiles;
    }

    public static void zip(String[] files, String zipFile) throws IOException {
        BufferedInputStream origin = null;
        final int BUFFER_SIZE = 2048;

        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try {
            byte[] data = new byte[BUFFER_SIZE];

            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try {
                    ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }
                } finally {
                    origin.close();
                }
            }
        } finally {
            out.close();
        }
    }

    public void locationRetrievingAndDistanceCalculating() {
        LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
        llaaa.locationRetrievingAndDistanceCalculating(this, true, true, 20, 1);


    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void success() {
        showAlertRefreshSucessFully("Information", "Data Refreshed Sucessfully");

    }


    // *****SYNC******

    @Override
    public void failure() {
        showAlertException(getResources().getString(R.string.txtError), getResources().getString(R.string.txtErrRetrieving));
    }

	/*private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {



		@Override
		protected Void doInBackground(Void... params) {

			try {

			}

			catch (Exception e) {
			//	Log.i("Sync ASync", "Sync ASync Failed!", e);

			}

			finally {

			}
			return null;
		}

		@Override
		protected void onCancelled() {

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			 pDialogSync.dismiss();

			//finish();
		}
	}*/

    // *****SYNC******

    public void SyncNow() {

        syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);


        //mDataSource.open();
        SharedPreferences sharedPrefCove= getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);

        String presentRoute = mDataSource.GetActiveRouteID();//GetActiveRouteID(sharedPrefCove.getInt("CoverageAreaNodeID",0), sharedPrefCove.getInt("CoverageAreaNodeType",0));
        //String presentRoute = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID, CommonInfo.CoverageAreaNodeType);
        //mDataSource.close();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss", Locale.ENGLISH);

        String newfullFileName = imei + "." + presentRoute + "." + df.format(dateobj);

        LinkedHashMap<String, String> hmapStoreListToProcessWithoutAlret = mDataSource.fnGetStoreListToProcessWithoutAlret();

        if (hmapStoreListToProcessWithoutAlret != null) {

            Set set2 = hmapStoreListToProcessWithoutAlret.entrySet();
            Iterator iterator = set2.iterator();
            //mDataSource.open();
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                String StoreIDToProcessWithoutAlret = me2.getKey().toString();
                mDataSource.UpdateStoreFlagAtDayEndOrChangeRouteWithOnlyVistOrCollection(StoreIDToProcessWithoutAlret, 3);

            }
            //mDataSource.close();;

            Set set3 = hmapStoreListToProcessWithoutAlret.entrySet();
            Iterator iterator1 = set3.iterator();

            while (iterator1.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator1.next();
                String StoreIDToProcessWithoutAlret = me2.getKey().toString();
                String StoreVisitCode = mDataSource.fnGetStoreVisitCodeForAlert(StoreIDToProcessWithoutAlret);
                String TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDAWhileSync(StoreIDToProcessWithoutAlret, StoreVisitCode);

                mDataSource.UpdateStoreVisitWiseTables(StoreIDToProcessWithoutAlret, 4, StoreVisitCode, TmpInvoiceCodePDA);
                mDataSource.updateflgFromWhereSubmitStatusAgainstStore(StoreIDToProcessWithoutAlret, 1, StoreVisitCode);
            }
        }

        try {

            File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!OrderXMLFolder.exists()) {
                OrderXMLFolder.mkdirs();
            }

            String routeID = mDataSource.GetActiveRouteIDSunil();

            DA.export(DBHelper.DATABASE_NAME, newfullFileName, routeID);


            if (hmapStoreListToProcessWithoutAlret != null) {

                Set set2 = hmapStoreListToProcessWithoutAlret.entrySet();
                Iterator iterator = set2.iterator();
                //mDataSource.open();
                while (iterator.hasNext()) {
                    Map.Entry me2 = (Map.Entry) iterator.next();
                    String StoreIDToProcessWithoutAlret = me2.getKey().toString();
                    mDataSource.UpdateStoreFlagAtDayEndOrChangeRouteWithOnlyVistOrCollection(StoreIDToProcessWithoutAlret, 4);
                   /* String  StoreVisitCode=mDataSource.fnGetStoreVisitCode(StoreIDToProcessWithoutAlret);
                    String TmpInvoiceCodePDA=mDataSource.fnGetInvoiceCodePDAWhileSync(StoreIDToProcessWithoutAlret,StoreVisitCode);
                    mDataSource.UpdateStoreVisitWiseTables(StoreIDToProcessWithoutAlret, 4,StoreVisitCode,TmpInvoiceCodePDA);*/
                }
                //mDataSource.close();;

                Set set3 = hmapStoreListToProcessWithoutAlret.entrySet();
                Iterator iterator1 = set3.iterator();

                while (iterator1.hasNext()) {
                    Map.Entry me2 = (Map.Entry) iterator1.next();
                    String StoreIDToProcessWithoutAlret = me2.getKey().toString();
                    String StoreVisitCode = mDataSource.fnGetStoreVisitCodeForAlert(StoreIDToProcessWithoutAlret);
                    String TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDAWhileSync(StoreIDToProcessWithoutAlret, StoreVisitCode);
                    mDataSource.UpdateStoreVisitWiseTables(StoreIDToProcessWithoutAlret, 4, StoreVisitCode, TmpInvoiceCodePDA);
                }

            }


            mDataSource.savetbl_XMLfiles(newfullFileName, "3", "1");
            //mDataSource.open();
            mDataSource.UpdateStoreImage("0", 5);
            for (int nosSelected = 0; nosSelected <= mSelectedItems.size() - 1; nosSelected++) {
                String valSN = (String) mSelectedItems.get(nosSelected);
                int valID = stNames.indexOf(valSN);
                String stIDneeded = stIDs.get(valID);

                mDataSource.UpdateStoreFlagAtDayEndOrChangeRoute(stIDneeded, 4);
                mDataSource.UpdateStoreImage(stIDneeded, 5);

                mDataSource.UpdateStoreMaterialphotoFlag(stIDneeded.trim(), 5);
                mDataSource.UpdateStoreReturnphotoFlag(stIDneeded.trim(), 5);
                mDataSource.UpdateStoreClosephotoFlag(stIDneeded.trim(), 5);

                mDataSource.UpdateNewAddedStorephotoFlag(stIDneeded.trim(), 5);


                if (mDataSource.fnchkIfStoreHasInvoiceEntry(stIDneeded) == 1) {
                    mDataSource.updateStoreQuoteSubmitFlgInStoreMstrInChangeRouteCase(stIDneeded, 0);
                }


            }

            //mDataSource.close();
            for (int nosSelected = 0; nosSelected <= mSelectedItems.size() - 1; nosSelected++) {
                String valSN = (String) mSelectedItems.get(nosSelected);
                int valID = stNames.indexOf(valSN);
                String stIDneeded = stIDs.get(valID);
                String StoreVisitCode = mDataSource.fnGetStoreVisitCodeForAlert(stIDneeded);
                String TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDAWhileSync(stIDneeded, StoreVisitCode);
                mDataSource.UpdateStoreVisitWiseTables(stIDneeded, 4, StoreVisitCode, TmpInvoiceCodePDA);
                mDataSource.updateflgFromWhereSubmitStatusAgainstStore(stIDneeded, 1, StoreVisitCode);
            }
            flgChangeRouteOrDayEnd = valDayEndOrChangeRoute;

            Intent syncIntent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, SyncMaster.class);
            syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
            syncIntent.putExtra("OrigZipFileName", newfullFileName);
            syncIntent.putExtra("whereTo", whereTo);
            startActivity(syncIntent);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(R.string.genTermGPSDisablePleaseEnable);

        alertDialog.setPositiveButton(R.string.AlertDialogOkButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.show();
    }


    public void onDestroy() {
        super.onDestroy();
        // unregister receiver
        this.unregisterReceiver(this.mBatInfoReceiver);

        //this.unregisterReceiver(this.KillME);
    }


    public void setUpVariable() {

    }

    Context mActitiy;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_distributor_selection);
        mActitiy=this;
        imei = AppUtils.getToken(ActivityUpdateExisitingDistributorSelectionList.this);
        pickerDate = getDateInMonthTextFormat();
        userDate = getDateInMonthTextFormat();
        DA = new DatabaseAssistant(this);
        mPreferencesManager=PreferenceManager.getInstance(this);
        CommonInfo.ActiveRouteSM = "0";

        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        Intent passedvals = getIntent();


        rv_main = findViewById(R.id.rv_main);
        initializeAllView();
        relativeLayout1 = findViewById(R.id.relativeLayout1);
        mProgressDialogReport = new ProgressDialog(mActitiy);
        mProgressDialogReport.setTitle("Please wait fetching data.");//context.getResources().getString(R.string.Loading));
        mProgressDialogReport.setMessage(mActitiy.getResources().getString(R.string.RetrivingDataMsg));
        mProgressDialogReport.setIndeterminate(true);
        mProgressDialogReport.setCancelable(false);
        mProgressDialogReport.show();

        MultipleInterfaceRetrofit interfaceRetrofit = (MultipleInterfaceRetrofit) mActitiy;
        CommonFunction.GetScoutingDistributorListAndDetails reportDownloadAsyncTask = new CommonFunction.GetScoutingDistributorListAndDetails(ActivityUpdateExisitingDistributorSelectionList.this, mProgressDialogReport, interfaceRetrofit,0);
        AppUtils.executeAsyncTask(reportDownloadAsyncTask);

    }


    public void initializeAllView() {

        img_side_popUp = findViewById(R.id.img_side_popUp);
        img_side_popUp.setOnClickListener(v -> open_pop_up());
        btnStartVisitScouting=findViewById(R.id.btnStartVisitScouting);
        btnStartVisitScouting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ScoutingDistributorID.equals("0")) {
                    Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, ActivityDistributorScouting.class);
                    intent.putExtra("ScoutingDistributorID",ScoutingDistributorID);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.genTermPleaseSelectScoutingDistributor, Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_AddScoutingDistributor=findViewById(R.id.btn_AddScoutingDistributor);
        btn_AddScoutingDistributor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ScoutingDistributorID="0";
                Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this,ActivityDistributorScouting.class);
                intent.putExtra("ScoutingDistributorID",ScoutingDistributorID);
                startActivity(intent);
                finish();
            }
        });

        btn_DistributorAppointment=findViewById(R.id.btn_DistributorAppointment);
        btn_DistributorAppointment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // ScoutingDistributorID="0";
               /* Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this,ActivityDistributorAppointment.class);
                intent.putExtra("ScoutingDistributorID",ScoutingDistributorID);
                startActivity(intent);
                finish();*/
                if (!ScoutingDistributorID.equals("0")) {
                    Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, ActivityDistributorAppointment.class);
                    intent.putExtra("ScoutingDistributorID",ScoutingDistributorID);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this,ActivityDistributorAppointment.class);
                    intent.putExtra("ScoutingDistributorID",ScoutingDistributorID);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }



    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        imei = AppUtils.getToken(mContext);

        String attendanceDateTime= mPreferencesManager.getStringValue(AppUtils.ATTENDANCETIME, TimeUtils.getNetworkDateTime(this, TimeUtils.DATE_TIME_FORMATForDisplay));

        checkAttendanceDateChange();
        if (fnCheckPreviousDayDayEndDone()) {

        }
    }
    private void checkAttendanceDateChange() {

        isPerformDayEndFirst=0;
        String attendanceDateTime=mDataSource.getAttendanceDateTime();

        if(TextUtils.isEmpty(attendanceDateTime) || attendanceDateTime.equalsIgnoreCase("NA"))
            attendanceDateTime=mPreferencesManager.getStringValue(AppUtils.ATTENDANCETIME,"");
        if(TextUtils.isEmpty(attendanceDateTime) || attendanceDateTime.equalsIgnoreCase("NA"))
            attendanceDateTime= TimeUtils.getNetworkDateTime(this, TimeUtils.DATE_TIME_FORMATForDisplay);
        attendanceDate= TimeUtils.getDateFromDateTime(attendanceDateTime);
        String networkDate= TimeUtils.getNetworkDate(this, TimeUtils.DATE_FORMAT);
        int numberOfDays= TimeUtils.calculateDaysDifferenceBetweenTwoDates(attendanceDate,networkDate);
        // numberOfDays=1;
        if(numberOfDays>0)
            isPerformDayEndFirst=1;

    }
    private boolean fnCheckPreviousDayDayEndDone() {
        if (isPerformDayEndFirst == 1) {
            showAlertDialogForDayEndFirst(this, "Your day end is still pending for " + attendanceDate + " , Please perform day end first.");
            return false;
        }
        return true;
    }
    public  void showAlertDialogForDayEndFirst(Context context, String alertdata) {

        if (context == null)
            return;
        try {
            if (mAlertDialog != null)
                mAlertDialog.dismiss();
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
            builder.setTitle(R.string.app_nameAlertInfo)
                    .setMessage(alertdata)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, AllButtonActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            mAlertDialog = builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int checkImagesInFolder() {
        int totalFiles = 0;
        File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.ImagesFolder);

        String[] AllFilesName = checkNumberOfFiles(del);

        if (AllFilesName != null && AllFilesName.length > 0) {
            totalFiles = AllFilesName.length;
        }
        return totalFiles;
    }

    public int checkXMLFilesInFolder() {
        int totalFiles = 0;
        File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

        String[] AllFilesName = checkNumberOfFiles(del);

        if (AllFilesName != null && AllFilesName.length > 0) {
            totalFiles = AllFilesName.length;
        }
        return totalFiles;
    }

    public void getPrevioisDateData() {
        //mDataSource.open();
        String getPDADate = mDataSource.fnGetPdaDate();
        //mDataSource.close();
        if (!getPDADate.equals("")) {
            /*Date date2 = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            String fDate = sdf.format(date2).toString().trim();
            if(!fDate.equals(getPDADate))
            {*/
            if (isOnline()) {
                try {
                    if (mDataSource.fnCheckForPendingImages() == 1) {
                        new ImageUploadAsyncTask(this).execute();
                    } else if (checkImagesInFolder() > 0) {
                        new ImageUploadFromFolderAsyncTask(this).execute();
                    } else if (mDataSource.fnCheckForPendingXMLFilesInTable() == 1) {
                        new XMLFileUploadAsyncTask(this).execute();
                    } else if (checkXMLFilesInFolder() > 0) {
                        new XMLFileUploadFromFolderAsyncTask(this).execute();
                    } else {
                        //mDataSource.open();
                        mDataSource.reCreateDB();
                        //mDataSource.close();
                        //SplashScreen.CheckUpdateVersion cuv = new SplashScreen.CheckUpdateVersion();
                        //cuv.execute();
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

            }

            // }
        }
    }


    protected void open_pop_up() {
        dialog = new Dialog(ActivityUpdateExisitingDistributorSelectionList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.selection_header_custom);
        dialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.side_dialog_animation;
        WindowManager.LayoutParams parms = dialog.getWindow().getAttributes();
        parms.gravity = Gravity.TOP | Gravity.LEFT;
        parms.height = ViewGroup.LayoutParams.MATCH_PARENT;
        parms.dimAmount = (float) 0.5;



        final  Button btnAttenReport=(Button) dialog.findViewById(R.id.btnAttenReport);
        btnAttenReport.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(ActivityUpdateExisitingDistributorSelectionList.this, Date_Wise_Activity.class));
            }
        });

        final Button butn_Census_report = dialog.findViewById(R.id.butn_Census_report);
        if (CommonInfo.flgDrctslsIndrctSls == 1) {
            butn_Census_report.setVisibility(View.GONE);
        }
        butn_Census_report.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, AddedOutletSummaryReportActivity.class);
                startActivity(intent);
                // finish();
            }
        });

        final Button btn_uploadPending_data = dialog.findViewById(R.id.btn_uploadPending_data);


        final Button butn_refresh_data = dialog.findViewById(R.id.butn_refresh_data);
        final Button but_day_end = dialog.findViewById(R.id.mainImg1);
        final Button changeRoute = dialog.findViewById(R.id.changeRoute);
        changeRoute.setVisibility(View.GONE);
        final Button btnewAddedStore = dialog.findViewById(R.id.btnewAddedStore);

        final Button btnRequestOrderOnMain = dialog.findViewById(R.id.btnRequestOrderOnMain);
        btnRequestOrderOnMain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dayEndCustomAlertForOrderInvoice(ActivityUpdateExisitingDistributorSelectionList.this, 1);
            }
        });


        final Button btnExecution = dialog.findViewById(R.id.btnExecution);
        btnExecution.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Date currDate = new Date();
                SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

                currSysDate = currDateFormat.format(currDate);
                Intent storeIntent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, InvoiceStoreSelection.class);
                storeIntent.putExtra("token", imei);
                storeIntent.putExtra("userDate", currSysDate);
                storeIntent.putExtra("pickerDate", fDate);

                startActivity(storeIntent);
            }
        });


        final Button btnRemainingStockStatus = dialog.findViewById(R.id.btnRemainingStockStatus);
        if (CommonInfo.flgDrctslsIndrctSls == 2) {
            btnRemainingStockStatus.setVisibility(View.GONE);
        }
        btnRemainingStockStatus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                btnRemainingStockStatus.setBackgroundColor(Color.GREEN);
                dialog.dismiss();
                Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, RemainingStockStatusReport.class);
                // Intent intent = new Intent(StoreSelection.this, DetailReport_Activity.class);
                intent.putExtra("token", imei);
                intent.putExtra("userDate", userDate);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", "0");
                intent.putExtra("back", "0");
                startActivity(intent);
                finish();

            }
        });


        final Button butHome = dialog.findViewById(R.id.butHome);
        butHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, AllButtonActivity.class);
                startActivity(intent);
                finish();
            }
        });
        final Button btnIncentiveDetails=(Button)dialog.findViewById(R.id.btnIncentiveDetails);
        int flgShowHideIncentiveSectorID=mDataSource.fnGetSectorID();
        if(flgShowHideIncentiveSectorID==3)
            btnIncentiveDetails.setVisibility(View.GONE);
        final Button btnTargetVsAchieved = dialog.findViewById(R.id.btnTargetVsAchieved);
        if (CommonInfo.flgDrctslsIndrctSls == 2) {
            //	btnTargetVsAchieved.setVisibility(View.VISIBLE);
        }
        btnTargetVsAchieved.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, TargetVsAchievedActivity.class);
                intent.putExtra("token", imei);
                intent.putExtra("userDate", userDate);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                intent.putExtra("Pagefrom", "0");
                //intent.putExtra("back", "0");
                startActivity(intent);
                finish();


            }
        });


        btnewAddedStore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, ViewAddedStore.class);
                intent.putExtra("token", imei);
                intent.putExtra("userDate", userDate);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                //intent.putExtra("back", "0");
                startActivity(intent);
                finish();

            }
        });
        final Button btnVersion = dialog.findViewById(R.id.btnVersion);
        btnVersion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                btnVersion.setBackgroundColor(Color.GREEN);
                dialog.dismiss();
            }
        });

        //mDataSource.open();
        String ApplicationVersion = DBHelper.AppVersionID;
        //mDataSource.close();
        btnVersion.setText(getResources().getString(R.string.VersionNo) + ApplicationVersion);

        // Version No-V12

        final Button but_SalesSummray = dialog.findViewById(R.id.btnSalesSummary);
        but_SalesSummray.setVisibility(View.GONE);
        but_SalesSummray.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                but_SalesSummray.setBackgroundColor(Color.GREEN);
                dialog.dismiss();

                SharedPreferences sharedPrefReport = getSharedPreferences("Report", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefReport.edit();
                editor.putString("fromPage", "2");
                editor.commit();

                Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, DetailReportSummaryActivity.class);
                intent.putExtra("token", imei);
                intent.putExtra("userDate", userDate);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                intent.putExtra("back", "0");
                intent.putExtra("fromPage", "StoreSelection");
                startActivity(intent);
                finish();

            }
        });


        final Button btnChangeLanguage = dialog.findViewById(R.id.btnChangeLanguage);
        if (CommonInfo.flgLangChangeReuired == 1) {
            btnChangeLanguage.setVisibility(View.VISIBLE);
        } else {
            btnChangeLanguage.setVisibility(View.GONE);
        }
        btnChangeLanguage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                final Dialog dialogLanguage = new Dialog(ActivityUpdateExisitingDistributorSelectionList.this);
                dialogLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogLanguage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                dialogLanguage.setCancelable(false);
                dialogLanguage.setContentView(R.layout.language_popup);

                TextView textviewEnglish = dialogLanguage.findViewById(R.id.textviewEnglish);
                TextView textviewHindi = dialogLanguage.findViewById(R.id.textviewHindi);
                TextView textviewGujrati = dialogLanguage.findViewById(R.id.textviewGujrati);

                textviewEnglish.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLanguage.dismiss();
                        setLanguage("en");
                    }
                });
                textviewHindi.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLanguage.dismiss();
                        setLanguage("hi");
                    }
                });
                textviewGujrati.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLanguage.dismiss();
                        setLanguage("gu");
                    }
                });
                dialogLanguage.show();


            }
        });


        final Button btnCheckTodayInvoice = dialog.findViewById(R.id.btnCheckTodayInvoice);
        if (CommonInfo.flgDrctslsIndrctSls == 2) {
            btnCheckTodayInvoice.setVisibility(View.GONE);
        }
        btnCheckTodayInvoice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                but_SalesSummray.setBackgroundColor(Color.GREEN);
                dialog.dismiss();
                Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, CheckDatabaseData.class);
                // Intent intent = new Intent(StoreSelection.this, DetailReport_Activity.class);
                intent.putExtra("token", imei);
                intent.putExtra("userDate", userDate);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                intent.putExtra("back", "0");
                startActivity(intent);
                finish();

            }
        });


        final Button btnCheckTodayOrder = dialog.findViewById(R.id.btnCheckTodayOrder);
        if (CommonInfo.flgDrctslsIndrctSls == 1) {
            btnCheckTodayOrder.setVisibility(View.GONE);
        }
        btnCheckTodayOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                but_SalesSummray.setBackgroundColor(Color.GREEN);
                dialog.dismiss();
                Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, CheckDatabaseDataOrderBooking.class);
                // Intent intent = new Intent(StoreSelection.this, DetailReport_Activity.class);
                intent.putExtra("token", imei);
                intent.putExtra("userDate", userDate);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                intent.putExtra("back", "0");
                startActivity(intent);
                finish();

            }
        });
        btn_uploadPending_data.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                if (isOnline()) {


					/*if(mDataSource.fnCheckForPendingImages()==1)
					{
						ImageSync task = new ImageSync(StoreSelection.this);
						task.execute();

					}
					else if(mDataSource.fnCheckForPendingXMLFilesInTable()==1)
					{
						new FullSyncDataNow(StoreSelection.this).execute();

					}
					else
					{
						showInfoSingleButtonError(getResources().getString(R.string.NoPendingDataMsg));
					}
*/
                    if (mDataSource.fnCheckForPendingImages() > 0) {
                        ImageSync task = new ImageSync(ActivityUpdateExisitingDistributorSelectionList.this);
                        task.execute();

                    } else if (mDataSource.fnCheckForPendingXMLFilesInTable() == 1) {
                        new FullSyncDataNow(ActivityUpdateExisitingDistributorSelectionList.this).execute();

                    } else {
                        showAlertSingleButtonInfo(getResources().getString(R.string.NoPendingDataMsg));
                    }
                } else {
                    showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
                }
            }
        });

        but_day_end.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


        changeRoute.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


        butn_refresh_data.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                butn_refresh_data.setBackgroundColor(Color.GREEN);
                if (CommonInfo.VanLoadedUnloaded == 1) {
                    showAlertSingleWareHouseStockconfirButtonInfo("Stock is updated, please confirm the warehouse stock first.");

                } else {
                    if (isOnline()) {
                        fnStartProcedureOfRefreshData();

                    } else {
                        showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
                        return;

                    }
                }

                dialog.dismiss();

            }

        });


        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void fnStartProcedureOfRefreshData() {
        AlertDialog.Builder alertDialogBuilderNEw11 = new AlertDialog.Builder(ActivityUpdateExisitingDistributorSelectionList.this);

        // set title
        alertDialogBuilderNEw11.setTitle(getResources().getString(R.string.genTermNoDataConnection));

        // set dialog message
        alertDialogBuilderNEw11.setMessage(getResources().getString(R.string.RefreshDataMsg));
        alertDialogBuilderNEw11.setCancelable(false);
        alertDialogBuilderNEw11.setPositiveButton(getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogintrfc, int id) {
                // if this button is clicked, close
                // current activity
                dialogintrfc.cancel();
                try {
                    try {
                        // new GetRouteInfo().execute();
                        CommonFunction.getAllMasterTableModelData(ActivityUpdateExisitingDistributorSelectionList.this, AppUtils.getToken(ActivityUpdateExisitingDistributorSelectionList.this), CommonInfo.RegistrationID, "Please wait Refreshing data.", 1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {

                }

                //onCreate(new Bundle());
            }
        });

        alertDialogBuilderNEw11.setNegativeButton(getResources().getString(R.string.AlertDialogNoButton),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogintrfc, int which) {
                        // //System.out.println("value ofwhatTask after no button pressed by sunil"+whatTask);

                        dialogintrfc.dismiss();
                    }
                });

        alertDialogBuilderNEw11.setIcon(R.drawable.info_ico);
        AlertDialog alert121 = alertDialogBuilderNEw11.create();
        alert121.show();
    }
	/* public void dayEndCustomAlert(int flagWhichButtonClicked)
	 {
		 final Dialog dialog = new Dialog(StoreSelection.this,R.style.AlertDialogDayEndTheme);

		 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.day_end_custom_alert);
			if(flagWhichButtonClicked==1)
			{
				dialog.setTitle(R.string.genTermSelectStoresPendingToCompleteDayEnd);
			}
			else if(flagWhichButtonClicked==2)
			{
				dialog.setTitle(R.string.genTermSelectStoresPendingToCompleteChangeRoute);
			}



				LinearLayout ll_product_not_submitted=(LinearLayout) dialog.findViewById(R.id.ll_product_not_submitted);
				mSelectedItems.clear();
				final String[] stNames4List = new String[stNames.size()];
				 checks=new boolean[stNames.size()];
				stNames.toArray(stNames4List);

				for(int cntPendingList=0;cntPendingList<stNames4List.length;cntPendingList++)
				{
					LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					final View viewAlertProduct=inflater.inflate(R.layout.day_end_alrt,null);
					final TextView txtVw_product_name=(TextView) viewAlertProduct.findViewById(R.id.txtVw_product_name);
					txtVw_product_name.setText(stNames4List[cntPendingList]);
					txtVw_product_name.setTextColor(this.getResources().getColor(R.color.green_submitted));
					final ImageView img_to_be_submit=(ImageView) viewAlertProduct.findViewById(R.id.img_to_be_submit);
					img_to_be_submit.setTag(cntPendingList);

					final ImageView img_to_be_cancel=(ImageView) viewAlertProduct.findViewById(R.id.img_to_be_cancel);
					img_to_be_cancel.setTag(cntPendingList);
					img_to_be_submit.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {


							if(!checks[Integer.valueOf(img_to_be_submit.getTag().toString())])
							{
								img_to_be_submit.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.select_hover) );
								img_to_be_cancel.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel_normal) );
								txtVw_product_name.setTextColor(getResources().getColor(R.color.green_submitted));
								mSelectedItems.add(stNames4List[Integer.valueOf(img_to_be_submit.getTag().toString())]);
								checks[Integer.valueOf(img_to_be_submit.getTag().toString())]=true;
							}


						}
					});

					img_to_be_cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (mSelectedItems.contains(stNames4List[Integer.valueOf(img_to_be_cancel.getTag().toString())]))
							{
								if(checks[Integer.valueOf(img_to_be_cancel.getTag().toString())])
								{

									img_to_be_submit.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.select_normal) );
									img_to_be_cancel.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel_hover) );
									txtVw_product_name.setTextColor(Color.RED);
									mSelectedItems.remove(stNames4List[Integer.valueOf(img_to_be_cancel.getTag().toString())]);
									checks[Integer.valueOf(img_to_be_cancel.getTag().toString())]=false;
								}

							}

						}
					});
					mSelectedItems.add(stNames4List[cntPendingList]);
					 checks[cntPendingList]=true;
					 ll_product_not_submitted.addView(viewAlertProduct);
				}


				Button btnSubmit=(Button) dialog.findViewById(R.id.btnSubmit);
				Button btn_cancel_Back=(Button) dialog.findViewById(R.id.btn_cancel_Back);
				btnSubmit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mSelectedItems.size() == 0) {

							DayEnd();
						//surbhi

						}

						else {

							int countOfOrderNonSelected=0;
							for(int countForFalse=0;countForFalse<checks.length;countForFalse++)
							{
								if(checks[countForFalse]==false)
								{
									countOfOrderNonSelected++;
								}

							}
							if(countOfOrderNonSelected>0)
							{
								confirmationForSubmission(String.valueOf(countOfOrderNonSelected));
							}

							else
							{


								whatTask = 2;
								// -- Route Info Exec()
								try {

									new bgTasker().execute().get();
								} catch (InterruptedException e) {
									e.printStackTrace();
									//System.out.println(e);
								} catch (ExecutionException e) {
									e.printStackTrace();
									//System.out.println(e);
								}
								// --
							}

						}

					}
				});

				btn_cancel_Back.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						valDayEndOrChangeRoute=0;
						dialog.dismiss();
					}
				});

				dialog.setCanceledOnTouchOutside(false);


				dialog.show();




	 }*/




    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
	/*	if(storeList.length>0)
		{

		}

		else
		{
			new GetStoresForDay().execute();
		}*/
    }

    public void showAlertRefreshSucessFully(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityUpdateExisitingDistributorSelectionList.this);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.info_icon);
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
              /*  Intent i = new Intent(StoreSelection.this, LauncherActivity.class);
                i.putExtra("token", imei);
                startActivity(i);
                finish();*/
                Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, ActivityUpdateExisitingDistributorSelectionList.class);
                intent.putExtra("imei", AppUtils.getToken(ActivityUpdateExisitingDistributorSelectionList.this));
                intent.putExtra("userDate", userDate);
                intent.putExtra("pickerDate", fDate);
                intent.putExtra("rID", rID);



                startActivity(intent);
                finish();
            }
        });


        // Showing Alert Message
        alertDialog.show();
    }

    public void showAlertException(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityUpdateExisitingDistributorSelectionList.this);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(getResources().getString(R.string.txtRetry), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
                try {
                    // new GetRouteInfo().execute();
                    CommonFunction.getAllMasterTableModelData(ActivityUpdateExisitingDistributorSelectionList.this, AppUtils.getToken(ActivityUpdateExisitingDistributorSelectionList.this), CommonInfo.RegistrationID, "Please wait Refreshing data.", 1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton(getResources().getString(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void showAlertSingleWareHouseStockconfirButtonInfo(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, AllButtonActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }



    public void saveLocale(String lang) {


        SharedPreferences prefs = getSharedPreferences("LanguagePref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Language", lang);
        editor.commit();
    }

    private void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());
        saveLocale(language);
        // updateTexts();
        //you can refresh or you can settext
        Intent refresh = new Intent(ActivityUpdateExisitingDistributorSelectionList.this, LauncherActivity.class);
        startActivity(refresh);
        finish();

    }


    public String convertExponential(double firstNumber) {
        String secondNumberAsString = String.format("%.10f", firstNumber);
        return secondNumberAsString;
    }

    public void uploadImage(String sourceFileUri, String fileName, String tempIdImage) throws IOException {
        BitmapFactory.Options IMGoptions01 = new BitmapFactory.Options();
        IMGoptions01.inDither = false;
        IMGoptions01.inPurgeable = true;
        IMGoptions01.inInputShareable = true;
        IMGoptions01.inTempStorage = new byte[16 * 1024];

        //finalBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(fnameIMG,IMGoptions01), 640, 480, false);

        Bitmap bitmap = BitmapFactory.decodeFile(Uri.parse(sourceFileUri).getPath(), IMGoptions01);

//			/Uri.parse(sourceFileUri).getPath()
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream); //compress to which format you want.

        //b is the Bitmap
        //int bytes = bitmap.getWidth()*bitmap.getHeight()*4; //calculate how many bytes our image consists of. Use a different value than 4 if you don't use 32bit images.

        //ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
        //bitmap.copyPixelsToBuffer(buffer); //Move the byte data to the buffer
        //byte [] byte_arr = buffer.array();


        //     byte [] byte_arr = stream.toByteArray();
        String image_str = BitMapToString(bitmap);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        ////System.out.println("image_str: "+image_str);

        stream.flush();
        stream.close();
        //buffer.clear();
        //buffer = null;
        bitmap.recycle();
        nameValuePairs.add(new BasicNameValuePair("image", image_str));
        nameValuePairs.add(new BasicNameValuePair("FileName", fileName));
        nameValuePairs.add(new BasicNameValuePair("storeID", tempIdImage));

        try {

            HttpParams httpParams = new BasicHttpParams();
            int some_reasonable_timeout = (int) (30 * DateUtils.SECOND_IN_MILLIS);

            //HttpConnectionParams.setConnectionTimeout(httpParams, some_reasonable_timeout);

            HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout + 2000);


            HttpClient httpclient = new DefaultHttpClient(httpParams);
            HttpPost httppost = new HttpPost(CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameImageSyncPath);


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);

            String the_string_response = convertResponseToString(response);
            if (response.getStatusLine().getStatusCode() == 200) {
                mDataSource.updateSSttImage(fileName, 4);
                mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);
                mDataSource.updateSSttStoreCheckImageImage(fileName, 4);
                mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);

                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + fileName;
                File fdelete = new File(file_dj_path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {

                        callBroadCast();
                    } else {

                    }
                }

            }

        } catch (Exception e) {

            System.out.println(e);
            //	IMGsyOK = 1;

        }
    }

    public String BitMapToString(Bitmap bitmap) {
        int h1 = bitmap.getHeight();
        int w1 = bitmap.getWidth();

        if (w1 > 768 || h1 > 1024) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 1024, 768, true);

        } else {

            bitmap = Bitmap.createScaledBitmap(bitmap, w1, h1, true);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] arr = baos.toByteArray();
        String result = android.util.Base64.encodeToString(arr, android.util.Base64.DEFAULT);
        return result;
    }

    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException {

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
        //System.out.println("contentLength : " + contentLength);
        //Toast.makeText(MainActivity.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();
        if (contentLength < 0) {
        } else {
            byte[] data = new byte[512];
            int len = 0;
            try {
                while (-1 != (len = inputStream.read(data))) {
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close(); // closing the stream…..
            } catch (IOException e) {
                e.printStackTrace();
            }
            res = buffer.toString();     // converting stringbuffer to string…..

            //System.out.println("Result : " + res);
            //Toast.makeText(MainActivity.this, "Result : " + res, Toast.LENGTH_LONG).show();
            ////System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
    }

    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(ActivityUpdateExisitingDistributorSelectionList.this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                public void onScanCompleted(String path, Uri uri) {

                }
            });
        } else {
            Log.e("-->", " < 14");
            ActivityUpdateExisitingDistributorSelectionList.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    public int upLoad2Server(String sourceFileUri, String fileUri) {

        fileUri = fileUri.replace(".xml", "");

        String fileName = fileUri;
        String zipFileName = fileUri;

        String newzipfile = Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + fileName + ".zip";

        sourceFileUri = newzipfile;

        xmlForWeb[0] = Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + fileName + ".xml";


        try {
            zip(xmlForWeb, newzipfile);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            //java.io.FileNotFoundException: /359648069495987.2.21.04.2016.12.44.02: open failed: EROFS (Read-only file system)
        }


        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;


        File file2send = new File(newzipfile);

        String urlString = "";
        if (zipFileName.contains(".xml")) {
            urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + zipFileName;

        } else {
            urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + zipFileName + ".xml";

        }

        try {

            // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(file2send);
            URL url = new URL(urlString);

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("zipFileName", zipFileName);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                    + zipFileName + "\"" + lineEnd);

            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            //Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

            if (serverResponseCode == 200) {
                syncFLAG = 1;


                mDataSource.upDateTblXmlFile(fileName);
                delXML(xmlForWeb[0]);


            } else {
                syncFLAG = 0;
            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return serverResponseCode;

    }

    public void delXML(String delPath) {
        File file = new File(delPath);
        file.delete();
        File file1 = new File(delPath.replace(".xml", ".zip"));
        file1.delete();
    }

    @Override
    public void success(int flgCalledFrom) {
        tblPotentialDistributorList=new ArrayList<TblPotentialDistributor>();
        tblPotentialDistributorList=mDataSource.fnGetPotentialDetailsList(150);

        socutingSelectionListAdapter = new ExistingSelectionListAdapter(ActivityUpdateExisitingDistributorSelectionList.this, tblPotentialDistributorList);
        rv_main.setAdapter(socutingSelectionListAdapter);
        if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
        {
            mProgressDialogReport.dismiss();
        }
    }

    @Override
    public void failure(int flgCalledFrom) {
        if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
        {
            mProgressDialogReport.dismiss();
        }
    }

    @Override
    public void startVisitOfSelectedStore(String StoreIDSelected) {
        ScoutingDistributorID = StoreIDSelected;
    }

    @Override
    public void ShowSelectedStoreAddress(String StoreAddress) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Information");
        alertDialog.setIcon(R.drawable.info_icon);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        String addr = StoreAddress.split(Pattern.quote("^"))[0];
        String numb = StoreAddress.split(Pattern.quote("^"))[1];

        alertDialog.setMessage("" + addr + "\n\n" + "Contact No:- " + numb);

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void ShowSelectedStoreTeleCallerOrders(String StoreIDSelected) {

    }



    private class ImageSync extends AsyncTask<Void, Void, Boolean> {

        public ImageSync(ActivityUpdateExisitingDistributorSelectionList activity) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress(getResources().getString(R.string.SubmittingPndngDataMsg));

        }

        @Override
        protected Boolean doInBackground(Void... args) {
            boolean isErrorExist = false;


            try {
                //mDataSource.upDateCancelTask("0");
                ArrayList<String> listImageDetails = new ArrayList<String>();

                listImageDetails = mDataSource.getImageDetails(5);

                if (listImageDetails != null && listImageDetails.size() > 0) {
                    for (String imageDetail : listImageDetails) {
                        String tempIdImage = imageDetail.split(Pattern.quote("^"))[0];
                        String imagePath = imageDetail.split(Pattern.quote("^"))[1];
                        String imageName = imageDetail.split(Pattern.quote("^"))[2];
                        String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageName;
                        File fImage = new File(file_dj_path);
                        if (fImage.exists()) {
                            uploadImage(imagePath, imageName, tempIdImage);
                        } else {
                            mDataSource.updateSSttImage(imageName, 4);
                            mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);
                        }

                    }
                }


                ArrayList<String> listImageStoreCheckIn = new ArrayList<String>();

                listImageStoreCheckIn = mDataSource.getStoreCheckInImages(5);

                if (listImageStoreCheckIn != null && listImageStoreCheckIn.size() > 0) {
                    for (String imageDetail : listImageStoreCheckIn) {
                        String tempIdImage = imageDetail.split(Pattern.quote("^"))[0];
                        String imagePath = imageDetail.split(Pattern.quote("^"))[1];
                        String imageName = imageDetail.split(Pattern.quote("^"))[2];
                        String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageName;
                        File fImage = new File(file_dj_path);
                        if (fImage.exists()) {
                            uploadImage(imagePath, imageName, tempIdImage);
                        } else {
                            mDataSource.updateSSttImage(imageName, 4);
                            mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);
                        }

                    }
                }


                ArrayList<String> listImageStoreClosed = new ArrayList<String>();

                listImageStoreClosed = mDataSource.getStoreClosedImages(5);

                if (listImageStoreClosed != null && listImageStoreClosed.size() > 0) {
                    for (String imageDetail : listImageStoreClosed) {
                        String tempIdImage = imageDetail.split(Pattern.quote("^"))[0];
                        String imagePath = imageDetail.split(Pattern.quote("^"))[1];
                        String imageName = imageDetail.split(Pattern.quote("^"))[2];
                        String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageName;
                        File fImage = new File(file_dj_path);
                        if (fImage.exists()) {
                            uploadImage(imagePath, imageName, tempIdImage);
                        } else {
                            mDataSource.updateSSttImage(imageName, 4);
                            mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);
                        }
                    }
                }

            } catch (Exception e) {
                isErrorExist = true;
            } finally {
                Log.i("SvcMgr", "Service Execution Completed...");
            }

            return isErrorExist;
        }

        @Override
        protected void onPostExecute(Boolean resultError) {
            super.onPostExecute(resultError);


            dismissProgress();


            mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);
            if (mDataSource.fnCheckForPendingXMLFilesInTable() == 1) {
                new FullSyncDataNow(ActivityUpdateExisitingDistributorSelectionList.this).execute();
            }


        }
    }

    private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


        int responseCode = 0;

        public FullSyncDataNow(ActivityUpdateExisitingDistributorSelectionList activity) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            File XMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!XMLFolder.exists()) {
                XMLFolder.mkdirs();
            }


            showProgress(getResources().getString(R.string.SubmittingPndngDataMsg));

        }

        @Override

        protected Void doInBackground(Void... params) {


            try {


                File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

                // check number of files in folder
                String[] AllFilesName = checkNumberOfFiles(del);


                if (AllFilesName != null && AllFilesName.length > 0) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);


                    for (int vdo = 0; vdo < AllFilesName.length; vdo++) {
                        String fileUri = AllFilesName[vdo];


                        //System.out.println("Sunil Again each file Name :" +fileUri);

                        if (fileUri.contains(".zip")) {
                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.OrderXMLFolder + "/" + fileUri);
                            file.delete();
                        } else {
                            String f1 = "";
                            if (fileUri.contains(".xml")) {
                                f1 = Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.OrderXMLFolder + "/" + fileUri;
                            } else {
                                f1 = Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.OrderXMLFolder + "/" + fileUri + ".xml";
                            }
                            // System.out.println("Sunil Again each file full path"+f1);
                            try {
                                responseCode = upLoad2Server(f1, fileUri);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (responseCode != 200) {
                            break;
                        }

                    }

                } else {
                    responseCode = 200;
                }


            } catch (Exception e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onCancelled() {

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dismissProgress();

            if (responseCode == 200) {
                mDataSource.deleteXmlTable("4");
                mDataSource.UpdateStoreVisitWiseTablesAfterSync(4);
                showAlertSingleButtonInfo("Pending Data Uploaded Suceessfully");
            }
        }
    }

    public void showStoreAddressAlert(String Addres) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Information");
        alertDialog.setIcon(R.drawable.info_icon);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        String addr = Addres.split(Pattern.quote("^"))[0];
        String numb = Addres.split(Pattern.quote("^"))[1];

        alertDialog.setMessage("" + addr + "\n\n" + "Contact No:- " + numb);

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void fnDeleteScoutingDistributor(TblPotentialDistributor tblPotentialDistributor) {
        showAlertSingleButtonInfoBeforeDeletingDistributor("Are you sure  that you want to remove distributor :"+tblPotentialDistributor.getDescr(),tblPotentialDistributor);
    }

    @Override
    public void fnViewScoutingDistributorDetails(TblPotentialDistributor tblPotentialDistributor) {
        if (!tblPotentialDistributor.getNodeID().equals("0")) {
            Intent intent = new Intent(ActivityUpdateExisitingDistributorSelectionList.this,ActivityDistributorUpdation.class);
            intent.putExtra("ScoutingDistributorID",tblPotentialDistributor.getNodeID());
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.genTermPleaseSelectScoutingDistributor, Toast.LENGTH_SHORT).show();
        }
    }


    public void showAlertSingleButtonInfoBeforeDeletingDistributor(String msg,TblPotentialDistributor tblPotentialDistributorForDelete)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {

                                dialogInterface.dismiss();
                                mProgressDialogDeleteDistributor=null;
                                mProgressDialogDeleteDistributor = new ProgressDialog(mContext);
                                mProgressDialogDeleteDistributor.setTitle("Please wait deleteting distributor.");//context.getResources().getString(R.string.Loading));
                                mProgressDialogDeleteDistributor.setMessage(mContext.getResources().getString(R.string.SubmittingDataMsg));
                                mProgressDialogDeleteDistributor.setIndeterminate(true);
                                mProgressDialogDeleteDistributor.setCancelable(false);
                                mProgressDialogDeleteDistributor.show();
                                InterfaceServerSuccessDistributorDeletion interfaceRetrofitDistributorDelete = (InterfaceServerSuccessDistributorDeletion) mContext;
                                CommonFunction.DeleteExistingDistributor reportDownloadAsyncTask = new CommonFunction.DeleteExistingDistributor(ActivityUpdateExisitingDistributorSelectionList.this, mProgressDialogReport, interfaceRetrofitDistributorDelete,0, tblPotentialDistributorForDelete);
                                AppUtils.executeAsyncTask(reportDownloadAsyncTask);
                            }
                        }

                ).create().show();
    }

    @Override
    public void successRecordDelete(TblPotentialDistributor tblPotentialDistributorForDelete) {
        for(TblPotentialDistributor tblPotentialDistributor1:tblPotentialDistributorList)
        {
            if(tblPotentialDistributor1.getNodeID()==tblPotentialDistributorForDelete.getNodeID())
            {
                tblPotentialDistributor1.setFlgDelete(1);
                break;
            }
        }
        try {
            if(mProgressDialogDeleteDistributor!=null && mProgressDialogDeleteDistributor.isShowing())
            {
                mProgressDialogDeleteDistributor.dismiss();
            }
        }
        catch (Exception ex)
        {

        }

        List<TblPotentialDistributor> tblPotentialDistributorListFilter=stream(tblPotentialDistributorList).where(p->p.getFlgDelete()==0).toList();
        socutingSelectionListAdapter = new ExistingSelectionListAdapter(ActivityUpdateExisitingDistributorSelectionList.this, tblPotentialDistributorListFilter);
        rv_main.setAdapter(socutingSelectionListAdapter);
    }

    @Override
    public void failureRecordDelete() {
        try {
            if(mProgressDialogDeleteDistributor!=null && mProgressDialogDeleteDistributor.isShowing())
            {
                mProgressDialogDeleteDistributor.dismiss();
            }
        }
        catch (Exception ex)
        {

        }
        showAlertException(getResources().getString(R.string.txtError), "Error while deleting distributor,\nPlese contact admin");
    }

}
