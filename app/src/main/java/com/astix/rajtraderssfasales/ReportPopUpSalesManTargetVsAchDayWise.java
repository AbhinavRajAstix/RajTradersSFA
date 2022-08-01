package com.astix.rajtraderssfasales;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.astix.Common.CommonFunction;
import com.astix.rajtraderssfasales.database.AppDataSource;

import com.astix.rajtraderssfasales.model.TblDailyDateWiseSalesManTargetVsAch;

import com.astix.rajtraderssfasales.model.TblDayWiseActualVsTargetReportSalesMan;
import com.astix.rajtraderssfasales.utils.AppUtils;
import static br.com.zbra.androidlinq.Linq.stream;

import br.com.zbra.androidlinq.Grouping;
import br.com.zbra.androidlinq.Stream;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportPopUpSalesManTargetVsAchDayWise extends BaseActivity implements  MultipleInterfaceRetrofit {

    String imei="";
    public Context mActitiy;
    ArrayList<TblDailyDateWiseSalesManTargetVsAch> tblStoreListMasterDataRetriveArrayList;;
    public ProgressDialog mProgressDialogReport;
    public int PersonID=0;
public String PersonNameToShowReport="";
    @BindView(R.id.tvDescr)
    TextView tvDescr;

    @BindView(R.id.llDynamicDayWiseReport)
    LinearLayout llDynamicDayWiseReport;


    @BindView(R.id.imgCncl)
    ImageView imgCncl;


    public Context getCtx() {
        return mActitiy;
    }


    @Override
    protected void onDestroy() {
       // this.unregisterReceiver(this.mBatInfoReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_report_salesman_targetvsach_daywise);
        mActitiy = this;

        ButterKnife.bind(this);
        imei = AppUtils.getToken(ReportPopUpSalesManTargetVsAchDayWise.this);

        mDataSource = AppDataSource.getInstance(mActitiy);


       /* Intent intent = getIntent();
        PersonNameToShowReport= intent.getStringExtra("PersonName");
        PersonID= intent.getIntExtra("PersonID",0);*/

      //  fnGetLocalData();

        PersonNameToShowReport=mDataSource.fnGetPersonNameFromtblUserAuthenticationMstr();
        imgCncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
     /*   mProgressDialogReport = new ProgressDialog(mActitiy);
        mProgressDialogReport.setTitle("Please wait fetching data.");//context.getResources().getString(R.string.Loading));
        mProgressDialogReport.setMessage(mActitiy.getResources().getString(R.string.RetrivingDataMsg));
        mProgressDialogReport.setIndeterminate(true);
        mProgressDialogReport.setCancelable(false);
        mProgressDialogReport.show();*/

     /*   int PersonNodeId=0;
        int PersonNodeType=0;

        MultipleInterfaceRetrofit interfaceRetrofit = (MultipleInterfaceRetrofit) mActitiy;
        CommonFunction.DateWiseTargetVsAchReportDownload reportDownloadAsyncTask = new CommonFunction.DateWiseTargetVsAchReportDownload(ReportPopUpSalesManTargetVsAchDayWise.this, mProgressDialogReport, interfaceRetrofit,PersonNodeId,PersonNodeType,0);
        AppUtils.executeAsyncTask(reportDownloadAsyncTask);*/


        GetData getData = new GetData();
        AppUtils.executeAsyncTask(getData);
    }
    /*public void fnGetLocalData()
    {

    }*/
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void success(int calledfor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescr.setText(Html.fromHtml("Here is a brief Date wise Target Vs Achievement details of <b>" + PersonNameToShowReport + "</b>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescr.setText(Html.fromHtml("Here is a brief Date wise Target Vs Achievement details of  <b>" + PersonNameToShowReport + "</b>"));
        }

        if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
            mProgressDialogReport.dismiss();
        GetData getData = new GetData();
        AppUtils.executeAsyncTask(getData);
    }

    @Override
    public void failure(int flgCalledFrom) {

    }


    private class GetData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            tblStoreListMasterDataRetriveArrayList=mDataSource.fnGetAlltblDailyDateWiseSalesManTargetVsAch(1);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            init();
        }
    }


    private void init() {
        llDynamicDayWiseReport.removeAllViews();
        fnLoadDynamicDayWiseReport();
       /* if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
        {
            mProgressDialogReport.dismiss();
        }*/
    }
    public void fnLoadDynamicDayWiseReport()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescr.setText(Html.fromHtml("Here is a brief Datewise Target Vs Achievement details of <b>" + PersonNameToShowReport + "</b>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescr.setText(Html.fromHtml("Here is a brief Datewise Target Vs Achievement details of  <b>" + PersonNameToShowReport + "</b>"));
        }
        LayoutInflater inflater = getLayoutInflater();
        List<String> dsitinctDates =
                stream(tblStoreListMasterDataRetriveArrayList)
                        .select(c -> c.getRptDate() ).distinct().toList();
        //tblStoreListMasterDataRetriveArrayList
        if(dsitinctDates!=null && dsitinctDates.size()>0) {
            for (int current = 0; current < dsitinctDates.size(); current++) {

                final LinearLayout row = (LinearLayout) inflater.inflate(R.layout.daywise_salesman_targetvsachv_row, llDynamicDayWiseReport, false);
                TextView tv_rpDate = row.findViewById(R.id.tv_rpDate);
                LinearLayout tbl_AllContainer=row.findViewById(R.id.tbl_AllContainer);
                String RptDate=dsitinctDates.get(current);

                List<TblDailyDateWiseSalesManTargetVsAch> tblStoreListMasterDataRetriveArrayListInnerReords=stream(tblStoreListMasterDataRetriveArrayList).where(c -> c.getRptDate().equals(RptDate)).toList();

                LayoutInflater inflaternew = getLayoutInflater();
                tbl_AllContainer.removeAllViews();
                if(tblStoreListMasterDataRetriveArrayListInnerReords!=null && tblStoreListMasterDataRetriveArrayListInnerReords.size()>0) {
                    for (TblDailyDateWiseSalesManTargetVsAch tblDailyDateWiseSalesManTargetVsAch : tblStoreListMasterDataRetriveArrayListInnerReords) {
                        final LinearLayout rownew = (LinearLayout) inflaternew.inflate(R.layout.targetvsachv_salesman_daily_target_previous_dates_records_row, tbl_AllContainer, false);
                        TextView tv_MeasureName=rownew.findViewById(R.id.tv_MeasureName);
                        EditText et_Target=rownew.findViewById(R.id.et_Target);
                        EditText et_Ache=rownew.findViewById(R.id.et_Ache);

                        SpannableString spanString = new SpannableString(tblDailyDateWiseSalesManTargetVsAch.getMeasure());
                        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ReportPopUpSalesManTargetVsAchDayWise.this, android.R.color.holo_blue_dark)), 0, spanString.length(), 0);
                        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_MeasureName.setText(spanString);
                        tv_MeasureName.setTextSize(11);
                        if(tblDailyDateWiseSalesManTargetVsAch.getMeasureID()==2 || tblDailyDateWiseSalesManTargetVsAch.getMeasureID()==4) {
                            et_Target.setText("" + ((int) (Double.parseDouble(tblDailyDateWiseSalesManTargetVsAch.getTarget()))));
                            et_Ache.setText("" + ((int) (Double.parseDouble(tblDailyDateWiseSalesManTargetVsAch.getAchievement()))));
                        }
                        else
                        {
                            et_Target.setText(tblDailyDateWiseSalesManTargetVsAch.getTarget());
                            et_Ache.setText(tblDailyDateWiseSalesManTargetVsAch.getAchievement());
                        }

                        tbl_AllContainer.addView(rownew);
                    }
                }


                tv_rpDate.setText(dsitinctDates.get(current));

                llDynamicDayWiseReport.addView(row);

            }
        }
    }
}
