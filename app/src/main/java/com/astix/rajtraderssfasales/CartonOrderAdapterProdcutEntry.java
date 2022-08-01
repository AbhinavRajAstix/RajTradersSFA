package com.astix.rajtraderssfasales;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.adapter.SchemeSlabAdapter;
import com.astix.rajtraderssfasales.model.TblSchemePerProduct;
import com.astix.rajtraderssfasales.model.TblStoreProductMappingForDisplay;
import com.astix.rajtraderssfasales.model.TblUOMMapping;
import com.astix.rajtraderssfasales.model.TblUOMMaster;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.OrderHistoryDialog;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static br.com.zbra.androidlinq.Linq.stream;

public class CartonOrderAdapterProdcutEntry extends RecyclerView.Adapter<CartonOrderAdapterProdcutEntry.ViewHolder> {
    HashMap<String, CartonOrderAdapterProdcutEntry.TextChangedListener> hmapTextListeners = new HashMap<>();
    public int intOldOdrQty=0;
    public int flgCallModelRefresh=0;
    ArrayAdapter adapterCategory;
    int IsDiscountApplicable=0;
    private Map<Integer, Integer> selectedUOMMap = new HashMap<>();
    private Map<Integer, List<TblUOMMapping>> hmapProductUOMMapping;
    List<String> UOMNames;
    LinkedHashMap<String, String> hmapPerBaseQty;
    HashMap<String, String> hmapProductImg;


    LinkedHashMap<String, String> hmapFilterProductList;
    HashMap<String, String> hmapProductStandardRate;
    HashMap<String, String> hmapProductIdStock;
    HashMap<String, String> hmapProductMRP;
    String[] listProduct;
    private final LayoutInflater inflater;
    ProductCartonFilledDataModel prdctModelArrayList;
    // InterfaceCartonCurrentNoOfSet interfaceCartonCurrentNoOfSet;
    focusLostCalled interfacefocusLostCalled;
    InterfaceRetrofitProductRecyclerView interfaceRetrofitProductRecyclerView;
    HashMap<String, String> hmapProductIdLastStock;
    LinkedHashMap<String, String> hampGetLastProductExecution;
    LinkedHashMap<String, Integer> hmapProductSuggestedQty;
    HashMap<String, Integer> hmapDistPrdctStockCount;
    HashMap<String, Integer> hmapVanPrdctStockCount;
    HashMap<String, String> hmapProductLODQty;
    HashMap<String, String> hmapProductRelatedSchemesList;
    Context context;
    String storeID;
    double singlePrdDisc = 0.0;
    int flgEntryOrReview;
    int flgIsAnySchemeMappedAgainstStore = 0;
    InterfaceShowProductRelatedScheme interfaceShowProductRelatedScheme;
    List<TblStoreProductMappingForDisplay> mTblStoreProductMappings;
    RecyclerView recyclerView;
    InterfaceCartonCurrentNoOfSet interfaceCartonCurrentNoOfSet;
    HashMap<String, String> hmapProductDisc;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public CartonOrderAdapterProdcutEntry(Context context, String storeID, String[] listProduct, LinkedHashMap<String, String> hmapFilterProductList, HashMap<String, String> hmapProductStandardRate, HashMap<String, String> hmapProductMRP, HashMap<String, String> hmapProductIdStock, HashMap<String, String> hmapProductIdLastStock, LinkedHashMap<String, String> hampGetLastProductExecution, HashMap<String, Integer> hmapDistPrdctStockCount, HashMap<String, Integer> hmapVanPrdctStockCount, ProductCartonFilledDataModel prdctModelArrayList, int flgEntryOrReview, LinkedHashMap<String, String> hmapPerBaseQty, Map<Integer, List<TblUOMMapping>> hmapProductUOMMapping, HashMap<String, String> hmapProductLODQty, LinkedHashMap<String, Integer> hmapProductSuggestedQty, HashMap<String, String> hmapProductImg, HashMap<String, String> hmapProductRelatedSchemesList, int flgIsAnySchemeMappedAgainstStore, List<TblStoreProductMappingForDisplay> mTblStoreProductMappings, HashMap<String, String> hmapProductDisc,int IsDiscountApplicable, InterfaceCartonCurrentNoOfSet interfaceCartonCurrentNoOfSet) {
        interfacefocusLostCalled = (focusLostCalled) context;
//        interfaceRetrofitProductRecyclerView = (InterfaceRetrofitProductRecyclerView) context;
        //  interfaceCartonCurrentNoOfSet=(InterfaceCartonCurrentNoOfSet)context;
        interfaceShowProductRelatedScheme = (InterfaceShowProductRelatedScheme) context;
        inflater = LayoutInflater.from(context);
        this.mTblStoreProductMappings = mTblStoreProductMappings;
        this.interfaceCartonCurrentNoOfSet = interfaceCartonCurrentNoOfSet;
        this.hmapProductSuggestedQty = hmapProductSuggestedQty;
        this.hmapProductUOMMapping = hmapProductUOMMapping;
        this.hmapProductImg = hmapProductImg;
        this.storeID = storeID;
        this.hmapPerBaseQty = hmapPerBaseQty;

        this.hmapFilterProductList = hmapFilterProductList;
        this.prdctModelArrayList = prdctModelArrayList;
        this.hmapProductStandardRate = hmapProductStandardRate;
        this.hmapProductMRP = hmapProductMRP;
        this.hmapProductDisc = hmapProductDisc;
        this.hmapProductIdStock = hmapProductIdStock;
        this.listProduct = listProduct;
        this.hmapProductLODQty = hmapProductLODQty;
        this.hmapProductRelatedSchemesList = hmapProductRelatedSchemesList;
        this.flgIsAnySchemeMappedAgainstStore = flgIsAnySchemeMappedAgainstStore;

        this.hmapProductIdLastStock = hmapProductIdLastStock;
        this.hampGetLastProductExecution = hampGetLastProductExecution;
        this.hmapDistPrdctStockCount = hmapDistPrdctStockCount;
        this.hmapVanPrdctStockCount = hmapVanPrdctStockCount;
        this.flgEntryOrReview = flgEntryOrReview;
        this.IsDiscountApplicable=IsDiscountApplicable;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_carton_order_example, parent, false);

