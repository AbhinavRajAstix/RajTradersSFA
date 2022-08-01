package com.astix.rajtraderssfasales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreVisitHistory;
import com.astix.rajtraderssfasales.model.TblProductLevelData;
import com.astix.rajtraderssfasales.model.TblUOMMapping;
import com.astix.rajtraderssfasales.utils.LastVisitProductWiseHistoryDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class TARSOrdersAdapter extends RecyclerView.Adapter<TARSOrdersAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TblGetPDAStoreVisitHistory> visitHistoryList;
    AppDataSource mDataSource;
    HashMap<String, String> hmapflgTeleCallerProductQtyStoreSelection = new HashMap<String, String>();
    HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMappingCOrderHistory;
    public TARSOrdersAdapter(Context mContext, ArrayList<TblGetPDAStoreVisitHistory> visitHistoryList,HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMappingCOrderHistory) {
        this.mContext = mContext;
        this.visitHistoryList = visitHistoryList;
        this.hmapProductTblUOMMappingCOrderHistory=hmapProductTblUOMMappingCOrderHistory;
        this.mDataSource = AppDataSource.getInstance(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_in_product_store_visit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TblGetPDAStoreVisitHistory visitHistory = visitHistoryList.get(position);
        holder.tvDate.setText(visitHistory.getLastCallVisit());
        holder.tvOrderVal.setText(visitHistory.getOrderQty() + " / \u20b9 " + visitHistory.getOrderValue());

        holder.tvOrderVal.setTag(visitHistory.getLastCallVisit() + "^" + visitHistory.getFlgSectinType());
        holder.ivViewProd.setOnClickListener(view -> {
            if (holder.rvProductDetails.getVisibility() == View.VISIBLE) {
//                holder.ll1.setVisibility(View.GONE);
                holder.rvProductDetails.setVisibility(View.GONE);
                holder.ivViewProd.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_down));
//                holder.tvOrderVal.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            } else {
//                holder.ll1.setVisibility(View.GONE);
                holder.rvProductDetails.setVisibility(View.VISIBLE);
                holder.ivViewProd.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_up));
//                holder.tvOrderVal.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up, 0);
            }
            notifyDataSetChanged();
        });

        if (holder.rvProductDetails.getVisibility() == View.VISIBLE) {
            String imgTag = holder.tvOrderVal.getTag().toString();
            int imgType = Integer.parseInt(imgTag.split(Pattern.quote("^"))[1]);
            String imgDate = imgTag.split(Pattern.quote("^"))[0];
            ArrayList<TblProductLevelData> tblProductLevelData = new ArrayList<TblProductLevelData>();
            if (imgType == 1)//GetData from Visit Table
            {
                tblProductLevelData = mDataSource.fnGettblStoreSumuuaryDetailsAgainstOrderID(visitHistory.getStoreID(), "0", imgDate);
            }
            if (imgType == 2)//GetData from Tars Order
            {
                hmapflgTeleCallerProductQtyStoreSelection=mDataSource.fnGethmapflgTeleCallerProductQtyStoreLastVisitTarsOrder(visitHistory.getStoreID(), CommonInfo.flgDrctslsIndrctSls);
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapflgTeleCallerProductQtyStoreSelection);
                Set set2 = map.entrySet();
                Iterator iterator = set2.iterator();
                while (iterator.hasNext()) {
                    Map.Entry me2 = (Map.Entry) iterator.next();
                    TblProductLevelData tblProductLevelData1=new TblProductLevelData();
                    Double otqy=Double.parseDouble(me2.getValue().toString().split(Pattern.quote("^"))[0]);
                    Double oval=Double.parseDouble(me2.getValue().toString().split(Pattern.quote("^"))[1]);
                    int PrdID=Integer.parseInt(me2.getValue().toString().split(Pattern.quote("^"))[2]);
                    if(hmapProductTblUOMMappingCOrderHistory!=null && hmapProductTblUOMMappingCOrderHistory.size()>0 && hmapProductTblUOMMappingCOrderHistory.containsKey(PrdID))
                    {
                        List<TblUOMMapping> tblUOMMappings= hmapProductTblUOMMappingCOrderHistory.get(PrdID);
                        if(tblUOMMappings!=null && tblUOMMappings.size()>0)
                        otqy=otqy/tblUOMMappings.get(0).getRelConversionUnits();

                    }
                    tblProductLevelData1.setPrdName(me2.getKey().toString());
                    tblProductLevelData1.setQty(otqy);
                    tblProductLevelData1.setOrderValue(oval);
                    tblProductLevelData.add(tblProductLevelData1);
                }

                //  tblProductLevelData=mDataSource.fnGettblStoreSumuuaryDetailsAgainstOrderID(visitHistory.getStoreID(),"0",imgDate);
            }

            LastVisitHistoryAdapterProductLevel2 orderAdapter = new LastVisitHistoryAdapterProductLevel2(mContext, tblProductLevelData);
            holder.rvProductDetails.setAdapter(orderAdapter);
            holder.rvProductDetails.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        }
    }

    @Override
    public int getItemCount() {
        return visitHistoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView /*tvContactedBy, */tvDate, tvOrderVal, tvOrderStatus;
        public LinearLayout ll1;
        public ImageView ivViewProd;
        public RecyclerView rvProductDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            ll1 = itemView.findViewById(R.id.ll1);
            tvOrderVal = itemView.findViewById(R.id.tvOrderVal);
            tvDate = itemView.findViewById(R.id.tvDate);
            rvProductDetails = itemView.findViewById(R.id.rvProductDetails);
            ivViewProd = itemView.findViewById(R.id.ivViewProd);
        }
    }
}
