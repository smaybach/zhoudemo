package com.example.zhoudemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhoudemo.R;
import com.example.zhoudemo.bean.NewsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by FLOWER on 2017/11/19.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoder>{
    private Context context;
    private List<NewsBean.DataBean> list;
    private OnClickListener onClickListener;

    public MyAdapter(Context context, List<NewsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    public interface OnClickListener{
        void OnItemClick(int position);

        void OnLongItemClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        MyViewHoder hoder = new MyViewHoder(view);
        return hoder;
    }

    @Override
    public void onBindViewHolder(MyViewHoder holder, final int position) {
        holder.tv.setText(list.get(position).getContent());
        Picasso.with(context).load(list.get(position).getImage_url()).into(holder.iv);
        String vedio_url = list.get(position).getVedio_url();
        holder.player.setUp(vedio_url
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, list.get(position).getTitle());
        holder.player.thumbImageView.setImageURI(Uri.parse(list.get(position).getImage_url()));
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener !=null){
                    onClickListener.OnItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        private final ImageView iv;
        private final TextView tv;
        private final LinearLayout ll;
        private final JZVideoPlayerStandard player;

        public MyViewHoder(View itemView) {
            super(itemView);
            iv = (ImageView)itemView.findViewById(R.id.iv);
            tv = (TextView)itemView.findViewById(R.id.tv);
            ll = (LinearLayout)itemView.findViewById(R.id.ll);
            player = (JZVideoPlayerStandard)itemView.findViewById(R.id.player);
        }
    }
}
