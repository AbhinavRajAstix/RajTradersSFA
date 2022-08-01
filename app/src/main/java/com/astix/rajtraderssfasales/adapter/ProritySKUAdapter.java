package com.astix.rajtraderssfasales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreVisitHistory;
import com.astix.rajtraderssfasales.model.TblStoreProductMappingForDisplay;

import java.util.ArrayList;
import java.util.List;

public class ProritySKUAdapter extends RecyclerView.Adapter<ProritySKUAdapter.ViewHolder> {
    private Context mContext;
    private List<TblStoreProductMappingForDisplay> listPrioritySKU;

    public ProritySKUAdapter(Context mContext, List<TblStoreProductMappingForDisplay> listPrioritySKU) {
        this.mContext = mContext;
        this.listPrioritySKU = listPrioritySKU;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_priority_sku, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TblStoreProductMappingForDisplay orderHistoryPerProduct = listPrioritySKU.get(position);
        holder.tvProductName.setText(orderHistoryPerProduct.getPrdName());
        holder.tvPrice.setText("\u20b9 " + orderHistoryPerProduct.getPrdPrice());
    }

    @Override
    public int getItemCount() {
        return listPrioritySKU.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductName, tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
