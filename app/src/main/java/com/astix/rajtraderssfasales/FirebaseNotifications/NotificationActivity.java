package com.astix.rajtraderssfasales.FirebaseNotifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.astix.rajtraderssfasales.BaseActivity;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.database.AppDataSource;

import java.util.StringTokenizer;

public class NotificationActivity extends BaseActivity
{

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	
	public TableLayout tbl1_dyntable_For_Notification; 
	public TableRow tr1PG2;
	AppDataSource dbengine;
	public int ComeFromActivity=0;
	
	
	  public String imei;
      public String date;
      public String pickerDate;
      public String rID;
      public int chkActivity=1;
      
      
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		dbengine = new AppDataSource(this);
		Intent passedvals = getIntent();
		
		
		
		if( getIntent().getExtras() != null)
		{
			if(getIntent().hasExtra("chkActivity"))
			{
				chkActivity=0;
				String str = getIntent().getStringExtra("msg");
				String comeFrom = getIntent().getStringExtra("comeFrom");
				ComeFromActivity=Integer.parseInt(comeFrom);
			}
		}
		
		/*public long inserttblNotificationMstr(String IMEI,String Noti_text,String Noti_DateTime,int Noti_ReadStatus,int Noti_NewOld,
				String Noti_ReadDateTime,int Noti_outStat)*/
		tbl1_dyntable_For_Notification = (TableLayout) findViewById(R.id.dyntable_For_Notification);
		

		int SerialNo=dbengine.countNoRowIntblNotificationMstr();
		System.out.println("Sunil LastNitificationrList SerialNo : "+SerialNo);
		//String LastOrderDetail[]=dbengine.fetchAllDataFromtblFirstOrderDetailsOnLastVisitDetailsActivity(storeID);
		String LastNitificationrList[]=dbengine.LastNitificationrListDB();
		//String LastNitificationrList[]={"10-06-2015_Hi ","11-06-2015_Bye "};
		
		System.out.println("Sunil LastNitificationrList : "+LastNitificationrList.length);

		
		LayoutInflater inflater = getLayoutInflater();
		
		DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	    double x = Math.pow(dm.widthPixels/dm.xdpi,2);
	    double y = Math.pow(dm.heightPixels/dm.ydpi,2);
	    double screenInches = Math.sqrt(x+y);
		
		
		for (int current = 0; current <= (LastNitificationrList.length - 1); current++) 
		{

			final TableRow row = (TableRow)inflater.inflate(R.layout.table_notification, tbl1_dyntable_For_Notification, false);
			
			TextView tv1 = (TextView)row.findViewById(R.id.tvDate);
			TextView tv2 = (TextView)row.findViewById(R.id.tvMessage);
			
			
			if(screenInches>6.5)
			{
				tv1.setTextSize(14);
				tv2.setTextSize(14);
				
			}
			else
			{
				
			}
			
			//System.out.println("Abhinav Raj LTDdet[current]:"+LTDdet[current]);
			StringTokenizer tokens = new StringTokenizer(String.valueOf(LastNitificationrList[current]), "_");
			
			tv1.setText("  "+tokens.nextToken().trim());
			
			tv2.setText("  "+tokens.nextToken().trim());
			
		
			tbl1_dyntable_For_Notification.addView(row);
		}
    	
		
		Button backbutton=(Button)findViewById(R.id.backbutton);
		backbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();
				
			}
		});
	
	}
	@Override
    public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

}
