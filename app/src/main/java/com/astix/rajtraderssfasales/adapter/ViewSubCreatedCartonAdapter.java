package com.astix.rajtraderssfasales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblProductCategoryUOMTypeList;

import java.util.List;

public class ViewSubCreatedCartonAdapter extends RecyclerView.Adapter<ViewSubCreatedCartonAdapter.ViewHolder> {

    public Context context;
    List<TblProductCategoryUOMTypeList> createdCartonModelList;

    public ViewSubCreatedCartonAdapter(Context context, List<TblProductCategoryUOMTypeList> createdCartonModelList) {
        this.context = context;
        this.createdCartonModelList = createdCartonModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_view_sub_carton_data, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        box 1: 100gm right side->2"" +

        // List<TblProductCategoryUOMTypeList> createdCartonModelListUOMTypeName=stream(createdCartonModelList).select(p->p.getCategoryDesc())

        holder.tvPrdName.setText(createdCartonModelList.get(position).getCategoryDesc()+"-"+ createdCartonModelList.get(position).getUOMType());
        holder.tvNoOfCases.setText("" + createdCartonModelList.get(position).getNoOfCarton());
        holder.tv_Line_value.setText("" + String.format("%.2f", createdCartonModelList.get(position).getStdLineVal()));
        holder.tvNetVal.setText("" + String.format("%.2f", createdCartonModelList.get(position).getNetLineVal()));
        holder.tv_FreeQty.setText("" + createdCartonModelList.get(position).getFreeQty());
    }

    @Override
    public int getItemCount() {
        return createdCartonModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPrdName, tv_FreeQty, tvNoOfCases, tv_Line_value, tvNetVal;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPrdName = itemView.findViewById(R.id.tvPrdName);
            tvNoOfCases = itemView.findViewById(R.id.tvNoOfCases);
            tv_Line_value = itemView.findViewById(R.id.tv_Line_value);
            tvNetVal = itemView.findViewById(R.id.tvNetVal);
            tv_FreeQty = itemView.findViewById(R.id.tv_FreeQty);
        }
    }

}