        return new ViewHolder(view);
    }

    public void updateList(List<TblStoreProductMappingForDisplay> mTblStoreProductMappings) {
        this.mTblStoreProductMappings = mTblStoreProductMappings;
        recyclerView.post(() -> notifyDataSetChanged());
    }

    public void updateListDiscountModel(ProductCartonFilledDataModel productCartonFilledDataModelForDiscount) {
        this.prdctModelArrayList = productCartonFilledDataModelForDiscount;
        recyclerView.post(() -> notifyDataSetChanged());
    }

    public void updateDiscountApplied(double singlePrdDisc) {
        this.singlePrdDisc = singlePrdDisc;
        recyclerView.post(() -> notifyDataSetChanged());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TblStoreProductMappingForDisplay tblStoreProductMapping = mTblStoreProductMappings.get(holder.getAdapterPosition());
        String prductName = tblStoreProductMapping.getPrdName();// listProduct[position].split(Pattern.quote("^"))[1];
        String prductId = "" + tblStoreProductMapping.getPrdNodeID();// listProduct[position].split(Pattern.quote("^"))[0];
        holder.tvLODQty.setVisibility(View.GONE);
        Glide.with(holder.ivProductImg).load(hmapProductImg.get(prductId)).error(R.drawable.no_img).into(holder.ivProductImg);

        holder.img_expandcollapse.setTag("" + tblStoreProductMapping.getCatID());
        holder.expanded_menu.setTag("" + tblStoreProductMapping.getCatID());

        holder.frmlayout.setTag("" + tblStoreProductMapping.getCatID());
        holder.ll_PrdctPage.setTag("" + tblStoreProductMapping.getCatID());

        // holder.frmlayout.setVisibility(View.GONE);
        holder.ll_PrdctPage.setVisibility(View.VISIBLE);
        holder.ivTARSOrder.setVisibility(View.GONE);//
       // holder.ivTARSOrder.setVisibility((tblStoreProductMapping.getFlgTeleCallerProduct() == 1) ? View.VISIBLE : View.GONE);
        holder.ivReplenishment.setVisibility((tblStoreProductMapping.getFlgReplenishmentSKU() == 1) ? View.VISIBLE : View.GONE);
        holder.ivScheme.setVisibility((tblStoreProductMapping.getFlgProductWithScheme() == 1) ? View.VISIBLE : View.GONE);
        holder.ivPriority.setVisibility((tblStoreProductMapping.getFlgFocusbrand() == 1) ? View.VISIBLE : View.GONE);

        holder.ll_HeaderSection.setVisibility(View.GONE);
        if (tblStoreProductMapping.getFlgMakeRowVisible() == 1) {
            //  holder.ll_PrdctPage.setVisibility(View.VISIBLE);
            holder.ll_HeaderSection.setVisibility(View.VISIBLE);
            holder.frmlayout.setVisibility(View.VISIBLE);


        }
        /*else
        {
            holder.ll_PrdctPage.setVisibility(View.GONE);
        }*/

        holder.expanded_menu.setVisibility(View.GONE);
        holder.img_expandcollapse.setVisibility(View.GONE);

        if (tblStoreProductMapping.getFlgShowCategoryHeader() == 1) {
            // holder.ll_PrdctPage.setVisibility(View.VISIBLE);
            holder.ll_HeaderSection.setVisibility(View.VISIBLE);
            // holder.frmlayout.setVisibility(View.VISIBLE);
        } else {
            // holder.ll_PrdctPage.setVisibility(View.VISIBLE);
            holder.ll_HeaderSection.setVisibility(View.GONE);
            //  holder.frmlayout.setVisibility(View.VISIBLE);

        }

       /* if(tblStoreProductMapping.getFlgMakeRowVisible()==0)
        {

            holder.frmlayout.setVisibility(View.GONE);
          //  holder.ll_PrdctPage.setVisibility(View.GONE);
            holder.ll_HeaderSection.setVisibility(View.GONE);
        }*/


        holder.tvSKUType.setText(tblStoreProductMapping.getCategoryDesc());
       /* if (tblStoreProductMapping.getFlgShowCategoryHeader() == 1) {

            holder.ll_HeaderSection.setVisibility(View.VISIBLE);


        } else {
            holder.ll_HeaderSection.setVisibility(View.GONE);
        }*/


        holder.img_expandcollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.getAdapterPosition()
                holder.expanded_menu.setVisibility(View.VISIBLE);
                holder.img_expandcollapse.setVisibility(View.GONE);
                interfaceRetrofitProductRecyclerView.fnExpandCategory(Integer.parseInt(holder.img_expandcollapse.getTag().toString()));
            }
        });

        holder.expanded_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.expanded_menu.setVisibility(View.GONE);
                holder.img_expandcollapse.setVisibility(View.VISIBLE);
                interfaceRetrofitProductRecyclerView.fnCollapseCategory(Integer.parseInt(holder.expanded_menu.getTag().toString()));
            }
        });
        if (hmapProductLODQty.containsKey(prductId)) {
            if (!hmapProductLODQty.get(prductId).equals("NA/0")) {
                holder.tvLODQty.setText(hmapProductLODQty.get(prductId));
                holder.tvLODQty.setVisibility(View.VISIBLE);
            }
        }


        // holder.tv_SuggestedOrder.setText("" + tblStoreProductMapping.getSuggestedQty());

        holder.tv_SuggestedOrder.setText("" + 0);
        holder.tv_SuggestedOrder.setTag(prductId);
        if (hmapProductSuggestedQty.containsKey(prductId)) {
            holder.tv_SuggestedOrder.setText("" + hmapProductSuggestedQty.get(prductId));
        }
        holder.tv_SuggestedOrder.setOnClickListener(view -> {
            holder.etOrderQty.setText(holder.tv_SuggestedOrder.getText().toString().trim());

            prdctModelArrayList.setFocusLostEditText(holder.itemView.findViewWithTag(view.getTag() + "_etOrderQty"));
            interfacefocusLostCalled.fcsLstCld(false, (EditText) holder.itemView.findViewWithTag(view.getTag() + "_etOrderQty"));
        });

        holder.llScheme.setTag(prductId);
        holder.llCalc.setTag(prductId);

        holder.llScheme.setVisibility(View.INVISIBLE);

        if (hmapProductRelatedSchemesList.containsKey(prductId)) {
            holder.llScheme.setVisibility(View.VISIBLE);
        }
        holder.llScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceShowProductRelatedScheme.fnShowSchemeOfProduct(view.getTag().toString());
            }
        });


        holder.llCalc.setOnClickListener(view -> {
            interfaceShowProductRelatedScheme.fnProductCalculation(view.getTag().toString());
           /* ProductInfoDialog productInfoDialog = new ProductInfoDialog(context, prductName,view.getTag().toString());
            productInfoDialog.show();*/
        });

        holder.llHistory.setOnClickListener(view -> {
            OrderHistoryDialog orderHistoryDialog = new OrderHistoryDialog(context, prductName, storeID, prductId);
            orderHistoryDialog.show();
        });


        holder.rvSchemeSlab.setVisibility(View.GONE);
       /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rvSchemeSlab.setLayoutManager(linearLayoutManager);

        if (prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode() != null && prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode().containsKey(prductId)) {
            holder.rvSchemeSlab.setVisibility(View.VISIBLE);
            holder.rvSchemeSlab.setAdapter(new SchemeSlabAdapter(context, prdctModelArrayList.getArrListProductCurrentAppliedSchemeIDandDescrAndColorCode(prductId)));

        }*/

        holder.tvProdctName.setText(prductName);
        if ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 0)) {
            if (hmapDistPrdctStockCount != null && hmapDistPrdctStockCount.size() > 0) {
                if (hmapDistPrdctStockCount.containsKey(prductId)) {

                    holder.tvProdctName.setText(prductName + "( Avl : " + hmapDistPrdctStockCount.get(prductId) + ")");//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
                } else {
                    holder.tvProdctName.setText(prductName);
                }
            } else {
                holder.tvProdctName.setText(prductName);
            }
        } else if ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1)) {
            if (hmapVanPrdctStockCount != null && hmapVanPrdctStockCount.size() > 0) {
                if (hmapVanPrdctStockCount.containsKey(prductId)) {

                    holder.tvProdctName.setText(prductName + "( Avl : " + hmapVanPrdctStockCount.get(prductId) + ")");//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
                } else {
                    holder.tvProdctName.setText(prductName);
                }
            } else {
                holder.tvProdctName.setText(prductName);
            }
        } else if ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1)) {
            if ((hmapDistPrdctStockCount != null && hmapDistPrdctStockCount.size() > 0)) {
                if (hmapDistPrdctStockCount.containsKey(prductId)) {

                    if ((hmapVanPrdctStockCount != null && hmapVanPrdctStockCount.size() > 0)) {
                        if (hmapVanPrdctStockCount.containsKey(prductId)) {
                            holder.tvProdctName.setText(prductName + "(D.Avl : " + hmapDistPrdctStockCount.get(prductId) + ")" + "(V.Avl : " + hmapVanPrdctStockCount.get(prductId) + ")");//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
                        } else {
                            holder.tvProdctName.setText(prductName + "(D.Avl : " + hmapDistPrdctStockCount.get(prductId) + ")");//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
                        }
                    } else {
                        holder.tvProdctName.setText(prductName + "(D.Avl : " + hmapDistPrdctStockCount.get(prductId) + ")");//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
                    }

                } else {
                    holder.tvProdctName.setText(prductName);
                }
            } else if ((hmapVanPrdctStockCount != null && hmapVanPrdctStockCount.size() > 0)) {
                if (hmapVanPrdctStockCount.containsKey(prductId)) {
                    holder.tvProdctName.setText(prductName + "(V.Avl : " + hmapVanPrdctStockCount.get(prductId) + ")");
                } else {
                    holder.tvProdctName.setText(prductName);
                }
            } else {
                holder.tvProdctName.setText(prductName);
            }
        } else {
            holder.tvProdctName.setText(prductName);
        }
        holder.txtVwRate.setTag(prductId + "_etPrdRate");
   /*     if (hmapProductStandardRate != null && hmapProductStandardRate.containsKey(prductId) && Double.parseDouble(hmapProductStandardRate.get(prductId)) > 0.0)
            holder.txtVwRate.setText(hmapProductStandardRate.get(prductId));
        else
            holder.txtVwRate.setText(null);
*/

        if ((hmapProductIdStock != null) && (hmapProductIdStock.containsKey(prductId))) {
            holder.tvStock.setText(hmapProductIdStock.get(prductId));
        } else {
            holder.tvStock.setText("0");
        }
        holder.etOrderQty.setTag(prductId + "_etOrderQty");
       /* holder.tvMRP.setText(hmapProductMRP.get(prductId));
        holder.tvMRP.setTag(prductId + "_etPrdMRP");*/

       /* if(prdctModelArrayList.getHmapPrdctOrderQty().containsKey(prductId)) {
            holder.etOrderQty.setText(prdctModelArrayList.getPrdctOrderQty(prductId));

        }else {
            holder.etOrderQty.setText("");

        }*/
      //  holder.et_DisVal.setText("");

       /* holder.et_DisVal.setTag(prductId + "_tvDisval");
        if (prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey(prductId)) {
            if(Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prductId))>0)
            holder.et_DisVal.setText(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prductId));
        }*/

        holder.et_DisVal.setTag(prductId + "_tvDisval");
        if (prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey(prductId)) {
            if(!TextUtils.isEmpty(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prductId))  && !TextUtils.isEmpty(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prductId)) && Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prductId))>0)

                for (int ji = 0; ji < tblStoreProductMapping.getTblUOMMasterList().size(); ji++) {
                    if (tblStoreProductMapping.getTblUOMMasterList().get(ji).getFlgSelected() == 1) {
                        if(tblStoreProductMapping.getTblUOMMasterList().get(ji).getBUOMID()==3)
                            holder.et_DisVal.setText(String.format("%.2f", Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prductId))));
                        else
                            holder.et_DisVal.setText(""+String.format("%.2f", (Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prductId))*tblStoreProductMapping.getTblUOMMasterList().get(ji).getRelConversionUnit())));
                        break;
                    }


                }


        }
        else
        {
            holder.et_DisVal.setText("");
        }


        if(IsDiscountApplicable==0)
            holder.et_DisVal.setEnabled(false);

        holder.tv_FreeQty.setText("0");

        holder.tv_FreeQty.setTag(prductId + "_tvFreeQty");
        if (prdctModelArrayList.getHmapPrdctFreeQty().containsKey(prductId)) {
            if(prdctModelArrayList.getPrdctFreeQtyAgainstProduct(prductId)!=null && !prdctModelArrayList.getPrdctFreeQtyAgainstProduct(prductId).equals("null"))
            holder.tv_FreeQty.setText(prdctModelArrayList.getPrdctFreeQtyAgainstProduct(prductId));
        }


        holder.tvTotal.setTag(prductId + "_tvOrderval");
        holder.spinnerUnit.setTag(prductId + "^" + position);
        List<String> UOMMasterDAta = stream(tblStoreProductMapping.getTblUOMMasterList()).select(p -> p.getBUOMName()).toList();

        adapterCategory = new ArrayAdapter(context, R.layout.spinner_item_2, UOMMasterDAta);
        adapterCategory.setDropDownViewResource(R.layout.spina);
        holder.spinnerUnit.setAdapter(adapterCategory);

        int flgIndex = 0;




        /*holder.swPcsToCase.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                String crntPrdID = toggleableView.getTag().toString();
                if (isOn) {
                    prdctModelArrayList.setPrdctQtyMappedToPicsOrCases(crntPrdID, "2");

                    String prdctOrderQtyEntered = prdctModelArrayList.getPrdctOrderQtyToShow("" + crntPrdID);
                    if (!prdctOrderQtyEntered.equals("")) {
                        Double StandardRate = Double.parseDouble(hmapProductStandardRate.get("" + crntPrdID));

                        Double OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQtyEntered);
                        prdctModelArrayList.setPrdctVal("" + crntPrdID, "" + OrderValPrdQtyBasis);
                        prdctModelArrayList.getHmapOrderQtyDataToShow().put(crntPrdID, prdctOrderQtyEntered);
                        prdctModelArrayList.getHmapPrdctOrderQty().put(crntPrdID, prdctOrderQtyEntered);
                        Double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
                        TextView txtOrderVal = holder.itemView.findViewWithTag(crntPrdID + "_tvOrderval");
                        txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);

                        TextView txtVwRate = holder.itemView.findViewWithTag(crntPrdID + "_etPrdRate");
                        txtVwRate.setText("" + StandardRate);

                        interfacefocusLostCalled.fcsLstCld(false, (EditText) holder.itemView.findViewWithTag(crntPrdID + "_etOrderQty"));
                        // holder.txtVwRate.setTag(prductId + "_etPrdRate");
                    }
                } else {
                    prdctModelArrayList.setPrdctQtyMappedToPicsOrCases(toggleableView.getTag().toString(), "1");

                    String prdctOrderQtyEntered = prdctModelArrayList.getPrdctOrderQtyToShow("" + crntPrdID);
                    if (!prdctOrderQtyEntered.equals("")) {
                        Double StandardRate = Double.parseDouble(hmapProductStandardRate.get("" + crntPrdID));

                        int conversionUnit = (int) (hmapProductUOMMapping.get(Integer.parseInt(crntPrdID)).get(0).getRelConversionUnits());
                        Double OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQtyEntered) * conversionUnit;

                        prdctModelArrayList.getHmapPrdctOrderQty().put(crntPrdID, "" + (Integer.parseInt(prdctOrderQtyEntered) * conversionUnit));

                        prdctModelArrayList.getHmapOrderQtyDataToShow().put(crntPrdID, prdctOrderQtyEntered);

                        prdctModelArrayList.setPrdctVal("" + crntPrdID, "" + OrderValPrdQtyBasis);
                        Double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
                        TextView txtOrderVal = holder.itemView.findViewWithTag(crntPrdID + "_tvOrderval");
                        txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);


                        Double PrdRateCases = StandardRate * conversionUnit;
                        PrdRateCases = Double.parseDouble(new DecimalFormat("##.##").format(PrdRateCases));
                        TextView txtVwRate = holder.itemView.findViewWithTag(crntPrdID + "_etPrdRate");
                        txtVwRate.setText("" + PrdRateCases);

                        interfacefocusLostCalled.fcsLstCld(false, (EditText) holder.itemView.findViewWithTag(crntPrdID + "_etOrderQty"));

                    }
                }

            }
        });*/

        //  if (prdctModelArrayList.getHmapflgPicsOrCases().containsKey(prductId)) {

        for (int ji = 0; ji < tblStoreProductMapping.getTblUOMMasterList().size(); ji++) {
            //for (TblUOMMaster uomMaster : tblStoreProductMapping.getTblUOMMasterList()) {
            int i = 0;
            if (tblStoreProductMapping.getTblUOMMasterList().get(ji).getFlgSelected() == 1) {
                flgIndex = ji;
                break;
            }


        }
        List<TblUOMMaster> tblUOMMastersForPrd = stream(tblStoreProductMapping.getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
        if (tblUOMMastersForPrd.size() > 0) {
            if (tblUOMMastersForPrd.get(0).getBUOMID() == 3) {
                //  int conversionUnit = 1;
                Double PrdRateCases = tblUOMMastersForPrd.get(0).getStandardRate();// * conversionUnit;
                PrdRateCases = Double.parseDouble(new DecimalFormat("##.##").format(PrdRateCases));
                holder.txtVwRate.setText("" + PrdRateCases);
            } else {
                //    int conversionUnit = (int) (tblUOMMastersForPrd.get(0).getRelConversionUnit());
                Double PrdRateCases = tblUOMMastersForPrd.get(0).getStandardRate();// * conversionUnit;
                PrdRateCases = Double.parseDouble(new DecimalFormat("##.##").format(PrdRateCases));
                holder.txtVwRate.setText("" + PrdRateCases);
            }
        } else {
            Double PrdRateCases = Double.parseDouble(hmapProductStandardRate.get(prductId));// * conversionUnit;
            PrdRateCases = Double.parseDouble(new DecimalFormat("##.##").format(PrdRateCases));
            holder.txtVwRate.setText("" + PrdRateCases);
        }


        //}
       /* else
        {
            List<TblUOMMaster> tblUOMMastersForPrd=stream(tblStoreProductMapping.getTblUOMMasterList()).where(p->p.getFlgSelected()==1).toList();
            if( tblUOMMastersForPrd.get(0).getBUOMID()==3)
            {
                //  int conversionUnit = 1;
                Double PrdRateCases =tblUOMMastersForPrd.get(0).getStandardRate();// * conversionUnit;
                PrdRateCases = Double.parseDouble(new DecimalFormat("##.##").format(PrdRateCases));
                holder.txtVwRate.setText("" + PrdRateCases);
            }
            else {
                //    int conversionUnit = (int) (tblUOMMastersForPrd.get(0).getRelConversionUnit());
                Double PrdRateCases =tblUOMMastersForPrd.get(0).getStandardRate();// * conversionUnit;
                PrdRateCases = Double.parseDouble(new DecimalFormat("##.##").format(PrdRateCases));
                holder.txtVwRate.setText("" + PrdRateCases);
            }
        }*/


        holder.spinnerUnit.setSelection(flgIndex);


        if (prdctModelArrayList.getHmapOrderQtyDataToShow().containsKey(prductId)) {
            holder.etOrderQty.setText(prdctModelArrayList.getPrdctOrderQtyToShow(prductId));

        } else {
            holder.etOrderQty.setText("");

        }


        holder.tvTotal.setText("" + prdctModelArrayList.getPrdctOrderVal(prductId));


        holder.spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                Spinner v1 = (Spinner) view.getParent();
                TableLayout v2 = (TableLayout) v1.getParent().getParent();
                String crntPrdID = v1.getTag().toString().trim().split(Pattern.quote("^"))[0];
                int i = Integer.parseInt(v1.getTag().toString().trim().split(Pattern.quote("^"))[1]);
                List<TblStoreProductMappingForDisplay> mProductRecord=stream(mTblStoreProductMappings).where(p->p.getPrdNodeID()==Integer.parseInt(crntPrdID)).toList();


                List<TblUOMMaster> UOMMasterDAtaSingleDat = stream(mTblStoreProductMappings.get(i).getTblUOMMasterList()).where(p -> p.getBUOMName().equals(v1.getSelectedItem().toString())).toList();

                List<TblUOMMaster> UOMMasterDAtaSingleDatForOrderCalculation = stream(mTblStoreProductMappings.get(i).getTblUOMMasterList()).where(p -> p.getBUOMID()==3).toList();

                prdctModelArrayList.setPrdctQtyMappedToPicsOrCases(crntPrdID, "" + UOMMasterDAtaSingleDat.get(0).getBUOMID());
                //Will change to dropdown selected  OptionID

                List<TblUOMMaster> tblUOMMasterListAgainstProduct = mTblStoreProductMappings.get(i).getTblUOMMasterList();
                for (TblUOMMaster tblUOMMaster : tblUOMMasterListAgainstProduct) {
                    tblUOMMaster.setFlgSelected(0);
                    if (tblUOMMaster.getBUOMID() == Integer.parseInt(prdctModelArrayList.getHmapflgPicsOrCases().get("" + crntPrdID))) {
                        tblUOMMaster.setFlgSelected(1);
                    }
                }



                List<TblUOMMaster> tblUOMMastersForPrd = stream(tblUOMMasterListAgainstProduct).where(p -> p.getFlgSelected() == 1).toList();
                String prdctOrderQtyEntered = prdctModelArrayList.getPrdctOrderQtyToShow("" + crntPrdID);









                double StandardRate = UOMMasterDAtaSingleDatForOrderCalculation.get(0).getStandardRate();
                Double PrdRateCases = UOMMasterDAtaSingleDat.get(0).getStandardRate();// * conversionUnit;
                PrdRateCases = Double.parseDouble(new DecimalFormat("##.##").format(PrdRateCases));
                TextView txtVwRate = v2.findViewWithTag(crntPrdID + "_etPrdRate");
                txtVwRate.setText("" + PrdRateCases);







                if (!prdctOrderQtyEntered.equals("")) {

                    String prdctOrderQtyEnteredForCalculation = prdctModelArrayList.getPrdctOrderQtyToShow("" + crntPrdID);
                    int conversionUnit = 1;
                    if (UOMMasterDAtaSingleDat.get(0).getBUOMID() != 3) {
                        conversionUnit = (int) (tblUOMMastersForPrd.get(0).getRelConversionUnit());
                        if(!prdctOrderQtyEnteredForCalculation.equals(""))
                            prdctOrderQtyEnteredForCalculation=""+(Integer.parseInt(prdctOrderQtyEnteredForCalculation)*conversionUnit);
                    }
                    prdctModelArrayList.getHmapOrderQtyDataToShow().put(crntPrdID, prdctOrderQtyEntered);
                    prdctModelArrayList.getHmapPrdctOrderQty().put(crntPrdID, "" + (Integer.parseInt(prdctOrderQtyEnteredForCalculation)));


                    if (prdctModelArrayList.getHmapPrdctOverAllDiscountToApply()!=null && prdctModelArrayList.getHmapPrdctOverAllDiscountToApply().containsKey("ApplyDiscount") && Double.parseDouble(  prdctModelArrayList.getHmapPrdctOverAllDiscountToApply().get("ApplyDiscount"))>0.0) {
                        double dis =Double.parseDouble(  prdctModelArrayList.getHmapPrdctOverAllDiscountToApply().get("ApplyDiscount"));
                        double totordval=0.0;

                        LinkedHashMap<String, String> hmapPrdctOrderQty = prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll();
                        int qty = 0;
                        for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : mTblStoreProductMappings) {
                                if (hmapPrdctOrderQty != null && hmapPrdctOrderQty.size() > 0) {
                                    if (hmapPrdctOrderQty.containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID())) {
                                        String prdctQty = "" + prdctModelArrayList.getHmapPrdctOrderQty().get("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                                        prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().put("" + tblStoreProductMappingForDisplay.getPrdNodeID(),prdctQty);
                                        if ((!TextUtils.isEmpty(prdctQty)) && (Integer.parseInt(prdctQty) > 0)) {

                                            qty = qty + Integer.parseInt(prdctQty);
                                            //  List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
                                            //  List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(UOMMasterDAtaSingleDatForOrderCalculation.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                                            int conversionUnitForOrderCalculation = 1;


                                            List<TblUOMMaster> UOMMasterDAtaSingleDatForCalc = stream(tblStoreProductMappingForDisplay.getTblUOMMasterList()).where(p -> p.getFlgSelected()==1).toList();

                                            List<TblUOMMaster> UOMMasterDAtaSingleDatForOrderCalculationForCalc = stream(tblStoreProductMappingForDisplay.getTblUOMMasterList()).where(p -> p.getBUOMID()==3).toList();

                                            double  StandardRateBeforeTax =UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getStandardRate();// StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));

                                            double DiscountAmount = 0.00;


                                            double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax) - DiscountAmount;// StandardRateBeforeTax;
                                            double ActualTax = ActualRateAfterDiscountBeforeTax * ( UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getVatTax() / 100);
                                            double ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + ( UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getVatTax() / 100));
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

                        if (dis <= totordval) {

                            //  double discToBeApplied = (double) dis / qty;
                            for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : mTblStoreProductMappings) {
                                if (tblStoreProductMappingForDisplay.getFlgTeleCallerProduct() == 0) {
                                    if (hmapPrdctOrderQty != null && hmapPrdctOrderQty.size() > 0) {
                                        if (hmapPrdctOrderQty.containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID())) {
                                            String prdctQty = "" + prdctModelArrayList.getHmapPrdctOrderQty().get("" + tblStoreProductMappingForDisplay.getPrdNodeID());

                                            //  List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                                            int conversionUnitForOrderCalculation = 1;




                                            List<TblUOMMaster> UOMMasterDAtaSingleDatForCalc = stream(tblStoreProductMappingForDisplay.getTblUOMMasterList()).where(p -> p.getFlgSelected()==1).toList();

                                            List<TblUOMMaster> UOMMasterDAtaSingleDatForOrderCalculationForCalc = stream(tblStoreProductMappingForDisplay.getTblUOMMasterList()).where(p -> p.getBUOMID()==3).toList();


                                            double  StandardRateBeforeTax =UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getStandardRate();// StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));

                                            double DiscountAmount = 0.00;


                                            double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax) - DiscountAmount;// StandardRateBeforeTax;
                                            double ActualTax = ActualRateAfterDiscountBeforeTax * ( UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getVatTax() / 100);
                                            double ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + ( UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getVatTax() / 100));
                                            Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQty);


                                            double discToBeApplied=(dis*OrderValPrdQtyBasis)/totordval;
                                            discToBeApplied=discToBeApplied/Double.parseDouble(prdctQty);
                                         /*   if (UOMMasterDAtaSingleDat.get(0).getBUOMID() != 3) {
                                                conversionUnitForOrderCalculation = (int) (UOMMasterDAtaSingleDat.get(0).getRelConversionUnit());

                                            }*/
                                            // discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                            //double discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                            prdctModelArrayList.getHmapPrdctDiscountPerUOM().put("" + tblStoreProductMappingForDisplay.getPrdNodeID(), "" + ( discToBeApplied));
                                        }
                                    }

                                }
                            }
                            //  updateListDiscountModel(prdctModelArrayList);

                        }
                    }












                    double discPercentage=0.0;


                    discPercentage=0.0;
                    if (prdctModelArrayList.getHmapPrdctDiscountPerUOM() != null && prdctModelArrayList.getHmapPrdctDiscountPerUOM().size() > 0 && prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey(crntPrdID)  && !TextUtils.isEmpty(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(crntPrdID))) {
                        discPercentage = Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(crntPrdID));
                    }

                    double StandardRateBeforeTax =UOMMasterDAtaSingleDatForOrderCalculation.get(0).getStandardRate();
                    double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax );// - discPercentage;// StandardRateBeforeTax - (StandardRateBeforeTax * (((discPercentage / 100))));
                    ActualRateAfterDiscountBeforeTax = Double.parseDouble(new DecimalFormat("##.##").format(ActualRateAfterDiscountBeforeTax));
                    double DiscountAmount = StandardRateBeforeTax - ActualRateAfterDiscountBeforeTax;
                    double DiscAmtOnPreQtyBasic = DiscountAmount * Double.parseDouble(prdctOrderQtyEnteredForCalculation);

                    double schemeDiscountVal=0.0;
                    if (prdctModelArrayList.getHmapPrdctIdPrdctDscnt().containsKey(crntPrdID)) {
                        schemeDiscountVal = Double.parseDouble(prdctModelArrayList.getHmapPrdctIdPrdctDscnt().get(crntPrdID));
                    }
                    Double totalValAfterDiscount = ((ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctOrderQtyEnteredForCalculation))) / Double.parseDouble(prdctOrderQtyEnteredForCalculation);
                    double ActualTax = totalValAfterDiscount * UOMMasterDAtaSingleDat.get(0).getVatTax();//   Double.parseDouble(hmapProductVatTaxPerventage.get(prdctId)) / 100);
                    if (Double.isNaN(ActualTax))
                        ActualTax = 0.0;


                    double ActualRateAfterDiscountAfterTax = totalValAfterDiscount + ActualTax;
                    double discToBeApplied=discPercentage;//(discPercentage*totalValAfterDiscount)/totalValAfterDiscount;

                 /*  if (UOMMasterDAtaSingleDat.get(0).getBUOMID() != 3) {

                        discToBeApplied=discToBeApplied/conversionUnit;
                    }*/

                   // prdctModelArrayList.getHmapPrdctOverAllDiscountCalculated().put(crntPrdID,""+(discToBeApplied+schemeDiscountVal));


                    //totalValAfterDiscount = Double.parseDouble(new DecimalFormat("##.##").format(totalValAfterDiscount));
                    if (totalValAfterDiscount.isNaN())
                        totalValAfterDiscount = 0.0;




                    ActualRateAfterDiscountAfterTax = Double.parseDouble(new DecimalFormat("##.##").format(ActualRateAfterDiscountAfterTax));
                    // Double OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQty);
                    Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctOrderQtyEnteredForCalculation);
                    OrderValPrdQtyBasis = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
                    //   prdctModelArrayList.setPrdctVal(crntPrdID, "" + OrderValPrdQtyBasis);

                    Double DiscountValPrdQtyBasis = totalValAfterDiscount * Double.parseDouble(prdctOrderQtyEntered);


                    //   prdctModelArrayList.setProductDiscountAmt(prdctId,""+DiscAmtOnPreQtyBasic);
                    DiscAmtOnPreQtyBasic = Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasic));

                    //  DisVal.setText("\u20B9"+DiscAmtOnPreQtyBasic);
                    //  OrderValPrdQtyBasis=OrderValPrdQtyBasis- (((Double.parseDouble(prdctOrderQtyEnteredForCalculation)*discToBeApplied)/((Double.parseDouble(prdctOrderQtyEnteredForCalculation)/conversionUnit)))+schemeDiscountVal);
                    OrderValPrdQtyBasis=OrderValPrdQtyBasis- (((Double.parseDouble(prdctOrderQtyEnteredForCalculation)*discToBeApplied)/((Double.parseDouble(prdctOrderQtyEnteredForCalculation)/conversionUnit)))+schemeDiscountVal);
                    Double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
                    TextView txtOrderVal = v2.findViewWithTag(crntPrdID + "_tvOrderval");
                    txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);


                    // Double OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQtyEntered);

                    prdctModelArrayList.setPrdctVal("" + crntPrdID, "" + OrderValPrdQtyBasis);

                    // double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));

                    //interfacefocusLostCalled.fcsLstCld(false, (EditText) v2.findViewWithTag(crntPrdID + "_etOrderQty"));

                } else {
                    Double OrderValPrdQtyBasis = StandardRate * 0;

                    prdctModelArrayList.setPrdctVal("" + crntPrdID, "" + OrderValPrdQtyBasis);
                    prdctModelArrayList.getHmapOrderQtyDataToShow().remove(crntPrdID);
                    prdctModelArrayList.getHmapPrdctOrderQty().remove(crntPrdID);
                    double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
                    TextView txtOrderVal = v2.findViewWithTag(crntPrdID + "_tvOrderval");
                    txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*
        holder.tvMRP.setTag(prductId + "_etPrdMRP");

        if (hmapProductStandardRate != null && hmapProductStandardRate.containsKey(prductId) && Double.parseDouble(hmapProductStandardRate.get(prductId)) > 0.0)
            holder.txtVwRate.setText(hmapProductStandardRate.get(prductId));
        else
            holder.txtVwRate.setText(null);

        holder.et_OrderQty.setText(prdctModelArrayList.getPrdctOrderQty(prductId));

        holder.tvMRP.setText(hmapProductMRP.get(prductId));
        holder.tv_Orderval.setTag(prductId + "_tvOrderval");
        holder.tv_Orderval.setText("" + prdctModelArrayList.getPrdctOrderVal(prductId));
        if ((hmapProductIdStock != null) && (hmapProductIdStock.containsKey(prductId))) {
            holder.et_Stock.setText(hmapProductIdStock.get(prductId));
        } else {
            holder.et_Stock.setText("0");
        }
        int LastStockQty = 0;
        int LastExecutionQty = 0;
        int CurrentStockQty = 0;
        if (hmapProductIdLastStock.containsKey(prductId)) {
            LastStockQty = Integer.parseInt(hmapProductIdLastStock.get(prductId));
            holder.et_LstStock.setText("" + LastStockQty);
        }
        if (hampGetLastProductExecution.containsKey(prductId)) {
            LastExecutionQty = Integer.parseInt(hampGetLastProductExecution.get(prductId));
        }
        if (hmapProductIdStock.containsKey(prductId)) {
            CurrentStockQty = Integer.parseInt(hmapProductIdStock.get(prductId));
        }
        int PStoreSale = ((LastStockQty + LastExecutionQty) - CurrentStockQty);
        if (PStoreSale > 0) {
            holder.et_StoreSale.setText("" + PStoreSale);
        } else {
            holder.et_StoreSale.setText("0");
        }*/


        holder.etOrderQty.setOnFocusChangeListener(new CartonOrderAdapterProdcutEntry.FocusChangeList(holder.tbl, prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(),holder.etOrderQty,holder.et_DisVal,1, holder.tvTotal, holder.txtVwRate));
        holder.et_DisVal.setOnFocusChangeListener(new CartonOrderAdapterProdcutEntry.FocusChangeList(holder.tbl, prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(),holder.etOrderQty,holder.et_DisVal,4, holder.tvTotal, holder.txtVwRate));
        CartonOrderAdapterProdcutEntry.TextChangedListener textChangedListenerOrderQty = new CartonOrderAdapterProdcutEntry.TextChangedListener(holder.etOrderQty, holder.tvTotal, holder.txtVwRate, 1,holder.et_DisVal);
        holder.etOrderQty.addTextChangedListener(textChangedListenerOrderQty);
        CartonOrderAdapterProdcutEntry.TextChangedListener textChangedListenerDiscount = new CartonOrderAdapterProdcutEntry.TextChangedListener(holder.etOrderQty, holder.tvTotal, holder.txtVwRate, 4,holder.et_DisVal);
        holder.etOrderQty.addTextChangedListener(textChangedListenerOrderQty);
        holder.et_DisVal.addTextChangedListener(textChangedListenerDiscount);

        hmapTextListeners.put(String.valueOf(holder.etOrderQty.getTag()),textChangedListenerOrderQty);
        hmapTextListeners.put(String.valueOf(holder.et_DisVal.getTag()),textChangedListenerDiscount);


        disableSoftInputFromAppearing(holder.etOrderQty);
    }
    public static void disableSoftInputFromAppearing(EditText editText) {
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }
    }
    @Override
    public int getItemCount() {
        // return listProduct.length;
        return mTblStoreProductMappings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProdctName, tv_SuggestedOrder, tvStock, tvLODQty, tvTotal, tvMRP, txtVwRate, tv_FreeQty;
        public TableLayout tbl;
        //        public LabeledSwitch swPcsToCase;
        public LinearLayout llHistory, llScheme, llCalc;
        public EditText et_DisVal, etOrderQty;
        public ImageView ivProductImg;
        public Spinner spinnerUnit;
        public RecyclerView rvSchemeSlab;
        TextView /*tv_DisValTV,*/ tvSKUType;
        FrameLayout frmlayout;
        ImageView ivTARSOrder, ivReplenishment, ivScheme, ivPriority, img_expandcollapse, expanded_menu;
        LinearLayout ll_HeaderSection;
        CardView ll_PrdctPage;
       /* public EditText et_ProductMRP;
        public View layout;
        public EditText et_OrderQty, et_Stock, et_StoreSale, et_LstStock;
        public TextView tv_Orderval;
        public LinearLayout ll_PrdctPage;*/

        public ViewHolder(View itemView) {
            super(itemView);
            et_DisVal = itemView.findViewById(R.id.et_DisVal);
//            tv_DisValTV = itemView.findViewById(R.id.tv_DisValTV);
            spinnerUnit = itemView.findViewById(R.id.spinnerUnit);
            tvLODQty = itemView.findViewById(R.id.tvLODQty);
//            swPcsToCase = itemView.findViewById(R.id.swPcsToCase);
            llHistory = itemView.findViewById(R.id.llHistory);
            llScheme = itemView.findViewById(R.id.llScheme);
            llCalc = itemView.findViewById(R.id.llCalc);
            tvSKUType = itemView.findViewById(R.id.tvSKUType);
            ivProductImg = itemView.findViewById(R.id.ivProductImg);
            ivTARSOrder = itemView.findViewById(R.id.ivTARSOrder);
            ivReplenishment = itemView.findViewById(R.id.ivReplenishment);
            ivScheme = itemView.findViewById(R.id.ivScheme);
            ivPriority = itemView.findViewById(R.id.ivPriority);
            tv_SuggestedOrder = itemView.findViewById(R.id.tv_SuggestedOrder);
            tvTotal = itemView.findViewById(R.id.tv_Orderval);
            tvProdctName = itemView.findViewById(R.id.tvPrdName);
            etOrderQty = itemView.findViewById(R.id.et_OrderQty);
            tv_FreeQty = itemView.findViewById(R.id.tv_FreeQty);
            frmlayout = itemView.findViewById(R.id.frmlayout);
            img_expandcollapse = itemView.findViewById(R.id.img_expandcollapse);
            expanded_menu = itemView.findViewById(R.id.expanded_menu);
            // tvMRP = itemView.findViewById(R.id.tvMRP);
            txtVwRate = itemView.findViewById(R.id.tvRate);
            tbl = itemView.findViewById(R.id.tbl);
            tvStock = itemView.findViewById(R.id.tvStock);
            rvSchemeSlab = itemView.findViewById(R.id.rvSchemeSlab);
            ll_PrdctPage = itemView.findViewById(R.id.ll_PrdctPage);
            ll_HeaderSection = itemView.findViewById(R.id.ll_HeaderSection);

//            swPcsToCase.setLabelOn("Pcs");
//            swPcsToCase.setLabelOff("Case");


       /*     etOrderQty.setOnFocusChangeListener(new FocusChangeList(tbl, prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode()));

            etOrderQty.addTextChangedListener(new TextChangedListener(etOrderQty, tvTotal, txtVwRate, 1));*/

/*

            etOrderQty.setOnFocusChangeListener(new FocusChangeList(tbl, prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(),etOrderQty,et_DisVal,1, holder.tvTotal, holder.txtVwRate));
            et_DisVal.setOnFocusChangeListener(new FocusChangeList(tbl, prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(),etOrderQty,et_DisVal,4, holder.tvTotal, holder.txtVwRate));

            etOrderQty.addTextChangedListener(new TextChangedListener(etOrderQty, tvTotal, txtVwRate, 1,et_DisVal));

            et_DisVal.addTextChangedListener(new TextChangedListener(etOrderQty, tvTotal, txtVwRate, 4,et_DisVal));
*/


//            et_DisVal.addTextChangedListener(new TextChangedListener(et_DisVal, tvTotal, txtVwRate, 1));




            /*layout = itemView;
            txtVwRate = (EditText) itemView.findViewById(R.id.txtVwRate);
            et_ProductMRP = (EditText) itemView.findViewById(R.id.et_ProductMRP);
            et_OrderQty = (EditText) itemView.findViewById(R.id.et_OrderQty);
            tv_Orderval = (TextView) itemView.findViewById(R.id.tv_Orderval);
            ll_PrdctPage = (LinearLayout) itemView.findViewById(R.id.ll_PrdctPage);
            et_Stock = (EditText) itemView.findViewById(R.id.et_Stock);
            et_StoreSale = (EditText) itemView.findViewById(R.id.et_StoreSale);
            et_LstStock = (EditText) itemView.findViewById(R.id.et_LstStock);
            et_OrderQty.addTextChangedListener(new TextChangedListener(et_OrderQty, tv_Orderval, txtVwRate, 1));
            et_OrderQty.setOnFocusChangeListener(new FocusChangeList());
            et_OrderQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(et_OrderQty.getWindowToken(), 0);
                }
            });

            if (flgEntryOrReview == 0) {
                et_OrderQty.setEnabled(true);
            } else {
                et_OrderQty.setEnabled(false);
            }

            txtVwRate.addTextChangedListener(new TextChangedListener(et_OrderQty, tv_Orderval, txtVwRate, 2));
            txtVwRate.setOnFocusChangeListener(new FocusChangeList());
            if (flgEntryOrReview == 0) {
                txtVwRate.setEnabled(true);
            } else {
                txtVwRate.setEnabled(false);
            }
*/

        }
    }

    class TextChangedListener implements TextWatcher {
        EditText ediText,etdisc;
        TextView txtOrderVal, txtVwRate;
        int flgClickBtn = 0;
        int upc;
        String disbefTextChange="";

        public TextChangedListener(EditText ediText, TextView txtOrderVal, TextView txtVwRateNew, int flgClickBtn,EditText etdisc) {//, int upc
            this.ediText = ediText;
            this.txtOrderVal = txtOrderVal;
            this.txtVwRate = txtVwRateNew;
            this.flgClickBtn = flgClickBtn;
            this.etdisc=etdisc;
            // this.upc = upc;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (!TextUtils.isEmpty(etdisc.getText().toString().trim())) {
                disbefTextChange=""+Double.parseDouble(etdisc.getText().toString().trim());
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String prdctId = ediText.getTag().toString().split(Pattern.quote("_"))[0];
            if(flgClickBtn==1) {


                if (!TextUtils.isEmpty(ediText.getText().toString().trim())) {

                    String prdctOrderQty = ediText.getText().toString().trim();



                    prdctModelArrayList.getHmapOrderQtyDataToShow().put(prdctId, prdctOrderQty);


                    List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysSingleItem = stream(mTblStoreProductMappings).where(p -> p.getPrdNodeID() == Integer.parseInt(prdctId)).toList();
                    List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();



                    List<TblUOMMaster> UOMMasterDAtaForPriceCalculation = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();




                    int conversionUnit = 1;
                    String prdctOrderQtyTotQty=prdctOrderQty;
                    if (UOMMasterDAta.get(0).getBUOMID() != 3) {
                        conversionUnit = (int) (UOMMasterDAta.get(0).getRelConversionUnit());
                        prdctOrderQtyTotQty=""+(Integer.parseInt(prdctOrderQtyTotQty)*conversionUnit);
                    }

                    prdctModelArrayList.setPrdctQty(prdctId, ""+(prdctOrderQtyTotQty));





                    if (prdctModelArrayList.getHmapPrdctOverAllDiscountToApply()!=null && prdctModelArrayList.getHmapPrdctOverAllDiscountToApply().containsKey("ApplyDiscount") && Double.parseDouble(  prdctModelArrayList.getHmapPrdctOverAllDiscountToApply().get("ApplyDiscount"))>0.0)
                    {
                        double dis =Double.parseDouble(  prdctModelArrayList.getHmapPrdctOverAllDiscountToApply().get("ApplyDiscount"));
                        double totordval=0.0;

                        LinkedHashMap<String, String> hmapPrdctOrderQty = prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll();
                        int qty = 0;
                        for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : mTblStoreProductMappings) {
                                if (hmapPrdctOrderQty != null && hmapPrdctOrderQty.size() > 0) {
                                    if (hmapPrdctOrderQty.containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID())) {
                                        String prdctQty = "" + prdctModelArrayList.getHmapPrdctOrderQty().get("" + tblStoreProductMappingForDisplay.getPrdNodeID());
                                        prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().put("" + tblStoreProductMappingForDisplay.getPrdNodeID(),prdctQty);
                                        if ((!TextUtils.isEmpty(prdctQty)) && (Integer.parseInt(prdctQty) > 0)) {

                                            qty = qty + Integer.parseInt(prdctQty);
                                            //  List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
                                            //  List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(UOMMasterDAtaSingleDatForOrderCalculation.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                                            int conversionUnitForOrderCalculation = 1;


                                            List<TblUOMMaster> UOMMasterDAtaSingleDatForCalc = stream(tblStoreProductMappingForDisplay.getTblUOMMasterList()).where(p -> p.getFlgSelected()==1).toList();

                                            List<TblUOMMaster> UOMMasterDAtaSingleDatForOrderCalculationForCalc = stream(tblStoreProductMappingForDisplay.getTblUOMMasterList()).where(p -> p.getBUOMID()==3).toList();

                                            double  StandardRateBeforeTax =UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getStandardRate();// StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));

                                            double DiscountAmount = 0.00;


                                            double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax) - DiscountAmount;// StandardRateBeforeTax;
                                            double ActualTax = ActualRateAfterDiscountBeforeTax * ( UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getVatTax() / 100);
                                            double ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + ( UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getVatTax() / 100));
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

                        if (dis <= totordval) {

                            //  double discToBeApplied = (double) dis / qty;
                            for (TblStoreProductMappingForDisplay tblStoreProductMappingForDisplay : mTblStoreProductMappings) {
                                    if (hmapPrdctOrderQty != null && hmapPrdctOrderQty.size() > 0) {
                                        if (hmapPrdctOrderQty.containsKey("" + tblStoreProductMappingForDisplay.getPrdNodeID())) {
                                            String prdctQty = "" + prdctModelArrayList.getHmapPrdctOrderQty().get("" + tblStoreProductMappingForDisplay.getPrdNodeID());

                                            //  List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                                            int conversionUnitForOrderCalculation = 1;




                                            List<TblUOMMaster> UOMMasterDAtaSingleDatForCalc = stream(tblStoreProductMappingForDisplay.getTblUOMMasterList()).where(p -> p.getFlgSelected()==1).toList();

                                            List<TblUOMMaster> UOMMasterDAtaSingleDatForOrderCalculationForCalc = stream(tblStoreProductMappingForDisplay.getTblUOMMasterList()).where(p -> p.getBUOMID()==3).toList();


                                            double  StandardRateBeforeTax =UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getStandardRate();// StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(""+tblStoreProductMappingForDisplay.getPrdNodeID())) / 100));

                                            double DiscountAmount = 0.00;


                                            double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax) - DiscountAmount;// StandardRateBeforeTax;
                                            double ActualTax = ActualRateAfterDiscountBeforeTax * ( UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getVatTax() / 100);
                                            double ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + ( UOMMasterDAtaSingleDatForOrderCalculationForCalc.get(0).getVatTax() / 100));
                                            Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQty);


                                            double discToBeApplied=(dis*OrderValPrdQtyBasis)/totordval;
                                            discToBeApplied=discToBeApplied/Double.parseDouble(prdctQty);
                                         /*   if (UOMMasterDAtaSingleDat.get(0).getBUOMID() != 3) {
                                                conversionUnitForOrderCalculation = (int) (UOMMasterDAtaSingleDat.get(0).getRelConversionUnit());

                                            }*/
                                            // discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                            //double discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                            prdctModelArrayList.getHmapPrdctDiscountPerUOM().put("" + tblStoreProductMappingForDisplay.getPrdNodeID(), "" + ( discToBeApplied));
                                        }
                                    }


                            }
                            //  updateListDiscountModel(prdctModelArrayList);

                        }
                    }













                    Double StandardRate = UOMMasterDAtaForPriceCalculation.get(0).getStandardRate();

                   // if (prdctModelArrayList.getHmapflgPicsOrCases().containsKey(prdctId)) {
                        if (!prdctOrderQty.equals("")) {
                            prdctModelArrayList.getHmapPrdctOrderQty().put(prdctId, "" + (Integer.parseInt(prdctOrderQtyTotQty)));// * conversionUnit
                            prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().put(prdctId,""+(Double.parseDouble(prdctOrderQtyTotQty)));

                        }
                        double discPercentage = 0.0;
                        double StandardRateBeforeTax =StandardRate;// UOMMasterDAtaForPriceCalculation.get(0).getStandardRateBeforeTax();//Double.parseDouble(hmapProductStandardRateBeforeTax.get(prdctId));



                  /*  if(prdctModelArrayList.getHmapProductDiscount().containsKey(prdctId))
                    {
                        discPercentage=Double.parseDouble(prdctModelArrayList.getPrdctProductDiscount(prdctId));
                    }*/
                /*    Double totalVal = StandardRateBeforeTax * Double.parseDouble(prdctOrderQty);
                    double PrdMaxValuePercentageDiscountInAmount = totalVal * (discPercentage / 100);*/
                        double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax ) - discPercentage;// StandardRateBeforeTax - (StandardRateBeforeTax * (((discPercentage / 100))));
                        ActualRateAfterDiscountBeforeTax = Double.parseDouble(new DecimalFormat("##.##").format(ActualRateAfterDiscountBeforeTax));
                        double DiscountAmount = StandardRateBeforeTax - ActualRateAfterDiscountBeforeTax;
                        double DiscAmtOnPreQtyBasic = DiscountAmount * Double.parseDouble(prdctOrderQtyTotQty);

                        double schemeDiscountVal=0.0;
                        if (prdctModelArrayList.getHmapPrdctIdPrdctDscnt().containsKey(prdctId)) {
                            schemeDiscountVal = Double.parseDouble(prdctModelArrayList.getHmapPrdctIdPrdctDscnt().get(prdctId));
                        }

                      //  prdctModelArrayList.getHmapPrdctOverAllDiscountCalculated().put(prdctId,""+(DiscAmtOnPreQtyBasic+schemeDiscountVal));
                        Double totalValAfterDiscount = ((ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctOrderQtyTotQty))) / Double.parseDouble(prdctOrderQtyTotQty);
                        //totalValAfterDiscount = Double.parseDouble(new DecimalFormat("##.##").format(totalValAfterDiscount));
                        if (totalValAfterDiscount.isNaN())
                            totalValAfterDiscount = 0.0;

                        double ActualTax = totalValAfterDiscount * UOMMasterDAtaForPriceCalculation.get(0).getVatTax();//   Double.parseDouble(hmapProductVatTaxPerventage.get(prdctId)) / 100);
                        if (Double.isNaN(ActualTax))
                            ActualTax = 0.0;


                        double ActualRateAfterDiscountAfterTax = totalValAfterDiscount + ActualTax;
                        ActualRateAfterDiscountAfterTax = Double.parseDouble(new DecimalFormat("##.##").format(ActualRateAfterDiscountAfterTax));
                        // Double OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQty);

                        if (prdctModelArrayList.getHmapPrdctDiscountPerUOM() != null && prdctModelArrayList.getHmapPrdctDiscountPerUOM().size() > 0 && prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey(prdctId) && !TextUtils.isEmpty(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prdctId))) {
                            discPercentage = Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prdctId));
                        }
                        Double OrderValPrdQtyBasis = (ActualRateAfterDiscountAfterTax - singlePrdDisc) * Double.parseDouble(prdctOrderQtyTotQty);


                        OrderValPrdQtyBasis=OrderValPrdQtyBasis-(discPercentage*Double.parseDouble(prdctOrderQtyTotQty));
                        OrderValPrdQtyBasis = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
                        prdctModelArrayList.setPrdctVal(prdctId, "" + OrderValPrdQtyBasis);

                        Double DiscountValPrdQtyBasis = totalValAfterDiscount * Double.parseDouble(prdctOrderQtyTotQty);


                        //   prdctModelArrayList.setProductDiscountAmt(prdctId,""+DiscAmtOnPreQtyBasic);
                        DiscAmtOnPreQtyBasic = Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasic));

                        //  DisVal.setText("\u20B9"+DiscAmtOnPreQtyBasic);

                        Double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
                        txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);
                    //}

                }
                else {
                    prdctModelArrayList.removePrdctQty(prdctId);
                    prdctModelArrayList.removePrdctVal(prdctId);
                    prdctModelArrayList.getHmapPrdctOrderVal().remove(prdctId);
                    //prdctModelArrayList.getHmapPrdctFreeQty().remove(prdctId);
                    prdctModelArrayList.getHmapPrdctDiscountPerUOM().remove(prdctId);
                    prdctModelArrayList.getHmapOrderQtyDataToShow().remove(prdctId);

                    txtOrderVal.setText("0.0");
                    //etdisc.setText("");
                }
                interfaceCartonCurrentNoOfSet.fnCalculateCurrentNoSet(prdctModelArrayList);
            }



            if(flgClickBtn==4)
            {
              /*  if (!TextUtils.isEmpty(etdisc.getText().toString().trim())) {
                    prdctModelArrayList.getHmapPrdctDiscountPerUOM().put(prdctId,etdisc.getText().toString().trim());
                }*/
                if (!TextUtils.isEmpty(ediText.getText().toString().trim())) {

                    String prdctOrderQty = ediText.getText().toString().trim();

                    List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysSingleItem = stream(mTblStoreProductMappings).where(p -> p.getPrdNodeID() == Integer.parseInt(prdctId)).toList();
                    List<TblUOMMaster> UOMMasterDAta = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();

                    List<TblUOMMaster> UOMMasterDAtaForCalculation = stream(tblStoreProductMappingForDisplaysSingleItem.get(0).getTblUOMMasterList()).where(p -> p.getBUOMID() == 3).toList();
                    Double StandardRate = UOMMasterDAtaForCalculation.get(0).getStandardRate();


                    int conversionUnit = 1;
                    String prdctOrderQtyTotQty=prdctOrderQty;
                    if (UOMMasterDAta.get(0).getBUOMID() != 3) {
                        conversionUnit = (int) (UOMMasterDAta.get(0).getRelConversionUnit());
                        prdctOrderQtyTotQty=""+(Integer.parseInt(prdctOrderQtyTotQty)*conversionUnit);
                    }

                    // prdctModelArrayList.setPrdctQty(prdctId, ""+(prdctOrderQtyTotQty));

                  //  if (prdctModelArrayList.getHmapflgPicsOrCases().containsKey(prdctId)) {
                        if (!prdctOrderQty.equals("")) {
                            prdctModelArrayList.getHmapPrdctOrderQty().put(prdctId, "" + (Integer.parseInt(prdctOrderQtyTotQty)));// * conversionUnit

                        }
                        double discPercentage = 0.0;
                        double StandardRateBeforeTax = StandardRate;//UOMMasterDAtaForCalculation.get(0).getStandardRateBeforeTax();//Double.parseDouble(hmapProductStandardRateBeforeTax.get(prdctId));

                      /*  if (prdctModelArrayList.getHmapPrdctDiscountPerUOM() != null && prdctModelArrayList.getHmapPrdctDiscountPerUOM().size() > 0 && prdctModelArrayList.getHmapPrdctDiscountPerUOM().containsKey(prdctId) && !TextUtils.isEmpty(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prdctId))) {
                            discPercentage = Double.parseDouble(prdctModelArrayList.getHmapPrdctDiscountPerUOM().get(prdctId));
                        }*/

                  /*  if(prdctModelArrayList.getHmapProductDiscount().containsKey(prdctId))
                    {
                        discPercentage=Double.parseDouble(prdctModelArrayList.getPrdctProductDiscount(prdctId));
                    }*/
                /*    Double totalVal = StandardRateBeforeTax * Double.parseDouble(prdctOrderQty);
                    double PrdMaxValuePercentageDiscountInAmount = totalVal * (discPercentage / 100);*/
                        double ActualRateAfterDiscountBeforeTax = (StandardRateBeforeTax ) - discPercentage;// StandardRateBeforeTax - (StandardRateBeforeTax * (((discPercentage / 100))));
                        ActualRateAfterDiscountBeforeTax = Double.parseDouble(new DecimalFormat("##.##").format(ActualRateAfterDiscountBeforeTax));
                        double DiscountAmount = StandardRateBeforeTax - ActualRateAfterDiscountBeforeTax;
                        double DiscAmtOnPreQtyBasic = DiscountAmount * Double.parseDouble(prdctOrderQtyTotQty);

                        double schemeDiscountVal=0.0;
                        if (prdctModelArrayList.getHmapPrdctIdPrdctDscnt().containsKey(prdctId)) {
                            schemeDiscountVal = Double.parseDouble(prdctModelArrayList.getHmapPrdctIdPrdctDscnt().get(prdctId));
                        }

                        //prdctModelArrayList.getHmapPrdctOverAllDiscountCalculated().put(prdctId,""+(DiscAmtOnPreQtyBasic+schemeDiscountVal));
                        Double totalValAfterDiscount = ((ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctOrderQtyTotQty))) / Double.parseDouble(prdctOrderQtyTotQty);
                        //totalValAfterDiscount = Double.parseDouble(new DecimalFormat("##.##").format(totalValAfterDiscount));
                        if (totalValAfterDiscount.isNaN())
                            totalValAfterDiscount = 0.0;

                        double ActualTax = totalValAfterDiscount * UOMMasterDAta.get(0).getVatTax();//   Double.parseDouble(hmapProductVatTaxPerventage.get(prdctId)) / 100);
                        if (Double.isNaN(ActualTax))
                            ActualTax = 0.0;


                        double ActualRateAfterDiscountAfterTax = totalValAfterDiscount + ActualTax;
                        ActualRateAfterDiscountAfterTax = Double.parseDouble(new DecimalFormat("##.##").format(ActualRateAfterDiscountAfterTax));
                        // Double OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQty);
                        Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctOrderQtyTotQty);
                        OrderValPrdQtyBasis = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));

                        double dis=0.0;
                        if (!TextUtils.isEmpty(etdisc.getText().toString().trim())) {
                            dis=Double.parseDouble(etdisc.getText().toString().trim());
                        }
                        double discToBeApplied=(dis*OrderValPrdQtyBasis)/OrderValPrdQtyBasis;
                       // if (UOMMasterDAta.get(0).getBUOMID() != 3) {
                            conversionUnit = (int) (UOMMasterDAta.get(0).getRelConversionUnit());
                            discToBeApplied=discToBeApplied*(Double.parseDouble(prdctOrderQtyTotQty)/conversionUnit);

                            if (!TextUtils.isEmpty(etdisc.getText().toString().trim())) {
                                //  discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                prdctModelArrayList.getHmapPrdctDiscountPerUOM().put(prdctId,""+(dis));
                              //  prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().put(prdctId,""+(Double.parseDouble(prdctOrderQtyTotQty)));

                                if(!disbefTextChange.equals(etdisc.getText().toString().trim()))
                                {
                                    prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().remove(prdctId);
                                }
                            }
                            else {
                                prdctModelArrayList.getHmapPrdctDiscountPerUOM().put(prdctId,"0");
                               // prdctModelArrayList.getHmapPrdctOrderQtyForDiscountApplyOverAll().put(prdctId,""+(Double.parseDouble(prdctOrderQtyTotQty)));
                            }


                        //}
                        /*if (UOMMasterDAta.get(0).getBUOMID() == 3) {
                            conversionUnit = (int) (UOMMasterDAta.get(0).getRelConversionUnit());
                            discToBeApplied=discToBeApplied*(Double.parseDouble(prdctOrderQtyTotQty));
                            discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                            if (!TextUtils.isEmpty(etdisc.getText().toString().trim())) {
                                discToBeApplied= Double.parseDouble(new DecimalFormat("##.###").format(discToBeApplied));
                                prdctModelArrayList.getHmapPrdctDiscountPerUOM().put(prdctId,""+discToBeApplied);
                            }
                        }*/


                     /*   Double DiscountValPrdQtyBasis = totalValAfterDiscount * Double.parseDouble(prdctOrderQty);

                        DiscAmtOnPreQtyBasic = Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasic));*/

                        //  DisVal.setText("\u20B9"+DiscAmtOnPreQtyBasic);
                        OrderValPrdQtyBasis=OrderValPrdQtyBasis-(discToBeApplied*Double.parseDouble(prdctOrderQtyTotQty));
                        Double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
                        txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);
                        prdctModelArrayList.setPrdctVal(prdctId, "" + OrderValPrdQtyBasis);
                    //}

                }

            }
        }
    }

    private boolean isDouble(String str) {
        try {
            // check if it can be parsed as any double
            double x = Double.parseDouble(str);
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    class FocusChangeList implements View.OnFocusChangeListener {
        TableLayout tbl;
        HashMap<String, ArrayList<TblSchemePerProduct>> hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew = new HashMap<String, ArrayList<TblSchemePerProduct>>();
        EditText etOrderQtyForFocus,et_DisValForFocus;
        int flgWhatBoxCliked=0;
        TextView tvTotal,  txtVwRate;

        public FocusChangeList(TableLayout tbl, HashMap<String, ArrayList<TblSchemePerProduct>> hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew,EditText etOrderQtyForFocus,EditText et_DisValForFocus,int flgWhatBoxCliked, TextView tvTotal, TextView txtVwRate) {
            this.tbl = tbl;
            this.hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew = hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew;
            this.etOrderQtyForFocus=etOrderQtyForFocus;
            this.et_DisValForFocus=et_DisValForFocus;
            this.flgWhatBoxCliked=flgWhatBoxCliked;
            this.tvTotal=tvTotal;
            this.txtVwRate=txtVwRate;

        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            EditText editText = (EditText) v;

            //  tbl.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            if (!hasFocus) {
                if (editText.getId() == R.id.et_OrderQty || editText.getId() == R.id.et_DisVal) {//
                    if (editText.getId() == R.id.et_OrderQty)
                    {

                        CartonOrderAdapterProdcutEntry.TextChangedListener textChangedListener = new CartonOrderAdapterProdcutEntry.TextChangedListener(etOrderQtyForFocus,tvTotal,txtVwRate,1,etOrderQtyForFocus);
                        hmapTextListeners.put(String.valueOf(editText.getTag()),textChangedListener);
                        ((EditText)v).addTextChangedListener(textChangedListener);
                        int intOldOdrQtynew=0;
                        if(
                                prdctModelArrayList.getLastEditText()!=null &&
                                        !TextUtils.isEmpty(prdctModelArrayList.getLastEditText().getText().toString()) &&
                                        prdctModelArrayList.getLastEditText().getId() == R.id.et_OrderQty &&
                                        prdctModelArrayList.getLastEditText().getTag().equals(editText.getTag().toString())
                        ) {
                            intOldOdrQtynew = Integer.parseInt(prdctModelArrayList.getLastEditText().getText().toString());
                            if(intOldOdrQtynew!=intOldOdrQty)
                            {
                                flgCallModelRefresh=1;
                            }
                        }

                    }
                    if(editText.getId() == R.id.et_DisVal)
                    {

                        if(editText.getId() == R.id.et_DisVal)
                        {
                            if (editText.getId() == R.id.et_DisVal) {

                                CartonOrderAdapterProdcutEntry.TextChangedListener textChangedListener = new CartonOrderAdapterProdcutEntry.TextChangedListener(etOrderQtyForFocus, tvTotal, txtVwRate, 4, etOrderQtyForFocus);
                                hmapTextListeners.put(String.valueOf(editText.getTag()), textChangedListener);
                                ((EditText)v).addTextChangedListener(textChangedListener);
                            }

                            if (TextUtils.isEmpty(editText.toString().trim())) {
                                prdctModelArrayList.getHmapPrdctDiscountPerUOM().remove(editText.getTag().toString().split(Pattern.quote("_"))[0]);
                                //  updateListDiscountModel(prdctModelArrayList);
                            }
                        }
                    }
                    prdctModelArrayList.setFocusLostEditText(etOrderQtyForFocus);
                    interfacefocusLostCalled.fcsLstCld(hasFocus, etOrderQtyForFocus);
                }
            } else {
                hideSoftKeyboard(editText);
                if (editText.getId() == R.id.et_OrderQty  || editText.getId() == R.id.et_DisVal) {

                    try {

                        AppUtils.hideKeyBoard(editText, context);

                    } catch (Exception ex) {

                    }

                    if (flgIsAnySchemeMappedAgainstStore == 1) {
                        if (hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew.containsKey(editText.getTag().toString().split(Pattern.quote("^"))[0])) {

                        } else {
                            tbl.setVisibility(View.GONE);
                        }
                    }
                    prdctModelArrayList.setLastEditText(etOrderQtyForFocus);
                    interfacefocusLostCalled.fcsLstCld(hasFocus, etOrderQtyForFocus);
                }
            }
        }
    }


    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
