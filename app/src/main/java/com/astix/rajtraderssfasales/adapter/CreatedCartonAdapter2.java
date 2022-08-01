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
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblProductCategoryUOMTypeList;
import com.astix.rajtraderssfasales.utils.CartonProductEntry;

import java.util.List;

public class CreatedCartonAdapter2 extends RecyclerView.Adapter<CreatedCartonAdapter2.ViewHolder> {

    public Context context;
    List<TblProductCategoryUOMTypeList> createdCartonModelList;
    String date, pickerDate, SN;
    int flgOrderType, catID;
//    GetCartonPrdsInterface getCartonPrdsInterface;

    public CreatedCartonAdapter2(Context context, List<TblProductCategoryUOMTypeList> createdCartonModelList, String date, String pickerDate, String SN, int flgOrderType, int catID/*, GetCartonPrdsInterface getCartonPrdsInterface*/) {
        this.context = context;
        this.date = date;
        this.pickerDate = pickerDate;
        this.SN = SN;
        this.flgOrderType = flgOrderType;
        this.catID = catID;
//        this.getCartonPrdsInterface = getCartonPrdsInterface;
        this.createdCartonModelList = createdCartonModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_carton_data, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        box 1: 100gm right side->2"" +

        // List<TblProductCategoryUOMTypeList> createdCartonModelListUOMTypeName=stream(createdCartonModelList).select(p->p.getCategoryDesc())


        holder.tvPrdName.setText(createdCartonModelList.get(position).getCategoryDesc() + " - " + createdCartonModelList.get(position).getUOMType());
        holder.tvNoOfCases.setText("" + createdCartonModelList.get(position).getNoOfCarton());
        holder.tv_Line_value.setText("" + String.format("%.2f", createdCartonModelList.get(position).getStdLineVal()));
        holder.tvNetVal.setText("" + String.format("%.2f", createdCartonModelList.get(position).getNetLineVal()));
        holder.tv_FreeQty.setText("" + createdCartonModelList.get(position).getFreeQty());

        holder.editCarton.setOnClickListener(view -> {

            Intent intent = new Intent(context, CartonProductEntry.class);
            intent.putExtra("StoreID", createdCartonModelList.get(position).getStoreID());
            intent.putExtra("date", date);
            intent.putExtra("pickerDate", pickerDate);
            intent.putExtra("SN", SN);
            intent.putExtra("flgOrderType", flgOrderType);
            intent.putExtra("CatName", createdCartonModelList.get(position).getCategoryDesc());
            intent.putExtra("CartonID", createdCartonModelList.get(position).getCartonID());
            intent.putExtra("CatID", createdCartonModelList.get(position).getCatID());
            intent.putExtra("NoOfCarton", createdCartonModelList.get(position).getNoOfCarton());
            intent.putExtra("UOMType", createdCartonModelList.get(position).getUOMType());
            context.startActivity(intent);
            ((Activity) context).finish();

//            CartonProductEntryDialog cartonProductEntryDialog = new CartonProductEntryDialog((AppCompatActivity) context, context, createdCartonModelList.get(position).getStoreID(), date, pickerDate, SN,
//                    flgOrderType, createdCartonModelList.get(position).getCatID(), createdCartonModelList.get(position).getCartonID(), createdCartonModelList.get(position).getNoOfCarton(), createdCartonModelList.get(position).getUOMType(), getCartonPrdsInterface);
//            createdCartonModelList.get(position).getStoreID(), date, pickerDate, SN,
//                    flgOrderType, createdCartonModelList.get(position).getCatID(), createdCartonModelList.get(position).getCartonID(), \
//                    createdCartonModelList.get(position).getNoOfCarton(), createdCartonModelList.get(position).getUOMType(), getCartonPrdsInterface
/*
            (tblStoreProductMappingForDisplaysCarton, productCartonFilledDataModel) -> {
                    *//*    LinkedHashMap<String, String> hmapPrdctOrderQty = productCartonFilledDataModel.getHmapPrdctOrderQty();

                        if (productCartonFilledDataModel.getHmapPrdctOrderQty() != null && productCartonFilledDataModel.getHmapPrdctOrderQty().size() > 0) {
                            for (Map.Entry<String, String> entry : productCartonFilledDataModel.getHmapPrdctOrderQty().entrySet()) {
                                if (!entry.getValue().equals("") && !entry.getValue().equals("0")) {
                                    getOrderData(entry.getKey());
                                }
                            }
                        }


                        for (TblStoreProductMappingForDisplay tblStoreProductMapping : tblStoreProductMappingForDisplaysCarton) {


                            if (hmapflgPicsOrCases != null && hmapflgPicsOrCases.size() > 0) {
                                if (hmapflgPicsOrCases.containsKey("" + tblStoreProductMapping.getPrdNodeID())) {
                                    List<TblUOMMaster> tblUOMMasterListAgainstProduct = tblStoreProductMapping.getTblUOMMasterList();
                                    for (TblUOMMaster tblUOMMaster : tblUOMMasterListAgainstProduct) {
                                        tblUOMMaster.setFlgSelected(0);
                                        if (tblUOMMaster.getBUOMID() == Integer.parseInt(hmapflgPicsOrCases.get("" + tblStoreProductMapping.getPrdNodeID()))) {
                                            tblUOMMaster.setFlgSelected(1);
                                            prdctModelArrayList.setPrdctQtyMappedToPicsOrCases("" + tblStoreProductMapping.getPrdNodeID(), "" + tblUOMMaster.getBUOMID());
                                        }
                                    }


                                }

                            }
                        }
                        orderBookingTotalCalc();
                    *//*
            }*/
//            cartonProductEntryDialog.setCancelable(false);
//            cartonProductEntryDialog.show();

            /*CartonProductEntryDialog cartonProductEntryDialog = new CartonProductEntryDialog((AppCompatActivity) context, context, createdCartonModelList.get(position).getStoreId() + "", "date", "pickerDate", "SN",
                    0, (tblStoreProductMappingForDisplaysCarton, productCartonFilledDataModel) -> {
                        *//*    LinkedHashMap<String, String> hmapPrdctOrderQty = productCartonFilledDataModel.getHmapPrdctOrderQty();

                            if (productCartonFilledDataModel.getHmapPrdctOrderQty() != null && productCartonFilledDataModel.getHmapPrdctOrderQty().size() > 0) {
                                for (Map.Entry<String, String> entry : productCartonFilledDataModel.getHmapPrdctOrderQty().entrySet()) {
                                    if (!entry.getValue().equals("") && !entry.getValue().equals("0")) {
                                        getOrderData(entry.getKey());
                                    }
                                }
                            }


                            for (TblStoreProductMappingForDisplay tblStoreProductMapping : tblStoreProductMappingForDisplaysCarton) {


                                if (hmapflgPicsOrCases != null && hmapflgPicsOrCases.size() > 0) {
                                    if (hmapflgPicsOrCases.containsKey("" + tblStoreProductMapping.getPrdNodeID())) {
                                        List<TblUOMMaster> tblUOMMasterListAgainstProduct = tblStoreProductMapping.getTblUOMMasterList();
                                        for (TblUOMMaster tblUOMMaster : tblUOMMasterListAgainstProduct) {
                                            tblUOMMaster.setFlgSelected(0);
                                            if (tblUOMMaster.getBUOMID() == Integer.parseInt(hmapflgPicsOrCases.get("" + tblStoreProductMapping.getPrdNodeID()))) {
                                                tblUOMMaster.setFlgSelected(1);
                                                prdctModelArrayList.setPrdctQtyMappedToPicsOrCases("" + tblStoreProductMapping.getPrdNodeID(), "" + tblUOMMaster.getBUOMID());
                                            }
                                        }


                                    }

                                }
                            }
                            orderBookingTotalCalc();
                        *//*
                    });
            cartonProductEntryDialog.setCancelable(false);
            cartonProductEntryDialog.show();*/
        });

    }

    @Override
    public int getItemCount() {
        return createdCartonModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout editCarton;
        public TextView tvPrdName, tv_FreeQty, tvNoOfCases, tv_Line_value, tvNetVal;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPrdName = itemView.findViewById(R.id.tvPrdName);
            tvNoOfCases = itemView.findViewById(R.id.tvNoOfCases);
            tv_Line_value = itemView.findViewById(R.id.tv_Line_value);
            tvNetVal = itemView.findViewById(R.id.tvNetVal);
            tv_FreeQty = itemView.findViewById(R.id.tv_FreeQty);
            editCarton = itemView.findViewById(R.id.editCarton);
        }
    }

}
