package com.astix.rajtraderssfasales;

import static br.com.zbra.androidlinq.Linq.stream;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.adapter.CardArrayAdapterCategory2;
import com.astix.rajtraderssfasales.model.TblDistributorProductStock;
import com.astix.rajtraderssfasales.model.TblProductLevelData;
import com.astix.rajtraderssfasales.model.TblProductTypeMasterForRetriving;
import com.astix.rajtraderssfasales.model.TblStoreProductMappingForDisplay;
import com.astix.rajtraderssfasales.model.TblUOMMapping;
import com.astix.rajtraderssfasales.model.TblUOMMaster;
import com.astix.rajtraderssfasales.sync.DatabaseAssistant;
import com.astix.rajtraderssfasales.sync.SyncJobService;
import com.astix.rajtraderssfasales.utils.AppUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class DistributorCheckInSecondActivity extends BaseActivity implements CategoryCommunicator, InterfaceRetrofit, focusLostCalled {
    SharedPreferences sharedPref;
    List<TblStoreProductMappingForDisplay> tblStoreProductMappingListtemp = new ArrayList<>();
    HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMappingForCaseConversion = new HashMap<Integer, List<TblUOMMapping>>();
    CustomKeyboard mCustomKeyboardNum, mCustomKeyboardNumWithoutDecimal;
    //    public int StockPcsCaseType = 0;
    LinearLayout ll_forTableHeaderName;
    RecyclerView rvProducts;
    ImageView img_ctgry, img_back_Btn;
    EditText ed_search;
    TextView txt_stockDate;
    Button btn_save, btn_skip;
    Spinner spinner_for_filter;
    String DbrNodeId, DbrNodeType, DbrName;
    int DistribtrId_Global = 0;

    //    public int chkFlgForErrorToCloseApp = 0;
    String imei, fDate, from;

    public int DistributorNodeType_Global = 0;
    //    LinkedHashMap<String, String> HmapDistribtrReport = new LinkedHashMap<>();
    LinkedHashMap<String, String> HmapGetPDAdata;
    //    LinkedHashMap<String, String> HmapGetUOMType;
//    LinkedHashMap<String, String> HmapGetPDAOldStockData;
//    LinkedHashMap<String, Integer> hmapStock;
//    ArrayList<String> DistribtrReportColumnDesc;
    ArrayList<String> DbrArray = new ArrayList<>();
    String[] Distribtr_list;

    public String[] PName = null;

    public ProgressDialog pDialog2STANDBY;
    public String newfullFileName;
    int SectorId=0;

//    public String[] xmlForWeb = new String[1];
//    public int syncFLAG = 0;
//    public ProgressDialog pDialogGetStores;
    //report alert
//    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_entry);

//        mProgressDialog = new ProgressDialog(this);
        sharedPref = getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);



        mCustomKeyboardNum = new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num);
        mCustomKeyboardNumWithoutDecimal = new CustomKeyboard(this, R.id.keyboardviewNumDecimal, R.xml.num_without_decimal);
        initializeFields();
        getDataFromatabase();
    }

    //    ArrayList<TblProductTypeMasterForRetriving> tblProductTypeMasterForRetrivingList = new ArrayList<>();
    LinkedHashMap<String, String> hmapctgry_details;
    HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMapping = new HashMap<>();
    HashMap<String, String> hmapPrdctOdrQty, hmapflgPicsOrCases;
    public ProductFilledDataModel prdctModelArrayList = new ProductFilledDataModel();
    List<TblStoreProductMappingForDisplay> tblStoreProductMappingList = new ArrayList<>(), tblStoreProductMappingListForDisplay = new ArrayList<>();
    OrderAdapterDistributor orderAdapter;
    List<String> categoryNames;

    private void getCategoryDetail() {
//        tblProductTypeMasterForRetrivingList = mDataSource.fetch_Category_List_OrderEntry();
        hmapctgry_details = mDataSource.fetch_Category_ListOtherFromOrderEntry();

        int index = 0;
        if (hmapctgry_details != null) {
            categoryNames = new ArrayList<>();
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapctgry_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                categoryNames.add(me2.getKey().toString());
                index = index + 1;
            }
        }

    }

    public String defaultCatName_Id = "0", previousSlctdCtgry, distID = "";

    public void getPrductInfoDetail() {
        hmapProductTblUOMMapping = mDataSource.fetch_ProductWiseGetAllMeasureUnitAgainstProductDistributorEntry(DistribtrId_Global);

        hmapPrdctOdrQty = mDataSource.fnGetProductPurchaseListStockEntryDistributorEntry(DistribtrId_Global, DistributorNodeType_Global);

        hmapflgPicsOrCases = mDataSource.fnGetProductPurchaseListPicOrCasesDistributorEntry(DistribtrId_Global, DistributorNodeType_Global);

        if (hmapPrdctOdrQty != null && hmapPrdctOdrQty.size() > 0) {
            for (Map.Entry<String, String> entry : hmapPrdctOdrQty.entrySet()) {
                if (Integer.parseInt(entry.getValue()) > 0) {
                    prdctModelArrayList.setPrdctQty(entry.getKey(), entry.getValue());


                    Double otqy=Double.parseDouble(entry.getValue().toString());

                    if(hmapProductTblUOMMappingForCaseConversion!=null && hmapProductTblUOMMappingForCaseConversion.size()>0 && hmapProductTblUOMMappingForCaseConversion.containsKey(Integer.parseInt(entry.getKey().toString())))
                    {
                        List<TblUOMMapping> tblUOMMappings= hmapProductTblUOMMappingForCaseConversion.get(Integer.parseInt(entry.getKey().toString()));
                        otqy=otqy/tblUOMMappings.get(0).getRelConversionUnits();

                    }


                    prdctModelArrayList.setPrdctQtyToShow(entry.getKey(), ""+(int)(otqy.intValue()));
                }

            }

        }


        if (hmapflgPicsOrCases != null && hmapflgPicsOrCases.size() > 0) {
            for (Map.Entry<String, String> entry : hmapflgPicsOrCases.entrySet()) {
                if (Integer.parseInt(entry.getValue()) > 0) {
                    prdctModelArrayList.setPrdctQtyMappedToPicsOrCases(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public void getDataFromatabase() {
        getCategoryDetail();
        getPrductInfoDetail();


        if (defaultCatName_Id.contains("^")) {
            ed_search.setText("");//defaultCatName_Id.split(Pattern.quote("^"))[0]


            searchProduct(defaultCatName_Id.split(Pattern.quote("^"))[0], 0);
//            searchProduct(defaultCatName_Id.split(Pattern.quote("^"))[0], defaultCatName_Id.split(Pattern.quote("^"))[1], 0, 0);

        }


        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.toString().trim().length() == 0) {

                } else {
                    searchProduct(ed_search.getText().toString().trim(), 0);
//                    searchProduct(ed_search.getText().toString().trim(), "0", 0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    String SelectedCategoryForSchemeFileter = "All";
    int SelectedCategoryIDForSchemeFileter = 0;

    public void searchProduct(String filterSearchText, int ParentID) {
        tblStoreProductMappingListForDisplay.clear();

        SelectedCategoryForSchemeFileter = "All";
        SelectedCategoryIDForSchemeFileter = 0;

        tblStoreProductMappingListtemp.clear();


        List<String> searchStringsArray = new ArrayList<>();
        if (filterSearchText.contains(",")) {
            searchStringsArray.addAll(Arrays.asList(filterSearchText.toLowerCase().split(Pattern.quote(","))));
        }
        if (searchStringsArray.size() == 0) {
           /* if (SelectedCategoryForSchemeFileter.equals("All")) {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase())).toList();
            } else {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase()) && p.getCatID() == ParentID).toList();
            }*/
            if (filterSearchText.equals("All") || filterSearchText.equals("")) {
                tblStoreProductMappingListtemp.addAll(tblStoreProductMappingList);
                // tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase())).toList();
            } else {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase()) && p.getCatID() == ParentID).toList();
            }
        } else {
            List<TblStoreProductMappingForDisplay> tblStoreProductMappingListtempFilter = new ArrayList<>();

            if (SelectedCategoryForSchemeFileter.equals("All")) {
                for (int i = 0; i < tblStoreProductMappingListtemp.size(); i++) {
                    TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay = tblStoreProductMappingList.get(i);
                    for (String s : searchStringsArray) {
                        if (tblStoreProductMappingForDisplay.getSearchField().toLowerCase().contains(s.toLowerCase())) {
                            tblStoreProductMappingListtempFilter.add(tblStoreProductMappingForDisplay);
                        }
                    }
                }
            } else {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingListtemp).where(p -> p.getCatID() == ParentID).toList();
                for (int i = 0; i < tblStoreProductMappingListtemp.size(); i++) {
                    TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay = tblStoreProductMappingList.get(i);
                    for (String s : searchStringsArray) {
                        if (tblStoreProductMappingForDisplay.getSearchField().toLowerCase().contains(s.toLowerCase())) {
                            tblStoreProductMappingListtempFilter.add(tblStoreProductMappingForDisplay);
                        }
                    }
                }
            }
            tblStoreProductMappingListtemp.clear();
            tblStoreProductMappingListtemp.addAll(tblStoreProductMappingListtempFilter);
        }


        Iterator<TblStoreProductMappingForDisplay> crunchifyIteratornew = tblStoreProductMappingListtemp.iterator();
        if (tblStoreProductMappingListtemp != null && tblStoreProductMappingListtemp.size() > 0) {

            while (crunchifyIteratornew.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIteratornew.next();
                // if (tblStoreProductMappingRecord.getFlgBaseProduct() == 0 && tblStoreProductMappingRecord.getSBDGroupID() == 0 && tblStoreProductMappingRecord.getSBDPrdCount() == 0) {
                tblStoreProductMappingListForDisplay.add(tblStoreProductMappingRecord);
                // }
            }
        }


        fnMarkFirstProductCategoryRowVisible();
        orderAdapter.updateList(tblStoreProductMappingListForDisplay);

    }

    public void fnMarkFirstProductCategoryRowVisible() {

        tblStoreProductMappingListForDisplay.clear();

        Iterator<TblStoreProductMappingForDisplay> crunchifyIterator = tblStoreProductMappingListtemp.iterator();
        if (tblStoreProductMappingListtemp != null && tblStoreProductMappingListtemp.size() > 0) {
            int catid = 0;
            Set catSet = new HashSet();
            while (crunchifyIterator.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIterator.next();
                if (!catSet.contains(tblStoreProductMappingRecord.getCatID())) {
                    tblStoreProductMappingRecord.setFlgShowCategoryHeader(1);
                    tblStoreProductMappingRecord.setFlgMakeFrameVisible(1);
                    tblStoreProductMappingRecord.setFlgMakeRowVisible(1);
                    catSet.add(tblStoreProductMappingRecord.getCatID());
                } else {
                    tblStoreProductMappingRecord.setFlgShowCategoryHeader(0);
                    tblStoreProductMappingRecord.setFlgMakeFrameVisible(1);
                    tblStoreProductMappingRecord.setFlgMakeRowVisible(1);
                }

            }
        }
        Iterator<TblStoreProductMappingForDisplay> crunchifyIteratornew = tblStoreProductMappingListtemp.iterator();
        if (tblStoreProductMappingListtemp != null && tblStoreProductMappingListtemp.size() > 0) {

            while (crunchifyIteratornew.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIteratornew.next();
                tblStoreProductMappingListForDisplay.add(tblStoreProductMappingRecord);
            }
        }
    }

    private void getDataFromIntent() {
        Intent i = getIntent();
        imei = AppUtils.getToken(this);
        fDate = i.getStringExtra("fDate");
        from = i.getStringExtra("From");
    }

    private void initializeFields() {

        getDataFromIntent();


        if (sharedPref.contains("CoverageAreaNodeID")) {
            if (sharedPref.getInt("CoverageAreaNodeID", 0) != 0) {
                CommonInfo.CoverageAreaNodeID = sharedPref.getInt("CoverageAreaNodeID", 0);
                CommonInfo.CoverageAreaNodeType = sharedPref.getInt("CoverageAreaNodeType", 0);
            }
        }
        if (sharedPref.contains("SalesmanNodeId")) {
            if (sharedPref.getInt("SalesmanNodeId", 0) != 0) {
                CommonInfo.SalesmanNodeId = sharedPref.getInt("SalesmanNodeId", 0);
                CommonInfo.SalesmanNodeType = sharedPref.getInt("SalesmanNodeType", 0);
            }
        }
        if (sharedPref.contains("flgDataScope")) {
            if (sharedPref.getInt("flgDataScope", 0) != 0) {
                CommonInfo.flgDataScope = sharedPref.getInt("flgDataScope", 0);

            }
        }
        if (sharedPref.contains("flgDSRSO")) {
            if (sharedPref.getInt("flgDSRSO", 0) != 0) {
                CommonInfo.FlgDSRSO = sharedPref.getInt("flgDSRSO", 0);

            }
        }

        rvProducts = findViewById(R.id.rvProducts);

        img_ctgry = (ImageView) findViewById(R.id.img_ctgry);
        img_back_Btn = (ImageView) findViewById(R.id.img_back_Btn);
        ed_search = (EditText) findViewById(R.id.ed_search);


        ll_forTableHeaderName = (LinearLayout) findViewById(R.id.ll_forTableHeaderName);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_skip = (Button) findViewById(R.id.btn_skip);

        spinner_for_filter = (Spinner) findViewById(R.id.spinner_for_filter);

        img_ctgry.setOnClickListener(view -> {
            customAlertStoreList(categoryNames, "Filter");
        });
        img_back_Btn.setOnClickListener(view -> {
//                saveDistributorStockInTable();
            Intent i = new Intent(DistributorCheckInSecondActivity.this, AllButtonActivity.class);
            i.putExtra("token", imei);
            i.putExtra("fDate", fDate);
            startActivity(i);
            finish();
        });

//        btn_save.setOnClickListener(this);
//        ed_search.setOnClickListener(this);


        if (from.equals("DayStart")) {
            img_back_Btn.setVisibility(View.GONE);
            if (CommonInfo.hmapAppMasterFlags.get("flgDBRStockCanSkipFillInDayStart") != null) {
                if ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockCanSkipFillInDayStart") == 1)) {
                    btn_skip.setVisibility(View.VISIBLE);
                } else {
                    btn_skip.setVisibility(View.GONE);
                }
            }

        } else {
            btn_skip.setVisibility(View.GONE);
        }

        btn_skip.setOnClickListener(v -> {
            // finish();
            Intent i = new Intent(DistributorCheckInSecondActivity.this, AllButtonActivity.class);
            i.putExtra("token", imei);
            i.putExtra("fDate", fDate);
            startActivity(i);
            finish();
        });
        txt_stockDate = (TextView) findViewById(R.id.txt_stockDate);

        fnGetDistributorList();

        spinner_for_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView) view;
                if(tv!=null) {
                    String text = tv.getText().toString();

                    if (text.equals("Select Distributor")) {
                        ll_forTableHeaderName.setVisibility(View.GONE);
                        btn_save.setVisibility(View.GONE);
                        txt_stockDate.setVisibility(View.GONE);

                    } else {
                        prdctModelArrayList = new ProductFilledDataModel();

                        txt_stockDate.setVisibility(View.GONE);
                        btn_save.setVisibility(View.VISIBLE);
                        ll_forTableHeaderName.setVisibility(View.GONE);
                        String Distribtor_Detail = mDataSource.fetchSuplierIdByName(text);

                        int StrDistribtrId_Global = Integer.parseInt(Distribtor_Detail.split(Pattern.quote("^"))[0]);
                        int StrDistributorNodeType_Global = Integer.parseInt(Distribtor_Detail.split(Pattern.quote("^"))[1]);

                        DistribtrId_Global = StrDistribtrId_Global;
                        DistributorNodeType_Global = StrDistributorNodeType_Global;

                        int check = mDataSource.countDataIntblDistributorSavedData(StrDistribtrId_Global, StrDistributorNodeType_Global, 3);
                        if (check == 0) {
                            if (isOnline()) {
                                mDataSource.deleteDistributorStockTblesOnDistributorIDBasic(StrDistribtrId_Global, StrDistributorNodeType_Global);
                                CommonFunction.getDistributorTodayStock(DistributorCheckInSecondActivity.this, AppUtils.getToken(DistributorCheckInSecondActivity.this), DistribtrId_Global, DistributorNodeType_Global);
                            } else {
                                showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
                            }
                            fnGetSavedDataFromPDA();
                        } else {

                            fnGetSavedDataFromPDA();
                            ll_forTableHeaderName.setVisibility(View.VISIBLE);
                            txt_stockDate.setVisibility(View.VISIBLE);
                        }

                        btn_save.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_save.setOnClickListener(v -> {
           // if (isOnline()) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(getText(R.string.genTermInformation));
                dialog.setMessage(getText(R.string.SubmitDistrbtrStck));
                dialog.setPositiveButton(getText(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            LinkedHashMap<String, String> hmapPrdctOrderQty = prdctModelArrayList.getHmapPrdctOrderQty();
                            if (hmapPrdctOrderQty != null && hmapPrdctOrderQty.size()>0)
                            {
                                saveDistributorStockInTable();

                                mDataSource.deletetblIsDBRStockSubmitted();
                                mDataSource.savetblIsDBRStockSubmitted(1);
                                FullSyncDataNow task = new FullSyncDataNow(DistributorCheckInSecondActivity.this);
                                task.execute();
                            }
                            else
                            {
                                showAlertSingleButtonInfo("Can not submit the data without any stock entry.");
                            }

                        } catch (Exception e) {

                        }


                    }
                });
                dialog.setNegativeButton(getText(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = dialog.create();
                alert.show();
           /* } else {
                FullSyncDataNow task = new FullSyncDataNow(DistributorCheckInSecondActivity.this);
                task.execute();
            }*/
        });
    }


    public void saveDistributorStockInTable() {

        try {
            LinkedHashMap<String, String> hmapPrdctOrderQty = prdctModelArrayList.getHmapPrdctOrderQty();
            String distributorNodeIdNodeType = "0";
            mDataSource.Delete_tblDistributorSavedData(DistribtrId_Global, DistributorNodeType_Global);
            mDataSource.saveSuplierDetail("" + DistribtrId_Global, "" + DistributorNodeType_Global, 3);
            if (hmapPrdctOrderQty != null && hmapPrdctOrderQty.size()>0) {
                List<TblDistributorProductStock> listtblDistributorProductStock = new ArrayList<TblDistributorProductStock>();
                for (Map.Entry<String, String> entry : hmapPrdctOrderQty.entrySet()) {


                    String ProductID = entry.getKey();
                    String prdctQty = "" + ((int) (Double.parseDouble(entry.getValue())));
                    TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay=stream(tblStoreProductMappingList).where(p->p.getPrdNodeID()==Integer.parseInt(ProductID)).first();

                    int DistID = DistribtrId_Global;
                    int DistNodeType = DistributorNodeType_Global;
                    String header = tblStoreProductMappingForDisplay.getCategoryDesc();
                    String Short_name = tblStoreProductMappingForDisplay.getPrdName();
                    int EntryType = 1;
                    int uomID = 1;
                    if (prdctModelArrayList != null && prdctModelArrayList.getHmapflgPicsOrCases().containsKey(ProductID)) {
                        uomID = Integer.parseInt(prdctModelArrayList.getHmapflgPicsOrCases().get(ProductID));
                    }
                    String Et_value = prdctModelArrayList.getHmapOrderQtyDataToShow().get(ProductID);

                    String ProductNodeTypeStockDate = mDataSource.fetchProductNodeTypeByID(Integer.parseInt(ProductID), DistID, DistNodeType);

                    int ProductNodeType = Integer.parseInt(ProductNodeTypeStockDate.split(Pattern.quote("^"))[0].toString().trim());
                    String StockDate = ProductNodeTypeStockDate.split(Pattern.quote("^"))[1].toString().trim();


                    if (!TextUtils.isEmpty(prdctQty)) {
                        TblDistributorProductStock tblDistributorProductStock = new TblDistributorProductStock();
                        distributorNodeIdNodeType = "0-" + DistID + "-" + DistNodeType;
                        tblDistributorProductStock.setCustomer("0-" + DistID + "-" + DistNodeType);
                        tblDistributorProductStock.setProductNodeID(Integer.parseInt(ProductID));
                        tblDistributorProductStock.setProductNodeType(0);
                        tblDistributorProductStock.setsKUName(Short_name);
                        tblDistributorProductStock.setStockDate(stockDate);
                        tblDistributorProductStock.setBuomID(uomID);
                        tblDistributorProductStock.setStockQty(Integer.parseInt(prdctQty));
                        listtblDistributorProductStock.add(tblDistributorProductStock);
                    }

                    mDataSource.savetblDistributorSavedData(header, Short_name, ProductID, stockDate, prdctQty, DistID, DistNodeType, ProductNodeType, StockDate, 3, EntryType, 1, uomID);
                }

                if ((listtblDistributorProductStock != null) && (listtblDistributorProductStock.size() > 0)) {
                    mDataSource.deleteDistributorStock(distributorNodeIdNodeType);
                    mDataSource.inserttblDistributorProductStock(listtblDistributorProductStock);
                }
            }

          /*  if (tblStoreProductMappingList.size() > 0) {

              *//*  String distributorNodeIdNodeType = "0";
                mDataSource.Delete_tblDistributorSavedData(DistribtrId_Global, DistributorNodeType_Global);
*//*

                mDataSource.saveSuplierDetail("" + DistribtrId_Global, "" + DistributorNodeType_Global, 3);
                for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : tblStoreProductMappingList) {
//                    String key = entry.getKey();
//                    String tag = key.split(Pattern.quote("^"))[0].toString().trim();
                    //Tag=productId"+"MnthName
                    String pID = "" + tblStoreProductMappingForDisplay.getPrdNodeID();
//                    String Date = tag.split(Pattern.quote("_"))[1];

                }

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fnGetDistributorList() {
        //mDataSource.open();
        Distribtr_list = mDataSource.getSuplierData();
        //mDataSource.close();
        for (int i = 0; i < Distribtr_list.length; i++) {
            //System.out.println("DISTRIBUTOR........"+Distribtr_list[i]);
            String value = Distribtr_list[i];
            DbrNodeId = value.split(Pattern.quote("^"))[0];
            DbrNodeType = value.split(Pattern.quote("^"))[1];
            DbrName = value.split(Pattern.quote("^"))[2];
            DbrArray.add(DbrName);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DistributorCheckInSecondActivity.this, R.layout.initial_spinner_text, DbrArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_for_filter.setAdapter(adapter);
        if (!DbrArray.isEmpty()) {
            if (DbrArray.size() == 2) {


                DistribtrId_Global = Integer.parseInt(Distribtr_list[1].split(Pattern.quote("^"))[0]);
                DistributorNodeType_Global = Integer.parseInt(Distribtr_list[1].split(Pattern.quote("^"))[1]);

                int check = mDataSource.countDataIntblDistributorSavedData(DistribtrId_Global, DistributorNodeType_Global, 3);
                if (check == 0) {
                    if (isOnline()) {
                        mDataSource.deleteDistributorStockTblesOnDistributorIDBasic(DistribtrId_Global, DistributorNodeType_Global);
                        spinner_for_filter.setSelection(1);
                        // GetDistributorStockEntryData getData= new GetDistributorStockEntryData();
                        //getData.execute();
                    } else {
                        spinner_for_filter.setSelection(0);
                        showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
                    }
                } else {
                    spinner_for_filter.setSelection(1);
                    //  fnGetSavedDataFromPDA();
                    //  ll_forSearchBox.setVisibility(View.GONE);
                    ll_forTableHeaderName.setVisibility(View.VISIBLE);
                    // fnToAddRows();
                }


            }

        }
    }

    public void customAlertStoreList(List<String> listOption, String sectionHeader) {

        final Dialog listDialog = new Dialog(DistributorCheckInSecondActivity.this);
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listDialog.setContentView(R.layout.search_list);
        listDialog.setCanceledOnTouchOutside(false);
        listDialog.setCancelable(false);
        WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
        parms.gravity = Gravity.CENTER;
        //there are a lot of settings, for dialog, check them all out!
        parms.dimAmount = (float) 0.5;


        TextView txt_section = listDialog.findViewById(R.id.txt_section);
        txt_section.setText(sectionHeader);
        TextView txtVwCncl = listDialog.findViewById(R.id.txtVwCncl);

        final ListView list_store = listDialog.findViewById(R.id.list_store);
        final CardArrayAdapterCategory2 cardArrayAdapter = new CardArrayAdapterCategory2(DistributorCheckInSecondActivity.this, listOption, listDialog, previousSlctdCtgry, 0);

        //img_ctgry.setText(previousSlctdCtgry);


        list_store.setAdapter(cardArrayAdapter);
        //	editText.setBackgroundResource(R.drawable.et_boundary);
        img_ctgry.setEnabled(true);


        txtVwCncl.setOnClickListener(v -> {
            listDialog.dismiss();
            img_ctgry.setEnabled(true);
        });


        //now that the dialog is set up, it's time to show it
        listDialog.show();

    }

    @Override
    public void fcsLstCld(boolean hasFocus, EditText editText) {
        AppUtils.hideKeyBoard(editText, this);


        mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
        mCustomKeyboardNum.hideCustomKeyboard();
        if (!hasFocus) {

        }
        else
        {
            if (editText.getTag().toString().contains("etOrderQty")) {
                if (editText.getTag().toString().contains("etOrderQty")) {
                    mCustomKeyboardNumWithoutDecimal.registerEditText(editText);
                    mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(editText);
                }

            } else {


            }
        }
    }

    @Override
    public void fcsLstCldOnFocus(boolean hasFocus, EditText editText) {

    }

    @Override
    public void selectedOption(String selectedCategory, Dialog dialog) {
        dialog.dismiss();
        previousSlctdCtgry = selectedCategory;
        String lastTxtSearch = ed_search.getText().toString().trim();
        //img_ctgry.setText(selectedCategory);
        ed_search.setText(previousSlctdCtgry);
        if (hmapctgry_details.containsKey(selectedCategory)) {
            int patID = Integer.parseInt(hmapctgry_details.get(selectedCategory));
            searchProduct(selectedCategory, patID);
//            searchProduct(selectedCategory,hmapctgry_details.get(selectedCategory),0,patID);
        } else {
            searchProduct(selectedCategory, 0);
//            searchProduct(selectedCategory,"0",0,0);
        }


    }

    @Override
    public void selectedOption(String selectedCategory, Dialog dialog, int flgCompanyCompetitorProducts) {

    }

    private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialogGetStores;

        public FullSyncDataNow(DistributorCheckInSecondActivity activity) {
            if (pDialog2STANDBY != null) {
                if (pDialog2STANDBY.isShowing()) {
                    pDialog2STANDBY.dismiss();
                }
            }
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage("Submitting Distributor Entry Details...");
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();


        }

        @Override

        protected Void doInBackground(Void... params) {

            int Outstat = 3;

            long syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String StampEndsTime = df.format(dateobj);


            //mDataSource.open();
            String presentRoute = mDataSource.GetActiveRouteIDForDistributor();
            //mDataSource.close();


            SimpleDateFormat df1 = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss", Locale.ENGLISH);

            newfullFileName = imei + "." + presentRoute + "." + df1.format(dateobj);


            try {


                File MeijiDistributorEntryXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

                if (!MeijiDistributorEntryXMLFolder.exists()) {
                    MeijiDistributorEntryXMLFolder.mkdirs();

                }


            } catch (Exception e) {

                e.printStackTrace();
                if (pDialogGetStores.isShowing()) {
                    pDialogGetStores.dismiss();
                }
            }
            return null;
        }

        @Override
        protected void onCancelled() {

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialogGetStores.isShowing()) {
                pDialogGetStores.dismiss();
            }

            try {

                  /*  task2 = new SyncXMLfileData(DistributorCheckInSecondActivity.this);
                    task2.execute();*/
                String routeID = mDataSource.GetActiveRouteIDSunil();
                Intent mMyServiceIntent = new Intent(DistributorCheckInSecondActivity.this, SyncJobService.class);  //is any of that needed?  idk.
                mMyServiceIntent.putExtra("routeID", routeID);//
                mMyServiceIntent.putExtra("storeID", "0");
                mMyServiceIntent.putExtra("whereTo", "Regular");//
                mMyServiceIntent.putExtra("StoreVisitCode", "NA");//
                mMyServiceIntent.putExtra("tmpInvoicePDACode", "NA");//

                SyncJobService.enqueueWork(DistributorCheckInSecondActivity.this, mMyServiceIntent);

            } catch (Exception e) {

            }

            try {
                int checkNoFiles = mDataSource.counttblDistributorSavedData();
                if (checkNoFiles == 1) {
                    showNoConnAlertforLocalDataSaved();
                } else {
                    if (from.equals("DayStart")) {
                        if ((CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1)) {
                            Intent intent = new Intent(DistributorCheckInSecondActivity.this, WarehouseCheckInSecondActivity.class);
                            intent.putExtra("token", imei);
                            intent.putExtra("fDate", fDate);
                            intent.putExtra("From", "DayStart");
                            startActivity(intent);
                            finish();
                        } else {
                            Intent i = new Intent(DistributorCheckInSecondActivity.this, AllButtonActivity.class);
                            i.putExtra("token", imei);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Intent i = new Intent(DistributorCheckInSecondActivity.this, AllButtonActivity.class);
                        i.putExtra("token", imei);
                        startActivity(i);
                        finish();
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    public void showNoConnAlertforLocalDataSaved() {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DistributorCheckInSecondActivity.this);
        alertDialogNoConn.setTitle(R.string.genTermNoDataConnection);
        alertDialogNoConn.setMessage(R.string.genlocaldataMsg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(DistributorCheckInSecondActivity.this, LauncherActivity.class);
                        i.putExtra("token", imei);

                        startActivity(i);
                        finish();
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }

    public void fnGetSavedDataFromPDA() {
        tblStoreProductMappingList = mDataSource.fnGetTblStoreProductMappingForSearchDistributor(DistribtrId_Global, DistributorNodeType_Global);
        hmapProductTblUOMMappingForCaseConversion=mDataSource.fetch_ProductWiseGetAllMeasureUnitAgainstProductForDistributorStockEntryPage("0");
        for (TblStoreProductMappingForDisplay tblStoreProductMapping : tblStoreProductMappingList) {
            if (hmapflgPicsOrCases != null && hmapflgPicsOrCases.size() > 0) {
                if (hmapflgPicsOrCases.containsKey("" + tblStoreProductMapping.getPrdNodeID())) {
                    List<TblUOMMaster> tblUOMMasterListAgainstProduct = tblStoreProductMapping.getTblUOMMasterList();
                    for (TblUOMMaster tblUOMMaster : tblUOMMasterListAgainstProduct) {
                        tblUOMMaster.setFlgSelected(0);
                        if (tblUOMMaster.getBUOMID() == Integer.parseInt(hmapflgPicsOrCases.get("" + tblStoreProductMapping.getPrdNodeID()))) {
                            tblUOMMaster.setFlgSelected(1);
                            prdctModelArrayList.setPrdctQtyMappedToPicsOrCases("" + tblStoreProductMapping.getPrdNodeID(), "" + tblUOMMaster.getBUOMID());
                            break;
                        }
                    }
                }
            }
        }
        getPrductInfoDetail();

        tblStoreProductMappingListtemp.clear();
        tblStoreProductMappingListtemp.addAll(stream(tblStoreProductMappingList).toList());
        tblStoreProductMappingListtemp = stream(tblStoreProductMappingListtemp).orderBy(p -> p.getCatID()).toList();

        fnMarkFirstProductCategoryRowVisible();
        orderAdapter = new OrderAdapterDistributor(this, prdctModelArrayList, tblStoreProductMappingList,hmapProductTblUOMMappingForCaseConversion);
        rvProducts.setAdapter(orderAdapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        HmapGetPDAdata = mDataSource.fetchtblDistribtrSavedData(DistribtrId_Global, DistributorNodeType_Global);

        for (Map.Entry<String, String> entry : HmapGetPDAdata.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("RETRIEVE DATA :" + key + "  " + value);
        }

        if (HmapGetPDAdata.size() > 0) {
            stockDate = mDataSource.getDistinctStockDate();
            txt_stockDate.setText(getText(R.string.StockAsOn) + stockDate);


        }

    }

    String stockDate = "";

    @Override
    public void success() {
//        HmapDistribtrReport = mDataSource.fetchtblDistribtrReport(DistribtrId_Global, DistributorNodeType_Global);

//        DistribtrReportColumnDesc = mDataSource.fetchtblDistribtrReportColumnDesc(DistribtrId_Global, DistributorNodeType_Global);
        fnGetSavedDataFromPDA();
//        ll_forSearchBox.setVisibility(View.GONE);
        ll_forTableHeaderName.setVisibility(View.VISIBLE);
        txt_stockDate.setVisibility(View.VISIBLE);
        //	LLparentOfInPcsCs.setVisibility(View.VISIBLE);

//        fnToAddRows();

        String stockDate = mDataSource.getDistinctStockDate();
        // txt_stockDate.setText("Stock as on- "+stockDate);
        txt_stockDate.setText(getText(R.string.StockAsOn) + stockDate);

//        if (lLayout_main.getChildCount() > 0) {
        btn_save.setVisibility(View.VISIBLE);
//        } else {
//            btn_save.setVisibility(View.GONE);
//        }
    }

    @Override
    public void failure() {
        showAlertSingleButtonError("Error while fetching data.");
    }

}
