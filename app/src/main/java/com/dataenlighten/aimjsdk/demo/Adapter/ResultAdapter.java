package com.dataenlighten.aimjsdk.demo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dataenlighten.aimjsdk.demo.R;
import com.dataenlighten.aimjsdk.demo.bean.PartBean;

import java.util.List;


/**
 * Created by kangjian on 2017/7/30.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultMoreViewHolder> {

    private Context mContext;
    private List<PartBean> mList;
    private LayoutInflater mInflater;
    private boolean itemCanClick = true;

    public ResultAdapter(Context mContext, List<PartBean> mList, boolean itemCanClick) {
        this.mContext = mContext;
        this.mList = mList;
        this.itemCanClick = itemCanClick;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<PartBean> list){
        this.mList = list;
    }
    @Override
    public ResultMoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_result_more, parent, false);
        return  new ResultMoreViewHolder(view,itemCanClick);
    }

    @Override
    public void onBindViewHolder(ResultMoreViewHolder holder, int position) {
        holder.setData(mContext, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
