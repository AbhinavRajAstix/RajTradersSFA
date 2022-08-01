package com.astix.rajtraderssfasales.adapter;


import android.os.AsyncTask;
import android.os.Handler;
import android.content.Context;
import android.os.Environment;
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

import androidx.recyclerview.widget.RecyclerView;

import com.allana.truetime.TimeUtils;
import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.CustomKeyboard;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.ActualVisitProductInfo;
import com.astix.rajtraderssfasales.model.TblUOMMapping;
import com.astix.rajtraderssfasales.model.TblUOMMaster;
import com.astix.rajtraderssfasales.utils.AppUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static br.com.zbra.androidlinq.Linq.stream;

public class CustomVisitStockAdapterMandotry extends RecyclerView.Adapter<CustomVisitStockAdapterMandotry.ViewHolder> {

    private CustomKeyboard customKeyboard;
    private Context mContext;
    private List<ActualVisitProductInfo> productInfoList;
    private String storeId;
    private Map<String, ActualVisitProductInfo> hmapFetchPDASavedDataState = new HashMap<>();
    private boolean isDefaultProducts = false;
    private Map<String, ActualVisitProductInfo> hmapFetchPDASavedData;
    private static final String TAG = CustomVisitStockAdapterMandotry.class.getName();
    private EditTextClickListenerMandatory editTextClickListener;
    HashMap<Integer, List<TblUOMMapping>> tblUOMListMaster;
    String[] UOM_names;
    ArrayAdapter adapterCategory;

