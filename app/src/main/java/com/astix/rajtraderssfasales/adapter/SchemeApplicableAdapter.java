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

import java.util.ArrayList;

public class SchemeApplicableAdapter extends RecyclerView.Adapter<SchemeApplicableAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TblGetPDAStoreVisitHistory> storeVisitHistoryArrayList;

    public SchemeApplicableAdapter(Context mContext, ArrayList<TblGetPDAStoreVisitHistory> storeVisitHistoryArrayList) {
        this.mContext = mContext;
        this.storeVisitHistoryArrayList = storeVisitHistoryArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_scheme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TblGetPDAStoreVisitHistory orderHistoryPerProduct = storeVisitHistoryArrayList.get(position);
        holder.tvSlabDesc.setText(orderHistoryPerProduct.getLastCallVisit());
        holder.tvSlab.setText(orderHistoryPerProduct.getOrderQty());
        holder.tvBenefit.setText(orderHistoryPerProduct.getOrderQty());
    }

    @Override
    public int getItemCount() {
        return storeVisitHistoryArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSlabDesc, tvBenefit, tvSlab;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSlabDesc = itemView.findViewById(R.id.tvSlabDesc);
            tvBenefit = itemView.findViewById(R.id.tvBenefit);
            tvSlab = itemView.findViewById(R.id.tvSlab);
        }
    }
}
