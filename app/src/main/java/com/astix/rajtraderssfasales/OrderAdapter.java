package com.astix.rajtraderssfasales;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.adapter.SchemeSlabAdapter;
import com.astix.rajtraderssfasales.model.TblSchemePerProduct;
import com.astix.rajtraderssfasales.model.TblUOMMapping;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.OrderHistoryDialog;
import com.astix.rajtraderssfasales.utils.customLabelledSwitch.LabeledSwitch;
import com.astix.rajtraderssfasales.utils.customLabelledSwitch.OnToggledListener;
import com.astix.rajtraderssfasales.utils.customLabelledSwitch.ToggleableView;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


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
    ProductFilledDataModel prdctModelArrayList;
    focusLostCalled interfacefocusLostCalled;
    HashMap<String, String> hmapProductIdLastStock;
    LinkedHashMap<String, String> hampGetLastProductExecution;
    LinkedHashMap<String, Integer> hmapProductSuggestedQty;
    HashMap<String, Integer> hmapDistPrdctStockCount;
    HashMap<String, Integer> hmapVanPrdctStockCount;
    HashMap<String, String> hmapProductLODQty;
    HashMap<String, String> hmapProductRelatedSchemesList;
    Context context;
    String storeID;
    int flgEntryOrReview;
    int flgIsAnySchemeMappedAgainstStore = 0;
    InterfaceShowProductRelatedScheme interfaceShowProductRelatedScheme;

    public OrderAdapter(Context context, String storeID, String[] listProduct, LinkedHashMap<String, String> hmapFilterProductList, HashMap<String, String> hmapProductStandardRate, HashMap<String, String> hmapProductMRP, HashMap<String, String> hmapProductIdStock, HashMap<String, String> hmapProductIdLastStock, LinkedHashMap<String, String> hampGetLastProductExecution, HashMap<String, Integer> hmapDistPrdctStockCount, HashMap<String, Integer> hmapVanPrdctStockCount, ProductFilledDataModel prdctModelArrayList, int flgEntryOrReview, LinkedHashMap<String, String> hmapPerBaseQty, Map<Integer, List<TblUOMMapping>> hmapProductUOMMapping, HashMap<String, String> hmapProductLODQty, LinkedHashMap<String, Integer> hmapProductSuggestedQty, HashMap<String, String> hmapProductImg, HashMap<String, String> hmapProductRelatedSchemesList, int flgIsAnySchemeMappedAgainstStore) {
            interfacefocusLostCalled = (focusLostCalled) context;
        interfaceShowProductRelatedScheme = (InterfaceShowProductRelatedScheme) context;
        inflater = LayoutInflater.from(context);
        this.hmapProductSuggestedQty = hmapProductSuggestedQty;
        this.hmapProductUOMMapping = hmapProductUOMMapping;
        this.hmapProductImg = hmapProductImg;
        this.storeID = storeID;
        this.hmapPerBaseQty = hmapPerBaseQty;

        this.hmapFilterProductList = hmapFilterProductList;
        this.prdctModelArrayList = prdctModelArrayList;
        this.hmapProductStandardRate = hmapProductStandardRate;
        this.hmapProductMRP = hmapProductMRP;
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
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_order_example, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String prductName = listProduct[position].split(Pattern.quote("^"))[1];
        String prductId = listProduct[position].split(Pattern.quote("^"))[0];
        holder.tvLODQty.setVisibility(View.INVISIBLE);
        Glide.with(holder.ivProductImg).load(hmapProductImg.get(prductId)).error(R.drawable.no_img).into(holder.ivProductImg);

        if (hmapProductLODQty!=null && hmapProductLODQty.size()>0 && hmapProductLODQty.containsKey(prductId)) {
            if (!hmapProductLODQty.get(prductId).equals("NA/0")) {
                holder.tvLODQty.setText(hmapProductLODQty.get(prductId));
                holder.tvLODQty.setVisibility(View.VISIBLE);
            }
        }


        // holder.tv_SuggestedOrder.setText("" + tblStoreProductMapping.getSuggestedQty());

        holder.tv_SuggestedOrder.setText("" + 0);
        holder.tv_SuggestedOrder.setTag(prductId);
        if (hmapProductSuggestedQty!=null && hmapProductSuggestedQty.size()>0 && hmapProductSuggestedQty.containsKey(prductId)) {
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

        if (hmapProductRelatedSchemesList!=null && hmapProductRelatedSchemesList.size()>0 && hmapProductRelatedSchemesList.containsKey(prductId)) {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rvSchemeSlab.setLayoutManager(linearLayoutManager);

       if(prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode()!=null && prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode().containsKey(prductId)) {
           holder.rvSchemeSlab.setVisibility(View.VISIBLE);
           holder.rvSchemeSlab.setAdapter(new SchemeSlabAdapter(context, prdctModelArrayList.getArrListProductCurrentAppliedSchemeIDandDescrAndColorCode(prductId)));

       }

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
        if (hmapProductStandardRate != null && hmapProductStandardRate.containsKey(prductId) && Double.parseDouble(hmapProductStandardRate.get(prductId)) > 0.0)
            holder.txtVwRate.setText(hmapProductStandardRate.get(prductId));
        else
            holder.txtVwRate.setText(null);
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


        holder.tv_DisValTV.setText("₹" + 0);

        holder.tv_DisValTV.setTag(prductId + "_tvDisval");
        if (prdctModelArrayList.getHmapPrdctIdPrdctDscnt().containsKey(prductId)) {

            holder.tv_DisValTV.setText("₹" + prdctModelArrayList.getPrdctDisounctAmountAvailed(prductId));
        }

        holder.tv_FreeQty.setText("0");

        holder.tv_FreeQty.setTag(prductId + "_tvFreeQty");
        if (prdctModelArrayList.getHmapPrdctFreeQty().containsKey(prductId)) {

            holder.tv_FreeQty.setText(prdctModelArrayList.getPrdctFreeQtyAgainstProduct(prductId));
        }

        holder.swPcsToCase.setTag(prductId);

        holder.tvTotal.setTag(prductId + "_tvOrderval");

        holder.swPcsToCase.setOnToggledListener(new OnToggledListener() {
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
                        txtVwRate.setText(""+StandardRate);

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




                        Double PrdRateCases = StandardRate  * conversionUnit;
                        PrdRateCases = Double.parseDouble(new DecimalFormat("##.##").format(PrdRateCases));
                        TextView txtVwRate = holder.itemView.findViewWithTag(crntPrdID + "_etPrdRate");
                        txtVwRate.setText(""+PrdRateCases);

                        interfacefocusLostCalled.fcsLstCld(false, (EditText) holder.itemView.findViewWithTag(crntPrdID + "_etOrderQty"));

                    }
                }

            }
        });
        if (prdctModelArrayList.getHmapflgPicsOrCases().containsKey(prductId)) {
            if (prdctModelArrayList.getHmapflgPicsOrCases().get(prductId).equals("1")) {

                int conversionUnit = (int) (hmapProductUOMMapping.get(Integer.parseInt(prductId)).get(0).getRelConversionUnits());
                Double PrdRateCases = Double.parseDouble(hmapProductStandardRate.get(prductId))  * conversionUnit;
                PrdRateCases = Double.parseDouble(new DecimalFormat("##.##").format(PrdRateCases));
                holder.txtVwRate.setText(""+PrdRateCases);
                holder.swPcsToCase.setOn(false);
                // prdctModelArrayList.getHmapOrderQtyDataToShow().put(prductId,prdctModelArrayList.getPrdctOrderQty(prductId));
            }
        } else {
            prdctModelArrayList.setPrdctQtyMappedToPicsOrCases(prductId, "2");
        }

        if (prdctModelArrayList.getHmapOrderQtyDataToShow().containsKey(prductId)) {
            holder.etOrderQty.setText(prdctModelArrayList.getPrdctOrderQtyToShow(prductId));

        } else {
            holder.etOrderQty.setText("");

        }


        holder.tvTotal.setText("" + prdctModelArrayList.getPrdctOrderVal(prductId));

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
    }

    @Override
    public int getItemCount() {
        return listProduct.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProdctName, tv_SuggestedOrder, tvStock, tvLODQty, tvTotal, tvMRP, txtVwRate, tv_FreeQty;
        public TableLayout tbl;
        public LabeledSwitch swPcsToCase;
        public LinearLayout llHistory, llScheme, llCalc;
        public EditText etOrderQty;
        public ImageView ivProductImg;
        public RecyclerView rvSchemeSlab;
        TextView tv_DisValTV;
       /* public EditText et_ProductMRP;
        public View layout;
        public EditText et_OrderQty, et_Stock, et_StoreSale, et_LstStock;
        public TextView tv_Orderval;
        public LinearLayout ll_PrdctPage;*/

        public ViewHolder(View itemView) {
            super(itemView);
            tv_DisValTV = itemView.findViewById(R.id.tv_DisValTV);
            tvLODQty = itemView.findViewById(R.id.tvLODQty);
            swPcsToCase = itemView.findViewById(R.id.swPcsToCase);
            llHistory = itemView.findViewById(R.id.llHistory);
            llScheme = itemView.findViewById(R.id.llScheme);
            llCalc = itemView.findViewById(R.id.llCalc);
            ivProductImg = itemView.findViewById(R.id.ivProductImg);
            tv_SuggestedOrder = itemView.findViewById(R.id.tv_SuggestedOrder);
            tvTotal = itemView.findViewById(R.id.tv_Orderval);
            tvProdctName = itemView.findViewById(R.id.tvPrdName);
            etOrderQty = itemView.findViewById(R.id.et_OrderQty);
            tv_FreeQty = itemView.findViewById(R.id.tv_FreeQty);
            // tvMRP = itemView.findViewById(R.id.tvMRP);
            txtVwRate = itemView.findViewById(R.id.tvRate);
            tbl = itemView.findViewById(R.id.tbl);
            tvStock = itemView.findViewById(R.id.tvStock);
            rvSchemeSlab = itemView.findViewById(R.id.rvSchemeSlab);
            swPcsToCase.setLabelOn("Pcs");
            swPcsToCase.setLabelOff("Case");


            etOrderQty.setOnFocusChangeListener(new FocusChangeList(tbl, prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode()));

            etOrderQty.addTextChangedListener(new TextChangedListener(etOrderQty, tvTotal, txtVwRate, 1));

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
        EditText ediText;
        TextView txtOrderVal, txtVwRate;
        int flgClickBtn = 0;
        int upc;

        public TextChangedListener(EditText ediText, TextView txtOrderVal, TextView txtVwRateNew, int flgClickBtn) {//, int upc
            this.ediText = ediText;
            this.txtOrderVal = txtOrderVal;
            this.txtVwRate = txtVwRateNew;
            this.flgClickBtn = flgClickBtn;
            // this.upc = upc;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String prdctId = ediText.getTag().toString().split(Pattern.quote("_"))[0];
           /* if (flgClickBtn == 2) {
                if (!TextUtils.isEmpty(txtVwRate.getText().toString().trim()) && !txtVwRate.getText().toString().trim().equals("0") && !txtVwRate.getText().toString().trim().equals(".") && isDouble(txtVwRate.getText().toString().trim()) == true) {
                    Double PrdRateInput = Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(txtVwRate.getText().toString().trim())));
                    hmapProductStandardRate.put(prdctId, "" + PrdRateInput);

                    prdctModelArrayList.setPrdctRate(prdctId, "" + PrdRateInput);
                } else {
                    hmapProductStandardRate.put(prdctId, "0.0");

                    prdctModelArrayList.setPrdctRate(prdctId, "0.0");
                }
                if (!TextUtils.isEmpty(ediText.getText().toString().trim())) {

                    String prdctOrderQty = ediText.getText().toString().trim();

                    Double StandardRate = Double.parseDouble(hmapProductStandardRate.get(prdctId));

                    Double OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQty);
                    prdctModelArrayList.setPrdctVal(prdctId, "" + OrderValPrdQtyBasis);
                    Double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
                    txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);
                } else {
                    txtOrderVal.setText("0");
                }
            }
*/
            if (flgClickBtn == 1) {
                if (!TextUtils.isEmpty(ediText.getText().toString().trim())) {

                    String prdctOrderQty = ediText.getText().toString().trim();

                    prdctModelArrayList.getHmapOrderQtyDataToShow().put(prdctId, prdctOrderQty);

                    Double StandardRate = Double.parseDouble(hmapProductStandardRate.get(prdctId).toString());
                    Double OrderValPrdQtyBasis = (StandardRate * (Double.parseDouble(prdctOrderQty)));


                    if (prdctModelArrayList.getHmapflgPicsOrCases().containsKey(prdctId)) {
                        if (prdctModelArrayList.getHmapflgPicsOrCases().get(prdctId).equals("1")) {


                            if (!prdctOrderQty.equals("")) {

                                int conversionUnit = (int) (hmapProductUOMMapping.get(Integer.parseInt(prdctId)).get(0).getRelConversionUnits());
                                OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQty) * conversionUnit;

                                prdctModelArrayList.getHmapPrdctOrderQty().put(prdctId, "" + (Integer.parseInt(prdctOrderQty) * conversionUnit));


                                prdctModelArrayList.setPrdctVal("" + prdctId, "" + OrderValPrdQtyBasis);
                                Double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));

                                txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);


                            }
                        }

                        if (prdctModelArrayList.getHmapflgPicsOrCases().get(prdctId).equals("2")) {


                            OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQty);

                            prdctModelArrayList.getHmapPrdctOrderQty().put(prdctId, "" + (Integer.parseInt(prdctOrderQty)));


                            prdctModelArrayList.setPrdctVal("" + prdctId, "" + OrderValPrdQtyBasis);
                            Double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));

                            txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);


                        }
                    }


                    Log.e("prdctId insert", "" + prdctId);

                    prdctModelArrayList.setFocusLostEditText(ediText);
                } else {
                    Log.e("prdctId remove", "" + prdctId);
                    prdctModelArrayList.removePrdctQty(prdctId);
                    prdctModelArrayList.removePrdctVal(prdctId);
                    prdctModelArrayList.removeDisCountValue(prdctId);
                    txtOrderVal.setText("0");

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

        public FocusChangeList(TableLayout tbl, HashMap<String, ArrayList<TblSchemePerProduct>> hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew) {
            this.tbl = tbl;
            this.hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew = hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            EditText editText = (EditText) v;

            //  tbl.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            if (!hasFocus) {
                if (editText.getId() == R.id.et_OrderQty) {
                    prdctModelArrayList.setFocusLostEditText(editText);
                    interfacefocusLostCalled.fcsLstCld(hasFocus, editText);
                }
            } else {
                if (editText.getId() == R.id.et_OrderQty) {

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
                    prdctModelArrayList.setLastEditText(editText);
                    interfacefocusLostCalled.fcsLstCld(hasFocus, editText);
                }
            }
        }
    }
}
