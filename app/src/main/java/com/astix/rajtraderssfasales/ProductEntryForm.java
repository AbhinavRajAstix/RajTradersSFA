package com.astix.rajtraderssfasales;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
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

import com.astix.Common.CommonInfo;
import com.allana.truetime.TimeUtils;

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
import com.astix.rajtraderssfasales.adapter.ViewCreatedCartonAdapter;
import com.astix.rajtraderssfasales.adapter.ViewSubCreatedCartonAdapter;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.location.LocationInterface;
import com.astix.rajtraderssfasales.location.LocationRetreivingGlobal;
import com.astix.rajtraderssfasales.merchandizing.PicAfterMerchandising;
import com.astix.rajtraderssfasales.model.StoreSummaryModel;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreVisitHistory;
import com.astix.rajtraderssfasales.model.TblInvoiceDetailsServer;
import com.astix.rajtraderssfasales.model.TblOnlyCategoryMasterForRetriving;
import com.astix.rajtraderssfasales.model.TblProductCategoryUOMTypeList;
import com.astix.rajtraderssfasales.model.TblProductTypeMasterForRetriving;
import com.astix.rajtraderssfasales.model.TblSchemePerProduct;
import com.astix.rajtraderssfasales.model.TblStoreProductMappingForDisplay;
import com.astix.rajtraderssfasales.model.TblUOMMapping;
import com.astix.rajtraderssfasales.model.TblUOMMaster;
import com.astix.rajtraderssfasales.model.TbltmpInvoiceHeaderCartonPlusOrder;
import com.astix.rajtraderssfasales.sync.SyncJobService;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.CartonProductEntry;
import com.astix.rajtraderssfasales.utils.PreferenceManager;
import com.astix.rajtraderssfasales.utils.PrioritySKUDialog;
import com.astix.rajtraderssfasales.utils.ProductInfoDialog;
import com.astix.rajtraderssfasales.utils.RandomString;
import com.astix.rajtraderssfasales.utils.StoreHistoryDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import static android.os.Build.VERSION.SDK_INT;
import static br.com.zbra.androidlinq.Linq.stream;

public class ProductEntryForm extends BaseActivity implements View.OnClickListener, CategoryCommunicatorOrderFilter, focusLostCalled, InterfaceShowProductRelatedScheme, InterfaceRetrofitProductRecyclerView, LocationInterface,InterfaceDiscountCalculationOnUOMChange,InterfaceWhenCartonBoxClicked/*,GetCartonPrdsInterface */ {//, InterfaceContactUpdate
    String StContactNo="NA";
    PreferenceManager mPreferencesManager;
    LinearLayout rv_dailandorder;
    TextView tv_ClickHereForDialNumberAndProceedForOrderEntry;
    int flgAppBtnClicked=0;
    SharedPreferences  sharedPrefReport;
    List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeLists=new ArrayList<TblProductCategoryUOMTypeList>();
    LinkedHashMap<String, String> hmapPrdctOrderQtyForDiscountApplyOverAll=new  LinkedHashMap<String, String>();
    List<TblStoreProductMappingForDisplay> tblTCOrderStoreProductDiscountApplicable = new ArrayList<TblStoreProductMappingForDisplay>();
    double TotalAllLevelDiscount=0.0;
    TextView tvProductLevelDiscount;
    public int IsDiscountApplicable = 0;
    LinearLayout ll_GiveOverAllDiscountSection;
    public int flgInCarton = 0;
    View viewContactSection;
    int OnResumeCheckWithNoOrderClick = 0;
    LinearLayout ll_ContactUpdateSection;
    TextView tvContactNoCorrection;
    Button btnEditContact;
    ImageView  iv2, iv1;
    ExpandableCategoryAdapter cardArrayAdapter;
    int flgRepSKU = 0;
    int flgPrioritySKU = 0;
    int flgSchemePrd = 0;
    int flgAllCategory = 0;

    private int flgIsPicsAllowed = 1;
    String storeVisitCode = "";
    String storePics = "";

    LinearLayout llSchemeAppl, llOrdHistory, llPrioritySKU;
    int countSubmitClicked = 0;
    public String VisitTimeInSideStore = "NA";
    public int flgLocationServicesOnOffOrderReview = 0;
    public int flgGPSOnOffOrderReview = 0;
    public int flgNetworkOnOffOrderReview = 0;
    public int flgFusedOnOffOrderReview = 0;
    public int flgInternetOnOffWhileLocationTrackingOrderReview = 0;
    public int flgRestartOrderReview = 0;
    public int flgStoreOrderOrderReview = 0;
    public String selectedCloaseCallComment = "";
    public int selectedCloaseCallOptionID = 0;
    public int butClickForGPS = 0;
    public HashMap<String, Integer> hmapCloseCallOption = new HashMap<String, Integer>();
    List<TblStoreProductMappingForDisplay> tblStoreProductMappingList = new ArrayList<TblStoreProductMappingForDisplay>();
    List<TblStoreProductMappingForDisplay> tblStoreProductMappingListtemp = new ArrayList<TblStoreProductMappingForDisplay>();
    List<TblStoreProductMappingForDisplay> tblStoreProductMappingListForDisplay = new ArrayList<>();
    List<TblStoreProductMappingForDisplay> tblStoreProductMappingListForSearching = new ArrayList<TblStoreProductMappingForDisplay>();


    List<TblStoreProductMappingForDisplay> tblStoreProductMappingListTCOrders = new ArrayList<TblStoreProductMappingForDisplay>();
    List<TblStoreProductMappingForDisplay> tblStoreProductMappingListFinalOrderWithoutTC = new ArrayList<TblStoreProductMappingForDisplay>();


    public int RegionID = 0;
    OrderAdapterProdcutEntry orderAdapter;
    LinearLayout ll_rcylContainer;
    String SelectedCategoryForSchemeFileter = "All";
    int SelectedCategoryIDForSchemeFileter = 0;
    public String[] arrSchId;
    public String SchemeDesc = "NA";
    HashMap<String, String> hmapProductLODQty = new HashMap<String, String>();
    //hmapSchemeIDandDescr= key=SchId,val=SchDescr
    HashMap<String, HashMap<String, HashMap<String, String>>> hmapProductCurrentAppliedSchemeIDandDescrAndColorCode = new HashMap<String, HashMap<String, HashMap<String, String>>>();

    //hmapSchemeIDandDescr= key=SchId,val=SchDescr
    HashMap<String, String> hmapSchemeIDandDescr = new HashMap<String, String>();
    LinkedHashMap<String, ArrayList<String>> hmapProductMinMax;

    //hmapMinDlvrQty= key =ProductID         value=MinQty
    LinkedHashMap<String, Integer> hmapMinDlvrQty = new LinkedHashMap<String, Integer>();
    //hmapMinDlvrQty= key =ProductID         value=QPBT
    LinkedHashMap<String, String> hmapMinDlvrQtyQPBT = new LinkedHashMap<String, String>();


    //hmapMinDlvrQty= key =ProductID         value=QPBT
    LinkedHashMap<String, String> hmapMinDlvrQtyQPAT = new LinkedHashMap<String, String>();


    //hmapMinDlvrQty= key =ProductID         value=QPTaxAmount
    LinkedHashMap<String, String> hmapMinDlvrQtyQPTaxAmount = new LinkedHashMap<String, String>();

    HashMap<String, String> hmapPrdctVolRatTax = new HashMap<String, String>();
    LinkedHashMap<String, String> hmapSchmDscrptnAndBenfit;
    HashMap<String, String> hmapPrdctFreeQty = new HashMap<String, String>();
    HashMap<String, String> hmapPrdctFreeQtyFinal = new HashMap<String, String>();
    //hmapProductVolumePer= key =ProductID         value=Per
    HashMap<String, String> hmapProductVolumePer = new HashMap<String, String>();
    //hmapProductDiscountPercentageGive= key =ProductID         value=DiscountPercentageGivenOnProduct
    HashMap<String, String> hmapProductDiscountPercentageGive = new HashMap<String, String>();
    HashMap<String, String> hmapProductFlatDiscountGive = new HashMap<String, String>();
    HashMap<String, String> hmapProductDiscountPercentageGiveFinal = new HashMap<String, String>();
    HashMap<String, String> hmapProductFlatDiscountGiveFinal = new HashMap<String, String>();
    ArrayList<String> arredtboc_OderQuantityFinalSchemesToApply;
    ArrayList<String> productFullFilledSlabGlobal = new ArrayList<String>();
    String defaultValForAlert;
    boolean disValClkdOpenAlert = false;
    private boolean alrtStopResult = false;
    HashMap<String, String> hmapProductVolumePerFinal = new HashMap<String, String>();
    boolean alertOpens = false;
    int alrtObjectTypeFlag = 1; //1=spinner,edittext=0;
    HashMap<String, String> hmapPrdctIdPrdctDscnt = new HashMap<String, String>();

    List<TblStoreProductAppliedSchemesBenifitsRecords> tblStoreProductAppliedSchemesBenifitsRecordsList = new ArrayList<>();
    List<TblProductMappedWithSchemeSlabApplied> tblProductMappedWithSchemeSlabAppliedArrayList = new ArrayList<>();
    List<TblStoreProductAddOnSchemeApplied> tblStoreProductAddOnSchemeAppliedArrayList = new ArrayList<>();
    List<TblSchemeSlabBucketProductMapping> tblSchemeSlabBucketProductMappingList = new ArrayList<>();
    List<TblSchemeSlabBenefitsBucketDetails> tblSchemeSlabBenefitsBucketDetailsList = new ArrayList<>();
    List<TblSchemeSlabBenefitsValueDetail> tblSchemeSlabBenefitsValueDetailList = new ArrayList<>();
    List<TblSchemeSlabBenefitsProductMappingDetail> tblSchemeSlabBenefitsProductMappingDetailList = new ArrayList<>();
    List<TblAlrtVal> tblAlrtValList = new ArrayList<>();

    HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMapping = new HashMap<Integer, List<TblUOMMapping>>();
    HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMappingCOrderHistory = new HashMap<Integer, List<TblUOMMapping>>();
    HashMap<String, String> hmapProductFinalConversionUnitOnLoad = new HashMap<String, String>();
    LinkedHashMap<String, String> hmapPerBaseQty;
    LinkedHashMap<String, Integer> hmapProductSuggestedQty;
    LinkedHashMap<String, Integer> hmapProductFocusedBrand;

    LinkedHashMap<String, Integer> hmapProductReplenishment;

    HashMap<String, String> hmapProductMeasureID = new HashMap<String, String>();
    HashMap<String, String> hmapProductMeasureIDBaseQty = new HashMap<String, String>();

    EditText ed_LastEditextFocusd;
    public String ProductIdOnClickedEdit;
    public String CtaegoryIddOfClickedView;
    LinkedHashMap<String, Integer> hmapCatSelecteList = new LinkedHashMap<String, Integer>();
    ArrayList<TblProductTypeMasterForRetriving> tblProductTypeMasterForRetrivingList = new ArrayList<TblProductTypeMasterForRetriving>();
    public int flgPriceMissing = 0;
    public String PriceMissingPrdName = "";

    public int CurrentCategorySelectedPosition = -1;

    public int isbtnExceptionVisible = 0;
    String ProductIdOnClickedControl123 = null;
    //Invoice TextView
    public int flgIsAnySchemeMappedAgainstStore = 0;
    public TextView tv_NetInvValue, tvTAmt, tvDis, tv_GrossInvVal, tvFtotal, tvAddDisc, tv_NetInvAfterDiscount, tvAmtPrevDueVAL, etAmtCollVAL, tvAfterTaxValue, tvPreAmtOutstandingVALNew, tvAmtOutstandingVAL, tvCredAmtVAL, tvINafterCredVAL, textView1_CredAmtVAL_new, tvNoOfCouponValue, txttvCouponAmountValue;
    TextView txt_Lststock;


    String lastStockDate = "";
    RecyclerView rv_prdct_detal, rvStoreVisitHistory, rvCartonData;
    public TableLayout tbl1_dyntable_For_ExecutionDetails;
    public TableLayout tbl1_dyntable_For_OrderDetails;
    String progressTitle;
    ProgressDialog mProgressDialog;
    //Database
    //Intent Data
    public String storeID, imei, date, pickerDate, SN;
    int flgOrderType = 0;
    //Custom
    CustomKeyboard mCustomKeyboardNum, mCustomKeyboardNumWithoutDecimal;

    //Initialize fields
    ImageView img_ctgry, img_return, btn_bck;
    EditText et_Disc, ed_search;
    TextView tvDisc, txt_RefreshOdrTot;
    Button btn_InvoiceReview, btn_OrderReview, btn_ShowSchemeApplicableProducts, btn_ShowAllProducts, btn_NoOrder;
    LinearLayout ll_scheme_detail, ll2, ll3;
    RelativeLayout rlTCOrderMain, rlCartons;

    //hmap for Products Info and Saving
    LinkedHashMap<String, String> hmapFilterProductList;
    LinkedHashMap<String, String> hmapFilterProductListSchemes = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapctgry_details;
    HashMap<String, String> hmapProductIdStock;
    ArrayList<HashMap<String, String>> arrLstHmapPrdct;
    HashMap<String, String> hmapProductStandardRateBeforeTax;
    HashMap<String, String> hmapProductMRP;
    HashMap<String, Integer> hmapDistPrdctStockCount;
    HashMap<String, Integer> hmapVanPrdctStockCount;// = new HashMap<String, Integer>();
    HashMap<String, String> hmapProductVatTaxPerventage;
    HashMap<String, String> hmapCtgryPrdctDetail;
    HashMap<String, String> hmapPrdctIdPrdctName;
    HashMap<String, String> hmapProductStandardRate;// = new HashMap<String, String>();
    HashMap<String, String> hmapProductTaxValue;

    HashMap<String, String> hmapProductTaxValueWithTCOrder;

    HashMap<String, String> hmapProductSelectedUOMId;
    HashMap<String, String> hmapLineValBfrTxAftrDscnt;

    HashMap<String, String> hmapLineValBfrTxAftrDscntWithTCOrder=new HashMap<String, String>();


    HashMap<String, String> hmapLineValAftrTxAftrDscnt;
    HashMap<String, String> hmapLineValAftrTxAftrDscntWithTCOrder=new HashMap<String, String>();
    HashMap<String, String> hmapProductIdLastStock;
    LinkedHashMap<String, String> hampGetLastProductExecution;
    HashMap<String, String> hmapPrdctOdrQty;
    LinkedHashMap<String, String> hmapPrdctCartonID = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapPrdctQtyInCartonID = new LinkedHashMap<String, String>();
    HashMap<String, String> hmapProductImg;// = new HashMap<String, String>();
    HashMap<String, String> hmapOrderQtyDataToShow;
    HashMap<String, String> hmapflgPicsOrCases;
    HashMap<String, String> hmapflgTeleCallerProduct;

    HashMap<String, String> hmapflgTeleCallerProductQty;
    HashMap<String, String> hmapflgUserEditedProduct;

    HashMap<String, String> hmapProductTeleCallerID;

    //ModelClass
    public int PriceApplyDiscountLevelType = 0, flgTransferStatus = 0;
    ;

    public ProductFilledDataModel prdctModelArrayList = new ProductFilledDataModel();

    public int StoreCurrentStoreType = 0, chkflgInvoiceAlreadyGenerated = 0;
    public String strFinalAllotedInvoiceIds = "NA", TmpInvoiceCodePDA = "NA", StoreVisitCode = "NA", defaultCatName_Id = "0", previousSlctdCtgry, distID = "", VisitTypeStatus = "1";
    List<String> categoryNames;


    //Scheme Variable Declarations Starts Here

    ArrayList<HashMap<String, String>> arrayListSchemeSlabDteail = new ArrayList<HashMap<String, String>>();
    // hmapSchemeIdStoreID= key=SchemeId value StoreId
    HashMap<String, String> hmapSchemeIdStoreID = new HashMap<String, String>();

    //hmapSchmeSlabIdSchemeId= key =SchemeSlabId         value=SchemeID
    HashMap<String, String> hmapSchmeSlabIdSchemeId = new HashMap<String, String>();
    //hmapSchmeSlabIdSlabDes= key =SchemeSlabId         value=SchemeSlabDes
    HashMap<String, String> hmapSchmeSlabIdSlabDes = new HashMap<String, String>();
    //hmapSchmeSlabIdBenifitDes= key = SchemeSlabId        value=BenifitDescription
    HashMap<String, String> hmapSchmeSlabIdBenifitDes = new HashMap<String, String>();

    //hmapCtgryPrdctDetail= key=prdctId,val=PrdString Applied Schemes,Slabs and other details
    HashMap<String, String> hmapProductRelatedSchemesList = new HashMap<String, String>();

    //hmapSlabIDArrRowID= key=SlabID,val=RowID Applied Schemes,Slabs and other details
    LinkedHashMap<Integer, ArrayList<Integer>> hmapSlabIDArrRowID = new LinkedHashMap<Integer, ArrayList<Integer>>();

    //hmapRowIDProductIds= key=RowID,val=ProductIds Applied Schemes,Slabs and other details
    LinkedHashMap<Integer, ArrayList<Integer>> hmapRowIDProductIds = new LinkedHashMap<Integer, ArrayList<Integer>>();

    //hmapCtgryPrdctDetail= key=prdctId,val=PrdString Applied ADD On Schemes,Slabs and other details
    HashMap<String, String> hmapProductAddOnSchemesList = new HashMap<String, String>();


    //hmapSchemeSlabIDBucketTypeBucketValueForSchemeType1And3= key=SchemeSlabID,val=BucketType^BucketValue^BucketValueType Applied Schemes,Slabs and other details
    LinkedHashMap<Integer, String> hmapSchemeSlabIDBucketTypeBucketValueForSchemeType1And3 = new LinkedHashMap<Integer, String>();

    //hmapProductQtyVolumeValue= key=ProductID,val=Qty^Volume^Value Applied Schemes,Slabs and other details
    HashMap<Integer, String> hmapProductQtyVolumeValue = new HashMap<Integer, String>();


    //Scheme Variable Declarations Ends Here
   /* @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putParcelable("MyInterface", prdctModelArrayList);

        // etc.
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        prdctModelArrayList = savedInstanceState.getParcelable("MyInterface");
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_entry_form);

        mProgressDialog = new ProgressDialog(ProductEntryForm.this);
        mPreferencesManager= PreferenceManager.getInstance(this);
        initializeFields();

        getDataFromatabase();
        setInvoiceTableView();
        loadPurchaseCartonDetail();

        /*
        orderBookingTotalCalc();*/

        if (flgIsAnySchemeMappedAgainstStore == 1) {

            hmapProductRelatedSchemesList = mDataSource.fnProductRelatedSchemesList(storeID);
            hmapProductAddOnSchemesList = mDataSource.fnProductADDOnScheme();
            getSchemeSlabDetails();
        }
        if (flgIsAnySchemeMappedAgainstStore == 1) {
            //First Calculate Scheme and the do Order Calculation
            isbtnExceptionVisible = 0;
            if (ProductIdOnClickedControl123 != null) {
                getOrderData(ProductIdOnClickedControl123);
            }
            //orderBookingTotalCalcOnLoadTCCalculation();
            orderBookingTotalCalc();
        } else {
            // orderBookingTotalCalcOnLoadTCCalculation();
            orderBookingTotalCalc();
        }


    }

    public void fnCheckNewSchemeIDsAppliedAfterValueChange(String SchIdsCompleteListOnProductID, String ProductIdOnClicked, int flgAddON) {
        arredtboc_OderQuantityFinalSchemesToApply = new ArrayList<String>();
        //Example :-1075_1_0_1!1026$1^1|1^23^1^10^0@1025$1^1|1^22^1^20^0@1022$1^1|1^19^5^5^0@1020$1^1|1^17^3^4^0@1019$1^1|1^16^1^12^0@1018$1^1|1^15^1^10^0@1017$1^1|1^14^1^12^0
        String valForVolumetQTYToMultiply = "0";
        productFullFilledSlabGlobal = new ArrayList<String>();
        if (SchIdsCompleteListOnProductID != null) {
            String[] arrSchIdsListOnProductID = SchIdsCompleteListOnProductID.split("#");
            for (int pSchIdsAppliCount = 0; pSchIdsAppliCount < arrSchIdsListOnProductID.length; pSchIdsAppliCount++) {
                //35_1_0_2 where 35=shcemId, 1= SchAppRule, 2= schemeTypeId
                String schOverviewDetails = arrSchIdsListOnProductID[pSchIdsAppliCount].split("!")[0];   //Example :-1075_1_0_1
                String schOverviewOtherDetails = arrSchIdsListOnProductID[pSchIdsAppliCount].split("!")[1]; //Example :-1026$1^1|1^23^1^10^0@1025$1^1|1^22^1^20^0@1022$1^1|1^19^5^5^0@1020$1^1|1^17^3^4^0@1019$1^1|1^16^1^12^0@1018$1^1|1^15^1^10^0@1017$1^1|1^14^1^12^0
                int schId = Integer.parseInt(schOverviewDetails.split("_")[0]);                           //Example :-1075
                int schAppRule = Integer.parseInt(schOverviewDetails.split("_")[1]);                                                                                        //Example :-1
                int schApplicationId = Integer.parseInt(schOverviewDetails.split("_")[2]);                                                              //Example :-0
                int SchTypeId = Integer.parseInt(schOverviewDetails.split("_")[3]);                                                                                           //Example :-1 // 1=Check Combined Skus, 2=Bundle,3=Simple with Check on Individual SKU
                String[] arrschSlbIDsOnSchIdBasis = schOverviewOtherDetails.split("@");   //Split for multiple slabs Example :-1026$1^1|1^23^1^10^0, 1025$1^1|1^22^1^20^0
                boolean bucketCndtnSchemeFullFill = false;
                int exitWhenSlabToExit = 0;




                if (hmapSchemeIdStoreID.containsKey("" + schId)) {

                    int prdOqty=0;//For Showing Gap
                /*   for (int pSchSlbCount = 0; pSchSlbCount < arrschSlbIDsOnSchIdBasis.length; pSchSlbCount++) {
                        int schSlabId = Integer.parseInt((arrschSlbIDsOnSchIdBasis[pSchSlbCount]).split(Pattern.quote("$"))[0]); //Exmaple Slab ID:- 1026
                        String schSlabOtherDetails = arrschSlbIDsOnSchIdBasis[pSchSlbCount].split(Pattern.quote("$"))[1]; //Exmaple Slab OtherDetails:- 1^1|1^23^1^10^0
                        String[] arrSchSlabBuckWiseDetails = schSlabOtherDetails.split(Pattern.quote("~")); //Example Split For Multiple Buckets (OR Condition)
                        for (int pSchSlbBuckCnt = 0; pSchSlbBuckCnt < arrSchSlabBuckWiseDetails.length; pSchSlbBuckCnt++) {
                            String schSlbBuckDetails = arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[0]; // Eaxmple:-1^1
                            String schSlbBuckOtherDetails = arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[1];  // Eaxmple:-1^23^1^10^0
                            int schSlbBuckId = Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[0]);  //Exmaple Slab Bucket ID:- 1
                            int schSlbBuckCnt = Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[1]);  //Example Number of Buckets under this Slab, Count:-1

                            String[] arrSubBucketDetails = schSlbBuckOtherDetails.split(Pattern.quote("*"));  //Example Split For Multiple Sub Buckets(AND Condition)
                            String[] arrMaintainDetailsOfBucketConditionsAgainstBuckId = new String[schSlbBuckCnt];  //Example Length of Buckes in Slab and which condition is true in case of OR
                            int schSlbSubRowID = Integer.parseInt(schSlbBuckOtherDetails.split(Pattern.quote("^"))[1]);
                            ArrayList<String> arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation = new ArrayList<String>();
                            if (SchTypeId == 1) {
                                  *//* if (schSlabSubBucketType == 1) {
                                        arrProductIDMappedInSchSlbSubBukRowId.add(ProductIdOnClicked);
                                    } else {*//*
                                arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation = mDataSource.fectProductIDMappedInSchSlbSubBukRowIdTemp(schSlbSubRowID);
                                // }
                            }
                            if (SchTypeId == 2) {

                                arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation = mDataSource.fectProductIDMappedInSchSlbSubBukRowIdTemp(schSlbSubRowID);


                            }
                            if (SchTypeId == 3) {
                                arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation.add(ProductIdOnClicked);
                            }

                            for(String pd:arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation)
                            {
                                if(prdctModelArrayList.getHmapPrdctOrderQty()!=null && prdctModelArrayList.getHmapPrdctOrderQty().size()>0 && prdctModelArrayList.getHmapPrdctOrderQty().containsKey(pd))
                                {
                                    prdOqty=prdOqty+Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(pd));
                                    int tcord=0;
                                    if(hmapflgTeleCallerProductQty!=null && hmapflgTeleCallerProductQty.size()>0 && hmapflgTeleCallerProductQty.containsKey(""+pd)) {
                                        tcord = Integer.parseInt(prdctModelArrayList.getPrdctOrderQtyWithTCOrder(pd));
                                        prdOqty = prdOqty + tcord;

                                    }


                                }
                            }

                            for(String pd:arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation) {
                                if (prdctModelArrayList.getHmapPrdctOrderQty() != null && prdctModelArrayList.getHmapPrdctOrderQty().size() > 0 && prdctModelArrayList.getHmapPrdctOrderQty().containsKey(pd)) {
                                    prdctModelArrayList.removhmapProductCurrentAppliedSchemeIDandDescrAndColorCode(pd);


                                    prdctModelArrayList.setHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(mDataSource.fnSchemeDescAndSlabDetails(pd, "" + schId, 0, prdOqty));

                                }
                            }

                           // break;
                        }


                    }
*/

                    boolean bucketCndtnFullFillisReally = false;
                    for (int pSchSlbCount = 0; pSchSlbCount < arrschSlbIDsOnSchIdBasis.length; pSchSlbCount++) {
                        //Exmaple Slab:- 1026$1^1|1^23^1^10^0
                        int schSlabId = Integer.parseInt((arrschSlbIDsOnSchIdBasis[pSchSlbCount]).split(Pattern.quote("$"))[0]); //Exmaple Slab ID:- 1026
                        String schSlabOtherDetails = arrschSlbIDsOnSchIdBasis[pSchSlbCount].split(Pattern.quote("$"))[1]; //Exmaple Slab OtherDetails:- 1^1|1^23^1^10^0
                        String[] arrSchSlabBuckWiseDetails = schSlabOtherDetails.split(Pattern.quote("~")); //Example Split For Multiple Buckets (OR Condition)
                        for (int pSchSlbBuckCnt = 0; pSchSlbBuckCnt < arrSchSlabBuckWiseDetails.length; pSchSlbBuckCnt++) {
                            String schSlbBuckDetails = arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[0]; // Eaxmple:-1^1
                            String schSlbBuckOtherDetails = arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[1];  // Eaxmple:-1^23^1^10^0
                            int schSlbBuckId = Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[0]);  //Exmaple Slab Bucket ID:- 1
                            int schSlbBuckCnt = Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[1]);  //Example Number of Buckets under this Slab, Count:-1

                            String[] arrSubBucketDetails = schSlbBuckOtherDetails.split(Pattern.quote("*"));  //Example Split For Multiple Sub Buckets(AND Condition)
                            String[] arrMaintainDetailsOfBucketConditionsAgainstBuckId = new String[schSlbBuckCnt];  //Example Length of Buckes in Slab and which condition is true in case of OR
                            int schSlbSubRowID = Integer.parseInt(schSlbBuckOtherDetails.split(Pattern.quote("^"))[1]);
                            // variables for calculating total sub bucket
                            ArrayList<String> productFullFilledSlab = new ArrayList<String>();
                            ArrayList<String> schSlabRowIdFullFilledSlab = new ArrayList<String>();
                            ArrayList<String> productFullFilledSlabForInvoice = new ArrayList<String>();
                            int totalProductQnty = 0;
                            double totalProductVol = 0.0;

                            double totalProductVal = 0.0;
                            int totalProductLine = 0;
                            double totalInvoice = 0.0;

                            //product invoice
                            for (Map.Entry<String, String> entryProduct : prdctModelArrayList.getHmapPrdctOrderQty().entrySet()) {
                                if (prdctModelArrayList.getHmapPrdctOrderQty().containsKey(entryProduct.getKey())) {
                                    if (Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(entryProduct.getKey())) > (0)) {


                                        int curntProdQty = 0;
                                        curntProdQty = Integer.parseInt(entryProduct.getValue());
                                        if (!prdctModelArrayList.getPrdctOrderQty(ProductIdOnClicked).equals("")) {

                                            int tcord=0;
                                            if(!prdctModelArrayList.getPrdctOrderQtyWithTCOrder(ProductIdOnClicked).equals(""))
                                                tcord=Integer.parseInt(prdctModelArrayList.getPrdctOrderQtyWithTCOrder(ProductIdOnClicked));
                                            curntProdQty = Integer.parseInt(prdctModelArrayList.getPrdctOrderQty(ProductIdOnClicked)) + tcord;
                                        }


                                        String curntProdVolumeRate = hmapPrdctVolRatTax.get(entryProduct.getKey());
                                        Double curntProdRate = Double.parseDouble(curntProdVolumeRate.split(Pattern.quote("^"))[1]);

                                        Double currentProductOverAllPriceQtywise = curntProdRate * curntProdQty;
                                        totalInvoice = totalInvoice + currentProductOverAllPriceQtywise;
                                        productFullFilledSlabForInvoice.add(entryProduct.getKey());
                                    }
                                }

                            }
                            // end product invoice
                            //sub bucket starts here
                            LinkedHashMap<String, String> hmapSubBucketDetailsData = new LinkedHashMap<String, String>();
                                LinkedHashMap<String, String> hmapSubBucketTotalQntty = new LinkedHashMap<String, String>();
                            LinkedHashMap<String, String> hmapSubBucketTotalValue = new LinkedHashMap<String, String>();
                            LinkedHashMap<String, String> hmapSubBucketTotalVolume = new LinkedHashMap<String, String>();

                            for (int cntSubBucket = 0; cntSubBucket < arrSubBucketDetails.length; cntSubBucket++) {
                                // Eaxmple:-1^23^1^10^0
                                int schSlbSubBuckID = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[0]); //Slab Sub BucketID Eaxmple:-1  subBucketId
                               // int schSlbSubRowID = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[1]);  //Slab Sub Bucket RowID Eaxmple:-23  rowid
                                int schSlabSubBucketType = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[2]);  ///Slab Sub Bucket Type Eaxmple:-1

                                Double schSlabSubBucketValue = Double.parseDouble(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[3]);  ///Slab Sub Bucket Value Eaxmple:-10
                                int schSubBucketValType = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[4]); ///Slab Sub Bucket Value Type Eaxmple:-0


                                int totalOderQtyProductsAgainstRowId = 0;
                                Double totalVolProductsAgainstRowId = 0.0;
                                Double totalValProductsAgainstRowId = 0.0;


                                //String[] productFullFilledSlab=new String[arrProductIDMappedInSchSlbSubBukRowId.length];
                                int positionOfProductHavingQntty = 0;
                                ArrayList<String> arrProductIDMappedInSchSlbSubBukRowId = new ArrayList<String>();

                                //IF SchTypeID==1 OR SchTypeID==2 OR SchTypeID==3  Code Starts Here To Check the Products

                                if (SchTypeId == 1) {
                                  /*  if (schSlabSubBucketType == 1) {
                                        arrProductIDMappedInSchSlbSubBukRowId.add(ProductIdOnClicked);
                                    } else {*/
                                        arrProductIDMappedInSchSlbSubBukRowId = mDataSource.fectProductIDMappedInSchSlbSubBukRowIdTemp(schSlbSubRowID);
                                   // }
                                }
                                if (SchTypeId == 2) {

                                    arrProductIDMappedInSchSlbSubBukRowId = mDataSource.fectProductIDMappedInSchSlbSubBukRowIdTemp(schSlbSubRowID);

                                }
                                if (SchTypeId == 3) {
                                    arrProductIDMappedInSchSlbSubBukRowId.add(ProductIdOnClicked);
                                }

                                //IF SchTypeID==1 OR SchTypeID==2 OR SchTypeID==3  Code Ends Here To Check the Products
                                //SlabSubBucketValType
                                //I           =Invoice Value                  Order Value After Tax
                                //G         =Gross Value                     Order Value Before Tax
                                //N         =Net Value                                         Order Value After Tax


                                if (arrProductIDMappedInSchSlbSubBukRowId.size() > 0) {


                                    for (String productMappedWithScheme : arrProductIDMappedInSchSlbSubBukRowId) {
                                        schSlabRowIdFullFilledSlab.add(productMappedWithScheme + "^" + schSlbSubRowID);
                                        productFullFilledSlab.add(productMappedWithScheme + "^" + schId);// productLine

                                        String hmapSubBucketDetailsData_Value = schId + "^" + schSlabId + "^" + schSlbBuckId + "^" + schSlabSubBucketValue + "^" + schSubBucketValType + "^" + schSlabSubBucketType + "^" + ProductIdOnClicked + "^" + valForVolumetQTYToMultiply + "^" + schSlbSubRowID + "^" + SchTypeId;
                                        hmapSubBucketDetailsData.put(productMappedWithScheme + "^" + schSlbSubRowID, hmapSubBucketDetailsData_Value);
                                        if (prdctModelArrayList.getHmapPrdctOrderQty().containsKey(productMappedWithScheme)) {
                                            if (Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(productMappedWithScheme)) > (0)) {
                                                //1. Product Quantity


                                                int oderQtyOnProd = Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(productMappedWithScheme));

                                                totalProductQnty = totalProductQnty + oderQtyOnProd;
                                                totalOderQtyProductsAgainstRowId = totalOderQtyProductsAgainstRowId + oderQtyOnProd;

                                                hmapSubBucketTotalQntty.put("" + schSlbSubRowID, "" + totalOderQtyProductsAgainstRowId);
                                                // product volume
                                                Double prodVolume = Double.parseDouble(hmapPrdctVolRatTax.get(productMappedWithScheme).split(Pattern.quote("^"))[0]);
                                                Double oderVolumeOfCurrentMapedProduct = prodVolume * oderQtyOnProd;
                                                totalProductVol = totalProductVol + oderVolumeOfCurrentMapedProduct;
                                                totalVolProductsAgainstRowId = totalVolProductsAgainstRowId + oderVolumeOfCurrentMapedProduct;

                                                hmapSubBucketTotalVolume.put("" + schSlbSubRowID, "" + totalVolProductsAgainstRowId);
                                                //product value

                                                Double prodRate = Double.parseDouble(hmapPrdctVolRatTax.get(productMappedWithScheme).split(Pattern.quote("^"))[1]);
                                                Double oderRateOfCurrentMapedProduct = prodRate * oderQtyOnProd;
                                                //oderRateOnProduct=oderRateOnProduct + oderRateOfCurrentMapedProduct;
                                                totalProductVal = totalProductVal + oderRateOfCurrentMapedProduct;
                                                totalValProductsAgainstRowId = totalValProductsAgainstRowId + oderRateOfCurrentMapedProduct;
                                                hmapSubBucketTotalValue.put("" + schSlbSubRowID, "" + totalValProductsAgainstRowId);


                                            }


                                        }


                                    }// for loops ends here productMappedWithScheme:arrProductIDMappedInSchSlbSubBukRowId


                                }// ends if(arrProductIDMappedInSchSlbSubBukRowId.size()>0)


                            } //sub bucket ends here

                            //schSlabSubBucketType
                            //1. Product Quantity
                            //5. Product Volume
                            //2. Invoice Value
                            //3. Product Lines
                            //4. Product Value
                            boolean bucketCndtnFullFill = true;
                            String stringValHmap = "";
                            String stringValHmapInvoice = "";
                            ArrayList<String> listStrValHmapForSchm2 = new ArrayList<String>();
                            if (productFullFilledSlabForInvoice != null && productFullFilledSlabForInvoice.size() > 0) {
                                for (String productIdFullFilledSlabInvoiceWithQty : productFullFilledSlabForInvoice) {
                                    if (hmapSubBucketDetailsData.containsKey(productIdFullFilledSlabInvoiceWithQty)) {
                                        stringValHmapInvoice = hmapSubBucketDetailsData.get(productIdFullFilledSlabInvoiceWithQty);
                                        String schSlabSubBucketType = stringValHmapInvoice.split(Pattern.quote("^"))[5];
                                        Double schSlabSubBucketVal = Double.valueOf(stringValHmapInvoice.split(Pattern.quote("^"))[3]);
                                        if (schSlabSubBucketType.equals("2")) {
                                            if (totalInvoice >= schSlabSubBucketVal) {
                                                mDataSource.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabInvoiceWithQty, "" + schSlabId, "" + schId, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);
                                                break;
                                            } else {
                                                mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                bucketCndtnFullFill = false;
                                                stringValHmapInvoice = "";
                                                break;
                                            }

                                        } else {
                                            stringValHmapInvoice = "";
                                            break;
                                        }
                                    }

                                }
                            }
                            if (schSlabRowIdFullFilledSlab != null && schSlabRowIdFullFilledSlab.size() > 0) {
                                for (String productIdRowIDFullFilledSlabWithQty : schSlabRowIdFullFilledSlab) {
                                    String productIdFullFilledSlabWithQty = productIdRowIDFullFilledSlabWithQty.split(Pattern.quote("^"))[0];
                                    String RowIDFullFilledSlabWithQty = productIdRowIDFullFilledSlabWithQty.split(Pattern.quote("^"))[1];
                                    stringValHmap = hmapSubBucketDetailsData.get(productIdRowIDFullFilledSlabWithQty);
                                    String schSlabSubBucketType = stringValHmap.split(Pattern.quote("^"))[5];
                                    Double schSlabSubBucketVal = Double.valueOf(stringValHmap.split(Pattern.quote("^"))[3]);
                                    if (SchTypeId == 1 || SchTypeId == 3) {


                                        if (schSlabSubBucketType.equals("1")) {
                                            if (totalProductQnty >= schSlabSubBucketVal) {
                                                mDataSource.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty, "" + schSlabId, "" + schId, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);

                                            } else {

                                                mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                bucketCndtnFullFill = false;
                                                stringValHmap = "";
                                                break;
                                            }

                                        }
                                        //Product Line
                                        if (schSlabSubBucketType.equals("3")) {
                                            if (productFullFilledSlab.size() >= schSlabSubBucketVal) {
                                                mDataSource.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty, "" + schSlabId, "" + schId, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);
                                            } else {
                                                mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                bucketCndtnFullFill = false;
                                                stringValHmap = "";
                                                break;
                                            }
                                        }
                                        //product Value
                                        if (schSlabSubBucketType.equals("4")) {
                                            if (totalProductVal >= schSlabSubBucketVal) {
                                                mDataSource.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty, "" + schSlabId, "" + schId, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);
                                            } else {
                                                mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                bucketCndtnFullFill = false;
                                                stringValHmap = "";
                                                break;
                                            }

                                        }
                                        //product volume
                                        if (schSlabSubBucketType.equals("5")) {
                                            if (totalProductVol >= (schSlabSubBucketVal * 1000)) {
                                                mDataSource.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty, "" + schSlabId, "" + schId, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);
                                            } else {
                                                mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                bucketCndtnFullFill = false;
                                                stringValHmap = "";
                                                break;
                                            }
                                        }

                                    } else // scheme typeid=2
                                    {

                                        if (schSlabSubBucketType.equals("1")) {
                                            if (hmapSubBucketTotalQntty.containsKey(RowIDFullFilledSlabWithQty)) {
                                                int quantity = Integer.parseInt(hmapSubBucketTotalQntty.get(RowIDFullFilledSlabWithQty));
                                                if (quantity >= schSlabSubBucketVal) {
                                                    mDataSource.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty, "" + schSlabId, "" + schId, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);
                                                    listStrValHmapForSchm2.add(stringValHmap);
                                                } else {
                                                    mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                    bucketCndtnFullFill = false;
                                                    listStrValHmapForSchm2.clear();
                                                    break;
                                                }
                                            } else {
                                                mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                bucketCndtnFullFill = false;
                                                listStrValHmapForSchm2.clear();
                                                break;
                                            }
										/*if(hmapPrdctOdrQty.containsKey(productIdFullFilledSlabWithQty))
										{
											if(Integer.parseInt(hmapPrdctOdrQty.get(productIdFullFilledSlabWithQty))>=schSlabSubBucketVal)
											{
												listStrValHmapForSchm2.add(stringValHmap);
												dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID);

											}
											else
											{
												dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID);
												bucketCndtnFullFill=false;
												listStrValHmapForSchm2.clear();
												break;
											}
										}

										else
										{
											dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID);
											bucketCndtnFullFill=false;
											listStrValHmapForSchm2.clear();
											break;
										}
*/
                                        }

                                        if (schSlabSubBucketType.equals("3")) {

                                            if (productFullFilledSlab.size() >= schSlabSubBucketVal) {
                                                listStrValHmapForSchm2.add(stringValHmap);
                                                mDataSource.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty, "" + schSlabId, "" + schId, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);
                                            } else {
                                                mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                bucketCndtnFullFill = false;
                                                listStrValHmapForSchm2.clear();
                                                break;
                                            }
                                        }
                                        if (schSlabSubBucketType.equals("4")) {
                                            Double singleProdRate = Double.parseDouble(hmapPrdctVolRatTax.get(productIdFullFilledSlabWithQty).split(Pattern.quote("^"))[1]);
                                            int mapPrdctOdrQty = 0;
                                            if (prdctModelArrayList.getHmapPrdctOrderQty().containsKey(productIdFullFilledSlabWithQty)) {
                                                mapPrdctOdrQty = Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(productIdFullFilledSlabWithQty));
                                            }
                                            Double singlePrdctOderRate = singleProdRate * mapPrdctOdrQty;
                                            if (hmapSubBucketTotalValue.containsKey(RowIDFullFilledSlabWithQty)) {
                                                Double prdctVal = Double.parseDouble(hmapSubBucketTotalValue.get(RowIDFullFilledSlabWithQty));
                                                if (prdctVal >= schSlabSubBucketVal) {
                                                    listStrValHmapForSchm2.add(stringValHmap);
                                                    mDataSource.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty, "" + schSlabId, "" + schId, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);
                                                }
										/*if(singlePrdctOderRate>=schSlabSubBucketVal)
										{
											listStrValHmapForSchm2.add(stringValHmap);
											dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID);
										}*/
                                                else {
                                                    mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                    bucketCndtnFullFill = false;
                                                    listStrValHmapForSchm2.clear();
                                                    break;
                                                }
                                            } else {
                                                mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                bucketCndtnFullFill = false;
                                                listStrValHmapForSchm2.clear();
                                                break;
                                            }


                                        }
                                        if (schSlabSubBucketType.equals("5")) {
                                            Double singleProdVol = Double.parseDouble(hmapPrdctVolRatTax.get(productIdFullFilledSlabWithQty).split(Pattern.quote("^"))[0]);
                                            Double singlePrdctOderVol = singleProdVol * Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(productIdFullFilledSlabWithQty));
                                            if (hmapSubBucketTotalVolume.containsKey(RowIDFullFilledSlabWithQty)) {
                                                Double prdctVol = Double.parseDouble(hmapSubBucketTotalVolume.get(RowIDFullFilledSlabWithQty));
                                                if (prdctVol >= (schSlabSubBucketVal * 1000)) {
                                                    mDataSource.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty, "" + schSlabId, "" + schId, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);
                                                    listStrValHmapForSchm2.add(stringValHmap);
                                                }
										/*if(singlePrdctOderVol>=schSlabSubBucketVal)
										{
											listStrValHmapForSchm2.add(stringValHmap);
											dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID);
										}*/
                                                else {
                                                    mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                    bucketCndtnFullFill = false;
                                                    listStrValHmapForSchm2.clear();
                                                    break;
                                                }
                                            } else {
                                                mDataSource.deleteAlertValueSlab(storeID, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                                bucketCndtnFullFill = false;
                                                listStrValHmapForSchm2.clear();
                                                break;
                                            }

                                        }


                                    }


                                }


                            }


                            if (bucketCndtnFullFill) {
                                bucketCndtnFullFillisReally = true;
                                if (SchTypeId == 1 || SchTypeId == 3) {
                                    if (!TextUtils.isEmpty(stringValHmap.trim())) {
                                        for (String allproductFullFilledSlab : productFullFilledSlab) {
                                            productFullFilledSlabGlobal.add(allproductFullFilledSlab);
                                        }


                                        arredtboc_OderQuantityFinalSchemesToApply.add(stringValHmap + "^" + totalProductQnty + "^" + totalInvoice + "^" + totalProductLine + "^" + totalProductVal + "^" + totalProductVol + "^0");
                                    } else if (!TextUtils.isEmpty(stringValHmapInvoice.trim())) {
                                        arredtboc_OderQuantityFinalSchemesToApply.add(stringValHmapInvoice + "^" + totalProductQnty + "^" + totalInvoice + "^" + totalProductLine + "^" + totalProductVal + "^" + totalProductVol + "^0");
                                    }
                                } else {
                                    if (listStrValHmapForSchm2 != null && listStrValHmapForSchm2.size() > 0) {
                                        for (String allproductFullFilledSlab : productFullFilledSlab) {
                                            productFullFilledSlabGlobal.add(allproductFullFilledSlab);
                                        }

                                        for (String strVal : listStrValHmapForSchm2) {

                                            arredtboc_OderQuantityFinalSchemesToApply.add(strVal + "^" + totalProductQnty + "^" + totalInvoice + "^" + totalProductLine + "^" + totalProductVal + "^" + totalProductVol + "^0");
                                        }
                                    }
                                    if (!TextUtils.isEmpty(stringValHmapInvoice.trim())) {
                                        arredtboc_OderQuantityFinalSchemesToApply.add(stringValHmapInvoice + "^" + totalProductQnty + "^" + totalInvoice + "^" + totalProductLine + "^" + totalProductVal + "^" + totalProductVol + "^0");
                                    }

                                }
                                break;
                            }//if(bucketCndtnFullFill) ends here

                        }// bucket ends here

                        if (bucketCndtnFullFillisReally) {
                            bucketCndtnSchemeFullFill = true;
                            break;
                        }
                    }
                }

            }
        }

        if (hmapProductAddOnSchemesList != null && hmapProductAddOnSchemesList.containsKey(ProductIdOnClicked)) {
            fnCheckExtraSchemeAfterValueChange(hmapProductAddOnSchemesList.get(ProductIdOnClicked), Integer.parseInt(ProductIdOnClicked));
        }

        fnAssignSchemeIDsAppliedOverProductAfterValueChange(ProductIdOnClicked);
    }


    private void initializeFields() {
        sharedPrefReport = getSharedPreferences("populateCategory", MODE_PRIVATE);
        getDataFromIntent();
        ll_GiveOverAllDiscountSection = findViewById(R.id.ll_GiveOverAllDiscountSection);
        iv2 = findViewById(R.id.iv2);
        iv1 = findViewById(R.id.iv1);
        ll_ContactUpdateSection = findViewById(R.id.ll_ContactUpdateSection);

        tvProductLevelDiscount= findViewById(R.id.tvProductLevelDiscount);
        inflateUpdateContactSectionLayout();
        inflateUpdateContactSection();
        VisitTimeInSideStore = mDataSource.fnGetDateTimeString();
        mCustomKeyboardNum = new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num);
        mCustomKeyboardNumWithoutDecimal = new CustomKeyboard(this, R.id.keyboardviewNumDecimal, R.xml.num_without_decimal);

        flgIsAnySchemeMappedAgainstStore = mDataSource.fnflgCheckAnySchemeStoreID(storeID);
        txt_Lststock = (TextView) findViewById(R.id.txt_Lststock);
        if (!TextUtils.isEmpty(lastStockDate)) {
            txt_Lststock.setText("Stk On " + lastStockDate);
        } else {
            txt_Lststock.setText("Last Stk NA");
        }
        rv_dailandorder=findViewById(R.id.rv_dailandorder);
        tv_ClickHereForDialNumberAndProceedForOrderEntry=findViewById(R.id.tv_ClickHereForDialNumberAndProceedForOrderEntry);
        rv_dailandorder.setVisibility(View.GONE);

        StContactNo=mDataSource.fncheckStoreContactNo(storeID);
     //   StContactNo="+918447130126";


        llOrdHistory = findViewById(R.id.llOrdHistory);
        llPrioritySKU = findViewById(R.id.llPrioritySKU);
        llSchemeAppl = findViewById(R.id.llSchemeAppl);
        et_Disc = findViewById(R.id.et_Disc);



        double ovlDiscount=0.0;
        ovlDiscount=mDataSource.fngettblStoreDSROverAllDiscountApplied(storeID);
        if(ovlDiscount>0.0)
        {
            et_Disc.setText(""+ovlDiscount);
        }
        tvDisc = findViewById(R.id.tvDisc);

        tvDisc.setOnClickListener(view -> {
            String disc = et_Disc.getText().toString().trim();
//            String cartonQtyFilled = txt_CartonTotal_filled.getText().toString().trim();

            if (!AppUtils.isNullOrEmpty(disc, "0")) {
                double dis = Double.parseDouble(disc);
                double totordval=0.0;
                prdctModelArrayList.getHmapPrdctOverAllDiscountToApply().put("ApplyDiscount",""+dis);
                LinkedHashMap<String, String> hmapPrdctOrderQty = prdctModelArrayList.getHmapPrdctOrderQty();
                int qty = 0;
                for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : tblStoreProductMappingList) {
                    if (tblStoreProductMappingForDisplay.getFlgTeleCallerProduct() == 0) {
                        if (hmapPrdctOrderQty != null && hmapPrdctOrderQty.size() > 0) {
                            if (hmapPrdctOrderQty.containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID())) {
                                String prdctQty = "" + hmapPrdctOrderQty.get("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                                if ((!TextUtils.isEmpty(prdctQty)) && (Integer.parseInt(prdctQty) > 0)) {

                                    qty = qty + Integer.parseInt(prdctQty);
                                    List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysSingleItem = stream(tblStoreProductMappingList).where(p -> p.getPrdNodeID() ==tblStoreProductMappingForDisplay.getPrdNodeID()).toList();
                                  //  List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
                                    List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                                    int conversionUnitForOrderCalculation = 1;
                                    double StandardRate = UOMMasterDAtaForCalculation.get(0).getStandardRate();
                                    double  StandardRateBeforeTax =StandardRate;// StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));

                                    double DiscountAmount = 0.00;


                                   double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax) - DiscountAmount;// StandardRateBeforeTax;
                                    double ActualTax = ActualRateAfterDiscountBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100);
                                    double ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));
                                    Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQty);
                                    totordval=totordval+OrderValPrdQtyBasis;

/*
                                    if (UOMMasterDAta.get(0).getBUOMID() != 3) {
                                        conversionUnitForOrderCalculation = (int) (UOMMasterDAta.get(0).getRelConversionUnit());

                                    }*/

                                }
                            }
                        }
                    }
                }

                if (dis <= totordval) {
                    hmapPrdctOrderQtyForDiscountApplyOverAll.clear();
                    prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().clear();
                  //  double discToBeApplied = (double) dis / qty;
                    for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : tblStoreProductMappingList) {
                        if (tblStoreProductMappingForDisplay.getFlgTeleCallerProduct() == 0) {
                            if (hmapPrdctOrderQty != null && hmapPrdctOrderQty.size() > 0) {
                                if (hmapPrdctOrderQty.containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID())) {
                                    String prdctQty = "" + hmapPrdctOrderQty.get("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                                    hmapPrdctOrderQtyForDiscountApplyOverAll.put("" + tblStoreProductMappingForDisplay.getPrdNodeID(),prdctQty);
                                    prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().put("" + tblStoreProductMappingForDisplay.getPrdNodeID(),prdctQty);
                                    List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysSingleItem = stream(tblStoreProductMappingList).where(p -> p.getPrdNodeID() ==tblStoreProductMappingForDisplay.getPrdNodeID()).toList();
                                     List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
                                    List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                                    int conversionUnitForOrderCalculation = 1;
                                    double StandardRate = UOMMasterDAtaForCalculation.get(0).getStandardRate();
                                    double  StandardRateBeforeTax =StandardRate;// StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));

                                    double DiscountAmount = 0.00;


                                    double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax) - DiscountAmount;// StandardRateBeforeTax;
                                    double ActualTax = ActualRateAfterDiscountBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100);
                                    double ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));
                                    Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQty);


                                    double discToBeApplied=(dis*OrderValPrdQtyBasis)/totordval;
                                    discToBeApplied=discToBeApplied/Double.parseDouble(prdctQty);
                                    if (UOMMasterDAta.get(0).getBUOMID() != 3) {
                                        conversionUnitForOrderCalculation = (int) (UOMMasterDAta.get(0).getRelConversionUnit());

                                    }
                                   // discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                    //double discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                    prdctModelArrayList.getHmapPrdctDiscountPerUOM().put("" + tblStoreProductMappingForDisplay.getPrdNodeID(), "" + ( discToBeApplied));
                                }
                            }

                        }
                    }
                    flgAppBtnClicked=1;
                    orderAdapter.updateListDiscountModel(prdctModelArrayList);
                    orderBookingTotalCalc();

                } else {
                    showAlertSingleButtonInfo("Discount cannot be greater than Invoice Value.");
                }
            }
            else
            {
                prdctModelArrayList.getHmapPrdctOverAllDiscountToApply().put("ApplyDiscount","0.0");
                for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : tblStoreProductMappingList) {
                    if (tblStoreProductMappingForDisplay.getFlgTeleCallerProduct() == 0) {
                        if(prdctModelArrayList.getHmapPrdctDiscountPerUOM()!=null && prdctModelArrayList.getHmapPrdctDiscountPerUOM().size()>0)
                        {
                            if( prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID()))
                            {
                                prdctModelArrayList.getHmapPrdctDiscountPerUOM().remove("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                                prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().remove("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                                hmapPrdctOrderQtyForDiscountApplyOverAll.remove("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                            }
                        }
                    }
                }
                orderAdapter.updateListDiscountModel(prdctModelArrayList);
                orderBookingTotalCalc();
            }

        });

        llOrdHistory.setOnClickListener(view -> {

            StoreHistoryDialog storeHistoryDialog = new StoreHistoryDialog(ProductEntryForm.this, storeID);
            storeHistoryDialog.setCancelable(false);
            storeHistoryDialog.show();

            /*
            if (ed_LastEditextFocusd != null) {
                fcsLstCld(false, ed_LastEditextFocusd);
            }
            ed_search.setText("");//defaultCatName_Id.split(Pattern.quote("^"))[0]
            btn_ShowSchemeApplicableProducts.setVisibility(View.GONE);
            btn_ShowAllProducts.setVisibility(View.VISIBLE);

            List<TblStoreProductMappingForDisplay> tblSchemePrds = stream(tblStoreProductMappingListtemp).where(p -> p.getFlgProductWithScheme() == 1 && hmapProductRelatedSchemesList.containsKey("" + p.getPrdNodeID())).orderBy(p -> p.getCatID()).toList();
            if (tblSchemePrds != null && tblSchemePrds.size() > 0) {
                if (CurrentCategorySelectedPosition == -1) {
                    searchProductSchemeProductsOnly("All", "0", 0, 0);
                } else {
                    if (tblStoreProductMappingListForDisplay != null && tblStoreProductMappingListForDisplay.size() > 0) {
                        searchProductSchemeProductsOnly(previousSlctdCtgry, "" + CurrentCategorySelectedPosition, 0, 0);
                    } else {
                        btn_ShowAllProducts.setVisibility(View.GONE);
                        showAlertSingleButtonInfo("No Products with scheme found.");
                    }

                }
            } else {
                btn_ShowAllProducts.setVisibility(View.GONE);
                showAlertSingleButtonInfo("No Products with scheme found.");
            }

        }*/
        });

        if(mDataSource.fnGetSectorID()==3)
        {
            llSchemeAppl.setVisibility(View.GONE);
        }

        llSchemeAppl.setOnClickListener(view -> {
          /*  SchemeApplicableDialog schemeApplicableDialog = new SchemeApplicableDialog(ProductEntryForm.this, storeID);
            schemeApplicableDialog.setCancelable(false);
            schemeApplicableDialog.show();*/
            fnShowAllSchemes();
        });

        llPrioritySKU.setOnClickListener(view -> {

            List<TblStoreProductMappingForDisplay> listPrioritySKU = stream(tblStoreProductMappingList).where(p -> p.getFlgFocusbrand() == 1).orderBy(p -> p.getCatID()).toList();

            PrioritySKUDialog prioritySKUDialog = new PrioritySKUDialog(ProductEntryForm.this, storeID, listPrioritySKU);
            prioritySKUDialog.setCancelable(false);
            prioritySKUDialog.show();
        });


     /*   llFocusBrand = (LinearLayout) findViewById(R.id.llFocusBrand);

        llFocusBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*searchForFoucusedBrand();*//*

                if (ed_LastEditextFocusd != null) {
                    fcsLstCld(false, ed_LastEditextFocusd);
                }
                ed_search.setText("");//defaultCatName_Id.split(Pattern.quote("^"))[0]
                btn_ShowSchemeApplicableProducts.setVisibility(View.GONE);
                btn_ShowAllProducts.setVisibility(View.VISIBLE);
                if (CurrentCategorySelectedPosition == -1) {
                    searchForFoucusedBrand("All", "0", 0, 0);
                } else {
                    searchForFoucusedBrand(previousSlctdCtgry, "" + CurrentCategorySelectedPosition, 0, 0);
                }
            }
        });*/

        ll_rcylContainer = (LinearLayout) findViewById(R.id.ll_rcylContainer);
        TextView order_detailHeading = (TextView) findViewById(R.id.order_detail);

        TextView txt_detalis = (TextView) findViewById(R.id.txt_detalis);

        TextView lbl_InvOrderHeader = (TextView) findViewById(R.id.lbl_InvOrderHeader);
        TextView tv_EntryInvValHeader = (TextView) findViewById(R.id.tv_EntryInvValHeader);

        if (CommonInfo.hmapAppMasterFlags!=null && CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.containsKey("flgSchemeAvailable") && CommonInfo.hmapAppMasterFlags.containsKey("flgVisitStartSchemeDetails")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgSchemeAvailable") == 1 && CommonInfo.hmapAppMasterFlags.get("flgVisitStartSchemeDetails") == 1) {
                RelativeLayout ll_schm_applcbl = (RelativeLayout) findViewById(R.id.ll_schm_applcbl);
                ll_schm_applcbl.setVisibility(View.VISIBLE);
            }
        }
        if (CommonInfo.flgDrctslsIndrctSls == 2) {
            order_detailHeading.setText("Order Details");
            txt_detalis.setText("Order Total");
            lbl_InvOrderHeader.setText("O.Qty.");
            tv_EntryInvValHeader.setText("O.Val");
        }


        img_ctgry = (ImageView) findViewById(R.id.img_ctgry);
        ed_search = (EditText) findViewById(R.id.ed_search);
//        btn_erase = (ImageView) findViewById(R.id.btn_erase);
//        btn_go = (ImageView) findViewById(R.id.btn_go);
        txt_RefreshOdrTot = (TextView) findViewById(R.id.txt_RefreshOdrTot);
        btn_InvoiceReview = (Button) findViewById(R.id.btn_InvoiceReview);
        btn_NoOrder = (Button) findViewById(R.id.btn_NoOrder);
        btn_ShowSchemeApplicableProducts = (Button) findViewById(R.id.btn_ShowSchemeApplicableProducts);
        btn_ShowAllProducts = (Button) findViewById(R.id.btn_ShowAllProducts);
        btn_OrderReview = (Button) findViewById(R.id.btn_OrderReview);
        img_return = (ImageView) findViewById(R.id.img_return);
        btn_bck = (ImageView) findViewById(R.id.btn_bck);
        rv_prdct_detal = (RecyclerView) findViewById(R.id.rv_prdct_detal);
        rvCartonData = (RecyclerView) findViewById(R.id.rvCartonData);
        rvStoreVisitHistory = (RecyclerView) findViewById(R.id.rvStoreVisitHistory);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll_scheme_detail = findViewById(R.id.ll_scheme_detail);
        rlTCOrderMain = findViewById(R.id.rlTCOrderMain);
        rlCartons = findViewById(R.id.rlCartons);

        ArrayList<TblGetPDAStoreVisitHistory> storeTCOrders = mDataSource.getStoreTCOrders(storeID);
        if (storeTCOrders != null && storeTCOrders.size() > 0) {
            hmapProductTblUOMMappingCOrderHistory=mDataSource.fetch_ProductWiseGetAllMeasureUnitAgainstProductForLastVisitPage(storeID);
            TARSOrdersAdapter tarsOrdersAdapter = new TARSOrdersAdapter(ProductEntryForm.this, storeTCOrders,hmapProductTblUOMMappingCOrderHistory);
            rvStoreVisitHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            rvStoreVisitHistory.setAdapter(tarsOrdersAdapter);
            rvStoreVisitHistory.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.VISIBLE);
            rlTCOrderMain.setVisibility(View.VISIBLE);
        } else {
            rvStoreVisitHistory.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            rlTCOrderMain.setVisibility(View.GONE);
        }
        if (CommonInfo.flgDrctslsIndrctSls == 2) {
            btn_InvoiceReview.setText(getResources().getString(R.string.OrderReview));
        }

        img_ctgry.setOnClickListener(this);
        btn_bck.setOnClickListener(this);
        btn_InvoiceReview.setOnClickListener(this);
        btn_NoOrder.setOnClickListener(this);
        btn_OrderReview.setOnClickListener(this);
        ed_search.setOnClickListener(this);
        et_Disc.setOnClickListener(this);
//        btn_go.setOnClickListener(this);

        ImageView executionDetails_butn = (ImageView) findViewById(R.id.txt_execution_Details);


        if (CommonInfo.flgDrctslsIndrctSls == 2) {

            //executionDetails_butn.setVisibility(View.VISIBLE);
            if (CommonInfo.hmapAppMasterFlags!=null && CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                    //    img_return.setVisibility(View.VISIBLE);
                } else {
                    img_return.setVisibility(View.GONE);
                }
            } else {
                //  img_return.setVisibility(View.VISIBLE);
            }

        } else if (CommonInfo.hmapAppMasterFlags!=null && CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                //  img_return.setVisibility(View.VISIBLE);
            } else {
                img_return.setVisibility(View.GONE);
            }
        } else {
            // img_return.setVisibility(View.VISIBLE);
        }

        iv2.setOnClickListener(view -> {
            if (ll3.getVisibility() == View.VISIBLE) {
                ll3.setVisibility(View.GONE);
                rvCartonData.setVisibility(View.GONE);
                iv2.setImageDrawable(ContextCompat.getDrawable(ProductEntryForm.this, R.drawable.ic_down));
            } else {
                ll3.setVisibility(View.VISIBLE);
                rvCartonData.setVisibility(View.VISIBLE);
                iv2.setImageDrawable(ContextCompat.getDrawable(ProductEntryForm.this, R.drawable.ic_up));
            }
        });

        iv1.setOnClickListener(view -> {
            if (ll2.getVisibility() == View.VISIBLE) {
                ll2.setVisibility(View.GONE);
                rvStoreVisitHistory.setVisibility(View.GONE);
                iv1.setImageDrawable(ContextCompat.getDrawable(ProductEntryForm.this, R.drawable.ic_down));
            } else {
                ll2.setVisibility(View.VISIBLE);
                rvStoreVisitHistory.setVisibility(View.VISIBLE);
                iv1.setImageDrawable(ContextCompat.getDrawable(ProductEntryForm.this, R.drawable.ic_up));
            }
        });

        btn_ShowAllProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_LastEditextFocusd != null) {
                    fcsLstCld(false, ed_LastEditextFocusd);
                }
                if (defaultCatName_Id.contains("^")) {
                    ed_search.setText("");//defaultCatName_Id.split(Pattern.quote("^"))[0]
                    btn_ShowSchemeApplicableProducts.setVisibility(View.GONE);
                    btn_ShowAllProducts.setVisibility(View.GONE);
                    searchProduct(defaultCatName_Id.split(Pattern.quote("^"))[0], defaultCatName_Id.split(Pattern.quote("^"))[1], 0, 0);
                }

            }
        });
        btn_ShowSchemeApplicableProducts.setVisibility(View.GONE);

        if (flgIsAnySchemeMappedAgainstStore == 1) {
            btn_ShowSchemeApplicableProducts.setVisibility(View.GONE);
        }


        btn_ShowSchemeApplicableProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_LastEditextFocusd != null) {
                    fcsLstCld(false, ed_LastEditextFocusd);
                }
                ed_search.setText("");//defaultCatName_Id.split(Pattern.quote("^"))[0]
                btn_ShowSchemeApplicableProducts.setVisibility(View.GONE);
                btn_ShowAllProducts.setVisibility(View.VISIBLE);
                if (CurrentCategorySelectedPosition == -1) {
                    searchProductSchemeProductsOnly("All", "0", 0, 0);
                } else {
                    searchProductSchemeProductsOnly(previousSlctdCtgry, "" + CurrentCategorySelectedPosition, 0, 0);
                }


            }
        });

        img_return.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ed_LastEditextFocusd = prdctModelArrayList.getLastEditText();

                fnCreditAndStockCal(4, ed_LastEditextFocusd, hmapDistPrdctStockCount);


            }
        });
        //	tvRateHeading = (TextView) findViewById(R.id.tvRateHeading);
        executionDetails_butn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub

                LayoutInflater layoutInflater = LayoutInflater.from(ProductEntryForm.this);
                View promptView = layoutInflater.inflate(R.layout.lastsummary_execution, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProductEntryForm.this);


                alertDialogBuilder.setTitle(ProductEntryForm.this.getResources().getString(R.string.genTermInformation));


                //mDataSource.open();

                String DateResult[] = mDataSource.fetchOrderDateFromtblForPDAGetExecutionSummary(storeID);
                String LastexecutionDetail[] = mDataSource.fetchAllDataFromtbltblForPDAGetExecutionSummary(storeID);

                String PrdNameDetail[] = mDataSource.fetchPrdNameFromtblForPDAGetExecutionSummary(storeID);

                String ProductIDDetail[] = mDataSource.fetchProductIDFromtblForPDAGetExecutionSummary(storeID);


                //System.out.println("Ashish and Anuj LastexecutionDetail : "+LastexecutionDetail.length);
                //mDataSource.close();

                if (DateResult.length > 0) {
                    TextView FirstDate = (TextView) promptView.findViewById(R.id.FirstDate);
                    TextView SecondDate = (TextView) promptView.findViewById(R.id.SecondDate);
                    TextView ThirdDate = (TextView) promptView.findViewById(R.id.ThirdDate);

                    TextView lastExecution = (TextView) promptView.findViewById(R.id.lastExecution);
                    lastExecution.setText(ProductEntryForm.this.getResources().getString(R.string.lastvisitdetails_last)
                            + DateResult.length + ProductEntryForm.this.getResources().getString(R.string.ExecSummary));


                    if (DateResult.length == 1) {
                        FirstDate.setText("" + DateResult[0]);
                        SecondDate.setVisibility(View.GONE);
                        ThirdDate.setVisibility(View.GONE);
                    } else if (DateResult.length == 2) {
                        FirstDate.setText("" + DateResult[0]);
                        SecondDate.setText("" + DateResult[1]);
                        ThirdDate.setVisibility(View.GONE);
                    } else if (DateResult.length == 3) {
                        FirstDate.setText("" + DateResult[0]);
                        SecondDate.setText("" + DateResult[1]);
                        ThirdDate.setText("" + DateResult[2]);
                    }
                }

                LayoutInflater inflater = getLayoutInflater();

                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
                double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
                double screenInches = Math.sqrt(x + y);
                if (LastexecutionDetail.length > 0) {
                    alertDialogBuilder.setView(promptView);


                    tbl1_dyntable_For_ExecutionDetails = (TableLayout) promptView.findViewById(R.id.dyntable_For_ExecutionDetails);
                    TableRow row1 = (TableRow) inflater.inflate(R.layout.table_execution_head, tbl1_dyntable_For_OrderDetails, false);

                    TextView firstDateOrder = (TextView) row1.findViewById(R.id.firstDateOrder);
                    TextView firstDateInvoice = (TextView) row1.findViewById(R.id.firstDateInvoice);
                    TextView secondDateOrder = (TextView) row1.findViewById(R.id.secondDateOrder);
                    TextView secondDateInvoice = (TextView) row1.findViewById(R.id.secondDateInvoice);
                    TextView thirdDateOrder = (TextView) row1.findViewById(R.id.thirdDateOrder);
                    TextView thirdDateInvoice = (TextView) row1.findViewById(R.id.thirdDateInvoice);
                    if (DateResult.length > 0) {
                        if (DateResult.length == 1) {

                            secondDateOrder.setVisibility(View.GONE);
                            secondDateInvoice.setVisibility(View.GONE);
                            thirdDateOrder.setVisibility(View.GONE);
                            thirdDateInvoice.setVisibility(View.GONE);
                        } else if (DateResult.length == 2) {
                            thirdDateOrder.setVisibility(View.GONE);
                            thirdDateInvoice.setVisibility(View.GONE);
                        }
                    }

                    tbl1_dyntable_For_ExecutionDetails.addView(row1);


                    for (int current = 0; current <= (PrdNameDetail.length - 1); current++) {


                        final TableRow row = (TableRow) inflater.inflate(R.layout.table_execution_row, tbl1_dyntable_For_OrderDetails, false);

                        TextView tv1 = (TextView) row.findViewById(R.id.skuName);
                        TextView tv2 = (TextView) row.findViewById(R.id.firstDateOrder);
                        TextView tv3 = (TextView) row.findViewById(R.id.firstDateInvoice);
                        TextView tv4 = (TextView) row.findViewById(R.id.secondDateOrder);
                        TextView tv5 = (TextView) row.findViewById(R.id.secondDateInvoice);
                        TextView tv6 = (TextView) row.findViewById(R.id.thirdDateOrder);
                        TextView tv7 = (TextView) row.findViewById(R.id.thirdDateInvoice);

                        tv1.setText(PrdNameDetail[current]);

                        if (DateResult.length > 0) {
                            if (DateResult.length == 1) {
                                tv4.setVisibility(View.GONE);
                                tv5.setVisibility(View.GONE);
                                tv6.setVisibility(View.GONE);
                                tv7.setVisibility(View.GONE);
                                //mDataSource.open();
                                String abc[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[0], ProductIDDetail[current]);
                                //mDataSource.close();

                                //System.out.println("Check Value Number "+abc.length);
                                //System.out.println("Check Value Number12 "+DateResult[0]);
                                if (abc.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
                                    tv2.setText(tokens.nextToken().trim());
                                    tv3.setText(tokens.nextToken().trim());
                                } else {
                                    tv2.setText("0");
                                    tv3.setText("0");
                                }
                            } else if (DateResult.length == 2) {
                                tv6.setVisibility(View.GONE);
                                tv7.setVisibility(View.GONE);

                                //mDataSource.open();
                                String abc[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[0], ProductIDDetail[current]);
                                //mDataSource.close();

                                //System.out.println("Check Value Number "+abc.length);
                                //System.out.println("Check Value Number12 "+DateResult[0]);
                                if (abc.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
                                    tv2.setText(tokens.nextToken().trim());
                                    tv3.setText(tokens.nextToken().trim());
                                } else {
                                    tv2.setText("0");
                                    tv3.setText("0");
                                }

                                //mDataSource.open();
                                String abc1[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[1], ProductIDDetail[current]);
                                //mDataSource.close();


                                if (abc1.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc1[0]), "_");
                                    tv4.setText(tokens.nextToken().trim());
                                    tv5.setText(tokens.nextToken().trim());
                                } else {
                                    tv4.setText("0");
                                    tv5.setText("0");
                                }


                            } else if (DateResult.length == 3) {
                                //mDataSource.open();
                                String abc[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[0], ProductIDDetail[current]);
                                //mDataSource.close();


                                if (abc.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
                                    tv2.setText(tokens.nextToken().trim());
                                    tv3.setText(tokens.nextToken().trim());
                                } else {
                                    tv2.setText("0");
                                    tv3.setText("0");
                                }

                                //mDataSource.open();
                                String abc1[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[1], ProductIDDetail[current]);
                                //mDataSource.close();


                                if (abc1.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc1[0]), "_");
                                    tv4.setText(tokens.nextToken().trim());
                                    tv5.setText(tokens.nextToken().trim());
                                } else {
                                    tv4.setText("0");
                                    tv5.setText("0");
                                }

                                //mDataSource.open();
                                String abc2[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[2], ProductIDDetail[current]);
                                //mDataSource.close();


                                if (abc2.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc2[0]), "_");
                                    tv6.setText(tokens.nextToken().trim());
                                    tv7.setText(tokens.nextToken().trim());
                                } else {
                                    tv6.setText("0");
                                    tv7.setText("0");
                                }


                            } else {

                            }
                        }

					/*if(screenInches>6.5)
					{
						tv1.setTextSize(14);
						tv2.setTextSize(14);
						tv3.setTextSize(14);
						tv4.setTextSize(14);
						tv5.setTextSize(14);
						tv6.setTextSize(14);
						tv7.setTextSize(14);
					}
					else
					{

					}*/


					/*StringTokenizer tokens = new StringTokenizer(String.valueOf(LastexecutionDetail[current]), "_");

					tv1.setText(tokens.nextToken().trim());
					tv2.setText(tokens.nextToken().trim());
					tokens.nextToken().trim();
					tv3.setText(tokens.nextToken().trim());*/
					/*tv4.setText(tokens.nextToken().trim());
					tv5.setText(tokens.nextToken().trim());*/
                        tbl1_dyntable_For_ExecutionDetails.addView(row);

                    }

                } else {
                    alertDialogBuilder.setMessage(ProductEntryForm.this.getResources().getString(R.string.AlertExecNoSum));
                }
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });


                alertDialogBuilder.setIcon(R.drawable.info_ico);
                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();


            }
        });

            if(flgOrderType==0)
            {
                rv_dailandorder.setVisibility(View.VISIBLE);
                StoreSummaryModel storeSummaryModel = new StoreSummaryModel();
                storeSummaryModel = mDataSource.getStoreSummary(storeID);
                String OwnName=storeSummaryModel.getOwnerName();
                if(OwnName==null && !OwnName.equals("NA") && !TextUtils.isEmpty(OwnName.trim()))
                tv_ClickHereForDialNumberAndProceedForOrderEntry.setText("Call "+storeSummaryModel.getStoreName()+" ("+OwnName+") on :"+StContactNo);
                else
                    tv_ClickHereForDialNumberAndProceedForOrderEntry.setText("Call "+storeSummaryModel.getStoreName()+" on :"+StContactNo);
               // tv_ClickHereForDialNumberAndProceedForOrderEntry.setText("Call  :"+StContactNo);
                tv_ClickHereForDialNumberAndProceedForOrderEntry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chooseSim(true);
                    }
                });
            }

    }

    private void getDataFromIntent() {


        Intent passedvals = getIntent();

        storeID = passedvals.getStringExtra("storeID");
        imei = passedvals.getStringExtra("imei");
        date = passedvals.getStringExtra("userdate");
        pickerDate = passedvals.getStringExtra("pickerDate");
        SN = passedvals.getStringExtra("SN");
        flgOrderType = passedvals.getIntExtra("flgOrderType", 0);


    }

    public void loadPurchaseCartonDetail() {

        tblProductCategoryUOMTypeLists = mDataSource.fnGetProductCategoryUOMTypeListOverAllSummaryInvoiceReview(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);

        if (tblProductCategoryUOMTypeLists != null && tblProductCategoryUOMTypeLists.size() > 0) {
            rlCartons.setVisibility(View.VISIBLE);
            rvCartonData.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvCartonData.setLayoutManager(linearLayoutManager);
            rvCartonData.setAdapter(new CreatedCartonAdapter(this, tblProductCategoryUOMTypeLists, date, pickerDate, SN,
                    flgOrderType, 0));
        } else {
            rlCartons.setVisibility(View.GONE);
            rvCartonData.setVisibility(View.GONE);
        }

    }

    public void searchProduct(String filterSearchText, String ctgrytext, int filterType, int ParentID) {
        tblStoreProductMappingListForDisplay.clear();
        if (hmapFilterProductList != null) {
            hmapFilterProductList.clear();
        }

        SelectedCategoryForSchemeFileter = "All";
        SelectedCategoryIDForSchemeFileter = 0;

        // tblStoreProductMappingList = mDataSource.fnGetTblStoreProductMapping(storeID, TmpInvoiceCodePDA,StoreCurrentStoreType,SupplierNodeType);
        // tblStoreProductMappingList = mDataSource.fnGetTblStoreProductMappingForSearch(storeID, TmpInvoiceCodePDA,StoreCurrentStoreType,RegionID);
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





   /* hmapFilterProductList = mDataSource.getFileredProductListMapPRoductFilter(filterSearchText.trim(), StoreCurrentStoreType, ctgrytext, filterType, ParentID);

        if (hmapFilterProductList.size() > 0) {
            String[] listProduct = new String[hmapFilterProductList.size()];
            int index = 0;
            for (Map.Entry<String, String> entry : hmapFilterProductList.entrySet()) {
                listProduct[index] = entry.getKey() + "^" + entry.getValue();
                index++;
            }



        } else {
            String[] listProduct = new String[hmapFilterProductList.size()];
            orderAdapter = new OrderAdapterProdcutEntry(ProductEntryForm.this, storeID, listProduct, hmapFilterProductList, hmapProductStandardRate, hmapProductMRP, hmapProductIdStock, hmapProductIdLastStock, hampGetLastProductExecution, hmapDistPrdctStockCount, hmapVanPrdctStockCount, prdctModelArrayList, 0, hmapPerBaseQty, hmapProductTblUOMMapping, hmapProductLODQty, hmapProductSuggestedQty, hmapProductImg, hmapProductRelatedSchemesList, flgIsAnySchemeMappedAgainstStore,tblStoreProductMappingListForDisplay);
            rv_prdct_detal.setAdapter(orderAdapter);
            rv_prdct_detal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            allMessageAlert(ProductEntryForm.this.getResources().getString(R.string.AlertFilter));
        }*/
        fnMarkFirstProductCategoryRowVisible();
        orderAdapter.updateList(tblStoreProductMappingListForDisplay);

    }


    private double round(double discToBeApplied, int decimalPlace) {
        return BigDecimal.valueOf(discToBeApplied).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public void searchProductSchemeProductsOnly(String filterSearchText, String ctgrytext, int filterType, int ParentID) {
        tblStoreProductMappingListForDisplay.clear();
        if (hmapFilterProductList != null) {
            hmapFilterProductList.clear();
        }

        SelectedCategoryForSchemeFileter = "All";
        SelectedCategoryIDForSchemeFileter = 0;

        // tblStoreProductMappingList = mDataSource.fnGetTblStoreProductMapping(storeID, TmpInvoiceCodePDA,StoreCurrentStoreType,SupplierNodeType);
        //  tblStoreProductMappingList = mDataSource.fnGetTblStoreProductMappingForSearch(storeID, TmpInvoiceCodePDA,StoreCurrentStoreType,RegionID);
        tblStoreProductMappingListtemp.clear();


        List<String> searchStringsArray = new ArrayList<>();
        if (filterSearchText.contains(",")) {
            searchStringsArray.addAll(Arrays.asList(filterSearchText.toLowerCase().split(Pattern.quote(","))));
        }
        if (searchStringsArray.size() == 0) {
            if (SelectedCategoryForSchemeFileter.equals("All")) {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase()) && p.getFlgProductWithScheme() == 1 && hmapProductRelatedSchemesList.containsKey("" + p.getPrdNodeID())).toList();
            } else {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase()) && p.getCatID() == ParentID && p.getFlgProductWithScheme() == 1 && hmapProductRelatedSchemesList.containsKey("" + p.getPrdNodeID())).toList();
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
                if (tblStoreProductMappingRecord.getFlgProductWithScheme() == 1)
                    tblStoreProductMappingListForDisplay.add(tblStoreProductMappingRecord);
                // }
            }
        }


        fnMarkFirstProductCategoryRowVisible();
        orderAdapter.updateList(tblStoreProductMappingListForDisplay);

    } /*{

        if (hmapFilterProductList != null) {
            hmapFilterProductList.clear();
        }

        hmapFilterProductList = mDataSource.getFileredProductListMapPRoductFilterSchemeProducts(SelectedCategoryForSchemeFileter.trim(), StoreCurrentStoreType, "" + SelectedCategoryIDForSchemeFileter, filterType, ParentID);

        if (hmapFilterProductList.size() > 0) {
            String[] listProduct = new String[hmapFilterProductList.size()];
            int index = 0;
            for (Map.Entry<String, String> entry : hmapFilterProductList.entrySet()) {
                listProduct[index] = entry.getKey() + "^" + entry.getValue();
                index++;
            }

            orderAdapter = new OrderAdapterProdcutEntry(ProductEntryForm.this, storeID, listProduct, hmapFilterProductList, hmapProductStandardRate, hmapProductMRP, hmapProductIdStock, hmapProductIdLastStock, hampGetLastProductExecution, hmapDistPrdctStockCount, hmapVanPrdctStockCount, prdctModelArrayList, 0, hmapPerBaseQty, hmapProductTblUOMMapping, hmapProductLODQty, hmapProductSuggestedQty, hmapProductImg, hmapProductRelatedSchemesList, flgIsAnySchemeMappedAgainstStore,tblStoreProductMappingListForDisplay);
            rv_prdct_detal.setAdapter(orderAdapter);
            rv_prdct_detal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            String[] listProduct = new String[hmapFilterProductList.size()];
            orderAdapter = new OrderAdapterProdcutEntry(ProductEntryForm.this, storeID, listProduct, hmapFilterProductList, hmapProductStandardRate, hmapProductMRP, hmapProductIdStock, hmapProductIdLastStock, hampGetLastProductExecution, hmapDistPrdctStockCount, hmapVanPrdctStockCount, prdctModelArrayList, 0, hmapPerBaseQty, hmapProductTblUOMMapping, hmapProductLODQty, hmapProductSuggestedQty, hmapProductImg, hmapProductRelatedSchemesList, flgIsAnySchemeMappedAgainstStore,tblStoreProductMappingListForDisplay);
            rv_prdct_detal.setAdapter(orderAdapter);
            rv_prdct_detal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            allMessageAlert(ProductEntryForm.this.getResources().getString(R.string.AlertFilter));
        }


    }*/

    private void allMessageAlert(String message) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ProductEntryForm.this);
        alertDialogNoConn.setTitle(ProductEntryForm.this.getResources().getString(R.string.genTermInformation));
        alertDialogNoConn.setMessage(message);
        //alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
        alertDialogNoConn.setNeutralButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogOkButton), (dialog, which) -> {
            dialog.dismiss();
            ed_search.requestFocus();
                     /*if(isMyServiceRunning())
                       {
                     stopService(new Intent(DynamicActivity.this,GPSTrackerService.class));
                       }
                     finish();*/
            //finish();
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

    public void getDataFromatabase() {

        //StoreVisitCode = mDataSource.fnGetStoreVisitCode(storeID);
        String prsnCvrgId_NdTyp = mDataSource.fngetSalesPersonCvrgIdCvrgNdTyp();
        int CoverageNodeId = Integer.parseInt(prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0]);
        int CoverageNodeType = Integer.parseInt(prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1]);
        CommonInfo.flgDrctslsIndrctSls = mDataSource.fnGetflgDrctslsIndrctSlsForDSR(CoverageNodeId, CoverageNodeType);
        CommonInfo.hmapAppMasterFlags = mDataSource.fnGetAppMasterFlags(CommonInfo.flgDrctslsIndrctSls);
        IsDiscountApplicable = mDataSource.fnCheckIsDiscountLevelApplicableOnStore(storeID);
        if (IsDiscountApplicable == 0) {
            ll_GiveOverAllDiscountSection.setVisibility(View.GONE);
        }
        if (CommonInfo.flgDrctslsIndrctSls == 2) {

            StoreVisitCode = mDataSource.fnGetStoreVisitCodeInCaseOfIndrectSales(storeID);
        } else {

            StoreVisitCode = mDataSource.fnGetStoreVisitCode(storeID);
        }
        StoreCurrentStoreType = Integer.parseInt(mDataSource.fnGetStoreTypeOnStoreIdBasis(storeID));
        // chkflgInvoiceAlreadyGenerated=mDataSource.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode,CommonInfo.flgDrctslsIndrctSls);//0=Need to Generate Invoice Number,1=No Need of Generating Invoice Number
        storePics = mDataSource.fnGetStoreNoPicsFlag(StoreVisitCode);
        flgIsPicsAllowed = Integer.parseInt(storePics.split(Pattern.quote("^"))[0]);

        RegionID = mDataSource.fetchStoreRegionID(storeID);
        selectedCloaseCallOptionID = mDataSource.fnGetFlgCloseCallID(storeID, StoreVisitCode);
        selectedCloaseCallComment = mDataSource.fnGetFlgCloseCallComments(storeID, StoreVisitCode);
        if (CommonInfo.flgDrctslsIndrctSls == 2) {

            chkflgInvoiceAlreadyGenerated = mDataSource.fnCheckForNewInvoiceOrPreviousValue(storeID, StoreVisitCode, CommonInfo.flgDrctslsIndrctSls);
            if (chkflgInvoiceAlreadyGenerated == 1) {
                TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDA(storeID, StoreVisitCode);

            } else if (mDataSource.fnCheckForNewInvoiceOrPreviousValueFromPermanentTable(storeID, StoreVisitCode) == 1) {
                TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDAFromPermanentTable(storeID, StoreVisitCode);
            } else {
                TmpInvoiceCodePDA = genTempInvoiceCodePDA();
            }
        } else {
            chkflgInvoiceAlreadyGenerated = mDataSource.fnCheckForNewInvoiceOrPreviousValue(storeID, StoreVisitCode, CommonInfo.flgDrctslsIndrctSls);
            if (chkflgInvoiceAlreadyGenerated == 1) {
                TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDA(storeID, StoreVisitCode);

            } else {


                TmpInvoiceCodePDA = genTempInvoiceCodePDA();//mDataSource.fnGettblInvoiceCaption(storeID);
            }
        }
        distID = mDataSource.getDisId(storeID);
        hmapProductIdStock = mDataSource.fetchActualVisitData(storeID);

        hmapVanPrdctStockCount = mDataSource.fnGetFinalInvoiceQtyProductWise(CommonInfo.flgDrctslsIndrctSls);


        hmapDistPrdctStockCount = mDataSource.getStockAsPerFlg(distID);
        hmapCloseCallOption = mDataSource.fetchHmaptblNoOrderReasonMaster();
        if (flgOrderType == 0)
            mDataSource.UpdateStoreVisitBattVisitWise(storeID, LastVisitDetails.battLevel + "%", StoreVisitCode);
        //distID=mDataSource.getDisId(storeID);

        getCategoryDetail();
        getPrductInfoDetail();

        tblStoreProductMappingList = mDataSource.fnGetTblStoreProductMappingForSearch(storeID, TmpInvoiceCodePDA, StoreCurrentStoreType, RegionID);
        tblTCOrderStoreProductDiscountApplicable = mDataSource.fnGetTblStoreProductMappingUOMTCOrder(storeID, TmpInvoiceCodePDA, StoreCurrentStoreType, RegionID);
        hmapFilterProductListSchemes = mDataSource.getFileredProductListMapPRoductFilterSchemeProducts(SelectedCategoryForSchemeFileter.trim(), StoreCurrentStoreType, "" + SelectedCategoryIDForSchemeFileter, 0, 0);


        for (TblStoreProductMappingForDisplay tblStoreProductMapping : tblStoreProductMappingList) {
            if (tblStoreProductMapping.getPrdNodeID() == 56) {
                System.out.println("sdadad");
            }
            if (hmapProductFocusedBrand.containsKey("" + tblStoreProductMapping.getPrdNodeID())) {
                tblStoreProductMapping.setFlgFocusbrand(1);
            }
            if (hmapFilterProductListSchemes.containsKey("" + tblStoreProductMapping.getPrdNodeID())) {
                tblStoreProductMapping.setFlgProductWithScheme(1);
            }

            if (hmapflgTeleCallerProduct.containsKey("" + tblStoreProductMapping.getPrdNodeID())) {
                tblStoreProductMapping.setFlgTeleCallerProduct(1);
            }
            if (hmapflgTeleCallerProductQty.containsKey("" + tblStoreProductMapping.getPrdNodeID())) {
                tblStoreProductMapping.setTeleCallerProductQty(Integer.parseInt(hmapflgTeleCallerProductQty.get("" + tblStoreProductMapping.getPrdNodeID())));
                prdctModelArrayList.setPrdctQtyWithTCOrder("" + tblStoreProductMapping.getPrdNodeID(), hmapflgTeleCallerProductQty.get("" + tblStoreProductMapping.getPrdNodeID()));
            }

            if (hmapProductReplenishment.containsKey("" + tblStoreProductMapping.getPrdNodeID())) {
                tblStoreProductMapping.setFlgReplenishmentSKU(1);
            }


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


        getSchemeSlabDetails();
        hmapProductRelatedSchemesList = mDataSource.fnProductRelatedSchemesList(storeID);
        hmapProductAddOnSchemesList = mDataSource.fnProductADDOnScheme();
        hmapProductMinMax = mDataSource.getProductMinMax();
        hmapSchemeIDandDescr = mDataSource.fnSchemeIDandDescr();
        hmapSchmDscrptnAndBenfit = mDataSource.getSchmDscrptnAndBenfit();

        hmapMinDlvrQty = mDataSource.getMinDlvryQntty(storeID);
        hmapMinDlvrQtyQPBT = mDataSource.getMinDlvryQnttyQPBT(storeID);
        hmapMinDlvrQtyQPTaxAmount = mDataSource.getMinDlvryQnttyQPTaxAmount(storeID);
        hmapMinDlvrQtyQPAT = mDataSource.getMinDlvryQnttyQPAT(storeID);
        tblStoreProductAppliedSchemesBenifitsRecordsList = mDataSource.fnGetDetailsFromtblStoreProductAppliedSchemesBenifitsRecordsList(storeID, TmpInvoiceCodePDA);
        tblProductMappedWithSchemeSlabAppliedArrayList = mDataSource.fnGetDetailsFromTblProductMappedWithSchemeSlabAppliedRecordsList(storeID, TmpInvoiceCodePDA);
        tblStoreProductAddOnSchemeAppliedArrayList = mDataSource.fnGetDetailsFromTblStoreProductAddOnSchemeAppliedArrayList(storeID, TmpInvoiceCodePDA);
        tblSchemeSlabBucketProductMappingList = mDataSource.fnGetDetailsFromTblSchemeSlabBucketProductMapping();
        //tblAlrtValList=mDataSource.fnGetDetailsFromTblAlrtVal(storeID,TmpInvoiceCodePDA);
        tblSchemeSlabBenefitsBucketDetailsList = mDataSource.fnGetDetailsFromTblSchemeSlabBenefitsBucketDetails();
        tblSchemeSlabBenefitsValueDetailList = mDataSource.fnGetDetailsFromTblSchemeSlabBenefitsValueDetail();
        tblSchemeSlabBenefitsProductMappingDetailList = mDataSource.fnGetDetailsFromTblSchemeSlabBenefitsProductMappingDetail();


        fnMarkFirstProductCategoryRowVisible();
        String[] listProduct = new String[tblStoreProductMappingListForDisplay.size()];
        orderAdapter = new OrderAdapterProdcutEntry(ProductEntryForm.this, storeID, listProduct, hmapFilterProductList, hmapProductStandardRate,
                hmapProductMRP, hmapProductIdStock, hmapProductIdLastStock, hampGetLastProductExecution, hmapDistPrdctStockCount, hmapVanPrdctStockCount,
                prdctModelArrayList, 0, hmapPerBaseQty, hmapProductTblUOMMapping, hmapProductLODQty, hmapProductSuggestedQty, hmapProductImg,
                hmapProductRelatedSchemesList, flgIsAnySchemeMappedAgainstStore, tblStoreProductMappingListForDisplay, date, pickerDate, SN,
                flgOrderType, IsDiscountApplicable, tblTCOrderStoreProductDiscountApplicable,flgAppBtnClicked, new GetCartonPrdsInterface() {
            @Override
            public void getAllCartonPrds(List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysCarton, ProductCartonFilledDataModel productCartonFilledDataModel) {
                String sdadadasd = "abhinav";

                Log.v(sdadadasd, "Data passed successfullu");
                LinkedHashMap<String, String> hmapPrdctOrderQty = productCartonFilledDataModel.getHmapPrdctOrderQty();


                if (hmapPrdctOrderQty != null) {
                    for (Map.Entry<String, String> entry : hmapPrdctOrderQty.entrySet()) {


                        String ProductID = entry.getKey();
                        String prdctQty = "" + ((int) (Double.parseDouble(entry.getValue())));
                        // prdctModelArrayList.setPrdctQty(ProductID,""+ Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(ProductID))+Integer.parseInt(prdctQty));
                        if (!prdctQty.equals("") && !prdctQty.equals("0")) {
                            getOrderData(ProductID);
                        }

                    }

                }

            }
        });
        rv_prdct_detal.setAdapter(orderAdapter);
        rv_prdct_detal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        if (defaultCatName_Id.contains("^")) {
            ed_search.setText("");//defaultCatName_Id.split(Pattern.quote("^"))[0]


            searchProduct(defaultCatName_Id.split(Pattern.quote("^"))[0], defaultCatName_Id.split(Pattern.quote("^"))[1], 0, 0);
          /*  if (prdctModelArrayList.getHmapPrdctOrderQty() != null && prdctModelArrayList.getHmapPrdctOrderQty().size() > 0) {
                for (Map.Entry<String, String> entry : prdctModelArrayList.getHmapPrdctOrderQty().entrySet()) {
                    if (!entry.getValue().equals("") && !entry.getValue().equals("0")) {
                        getOrderData(entry.getKey());
                    }
                }
            }*/
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



        if(sharedPrefReport!=null && sharedPrefReport.contains("customCategoryfilter"))
        {
            int flgLoadDefault=0;
            for (TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingForFilter : tblProductTypeMasterForRetrivingList) {

                ArrayList<TblOnlyCategoryMasterForRetriving> tblProductTypeMasterForRetrivingListChildReords = tblProductTypeMasterForRetrivingForFilter.getTblOnlyCategoryMasterForRetrivingList();
                if (tblProductTypeMasterForRetrivingListChildReords != null && tblProductTypeMasterForRetrivingListChildReords.size() > 0) {
                    for (TblOnlyCategoryMasterForRetriving tblChildRecord : tblProductTypeMasterForRetrivingListChildReords) {
                        if(tblChildRecord.getSelected())
                        {
                            flgLoadDefault=1;
                            break;
                        }

                    }
                }
            }
            if(flgLoadDefault==1)
            searchForProdcutsBasedOnVariousConditions(hmapCatSelecteList, flgRepSKU, flgPrioritySKU, flgSchemePrd);
        }

    }


    public void fnMarkFirstProductCategoryRowVisible() {
        //tblStoreProductMappingListForSearching


        tblStoreProductMappingListForDisplay.clear();
        /*Iterator<TblStoreProductMappingForDisplay> crunchifyIteratorForSeacrh = tblStoreProductMappingListForSearching.iterator();
        if (tblStoreProductMappingListForSearching != null && tblStoreProductMappingListForSearching.size() > 0) {
            int catid = 0;
            Set catSet = new HashSet();
            while (crunchifyIteratorForSeacrh.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecordForSeach = crunchifyIteratorForSeacrh.next();
                if (!catSet.contains(tblStoreProductMappingRecordForSeach.getCatID())) {
                    tblStoreProductMappingRecordForSeach.setFlgShowCategoryHeader(1);
                    catSet.add(tblStoreProductMappingRecordForSeach.getCatID());
                } else {
                    tblStoreProductMappingRecordForSeach.setFlgShowCategoryHeader(0);
                }
            }
        }
*/

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

               /* if (!catSet.contains(tblStoreProductMappingRecord.getCatID())) {
                    tblStoreProductMappingRecord.setFlgShowCategoryHeader(1);
                    catSet.add(tblStoreProductMappingRecord.getCatID());
                } else {
                    tblStoreProductMappingRecord.setFlgShowCategoryHeader(0);
                }*/
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
        hmapProductIdLastStock = mDataSource.fnGetLastStockByDMS_Or_SFA(storeID);
        arrLstHmapPrdct = mDataSource.fetch_catgry_prdctsData(storeID, StoreCurrentStoreType, RegionID);
        hampGetLastProductExecution = mDataSource.fnGetHampGetLastProductExecution(storeID);
        lastStockDate = mDataSource.fnGetLastStockDate(storeID);
        hmapProductSuggestedQty = mDataSource.fnStoreProductSuggestedQty(storeID);
        hmapProductFocusedBrand = mDataSource.fnStoreProductFocusedProduct(storeID);
        hmapProductReplenishment = mDataSource.fnStoreProductReplenishmentSKU(storeID);
        hmapPerBaseQty = mDataSource.getPerBaseQty();
        hmapProductTblUOMMapping = mDataSource.fetch_ProductWiseGetAllMeasureUnitAgainstProduct(storeID);

        HashMap<String, String> hmapTempProd = mDataSource.FetchLODqty(storeID);

        if (arrLstHmapPrdct != null && arrLstHmapPrdct.size() > 0) {
            hmapCtgryPrdctDetail = arrLstHmapPrdct.get(0);
            hmapPrdctVolRatTax = arrLstHmapPrdct.get(1);
            hmapPrdctIdPrdctName = arrLstHmapPrdct.get(5);
            hmapProductVatTaxPerventage = arrLstHmapPrdct.get(8);
            hmapProductMRP = arrLstHmapPrdct.get(9);
            hmapProductTaxValue = arrLstHmapPrdct.get(12);
            hmapProductLODQty = arrLstHmapPrdct.get(13);
            hmapProductStandardRate = arrLstHmapPrdct.get(15);
            hmapProductStandardRateBeforeTax = arrLstHmapPrdct.get(16);
            hmapProductSelectedUOMId = arrLstHmapPrdct.get(25);
            hmapLineValBfrTxAftrDscnt = arrLstHmapPrdct.get(26);
            hmapLineValAftrTxAftrDscnt = arrLstHmapPrdct.get(27);
            hmapProductTaxValueWithTCOrder = arrLstHmapPrdct.get(12);
           /* hmapLineValBfrTxAftrDscntWithTCOrder = arrLstHmapPrdct.get(26);
            hmapLineValAftrTxAftrDscntWithTCOrder = arrLstHmapPrdct.get(27);*/


            hmapProductMeasureID = arrLstHmapPrdct.get(30);
            hmapPrdctFreeQty = arrLstHmapPrdct.get(4);
            hmapProductMeasureIDBaseQty = arrLstHmapPrdct.get(31);
            hmapProductFinalConversionUnitOnLoad = arrLstHmapPrdct.get(33);
            hmapProductImg = arrLstHmapPrdct.get(34);

        }
        hmapPrdctOdrQty = mDataSource.fnGetProductPurchaseList(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);

        hmapflgTeleCallerProduct = mDataSource.fnGethmapflgTeleCallerProduct(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);
        hmapflgTeleCallerProductQty = mDataSource.fnGethmapflgTeleCallerProductQty(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);
        hmapOrderQtyDataToShow = mDataSource.fnGetProductPurchaseListOrderQtyToShow(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);

        hmapProductTeleCallerID = mDataSource.fnGethmapProductTeleCallerID(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);

        hmapflgPicsOrCases = mDataSource.fnGetProductPurchaseListPicOrCases(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);


        HashMap<String, String> hmapPrdctOdrPrice = mDataSource.fnGetProductRateDuringPurchaseList(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);

        hmapPrdctFreeQty = mDataSource.fnGetProductPurchaseFreeOrderQty(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);
        hmapPrdctIdPrdctDscnt = mDataSource.fnGetProductPurchaseDiscountAmountAgainst(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);

        HashMap<String, String> hmapLineValAftrTxAftrDscntnew = new HashMap<String, String>();
        hmapLineValAftrTxAftrDscntnew = mDataSource.fnGetProductSkuPriceList(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated, flgInCarton);


        hmapPrdctCartonID = mDataSource.fnGetHmapProductsQtyInCarton(storeID, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls, chkflgInvoiceAlreadyGenerated);

        if (hmapPrdctOdrQty != null && hmapPrdctOdrQty.size() > 0) {
            for (Map.Entry<String, String> entry : hmapPrdctOdrQty.entrySet()) {
                if (Integer.parseInt(entry.getValue()) > 0) {
                    prdctModelArrayList.setPrdctQty(entry.getKey(), entry.getValue());
                }

            }

        }

        if (hmapPrdctQtyInCartonID != null && hmapPrdctQtyInCartonID.size() > 0) {
            for (Map.Entry<String, String> entry : hmapPrdctQtyInCartonID.entrySet()) {
                if (Integer.parseInt(entry.getValue()) > 0) {
                    prdctModelArrayList.setPrdctQtyInCartonID(entry.getKey(), entry.getValue());
                }

            }

        }


        if (hmapOrderQtyDataToShow != null && hmapOrderQtyDataToShow.size() > 0) {
            for (Map.Entry<String, String> entry : hmapOrderQtyDataToShow.entrySet()) {
                if (Integer.parseInt(entry.getValue()) > 0) {
                    prdctModelArrayList.setPrdctQtyToShow(entry.getKey(), entry.getValue());
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


        if (hmapLineValAftrTxAftrDscntnew != null && hmapLineValAftrTxAftrDscntnew.size() > 0) {
            for (Map.Entry<String, String> entry : hmapLineValAftrTxAftrDscntnew.entrySet()) {
                if (Double.parseDouble(entry.getValue()) > 0.0) {
                    prdctModelArrayList.setPrdctVal(entry.getKey(), entry.getValue());
                }

            }

        }

     /*   if (hmapPrdctOdrPrice != null && hmapPrdctOdrPrice.size() > 0) {
            for (Map.Entry<String, String> entry : hmapPrdctOdrPrice.entrySet()) {

                hmapProductStandardRate.put(entry.getKey(), entry.getValue());

            }

        }*/
        if (hmapPrdctFreeQty != null && hmapPrdctFreeQty.size() > 0) {
            for (Map.Entry<String, String> entry : hmapPrdctFreeQty.entrySet()) {
                if (Integer.parseInt(entry.getValue()) > 0) {
                    // prdctModelArrayList.setFreeQty(entry.getKey(), entry.getValue());
                    prdctModelArrayList.getHmapPrdctFreeQty().put(entry.getKey(), entry.getValue());
                }

            }

        }
        if (hmapPrdctIdPrdctDscnt != null && hmapPrdctIdPrdctDscnt.size() > 0) {
            for (Map.Entry<String, String> entry : hmapPrdctIdPrdctDscnt.entrySet()) {
                if (Double.parseDouble(entry.getValue()) > 0.0) {
                  /*  prdctModelArrayList.setDiscountValue(entry.getKey(), entry.getValue());
                    prdctModelArrayList.getHmapProductFlatDiscountGive().put(entry.getKey(), entry.getValue());*/
                    prdctModelArrayList.getHmapPrdctDiscountPerUOM().put(entry.getKey(), entry.getValue());
                }

            }

        }
        Iterator it = hmapProductLODQty.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (hmapTempProd.containsKey(pair.getKey().toString())) {
                hmapProductLODQty.put(pair.getKey().toString(), hmapTempProd.get(pair.getKey().toString()));
            }

        }
    }

    private void getCategoryDetail() {
        tblProductTypeMasterForRetrivingList = mDataSource.fetch_Category_List_OrderEntry(storeID);

        if(sharedPrefReport!=null && sharedPrefReport.contains("customCategoryfilter"))
        {
            Gson gson1=new Gson();
            String json1=sharedPrefReport.getString("customCategoryfilter",null);
            Type type=new TypeToken<ArrayList<TblProductTypeMasterForRetriving>>(){}.getType();
            ArrayList<TblProductTypeMasterForRetriving> sdfsdfsdfsfdasasd=gson1.fromJson(json1,type);
            tblProductTypeMasterForRetrivingList.clear();
            tblProductTypeMasterForRetrivingList.addAll(sdfsdfsdfsfdasasd);

        }
        hmapctgry_details = mDataSource.fetch_Category_List();

        int index = 0;
        if (hmapctgry_details != null) {


            /*categoryNames = new ArrayList<String>();
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapctgry_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                categoryNames.add(me2.getKey().toString());
                if (index == 0) {
                    defaultCatName_Id = me2.getKey().toString() + "^" + me2.getValue().toString();
                }
                index = index + 1;
            }*/
        }


    }

    public String genTempInvoiceCodePDA() {

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
        String  sdcxz =  "TmpInvoiceCodePDA" + "-" + IMEIid + "-" + randomString + "-" + VisitStartTS.replace(" ", "").replace(":", "").trim();
        return sdcxz;
        //-_
    }

    @Override
    public void onClick(View v) {
        EditText ed_LastEditextFocusd = prdctModelArrayList.getLastEditText();
        switch (v.getId()) {
            case R.id.et_Disc:
                if (ed_LastEditextFocusd != null) {
                    fcsLstCld(false, ed_LastEditextFocusd);
                }
                mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
                mCustomKeyboardNum.hideCustomKeyboard();
                mCustomKeyboardNum.showCustomKeyboard(et_Disc);

                break;
            case R.id.img_ctgry:
                img_ctgry.setEnabled(false);
                if (ed_LastEditextFocusd != null) {
                    fcsLstCld(false, ed_LastEditextFocusd);
                }
                customAlertStoreList(categoryNames, "Filter");
                break;
            case R.id.btn_bck:

                if(sharedPrefReport!=null && sharedPrefReport.contains("customCategoryfilter"))
                {
                    SharedPreferences.Editor editor = sharedPrefReport.edit();
                    editor.clear();
                    editor.commit();
                }
                if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0)) {
                    fnCreditAndStockCal(5, ed_LastEditextFocusd, hmapDistPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0)) {
                    fnCreditAndStockCal(5, ed_LastEditextFocusd, hmapDistPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) {
                    fnCreditAndStockCal(5, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) {
                    fnCreditAndStockCal(5, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                    fnCreditAndStockCal(5, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") &&  (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                    fnCreditAndStockCal(5, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 2) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                    fnCreditAndStockCal(5, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                } else {


                    nextStepAfterRetailerCreditBal(5);
                }

                break;
            case R.id.btn_NoOrder:
                OnResumeCheckWithNoOrderClick = 1;
                if (ed_LastEditextFocusd != null) {
                    fcsLstCld(false, ed_LastEditextFocusd);
                }
                if (mDataSource.fnCheckStoreIntblStoreListForUpdateMstr(storeID) == 1 && (mDataSource.fnCheckStoreMappedInStoreListForUpdateMstr(storeID) != 1) ) {
                  //  viewContactSection.setVisibility(View.VISIBLE);
                    showAlertForEveryOneForPhoneNumberValidation("Please Verify the Store Contact Number");
                   /* if (mDataSource.fnCheckStoreMappedInStoreListForUpdateMstr(storeID) == 1) {
                        viewContactSection.setVisibility(View.GONE);
                        tvContactNoCorrection.setText("Contact Number Verified");
                        tvContactNoCorrection.setTextColor(Color.parseColor("#74C543"));
                        new NextActivityCustomDialog(ProductEntryForm.this).show();
                    } else {
                        showAlertForEveryOneForPhoneNumberValidation("Please Verify the Store Contact Number");

                    }*/

                } else {

                    if (prdctModelArrayList.getHmapPrdctOrderQty() != null && prdctModelArrayList.getHmapPrdctOrderQty().size() > 0) {

                        showAlertSingleButtonInfo("We found some SKUs with order quantity, so cannot move with No Order Option.");
                    }
                    else if (tblProductCategoryUOMTypeLists != null && tblProductCategoryUOMTypeLists.size() > 0)
                    {

                        showAlertSingleButtonInfo("We found some SKUs with order quantity, so cannot move with No Order Option.");
                    }
                    else
                    {
                        new NextActivityCustomDialog(ProductEntryForm.this).show();
                    }



                }


                break;
            case R.id.btn_InvoiceReview:
                if (prdctModelArrayList.getHmapPrdctOrderQty() != null && prdctModelArrayList.getHmapPrdctOrderQty().size() > 0) {
                    if (ed_LastEditextFocusd != null) {
                        fcsLstCld(false, ed_LastEditextFocusd);
                    }

                    if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0)) {
                        fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapDistPrdctStockCount);
                    } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0)) {
                        fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapDistPrdctStockCount);
                    } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) {
                        fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                    } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) {
                        fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                    } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                        fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                    } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                        fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                    } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 2) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                        fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                    } else {
                        nextStepAfterRetailerCreditBal(0);
                    }
                } else {
                    if (tblProductCategoryUOMTypeLists != null && tblProductCategoryUOMTypeLists.size() > 0)
                    {
                        if (ed_LastEditextFocusd != null) {
                            fcsLstCld(false, ed_LastEditextFocusd);
                        }

                        if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0)) {
                            fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapDistPrdctStockCount);
                        } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0)) {
                            fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapDistPrdctStockCount);
                        } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) {
                            fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                        } else if ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) {
                            fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                        } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                            fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                        } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                            fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                        } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 2) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                            fnCreditAndStockCal(0, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                        } else {
                            nextStepAfterRetailerCreditBal(0);
                        }
                    }
                    else {
                        showAlertSingleButtonError("Can not proceed with no orders,\nPlease submiit by clicking No Order");
                    }
                }


                break;
            case R.id.btn_OrderReview:
                if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0)) {
                    fnCreditAndStockCal(1, ed_LastEditextFocusd, hmapDistPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0)) {
                    fnCreditAndStockCal(1, ed_LastEditextFocusd, hmapDistPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) {
                    fnCreditAndStockCal(1, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                } else if ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) {
                    fnCreditAndStockCal(1, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                    fnCreditAndStockCal(1, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                    fnCreditAndStockCal(1, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 2) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                    fnCreditAndStockCal(1, ed_LastEditextFocusd, hmapVanPrdctStockCount);
                } else {
                    nextStepAfterRetailerCreditBal(1);
                }

                break;
            case R.id.ed_search:
                if (ed_LastEditextFocusd != null) {
                    fcsLstCld(false, ed_LastEditextFocusd);
                }
                mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
                mCustomKeyboardNum.hideCustomKeyboard();
                AppUtils.showKeyBoard((EditText)v, this);
                break;
//            case R.id.btn_erase:
//                ed_search.setText("");
//                break;
//            case R.id.btn_go:
//                if (!TextUtils.isEmpty(ed_search.getText().toString().trim())) {
//                    if (!ed_search.getText().toString().trim().equals("")) {
//                        searchProduct(ed_search.getText().toString().trim(), "", 0, 0);
//                    }
//                }
        }


    }

    public void customAlertStoreList(final List<String> listOption, String sectionHeader) {

        final Dialog listDialog = new Dialog(ProductEntryForm.this);
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

        if(mDataSource.fnGetSectorID()==3)
        {
            cbSchemePrd.setVisibility(View.GONE);
        }

        if (flgRepSKU == 1)
            cbRepSKU.setChecked(true);

        if (flgPrioritySKU == 1)
            cbPrioritySKU.setChecked(true);

        if (flgSchemePrd == 1)
            cbSchemePrd.setChecked(true);


        txtVwSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbRepSKU.isChecked()) {
                    flgRepSKU = 1;
                }
                if (cbPrioritySKU.isChecked()) {
                    flgPrioritySKU = 1;
                }
                if (cbSchemePrd.isChecked()) {
                    flgSchemePrd = 1;
                }
                listDialog.dismiss();
                if (hmapCatSelecteList != null && hmapCatSelecteList.size() > 0) {
                    if (hmapCatSelecteList.size() == tblProductTypeMasterForRetrivingList.size() + 1) {
                        cbAll.setChecked(true);
                        flgAllCategory = 1;
                    } else {
                        flgAllCategory = 0;
                    }
                }
                if(sharedPrefReport!=null && sharedPrefReport.contains("customCategoryfilter"))
                {
                    SharedPreferences.Editor editor = sharedPrefReport.edit();
                    editor.clear();
                    editor.commit();
                }
                searchForProdcutsBasedOnVariousConditions(hmapCatSelecteList, flgRepSKU, flgPrioritySKU, flgSchemePrd);
                //    tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getCatID().In).toList();

            }
        });
        cbRepSKU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected())
                    flgRepSKU = 1;
                else
                    flgRepSKU = 0;
            }
        });
        cbPrioritySKU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected())
                    flgPrioritySKU = 1;
                else
                    flgPrioritySKU = 0;
            }
        });

        cbSchemePrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected())
                    flgSchemePrd = 1;
                else
                    flgSchemePrd = 0;
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

                        TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingListCurentReords=stream(tblProductTypeMasterForRetrivingList).where(p->p.getProductType().equals(tblProductTypeMasterForRetriving.getProductType().toString())).first();
                        ArrayList<TblOnlyCategoryMasterForRetriving> tblProductTypeMasterForRetrivingListChildReords =tblProductTypeMasterForRetrivingListCurentReords.getTblOnlyCategoryMasterForRetrivingList();
                        if(tblProductTypeMasterForRetrivingListChildReords!=null && tblProductTypeMasterForRetrivingListChildReords.size()>0)
                        {
                            for(TblOnlyCategoryMasterForRetriving tblChildRecord:tblProductTypeMasterForRetrivingListChildReords)
                            {
                                tblChildRecord.setSelected(true);
                            }
                        }

                    }
                } else {
                    flgAllCategory = 0;
                    hmapCatSelecteList.remove(0);
                    for (TblProductTypeMasterForRetriving tblProductTypeMasterForRetriving : tblProductTypeMasterForRetrivingList) {
                        hmapCatSelecteList.remove(tblProductTypeMasterForRetriving.getProductType());
                        TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingListCurentReords=stream(tblProductTypeMasterForRetrivingList).where(p->p.getProductType().equals(tblProductTypeMasterForRetriving.getProductType().toString())).first();
                        ArrayList<TblOnlyCategoryMasterForRetriving> tblProductTypeMasterForRetrivingListChildReords =tblProductTypeMasterForRetrivingListCurentReords.getTblOnlyCategoryMasterForRetrivingList();
                        if(tblProductTypeMasterForRetrivingListChildReords!=null && tblProductTypeMasterForRetrivingListChildReords.size()>0)
                        {
                            for(TblOnlyCategoryMasterForRetriving tblChildRecord:tblProductTypeMasterForRetrivingListChildReords)
                            {
                                tblChildRecord.setSelected(false);
                            }
                        }
                    }
                }
                cardArrayAdapter.updateList();
            }
        });


        //    TextView txtVwSubmit=(TextView) listDialog.findViewById(R.id.txtVwSubmit);

        final EditText ed_searchBrand = (AutoCompleteTextView) listDialog.findViewById(R.id.ed_search);
        ed_searchBrand.setVisibility(View.GONE);
        final RecyclerView list_store = (RecyclerView) listDialog.findViewById(R.id.list_store);
        cardArrayAdapter = new ExpandableCategoryAdapter(ProductEntryForm.this, listDialog, previousSlctdCtgry, tblProductTypeMasterForRetrivingList, CurrentCategorySelectedPosition, hmapCatSelecteList);

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

  /*  @Override
    public void selectedOption(String selectedCategory, Dialog dialog) {
        dialog.dismiss();
        previousSlctdCtgry=selectedCategory;
        String lastTxtSearch=ed_search.getText().toString().trim();
        //img_ctgry.setText(selectedCategory);
        ed_search.setText(previousSlctdCtgry);
        int filterType=0;
        if(hmapctgry_details.containsKey(selectedCategory))
        {
            searchProduct(selectedCategory,hmapctgry_details.get(selectedCategory),filterType);
        }
        else
        {
            searchProduct(selectedCategory,"",0);
        }



    }*/


    public void fnCreditAndStockCal(int butnClkd, EditText ed_LastEditextFocusd, HashMap<String, Integer> hmapPrdctStockCount) {

        if (ed_LastEditextFocusd != null) {

            String ProductIdOnClickedEdit = ed_LastEditextFocusd.getTag().toString().split(Pattern.quote("_"))[0];
            String tag = ed_LastEditextFocusd.getTag().toString();
            if (tag.contains("etOrderQty")) {
                String prdctQty = prdctModelArrayList.getPrdctOrderQty(ProductIdOnClickedEdit);

                if (!TextUtils.isEmpty(prdctQty) && (hmapPrdctStockCount != null) && (hmapPrdctStockCount.size() > 0)) {


                    int originalNetQntty = Integer.parseInt(prdctQty);
                    int totalStockLeft = 0;
                    if (hmapPrdctStockCount.containsKey(ProductIdOnClickedEdit)) {
                        totalStockLeft = hmapPrdctStockCount.get(ProductIdOnClickedEdit);
                    }


                    if (originalNetQntty > totalStockLeft) {
                        EditText edOrderCurrent = prdctModelArrayList.getLastEditText();
                        if (edOrderCurrent != null) {
                            alertForOrderExceedStock(ProductIdOnClickedEdit, edOrderCurrent, ed_LastEditextFocusd, butnClkd, hmapPrdctStockCount);
                        } else {
                            alertForOrderExceedStock(ProductIdOnClickedEdit, ed_LastEditextFocusd, ed_LastEditextFocusd, butnClkd, hmapPrdctStockCount);
                        }


                    } else {

                        nextStepAfterRetailerCreditBal(butnClkd);


                    }

                } else {


                    nextStepAfterRetailerCreditBal(butnClkd);


                }

            } else {

                nextStepAfterRetailerCreditBal(butnClkd);


            }
        } else {
            nextStepAfterRetailerCreditBal(butnClkd);

        }


    }

    public void nextStepAfterRetailerCreditBal(int btnClkd) {
        if (btnClkd != -1) {
            if (btnClkd == 0 || btnClkd == 1 || btnClkd == 4) // Invoice Review
            {
                orderInvoiceReviewClkd(btnClkd);
            }

            if (btnClkd == 5)// btn back pressed
            {
                orderInvoiceReviewClkd(btnClkd);

            }
        }

    }

    public void orderInvoiceReviewClkd(int btnClkd) {
        long StartClickTime = System.currentTimeMillis();
        Date dateobj1 = new Date(StartClickTime);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String StartClickTimeFinal = TimeUtils.getNetworkDateTime(ProductEntryForm.this, TimeUtils.DATE_TIME_FORMAT);

        String fileName = AppUtils.getToken(ProductEntryForm.this) + "_" + storeID;
        //StringBuffer content=new StringBuffer(imei+"_"+storeID+"_"+"SaveExit Button Click on Product List"+StartClickTimeFinal);
        //File file = new File("/sdcard/MeijiIndirectTextFile/"+fileName);
        File file = new File(Environment.getExternalStorageDirectory() + "/" + CommonInfo.TextFileFolder + "/" + fileName+".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        CommonInfo.fileContent = CommonInfo.fileContent + "     " + imei + "_" + storeID + "_" + "SaveExit Button Click on Product List" + StartClickTimeFinal;
        FileWriter fw;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(CommonInfo.fileContent);
            bw.close();
            //mDataSource.open();
            mDataSource.savetblMessageTextFileContainer(fileName, 0);
            //mDataSource.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if(tvAfterTaxValue!=null && !TextUtils.isEmpty(tvAfterTaxValue.getText().toString().trim()) && Double.parseDouble(tvAfterTaxValue.getText().toString().trim())>=0.0) {
            progressTitle = ProductEntryForm.this.getResources().getString(R.string.WhileWeSaveExit);
            //  progressTitle="While we save your data then review Order";
            new SaveData().execute(String.valueOf(btnClkd));
        }
        else
        {
            showAlertSingleButtonInfo("Order Value Can not be less then 0.");
        }
    }

    public void alertForOrderExceedStock(final String productOIDClkd, final EditText edOrderCurrent, final EditText edOrderCurrentLast, final int flagClkdButton, HashMap<String, Integer> hmapPrdctStockCount) {

        final Dialog listDialog = new Dialog(ProductEntryForm.this);
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listDialog.setContentView(R.layout.custom_stock_alert);
        listDialog.setCanceledOnTouchOutside(false);
        listDialog.setCancelable(false);
        WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
        parms.gravity = Gravity.CENTER;
        parms.width = WindowManager.LayoutParams.MATCH_PARENT;
        //there are a lot of settings, for dialog, check them all out!
        parms.dimAmount = (float) 0.5;

        int avilabQty = 0;
        if (hmapPrdctStockCount.containsKey(productOIDClkd)) {
            avilabQty = hmapPrdctStockCount.get(productOIDClkd);
        }

        //final int avilabQty=hmapDistPrdctStockCount.get(productOIDClkd);

        TextView order_detail = (TextView) listDialog.findViewById(R.id.order_detail);


        Button btn_done = (Button) listDialog.findViewById(R.id.btn_done);
        Button btn_cancel = (Button) listDialog.findViewById(R.id.btn_cancel);

        final EditText editText_prdctQty = (EditText) listDialog.findViewById(R.id.editText_prdctQty);
        editText_prdctQty.setText("" + avilabQty);
        editText_prdctQty.setVisibility(View.GONE);
        EditText ed_extraQty = (EditText) listDialog.findViewById(R.id.ed_extraQty);
        ed_extraQty.setVisibility(View.GONE);

        if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) || ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1))) {
            order_detail.setText(hmapFilterProductList.get(productOIDClkd) + "\n\n" + ProductEntryForm.this.getResources().getString(R.string.AvailableQty) + avilabQty + "\n" + ProductEntryForm.this.getResources().getString(R.string.RqrdQty) + prdctModelArrayList.getPrdctOrderQty(productOIDClkd) + "\n" + getText(R.string.alert_order_exceeds_stock));
            btn_cancel.setVisibility(View.VISIBLE);
            btn_done.setText("Change Qty");
        } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1))) {
            order_detail.setText(hmapFilterProductList.get(productOIDClkd) + "\n\n" + ProductEntryForm.this.getResources().getString(R.string.AvailableQty) + avilabQty + "\n" + ProductEntryForm.this.getResources().getString(R.string.RqrdQty) + prdctModelArrayList.getPrdctOrderQty(productOIDClkd) + "\n" + getText(R.string.alert_order_exceeds_stock));
            btn_cancel.setVisibility(View.VISIBLE);
            btn_done.setText("Change Qty");
        } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 2) && ((CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) || (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0))) {
            int dbrStock=0;
            if(hmapDistPrdctStockCount!=null && hmapDistPrdctStockCount.size()>0 && hmapDistPrdctStockCount.containsKey(productOIDClkd))
             dbrStock = hmapDistPrdctStockCount.get(productOIDClkd);
            int orderQtyFilled = Integer.parseInt(prdctModelArrayList.getPrdctOrderQty(productOIDClkd));
            if (orderQtyFilled > dbrStock) {
                btn_cancel.setVisibility(View.GONE);
                order_detail.setText(hmapFilterProductList.get(productOIDClkd) + "\n\n" + ProductEntryForm.this.getResources().getString(R.string.AvailableQty) + avilabQty + "\n" + ProductEntryForm.this.getResources().getString(R.string.RqrdQty) + prdctModelArrayList.getPrdctOrderQty(productOIDClkd) + "\n" + getText(R.string.order_exceeds_stock));

            } else {
                order_detail.setText(hmapFilterProductList.get(productOIDClkd) + "\n\n" + ProductEntryForm.this.getResources().getString(R.string.AvailableQty) + avilabQty + "\n" + ProductEntryForm.this.getResources().getString(R.string.RqrdQty) + prdctModelArrayList.getPrdctOrderQty(productOIDClkd) + "\n" + getText(R.string.alert_order_exceeds_stock));
                btn_cancel.setVisibility(View.VISIBLE);
                btn_done.setText("Change Qty");
            }

        } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2))) {
            btn_cancel.setVisibility(View.GONE);
            order_detail.setText(hmapFilterProductList.get(productOIDClkd) + "\n\n" + ProductEntryForm.this.getResources().getString(R.string.AvailableQty) + avilabQty + "\n" + ProductEntryForm.this.getResources().getString(R.string.RqrdQty) + prdctModelArrayList.getPrdctOrderQty(productOIDClkd) + "\n" + getText(R.string.order_exceeds_stock));
        } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2))) {
            btn_cancel.setVisibility(View.GONE);
            order_detail.setText(hmapFilterProductList.get(productOIDClkd) + "\n\n" + ProductEntryForm.this.getResources().getString(R.string.AvailableQty) + avilabQty + "\n" + ProductEntryForm.this.getResources().getString(R.string.RqrdQty) + prdctModelArrayList.getPrdctOrderQty(productOIDClkd) + "\n" + getText(R.string.order_exceeds_stock));
        } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 2) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2))) {
            btn_cancel.setVisibility(View.GONE);
            order_detail.setText(hmapFilterProductList.get(productOIDClkd) + "\n\n" + ProductEntryForm.this.getResources().getString(R.string.AvailableQty) + avilabQty + "\n" + ProductEntryForm.this.getResources().getString(R.string.RqrdQty) + prdctModelArrayList.getPrdctOrderQty(productOIDClkd) + "\n" + getText(R.string.order_exceeds_stock));
        } else {
            btn_cancel.setVisibility(View.GONE);
            order_detail.setText(hmapFilterProductList.get(productOIDClkd) + "\n\n" + ProductEntryForm.this.getResources().getString(R.string.AvailableQty) + avilabQty + "\n" + ProductEntryForm.this.getResources().getString(R.string.RqrdQty) + prdctModelArrayList.getPrdctOrderQty(productOIDClkd) + "\n" + getText(R.string.order_exceeds_stock));
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagClkdButton != -1) {
                    listDialog.dismiss();
                    nextStepAfterRetailerCreditBal(flagClkdButton);
                } else {
                    listDialog.dismiss();
                }
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listDialog.dismiss();
                prdctModelArrayList.removePrdctQty(productOIDClkd);

                edOrderCurrentLast.setText("");

                edOrderCurrent.clearFocus();
                edOrderCurrentLast.requestFocus();
                //alertForOrderExceedStock(productOIDClkd,edOrderCurrent,edOrderCurrentLast,-1);

            }
        });


        //now that the dialog is set up, it's time to show it
        listDialog.show();

    }

    @Override
    public void fcsLstCld(boolean hasFocus, EditText editText) {
        if(editText!=null)
           AppUtils.hideKeyBoard(editText, this);

        EditText et_ValueOnFocuslostnew = null;
        mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
        mCustomKeyboardNum.hideCustomKeyboard();
        if (editText!=null && !hasFocus) {
          //  tvDisc.performClick();
          /*  mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
            mCustomKeyboardNum.hideCustomKeyboard();*/
            EditText edtFcsLst = prdctModelArrayList.getFocusLostEditText();
            ed_LastEditextFocusd = editText;
            if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0)) {
                fnCreditAndStockCal(-1, edtFcsLst, hmapDistPrdctStockCount);
            } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") &&((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) || (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 2)) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 0)) {
                fnCreditAndStockCal(-1, edtFcsLst, hmapDistPrdctStockCount);
            } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") &&(CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) {
                fnCreditAndStockCal(-1, edtFcsLst, hmapVanPrdctStockCount);
            } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") &&(CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 1)) {
                fnCreditAndStockCal(-1, edtFcsLst, hmapVanPrdctStockCount);
            } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") &&(CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                fnCreditAndStockCal(-1, edtFcsLst, hmapVanPrdctStockCount);
            } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") &&(CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                fnCreditAndStockCal(-1, edtFcsLst, hmapVanPrdctStockCount);
            } else if (CommonInfo.hmapAppMasterFlags !=null && CommonInfo.hmapAppMasterFlags.containsKey("flgDBRStockControl")   && CommonInfo.hmapAppMasterFlags.containsKey("flgVanStockControl") &&(CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl") == 2) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl") == 2)) {
                fnCreditAndStockCal(-1, edtFcsLst, hmapVanPrdctStockCount);
            } else {
                nextStepAfterRetailerCreditBal(-1);
            }
            if (flgIsAnySchemeMappedAgainstStore == 1) {
                //First Calculate Scheme and the do Order Calculation
                // AppUtils.hideKeyBoard( editText, this);
                EditText et_ValueOnFocuslost = editText;
                et_ValueOnFocuslostnew = et_ValueOnFocuslost;

                ProductIdOnClickedEdit = et_ValueOnFocuslost.getTag().toString().split(Pattern.quote("_"))[0];
                if(hmapCtgryPrdctDetail!=null && hmapCtgryPrdctDetail.size()>0 && hmapCtgryPrdctDetail.containsKey(ProductIdOnClickedEdit)) {
                    CtaegoryIddOfClickedView = hmapCtgryPrdctDetail.get(ProductIdOnClickedEdit);
                }
                prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode().clear();
                fnCalculateSchemeAndMoveForworad(editText);

              //  orderBookingTotalCalc();
               /* try {
                    if(orderAdapter!=null)
                        orderAdapter.notifyAll();
                }
                catch (Exception ex)
                {

                }
*/
            } else {
              //  orderBookingTotalCalc();
            }
          //  orderBookingTotalCalc();

            orderBookingTotalCalc();

        } else {
            if (editText!=null) {
                if (editText.getTag().toString().contains("etOrderQty") || editText.getTag().toString().contains("etPrdRate")) {
                    if (editText.getTag().toString().contains("etOrderQty")) {
                        mCustomKeyboardNumWithoutDecimal.registerEditText(editText);
                        mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(editText);
                    }
                    if (editText.getTag().toString().contains("etPrdRate")) {
                        mCustomKeyboardNum.registerEditText(editText);
                        mCustomKeyboardNum.showCustomKeyboard(editText);
                    }
                } else {


                }
            }
        }

    }

    @Override
    public void fcsLstCldOnFocus(boolean hasFocus, EditText editText) {}/*{
        AppUtils.hideKeyBoard(editText, this);

        EditText et_ValueOnFocuslostnew = null;
        mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
        mCustomKeyboardNum.hideCustomKeyboard();

            if (editText.getTag().toString().contains("etOrderQty") || editText.getTag().toString().contains("etPrdRate")) {
                if (editText.getTag().toString().contains("etOrderQty")) {
                    mCustomKeyboardNumWithoutDecimal.registerEditText(editText);
                    mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(editText);
                }
                if (editText.getTag().toString().contains("etPrdRate")) {
                    mCustomKeyboardNum.registerEditText(editText);
                    mCustomKeyboardNum.showCustomKeyboard(editText);
                }
            } else {


            }


    }*/

    @Override
    public void selectedOption(String selectedCategory, String selectedText, Dialog dialog, int filterType, int ParentID, int parentPosition, Boolean isParentSelected, Boolean isChildSelected, TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingGlobal, ArrayList<TblOnlyCategoryMasterForRetriving> tblOnlyCategoryMasterForRetrivingGlobal, int crntChildID) {
        dialog.dismiss();
        previousSlctdCtgry = selectedCategory;
        String lastTxtSearch = ed_search.getText().toString().trim();
        //img_ctgry.setText(selectedCategory);
        // ed_search.setText(selectedCategory);
        CurrentCategorySelectedPosition = parentPosition;
        if (flgIsAnySchemeMappedAgainstStore == 1) {
            btn_ShowSchemeApplicableProducts.setVisibility(View.GONE);
        }
        btn_ShowAllProducts.setVisibility(View.GONE);

        SelectedCategoryForSchemeFileter = selectedCategory;
        SelectedCategoryIDForSchemeFileter = Integer.parseInt(selectedText);

        for (TblProductTypeMasterForRetriving ProductTypeMaster : tblProductTypeMasterForRetrivingList) {
            if (ProductTypeMaster.getProductTypeNodeID() == tblProductTypeMasterForRetrivingGlobal.getProductTypeNodeID()) {
                ProductTypeMaster.setExpanded(true);
                ProductTypeMaster.setParentPsition(parentPosition);
                ProductTypeMaster.setChildExpanded(isChildSelected);

               /* ProductTypeMaster.getTblOnlyCategoryMasterForRetrivingList().remove(tblOnlyCategoryMasterForRetrivingGlobal);
                ProductTypeMaster.setTblOnlyCategoryMasterForRetrivingList(tblOnlyCategoryMasterForRetrivingGlobal);
*/
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
    /*    tblProductTypeMasterForRetrivingList.remove(tblProductTypeMasterForRetrivingGlobal);

        tblProductTypeMasterForRetrivingList.add(tblProductTypeMasterForRetrivingGlobal);*/
        if (Integer.parseInt(selectedText) > 0) {
            searchProduct(selectedCategory, selectedText, filterType, ParentID);
        } else {
            searchProduct(selectedCategory, "", 0, ParentID);
        }


    }

    @Override
    public void fnCalculateNewDiscountApPerUOM() {}

    @Override
    public void CartonBoxClicked(String CatDesc,int CatID) {
        int Outstat=1;
        TransactionTableDataDeleteAndSaving(Outstat);
        InvoiceTableDataDeleteAndSaving(Outstat, flgTransferStatus);
        Intent intent = new Intent(ProductEntryForm.this, CartonProductEntry.class);
        intent.putExtra("StoreID", storeID);
        intent.putExtra("date", date);
        intent.putExtra("CatName", CatDesc);
        intent.putExtra("pickerDate", pickerDate);
        intent.putExtra("SN", SN);
        intent.putExtra("flgOrderType", flgOrderType);
        intent.putExtra("CatID", CatID);
        intent.putExtra("CartonID", "0");
        intent.putExtra("NoOfCarton", 0);
        intent.putExtra("UOMType", "NA");
        startActivity(intent);
        finish();
    }

   /* @Override
    public void getAllCartonPrds(List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysCarton, ProductCartonFilledDataModel productCartonFilledDataModel) {
String sdadadasd="abhinav";


        LinkedHashMap<String, String> hmapPrdctOrderQty = productCartonFilledDataModel.getHmapPrdctOrderQty();


        if (hmapPrdctOrderQty != null) {
            for (Map.Entry<String, String> entry : hmapPrdctOrderQty.entrySet()) {


                String ProductID = entry.getKey();
                String prdctQty = "" + ((int) (Double.parseDouble(entry.getValue())));
               // prdctModelArrayList.setPrdctQty(ProductID,""+ Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(ProductID))+Integer.parseInt(prdctQty));
                if (!prdctQty.equals("") && !prdctQty.equals("0")) {
                    getOrderData(ProductID);
                }

            }

        }
    }*/


    public class SaveData extends AsyncTask<String, String, Void> {
        int btnClkd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Text need to e changed according to btn Click
          /*  if (flgIsAnySchemeMappedAgainstStore == 1) {
                //First Calculate Scheme and the do Order Calculation

                orderBookingTotalCalc();
            } else {
                orderBookingTotalCalc();
            }
*/
            orderBookingTotalCalc();
            if (mProgressDialog.isShowing() == false) {
                mProgressDialog = new ProgressDialog(ProductEntryForm.this);
                mProgressDialog.setTitle(ProductEntryForm.this.getResources().getString(R.string.Loading));
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


            fnSaveFilledDataToDatabase(btnClkd);
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (mProgressDialog.isShowing() == true) {
                mProgressDialog.dismiss();
            }


            if (flgPriceMissing == 1) {
                if (btnClkd == 5) {
                    if (flgOrderType == 0) {
                        Intent prevP2 = new Intent(ProductEntryForm.this, StoreSelection.class);
                        String rID = mDataSource.GetActiveRouteID();
                        //Location_Getting_Service.closeFlag = 0;
                        prevP2.putExtra("imei", imei);
                        prevP2.putExtra("userDate", date);
                        prevP2.putExtra("pickerDate", pickerDate);
                        prevP2.putExtra("rID", rID);
                        startActivity(prevP2);
                        finish();
                    } else {
                        if (CommonInfo.hmapAppMasterFlags!=null && CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                            if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {

                              /*  Intent nxtP4 = new Intent(ProductEntryForm.this, ActualVisitStock.class);
                                nxtP4.putExtra("storeID", storeID);
                                nxtP4.putExtra("SN", SN);
                                nxtP4.putExtra("imei", imei);
                                nxtP4.putExtra("userdate", date);
                                nxtP4.putExtra("pickerDate", pickerDate);
                                startActivity(nxtP4);
                                finish();*/
                                if (flgIsPicsAllowed == 1) {
                                    Intent fireBackDetPg = new Intent(ProductEntryForm.this, PicAfterMerchandising.class);
                                    fireBackDetPg.putExtra("storeID", storeID);
                                    fireBackDetPg.putExtra("SN", SN);
                                    fireBackDetPg.putExtra("bck", 1);
                                    fireBackDetPg.putExtra("imei", imei);
                                    fireBackDetPg.putExtra("userdate", date);
                                    fireBackDetPg.putExtra("pickerDate", pickerDate);
                                    fireBackDetPg.putExtra("flgOrderType", 1);
                                    //fireBackDetPg.putExtra("rID", routeID);
                                    startActivity(fireBackDetPg);
                                    finish();
                                } else {
                                    Intent nxtP4 = new Intent(ProductEntryForm.this, ActualVisitStock.class);
                                    nxtP4.putExtra("storeID", storeID);
                                    nxtP4.putExtra("SN", SN);
                                    nxtP4.putExtra("imei", imei);
                                    nxtP4.putExtra("userdate", date);
                                    nxtP4.putExtra("pickerDate", pickerDate);
                                    nxtP4.putExtra("imei", AppUtils.getToken(ProductEntryForm.this));
                                    nxtP4.putExtra("StoreVisitCode", storeVisitCode);
                                    nxtP4.putExtra("token", AppUtils.getToken(ProductEntryForm.this));
                                    nxtP4.putExtra("flgOrderType", 1);
                                    startActivity(nxtP4);
                                    finish();
                                }

                            } else {
                                Intent fireBackDetPg = new Intent(ProductEntryForm.this, LastVisitDetails.class);
                                fireBackDetPg.putExtra("storeID", storeID);
                                fireBackDetPg.putExtra("SN", SN);
                                fireBackDetPg.putExtra("bck", 1);
                                fireBackDetPg.putExtra("imei", imei);
                                fireBackDetPg.putExtra("userdate", date);
                                fireBackDetPg.putExtra("pickerDate", pickerDate);
                                fireBackDetPg.putExtra("flgOrderType", 1);
                                //fireBackDetPg.putExtra("rID", routeID);
                                startActivity(fireBackDetPg);
                                finish();
                            }

                        } else {
                            Intent fireBackDetPg = new Intent(ProductEntryForm.this, LastVisitDetails.class);
                            fireBackDetPg.putExtra("storeID", storeID);
                            fireBackDetPg.putExtra("SN", SN);
                            fireBackDetPg.putExtra("bck", 1);
                            fireBackDetPg.putExtra("imei", imei);
                            fireBackDetPg.putExtra("userdate", date);
                            fireBackDetPg.putExtra("pickerDate", pickerDate);
                            fireBackDetPg.putExtra("flgOrderType", 1);
                            //fireBackDetPg.putExtra("rID", routeID);
                            startActivity(fireBackDetPg);
                            finish();
                        }
                    }


                } else {
                    allMessageAlertPriceMissing("Price required for " + PriceMissingPrdName);
                }
            } else {
                long syncTIMESTAMP = System.currentTimeMillis();
                Date dateobj = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat(
                        "dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
                String startTS = TimeUtils.getNetworkDateTime(ProductEntryForm.this, TimeUtils.DATE_TIME_FORMAT);
                mDataSource.UpdateStoreEndVisit(storeID, startTS);
                //mDataSource.close();


                if (btnClkd == 0) {
                    SharedPreferences.Editor editor = sharedPrefReport.edit();
                    Gson gson=new Gson();
                    String json= gson.toJson(tblProductTypeMasterForRetrivingList);
                    editor.putString("customCategoryfilter", json);
                    editor.commit();


                  /*  Gson gson1=new Gson();
                    String json1=sharedPrefReport.getString("customCategoryfilter",null);
                    Type type=new TypeToken<ArrayList<TblProductTypeMasterForRetriving>>(){}.getType();
                    ArrayList<TblProductTypeMasterForRetriving> sdfsdfsdfsfdasasd=gson1.fromJson(json1,type);*/


                    mDataSource.fnUpdateFlgCloseCallIDComment(0, "NA", storeID, StoreVisitCode);
                    Intent storeOrderReviewIntent = new Intent(ProductEntryForm.this, ProductInvoiceReview.class);
                    storeOrderReviewIntent.putExtra("storeID", storeID);
                    storeOrderReviewIntent.putExtra("SN", SN);
                    storeOrderReviewIntent.putExtra("bck", 1);
                    storeOrderReviewIntent.putExtra("imei", imei);
                    storeOrderReviewIntent.putExtra("userdate", date);
                    storeOrderReviewIntent.putExtra("pickerDate", pickerDate);
                    storeOrderReviewIntent.putExtra("OrderPDAID", TmpInvoiceCodePDA);
                    storeOrderReviewIntent.putExtra("flgOrderType", flgOrderType);
                    startActivity(storeOrderReviewIntent);
                    finish();
                }
                if (btnClkd == 1) {
                    Intent storeOrderReviewIntent = new Intent(ProductEntryForm.this, ProductOrderReview.class);
                    storeOrderReviewIntent.putExtra("storeID", storeID);
                    storeOrderReviewIntent.putExtra("SN", SN);
                    storeOrderReviewIntent.putExtra("bck", 1);
                    storeOrderReviewIntent.putExtra("imei", imei);
                    storeOrderReviewIntent.putExtra("userdate", date);
                    storeOrderReviewIntent.putExtra("pickerDate", pickerDate);
                    storeOrderReviewIntent.putExtra("OrderPDAID", TmpInvoiceCodePDA);
                    storeOrderReviewIntent.putExtra("flgOrderType", flgOrderType);
                    startActivity(storeOrderReviewIntent);
                    finish();
                }
                if (btnClkd == 5) {
                    if (flgOrderType == 0) {
                        Intent prevP2 = new Intent(ProductEntryForm.this, StoreSelection.class);
                        String rID = mDataSource.GetActiveRouteID();
                        //Location_Getting_Service.closeFlag = 0;
                        prevP2.putExtra("imei", imei);
                        prevP2.putExtra("userDate", date);
                        prevP2.putExtra("pickerDate", pickerDate);
                        prevP2.putExtra("rID", rID);
                        startActivity(prevP2);
                        finish();
                    } else {
                        if (CommonInfo.hmapAppMasterFlags!=null && CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                            if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {

                                if (flgIsPicsAllowed == 1) {
                                    Intent fireBackDetPg = new Intent(ProductEntryForm.this, PicAfterMerchandising.class);
                                    fireBackDetPg.putExtra("storeID", storeID);
                                    fireBackDetPg.putExtra("SN", SN);
                                    fireBackDetPg.putExtra("bck", 1);
                                    fireBackDetPg.putExtra("imei", imei);
                                    fireBackDetPg.putExtra("userdate", date);
                                    fireBackDetPg.putExtra("pickerDate", pickerDate);
                                    fireBackDetPg.putExtra("flgOrderType", 1);
                                    //fireBackDetPg.putExtra("rID", routeID);
                                    startActivity(fireBackDetPg);
                                    finish();
                                } else {
                                    Intent nxtP4 = new Intent(ProductEntryForm.this, ActualVisitStock.class);
                                    nxtP4.putExtra("storeID", storeID);
                                    nxtP4.putExtra("SN", SN);
                                    nxtP4.putExtra("imei", imei);
                                    nxtP4.putExtra("userdate", date);
                                    nxtP4.putExtra("pickerDate", pickerDate);
                                    nxtP4.putExtra("imei", AppUtils.getToken(ProductEntryForm.this));
                                    nxtP4.putExtra("StoreVisitCode", storeVisitCode);
                                    nxtP4.putExtra("token", AppUtils.getToken(ProductEntryForm.this));
                                    nxtP4.putExtra("flgOrderType", 1);
                                    startActivity(nxtP4);
                                    finish();
                                }
                            } else {
                                Intent fireBackDetPg = new Intent(ProductEntryForm.this, LastVisitDetails.class);
                                fireBackDetPg.putExtra("storeID", storeID);
                                fireBackDetPg.putExtra("SN", SN);
                                fireBackDetPg.putExtra("bck", 1);
                                fireBackDetPg.putExtra("imei", imei);
                                fireBackDetPg.putExtra("userdate", date);
                                fireBackDetPg.putExtra("pickerDate", pickerDate);
                                fireBackDetPg.putExtra("flgOrderType", 1);
                                //fireBackDetPg.putExtra("rID", routeID);
                                startActivity(fireBackDetPg);
                                finish();
                            }

                        } else {
                            Intent fireBackDetPg = new Intent(ProductEntryForm.this, LastVisitDetails.class);
                            fireBackDetPg.putExtra("storeID", storeID);
                            fireBackDetPg.putExtra("SN", SN);
                            fireBackDetPg.putExtra("bck", 1);
                            fireBackDetPg.putExtra("imei", imei);
                            fireBackDetPg.putExtra("userdate", date);
                            fireBackDetPg.putExtra("pickerDate", pickerDate);
                            fireBackDetPg.putExtra("flgOrderType", 1);
                            //fireBackDetPg.putExtra("rID", routeID);
                            startActivity(fireBackDetPg);
                            finish();
                        }
                    }

                }
//For Button 4

                if (btnClkd == 4) // btn return clkd
                {
                    Intent fireBackDetPg = new Intent(ProductEntryForm.this, ReturnActivity.class);
                    fireBackDetPg.putExtra("storeID", storeID);
                    fireBackDetPg.putExtra("SN", SN);
                    fireBackDetPg.putExtra("bck", 1);
                    fireBackDetPg.putExtra("imei", imei);
                    fireBackDetPg.putExtra("userdate", date);
                    fireBackDetPg.putExtra("pickerDate", pickerDate);
                    fireBackDetPg.putExtra("OrderPDAID", TmpInvoiceCodePDA);
                    fireBackDetPg.putExtra("flgPageToRedirect", "1");
                    fireBackDetPg.putExtra("flgOrderType", flgOrderType);
                    // fireBackDetPg.putExtra("rID", routeID);

                    startActivity(fireBackDetPg);
                    finish();
                }
            }
           /* long syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat(
                    "dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
            String startTS = TimeUtils.getNetworkDateTime(ProductEntryForm.this,TimeUtils.DATE_TIME_FORMAT);
            mDataSource.UpdateStoreEndVisit(storeID,startTS);
            //mDataSource.close();


            if(btnClkd==0) {
             mDataSource.fnUpdateFlgCloseCallIDComment( 0,"NA",storeID,StoreVisitCode);
                Intent storeOrderReviewIntent = new Intent(ProductEntryForm.this, ProductInvoiceReview.class);
                storeOrderReviewIntent.putExtra("storeID", storeID);
                storeOrderReviewIntent.putExtra("SN", SN);
                storeOrderReviewIntent.putExtra("bck", 1);
                storeOrderReviewIntent.putExtra("imei", imei);
                storeOrderReviewIntent.putExtra("userdate", date);
                storeOrderReviewIntent.putExtra("pickerDate", pickerDate);
                storeOrderReviewIntent.putExtra("OrderPDAID", TmpInvoiceCodePDA);
                storeOrderReviewIntent.putExtra("flgOrderType", flgOrderType);
                startActivity(storeOrderReviewIntent);
                finish();
            }
            if(btnClkd==1) {
                Intent storeOrderReviewIntent = new Intent(ProductEntryForm.this, ProductOrderReview.class);
                storeOrderReviewIntent.putExtra("storeID", storeID);
                storeOrderReviewIntent.putExtra("SN", SN);
                storeOrderReviewIntent.putExtra("bck", 1);
                storeOrderReviewIntent.putExtra("imei", imei);
                storeOrderReviewIntent.putExtra("userdate", date);
                storeOrderReviewIntent.putExtra("pickerDate", pickerDate);
                storeOrderReviewIntent.putExtra("OrderPDAID", TmpInvoiceCodePDA);
                storeOrderReviewIntent.putExtra("flgOrderType", flgOrderType);
                startActivity(storeOrderReviewIntent);
                finish();
            }
            if(btnClkd==5) {
                if(flgOrderType==0)
                {
                    Intent prevP2 = new Intent(ProductEntryForm.this, StoreSelection.class);
                    String rID= mDataSource.GetActiveRouteID();
                    //Location_Getting_Service.closeFlag = 0;
                    prevP2.putExtra("imei", imei);
                    prevP2.putExtra("userDate", date);
                    prevP2.putExtra("pickerDate", pickerDate);
                    prevP2.putExtra("rID", rID);
                    startActivity(prevP2);
                    finish();
                }
                else
                {
                    if(CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable"))
                    {
                        if(CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable")==1)
                        {

                            Intent nxtP4 = new Intent(ProductEntryForm.this,ActualVisitStock.class);
                            nxtP4.putExtra("storeID", storeID);
                            nxtP4.putExtra("SN", SN);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", date);
                            nxtP4.putExtra("pickerDate", pickerDate);
                            startActivity(nxtP4);
                            finish();
                        }
                        else
                        {
                            Intent fireBackDetPg=new Intent(ProductEntryForm.this,LastVisitDetails.class);
                            fireBackDetPg.putExtra("storeID", storeID);
                            fireBackDetPg.putExtra("SN", SN);
                            fireBackDetPg.putExtra("bck", 1);
                            fireBackDetPg.putExtra("imei", imei);
                            fireBackDetPg.putExtra("userdate", date);
                            fireBackDetPg.putExtra("pickerDate", pickerDate);
                            fireBackDetPg.putExtra("flgOrderType", 1);
                            //fireBackDetPg.putExtra("rID", routeID);
                            startActivity(fireBackDetPg);
                            finish();
                        }

                    }
                    else
                    {
                        Intent fireBackDetPg=new Intent(ProductEntryForm.this,LastVisitDetails.class);
                        fireBackDetPg.putExtra("storeID", storeID);
                        fireBackDetPg.putExtra("SN", SN);
                        fireBackDetPg.putExtra("bck", 1);
                        fireBackDetPg.putExtra("imei", imei);
                        fireBackDetPg.putExtra("userdate", date);
                        fireBackDetPg.putExtra("pickerDate", pickerDate);
                        fireBackDetPg.putExtra("flgOrderType", 1);
                        //fireBackDetPg.putExtra("rID", routeID);
                        startActivity(fireBackDetPg);
                        finish();
                    }
                }

            }
//For Button 4

            if(btnClkd==4) // btn return clkd
            {
                Intent fireBackDetPg=new Intent(ProductEntryForm.this,ReturnActivity.class);
                fireBackDetPg.putExtra("storeID", storeID);
                fireBackDetPg.putExtra("SN", SN);
                fireBackDetPg.putExtra("bck", 1);
                fireBackDetPg.putExtra("imei", imei);
                fireBackDetPg.putExtra("userdate", date);
                fireBackDetPg.putExtra("pickerDate", pickerDate);
                fireBackDetPg.putExtra("OrderPDAID", TmpInvoiceCodePDA);
                fireBackDetPg.putExtra("flgPageToRedirect", "1");
                fireBackDetPg.putExtra("flgOrderType", flgOrderType);
                // fireBackDetPg.putExtra("rID", routeID);

                startActivity(fireBackDetPg);
                finish();
            }*/
        }


    }


    public void fnSaveFilledDataToDatabase(int valBtnClickedFrom) {

        int Outstat = 1;
        flgPriceMissing = 0;
        PriceMissingPrdName = "";

        if (flgPriceMissing == 1) {
            // allMessageAlertPriceMissing("Price required for "+PriceMissingPrdName );
        } else {
            if (valBtnClickedFrom != 5) {
                TransactionTableDataDeleteAndSaving(Outstat);
                InvoiceTableDataDeleteAndSaving(Outstat, flgTransferStatus);

                //mDataSource.open();
                mDataSource.UpdateStoreFlag(storeID.trim(), 1);
                mDataSource.UpdateStoreOtherMainTablesFlag(storeID.trim(), 1, TmpInvoiceCodePDA, TmpInvoiceCodePDA);
                mDataSource.UpdateStoreStoreReturnDetail(storeID.trim(), "1", TmpInvoiceCodePDA, TmpInvoiceCodePDA);
                mDataSource.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(), "1", TmpInvoiceCodePDA, TmpInvoiceCodePDA);
                //mDataSource.close();

                if (mDataSource.checkCountIntblStoreSalesOrderPaymentDetails(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA) == 0) {
                    String strDefaultPaymentStageForStore = mDataSource.fnGetDefaultStoreOrderPAymentDetails(storeID);
                    if (!strDefaultPaymentStageForStore.equals("")) {
                        //mDataSource.open();
                        mDataSource.fnsaveStoreSalesOrderPaymentDetails(storeID, TmpInvoiceCodePDA, strDefaultPaymentStageForStore, "1", TmpInvoiceCodePDA);
                        //mDataSource.close();
                    }
                }
                long syncTIMESTAMP = System.currentTimeMillis();
                Date dateobj = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                String StampEndsTime = df.format(dateobj);
            }
        }


    }


    public void TransactionTableDataDeleteAndSaving(int Outstat) {

        mDataSource.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsaction(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA, flgInCarton);
        flgPriceMissing = 0;
        PriceMissingPrdName = "";
        //LinkedHashMap<String,String> hmapPrdctOrderQty=prdctModelArrayList.getHmapPrdctOrderQty();
        if (tblStoreProductMappingList != null && tblStoreProductMappingList.size() > 0) {
            for (TblStoreProductMappingForDisplay entry : tblStoreProductMappingList) {
                int PCateId = entry.getCatID();// Integer.parseInt(hmapCtgryPrdctDetail.get(entry.getKey()));
                String PName = entry.getPrdName();// entry.getValue();
                PName = PName.replaceAll("&", "-");
                String ProductID = "" + entry.getPrdNodeID();// entry.getKey();
                String ProductStock = "0";
                if (hmapProductIdStock != null && hmapProductIdStock.containsKey(ProductID)) {
                    ProductStock = hmapProductIdStock.get(ProductID);
                }


                int ProductExtraOrder = 0;
                double TaxRate = 0.00;
                double TaxValue = 0.00;

                double TaxValueWithTCOrder = 0.00;

                if (ProductStock == null) {
                    ProductStock = "0";
                }
                String SampleQTY = "0";
                if (SampleQTY.equals("")) {
                    SampleQTY = "0";
                }
                String OrderQTY = prdctModelArrayList.getPrdctOrderQty(ProductID);
                if (OrderQTY.equals("")) {
                    OrderQTY = "0";

                }

                String OrderQTYOnTC = "0";


                if (hmapflgTeleCallerProductQty != null && hmapflgTeleCallerProductQty.containsKey(ProductID)) {
                    OrderQTYOnTC = hmapflgTeleCallerProductQty.get(ProductID);
                }
                if (OrderQTYOnTC.equals("")) {
                    OrderQTYOnTC = "0";

                }


                String OrderQTYWithTCOrder = "" + (Integer.parseInt(OrderQTY) + Integer.parseInt(OrderQTYOnTC));

                String OrderQTYToShow = "0";
                if (prdctModelArrayList.getPrdctOrderQtyToShow(ProductID).equals("")) {
                    OrderQTYToShow = "0";
                } else {
                    OrderQTYToShow = "" + (Integer.parseInt(prdctModelArrayList.getPrdctOrderQtyToShow(ProductID)));
                }
                String OrderQTYToShowWithTCOrder = "" + (Integer.parseInt(OrderQTYToShow) + Integer.parseInt(OrderQTYOnTC));

                int UOMSeleted = 3;
                if (prdctModelArrayList.getHmapflgPicsOrCases().containsKey(ProductID)) {
                    if (!prdctModelArrayList.getPrdctOrderMappedToPicsOrCases(ProductID).equals("")) {
                        UOMSeleted = Integer.parseInt(prdctModelArrayList.getPrdctOrderMappedToPicsOrCases(ProductID));
                    }
                }

                String OrderValue = "0";
                if (Integer.parseInt(OrderQTY) > 0) {
                    OrderValue = String.valueOf(prdctModelArrayList.getPrdctOrderVal(ProductID));// ((TextView)(vRow).findViewById(R.id.tv_Orderval)).getText().toString();
                    if (OrderValue.equals("")) {
                        OrderValue = "0";
                    }
                }
                String OrderValueWithTCOrder = "0";
                if (Integer.parseInt(OrderQTYOnTC)>0 && (hmapLineValAftrTxAftrDscntWithTCOrder != null && hmapLineValAftrTxAftrDscntWithTCOrder.size() > 0 && hmapLineValAftrTxAftrDscntWithTCOrder.containsKey(ProductID))) {
                    OrderValueWithTCOrder = String.valueOf(hmapLineValAftrTxAftrDscntWithTCOrder.get(ProductID));
                    if (OrderValueWithTCOrder.equals("")) {
                        OrderValueWithTCOrder = "0";
                    }
                }

                String OrderValueWithTCOrderBeforeTax = "0";
                if (Integer.parseInt(OrderQTYOnTC)>0 && (hmapLineValBfrTxAftrDscntWithTCOrder != null && hmapLineValBfrTxAftrDscntWithTCOrder.size() > 0 && hmapLineValBfrTxAftrDscntWithTCOrder.containsKey(ProductID))) {
                    OrderValueWithTCOrderBeforeTax = String.valueOf(hmapLineValBfrTxAftrDscntWithTCOrder.get(ProductID));
                    if (OrderValueWithTCOrderBeforeTax.equals("")) {
                        OrderValueWithTCOrderBeforeTax = "0";
                    }
                }
                OrderValue = "" + (Double.parseDouble(OrderValue) + Double.parseDouble(OrderValueWithTCOrder));

                String OrderFreeQty = "0";

                if (prdctModelArrayList.getHmapPrdctFreeQty().containsKey(ProductID)) {
                    OrderFreeQty = prdctModelArrayList.getHmapPrdctFreeQty().get(ProductID);
                }

                String OrderDisVal = "0";
                if (Integer.parseInt(OrderQTY) > 0) {
                    // hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);

                    if (prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey(ProductID) && !TextUtils.isEmpty(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(ProductID))) {

                        OrderDisVal =""+(Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(ProductID))*Double.parseDouble(OrderQTY));
                    }

                    if (Double.parseDouble(OrderDisVal)==0.0) {
                        OrderDisVal = "0";
                    }

                /*    if (hmapPrdctIdPrdctDscnt != null && hmapPrdctIdPrdctDscnt.size() > 0 && hmapPrdctIdPrdctDscnt.containsKey(ProductID))
                        OrderDisVal = hmapPrdctIdPrdctDscnt.get(ProductID);*/

                }


                double OrderDisValOverAll = 0.0;

                if (prdctModelArrayList.getHmapPrdctOverAllDiscountCalculated().containsKey(ProductID)) {
                //+ Double.parseDouble(prdctModelArrayList.getHmapPrdctOverAllDiscountCalculated().get(ProductID));
                }


                double OrderDisPerUOM = 0.0;

                if (prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey(ProductID) && !TextUtils.isEmpty(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(ProductID))) {
                    OrderDisPerUOM = Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(ProductID));
                    OrderDisValOverAll =OrderDisPerUOM*Double.parseDouble(OrderQTY);
                }

                String PRate = "0.00";
                int flgIsQuoteRateApplied = 0;


                // PRate = hmapProductStandardRate.get(ProductID);
                /*if (ProductID.equals("56")) {
                    System.out.println("Tans fun PridID:-" + ProductID);
                }*/
                List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysSingleItem = stream(tblStoreProductMappingList).where(p -> p.getPrdNodeID() == Integer.parseInt(ProductID)).toList();
                List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                if (UOMMasterDAta.size() > 0)
                    PRate = "" + UOMMasterDAta.get(0).getStandardRate();
                else
                    PRate = "" + hmapProductStandardRate.get(ProductID);

                TaxRate = Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
                if (hmapProductTaxValue != null && hmapProductTaxValue.containsKey(ProductID)) {
                    TaxValue = Double.parseDouble(hmapProductTaxValue.get(ProductID));
                }
                if (hmapProductTaxValueWithTCOrder != null && hmapProductTaxValueWithTCOrder.size() > 0 && hmapProductTaxValueWithTCOrder.containsKey(ProductID))
                    TaxValueWithTCOrder = Double.parseDouble(hmapProductTaxValueWithTCOrder.get(ProductID));

                if (Integer.valueOf(OrderQTY) > 0 && Double.parseDouble(PRate) == 0.0) {
                    flgPriceMissing = 1;
                    PriceMissingPrdName = PName;
                    break;
                    //  return;
                }
                String TeleCallingID = "0";
               /* if (hmapProductTeleCallerID != null && hmapProductTeleCallerID.containsKey(storeID)) {
                    TeleCallingID = hmapProductTeleCallerID.get(storeID);
                }*/
                int flgTeleCallerProduct = 0;
                if (hmapflgTeleCallerProduct != null && hmapflgTeleCallerProduct.containsKey(ProductID)) {
                    flgTeleCallerProduct = Integer.parseInt(hmapflgTeleCallerProduct.get(ProductID));
                }
                int OriginalTeleCallerProductQty = 0;
                // if (prdctModelArrayList != null && prdctModelArrayList.getHmapOrderQtyDataToShow() != null && prdctModelArrayList.getHmapOrderQtyDataToShow().containsKey(ProductID)) {
                OriginalTeleCallerProductQty = Integer.parseInt(OrderQTYOnTC);// Integer.parseInt(prdctModelArrayList.getHmapOrderQtyDataToShow().get(ProductID));
                // }


                String CartonID = "0";

               /* if (prdctModelArrayList != null && prdctModelArrayList.getHmapPrdctCartonID() != null && prdctModelArrayList.getHmapPrdctCartonID().containsKey(ProductID)) {
                    CartonID = prdctModelArrayList.getPrdctCartonID(ProductID);
                }*/


//, int flgTeleCallerProduct, int flgUserEditedProduct, int OriginalTeleCallerProductQty

                double DiscountPerUOMTCOrder = 0.0;
                double OverAllDiscountTCOrder = 0.0;
              //  if (Integer.valueOf(OrderQTYWithTCOrder) > 0) {
                  //  int flgRuleTaxVal = 1;
                   /* if (Integer.parseInt(OrderQTY) == 0 || Integer.valueOf(OrderQTYWithTCOrder) > Integer.parseInt(OrderQTY)) {
                        mDataSource.fnsaveStoreTempOrderEntryDetailsMainSyncTable(TmpInvoiceCodePDA, storeID, "" + PCateId, ProductID, Double.parseDouble(PRate), TaxRate, flgRuleTaxVal, Integer.parseInt(OrderQTYWithTCOrder), Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)), Double.parseDouble(OrderValueWithTCOrderBeforeTax), Double.parseDouble(OrderValueWithTCOrder), Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]), Double.parseDouble(OrderDisVal), Integer.parseInt(SampleQTY), PName, TaxValueWithTCOrder, TmpInvoiceCodePDA, flgIsQuoteRateApplied, PriceApplyDiscountLevelType, distID, Outstat, ProductExtraOrder, OrderValue, 0, Integer.parseInt(OrderQTYToShowWithTCOrder), UOMSeleted, 1, 0, OriginalTeleCallerProductQty, TeleCallingID, CartonID, flgInCarton, DiscountPerUOMTCOrder, OverAllDiscountTCOrder);
                    } else {
                        mDataSource.fnsaveStoreTempOrderEntryDetailsMainSyncTable(TmpInvoiceCodePDA, storeID, "" + PCateId, ProductID, Double.parseDouble(PRate), TaxRate, flgRuleTaxVal, Integer.parseInt(OrderQTYWithTCOrder), Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)), Double.parseDouble(OrderValueWithTCOrderBeforeTax), Double.parseDouble(OrderValueWithTCOrder), Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]), Double.parseDouble(OrderDisVal), Integer.parseInt(SampleQTY), PName, TaxValueWithTCOrder, TmpInvoiceCodePDA, flgIsQuoteRateApplied, PriceApplyDiscountLevelType, distID, Outstat, ProductExtraOrder, OrderValue, 0, Integer.parseInt(OrderQTYToShowWithTCOrder), UOMSeleted, flgTeleCallerProduct, 0, OriginalTeleCallerProductQty, TeleCallingID, CartonID, flgInCarton, OrderDisPerUOM, OrderDisValOverAll);
                    }*/

                   /* if (Integer.parseInt(OrderQTY) == 0 || Integer.valueOf(OrderQTYWithTCOrder) > Integer.parseInt(OrderQTY)) {
                        mDataSource.fnsaveStoreTempOrderEntryDetailsMainSyncTable(TmpInvoiceCodePDA, storeID, "" + PCateId, ProductID, Double.parseDouble(PRate), TaxRate, flgRuleTaxVal, Integer.parseInt(OrderQTYWithTCOrder), Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)), Double.parseDouble(OrderValueWithTCOrderBeforeTax), Double.parseDouble(OrderValueWithTCOrder), Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]), Double.parseDouble(OrderDisVal), Integer.parseInt(SampleQTY), PName, TaxValueWithTCOrder, TmpInvoiceCodePDA, flgIsQuoteRateApplied, PriceApplyDiscountLevelType, distID, Outstat, ProductExtraOrder, OrderValue, 0, Integer.parseInt(OrderQTYToShowWithTCOrder), UOMSeleted, 1, 0, OriginalTeleCallerProductQty, TeleCallingID, CartonID, flgInCarton, DiscountPerUOMTCOrder, OverAllDiscountTCOrder);
                    } else {
                        mDataSource.fnsaveStoreTempOrderEntryDetailsMainSyncTable(TmpInvoiceCodePDA, storeID, "" + PCateId, ProductID, Double.parseDouble(PRate), TaxRate, flgRuleTaxVal, Integer.parseInt(OrderQTYWithTCOrder), Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)), Double.parseDouble(OrderValueWithTCOrderBeforeTax), Double.parseDouble(OrderValueWithTCOrder), Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]), Double.parseDouble(OrderDisVal), Integer.parseInt(SampleQTY), PName, TaxValueWithTCOrder, TmpInvoiceCodePDA, flgIsQuoteRateApplied, PriceApplyDiscountLevelType, distID, Outstat, ProductExtraOrder, OrderValue, 0, Integer.parseInt(OrderQTYToShowWithTCOrder), UOMSeleted, flgTeleCallerProduct, 0, OriginalTeleCallerProductQty, TeleCallingID, CartonID, flgInCarton, OrderDisPerUOM, OrderDisValOverAll);
                    }*/

                //}
                if (Integer.valueOf(OrderFreeQty) > 0 || Integer.valueOf(SampleQTY) > 0 || Integer.valueOf(OrderQTY) > 0 || Double.parseDouble(OrderValue) > 0.0 || Integer.valueOf(OrderDisVal) > 0 || ProductExtraOrder > 0)// || Integer.valueOf(ProductStock)>0
                {

                    int flgRuleTaxVal = 1;

                    mDataSource.fnsaveStoreTempOrderEntryDetailsMainSyncTable(TmpInvoiceCodePDA, storeID, "" + PCateId, ProductID, Double.parseDouble(PRate), TaxRate, flgRuleTaxVal, Integer.parseInt(OrderQTY), Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)),  Double.parseDouble(hmapLineValBfrTxAftrDscnt.get(ProductID)), Double.parseDouble(hmapLineValAftrTxAftrDscnt.get(ProductID)), Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]), OrderDisValOverAll, Integer.parseInt(SampleQTY), PName, TaxValue, TmpInvoiceCodePDA, flgIsQuoteRateApplied, PriceApplyDiscountLevelType, distID, Outstat, ProductExtraOrder, OrderValue, 0, Integer.parseInt(OrderQTYToShow), UOMSeleted, flgTeleCallerProduct, 0, OriginalTeleCallerProductQty, TeleCallingID, CartonID, flgInCarton, OrderDisPerUOM, OrderDisValOverAll);
                    mDataSource.fnsaveStoreTempOrderEntryDetails(TmpInvoiceCodePDA, storeID, "" + PCateId, ProductID, Double.parseDouble(PRate), TaxRate, flgRuleTaxVal, Integer.parseInt(OrderQTY), Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)), Double.parseDouble(hmapLineValBfrTxAftrDscnt.get(ProductID)), Double.parseDouble(hmapLineValAftrTxAftrDscnt.get(ProductID)), Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]), OrderDisValOverAll, Integer.parseInt(SampleQTY), PName, TaxValue, TmpInvoiceCodePDA, flgIsQuoteRateApplied, PriceApplyDiscountLevelType, distID, Outstat, ProductExtraOrder, OrderValue, 0, Integer.parseInt(OrderQTYToShow), UOMSeleted, flgTeleCallerProduct, 0, OriginalTeleCallerProductQty, TeleCallingID, CartonID, flgInCarton, OrderDisPerUOM, OrderDisValOverAll);
                    //mDataSource.close();
                }


            }
        }


    }

    public void InvoiceTableDataDeleteAndSaving(int Outstat, int flgTransferStatus) {

        mDataSource.deleteOldStoreInvoice(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);

        Double TBtaxDis;
        Double TAmt;
        Double Dis;
        Double INval;

        Double AddDis;
        Double InvAfterDis;

        Double INvalCreditAmt;
        Double INvalInvoiceAfterCreditAmt;

        Double INvalInvoiceOrginal = 0.00;


        int Ftotal;

        String mrouteID = mDataSource.fnGetRouteIDAgainstStoreID(storeID);
        String mrouteType = mDataSource.FetchRouteType(mrouteID);


        if (!tv_NetInvValue.getText().toString().isEmpty()) {
            TBtaxDis = Double.parseDouble(tv_NetInvValue.getText().toString().trim());
        } else {
            TBtaxDis = 0.00;
        }
        if (!tvTAmt.getText().toString().isEmpty()) {
            TAmt = Double.parseDouble(tvTAmt.getText().toString().trim());
        } else {
            TAmt = 0.00;
        }
        if (!tvDis.getText().toString().isEmpty()) {
            Dis = Double.parseDouble(tvDis.getText().toString().trim());
        } else {
            Dis = 0.00;
        }
        if (!tv_GrossInvVal.getText().toString().isEmpty()) {


            INval = Double.parseDouble(tv_GrossInvVal.getText().toString().trim());
        } else {
            INval = 0.00;
        }
        if (!tvFtotal.getText().toString().isEmpty()) {
            Double FtotalValue = Double.parseDouble(tvFtotal.getText().toString().trim());
            Ftotal = FtotalValue.intValue();
        } else {
            Ftotal = 0;
        }

        if (!tv_NetInvAfterDiscount.getText().toString().isEmpty()) {
            InvAfterDis = Double.parseDouble(tv_NetInvAfterDiscount.getText().toString().trim());
        } else {
            InvAfterDis = 0.00;
        }
        if (!tvAddDisc.getText().toString().isEmpty()) {
            AddDis = Double.parseDouble(tvAddDisc.getText().toString().trim());
        } else {
            AddDis = 0.00;
        }


        Double AmtPrevDueVA = 0.00;
        Double AmtCollVA = 0.00;
        Double AmtOutstandingVAL = 0.00;
        if (!tvAmtPrevDueVAL.getText().toString().isEmpty()) {
            AmtPrevDueVA = Double.parseDouble(tvAmtPrevDueVAL.getText().toString().trim());
        } else {
            AmtPrevDueVA = 0.00;
        }
        if (!etAmtCollVAL.getText().toString().isEmpty()) {
            AmtCollVA = Double.parseDouble(etAmtCollVAL.getText().toString().trim());
        } else {
            AmtCollVA = 0.00;
        }

        if (!tvAmtOutstandingVAL.getText().toString().isEmpty()) {
            AmtOutstandingVAL = Double.parseDouble(tvAmtOutstandingVAL.getText().toString().trim());
        } else {
            AmtOutstandingVAL = 0.00;
        }

        int NoOfCouponValue = 0;
		/*if(!txttvNoOfCouponValue.getText().toString().isEmpty()){
			NoOfCouponValue = Integer.parseInt(txttvNoOfCouponValue.getText().toString().trim());
		}
		else{
			NoOfCouponValue = 0;
		}
		*/
        Double TotalCoupunAmount = 0.00;
        if (!txttvCouponAmountValue.getText().toString().isEmpty()) {
            TotalCoupunAmount = Double.parseDouble(txttvCouponAmountValue.getText().toString().trim());
        } else {
            TotalCoupunAmount = 0.00;
        }

        int flgRuleTaxVal = 1;
        int flgTransType = 1;
        //mDataSource.open();
        //mDataSource.saveStoreInvoice(imei,storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis, AmtPrevDueVA, AmtCollVA, AmtOutstandingVAL, NoOfCouponValue, TotalCoupunAmount,Outstat,strGlobalOrderID,TmpInvoiceCodePDA,strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);
        String TeleCallingID = "0";
        if (hmapProductTeleCallerID != null && hmapProductTeleCallerID.containsKey(storeID)) {
            TeleCallingID = hmapProductTeleCallerID.get(storeID);
        }

         /*   TbltmpInvoiceHeaderCartonPlusOrder tbltmpInvoiceHeaderCartonPlusOrder = mDataSource.fnGetTbltmpInvoiceHeaderCartonPlusOrder(storeID, StoreVisitCode);

        mDataSource.saveStoreTempInvoice(StoreVisitCode, TmpInvoiceCodePDA, storeID, pickerDate, tbltmpInvoiceHeaderCartonPlusOrder.getTotalBeforeTaxDis(), tbltmpInvoiceHeaderCartonPlusOrder.getTaxAmt(), tbltmpInvoiceHeaderCartonPlusOrder.getTotalDis(), tbltmpInvoiceHeaderCartonPlusOrder.getInvoiceVal(), tbltmpInvoiceHeaderCartonPlusOrder.getFreeTotal(), tbltmpInvoiceHeaderCartonPlusOrder.getInvAfterDis(), AddDis, NoOfCouponValue, TotalCoupunAmount, pickerDate, flgTransType, PriceApplyDiscountLevelType, flgRuleTaxVal, Outstat, flgTransferStatus, mrouteID, mrouteType, TeleCallingID);//strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);
*/
        mDataSource.saveStoreTempInvoice(StoreVisitCode, TmpInvoiceCodePDA, storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis, NoOfCouponValue, TotalCoupunAmount, pickerDate, flgTransType, PriceApplyDiscountLevelType, flgRuleTaxVal, Outstat, flgTransferStatus, mrouteID, mrouteType, TeleCallingID);//strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);


        if(et_Disc!=null && !TextUtils.isEmpty(et_Disc.getText().toString().trim()) && Double.parseDouble(et_Disc.getText().toString().trim())>0.0)
        mDataSource.fnSavetblStoreDSROverAllDiscountApplied(storeID,Double.parseDouble(et_Disc.getText().toString().trim()));
        else
        {
            mDataSource.Delete_tblStoreDSROverAllDiscountApplied(storeID);
        }
        //mDataSource.close();


    }

    Double GrossInvValue = 0.0;

    public void orderBookingTotalCalc() {
        String disc = et_Disc.getText().toString().trim();
//            String cartonQtyFilled = txt_CartonTotal_filled.getText().toString().trim();

      /*  if (!AppUtils.isNullOrEmpty(disc, "0")) {
            double dis = Double.parseDouble(disc);

            double totordval=0.0;


            int qty = 0;
            for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : tblStoreProductMappingList) {
                if (tblStoreProductMappingForDisplay.getFlgTeleCallerProduct() == 0) {
                    if (prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll() != null && prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().size() > 0) {
                        if (prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID())) {
                            String prdctQty = "" + prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().get("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                            if ((!TextUtils.isEmpty(prdctQty)) && (Integer.parseInt(prdctQty) > 0)) {

                                qty = qty + Integer.parseInt(prdctQty);
                                List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysSingleItem = stream(tblStoreProductMappingList).where(p -> p.getPrdNodeID() ==tblStoreProductMappingForDisplay.getPrdNodeID()).toList();
                                //  List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
                                List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                                int conversionUnitForOrderCalculation = 1;
                                double StandardRate = UOMMasterDAtaForCalculation.get(0).getStandardRate();
                                double  StandardRateBeforeTax =StandardRate;// StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));

                                double DiscountAmount = 0.00;


                                double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax) - DiscountAmount;// StandardRateBeforeTax;
                                double ActualTax = ActualRateAfterDiscountBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100);
                                double ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));
                                Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQty);
                                totordval=totordval+OrderValPrdQtyBasis;




                            }
                        }
                    }
                }
            }

            if (dis <= totordval) {

                //  double discToBeApplied = (double) dis / qty;
                for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : tblStoreProductMappingList) {
                    if (tblStoreProductMappingForDisplay.getFlgTeleCallerProduct() == 0) {
                        if (prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll() != null && prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().size() > 0) {
                            if (prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID())) {
                                String prdctQty = "" + prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().get("" + tblStoreProductMappingForDisplay.getPrdNodeID());

                                List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysSingleItem = stream(tblStoreProductMappingList).where(p -> p.getPrdNodeID() ==tblStoreProductMappingForDisplay.getPrdNodeID()).toList();
                                List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
                                List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                                int conversionUnitForOrderCalculation = 1;
                                double StandardRate = UOMMasterDAtaForCalculation.get(0).getStandardRate();
                                double  StandardRateBeforeTax =StandardRate;// StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));

                                double DiscountAmount = 0.00;


                                double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax) - DiscountAmount;// StandardRateBeforeTax;
                                double ActualTax = ActualRateAfterDiscountBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100);
                                double ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));
                                Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQty);


                                double discToBeApplied=(dis*OrderValPrdQtyBasis)/totordval;
                                discToBeApplied=discToBeApplied/Double.parseDouble(prdctQty);
                                if (UOMMasterDAta.get(0).getBUOMID() != 3) {
                                    conversionUnitForOrderCalculation = (int) (UOMMasterDAta.get(0).getRelConversionUnit());

                                }
                                // discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                //double discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                prdctModelArrayList.getHmapPrdctDiscountPerUOM().put("" + tblStoreProductMappingForDisplay.getPrdNodeID(), "" + ( discToBeApplied));
                            }
                        }

                    }
                }
              //  orderAdapter.updateListDiscountModel(prdctModelArrayList);

            } else {
               // showAlertSingleButtonInfo("Discount cannot be greater than Invoice Value.");
            }
        }
        else
        {

            for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : tblStoreProductMappingList) {
                if (tblStoreProductMappingForDisplay.getFlgTeleCallerProduct() == 0) {
                    if(prdctModelArrayList.getHmapPrdctDiscountPerUOM()!=null && prdctModelArrayList.getHmapPrdctDiscountPerUOM().size()>0)
                    {
                        if( prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID()))
                        {
                            prdctModelArrayList.getHmapPrdctDiscountPerUOM().remove("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                        }
                    }
                }
            }
         //   orderAdapter.updateListDiscountModel(prdctModelArrayList);
        }*/








        Double StandardRate = 0.00;
        Double StandardRateBeforeTax = 0.00;

        Double ActualRateAfterDiscountBeforeTax = 0.00;
        Double DiscountAmount = 0.00;
        Double ActualTax = 0.00;
        Double ActualRateAfterDiscountAfterTax = 0.00;


        Double TotalFreeQTY = 0.00;
        Double TotalProductLevelDiscount = 0.00;
        Double TotalOrderValBeforeTax = 0.00;

        Double TotTaxAmount = 0.00;
        Double TotOderValueAfterTax = 0.00;

        Double TotTaxAmountWithTCOrder = 0.00;
        Double TotalOrderValBeforeTaxWithTCOrder = 0.00;

        LinkedHashMap<String, String> hmapPrdctOrderQty = prdctModelArrayList.getHmapPrdctOrderQty();
        HashMap<String, String> hmapPrdctOrderQtyDiscountPercentage = prdctModelArrayList.getHmapProductDiscountPercentageGive();
        HashMap<String, String> hmapPrdctOrderQtyDiscountFlat = prdctModelArrayList.getHmapProductFlatDiscountGive();
        HashMap<String, String> hmapPrdctFreeProdductGot = prdctModelArrayList.getHmapPrdctFreeQty();
        LinkedHashMap<String, String> hmapPrdctDiscountPerUOM = prdctModelArrayList.getHmapPrdctDiscountPerUOM();

        TotalAllLevelDiscount=0.0;
        if (hmapPrdctOrderQty != null) {

            for (Map.Entry<String, String> entry : hmapPrdctOrderQty.entrySet()) {


                String ProductID = entry.getKey();
                String prdctQty = "" + ((int) (Double.parseDouble(entry.getValue())));
                List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysSingleItem = stream(tblStoreProductMappingList).where(p -> p.getPrdNodeID() == Integer.parseInt(ProductID)).toList();


                if (prdctModelArrayList.getHmapflgPicsOrCases()!=null && prdctModelArrayList.getHmapflgPicsOrCases().containsKey(ProductID) && prdctModelArrayList.getHmapflgPicsOrCases().get(ProductID).equals("1")) {

                    int conversionUnit = (int) (hmapProductTblUOMMapping.get(Integer.parseInt(ProductID)).get(0).getRelConversionUnits());

                    int OqtyTcOrder = 0;
                    if (hmapflgTeleCallerProductQty != null && hmapflgTeleCallerProductQty.containsKey(ProductID)) {
                        OqtyTcOrder = Integer.parseInt(hmapflgTeleCallerProductQty.get(ProductID));
                    }
                    OqtyTcOrder = OqtyTcOrder / conversionUnit;
                    prdctModelArrayList.setPrdctQtyWithTCOrder("" + ProductID, "" + OqtyTcOrder);
                } else {
                    if (hmapflgTeleCallerProductQty != null && hmapflgTeleCallerProductQty.containsKey(ProductID))
                        prdctModelArrayList.setPrdctQtyWithTCOrder("" + ProductID, "" + hmapflgTeleCallerProductQty.get(ProductID));
                    else {
                        prdctModelArrayList.setPrdctQtyWithTCOrder("" + ProductID, "0");
                    }
                }
                String prdctQtyWithTCOrder = "" + (int) (Double.parseDouble(entry.getValue()));
                if (prdctModelArrayList.getHmapPrdctOrderQtyWithTCOrder().containsKey(ProductID)) {
                    prdctQtyWithTCOrder = "" + (Integer.parseInt(prdctQtyWithTCOrder) + Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQtyWithTCOrder().get(ProductID)));
                    prdctModelArrayList.setPrdctQtyWithTCOrder("" + ProductID, prdctQtyWithTCOrder);
                }


                if ((!TextUtils.isEmpty(prdctQty)) && (Integer.parseInt(prdctQty) > 0) || Integer.parseInt(prdctQtyWithTCOrder) > 0) {
                    System.out.println("Order book Prd:-" + ProductID);

                    if (Integer.parseInt(prdctQtyWithTCOrder) > 0 && (TextUtils.isEmpty(prdctQty)) || (Integer.parseInt(prdctQty) == 0)) {
                        prdctQty = "0";
                    }
                    List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
                    List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                    int conversionUnitForOrderCalculation = 1;
                    StandardRate = UOMMasterDAtaForCalculation.get(0).getStandardRate();
                    if (UOMMasterDAta.get(0).getBUOMID() != 3) {
                        conversionUnitForOrderCalculation = (int) (UOMMasterDAta.get(0).getRelConversionUnit());
                        //prdctQty=""+(Integer.parseInt(prdctQty)/conversionUnitForOrderCalculation);


                    }

                  ///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));

                   /* if (prdctModelArrayList.getHmapflgPicsOrCases().containsKey(ProductID)) {
                        if (prdctModelArrayList.getHmapflgPicsOrCases().get(ProductID).equals("1")) {

                            int conversionUnit = (int) (hmapProductTblUOMMapping.get(Integer.parseInt(ProductID)).get(0).getRelConversionUnits());
                            StandardRate = StandardRate * conversionUnit;
                            StandardRate = Double.parseDouble(new DecimalFormat("##.##").format(StandardRate));

                           *//* EditText etrate=ll_rcylContainer.findViewWithTag(ProductID + "_etPrdRate");
                            etrate.setText(""+PrdRateCases);*//*

                            // prdctModelArrayList.getHmapOrderQtyDataToShow().put(prductId,prdctModelArrayList.getPrdctOrderQty(prductId));
                        }
                    }*/


                    StandardRateBeforeTax =StandardRate;// StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));

                    //If No Percentage Discount or Flat Discount is Applicable Code Starts Here

                    DiscountAmount = 0.00;


                    ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax) - DiscountAmount;// StandardRateBeforeTax;


                    ActualTax = ActualRateAfterDiscountBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
                    ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));



                    TotTaxAmount = TotTaxAmount + (ActualTax * Double.parseDouble(prdctQty));

                    TotTaxAmountWithTCOrder = TotTaxAmountWithTCOrder + (ActualTax * Double.parseDouble(prdctQtyWithTCOrder));

                    Double TaxValue = ActualTax * Double.parseDouble(prdctQty);
                    TaxValue = Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
                    hmapProductTaxValue.put(ProductID, "" + TaxValue);

                    Double TaxValueWithTCOrder = ActualTax * Double.parseDouble(prdctQtyWithTCOrder);
                    TaxValueWithTCOrder = Double.parseDouble(new DecimalFormat("##.##").format(TaxValueWithTCOrder));
                    hmapProductTaxValueWithTCOrder.put(ProductID, "" + TaxValueWithTCOrder);


                    if (hmapPrdctDiscountPerUOM != null && hmapPrdctDiscountPerUOM.size() > 0 && hmapPrdctDiscountPerUOM.containsKey(ProductID) && !TextUtils.isEmpty(hmapPrdctDiscountPerUOM.get(ProductID))) {
                        DiscountAmount = Double.parseDouble(hmapPrdctDiscountPerUOM.get(ProductID));
                    }
                    Double DiscAmtOnPreQtyBasic = DiscountAmount * Double.parseDouble(prdctQty);

                    Double DiscAmtOnPreQtyBasicToDisplay = DiscAmtOnPreQtyBasic;
                    //   DiscAmtOnPreQtyBasicToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));

                    TotalAllLevelDiscount=TotalAllLevelDiscount+DiscAmtOnPreQtyBasicToDisplay;
                    TotalProductLevelDiscount = TotalProductLevelDiscount + DiscAmtOnPreQtyBasic;
                    Double OrderValPrdQtyBasis =(( ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQty))-(DiscAmtOnPreQtyBasic));
                    hmapLineValAftrTxAftrDscnt.put(ProductID, "" + OrderValPrdQtyBasis);

                    Double OrderValPrdQtyBasisWithTCOrder = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQtyWithTCOrder);
                    hmapLineValAftrTxAftrDscntWithTCOrder.put(ProductID, "" + OrderValPrdQtyBasisWithTCOrder);

                    TotalOrderValBeforeTax = TotalOrderValBeforeTax + (ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQty));
                    hmapLineValBfrTxAftrDscnt.put(ProductID, "" + (( ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQty))-(DiscAmtOnPreQtyBasic)));


                    TotalOrderValBeforeTaxWithTCOrder = TotalOrderValBeforeTaxWithTCOrder + (ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQtyWithTCOrder));
                    hmapLineValBfrTxAftrDscntWithTCOrder.put(ProductID, "" + ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQtyWithTCOrder));

                    TotOderValueAfterTax = TotOderValueAfterTax + OrderValPrdQtyBasis;
                    double prdLineVal = OrderValPrdQtyBasis;// (ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQty));
                    prdLineVal = Double.parseDouble(new DecimalFormat("##.##").format(prdLineVal));
                    prdctModelArrayList.setPrdctVal(ProductID, "" + prdLineVal);


                    double prdLineValWithTCOrder = OrderValPrdQtyBasisWithTCOrder;// (ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQty));
                    prdLineValWithTCOrder = Double.parseDouble(new DecimalFormat("##.##").format(prdLineValWithTCOrder));
                    prdctModelArrayList.setPrdctValWithTCOder(ProductID, "" + prdLineValWithTCOrder);

                    //If No Percentage Discount or Flat Discount is Applicable Code Ends Here


                }

            }
        }

        //Now the its Time to Show the OverAll Summary Code Starts Here

        tvFtotal.setText(("" + TotalFreeQTY).trim());

        TotalProductLevelDiscount = Double.parseDouble(new DecimalFormat("##.##").format(TotalProductLevelDiscount));
        tvDis.setText(("" + TotalProductLevelDiscount).trim());

        TotalOrderValBeforeTax = Double.parseDouble(new DecimalFormat("##.##").format(TotalOrderValBeforeTax));
        tv_NetInvValue.setText(("" + TotalOrderValBeforeTax).trim());

        String percentBenifitMax = mDataSource.fnctnGetMaxAssignedBen8DscntApld1(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);
        Double percentMax = 0.00;
        Double percentMaxGross = 0.0;
        Double amountMaxGross = 0.0;

        String amountBenfitMaxGross = mDataSource.fnctnGetMaxAssignedBen9DscntApld2(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);
        String percentBenifitMaxGross = mDataSource.fnctnGetMaxAssignedBen8DscntApld2(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);

        if (percentBenifitMaxGross.equals("")) {
            percentMaxGross = 0.0;
        } else {
            percentMaxGross = Double.parseDouble(percentBenifitMaxGross.split(Pattern.quote("^"))[0]);
        }
        if (percentBenifitMax.equals("")) {
            percentMax = 0.00;
        } else {
            percentMax = Double.parseDouble(percentBenifitMax.split(Pattern.quote("^"))[0]);
        }

        String amountBenifitMax = mDataSource.fnctnGetMaxAssignedBen9DscntApld1(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);
        Double amountMax = 0.00;
        if (percentBenifitMax.equals("")) {
            amountMax = 0.0;
        } else {
            amountMax = Double.parseDouble(amountBenifitMax.split(Pattern.quote("^"))[0]);
        }


        tvAddDisc.setText("" + "0.00");

        tv_NetInvAfterDiscount.setText("" + TotOderValueAfterTax);

        TotTaxAmount = Double.parseDouble(new DecimalFormat("##.##").format(TotTaxAmount));
        tvTAmt.setText("" + TotTaxAmount);

        Double totalGrossVALMaxPercentage = TotalOrderValBeforeTax - TotalOrderValBeforeTax * (percentMaxGross / 100);
        Double totalGrossrVALMaxAmount = TotalOrderValBeforeTax - amountMaxGross;
        Double totalGrossVALAfterDiscount = 0.0;
        if (totalGrossVALMaxPercentage != totalGrossrVALMaxAmount) {
            totalGrossVALAfterDiscount = Math.min(totalGrossrVALMaxAmount, totalGrossVALMaxPercentage);
        } else {
            totalGrossVALAfterDiscount = totalGrossrVALMaxAmount;
        }

        if (totalGrossVALAfterDiscount == totalGrossrVALMaxAmount && totalGrossrVALMaxAmount != 0.0) {
            mDataSource.updatewhatAppliedFlag(1, storeID, Integer.parseInt(amountBenfitMaxGross.split(Pattern.quote("^"))[1]), TmpInvoiceCodePDA, TmpInvoiceCodePDA, flgInCarton);
        } else if (totalGrossVALAfterDiscount == totalGrossVALMaxPercentage && percentMaxGross != 0.0) {
            mDataSource.updatewhatAppliedFlag(1, storeID, Integer.parseInt(percentBenifitMaxGross.split(Pattern.quote("^"))[1]), TmpInvoiceCodePDA, TmpInvoiceCodePDA, flgInCarton);
        }
//        GrossInvValue = 0.0;


        Double CollectionAmt = mDataSource.fnTotCollectionAmtAgainstStore(storeID, TmpInvoiceCodePDA, StoreVisitCode);
        CollectionAmt = Double.parseDouble(new DecimalFormat("##.##").format(CollectionAmt));

        if (GrossInvValue > 0.0) {
            VisitTypeStatus = "2";
        }
        if (CollectionAmt > 0.0) {
            VisitTypeStatus = "3";
        }
        if (CollectionAmt > 0.0 && GrossInvValue > 0.0) {
            VisitTypeStatus = "4";
        }

        mDataSource.updateVisitTypeStatusOfStore(storeID, VisitTypeStatus, StoreVisitCode);
        double cartondiscount=0.0;
        double cartonInvoiceVal=0.0;
        double cartonTaxVal=0.0;
        if(tvProductLevelDiscount!=null)
        {


            if(tblProductCategoryUOMTypeLists!=null && tblProductCategoryUOMTypeLists.size()>0)
            {
                for(TblProductCategoryUOMTypeList tblProductCategoryUOMTypeList:tblProductCategoryUOMTypeLists) {
                    cartondiscount = cartondiscount + Double.parseDouble(tblProductCategoryUOMTypeList.getTotalDiscountVal());
                    cartonInvoiceVal = cartonInvoiceVal + tblProductCategoryUOMTypeList.getNetLineVal();
                    //cartonTaxVal=cartonTaxVal++ tblProductCategoryUOMTypeList.get();
                }
            }

        }
        TotalAllLevelDiscount=TotalAllLevelDiscount+cartondiscount;
        TotalAllLevelDiscount=Double.parseDouble(new DecimalFormat("##.##").format(TotalAllLevelDiscount));
        //tvProductLevelDiscount.setText(""+String.format("%.2f", TotalAllLevelDiscount));
        tvProductLevelDiscount.setText(""+TotalAllLevelDiscount);
        GrossInvValue = TotOderValueAfterTax + TotTaxAmount+cartonInvoiceVal;
        GrossInvValue = Double.parseDouble(new DecimalFormat("##.##").format(GrossInvValue));
        tv_GrossInvVal.setText("" + String.format("%.2f", GrossInvValue));
        tvAfterTaxValue.setText("" + String.format("%.2f", GrossInvValue));
      /*  try {
           orderAdapter.updateList(tblStoreProductMappingListForDisplay);
           // orderAdapter.notifyAll();//.notifyDataSetChanged();
           // fnCallAction();
//            productsAdapter.notifyItemChanged(prdctModelArrayList.getHolderPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            //  productsAdapter.notifyDataSetChanged();
         //   orderAdapter.updateListDiscountModel(prdctModelArrayList);



          /*  {

                String disc = et_Disc.getText().toString().trim();
//            String cartonQtyFilled = txt_CartonTotal_filled.getText().toString().trim();

                if (!AppUtils.isNullOrEmpty(disc, "0")) {
                    double dis = Double.parseDouble(disc);
                    if (dis <= GrossInvValue) {
                        LinkedHashMap<String, String> hmapPrdctOrderQtyForDiscountCalc = prdctModelArrayList.getHmapPrdctOrderQty();
                        int qty = 0;
                        for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : tblStoreProductMappingList) {
                            if (tblStoreProductMappingForDisplay.getFlgTeleCallerProduct() == 0) {
                                if (hmapPrdctOrderQtyForDiscountCalc != null && hmapPrdctOrderQtyForDiscountCalc.size() > 0) {
                                    if (hmapPrdctOrderQtyForDiscountCalc.containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID())) {
                                        String prdctQty = "" + hmapPrdctOrderQtyForDiscountCalc.get("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                                        if ((!TextUtils.isEmpty(prdctQty)) && (Integer.parseInt(prdctQty) > 0)) {
                                            qty = qty + Integer.parseInt(prdctQty);
                                        }
                                    }
                                }
                            }
                        }
                        double discToBeApplied = (double) dis / qty;
                        for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : tblStoreProductMappingList) {
                            if (tblStoreProductMappingForDisplay.getFlgTeleCallerProduct() == 0) {
                                if (hmapPrdctOrderQtyForDiscountCalc != null && hmapPrdctOrderQtyForDiscountCalc.size() > 0) {
                                    if (hmapPrdctOrderQtyForDiscountCalc.containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID())) {
                                        String prdctQty = "" + hmapPrdctOrderQtyForDiscountCalc.get("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                                        discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                        prdctModelArrayList.getHmapPrdctDiscountPerUOM().put("" + tblStoreProductMappingForDisplay.getPrdNodeID(), "" + ( discToBeApplied));
                                    }
                                }

                            }
                        }
                        orderAdapter.updateListDiscountModel(prdctModelArrayList);

                    } else {

                       // showAlertSingleButtonInfo("Discount cannot be greater than Invoice Value.");
                    }
                }
                else
                {

                    for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : tblStoreProductMappingList) {
                        if (tblStoreProductMappingForDisplay.getFlgTeleCallerProduct() == 0) {
                            if(prdctModelArrayList.getHmapPrdctDiscountPerUOM()!=null && prdctModelArrayList.getHmapPrdctDiscountPerUOM().size()>0)
                            {
                                if( prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID()))
                                {
                                    prdctModelArrayList.getHmapPrdctDiscountPerUOM().remove("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                                }
                            }
                        }
                    }
                    orderAdapter.updateListDiscountModel(prdctModelArrayList);
                }
            }*/

            orderAdapter.updateList(tblStoreProductMappingListForDisplay);

//            productsAdapter.notifyItemChanged(prdctModelArrayList.getHolderPosition());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setInvoiceTableView() {

        LayoutInflater inflater = getLayoutInflater();
        final View row123 = (View) inflater.inflate(R.layout.activity_detail_productfilter, ll_scheme_detail, false);


        tvCredAmtVAL = (TextView) row123.findViewById(R.id.textView1_CredAmtVAL);
        tvINafterCredVAL = (TextView) row123.findViewById(R.id.textView1_INafterCredVAL);
        textView1_CredAmtVAL_new = (TextView) row123.findViewById(R.id.textView1_CredAmtVAL_new);


        tv_NetInvValue = (TextView) row123.findViewById(R.id.tv_NetInvValue);
        tvTAmt = (TextView) row123.findViewById(R.id.textView1_v2);
        tvDis = (TextView) row123.findViewById(R.id.textView1_v3);
        tv_GrossInvVal = (TextView) row123.findViewById(R.id.tv_GrossInvVal);
        tvFtotal = (TextView) row123.findViewById(R.id.textView1_v5);
        tvAddDisc = (TextView) row123.findViewById(R.id.textView1_AdditionalDiscountVAL);
        tv_NetInvAfterDiscount = (TextView) row123.findViewById(R.id.tv_NetInvAfterDiscount);

        tvAmtPrevDueVAL = (TextView) row123.findViewById(R.id.tvAmtPrevDueVAL);
        tvAmtOutstandingVAL = (TextView) row123.findViewById(R.id.tvAmtOutstandingVAL);
        etAmtCollVAL = (EditText) row123.findViewById(R.id.etAmtCollVAL);

        tvNoOfCouponValue = (EditText) row123.findViewById(R.id.tvNoOfCouponValue);
        txttvCouponAmountValue = (EditText) row123.findViewById(R.id.tvCouponAmountValue);

        tvPreAmtOutstandingVALNew = (TextView) row123.findViewById(R.id.tvPreAmtOutstandingVALNew);
        tvAfterTaxValue = (TextView) row123.findViewById(R.id.tvAfterTaxValue);
        ll_scheme_detail.addView(row123);
        Double outstandingvalue = mDataSource.fnGetStoretblLastOutstanding(storeID);
        tvPreAmtOutstandingVALNew.setText("" + String.format("%.2f", outstandingvalue));

        if (CommonInfo.hmapAppMasterFlags!=null && CommonInfo.hmapAppMasterFlags.size()>0 &&  CommonInfo.hmapAppMasterFlags.containsKey("flgVisitStartOutstandingDetails")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgVisitStartOutstandingDetails") == 0) {
                TableRow table_trPreviousOutStanding = (TableRow) findViewById(R.id.table_trPreviousOutStanding);
                table_trPreviousOutStanding.setVisibility(View.GONE);
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

    private void getSchemeSlabDetails() {

        arrayListSchemeSlabDteail = mDataSource.fnctnSchemeSlabIdSchmVal(storeID);

        hmapSchemeIdStoreID = mDataSource.fnctnSchemeStoreID(storeID);
        hmapSlabIDArrRowID = mDataSource.fngetSlabIDArrRowID(storeID);
        hmapRowIDProductIds = mDataSource.fngetRowIDProductIds(storeID);
        hmapSchemeSlabIDBucketTypeBucketValueForSchemeType1And3 = mDataSource.fngetSchemeSlabIDBucketTypeBucketValueForSchemeType1And3(storeID);
        // hmapSchemeStoreID=mDataSource.fnctnSchemeStoreID(storeID);
        if (arrayListSchemeSlabDteail != null && arrayListSchemeSlabDteail.size() > 0) {
            hmapSchmeSlabIdSchemeId = arrayListSchemeSlabDteail.get(0);
            hmapSchmeSlabIdSlabDes = arrayListSchemeSlabDteail.get(1);
            hmapSchmeSlabIdBenifitDes = arrayListSchemeSlabDteail.get(2);
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

    private void allMessageAlertPriceMissing(String message) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ProductEntryForm.this);
        alertDialogNoConn.setTitle(ProductEntryForm.this.getResources().getString(R.string.genTermInformation));
        alertDialogNoConn.setMessage(message);
        //alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
        alertDialogNoConn.setNeutralButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogOkButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }


    public void fnCalculateSchemeAndMoveForworad(EditText editText) {
        if (editText != null && editText.getId() == R.id.et_OrderQty) {


            if (!TextUtils.isEmpty(ed_LastEditextFocusd.getText().toString())) {
                if (hmapProductMinMax != null && hmapProductMinMax.size()>0 && hmapProductMinMax.containsKey(ProductIdOnClickedEdit)) {
                    String fnlSchmIdSlabId = "0";
                    ArrayList<String> listValData = hmapProductMinMax.get(ProductIdOnClickedEdit);
                    for (String prdctData : listValData) {
                        String minMaxVal = prdctData.split(Pattern.quote("^"))[0];
                        String otherVal = prdctData.split(Pattern.quote("^"))[1];
                        String slabBcktTyp = otherVal.split(Pattern.quote("#"))[0];
                        String schmIdSlabId = otherVal.split(Pattern.quote("#"))[1];
                        //1. Product Quantity
                        if (slabBcktTyp.equals("1")) {

                            int ordrQty =Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(ProductIdOnClickedEdit));// Integer.parseInt(ed_LastEditextFocusd.getText().toString());
                            int minVal = Integer.parseInt(minMaxVal.split(Pattern.quote("~"))[0]);
                            int maxVal = Integer.parseInt(minMaxVal.split(Pattern.quote("~"))[1]);
                            if ((minVal <= ordrQty) && (ordrQty <= maxVal)) {
                                fnlSchmIdSlabId = schmIdSlabId;
                                break;
                            }

                        }
                        //4. Product Value
                        else if (slabBcktTyp.equals("4")) {
                            Double minVal = Double.parseDouble(minMaxVal.split(Pattern.quote("~"))[0]);
                            Double maxVal = Double.parseDouble(minMaxVal.split(Pattern.quote("~"))[1]);
                            int ordrQty = Integer.parseInt(ed_LastEditextFocusd.getText().toString());
                            Double prodVolume = Double.parseDouble(hmapPrdctVolRatTax.get(ProductIdOnClickedEdit).split(Pattern.quote("^"))[0]);
                            Double oderVolumeOfCurrentMapedProduct = prodVolume * ordrQty;
                            if ((minVal <= oderVolumeOfCurrentMapedProduct) && (oderVolumeOfCurrentMapedProduct <= maxVal)) {
                                fnlSchmIdSlabId = schmIdSlabId;
                                break;
                            }
                        }
                        //5. Product Volume
                        else if (slabBcktTyp.equals("5")) {

                            Double minVal = Double.parseDouble(minMaxVal.split(Pattern.quote("~"))[0]);
                            Double maxVal = Double.parseDouble(minMaxVal.split(Pattern.quote("~"))[1]);
                            int ordrQty = Integer.parseInt(ed_LastEditextFocusd.getText().toString());

                            Double prodRate = Double.parseDouble(hmapPrdctVolRatTax.get(ProductIdOnClickedEdit).split(Pattern.quote("^"))[1]);
                            Double oderRateOfCurrentMapedProduct = prodRate * ordrQty;

                            if ((minVal <= oderRateOfCurrentMapedProduct) && (oderRateOfCurrentMapedProduct <= maxVal)) {
                                fnlSchmIdSlabId = schmIdSlabId;
                                break;
                            }
                        }

                    }

                    if (!fnlSchmIdSlabId.equals("0")) {
                        if ((hmapSchmDscrptnAndBenfit != null) && (hmapSchmDscrptnAndBenfit.containsKey(fnlSchmIdSlabId))) {
                            String benifitDescr = hmapSchmDscrptnAndBenfit.get(fnlSchmIdSlabId);
                            AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ProductEntryForm.this);
                            alertDialogNoConn.setTitle(ProductEntryForm.this.getResources().getString(R.string.genTermInformation));
                            alertDialogNoConn.setMessage(benifitDescr.split(Pattern.quote("^"))[0] + "\nAND\n" + benifitDescr.split(Pattern.quote("^"))[1]);
                            //alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
                            alertDialogNoConn.setNeutralButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogOkButton),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                        }
                                    });
                            alertDialogNoConn.setIcon(R.drawable.info_ico);
                            AlertDialog alert = alertDialogNoConn.create();
                            alert.show();
                        }
                    }
                }
            }

            if (prdctModelArrayList.getHmapPrdctOrderQty()!=null && prdctModelArrayList.getPrdctOrderQty(ProductIdOnClickedEdit) != null && prdctModelArrayList.getHmapPrdctOrderQty().containsKey(ProductIdOnClickedEdit) && prdctModelArrayList.getPrdctOrderQty(ProductIdOnClickedEdit)!=null && prdctModelArrayList.getPrdctOrderQty(ProductIdOnClickedEdit).equals("") && Integer.parseInt(prdctModelArrayList.getPrdctOrderQty(ProductIdOnClickedEdit)) > 0) {
                // if ((!hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("") && !hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("-99")) && !hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("-99.00") && !hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("-99.0")) {
                    getOrderData(ProductIdOnClickedEdit);

                //  }
                /*else {
                        EditText temprt = (EditText) ll_prdct_detal.findViewWithTag("tvRate_" + et_ValueOnFocuslostnew.getTag().toString().split(Pattern.quote("_"))[1]);
                        temprt.setSelected(true);
                        temprt.requestFocus();
                        temprt.setCursorVisible(true);
                    }*/
            } else {
                getOrderData(ProductIdOnClickedEdit);
                //   fnCallAction();
            }
            if (hmapProductRelatedSchemesList!=null && hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedEdit) || hmapProductAddOnSchemesList.containsKey(ProductIdOnClickedEdit)) {

                String SchIdsCompleteSchemeIdListOnProductID = "";
                if (hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedEdit)) {
                    SchIdsCompleteSchemeIdListOnProductID = hmapProductRelatedSchemesList.get(ProductIdOnClickedEdit);

                    if (SchIdsCompleteSchemeIdListOnProductID != null) {
                        String[] arrSchIdsListOnProductID = SchIdsCompleteSchemeIdListOnProductID.split("#");
                        for (int pSchIdsAppliCount = 0; pSchIdsAppliCount < arrSchIdsListOnProductID.length; pSchIdsAppliCount++) {
                            //35_1_0_2 where 35=shcemId, 1= SchAppRule, 2= schemeTypeId
                            String schOverviewDetails = arrSchIdsListOnProductID[pSchIdsAppliCount].split("!")[0];   //Example :-1075_1_0_1
                            String schOverviewOtherDetails = arrSchIdsListOnProductID[pSchIdsAppliCount].split("!")[1]; //Example :-1026$1^1|1^23^1^10^0@1025$1^1|1^22^1^20^0@1022$1^1|1^19^5^5^0@1020$1^1|1^17^3^4^0@1019$1^1|1^16^1^12^0@1018$1^1|1^15^1^10^0@1017$1^1|1^14^1^12^0
                            int schId = Integer.parseInt(schOverviewDetails.split("_")[0]);                           //Example :-1075
                            int schAppRule = Integer.parseInt(schOverviewDetails.split("_")[1]);                                                                                        //Example :-1
                            int schApplicationId = Integer.parseInt(schOverviewDetails.split("_")[2]);                                                              //Example :-0
                            int SchTypeId = Integer.parseInt(schOverviewDetails.split("_")[3]);                                                                                           //Example :-1 // 1=Check Combined Skus, 2=Bundle,3=Simple with Check on Individual SKU
                            String[] arrschSlbIDsOnSchIdBasis = schOverviewOtherDetails.split("@");   //Split for multiple slabs Example :-1026$1^1|1^23^1^10^0, 1025$1^1|1^22^1^20^0
                            boolean bucketCndtnSchemeFullFill = false;
                            int exitWhenSlabToExit = 0;



                            if (hmapSchemeIdStoreID.containsKey("" + schId)) {

                                int prdOqty = 0;//For Showing Gap
                                for (int pSchSlbCount = 0; pSchSlbCount < arrschSlbIDsOnSchIdBasis.length; pSchSlbCount++) {
                                    int schSlabId = Integer.parseInt((arrschSlbIDsOnSchIdBasis[pSchSlbCount]).split(Pattern.quote("$"))[0]); //Exmaple Slab ID:- 1026
                                    String schSlabOtherDetails = arrschSlbIDsOnSchIdBasis[pSchSlbCount].split(Pattern.quote("$"))[1]; //Exmaple Slab OtherDetails:- 1^1|1^23^1^10^0
                                    String[] arrSchSlabBuckWiseDetails = schSlabOtherDetails.split(Pattern.quote("~")); //Example Split For Multiple Buckets (OR Condition)
                                    for (int pSchSlbBuckCnt = 0; pSchSlbBuckCnt < arrSchSlabBuckWiseDetails.length; pSchSlbBuckCnt++) {
                                        String schSlbBuckDetails = arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[0]; // Eaxmple:-1^1
                                        String schSlbBuckOtherDetails = arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[1];  // Eaxmple:-1^23^1^10^0
                                        int schSlbBuckId = Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[0]);  //Exmaple Slab Bucket ID:- 1
                                        int schSlbBuckCnt = Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[1]);  //Example Number of Buckets under this Slab, Count:-1

                                        String[] arrSubBucketDetails = schSlbBuckOtherDetails.split(Pattern.quote("*"));  //Example Split For Multiple Sub Buckets(AND Condition)
                                        String[] arrMaintainDetailsOfBucketConditionsAgainstBuckId = new String[schSlbBuckCnt];  //Example Length of Buckes in Slab and which condition is true in case of OR
                                        int schSlbSubRowID = Integer.parseInt(schSlbBuckOtherDetails.split(Pattern.quote("^"))[1]);
                                        ArrayList<String> arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation = new ArrayList<String>();
                                        if (SchTypeId == 1) {
                                  /* if (schSlabSubBucketType == 1) {
                                        arrProductIDMappedInSchSlbSubBukRowId.add(ProductIdOnClicked);
                                    } else {*/
                                            arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation = mDataSource.fectProductIDMappedInSchSlbSubBukRowIdTemp(schSlbSubRowID);
                                            // }
                                        }
                                        if (SchTypeId == 2) {

                                            arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation = mDataSource.fectProductIDMappedInSchSlbSubBukRowIdTemp(schSlbSubRowID);


                                        }
                                        if (SchTypeId == 3) {
                                            arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation.add(ProductIdOnClickedEdit);
                                        }

                                        for (String pd : arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation) {
                                            if (prdctModelArrayList.getHmapPrdctOrderQty() != null && prdctModelArrayList.getHmapPrdctOrderQty().size() > 0 && prdctModelArrayList.getHmapPrdctOrderQty().containsKey(pd)) {
                                                prdOqty = prdOqty + Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(pd));
                                                int tcord = 0;
                                                if (hmapflgTeleCallerProductQty != null && hmapflgTeleCallerProductQty.size() > 0 && hmapflgTeleCallerProductQty.containsKey("" + pd)) {
                                                    tcord = Integer.parseInt(prdctModelArrayList.getPrdctOrderQtyWithTCOrder(pd));
                                                    prdOqty = prdOqty + tcord;
                                                }


                                            }
                                        }

                                       /* HashMap<String, ArrayList<TblSchemePerProduct>> rst=mDataSource.fnSchemeDescAndSlabDetails(ProductIdOnClickedEdit, "" + schId, 0, prdOqty);
                                        prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode().put(ProductIdOnClickedEdit,rst.get(ProductIdOnClickedEdit));
*/
                                        for(String pd:arrProductIDMappedInSchSlbSubBukRowIdSlabCalculation) {
                                            if (prdctModelArrayList.getHmapPrdctOrderQty() != null && prdctModelArrayList.getHmapPrdctOrderQty().size() > 0 && prdctModelArrayList.getHmapPrdctOrderQty().containsKey(pd)) {
                                                prdctModelArrayList.removhmapProductCurrentAppliedSchemeIDandDescrAndColorCode(pd);
                                                if(prdctModelArrayList.getHmapPrdctOrderQty().containsKey(ProductIdOnClickedEdit)  && Double.parseDouble(prdctModelArrayList.getHmapPrdctOrderQty().get(ProductIdOnClickedEdit))>0)
                                                prdctModelArrayList.setHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(mDataSource.fnSchemeDescAndSlabDetails(ProductIdOnClickedEdit, "" + schId, 0, prdOqty));
                                                else
                                                    prdctModelArrayList.setHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(mDataSource.fnSchemeDescAndSlabDetails(pd, "" + schId, 0, prdOqty));
                                            }
                                        }
                                        // break;
                                    }


                                      /*  prdctModelArrayList.removhmapProductCurrentAppliedSchemeIDandDescrAndColorCode(ProductIdOnClickedEdit);


                                        prdctModelArrayList.setHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(mDataSource.fnSchemeDescAndSlabDetails(ProductIdOnClickedEdit, "" + schId, 0, prdOqty));
*/

                                }
                            }

                        }
                    }







                }



            }


        }


      //  orderBookingTotalCalc();
    }


    private void getOrderData(String ProductIdOnClickedControl123) {

        isbtnExceptionVisible = 0;

        if (hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl123) || hmapProductAddOnSchemesList.containsKey(ProductIdOnClickedControl123)) {

            String SchIdsCompleteSchemeIdListOnProductID = "";
            if (hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl123)) {
                SchIdsCompleteSchemeIdListOnProductID = hmapProductRelatedSchemesList.get(ProductIdOnClickedControl123);
            }

            if (hmapProductAddOnSchemesList.containsKey(ProductIdOnClickedControl123)) {
                if (!TextUtils.isEmpty(SchIdsCompleteSchemeIdListOnProductID)) {
                    SchIdsCompleteSchemeIdListOnProductID = SchIdsCompleteSchemeIdListOnProductID + "#" + hmapProductAddOnSchemesList.get(ProductIdOnClickedControl123);
                } else {
                    SchIdsCompleteSchemeIdListOnProductID = hmapProductAddOnSchemesList.get(ProductIdOnClickedControl123);
                }

            }

            fnDeletePreviousEntriesSchemeIDsAppliedOverProductAfterValueChange(SchIdsCompleteSchemeIdListOnProductID, ProductIdOnClickedControl123);


        } else if (mDataSource.isFreeProductIdExist(Integer.parseInt(ProductIdOnClickedControl123))) {
            String productIdAgaingtFreeProductId = mDataSource.getFreeProductIdAgainstFreeProductId(Integer.parseInt(ProductIdOnClickedControl123));
            if (productIdAgaingtFreeProductId != null) {
                String SchIdsCompleteSchemeIdListOnProductID = hmapProductRelatedSchemesList.get(productIdAgaingtFreeProductId);
                if (hmapProductAddOnSchemesList.containsKey(productIdAgaingtFreeProductId)) {
                    if ((!SchIdsCompleteSchemeIdListOnProductID.equals("null")) && (!SchIdsCompleteSchemeIdListOnProductID.equals("")) && (SchIdsCompleteSchemeIdListOnProductID != null)) {
                        SchIdsCompleteSchemeIdListOnProductID = SchIdsCompleteSchemeIdListOnProductID + "#" + hmapProductAddOnSchemesList.get(productIdAgaingtFreeProductId);
                    } else {
                        SchIdsCompleteSchemeIdListOnProductID = hmapProductAddOnSchemesList.get(productIdAgaingtFreeProductId);
                    }
                }
                fnDeletePreviousEntriesSchemeIDsAppliedOverProductAfterValueChange(SchIdsCompleteSchemeIdListOnProductID, productIdAgaingtFreeProductId);

            }
        }

        fnCallAction();


    }


    public void fnDeletePreviousEntriesSchemeIDsAppliedOverProductAfterValueChange(String CompleteSchemeIdListOnProductID, String ProductIdOnClicked) {

        ArrayList<String> listProductChecked = new ArrayList<>();
        //53_1_0_1!95$1^1|1^25^5^75^0@94$1^1|1^24^5^50^0@93$1^1|1^23^5^35^0@92$1^1|1^22^5^20^0@91$1^1|1^21^5^15^0@90$1^1|1^20^5^10^0@89$1^1|1^19^5^1^0#
        String[] werer = CompleteSchemeIdListOnProductID.split(Pattern.quote("#"));
        for (int pos = 0; pos < werer.length; pos++) {

            String schIdforBen10 = "0";
            if (!werer[pos].split(Pattern.quote("_"))[0].equals("null")) {
                schIdforBen10 = werer[pos].split(Pattern.quote("_"))[0].toString();
            }
            //String schmTypeId=werer[pos]..split(Pattern.quote("_")))[1].toString();

            String[] arrProductRelatedToProject = mDataSource.fnGetDistinctProductIdAgainstStoreProduct(storeID, schIdforBen10, StoreVisitCode, TmpInvoiceCodePDA);
            if (arrProductRelatedToProject.length > 0) {

                for (int i = 0; i < arrProductRelatedToProject.length; i++) {
                    if (arrProductRelatedToProject[i] != null) {

                        //BenSubBucketType,FreeProductID,BenifitAssignedValue,BenifitDiscountApplied,BenifitCouponCode

                        int BenSubBucketType = Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[0]);
                        int FreeProductID = Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[1]);
                        Double BenifitAssignedValue = Double.parseDouble("" + arrProductRelatedToProject[i].split(Pattern.quote("^"))[2]);//Actually Given Values
                        Double BenifitDiscountApplied = Double.parseDouble("" + arrProductRelatedToProject[i].split(Pattern.quote("^"))[3]);//BenifitAssignedValueType on Net Order or Invoice
                        String BenifitCouponCode = arrProductRelatedToProject[i].split(Pattern.quote("^"))[4];
                        String CurrentSchemeIDOnProduct = arrProductRelatedToProject[i].split(Pattern.quote("^"))[5];
                        int schSlbRowId = Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[6]);
                        int SchTypeId = Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[7]);

                        String[] AllProductInSchSlab = mDataSource.fnGetProductsSchIdSlabRow(storeID, schSlbRowId, StoreVisitCode, TmpInvoiceCodePDA);


                        if (AllProductInSchSlab.length > 0) {

                            if (BenSubBucketType == 10 || BenSubBucketType == 7) {
									/*for(int mm=0;mm<AllProductInSchSlab.length;mm++)
									{*/
                                //Get the Object of Free Quantity TextBox of FreeProductID
                                //Get the value inside the TextBox of FreeProductID
                                //TextBox of  FreeProductID=TextBox of FreeProductID-BenifitAssignedValue

                                //hmapPrdctFreeQty.put(""+FreeProductID, ""+(Integer.parseInt(hmapPrdctFreeQty.get(""+FreeProductID).toString())-BenifitAssignedValue.intValue()));
                                //((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+FreeProductID)).setText(""+(Integer.parseInt(((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+FreeProductID)).getText().toString())-BenifitAssignedValue));
                                /*}*/
                                //dbengine.fnDeleteOldSchemeRowIdRecords(schSlbRowId);

                                String[] bensubBucket10Product = mDataSource.fnctnGetBensubBucket10Column(CurrentSchemeIDOnProduct, storeID, StoreVisitCode, TmpInvoiceCodePDA,flgInCarton);
                                if (bensubBucket10Product.length > 0) {
                                    for (int index = 0; index < bensubBucket10Product.length; index++) {

                                     /*   ImageView buttonException = (ImageView) ll_prdct_detal.findViewWithTag("btnException_" + (bensubBucket10Product[index]).split(Pattern.quote("^"))[0]);
                                        {

                                            if (buttonException.getVisibility() == View.VISIBLE) {
                                                buttonException.setVisibility(View.INVISIBLE);

                                            }

                                        }*/
                                        if (SchTypeId == 3) {
                                            hmapPrdctFreeQty.put(ProductIdOnClicked, "0");
                                           // hmapPrdctFreeQty.put(ProductIdOnClicked, "0");
                                            //hmapPrdctFreeQty.put(bensubBucket10Product[index], "0");
                                            hmapProductVolumePer.put("" + ProductIdOnClicked, "0.00");

                                            hmapPrdctIdPrdctDscnt.put("" + ProductIdOnClicked, "0.00");
                                            prdctModelArrayList.getHmapProductVolumePer().put("" + ProductIdOnClicked, "0.00");
                                            prdctModelArrayList.getHmapPrdctFreeQty().put("" + ProductIdOnClicked, "0");
                                            /*((TextView) ll_prdct_detal.findViewWithTag("tvDiscountVal_" + ProductIdOnClicked)).setText("0.00");
                                            if (Integer.parseInt(ProductIdOnClicked) != 0) {
                                                ((TextView) ll_prdct_detal.findViewWithTag("tvFreeQty_" + ProductIdOnClicked)).setText("0");
                                            }
                                            ((TextView) ll_prdct_detal.findViewWithTag("tvFreeQty_" + ProductIdOnClicked)).setText("0");*/

                                            mDataSource.deleteProductOldSlab215(storeID, schIdforBen10, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);
                                            mDataSource.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID, Integer.parseInt(schIdforBen10), StoreVisitCode, TmpInvoiceCodePDA,flgInCarton);
                                           // mDataSource.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID, ProductIdOnClicked, StoreVisitCode, TmpInvoiceCodePDA);
                                           // mDataSource.fnDeleteRecordsStoreProductAddOnSchemeApplied(storeID, Integer.parseInt(schIdforBen10), strGlobalOrderID);
                                        } else {
                                            hmapPrdctFreeQty.put(((bensubBucket10Product[index]).split(Pattern.quote("^")))[0], "0");
                                            hmapPrdctFreeQty.put(((bensubBucket10Product[index]).split(Pattern.quote("^")))[1], "0");
                                            prdctModelArrayList.getHmapPrdctFreeQty().put(((bensubBucket10Product[index]).split(Pattern.quote("^")))[0], "0");
                                            //hmapPrdctFreeQty.put(bensubBucket10Product[index], "0");
                                            hmapProductVolumePer.put("" + ((bensubBucket10Product[index]).split(Pattern.quote("^")))[0], "0.00");
                                            hmapProductVolumePerFinal.remove("" + ((bensubBucket10Product[index]).split(Pattern.quote("^")))[0] + "^" + schIdforBen10);
                                           // hmapPrdctIdPrdctDscnt.put("" + ((bensubBucket10Product[index]).split(Pattern.quote("^")))[0], "0.00");
                                            prdctModelArrayList.getHmapProductFlatDiscountGive().put("" + ((bensubBucket10Product[index]).split(Pattern.quote("^")))[0], "0.00");
/*

                                            ((TextView) ll_prdct_detal.findViewWithTag("tvDiscountVal_" + ((bensubBucket10Product[index]).split(Pattern.quote("^")))[0])).setText("0.00");
                                            if (Integer.parseInt(((bensubBucket10Product[index]).split(Pattern.quote("^")))[1].toString()) != 0) {
                                                ((TextView) ll_prdct_detal.findViewWithTag("tvFreeQty_" + ((bensubBucket10Product[index]).split(Pattern.quote("^")))[1].toString())).setText("0");
                                            }
                                            ((TextView) ll_prdct_detal.findViewWithTag("tvFreeQty_" + ((bensubBucket10Product[index]).split(Pattern.quote("^"))[0]))).setText("0");
*/

                                            mDataSource.deleteProductOldSlab215(storeID, schIdforBen10, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);
                                            // dbengine.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID,((bensubBucket10Product[index]).split(Pattern.quote("^")))[1],strGlobalOrderID);
                                            //dbengine.fnDeleteRecordsStoreProductAddOnSchemeApplied(storeID,((bensubBucket10Product[index]).split(Pattern.quote("^")))[1],strGlobalOrderID);
                                            mDataSource.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID, Integer.parseInt(schIdforBen10), StoreVisitCode, TmpInvoiceCodePDA,flgInCarton);
                                          //  mDataSource.fnDeleteRecordsStoreProductAddOnSchemeApplied(storeID, Integer.parseInt(schIdforBen10), strGlobalOrderID);


                                        }


                                    }


                                }


                            }
                        }


                    }
                }


            } else {
                //String arrSchmesRelatedToProject=dbengine.fnGetDistinctSchIdsAgainstStoreProduct(storeID,ProductIdOnClicked,Integer.parseInt(schIdforBen10));

                String arrSchmesRelatedToProject = mDataSource.fnGetDistinctSchIdsAgainstStoreForDelete(storeID, ProductIdOnClicked, Integer.parseInt(schIdforBen10),flgInCarton);


                if (!TextUtils.isEmpty(arrSchmesRelatedToProject)) {


                    //BenSubBucketType,FreeProductID,BenifitAssignedValue,BenifitDiscountApplied,BenifitCouponCode


                    //int FreeProductID=Integer.parseInt(arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[1]);
                    //Double BenifitAssignedValue=Double.parseDouble(""+arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[2]);//Actually Given Values
                    //Double BenifitDiscountApplied=Double.parseDouble(""+arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[3]);//BenifitAssignedValueType on Net Order or Invoice
                    //String BenifitCouponCode=arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[4];
                    //String CurrentSchemeIDOnProduct=arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[5];
                    int schSlbRowId = Integer.parseInt(arrSchmesRelatedToProject.split(Pattern.quote("^"))[0]);
                    int SchTypeId = Integer.parseInt(arrSchmesRelatedToProject.split(Pattern.quote("^"))[1]);
                    //int SchProdID=Integer.parseInt(arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[8]);
                    ArrayList<String> AllProductInSchSlab = new ArrayList<String>();
                    if (SchTypeId == 1 || SchTypeId == 2) {
                        AllProductInSchSlab = mDataSource.fnGetProductsSchIdSlabRowList(storeID, schSlbRowId, StoreVisitCode, TmpInvoiceCodePDA,flgInCarton);
                    } else {
                        AllProductInSchSlab.add(ProductIdOnClicked);
                    }


                    //BenSubBucketType
                    //1. Free Other Product
                    //2. Discount in Percentage with other product
                    //3. Discount in Amount with other product
                    //4. Coupons
                    //5. Free Same Product
                    //6. Discount in Percentage with same product
                    //7. Discount in Amount with same product
                    //8. Percentage On Invoice
                    //9.  Amount On Invoice
                 /*   if(((TextView) ll_prdct_detal.findViewWithTag("tvDiscountVal_" + ProductIdOnClicked))!=null)
                        ((TextView) ll_prdct_detal.findViewWithTag("tvDiscountVal_" + ProductIdOnClicked)).setText("0.00");
                    ImageView buttonException = (ImageView) ll_prdct_detal.findViewWithTag("btnException_" + ProductIdOnClicked);

                    if(buttonException!=null)
                    {

                        if (buttonException.getVisibility() == View.VISIBLE) {
                            buttonException.setVisibility(View.INVISIBLE);

                        }

                    }*/


                    for (String prodctMpdWdSchm : AllProductInSchSlab) {
                        String freePrdctId_benassVal = mDataSource.fnGetBenifitAssignedValue(storeID, Integer.valueOf(prodctMpdWdSchm), Integer.parseInt(schIdforBen10), TmpInvoiceCodePDA,flgInCarton);

                        if (!TextUtils.isEmpty(freePrdctId_benassVal.trim())) {
                            String freePrdctId = freePrdctId_benassVal.split(Pattern.quote("^"))[0];
                            Double besnAssignVal = Double.valueOf(freePrdctId_benassVal.split(Pattern.quote("^"))[1]);
                            int BenSubBucketType = Integer.parseInt(freePrdctId_benassVal.split(Pattern.quote("^"))[2]);
                            if (BenSubBucketType == 1 || BenSubBucketType == 5) {
                                hmapPrdctFreeQty.put("" + freePrdctId, "" + (Integer.valueOf(hmapPrdctFreeQty.get(freePrdctId)) - Math.abs(besnAssignVal.intValue())));
                                prdctModelArrayList.getHmapPrdctFreeQty().put("" + freePrdctId, "" + hmapPrdctFreeQty.get("" + freePrdctId));
                                hmapPrdctFreeQtyFinal.remove("" + freePrdctId + "^" + schIdforBen10);
                           /*     if( ((TextView) ll_prdct_detal.findViewWithTag("tvFreeQty_" + freePrdctId))!=null)
                                    ((TextView) ll_prdct_detal.findViewWithTag("tvFreeQty_" + freePrdctId)).setText(hmapPrdctFreeQty.get("" + freePrdctId).toString());*/
                            } else if (BenSubBucketType == 2 || BenSubBucketType == 6) {
                                hmapProductDiscountPercentageGive.put("" + freePrdctId, "" + (Double.valueOf(hmapProductDiscountPercentageGive.get(freePrdctId)) - besnAssignVal));
                                prdctModelArrayList.getHmapProductFlatDiscountGive().put("" + freePrdctId, "" + (Double.valueOf(prdctModelArrayList.getHmapProductFlatDiscountGive().get(freePrdctId)) - besnAssignVal));
                                hmapProductDiscountPercentageGiveFinal.remove("" + freePrdctId + "^" + schIdforBen10);
                             /*   if(((TextView) ll_prdct_detal.findViewWithTag("tvFreeQty_" + freePrdctId))!=null)
                                    ((TextView) ll_prdct_detal.findViewWithTag("tvDiscountVal_" + freePrdctId)).setText("0.0");*/

                            } else if (BenSubBucketType == 3 || BenSubBucketType == 7) {
                                hmapPrdctIdPrdctDscnt.put("" + freePrdctId, "" + (Double.valueOf(hmapPrdctIdPrdctDscnt.get(freePrdctId)) - besnAssignVal));
                                prdctModelArrayList.getHmapProductFlatDiscountGive().put("" + freePrdctId, "" + (Double.valueOf(prdctModelArrayList.getHmapProductFlatDiscountGive().get(freePrdctId)) - besnAssignVal));
                              /*  if(((TextView) ll_prdct_detal.findViewWithTag("tvFreeQty_" + freePrdctId))!=null)
                                    ((TextView) ll_prdct_detal.findViewWithTag("tvDiscountVal_" + freePrdctId)).setText(hmapPrdctIdPrdctDscnt.get("" + freePrdctId).toString());*/
                            }
                            if (SchTypeId == 1 || SchTypeId == 3) {
                                mDataSource.fnDeleteRecordsAllRecordsForClickedProdoductIdSchm_1_3(storeID, Integer.parseInt(prodctMpdWdSchm), StoreVisitCode, Integer.parseInt(schIdforBen10),flgInCarton);
                               // mDataSource.fnDeleteRecordsStoreProductAddOnSchemeAppliedSchm_1_3(storeID, Integer.parseInt(prodctMpdWdSchm), strGlobalOrderID, Integer.parseInt(schIdforBen10));

                            } else {
                                mDataSource.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID, Integer.parseInt(schIdforBen10),StoreVisitCode, TmpInvoiceCodePDA,flgInCarton);
                               // mDataSource.fnDeleteRecordsStoreProductAddOnSchemeApplied(storeID, Integer.parseInt(schIdforBen10), strGlobalOrderID);

                            }
                            mDataSource.deleteProductSchemeType3(storeID, prodctMpdWdSchm, StoreVisitCode,TmpInvoiceCodePDA,flgInCarton);


                        }

                    }


                    for (String prodctMpdWdSchm : AllProductInSchSlab) {
                        if (!ProductIdOnClicked.equals(prodctMpdWdSchm) && (!listProductChecked.contains(prodctMpdWdSchm))) {
                            if (hmapProductRelatedSchemesList.containsKey(prodctMpdWdSchm)) {
                                listProductChecked.add(prodctMpdWdSchm);
                                fnCheckNewSchemeIDsAppliedAfterValueChange(hmapProductRelatedSchemesList.get(prodctMpdWdSchm), prodctMpdWdSchm,0);
                            }
                        }


                    }


                } else {


                }
            }
        }


        fnCheckNewSchemeIDsAppliedAfterValueChange(hmapProductRelatedSchemesList.get(ProductIdOnClicked), ProductIdOnClicked,0);

    }


    public void fnCheckExtraSchemeAfterValueChange(String SchIdsCompleteListOnProductID, int ProductIdOnClicked) {
        //	arredtboc_OderQuantityFinalSchemesToApply=new ArrayList<String>();
        //Example :-1075_1_0_1!1026$1^1|1^23^1^10^0@1025$1^1|1^22^1^20^0@1022$1^1|1^19^5^5^0@1020$1^1|1^17^3^4^0@1019$1^1|1^16^1^12^0@1018$1^1|1^15^1^10^0@1017$1^1|1^14^1^12^0
        String valForVolumetQTYToMultiply = "0";
        //productFullFilledSlabGlobal=new ArrayList<String>();
        String[] arrSchIdsListOnProductID = SchIdsCompleteListOnProductID.split("#");
        for (int pSchIdsAppliCount = 0; pSchIdsAppliCount < arrSchIdsListOnProductID.length; pSchIdsAppliCount++) {
            //35_1_0_2 where 35=shcemId, 1= SchAppRule, 2= schemeTypeId
            String schOverviewDetails = arrSchIdsListOnProductID[pSchIdsAppliCount].split("!")[0];   //Example :-1075_1_0_1
            String schOverviewOtherDetails = arrSchIdsListOnProductID[pSchIdsAppliCount].split("!")[1]; //Example :-1026$1^1|1^23^1^10^0@1025$1^1|1^22^1^20^0@1022$1^1|1^19^5^5^0@1020$1^1|1^17^3^4^0@1019$1^1|1^16^1^12^0@1018$1^1|1^15^1^10^0@1017$1^1|1^14^1^12^0
            int schId = Integer.parseInt(schOverviewDetails.split("_")[0]);                           //Example :-1075
            int schAppRule = Integer.parseInt(schOverviewDetails.split("_")[1]);                                                                                        //Example :-1
            int schApplicationId = Integer.parseInt(schOverviewDetails.split("_")[2]);                                                              //Example :-0
            int SchTypeId = Integer.parseInt(schOverviewDetails.split("_")[3]);                                                                                           //Example :-1 // 1=Check Combined Skus, 2=Bundle,3=Simple with Check on Individual SKU
            String[] arrschSlbIDsOnSchIdBasis = schOverviewOtherDetails.split("@");   //Split for multiple slabs Example :-1026$1^1|1^23^1^10^0, 1025$1^1|1^22^1^20^0
            boolean bucketCndtnSchemeFullFill = false;
            int exitWhenSlabToExit = 0;

            if (hmapSchemeIdStoreID.containsKey("" + schId)) {
                boolean bucketCndtnFullFillisReally = false;
                for (int pSchSlbCount = 0; pSchSlbCount < arrschSlbIDsOnSchIdBasis.length; pSchSlbCount++) {
                    //Exmaple Slab:- 1026$1^1|1^23^1^10^0
                    int schSlabId = Integer.parseInt((arrschSlbIDsOnSchIdBasis[pSchSlbCount]).split(Pattern.quote("$"))[0]); //Exmaple Slab ID:- 1026
                    String schSlabOtherDetails = arrschSlbIDsOnSchIdBasis[pSchSlbCount].split(Pattern.quote("$"))[1]; //Exmaple Slab OtherDetails:- 1^1|1^23^1^10^0
                    String[] arrSchSlabBuckWiseDetails = schSlabOtherDetails.split(Pattern.quote("~")); //Example Split For Multiple Buckets (OR Condition)
                    for (int pSchSlbBuckCnt = 0; pSchSlbBuckCnt < arrSchSlabBuckWiseDetails.length; pSchSlbBuckCnt++) {
                        String schSlbBuckDetails = arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[0]; // Eaxmple:-1^1
                        String schSlbBuckOtherDetails = arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[1];  // Eaxmple:-1^23^1^10^0
                        int schSlbBuckId = Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[0]);  //Exmaple Slab Bucket ID:- 1
                        int schSlbBuckCnt = Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[1]);            //Example Number of Buckets under this Slab, Count:-1

                        String[] arrSubBucketDetails = schSlbBuckOtherDetails.split(Pattern.quote("*"));  //Example Split For Multiple Sub Buckets(AND Condition)
                        String[] arrMaintainDetailsOfBucketConditionsAgainstBuckId = new String[schSlbBuckCnt];  //Example Length of Buckes in Slab and which condition is true in case of OR
                        // variables for calculating total sub bucket
                        ArrayList<String> productFullFilledSlab = new ArrayList<String>();
                        ArrayList<String> schSlabRowIdFullFilledSlab = new ArrayList<String>();
                        ArrayList<String> productFullFilledSlabForInvoice = new ArrayList<String>();
                        int totalProductQnty = 0;
                        double totalProductVol = 0.0;

                        double totalProductVal = 0.0;
                        int totalProductLine = 0;
                        double totalInvoice = 0.0;

                        //product invoice
                        for (Map.Entry<String, String> entryProduct : prdctModelArrayList.getHmapPrdctOrderQty().entrySet()) {
                            if (prdctModelArrayList.getHmapPrdctOrderQty().containsKey(entryProduct.getKey())) {
                                if (Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(entryProduct.getKey())) > (0)) {

                                    int curntProdQty = Integer.parseInt(entryProduct.getValue());
                                    String curntProdVolumeRate = hmapPrdctVolRatTax.get(entryProduct.getKey());
                                    Double curntProdRate = Double.parseDouble(curntProdVolumeRate.split(Pattern.quote("^"))[1]);

                                    Double currentProductOverAllPriceQtywise = curntProdRate * curntProdQty;
                                    totalInvoice = totalInvoice + currentProductOverAllPriceQtywise;
                                    productFullFilledSlabForInvoice.add(entryProduct.getKey());
                                }
                            }

                        }
                        // end product invoice
                        //sub bucket starts here
                        LinkedHashMap<String, String> hmapSubBucketDetailsData = new LinkedHashMap<String, String>();
                        LinkedHashMap<String, String> hmapSubBucketTotalQntty = new LinkedHashMap<String, String>();
                        LinkedHashMap<String, String> hmapSubBucketTotalValue = new LinkedHashMap<String, String>();
                        LinkedHashMap<String, String> hmapSubBucketTotalVolume = new LinkedHashMap<String, String>();

                        for (int cntSubBucket = 0; cntSubBucket < arrSubBucketDetails.length; cntSubBucket++) {
                            // Eaxmple:-1^23^1^10^0
                            int schSlbSubBuckID = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[0]); //Slab Sub BucketID Eaxmple:-1  subBucketId
                            int schSlbSubRowID = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[1]);  //Slab Sub Bucket RowID Eaxmple:-23  rowid
                            int schSlabSubBucketType = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[2]);  ///Slab Sub Bucket Type Eaxmple:-1

                            Double schSlabSubBucketValue = Double.parseDouble(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[3]);  ///Slab Sub Bucket Value Eaxmple:-10
                            int schSubBucketValType = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[4]); ///Slab Sub Bucket Value Type Eaxmple:-0


                            int totalOderQtyProductsAgainstRowId = 0;
                            Double totalVolProductsAgainstRowId = 0.0;
                            Double totalValProductsAgainstRowId = 0.0;


                            //String[] productFullFilledSlab=new String[arrProductIDMappedInSchSlbSubBukRowId.length];
                            int positionOfProductHavingQntty = 0;
                            ArrayList<Integer> arrProductIDMappedInSchSlbSubBukRowId = new ArrayList<Integer>();

                            //IF SchTypeID==1 OR SchTypeID==2 OR SchTypeID==3  Code Starts Here To Check the Products

                            if (SchTypeId == 1 || SchTypeId == 2) {

                                arrProductIDMappedInSchSlbSubBukRowId = fectProductIDMappedInSchSlbSubBukRowIdTemp(schSlbSubRowID, 0, 0);// dbengine.fectProductIDMappedInSchSlbSubBukRowIdTemp(schSlbSubRowID);


                            }
                            if (SchTypeId == 3) {
                                arrProductIDMappedInSchSlbSubBukRowId.add(ProductIdOnClicked);
                            }

                            //IF SchTypeID==1 OR SchTypeID==2 OR SchTypeID==3  Code Ends Here To Check the Products
                            //SlabSubBucketValType
                            //I           =Invoice Value                  Order Value After Tax
                            //G         =Gross Value                     Order Value Before Tax
                            //N         =Net Value                                         Order Value After Tax


                            if (arrProductIDMappedInSchSlbSubBukRowId.size() > 0) {


                                for (Integer productMappedWithScheme : arrProductIDMappedInSchSlbSubBukRowId) {

                                    schSlabRowIdFullFilledSlab.add(productMappedWithScheme + "^" + schSlbSubRowID);
                                    productFullFilledSlab.add(productMappedWithScheme + "^" + schId);// productLine

                                    String hmapSubBucketDetailsData_Value = schId + "^" + schSlabId + "^" + schSlbBuckId + "^" + schSlabSubBucketValue + "^" + schSubBucketValType + "^" + schSlabSubBucketType + "^" + ProductIdOnClicked + "^" + valForVolumetQTYToMultiply + "^" + schSlbSubRowID + "^" + SchTypeId;
                                    hmapSubBucketDetailsData.put(productMappedWithScheme + "^" + schSlbSubRowID, hmapSubBucketDetailsData_Value);
                                    if (prdctModelArrayList.getHmapPrdctOrderQty().containsKey(productMappedWithScheme)) {
                                        if (Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(productMappedWithScheme)) > (0)) {
                                            //1. Product Quantity


                                            int oderQtyOnProd = Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(productMappedWithScheme));

                                            totalProductQnty = totalProductQnty + oderQtyOnProd;
                                            totalOderQtyProductsAgainstRowId = totalOderQtyProductsAgainstRowId + Integer.parseInt("" + oderQtyOnProd);
                                            hmapSubBucketTotalQntty.put("" + schSlbSubRowID, "" + totalOderQtyProductsAgainstRowId);
                                            // product volume
                                            Double prodVolume = Double.parseDouble(hmapPrdctVolRatTax.get(productMappedWithScheme).split(Pattern.quote("^"))[0]);
                                            Double oderVolumeOfCurrentMapedProduct = prodVolume * oderQtyOnProd;
                                            totalProductVol = totalProductVol + oderVolumeOfCurrentMapedProduct;
                                            totalVolProductsAgainstRowId = totalVolProductsAgainstRowId + oderVolumeOfCurrentMapedProduct;
                                            hmapSubBucketTotalVolume.put("" + schSlbSubRowID, "" + totalVolProductsAgainstRowId);
                                            //product value

                                            Double prodRate = Double.parseDouble(hmapPrdctVolRatTax.get(productMappedWithScheme).split(Pattern.quote("^"))[1]);
                                            Double oderRateOfCurrentMapedProduct = prodRate * oderQtyOnProd;
                                            //oderRateOnProduct=oderRateOnProduct + oderRateOfCurrentMapedProduct;
                                            totalProductVal = totalProductVal + oderRateOfCurrentMapedProduct;
                                            totalValProductsAgainstRowId = totalValProductsAgainstRowId + oderRateOfCurrentMapedProduct;
                                            hmapSubBucketTotalValue.put("" + schSlbSubRowID, "" + totalProductVal);


                                        }


                                    }


                                }// for loops ends here productMappedWithScheme:arrProductIDMappedInSchSlbSubBukRowId


                            }// ends if(arrProductIDMappedInSchSlbSubBukRowId.size()>0)


                        } //sub bucket ends here

                        //schSlabSubBucketType
                        //1. Product Quantity
                        //5. Product Volume
                        //2. Invoice Value
                        //3. Product Lines
                        //4. Product Value
                        boolean bucketCndtnFullFill = true;
                        String stringValHmap = "";
                        String stringValHmapInvoice = "";
                        ArrayList<String> listStrValHmapForSchm2 = new ArrayList<String>();
                        if (productFullFilledSlabForInvoice != null && productFullFilledSlabForInvoice.size() > 0) {
                            for (String productIdFullFilledSlabInvoiceWithQty : productFullFilledSlabForInvoice) {
                                if (hmapSubBucketDetailsData.containsKey(productIdFullFilledSlabInvoiceWithQty)) {
                                    stringValHmapInvoice = hmapSubBucketDetailsData.get(productIdFullFilledSlabInvoiceWithQty);
                                    String schSlabSubBucketType = stringValHmapInvoice.split(Pattern.quote("^"))[5];
                                    Double schSlabSubBucketVal = Double.valueOf(stringValHmapInvoice.split(Pattern.quote("^"))[3]);
                                    if (schSlabSubBucketType.equals("2")) {
                                        if (totalInvoice >= schSlabSubBucketVal) {
                                            insertProductMappedWithSchemApplied(storeID, Integer.parseInt(productIdFullFilledSlabInvoiceWithQty), schSlabId, schId, TmpInvoiceCodePDA);
                                            //dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabInvoiceWithQty, "" + schSlabId, "" + schId, strGlobalOrderID);
                                            break;
                                        } else {
                                            deleteAlertValueSlab(schSlabId);
                                            //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                            bucketCndtnFullFill = false;
                                            stringValHmapInvoice = "";
                                            break;
                                        }

                                    } else {
                                        stringValHmapInvoice = "";
                                    }
                                }

                            }
                        }

                        if (schSlabRowIdFullFilledSlab != null && schSlabRowIdFullFilledSlab.size() > 0) {
                            for (String productIdRowIDFullFilledSlabWithQty : schSlabRowIdFullFilledSlab) {
                                String productIdFullFilledSlabWithQty = productIdRowIDFullFilledSlabWithQty.split(Pattern.quote("^"))[0];
                                String RowIDFullFilledSlabWithQty = productIdRowIDFullFilledSlabWithQty.split(Pattern.quote("^"))[1];
                                stringValHmap = hmapSubBucketDetailsData.get(productIdRowIDFullFilledSlabWithQty);
                                String schSlabSubBucketType = stringValHmap.split(Pattern.quote("^"))[5];
                                Double schSlabSubBucketVal = Double.valueOf(stringValHmap.split(Pattern.quote("^"))[3]);
                                if (SchTypeId == 1 || SchTypeId == 3) {


                                    if (schSlabSubBucketType.equals("1")) {
                                        if (totalProductQnty >= schSlabSubBucketVal) {
                                            insertProductMappedWithSchemApplied(storeID, Integer.parseInt(productIdFullFilledSlabWithQty), schSlabId, schId, TmpInvoiceCodePDA);

                                            //dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty, "" + schSlabId, "" + schId, strGlobalOrderID);

                                        } else {

                                            deleteAlertValueSlab(schSlabId);
                                            //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                            bucketCndtnFullFill = false;
                                            stringValHmap = "";
                                            break;
                                        }

                                    }
                                    //Product Line
                                    if (schSlabSubBucketType.equals("3")) {
                                        if (productFullFilledSlab.size() >= schSlabSubBucketVal) {
                                            insertProductMappedWithSchemApplied(storeID, Integer.parseInt(productIdFullFilledSlabWithQty), schSlabId, schId, TmpInvoiceCodePDA);
                                        } else {
                                            deleteAlertValueSlab(schSlabId);
                                            //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                            bucketCndtnFullFill = false;
                                            stringValHmap = "";
                                            break;
                                        }
                                    }
                                    //product Value
                                    if (schSlabSubBucketType.equals("4")) {
                                        if (totalProductVal >= schSlabSubBucketVal) {
                                            insertProductMappedWithSchemApplied(storeID, Integer.parseInt(productIdFullFilledSlabWithQty), schSlabId, schId, TmpInvoiceCodePDA);
                                        } else {
                                            deleteAlertValueSlab(schSlabId);
                                            //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                            bucketCndtnFullFill = false;
                                            stringValHmap = "";
                                            break;
                                        }

                                    }
                                    //product volume
                                    if (schSlabSubBucketType.equals("5")) {
                                        if (totalProductVol >= (schSlabSubBucketVal * 1000)) {
                                            insertProductMappedWithSchemApplied(storeID, Integer.parseInt(productIdFullFilledSlabWithQty), schSlabId, schId, TmpInvoiceCodePDA);
                                        } else {
                                            deleteAlertValueSlab(schSlabId);
                                            //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                            bucketCndtnFullFill = false;
                                            stringValHmap = "";
                                            break;
                                        }
                                    }

                                } else // scheme typeid=2
                                {

                                    if (schSlabSubBucketType.equals("1")) {
                                        if (hmapSubBucketTotalQntty.containsKey(RowIDFullFilledSlabWithQty)) {
                                            int quantity = Integer.parseInt(hmapSubBucketTotalQntty.get(RowIDFullFilledSlabWithQty));
                                            if (quantity >= schSlabSubBucketVal) {
                                                insertProductMappedWithSchemApplied(storeID, Integer.parseInt(productIdFullFilledSlabWithQty), schSlabId, schId, TmpInvoiceCodePDA);
                                                listStrValHmapForSchm2.add(stringValHmap);
                                            } else {
                                                deleteAlertValueSlab(schSlabId);
                                                //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                                bucketCndtnFullFill = false;
                                                listStrValHmapForSchm2.clear();
                                                break;
                                            }
                                        } else {
                                            deleteAlertValueSlab(schSlabId);
                                            //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                            bucketCndtnFullFill = false;
                                            listStrValHmapForSchm2.clear();
                                            break;
                                        }

                                    }

                                    if (schSlabSubBucketType.equals("3")) {

                                        if (productFullFilledSlab.size() >= schSlabSubBucketVal) {
                                            listStrValHmapForSchm2.add(stringValHmap);
                                            insertProductMappedWithSchemApplied(storeID, Integer.parseInt(productIdFullFilledSlabWithQty), schSlabId, schId, TmpInvoiceCodePDA);
                                        } else {
                                            deleteAlertValueSlab(schSlabId);
                                            //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                            bucketCndtnFullFill = false;
                                            listStrValHmapForSchm2.clear();
                                            break;
                                        }
                                    }
                                    if (schSlabSubBucketType.equals("4")) {
                                        Double singleProdRate = Double.parseDouble(hmapPrdctVolRatTax.get(productIdFullFilledSlabWithQty).split(Pattern.quote("^"))[1]);
                                        Double singlePrdctOderRate = singleProdRate * Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(productIdFullFilledSlabWithQty));
                                        if (hmapSubBucketTotalValue.containsKey(RowIDFullFilledSlabWithQty)) {
                                            Double prdctVal = Double.parseDouble(hmapSubBucketTotalValue.get(RowIDFullFilledSlabWithQty));
                                            if (prdctVal >= schSlabSubBucketVal) {
                                                listStrValHmapForSchm2.add(stringValHmap);
                                                insertProductMappedWithSchemApplied(storeID, Integer.parseInt(productIdFullFilledSlabWithQty), schSlabId, schId, TmpInvoiceCodePDA);
                                            }
										/*if(singlePrdctOderRate>=schSlabSubBucketVal)
										{
											listStrValHmapForSchm2.add(stringValHmap);
											dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID);
										}*/
                                            else {
                                                deleteAlertValueSlab(schSlabId);
                                                //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                                bucketCndtnFullFill = false;
                                                listStrValHmapForSchm2.clear();
                                                break;
                                            }
                                        } else {
                                            deleteAlertValueSlab(schSlabId);
                                            //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                            bucketCndtnFullFill = false;
                                            listStrValHmapForSchm2.clear();
                                            break;
                                        }


                                    }
                                    if (schSlabSubBucketType.equals("5")) {
                                        Double singleProdVol = Double.parseDouble(hmapPrdctVolRatTax.get(productIdFullFilledSlabWithQty).split(Pattern.quote("^"))[0]);
                                        Double singlePrdctOderVol = singleProdVol * Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(productIdFullFilledSlabWithQty));
                                        if (hmapSubBucketTotalVolume.containsKey(RowIDFullFilledSlabWithQty)) {
                                            Double prdctVol = Double.parseDouble(hmapSubBucketTotalVolume.get(RowIDFullFilledSlabWithQty));
                                            if (prdctVol >= (schSlabSubBucketVal * 1000)) {
                                                insertProductMappedWithSchemApplied(storeID, Integer.parseInt(productIdFullFilledSlabWithQty), schSlabId, schId, TmpInvoiceCodePDA);
                                                listStrValHmapForSchm2.add(stringValHmap);
                                            } else {
                                                deleteAlertValueSlab(schSlabId);
                                                //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                                bucketCndtnFullFill = false;
                                                listStrValHmapForSchm2.clear();
                                                break;
                                            }
                                        } else {
                                            deleteAlertValueSlab(schSlabId);
                                            //dbengine.deleteAlertValueSlab(storeID, "" + schSlabId, strGlobalOrderID);
                                            bucketCndtnFullFill = false;
                                            listStrValHmapForSchm2.clear();
                                            break;
                                        }

                                    }


                                }


                            }


                        }//	if(productFullFilledSlab!=null && productFullFilledSlab.size()>0) ends here


                        if (bucketCndtnFullFill) {
                            bucketCndtnFullFillisReally = true;
                            if (SchTypeId == 1 || SchTypeId == 3) {
                                if (!TextUtils.isEmpty(stringValHmap.trim())) {
                                    for (String allproductFullFilledSlab : productFullFilledSlab) {
                                        productFullFilledSlabGlobal.add(allproductFullFilledSlab);
                                    }
                                    //productFullFilledSlabGlobal.addAll(productFullFilledSlab);

                                    arredtboc_OderQuantityFinalSchemesToApply.add(stringValHmap + "^" + totalProductQnty + "^" + totalInvoice + "^" + totalProductLine + "^" + totalProductVal + "^" + totalProductVol + "^1");
                                } else if (!TextUtils.isEmpty(stringValHmapInvoice.trim())) {
                                    arredtboc_OderQuantityFinalSchemesToApply.add(stringValHmapInvoice + "^" + totalProductQnty + "^" + totalInvoice + "^" + totalProductLine + "^" + totalProductVal + "^" + totalProductVol + "^1");
                                }
                            } else {
                                if (listStrValHmapForSchm2 != null && listStrValHmapForSchm2.size() > 0) {
                                    for (String allproductFullFilledSlab : productFullFilledSlab) {
                                        productFullFilledSlabGlobal.add(allproductFullFilledSlab);
                                    }
                                    //productFullFilledSlabGlobal.addAll(productFullFilledSlab);

                                    for (String strVal : listStrValHmapForSchm2) {

                                        arredtboc_OderQuantityFinalSchemesToApply.add(strVal + "^" + totalProductQnty + "^" + totalInvoice + "^" + totalProductLine + "^" + totalProductVal + "^" + totalProductVol + "^1");
                                    }
                                }
                                if (!TextUtils.isEmpty(stringValHmapInvoice.trim())) {
                                    arredtboc_OderQuantityFinalSchemesToApply.add(stringValHmapInvoice + "^" + totalProductQnty + "^" + totalInvoice + "^" + totalProductLine + "^" + totalProductVal + "^" + totalProductVol + "^1");
                                }

                            }
                            break;
                        }//if(bucketCndtnFullFill) ends here

                    }// bucket ends here

                    if (bucketCndtnFullFillisReally) {

                        break;
                    }
                }
            }

        }
        if (arredtboc_OderQuantityFinalSchemesToApply != null && arredtboc_OderQuantityFinalSchemesToApply.size() > 0) {
            fnAssignSchemeIDsAppliedOverProductAfterValueChange("" + ProductIdOnClicked);
        }
    }

    public ArrayList<Integer> fectProductIDMappedInSchSlbSubBukRowIdTemp(int schSlbSubRowID, int PrdID, int flgWhatToCall) {

        ArrayList<Integer> CompleteResult = new ArrayList<Integer>();
        if (flgWhatToCall == 0) {
            if (tblSchemeSlabBucketProductMappingList != null && tblSchemeSlabBucketProductMappingList.size() > 0) {
                Iterator<TblSchemeSlabBucketProductMapping> crunchifyIterator = tblSchemeSlabBucketProductMappingList.iterator();
                while (crunchifyIterator.hasNext()) {
                    TblSchemeSlabBucketProductMapping tblSchemeSlabBucketProductRecord = crunchifyIterator.next();
                    if (tblSchemeSlabBucketProductRecord != null) {
                        if (tblSchemeSlabBucketProductRecord.getRowID() == schSlbSubRowID) {
                            CompleteResult.add(tblSchemeSlabBucketProductRecord.getProductID());

                        }
                    }
                }
            }
        } else {
            if (tblSchemeSlabBucketProductMappingList != null && tblSchemeSlabBucketProductMappingList.size() > 0) {
                Iterator<TblSchemeSlabBucketProductMapping> crunchifyIterator = tblSchemeSlabBucketProductMappingList.iterator();
                while (crunchifyIterator.hasNext()) {
                    TblSchemeSlabBucketProductMapping tblSchemeSlabBucketProductRecord = crunchifyIterator.next();
                    if (tblSchemeSlabBucketProductRecord != null) {
                        if (tblSchemeSlabBucketProductRecord.getRowID() == schSlbSubRowID && tblSchemeSlabBucketProductRecord.getProductID() == PrdID) {
                            CompleteResult.add(tblSchemeSlabBucketProductRecord.getProductID());

                        }
                    }
                }
            }
        }

        return CompleteResult;
    }

    public void insertProductMappedWithSchemApplied(String storeId, int productId, int _schSlabId, int _schmId, String pdaOrderID) {
        TblProductMappedWithSchemeSlabApplied tblProductMappedWithSchemeSlabApplied = new TblProductMappedWithSchemeSlabApplied();
        tblProductMappedWithSchemeSlabApplied.setSchmIdMapped(_schmId);
        tblProductMappedWithSchemeSlabApplied.setSchSlabId(_schSlabId);
        tblProductMappedWithSchemeSlabApplied.setStoreId(storeId);
        tblProductMappedWithSchemeSlabApplied.setProductID(productId);
        tblProductMappedWithSchemeSlabApplied.setSstat(1);
        tblProductMappedWithSchemeSlabApplied.setOrderIDPDA(pdaOrderID);
        tblProductMappedWithSchemeSlabApplied.setTmpInvoiceCodePDA(pdaOrderID);
        tblProductMappedWithSchemeSlabApplied.setFlgInCarton(flgInCarton);
        tblProductMappedWithSchemeSlabAppliedArrayList.add(tblProductMappedWithSchemeSlabApplied);
    }

    public void fnCallAction() {

    }

    public void deleteAlertValueSlab(int _schSlabId) {
        if (tblAlrtValList != null && tblAlrtValList.size() > 0) {
            Iterator<TblAlrtVal> crunchifyIterator = tblAlrtValList.iterator();
            while (crunchifyIterator.hasNext()) {
                TblAlrtVal tblAlrtValRecord = crunchifyIterator.next();
                if (tblAlrtValRecord != null) {
                    if (tblAlrtValRecord.getSchSlabId() == _schSlabId) {
                        tblAlrtValList.remove(tblAlrtValRecord);
                    }
                }
            }
        }
    }

    public void fnAssignSchemeIDsAppliedOverProductAfterValueChange(String ProductIdOnClicked) {
        HashMap<String, ArrayList<String>> noAlrtHshMaptoSaveData = new HashMap<String, ArrayList<String>>();
        ArrayList<String> noAlrtStringSchemeIdWthAllVal = new ArrayList<String>();
        ArrayList<String> stringSchemeIdWthAllVal = new ArrayList<String>();
        ArrayList<HashMap<String, String>> listArrayHashmapProduct = new ArrayList<HashMap<String, String>>();
        ArrayList<String[]> listArrayFreePrdctQty = new ArrayList<String[]>();

        if (arredtboc_OderQuantityFinalSchemesToApply.size() > 0) {
            for (String strListMpdWdPrdct : arredtboc_OderQuantityFinalSchemesToApply) {
                //schId+"^"+schSlabId+"^"+schSlbBuckId+"^"+schSlabSubBucketValue+"^"+schSubBucketValType+"^"
                //+schSlabSubBucketType+"^"+ProductIdOnClicked +"^"+valForVolumetQTYToMultiply+"^"
                //+schSlbSubRowID+"^"+SchTypeId+"^"+totalProductQnty+"^"+totalInvoice+"^"
                //+totalProductLine+"^"+totalProductVal+totalProductVol;
                int schId = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[0]);
                int schSlabId = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[1]);
                int schSlbBuckId = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[2]);
                Double schSlabSubBucketValue = Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[3]);
                int schSubBucketValType = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[4]);
                int schSlabSubBucketType = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[5]);
                int Pid = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[6]);
                int toMultiply = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[7]);
                int schSlbSubRowID = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[8]);
                int SchTypeId = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[9]);
                int totalProductQty = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[10]);
                double totalInvoice = Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[11]);
                int totalProductLine = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[12]);
                double totalProductVal = Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[13]);
                double totalProductVol = Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[14]);
                int flgAddOn = Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[15]);

                if (hmapSchemeIdStoreID.containsKey("" + schId)) {
                    String[] arrProductIDBenifitsListOnPurchase = mDataSource.fectProductIDBenifitsListOnPurchase(schId, schSlabId, schSlbBuckId);
                    // RowID AS BenifitRowID,BenSubBucketType,BenDiscApplied,CouponCode,BenSubBucketValue,Per, UOM,ProRata
                    if (arrProductIDBenifitsListOnPurchase != null && arrProductIDBenifitsListOnPurchase.length > 0) {
                        for (String strProductIDBenifitsListOnPurchase : arrProductIDBenifitsListOnPurchase) {
                            int BenifitRowID = Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[0]);
                            int BenSubBucketType = Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[1]);
                            int BenDiscApplied = Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[2]);

                            // MinValueQty of free product
                            //String CouponCode=strProductIDBenifitsListOnPurchase.split("^")[0];
                            Double BenSubBucketValue = Double.parseDouble(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[4]);
                            Double Per = Double.parseDouble(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[5]);
                            Double UOM = Double.parseDouble(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[6]);
                            int chkflgProDataCalculation = Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[7]);
                            int isDiscountOnTotalAmount = Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[8]);

                            //BenSubBucketType
                            //1. Free Other Product =
                            //2. Discount in Percentage with other product
                            //3. Discount in Amount with other product
                            //4. Coupons
                            //5. Free Same Product

                            //6. Discount in Percentage with same product
                            //7. Discount in Amount with same product
                            //8. Percentage On Invoice
                            //9.  Amount On Invoice
                            //10. PerVolume Discount


                            if (BenSubBucketType == 1 || BenSubBucketType == 2 || BenSubBucketType == 3 || BenSubBucketType == 5 || BenSubBucketType == 6 || BenSubBucketType == 7) //1. Free Other Product 2. Discount in Percentage with other product 3. Discount in Amount with other product
                            {
                                HashMap<String, String> arrProductIDMappedInSchSlbSubBukBenifits = new HashMap<String, String>();
                                //productFullFilledSlabGlobal;
                                //BenValue
                                int isHaveMoreBenifits = 0;

                                String[] strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail = mDataSource.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID, toMultiply, BenSubBucketValue, BenSubBucketType);
                                if (BenSubBucketType == 1 || BenSubBucketType == 2 || BenSubBucketType == 3) {
                                    arrProductIDMappedInSchSlbSubBukBenifits = mDataSource.fectProductIDMappedInSchSlbSubBukBenifits(BenifitRowID);
                                } else if (BenSubBucketType == 6 || BenSubBucketType == 7) {
                                    arrProductIDMappedInSchSlbSubBukBenifits.put(hmapPrdctIdPrdctName.get(ProductIdOnClicked), ProductIdOnClicked);
                                } else {

                                    for (String productIdToFillSlab : productFullFilledSlabGlobal) {
                                        if ((prdctModelArrayList.getHmapPrdctOrderQty().containsKey(productIdToFillSlab.split(Pattern.quote("^"))[0])) && (Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(productIdToFillSlab.split(Pattern.quote("^"))[0])) > 0)) {
                                            arrProductIDMappedInSchSlbSubBukBenifits.put(hmapPrdctIdPrdctName.get(productIdToFillSlab.split(Pattern.quote("^"))[0]), productIdToFillSlab.split(Pattern.quote("^"))[0]);
                                        }
                                        //arrProductIDMappedInSchSlbSubBukBenifits.put(hmapPrdctIdPrdctName.get(productIdToFillSlab.split(Pattern.quote("^"))[0]), productIdToFillSlab.split(Pattern.quote("^"))[0]);
                                    }

                                }

                                defaultValForAlert = strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0];
                                defaultValForAlert = strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0];
                                HashMap<String, String> hmapFreeProdID = new HashMap<String, String>();
                                HashMap<String, String> hmapFreeProdIDAlrt = new HashMap<String, String>();
                                if (arrProductIDMappedInSchSlbSubBukBenifits.size() > 0) {

                                    String[] arrBenifitAssignedVal = new String[arrProductIDMappedInSchSlbSubBukBenifits.size()];
                                    int countAssignVal = 0;
                                    for (Map.Entry<String, String> allPrdctNamePrdctId : arrProductIDMappedInSchSlbSubBukBenifits.entrySet()) {
                                        Double accAsignVal = 0.0;
                                        String productIdForFree = allPrdctNamePrdctId.getValue();

                                        String maxBenifiAssignedValToCalc = "";
                                        String maxBenifiAssignedVal = mDataSource.getValOfSchemeAlrtSelected(storeID, "" + schId, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                        if (Double.parseDouble(maxBenifiAssignedVal) > 0) {

                                            maxBenifiAssignedValToCalc = maxBenifiAssignedVal;

                                        } else {
                                            mDataSource.deleteAlertValueProduct(storeID, "" + schId, StoreVisitCode, TmpInvoiceCodePDA);
                                        }
                                        boolean defaultSelected = false;
                                        if (!maxBenifiAssignedValToCalc.equals("")) {

                                            if (Double.parseDouble(maxBenifiAssignedValToCalc) > 0) {
                                                accAsignVal = Double.parseDouble(maxBenifiAssignedValToCalc);

                                                String[] strBeniftRowIdTest = mDataSource.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID, 1, BenSubBucketValue, BenSubBucketType);
                                                for (int i = 0; i < strBeniftRowIdTest.length; i++) {

                                                    Double benifit = Double.parseDouble(strBeniftRowIdTest[i]);
                                                    if (Double.parseDouble(maxBenifiAssignedValToCalc) == benifit) {
                                                        if (countAssignVal == (arrProductIDMappedInSchSlbSubBukBenifits.size() - 1)) {
                                                            accAsignVal = getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice, Per, productIdForFree, true);

                                                        } else {
                                                            if (BenSubBucketType == 6) {
                                                                accAsignVal = getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice, Per, productIdForFree, true);
                                                            } else {
                                                                accAsignVal = getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice, Per, productIdForFree, false);
                                                            }

                                                        }
                                                        // accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per ,productIdForFree);

                                                        defaultSelected = true;
                                                        break;
                                                    }
                                                }
                                            }

                                        }
                                        if (!defaultSelected) {
                                            if (countAssignVal == (arrProductIDMappedInSchSlbSubBukBenifits.size() - 1)) {
                                                accAsignVal = getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, BenSubBucketValue, schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice, Per, productIdForFree, true);

                                            } else {
                                                if (BenSubBucketType == 6) {
                                                    accAsignVal = getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, BenSubBucketValue, schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice, Per, productIdForFree, true);
                                                } else {
                                                    accAsignVal = getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, BenSubBucketValue, schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice, Per, productIdForFree, false);
                                                }

                                            }

                                        }

                                        String productNameValue = allPrdctNamePrdctId.getKey();
                                        hmapFreeProdIDAlrt.put(productNameValue, productIdForFree);
                                        hmapFreeProdID.put(productNameValue, productIdForFree);

                                        if (strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail.length > 1) {
                                            String subValues = String.valueOf(schId + "~" + schSlabId + "~" + schSlbBuckId + "~" + schSlabSubBucketValue + "~" + 0 + "~" + schSlabSubBucketType + "~" + BenifitRowID + "~" +
                                                    BenSubBucketType + "~" + 0 + "~" + BenSubBucketValue + "~" + 0 + "~" + 0 + "~" +
                                                    0 + "~" + 0 + "~" + 0 + "~" + 0.0 + "~" + 0.0 + "~" + schSlbSubRowID + "~" + SchTypeId + "~" + chkflgProDataCalculation + "~" + flgAddOn + "~" + isDiscountOnTotalAmount);
                                            stringSchemeIdWthAllVal.add(subValues);
                                            //listArrayHashmapProduct.add(hmapFreeProdID);
                                            isHaveMoreBenifits = 1;
                                            arrBenifitAssignedVal[countAssignVal] = (String.valueOf(accAsignVal));
                                            //       listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);


                                        } else {
                                            isHaveMoreBenifits = 0;


                                            ArrayList<String> noAlrtStringSchemeIdWthAllValTemp = new ArrayList<String>();

                                            String noAlrtsubValues = String.valueOf(accAsignVal + "~" + schId + "~" + schSlabId + "~" + schSlbBuckId + "~" + schSlabSubBucketValue + "~" + 0 + "~" + schSlabSubBucketType + "~" + BenifitRowID + "~" +
                                                    BenSubBucketType + "~" + 0 + "~" + BenSubBucketValue + "~" + 0 + "~" + 0 + "~" +
                                                    0 + "~" + 0 + "~" + 0 + "~" + 0.0 + "~" + 0.0 + "~" + schSlbSubRowID + "~" + SchTypeId + "~" + flgAddOn + "~" + isDiscountOnTotalAmount);

                                            noAlrtStringSchemeIdWthAllValTemp.add(noAlrtsubValues);


                                            //String[] arrayProductId=changeHmapToArrayValue(arrProductIDMappedInSchSlbSubBukBenifits);

                                            noAlrtHshMaptoSaveData.put(productIdForFree + "^" + schId, noAlrtStringSchemeIdWthAllValTemp);

                                        }
                                        countAssignVal++;
                                    }// for loop arrProductIDMappedInSchSlbSubBukBenifits.entrySet() ends here

                                    if (isHaveMoreBenifits == 1) {
                                        if (disValClkdOpenAlert) {


                                            listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);
                                            listArrayHashmapProduct.add(hmapFreeProdIDAlrt);
                                        } else {

                                            listArrayFreePrdctQty.add(arrBenifitAssignedVal);
                                            listArrayHashmapProduct.add(hmapFreeProdID);
                                        }
                                    }
                                }//if(arrProductIDMappedInSchSlbSubBukBenifits.size()>0)
                                alrtStopResult = false;
                            }//if(BenSubBucketType==1 || BenSubBucketType==2 || BenSubBucketType==3) ends here
                            if (BenSubBucketType == 10) //10. Free pr Unit Volume
                            {

                                HashMap<String, String> hmapMultiplePuschasedProductVolumeAndValue = new HashMap<String, String>();
                                HashMap<String, String> arrProductIDMappedInSchSlbSubBukBenifits = new HashMap<String, String>();
                                LinkedHashMap<String, String> hmapFreeProdID = new LinkedHashMap<String, String>();
                                // dbengine.open();

                     /*String productNameValue=hmapPrdctIdPrdctName.get(ProductIdOnClicked);
                     arrProductIDMappedInSchSlbSubBukBenifits.put(productNameValue,ProductIdOnClicked);*/


                                String[] strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail = mDataSource.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID, toMultiply, BenSubBucketValue, BenSubBucketType);
                                defaultValForAlert = strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0];


                                // dbengine.close();
                                Double AssigendValue = Double.parseDouble(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0]);
                                double totVolumeofProducts = 0.00;
                                double totlCombinedPriceOfProdcuts = 0.00;
                                if (productFullFilledSlabGlobal.size() > 0) {
                                    int countPerVol = 0;
                                    String maxBenifiAssignedVal = null;
                                    String maxBenifiAssignedValToCalc = "";
                                    int isHaveMoreBenifits = 0;
                                    for (String prdctIdMpdWithScheme : productFullFilledSlabGlobal) {

                                        prdctIdMpdWithScheme = prdctIdMpdWithScheme.split(Pattern.quote("^"))[0];
                                        if (Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(prdctIdMpdWithScheme)) > 0) {


                                            maxBenifiAssignedVal = mDataSource.getValOfSchemeAlrtSelected(storeID, "" + schId, "" + schSlabId, StoreVisitCode, TmpInvoiceCodePDA);
                                            if (Double.parseDouble(maxBenifiAssignedVal) > 0) {

                                                maxBenifiAssignedValToCalc = maxBenifiAssignedVal;

                                            } else {
                                                mDataSource.deleteAlertValueProduct(storeID, "" + schId, StoreVisitCode, TmpInvoiceCodePDA);
                                            }
                                            double prdPrice = Double.parseDouble(hmapPrdctVolRatTax.get(prdctIdMpdWithScheme).split(Pattern.quote("^"))[1]) * Double.parseDouble(hmapPrdctOdrQty.get(prdctIdMpdWithScheme));
                                            double prdVol = Double.parseDouble(hmapPrdctVolRatTax.get(prdctIdMpdWithScheme).split(Pattern.quote("^"))[0]) * Double.parseDouble(hmapPrdctOdrQty.get(prdctIdMpdWithScheme));
                                            if (prdVol >= Per) {
                                                countPerVol++;
                                            }
                                            hmapMultiplePuschasedProductVolumeAndValue.put(prdctIdMpdWithScheme, hmapPrdctIdPrdctName.get(prdctIdMpdWithScheme) + "^" + prdPrice + "^" + prdVol);
                                            totlCombinedPriceOfProdcuts = totlCombinedPriceOfProdcuts + prdPrice;
                                            totVolumeofProducts = totVolumeofProducts + prdVol;


                                        }


                                    }
                                    if (!maxBenifiAssignedValToCalc.equals("")) {
                                        boolean defaultSelected = false;
                                        if (Double.parseDouble(maxBenifiAssignedValToCalc) > 0) {
                                            if (countPerVol == 0) {
                                                countPerVol = 1;
                                            }
                                            String[] strBeniftRowIdTest = mDataSource.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID, 1, BenSubBucketValue, BenSubBucketType);
                                            for (int i = 0; i < strBeniftRowIdTest.length; i++) {

                                                Double benifit = Double.parseDouble(strBeniftRowIdTest[i]);
                                                if (Double.parseDouble(maxBenifiAssignedValToCalc) == benifit) {
                                                    AssigendValue = Double.parseDouble(maxBenifiAssignedValToCalc) * toMultiply;
                                                    defaultSelected = true;
                                                    break;
                                                }
                                            }
                                            if (!defaultSelected) {
                                                AssigendValue = Double.parseDouble(maxBenifiAssignedValToCalc);
                                            }


                                        }

                                    }


                                    if (hmapMultiplePuschasedProductVolumeAndValue.size() > 0) {
                                        double totOverAllValueDis = 0.00;
                                        if (schSlabSubBucketType == 5)//Vol-Flat Disc on Same Prd
                                        {
                                            if (chkflgProDataCalculation == 1) {
                                                if (Per.intValue() == 0) {
                                                    totOverAllValueDis = totVolumeofProducts * (AssigendValue / schSlabSubBucketValue);//BenSubBucketValue;BenSubBucketValue;
                                                } else {
                                                    //totOverAllValueDis=(totVolumeofProducts*(AssigendValue/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
                                                    totOverAllValueDis = ((totVolumeofProducts / Per) * AssigendValue);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
                                                }
                                            } else if (chkflgProDataCalculation == 0) {
                                                if (Per.intValue() == 0) {
                                                    totOverAllValueDis = AssigendValue;
                                                } else {

                                                    totOverAllValueDis = (Double.valueOf(totVolumeofProducts / Per).intValue()) * AssigendValue;

                                                }
                                            }
                                        }
                                        if (schSlabSubBucketType == 4)//Val-Flat Disc on Same Prd
                                        {
                                            if (chkflgProDataCalculation == 1) {
                                                if (Per.intValue() == 0) {
                                                    totOverAllValueDis = totlCombinedPriceOfProdcuts * (AssigendValue / schSlabSubBucketValue);
                                                } else {
                                                    //totOverAllValueDis=(totVolumeofProducts*(totlCombinedPriceOfProdcuts/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;(totlCombinedPriceOfProdcuts*BenSubBucketValue)/Per;
                                                    totOverAllValueDis = ((totVolumeofProducts / Per) * AssigendValue);//(totVolumeofProducts*(totlCombinedPriceOfProdcuts/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;(totlCombinedPriceOfProdcuts*BenSubBucketValue)/Per;
                                                }
                                            } else if (chkflgProDataCalculation == 0) {
                                                if (Per.intValue() == 0) {
                                                    totOverAllValueDis = AssigendValue;
                                                } else {
                                                    totOverAllValueDis = (Double.valueOf(totlCombinedPriceOfProdcuts / Per).intValue()) * AssigendValue;
                                                }
                                            }
                                        }
                                        if (schSlabSubBucketType == 1)//Quantity Based
                                        {
                                            if (Per.intValue() == 0) {
                                                totOverAllValueDis = totVolumeofProducts * (AssigendValue / schSlabSubBucketValue);//BenSubBucketValue;BenSubBucketValue;
                                            } else {
                                                //totOverAllValueDis=(totVolumeofProducts*(AssigendValue/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
                                                totOverAllValueDis = ((totVolumeofProducts / Per) * AssigendValue);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
                                            }                            //totOverAllValueDis=AssigendValue*toMultiply;

                                        }
                                        String[] arrPurchasedProductListVolumeAndValue = changeHmapToArrayKey(hmapMultiplePuschasedProductVolumeAndValue);
                                        String[] arrBenifitAssignedVal = new String[arrPurchasedProductListVolumeAndValue.length];
                                        HashMap<String, String> hmapFreeProdIDAlrt = new HashMap<String, String>();
                                        for (int cntPurchasedProductList = 0; cntPurchasedProductList < arrPurchasedProductListVolumeAndValue.length; cntPurchasedProductList++) {
                                            double calculatedBenifitAssignedValueSKULevel = 0.00;
                                            double prodValOrVol = 0.00;
                                            double caculateVolOrValOnSchSlabBasis = 0.00;

                                            if (schSlabSubBucketType == 5)//Vol-Flat Disc on Same Prd
                                            {
                                                if (chkflgProDataCalculation == 0) {
                                                    if (Per.intValue() != 0) {
                                                        prodValOrVol = Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
                                                        int perProdValOrVol = Double.valueOf(prodValOrVol / Per).intValue();
                                                        int perTotVolumeofProducts = Double.valueOf(totVolumeofProducts / Per).intValue();
                                                        calculatedBenifitAssignedValueSKULevel = ((float) perProdValOrVol / (float) perTotVolumeofProducts) * totOverAllValueDis;
                                                    } else {
                                                        prodValOrVol = Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
                                                        calculatedBenifitAssignedValueSKULevel = Double.valueOf(prodValOrVol / totVolumeofProducts).intValue() * totOverAllValueDis;
                                                    }

                                                } else {
                                                    prodValOrVol = Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
                                                    calculatedBenifitAssignedValueSKULevel = (prodValOrVol / totVolumeofProducts) * totOverAllValueDis;
                                                }

                                            }
                                            if (schSlabSubBucketType == 4)//Val-Flat Disc on Same Prd
                                            {
                                                if (chkflgProDataCalculation == 0) {
                                                    if (Per.intValue() != 0) {
                                                        prodValOrVol = Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
                                                        int perProdValOrVol = Double.valueOf(prodValOrVol / Per).intValue();
                                                        int perTotlCombinedPriceOfProdcuts = Double.valueOf(totlCombinedPriceOfProdcuts / Per).intValue();
                                                        calculatedBenifitAssignedValueSKULevel = ((float) perProdValOrVol / (float) perTotlCombinedPriceOfProdcuts) * totOverAllValueDis;
                                                    } else {
                                                        prodValOrVol = Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[1]);
                                                        calculatedBenifitAssignedValueSKULevel = (prodValOrVol / totlCombinedPriceOfProdcuts) * totOverAllValueDis;
                                                    }

                                                }

                                            }
                                            if (schSlabSubBucketType == 1)//Quantity Based
                                            {
                                                if (chkflgProDataCalculation == 0) {
                                                    if (Per.intValue() != 0) {
                                                        prodValOrVol = Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
                                                        int perProdValOrVol = Double.valueOf(prodValOrVol / Per).intValue();
                                                        int perTotVolumeofProducts = Double.valueOf(totVolumeofProducts / Per).intValue();
                                                        calculatedBenifitAssignedValueSKULevel = ((float) perProdValOrVol / (float) perTotVolumeofProducts) * totOverAllValueDis;
                                                    } else {
                                                        prodValOrVol = Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
                                                        calculatedBenifitAssignedValueSKULevel = Double.valueOf(prodValOrVol / totVolumeofProducts).intValue() * totOverAllValueDis;
                                                    }

                                                } else {
                                                    prodValOrVol = Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
                                                    calculatedBenifitAssignedValueSKULevel = (prodValOrVol / totVolumeofProducts) * totOverAllValueDis;
                                                }
                                            }
                                            String productNameValue = hmapPrdctIdPrdctName.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
                                            arrProductIDMappedInSchSlbSubBukBenifits.put(productNameValue, arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
                                            hmapFreeProdIDAlrt.put(productNameValue, arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
                                            hmapFreeProdID.put(productNameValue, arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
                                            if (strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail.length > 1) {
                                                String subValues = String.valueOf(schId + "~" + schSlabId + "~" + schSlbBuckId + "~" + schSlabSubBucketValue + "~" + 0 + "~" + schSlabSubBucketType + "~" + BenifitRowID + "~" +
                                                        BenSubBucketType + "~" + 0 + "~" + calculatedBenifitAssignedValueSKULevel + "~" + 0 + "~" + 0 + "~" +
                                                        0 + "~" + 0 + "~" + 0 + "~" + Per + "~" + UOM + "~" + schSlbSubRowID + "~" + SchTypeId + "~" + flgAddOn + "~" + isDiscountOnTotalAmount);
                                                arrBenifitAssignedVal[cntPurchasedProductList] = String.valueOf(calculatedBenifitAssignedValueSKULevel);
                                                stringSchemeIdWthAllVal.add(subValues);
                                                isHaveMoreBenifits = 1;
                                                //listArrayHashmapProduct.add(hmapFreeProdID);

                                            } else {
                                                ArrayList<String> noAlrtStringSchemeIdWthAllValTemp = new ArrayList<String>();

                                                String noAlrtsubValues = String.valueOf(calculatedBenifitAssignedValueSKULevel + "~" + schId + "~" + schSlabId + "~" + schSlbBuckId + "~" + schSlabSubBucketValue + "~" + 0 + "~" + schSlabSubBucketType + "~" + BenifitRowID + "~" +
                                                        BenSubBucketType + "~" + 0 + "~" + BenSubBucketValue + "~" + 0 + "~" + 0 + "~" +
                                                        0 + "~" + 0 + "~" + 0 + "~" + 0.0 + "~" + 0.0 + "~" + schSlbSubRowID + "~" + SchTypeId + "~" + flgAddOn + "~" + isDiscountOnTotalAmount);

                                                noAlrtStringSchemeIdWthAllValTemp.add(noAlrtsubValues);


                                                String[] arrayProductId = changeHmapToArrayValue(arrProductIDMappedInSchSlbSubBukBenifits);
                                                noAlrtHshMaptoSaveData.put(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList] + "^" + schId, noAlrtStringSchemeIdWthAllValTemp);
                                            }


                                            //listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);
                                        }
                                        if (isHaveMoreBenifits == 1) {
                                            if (disValClkdOpenAlert) {

                                                listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);
                                                listArrayHashmapProduct.add(hmapFreeProdIDAlrt);
                                            } else {

                                                listArrayFreePrdctQty.add(arrBenifitAssignedVal);
                                                listArrayHashmapProduct.add(hmapFreeProdID);
                                            }
                                        }
                                    }
                                }

                                //Now get the Free Product EditTextBox ID and  gets its current value
                                //Minus the AppliedQty from the present value
                            }// if(BensubBucketType==10) ends here
                        }//for(String strProductIDBenifitsListOnPurchase:arrProductIDBenifitsListOnPurchase ) ends here

                    }//arrProductIDBenifitsListOnPurchase length condition ends here

                }//hmapSchemeIdStoreID.containsKey(""+schId) ends here
            }// for loops ends here for arredtboc_OderQuantityFinalSchemesToApply

            if (noAlrtHshMaptoSaveData != null && noAlrtHshMaptoSaveData.size() > 0) {
                boolean flagMappedToProduct = false; // if noAlrtHshMaptoSaveData doenot contains arrProductIDMappedInSchSlbSubBukRowId
                if (productFullFilledSlabGlobal != null && productFullFilledSlabGlobal.size() > 0) {

                    for (int cntProdcutsRowIdCnt = 0; cntProdcutsRowIdCnt < productFullFilledSlabGlobal.size(); cntProdcutsRowIdCnt++) {

                        if (noAlrtHshMaptoSaveData.containsKey(productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt))) {
                            flagMappedToProduct = true;
                            HashMap<String, ArrayList<String>> noAlrtHshMaptoSaveDataTemp = new HashMap<String, ArrayList<String>>();

                            noAlrtHshMaptoSaveDataTemp.put(productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt).split(Pattern.quote("^"))[0], noAlrtHshMaptoSaveData.get(productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt)));
                            saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveDataTemp, productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt).split(Pattern.quote("^"))[0]);
                        }

                    }

                    if (!flagMappedToProduct) {
                        saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
                    }

                } else {
                    saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
                }
            }
            //saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
            if (listArrayHashmapProduct.size() > 0) {
                //saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
                if (disValClkdOpenAlert) {
                    disValClkdOpenAlert = false;

                    customAlert(listArrayHashmapProduct, listArrayFreePrdctQty, alrtObjectTypeFlag, stringSchemeIdWthAllVal, ProductIdOnClicked);
                } else {

                    String[] arrayProductIdToDefault = changeHmapToArrayValue(listArrayHashmapProduct.get(0));

                    for (int abc = 0; abc < arrayProductIdToDefault.length; abc++) {
                        ArrayList<String> arrayListSaveAssigndVal = new ArrayList<String>();
                        HashMap<String, ArrayList<String>> alerValWithDefault = new HashMap<String, ArrayList<String>>();


                        String defaultVal = (listArrayFreePrdctQty.get(0))[abc].toString();

                        String defaultValWithDefltAssigndVal = defaultVal + "~" + stringSchemeIdWthAllVal.get(0).toString();
                        String spinnerValSelected = mDataSource.getValOfSchemeAlrtSelected(storeID, (stringSchemeIdWthAllVal.get(0)).split(Pattern.quote("~"))[1], (stringSchemeIdWthAllVal.get(0)).split(Pattern.quote("~"))[0], TmpInvoiceCodePDA, TmpInvoiceCodePDA);
                        String[] spinnerPositionSelected = mDataSource.getValOfSchemeAlrt(storeID, ProductIdOnClicked, "" + (stringSchemeIdWthAllVal.get(0)).split(Pattern.quote("~"))[1], TmpInvoiceCodePDA, TmpInvoiceCodePDA);


                        arrayListSaveAssigndVal.add(defaultValWithDefltAssigndVal);
                        alerValWithDefault.put(arrayProductIdToDefault[abc], arrayListSaveAssigndVal);
                        if (defaultValWithDefltAssigndVal.split(Pattern.quote("~"))[8].equals("10") || defaultValWithDefltAssigndVal.split(Pattern.quote("~"))[8].equals("7") || defaultValWithDefltAssigndVal.split(Pattern.quote("~"))[8].equals("6")) {
                            saveFreeProductDataWithSchemeToDatabase(alerValWithDefault, arrayProductIdToDefault[abc]);
                            // exception sighn
                        /*    final ImageView btnExcptnAlrt = (ImageView) ll_prdct_detal.findViewWithTag("btnException_" + arrayProductIdToDefault[abc]);
                            EditText edOrderText = (EditText) ll_prdct_detal.findViewWithTag("etOrderQty_" + arrayProductIdToDefault[abc]);
                            if (edOrderText.getText().equals("") || TextUtils.isEmpty(edOrderText.getText().toString())) {
                                btnExcptnAlrt.setVisibility(View.INVISIBLE);
                            } else {
                                btnExcptnAlrt.setVisibility(View.VISIBLE);
                            }*/

/*
                            btnExcptnAlrt.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    disValClkdOpenAlert = true;
                                    getOrderData(((btnExcptnAlrt.getTag().toString()).split(Pattern.quote("_")))[1]);

                                    // orderBookingTotalCalc();

                                }
                            });*/


                        } else {
                            saveFreeProductDataWithSchemeToDatabase(alerValWithDefault, ProductIdOnClicked);


                           /* final ImageView btnExcptnAlrt = (ImageView) ll_prdct_detal.findViewWithTag("btnException_" + ProductIdOnClicked);
                            EditText edOrderText = (EditText) ll_prdct_detal.findViewWithTag("etOrderQty_" + ProductIdOnClicked);
                            if (edOrderText.getText().equals("") || TextUtils.isEmpty(edOrderText.getText().toString())) {
                                if (btnExcptnAlrt.getVisibility() == View.VISIBLE) {
                                    btnExcptnAlrt.setVisibility(View.INVISIBLE);
                                }

                            } else {

                                btnExcptnAlrt.setVisibility(View.VISIBLE);
                                btnExcptnAlrt.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        disValClkdOpenAlert = true;
                                        getOrderData(((btnExcptnAlrt.getTag().toString()).split(Pattern.quote("_")))[1]);

                                        // orderBookingTotalCalc();

                                    }
                                });
                            }*/


                            for (int i = 0; i < productFullFilledSlabGlobal.size(); i++) {
                                if (Integer.parseInt(prdctModelArrayList.getHmapPrdctOrderQty().get(productFullFilledSlabGlobal.get(i))) > 0) {
                                   /* final ImageView btnExcptnAlrtTemp = (ImageView) ll_prdct_detal.findViewWithTag("btnException_" + productFullFilledSlabGlobal.get(i));
                                    EditText edOrderTextTemp = (EditText) ll_prdct_detal.findViewWithTag("etOrderQty_" + productFullFilledSlabGlobal.get(i));

                                    if (btnExcptnAlrtTemp.getVisibility() == View.INVISIBLE) {
                                        btnExcptnAlrtTemp.setVisibility(View.VISIBLE);
                                        btnExcptnAlrtTemp.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {

                                                disValClkdOpenAlert = true;
                                                getOrderData(((btnExcptnAlrtTemp.getTag().toString()).split(Pattern.quote("_")))[1]);

                                                // orderBookingTotalCalc();

                                            }
                                        });
                                    }*/


                                }

                            }


                        }


                    }


                    // }


                }
            }

        }// ends here arredtboc_OderQuantityFinalSchemesToApply length check

        fnCallAction();
    }


    public Double getAccAsignValue(int schSlabSubBucketType, int chkflgProDataCalculation, double BenSubBucketValue, double schSlabSubBucketValue, int totalProductQty, int totalProductLine, double totalProductValue, double totalProductVol, double totalInvoice, double per, String productIdForFree, boolean isLastPrdct) {
        Double accAsignVal = 0.0;
        //1. Product Quantity  //Check Prorata multiple Condition
        //5. Product Volume  //Check Prorata multiple Condition
        //2. Invoice Value
        //3. Product Lines
        //4. Product Value      //Check Prorata multiple Condition

        //Product Quantity
        if (schSlabSubBucketType == 1) {
            if (chkflgProDataCalculation == 1) {

                if (isLastPrdct) {
                accAsignVal = BenSubBucketValue * (totalProductQty / schSlabSubBucketValue);
                accAsignVal = Double.valueOf(Math.abs(accAsignVal.intValue()));
                 }
                //accAsignVal=Double.valueOf(Double.valueOf(hmapPrdctOdrQty.get(productIdForFree))*accAsignVal/totalProductQty);

            } else {
                if (isLastPrdct) {
                    //accAsignVal = BenSubBucketValue;
                    accAsignVal = BenSubBucketValue;// * (totalProductQty / schSlabSubBucketValue)
                    //  accAsignVal =accAsignVal;
                }

            }

        }
        //Invoice Value
        else if (schSlabSubBucketType == 2) {

            if (chkflgProDataCalculation == 1) {
                accAsignVal = BenSubBucketValue * (totalInvoice / schSlabSubBucketValue);

            } else {
                if (isLastPrdct) {
                    accAsignVal = BenSubBucketValue;
                }

            }
        }
        //Product Lines
        else if (schSlabSubBucketType == 3) {
            if (chkflgProDataCalculation == 1) {
                accAsignVal = BenSubBucketValue * (totalProductLine / schSlabSubBucketValue);
                accAsignVal = Double.valueOf(Double.valueOf(productFullFilledSlabGlobal.size()) * accAsignVal / totalProductLine);
            } else {
                if (isLastPrdct) {
                    accAsignVal = BenSubBucketValue;
                }
            }
        }
        //Product Value
        else if (schSlabSubBucketType == 4) {
            if (chkflgProDataCalculation == 1) {
                accAsignVal = BenSubBucketValue * (totalProductValue / schSlabSubBucketValue);

                //product value

                Double prodRate = Double.parseDouble(hmapPrdctVolRatTax.get(productIdForFree).split(Pattern.quote("^"))[1]);
                Double oderRateOfCurrentMapedProduct = prodRate * Double.valueOf(prdctModelArrayList.getHmapPrdctOrderQty().get(productIdForFree));
                //oderRateOnProduct=oderRateOnProduct + oderRateOfCurrentMapedProduct;
                double singleProductVal = oderRateOfCurrentMapedProduct;

                if (per > 0.0) {
                    singleProductVal = singleProductVal / per;
                }
                accAsignVal = Double.valueOf(Double.valueOf(singleProductVal) * (accAsignVal / totalProductValue));


            } else {

                if (isLastPrdct) {
                    accAsignVal = BenSubBucketValue;
                }
            }

        }
        // Product Volume
        else if (schSlabSubBucketType == 5) {
            if (chkflgProDataCalculation == 1) {

                accAsignVal = BenSubBucketValue * (totalProductVol / (schSlabSubBucketValue * 1000));
                // product volume
                Double prodVolume = Double.parseDouble(hmapPrdctVolRatTax.get(productIdForFree).split(Pattern.quote("^"))[0]);
                Double oderVolumeOfCurrentMapedProduct = prodVolume * Double.valueOf(prdctModelArrayList.getHmapPrdctOrderQty().get(productIdForFree));
                Double singleProductVol = oderVolumeOfCurrentMapedProduct;
                if (per > 0.0) {
                    singleProductVol = singleProductVol / per;
                }
                accAsignVal = Double.valueOf(Double.valueOf(singleProductVol) * accAsignVal / totalProductVol);

            } else {
                if (isLastPrdct) {
                    accAsignVal = BenSubBucketValue;
                }
            }
        }
        return accAsignVal;
    }

    public void saveFreeProductDataWithSchemeToDatabase(HashMap<String, ArrayList<String>> hashMapSelectionFreeQty, String savProductIdOnClicked) {

        String freeProductID;
        ArrayList<String> listFreeProdctQtyScheme;


        for (Map.Entry<String, ArrayList<String>> entry : hashMapSelectionFreeQty.entrySet()) {
            if (entry.getKey().contains("^")) {
                freeProductID = entry.getKey().split(Pattern.quote("^"))[0];
            } else {
                freeProductID = entry.getKey();
            }

            listFreeProdctQtyScheme = entry.getValue();

            for (String strFreeProdctQtyScheme : listFreeProdctQtyScheme) {
                //[10.0, 41, 60, 1, 500.0, 0, 4, 2, 6, 0, 10.0, 0, 0, 0, 0, 0, 0.0, 0.0, 2]

                String[] arrayAllValues = strFreeProdctQtyScheme.split(Pattern.quote("~"));

                int schemeId = Integer.parseInt(arrayAllValues[1]);

                int schemeSlabId = Integer.parseInt(arrayAllValues[2]);

                int schemeSlabBcktId = Integer.parseInt((arrayAllValues[3]));

                Double schemeSlabSubBcktVal = Double.parseDouble(arrayAllValues[4]);

                int schemeSubBucktValType = Integer.parseInt(arrayAllValues[5]);
                //[10.0, 41, 60, 1, 500.0, 0, 4, 2, 6, 0, 10.0, 0, 0, 0, 0, 0, 0.0, 0.0, 2]
                int schemeSlabSubBucktType = Integer.parseInt(arrayAllValues[6]);

                int benifitRowId = Integer.parseInt(arrayAllValues[7]);

                int benSubBucketType = Integer.parseInt(arrayAllValues[8]);

                int freeProductId = Integer.parseInt(freeProductID);

                Double benifitSubBucketValue = Double.parseDouble(arrayAllValues[10]);

                Double benifitMaxValue = Double.parseDouble(arrayAllValues[11]);

                Double benifitAssignedVal = Double.parseDouble(arrayAllValues[0]);

                Double benifitAssignedValueType = Double.parseDouble(arrayAllValues[13]);

                int benifitDiscountApplied = Integer.parseInt(arrayAllValues[14]);

                String benifitCoupnCode = arrayAllValues[15];

                Double per = Double.parseDouble(arrayAllValues[16]);

                Double UOM = Double.parseDouble(arrayAllValues[17]);
                int schSlbRowId = Integer.parseInt(arrayAllValues[18]);
                int SchTypeId = Integer.parseInt(arrayAllValues[19]);
                int flgAddOn = Integer.parseInt(arrayAllValues[20]);
                int isDiscountOnTotalAmount = Integer.parseInt(arrayAllValues[21]);
                int WhatFinallyApplied = 1;


                if (benSubBucketType == 1 || benSubBucketType == 5)//Free Different Product  / Free Same Product
                {

                    if (flgAddOn == 0) {
                        hmapPrdctFreeQty.put("" + freeProductId, "" + benifitAssignedVal.intValue());
                        prdctModelArrayList.setFreeQty("" + freeProductId, "" + benifitAssignedVal.intValue());

                    } else {
                        if (hmapPrdctFreeQty != null && hmapPrdctFreeQty.containsKey("" + freeProductId)) {
                            int exactVal = 0;
                            if (hmapPrdctFreeQtyFinal.containsKey("" + freeProductId + "^" + schemeId)) {

                            } else {
                                hmapPrdctFreeQtyFinal.put("" + freeProductId + "^" + schemeId, "" + benifitAssignedVal);
                                exactVal = Integer.parseInt(hmapPrdctFreeQty.get("" + freeProductId));
                            }

                            int totalVal = benifitAssignedVal.intValue() + exactVal;
                            hmapPrdctFreeQty.put("" + freeProductId, "" + totalVal);
                            prdctModelArrayList.setFreeQty("" + freeProductId, "" + totalVal);

                        }
                    }
                    //hmapPrdctFreeQty.put(String.valueOf(freeProductId),((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+freeProductId)).getText().toString());
                    WhatFinallyApplied = 1;
                }
                if (benSubBucketType == 7)//FlatDiscount
                {
                    if (flgAddOn == 0) {
                        hmapProductFlatDiscountGive.put("" + freeProductId, "" + benifitAssignedVal.intValue());
                        prdctModelArrayList.getHmapProductFlatDiscountGive().put("" + freeProductId, "" + benifitAssignedVal);
                     //   prdctModelArrayList.getHmapPrdctDiscountPerUOM().put("" + freeProductId, "" + benifitAssignedVal);


                       /* double OverAllDiscountCalculated = 0.0;
                        if (prdctModelArrayList.getHmapPrdctDiscountPerUOM() != null && prdctModelArrayList.getHmapPrdctDiscountPerUOM().size() > 0 && prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey(freeProductId)) {
                            OverAllDiscountCalculated = Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(freeProductId));
                        }
                        prdctModelArrayList.getHmapPrdctOverAllDiscountCalculated().put("" + freeProductId, "" + ((OverAllDiscountCalculated) + benifitAssignedVal));*/

                    } else {
                        if (hmapProductFlatDiscountGive != null && hmapProductFlatDiscountGive.containsKey("" + freeProductId)) {
                            Double val = Double.parseDouble(hmapProductFlatDiscountGive.get("" + freeProductId));
                            int exactVal = 0;
                            if (hmapProductFlatDiscountGiveFinal.containsKey("" + freeProductId + "^" + schemeId)) {

                            } else {
                                hmapProductFlatDiscountGiveFinal.put("" + freeProductId + "^" + schemeId, "" + benifitAssignedVal);
                                exactVal = val.intValue();
                            }
                            int totalVal = benifitAssignedVal.intValue() + exactVal;
                            hmapProductFlatDiscountGive.put("" + freeProductId, "" + totalVal);
                        }
                    }
                    //hmapPrdctFreeQty.put(String.valueOf(freeProductId),((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+freeProductId)).getText().toString());
                    WhatFinallyApplied = 1;
                }
                if (benSubBucketType == 2 || benSubBucketType == 6)//Discount in Percentage with other product  / Discount in Percentage with same product
                {
                    if (flgAddOn == 0) {
                        hmapProductDiscountPercentageGive.put("" + freeProductId, "" + benifitAssignedVal.intValue());
                        prdctModelArrayList.getHmapProductDiscountPercentageGive().put("" + freeProductId, "" + benifitAssignedVal);
                    } else {
                        if (hmapProductDiscountPercentageGive != null && hmapProductDiscountPercentageGive.containsKey("" + freeProductId)) {
                            Double val = Double.parseDouble(hmapProductDiscountPercentageGive.get("" + freeProductId));
                            int exactVal = 0;
                            if (hmapProductDiscountPercentageGiveFinal.containsKey("" + freeProductId + "^" + schemeId)) {

                            } else {
                                hmapProductDiscountPercentageGiveFinal.put("" + freeProductId + "^" + schemeId, "" + benifitAssignedVal);
                                exactVal = val.intValue();
                            }


                            int totalVal = benifitAssignedVal.intValue() + exactVal;
                            hmapProductDiscountPercentageGive.put("" + freeProductId, "" + totalVal);
                        }
                    }

                    //hmapPrdctFreeQty.put(String.valueOf(freeProductId),((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+freeProductId)).getText().toString());
                    WhatFinallyApplied = 1;
                }
                //((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+AllProductInSchSlab[mm])).setText("0.00");

                if (benSubBucketType == 10) {
                    WhatFinallyApplied = 1;
                    //benifitAssignedVal=benifitSubBucketValue;

                    if (flgAddOn == 0) {
                        hmapProductVolumePer.put("" + freeProductId, "" + per);

                    } else {
                        if (hmapProductVolumePer != null && hmapProductVolumePer.containsKey("" + freeProductId)) {
                            Double exactVal = 0.0;
                            if (hmapProductVolumePerFinal.containsKey("" + freeProductId + "^" + schemeId)) {

                            } else {
                                hmapProductVolumePerFinal.put("" + freeProductId + "^" + schemeId, "" + per);
                                exactVal = Double.parseDouble(hmapProductVolumePer.get("" + freeProductId));
                            }
                            //Double exactVal=Double.parseDouble(hmapProductVolumePer.get(""+freeProductId));
                            Double totalVal = per + exactVal;
                            hmapProductVolumePer.put("" + freeProductId, "" + totalVal);
                        }
                    }
                }

                //BenSubBucketType
                //1. Free Other Product
                //2. Discount in Percentage with other product
                //3. Discount in Amount with other product
                //4. Coupons
                //5. Free Same Product
                //6. Discount in Percentage with same product
                //7. Discount in Amount with same product
                //8. Percentage On Invoice
                //9.  Amount On Invoice
                //10. Volume Based Per KG

                int prdOqty = 0;
                if (!prdctModelArrayList.getPrdctOrderQty(savProductIdOnClicked).equals("")) {

                    int tcord=0;
                    if(!prdctModelArrayList.getPrdctOrderQtyWithTCOrder(savProductIdOnClicked).equals(""))
                        tcord=Integer.parseInt(prdctModelArrayList.getPrdctOrderQtyWithTCOrder(savProductIdOnClicked));
                    prdOqty = Integer.parseInt(prdctModelArrayList.getPrdctOrderQty(savProductIdOnClicked)) + tcord;

                   // prdOqty = Integer.parseInt(prdctModelArrayList.getPrdctOrderQty(savProductIdOnClicked)) + Integer.parseInt(prdctModelArrayList.getPrdctOrderQtyWithTCOrder(savProductIdOnClicked));
                    ;
                }
/*
                if (prdctModelArrayList.hmapProductCurrentAppliedSchemeIDandDescrAndColorCode.containsKey(savProductIdOnClicked)) {
                    prdctModelArrayList.removhmapProductCurrentAppliedSchemeIDandDescrAndColorCode(savProductIdOnClicked);


                    prdctModelArrayList.setHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(mDataSource.fnSchemeDescAndSlabDetails(savProductIdOnClicked, "" + schemeId, schemeSlabId, prdOqty));

                } else {
                    prdctModelArrayList.setHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(mDataSource.fnSchemeDescAndSlabDetails(savProductIdOnClicked, "" + schemeId, schemeSlabId, prdOqty));

                }
*/
                mDataSource.fnsavetblStoreProductAppliedSchemesBenifitsRecords(storeID, Integer.parseInt(savProductIdOnClicked), schemeId, schemeSlabId, schemeSlabBcktId, schemeSlabSubBcktVal, schemeSubBucktValType,
                        schemeSlabSubBucktType, benifitRowId, benSubBucketType,
                        freeProductId, benifitSubBucketValue, benifitMaxValue, benifitAssignedVal, benifitAssignedValueType, benifitDiscountApplied, benifitCoupnCode, per, UOM, WhatFinallyApplied, schSlbRowId, SchTypeId, StoreVisitCode, TmpInvoiceCodePDA, flgInCarton);

            if(benifitAssignedVal>0.0)

                if (flgAddOn == 1) {
                    System.out.println("Nitis AddOnSaving = " + storeID + ":" + savProductIdOnClicked + ":" + schemeId + ":" + schemeSlabId + ":" + schemeSlabBcktId + ":" + schemeSlabSubBcktVal + ":" + schemeSubBucktValType + ":" +
                            schemeSlabSubBucktType + ":" + benifitRowId + ":" + benSubBucketType + ":" +
                            freeProductId + ":" + benifitSubBucketValue + ":" + benifitMaxValue + ":" + benifitAssignedVal + ":" + benifitAssignedValueType + ":" + benifitDiscountApplied + ":" + benifitCoupnCode + ":" + per + ":" + UOM + ":" + WhatFinallyApplied + ":" + schSlbRowId + ":" + SchTypeId + ":" + TmpInvoiceCodePDA);
               /*    mDataSource.fnsavetblStoreProductAddOnSchemeApplied(storeID, Integer.parseInt(savProductIdOnClicked), schemeId, schemeSlabId, schemeSlabBcktId, schemeSlabSubBcktVal, schemeSubBucktValType,
                           schemeSlabSubBucktType, benifitRowId, benSubBucketType,
                           freeProductId, benifitSubBucketValue, benifitMaxValue, benifitAssignedVal, benifitAssignedValueType, benifitDiscountApplied, benifitCoupnCode, per, UOM, WhatFinallyApplied, schSlbRowId, SchTypeId, TmpInvoiceCodePDA);//, flgAddOn, isDiscountOnTotalAmount
*/

                    fnsavetblStoreProductAddOnSchemeApplied(storeID, Integer.parseInt(savProductIdOnClicked), schemeId, schemeSlabId, schemeSlabBcktId, schemeSlabSubBcktVal, schemeSubBucktValType,
                            schemeSlabSubBucktType, benifitRowId, benSubBucketType,
                            freeProductId, benifitSubBucketValue, benifitMaxValue, benifitAssignedVal, benifitAssignedValueType.intValue(), benifitDiscountApplied, benifitCoupnCode, per, UOM, WhatFinallyApplied, schSlbRowId, SchTypeId, StoreVisitCode, flgAddOn, isDiscountOnTotalAmount);

                }
            }


        }

        //orderBookingTotalCalc();

       /*if (alertOpens) {
           if (flagClkdButton == 1) {
               flagClkdButton = 0;
               progressTitle = ProductOrderFilterSearch.this.getResources().getString(R.string.genTermPleaseWaitNew);
               new SaveData().execute("1~3");
           } else if (flagClkdButton == 4) {
               flagClkdButton = 0;
               progressTitle = ProductOrderFilterSearch.this.getResources().getString(R.string.WhileSave);
               new SaveData().execute("1~2");
           } else if (flagClkdButton == 2) {
               flagClkdButton = 0;
               progressTitle = ProductOrderFilterSearch.this.getResources().getString(R.string.WhileWeSaveExit);
               new SaveData().execute("2");
           } else if (flagClkdButton == 3) {
               flagClkdButton = 0;
               fnSaveFilledDataToDatabase(3);
           } else if (flagClkdButton == 5) {
               flagClkdButton = 0;
               progressTitle = ProductOrderFilterSearch.this.getResources().getString(R.string.WhileSave);
               new SaveData().execute("1");
           } else if (flagClkdButton == 6) {
               flagClkdButton = 0;
               progressTitle = ProductOrderFilterSearch.this.getResources().getString(R.string.WhileReview);
               new SaveData().execute("6");
           }

       }*/
    }

    private void customAlert(ArrayList<HashMap<String, String>> arrLstHmap, ArrayList<String[]> listfreeProductQty, final int fieldFlag, final ArrayList<String> listSchemeAllData, final String alrtProductIdOnClicked) {

        alertOpens = true;
        //	 HashMap<String, String> hmapProductNameId
        final Dialog dialog = new Dialog(ProductEntryForm.this);

        //setting custom layout to dialog
        dialog.setContentView(R.layout.custom_alert);
        dialog.setTitle(ProductEntryForm.this.getResources().getString(R.string.FreeOffer));

        LinearLayout ll_radio_spinner = (LinearLayout) dialog.findViewById(R.id.ll_radio_spinner);
        //adding text dynamically


        final int imgUnChckdbtn = R.drawable.unchekedradiobutton;
        final int imgChckdbtn = R.drawable.checkedradiobutton;
        LinearLayout ll_prdctScheme;
        String titleAlrtVal = null;
        String spnr_EditText_Value;
        EditText crntEditTextValue;
        boolean completedAlrt = false;
        String alrtbodyToShow = null;
        int count = 0;
        boolean benSubBucketType7or10 = false;

        // key = productId   value=qtyselected+"~"+scheId+"~"+..............
        final HashMap<String, ArrayList<String>> hashMapSelectionFreeQty = new HashMap<String, ArrayList<String>>();

        final String[] arrayStringSpinner = new String[arrLstHmap.size()];
        final String[] arrayProduct = new String[arrLstHmap.size()];


        final TextView[] alrtPrvsPrdctSlctd = new TextView[arrLstHmap.size()];
        final Spinner[] alrtPrvsSpnrSlctd = new Spinner[arrLstHmap.size()];

        final String schemAllStringForBen7or10 = listSchemeAllData.get(0);

        final int alrtBenSubBucketTypeFor7or10 = Integer.parseInt(schemAllStringForBen7or10.split(Pattern.quote("~"))[7].toString());
        final int alrtschSlabSubBucketType = Integer.parseInt(schemAllStringForBen7or10.split(Pattern.quote("~"))[5].toString());
        for (int i = 0; i < arrLstHmap.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View viewScheme = inflater.inflate(R.layout.alrt_scheme, null);
            String[] arraySpnrVal = listfreeProductQty.get(i);
            ll_prdctScheme = (LinearLayout) viewScheme.findViewById(R.id.ll_prdctScheme);
            final TextView txtVw_scheme = (TextView) viewScheme.findViewById(R.id.txtVw_scheme);

            final String schemAllString = listSchemeAllData.get(i);
            String schemeIdFromAllVal = (schemAllString.split(Pattern.quote("~"))[0].toString());
            final int alrtBenSubBucketType = Integer.parseInt(schemAllString.split(Pattern.quote("~"))[7].toString());
            String descrptionBenType = "";

            //alrtschSlabSubBucketType
            //1. Product Quantity
            //5. Product Volume
            //2. Invoice Value
            //3. Product Lines
            //4. Product Value


            //BenSubBucketType
            //1. Free Other Product =
            //2. Discount in Percentage with other product
            //3. Discount in Amount with other product
            //4. Coupons
            //5. Free Same Product
            //6. Discount in Percentage with same product
            //7. Discount in Amount with same product
            //8. Percentage On Invoice
            //9.  Amount On Invoice
            //10. PEr Case
            //alrtbodyToShow
            //titleAlrtVal
            //arraySpnrVal[0]
            if (alrtschSlabSubBucketType == 1 || alrtschSlabSubBucketType == 5 || alrtschSlabSubBucketType == 4) {
                if (alrtBenSubBucketType == 1 || alrtBenSubBucketType == 5) {
                    benSubBucketType7or10 = true;
                    titleAlrtVal = ProductEntryForm.this.getResources().getString(R.string.DefaultFreeQty) + arraySpnrVal[0];

                    alrtbodyToShow = ProductEntryForm.this.getResources().getString(R.string.SelectActualFreeQty);

                } else if (alrtBenSubBucketType == 3 || alrtBenSubBucketType == 7) {
                    benSubBucketType7or10 = true;


                    titleAlrtVal = ProductEntryForm.this.getResources().getString(R.string.DefaultDiscValue) + arraySpnrVal[0];

                    alrtbodyToShow = ProductEntryForm.this.getResources().getString(R.string.SelectActualValue);

                } else if (alrtBenSubBucketType == 2 || alrtBenSubBucketType == 6) {
                    benSubBucketType7or10 = true;


                    titleAlrtVal = ProductEntryForm.this.getResources().getString(R.string.DefaultDisc) + arraySpnrVal[0];

                    alrtbodyToShow = ProductEntryForm.this.getResources().getString(R.string.SelectActualDisc);

                } else if (alrtBenSubBucketType == 10) {


                    benSubBucketType7or10 = true;
                    titleAlrtVal = ProductEntryForm.this.getResources().getString(R.string.DefaultFreeVal) + arraySpnrVal[0];

                    alrtbodyToShow = ProductEntryForm.this.getResources().getString(R.string.SelectActualValue);

                }
            } else {
                benSubBucketType7or10 = true;
                titleAlrtVal = ProductEntryForm.this.getResources().getString(R.string.DefaultVal) + arraySpnrVal[0];

                alrtbodyToShow = ProductEntryForm.this.getResources().getString(R.string.SelectActualValue);
            }


            txtVw_scheme.setTag(i);
            boolean ifChckdRadio = false;
            final HashMap<String, String> hmapProductNameId = arrLstHmap.get(i);

            txtVw_scheme.setText(titleAlrtVal);
            String hMapScheme = "hMapScheme";
            if (benSubBucketType7or10) {


                String[] productGivenDiscount = mDataSource.getValOfSchemeAlrt(storeID, alrtProductIdOnClicked, schemAllString.split(Pattern.quote("~"))[1].toString(), StoreVisitCode, TmpInvoiceCodePDA);

                LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View viewProduct = inflater2.inflate(R.layout.row_free_prodct_qty, null);
                viewProduct.setTag(alrtProductIdOnClicked);
                final TextView tv_prdct_name = (TextView) viewProduct.findViewById(R.id.tv_prdct_name);
                tv_prdct_name.setTag(i);
                tv_prdct_name.setText(alrtbodyToShow);
                //	tv_prdct_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


                final EditText editText_Qty = (EditText) viewProduct.findViewById(R.id.editText_Qty);
                final Spinner spnrMinMax = (Spinner) viewProduct.findViewById(R.id.spnr_Qty);

                if (fieldFlag == 1) {
                    if (spnrMinMax.getVisibility() == View.GONE) {
                        spnrMinMax.setVisibility(View.VISIBLE);
                        editText_Qty.setVisibility(View.GONE);

                    }
                    spnrMinMax.setVisibility(View.VISIBLE);
                    final ArrayAdapter adapterProduct = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpnrVal);
                    adapterProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnrMinMax.setAdapter(adapterProduct);
                    if (!productGivenDiscount[0].equals("No Data")) {
                        spnrMinMax.setSelection(Integer.valueOf(productGivenDiscount[1].toString()));
                    }


                    //dbengine.insertSchemeAlrtVal(storeID,arrayProductIdToDefault[0],"0",hmapPrdctIdPrdctName.get(arrayProductIdToDefault[0]));

                    spnrMinMax.setEnabled(true);

                    spnrMinMax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent,
                                                   View view, int position, long id) {

                            String strngPrdctCompltValue = spnrMinMax.getSelectedItem().toString();

                            arrayStringSpinner[Integer.parseInt(tv_prdct_name.getTag().toString())] = strngPrdctCompltValue;

                            for (Map.Entry<String, String> entry : hmapProductNameId.entrySet()) {

                                mDataSource.insertSchemeAlrtVal(storeID, entry.getValue(), strngPrdctCompltValue, tv_prdct_name.getText().toString(), String.valueOf(adapterProduct.getPosition(strngPrdctCompltValue)), schemAllStringForBen7or10.split(Pattern.quote("~"))[1].toString(), schemAllStringForBen7or10.split(Pattern.quote("~"))[0].toString(), StoreVisitCode, TmpInvoiceCodePDA);


                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                } else {
                    editText_Qty.setVisibility(View.VISIBLE);
                    spnrMinMax.setVisibility(View.GONE);
                    editText_Qty.setEnabled(false);
                }

                count++;


                ll_prdctScheme.addView(viewProduct);

            }

            ll_radio_spinner.addView(viewScheme);
        }


        //adding button click event
        Button dismissButton = (Button) dialog.findViewById(R.id.btnDisplay);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i;


			/*		else
					{
						for(i=0;i<arrayProduct.length;i++)
						{

							if(arrayProduct[i]!=null)
							{
							 ArrayList<String> arrayList=new ArrayList<String>();
								for(int k=0;k<arrayStringSpinner.length;k++)
								{
									if(i!=k)
									{
										if(arrayProduct[i].equals(arrayProduct[k]))
										{
											if(i<k)
											{
												arrayList.add(arrayStringSpinner[i]+"~"+(listSchemeAllData.get(i)).toString());
											}
											else
											{
												ArrayList<String> listArray=hashMapSelectionFreeQty.get(arrayProduct[k]);
												//System.out.println("Raj Abhinav ="+arrayProduct[
		//]+" index i="+i+"index k="+k);


												arrayList.add((listArray.get(k)).toString()+"~"+(listSchemeAllData.get(i)).toString());
												//sdsd
											}



										}
									}
									else
									{
										arrayList.add(arrayStringSpinner[i]+"~"+(listSchemeAllData.get(i)).toString());
									}


									hashMapSelectionFreeQty.put(arrayProduct[i], arrayList);
								}


								System.out.println("Raj Abhinav ="+hashMapSelectionFreeQty);
								condition=true;
							}
							else
							{
								condition=false;
								break;
							}
						}
					}*/


                getOrderData(alrtProductIdOnClicked);
               // orderBookingTotalCalc();
                //saveFreeProductDataWithSchemeToDatabase(hashMapSelectionFreeQty,alrtProductIdOnClicked);

                dialog.dismiss();
                alertOpens = false;


            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.show();


    }

    public void fnsavetblStoreProductAddOnSchemeApplied(String StoreID, int ProdID, int schId, int schSlabId, int schSlbBuckId, double schSlabSubBucketValue, int schSubBucketValType,
                                                        int schSlabSubBucketType, int BenifitRowID, int BenSubBucketType,
                                                        int FreeProductID, double BenifitSubBucketValue, double BenifitMaxValue, double BenifitAssignedValue, int BenifitAssignedValueType, int BenifitDiscountApplied, String BenifitCouponCode, double per, double UOM, int WhatFinallyApplied, int schSlbRowId, int SchTypeId, String pdaOrderID, int flgAddOn, int isDiscountOnTotalAmount) {

        int i = 0;
        if (tblStoreProductAddOnSchemeAppliedArrayList != null && tblStoreProductAddOnSchemeAppliedArrayList.size() > 0) {

            Iterator<TblStoreProductAddOnSchemeApplied> crunchifyIterator = tblStoreProductAddOnSchemeAppliedArrayList.iterator();
            while (crunchifyIterator.hasNext()) {
                TblStoreProductAddOnSchemeApplied tblStoreProductAddOnSchemeAppliedRecord = crunchifyIterator.next();
                if (tblStoreProductAddOnSchemeAppliedRecord != null) {
                    if (tblStoreProductAddOnSchemeAppliedRecord.getProductID() == ProdID && tblStoreProductAddOnSchemeAppliedRecord.getSchId() == schId && tblStoreProductAddOnSchemeAppliedRecord.getSchSlabId() == schSlabId) {

                        i = 1;
                        break;
                    }
                }
            }
        }
        if (i == 0) {

            TblStoreProductAddOnSchemeApplied tblStoreProductAddOnSchemeAppliedRecords = new TblStoreProductAddOnSchemeApplied();
            tblStoreProductAddOnSchemeAppliedRecords.setStoreID(StoreID);
            tblStoreProductAddOnSchemeAppliedRecords.setProductID(ProdID);
            tblStoreProductAddOnSchemeAppliedRecords.setSchId(schId);
            tblStoreProductAddOnSchemeAppliedRecords.setSchSlabId(schSlabId);
            tblStoreProductAddOnSchemeAppliedRecords.setSchSlbBuckId(schSlbBuckId);
            tblStoreProductAddOnSchemeAppliedRecords.setSchSlabSubBucketValue(schSlabSubBucketValue);
            tblStoreProductAddOnSchemeAppliedRecords.setSchSubBucketValType(schSubBucketValType);
            tblStoreProductAddOnSchemeAppliedRecords.setSchSlabSubBucketType(schSlabSubBucketType);
            tblStoreProductAddOnSchemeAppliedRecords.setBenifitRowID(BenifitRowID);
            tblStoreProductAddOnSchemeAppliedRecords.setBenSubBucketType(BenSubBucketType);
            tblStoreProductAddOnSchemeAppliedRecords.setFreeProductID(FreeProductID);
            tblStoreProductAddOnSchemeAppliedRecords.setBenifitSubBucketValue(BenifitSubBucketValue);
            tblStoreProductAddOnSchemeAppliedRecords.setBenifitMaxValue(BenifitMaxValue);
            tblStoreProductAddOnSchemeAppliedRecords.setBenifitAssignedValue(BenifitAssignedValue);
            tblStoreProductAddOnSchemeAppliedRecords.setBenifitAssignedValueType(BenifitAssignedValueType);
            tblStoreProductAddOnSchemeAppliedRecords.setBenifitDiscountApplied(BenifitDiscountApplied);
            tblStoreProductAddOnSchemeAppliedRecords.setBenifitCouponCode(BenifitCouponCode);
            tblStoreProductAddOnSchemeAppliedRecords.setPer(per);
            tblStoreProductAddOnSchemeAppliedRecords.setUOM(UOM);
            tblStoreProductAddOnSchemeAppliedRecords.setWhatFinallyApplied(WhatFinallyApplied);
            tblStoreProductAddOnSchemeAppliedRecords.setSchSlbRowId(schSlbRowId);
            tblStoreProductAddOnSchemeAppliedRecords.setSchTypeId(SchTypeId);
            tblStoreProductAddOnSchemeAppliedRecords.setDiscountPercentage(0.0);
            tblStoreProductAddOnSchemeAppliedRecords.setOrderIDPDA(pdaOrderID);
            tblStoreProductAddOnSchemeAppliedRecords.setFlgAddOn(flgAddOn);
            tblStoreProductAddOnSchemeAppliedRecords.setIsDiscountOnTotalAmount(isDiscountOnTotalAmount);
            tblStoreProductAddOnSchemeAppliedRecords.setTmpInvoiceCodePDA(pdaOrderID);

            tblStoreProductAddOnSchemeAppliedArrayList.add(tblStoreProductAddOnSchemeAppliedRecords);
        }
    }

    @Override
    public void fnShowSchemeOfProduct(String PrdID) {
        fnUpdateSchemeNameOnScehmeControl(PrdID);
    }

    @Override
    public void fnProductCalculation(String PrdID) {

        List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysSingleItem = stream(tblStoreProductMappingList).where(p -> p.getPrdNodeID() == Integer.parseInt(PrdID)).toList();
        List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
        List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
        TblInvoiceDetailsServer tblInvoiceDetailsServer = new TblInvoiceDetailsServer();
        tblInvoiceDetailsServer.setProdID(PrdID);
        tblInvoiceDetailsServer.setProductShortName(hmapPrdctIdPrdctName.get(PrdID));

        tblInvoiceDetailsServer.setProductMRP(UOMMasterDAta.get(0).getProductMRP());
        tblInvoiceDetailsServer.setProductPrice(UOMMasterDAta.get(0).getStandardRate());

        if (prdctModelArrayList.getHmapOrderQtyDataToShow()!=null && prdctModelArrayList.getHmapOrderQtyDataToShow().containsKey(PrdID)) {
            tblInvoiceDetailsServer.setOrderQty(Integer.parseInt(prdctModelArrayList.getHmapOrderQtyDataToShow().get(PrdID)));
        } else {
            tblInvoiceDetailsServer.setOrderQty(0);
        }

        int OqtyTcOrder = 0;
       /* if (!prdctModelArrayList.getPrdctOrderQtyWithTCOrder(PrdID).equals("")) {
            OqtyTcOrder=Integer.parseInt(prdctModelArrayList.getPrdctOrderQtyWithTCOrder(PrdID));
            prdctModelArrayList.setPrdctQtyWithTCOrder(""+PrdID,""+OqtyTcOrder);
        }*/
        if (hmapProductStandardRate!=null && hmapProductStandardRate.containsKey(PrdID)) {


            if (prdctModelArrayList.getHmapflgPicsOrCases()!=null && prdctModelArrayList.getHmapflgPicsOrCases().size()>0 && prdctModelArrayList.getHmapflgPicsOrCases().containsKey(PrdID) && prdctModelArrayList.getHmapflgPicsOrCases().get(PrdID).equals("1")) {

                int conversionUnit = (int) (hmapProductTblUOMMapping.get(Integer.parseInt(PrdID)).get(0).getRelConversionUnits());
                double StandardRate = Double.parseDouble(hmapProductStandardRate.get(PrdID)) * conversionUnit;
                StandardRate = Double.parseDouble(new DecimalFormat("##.##").format(StandardRate));
              //  tblInvoiceDetailsServer.setProductPrice(StandardRate);
                tblInvoiceDetailsServer.setUOMConverstionUnitQty(conversionUnit);
                tblInvoiceDetailsServer.setUOMName(hmapProductTblUOMMapping.get(Integer.parseInt(PrdID)).get(0).getUOMName());
                /*if(hmapflgTeleCallerProductQty!=null && hmapflgTeleCallerProductQty.containsKey(PrdID))
                {
                    OqtyTcOrder=Integer.parseInt(hmapflgTeleCallerProductQty.get(PrdID));
                    OqtyTcOrder=OqtyTcOrder/conversionUnit;
                    prdctModelArrayList.setPrdctQtyWithTCOrder(""+PrdID,""+OqtyTcOrder);
                }*/

            } else {
               // tblInvoiceDetailsServer.setProductPrice(Double.parseDouble(hmapProductStandardRate.get(PrdID)));
                tblInvoiceDetailsServer.setUOMConverstionUnitQty(1);
                tblInvoiceDetailsServer.setUOMName("Pcs");
              /*  if(hmapflgTeleCallerProductQty!=null && hmapflgTeleCallerProductQty.containsKey(PrdID))
                {
                    OqtyTcOrder=Integer.parseInt(hmapflgTeleCallerProductQty.get(PrdID));
                    prdctModelArrayList.setPrdctQtyWithTCOrder(""+PrdID,""+OqtyTcOrder);
                }
                else
                {
                    prdctModelArrayList.setPrdctQtyWithTCOrder(""+PrdID,"0");
                }*/

            }


        } else {
            tblInvoiceDetailsServer.setProductPrice(0.0);
        }
       /* if (!prdctModelArrayList.getPrdctOrderQtyWithTCOrder(PrdID).equals("")) {
            tblInvoiceDetailsServer.setOrderQty(tblInvoiceDetailsServer.getOrderQty() +OqtyTcOrder);
        }*/


        if (hmapLineValBfrTxAftrDscnt!=null && hmapLineValBfrTxAftrDscnt.containsKey(PrdID)) {


            tblInvoiceDetailsServer.setLineValBfrTxAftrDscnt(Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(hmapLineValBfrTxAftrDscnt.get(PrdID)))));
        } else {
            tblInvoiceDetailsServer.setLineValBfrTxAftrDscnt(0.0);
        }


      /*  if (hmapLineValBfrTxAftrDscntWithTCOrder.containsKey(PrdID)) {


            tblInvoiceDetailsServer.setLineValBfrTxAftrDscnt(Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(hmapLineValBfrTxAftrDscntWithTCOrder.get(PrdID)))));//+ tblInvoiceDetailsServer.getLineValBfrTxAftrDscnt()
        }
        else {
            tblInvoiceDetailsServer.setLineValBfrTxAftrDscnt(0.0);
        }
*/
        double schemediscount = 0.0;
        if (prdctModelArrayList.getHmapProductFlatDiscountGive() != null && prdctModelArrayList.getHmapProductFlatDiscountGive().size() > 0 && prdctModelArrayList.getHmapProductFlatDiscountGive().containsKey(PrdID)) {
            schemediscount = Double.parseDouble(prdctModelArrayList.getHmapProductFlatDiscountGive().get(PrdID));
        }
        if (prdctModelArrayList.getHmapPrdctDiscountPerUOM()!=null && prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey(PrdID)) {


            tblInvoiceDetailsServer.setDisVal(schemediscount + (Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(PrdID))*Integer.parseInt(prdctModelArrayList.getHmapOrderQtyDataToShow().get(PrdID))));
        } else {
            tblInvoiceDetailsServer.setDisVal(schemediscount);
        }


        if (prdctModelArrayList.getHmapPrdctFreeQty()!=null && prdctModelArrayList.getHmapPrdctFreeQty().containsKey(PrdID)) {
            tblInvoiceDetailsServer.setFreeQty(Integer.parseInt(prdctModelArrayList.getHmapPrdctFreeQty().get(PrdID)));
        } else {
            tblInvoiceDetailsServer.setFreeQty(0);
        }


        if (hmapProductVatTaxPerventage!=null && hmapProductVatTaxPerventage.containsKey(PrdID)) {
            tblInvoiceDetailsServer.setTaxRate(Double.parseDouble(hmapProductVatTaxPerventage.get(PrdID)));
        } else {
            tblInvoiceDetailsServer.setTaxRate(0.0);
        }

    /*    if(hmapProductTaxValueWithTCOrder.containsKey(PrdID))
        {
            tblInvoiceDetailsServer.setTaxValue(Double.parseDouble(hmapProductTaxValueWithTCOrder.get(PrdID)));
        }*/
        tblInvoiceDetailsServer.setTaxValue(Double.parseDouble(prdctModelArrayList.getPrdcTaxtAmount(PrdID)));

        if (prdctModelArrayList.getHmapPrdctOrderVal()!=null && prdctModelArrayList.getHmapPrdctOrderVal().containsKey(PrdID)) {
            tblInvoiceDetailsServer.setOrderVal(prdctModelArrayList.getPrdctOrderVal(PrdID));
        } else {
            tblInvoiceDetailsServer.setOrderVal(0.0);
        }

       /* if (hmapLineValAftrTxAftrDscnt.containsKey(PrdID)) {

            tblInvoiceDetailsServer.setOrderVal(Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(hmapLineValAftrTxAftrDscnt.get(PrdID)))));//+ tblInvoiceDetailsServer.getLineValBfrTxAftrDscnt()
        } else {
            tblInvoiceDetailsServer.setOrderVal(0.0);
        }
*/
      /*  if (prdctModelArrayList.getHmapPrdctOrderValWithTCOrder().containsKey(PrdID)) {
            tblInvoiceDetailsServer.setOrderVal(prdctModelArrayList.getPrdctOrderValWithTCOrder(PrdID));
        }*/
        ProductInfoDialog productInfoDialog = new ProductInfoDialog(ProductEntryForm.this, tblInvoiceDetailsServer,UOMMasterDAtaForCalculation.get(0).getStandardRate());
        productInfoDialog.show();
    }

    public void fnUpdateSchemeNameOnScehmeControl(String ProductIdOnClickedControl) {
        String SchemeNamesApplies = "No Scheme Applicable";
        String scIds = "0";

        if (hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl) || hmapProductAddOnSchemesList.containsKey(ProductIdOnClickedControl)) {
            String SchemeOnProduct = "";

            if (hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl)) {
                SchemeOnProduct = hmapProductRelatedSchemesList.get(ProductIdOnClickedControl).toString();
                if (hmapProductAddOnSchemesList.containsKey(ProductIdOnClickedControl)) {
                    SchemeOnProduct = SchemeOnProduct + "#" + hmapProductAddOnSchemesList.get(ProductIdOnClickedControl);
                }

            } else {

                SchemeOnProduct = hmapProductAddOnSchemesList.get(ProductIdOnClickedControl);

            }

            String[] arrSchIdsListOnProductID = SchemeOnProduct.split("#");


            for (int pSchIdsAppliCount = 0; pSchIdsAppliCount < arrSchIdsListOnProductID.length; pSchIdsAppliCount++) {
                String schOverviewDetails = arrSchIdsListOnProductID[pSchIdsAppliCount].split(Pattern.quote("!"))[0];
                String schId = schOverviewDetails.split(Pattern.quote("_"))[0];
                if (hmapSchemeIdStoreID.containsKey(schId)) {
                    if (SchemeNamesApplies.equals("No Scheme Applicable")) {
                        SchemeNamesApplies = hmapSchemeIDandDescr.get(schId);
                        scIds = schId;
                    } else {
                        SchemeNamesApplies = SchemeNamesApplies + " , " + hmapSchemeIDandDescr.get(schId);
                        scIds = scIds + "^" + schId;
                    }
                }
			 /* else
			  {
			   SchemeNamesApplies="Not Applicable Here";
			   scIds="0";
			  }*/
            }
        }


      /*  txtVw_schemeApld.setTextColor(Color.parseColor("#3f51b5"));
        SpannableString content = new SpannableString(SchemeNamesApplies);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txtVw_schemeApld.setText(SchemeNamesApplies);
        txtVw_schemeApld.setTag(scIds);*/


        if (!scIds.equals("0")) {
            arrSchId = scIds.split(Pattern.quote("^"));
            CustomAlertBoxForSchemeDetails();

        }

    }

    public void CustomAlertBoxForSchemeDetails() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        ScrollView scroll = new ScrollView(this);

        scroll.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));


        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);
        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 0);
        layout.setBackgroundColor(Color.WHITE);


        TextView tv = new TextView(this);
        tv.setText("Scheme");
        tv.setPadding(40, 10, 40, 10);
        tv.setBackgroundColor(Color.parseColor("#F1F5FE"));
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);
        tv.setTextColor(Color.parseColor("#5173B2"));


        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv1Params.bottomMargin = 5;
        int ssa = 1;

        for (int i = 0; i < arrSchId.length; i++) {
            CardView cardView = new CardView(ProductEntryForm.this);
            LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            cardView.setLayoutParams(cardViewParams);
            cardView.setRadius(10f);
            cardView.setCardElevation(15f);
            cardView.setContentPadding(20, 20, 20, 20);
            ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
            cardViewMarginParams.setMargins(20, 10, 20, 10);
            cardView.requestLayout();  //Dont forget this line

            LinearLayout ChildViewDynamic = new LinearLayout(ProductEntryForm.this);
            ChildViewDynamic.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 25f));
            ChildViewDynamic.setOrientation(LinearLayout.VERTICAL);
            ChildViewDynamic.setWeightSum(25f);

            TextView tv1 = new TextView(this);
            tv1.setTextColor(Color.BLACK);
            tv1.setBackgroundColor(Color.parseColor("#FFFEFC"));
            SchemeDesc = hmapSchemeIDandDescr.get(arrSchId[i]);
            //tv1.setText("Scheme Name :"+SchemeDesc);
            tv1.setTextColor(Color.parseColor("#303F9F"));
            tv1.setTypeface(null, Typeface.BOLD);
            String mystring = (ssa++) + ". Scheme Name : " + SchemeDesc;
            SpannableString content = new SpannableString(mystring);
//            content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
            tv1.setText(content);

            ChildViewDynamic.addView(tv1, tv1Params);


            String AllSchemeSlabID[] = mDataSource.fnGetAllSchSlabbasedOnSchemeID(arrSchId[i]);

            // below two line for Testing,  please comment below two line for live
            // hmapSchemeSlabIdSlabDes.put("62", "Buy [ 12 units from (Series - [ Go Fresh Cream, Go UHT Milk, Gowardhan Milk rich, Gowardhan Skim Milk Powder, GO Badam Milk, GO Butter Milk, Gowardhan Butter) AND 1 lines among these (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 Total Net value ] OR [5 Kg volume of Products from (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 value of Products from (Series - [ Go UHT Milk, GO Butter Milk)]");
            // hmapSchemeSlabIdSlabDes.put("63", "Buy [ 12 units from (Series - [ Go Fresh Cream, Go UHT Milk, Gowardhan Milk rich, Gowardhan Skim Milk Powder, GO Badam Milk, GO Butter Milk, Gowardhan Butter) AND 1 lines among these (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 Total Net value ] OR [5 Kg volume of Products from (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 value of Products from (Series - [ Go UHT Milk, GO Butter Milk)]");


            // hmapSchmeSlabIdSlabDes

            // hmapSchemeSlabIdBenifitDes.put("62", "GET [ 2 units from Same Product - [Exceptions -  3 units for Old Buyer] AND  Coupon No. 0002 AND 2% discount on Invoice value  - [Exceptions -  3% for Old Buyer, 4% for New Buyer]]");
            // hmapSchemeSlabIdBenifitDes.put("63", "GET [ 2 units from Same Product - [Exceptions -  3 units for Old Buyer] AND  Coupon No. 0002 AND 2% discount on Invoice value  - [Exceptions -  3% for Old Buyer, 4% for New Buyer]]");

            int k = 0;
            for (int j = 0; j < AllSchemeSlabID.length; j++)   // change 3 into SchmSlabId.length which i got hmapSchmSlabIdSchmID (Length of SchmSlabId)
            {

                k = j + 1;

                // System.out.println("List of all SchemeSlabID :"+AllSchemeSlabID[j]);

                TextView tv2 = new TextView(this);
                tv2.setTextColor(Color.BLACK);
                tv2.setBackgroundColor(Color.parseColor("#FFFEFC"));
                tv2.setText("Slab " + k + "  :" + hmapSchmeSlabIdSlabDes.get(AllSchemeSlabID[j])); // It is for Live
                //  tv2.setText("Slab "+k+"  :"+hmapSchemeSlabIdSlabDes.get("62"));  // It is for Testing
                tv2.setTextColor(Color.parseColor("#E65100"));

                ChildViewDynamic.addView(tv2, tv1Params);


                TextView tv3 = new TextView(this);
                tv3.setTextColor(Color.BLACK);
                tv3.setBackgroundColor(Color.parseColor("#FFFEFC"));
                tv3.setText("Benefit :" + hmapSchmeSlabIdBenifitDes.get(AllSchemeSlabID[j]));  // It is for Live
                // tv3.setText("Benifit :"+hmapSchemeSlabIdBenifitDes.get("62"));   // It is for Testing
                tv3.setTextColor(Color.parseColor("#3BA1B3"));

                ChildViewDynamic.addView(tv3, tv1Params);
            }


            cardView.addView(ChildViewDynamic, tv1Params);
            layout.addView(cardView);
        }

        scroll.addView(layout);


        alertDialogBuilder.setView(scroll);
        alertDialogBuilder.setCustomTitle(tv);
        alertDialogBuilder.setIcon(R.drawable.info_ico);
        alertDialogBuilder.setCancelable(false);


        // Setting Positive "Yes" Button
        alertDialogBuilder.setPositiveButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();


        try {
            alertDialog.show();
        } catch (Exception e) {
            // WindowManager$BadTokenException will be caught and the app would
            // not display the 'Force Close' message
            e.printStackTrace();
        }


    }


    @Override
    public void fnExpandCategory(int CategoryID) {
        // tblStoreProductMappingListForDisplay.clear();
        if (hmapFilterProductList != null) {
            hmapFilterProductList.clear();
        }

      /*  tblStoreProductMappingListtemp.clear();


        List<String> searchStringsArray = new ArrayList<>();
        tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getCatID()==CategoryID).toList();
*/
       /* Iterator<TblStoreProductMappingForDisplay> crunchifyIteratornew = tblStoreProductMappingListtemp.iterator();
        if (tblStoreProductMappingListtemp != null && tblStoreProductMappingListtemp.size() > 0) {


            while (crunchifyIteratornew.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIteratornew.next();
                tblStoreProductMappingListForDisplay.add(tblStoreProductMappingRecord);
            }
        }*/

        fnMarkFirstProductCategoryRowVisibleExpand(CategoryID);
        orderAdapter.updateList(tblStoreProductMappingListForDisplay);

    }

    @Override
    public void fnCollapseCategory(int CategoryID) {
        // tblStoreProductMappingListForDisplay.clear();
        if (hmapFilterProductList != null) {
            hmapFilterProductList.clear();
        }

      /*  tblStoreProductMappingListtemp.clear();


        List<String> searchStringsArray = new ArrayList<>();
        tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getCatID()==CategoryID).toList();
*/
       /* Iterator<TblStoreProductMappingForDisplay> crunchifyIteratornew = tblStoreProductMappingListtemp.iterator();
        if (tblStoreProductMappingListtemp != null && tblStoreProductMappingListtemp.size() > 0) {


            while (crunchifyIteratornew.hasNext()) {
                TblStoreProductMappingForDisplay tblStoreProductMappingRecord = crunchifyIteratornew.next();
                tblStoreProductMappingListForDisplay.add(tblStoreProductMappingRecord);
            }
        }*/

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


    public void searchForFoucusedBrand(String filterSearchText, String ctgrytext, int filterType, int ParentID) {
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
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase()) && p.getFlgFocusbrand() == 1).toList();
            } else {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase()) && p.getCatID() == ParentID && p.getFlgFocusbrand() == 1).toList();
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





   /* hmapFilterProductList = mDataSource.getFileredProductListMapPRoductFilter(filterSearchText.trim(), StoreCurrentStoreType, ctgrytext, filterType, ParentID);

        if (hmapFilterProductList.size() > 0) {
            String[] listProduct = new String[hmapFilterProductList.size()];
            int index = 0;
            for (Map.Entry<String, String> entry : hmapFilterProductList.entrySet()) {
                listProduct[index] = entry.getKey() + "^" + entry.getValue();
                index++;
            }



        } else {
            String[] listProduct = new String[hmapFilterProductList.size()];
            orderAdapter = new OrderAdapterProdcutEntry(ProductEntryForm.this, storeID, listProduct, hmapFilterProductList, hmapProductStandardRate, hmapProductMRP, hmapProductIdStock, hmapProductIdLastStock, hampGetLastProductExecution, hmapDistPrdctStockCount, hmapVanPrdctStockCount, prdctModelArrayList, 0, hmapPerBaseQty, hmapProductTblUOMMapping, hmapProductLODQty, hmapProductSuggestedQty, hmapProductImg, hmapProductRelatedSchemesList, flgIsAnySchemeMappedAgainstStore,tblStoreProductMappingListForDisplay);
            rv_prdct_detal.setAdapter(orderAdapter);
            rv_prdct_detal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            allMessageAlert(ProductEntryForm.this.getResources().getString(R.string.AlertFilter));
        }*/
        fnMarkFirstProductCategoryRowVisible();
        orderAdapter.updateList(tblStoreProductMappingListForDisplay);

    }


    public void searchForTeleCallerProducts(String filterSearchText, String ctgrytext, int filterType, int ParentID) {
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
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase()) && p.getFlgTeleCallerProduct() == 1).toList();
            } else {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingList).where(p -> p.getSearchField().toLowerCase().contains(filterSearchText.toLowerCase()) && p.getCatID() == ParentID && p.getFlgTeleCallerProduct() == 1).toList();
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





   /* hmapFilterProductList = mDataSource.getFileredProductListMapPRoductFilter(filterSearchText.trim(), StoreCurrentStoreType, ctgrytext, filterType, ParentID);

        if (hmapFilterProductList.size() > 0) {
            String[] listProduct = new String[hmapFilterProductList.size()];
            int index = 0;
            for (Map.Entry<String, String> entry : hmapFilterProductList.entrySet()) {
                listProduct[index] = entry.getKey() + "^" + entry.getValue();
                index++;
            }



        } else {
            String[] listProduct = new String[hmapFilterProductList.size()];
            orderAdapter = new OrderAdapterProdcutEntry(ProductEntryForm.this, storeID, listProduct, hmapFilterProductList, hmapProductStandardRate, hmapProductMRP, hmapProductIdStock, hmapProductIdLastStock, hampGetLastProductExecution, hmapDistPrdctStockCount, hmapVanPrdctStockCount, prdctModelArrayList, 0, hmapPerBaseQty, hmapProductTblUOMMapping, hmapProductLODQty, hmapProductSuggestedQty, hmapProductImg, hmapProductRelatedSchemesList, flgIsAnySchemeMappedAgainstStore,tblStoreProductMappingListForDisplay);
            rv_prdct_detal.setAdapter(orderAdapter);
            rv_prdct_detal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            allMessageAlert(ProductEntryForm.this.getResources().getString(R.string.AlertFilter));
        }*/
        fnMarkFirstProductCategoryRowVisible();
        orderAdapter.updateList(tblStoreProductMappingListForDisplay);

    }

    public class NextActivityCustomDialog extends Dialog {

        RadioGroup radiogroupCloseCall;

        EditText commenttext;
        Button bt_save_and_continue;
        ImageView imgCncl;

        private AppDataSource mDataSource;

        public NextActivityCustomDialog(@NonNull Context context) {
            super(context);
            mDataSource = new AppDataSource(context);

            // bt_save_and_continueOutStation.setVisibility(View.GONE);

        }


        @Override
        public void onBackPressed() {
            //super.onBackPressed();
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.popup_close_call_layout);

         /*   View view = View.inflate(getContext(), R.layout.popup_close_call_layout, null);
           ButterKnife.bind(this, view);*/
            //	ButterKnife.bind(NextActivityCustomDialog.this);
            setCancelable(false);
            radiogroupCloseCall = (RadioGroup) findViewById(R.id.radiogroupCloseCall);
            commenttext = (EditText) findViewById(R.id.commenttext);
            bt_save_and_continue = (Button) findViewById(R.id.bt_save_and_continue);
            imgCncl = (ImageView) findViewById(R.id.imgCncl);
            imgCncl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            if (!selectedCloaseCallComment.equals("NA")) {
                commenttext.setText(selectedCloaseCallComment);
            }


            bt_save_and_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedCloaseCallOptionID == 0) {
                        showAlertSingleButtonError("Please select one option for no Order.");
                    } else {
                        if (commenttext != null && !TextUtils.isEmpty(commenttext.getText().toString().trim())) {
                            selectedCloaseCallComment = commenttext.getText().toString().trim();
                        }
                        mDataSource.fnUpdateFlgOrderComment("NA", storeID, StoreVisitCode);
                        mDataSource.fnUpdateFlgCloseCallIDComment(selectedCloaseCallOptionID, selectedCloaseCallComment, storeID, StoreVisitCode);

                        int Outstat=1;
                        TransactionTableDataDeleteAndSaving(Outstat);
                        InvoiceTableDataDeleteAndSaving(Outstat, flgTransferStatus);
                        dismiss();
                        fnCallSubmitProcess(3);
                    }
                }
            });

            radiogroupCloseCall.setOrientation(LinearLayout.VERTICAL);
            //
            if (hmapCloseCallOption != null && hmapCloseCallOption.size() > 0) {
                Set set2 = hmapCloseCallOption.entrySet();
                Iterator iterator = set2.iterator();
                while (iterator.hasNext()) {
                    Map.Entry me2 = (Map.Entry) iterator.next();
                    RadioButton rdbtn = new RadioButton(getContext());
                    rdbtn.setId(Integer.parseInt(me2.getValue().toString()));
                    rdbtn.setText(me2.getKey().toString());
                    rdbtn.setTag(me2.getValue().toString());
                    if (selectedCloaseCallOptionID == Integer.parseInt(me2.getValue().toString())) {
                        rdbtn.setChecked(true);
                    }
                    // rdbtn.setOnClickListener(this);
                    rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
                            selectedCloaseCallOptionID = Integer.parseInt(cb.getTag().toString());
                        }
                    });
                    radiogroupCloseCall.addView(rdbtn);
                }


            }


        }

    }

    public void fnCallSubmitProcess(int valBtnClickedFrom) {
        AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(ProductEntryForm.this);
        alertDialogSubmitConfirm.setTitle(getText(R.string.genTermNoDataConnection));
        alertDialogSubmitConfirm.setMessage(getText(R.string.submitConfirmAlertWithNoOrders));
        alertDialogSubmitConfirm.setCancelable(false);

        alertDialogSubmitConfirm.setNeutralButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                butClickForGPS = 3;
                //mDataSource.open();
                if(sharedPrefReport!=null && sharedPrefReport.contains("customCategoryfilter"))
                {
                    SharedPreferences.Editor editor = sharedPrefReport.edit();
                    editor.clear();
                    editor.commit();
                }
                //This is commented for because Store Lat Long checking is already done after sotre selection to verifiy sotre lat longs
               //if ((mDataSource.PrevLocChk(storeID.trim(), StoreVisitCode))) {

                    FullSyncDataNow task = new FullSyncDataNow(ProductEntryForm.this);
                    task.execute();
               /* } else {

                    LocationRetreivingGlobal locationRetreivingGlobal = new LocationRetreivingGlobal();
                    locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(ProductEntryForm.this, true, true, 20, 0);


                }*/


            }
        });

        alertDialogSubmitConfirm.setNegativeButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);

        AlertDialog alert = alertDialogSubmitConfirm.create();

        alert.show();
    }


    private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialogGetStores;

        public FullSyncDataNow(ProductEntryForm activity) {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage(ProductEntryForm.this.getResources().getString(R.string.SubmittingInvoiceOrderDetailsNoOrders));
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();


        }

        @Override

        protected Void doInBackground(Void... params) {

            int Outstat = 1;
            flgTransferStatus = 1;
            InvoiceTableDataDeleteAndSaving(Outstat, flgTransferStatus);
          //  mDataSource.deleteOldStoreInvoice(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);
            Double cntInvoceValue = mDataSource.fetch_Store_InvValAmount(storeID, TmpInvoiceCodePDA);
            cntInvoceValue = Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));

            Outstat = 3;
            long syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String StampEndsTime = TimeUtils.getNetworkDateTime(ProductEntryForm.this, TimeUtils.DATE_TIME_FORMAT);
            mDataSource.UpdateStoreEndVisittblStoreOrderVisitDayActivity(storeID, StampEndsTime, LastVisitDetails.TempStoreVisitCode);
            if (Outstat == 3) {
                mDataSource.UpdatetblStoreOrderVisitDayActivitySstat(storeID, 3, StoreVisitCode, LastVisitDetails.TempStoreVisitCode);

                mDataSource.fnUpdateFlgCloseCallIDComment(selectedCloaseCallOptionID, selectedCloaseCallComment, storeID, StoreVisitCode);
                mDataSource.fnUpdateIsNewStoreDataCompleteSavedStoreVisitMaster(storeID,1);
                if (strFinalAllotedInvoiceIds.equals("NA")) {
                    mDataSource.fnTransferDataFromTempToPermanent(storeID, StoreVisitCode, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls);

                    int chkflgTransferStatus = mDataSource.fnCheckflgTransferStatus(storeID, StoreVisitCode, TmpInvoiceCodePDA);
                    if (chkflgTransferStatus == 2) {
                        mDataSource.deleteOldStoreInvoice(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);
                        mDataSource.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsactionBeforPermanentTransfer(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);
                        mDataSource.UpdateStoreVisitMStrTable(storeID, 3, StoreVisitCode);
                        mDataSource.fnupdatetblStoreLastDeliveryNoteNumber();



                    } else {
                        mDataSource.deleteMasterTblFromParmanentInvoiceTables(storeID, TmpInvoiceCodePDA);
                    }

                }


            }

            mDataSource.UpdateStoreVisitWiseTables(storeID, 3, StoreVisitCode, TmpInvoiceCodePDA);


            mDataSource.UpdateStoreEndVisit(storeID, StampEndsTime);
            mDataSource.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(), "3", TmpInvoiceCodePDA, TmpInvoiceCodePDA);
            mDataSource.UpdateStoreStoreReturnDetail(storeID.trim(), "3", TmpInvoiceCodePDA, TmpInvoiceCodePDA);
            mDataSource.UpdateStoreFlag(storeID.trim(), 3);
            mDataSource.UpdateStoreOtherMainTablesFlag(storeID.trim(), 3, TmpInvoiceCodePDA, TmpInvoiceCodePDA);

            Double CollectionAmtAgainstStore = mDataSource.fnTotCollectionAmtAgainstStore(storeID.trim(), TmpInvoiceCodePDA, StoreVisitCode);

            mDataSource.updateStoreQuoteSubmitFlgInStoreMstr(storeID.trim(), 0, StoreVisitCode);
            if (mDataSource.checkCountIntblStoreSalesOrderPaymentDetails(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA) == 0) {
                String strDefaultPaymentStageForStore = mDataSource.fnGetDefaultStoreOrderPAymentDetails(storeID);
                if (!strDefaultPaymentStageForStore.equals("")) {
                    //mDataSource.open();
                    mDataSource.fnsaveStoreSalesOrderPaymentDetails(storeID, TmpInvoiceCodePDA, strDefaultPaymentStageForStore, "3", TmpInvoiceCodePDA);
                    //mDataSource.close();
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
            if (pDialogGetStores!=null && pDialogGetStores.isShowing()) {
                pDialogGetStores.dismiss();
            }
            showOrderSubmissionAlert();

        }
    }


    public void showOrderSubmissionAlert() {
        AlertDialog.Builder alertDialogOrderSubmission = new AlertDialog.Builder(this);
        alertDialogOrderSubmission.setTitle(R.string.genTermInformation);
        alertDialogOrderSubmission.setMessage(R.string.DataSucc);
        alertDialogOrderSubmission.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            StoreSelection.flgChangeRouteOrDayEnd = 0;
                            String presentRoute = mDataSource.GetActiveRouteID();

                            Intent mMyServiceIntent = new Intent(ProductEntryForm.this, SyncJobService.class);  //is any of that needed?  idk.
                            mMyServiceIntent.putExtra("routeID", presentRoute);//
                            mMyServiceIntent.putExtra("storeID", storeID);
                            mMyServiceIntent.putExtra("whereTo", "Regular");//
                            mMyServiceIntent.putExtra("StoreVisitCode", StoreVisitCode);//
                            mMyServiceIntent.putExtra("tmpInvoicePDACode", TmpInvoiceCodePDA);//

                            SyncJobService.enqueueWork(ProductEntryForm.this, mMyServiceIntent);


                            Intent prevP2 = new Intent(ProductEntryForm.this, StoreSelection.class);
                            //Location_Getting_Service.closeFlag = 0;
                            prevP2.putExtra("imei", imei);
                            prevP2.putExtra("userDate", date);
                            prevP2.putExtra("pickerDate", pickerDate);
                            prevP2.putExtra("rID", presentRoute);
                            startActivity(prevP2);
                            finish();

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                });
        alertDialogOrderSubmission.setIcon(R.drawable.info_icon);
        AlertDialog alert = alertDialogOrderSubmission.create();
        alert.show();

    }

    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy,
                                    String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy,
                                    String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {
        checkHighAccuracyLocationMode(ProductEntryForm.this);
        if(FusedAccuracy==null)
        {
            FusedAccuracy="0";
        }
        if(FusedLat==null)
        {
            FusedLat="0.0";
        }
        if(FusedLong==null)
        {
            FusedLong="0.0";
        }
        if(NetwAccuracy==null)
        {
            NetwAccuracy="0";
        }
        if(NetwLong==null)
        {
            NetwLong="0.0";
        }
        if(NetwLat==null)
        {
            NetwLat="0.0";
        }
        if(GpsAccuracy==null)
        {
            GpsAccuracy="0";
        }
        if(GpsLong==null)
        {
            GpsLong="0.0";
        }
        if(fnLati==null)
        {
            fnLati="0.0";
        }
        if(fnLongi==null)
        {
            fnLongi="0.0";
        }
        if(finalAccuracy==null)
        {
            finalAccuracy="0";
        }
        if(GpsLat==null)
        {
            GpsLat="0.0";
        }
        mDataSource.UpdateStoreActualLatLongi(storeID, String.valueOf(fnLati), String.valueOf(fnLongi), "" + finalAccuracy, fnAccurateProvider, flgLocationServicesOnOffOrderReview, flgGPSOnOffOrderReview, flgNetworkOnOffOrderReview, flgFusedOnOffOrderReview, flgInternetOnOffWhileLocationTrackingOrderReview, flgRestartOrderReview, flgStoreOrderOrderReview, StoreVisitCode, VisitTimeInSideStore);
        mDataSource.saveLatLngToTxtFile(storeID,StoreVisitCode,String.valueOf(fnLati), String.valueOf(fnLongi), "" + finalAccuracy, fnAccurateProvider, GpsLat, GpsLong, GpsAccuracy, NetwLat, NetwLong, NetwAccuracy, FusedLat, FusedLong, FusedAccuracy, 3, "0", fnAddress, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt
                , FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt);
        if (!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy))) {

            fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy));
            FullSyncDataNow task = new FullSyncDataNow(ProductEntryForm.this);
            task.execute();
        } else {
            countSubmitClicked++;
            if (countSubmitClicked == 1 && flgOrderType == 1) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductEntryForm.this);

                // Setting Dialog Title
                alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
                alertDialog.setIcon(R.drawable.error_info_ico);
                alertDialog.setCancelable(false);
                // Setting Dialog Message
                alertDialog.setMessage(ProductEntryForm.this.getResources().getString(R.string.AlertSameLoc));

                // On pressing Settings button
                alertDialog.setPositiveButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        countSubmitClicked++;
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

                // Showing Alert Message
                alertDialog.show();


            } else {

                FullSyncDataNow task = new FullSyncDataNow(ProductEntryForm.this);
                task.execute();

            }


        }
    }

    public void checkHighAccuracyLocationMode(Context context) {
        int locationMode = 0;
        String locationProviders;

        flgLocationServicesOnOffOrderReview = 0;
        flgGPSOnOffOrderReview = 0;
        flgNetworkOnOffOrderReview = 0;
        flgFusedOnOffOrderReview = 0;
        flgInternetOnOffWhileLocationTrackingOrderReview = 0;

        if (isGooglePlayServicesAvailable()) {
            flgFusedOnOffOrderReview = 1;
        }
        if (isOnline()) {
            flgInternetOnOffWhileLocationTrackingOrderReview = 1;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Equal or higher than API 19/KitKat
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
                if (locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 1;
                    flgNetworkOnOffOrderReview = 1;
                    //flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 0;
                    flgNetworkOnOffOrderReview = 1;
                    // flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 1;
                    flgNetworkOnOffOrderReview = 0;
                    //flgFusedOnOff=0;
                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            //Lower than API 19
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


            if (TextUtils.isEmpty(locationProviders)) {
                locationMode = Settings.Secure.LOCATION_MODE_OFF;

                flgLocationServicesOnOffOrderReview = 0;
                flgGPSOnOffOrderReview = 0;
                flgNetworkOnOffOrderReview = 0;
                // flgFusedOnOff = 0;
            }
            if (locationProviders.contains(LocationManager.GPS_PROVIDER) && locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                flgLocationServicesOnOffOrderReview = 1;
                flgGPSOnOffOrderReview = 1;
                flgNetworkOnOffOrderReview = 1;
                //flgFusedOnOff = 0;
            } else {
                if (locationProviders.contains(LocationManager.GPS_PROVIDER)) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 1;
                    flgNetworkOnOffOrderReview = 0;
                    // flgFusedOnOff = 0;
                }
                if (locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 0;
                    flgNetworkOnOffOrderReview = 1;
                    //flgFusedOnOff = 0;
                }
            }
        }

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

    public boolean checkLastFinalLoctionIsRepeated(String currentLat, String currentLong, String currentAccuracy) {
        boolean repeatedLoction = false;

        try {

            String chekLastGPSLat = "0";
            String chekLastGPSLong = "0";
            String chekLastGpsAccuracy = "0";
            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists()) {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew = "FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder, txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory() + "/" + CommonInfo.FinalLatLngJsonFile + "/" + txtFileNamenew;

            // If file does not exists, then create it
            if (file.exists()) {
                StringBuffer buffer = new StringBuffer();
                String myjson_stampiGPSLastLocation = "";
                StringBuffer sb = new StringBuffer();
                BufferedReader br = null;

                try {
                    br = new BufferedReader(new FileReader(file));

                    String temp;
                    while ((temp = br.readLine()) != null)
                        sb.append(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close(); // stop reading
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                myjson_stampiGPSLastLocation = sb.toString();

                JSONObject jsonObjGPSLast = new JSONObject(myjson_stampiGPSLastLocation);
                JSONArray jsonObjGPSLastInneralues = jsonObjGPSLast.getJSONArray("GPSLastLocationDetils");

                String StringjsonGPSLastnew = jsonObjGPSLastInneralues.getString(0);
                JSONObject jsonObjGPSLastnewwewe = new JSONObject(StringjsonGPSLastnew);

                chekLastGPSLat = jsonObjGPSLastnewwewe.getString("chekLastGPSLat");
                chekLastGPSLong = jsonObjGPSLastnewwewe.getString("chekLastGPSLong");
                chekLastGpsAccuracy = jsonObjGPSLastnewwewe.getString("chekLastGpsAccuracy");

                if (currentLat != null) {
                    if (currentLat.equals(chekLastGPSLat) && currentLong.equals(chekLastGPSLong) && currentAccuracy.equals(chekLastGpsAccuracy)) {
                        repeatedLoction = true;
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repeatedLoction;

    }

    public void fnCreateLastKnownFinalLocation(String chekLastGPSLat, String chekLastGPSLong, String chekLastGpsAccuracy) {

        try {

            JSONArray jArray = new JSONArray();
            JSONObject jsonObjMain = new JSONObject();


            JSONObject jOnew = new JSONObject();
            jOnew.put("chekLastGPSLat", chekLastGPSLat);
            jOnew.put("chekLastGPSLong", chekLastGPSLong);
            jOnew.put("chekLastGpsAccuracy", chekLastGpsAccuracy);


            jArray.put(jOnew);
            jsonObjMain.put("GPSLastLocationDetils", jArray);

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists()) {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew = "FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder, txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory() + "/" + CommonInfo.FinalLatLngJsonFile + "/" + txtFileNamenew;


            // If file does not exists, then create it
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            FileWriter fw;
            try {
                fw = new FileWriter(file.getAbsoluteFile());

                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(jsonObjMain.toString());

                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
				 /*  file=contextcopy.getFilesDir();
				//fileOutputStream=contextcopy.openFileOutput("FinalGPSLastLocation.txt", Context.MODE_PRIVATE);
				fileOutputStream.write(jsonObjMain.toString().getBytes());
				fileOutputStream.close();*/
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
    }


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
            viewContactSection.setVisibility(View.VISIBLE);
            if (mDataSource.fnCheckStoreMappedInStoreListForUpdateMstr(storeID) == 1) {
                viewContactSection.setVisibility(View.GONE);
                tvContactNoCorrection.setText("Contact Number Verified");
                tvContactNoCorrection.setTextColor(Color.parseColor("#74C543"));
            }

            btnEditContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ready4GetLoc = new Intent(ProductEntryForm.this, StoreEditActivityContactLocation.class);
                    ready4GetLoc.putExtra("storeID", storeID);
                    ready4GetLoc.putExtra("bck", 0);
                    startActivity(ready4GetLoc);
                    //finish();
                }
            });
        } else {
            viewContactSection.setVisibility(View.GONE);
        }


    }




    /*@Override
    public void fnCallUpdateLocation() {
        inflateUpdateContactSection();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
       /* if (StoreEditActivityContactLocation.flgCallActivityContactLocation == 1) {
            inflateUpdateContactSection();
            StoreEditActivityContactLocation.flgCallActivityContactLocation = 0;
            if (mDataSource.fnCheckStoreIntblStoreListForUpdateMstr(storeID) == 1) {

                if (mDataSource.fnCheckStoreMappedInStoreListForUpdateMstr(storeID) == 1) {
                    if (OnResumeCheckWithNoOrderClick == 1) {
                        new NextActivityCustomDialog(ProductEntryForm.this).show();
                        OnResumeCheckWithNoOrderClick = 0;
                    }
                }
            }
        }*/
    }

    public void fnShowAllSchemes() {
        String SchemeNamesApplies = "No Scheme Applicable";
        String scIds = "0";
        HashMap<String, String> map = new HashMap<String, String>(hmapSchemeIdStoreID);
        Set set2 = map.entrySet();
        Iterator iterator = set2.iterator();
        while (iterator.hasNext()) {
            Map.Entry me2 = (Map.Entry) iterator.next();
            String SchID = me2.getKey().toString();

            if (hmapSchemeIdStoreID.containsKey(SchID)) {
                if (SchemeNamesApplies.equals("No Scheme Applicable")) {
                    SchemeNamesApplies = hmapSchemeIDandDescr.get(SchID);
                    scIds = SchID;
                } else {
                    SchemeNamesApplies = SchemeNamesApplies + " , " + hmapSchemeIDandDescr.get(SchID);
                    scIds = scIds + "^" + SchID;
                }
            }


        }


        if (!scIds.equals("0")) {
            arrSchId = scIds.split(Pattern.quote("^"));
            CustomAlertBoxForSchemeDetails();

        } else {
            showAlertSingleButtonInfo("No Schemes mapped to this sotre.");
        }

    }


    public void searchForProdcutsBasedOnVariousConditions(LinkedHashMap<String, Integer> hmapCatSelecteListForFiter, int flgRepSKUnew, int flgPrioritySKUnew, int flgSchemePrdnew) {
        tblStoreProductMappingListForDisplay.clear();
        if (hmapFilterProductList != null) {
            hmapFilterProductList.clear();
        }
        tblStoreProductMappingListtemp.clear();

        List<TblStoreProductMappingForDisplay> tblStoreProductMappingListForFilterOnly = new ArrayList<TblStoreProductMappingForDisplay>();

        ArrayList<TblProductTypeMasterForRetriving> tblProductTypeMasterForRetrivingListForFilterOnly = new ArrayList<TblProductTypeMasterForRetriving>();
        for (TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingForFilter : tblProductTypeMasterForRetrivingList) {
            ArrayList<TblOnlyCategoryMasterForRetriving> tblProductTypeMasterForRetrivingListChildReords = tblProductTypeMasterForRetrivingForFilter.getTblOnlyCategoryMasterForRetrivingList();
            if (tblProductTypeMasterForRetrivingListChildReords != null && tblProductTypeMasterForRetrivingListChildReords.size() > 0) {
                for (TblOnlyCategoryMasterForRetriving tblChildRecord : tblProductTypeMasterForRetrivingListChildReords) {
                    if(tblChildRecord.getSelected())
                    {
                        for (TblStoreProductMappingForDisplay tblStoreProductMappingDisplaynew : tblStoreProductMappingList) {
                            if (tblStoreProductMappingDisplaynew.getUOMType().toLowerCase().equals(tblChildRecord.getCategory().toString().toLowerCase()) && tblChildRecord.getParentID()==tblStoreProductMappingDisplaynew.getCatID()) {
                                tblStoreProductMappingListtemp.add(tblStoreProductMappingDisplaynew);
                            }
                        }
                    }

                }
            }
        }

        /*for (TblStoreProductMappingForDisplay tblStoreProductMappingDisplaynew : tblStoreProductMappingList) {
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

        }*/
        if (flgRepSKUnew == 1) {
            tblStoreProductMappingListtemp = stream(tblStoreProductMappingListtemp).where(p -> p.getFlgReplenishmentSKU() == 0).toList();
            tblStoreProductMappingListtemp.addAll(stream(tblStoreProductMappingList).where(p -> p.getFlgReplenishmentSKU() == 1).toList());
        }
        if (flgPrioritySKUnew == 1) {
            tblStoreProductMappingListtemp = stream(tblStoreProductMappingListtemp).where(p -> p.getFlgFocusbrand() == 0).toList();
            tblStoreProductMappingListtemp.addAll(stream(tblStoreProductMappingList).where(p -> p.getFlgFocusbrand() == 1).toList());
        }
        if (flgIsAnySchemeMappedAgainstStore == 1) {
            if (flgSchemePrdnew == 1) {
                tblStoreProductMappingListtemp = stream(tblStoreProductMappingListtemp).where(p -> p.getFlgProductWithScheme() == 0).toList();
                tblStoreProductMappingListtemp.addAll(stream(tblStoreProductMappingList).where(p -> p.getFlgProductWithScheme() == 1).toList());
            }
        }
        tblStoreProductMappingListtemp = stream(tblStoreProductMappingListtemp).orderBy(p -> p.getCatID()).toList();


        fnMarkFirstProductCategoryRowVisible();
        orderAdapter.updateList(tblStoreProductMappingListForDisplay);

    }


    public void showAlertForEveryOneForPhoneNumberValidation(String msg) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ProductEntryForm.this);
        alertDialogNoConn.setTitle(getText(R.string.genTermNoDataConnection));
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Intent ready4GetLoc = new Intent(ProductEntryForm.this, StoreEditActivityContactLocation.class);
                ready4GetLoc.putExtra("storeID", storeID);
                startActivity(ready4GetLoc);

            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }


    public void orderBookingTotalCalcOnLoadTCCalculation() {


        Double StandardRate = 0.00;
        Double StandardRateBeforeTax = 0.00;

        Double ActualRateAfterDiscountBeforeTax = 0.00;
        Double DiscountAmount = 0.00;
        Double ActualTax = 0.00;
        Double ActualRateAfterDiscountAfterTax = 0.00;


        Double TotalFreeQTY = 0.00;
        Double TotalProductLevelDiscount = 0.00;
        Double TotalOrderValBeforeTax = 0.00;

        Double TotTaxAmount = 0.00;
        Double TotOderValueAfterTax = 0.00;

        Double TotTaxAmountWithTCOrder = 0.00;
        Double TotalOrderValBeforeTaxWithTCOrder = 0.00;


        if (hmapflgTeleCallerProductQty != null) {

            for (Map.Entry<String, String> entry : hmapflgTeleCallerProductQty.entrySet()) {


                String ProductID = entry.getKey();
                String prdctQty = "0";
                List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysSingleItem = stream(tblStoreProductMappingList).where(p -> p.getPrdNodeID() == Integer.parseInt(ProductID)).toList();

                String prdctQtyWithTCOrder = "" + (((int) (Double.parseDouble("0"))) + tblStoreProductMappingForDisplaysSingleItem.get(0).getTeleCallerProductQty());
                //((TextView) (vRow).findViewById(R.id.txtVwRate)).setText("" + hmapProductStandardRate.get(ProductID).toString());


                if ((!TextUtils.isEmpty(prdctQty)) && (Integer.parseInt(prdctQty) > 0) || Integer.parseInt(prdctQtyWithTCOrder) > 0) {
                    System.out.println("Order book Prd:-" + ProductID);

                    if (Integer.parseInt(prdctQtyWithTCOrder) > 0 && (TextUtils.isEmpty(prdctQty)) || (Integer.parseInt(prdctQty) == 0)) {
                        prdctQty = "0";
                    }
                    List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();

                    if (UOMMasterDAta != null && UOMMasterDAta.size() > 0)
                        StandardRate = UOMMasterDAta.get(0).getStandardRate();///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
                    else
                        StandardRate = Double.parseDouble(hmapProductStandardRate.get(ProductID));
                   /* if (prdctModelArrayList.getHmapflgPicsOrCases().containsKey(ProductID)) {
                        if (prdctModelArrayList.getHmapflgPicsOrCases().get(ProductID).equals("1")) {

                            int conversionUnit = (int) (hmapProductTblUOMMapping.get(Integer.parseInt(ProductID)).get(0).getRelConversionUnits());
                            StandardRate = StandardRate * conversionUnit;
                            StandardRate = Double.parseDouble(new DecimalFormat("##.##").format(StandardRate));

                           *//* EditText etrate=ll_rcylContainer.findViewWithTag(ProductID + "_etPrdRate");
                            etrate.setText(""+PrdRateCases);*//*

                            // prdctModelArrayList.getHmapOrderQtyDataToShow().put(prductId,prdctModelArrayList.getPrdctOrderQty(prductId));
                        }
                    }*/


                    StandardRateBeforeTax = StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));

                    //If No Percentage Discount or Flat Discount is Applicable Code Starts Here
                    ActualRateAfterDiscountBeforeTax = StandardRateBeforeTax;
                    DiscountAmount = 0.00;
                    ActualTax = ActualRateAfterDiscountBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
                    ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));

                    Double DiscAmtOnPreQtyBasic = DiscountAmount * Double.parseDouble(prdctQty);

                    Double DiscAmtOnPreQtyBasicToDisplay = DiscAmtOnPreQtyBasic;
                    DiscAmtOnPreQtyBasicToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));


                    TotalProductLevelDiscount = TotalProductLevelDiscount + DiscAmtOnPreQtyBasic;
                    TotTaxAmount = TotTaxAmount + (ActualTax * Double.parseDouble(prdctQty));

                    TotTaxAmountWithTCOrder = TotTaxAmountWithTCOrder + (ActualTax * Double.parseDouble(prdctQtyWithTCOrder));

                    Double TaxValue = ActualTax * Double.parseDouble(prdctQty);
                    TaxValue = Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
                    hmapProductTaxValue.put(ProductID, "" + TaxValue);

                    Double TaxValueWithTCOrder = ActualTax * Double.parseDouble(prdctQtyWithTCOrder);
                    TaxValueWithTCOrder = Double.parseDouble(new DecimalFormat("##.##").format(TaxValueWithTCOrder));
                    hmapProductTaxValueWithTCOrder.put(ProductID, "" + TaxValueWithTCOrder);

                    Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQty);
                    hmapLineValAftrTxAftrDscnt.put(ProductID, "" + OrderValPrdQtyBasis);

                    Double OrderValPrdQtyBasisWithTCOrder = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQtyWithTCOrder);
                    hmapLineValAftrTxAftrDscntWithTCOrder.put(ProductID, "" + OrderValPrdQtyBasisWithTCOrder);

                    TotalOrderValBeforeTax = TotalOrderValBeforeTax + (ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQty));
                    hmapLineValBfrTxAftrDscnt.put(ProductID, "" + ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQty));


                    TotalOrderValBeforeTaxWithTCOrder = TotalOrderValBeforeTaxWithTCOrder + (ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQtyWithTCOrder));
                    hmapLineValBfrTxAftrDscntWithTCOrder.put(ProductID, "" + ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQtyWithTCOrder));

                    TotOderValueAfterTax = TotOderValueAfterTax + OrderValPrdQtyBasis;
                    double prdLineVal = OrderValPrdQtyBasis;// (ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQty));
                    prdLineVal = Double.parseDouble(new DecimalFormat("##.##").format(prdLineVal));
                    prdctModelArrayList.setPrdctVal(ProductID, "" + prdLineVal);


                    double prdLineValWithTCOrder = OrderValPrdQtyBasisWithTCOrder;// (ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQty));
                    prdLineValWithTCOrder = Double.parseDouble(new DecimalFormat("##.##").format(prdLineValWithTCOrder));
                    prdctModelArrayList.setPrdctValWithTCOder(ProductID, "" + prdLineValWithTCOrder);

                    //If No Percentage Discount or Flat Discount is Applicable Code Ends Here


                }

            }
        }


    }



    public void chooseSim(Boolean showCloseButton ) {
        if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager localSubscriptionManager  = SubscriptionManager.from(this);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (localSubscriptionManager.getActiveSubscriptionInfoCount() > 1) {
                List<SubscriptionInfo> localList = localSubscriptionManager.getActiveSubscriptionInfoList();//getActiveSubscriptionInfo
                SubscriptionInfo simInfo1 = localList.get(0);//[0];// as SubscriptionInfo
                SubscriptionInfo simInfo2=  localList.get(1);// as SubscriptionInfo

                showPopup(simInfo1, simInfo2, showCloseButton);
            } else {
                mPreferencesManager.setValue(PreferenceManager.SIM_PREF_KEY, 0);
                int dSimNumber=mPreferencesManager.getIntValue(PreferenceManager.SIM_PREF_KEY,0);
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +StContactNo ));

                intent.putExtra("com.android.phone.extra.slot",dSimNumber-1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //=
                startActivity(intent);

            }
        } else {
            mPreferencesManager.setValue(PreferenceManager.SIM_PREF_KEY, 0);
            int dSimNumber=mPreferencesManager.getIntValue(PreferenceManager.SIM_PREF_KEY,0);
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +StContactNo ));

            intent.putExtra("com.android.phone.extra.slot",dSimNumber-1);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //=
            startActivity(intent);

        }
    }
    public void showPopup(SubscriptionInfo simInfo1, SubscriptionInfo simInfo2,Boolean showCloseButton) {
        Dialog alert =new Dialog(ProductEntryForm.this);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(R.layout.sim_selection_layout);
        alert.getWindow().setBackgroundDrawableResource(
                android.R.color.background_light);
        alert.getWindow().getAttributes().windowAnimations = R.style.side_dialog_animation;
        WindowManager.LayoutParams parms = alert.getWindow().getAttributes();
        parms.gravity = Gravity.CENTER | Gravity.CENTER;
        parms.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        parms.width = ViewGroup.LayoutParams.MATCH_PARENT;
        parms.dimAmount = (float) 0.5;
        ImageView closeBtn=alert.findViewById(R.id.closeBtn);
        Button sim1TV=alert.findViewById(R.id.sim1TV);
        Button sim2TV=alert.findViewById(R.id.sim2TV);
        if(simInfo1.getCarrierName()!=null && !TextUtils.isEmpty(simInfo1.getCarrierName().toString().trim()))
            sim1TV.setText(simInfo1.getCarrierName().toString());
        if(simInfo2.getCarrierName()!=null && !TextUtils.isEmpty(simInfo2.getCarrierName().toString().trim()))
            sim2TV.setText(simInfo2.getCarrierName().toString());
        if (!showCloseButton) {
            closeBtn.setVisibility(View.GONE);//.visibility = View.GONE
        }
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.dismiss();

            }
        });
        sim1TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                mPreferencesManager.setValue(PreferenceManager.SIM_PREF_KEY, 1);


                int dSimNumber=mPreferencesManager.getIntValue(PreferenceManager.SIM_PREF_KEY,0);
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +StContactNo ));

                intent.putExtra("com.android.phone.extra.slot",dSimNumber-1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //=
                startActivity(intent);

            }
        });

        sim2TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                mPreferencesManager.setValue(PreferenceManager.SIM_PREF_KEY, 2);
                int dSimNumber=mPreferencesManager.getIntValue(PreferenceManager.SIM_PREF_KEY,0);
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +StContactNo ));

                intent.putExtra("com.android.phone.extra.slot",dSimNumber-1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //=
                startActivity(intent);

            }
        });



        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }
}

