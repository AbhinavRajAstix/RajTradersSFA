package com.astix.rajtraderssfasales;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ExpandableSpokeWiseDateVisitValueAdapter extends BaseExpandableListAdapter {



    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableSpokeWiseDateVisitValueAdapter(Context context, List<String> listDataHeader,
                                                    HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ex_list_spokeforpjp, null);
        }



        if(_listDataChild.containsKey((_listDataHeader.get(groupPosition).toString().split(Pattern.quote("^"))[0])))

        {
            if( _listDataChild.get(_listDataHeader.get(groupPosition).toString().split(Pattern.quote("^"))[0])!=null)
            {
                List<String> newList = _listDataChild.get(_listDataHeader.get(groupPosition).toString().split(Pattern.quote("^"))[0]);

                for(int i=0;i<newList.size();i++)
                {
                    String childText = newList.get(childPosition).split(Pattern.quote("^"))[0];
                    int flgProductive=0;
                    flgProductive=Integer.parseInt(childText.split(Pattern.quote("~"))[1]);
                    String SName=childText.split(Pattern.quote("~"))[0];

                    TextView txtSpokeName = (TextView) convertView
                            .findViewById(R.id.spoke_name_ex);


                    txtSpokeName.setText((childPosition+1) +". "+SName);
                    txtSpokeName.setTextColor(_context.getResources().getColor(R.color.black));
                    txtSpokeName.setTypeface(null, Typeface.NORMAL);
                    if(flgProductive==1)
                    {
                        txtSpokeName.setTypeface(null, Typeface.BOLD);
                        txtSpokeName.setTextColor(_context.getResources().getColor(R.color.green_submitted));
                    }

                }
            }

        }






        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(_listDataHeader.get(groupPosition).split(Pattern.quote("^"))[0]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String headerTitle = _listDataHeader.get(groupPosition).split(Pattern.quote("^"))[0];
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ex_beat_name_against_spoke_for_pjp_title, null);
        }

        TextView beatNameHeader = (TextView) convertView
                .findViewById(R.id.beat_name_ex);
        beatNameHeader.setText(headerTitle);

        if (isExpanded) {
            beatNameHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_bold_up, 0,
                    0, 0);
        } else {
            // If group is not expanded then change the text back into normal
            // and change the icon

            beatNameHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_bold_down, 0,
                    0, 0);
        }



        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
