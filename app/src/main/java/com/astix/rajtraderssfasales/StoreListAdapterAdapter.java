package com.astix.rajtraderssfasales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.model.TblStoreListMasterDataRetrive;
import com.astix.rajtraderssfasales.utils.AppUtils;

import java.util.List;

public class StoreListAdapterAdapter extends RecyclerView.Adapter<StoreListAdapterAdapter.ViewHolder> {

    public Context context;
//    private LayoutInflater inflater;

//    private List<String> listStore = null;

//    private List<String> listOutletId;
//    EditText txtTextSearch;
//    String tagVal;
//    private ArrayList<String> listStoreOrigin;
//    private ArrayList<String> listStoreIDOrigin;

//    ListView listViewOption;

//    SearchListCommunicator communicator;

    //    Dialog listDialog;
//    TextView textView;
    List<TblStoreListMasterDataRetrive> tblStoreListMasterDataRetriveList;
    String userDate, pickerDate;

    public StoreListAdapterAdapter(Context context, List<TblStoreListMasterDataRetrive> tblStoreListMasterDataRetriveList, String userDate, String pickerDate) {
        this.context = context;

//        inflater = LayoutInflater.from(context);
	/*	this.listStore=listStore;
		this.listDialog=listDialog;
		this.textView=textView;
		this.txtTextSearch=txtTextSearch;
		this.listOutletId=listOutletId;
		this.listViewOption=listViewOption;
		this.tagVal=tagVal;*/
        this.tblStoreListMasterDataRetriveList = tblStoreListMasterDataRetriveList;
        this.pickerDate = pickerDate;
        this.userDate = userDate;
/*		this.listStoreOrigin=new ArrayList<String>();
		
		this.listStoreIDOrigin=new ArrayList<String>();
		
		
		listStoreIDOrigin.addAll(this.listOutletId);
		this.listStoreOrigin.addAll(this.listStore);
	*/


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_selection_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final TblStoreListMasterDataRetrive masterDataRetrive = tblStoreListMasterDataRetriveList.get(position);

        holder.tv_StoreAddress.setText("Not Available");
        holder.tv_StoreAddress.setTag("Not Available" + "^" + masterDataRetrive.getStoreContactNo());
        holder.show_path.setTag(masterDataRetrive.getStoreID());
        holder.edit_store.setTag(masterDataRetrive.getStoreID());
        holder.tv_StoreDistance.setTag(masterDataRetrive.getStoreID());
        holder.tv_StoreDistance.setText("");
        if(masterDataRetrive.getDistanceNear()>=1000) {
            //holder.tv_StoreDistance.setText("" + masterDataRetrive.getDistanceNear() + " meters");
           //holder.tv_StoreDistance.setText(KilometerDisplay(Double.parseDouble(""+masterDataRetrive.getDistanceNear())));//convertMeterToKilometer

            holder.tv_StoreDistance.setText("Km: "+(int)(convertMeterToKilometer(Double.parseDouble(""+masterDataRetrive.getDistanceNear()))));
        }
        else if(masterDataRetrive.getDistanceNear()>0 && masterDataRetrive.getDistanceNear()<1000) {
            //holder.tv_StoreDistance.setText("" + masterDataRetrive.getDistanceNear() + " meters");
            //holder.tv_StoreDistance.setText(KilometerDisplay(Double.parseDouble(""+masterDataRetrive.getDistanceNear())));//convertMeterToKilometer
           // holder.tv_StoreDistance.setText("Mtrs: "+convertMeterToKilometer(Double.parseDouble(""+masterDataRetrive.getDistanceNear())));
            holder.tv_StoreDistance.setText("Mtrs: "+masterDataRetrive.getDistanceNear());
        }
        else {
            holder.tv_StoreDistance.setText("");
        }

        if (masterDataRetrive.getStoreAddress() != null) {

            holder.tv_StoreAddress.setText(masterDataRetrive.getStoreAddress());
            SpannableString content = new SpannableString(masterDataRetrive.getStoreAddress());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            holder.tv_StoreAddress.setTextColor(Color.parseColor("#3f51b5"));
            holder.tv_StoreAddress.setText(content);
            holder.tv_StoreAddress.setTag(masterDataRetrive.getStoreAddress() + "^" + masterDataRetrive.getStoreContactNo());
        }
        holder.tvRouteName.setText(masterDataRetrive.getRouteName());
        holder.ll_RouteHeader.setVisibility(View.GONE);
        if (masterDataRetrive.getFlgShowHeader() == 1) {
            holder.ll_RouteHeader.setVisibility(View.VISIBLE);
        }

        holder.ivTC.setVisibility((masterDataRetrive.getFlgTCOrder() == 1) ? View.VISIBLE : View.GONE);
        holder.imgContactVerviedOrNot.setVisibility((masterDataRetrive.getFlgMapped() == 0) ? View.VISIBLE : View.GONE);

        holder.imgStarStore.setVisibility((masterDataRetrive.getFlgStarStore() == 1) ? View.VISIBLE : View.GONE);
        holder.imgUnProductiveStore.setVisibility((masterDataRetrive.getFlgUnProductiveStore() == 1) ? View.VISIBLE : View.GONE);

//        holder.ivTC.setOnClickListener(view -> {
//
//        });

        holder.tv_StoreName.setText(masterDataRetrive.getStoreName());
//        holder.tv_StoreName.setTextSize(11.0f);
        holder.rg1StoreName.setChecked(false);
        holder.rg1StoreName.setTag(masterDataRetrive.getStoreID());
//        if (masterDataRetrive.getInvoiceVal() != 0.0) {
        holder.OrderValue.setText("\u20b9 " + Math.round(masterDataRetrive.getInvoiceVal()));
//        }
        holder.rg1StoreName.setChecked(false);
        if (masterDataRetrive.getFlgRadioButtonSelected() == 1) {
            holder.rg1StoreName.setChecked(true);
        } else {
            holder.rg1StoreName.setChecked(false);
        }
//        holder.rg1StoreName.setOnCheckedChangeListener((compoundButton, b) -> {
//            holder.cv1.performClick();
//        });
//        holder.tv_UpdatedContactNumber.setTag(masterDataRetrive.getStoreID());
        holder.imgContactVerviedOrNot.setTag(masterDataRetrive.getStoreID());
//        holder.tv_UpdatedContactNumber.setText(masterDataRetrive.getStoreContactNo());
//        if(masterDataRetrive.getUpdatedContactnumber()!=null && !masterDataRetrive.getUpdatedContactnumber().equals("NA"))
//        holder.tv_UpdatedContactNumber.setText(masterDataRetrive.getUpdatedContactnumber().toString());
//        holder.imgContactVerviedOrNot.setImageResource(R.drawable.notverfiredphone);
//        if(masterDataRetrive.getFlgMapped()==1)
//        {
//            holder.imgContactVerviedOrNot.setImageResource(R.drawable.verfiredphone);
//        }
//        else
//        {
//            holder.imgContactVerviedOrNot.setImageResource(R.drawable.notverfiredphone);
//        }
//
//        holder.tv_UpdatedContactNumber.setText("");
        if (masterDataRetrive.getFlgMapped() == 0) {
            //  if (masterDataRetrive.getUpdatedContactnumber() != null && !masterDataRetrive.getUpdatedContactnumber().equals("NA"))
//            holder.tv_UpdatedContactNumber.setText(masterDataRetrive.getUpdatedContactnumber().toString());
/*
                holder.imgContactVerviedOrNot.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ready4GetLoc = new Intent(context, StoreEditActivityContactLocation.class);
                        ready4GetLoc.putExtra("storeID", view.getTag().toString());
                        context.startActivity(ready4GetLoc);
                        ((Activity) context).finish();
                    }
                });*/
       /* holder.tv_UpdatedContactNumber.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ready4GetLoc = new Intent(context, StoreEditActivityContactLocation.class);
                ready4GetLoc.putExtra("storeID", view.getTag().toString());
                context.startActivity(ready4GetLoc);
                ((Activity)context).finish();
            }
        });   */
        }


        holder.rg1StoreName.setOnClickListener(view -> {
            String selStoreID = holder.rg1StoreName.getTag().toString();
            for (TblStoreListMasterDataRetrive wp : tblStoreListMasterDataRetriveList) {
                if (wp.getStoreID().equals(selStoreID)) {
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
//            RadioButton rb = (RadioButton) v;
            String selStoreID = holder.rg1StoreName.getTag().toString();
          /*  for (TblStoreListMasterDataRetrive wp : tblStoreListMasterDataRetriveList) {
                if (wp.getStoreID().equals(selStoreID)) {
                    wp.setFlgRadioButtonSelected(1);
                } else {
                    wp.setFlgRadioButtonSelected(0);
                }
            }
            notifyDataSetChanged();
            InterfaceRetrofitStoreSeleted intrfc = (InterfaceRetrofitStoreSeleted) context;
            intrfc.startVisitOfSelectedStore(selStoreID);*/
        });
        if (CommonInfo.hmapAppMasterFlags != null && CommonInfo.hmapAppMasterFlags.containsKey("flgStoreDetailsEdit")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgStoreDetailsEdit") == 1) {
                if (masterDataRetrive.getFlgStoreEdit() == 1) {
                    //holder.edit_store.setVisibility(View.VISIBLE);
                    holder.edit_store.setVisibility(View.INVISIBLE);
                }
                else
                    holder.edit_store.setVisibility(View.GONE);
            } else {
                holder.edit_store.setVisibility(View.GONE);
            }
        } else {
            holder.edit_store.setVisibility(View.INVISIBLE);
        }
       // holder.edit_store.setVisibility(View.VISIBLE);
        holder.edit_store.setVisibility(View.INVISIBLE);

        if (masterDataRetrive.getISNewStore() == 1)//New Store
        {
            holder.edit_store.setVisibility(View.INVISIBLE);
        }

        holder.tv_StoreAddress.setOnClickListener(view -> {
            //context.showStoreAddressAlert(view.getTag().toString());
            InterfaceRetrofitStoreSeleted intrfc = (InterfaceRetrofitStoreSeleted) context;

            intrfc.ShowSelectedStoreAddress(view.getTag().toString());

        });


        holder.show_path.setOnClickListener(view -> {
            if (masterDataRetrive.getStoreLatitude() > 0.0 && masterDataRetrive.getStoreLongitude() > 0.0) {

                Intent intent = new Intent(context, ShowPathActivity.class);
                intent.putExtra("StoreID", view.getTag().toString());
            /*intent.putExtra("FinalLat", fnLati);
            intent.putExtra("FinalLon", fnLongi);*/
                context.startActivity(intent);
            } else {

                AlertDialog.Builder alertDialogOrderSubmission =
                        new AlertDialog.Builder(context)
                                .setTitle(R.string.genTermInformation)
                                .setMessage("Address Not Available")
                                .setPositiveButton(context.getResources().getString(R.string.AlertDialogOkButton),
                                        (dialog, which) -> {
                                            dialog.dismiss();
                                        });
                alertDialogOrderSubmission.setIcon(R.drawable.info_icon);
                AlertDialog alert = alertDialogOrderSubmission.create();
                alert.show();
            }
        });

        holder.edit_store.setOnClickListener(view -> {
            Intent intent = new Intent(context, StoreEditActivity1.class);

            intent.putExtra("storeID", view.getTag().toString());
            intent.putExtra("activityFrom", "StoreSelection");
            intent.putExtra("userdate", userDate);
            intent.putExtra("pickerDate", pickerDate);
            intent.putExtra("token", AppUtils.getToken(context));
            intent.putExtra("rID", masterDataRetrive.getRouteID());
            context.startActivity(intent);
            ((Activity) context).finish();
            //context.getA;
        });


        holder.tv_StoreName.setTextColor(ContextCompat.getColor(context, R.color.black));
        holder.rg1StoreName.setEnabled(true);
        holder.rg1StoreName.setTypeface(null, Typeface.NORMAL);

        if (masterDataRetrive.getSstat().equals("4")) {
            holder.rg1StoreName.setEnabled(false);
//            holder.tv_StoreName.setTypeface(null, Typeface.BOLD);
            holder.tv_StoreName.setTextColor(ContextCompat.getColor(context, R.color.green_submitted));
        }
        if (masterDataRetrive.getSstat().equals("3") || masterDataRetrive.getSstat().equals("5") || masterDataRetrive.getSstat().equals("6")) {
            if (masterDataRetrive.getISNewStore() == 1)//New Store
            {
                if (!masterDataRetrive.getVisitCode().equals("NA")) {
                    if (masterDataRetrive.getStoreClose() == 1) {
                        SpannableString spanString = new SpannableString(masterDataRetrive.getStoreName());
                        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, android.R.color.holo_orange_dark)), 0, spanString.length(), 0);
                        spanString.setSpan(new StrikethroughSpan(), 0, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.tv_StoreName.setText(spanString);

                    } else {
                        SpannableString spanString = new SpannableString(masterDataRetrive.getStoreName());
                        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.green)), 0, spanString.length(), 0);
                        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
//                        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
                        holder.tv_StoreName.setText(spanString);
                    }
                } else {
                    SpannableString spanString = new SpannableString(masterDataRetrive.getStoreName());
                    spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)), 0, spanString.length(), 0);
                    spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                    holder.tv_StoreName.setText(spanString);
                }
            } else {
                holder.tv_StoreName.setTextColor(ContextCompat.getColor(context, R.color.green));
            }
        } else if (masterDataRetrive.getSstat().equals("1")) {
            holder.tv_StoreName.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        //holder.prdName.setText(tblStoreListMasterDataRetriveList.get(position).getProductName());
/*
		if (!TextUtils.isEmpty(actualVisitProductInfoRecordOnlyCategoryWiseNew.get(position).getStock()))
			holder.etCartQty.setText(actualVisitProductInfoRecordOnlyCategoryWiseNew.get(position).getStock());
		else
			holder.etCartQty.setText("0");*/

       /* holder.et_unit.setText(hmapPrdctAndDisplayUnitData.get(prductId));
        holder.et_unit.setEnabled(false);*/


    }

    @Override
    public int getItemCount() {
        return tblStoreListMasterDataRetriveList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        public RadioButton rg1StoreName;
        public TextView OrderValue, tv_StoreName, tv_LastVisitDate, tv_StoreAddress;
        public ImageView ivTC, imgContactVerviedOrNot, edit_store, show_path,imgStarStore,imgUnProductiveStore;
        public CardView cv1;
        public EditText et_unit;
        public TextView tvMinus, tvAddQty,tv_StoreDistance;
        public AppCompatTextView tvLastStockDate, tvExecQtyDate;
        LinearLayout ll_RouteHeader;
        TextView tvRouteName;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            cv1 = itemView.findViewById(R.id.cv1);
            rg1StoreName = itemView.findViewById(R.id.rg1StoreName);
            ll_RouteHeader = itemView.findViewById(R.id.ll_RouteHeader);
            tvRouteName = itemView.findViewById(R.id.tvRouteName);
            OrderValue = itemView.findViewById(R.id.OrderValue);

            tv_StoreName = itemView.findViewById(R.id.tv_StoreName);
            tv_LastVisitDate = itemView.findViewById(R.id.tv_LastVisitDate);
            tv_StoreAddress = itemView.findViewById(R.id.tv_StoreAddress);
            tv_StoreDistance=itemView.findViewById(R.id.tv_StoreDistance);
            edit_store = itemView.findViewById(R.id.edit_store);
            show_path = itemView.findViewById(R.id.show_path);
            imgContactVerviedOrNot = itemView.findViewById(R.id.imgContactVerviedOrNot);
            ivTC = itemView.findViewById(R.id.ivTC);
            imgStarStore = itemView.findViewById(R.id.imgStarStore);
            imgUnProductiveStore = itemView.findViewById(R.id.imgUnProductiveStore);
        }
    }
       public static double convertMeterToKilometer(Double totalDistance) {
        return (double) (totalDistance * 0.001);
    }
}