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
import com.astix.rajtraderssfasales.adapter.StoreOrderHistoryAdapter;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.model.TblGetPDAStoreVisitHistory;

import java.util.ArrayList;

public class SchemeApplicableDialog extends Dialog {
    String storeId;
    TextView tvNoProduct;
    RecyclerView rvSchemes;
    Context context;
    AppDataSource mDataSource;

    public SchemeApplicableDialog(@NonNull Context context, String storeId) {
        super(context);
        this.context = context;
        this.storeId = storeId;
        mDataSource = AppDataSource.getInstance(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_scheme_applicable_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        tvNoProduct = findViewById(R.id.tvNoProduct);
        rvSchemes = findViewById(R.id.rvSchemes);

        ImageView ivCancel = findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(view -> dismiss());

        tvNoProduct.setOnClickListener(view -> {
            OrderHistoryDialog orderHistoryDialog = new OrderHistoryDialog(context, "prductName", storeId, "234");
            orderHistoryDialog.show();
        });

        ArrayList<TblGetPDAStoreVisitHistory> tblStoreOrders = mDataSource.getStoreVisitHistory(storeId);
        if (tblStoreOrders.size() > 0) {
            tvNoProduct.setVisibility(View.GONE);
            rvSchemes.setVisibility(View.VISIBLE);

//            StoreOrderHistoryAdapter orderAdapter = new StoreOrderHistoryAdapter(context, tblStoreOrders);
//            rvSchemes.setAdapter(orderAdapter);
            rvSchemes.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        } else {
            tvNoProduct.setVisibility(View.VISIBLE);
            rvSchemes.setVisibility(View.GONE);
        }

    }
}
