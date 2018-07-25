package com.example.mao.real_timevideo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private String videoName[]={"少林寺广场","少林寺广场","少林寺广场","少林寺广场","少林寺广场","香江熊猫","香江熊猫","香江熊猫","香江熊猫","香江熊猫","香江熊猫","香港卫视","香港卫视","香港卫视","香港卫视","香港卫视"};
    private String UrlAddress[]={"http://livechina.cntv.wscdns.com:8000/live/flv/channel1384",
            "http://livechina.cntv.wscdns.com:8000/live/flv/channel1384",
            "http://livechina.cntv.wscdns.com:8000/live/flv/channel1384",
            "http://livechina.cntv.wscdns.com:8000/live/flv/channel1384",
            "http://livechina.cntv.wscdns.com:8000/live/flv/channel1384",
            "http://fms.cntv.lxdns.com/live/flv/channel89.flv",
            "http://fms.cntv.lxdns.com/live/flv/channel89.flv",
            "http://fms.cntv.lxdns.com/live/flv/channel89.flv",
            "http://fms.cntv.lxdns.com/live/flv/channel89.flv",
            "http://fms.cntv.lxdns.com/live/flv/channel89.flv",
            "http://fms.cntv.lxdns.com/live/flv/channel89.flv",
            "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8",
            "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8",
            "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8",
            "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8",
            "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8"};

    public MyAdapter(Context context) {
        mContext=context;
    }

    @Override
    public int getCount() {
        return videoName.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        viewHolder holder;
        if(view==null){
            view=LayoutInflater.from(mContext).inflate(R.layout.listview_item,null);
            holder=new viewHolder();
            holder.textView=view.findViewById(R.id.listItem);
            view.setTag(holder);
        }else{
            holder=(viewHolder) view.getTag();
        }
        holder.textView.setText(videoName[i]);
        holder.textView.setTextSize(25);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,LiveActivity.class);
                intent.putExtra("url",UrlAddress[i]);
                intent.putExtra("title",videoName[i]);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    public final class viewHolder{
        TextView textView;
    }
}
