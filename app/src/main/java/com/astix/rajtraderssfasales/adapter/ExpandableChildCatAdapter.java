package com.astix.rajtraderssfasales.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.CategoryCommunicatorOrderFilter;
import com.astix.rajtraderssfasales.ProductEntryForm;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblOnlyCategoryMasterForRetriving;
import com.astix.rajtraderssfasales.model.TblProductTypeMasterForRetriving;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ExpandableChildCatAdapter extends RecyclerView.Adapter<ExpandableChildCatAdapter.ViewHolder> {
    Context activity;
    ArrayList<TblOnlyCategoryMasterForRetriving> tblProductTypeMasterForRetrivingList;
    int productTypeNodeID;
    CategoryCommunicatorOrderFilter communicator;
    Dialog listDialog;
    public int parentPosition=0;
    public ArrayList<TblProductTypeMasterForRetriving>  tblProductTypeMasterForRetrivingGlobal;
    TblOnlyCategoryMasterForRetriving tblOnlyCategoryMasterForRetrivingGlobal;



    public ExpandableChildCatAdapter(Context activity, Dialog listDialog, int productTypeNodeID, ArrayList<TblOnlyCategoryMasterForRetriving> tblProductTypeMasterForRetrivingList, int parentPosition, ArrayList<TblProductTypeMasterForRetriving>  tblProductTypeMasterForRetrivingGlobal) {
        this.activity = activity;
        this.tblProductTypeMasterForRetrivingList = tblProductTypeMasterForRetrivingList;
        this.productTypeNodeID = productTypeNodeID;
        this.listDialog=listDialog;
        this.parentPosition=parentPosition;
        this.tblProductTypeMasterForRetrivingGlobal=tblProductTypeMasterForRetrivingGlobal;
    }

    @NonNull
    @Override
    public ExpandableChildCatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.raw_childcatagory, parent, false);
        ExpandableChildCatAdapter.ViewHolder holder = new ExpandableChildCatAdapter.ViewHolder(view);
        communicator=(CategoryCommunicatorOrderFilter) activity;

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ExpandableChildCatAdapter.ViewHolder holder, int position) {

        holder.cbChildCategoryName.setText(tblProductTypeMasterForRetrivingList.get(position).getCategory());
        holder.cbChildCategoryName.setTag(tblProductTypeMasterForRetrivingList.get(position).getCategoryNodeID()+"^"+tblProductTypeMasterForRetrivingList.get(position).getCategory()+"^"+tblProductTypeMasterForRetrivingList.get(position).getParentID()+"^"+position);
        //holder.ll_childRecord.setBackgroundResource( R.drawable.card_background_odd);


        holder.cbChildCategoryName.setBackgroundResource(R.drawable.card_background_summary);

        if(tblProductTypeMasterForRetrivingList.get(position).getSelected()!=null && tblProductTypeMasterForRetrivingList.get(position).getSelected())
        {
            holder.cbChildCategoryName.setBackgroundResource(R.drawable.card_background_return_even);
            holder.cbChildCategoryName.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return tblProductTypeMasterForRetrivingList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        CheckBox cbChildCategoryName;
        AppCompatTextView tvChildCategoryName;
        LinearLayout ll_childRecord;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           // tvChildCategoryName = (AppCompatTextView)itemView.findViewById(R.id.tvChildCategoryName);
            cbChildCategoryName =(CheckBox) itemView.findViewById(R.id.cbChildCategoryName);
            ll_childRecord=(LinearLayout)itemView.findViewById(R.id.ll_childRecord);

            cbChildCategoryName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox view=(CheckBox)v;

                    int pos=Integer.parseInt(v.getTag().toString().trim().split(Pattern.quote("^"))[3]);
                 //   TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingModel=tblProductTypeMasterForRetrivingGlobal.get(parentPosition);
                    tblOnlyCategoryMasterForRetrivingGlobal=tblProductTypeMasterForRetrivingList.get(getAdapterPosition());




                    tblOnlyCategoryMasterForRetrivingGlobal=tblProductTypeMasterForRetrivingList.get(pos);

                   // communicator.selectedOption(v.getTag().toString().trim().split(Pattern.quote("^"))[1],v.getTag().toString().trim().split(Pattern.quote("^"))[0], listDialog,2,Integer.parseInt(v.getTag().toString().trim().split(Pattern.quote("^"))[2]),parentPosition,true,true,tblProductTypeMasterForRetrivingModel,tblProductTypeMasterForRetrivingList,tblOnlyCategoryMasterForRetrivingGlobal.getCategoryNodeID());

                    if(view.isChecked())
                    {
                        tblOnlyCategoryMasterForRetrivingGlobal.setSelected(true);
                    }
                    else
                    {
                        tblOnlyCategoryMasterForRetrivingGlobal.setSelected(false);
                    }


                    // Code
                }
            });
        }
    }


}
