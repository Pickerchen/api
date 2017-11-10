package com.rrioo.smartlife.ui.main.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.helper.SimpleOnItemClickListener;
import com.sen5.lib.entity.room.Room;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/30 16:17 1.0
 * @time 2017/9/30 16:17
 * @project secQreNew3.0 com.rrioo.smartlife.ui.main.adapter
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/30 16:17
 */

public class HomeRoomAdapter extends RecyclerView.Adapter<HomeRoomAdapter.ViewHolder> {

    private Context mContext;
    private List<Room> mRooms;

    private SimpleOnItemClickListener onItemClickListener;

    public HomeRoomAdapter(Context mContext, List<Room> mRooms) {
        this.mContext = mContext;
        this.mRooms = mRooms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_home_room, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(getRoomName(mRooms.get(position)));
    }

    private String getRoomName(Room room) {
        if (room.getRoom_id() == Room.DEFAULT_ROOM_ID) {
            return mContext.getResources().getString(R.string.default_room);
        }
        return room.getRoom_name();
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_home_room_adapter_tv)
        TextView tv;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Room room = mRooms.get(position);
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(HomeRoomAdapter.this,
                                itemView, position, room);
                    }
                }
            });
        }
    }

    public SimpleOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(SimpleOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
