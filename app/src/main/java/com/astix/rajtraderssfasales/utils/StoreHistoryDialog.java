package com.astix.rajtraderssfasales.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.adapter.ProductOrderHistoryAdapter;
import com.astix.rajtraderssfasales.adapter.StoreOrderHistoryAdapter;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreOrderHistory;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreVisitHistory;
import com.astix.rajtraderssfasales.model.TblProductLevelData;

import java.util.ArrayList;

public class StoreHistoryDialog extends Dialog {
    String storeId;
    LinearLayout llNoProduct;
    RecyclerView rvStoreHistory;
    Context context;
    AppDataSource mDataSource;

    public StoreHistoryDialog(@NonNull Context context, String storeId) {
        super(context);
        this.context = context;
        this.storeId = storeId;
        mDataSource = AppDataSource.getInstance(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_store_history_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llNoProduct = findViewById(R.id.llNoProduct);
        rvStoreHistory = findViewById(R.id.rvStoreHistory);

        ImageView ivCancel = findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(view -> dismiss());

        ArrayList<TblGetPDAStoreOrderHistory> tblStoreOrders = mDataSource.getStoreOrderHistory(storeId);
        if (tblStoreOrders.size() > 0) {
            llNoProduct.setVisibility(View.GONE);
            rvStoreHistory.setVisibility(View.VISIBLE);

            StoreOrderHistoryAdapter orderAdapter = new StoreOrderHistoryAdapter(context, tblStoreOrders);
            rvStoreHistory.setAdapter(orderAdapter);
            rvStoreHistory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            rvStoreHistory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        } else {
            llNoProduct.setVisibility(View.VISIBLE);
            rvStoreHistory.setVisibility(View.GONE);
        }

    }
}
