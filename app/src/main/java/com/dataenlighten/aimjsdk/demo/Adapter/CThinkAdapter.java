package com.dataenlighten.aimjsdk.demo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dataenlighten.aimjsdk.demo.R;

import java.util.List;

/**
 * Created by kangjian on 2017/8/19.
 */

public class CThinkAdapter extends RecyclerView.Adapter<CThinkViewHolder> {
    private Context mContext;
    private List<String> mList;
    private OnThinkItemClickListener mItemClick;

    public CThinkAdapter(Context mContext, List<String> mList, OnThinkItemClickListener mItemClick) {
        this.mContext = mContext;
        this.mList = mList;
        this.mItemClick=mItemClick;
    }

    @Override
    public CThinkViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cthink, viewGroup, false);
        CThinkViewHolder holder=new CThinkViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CThinkViewHolder cThinkViewHolder, final int i) {
        cThinkViewHolder.setData(i,mList.get(i));
        cThinkViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClick.onThinkItemClick(mList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnThinkItemClickListener{
        void onThinkItemClick(String key);
    }
}
