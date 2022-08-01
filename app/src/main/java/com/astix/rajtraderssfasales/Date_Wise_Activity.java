package com.astix.rajtraderssfasales;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.adapter.DateAdapter;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.model.TblBeatPlans;
import com.astix.rajtraderssfasales.utils.AppUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Date_Wise_Activity extends AppCompatActivity {

    /*
        RecyclerView mon_list,tue_list,wed_list,thu_list,fri_list,sat_list,sun_list;fnGettblBeatPlanDayWiseDetailsNewFunction
    */
    int flgHasSixWeek=0;
    DateAdapter dateAdapter = null;
    AppDataSource mdataSource;
    LinkedHashMap<String, List<TblBeatPlans>> hmapbeatPlans = new LinkedHashMap<String, List<TblBeatPlans>>();
    LinkedHashMap<String, String> hmapDates = new LinkedHashMap<>();
    LinkedHashMap<String, String> hmapColors_flg = new LinkedHashMap<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.color_layout1)
    LinearLayout color_layout1;

    @BindView(R.id.prev_btn)
    Button prev_btn;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.next_btn)
    Button next_btn;

    @BindView(R.id.week6TV)
    TextView week6TV;

    @BindView(R.id.week_layout)
    LinearLayout week_layout;

    @BindView(R.id.month_txt)
    TextView month_txt;
    String current_month_name = "";
    int prev_count = 0, next_count = 0;
    String imei;
    Date date = null;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    Calendar cal = Calendar.getInstance();
    int selected_curr_month_index, next_month_index, prev_month_index, month_index;
    LinearLayoutManager manager = new LinearLayoutManager(this);
    String[] dateValues;
    private Calendar mCalendar;
    private Activity context;
    private ProgressDialog mProgressDialog;
    private List<String> pjpDataList;
    private boolean showPrevButton = false;
    public Button btn_bck;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date__wise_);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imei = AppUtils.getToken(this);
        mdataSource = new AppDataSource(this);
        btn_bck=(Button)findViewById(R.id.btn_bck);
        btn_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            Date currDate = new Date();
            SimpleDateFormat currDateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);

            current_month_name = currDateFormat.format(currDate);
           // current_month_name = (String) android.text.format.DateFormat.format("MMMM", new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH).parse(getCurrentDeviceDate("dd-MMM-yyyy")));
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            date = sdf.parse(current_month_name);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        month_txt.setText(current_month_name);

        cal.setTime(date);
        selected_curr_month_index = cal.get(Calendar.MONTH) + 1;


        next_month_index = selected_curr_month_index + 1;
        prev_month_index = selected_curr_month_index - 1;


        //  mProgressDialog.setMessage("Please wait while updating month");

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                dateValues = mdataSource.fnGettblBeatPlanDayWiseDetailsNewFunction();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                DateFormat format = new SimpleDateFormat("MMM-yyyy",Locale.ENGLISH);
                try {
                    if(dateValues!=null && dateValues[0]!=null) {
                        Date date1 = format.parse(dateValues[0]);
                        Date date2 = format.parse(dateValues[1]);
                        Date currentDate = new Date();
                        if (format.format(currentDate).equals(format.format(date1))) {
                            currentDate = date1;
                            showPrevButton = currentDate.after(date2);
                        } else {
                            currentDate = date2;
                            showPrevButton = currentDate.after(date1);
                        }
                        setButtons(0);
                        showMonthData(selected_curr_month_index);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }.execute();


        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_curr_month_index = selected_curr_month_index - 1;
                setButtons(-1);
                showMonthData(selected_curr_month_index);
            }
        });


        next_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View view) {
                selected_curr_month_index = selected_curr_month_index + 1;
                setButtons(1);
                showMonthData(selected_curr_month_index);
            }
        });


    /*    date_list.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        date_list.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));*/


    }

    public void showMonthData(int MonthIdex) {
    /*    GridLayoutManager gridLayoutManager = new GridLayoutManager(this,6);
        recycler.addItemDecoration(new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dimen_2dp),6));
        recycler.setLayoutManager(gridLayoutManager);*/

        if (mProgressDialog != null)
            mProgressDialog = null;
        mProgressDialog = new ProgressDialog(Date_Wise_Activity.this);
        mProgressDialog.setMessage("Please wait while loading this month's data");
        mProgressDialog.show();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                String[] strData = mdataSource.fnGettblBeatPlanDayWiseDetailsNewFunction(MonthIdex);
                 flgHasSixWeek=mdataSource.fnCountSixWeekData(MonthIdex);
                if (pjpDataList != null)
                    pjpDataList = null;

                pjpDataList = Arrays.asList(strData);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (mProgressDialog != null)
                    mProgressDialog.dismiss();

                displayData(0);

            }
        }.execute();

    }

    public void displayData(int thisIndex) {
        GridLayoutManager gridLayoutManager = null;
        //  recycler.addItemDecoration(new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dimen_2dp),6));
        if (flgHasSixWeek == 1) {
            gridLayoutManager = new GridLayoutManager(this, 7);
            week6TV.setVisibility(View.VISIBLE);
            week_layout.setWeightSum(7);
        } else {
            gridLayoutManager = new GridLayoutManager(this, 6);
            week6TV.setVisibility(View.GONE);
            week_layout.setWeightSum(6);
        }
        recycler.setLayoutManager(gridLayoutManager);
        DateAdapter dateAdapter = new DateAdapter(this, pjpDataList, 0,flgHasSixWeek);
        recycler.setAdapter(dateAdapter);
    }


    public void setButtons(int this_index) {

        if (this_index == 0) {
            cal.set(Calendar.MONTH, selected_curr_month_index - 1);
        } else if (this_index == -1) {
            cal.set(Calendar.MONTH, selected_curr_month_index - 1);
        } else if (this_index == 1) {
            cal.set(Calendar.MONTH, selected_curr_month_index - 1/*+ 1*/);
        }

        String month_name = sdf.format(cal.getTime());
        if (this_index == 0) {

            if (showPrevButton) {
                prev_btn.setVisibility(View.VISIBLE);
                month_txt.setText(current_month_name);
                prev_btn.setEnabled(true);
                prev_btn.setTextColor(Color.parseColor("#000000"));
                prev_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_left, 0, 0, 0);
                next_btn.setVisibility(View.INVISIBLE);
                next_btn.setTextColor(Color.parseColor("#DCDCDC"));
                next_btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right_disabled, 0);
            } else {
                prev_btn.setVisibility(View.INVISIBLE);
                prev_btn.setEnabled(false);
                month_txt.setText(current_month_name);
                month_txt.setGravity(Gravity.CENTER_VERTICAL);
                prev_btn.setTextColor(Color.parseColor("#DCDCDC"));
                prev_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_left_disabled, 0, 0, 0);
                next_btn.setVisibility(View.VISIBLE);
                next_btn.setEnabled(true);
                next_btn.setTextColor(Color.parseColor("#000000"));
                next_btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
            }

        } else if (next_btn.getVisibility() == View.VISIBLE) {

       /*     cal.set(Calendar.MONTH, next_month_index - 1);
            String month_name = sdf.format(cal.getTime());
       */
            prev_btn.setVisibility(View.VISIBLE);
            month_txt.setText(month_name);
            prev_btn.setEnabled(true);
            prev_btn.setTextColor(Color.parseColor("#000000"));
            prev_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_left, 0, 0, 0);
            next_btn.setVisibility(View.INVISIBLE);
            next_btn.setTextColor(Color.parseColor("#DCDCDC"));
            next_btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right_disabled, 0);
        } else if (prev_btn.getVisibility() == View.VISIBLE) {
/*

            cal.set(Calendar.MONTH, prev_month_index - 1);
            String month_name = sdf.format(cal.getTime());
*/

            month_txt.setText(month_name);
            prev_btn.setVisibility(View.INVISIBLE);
            prev_btn.setEnabled(false);
            prev_btn.setTextColor(Color.parseColor("#DCDCDC"));
            prev_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_left_disabled, 0, 0, 0);

            next_btn.setVisibility(View.VISIBLE);
            next_btn.setEnabled(true);
            next_btn.setTextColor(Color.parseColor("#000000"));
            next_btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }



    public String getCurrentDeviceDate(String format) {
        Date pdaDate = new Date();
      /*  Calendar calendar=Calendar.getInstance();
        calendar.setTime(pdaDate);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        pdaDate=calendar.getTime();*/
        SimpleDateFormat sdfPDaDate = new SimpleDateFormat(format, Locale.ENGLISH);
        String fDatePda = sdfPDaDate.format(pdaDate).trim();
        return fDatePda;
    }
}
