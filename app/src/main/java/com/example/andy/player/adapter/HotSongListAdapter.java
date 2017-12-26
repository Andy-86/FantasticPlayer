package com.example.andy.player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andy.player.R;
import com.example.andy.player.aidl.SongBean;

import java.util.List;

/**
 * Created by andy on 2017/12/18.
 */

public class HotSongListAdapter extends RecyclerView.Adapter<HotSongListAdapter.MyViewHolder>{
    Context context;
    List<SongBean> list;
    LayoutInflater inflater;
    private OnRItemClickListner listner;
   public HotSongListAdapter(Context context, List<SongBean> list){
       this.context=context;
       this.list=list;
       inflater=LayoutInflater.from(context);
   }

    public void setListner(OnRItemClickListner listner){
        this.listner=listner;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.view_holder_music,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final SongBean songBean=list.get(position);
        if(songBean!=null) {
            holder.singerName.setText(songBean.getSingername());
            holder.songname.setText(songBean.getSongname());
            Glide.with(context)
                    .load(songBean.getAlbumpic_small())
                    .placeholder(R.drawable.default_cover)
                    .error(R.drawable.default_cover)
                    .into(holder.imageView);
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onClick(songBean);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView songname;
        public TextView singerName;
        public ImageView more;
        public LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.iv_cover);
            songname=(TextView)itemView.findViewById(R.id.tv_title);
            singerName=(TextView)itemView.findViewById(R.id.tv_artist);
            more=(ImageView)itemView.findViewById(R.id.iv_more);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.just_for_click);
        }
    }

    public interface OnRItemClickListner {
        public void onClick(SongBean songBean);
    }
}
