package com.astix.rajtraderssfasales;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.TblActualVsTargetReport;

import java.util.List;

public class TargetVsAchievAdapter extends RecyclerView.Adapter<TargetVsAchievAdapter.ViewHolder>{

	public Context context;
	private LayoutInflater inflater;
	public int flgRecylType;

	List<TblActualVsTargetReport> tblActualVsTargetReport;
	String userDate,pickerDate;
	public TargetVsAchievAdapter(Context context, List<TblActualVsTargetReport> tblActualVsTargetReport, int flgRecylType)
	{
		this.context=context;

		inflater=LayoutInflater.from(context);

		this.tblActualVsTargetReport=tblActualVsTargetReport;
		this.flgRecylType=flgRecylType;

	}
	//91D2A6
	//B8D6FA
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_targetvsacheiv_row_newformat, parent, false);
		/*if(flgRecylType==1)
		{
			view.setBackgroundResource(R.drawable.edit_text_tva1bg);
		}
		if(flgRecylType==2)
		{
			view.setBackgroundResource(R.drawable.edit_text_tva2bg);
		}*/
		ViewHolder holder = new ViewHolder(view);

		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		//tvProductName,tvTodayTarget,tvTodayAchieved,tvTodayBal,tvMTDTarget,tvMTDAchieved,tvMTDBal;

		holder.tvProductName.setText(tblActualVsTargetReport.get(position).getDescr());
		holder.tvProductName.setTypeface(null,Typeface.NORMAL);
		holder.tvProductName.setTextColor(Color.parseColor("#000000"));

		holder.tvTodayTarget.setText(""+(int)tblActualVsTargetReport.get(position).getTodayTarget());
		holder.tvTodayAchieved.setText(""+tblActualVsTargetReport.get(position).getTodayAchieved());
		holder.tvTodayBal.setText(""+Math.round(tblActualVsTargetReport.get(position).getTodayBal()));
		holder.tvMTDTarget.setText(""+Math.round(tblActualVsTargetReport.get(position).getMonthTarget()));
		holder.tvMTDAchieved.setText(""+Math.round(tblActualVsTargetReport.get(position).getMonthAchieved()));
		holder.tvMTDBal.setText(""+Math.round(tblActualVsTargetReport.get(position).getMonthBal())+"%");

		holder.tvTodayTarget.setTextColor(Color.parseColor("#000000"));
		holder.tvTodayAchieved.setTextColor(Color.parseColor("#000000"));
		holder.tvTodayBal.setTextColor(Color.parseColor("#000000"));

		holder.tvMTDTarget.setTextColor(Color.parseColor("#000000"));
		holder.tvMTDAchieved.setTextColor(Color.parseColor("#000000"));
		holder.tvMTDBal.setTextColor(Color.parseColor("#000000"));

		if(tblActualVsTargetReport.get(position).getFlgStyleBold()==1)
		{
			holder.tvProductName.setTextSize(12.0f);
			holder.tvProductName.setTypeface(null,Typeface.BOLD);

			holder.tvTodayTarget.setTypeface(null,Typeface.BOLD);
			holder.tvTodayAchieved.setTypeface(null,Typeface.BOLD);
			holder.tvTodayBal.setTypeface(null,Typeface.BOLD);
			holder.tvMTDTarget.setTypeface(null,Typeface.BOLD);
			holder.tvMTDAchieved.setTypeface(null,Typeface.BOLD);
			holder.tvMTDBal.setTypeface(null,Typeface.BOLD);

		}
		if(flgRecylType==1)
		{
			holder.tvTodayTarget.setBackgroundResource(R.drawable.edit_text_tva1bg);
			holder.tvTodayAchieved.setBackgroundResource(R.drawable.edit_text_tva1bg);
			holder.tvTodayBal.setBackgroundResource(R.drawable.edit_text_tva1bg);
			holder.tvMTDTarget.setBackgroundResource(R.drawable.edit_text_tva1bg);
			holder.tvMTDAchieved.setBackgroundResource(R.drawable.edit_text_tva1bg);
			holder.tvMTDBal.setBackgroundResource(R.drawable.edit_text_tva1bg);



		}
		if(flgRecylType==2)
		{
			holder.tvTodayTarget.setBackgroundResource(R.drawable.edit_text_tva2bg);
			holder.tvTodayAchieved.setBackgroundResource(R.drawable.edit_text_tva2bg);
			holder.tvTodayBal.setBackgroundResource(R.drawable.edit_text_tva2bg);
			holder.tvMTDTarget.setBackgroundResource(R.drawable.edit_text_tva2bg);
			holder.tvMTDAchieved.setBackgroundResource(R.drawable.edit_text_tva2bg);
			holder.tvMTDBal.setBackgroundResource(R.drawable.edit_text_tva2bg);

		}

		//if(tblActualVsTargetReport.get(position).getTodayflg()==1){

		DrawableCompat.setTint(holder.tvTodayTarget.getBackground(), ContextCompat.getColor(context, R.color.color1));
		DrawableCompat.setTint(holder.tvTodayAchieved.getBackground(), ContextCompat.getColor(context, R.color.color1));
		DrawableCompat.setTint(holder.tvTodayBal.getBackground(), ContextCompat.getColor(context, R.color.color1));


			/*holder.tvTodayTarget.setBackgroundColor(Color.parseColor("#FFC9C1"));
			holder.tvTodayAchieved.setBackgroundColor(Color.parseColor("#FFC9C1"));
			holder.tvTodayBal.setBackgroundColor(Color.parseColor("#FFC9C1"));*/
		/*}
		else if(tblActualVsTargetReport.get(position).getTodayflg()==2){
			DrawableCompat.setTint(holder.tvTodayTarget.getBackground(), ContextCompat.getColor(context, R.color.color2));
			DrawableCompat.setTint(holder.tvTodayAchieved.getBackground(), ContextCompat.getColor(context, R.color.color2));
			DrawableCompat.setTint(holder.tvTodayBal.getBackground(), ContextCompat.getColor(context, R.color.color2));
			*//*holder.tvTodayTarget.setBackgroundColor(Color.parseColor("#E7E287"));
			holder.tvTodayAchieved.setBackgroundColor(Color.parseColor("#E7E287"));
			holder.tvTodayBal.setBackgroundColor(Color.parseColor("#E7E287"));*//*
		}
		else if(tblActualVsTargetReport.get(position).getTodayflg()==3){
			DrawableCompat.setTint(holder.tvTodayTarget.getBackground(), ContextCompat.getColor(context, R.color.color3));
			DrawableCompat.setTint(holder.tvTodayAchieved.getBackground(), ContextCompat.getColor(context, R.color.color3));
			DrawableCompat.setTint(holder.tvTodayBal.getBackground(), ContextCompat.getColor(context, R.color.color3));

			*//*holder.tvTodayTarget.setBackgroundColor(Color.parseColor("#D5F7DB"));
			holder.tvTodayAchieved.setBackgroundColor(Color.parseColor("#D5F7DB"));
			holder.tvTodayBal.setBackgroundColor(Color.parseColor("#D5F7DB"));*//*
		}
		else if(tblActualVsTargetReport.get(position).getTodayflg()==4){
			DrawableCompat.setTint(holder.tvTodayTarget.getBackground(), ContextCompat.getColor(context, R.color.color4));
			DrawableCompat.setTint(holder.tvTodayAchieved.getBackground(), ContextCompat.getColor(context, R.color.color4));
			DrawableCompat.setTint(holder.tvTodayBal.getBackground(), ContextCompat.getColor(context, R.color.color4));

			*//*holder.tvTodayTarget.setBackgroundColor(Color.parseColor("#7ABB86"));
			holder.tvTodayAchieved.setBackgroundColor(Color.parseColor("#7ABB86"));
			holder.tvTodayBal.setBackgroundColor(Color.parseColor("#7ABB86"));*//*
		}*/

		//if(tblActualVsTargetReport.get(position).getMonthflg()==1){

		DrawableCompat.setTint(holder.tvMTDTarget.getBackground(), ContextCompat.getColor(context, R.color.color1));
		DrawableCompat.setTint(holder.tvMTDAchieved.getBackground(), ContextCompat.getColor(context, R.color.color1));
		DrawableCompat.setTint(holder.tvMTDBal.getBackground(), ContextCompat.getColor(context, R.color.color1));


			/*holder.tvMTDTarget.setBackgroundColor(Color.parseColor("#FFC9C1"));
			holder.tvMTDAchieved.setBackgroundColor(Color.parseColor("#FFC9C1"));
			holder.tvMTDBal.setBackgroundColor(Color.parseColor("#FFC9C1"));*/
		/*}
		else if(tblActualVsTargetReport.get(position).getMonthflg()==2){
			DrawableCompat.setTint(holder.tvMTDTarget.getBackground(), ContextCompat.getColor(context, R.color.color2));
			DrawableCompat.setTint(holder.tvMTDAchieved.getBackground(), ContextCompat.getColor(context, R.color.color2));
			DrawableCompat.setTint(holder.tvMTDBal.getBackground(), ContextCompat.getColor(context, R.color.color2));

			*//*holder.tvMTDTarget.setBackgroundColor(Color.parseColor("#E7E287"));
			holder.tvMTDAchieved.setBackgroundColor(Color.parseColor("#E7E287"));
			holder.tvMTDBal.setBackgroundColor(Color.parseColor("#E7E287"));*//*
		}
		else if(tblActualVsTargetReport.get(position).getMonthflg()==3){
			DrawableCompat.setTint(holder.tvMTDTarget.getBackground(), ContextCompat.getColor(context, R.color.color3));
			DrawableCompat.setTint(holder.tvMTDAchieved.getBackground(), ContextCompat.getColor(context, R.color.color3));
			DrawableCompat.setTint(holder.tvMTDBal.getBackground(), ContextCompat.getColor(context, R.color.color3));

			*//*holder.tvMTDTarget.setBackgroundColor(Color.parseColor("#D5F7DB"));
			holder.tvMTDAchieved.setBackgroundColor(Color.parseColor("#D5F7DB"));
			holder.tvMTDBal.setBackgroundColor(Color.parseColor("#D5F7DB"));*//*
		}
		else if(tblActualVsTargetReport.get(position).getMonthflg()==4){
			DrawableCompat.setTint(holder.tvMTDTarget.getBackground(), ContextCompat.getColor(context, R.color.color4));
			DrawableCompat.setTint(holder.tvMTDAchieved.getBackground(), ContextCompat.getColor(context, R.color.color4));
			DrawableCompat.setTint(holder.tvMTDBal.getBackground(), ContextCompat.getColor(context, R.color.color4));

			*//*holder.tvMTDTarget.setBackgroundColor(Color.parseColor("#FD3417"));
			holder.tvMTDAchieved.setBackgroundColor(Color.parseColor("#FD3417"));
			holder.tvMTDBal.setBackgroundColor(Color.parseColor("#7ABB86"));*//*
		}
*/
		if(tblActualVsTargetReport.get(position).getDescr().contains("Sec Sales"))
		{


			DrawableCompat.setTint(holder.tvTodayTarget.getBackground(), ContextCompat.getColor(context, R.color.col_yellow));
			DrawableCompat.setTint(holder.tvTodayAchieved.getBackground(), ContextCompat.getColor(context, R.color.col_yellow));
			DrawableCompat.setTint(holder.tvTodayBal.getBackground(), ContextCompat.getColor(context, R.color.col_yellow));
			DrawableCompat.setTint(holder.tvMTDTarget.getBackground(), ContextCompat.getColor(context, R.color.col_yellow));
			DrawableCompat.setTint(holder.tvMTDAchieved.getBackground(), ContextCompat.getColor(context, R.color.col_yellow));
			DrawableCompat.setTint(holder.tvMTDBal.getBackground(), ContextCompat.getColor(context, R.color.col_yellow));




		}
		if(tblActualVsTargetReport.get(position).getDescr().contains("Total"))
		{


			DrawableCompat.setTint(holder.tvTodayTarget.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach3));
			DrawableCompat.setTint(holder.tvTodayAchieved.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach3));
			DrawableCompat.setTint(holder.tvTodayBal.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach3));
			DrawableCompat.setTint(holder.tvMTDTarget.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach3));
			DrawableCompat.setTint(holder.tvMTDAchieved.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach3));
			DrawableCompat.setTint(holder.tvMTDBal.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach3));

			//holder.tvProductName.setTextColor(Color.parseColor("#ffffff"));


			holder.tvTodayTarget.setTextColor(Color.parseColor("#ffffff"));
			holder.tvTodayAchieved.setTextColor(Color.parseColor("#ffffff"));
			holder.tvTodayBal.setTextColor(Color.parseColor("#ffffff"));

			holder.tvMTDTarget.setTextColor(Color.parseColor("#ffffff"));
			holder.tvMTDAchieved.setTextColor(Color.parseColor("#ffffff"));
			holder.tvMTDBal.setTextColor(Color.parseColor("#ffffff"));
		}


		if(tblActualVsTargetReport.get(position).getDescr().contains("SFO Total"))
		{


			DrawableCompat.setTint(holder.tvTodayTarget.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach1));
			DrawableCompat.setTint(holder.tvTodayAchieved.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach1));
			DrawableCompat.setTint(holder.tvTodayBal.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach1));
			DrawableCompat.setTint(holder.tvMTDTarget.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach1));
			DrawableCompat.setTint(holder.tvMTDAchieved.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach1));
			DrawableCompat.setTint(holder.tvMTDBal.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach1));

			holder.tvTodayTarget.setTextColor(Color.parseColor("#000000"));
			holder.tvTodayAchieved.setTextColor(Color.parseColor("#000000"));
			holder.tvTodayBal.setTextColor(Color.parseColor("#000000"));

			holder.tvMTDTarget.setTextColor(Color.parseColor("#000000"));
			holder.tvMTDAchieved.setTextColor(Color.parseColor("#000000"));
			holder.tvMTDBal.setTextColor(Color.parseColor("#000000"));

			holder.tvMTDTarget.setTypeface(null,Typeface.BOLD);
			holder.tvMTDAchieved.setTypeface(null,Typeface.BOLD);
			holder.tvMTDBal.setTypeface(null,Typeface.BOLD);
		}


		if(tblActualVsTargetReport.get(position).getDescr().contains("SBO Total"))
		{


			DrawableCompat.setTint(holder.tvTodayTarget.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach2));
			DrawableCompat.setTint(holder.tvTodayAchieved.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach2));
			DrawableCompat.setTint(holder.tvTodayBal.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach2));
			DrawableCompat.setTint(holder.tvMTDTarget.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach2));
			DrawableCompat.setTint(holder.tvMTDAchieved.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach2));
			DrawableCompat.setTint(holder.tvMTDBal.getBackground(), ContextCompat.getColor(context, R.color.col_tagvsach2));

			holder.tvTodayTarget.setTextColor(Color.parseColor("#000000"));
			holder.tvTodayAchieved.setTextColor(Color.parseColor("#000000"));
			holder.tvTodayBal.setTextColor(Color.parseColor("#000000"));

			holder.tvMTDTarget.setTextColor(Color.parseColor("#000000"));
			holder.tvMTDAchieved.setTextColor(Color.parseColor("#000000"));
			holder.tvMTDBal.setTextColor(Color.parseColor("#000000"));

			holder.tvMTDTarget.setTypeface(null,Typeface.BOLD);
			holder.tvMTDAchieved.setTypeface(null,Typeface.BOLD);
			holder.tvMTDBal.setTypeface(null,Typeface.BOLD);
		}

	}

	@Override
	public int getItemCount() {
		return tblActualVsTargetReport.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		public View layout;

		public TextView tvProductName,tvTodayTarget,tvTodayAchieved,tvTodayBal,tvMTDTarget,tvMTDAchieved,tvMTDBal;


		public ViewHolder(View itemView) {
			super(itemView);
			layout = itemView;

			tvProductName = itemView.findViewById(R.id.tvProductName);
			tvTodayTarget = itemView.findViewById(R.id.tvTodayTarget);
			tvTodayAchieved = itemView.findViewById(R.id.tvTodayAchievednew);
			tvTodayBal = itemView.findViewById(R.id.tvTodayBal);
			tvMTDTarget = itemView.findViewById(R.id.tvMTDTarget);
			tvMTDAchieved = itemView.findViewById(R.id.tvMTDAchieved);
			tvMTDBal = itemView.findViewById(R.id.tvMTDBal);

		}
	}

}
