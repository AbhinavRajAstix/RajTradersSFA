package com.astix.rajtraderssfasales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.ProductInfo;

import java.util.ArrayList;

public class ProductInfoAdapter extends RecyclerView.Adapter<ProductInfoAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ProductInfo> productInfoArrayList;

    public ProductInfoAdapter(Context mContext, ArrayList<ProductInfo> productInfoArrayList) {
        this.mContext = mContext;
        this.productInfoArrayList = productInfoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_product_order_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductInfo productInfo = productInfoArrayList.get(position);
        holder.tvOrderDate.setText(productInfo.getTitle());
        holder.tvOrderQty.setText(productInfo.getData());
    }

    @Override
    public int getItemCount() {
        return productInfoArrayList.size();
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
