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
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorCompetitorCompanyMstr;
import com.astix.rajtraderssfasales.R;

import java.util.List;
import java.util.regex.Pattern;

public class ScoutingCompetitorAdapter extends RecyclerView.Adapter<ScoutingCompetitorAdapter.ViewHolder> {
    private Context context;
    private List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyMstrList;
    int flgViewEdit;

    public ScoutingCompetitorAdapter(Context context, List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyMstrList,int flgViewEdit) {
        this.context = context;
        this.tblPotentialDistributorCompetitorCompanyMstrList = tblPotentialDistributorCompetitorCompanyMstrList;
        this.flgViewEdit=flgViewEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.distributor_scouting_competitor_rows, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cbCompetitorEnable.setChecked(false);

        holder.cbCompetitorEnable.setText(tblPotentialDistributorCompetitorCompanyMstrList.get(position).getCompetitorCompany());
        holder.et_Sales.setTag("CompanySales_"+tblPotentialDistributorCompetitorCompanyMstrList.get(position).getCompetitorCompanyID());
        holder.et_Sales.setText("");
        if(tblPotentialDistributorCompetitorCompanyMstrList.get(position).getFlgCheck()==1)
        {
            holder.cbCompetitorEnable.setChecked(true);

            holder.et_Sales.setText(tblPotentialDistributorCompetitorCompanyMstrList.get(position).getSalesQty());
            holder.et_Sales.setBackground(context.getResources().getDrawable(R.drawable.edittex_with_white_background));
            holder.et_Sales.setEnabled(true);
        }

        holder.cbCompetitorEnable.setTag("Company_"+tblPotentialDistributorCompetitorCompanyMstrList.get(position).getCompetitorCompanyID());
        holder.cbCompetitorEnable.setOnCheckedChangeListener((compoundButton, b) -> {

            tblPotentialDistributorCompetitorCompanyMstrList.get(position).setFlgCheck(b ? 1 : 0);

            if( tblPotentialDistributorCompetitorCompanyMstrList.get(position).getFlgCheck()==1)
            {
                CheckBox chk = (CheckBox)compoundButton; //.findViewWithTag(crntPrdID + "_tvDistext");
                LinearLayout view =(LinearLayout)chk.getParent();
                int CompID=Integer.parseInt(chk.getTag().toString().split(Pattern.quote("_"))[1]);
                EditText etSales = (EditText)view.findViewWithTag("CompanySales_"+CompID);
                etSales.setText("");
                etSales.setEnabled(true);
                etSales.setBackground(context.getResources().getDrawable(R.drawable.edittex_with_white_background));

            }
            else
            {
                CheckBox chk = (CheckBox)compoundButton; //.findViewWithTag(crntPrdID + "_tvDistext");
                LinearLayout view =(LinearLayout)chk.getParent();
                int CompID=Integer.parseInt(chk.getTag().toString().split(Pattern.quote("_"))[1]);
                EditText etSales = (EditText)view.findViewWithTag("CompanySales_"+CompID);
                etSales.setEnabled(false);
                etSales.setBackground(context.getResources().getDrawable(R.drawable.edittext_with_gray_background));
                tblPotentialDistributorCompetitorCompanyMstrList.get(position).setSalesQty("0");
            }
            if(tblPotentialDistributorCompetitorCompanyMstrList.get(position).getFlgOther()==1)
            {
                InterfaceActivityScoutingShowHideOthersBox intrfc = (InterfaceActivityScoutingShowHideOthersBox) context;

                intrfc.showCompanyOtherBox(tblPotentialDistributorCompetitorCompanyMstrList.get(position));
            }

        });
        holder.cbCompetitorEnable.setEnabled(true);


        TextChangedListener textChangedListenerOrderQty = new TextChangedListener(holder.et_Sales, tblPotentialDistributorCompetitorCompanyMstrList.get(position));
        holder.et_Sales.addTextChangedListener(textChangedListenerOrderQty);
        if(flgViewEdit==2) {
            holder.cbCompetitorEnable.setEnabled(false);
            holder.et_Sales.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return tblPotentialDistributorCompetitorCompanyMstrList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //EditText etNoOfVechiles;
        CheckBox cbCompetitorEnable;
        EditText et_Sales;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cbCompetitorEnable = itemView.findViewById(R.id.cbCompetitorEnable);
            et_Sales=itemView.findViewById(R.id.et_Sales);
           // etNoOfVechiles = itemView.findViewById(R.id.etNoOfVechiles);

        }
    }

    class TextChangedListener implements TextWatcher {
        EditText ediText;
        TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyMstrLocal;
        public TextChangedListener(EditText ediText, TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyMstrLocal) {//, int upc
            this.ediText = ediText;
          this.tblPotentialDistributorCompetitorCompanyMstrLocal=tblPotentialDistributorCompetitorCompanyMstrLocal;
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
            String CompanyId = ediText.getTag().toString().split(Pattern.quote("_"))[1];

                if (!TextUtils.isEmpty(ediText.getText().toString().trim())) {

                    String SalesVal = ediText.getText().toString().trim();
                    tblPotentialDistributorCompetitorCompanyMstrLocal.setSalesQty(SalesVal);

                }
                else {
                    tblPotentialDistributorCompetitorCompanyMstrLocal.setSalesQty("0");

                }

        }
    }

}