package com.astix.rajtraderssfasales.UpdateExisitingDistributor;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceDeleteOrViewScoutingDistributor;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributor;
import com.astix.rajtraderssfasales.InterfaceRetrofitStoreSeleted;
import com.astix.rajtraderssfasales.R;

import java.util.List;

public class ExistingSelectionListAdapter extends RecyclerView.Adapter<ExistingSelectionListAdapter.ViewHolder> {

    public Context context;

    List<TblPotentialDistributor> tblPotentialDistributorList;
    String userDate, pickerDate;


    public ExistingSelectionListAdapter(Context context, List<TblPotentialDistributor> tblPotentialDistributorList) {
        this.context = context;

        this.tblPotentialDistributorList = tblPotentialDistributorList;
        this.pickerDate = pickerDate;
        this.userDate = userDate;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exisiting_distributor_row_selection_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final TblPotentialDistributor tblPotentialDistributor = tblPotentialDistributorList.get(position);


        if (tblPotentialDistributor.getAddress() != null) {

            holder.tv_StoreAddress.setText(tblPotentialDistributor.getAddress());
            SpannableString content = new SpannableString(tblPotentialDistributor.getAddress());
            // content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            holder.tv_StoreAddress.setTextColor(Color.parseColor("#3f51b5"));
            holder.tv_StoreAddress.setText(content);

        }
        holder.tv_StoreAddress.setTag(tblPotentialDistributor.getAddress() + "^" + tblPotentialDistributor.getContactPersonMobileNumber());

        holder.rg1StoreName.setChecked(false);
        holder.rg1StoreName.setTag(tblPotentialDistributor.getNodeID());
        //  holder.rg1StoreName.setText(tblPotentialDistributor.getDescr());
        if (tblPotentialDistributor.getFlgRadioButtonSelected() == 1) {
            holder.rg1StoreName.setChecked(true);
        } else {
            holder.rg1StoreName.setChecked(false);
        }
        holder.rg1StoreName.setOnClickListener(view -> {
            String selStoreID = holder.rg1StoreName.getTag().toString();
            for (TblPotentialDistributor wp : tblPotentialDistributorList) {
                if (wp.getNodeID().equals(selStoreID)) {
                    wp.setFlgRadioButtonSelected(1);
                } else {
                    wp.setFlgRadioButtonSelected(0);
                }
            }
            notifyDataSetChanged();
            InterfaceRetrofitStoreSeleted intrfc = (InterfaceRetrofitStoreSeleted) context;
            intrfc.startVisitOfSelectedStore(selStoreID);
        });
        holder.cv1.setOnClickListener(v -> {

            String selStoreID = holder.rg1StoreName.getTag().toString();

        });

        holder.img_removedistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InterfaceDeleteOrViewScoutingDistributor intrfc = (InterfaceDeleteOrViewScoutingDistributor) context;

                intrfc.fnDeleteScoutingDistributor( tblPotentialDistributorList.get(position));
            }
        });
        holder.tv_StoreAddress.setOnClickListener(view -> {
            //context.showStoreAddressAlert(view.getTag().toString());
            InterfaceRetrofitStoreSeleted intrfc = (InterfaceRetrofitStoreSeleted) context;

            intrfc.ShowSelectedStoreAddress(view.getTag().toString());

        });


    /*    holder.tv_StoreName.setTextColor(ContextCompat.getColor(context, R.color.black));
        holder.rg1StoreName.setEnabled(true);
        holder.rg1StoreName.setTypeface(null, Typeface.NORMAL);
        holder.rg1StoreName.setVisibility(View.GONE);*/



       /* SpannableString content = new SpannableString(tblPotentialDistributor.getDescr());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        holder.tv_StoreName.setTextColor(Color.parseColor("#a6004e"));
        holder.tv_StoreName.setText(content);*/
        holder.tv_StoreName.setText(tblPotentialDistributor.getDescr());


        holder.img_viewDistributorDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InterfaceDeleteOrViewScoutingDistributor intrfc = (InterfaceDeleteOrViewScoutingDistributor) context;

                intrfc.fnViewScoutingDistributorDetails( tblPotentialDistributorList.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return tblPotentialDistributorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        public RadioButton rg1StoreName;
        public TextView  tv_StoreName,  tv_StoreAddress;
        ImageView img_removedistributor,img_viewDistributorDetails;

        public CardView cv1;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            cv1 = itemView.findViewById(R.id.cv1);
            rg1StoreName = itemView.findViewById(R.id.rg1StoreName);

            tv_StoreName = itemView.findViewById(R.id.tv_StoreName);

            tv_StoreAddress = itemView.findViewById(R.id.tv_StoreAddress);
            img_removedistributor=itemView.findViewById(R.id.img_removedistributor);
            img_viewDistributorDetails=itemView.findViewById(R.id.img_viewDistributorDetails);

        }
    }

}