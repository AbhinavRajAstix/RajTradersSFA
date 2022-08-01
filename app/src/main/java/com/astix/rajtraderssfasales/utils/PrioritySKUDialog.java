package com.astix.rajtraderssfasales.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.adapter.ProritySKUAdapter;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.model.TblStoreProductMappingForDisplay;

import java.util.List;

public class PrioritySKUDialog extends Dialog {
    String storeId;
    TextView tvNoProduct;
    RecyclerView rvPrioritySKU;
    Context context;
    AppDataSource mDataSource;
    List<TblStoreProductMappingForDisplay> listPrioritySKU;
    public PrioritySKUDialog(@NonNull Context context, String storeId, List<TblStoreProductMappingForDisplay> listPrioritySKU) {
        super(context);
        this.context = context;
        this.storeId = storeId;
        this.listPrioritySKU=listPrioritySKU;
        mDataSource = AppDataSource.getInstance(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_priority_sku_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        tvNoProduct = findViewById(R.id.tvNoProduct);
        rvPrioritySKU = findViewById(R.id.rvPrioritySKU);

        ImageView ivCancel = findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(view -> dismiss());

        tvNoProduct.setOnClickListener(view -> {
            OrderHistoryDialog orderHistoryDialog = new OrderHistoryDialog(context, "prductName", storeId, "234");
            orderHistoryDialog.show();
        });

//        ArrayList<TblGetPDAStoreVisitHistory> tblStoreOrders = mDataSource.getStoreVisitHistory(storeId);
        if (listPrioritySKU.size() > 0) {
            tvNoProduct.setVisibility(View.GONE);
            rvPrioritySKU.setVisibility(View.VISIBLE);

            ProritySKUAdapter orderAdapter = new ProritySKUAdapter(context, listPrioritySKU);
            rvPrioritySKU.setAdapter(orderAdapter);
            rvPrioritySKU.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        } else {
            tvNoProduct.setVisibility(View.VISIBLE);
            rvPrioritySKU.setVisibility(View.GONE);
        }

    }
}
