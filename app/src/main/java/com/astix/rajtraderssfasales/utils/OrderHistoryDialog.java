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
import com.astix.rajtraderssfasales.adapter.ProductOrderHistoryAdapter;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.model.TblProductLevelData;

import java.util.ArrayList;

public class OrderHistoryDialog extends Dialog {
    String prdName, storeId, productId;
    TextView tvPdtName, tvNoProduct;
    RecyclerView rvProductOrderHistory;
    Context context;
    AppDataSource mDataSource;

    public OrderHistoryDialog(@NonNull Context context, String prdName, String storeId, String productId) {
        super(context);
        this.context = context;
        this.storeId = storeId;
        this.productId = productId;
        this.prdName = prdName;
        mDataSource = AppDataSource.getInstance(context);

        Log.v("OrderHistory", "Deat: " + storeId + " " + productId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_order_history_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvNoProduct = findViewById(R.id.tvNoProduct);
        tvPdtName = findViewById(R.id.tvPdtName);
        rvProductOrderHistory = findViewById(R.id.rvProductOrderHistory);
        tvPdtName.setText(prdName);

        ImageView ivCancel = findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(view -> dismiss());

        ArrayList<TblProductLevelData> tblProductLevelData = mDataSource.getPrdInvForStore(storeId, productId);
        if (tblProductLevelData.size() > 0) {
            tvNoProduct.setVisibility(View.GONE);
            rvProductOrderHistory.setVisibility(View.VISIBLE);

            ProductOrderHistoryAdapter orderAdapter = new ProductOrderHistoryAdapter(context, tblProductLevelData);
            rvProductOrderHistory.setAdapter(orderAdapter);
            rvProductOrderHistory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            rvProductOrderHistory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        } else {
            tvNoProduct.setVisibility(View.VISIBLE);
            rvProductOrderHistory.setVisibility(View.GONE);
        }

    }
}
