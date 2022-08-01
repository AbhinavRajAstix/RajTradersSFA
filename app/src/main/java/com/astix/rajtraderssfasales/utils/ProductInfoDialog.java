package com.astix.rajtraderssfasales.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.adapter.ProductInfoAdapter;
import com.astix.rajtraderssfasales.model.ProductInfo;
import com.astix.rajtraderssfasales.model.TblInvoiceDetailsServer;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductInfoDialog extends Dialog {
    TextView tvPdtName/*, tv_OrdQty, tv_prdRate, tv_valWithoutTax, tv_Discount, tv_freeQty, tv_TaxPercentage, tv_taxVal, tv_lineVal*/;
    RecyclerView rvData;
    TblInvoiceDetailsServer tblInvoiceDetailsServer;
    Context context;
    double StandardRateForCalculation;


    public ProductInfoDialog(@NonNull Context context, TblInvoiceDetailsServer tblInvoiceDetailsServer,Double StandardRateForCalculation) {
        super(context);
        this.context = context;
        this.tblInvoiceDetailsServer = tblInvoiceDetailsServer;
        this.StandardRateForCalculation=StandardRateForCalculation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_product_info_dialog);
        rvData = findViewById(R.id.rvData);
        tvPdtName = findViewById(R.id.tvPdtName);
//        tv_OrdQty = findViewById(R.id.tv_OrdQty);

        ImageView ivCancel = findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(view -> dismiss());

        double TotInvMRP = tblInvoiceDetailsServer.getOrderVal() +((tblInvoiceDetailsServer.getFreeQty() * StandardRateForCalculation)+(tblInvoiceDetailsServer.getDisVal()));
        //(((tblInvoiceDetailsServer.getOrderQty()* tblInvoiceDetailsServer.getProductPrice())-(tblInvoiceDetailsServer.getDisVal()*tblInvoiceDetailsServer.getOrderQty()))/((tblInvoiceDetailsServer.getOrderQty()*tblInvoiceDetailsServer.getUOMConverstionUnitQty())+tblInvoiceDetailsServer.getFreeQty()))*tblInvoiceDetailsServer.getUOMConverstionUnitQty()
       // double NetRate=(((tblInvoiceDetailsServer.getOrderQty()* tblInvoiceDetailsServer.getProductPrice())-(tblInvoiceDetailsServer.getDisVal()*tblInvoiceDetailsServer.getOrderQty()))/((tblInvoiceDetailsServer.getOrderQty()*tblInvoiceDetailsServer.getUOMConverstionUnitQty())+tblInvoiceDetailsServer.getFreeQty()))*tblInvoiceDetailsServer.getUOMConverstionUnitQty();
      //  TotInvMRP=(((tblInvoiceDetailsServer.getOrderQty()* StandardRateForCalculation)-(tblInvoiceDetailsServer.getDisVal()*tblInvoiceDetailsServer.getOrderQty()))/((tblInvoiceDetailsServer.getOrderQty() * StandardRateForCalculation)+(tblInvoiceDetailsServer.getFreeQty() * tblInvoiceDetailsServer.getProductPrice())));//*tblInvoiceDetailsServer.getUOMConverstionUnitQty();
        //=(((tblInvoiceDetailsServer.getOrderQty()* tblInvoiceDetailsServer.getProductPrice())-((tblInvoiceDetailsServer.getDisVal()*tblInvoiceDetailsServer.getOrderQty())))/((tblInvoiceDetailsServer.getOrderQty())+tblInvoiceDetailsServer.getFreeQty()))*1
      // TotInvMRP=(((tblInvoiceDetailsServer.getOrderQty()* StandardRateForCalculation)-((tblInvoiceDetailsServer.getDisVal()*tblInvoiceDetailsServer.getOrderQty()))));///((tblInvoiceDetailsServer.getOrderQty()+tblInvoiceDetailsServer.getFreeQty())*1
        double BranMargin =TotInvMRP- tblInvoiceDetailsServer.getOrderVal();
        double NetMargin = 0.0;



        double NetMarginPercentage = 0.0;
        if (BranMargin < 0.0) {
            BranMargin = 0.0;
        }
        if (TotInvMRP > 0) {
            NetMarginPercentage = ((BranMargin * 100) / TotInvMRP);
           // NetMarginPercentage = (((BranMargin -TotInvMRP)*100) / BranMargin);
        }

      /*  if(NetRate>0.0)
        {
            NetMarginPercentage=((tblInvoiceDetailsServer.getOrderQty()* tblInvoiceDetailsServer.getProductPrice())-NetRate)/(tblInvoiceDetailsServer.getOrderQty()* tblInvoiceDetailsServer.getProductPrice());
        }*/
       // NetMargin = Double.parseDouble(new DecimalFormat("##.##").format(BranMargin-TotInvMRP));
        NetMargin = Double.parseDouble(new DecimalFormat("##.##").format(BranMargin));
        NetMarginPercentage = Double.parseDouble(new DecimalFormat("##.##").format(NetMarginPercentage));

       // NetRate = Double.parseDouble(new DecimalFormat("##.##").format(NetRate));

        ArrayList<ProductInfo> orderHistoryPerProductArrayList = new ArrayList<>();
        // orderHistoryPerProductArrayList.add(new ProductInfo("Value before TAX", "\u20b9 " + tblInvoiceDetailsServer.getLineValBfrTxAftrDscnt()));
        // orderHistoryPerProductArrayList.add(new ProductInfo("Tax Value", "\u20b9 " + tblInvoiceDetailsServer.getTaxValue() + " (" + tblInvoiceDetailsServer.getTaxRate() + " %)"));
        // orderHistoryPerProductArrayList.add(new ProductInfo("Value After Tax", "\u20b9 " + (tblInvoiceDetailsServer.getLineValBfrTxAftrDscnt() + tblInvoiceDetailsServer.getTaxValue())));
        orderHistoryPerProductArrayList.add(new ProductInfo("MRP", "\u20b9 " +(tblInvoiceDetailsServer.getProductMRP())));
        orderHistoryPerProductArrayList.add(new ProductInfo("Product Rate", "\u20b9 " + tblInvoiceDetailsServer.getProductPrice()));
        orderHistoryPerProductArrayList.add(new ProductInfo("Order Qty", "" + tblInvoiceDetailsServer.getOrderQty() +" ("+tblInvoiceDetailsServer.getUOMName()+")"));
        orderHistoryPerProductArrayList.add(new ProductInfo("Free Qty", "" + tblInvoiceDetailsServer.getFreeQty()));
        orderHistoryPerProductArrayList.add(new ProductInfo("Free Value", "" + new DecimalFormat("##.##").format((tblInvoiceDetailsServer.getFreeQty() * StandardRateForCalculation))));
        orderHistoryPerProductArrayList.add(new ProductInfo("Line Value-MRP", "\u20b9 " +(tblInvoiceDetailsServer.getProductMRP()* tblInvoiceDetailsServer.getOrderQty())));
        orderHistoryPerProductArrayList.add(new ProductInfo("Line Value-Rate", "\u20b9 " +Double.parseDouble(new DecimalFormat("##.##").format((StandardRateForCalculation* tblInvoiceDetailsServer.getOrderQty())))));
        orderHistoryPerProductArrayList.add(new ProductInfo("Line Discount Value", "\u20b9 " + new DecimalFormat("##.##").format(tblInvoiceDetailsServer.getDisVal())));
        orderHistoryPerProductArrayList.add(new ProductInfo("Gross Value", "\u20b9 " + new DecimalFormat("##.##").format(tblInvoiceDetailsServer.getOrderVal())));
        orderHistoryPerProductArrayList.add(new ProductInfo("Net Margin", "\u20b9 " + NetMargin));
        orderHistoryPerProductArrayList.add(new ProductInfo("Net Margin %", NetMarginPercentage + "%"));

        ProductInfoAdapter orderAdapter = new ProductInfoAdapter(context, orderHistoryPerProductArrayList);
        rvData.setAdapter(orderAdapter);
        rvData.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvData.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

//        tv_prdRate = findViewById(R.id.tv_prdRate);
//        tv_valWithoutTax = findViewById(R.id.tv_valWithoutTax);
//        tv_Discount = findViewById(R.id.tv_Discount);
//        tv_freeQty = findViewById(R.id.tv_freeQty);
//        tv_TaxPercentage = findViewById(R.id.tv_TaxPercentage);
//        tv_taxVal = findViewById(R.id.tv_taxVal);
//        tv_lineVal = findViewById(R.id.tv_lineVal);


        tvPdtName.setText(tblInvoiceDetailsServer.getProductShortName());
//        tv_OrdQty.setText("Order Qty : " + tblInvoiceDetailsServer.getOrderQty());
//        tv_prdRate.setText("\u20b9 " + tblInvoiceDetailsServer.getProductPrice());
//        tv_valWithoutTax.setText("\u20b9 " + tblInvoiceDetailsServer.getLineValBfrTxAftrDscnt());
//        tv_Discount.setText("\u20b9 " + tblInvoiceDetailsServer.getDisVal());
//        tv_freeQty.setText(" " + tblInvoiceDetailsServer.getFreeQty());
//        tv_TaxPercentage.setText(tblInvoiceDetailsServer.getTaxRate() + " %");
//        tv_taxVal.setText("\u20b9 " + tblInvoiceDetailsServer.getTaxValue());
//        tv_lineVal.setText("\u20b9 " + tblInvoiceDetailsServer.getOrderVal());

    }
}
