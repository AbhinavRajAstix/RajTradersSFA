package com.astix.rajtraderssfasales.utils;

import static br.com.zbra.androidlinq.Linq.stream;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.CustomKeyboard;
import com.astix.rajtraderssfasales.GetCartonPrdsInterface;
import com.astix.rajtraderssfasales.InterfaceCartonCurrentNoOfSet;
import com.astix.rajtraderssfasales.ProductCartonFilledDataModel;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblAlrtVal;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblProductMappedWithSchemeSlabApplied;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsBucketDetails;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsProductMappingDetail;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsValueDetail;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBucketProductMapping;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblStoreProductAddOnSchemeApplied;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblStoreProductAppliedSchemesBenifitsRecords;
import com.astix.rajtraderssfasales.adapter.CreatedCartonAdapter;
import com.astix.rajtraderssfasales.adapter.ExpandableCategoryCartonProductAdapter;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.model.CreatedCartonModel;
import com.astix.rajtraderssfasales.model.TblProductCategoryUOMTypeList;
import com.astix.rajtraderssfasales.model.TblProductTypeMasterForRetriving;
import com.astix.rajtraderssfasales.model.TblStoreProductMappingForDisplay;
import com.astix.rajtraderssfasales.model.TblUOMMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CreatedCartonDialog extends Dialog {

    GetCartonPrdsInterface getCartonPrdsInterface;
    //InterfaceCartonCurrentNoOfSet interfaceCartonCurrentNoOfSet;
    Context context;
    AppCompatActivity mactivity;
    public AppDataSource mDataSource;
    String storeID;
    String imei;
    String date;
    String pickerDate;
    String SN;
    int flgOrderType;
   /*
         storeID = passedvals.getStringExtra("storeID");
        imei = passedvals.getStringExtra("imei");
        date = passedvals.getStringExtra("userdate");
        pickerDate = passedvals.getStringExtra("pickerDate");
        SN = passedvals.getStringExtra("SN");
        flgOrderType = passedvals.getIntExtra("flgOrderType", 0);
         */


    View viewContactSection;
    int OnResumeCheckWithNoOrderClick = 0;
    LinearLayout ll_ContactUpdateSection;
    TextView tvContactNoCorrection;
    Button btnEditContact;
    ImageView ivAddCartonPrd;
    ExpandableCategoryCartonProductAdapter cardArrayAdapter;
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
    List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeLists = new ArrayList<>();
    public int RegionID = 0;
    CreatedCartonAdapter orderAdapter;
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
    RecyclerView rvCreatedCartons;
    public TableLayout tbl1_dyntable_For_ExecutionDetails;
    public TableLayout tbl1_dyntable_For_OrderDetails;
    String progressTitle;
    ProgressDialog mProgressDialog;
    //Database
    //Intent Data

    //Custom
    CustomKeyboard mCustomKeyboardNum, mCustomKeyboardNumWithoutDecimal;

    //Initialize fields
    ImageView img_ctgry, img_return, btn_bck;
    EditText ed_search;
    TextView txt_RefreshOdrTot;
    Button btn_InvoiceReview, btn_OrderReview, btn_ShowSchemeApplicableProducts, btn_ShowAllProducts, btn_NoOrder;
    LinearLayout ll_scheme_detail;

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
    HashMap<String, String> hmapProductSelectedUOMId;
    HashMap<String, String> hmapLineValBfrTxAftrDscnt;
    HashMap<String, String> hmapLineValAftrTxAftrDscnt;
    HashMap<String, String> hmapProductIdLastStock;
    LinkedHashMap<String, String> hampGetLastProductExecution;
    HashMap<String, String> hmapPrdctOdrQty;
    HashMap<String, String> hmapProductImg;// = new HashMap<String, String>();
    HashMap<String, String> hmapOrderQtyDataToShow;
    HashMap<String, String> hmapflgPicsOrCases;
    HashMap<String, String> hmapflgTeleCallerProduct;

    HashMap<String, String> hmapflgTeleCallerProductQty;
    HashMap<String, String> hmapflgUserEditedProduct;

    HashMap<String, String> hmapProductTeleCallerID;

    //ModelClass
    public int PriceApplyDiscountLevelType = 0, flgTransferStatus = 0;

    public ProductCartonFilledDataModel prdctModelArrayList = new ProductCartonFilledDataModel();

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
    int CatID = 0;


    public CreatedCartonDialog(AppCompatActivity mactivity, @NonNull Context context, String storeID, String date, String pickerDate, String SN, int flgOrderType,
                               int CatID, List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeLists, GetCartonPrdsInterface getCartonPrdsInterface) {
        //,InterfaceCartonCurrentNoOfSet interfaceCartonCurrentNoOfSet
        super(context, R.style.full_screen_dialog);
        this.context = context;
        this.mactivity = mactivity;
        this.storeID = storeID;
        this.date = date;
        this.pickerDate = pickerDate;
        this.SN = SN;
        this.flgOrderType = flgOrderType;
        this.getCartonPrdsInterface = getCartonPrdsInterface;
        this.tblProductCategoryUOMTypeLists = tblProductCategoryUOMTypeLists;
        this.CatID = CatID;
        // this.interfaceCartonCurrentNoOfSet=interfaceCartonCurrentNoOfSet;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list_carton);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDataSource = AppDataSource.getInstance(context);


        Button btnAddCarton = findViewById(R.id.btnAddCarton);
        ImageView ivCancel = findViewById(R.id.ivCancel);
        rvCreatedCartons = findViewById(R.id.rvCreatedCartons);
        ivCancel.setOnClickListener(view -> dismiss());

        initializeFields();
//        getDataFromatabase();

        orderAdapter = new CreatedCartonAdapter(context, tblProductCategoryUOMTypeLists, date, pickerDate, SN, flgOrderType, CatID/*, getCartonPrdsInterface*/);
        rvCreatedCartons.setAdapter(orderAdapter);
        rvCreatedCartons.setLayoutManager(new LinearLayoutManager(context.getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        btnAddCarton.setOnClickListener(view -> {
//            CartonProductEntryDialog cartonProductEntryDialog = new CartonProductEntryDialog((AppCompatActivity) context, context, storeID, date, pickerDate, SN,
//                    flgOrderType, CatID, "0", 0, "NA", getCartonPrdsInterface);
            dismiss();
            /*(tblStoreProductMappingForDisplaysCarton, productCartonFilledDataModel) -> {
                        LinkedHashMap<String, String> hmapPrdctOrderQty = productCartonFilledDataModel.getHmapPrdctOrderQty();

                        if (productCartonFilledDataModel.getHmapPrdctOrderQty() != null && productCartonFilledDataModel.getHmapPrdctOrderQty().size() > 0) {
                            for (Map.Entry<String, String> entry : productCartonFilledDataModel.getHmapPrdctOrderQty().entrySet()) {
                                if (!entry.getValue().equals("") && !entry.getValue().equals("0")) {
                                    getOrderData(entry.getKey());
                                }
                            }
                        }


                        for (TblStoreProductMappingForDisplay tblStoreProductMapping : tblStoreProductMappingForDisplaysCarton) {


                            if (hmapflgPicsOrCases != null && hmapflgPicsOrCases.size() > 0) {
                                if (hmapflgPicsOrCases.containsKey("" + tblStoreProductMapping.getPrdNodeID())) {
                                    List<TblUOMMaster> tblUOMMasterListAgainstProduct = tblStoreProductMapping.getTblUOMMasterList();
                                    for (TblUOMMaster tblUOMMaster : tblUOMMasterListAgainstProduct) {
                                        tblUOMMaster.setFlgSelected(0);
                                        if (tblUOMMaster.getBUOMID() == Integer.parseInt(hmapflgPicsOrCases.get("" + tblStoreProductMapping.getPrdNodeID()))) {
                                            tblUOMMaster.setFlgSelected(1);
                                            prdctModelArrayList.setPrdctQtyMappedToPicsOrCases("" + tblStoreProductMapping.getPrdNodeID(), "" + tblUOMMaster.getBUOMID());
                                        }
                                    }


                                }

                            }
                        }
                        orderBookingTotalCalc();
            }
                    */
//            cartonProductEntryDialog.setCancelable(false);
//            cartonProductEntryDialog.show();
        });
    }

    private void initializeFields() {

    }
}