package com.example.andy.player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andy.player.R;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.interfaces.ClickMoreListner;

import java.util.List;

/**
 * Created by mac on 2018/3/5.
 */

public class PopSongListAdapter extends RecyclerView.Adapter<PopSongListAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    List<SongBean> data;
    private ClickMoreListner<SongBean> clickMoreListner;

    public void setClickMoreListner(ClickMoreListner<SongBean> clickMoreListner) {
        this.clickMoreListner = clickMoreListner;
    }

    public PopSongListAdapter(Context context, List<SongBean> list) {
        this.context = context;
        this.data = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PopSongListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_holder_pop_songitem, parent, false);
        //有自定义的Holder实例，改进：从单独封装的Holder基类中获取
        return new PopSongListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.songname.setText(data.get(position).getSongname());
        holder.songname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickMoreListner!=null){
                    clickMoreListner.ClickMore(data.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView songname;

        public MyViewHolder(View itemView) {
            super(itemView);
            songname=(TextView) itemView.findViewById(R.id.pop_songname);

        }
    }
}
