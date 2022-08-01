package com.astix.rajtraderssfasales.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.adapter.InvHistoryAdapter;
import com.astix.rajtraderssfasales.adapter.LastVisitHistoryAdapterProductLevel;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreOrderHistory;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreOrderLastVisitHistory;
import com.astix.rajtraderssfasales.model.TblProductLevelData;

import java.util.ArrayList;
import java.util.List;

public class LastVisitProductWiseHistoryDialog extends Dialog {
    ArrayList<TblProductLevelData> storeOrderHistory;
    TextView tvOrderDate, tvNoProduct;
    RecyclerView rvProductOrderHistory;
    Context context;
    AppDataSource mDataSource;

    public LastVisitProductWiseHistoryDialog(@NonNull Context context, ArrayList<TblProductLevelData> storeOrderHistory) {
        super(context);
        this.context = context;
        this.storeOrderHistory = storeOrderHistory;
        mDataSource = AppDataSource.getInstance(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_inv_prd_wise_dialog_lastvisit);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        rvProductOrderHistory=findViewById(R.id.rvProductOrderHistory);
        tvNoProduct=findViewById(R.id.tvNoProduct);
        ImageView ivCancel = findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(view -> dismiss());


        if (storeOrderHistory.size() > 0) {
         //   tvNoProduct.setVisibility(View.GONE);
            rvProductOrderHistory.setVisibility(View.VISIBLE);

            LastVisitHistoryAdapterProductLevel orderAdapter = new LastVisitHistoryAdapterProductLevel(context, storeOrderHistory);
            rvProductOrderHistory.setAdapter(orderAdapter);
            rvProductOrderHistory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            rvProductOrderHistory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        } else {
          //  tvNoProduct.setVisibility(View.VISIBLE);
            rvProductOrderHistory.setVisibility(View.GONE);
        }

    }
}
