package com.astix.rajtraderssfasales;

import static br.com.zbra.androidlinq.Linq.stream;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.model.TblProductLevelData;
import com.astix.rajtraderssfasales.model.TblSchemePerProduct;
import com.astix.rajtraderssfasales.model.TblStoreProductMappingForDisplay;
import com.astix.rajtraderssfasales.model.TblUOMMapping;
import com.astix.rajtraderssfasales.model.TblUOMMaster;
import com.astix.rajtraderssfasales.utils.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class OrderAdapterDistributor extends RecyclerView.Adapter<OrderAdapterDistributor.ViewHolder> {
    HashMap<String, TextChangedListener> hmapTextListeners = new HashMap<>();
    ArrayAdapter adapterCategory;
    private final LayoutInflater inflater;
    ProductFilledDataModel prdctModelArrayList;
    focusLostCalled interfacefocusLostCalled;
    Context context;
    List<TblStoreProductMappingForDisplay> mTblStoreProductMappings;
    HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMappingForCaseConversion;
    RecyclerView recyclerView;

    public OrderAdapterDistributor(Context context, ProductFilledDataModel prdctModelArrayList, List<TblStoreProductMappingForDisplay> tblStoreProductMappingList, HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMappingForCaseConversion ) {
        interfacefocusLostCalled = (focusLostCalled) context;
        this.prdctModelArrayList = prdctModelArrayList;
        this.mTblStoreProductMappings = tblStoreProductMappingList;
        this.hmapProductTblUOMMappingForCaseConversion=hmapProductTblUOMMappingForCaseConversion;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_distributor_order, parent, false);

        return new ViewHolder(view);
    }

    public void updateList(List<TblStoreProductMappingForDisplay> mTblStoreProductMappings) {
        this.mTblStoreProductMappings = mTblStoreProductMappings;
        recyclerView.post(this::notifyDataSetChanged);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TblStoreProductMappingForDisplay tblStoreProductMapping = mTblStoreProductMappings.get(holder.getAdapterPosition());
        String prductName = tblStoreProductMapping.getPrdName();// listProduct[position].split(Pattern.quote("^"))[1];
        String prductId = "" + tblStoreProductMapping.getPrdNodeID();// listProduct[position].split(Pattern.quote("^"))[0];

        holder.ll_HeaderSection.setVisibility(View.GONE);

        if (tblStoreProductMapping.getFlgShowCategoryHeader() == 1) {
            holder.ll_HeaderSection.setVisibility(View.VISIBLE);
        } else {
            holder.ll_HeaderSection.setVisibility(View.GONE);
        }

        holder.tvSKUType.setText(tblStoreProductMapping.getCategoryDesc());

        holder.tvProdctName.setText(prductName);
        holder.etOrderQty.setTag(prductId + "_etOrderQty");
        holder.spinnerUnit.setTag(prductId + "^" + position);
        List<String> UOMMasterDAta = stream(tblStoreProductMapping.getTblUOMMasterList()).select(p -> p.getBUOMName()).toList();

        adapterCategory = new ArrayAdapter(context, R.layout.spinner_item_3, UOMMasterDAta);
        adapterCategory.setDropDownViewResource(R.layout.spina2);
        holder.spinnerUnit.setAdapter(adapterCategory);
        int flgIndex = 0;

        for (int ji = 0; ji < tblStoreProductMapping.getTblUOMMasterList().size(); ji++) {
            if (tblStoreProductMapping.getTblUOMMasterList().get(ji).getFlgSelected() == 1) {
                flgIndex = ji;
                break;
            }
        }

        holder.spinnerUnit.setSelection(flgIndex);

//        if (!TextUtils.isEmpty(prdctModelArrayList.getPrdctOrderQty(prductId))) {
//            holder.etOrderQty.setText(prdctModelArrayList.getPrdctOrderQty(prductId));
        if (prdctModelArrayList.getHmapOrderQtyDataToShow().containsKey(prductId)) {
            holder.etOrderQty.setText(prdctModelArrayList.getPrdctOrderQtyToShow(prductId));
        } else {
            holder.etOrderQty.setText("");
        }

        holder.etOrderQty.setEnabled(true);
        holder.spinnerUnit.setEnabled(true);

        holder.spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                Spinner v1 = (Spinner) view.getParent();
                String crntPrdID = v1.getTag().toString().trim().split(Pattern.quote("^"))[0];
                int i = Integer.parseInt(v1.getTag().toString().trim().split(Pattern.quote("^"))[1]);

                List<TblUOMMaster> UOMMasterDAtaSingleDat = stream(mTblStoreProductMappings.get(i).getTblUOMMasterList()).where(p -> p.getBUOMName().equals(v1.getSelectedItem().toString())).toList();
                prdctModelArrayList.setPrdctQtyMappedToPicsOrCases(crntPrdID, "" + UOMMasterDAtaSingleDat.get(0).getBUOMID());

                List<TblUOMMaster> tblUOMMasterListAgainstProduct = mTblStoreProductMappings.get(i).getTblUOMMasterList();
                for (TblUOMMaster tblUOMMaster : tblUOMMasterListAgainstProduct) {
                    tblUOMMaster.setFlgSelected(0);
                    if (tblUOMMaster.getBUOMID() == Integer.parseInt(prdctModelArrayList.getHmapflgPicsOrCases().get("" + crntPrdID))) {
                        tblUOMMaster.setFlgSelected(1);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.etOrderQty.setOnFocusChangeListener(new FocusChangeList(prdctModelArrayList.getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(), holder.etOrderQty, 1));
        TextChangedListener textChangedListenerOrderQty = new TextChangedListener(holder.etOrderQty, 1);
        holder.etOrderQty.addTextChangedListener(textChangedListenerOrderQty);

        hmapTextListeners.put(String.valueOf(holder.etOrderQty.getTag()), textChangedListenerOrderQty);

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

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProdctName, tvSKUType;
        public EditText etOrderQty;
        public Spinner spinnerUnit;
        LinearLayout ll_HeaderSection;

        public ViewHolder(View itemView) {
            super(itemView);
            spinnerUnit = itemView.findViewById(R.id.spinnerUnit);
            tvSKUType = itemView.findViewById(R.id.tvSKUType);
            tvProdctName = itemView.findViewById(R.id.tvPrdName);
            etOrderQty = itemView.findViewById(R.id.et_OrderQty);
            ll_HeaderSection = itemView.findViewById(R.id.ll_HeaderSection);

        }
    }

    class TextChangedListener implements TextWatcher {
        EditText ediText;
        int flgClickBtn;

        public TextChangedListener(EditText ediText, int flgClickBtn) {//, int upc
            this.ediText = ediText;
            this.flgClickBtn = flgClickBtn;
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
//            if (flgClickBtn == 1) {

            if (!TextUtils.isEmpty(ediText.getText().toString().trim())) {
                String prdctOrderQty = ediText.getText().toString().trim();


                Double otqy=Double.parseDouble(prdctOrderQty);

                if(hmapProductTblUOMMappingForCaseConversion!=null && hmapProductTblUOMMappingForCaseConversion.size()>0 && hmapProductTblUOMMappingForCaseConversion.containsKey(Integer.parseInt(prdctId)))
                {
                    List<TblUOMMapping> tblUOMMappings= hmapProductTblUOMMappingForCaseConversion.get(Integer.parseInt(prdctId));
                    otqy=otqy*tblUOMMappings.get(0).getRelConversionUnits();

                }


                prdctModelArrayList.getHmapPrdctOrderQty().put(prdctId, ""+(int)(otqy.intValue()));
                prdctModelArrayList.getHmapOrderQtyDataToShow().put(prdctId, prdctOrderQty);
            } else {
                prdctModelArrayList.removePrdctQty(prdctId);
                prdctModelArrayList.getHmapOrderQtyDataToShow().remove(prdctId);
            }
//            }
        }
    }

    class FocusChangeList implements View.OnFocusChangeListener {
        HashMap<String, ArrayList<TblSchemePerProduct>> hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew;
        EditText etOrderQtyForFocus;
        int flgWhatBoxCliked;

        public FocusChangeList(HashMap<String, ArrayList<TblSchemePerProduct>> hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew,
                               EditText etOrderQtyForFocus, int flgWhatBoxCliked) {
            this.hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew = hmapProductCurrentAppliedSchemeIDandDescrAndColorCodenew;
            this.etOrderQtyForFocus = etOrderQtyForFocus;
            this.flgWhatBoxCliked = flgWhatBoxCliked;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            EditText editText = (EditText) v;

            if (!hasFocus) {
                if (editText.getId() == R.id.et_OrderQty) {
//                    TextChangedListener textChangedListener = new TextChangedListener(etOrderQtyForFocus, 1);
//                    hmapTextListeners.put(String.valueOf(editText.getTag()), textChangedListener);
//                    ((EditText) v).addTextChangedListener(textChangedListener);

                    prdctModelArrayList.setFocusLostEditText(etOrderQtyForFocus);
                    interfacefocusLostCalled.fcsLstCld(hasFocus, etOrderQtyForFocus);
                }
            } else {
                hideSoftKeyboard(editText);
                if (editText.getId() == R.id.et_OrderQty) {
                    try {
                        AppUtils.hideKeyBoard(editText, context);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

//                    if (hmapTextListeners.containsKey(etOrderQtyForFocus.getTag()))
//                        etOrderQtyForFocus.removeTextChangedListener(hmapTextListeners.get(etOrderQtyForFocus.getTag()));

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
