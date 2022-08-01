package com.astix.rajtraderssfasales.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.astix.rajtraderssfasales.adapter.ProductOrderHistoryAdapter;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreOrderHistory;
import com.astix.rajtraderssfasales.model.TblProductLevelData;

import java.util.ArrayList;

public class InvHistoryDialog extends Dialog {
    TblGetPDAStoreOrderHistory storeOrderHistory;
    TextView tvOrderDate, tvNoProduct;
    RecyclerView rvProductOrderHistory;
    Context context;
    AppDataSource mDataSource;

    public InvHistoryDialog(@NonNull Context context, TblGetPDAStoreOrderHistory storeOrderHistory) {
        super(context);
        this.context = context;
        this.storeOrderHistory = storeOrderHistory;
        mDataSource = AppDataSource.getInstance(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_inv_history_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvNoProduct = findViewById(R.id.tvNoProduct);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        rvProductOrderHistory = findViewById(R.id.rvProductOrderHistory);
        tvOrderDate.setText("Order Date : " + storeOrderHistory.getInvDate());
        ImageView ivCancel = findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(view -> dismiss());

        ArrayList<TblProductLevelData> tblProductLevelData = mDataSource.getOrderInvForStore("" + storeOrderHistory.getStoreID(), "" + storeOrderHistory.getOrderID());
        if (tblProductLevelData.size() > 0) {
            tvNoProduct.setVisibility(View.GONE);
            rvProductOrderHistory.setVisibility(View.VISIBLE);

            InvHistoryAdapter orderAdapter = new InvHistoryAdapter(context, tblProductLevelData);
            rvProductOrderHistory.setAdapter(orderAdapter);
            rvProductOrderHistory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            rvProductOrderHistory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        } else {
            tvNoProduct.setVisibility(View.VISIBLE);
            rvProductOrderHistory.setVisibility(View.GONE);
        }

    }
}
