package com.astix.rajtraderssfasales.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.ProductInvoiceReview;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblProductCategoryUOMTypeList;
import com.astix.rajtraderssfasales.utils.CartonProductEntry;

import java.util.List;

public class ViewCreatedCartonAdapter extends RecyclerView.Adapter<ViewCreatedCartonAdapter.ViewHolder> {

    public Context context;
    List<TblProductCategoryUOMTypeList> createdCartonModelList;

    public ViewCreatedCartonAdapter(Context context, List<TblProductCategoryUOMTypeList> createdCartonModelList) {
        this.context = context;
        this.createdCartonModelList = createdCartonModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_view_carton_data, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        box 1: 100gm right side->2"" +

        // List<TblProductCategoryUOMTypeList> createdCartonModelListUOMTypeName=stream(createdCartonModelList).select(p->p.getCategoryDesc())

     /*   holder.tvCategoryName.setText(createdCartonModelList.get(position).getCategoryDesc());*/

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rvCartonDetail.setLayoutManager(linearLayoutManager);
       // holder.rvCartonDetail.setAdapter(new ViewSubCreatedCartonAdapter(context, createdCartonModelList.get(position).getTblProductCategoryUOMTypeListsSubCategor()));
        holder.rvCartonDetail.setAdapter(new ViewSubCreatedCartonAdapter(context, createdCartonModelList));
    }

    @Override
    public int getItemCount() {
        return createdCartonModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCategoryName;
        public RecyclerView rvCartonDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            rvCartonDetail = itemView.findViewById(R.id.rvCartonDetail);
        }
    }

}
