package com.astix.rajtraderssfasales;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.allana.truetime.TimeUtils;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblActualVsTargetReport;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static br.com.zbra.androidlinq.Linq.stream;

public class TargetVsAchievedActivity extends BaseActivity  implements InterfaceRetrofit
{

	RecyclerView rv_main;
	RecyclerView rv_subrecords;
	String date_value="";
	String imei="";
	String pickerDate="";
	String rID;
	String strIntentValuePageFrom="0";
	public String fDate;
	List<TblActualVsTargetReport> tblAllRecordsActualVsTarget=new ArrayList<TblActualVsTargetReport>();
	List<TblActualVsTargetReport> tblAllRecordsActualVsTargetLevel0=new ArrayList<TblActualVsTargetReport>();
	List<TblActualVsTargetReport> tblAllRecordsActualVsTargetLevel1=new ArrayList<TblActualVsTargetReport>();

	public TableLayout tbl3_dyntable_SchemeApplicable,tl_headerVal;
	TextView txt_note;
	public TableRow tr2PG2;
	public String[] AllDataContainer;
	String note="";
	TargetVsAchievAdapter targetVsAchievAdapterListAdapter;
	TextView tv_totOutlets;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_target_achieved_summary_newformat);

		Intent extras = getIntent();
		if(extras !=null)
		{
			date_value=extras.getStringExtra("userDate");
			pickerDate= extras.getStringExtra("pickerDate");
			imei=extras.getStringExtra("token");
			rID=extras.getStringExtra("rID");
			strIntentValuePageFrom=extras.getStringExtra("Pagefrom");
		}
		Date date1=new Date();
		SimpleDateFormat	sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		fDate = TimeUtils.getNetworkDateTime(TargetVsAchievedActivity.this,TimeUtils.DATE_FORMAT);
		rv_main = findViewById(R.id.rv_main);
		rv_subrecords = findViewById(R.id.rv_subrecords);
		tv_totOutlets=findViewById(R.id.tv_totOutlets);
		setUpVariable();
		if(isOnline())
		{

			/* try
			    {
			      GetSKUWiseSummaryForDay task = new GetSKUWiseSummaryForDay();
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
				String CovNodeIDType=mDataSource.fngetSalesPersonCvrgIdCvrgNdTyp();
				int CovNodeID=Integer.parseInt(CovNodeIDType.split(Pattern.quote("^"))[0]);
				int CovNodeType=Integer.parseInt(CovNodeIDType.split(Pattern.quote("^"))[1]);

				String SONodeIdAndNodeType= mDataSource.fnGetPersonNodeIDAndPersonNodeTypeForSO();
				//String SalesNodeIDType=mDataSource.fngetSalesPersonCvrgIdCvrgNdTyp();
				int SalesNodeID=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
				int SalesNodeType=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);
				CommonFunction.getAllTargetVsAcheivedData(TargetVsAchievedActivity.this,imei, CommonInfo.RegistrationID,"Please wait generating report.",1,CovNodeID,CovNodeType,SalesNodeID,SalesNodeType);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
		}
	}

	public void setUpVariable()
	{


		/*ScrollView outerScrollView=(ScrollView)findViewById(R.id.outerScrollView);
		outerScrollView.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event)
			{
				findViewById(R.id.innerScrollView).getParent().requestDisallowInterceptTouchEvent(false);
				return false;
			}
		});

		ScrollView innerScrollView=(ScrollView)findViewById(R.id.innerScrollView);
		innerScrollView.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});*/

		ImageView but_back=(ImageView)findViewById(R.id.backbutton);
		but_back.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				if(strIntentValuePageFrom.equals("1"))
				{
					Intent ide=new Intent(TargetVsAchievedActivity.this,StoreSelection.class);
					ide.putExtra("userDate", date_value);
					ide.putExtra("pickerDate", pickerDate);
					ide.putExtra("token", imei);
					ide.putExtra("rID", rID);
					ide.putExtra("back", "1");
					startActivity(ide);
					finish();

				}
				else if(strIntentValuePageFrom.equals("2"))
				{
					Intent ide=new Intent(TargetVsAchievedActivity.this,DetailReportSummaryActivity.class);
					ide.putExtra("userDate", date_value);
					ide.putExtra("pickerDate", pickerDate);
					ide.putExtra("token", imei);
					ide.putExtra("rID", rID);
					ide.putExtra("back", "1");
					startActivity(ide);
					finish();
				}
				else if(strIntentValuePageFrom.equals("0"))
				{

					finish();
				}


			}
		});
	}


	private void intializeFields()
	{
		tbl3_dyntable_SchemeApplicable = (TableLayout) findViewById(R.id.dyntable_SchemeApplicable);
		tl_headerVal = (TableLayout) findViewById(R.id.tl_headerVal);
		txt_note=(TextView) findViewById(R.id.txt_note);
		txt_note.setText(note);

		tv_totOutlets.setText("Total Outlets\n"+note);

		targetVsAchievAdapterListAdapter = new TargetVsAchievAdapter(TargetVsAchievedActivity.this, tblAllRecordsActualVsTargetLevel0,1);
		rv_main.setAdapter(targetVsAchievAdapterListAdapter);

		targetVsAchievAdapterListAdapter = new TargetVsAchievAdapter(TargetVsAchievedActivity.this, tblAllRecordsActualVsTargetLevel1,2);
		rv_subrecords.setAdapter(targetVsAchievAdapterListAdapter);
			/*//mDataSource.open();
			String LTschApp[] = {"380_10_5_1_20_15_2","500_20_35_3_50_25_4"};//mDataSource.PrevPDASchemeApplicableSecondPage(storeID);
			//mDataSource.close();*/

		/*LayoutInflater inflater2 = getLayoutInflater();
		for (int current2 = 0; current2 <= (AllDataContainer.length - 1); current2++)
		{

			final TableRow row2 = (TableRow)inflater2.inflate(R.layout.row_for_target_vs_achieved, tbl3_dyntable_SchemeApplicable, false);


			StringTokenizer token = new StringTokenizer(String.valueOf(AllDataContainer[current2]), "^");

			String AutoId=token.nextToken().toString().trim();
			String ProductName=token.nextToken().toString().trim();
			String TodayTarget=token.nextToken().toString().trim();
			String TodayAchieved=token.nextToken().toString().trim();
			String TodayBal=token.nextToken().toString().trim();
			String TodayFlag=token.nextToken().toString().trim();
			String MTDTarget=token.nextToken().toString().trim();
			String MTDAchieved=token.nextToken().toString().trim();
			String MTDBal=token.nextToken().toString().trim();
			String MTDFlag=token.nextToken().toString().trim();
			int valuesTargetFlg=Integer.valueOf(token.nextToken().toString().trim());

			//TodayFlag="0";
			//MTDFlag="0";

			TextView tvProductName = (TextView)row2.findViewById(R.id.tvProductName);
			tvProductName.setTextColor(Color.parseColor("#000000"));
			tvProductName.setTypeface(null, Typeface.BOLD);
			tvProductName.setText(ProductName);

			TextView tvTodayTarget = (TextView)row2.findViewById(R.id.tvTodayTarget);
			tvTodayTarget.setTextColor(Color.parseColor("#ffffff"));
			tvTodayTarget.setTypeface(null, Typeface.BOLD);
			tvTodayTarget.setText(TodayTarget);

			TextView tvTodayAchieved = (TextView)row2.findViewById(R.id.tvTodayAchieved);
			tvTodayAchieved.setTextColor(Color.parseColor("#ffffff"));
			tvTodayAchieved.setTypeface(null, Typeface.BOLD);
			tvTodayAchieved.setText(TodayAchieved);

			TextView tvTodayBal = (TextView)row2.findViewById(R.id.tvTodayBal);
			tvTodayBal.setTextColor(Color.parseColor("#ffffff"));
			tvTodayBal.setTypeface(null, Typeface.BOLD);
			tvTodayBal.setText(TodayBal);

			if(TodayFlag.equals("1"))  //&& current2!=0
			{
				tvTodayTarget.setBackgroundColor(Color.parseColor("#FD3417"));
				tvTodayAchieved.setBackgroundColor(Color.parseColor("#FD3417"));
				tvTodayBal.setBackgroundColor(Color.parseColor("#FD3417"));
			}
			else if(TodayFlag.equals("2") )
			{
				tvTodayTarget.setBackgroundColor(Color.parseColor("#D5CC2B"));
				tvTodayAchieved.setBackgroundColor(Color.parseColor("#D5CC2B"));
				tvTodayBal.setBackgroundColor(Color.parseColor("#D5CC2B"));
			}
			else if(TodayFlag.equals("3") )
			{
				tvTodayTarget.setBackgroundColor(Color.parseColor("#95EAA4"));
				tvTodayAchieved.setBackgroundColor(Color.parseColor("#95EAA4"));
				tvTodayBal.setBackgroundColor(Color.parseColor("#95EAA4"));
			}
			else if(TodayFlag.equals("4") )
			{
				tvTodayTarget.setBackgroundColor(Color.parseColor("#1F9C35"));
				tvTodayAchieved.setBackgroundColor(Color.parseColor("#1F9C35"));
				tvTodayBal.setBackgroundColor(Color.parseColor("#1F9C35"));
			}

			TextView tvMTDTarget = (TextView)row2.findViewById(R.id.tvMTDTarget);
			tvMTDTarget.setTextColor(Color.parseColor("#ffffff"));
			tvMTDTarget.setTypeface(null, Typeface.BOLD);
			tvMTDTarget.setText(MTDTarget);

			TextView tvMTDAchieved = (TextView)row2.findViewById(R.id.tvMTDAchieved);
			tvMTDAchieved.setTextColor(Color.parseColor("#ffffff"));
			tvMTDAchieved.setTypeface(null, Typeface.BOLD);
			tvMTDAchieved.setText(MTDAchieved);

			TextView tvMTDBal = (TextView)row2.findViewById(R.id.tvMTDBal);
			tvMTDBal.setTextColor(Color.parseColor("#ffffff"));
			tvMTDBal.setTypeface(null, Typeface.BOLD);
			tvMTDBal.setText(MTDAchieved);

			if(MTDFlag.equals("1"))
			{
				tvMTDTarget.setBackgroundColor(Color.parseColor("#FD3417"));
				tvMTDAchieved.setBackgroundColor(Color.parseColor("#FD3417"));
				tvMTDBal.setBackgroundColor(Color.parseColor("#FD3417"));
			}
			else if(MTDFlag.equals("2"))
			{
				tvMTDTarget.setBackgroundColor(Color.parseColor("#D5CC2B"));
				tvMTDAchieved.setBackgroundColor(Color.parseColor("#D5CC2B"));
				tvMTDBal.setBackgroundColor(Color.parseColor("#D5CC2B"));
			}
			else if(MTDFlag.equals("3"))
			{
				tvMTDTarget.setBackgroundColor(Color.parseColor("#95EAA4"));
				tvMTDAchieved.setBackgroundColor(Color.parseColor("#95EAA4"));
				tvMTDBal.setBackgroundColor(Color.parseColor("#95EAA4"));
			}
			else if(MTDFlag.equals("4"))
			{
				tvMTDTarget.setBackgroundColor(Color.parseColor("#1F9C35"));
				tvMTDAchieved.setBackgroundColor(Color.parseColor("#1F9C35"));
				tvMTDBal.setBackgroundColor(Color.parseColor("#1F9C35"));
			}
			if(valuesTargetFlg==0)
			{
				tl_headerVal.addView(row2);
			}
			else
			{
				tbl3_dyntable_SchemeApplicable.addView(row2);

			}

		}*/
	}
	@Override
	public void success() {
		//AllDataContainer= mDataSource.fetchAllDataFromtblTargetVsAchievedSummary();


		note= mDataSource.fetchNoteFromtblTargetVsAchievedNote();
		tblAllRecordsActualVsTarget=mDataSource.fetchAllRecordsFromtblTargetVsAchievedSummary();
		tblAllRecordsActualVsTargetLevel0 = stream(tblAllRecordsActualVsTarget).where(p -> p.getIsStoreLevel()==1).toList();

		tblAllRecordsActualVsTargetLevel1 = stream(tblAllRecordsActualVsTarget).where(p -> p.getIsStoreLevel()==0).toList();
		intializeFields();
	}

	@Override
	public void failure() {

	}
}
