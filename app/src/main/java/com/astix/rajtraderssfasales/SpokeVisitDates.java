package com.astix.rajtraderssfasales;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.astix.rajtraderssfasales.database.AppDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SpokeVisitDates extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.visit_date_tv)
    TextView visitDateTv;

    @BindView(R.id.visit_count_tv)
    TextView visitCountTV;

    @BindView(R.id.notvisit_count_tv)
    TextView notVisitCountTV;

   /* @BindView(R.id.recycler_visited_spoke)
    RecyclerView mVisitedSpokeRecyclerView;*/



   /* @BindView(R.id.recycler_notvisited_spoke)
    RecyclerView mNotVisitedSpokeRecyclerView;*/
   ExpandableListView listViewSpokeDetail;
   /* private List<TblSpokeProfile> visitedSpokesProfile;
    private List<TblSpokeProfile> notVisitedSpokesProfile;*/
    private AppDataSource mDataSource;
    public String mDateForFilter="";
    ExpandableSpokeWiseDateVisitValueAdapter expandableListAdapter;

    LinkedHashMap<String, List<String>> hampBeatWiseSpokeVistedonParticularDate=new LinkedHashMap<>();

    List<String> spokelist=new ArrayList<>();
    LinkedHashMap<String, List<String>> hashMap=new LinkedHashMap<>();
    public Button btn_bck;

    private Unbinder mUnbinder;


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
        setContentView(R.layout.activity_spoke_visit_dates);
        mUnbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDataSource = new AppDataSource(this);
        Bundle bundle = getIntent().getExtras();
        visitDateTv.setText(bundle.getString("Date_tag"));
        visitCountTV.setText(bundle.getString("PlannedSpokes"));
        notVisitCountTV.setText(""+bundle.getInt("RemainingSpokes"));
        mDateForFilter=bundle.getString("Date_tag");


        btn_bck=(Button)findViewById(R.id.btn_bck);
        btn_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listViewSpokeDetail = (ExpandableListView)findViewById(R.id.expandableListView);
        listViewSpokeDetail.setGroupIndicator(null);


     //   visitedSpokesProfile = new ArrayList<>();
       // notVisitedSpokesProfile= new ArrayList<>();
        hampBeatWiseSpokeVistedonParticularDate=mDataSource.fnGetBeatWiseSpokeVisitPlanListview(mDateForFilter);

        for(Map.Entry<String, List<String>> map: hampBeatWiseSpokeVistedonParticularDate.entrySet())
        {

           String BeatName=map.getKey().split(Pattern.quote("^"))[1];

            List<String> arrStores=map.getValue();
            List<String> storeValue=new ArrayList<>();
            for(int j=0;j<arrStores.size();j++)
            {


               String spokename=arrStores.get(j);

                storeValue.add(spokename);


            }
            spokelist.add(BeatName);
            hashMap.put(BeatName ,storeValue);




        }

     /*   List<TblSpokeProfile> tblSpokeProfiles=mDataSource.fnGettblBeatSpokeProfileVisitSchedule(mDateForFilter);
        int cntVisited=0;
        int cntNotVisited=0;
        for (int i = 0; i<tblSpokeProfiles.size(); i++){
           // TblSpokeProfile tblSpokeProfilenew=tblSpokeProfiles.get(i);
          //  if(tblSpokeProfilenew.getFlgSpokeVisited()==1) {
                TblSpokeProfile tblSpokeProfile = new TblSpokeProfile();
                tblSpokeProfile.setSpokename(tblSpokeProfiles.get(i).getSpokename());
                visitedSpokesProfile.add(tblSpokeProfile);
                cntVisited++;
         //   }
          *//*  if(tblSpokeProfilenew.getFlgSpokeVisited()==0) {
                TblSpokeProfile tblSpokeProfile = new TblSpokeProfile();
                tblSpokeProfile.setSpokename("Spoke " + (cntNotVisited + 1));
                notVisitedSpokesProfile.add(tblSpokeProfile);
                cntNotVisited++;
            }*//*
        }*/
new GetDataFromDB().execute();
       // initRecyclers();
    }

    private void initRecyclers() {
      /*  mVisitedSpokeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mVisitedSpokeRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mVisitedSpokeRecyclerView.setAdapter(new DateWiseSpokeAdapter(this,visitedSpokesProfile));*/

        expandableListAdapter = new ExpandableSpokeWiseDateVisitValueAdapter(this, spokelist, hashMap);
        listViewSpokeDetail.setAdapter(expandableListAdapter);

        for (int i = 0; i < expandableListAdapter.getGroupCount(); i++){
            listViewSpokeDetail.expandGroup(i);
    }

     /*   mNotVisitedSpokeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNotVisitedSpokeRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mNotVisitedSpokeRecyclerView.setAdapter(new DateWiseSpokeAdapter(this,visitedSpokesProfile));*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class GetDataFromDB extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {



           /* for (int i = 0; i<12; i++){
                TblSpokeProfile tblSpokeProfile = new TblSpokeProfile();
                tblSpokeProfile.setSpokename("Spoke "+(i + 1));
                visitedSpokesProfile.add(tblSpokeProfile);
            }*/
          /*  for (int i = 0; i<12; i++){
                TblSpokeProfile tblSpokeProfile = new TblSpokeProfile();
                tblSpokeProfile.setSpokename("Spoke "+(i + 1));
                notVisitedSpokesProfile.add(tblSpokeProfile);
            }*/

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initRecyclers();
        }
    }
}
