package com.astix.rajtraderssfasales;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allana.truetime.TimeUtils;
import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblAlrtVal;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblProductMappedWithSchemeSlabApplied;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsBucketDetails;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsProductMappingDetail;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsValueDetail;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBucketProductMapping;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblStoreProductAddOnSchemeApplied;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblStoreProductAppliedSchemesBenifitsRecords;
import com.astix.rajtraderssfasales.adapter.CreatedCartonAdapter;
import com.astix.rajtraderssfasales.adapter.ExpandableCategoryAdapter;
import com.astix.rajtraderssfasales.adapter.TARSOrdersAdapter;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.location.LocationInterface;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobal;
import com.astix.rajtraderssfasales.merchandizing.PicAfterMerchandising;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreVisitHistory;
import com.astix.rajtraderssfasales.model.TblInvoiceDetailsServer;
import com.astix.rajtraderssfasales.model.TblOnlyCategoryMasterForRetriving;
import com.astix.rajtraderssfasales.model.TblProductCategoryUOMTypeList;
import com.astix.rajtraderssfasales.model.TblProductTypeMasterForRetriving;
import com.astix.rajtraderssfasales.model.TblStoreProductMappingForDisplay;
import com.astix.rajtraderssfasales.model.TblUOMMapping;
import com.astix.rajtraderssfasales.model.TblUOMMaster;
import com.astix.rajtraderssfasales.model.TbltmpInvoiceHeaderCartonPlusOrder;
import com.astix.rajtraderssfasales.sync.SyncJobService;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.PrioritySKUDialog;
import com.astix.rajtraderssfasales.utils.ProductInfoDialog;
import com.astix.rajtraderssfasales.utils.StoreHistoryDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;

import io.reactivex.annotations.NonNull;

import static br.com.zbra.androidlinq.Linq.stream;

public class ProductEntryFormCopyForDistributorEntry extends BaseActivity implements View.OnClickListener, CategoryCommunicatorOrderFilter,   InterfaceRetrofitProductRecyclerView {
    String[] Distribtr_list_alert;
    String DbrSelNodeId, DbrSelNodeType, DbrSelName;
    ArrayList<String> DbrSelArray = new ArrayList<String>();
    LinkedHashMap<String, String> hmapDistrbtrList = new LinkedHashMap<>();
    String imei,fDate,from;
    Spinner spinner_for_filter;
    RelativeLayout relLayout_img;
    LinearLayout  ll_forTableHeaderName;
    Button btn_save, btn_skip;
    ArrayList<String> DbrArray = new ArrayList<String>();
    String[] Distribtr_list;
    String DbrNodeId, DbrNodeType, DbrName;
    int DistribtrId_Global = 0;
    public int DistributorNodeType_Global = 0;
    public ProgressDialog pDialog2STANDBY;
String newfullFileName="";

    List<TblStoreProductMappingForDisplay> tblTCOrderStoreProductDiscountApplicable = new ArrayList<TblStoreProductMappingForDisplay>();
    public int IsDiscountApplicable = 0;
    public int flgInCarton = 0;

    ExpandableCategoryAdapter cardArrayAdapter;

    int flgAllCategory = 0;

    List<TblStoreProductMappingForDisplay> tblStoreProductMappingList = new ArrayList<TblStoreProductMappingForDisplay>();
    List<TblStoreProductMappingForDisplay> tblStoreProductMappingListtemp = new ArrayList<TblStoreProductMappingForDisplay>();
    List<TblStoreProductMappingForDisplay> tblStoreProductMappingListForDisplay = new ArrayList<>();

    public int RegionID = 0;
    OrderAdapterProdcutEntry orderAdapter;
    LinearLayout ll_rcylContainer;
    String SelectedCategoryForSchemeFileter = "All";
    int SelectedCategoryIDForSchemeFileter = 0;
    public String[] arrSchId;

    HashMap<String, String> hmapProductLODQty = new HashMap<String, String>();

    HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMapping = new HashMap<Integer, List<TblUOMMapping>>();
    LinkedHashMap<String, String> hmapPerBaseQty;

    EditText ed_LastEditextFocusd;
    public String ProductIdOnClickedEdit;
    public String CtaegoryIddOfClickedView;
    LinkedHashMap<String, Integer> hmapCatSelecteList = new LinkedHashMap<String, Integer>();
    ArrayList<TblProductTypeMasterForRetriving> tblProductTypeMasterForRetrivingList = new ArrayList<TblProductTypeMasterForRetriving>();
    public int CurrentCategorySelectedPosition = -1;
    RecyclerView rv_prdct_detal;

    String progressTitle;
    ProgressDialog mProgressDialog;

    CustomKeyboard mCustomKeyboardNum, mCustomKeyboardNumWithoutDecimal;


    ImageView img_ctgry, btn_bck;
    EditText  ed_search;

    TextView txt_stockDate;

    Button  btn_OrderReview;

    LinkedHashMap<String, String> hmapFilterProductList;
    LinkedHashMap<String, String> hmapctgry_details;
    HashMap<String, String> hmapProductIdStock;
    ArrayList<HashMap<String, String>> arrLstHmapPrdct;

    HashMap<String, String> hmapPrdctOdrQty;

    HashMap<String, String> hmapProductImg;// = new HashMap<String, String>();

    HashMap<String, String> hmapflgPicsOrCases;

    public ProductFilledDataModel prdctModelArrayList = new ProductFilledDataModel();

