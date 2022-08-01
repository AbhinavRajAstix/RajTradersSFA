package com.astix.rajtraderssfasales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblProductLevelData;
import com.astix.rajtraderssfasales.utils.AppUtils;

import java.util.ArrayList;

public class LastVisitHistoryAdapterProductLevel2 extends RecyclerView.Adapter<LastVisitHistoryAdapterProductLevel2.ViewHolder> {

    private Context mContext;
    private ArrayList<TblProductLevelData> orderHistoryPerProductArrayList;

    public LastVisitHistoryAdapterProductLevel2(Context mContext, ArrayList<TblProductLevelData> orderHistoryPerProductArrayList) {
        this.mContext = mContext;
        this.orderHistoryPerProductArrayList = orderHistoryPerProductArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_prd_inv_history_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TblProductLevelData orderHistoryPerProduct = orderHistoryPerProductArrayList.get(position);
        holder.tvOrderDate.setText("" + orderHistoryPerProduct.getPrdName());
        int orderValue = (int) Math.round(orderHistoryPerProduct.getOrderValue());
        String value = "\u20b9 " + AppUtils.checkNullOrEmpty("" + orderValue, "", "NA");
//        holder.tvOrderVal.setText(value);
        holder.tvOrderQty.setText(orderHistoryPerProduct.getQty() + " / " + value);
    }

    @Override
    public int getItemCount() {
        return orderHistoryPerProductArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOrderQty/*, tvOrderVal*/, tvOrderDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderQty = itemView.findViewById(R.id.tvOrderQty);
//            tvOrderVal = itemView.findViewById(R.id.tvOrderVal);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
        }
    }
}
