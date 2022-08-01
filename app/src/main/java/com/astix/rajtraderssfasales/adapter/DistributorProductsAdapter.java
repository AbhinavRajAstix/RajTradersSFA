package com.astix.rajtraderssfasales.adapter;

import static br.com.zbra.androidlinq.Linq.stream;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Editable;
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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.CustomKeyboard;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.ActualVisitProductInfo;
import com.astix.rajtraderssfasales.model.TblDistributorDayReport;
import com.astix.rajtraderssfasales.model.TblDistributorProductStock;
import com.astix.rajtraderssfasales.model.TblUOMMapping;
import com.astix.rajtraderssfasales.model.TblUOMMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class DistributorProductsAdapter extends RecyclerView.Adapter<DistributorProductsAdapter.ViewHolder> {

    private CustomKeyboard customKeyboard;
    private Context mContext;
    private List<TblDistributorDayReport> productInfoList;
    private String storeId;
    private Map<String, ActualVisitProductInfo> hmapFetchPDASavedDataState = new HashMap<>();
    private boolean isDefaultProducts = false;
    private Map<String, ActualVisitProductInfo> hmapFetchPDASavedData;
    private static final String TAG = DistributorProductsAdapter.class.getName();
    private EditTextClickListener editTextClickListener;
    HashMap<Integer, List<TblUOMMapping>> tblUOMListMaster;
    String[] prdName, UOM_names;
    ArrayAdapter adapterCategory;

    public DistributorProductsAdapter(Context mContext, List<TblDistributorDayReport> productInfoList, String[] prdName) {
//    public DistributorProductsAdapter(Context mContext, List<ActualVisitProductInfo> productInfoList, CustomKeyboard customKeyboard, HashMap<Integer, List<TblUOMMapping>> tblUOMListMaster, String[] UOM_names) {
        this.mContext = mContext;
        this.prdName = prdName;
        this.productInfoList = productInfoList;
        Log.d(TAG, "productInfoList :" + this.productInfoList.toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_distributor_stock, parent, false);
        return new ViewHolder(view);
    }

    RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        //recyclerView.getLayoutManager().findViewByPosition()
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        TblDistributorDayReport distributorDayReport = productInfoList.get(position);
//        hmapFetchPDASavedDataState.put("" + holder.getAdapterPosition(), productInfo);
        holder.txt_forBtn_OldStck.setText(prdName[position]);


//        holder.stockUnitET.setText(productInfo.getDisplayUnit());


//        if (hmapFetchPDASavedDataState.get("" + holder.getAdapterPosition()).getStock() != null) {
//            if (hmapFetchPDASavedDataState.get("" + holder.getAdapterPosition()).getStock().equals("-1")) {
//                holder.stockValueET.setText("");
//            } else {
//                holder.stockValueET.setText(hmapFetchPDASavedDataState.get("" + holder.getAdapterPosition()).getStock());
//            }
//        } else {
//            holder.stockValueET.setText("");
//        }

//        holder.stockValueET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(final View v, boolean b) {
//                if (v instanceof EditText) {
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            InputMethodManager keyboard = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                            keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                        }
//                    });
//
//                    editTextClickListener.onClick((EditText) v);
//                }
//            }
//        });

//        holder.stockValueET.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        ActualVisitProductInfo visitProductInfo = null;
//                        if (!TextUtils.isEmpty(holder.stockValueET.getText().toString().trim())) {
//                            //String tagProductId = holder.stockValueET.getTag().toString().split(Pattern.quote("_"))[0];
//                            String stock = holder.stockValueET.getText().toString().trim();
//                            visitProductInfo = hmapFetchPDASavedDataState.get("" + holder.getAdapterPosition());
//                            visitProductInfo.setStock(stock);
//
//                            hmapFetchPDASavedData.put(visitProductInfo.getProductId(), visitProductInfo);
//                            hmapFetchPDASavedDataState.put("" + holder.getAdapterPosition(), visitProductInfo);
//                        } else {
//                            visitProductInfo = hmapFetchPDASavedDataState.get("" + holder.getAdapterPosition());
//                            visitProductInfo.setStock("");
//
//                            hmapFetchPDASavedDataState.put("" + holder.getAdapterPosition(), visitProductInfo);
//                            hmapFetchPDASavedData.remove(visitProductInfo.getProductId());
//                        }
//                    }
//                });
//            }
//        });