    public int StoreCurrentStoreType = 0, chkflgInvoiceAlreadyGenerated = 0;
    public String defaultCatName_Id = "0", previousSlctdCtgry, distID = "";
    List<String> categoryNames;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_entry_form);

        mProgressDialog = new ProgressDialog(ProductEntryFormCopyForDistributorEntry.this);
        sharedPref = getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);
        initializeFields();
        getDataFromatabase();

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

        ll_rcylContainer = (LinearLayout) findViewById(R.id.ll_rcylContainer);

        img_ctgry = (ImageView) findViewById(R.id.img_ctgry);
        ed_search = (EditText) findViewById(R.id.ed_search);


        img_ctgry.setOnClickListener(this);
        btn_bck.setOnClickListener(this);

        btn_OrderReview.setOnClickListener(this);
        ed_search.setOnClickListener(this);


        spinner_for_filter = (Spinner) findViewById(R.id.spinner_for_filter);
        relLayout_img = (RelativeLayout) findViewById(R.id.relLayout_img);
        relLayout_img.setTag("img");

        ll_forTableHeaderName = (LinearLayout) findViewById(R.id.ll_forTableHeaderName);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_skip = (Button) findViewById(R.id.btn_skip);

        if (from.equals("DayStart")) {
            btn_bck.setVisibility(View.GONE);
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


        btn_skip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // finish();
                Intent i = new Intent(ProductEntryFormCopyForDistributorEntry.this, AllButtonActivity.class);
                i.putExtra("token", imei);
                i.putExtra("fDate", fDate);
                startActivity(i);
                finish();
            }
        });
        txt_stockDate = (TextView) findViewById(R.id.txt_stockDate);

        fnGetDistributorList();

        spinner_for_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView) view;
                String text = tv.getText().toString();

                if (text.equals("Select Distributor")) {

                    ll_forTableHeaderName.setVisibility(View.GONE);

                    btn_save.setVisibility(View.GONE);
                    txt_stockDate.setVisibility(View.GONE);


                } else {

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
                            CommonFunction.getDistributorTodayStock(ProductEntryFormCopyForDistributorEntry.this, AppUtils.getToken(ProductEntryFormCopyForDistributorEntry.this), DistribtrId_Global, DistributorNodeType_Global);

                        } else {
                            showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
                        }
                    } else {

                       //GetOldData
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_save.setOnClickListener(v -> {
            if (isOnline()) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ProductEntryFormCopyForDistributorEntry.this);
                dialog.setTitle(getText(R.string.genTermInformation));
                dialog.setMessage(getText(R.string.SubmitDistrbtrStck));
                dialog.setPositiveButton(getText(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                          //  saveDistributorStockInTable();

                            mDataSource.deletetblIsDBRStockSubmitted();
                            mDataSource.savetblIsDBRStockSubmitted(1);
                            FullSyncDataNow task = new FullSyncDataNow(ProductEntryFormCopyForDistributorEntry.this);
                            task.execute();
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
            } else {
               // saveDistributorStockInTable();
                FullSyncDataNow task = new FullSyncDataNow(ProductEntryFormCopyForDistributorEntry.this);
                task.execute();
                //showNoConnAlertforLocalDataSaved();
            }
        });
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductEntryFormCopyForDistributorEntry.this, R.layout.initial_spinner_text, DbrArray);
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

    private void getDataFromIntent() {
        Intent i = getIntent();
        imei = AppUtils.getToken(ProductEntryFormCopyForDistributorEntry.this);
        fDate = i.getStringExtra("fDate");
        from = i.getStringExtra("From");
    }


    public void searchProduct(String filterSearchText, String ctgrytext, int filterType, int ParentID) {
        tblStoreProductMappingListForDisplay.clear();
        if (hmapFilterProductList != null) {
            hmapFilterProductList.clear();
        }

        SelectedCategoryForSchemeFileter = "All";
        SelectedCategoryIDForSchemeFileter = 0;

        tblStoreProductMappingListtemp.clear();


        List<String> searchStringsArray = new ArrayList<>();
        if (filterSearchText.contains(",")) {
            searchStringsArray.addAll(Arrays.asList(filterSearchText.toLowerCase().split(Pattern.quote(","))));
        }
        if (searchStringsArray.size() == 0) {
            if (SelectedCategoryForSchemeFileter.equals("All")) {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase())).toList();
            } else {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase()) && p.getCatID() == ParentID).toList();
            }
        } else {
            List<TblStoreProductMappingForDisplay> tblStoreProductMappingListtempFilter = new ArrayList<TblStoreProductMappingForDisplay>();


            List<Integer> positions = new ArrayList<>();
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


    public void getDataFromatabase() {
         getCategoryDetail();
        getPrductInfoDetail();

//        tblStoreProductMappingList = mDataSource.fnGetTblStoreProductMappingForSearchDistributor(DistribtrId_Global,DistributorNodeType_Global);


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


        tblStoreProductMappingListtemp.clear();
        tblStoreProductMappingListtemp.addAll(stream(tblStoreProductMappingList).toList());
        tblStoreProductMappingListtemp = stream(tblStoreProductMappingListtemp).orderBy(p -> p.getCatID()).toList();

        fnMarkFirstProductCategoryRowVisible();
        String[] listProduct = new String[tblStoreProductMappingListForDisplay.size()];
     /*   orderAdapter = new OrderAdapterProdcutEntry(ProductEntryFormCopyForDistributorEntry.this, storeID, listProduct, hmapFilterProductList, hmapProductStandardRate,
                hmapProductMRP, hmapProductIdStock, hmapProductIdLastStock, hampGetLastProductExecution, hmapDistPrdctStockCount, hmapVanPrdctStockCount,
                prdctModelArrayList, 0, hmapPerBaseQty, hmapProductTblUOMMapping, hmapProductLODQty, hmapProductSuggestedQty, hmapProductImg,
                hmapProductRelatedSchemesList, flgIsAnySchemeMappedAgainstStore, tblStoreProductMappingListForDisplay, date, pickerDate, SN,
                flgOrderType, IsDiscountApplicable, tblTCOrderStoreProductDiscountApplicable, new GetCartonPrdsInterface() {
            @Override
            public void getAllCartonPrds(List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysCarton, ProductCartonFilledDataModel productCartonFilledDataModel) {


            }
        });*/
        rv_prdct_detal.setAdapter(orderAdapter);
        rv_prdct_detal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        if (defaultCatName_Id.contains("^")) {
            ed_search.setText("");//defaultCatName_Id.split(Pattern.quote("^"))[0]


            searchProduct(defaultCatName_Id.split(Pattern.quote("^"))[0], defaultCatName_Id.split(Pattern.quote("^"))[1], 0, 0);

        }


        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.toString().trim().length() == 0) {

                } else {
                    searchProduct(ed_search.getText().toString().trim(), "0", 0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String dfsdfs = "sdfs";


    }


    public void fnMarkFirstProductCategoryRowVisible() {
        //tblStoreProductMappingListForSearching


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


    public void getPrductInfoDetail() {
     /*   arrLstHmapPrdct = mDataSource.fetch_catgry_prdctsData(storeID, StoreCurrentStoreType, RegionID);

        hmapProductTblUOMMapping = mDataSource.fetch_ProductWiseGetAllMeasureUnitAgainstProduct(storeID);


        hmapPrdctOdrQty = mDataSource.fnGetProductPurchaseList(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);

        hmapflgPicsOrCases = mDataSource.fnGetProductPurchaseListPicOrCases(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);

*/
        if (hmapPrdctOdrQty != null && hmapPrdctOdrQty.size() > 0) {
            for (Map.Entry<String, String> entry : hmapPrdctOdrQty.entrySet()) {
                if (Integer.parseInt(entry.getValue()) > 0) {
                    prdctModelArrayList.setPrdctQty(entry.getKey(), entry.getValue());
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

    private void getCategoryDetail() {
        tblProductTypeMasterForRetrivingList = mDataSource.fetch_Category_List_Distribution();
        hmapctgry_details = mDataSource.fetch_Category_ListOtherFromOrderEntry();

        int index = 0;
        if (hmapctgry_details != null) {
        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.img_ctgry:
                img_ctgry.setEnabled(false);
                customAlertStoreList(categoryNames, "Filter");
                break;
            case R.id.btn_bck:
               // saveDistributorStockInTable();
                Intent i = new Intent(ProductEntryFormCopyForDistributorEntry.this, AllButtonActivity.class);
                i.putExtra("token", imei);
                i.putExtra("fDate", fDate);
                startActivity(i);
                finish();
                break;

            case R.id.btn_InvoiceReview:

                break;

            case R.id.ed_search:

                mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
                mCustomKeyboardNum.hideCustomKeyboard();
                break;

        }


    }

    public void customAlertStoreList(final List<String> listOption, String sectionHeader) {

        final Dialog listDialog = new Dialog(ProductEntryFormCopyForDistributorEntry.this);
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listDialog.setContentView(R.layout.search_list_order_filter);
        listDialog.setCanceledOnTouchOutside(false);
        listDialog.setCancelable(false);
        WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
        parms.gravity = Gravity.CENTER;
        //there are a lot of settings, for dialog, check them all out!
        parms.dimAmount = (float) 0.5;


        TextView txt_section = (TextView) listDialog.findViewById(R.id.txt_section);
        txt_section.setText(sectionHeader);
        TextView txtVwCncl = (TextView) listDialog.findViewById(R.id.txtVwCncl);

        TextView txtVwSubmit = (TextView) listDialog.findViewById(R.id.txtVwSubmit);

        CheckBox cbRepSKU = (CheckBox) listDialog.findViewById(R.id.cbRepSKU);
        CheckBox cbPrioritySKU = (CheckBox) listDialog.findViewById(R.id.cbPrioritySKU);
        CheckBox cbSchemePrd = (CheckBox) listDialog.findViewById(R.id.cbSchemePrd);

        CheckBox cbAll = (CheckBox) listDialog.findViewById(R.id.cbAll);


        txtVwSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listDialog.dismiss();
                if (hmapCatSelecteList != null && hmapCatSelecteList.size() > 0) {
                    if (hmapCatSelecteList.size() == tblProductTypeMasterForRetrivingList.size() + 1) {
                        cbAll.setChecked(true);
                        flgAllCategory = 1;
                    } else {
                        flgAllCategory = 0;
                    }
                }
                searchForProdcutsBasedOnVariousConditions(hmapCatSelecteList, 0, 0, 0);


            }
        });

        if (flgAllCategory == 1) {
            cbAll.setChecked(true);
            hmapCatSelecteList.put("All", 1);
            for (TblProductTypeMasterForRetriving tblProductTypeMasterForRetriving : tblProductTypeMasterForRetrivingList) {
                hmapCatSelecteList.put(tblProductTypeMasterForRetriving.getProductType(), 1);
            }
        }
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox v = (CheckBox) view;
                if (v.isChecked()) {
                    flgAllCategory = 1;
                    hmapCatSelecteList.put("All", 1);
                    for (TblProductTypeMasterForRetriving tblProductTypeMasterForRetriving : tblProductTypeMasterForRetrivingList) {
                        hmapCatSelecteList.put(tblProductTypeMasterForRetriving.getProductType(), 1);
                    }
                } else {
                    flgAllCategory = 0;
                    hmapCatSelecteList.remove(0);
                    for (TblProductTypeMasterForRetriving tblProductTypeMasterForRetriving : tblProductTypeMasterForRetrivingList) {
                        hmapCatSelecteList.remove(tblProductTypeMasterForRetriving.getProductType());
                    }
                }
                cardArrayAdapter.updateList();
            }
        });


        //    TextView txtVwSubmit=(TextView) listDialog.findViewById(R.id.txtVwSubmit);

        final EditText ed_searchBrand = (AutoCompleteTextView) listDialog.findViewById(R.id.ed_search);
        ed_searchBrand.setVisibility(View.GONE);
        final RecyclerView list_store = (RecyclerView) listDialog.findViewById(R.id.list_store);
        //cardArrayAdapter = new ExpandableCategoryAdapter(ProductEntryFormCopyForDistributorEntry.this, listDialog, previousSlctdCtgry, tblProductTypeMasterForRetrivingList, CurrentCategorySelectedPosition, hmapCatSelecteList);

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
    public void selectedOption(String selectedCategory, String selectedText, Dialog dialog, int filterType, int ParentID, int parentPosition, Boolean isParentSelected, Boolean isChildSelected, TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingGlobal, ArrayList<TblOnlyCategoryMasterForRetriving> tblOnlyCategoryMasterForRetrivingGlobal, int crntChildID) {
        dialog.dismiss();
        previousSlctdCtgry = selectedCategory;
        String lastTxtSearch = ed_search.getText().toString().trim();
        //img_ctgry.setText(selectedCategory);
        // ed_search.setText(selectedCategory);
        CurrentCategorySelectedPosition = parentPosition;

        SelectedCategoryForSchemeFileter = selectedCategory;
        SelectedCategoryIDForSchemeFileter = Integer.parseInt(selectedText);

        for (TblProductTypeMasterForRetriving ProductTypeMaster : tblProductTypeMasterForRetrivingList) {
            if (ProductTypeMaster.getProductTypeNodeID() == tblProductTypeMasterForRetrivingGlobal.getProductTypeNodeID()) {
                ProductTypeMaster.setExpanded(true);
                ProductTypeMaster.setParentPsition(parentPosition);
                ProductTypeMaster.setChildExpanded(isChildSelected);

                ArrayList<TblOnlyCategoryMasterForRetriving> ProductTypeMasterSubType = ProductTypeMaster.getTblOnlyCategoryMasterForRetrivingList();
                if (ProductTypeMasterSubType.size() > 0) {
                    for (TblOnlyCategoryMasterForRetriving ProductTypeMasterSubRecord : ProductTypeMasterSubType) {
                        if (ProductTypeMasterSubRecord.getCategoryNodeID() == crntChildID) {
                            ProductTypeMasterSubRecord.setSelected(true);
                        } else {
                            ProductTypeMasterSubRecord.setSelected(false);
                        }
                    }
                }
            } else {
                ProductTypeMaster.setExpanded(false);
                ProductTypeMaster.setParentPsition(parentPosition);
                ProductTypeMaster.setChildExpanded(false);
                ArrayList<TblOnlyCategoryMasterForRetriving> ProductTypeMasterSubType = ProductTypeMaster.getTblOnlyCategoryMasterForRetrivingList();
                if (ProductTypeMasterSubType.size() > 0) {
                    for (TblOnlyCategoryMasterForRetriving ProductTypeMasterSubRecord : ProductTypeMasterSubType) {
                        if (ProductTypeMasterSubRecord.getCategoryNodeID() == crntChildID) {
                            ProductTypeMasterSubRecord.setSelected(false);
                        } else {
                            ProductTypeMasterSubRecord.setSelected(false);
                        }
                    }
                }
            }

        }
        if (Integer.parseInt(selectedText) > 0) {
            searchProduct(selectedCategory, selectedText, filterType, ParentID);
        } else {
            searchProduct(selectedCategory, "", 0, ParentID);
        }


    }



    public class SaveData extends AsyncTask<String, String, Void> {
        int btnClkd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mProgressDialog.isShowing() == false) {
                mProgressDialog = new ProgressDialog(ProductEntryFormCopyForDistributorEntry.this);
                mProgressDialog.setTitle(ProductEntryFormCopyForDistributorEntry.this.getResources().getString(R.string.Loading));
                mProgressDialog.setMessage(progressTitle);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            String executedData = params[0];


            btnClkd = Integer.parseInt(executedData);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (mProgressDialog.isShowing() == true) {
                mProgressDialog.dismiss();
            }


        }


    }


    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {

         /*   mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
            mCustomKeyboardNum.hideCustomKeyboard();*/

            return false;


//			return true;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            // finish();
            return true;
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
    public void fnExpandCategory(int CategoryID) {
        // tblStoreProductMappingListForDisplay.clear();
        if (hmapFilterProductList != null) {
            hmapFilterProductList.clear();
        }


        fnMarkFirstProductCategoryRowVisibleExpand(CategoryID);
        orderAdapter.updateList(tblStoreProductMappingListForDisplay);

    }

    @Override
    public void fnCollapseCategory(int CategoryID) {
        // tblStoreProductMappingListForDisplay.clear();
        if (hmapFilterProductList != null) {
            hmapFilterProductList.clear();
        }

            fnMarkFirstProductCategoryRowVisibleCollapse(CategoryID);
        orderAdapter.updateList(tblStoreProductMappingListForDisplay);

    }

    public void fnMarkFirstProductCategoryRowVisibleExpand(int CategoryID) {


        Iterator<TblStoreProductMappingForDisplay> crunchifyIterator = tblStoreProductMappingListtemp.iterator();
        if (tblStoreProductMappingListtemp != null && tblStoreProductMappingListtemp.size() > 0) {
            int catid = 0;
            Set catSet = new HashSet();
            while (crunchifyIterator.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIterator.next();
                if (tblStoreProductMappingRecord.getCatID() == CategoryID) {
                    if (!catSet.contains(tblStoreProductMappingRecord.getCatID())) {
                        tblStoreProductMappingRecord.setFlgShowCategoryHeader(1);
                        tblStoreProductMappingRecord.setFlgMakeFrameVisible(1);
                        tblStoreProductMappingRecord.setFlgMakeRowVisible(1);
                        catSet.add(tblStoreProductMappingRecord.getCatID());
                    } else {
                        //  tblStoreProductMappingRecord.setFlgShowCategoryHeader(0);
                        tblStoreProductMappingRecord.setFlgMakeFrameVisible(1);
                        tblStoreProductMappingRecord.setFlgMakeRowVisible(1);
                    }
                }
            }


            /*while (crunchifyIterator.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIterator.next();


                if (CategoryID==tblStoreProductMappingRecord.getCatID()) {
                    tblStoreProductMappingRecord.setFlgShowCategoryHeader(1);
                    tblStoreProductMappingRecord.setFlgMakeFrameVisible(1);
                    tblStoreProductMappingRecord.setFlgMakeRowVisible(1);
                    catSet.add(tblStoreProductMappingRecord.getCatID());
                } else {
                    tblStoreProductMappingRecord.setFlgShowCategoryHeader(0);
                    tblStoreProductMappingRecord.setFlgMakeFrameVisible(1);
                    tblStoreProductMappingRecord.setFlgMakeRowVisible(1);
                }
            }*/
        }
       /* Iterator<TblStoreProductMappingForDisplay> crunchifyIteratornew = tblStoreProductMappingListtemp.iterator();
        if (tblStoreProductMappingListtemp != null && tblStoreProductMappingListtemp.size() > 0) {

            while (crunchifyIteratornew.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIteratornew.next();

                tblStoreProductMappingListForDisplay.add(tblStoreProductMappingRecord);
            }
        }*/
    }

    public void fnMarkFirstProductCategoryRowVisibleCollapse(int CategoryID) {


        Iterator<TblStoreProductMappingForDisplay> crunchifyIterator = tblStoreProductMappingListtemp.iterator();
        if (tblStoreProductMappingListtemp != null && tblStoreProductMappingListtemp.size() > 0) {
            int catid = 0;
            Set catSet = new HashSet();

            while (crunchifyIterator.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIterator.next();
                if (tblStoreProductMappingRecord.getCatID() == CategoryID) {
                    if (!catSet.contains(tblStoreProductMappingRecord.getCatID())) {
                        tblStoreProductMappingRecord.setFlgShowCategoryHeader(1);
                        tblStoreProductMappingRecord.setFlgMakeFrameVisible(0);
                        tblStoreProductMappingRecord.setFlgMakeRowVisible(0);
                        catSet.add(tblStoreProductMappingRecord.getCatID());
                    } else {
                        // tblStoreProductMappingRecord.setFlgShowCategoryHeader(0);
                        tblStoreProductMappingRecord.setFlgMakeFrameVisible(0);
                        tblStoreProductMappingRecord.setFlgMakeRowVisible(0);
                    }
                }
            }

           /* while (crunchifyIterator.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIterator.next();


                if (CategoryID==tblStoreProductMappingRecord.getCatID()) {
                    tblStoreProductMappingRecord.setFlgShowCategoryHeader(1);
                    tblStoreProductMappingRecord.setFlgMakeFrameVisible(0);
                    tblStoreProductMappingRecord.setFlgMakeRowVisible(1);
                    catSet.add(tblStoreProductMappingRecord.getCatID());
                } else {
                    tblStoreProductMappingRecord.setFlgShowCategoryHeader(0);
                    tblStoreProductMappingRecord.setFlgMakeFrameVisible(0);
                    tblStoreProductMappingRecord.setFlgMakeRowVisible(0);
                }
            }*/
        }
       /* Iterator<TblStoreProductMappingForDisplay> crunchifyIteratornew = tblStoreProductMappingListtemp.iterator();
        if (tblStoreProductMappingListtemp != null && tblStoreProductMappingListtemp.size() > 0) {

            while (crunchifyIteratornew.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIteratornew.next();

                tblStoreProductMappingListForDisplay.add(tblStoreProductMappingRecord);
            }
        }*/
    }


    public void searchForProdcutsBasedOnVariousConditions(LinkedHashMap<String, Integer> hmapCatSelecteListForFiter, int flgRepSKUnew, int flgPrioritySKUnew, int flgSchemePrdnew) {
        tblStoreProductMappingListForDisplay.clear();
        if (hmapFilterProductList != null) {
            hmapFilterProductList.clear();
        }
        tblStoreProductMappingListtemp.clear();
        for (TblStoreProductMappingForDisplay tblStoreProductMappingDisplaynew : tblStoreProductMappingList) {
            if (hmapCatSelecteListForFiter != null && hmapCatSelecteListForFiter.size() > 0) {
                LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>(hmapCatSelecteListForFiter);
                Set set2 = map.entrySet();
                Iterator iterator = set2.iterator();
                while (iterator.hasNext()) {
                    Map.Entry me2 = (Map.Entry) iterator.next();
                    if (tblStoreProductMappingDisplaynew.getCategoryDesc().toLowerCase().equals(me2.getKey().toString().toLowerCase())) {
                        tblStoreProductMappingListtemp.add(tblStoreProductMappingDisplaynew);
                    }
                }
            }

        }

        tblStoreProductMappingListtemp = stream(tblStoreProductMappingListtemp).orderBy(p -> p.getCatID()).toList();


        fnMarkFirstProductCategoryRowVisible();
        orderAdapter.updateList(tblStoreProductMappingListForDisplay);

    }
    void getDistribtrList() {
        //mDataSource.open();

        Distribtr_list_alert = mDataSource.getSuplierDataMstr();
        //mDataSource.close();
        for (int i = 0; i < Distribtr_list_alert.length; i++) {
            String value = Distribtr_list_alert[i];
            DbrSelNodeId = value.split(Pattern.quote("^"))[0];
            DbrSelNodeType = value.split(Pattern.quote("^"))[1];
            DbrSelName = value.split(Pattern.quote("^"))[2];
            //flgReMap=Integer.parseInt(value.split(Pattern.quote("^"))[3]);

            hmapDistrbtrList.put(DbrSelName, DbrSelNodeId + "^" + DbrSelNodeType);
            DbrSelArray.add(DbrSelName);
        }

    }

    private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialogGetStores;

        public FullSyncDataNow(ProductEntryFormCopyForDistributorEntry activity) {
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
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }

            try
            {

                  /*  task2 = new SyncXMLfileData(DistributorCheckInSecondActivity.this);
                    task2.execute();*/
                String routeID=mDataSource.GetActiveRouteIDSunil();
                Intent mMyServiceIntent = new Intent(ProductEntryFormCopyForDistributorEntry.this, SyncJobService.class);  //is any of that needed?  idk.
                mMyServiceIntent.putExtra("routeID", routeID);//
                mMyServiceIntent.putExtra("storeID", "0");
                mMyServiceIntent.putExtra("whereTo", "Regular");//
                mMyServiceIntent.putExtra("StoreVisitCode", "NA");//
                mMyServiceIntent.putExtra("tmpInvoicePDACode", "NA");//

                SyncJobService.enqueueWork(ProductEntryFormCopyForDistributorEntry.this, mMyServiceIntent);

            }
            catch(Exception e)
            {

            }

            try
            {
                int checkNoFiles=mDataSource.counttblDistributorSavedData();
                if(checkNoFiles==1)
                {
                    showNoConnAlertforLocalDataSaved();
                }
                else
                {
                    if(from.equals("DayStart"))
                    {
                        if ((CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1)) {
                            Intent intent = new Intent(ProductEntryFormCopyForDistributorEntry.this, WarehouseCheckInSecondActivity.class);
                            intent.putExtra("token", imei);
                            intent.putExtra("fDate", fDate);
                            intent.putExtra("From", "DayStart");
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Intent i = new Intent(ProductEntryFormCopyForDistributorEntry.this, AllButtonActivity.class);
                            i.putExtra("token", imei);
                            startActivity(i);
                            finish();
                        }
                    }
                    else {
                        Intent i = new Intent(ProductEntryFormCopyForDistributorEntry.this, AllButtonActivity.class);
                        i.putExtra("token", imei);
                        startActivity(i);
                        finish();
                    }
                }
            }
            catch(Exception e)
            {

            }
        }
    }

    public void showNoConnAlertforLocalDataSaved() {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ProductEntryFormCopyForDistributorEntry.this);
        alertDialogNoConn.setTitle(R.string.genTermNoDataConnection);
        alertDialogNoConn.setMessage(R.string.genlocaldataMsg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(ProductEntryFormCopyForDistributorEntry.this, LauncherActivity.class);
                        i.putExtra("token", imei);

                        startActivity(i);
                        finish();
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }
}

