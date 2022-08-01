package com.astix.rajtraderssfasales;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class IncentiveStaticPage extends BaseActivity  {
    ImageView imgVw_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incentive_sku);

        imgVw_back=findViewById(R.id.imgVw_back);

        imgVw_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            }
        });


    }


}
