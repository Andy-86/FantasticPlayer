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
import com.example.andy.player.activity.MusicAcitivity;
import com.example.andy.player.bean.SongListInfo;

import java.util.List;

/**
 * Created by andy on 2017/12/15.
 */

public class RemoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<SongListInfo> listInfos;
    private OnRItemClickListner listner;
    public static final  int Type_Title=0;
    public static final  int Type_Content=1;
    public RemoteListAdapter(Context context, List<SongListInfo> listInfos){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.listInfos=listInfos;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==Type_Title){
            View view=layoutInflater.inflate(R.layout.view_holder_playlist_profile,parent,false);
            return new TitleViewHolder(view);
        }else{
            View view=layoutInflater.inflate(R.layout.view_holder_playlist,parent,false);
            return new SongLisViewHolder(view);
        }

    }

    public void setListner(OnRItemClickListner listner){
        this.listner=listner;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SongListInfo info=listInfos.get(position);
        if(listInfos.get(position).isSonglist){
            if (info.firsSongname != null) {
                ((SongLisViewHolder)holder).name.setText(info.Title);
                ((SongLisViewHolder)holder).song1.setText("1" + " " + info.firsSongname + "  " + info.firsSingername);
                ((SongLisViewHolder)holder).song2.setText("2" + " " + info.secondSongname + "  " + info.secondSingername);
                ((SongLisViewHolder)holder).song3.setText("3" + " " + info.thirdSingername + "  " + info.thirdSingername);
            }
            if (info.imageUrl != null) {
                Glide.with(context)
                        .load(info.imageUrl)
                        .placeholder(R.drawable.default_cover)
                        .error(R.drawable.default_cover)
                        .into( ((SongLisViewHolder)holder).ivCover);
            }
            ((SongLisViewHolder)holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner!=null&& (!MusicAcitivity.isPlayFragmentShow))
                    listner.onClick(info);
                }
            });
        }else {

            ((TitleViewHolder)holder).title.setText(info.Title);
        }
    }


    @Override
    public int getItemCount() {
        return listInfos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(!listInfos.get(position).isSonglist){
            return Type_Title;
        }else
            return Type_Content;
    }

    class SongLisViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;
        public ImageView ivCover;
        public TextView song1;
        public TextView song2;
        public TextView song3;
        public TextView name;
        public SongLisViewHolder(View itemView) {
            super(itemView);
            ivCover=(ImageView)itemView.findViewById(R.id.iv_cover);
            song1=(TextView)itemView.findViewById(R.id.tv_music_1);
            song2=(TextView)itemView.findViewById(R.id.tv_music_2);
            song3=(TextView)itemView.findViewById(R.id.tv_music_3);
            name=(TextView)itemView.findViewById(R.id.tv_list_name);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.songinfo_contents);
        }



    }
    class TitleViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TitleViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.tv_profile);
        }
    }

    public interface OnRItemClickListner {
        public void onClick(SongListInfo info);
    }
}
