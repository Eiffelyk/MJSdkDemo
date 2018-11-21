package com.dataenlighten.aimjsdk.demo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dataenlighten.aimjsdk.demo.R;


/**
 * Created by kangjian on 2017/8/19.
 */

public class CThinkViewHolder extends RecyclerView.ViewHolder {
    private TextView mNum;
    private TextView mCThink;

    public CThinkViewHolder(View itemView) {
        super(itemView);
        mNum = (TextView) itemView.findViewById(R.id.item_num);
        mCThink = (TextView) itemView.findViewById(R.id.item_cthink);
    }

    public void setData(int position, final String content) {
        mNum.setText((position + 1) + "");
        mCThink.setText(content);
    }
}
