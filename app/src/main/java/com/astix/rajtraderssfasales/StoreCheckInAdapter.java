package com.astix.rajtraderssfasales;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astix.rajtraderssfasales.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class StoreCheckInAdapter extends RecyclerView.Adapter<StoreCheckInAdapter.ViewHolder> {

    LinkedHashMap<String, String> hmapFilterProductList ;

    focusLostCalled interfacefocusLostCalled;
    String[] listProduct;
    private LayoutInflater inflater;
    StockFilledDataModel stockFilledDataModel;
    InRefStockFilledDataModel refStockFilledDataModel;
    int flg_Stock=0;
    HashMap<String,Integer> hashMapIsRef=new HashMap<>();
    CustomKeyboard mCustomKeyboardNumWithoutDecimal;


    public StoreCheckInAdapter(Context context, String[] listProduct, LinkedHashMap<String, String> hmapFilterProductList, StockFilledDataModel stockFilledDataModel,int flg_Stock,HashMap<String,Integer> hashMapIsRef,CustomKeyboard mCustomKeyboardNumWithoutDecimal)
    {

        inflater = LayoutInflater.from(context);
        interfacefocusLostCalled= (focusLostCalled) context;
        this.hmapFilterProductList=hmapFilterProductList;
        this.stockFilledDataModel=stockFilledDataModel;
        this.listProduct=listProduct;
        this.flg_Stock=flg_Stock;
        this.hashMapIsRef=hashMapIsRef;
        this.mCustomKeyboardNumWithoutDecimal=mCustomKeyboardNumWithoutDecimal;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.inflate_row_actual_visit, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if ((position + 1) % 2 == 0) {
            holder.ll_PrdctPage.setBackgroundResource(R.drawable.card_background);
        } else {
            holder.ll_PrdctPage.setBackgroundResource(R.drawable.card_background_white);
        }
        String prductId = listProduct[position].split(Pattern.quote("^"))[0];
        String prductName = listProduct[position].split(Pattern.quote("^"))[1];
        holder.prdName.setText(prductName);

        if(flg_Stock==1)
        {
            String productID=prductId+"^" + flg_Stock;
            holder.et_stckVal.setTag(productID + "_etStock");
            holder.et_stckVal.setText(stockFilledDataModel.getPrdctStock(productID));

        }
        else if(flg_Stock==2)
        {
            String productID=prductId+"^" + flg_Stock;
            holder.et_stckVal.setTag(productID + "_etStock");
            holder.et_stckVal.setText(stockFilledDataModel.getPrdctStock(productID));
        }


    }

    @Override
    public int getItemCount() {
        return listProduct.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        public View layout;
        public EditText et_stckVal;
        public TextView prdName;
        public LinearLayout ll_PrdctPage;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            prdName = (TextView) itemView.findViewById(R.id.tvPrdName);

            et_stckVal = (EditText) itemView.findViewById(R.id.et_stckVal);
            et_stckVal.setOnFocusChangeListener(new StoreCheckInAdapter.FocusChangeList());
            ll_PrdctPage= (LinearLayout) itemView.findViewById(R.id.ll_inflate);
           /* mCustomKeyboardNumWithoutDecimal.registerEditText(et_stckVal);
            View et=(View) et_stckVal;
            mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(et);*/


        }
    }

    class TextChangedListener implements TextWatcher
    {
        EditText ediText;

        public TextChangedListener(EditText ediText)
        {
            this.ediText=ediText;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            String prdctId=ediText.getTag().toString().split(Pattern.quote("_"))[0];
            if(!TextUtils.isEmpty(ediText.getText().toString().trim()))
            {

                String prdctOrderQty=ediText.getText().toString().trim();
                stockFilledDataModel.setPrdctStock(prdctId,prdctOrderQty);

            }
            else
            {
                stockFilledDataModel.removePrdctStk(prdctId);


            }

        }
    }

    class FocusChangeList implements View.OnFocusChangeListener
    {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            EditText editText= (EditText) v;

            if(!hasFocus)
            {
                if(editText.getId()==R.id.et_stckVal)
                {

                    interfacefocusLostCalled.fcsLstCld(hasFocus,editText);
                }
            }
            else
            {
                if(editText.getId()==R.id.et_stckVal)
                {
                    interfacefocusLostCalled.fcsLstCld(hasFocus,editText);
                }

            }
        }
    }

}
