package com.astix.rajtraderssfasales;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.allana.truetime.TimeUtils;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblDailyDateWiseSalesManTargetVsAch;
import com.astix.rajtraderssfasales.model.TblDayWiseActualVsTargetReportSalesMan;
import com.astix.rajtraderssfasales.utils.AppUtils;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class DetailReportSummaryActivity extends BaseActivity implements  InterfaceRetrofit,MultipleInterfaceRetrofit
{
	ArrayList<TblDayWiseActualVsTargetReportSalesMan> tblDayWiseActualVsTargetReportSalesManArrayList;
	ArrayList<TblDailyDateWiseSalesManTargetVsAch> tblStoreListMasterDataRetriveArrayList;
	TableLayout tbl_inflate;
	Button btn_done;
	LinearLayout ll_MonthToDateContainer,llDayTargetContainer;
	TextView txtLastRefreshTime,refresh_text,tv_ViewPreviousDateTargets;

	LinkedHashMap<String, LinkedHashMap<String, String>> hmapSummaryDataNew=new LinkedHashMap<String, LinkedHashMap<String, String>>();
	public ProgressDialog mProgressDialogReport;
	String date_value="";
	String imei="";
	String rID;
	String pickerDate="";
	String fromPage="1";

	public String back="0";

	public TableLayout tl2;
	public int bck = 0;

	public String Noti_text="Null";
	public int MsgServerID=0;

	Locale locale  = new Locale("en", "UK");
	String pattern = "###.##";

	public String fDate;
	public String[] AllDataContainer;



	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		//mDataSource.open();
		String Noti_textWithMsgServerID= mDataSource.fetchNoti_textFromtblPDANotificationMaster();
		//mDataSource.close();

		if(!Noti_textWithMsgServerID.equals("Null"))
		{
			StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");

			MsgServerID= Integer.parseInt(token.nextToken().trim());
			Noti_text= token.nextToken().trim();

			//mDataSource.close();
			if(Noti_text.equals("") || Noti_text.equals("Null"))
			{

			}
			else
			{
				AlertDialog.Builder alertDialogSaveOK = new AlertDialog.Builder(DetailReportSummaryActivity.this);
				alertDialogSaveOK.setTitle(getResources().getString(R.string.txtNotification));

				alertDialogSaveOK.setMessage(Noti_text);
				alertDialogSaveOK.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								long syncTIMESTAMP = System.currentTimeMillis();
								Date dateobj = new Date(syncTIMESTAMP);
								SimpleDateFormat df = new SimpleDateFormat(
										"dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
								String Noti_ReadDateTime = TimeUtils.getNetworkDateTime(DetailReportSummaryActivity.this,TimeUtils.DATE_TIME_FORMAT);
								//dbengine.open();

								mDataSource.updatetblPDANotificationMaster(MsgServerID,Noti_text,0,Noti_ReadDateTime,3);
								//mDataSource.close();
								dialog.dismiss();

							}
						});
				alertDialogSaveOK.setIcon(R.drawable.info_ico);
				//alertDialogSaveOK.setIcon(R.drawable.error_info_ico);

				AlertDialog alert = alertDialogSaveOK.create();
				alert.show();

			}
		}

	}





	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_summary);
		tbl_inflate= (TableLayout) findViewById(R.id.tbl_inflate);
		Intent extras = getIntent();

		bck = extras.getIntExtra("bck", 0);
		imei = AppUtils.getToken(this);


		if(extras !=null)
		{
			date_value=extras.getStringExtra("userDate");
			pickerDate= extras.getStringExtra("pickerDate");
			rID=extras.getStringExtra("rID");
			back=extras.getStringExtra("back");
			//fromPage=extras.getStringExtra("fromPage");

		}


		Date date1=new Date();
		SimpleDateFormat	sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		fDate = TimeUtils.getNetworkDateTime(DetailReportSummaryActivity.this,TimeUtils.DATE_FORMAT);

		txtLastRefreshTime=(TextView) findViewById(R.id.txtLastRefreshTime);

		ll_MonthToDateContainer=(LinearLayout) findViewById(R.id.ll_MonthToDateContainer);
		llDayTargetContainer=(LinearLayout) findViewById(R.id.llDayTargetContainer);

		tv_ViewPreviousDateTargets=(TextView)findViewById(R.id.tv_ViewPreviousDateTargets);
		SpannableString spanString = new SpannableString(tv_ViewPreviousDateTargets.getText());
		spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(DetailReportSummaryActivity.this, android.R.color.holo_blue_dark)), 0, spanString.length(), 0);
		spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		tv_ViewPreviousDateTargets.setText(spanString);
		tv_ViewPreviousDateTargets.setTextSize(12);
		tv_ViewPreviousDateTargets.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(DetailReportSummaryActivity.this,ReportPopUpSalesManTargetVsAchDayWise.class);
				/*intent.putExtra("PersonID",Integer.parseInt(tblPersonGateMeetingTargetDataSinglePerson.get(0).getPersonNodeID()));
				intent.putExtra("PersonName", tblPersonGateMeetingTargetDataSinglePerson.get(0).getPersonname());*/
				startActivity(intent);
			}
		});




		refresh_text=(TextView)findViewById(R.id.refresh_text);
		refresh_text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(isOnline())
				{

					try
					{
						// new GetRouteInfo().execute();
						CommonFunction.getAllSummaryReportData(DetailReportSummaryActivity.this,imei, CommonInfo.RegistrationID,"Please wait generating report.");

					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					Toast.makeText(DetailReportSummaryActivity.this,getResources().getString(R.string.NoDataConnectionFullMsg) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		TextView txtSalessumuryDate=(TextView)findViewById(R.id.txtSalessumuryDate);
		txtSalessumuryDate.setText(getResources().getString(R.string.txtSummaryAsOn)+fDate);
		btn_done= (Button) findViewById(R.id.btn_done);
		btn_done.setVisibility(View.GONE);

		setUpVariable();

		if(isOnline())
		{

			/*try
			{
				GetSummaryForDay task = new GetSummaryForDay(DetailReportSummaryActivity.this);
				task.execute();
			}
			catch (Exception e)
			{
				// TODO Autouuid-generated catch block
				e.printStackTrace();
			}*/
			try
			{
				// new GetRouteInfo().execute();
				CommonFunction.getAllSummaryReportData(DetailReportSummaryActivity.this,imei, CommonInfo.RegistrationID,"Please wait generating report.");

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			Toast.makeText(DetailReportSummaryActivity.this,getResources().getString(R.string.NoDataConnectionFullMsg) , Toast.LENGTH_SHORT).show();
		}






	}

	public void getDataFromDatabase()
	{
		//String[] tblRowCount=dbengine.fetchTblRowSummary();
		tblDayWiseActualVsTargetReportSalesManArrayList=mDataSource.fnGetAlltblDayWiseActualVsTargetReportSalesMan();
		tblStoreListMasterDataRetriveArrayList=mDataSource.fnGetAlltblDailyDateWiseSalesManTargetVsAch(0);
		hmapSummaryDataNew=mDataSource.fetchTblRowSummary();
		String LastRefreshTime=mDataSource.fetchTblLastRefreshTimeReport();
		txtLastRefreshTime.setText("Last Processed Time: "+LastRefreshTime);
		tbl_inflate.removeAllViews();
		//System.out.println("CountNew " +tblRowCount.length);
		//LinkedHashMap<String, LinkedHashMap<String, String>> hmapSummaryDataNew=new LinkedHashMap<String, LinkedHashMap<String, String>>();

		int a=1;
		for (Map.Entry<String, LinkedHashMap<String, String>> entry : hmapSummaryDataNew.entrySet())
		{
			String key = entry.getKey();
			LinkedHashMap<String, String> ab = entry.getValue();

			if(a==0)
			{
				LinearLayout addSpace = new LinearLayout(DetailReportSummaryActivity.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 20);
				addSpace.setLayoutParams(lp);
				tbl_inflate.addView(addSpace);
			}
			a=0;

			for (Map.Entry<String, String> entry1 : ab.entrySet())
			{

				String key1 = entry1.getKey();

				String value = entry1.getValue();


				String TodaysSummary=value.split(Pattern.quote("^"))[0];
				String MTDSummary=value.split(Pattern.quote("^"))[1];
				String ColorCode=value.split(Pattern.quote("^"))[2];

				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.summary_row_inflate, null);



				TextView measure_val=(TextView) view.findViewById(R.id.measure_val);

				TextView txtValueAchievedToday=(TextView) view.findViewById(R.id.txtValueAchievedToday);
				TextView txtValueAchievedMTD=(TextView) view.findViewById(R.id.txtValueAchievedMTD);

				measure_val.setText(key1);
				txtValueAchievedToday.setText(TodaysSummary);
				txtValueAchievedMTD.setText(MTDSummary);

				view.setBackgroundColor(Color.parseColor(ColorCode));


				tbl_inflate.addView(view);


			}



		}

		LayoutInflater inflater = getLayoutInflater();
		ll_MonthToDateContainer.removeAllViews();
		if(tblDayWiseActualVsTargetReportSalesManArrayList!=null && tblDayWiseActualVsTargetReportSalesManArrayList.size()>0)
		{
			for (TblDayWiseActualVsTargetReportSalesMan tblDayWiseActualVsTargetReportSalesMan : tblDayWiseActualVsTargetReportSalesManArrayList)
			{
				final LinearLayout row = (LinearLayout) inflater.inflate(R.layout.targetvsachv_salesman_date_to_month_row, ll_MonthToDateContainer, false);
				TextView tv_MeasureName=row.findViewById(R.id.tv_MeasureName);
				TextView tv_MonthTgt=row.findViewById(R.id.tv_MonthTgt);
				TextView tv_MTD_Ach=row.findViewById(R.id.tv_MTD_Ach);
				TextView tv_CurrentDayRate=row.findViewById(R.id.tv_CurrentDayRate);
				TextView tv_RequiredDayRate=row.findViewById(R.id.tv_RequiredDayRate);
				//tv_MeasureName.setText(tblDayWiseActualVsTargetReportSalesMan.getMeasure());
				if(tblDayWiseActualVsTargetReportSalesMan.getMeasureID()==2 || tblDayWiseActualVsTargetReportSalesMan.getMeasureID()==4)
				{
					tv_MonthTgt.setText(""+((int)(Double.parseDouble(tblDayWiseActualVsTargetReportSalesMan.getMonthTgt()))));
					tv_MTD_Ach.setText(""+((int)(Double.parseDouble(tblDayWiseActualVsTargetReportSalesMan.getMTD_Ach()))));
					tv_CurrentDayRate.setText(""+((int)(Double.parseDouble(tblDayWiseActualVsTargetReportSalesMan.getCurrentDayRate()))));
					tv_RequiredDayRate.setText(""+((int)(Double.parseDouble(tblDayWiseActualVsTargetReportSalesMan.getRequiredDayRate()))));

				}
				else
				{
					tv_MonthTgt.setText(tblDayWiseActualVsTargetReportSalesMan.getMonthTgt());
					tv_MTD_Ach.setText(tblDayWiseActualVsTargetReportSalesMan.getMTD_Ach());
					tv_CurrentDayRate.setText(tblDayWiseActualVsTargetReportSalesMan.getCurrentDayRate());
					tv_RequiredDayRate.setText(tblDayWiseActualVsTargetReportSalesMan.getRequiredDayRate());
				}
				if(tblDayWiseActualVsTargetReportSalesMan.getMeasureID()==3 || tblDayWiseActualVsTargetReportSalesMan.getMeasureID()==4)
				{
					tv_MonthTgt.setBackgroundColor(Color.parseColor("#D7D7D7"));
					tv_CurrentDayRate.setBackgroundColor(Color.parseColor("#D7D7D7"));
					tv_RequiredDayRate.setBackgroundColor(Color.parseColor("#D7D7D7"));
				}


				SpannableString spanString = new SpannableString(tblDayWiseActualVsTargetReportSalesMan.getMeasure());
				spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(DetailReportSummaryActivity.this, android.R.color.holo_blue_dark)), 0, spanString.length(), 0);
				spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				tv_MeasureName.setText(spanString);
				tv_MeasureName.setTextSize(11);

				ll_MonthToDateContainer.addView(row);

			}
		}
//targetvsachv_salesman_daily_target_row
		llDayTargetContainer.removeAllViews();

		if(tblStoreListMasterDataRetriveArrayList!=null && tblStoreListMasterDataRetriveArrayList.size()>0)
		{
			for (TblDailyDateWiseSalesManTargetVsAch tblDailyDateWiseSalesManTargetVsAch : tblStoreListMasterDataRetriveArrayList)
			{
				final LinearLayout row = (LinearLayout) inflater.inflate(R.layout.targetvsachv_salesman_daily_target_row, llDayTargetContainer, false);
				TextView tv_MeasureName=row.findViewById(R.id.tv_MeasureName);
				TextView tv_DayTarget=row.findViewById(R.id.tv_DayTarget);
				TextView tv_DayTargetAchv=row.findViewById(R.id.tv_DayTargetAchv);

				//tv_MeasureName.setText(tblDailyDateWiseSalesManTargetVsAch.getMeasure());
				/*tv_DayTarget.setText(tblDailyDateWiseSalesManTargetVsAch.getTarget());
				tv_DayTargetAchv.setText(tblDailyDateWiseSalesManTargetVsAch.getAchievement());*/

				if(tblDailyDateWiseSalesManTargetVsAch.getMeasureID()==2 || tblDailyDateWiseSalesManTargetVsAch.getMeasureID()==4) {
					tv_DayTarget.setText("" + ((int) (Double.parseDouble(tblDailyDateWiseSalesManTargetVsAch.getTarget()))));
					tv_DayTargetAchv.setText("" + ((int) (Double.parseDouble(tblDailyDateWiseSalesManTargetVsAch.getAchievement()))));
				}
				else
				{
					tv_DayTarget.setText(tblDailyDateWiseSalesManTargetVsAch.getTarget());
					tv_DayTargetAchv.setText(tblDailyDateWiseSalesManTargetVsAch.getAchievement());
				}

				SpannableString spanString = new SpannableString(tblDailyDateWiseSalesManTargetVsAch.getMeasure());
				spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(DetailReportSummaryActivity.this, android.R.color.holo_blue_dark)), 0, spanString.length(), 0);
				spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				tv_MeasureName.setText(spanString);
				tv_MeasureName.setTextSize(11);
				llDayTargetContainer.addView(row);

			}
		}
	}

	@Override
	public void success() {
		if(isOnline())
		{

			try
			{
				mProgressDialogReport = new ProgressDialog(DetailReportSummaryActivity.this);
				mProgressDialogReport.setTitle("Please wait fetching data.");//context.getResources().getString(R.string.Loading));
				mProgressDialogReport.setMessage(getResources().getString(R.string.RetrivingDataMsg));
				mProgressDialogReport.setIndeterminate(true);
				mProgressDialogReport.setCancelable(false);
				mProgressDialogReport.show();
				MultipleInterfaceRetrofit interfaceRetrofit = (MultipleInterfaceRetrofit) this;
				CommonFunction.DateWiseTargetVsAchReportDownload reportDownloadAsyncTask = new CommonFunction.DateWiseTargetVsAchReportDownload(DetailReportSummaryActivity.this, mProgressDialogReport, interfaceRetrofit,0,0,0);
				AppUtils.executeAsyncTask(reportDownloadAsyncTask);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	@Override
	public void failure() {

	}

/*

	private class GetSummaryForDay extends AsyncTask<Void, Void, Void>
	{

		ProgressDialog pDialogGetStores;//=new ProgressDialog(DetailReport_Activity.this);
		public GetSummaryForDay(DetailReportSummaryActivity activity)
		{
			pDialogGetStores = new ProgressDialog(activity);
		}
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			//dbengine.open();
			dbengine.truncateAllSummaryDayDataTable();
			//dbengine.close();


			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(getText(R.string.genTermRetrivingSummary));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();
		}

		@Override
		protected Void doInBackground(Void... args)
		{
			ServiceWorker newservice = new ServiceWorker();

			try
			{
				newservice = newservice.getfnCallspPDAGetDayAndMTDSummary(getApplicationContext(), fDate , token);

			}
			catch (Exception e)
			{
				Log.i("SvcMgr", "Service Execution Failed!", e);
			}
			finally
			{
				Log.i("SvcMgr", "Service Execution Completed...");
			}
			return null;
		}


		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);

			Log.i("SvcMgr", "Service Execution cycle completed");

			if(pDialogGetStores.isShowing())
			{
				pDialogGetStores.dismiss();
			}
			//dbengine.open();

			getDataFromDatabase();


			//dbengine.close();




		}
	}
*/


	public void setUpVariable()
	{


		Button btn_Target_Achieved_Report = (Button) findViewById(R.id.btn_Target_Achieved_Report);
		/*if(CommonInfo.hmapAppMasterFlags.containsKey("hmapAppMasterFlags")) {
			if (CommonInfo.hmapAppMasterFlags.get("hmapAppMasterFlags") == 1) {
				btn_Target_Achieved_Report.setVisibility(View.VISIBLE);
			}
		}*/
		btn_Target_Achieved_Report.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailReportSummaryActivity.this, TargetVsAchievedActivity.class);
				intent.putExtra("token", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				intent.putExtra("Pagefrom", "2");
				DetailReportSummaryActivity.this.startActivity(intent);
				finish();

			}
		});


		ImageView but_back=(ImageView)findViewById(R.id.backbutton);
		but_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				SharedPreferences prefs = getSharedPreferences("Report",MODE_PRIVATE);
				fromPage = prefs.getString("fromPage","1");

				if(fromPage.equals("1"))
				{
					Intent ide=new Intent(DetailReportSummaryActivity.this,AllButtonActivity.class);
					ide.putExtra("userDate", date_value);
					ide.putExtra("pickerDate", pickerDate);
					ide.putExtra("token", imei);
					ide.putExtra("rID", rID);
					startActivity(ide);
					finish();

				}
				else if(fromPage.equals("2"))
				{
					Intent ide=new Intent(DetailReportSummaryActivity.this,StoreSelection.class);
					ide.putExtra("userDate", date_value);
					ide.putExtra("pickerDate", pickerDate);
					ide.putExtra("token", imei);
					ide.putExtra("rID", rID);
					startActivity(ide);
					finish();

				}
				else if(fromPage.equals("3"))
				{

					finish();

				}

			}
		});

		Button btn_sku_wise = (Button) findViewById(R.id.btn_sku_wise);
		btn_sku_wise.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailReportSummaryActivity.this, SKUWiseSummaryReport_ByTab.class);
				intent.putExtra("token", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DetailReportSummaryActivity.this.startActivity(intent);
				finish();

			}
		});

		Button btn_store_wise = (Button) findViewById(R.id.btn_store_wise);
		btn_store_wise.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailReportSummaryActivity.this, StoreWiseSummaryReport_ByTab.class);
				intent.putExtra("token", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DetailReportSummaryActivity.this.startActivity(intent);
				finish();

			}
		});

		Button btn_str_sku_wise = (Button) findViewById(R.id.btn_str_sku_wise);
		btn_str_sku_wise.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailReportSummaryActivity.this, StoreAndSKUWiseSummaryReport_ByTab.class);
				intent.putExtra("token", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DetailReportSummaryActivity.this.startActivity(intent);
				finish();

			}
		});

		Button btn_mtd_sku_wise = (Button) findViewById(R.id.btn_mtd_sku_wise);
		btn_mtd_sku_wise.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailReportSummaryActivity.this, SKUWiseSummaryReportMTD.class);
				intent.putExtra("token", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DetailReportSummaryActivity.this.startActivity(intent);
				finish();

			}
		});

		Button btn_mtd_store_wise = (Button) findViewById(R.id.btn_mtd_store_wise);
		btn_mtd_store_wise.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailReportSummaryActivity.this, StoreWiseSummaryReportMTD.class);
				intent.putExtra("token", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DetailReportSummaryActivity.this.startActivity(intent);
				finish();

			}
		});

		Button btn_mtd_str_sku_wise = (Button) findViewById(R.id.btn_mtd_str_sku_wise);
		btn_mtd_str_sku_wise.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailReportSummaryActivity.this, StoreAndSKUWiseSummaryReportMTD.class);
				intent.putExtra("token", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DetailReportSummaryActivity.this.startActivity(intent);
				finish();

			}
		});

	}

	@Override
	public void success(int flgCalledFrom) {
		if(mProgressDialogReport!=null && mProgressDialogReport.isShowing())
			mProgressDialogReport.dismiss();
		getDataFromDatabase();
	}

	@Override
	public void failure(int flgCalledFrom) {

	}
}