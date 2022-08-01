package com.astix.rajtraderssfasales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreVisitHistory;
import com.astix.rajtraderssfasales.model.TblSchemePerProduct;

import java.util.ArrayList;

public class SchemeSlabAdapter extends RecyclerView.Adapter<SchemeSlabAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TblSchemePerProduct> schemePerProductArrayList;

    public SchemeSlabAdapter(Context mContext, ArrayList<TblSchemePerProduct> schemePerProductArrayList) {
        this.mContext = mContext;
        this.schemePerProductArrayList = schemePerProductArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_scheme_slab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TblSchemePerProduct schemePerProduct = schemePerProductArrayList.get(position);
        holder.tvSlabNo.setText("" + (position+1));
        holder.tvSlabDesc.setText(schemePerProduct.getAmtToAchieve());
        holder.tvSlabGap.setText(schemePerProduct.getDiscount());

       // holder.tvSlabGap.setText("Get Benifit -");
        //holder.tvSlabAchievedAmt.setText(" "+schemePerProduct.getDiscount());
        holder.tvSlabAchievedAmt.setText("Gap : "+schemePerProduct.getGap());
//        holder.tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.black));

        holder.tvSlabDesc.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        holder.tvSlabAchievedAmt.setTextColor(ContextCompat.getColor(mContext, R.color.black));

        if (schemePerProduct.getFlgColor()==1) {
            holder.tvSlabDesc.setTextColor(ContextCompat.getColor(mContext, R.color.new_green));
            holder.tvSlabAchievedAmt.setTextColor(ContextCompat.getColor(mContext, R.color.new_green));
            holder.tvSlabGap.setTextColor(ContextCompat.getColor(mContext, R.color.new_green));
        } else if (schemePerProduct.getFlgColor()==2)  {
            holder.tvSlabDesc.setTextColor(ContextCompat.getColor(mContext, R.color.new_orange));
            holder.tvSlabAchievedAmt.setTextColor(ContextCompat.getColor(mContext, R.color.new_orange));
            holder.tvSlabGap.setTextColor(ContextCompat.getColor(mContext, R.color.new_orange));

        }
    }

    @Override
    public int getItemCount() {
        return schemePerProductArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSlabNo, tvSlabDesc, tvSlabGap, tvSlabAchievedAmt;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSlabNo = itemView.findViewById(R.id.tvSlabNo);
            tvSlabDesc = itemView.findViewById(R.id.tvSlabDesc);
            tvSlabGap = itemView.findViewById(R.id.tvSlabGap);
            tvSlabAchievedAmt = itemView.findViewById(R.id.tvSlabAchievedAmt);
        }
    }
}
