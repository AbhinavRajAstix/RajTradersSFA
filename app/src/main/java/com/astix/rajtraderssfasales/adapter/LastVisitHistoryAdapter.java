package com.astix.rajtraderssfasales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.Common.CommonInfo;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreOrderLastVisitHistory;
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

public class LastVisitHistoryAdapter extends RecyclerView.Adapter<LastVisitHistoryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TblGetPDAStoreVisitHistory> visitHistoryList;
    AppDataSource mDataSource;
    HashMap<String, String> hmapflgTeleCallerProductQtyStoreSelection = new HashMap<String, String>();
    HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMapping;

    public LastVisitHistoryAdapter(Context mContext, ArrayList<TblGetPDAStoreVisitHistory> visitHistoryList,HashMap<Integer, List<TblUOMMapping>> hmapProductTblUOMMapping) {
        this.mContext = mContext;
        this.visitHistoryList = visitHistoryList;
        this.hmapProductTblUOMMapping=hmapProductTblUOMMapping;
        this.mDataSource= AppDataSource.getInstance(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_store_visit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TblGetPDAStoreVisitHistory visitHistory = visitHistoryList.get(position);
//        holder.tvContactedBy.setText(visitHistory.getContactedBy());
        holder.tvDate.setText(visitHistory.getLastCallVisit());
        holder.tvOrderVal.setText(visitHistory.getOrderQty() + " / \u20b9 " + visitHistory.getOrderValue());
       // holder.tvOrderStatus.setText(visitHistory.getOrderStatus());
       // holder.tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.black));
      /*  if (visitHistory.getOrderStatus().equalsIgnoreCase("completed")) {
            holder.tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.new_green));
        } else if (visitHistory.getOrderStatus().equalsIgnoreCase("in process")) {
            holder.tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.new_orange));
        }*/

        holder.ivViewProd.setTag(visitHistory.getLastCallVisit()+"^"+visitHistory.getFlgSectinType());
        holder.ivViewProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imgTag=view.getTag().toString();
                int imgType=Integer.parseInt(imgTag.split(Pattern.quote("^"))[1]);
                String imgDate=imgTag.split(Pattern.quote("^"))[0];
                ArrayList<TblProductLevelData> tblProductLevelData  =new ArrayList<TblProductLevelData>();
                if(imgType==1)//GetData from Visit Table
                {
                    tblProductLevelData=mDataSource.fnGettblStoreSumuuaryDetailsAgainstOrderID(visitHistory.getStoreID(),"0",imgDate);
                    for(TblProductLevelData tblProductLevelData1:tblProductLevelData)
                    {
                        if(hmapProductTblUOMMapping!=null && hmapProductTblUOMMapping.size()>0 && hmapProductTblUOMMapping.containsKey(tblProductLevelData1.getPrdNodeId()))
                        {
                            Double otqy=tblProductLevelData1.getQty();
                            List<TblUOMMapping> tblUOMMappings= hmapProductTblUOMMapping.get(tblProductLevelData1.getPrdNodeId());
                            otqy=otqy/tblUOMMappings.get(0).getRelConversionUnits();
                            if(tblProductLevelData1.getPrdName()!=null)
                                tblProductLevelData1.setPrdName(tblProductLevelData1.getPrdName().toString());
                            else
                                tblProductLevelData1.setPrdName("NA");

                            tblProductLevelData1.setQty(otqy);
                        }
                    }
                }
                if(imgType==2)//GetData from Tars Order
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
                        if(hmapProductTblUOMMapping!=null && hmapProductTblUOMMapping.size()>0 && hmapProductTblUOMMapping.containsKey(PrdID))
                        {
                            List<TblUOMMapping> tblUOMMappings= hmapProductTblUOMMapping.get(PrdID);
                            otqy=otqy/tblUOMMappings.get(0).getRelConversionUnits();

                        }
                        if(me2.getKey()!=null)
                        tblProductLevelData1.setPrdName(me2.getKey().toString());
                        else
                            tblProductLevelData1.setPrdName("NA");

                        tblProductLevelData1.setQty(otqy);
                        tblProductLevelData1.setOrderValue(oval);
                        tblProductLevelData.add(tblProductLevelData1);
                    }

                  //  tblProductLevelData=mDataSource.fnGettblStoreSumuuaryDetailsAgainstOrderID(visitHistory.getStoreID(),"0",imgDate);
                }

                LastVisitProductWiseHistoryDialog lastVisitProductWiseHistoryDialog=new LastVisitProductWiseHistoryDialog(mContext,tblProductLevelData);
                lastVisitProductWiseHistoryDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return visitHistoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView /*tvContactedBy, */tvDate, tvOrderVal, tvOrderStatus;
        public ImageView ivViewProd;

        public ViewHolder(View itemView) {
            super(itemView);
//            tvContactedBy = itemView.findViewById(R.id.tvContactedBy);
            tvOrderVal = itemView.findViewById(R.id.tvOrderVal);
            tvDate = itemView.findViewById(R.id.tvDate);
            //tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            ivViewProd= itemView.findViewById(R.id.ivViewProd);
        }
    }
}
