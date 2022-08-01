package com.astix.rajtraderssfasales;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allana.truetime.TimeUtils;
import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.adapter.CardArrayAdapterCategory2;
import com.astix.rajtraderssfasales.adapter.CustomVisitStockAdapter;
import com.astix.rajtraderssfasales.adapter.CustomVisitStockAdapterMandotry;
import com.astix.rajtraderssfasales.merchandizing.PicAfterMerchandising;
import com.astix.rajtraderssfasales.merchandizing.PicBeforeMerchandising;
import com.astix.rajtraderssfasales.model.ActualVisitProductInfo;
import com.astix.rajtraderssfasales.model.TblUOMMapping;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.RandomString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ActualVisitStock extends BaseActivity implements CategoryCommunicator, CustomVisitStockAdapter.EditTextClickListener,CustomVisitStockAdapterMandotry.EditTextClickListenerMandatory,focusLostCalled {//, InterfaceContactUpdate

    View viewContactSection;
    LinearLayout ll_ContactUpdateSection;
    TextView tvContactNoCorrection;
    Button btnEditContact;
    HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMapping;// = new HashMap<Integer, List<TblUOMMapping>>();
    String[] UOM_names = null;
    private int flgIsPicsAllowed = 1;
    String storeVisitCode = "";
    String storePics = "";
    private static final String TAG = ActualVisitStock.class.getSimpleName();
    public EditText ed_search, ed_searchSelfOtherProducts;
    public ImageView btn_go, btn_goSelfOtherProduct;
    public int flgCompanyCompetitorProducts = 0;
    public String storeID;
    public String imei;
    public String date;
    public String pickerDate;
    public String selStoreName;
    public Dialog dialog = null;
    public int StoreCurrentStoreType = 0;
    LinearLayout lLayout_main, ll_CompleteSelfOtherProductSection, ll_CompanyPrdHeader;
    Button btnNext;
    CustomKeyboard customKeyboard;
    CustomKeyboard customKeyboard2;
    int isStockAvlbl = 0;
    int isCmpttrAvlbl = 0;
    List<String> categoryNames;
    int progressBarStatus = 0;
    LinkedHashMap<String, String> hmapctgry_details;// = new LinkedHashMap<String, String>();
    ImageView img_ctgry, img_ctgrySelfOtherProducts;
    String previousSlctdCtgry = "";
    LinkedHashMap<String, String> hmapFilterProductListCompany = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapFilterProductListCompetitor = new LinkedHashMap<String, String>();
    LinkedHashMap<String, ActualVisitProductInfo> hmapFetchPDASavedDataForDefaultData;// = new LinkedHashMap<String, ActualVisitProductInfo>();
    LinkedHashMap<String, ActualVisitProductInfo> hmapFetchPDASavedDataForFilteredData;// = new LinkedHashMap<String, ActualVisitProductInfo>();
    private View divider;
    private TextView tvNoOtherPrd, tvNoMandatoryPrd;
    private LinearLayout ll_forTableHeaderNameSelfOtherProducts;
    private RecyclerView defaultProductRV, filteredProductRV;
    private String storeVistCode;
    private ArrayList<ActualVisitProductInfo> filteredProductList;// = new ArrayList<>();
    private ArrayList<ActualVisitProductInfo> defaultProductInfoArrayList;// = new ArrayList<>();
    private CustomVisitStockAdapter visitStockAdapter;
    private CustomVisitStockAdapterMandotry visitStockAdapterMandatory;

    public int RegionID = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
          //  customKeyboard.hideCustomKeyboard();
            return false;

        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {

        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_visit_stock);
        System.out.println("Actual visit Step 0 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
        hmapProductTblUOMMapping = new HashMap<Integer, List<TblUOMMapping>>();
        hmapctgry_details = new LinkedHashMap<String, String>();
       hmapFetchPDASavedDataForDefaultData = new LinkedHashMap<String, ActualVisitProductInfo>();
       hmapFetchPDASavedDataForFilteredData = new LinkedHashMap<String, ActualVisitProductInfo>();
        filteredProductList = new ArrayList<>();
       defaultProductInfoArrayList = new ArrayList<>();
        getDataFromIntent();
        System.out.println("Actual visit Step 1 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
        customKeyboard = new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num);
        customKeyboard2 = new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        System.out.println("Actual visit Step 2 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

        try {
            initializeallViews();
        }
        catch (Exception ex)
        {
            fnWriteLogFileHere(ex.toString());
        }

        System.out.println("Actual visit Step 3 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

        try {
            fetchDataFromDatabase();
        }
        catch (Exception ex)
        {
            fnWriteLogFileHere(ex.toString());
        }


      //  int i=1/0;
    }

    public String getStoreVisitCode() {

        String IMEINo = AppUtils.getToken(this);

        storeVisitCode = mDataSource.fnGetStoreVisitCode(storeID);
        if (storeVisitCode.equals("NA")) {
            storeVisitCode = genStoreVisitCode();
            String Comments = "NA";
           /* locationRetreivingGlobal = new LocationRetreivingGlobal();
            locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(PicBeforeMerchandising.this, true, true, 20, 0);
*/

        }


        return storeVisitCode;
    }

    public String genStoreVisitCode() {
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

      /*  String cxz;
        cxz = UUID.randomUUID().toString();*/
        RandomString cxz = new RandomString();
        String randomString=cxz.nextString();
        //  StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");
        String VisitStartTS ="";// TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);
      /*  if(VisitStartTS.equals("") || VisitStartTS.equals("NA"))
        {*/
        VisitStartTS = df.format(new Date());
        //}
     /*   String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();*/
        imei = AppUtils.getToken(this);
        String IMEIid = imei.substring(9);
        String  sdcxz = "StoreVisitCode" + "-" + IMEIid + "-" + randomString + "-" + VisitStartTS.replace(" ", "").replace(":", "").trim();
        return sdcxz;

    }

    public void initializeallViews() {


        ll_ContactUpdateSection = findViewById(R.id.ll_ContactUpdateSection);
        inflateUpdateContactSectionLayout();
        inflateUpdateContactSection();


        tvNoMandatoryPrd = findViewById(R.id.tvNoMandatoryPrd);
        tvNoOtherPrd = findViewById(R.id.tvNoOtherPrd);
        divider = findViewById(R.id.divider);
        defaultProductRV = findViewById(R.id.default_products_recycler_view);
        RecyclerView.LayoutManager layoutManagerForDefaultRV = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        defaultProductRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        defaultProductRV.setLayoutManager(layoutManagerForDefaultRV);

        filteredProductRV = findViewById(R.id.filtered_products_recycler_view);
        RecyclerView.LayoutManager layoutManagerForFilteredRV = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        filteredProductRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        filteredProductRV.setLayoutManager(layoutManagerForFilteredRV);

        lLayout_main = findViewById(R.id.lLayout_main);
        // ll_SelfOtherProducts = (LinearLayout) findViewById(R.id.ll_SelfOtherProducts);
        ImageView img_back_Btn = findViewById(R.id.img_back_Btn);
        btnNext = findViewById(R.id.btnNext);
        ll_CompleteSelfOtherProductSection = findViewById(R.id.ll_CompleteSelfOtherProductSection);
        ll_forTableHeaderNameSelfOtherProducts = findViewById(R.id.ll_forTableHeaderNameSelfOtherProducts);
        ll_CompanyPrdHeader = findViewById(R.id.ll_CompanyPrdHeader);
        img_ctgry = findViewById(R.id.img_ctgry);
        ed_search = findViewById(R.id.ed_search);
        btn_go = findViewById(R.id.btn_go);


        img_ctgrySelfOtherProducts = findViewById(R.id.img_ctgrySelfOtherProducts);
        ed_searchSelfOtherProducts = findViewById(R.id.ed_searchSelfOtherProducts);

        ed_searchSelfOtherProducts.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                customKeyboard.hideCustomKeyboard();
            }
        });

        btn_goSelfOtherProduct = findViewById(R.id.btn_goSelfOtherProduct);

        img_ctgry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_ctgry.setEnabled(false);
                // flgCompanyCompetitorProducts = 0;
                customAlertStoreList(categoryNames, "Select Category");
            }
        });


        img_ctgrySelfOtherProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //img_ctgrySelfOtherProducts.setEnabled(false);
                flgCompanyCompetitorProducts = 1;
                customAlertStoreList(categoryNames, "Select Competitor Category");
            }
        });
        btn_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(ed_search.getText().toString().trim())) {

                    if (!ed_search.getText().toString().trim().equals("")) {
                        searchProduct(ed_search.getText().toString().trim(), "", 0, false);
                    }


                } else {
                    searchProduct("All", "", 0, false);
                }

            }


        });


        btn_goSelfOtherProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(ed_searchSelfOtherProducts.getText().toString().trim())) {

                    if (!ed_searchSelfOtherProducts.getText().toString().trim().equals("")) {
                        searchProduct(ed_searchSelfOtherProducts.getText().toString().trim(), "", 1, false);

                    }


                } else {
                    searchProduct("All", "", 1, false);
                }

            }


        });
        img_back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent fireBackDetPg = new Intent(ActualVisitStock.this, LastVisitDetails.class);
                fireBackDetPg.putExtra("storeID", storeID);
                fireBackDetPg.putExtra("SN", selStoreName);
                fireBackDetPg.putExtra("bck", 1);
                fireBackDetPg.putExtra("token", imei);
                fireBackDetPg.putExtra("userdate", date);
                fireBackDetPg.putExtra("pickerDate", pickerDate);
                fireBackDetPg.putExtra("flgOrderType", 1);
                //fireBackDetPg.putExtra("rID", routeID);
                startActivity(fireBackDetPg);
                finish();*/

                Intent fireBackDetPg = new Intent(ActualVisitStock.this, PicBeforeMerchandising.class);
                fireBackDetPg.putExtra("storeID", storeID);
                fireBackDetPg.putExtra("SN", selStoreName);
                fireBackDetPg.putExtra("bck", 1);
                fireBackDetPg.putExtra("imei", imei);
                fireBackDetPg.putExtra("userdate", date);
                fireBackDetPg.putExtra("pickerDate", pickerDate);
                fireBackDetPg.putExtra("flgOrderType", 1);
                //fireBackDetPg.putExtra("rID", routeID);
                startActivity(fireBackDetPg);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStockFilledProperly()) {
                    mDataSource.deleteActualVisitData(storeID);

                    Log.d(TAG, "UpdatedDefaultData : " + hmapFetchPDASavedDataForDefaultData.toString());
                    if (hmapFetchPDASavedDataForDefaultData != null && hmapFetchPDASavedDataForDefaultData.size() > 0) {
                        for (Map.Entry<String, ActualVisitProductInfo> entry : hmapFetchPDASavedDataForDefaultData.entrySet()) {

                            ActualVisitProductInfo visitProductInfo = entry.getValue();
                            mDataSource.saveTblActualVisitStock(storeID, visitProductInfo.getProductId(), visitProductInfo.getStock(), 1, 1, visitProductInfo.getDisplayUnit(), storeVistCode);
                        }
                    }

                    Log.d(TAG, "UpdatedFilteredData : " + hmapFetchPDASavedDataForFilteredData.toString());
                    if (hmapFetchPDASavedDataForFilteredData != null && hmapFetchPDASavedDataForFilteredData.size() > 0) {
                        for (Map.Entry<String, ActualVisitProductInfo> entry : hmapFetchPDASavedDataForFilteredData.entrySet()) {

                            ActualVisitProductInfo visitProductInfo = entry.getValue();
                            mDataSource.saveTblActualVisitStock(storeID, visitProductInfo.getProductId(), visitProductInfo.getStock(), 1, 0, visitProductInfo.getDisplayUnit(), storeVistCode);
                        }
                    }

                    passIntentToProductOrderFilter();
                } else {
                    //showAlertForEveryOne("It's compulsory to fill atleast one stock as you have mentioned Ltfoods Stock available.");
                    showAlertForEveryOne("It's compulsory to fill stocks of the Section 1.");
                }
            }
        });

    }


    public void showAlertForEveryOne(String msg) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ActualVisitStock.this);
        alertDialogNoConn.setTitle("Information");
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(R.string.txtOk, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }

    public boolean isStockFilledProperly() {
        boolean stockFilledPrprly = true;


        if (defaultProductInfoArrayList != null && !defaultProductInfoArrayList.isEmpty()) {
            for (ActualVisitProductInfo visitProductInfo : defaultProductInfoArrayList) {
                if (hmapFetchPDASavedDataForDefaultData.containsKey(visitProductInfo.getProductId())) {
                    if (!TextUtils.isEmpty(visitProductInfo.getStock()) && !visitProductInfo.getStock().equals("")) {
                        if (Integer.parseInt(visitProductInfo.getStock()) < 0) {
                            stockFilledPrprly = false;
                            return stockFilledPrprly;
                        }
                    } else {
                        stockFilledPrprly = false;
                        return stockFilledPrprly;
                    }
                } else {
                    stockFilledPrprly = false;
                    return stockFilledPrprly;
                }
            }
        }


      /*  if (hmapFetchPDASavedDataForFilteredData != null && hmapFetchPDASavedDataForFilteredData.size() > 0) {
            for (Map.Entry<String, ActualVisitProductInfo> entry : hmapFetchPDASavedDataForFilteredData.entrySet()) {
                ActualVisitProductInfo visitProductInfo = entry.getValue();
                if (!TextUtils.isEmpty(visitProductInfo.getStock())) {
                    if (Integer.parseInt(visitProductInfo.getStock()) < 0) {
                        stockFilledPrprly = false;
                        return stockFilledPrprly;
                    }
                }
            }
        }*/
        return stockFilledPrprly;
    }

    public void passIntentToProductOrderFilter() {
       /* Intent nxtP4 = new Intent(ActualVisitStock.this, ProductEntryForm.class);

        nxtP4.putExtra("storeID", storeID);
        nxtP4.putExtra("SN", selStoreName);
        nxtP4.putExtra("token", imei);
        nxtP4.putExtra("userdate", date);
        nxtP4.putExtra("pickerDate", pickerDate);
        nxtP4.putExtra("flgOrderType", 1);
        startActivity(nxtP4);
        finish();*/
        if (flgIsPicsAllowed == 1) {
            Intent fireBackDetPg = new Intent(ActualVisitStock.this, PicAfterMerchandising.class);
            fireBackDetPg.putExtra("storeID", storeID);
            fireBackDetPg.putExtra("SN", selStoreName);
            fireBackDetPg.putExtra("bck", 1);
            fireBackDetPg.putExtra("token", AppUtils.getToken(ActualVisitStock.this));
            fireBackDetPg.putExtra("imei", AppUtils.getToken(ActualVisitStock.this));
            fireBackDetPg.putExtra("userdate", date);
            fireBackDetPg.putExtra("pickerDate", pickerDate);
            fireBackDetPg.putExtra("flgOrderType", 1);
            //fireBackDetPg.putExtra("rID", routeID);
            startActivity(fireBackDetPg);
            finish();
        } else {
            Intent nxtP4 = new Intent(ActualVisitStock.this, ProductEntryForm.class);

            nxtP4.putExtra("storeID", storeID);
            nxtP4.putExtra("SN", selStoreName);
            nxtP4.putExtra("token", AppUtils.getToken(ActualVisitStock.this));
            nxtP4.putExtra("userdate", date);
            nxtP4.putExtra("pickerDate", pickerDate);
            nxtP4.putExtra("flgOrderType", 1);
            startActivity(nxtP4);
            finish();
        }

    }

    public void inflatePrdctStockData(int flgCompanyCompetitorProducts) {

        /*if (flgCompanyCompetitorProducts == 0) {
            if (hmapFilterProductListCompany != null && hmapFilterProductListCompany.size() > 0) {
                for (Map.Entry<String, String> entry : hmapFilterProductListCompany.entrySet()) {

                    String prdId = entry.getKey();
                    String value = entry.getValue().toString().split(Pattern.quote("^"))[0];
                    String unit = entry.getValue().toString().split(Pattern.quote("^"))[1];


                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewProduct = inflater.inflate(R.layout.inflate_row_actual_visit, null);
                    LinearLayout ll_inflate = (LinearLayout) viewProduct.findViewById(R.id.ll_inflate);

                    TextView prdName = (TextView) viewProduct.findViewById(R.id.prdName);
                    final EditText et_stckVal = (EditText) viewProduct.findViewById(R.id.et_stckVal);
                    TextView tv_stckUnit = (TextView) viewProduct.findViewById(R.id.tv_stckUnit);
                    prdName.setText(value);
                    prdName.setTag(prdId);
                    tv_stckUnit.setText(unit);
                    tv_stckUnit.setTag(prdId + "_Unit");

                    et_stckVal.setTag(prdId + "_Stock");

                    if (hmapFetchPDASavedDataForDefaultData != null && hmapFetchPDASavedDataForDefaultData.containsKey(prdId)) {
                        et_stckVal.setText(hmapFetchPDASavedDataForDefaultData.get(prdId));
                    } else {
                        hmapFetchPDASavedDataForDefaultData.put(prdId, "-1");
                    }
                    et_stckVal.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            ActualVisitProductInfo visitProductInfo;
                            if (!TextUtils.isEmpty(et_stckVal.getText().toString().trim())) {
                                String tagProductId = et_stckVal.getTag().toString().split(Pattern.quote("_"))[0];
                                String stock = et_stckVal.getText().toString().trim();

                                if (hmapFetchPDASavedDataForDefaultData.containsKey(tagProductId)) {
                                    visitProductInfo = hmapFetchPDASavedDataForDefaultData.get(tagProductId);
                                    visitProductInfo.setStock(stock);
                                } else {
                                    visitProductInfo = new ActualVisitProductInfo(storeID,
                                            tagProductId,
                                            1,
                                            stock,
                                            1,
                                            ""
                                    );
                                }
                                hmapFetchPDASavedDataForDefaultData.put(tagProductId, visitProductInfo);
                            } else {
                                String tagProductId = et_stckVal.getTag().toString().split(Pattern.quote("_"))[0];
                                if (hmapFetchPDASavedDataForDefaultData.containsKey(tagProductId)) {
                                    // hmapFetchPDASavedDataForDefaultData.remove(tagProductId);
                                    visitProductInfo = new ActualVisitProductInfo(storeID,
                                            tagProductId,
                                            1,
                                            "-1",
                                            1,
                                            ""
                                    );
                                    hmapFetchPDASavedDataForDefaultData.put(tagProductId, visitProductInfo);
                                }
                            }
                        }
                    });
                    lLayout_main.addView(viewProduct);

                    // btnNextClick(storeID,prdId,et_stckVal);


                }
            }
        }
        if (flgCompanyCompetitorProducts == 1) {
            if (hmapFilterProductListCompetitor != null && hmapFilterProductListCompetitor.size() > 0) {
                for (Map.Entry<String, String> entry : hmapFilterProductListCompetitor.entrySet()) {

                    String prdId = entry.getKey();
                    String value = entry.getValue().toString().split(Pattern.quote("^"))[0];
                    String unit = entry.getValue().toString().split(Pattern.quote("^"))[1];


                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewProduct = inflater.inflate(R.layout.inflate_row_actual_visit, null);
                    LinearLayout ll_inflate = (LinearLayout) viewProduct.findViewById(R.id.ll_inflate);

                    TextView prdName = (TextView) viewProduct.findViewById(R.id.prdName);
                    final EditText et_stckVal = (EditText) viewProduct.findViewById(R.id.et_stckVal);
                    TextView tv_stckUnit = (TextView) viewProduct.findViewById(R.id.tv_stckUnit);
                    prdName.setText(value);
                    prdName.setTag(prdId);
                    tv_stckUnit.setText(unit);
                    tv_stckUnit.setTag(prdId + "_Unit");

                    et_stckVal.setTag(prdId + "_Stock");

                    if (hmapFetchPDASavedDataForDefaultData != null && hmapFetchPDASavedDataForDefaultData.containsKey(prdId)) {
                        et_stckVal.setText(hmapFetchPDASavedDataForDefaultData.get(prdId));
                    } else {
                        hmapFetchPDASavedDataForDefaultData.put(prdId, "-1");
                    }
                    et_stckVal.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (!TextUtils.isEmpty(et_stckVal.getText().toString().trim())) {
                                String tagProductId = et_stckVal.getTag().toString().split(Pattern.quote("_"))[0];
                                hmapFetchPDASavedDataForDefaultData.put(tagProductId, et_stckVal.getText().toString().trim());
                            } else {
                                String tagProductId = et_stckVal.getTag().toString().split(Pattern.quote("_"))[0];
                                if (hmapFetchPDASavedDataForDefaultData.containsKey(tagProductId)) {
                                    // hmapFetchPDASavedDataForDefaultData.remove(tagProductId);
                                    hmapFetchPDASavedDataForDefaultData.put(tagProductId, "-1");
                                }
                            }
                        }
                    });
                    ll_SelfOtherProducts.addView(viewProduct);

                    // btnNextClick(storeID,prdId,et_stckVal);


                }
            }
        }*/
    }

    public void fetchDataFromDatabase() {
        /*hmapPrdctData = mDataSource.fetchProductDataForActualVisit();
        hmapFetchPDASavedDataForDefaultData = mDataSource.fetchActualVisitData(storeID);
        hmapProductStockFromPurchaseTable = mDataSource.fetchProductStockFromPurchaseTable(storeID);
        Iterator it11new = hmapProductStockFromPurchaseTable.entrySet().iterator();
        String crntPID = "0";
        int cntPsize = 0;
        while (it11new.hasNext()) {
            Map.Entry pair = (Map.Entry) it11new.next();
            hmapFetchPDASavedDataForDefaultData.put(pair.getKey().toString(), pair.getValue().toString());
        }
*/
        //  img_ctgry.setText("All");
        //searchProduct("All","");
        storeVisitCode = getStoreVisitCode();

        storePics = mDataSource.fnGetStoreNoPicsFlag(storeVisitCode);
        flgIsPicsAllowed = Integer.parseInt(storePics.split(Pattern.quote("^"))[0]);
        RegionID = mDataSource.fetchStoreRegionID(storeID);
        List<ActualVisitProductInfo> savedActualVisitProductInfos = mDataSource.fetchActualVisitDataForActualVisitStock(storeID);
        if (savedActualVisitProductInfos != null && savedActualVisitProductInfos.size()>0) {
            for (ActualVisitProductInfo visitProductInfo : savedActualVisitProductInfos) {
                if (visitProductInfo.getIsDefaultProduct() == 1) {
                    hmapFetchPDASavedDataForDefaultData.put(visitProductInfo.getProductId(), visitProductInfo);
                } else if (visitProductInfo.getIsDefaultProduct() == 0) {
                    hmapFetchPDASavedDataForFilteredData.put(visitProductInfo.getProductId(), visitProductInfo);
                }
            }
        }

        hmapProductTblUOMMapping = mDataSource.fetch_ProductWiseGetAllMeasureUnitAgainstProduct(storeID);

        StoreCurrentStoreType = Integer.parseInt(mDataSource.fnGetStoreTypeOnStoreIdBasis(storeID));
        System.out.println("Actual visit Step 4 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

        try {
            getCategoryDetail();
            System.out.println("Actual visit Step 5 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
            searchLoadDefaultProduct("All", "", 0);//Company Product Loading//********WE load defualt product on Oncreate
            System.out.println("Actual visit Step 6 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
           /* try {
            Thread.sleep(2000);
            }
             catch (Exception ex)
                {

                }*/
            searchProduct("All", "", 1, true);//Competitor Product Loading
            System.out.println("Actual visit Step 7 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
        }
        catch (Exception ex)
        {
            fnWriteLogFileHere(ex.toString());
        }
            /* if(hmapFetchPDASavedDataForDefaultData!=null && hmapFetchPDASavedDataForDefaultData.size()>0) {


            for (Map.Entry<String, String> entry : hmapFetchPDASavedDataForDefaultData.entrySet()) {

                String prdId=entry.getKey();
                String stckVal=entry.getValue();


                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewProduct = inflater.inflate(R.layout.inflate_row_actual_visit, null);
                LinearLayout ll_inflate= (LinearLayout) viewProduct.findViewById(R.id.ll_inflate);

                TextView prdName= (TextView) viewProduct.findViewById(R.id.prdName);
                EditText et_stckVal= (EditText) findViewById(R.id.et_stckVal);

                lLayout_main.addView(viewProduct);

            }
        }*/
    }

    private void getDataFromIntent() {


        Intent passedvals = getIntent();

        if (passedvals != null) {

            storeID = passedvals.getStringExtra("storeID");
            imei =AppUtils.getToken(ActualVisitStock.this);// passedvals.getStringExtra("imei");
            if(passedvals.hasExtra("userdate"))
            date = passedvals.getStringExtra("userdate");
            else
            date = getDateInMonthTextFormat();

            if(passedvals.hasExtra("pickerDate"))
            pickerDate = passedvals.getStringExtra("pickerDate");
            else
                pickerDate = getDateInMonthTextFormat();

            if(passedvals.hasExtra("SN"))
            selStoreName = passedvals.getStringExtra("SN");
            else
                selStoreName="NA";

       /*     isStockAvlbl = passedvals.getIntExtra("isStockAvlbl", 0);
            isCmpttrAvlbl = passedvals.getIntExtra("isCmpttrAvlbl", 0);*/
            storeVistCode = passedvals.getStringExtra("StoreVisitCode");

        }

    }

    public void customAlertStoreList(final List<String> listOption, String sectionHeader) {

        final Dialog listDialog = new Dialog(ActualVisitStock.this);
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
        //    TextView txtVwSubmit=(TextView) listDialog.findViewById(R.id.txtVwSubmit);


        final ListView list_store = listDialog.findViewById(R.id.list_store);
        final CardArrayAdapterCategory2 cardArrayAdapter = new CardArrayAdapterCategory2(ActualVisitStock.this, listOption, listDialog, previousSlctdCtgry, flgCompanyCompetitorProducts);

        //img_ctgry.setText(previousSlctdCtgry);


        list_store.setAdapter(cardArrayAdapter);
        //	editText.setBackgroundResource(R.drawable.et_boundary);
        img_ctgry.setEnabled(true);


        txtVwCncl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listDialog.dismiss();
                img_ctgry.setEnabled(true);


            }
        });


        //now that the dialog is set up, it's time to show it
        listDialog.show();

    }

    @Override
    public void selectedOption(String selectedCategory, Dialog dialog) {
        dialog.dismiss();
        previousSlctdCtgry = selectedCategory;

        //  img_ctgry.setText(selectedCategory);

        if (hmapctgry_details.containsKey(selectedCategory)) {
            searchProduct(selectedCategory, hmapctgry_details.get(selectedCategory), flgCompanyCompetitorProducts, false);
        } else {
            searchProduct(selectedCategory, "", flgCompanyCompetitorProducts, false);
        }
    }

    @Override
    public void selectedOption(String selectedCategory, Dialog dialog, int flgCompanyCompetitorProducts) {
        dialog.dismiss();
        previousSlctdCtgry = selectedCategory;

        //  img_ctgry.setText(selectedCategory);

        if (hmapctgry_details.containsKey(selectedCategory)) {
            searchProduct(selectedCategory, hmapctgry_details.get(selectedCategory), flgCompanyCompetitorProducts, false);
        } else {
            searchProduct(selectedCategory, "", flgCompanyCompetitorProducts, false);
        }


    }

    public void searchProduct(String filterSearchText, String ctgryId, int flgCompanyCompetitorProducts, boolean isFirstTime) {
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBarStatus = 0;

        /*if (flgCompanyCompetitorProducts == 0) {
            hmapFilterProductListCompany.clear();
            hmapFilterProductListCompany = mDataSource.getFileredProductListMapStoreCheck(filterSearchText.trim(),
                    StoreCurrentStoreType,
                    ctgryId,
                    flgCompanyCompetitorProducts);

            lLayout_main.removeAllViews();
            if (hmapFilterProductListCompany.size() > 0) {
                inflatePrdctStockData(flgCompanyCompetitorProducts);
            } else {
                allMessageAlert(ActualVisitStock.this.getResources().getString(R.string.AlertFilter));
            }
        }
        */
        if (flgCompanyCompetitorProducts == 1) {
            filteredProductList.clear();
            /*hmapFilterProductListCompetitor.clear();
            hmapFilterProductListCompetitor = mDataSource.getFileredProductListMapStoreCheck(filterSearchText.trim(), StoreCurrentStoreType, ctgryId, flgCompanyCompetitorProducts);
            */
            filteredProductList = mDataSource.getFileredProductListMapStoreCheck(filterSearchText.trim(), StoreCurrentStoreType, ctgryId, flgCompanyCompetitorProducts, storeID, RegionID);
            if (filteredProductList!=null && filteredProductList.size() > 0) {
                for (ActualVisitProductInfo visitProductInfo : filteredProductList) {
                    if (hmapFetchPDASavedDataForFilteredData.containsKey(visitProductInfo.getProductId())) {
                        visitProductInfo.setStock(hmapFetchPDASavedDataForFilteredData.get(visitProductInfo.getProductId()).getStock());
                        visitProductInfo.setDisplayUnit(hmapFetchPDASavedDataForFilteredData.get(visitProductInfo.getProductId()).getDisplayUnit());
                    }
                }
                displayFilteredProductsMandatory(isFirstTime);
            } else {
                allMessageAlert(ActualVisitStock.this.getResources().getString(R.string.AlertFilter));
            }
            Log.d("ActualVisit", filteredProductList.toString());

            /*ll_SelfOtherProducts.removeAllViews();
            if (hmapFilterProductListCompetitor.size() > 0) {
                inflatePrdctStockData(flgCompanyCompetitorProducts);
            }*/
        }



		/*if(hmapFilterProductList.size()<250)
		{*/


		/*}

		else
		{
			allMessageAlert("Please put some extra filter on Search-Box to fetch related product");
		}*/


    //    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    private void allMessageAlert(String message) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ActualVisitStock.this);
        alertDialogNoConn.setTitle(ActualVisitStock.this.getResources().getString(R.string.genTermInformation));
        alertDialogNoConn.setMessage(message);
        //alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
        alertDialogNoConn.setNeutralButton(ActualVisitStock.this.getResources().getString(R.string.AlertDialogOkButton), (dialog, which) -> dialog.dismiss());
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

    private void getCategoryDetail() {

        hmapctgry_details = mDataSource.fetch_Category_ListOtherFromOrderEntry();

        int index = 0;
        if (hmapctgry_details != null && hmapctgry_details.size()>0) {
            categoryNames = new ArrayList<String>();
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

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public void searchLoadDefaultProduct(String filterSearchText, String ctgryId, int flgCompanyCompetitorProductsDefault) {
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBarStatus = 0;

        if (flgCompanyCompetitorProductsDefault == 0) {
            defaultProductInfoArrayList.clear();
            defaultProductInfoArrayList = mDataSource.fetchProductListLastvisitAndOrderBasis(storeID, RegionID);
            if (defaultProductInfoArrayList != null && !defaultProductInfoArrayList.isEmpty() && defaultProductInfoArrayList.size()>0) {
                for (ActualVisitProductInfo visitProductInfo : defaultProductInfoArrayList) {
                    if (hmapFetchPDASavedDataForDefaultData.containsKey(visitProductInfo.getProductId())) {
                        visitProductInfo.setStock(hmapFetchPDASavedDataForDefaultData.get(visitProductInfo.getProductId()).getStock());
                        visitProductInfo.setDisplayUnit(hmapFetchPDASavedDataForDefaultData.get(visitProductInfo.getProductId()).getDisplayUnit());
                    } else {
                        // hmapFetchPDASavedDataForDefaultData.put(visitProductInfo.getProductId(),"0")  ;
                    }
                }
            }
            displayDefaultProducts();
        }

        //System.out.println("hmapFilterProductListCount :-"+ hmapFilterProductList.size());


		/*if(hmapFilterProductList.size()<250)

		else
		{
			allMessageAlert("Please put some extra filter on Search-Box to fetch related product");
		}*/


       // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    private void displayDefaultProducts() {

      /*  if (defaultProductInfoArrayList != null && defaultProductInfoArrayList.size()>0 && defaultProductInfoArrayList.size() > 6) {
            tvNoMandatoryPrd.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParamsForDefault = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.4f);
            ll_CompanyPrdHeader.setLayoutParams(layoutParamsForDefault);
            LinearLayout.LayoutParams layoutParamsForFiltered = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.6f);
            ll_CompleteSelfOtherProductSection.setLayoutParams(layoutParamsForFiltered);
            CustomVisitStockAdapter visitStockAdapter = new CustomVisitStockAdapter(this, defaultProductInfoArrayList, storeID, hmapFetchPDASavedDataForDefaultData, true, customKeyboard, hmapProductTblUOMMapping, UOM_names);
            defaultProductRV.setAdapter(visitStockAdapter);
        } else*/ if (defaultProductInfoArrayList != null && !defaultProductInfoArrayList.isEmpty() && defaultProductInfoArrayList.size()>0) {
            tvNoMandatoryPrd.setVisibility(View.GONE);

             visitStockAdapter = new CustomVisitStockAdapter(this, defaultProductInfoArrayList, storeID, hmapFetchPDASavedDataForDefaultData, true, customKeyboard, hmapProductTblUOMMapping, UOM_names);
            defaultProductRV.setAdapter(visitStockAdapter);
        } else {
            tvNoMandatoryPrd.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParamsForFiltered = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 3.0f);
            ll_CompleteSelfOtherProductSection.setLayoutParams(layoutParamsForFiltered);
        }
    }

    private void displayFilteredProducts(boolean isFirstTime) {
        if (filteredProductList!=null && filteredProductList.size() > 0) {
            tvNoOtherPrd.setVisibility(View.GONE);
            filteredProductRV.setVisibility(View.VISIBLE);
            if (isFirstTime) {
                visitStockAdapter = new CustomVisitStockAdapter(this, filteredProductList, storeID, hmapFetchPDASavedDataForFilteredData, false, customKeyboard, hmapProductTblUOMMapping, UOM_names);
                filteredProductRV.setAdapter(visitStockAdapter);
            } else {
                visitStockAdapter.refreshData(filteredProductList);
            }
        } else {
            tvNoOtherPrd.setVisibility(View.VISIBLE);
            filteredProductRV.setVisibility(View.GONE);
        }

    }



    private void displayFilteredProductsMandatory(boolean isFirstTime) {
        if (filteredProductList!=null && filteredProductList.size() > 0) {
            tvNoOtherPrd.setVisibility(View.GONE);
            filteredProductRV.setVisibility(View.VISIBLE);
            if (isFirstTime) {
                visitStockAdapterMandatory = new CustomVisitStockAdapterMandotry(this, filteredProductList, storeID, hmapFetchPDASavedDataForFilteredData, false, customKeyboard, hmapProductTblUOMMapping, UOM_names);
                filteredProductRV.setAdapter(visitStockAdapterMandatory);
            } else {
                visitStockAdapterMandatory.refreshData(filteredProductList);
            }
        } else {
            tvNoOtherPrd.setVisibility(View.VISIBLE);
            filteredProductRV.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(EditText editText) {
        try {
            InputMethodManager keyboard = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            customKeyboard.registerEditText(editText);
            customKeyboard.showCustomKeyboard(editText);
        }
        catch (Exception ex)
        {
            fnWriteLogFileHere(ex.toString());
        }

    }

   /* @Override
    public void fnCallUpdateLocation() {
        inflateUpdateContactSection();
    }*/

    void inflateUpdateContactSectionLayout() {
        viewContactSection = getLayoutInflater().inflate(R.layout.layout_update_contact_frame, null);

        tvContactNoCorrection = (TextView) viewContactSection.findViewById(R.id.tvContactNoCorrection);
        btnEditContact = (Button) viewContactSection.findViewById(R.id.btnEditContact);

        ll_ContactUpdateSection.addView(viewContactSection);


    }

    void inflateUpdateContactSection() {


        tvContactNoCorrection = (TextView) viewContactSection.findViewById(R.id.tvContactNoCorrection);
        btnEditContact = (Button) viewContactSection.findViewById(R.id.btnEditContact);


        if (mDataSource.fnCheckStoreIntblStoreListForUpdateMstr(storeID) == 1) {
            ll_ContactUpdateSection.setVisibility(View.VISIBLE);
            if (mDataSource.fnCheckStoreMappedInStoreListForUpdateMstr(storeID) == 1) {
                ll_ContactUpdateSection.setVisibility(View.GONE);
                tvContactNoCorrection.setText("Contact Number Verified");
                tvContactNoCorrection.setTextColor(Color.parseColor("#74C543"));
            }

            btnEditContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ready4GetLoc = new Intent(ActualVisitStock.this, StoreEditActivityContactLocation.class);
                    ready4GetLoc.putExtra("storeID", storeID);
                    startActivity(ready4GetLoc);
                    //finish();
                }
            });
        } else {
            ll_ContactUpdateSection.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StoreEditActivityContactLocation.flgCallActivityContactLocation == 1) {
            inflateUpdateContactSection();
            StoreEditActivityContactLocation.flgCallActivityContactLocation = 0;
        }
    }

    @Override
    public void fcsLstCld(boolean hasFocus, EditText editText) {
        AppUtils.hideKeyBoard(editText, this);


        customKeyboard.hideCustomKeyboard();
      //  mCustomKeyboardNum.hideCustomKeyboard();
        if (!hasFocus) {

        }
        else
        {
            customKeyboard.registerEditText(editText);
            customKeyboard.showCustomKeyboard(editText);
        }
    }

    @Override
    public void fcsLstCldOnFocus(boolean hasFocus, EditText editText) {

    }



    public void fnWriteLogFileHere(String crashString)
    {

        long StartClickTime = System.currentTimeMillis();
        Date dateobj1 = new Date(StartClickTime);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String StartClickTimeFinal = TimeUtils.getNetworkDateTime(ActualVisitStock.this, TimeUtils.DATE_TIME_FORMAT);


        File RajTextFileFolderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.TextFileFolderCrashDetails);

        if (!RajTextFileFolderXMLFolder.exists()) {
            RajTextFileFolderXMLFolder.mkdirs();
        }

        String fileName = AppUtils.getToken(ActualVisitStock.this) + "_" + storeID;

        File file = new File(Environment.getExternalStorageDirectory() + "/" + CommonInfo.TextFileFolderCrashDetails + "/" + fileName+".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();

            }
        }
        CommonInfo.fileContent = CommonInfo.fileContent + " _ "+StartClickTimeFinal +"        " + "_" + storeID + " _ " + crashString ;
        FileWriter fw;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(CommonInfo.fileContent);
            bw.close();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();


        }
    }
}
