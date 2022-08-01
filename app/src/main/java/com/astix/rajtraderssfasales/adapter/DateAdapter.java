package com.astix.rajtraderssfasales.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.SpokeVisitDates;
import com.astix.rajtraderssfasales.model.TblBeatPlans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;


    List<String> beatPlans;
    int c = 0;
    int flg = 0;
        int flgHasSixWeek=0;

    public DateAdapter(Context context, List<String> beatPlans, int flg, int flgHasSixWeek) {
        this.context = context;
        this.beatPlans = beatPlans;
        this.flg = flg;
        this.flgHasSixWeek=flgHasSixWeek;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = null;
        switch (i) {
            case 0:
                v = LayoutInflater.from(context).inflate(R.layout.week_layout, null, false);
                return new WeekHolder(v);
            case 1:
                v = LayoutInflater.from(context).inflate(R.layout.day_layout, null, false);
                return new DaysHolder(v);
            case 2:
                v = LayoutInflater.from(context).inflate(R.layout.date_wise_layout, null, false);
                return new DateViewHolder(v);
            case 3:
                v = LayoutInflater.from(context).inflate(R.layout.date_wise_layout, null, false);
                return new DateViewHolder(v);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        switch (viewHolder.getItemViewType()) {
            case 0:
                WeekHolder weekHolder = (WeekHolder) viewHolder;
                weekHolder.weekTV.setText(beatPlans.get(i).split(Pattern.quote("~"))[1]);
                break;
            case 1:
                DaysHolder daysHolder = (DaysHolder) viewHolder;
                daysHolder.dayTV.setText(beatPlans.get(i).split(Pattern.quote("~"))[1]);
                break;
            case 2:
                DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;
                String str = beatPlans.get(i);

                TblBeatPlans tblBeatPlan = new TblBeatPlans();
                tblBeatPlan.setmflgAttendance(Integer.parseInt(beatPlans.get(i).split(Pattern.quote("^"))[3]));
                tblBeatPlan.setmDate(beatPlans.get(i).split(Pattern.quote("^"))[1]);
                tblBeatPlan.setmPlannedSpoke(beatPlans.get(i).split(Pattern.quote("^"))[2]+"\n"+beatPlans.get(i).split(Pattern.quote("^"))[5]);
                tblBeatPlan.setMflg(Integer.parseInt(beatPlans.get(i).split(Pattern.quote("^"))[0].split(Pattern.quote("~"))[1]));

                int flgForColor = tblBeatPlan.getmflgAttendance();

                if (flgForColor == 1) {
                 //   dateViewHolder.date_details.setBackgroundColor(Color.parseColor("#00df00"));
                    dateViewHolder.date_details.setTag("#00df00");
                } else if (flgForColor == 0) {
                  //  dateViewHolder.date_details.setTag("#FFFFFF");
                }
                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(beatPlans.get(viewHolder.getAdapterPosition()).split(Pattern.quote("^"))[4]);
                    Date date2 = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH).parse(getCurrentDeviceDate("dd-MMM-yyyy"));





                    if (date1.before(date2)) {

                        int flgForAttendance = tblBeatPlan.getMflg();
                        if (flgForAttendance == 0) {
                            dateViewHolder.day_txt.setText("A");
                            dateViewHolder.day_txt.setTextColor(Color.parseColor("#FF0000"));
                        } else if (flgForAttendance == 1) {
                            dateViewHolder.day_txt.setText("P");
                            dateViewHolder.day_txt.setTextColor(Color.parseColor("#008800"));
                        } else {
                            dateViewHolder.day_txt.setText("L");
                            dateViewHolder.day_txt.setTextColor(Color.parseColor("#FFA500"));
                        }

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

if(tblBeatPlan.getmDate().substring(0, 6).equals("29 Nov"))
{
    String sdsdsad="asdsad";
}
                dateViewHolder.date_txt.setText(tblBeatPlan.getmDate().substring(0, 6));

                dateViewHolder.date_layout.setTag(tblBeatPlan.getmDate());

                dateViewHolder.spoke_txt.setText(tblBeatPlan.getmPlannedSpoke());

                dateViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, SpokeVisitDates.class);
                        intent.putExtra("Date_tag", beatPlans.get(viewHolder.getAdapterPosition()).split(Pattern.quote("^"))[4]);
                        intent.putExtra("Color_Code", "0");
                        context.startActivity(intent);


                    }
                });
                break;

            case 3:
                DateViewHolder dateViewHolderEmpty = (DateViewHolder) viewHolder;
                dateViewHolderEmpty.itemView.setVisibility(View.INVISIBLE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return beatPlans.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (beatPlans.get(position).contains("~")) {
            if (beatPlans.get(position).split(Pattern.quote("~"))[0].equals("0")) {
                return 0;
            } else if (beatPlans.get(position).split(Pattern.quote("~"))[0].equals("1")) {
                return 1;
            } else if (beatPlans.get(position).split(Pattern.quote("~"))[0].equals("2")) {
                return 2;
            }
        } else {
            return 3;
        }
        return 3;
    }

    public class DaysHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.day_text)
        TextView dayTV;

        public DaysHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class WeekHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.week)
        TextView weekTV;

        public WeekHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {

        TextView date_txt, spoke_txt, store_txt, day_txt;
        LinearLayout date_layout, date_details;
        ImageView spoke, store;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);


            date_txt = itemView.findViewById(R.id.date_txt);
            spoke_txt = itemView.findViewById(R.id.actual_spoke_txt);
            store_txt = itemView.findViewById(R.id.actual_store_txt);
            day_txt = itemView.findViewById(R.id.day_text);
            date_layout = itemView.findViewById(R.id.date_layout);
            date_details = itemView.findViewById(R.id.date_details);
            spoke = itemView.findViewById(R.id.spoke_icon);
            store = itemView.findViewById(R.id.store_icon);

        }
    }
    public String getCurrentDeviceDate(String format) {
        Date pdaDate = new Date();
      /*  Calendar calendar=Calendar.getInstance();
        calendar.setTime(pdaDate);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        pdaDate=calendar.getTime();*/
        SimpleDateFormat sdfPDaDate = new SimpleDateFormat(format, Locale.ENGLISH);
        String fDatePda = sdfPDaDate.format(pdaDate).trim();
        return fDatePda;
    }
}
