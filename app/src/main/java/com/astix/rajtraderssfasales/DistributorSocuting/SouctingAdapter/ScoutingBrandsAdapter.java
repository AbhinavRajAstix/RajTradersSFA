package com.astix.rajtraderssfasales.DistributorSocuting.SouctingAdapter;



import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceActivityScoutingShowHideOthersBox;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorCompetitorBrandMstr;
import com.astix.rajtraderssfasales.R;

import java.util.List;
import java.util.regex.Pattern;

public class ScoutingBrandsAdapter extends RecyclerView.Adapter<ScoutingBrandsAdapter.ViewHolder> {
    private Context context;
    private List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandMstrList;
    int flgViewEdit;

    public ScoutingBrandsAdapter(Context context, List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandMstrList,int flgViewEdit) {
        this.context = context;
        this.tblPotentialDistributorCompetitorBrandMstrList = tblPotentialDistributorCompetitorBrandMstrList;
        this.flgViewEdit=flgViewEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.distributor_scouting_brand_rows, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cbBrandEnable.setChecked(false);

        holder.cbBrandEnable.setText(tblPotentialDistributorCompetitorBrandMstrList.get(position).getCompetitorBrand());

        if(tblPotentialDistributorCompetitorBrandMstrList.get(position).getFlgCheck()==1)
        {
            holder.cbBrandEnable.setChecked(true);
        }

        holder.et_Sales.setTag("BrandSales_"+tblPotentialDistributorCompetitorBrandMstrList.get(position).getCompetitorBrandID());
        holder.cbBrandEnable.setTag("Brand_"+tblPotentialDistributorCompetitorBrandMstrList.get(position).getCompetitorBrandID());
        holder.cbBrandEnable.setOnCheckedChangeListener((compoundButton, b) -> {

            tblPotentialDistributorCompetitorBrandMstrList.get(position).setFlgCheck(b ? 1 : 0);


            if( tblPotentialDistributorCompetitorBrandMstrList.get(position).getFlgCheck()==1)
            {
                CheckBox chk = (CheckBox)compoundButton; //.findViewWithTag(crntPrdID + "_tvDistext");
                LinearLayout view =(LinearLayout)chk.getParent();
                int BrandID=Integer.parseInt(chk.getTag().toString().split(Pattern.quote("_"))[1]);
                EditText etSales = (EditText)view.findViewWithTag("BrandSales_"+BrandID);
                etSales.setText("");
                etSales.setEnabled(true);
                etSales.setBackground(context.getResources().getDrawable(R.drawable.edittex_with_white_background));

            }
            else
            {
                CheckBox chk = (CheckBox)compoundButton; //.findViewWithTag(crntPrdID + "_tvDistext");
                LinearLayout view =(LinearLayout)chk.getParent();
                int BrandID=Integer.parseInt(chk.getTag().toString().split(Pattern.quote("_"))[1]);
                EditText etSales = (EditText)view.findViewWithTag("BrandSales_"+BrandID);
                etSales.setEnabled(false);
                etSales.setBackground(context.getResources().getDrawable(R.drawable.edittext_with_gray_background));
                tblPotentialDistributorCompetitorBrandMstrList.get(position).setSalesQty("0");
            }
            if(tblPotentialDistributorCompetitorBrandMstrList.get(position).getFlgOther()==1)
            {
                InterfaceActivityScoutingShowHideOthersBox intrfc = (InterfaceActivityScoutingShowHideOthersBox) context;

                intrfc.showBrandOtherBox(tblPotentialDistributorCompetitorBrandMstrList.get(position));
            }


        });
        holder.cbBrandEnable.setEnabled(true);
        if(flgViewEdit==2) {
            holder.cbBrandEnable.setEnabled(false);
            holder.et_Sales.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return tblPotentialDistributorCompetitorBrandMstrList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //EditText etNoOfVechiles;
        CheckBox cbBrandEnable;
        EditText et_Sales;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cbBrandEnable = itemView.findViewById(R.id.cbBrandEnable);
            et_Sales=itemView.findViewById(R.id.et_Sales);
           // etNoOfVechiles = itemView.findViewById(R.id.etNoOfVechiles);

        }
    }

    class TextChangedListener implements TextWatcher {
        EditText ediText;
        TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandMstrLocal;
        public TextChangedListener(EditText ediText, TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandMstrLocal) {//, int upc
            this.ediText = ediText;
            this.tblPotentialDistributorCompetitorBrandMstrLocal=tblPotentialDistributorCompetitorBrandMstrLocal;
            // this.upc = upc;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String BrandId = ediText.getTag().toString().split(Pattern.quote("_"))[1];

            if (!TextUtils.isEmpty(ediText.getText().toString().trim())) {

                String SalesVal = ediText.getText().toString().trim();
                tblPotentialDistributorCompetitorBrandMstrLocal.setSalesQty(SalesVal);

            }
            else {
                tblPotentialDistributorCompetitorBrandMstrLocal.setSalesQty("0");

            }

        }
    }
}