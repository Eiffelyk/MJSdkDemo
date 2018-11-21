package com.dataenlighten.aimjsdk.demo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dataenlighten.aimjsdk.demo.R;
import com.dataenlighten.aimjsdk.demo.RelatedPartActivity;
import com.dataenlighten.aimjsdk.demo.bean.PartBean;
import com.dataenlighten.aimjsdk.demo.custom.GlideApp;

/**
 * Created by kangjian on 2017/7/30.
 */

public class ResultMoreViewHolder extends RecyclerView.ViewHolder {
    private TextView mName;
    private TextView mOe;
    private TextView price;
    private ImageView mImg;
    private ImageView nextImg;
    private RelativeLayout mDetailRl;
    private boolean canClick = false;


    public ResultMoreViewHolder(View itemView, boolean canClick) {
        super(itemView);
        mName = itemView.findViewById(R.id.tv_name);
        mImg = itemView.findViewById(R.id.item_img);
        mDetailRl = itemView.findViewById(R.id.rl_detail);
        nextImg = itemView.findViewById(R.id.iv_detail);
        mOe = itemView.findViewById(R.id.tv_oe);
        price = itemView.findViewById(R.id.tv_price);
        this.canClick = canClick;
        if (!canClick) {
            nextImg.setVisibility(View.GONE);
            mDetailRl.setClickable(false);
        }
    }

    public void setData(final Context context, final PartBean partBean) {
        //首先查看是否被添加到SelectManager中
        if (partBean.getThumbnailImage() != null) {
            if (partBean.getThumbnailImage().startsWith("http://")) {//android P下载文件不支持http。后续这个字段会直接返回https的
                partBean.setThumbnailImage(partBean.getThumbnailImage().replace("http://", "https://"));
            }
            GlideApp.with(context)
                    .load(partBean.getThumbnailImage())
                    .placeholder(R.drawable.placeholder)//图片加载出来前，显示的图片
                    .error(R.drawable.placeholder)
                    .into(mImg);
        }

        mName.setText(partBean.getStandardPartName());
        mOe.setText("OE号: " + partBean.getPartNumber());
        price.setText("4S店价格: " + partBean.getPartPrice());
        mDetailRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canClick) {
                    return;
                }
                Intent intent = new Intent(context, RelatedPartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("partBean", partBean);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}
