package com.astix.rajtraderssfasales.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.CategoryCommunicatorOrderFilter;

import com.astix.rajtraderssfasales.ProductEntryForm;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblOnlyCategoryMasterForRetriving;
import com.astix.rajtraderssfasales.model.TblProductTypeMasterForRetriving;
import com.astix.rajtraderssfasales.model.TblStoreProductMappingForDisplay;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;
import static br.com.zbra.androidlinq.Linq.stream;

public class ExpandableCategoryAdapter extends RecyclerView.Adapter<ExpandableCategoryAdapter.ParentViewHolder> {

    ProductEntryForm activity;
    ArrayList<TblProductTypeMasterForRetriving> tblProductTypeMasterForRetrivingList;
    ExpandableChildCatAdapter expandableChildCatAdapter;
    Dialog listDialog;
    CategoryCommunicatorOrderFilter communicator;
    public TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingGlobal;
    public ArrayList<TblOnlyCategoryMasterForRetriving> tblOnlyCategoryMasterForRetrivingGlobal;
    public int mExpandedPosition = 1;
    public int previousExpandedPosition = 0;
    public int CurrentCategorySelectedPosition = -1;
    LinkedHashMap<String,Integer> hmapCatSelecteList=new LinkedHashMap<String, Integer>();

    public ExpandableCategoryAdapter(ProductEntryForm activity, Dialog listDialog, String previousSlctdCtgry, ArrayList<TblProductTypeMasterForRetriving> tblProductTypeMasterForRetrivingList, int CurrentCategorySelectedPosition,LinkedHashMap<String,Integer> hmapCatSelecteList) {

        this.activity = activity;
        this.tblProductTypeMasterForRetrivingList = tblProductTypeMasterForRetrivingList;
        this.listDialog = listDialog;
        mExpandedPosition = CurrentCategorySelectedPosition;
        this.hmapCatSelecteList=hmapCatSelecteList;
    }

