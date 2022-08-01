package com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter;



import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorVehicleMasterList;
import com.astix.rajtraderssfasales.R;

import java.util.List;

public class ScoutingVechileAdapter extends RecyclerView.Adapter<ScoutingVechileAdapter.ViewHolder> {
    private Context context;
    private List<TblPotentialDistributorVehicleMasterList> tblPotentialDistributorVehicleMasterLists;
    int flgViewEdit;
    public ScoutingVechileAdapter(Context context, List<TblPotentialDistributorVehicleMasterList> tblPotentialDistributorVehicleMasterLists,int flgViewEdit) {
        this.context = context;
        this.tblPotentialDistributorVehicleMasterLists = tblPotentialDistributorVehicleMasterLists;
        this.flgViewEdit=flgViewEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.distributor_scouting_vechile_rows, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cbVechileEnable.setChecked(false);
        holder.etNoOfVechiles.setEnabled(false);

        holder.cbVechileEnable.setChecked((tblPotentialDistributorVehicleMasterLists.get(position).getVehicleTypeID() == 1));
        if (tblPotentialDistributorVehicleMasterLists.get(position).getFlgEnable() == 1) {
            holder.etNoOfVechiles.setEnabled(true);

        }
        holder.cbVechileEnable.setText(tblPotentialDistributorVehicleMasterLists.get(position).getVehicleType());
        if (tblPotentialDistributorVehicleMasterLists.get(position).getNoOfVechile() !=null &&  !tblPotentialDistributorVehicleMasterLists.get(position).getNoOfVechile() .equals("0")) {
            holder.etNoOfVechiles.setText(""+tblPotentialDistributorVehicleMasterLists.get(position).getNoOfVechile());
        }

        holder.etNoOfVechiles.setTag("" + tblPotentialDistributorVehicleMasterLists.get(position).getVehicleTypeID());


        holder.cbVechileEnable.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                holder.etNoOfVechiles.setText("");

            }
            tblPotentialDistributorVehicleMasterLists.get(position).setFlgEnable(b ? 1 : 0);
            holder.etNoOfVechiles.setEnabled(b);

        });


        holder.etNoOfVechiles.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tblPotentialDistributorVehicleMasterLists.get(holder.getAdapterPosition()).setNoOfVechile(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return tblPotentialDistributorVehicleMasterLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        EditText etNoOfVechiles;
        CheckBox cbVechileEnable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cbVechileEnable = itemView.findViewById(R.id.cbVechileEnable);
            etNoOfVechiles = itemView.findViewById(R.id.etNoOfVechiles);

        }
    }
}