    public CustomVisitStockAdapterMandotry(Context mContext, List<ActualVisitProductInfo> productInfoList, String storeId, Map<String, ActualVisitProductInfo> hmapFetchPDASavedData, boolean isDefaultProducts, CustomKeyboard customKeyboard, HashMap<Integer, List<TblUOMMapping>> tblUOMListMaster, String[] UOM_names) {
        this.mContext = mContext;
        this.productInfoList = productInfoList;
        this.storeId = storeId;
        this.hmapFetchPDASavedData = hmapFetchPDASavedData;
        this.isDefaultProducts = isDefaultProducts;
        this.customKeyboard = customKeyboard;
        this.tblUOMListMaster = tblUOMListMaster;
        this.UOM_names = UOM_names;
        editTextClickListener = (EditTextClickListenerMandatory) mContext;
        //  customKeyboard = new CustomKeyboard((Activity) mContext,R.id.keyboardviewNum,R.xml.num);
        Log.d(TAG, "productInfoList :" + this.productInfoList.toString());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inflate_row_actual_visit, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
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
        if(position< productInfoList.size())
        {
            ActualVisitProductInfo productInfo = productInfoList.get(position);
            hmapFetchPDASavedDataState.put("" + holder.getAdapterPosition(), productInfo);
            holder.productName.setText(productInfo.getProductName());

            holder.stockValueET.setTag(productInfo.getProductId()+"^"+holder.getAdapterPosition());

            if (productInfo.getStock() != null) {
                if (productInfo.getStock().equals("-1")) {
                    holder.stockValueET.setText("");
                } else {
                    holder.stockValueET.setText(productInfo.getStock());
                }
            } else {
                holder.stockValueET.setText("");
            }

            holder.stockValueET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(final View v, boolean b) {
                    try
                    {
                        if (v instanceof EditText) {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        InputMethodManager keyboard = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                                        keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                    }
                                    catch (Exception ex)
                                    {
                                        fnWriteLogFileHereAdapter(ex.toString());
                                    }
                                }
                            });


                            editTextClickListener.onClick((EditText) v);
                        }
                    }
                    catch (Exception ex)
                    {
                        fnWriteLogFileHereAdapter(ex.toString());
                    }

                }
            });

    /*    new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {

                return false;
            }
        });*/
        /*holder.stockValueET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, boolean hasFocus) {
                if (hasFocus) {
                    //EditText editText = (EditText) recyclerView.getLayoutManager().findViewByPosition(holder.getAdapterPosition()).findViewById(R.id.et_stckVal);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager keyboard = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                            keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);

                            editTextClickListener.onClick((EditText) v);

                        }
                    });

                }
            }
        });*//*
        holder.stockValueET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = recyclerView.getLayoutManager().findViewByPosition(holder.getAdapterPosition()).findViewById(R.id.et_stckVal);
                customKeyboard.showCustomKeyboard(view);
            }
        });*/

            holder.stockValueET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try
                    {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    ActualVisitProductInfo visitProductInfo = null;
                                    if (!TextUtils.isEmpty(holder.stockValueET.getText().toString().trim())) {
                                        //String tagProductId = holder.stockValueET.getTag().toString().split(Pattern.quote("_"))[0];
                                        String stock = holder.stockValueET.getText().toString().trim();
                                        visitProductInfo = hmapFetchPDASavedDataState.get("" + holder.getAdapterPosition());
                                        visitProductInfo.setStock(stock);

                                        hmapFetchPDASavedData.put(visitProductInfo.getProductId(), visitProductInfo);
                                        hmapFetchPDASavedDataState.put("" + holder.getAdapterPosition(), visitProductInfo);
                                    } else {
                                        visitProductInfo = hmapFetchPDASavedDataState.get("" + holder.getAdapterPosition());
                                        visitProductInfo.setStock("");

                                        hmapFetchPDASavedDataState.put("" + holder.getAdapterPosition(), visitProductInfo);
                                        hmapFetchPDASavedData.remove(visitProductInfo.getProductId());
                                    }
                                }
                                catch (Exception ex)
                                {
                                    fnWriteLogFileHereAdapter(ex.toString());
                                }

                            }
                        });
                    }
                    catch (Exception ex)
                    {
                        fnWriteLogFileHereAdapter(ex.toString());
                    }

                }
            });


            holder.spinnerUnit.setTag(productInfo.getProductId() + "^" + position);

            List<String> UOMMasterDAta = stream(productInfo.getTblUOMMasterList()).select(p -> p.getBUOMName()).toList();
            if(productInfo.getTblUOMMasterList()!=null && productInfo.getTblUOMMasterList().size()>0) {
                adapterCategory = new ArrayAdapter(mContext, R.layout.spinner_item_3, UOMMasterDAta);
                adapterCategory.setDropDownViewResource(R.layout.spina2);
                holder.spinnerUnit.setAdapter(adapterCategory);

            }
            else
            {
                UOMMasterDAta.add("Pcs");
                adapterCategory = new ArrayAdapter(mContext, R.layout.spinner_item_3, UOMMasterDAta);
                adapterCategory.setDropDownViewResource(R.layout.spina2);
                holder.spinnerUnit.setAdapter(adapterCategory);
            }
            List<TblUOMMaster> tblUOMMastersForPrd = stream(productInfo.getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();

            int index = 0;
            if (tblUOMListMaster != null && tblUOMListMaster.size() > 0) {
                try
                {
                    Set set2 = tblUOMListMaster.entrySet();
                    Iterator iterator = set2.iterator();
                    boolean isSelected = false;
                    while (iterator.hasNext()) {
                        Map.Entry me2 = (Map.Entry) iterator.next();
                        //if (prdctModelArrayList.getHmapflgPicsOrCases().get(prductId).equals(me2.getValue())) {
                        if (tblUOMMastersForPrd.size()>0 && tblUOMMastersForPrd.get(0).getBUOMName().equals(me2.getValue())) {
                            isSelected = true;
                            break;
                        }
                        index = index + 1;
                    }
                    if (isSelected) {
                        holder.spinnerUnit.setSelection(index);
                    } else {
                        holder.spinnerUnit.setSelection(0);
                        // spinner_RouteChangeListOptions.setSelection(0);
                    }
                }
                catch (Exception ex)
                {
                    fnWriteLogFileHereAdapter(ex.toString());
                }

            }
            holder.spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                    Spinner v1 = (Spinner) view.getParent();
                    int i = Integer.parseInt(v1.getTag().toString().trim().split(Pattern.quote("^"))[1]);

                    productInfoList.get(i).setDisplayUnit(v1.getSelectedItem().toString());
//                Spinner v1 = (Spinner) view.getParent();
//                TableLayout v2 = (TableLayout) v1.getParent().getParent();
//                String crntPrdID = v1.getTag().toString().trim().split(Pattern.quote("^"))[0];
//                int i = Integer.parseInt(v1.getTag().toString().trim().split(Pattern.quote("^"))[1]);
//                List<TblUOMMaster> UOMMasterDAtaSingleDat = stream(mTblStoreProductMappings.get(i).getTblUOMMasterList()).where(p -> p.getBUOMName().equals(v1.getSelectedItem().toString())).toList();
//                prdctModelArrayList.setPrdctQtyMappedToPicsOrCases(crntPrdID, "" + UOMMasterDAtaSingleDat.get(0).getBUOMID());
//                //Will change to dropdown selected  OptionID
//
//                List<TblUOMMaster> tblUOMMasterListAgainstProduct = mTblStoreProductMappings.get(i).getTblUOMMasterList();
//                for (TblUOMMaster tblUOMMaster : tblUOMMasterListAgainstProduct) {
//                    tblUOMMaster.setFlgSelected(0);
//                    if (tblUOMMaster.getBUOMID() == Integer.parseInt(prdctModelArrayList.getHmapflgPicsOrCases().get("" + crntPrdID))) {
//                        tblUOMMaster.setFlgSelected(1);
//                    }
//                }
//
//
//                List<TblUOMMaster> tblUOMMastersForPrd = stream(mTblStoreProductMappings.get(i).getTblUOMMasterList()).where(p -> p.getFlgSelected() == 1).toList();
//                String prdctOrderQtyEntered = prdctModelArrayList.getPrdctOrderQtyToShow("" + crntPrdID);
//                double StandardRate = tblUOMMastersForPrd.get(0).getStandardRate();
//                Double PrdRateCases = tblUOMMastersForPrd.get(0).getStandardRate();// * conversionUnit;
//                PrdRateCases = Double.parseDouble(new DecimalFormat("##.##").format(PrdRateCases));
//                TextView txtVwRate = v2.findViewWithTag(crntPrdID + "_etPrdRate");
//                txtVwRate.setText("" + PrdRateCases);
//
//                int conversionUnit = 1;
//                if (UOMMasterDAtaSingleDat.get(0).getBUOMID() != 3) {
//                    conversionUnit = (int) (tblUOMMastersForPrd.get(0).getRelConversionUnit());
//                }
//
//                if (!prdctOrderQtyEntered.equals("")) {
//
//
//                    Double OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQtyEntered);
//
//                    prdctModelArrayList.setPrdctVal("" + crntPrdID, "" + OrderValPrdQtyBasis);
//                    prdctModelArrayList.getHmapOrderQtyDataToShow().put(crntPrdID, prdctOrderQtyEntered);
//                    prdctModelArrayList.getHmapPrdctOrderQty().put(crntPrdID, "" + (Integer.parseInt(prdctOrderQtyEntered) * conversionUnit));
//                    double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
//                    TextView txtOrderVal = v2.findViewWithTag(crntPrdID + "_tvOrderval");
//                    txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);
//                    //interfacefocusLostCalled.fcsLstCld(false, (EditText) v2.findViewWithTag(crntPrdID + "_etOrderQty"));
//
//                } else {
//                    Double OrderValPrdQtyBasis = StandardRate * 0;
//
//                    prdctModelArrayList.setPrdctVal("" + crntPrdID, "" + OrderValPrdQtyBasis);
//                    prdctModelArrayList.getHmapOrderQtyDataToShow().get(crntPrdID);
//                    prdctModelArrayList.getHmapPrdctOrderQty().remove(crntPrdID);
//                    double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
//                    TextView txtOrderVal = v2.findViewWithTag(crntPrdID + "_tvOrderval");
//                    txtOrderVal.setText("" + OrderValPrdQtyBasisToDisplay);
//                }
//
//                interfacefocusLostCalled.fcsLstCld(false, (EditText) v2.findViewWithTag(crntPrdID + "_etOrderQty"));

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    public void refreshData(ArrayList<ActualVisitProductInfo> updatedActualVisitProductInfos) {
        if(updatedActualVisitProductInfos!=null && updatedActualVisitProductInfos.size()>0)
        {
            for (ActualVisitProductInfo visitProductInfo : updatedActualVisitProductInfos) {
                if (hmapFetchPDASavedData!=null && hmapFetchPDASavedData.size()>0 && hmapFetchPDASavedData.containsKey(visitProductInfo.getProductId())) {
                    visitProductInfo.setStock(hmapFetchPDASavedData.get(visitProductInfo.getProductId()).getStock());
                }
            }
            this.productInfoList = updatedActualVisitProductInfos;

        }
        try {
            hmapFetchPDASavedDataState.clear();
            notifyDataSetChanged();
        }
        catch (Exception ex)
        {
            fnWriteLogFileHereAdapter(ex.toString());
        }
    }

    @Override
    public int getItemCount() {



        if (productInfoList!= null)
            return productInfoList.size();
        else
            return 0;
    }



    public interface EditTextClickListenerMandatory {
        void onClick(EditText editText);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private EditText stockValueET;
      //  private TextView stockUnitET;
        private TextView productName;
        public Spinner spinnerUnit;
        public ViewHolder(final View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.tvPrdName);
            stockValueET = (EditText) itemView.findViewById(R.id.et_stckVal);
            spinnerUnit = itemView.findViewById(R.id.spinnerUnit);

            stockValueET.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* InputMethodManager keyboard = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
*/

                    try {
                        InputMethodManager keyboard = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    }
                    catch (Exception ex)
                    {
                        fnWriteLogFileHereAdapter(ex.toString());
                    }
                }
            });
        }
    }


    public void fnWriteLogFileHereAdapter(String crashString)
    {

        long StartClickTime = System.currentTimeMillis();
        Date dateobj1 = new Date(StartClickTime);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String StartClickTimeFinal = TimeUtils.getNetworkDateTime(mContext, TimeUtils.DATE_TIME_FORMAT);


        File RajTextFileFolderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.TextFileFolderCrashDetails);

        if (!RajTextFileFolderXMLFolder.exists()) {
            RajTextFileFolderXMLFolder.mkdirs();
        }
        String fileName = AppUtils.getToken(mContext) + "_" + storeId;

        File file = new File(Environment.getExternalStorageDirectory() + "/" + CommonInfo.TextFileFolderCrashDetails + "/" + fileName+".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();

            }
        }
        CommonInfo.fileContent = CommonInfo.fileContent + " _ "+StartClickTimeFinal +"        " + "_" + storeId + " _ " + crashString ;
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