    @Override
    public ExpandableCategoryAdapter.ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.list_item_order_filter, parent, false);


        ExpandableCategoryAdapter.ParentViewHolder holder = new ExpandableCategoryAdapter.ParentViewHolder(view);
        communicator = (CategoryCommunicatorOrderFilter) activity;


        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull ExpandableCategoryAdapter.ParentViewHolder holder, int position) {

        holder.cbProductName.setText(tblProductTypeMasterForRetrivingList.get(position).getProductType());
        holder.cbProductName.setTag(tblProductTypeMasterForRetrivingList.get(position).getProductType());

        holder.cbProductName.setChecked(false);
        if(hmapCatSelecteList!=null && hmapCatSelecteList.size()>0 && hmapCatSelecteList.containsKey(tblProductTypeMasterForRetrivingList.get(position).getProductType()))
            holder.cbProductName.setChecked(true);
        if(hmapCatSelecteList!=null && hmapCatSelecteList.size()>0) {
            if (hmapCatSelecteList.size() == tblProductTypeMasterForRetrivingList.size()) {
                hmapCatSelecteList.put("All",0);
            }
        }
/*
        holder.cbProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

//        holder.cbProductName.setBackgroundResource(R.drawable.card_background_odd);

//        if (tblProductTypeMasterForRetrivingList.get(position).getParentPsition() == 0 && position == 0) {
//            holder.cbProductName.setBackgroundResource(R.drawable.card_background_header);
//        } else if (tblProductTypeMasterForRetrivingList.get(position).isExpanded()) {
//            holder.cbProductName.setBackgroundResource(R.drawable.card_background_header);
//        }
        expandableChildCatAdapter = new ExpandableChildCatAdapter(activity,  listDialog,tblProductTypeMasterForRetrivingList.get(position).getProductTypeNodeID(),tblProductTypeMasterForRetrivingList.get(position).getTblOnlyCategoryMasterForRetrivingList(),position,tblProductTypeMasterForRetrivingList);
        holder.rvChildList.setAdapter(expandableChildCatAdapter);
        holder.rvChildList.setVisibility(View.GONE);
        if(tblProductTypeMasterForRetrivingList.get(position).getTblOnlyCategoryMasterForRetrivingList()!=null && tblProductTypeMasterForRetrivingList.get(position).getTblOnlyCategoryMasterForRetrivingList().size()>0)
        {
            holder.rvChildList.setVisibility(View.VISIBLE);
        }


      /*  final boolean isExpanded = position==mExpandedPosition;
        holder.rvChildList.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(position);
            }
        });*/

        final boolean isExpanded = position == mExpandedPosition;

        holder.rvChildList.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.itemView.setActivated(isExpanded);

        holder.itemView.setActivated(isExpanded);
        holder.img_expandcollapse.setTag(tblProductTypeMasterForRetrivingList.get(position).getProductTypeNodeID()+"^"+tblProductTypeMasterForRetrivingList.get(position).getProductType()+"^0^"+position);
        holder.img_expandcollapse.setBackgroundResource(R.drawable.expanblue);
        holder.img_expandcollapse.setVisibility(View.VISIBLE);
        if(tblProductTypeMasterForRetrivingList.get(position).getProductTypeNodeID()==0)
        {
            holder.img_expandcollapse.setVisibility(View.INVISIBLE);
        }
        if (isExpanded) {
            previousExpandedPosition = position;
            holder.img_expandcollapse.setBackgroundResource(R.drawable.colablue);
        }

        holder.img_expandcollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                if(isExpanded)
                {
                    v.setBackgroundResource(R.drawable.colablue);
                }
                else
                {
                    v.setBackgroundResource(R.drawable.expanblue);
                }
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return tblProductTypeMasterForRetrivingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    public void updateList(){

     notifyDataSetChanged();
    }
    public class ParentViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvChildList;
        CheckBox cbProductName;
        ImageView img_expandcollapse;

        //        AppCompatTextView product_name;
//        ImageView img_expandcollapse;
        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);

            rvChildList = (RecyclerView) itemView.findViewById(R.id.rvChildList);
            cbProductName =(CheckBox) itemView.findViewById(R.id.cbProductName);
            img_expandcollapse = (ImageView) itemView.findViewById(R.id.img_expandcollapse);


            cbProductName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CheckBox v=(CheckBox)view;

                    if(v.isChecked())
                    {
                        hmapCatSelecteList.put(v.getTag().toString(),1);
                        v.setChecked(true);
                       TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingListCurentReords=stream(tblProductTypeMasterForRetrivingList).where(p->p.getProductType().equals(v.getTag().toString())).first();
                       ArrayList<TblOnlyCategoryMasterForRetriving> tblProductTypeMasterForRetrivingListChildReords =tblProductTypeMasterForRetrivingListCurentReords.getTblOnlyCategoryMasterForRetrivingList();
                        if(tblProductTypeMasterForRetrivingListChildReords!=null && tblProductTypeMasterForRetrivingListChildReords.size()>0)
                        {
                            for(TblOnlyCategoryMasterForRetriving tblChildRecord:tblProductTypeMasterForRetrivingListChildReords)
                            {
                                tblChildRecord.setSelected(true);
                            }
                        }


                    }
                    else {
                        hmapCatSelecteList.remove(v.getTag().toString());
                        v.setChecked(false);
                        TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingListCurentReords=stream(tblProductTypeMasterForRetrivingList).where(p->p.getProductType().equals(v.getTag().toString())).first();
                        ArrayList<TblOnlyCategoryMasterForRetriving> tblProductTypeMasterForRetrivingListChildReords =tblProductTypeMasterForRetrivingListCurentReords.getTblOnlyCategoryMasterForRetrivingList();
                        if(tblProductTypeMasterForRetrivingListChildReords!=null && tblProductTypeMasterForRetrivingListChildReords.size()>0)
                        {
                            for(TblOnlyCategoryMasterForRetriving tblChildRecord:tblProductTypeMasterForRetrivingListChildReords)
                            {
                                tblChildRecord.setSelected(false);
                            }
                        }
                        hmapCatSelecteList.remove("All");
                    }

                    if(hmapCatSelecteList!=null && hmapCatSelecteList.size()>0) {
                        if (hmapCatSelecteList.size() == (tblProductTypeMasterForRetrivingList.size())) {
                            hmapCatSelecteList.put("All",1);
                        }
                    }
                    updateList();

                   /* tblProductTypeMasterForRetrivingGlobal = tblProductTypeMasterForRetrivingList.get(getAdapterPosition());
                    tblOnlyCategoryMasterForRetrivingGlobal = tblProductTypeMasterForRetrivingList.get(getAdapterPosition()).getTblOnlyCategoryMasterForRetrivingList();


                    communicator.selectedOption(v.getTag().toString().trim().split(Pattern.quote("^"))[1], v.getTag().toString().trim().split(Pattern.quote("^"))[0], listDialog, 1, 0, Integer.parseInt(v.getTag().toString().trim().split(Pattern.quote("^"))[3]), true, false, tblProductTypeMasterForRetrivingGlobal, tblOnlyCategoryMasterForRetrivingGlobal, 0);*/
                    // Code
                }
            });
        }
    }


}
