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

import java.util.ArrayList;

public class ProductOrderHistoryAdapter extends RecyclerView.Adapter<ProductOrderHistoryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TblProductLevelData> orderHistoryPerProductArrayList;

    public ProductOrderHistoryAdapter(Context mContext, ArrayList<TblProductLevelData> orderHistoryPerProductArrayList) {
        this.mContext = mContext;
        this.orderHistoryPerProductArrayList = orderHistoryPerProductArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_product_order_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TblProductLevelData orderHistoryPerProduct = orderHistoryPerProductArrayList.get(position);
        holder.tvOrderDate.setText(orderHistoryPerProduct.getInvDate());
        holder.tvOrderQty.setText("\u20b9 " + orderHistoryPerProduct.getOrderValue());
    }

    @Override
    public int getItemCount() {
        return orderHistoryPerProductArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOrderQty, tvOrderDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderQty = itemView.findViewById(R.id.tvOrderQty);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
        }
    }
}