//        List<String> UOMMasterDAta = stream(productInfo.getTblUOMMasterList()).select(p -> p.getBUOMName()).toList();
//        holder.spinnerUnit.setTag(productInfo.getProductId() + "^" + position);


//        adapterCategory = new ArrayAdapter(mContext, R.layout.spinner_item_3, UOMMasterDAta);
//        adapterCategory.setDropDownViewResource(R.layout.spina2);
//        holder.spinnerUnit.setAdapter(adapterCategory);


//        List<TblUOMMaster> tblUOMMastersForPrd = stream(productInfo.getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
        int index = 0;
        if (tblUOMListMaster != null && tblUOMListMaster.size() > 0) {
            Set set2 = tblUOMListMaster.entrySet();
            Iterator iterator = set2.iterator();
            boolean isSelected = false;
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                //if (prdctModelArrayList.getHmapflgPicsOrCases().get(prductId).equals(me2.getValue())) {
//                if (tblUOMMastersForPrd.get(0).getBUOMName().equals(me2.getValue())) {
//                    isSelected = true;
//                    break;
//                }
                index = index + 1;
            }
            if (isSelected) {
                holder.spinnerUnit.setSelection(index);
            } else {
                holder.spinnerUnit.setSelection(0);
                // spinner_RouteChangeListOptions.setSelection(0);
            }
        }


        holder.spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                Spinner v1 = (Spinner) view.getParent();
                int i = Integer.parseInt(v1.getTag().toString().trim().split(Pattern.quote("^"))[1]);
//                productInfoList.get(i).setDisplayUnit(v1.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void refreshData(ArrayList<ActualVisitProductInfo> updatedActualVisitProductInfos) {
//        for (ActualVisitProductInfo visitProductInfo : updatedActualVisitProductInfos) {
//            if (hmapFetchPDASavedData.containsKey(visitProductInfo.getProductId())) {
//                visitProductInfo.setStock(hmapFetchPDASavedData.get(visitProductInfo.getProductId()).getStock());
//            }
//        }
//        this.productInfoList = updatedActualVisitProductInfos;
//        hmapFetchPDASavedDataState.clear();
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return prdName.length;
    }

    public interface EditTextClickListener {
        void onClick(EditText editText);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private EditText edittxt_mnth5, edittxt_mnth4, edittxt_mnth3, edittxt_mnth2, edittxt_mnth1, edittxt_freeStck, edittxt_sampleStck;
        private TextView txt_forBtn_OldStck;
        public Spinner spinnerUnit;

        public ViewHolder(final View view1) {
            super(view1);
            edittxt_mnth5 = (EditText) view1.findViewById(R.id.edittxt_mnth5);
            edittxt_mnth4 = (EditText) view1.findViewById(R.id.edittxt_mnth4);
            edittxt_mnth3 = (EditText) view1.findViewById(R.id.edittxt_mnth3);
            edittxt_mnth2 = (EditText) view1.findViewById(R.id.edittxt_mnth2);
            edittxt_mnth1 = (EditText) view1.findViewById(R.id.edittxt_mnth1);
            edittxt_freeStck = (EditText) view1.findViewById(R.id.edittxt_freeStck);
            edittxt_sampleStck = (EditText) view1.findViewById(R.id.edittxt_sampleStck);
            spinnerUnit = view1.findViewById(R.id.spinnerUnit);
            txt_forBtn_OldStck = (TextView) view1.findViewById(R.id.txt_forBtn_OldStck);

//            edittxt_sampleStck.setOnClickListener(v -> {
//                InputMethodManager keyboard = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
//            });
        }
    }
}
