package com.example.jinwe.fireant_project.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.jinwe.fireant_project.Activity.MainActivity;
import com.example.jinwe.fireant_project.Class.Topics;
import com.example.jinwe.fireant_project.DatabaseHandler;
import com.example.jinwe.fireant_project.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by jinwe on 7/5/2018.
 */

public class TopicsAdapter extends BaseAdapter {

    private ArrayList<Topics> topicses;
    private LayoutInflater mInflater;
    private Context mContext;
    private DatabaseHandler mDBHandler;

    public TopicsAdapter(Context context, ArrayList<Topics> topicses, Activity activity){
        mContext = context;
        this.topicses = topicses;
        mInflater = LayoutInflater.from(context);
        mDBHandler = new DatabaseHandler(activity);
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return topicses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        convertView = mInflater.inflate(R.layout.listview_item, null);
        holder = new TopicsAdapter.ViewHolder();
        holder.title = (TextView) convertView.findViewById(R.id.topics);
        holder.details = (TextView) convertView.findViewById(R.id.details);
        holder.down = (Button) convertView.findViewById(R.id.downVote);
        holder.up = (Button) convertView.findViewById(R.id.upVote);
        convertView.setTag(holder);

        holder.title.setText(topicses.get(position).getTopic());
        Spanned spanned = Html.fromHtml("Created at : " + topicses.get(position).getCreated_at() + " up vote :" + topicses.get(position).getUp_vote() + " & down vote :" + topicses.get(position).getDown_vote());
        holder.details.setText(spanned);
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("+1 ", " for down");
                List<Topics> topicList = mDBHandler.getAllTopicsData();
                for(Topics tp: topicList){
                    if(tp.getCreated_at().equals(topicses.get(position).getCreated_at())){
                        int d = topicses.get(position).getDown_vote();
                        d = d+1;
                        tp.setDown_vote(d);
                        mDBHandler.updateDownVote(tp);
                        Intent intent = new Intent("send_back");
                        intent.putExtra("Message", "yes");
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                    }
                }
            }
        });
        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("+1 ", " for up");
                List<Topics> topicList = mDBHandler.getAllTopicsData();
                for(Topics tp: topicList){
                    if(tp.getCreated_at().equals(topicses.get(position).getCreated_at())){
                        Log.i("data of tp : ", " in SQLite " + tp.getCreated_at().toString() + " and in position : " + topicses.get(position).getCreated_at());
                        int u = topicses.get(position).getUp_vote();
                        u = u+1;
                        tp.setUp_vote(u);
                        mDBHandler.updateUpVote(tp);
                        Intent intent = new Intent("send_back");
                        intent.putExtra("Message", "yes");
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                    }
                }
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView title, details;
        Button up, down;
    }
}
