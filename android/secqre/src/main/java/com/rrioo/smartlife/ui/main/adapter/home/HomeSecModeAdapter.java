package com.rrioo.smartlife.ui.main.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.R;
import com.rrioo.smartlife.util.ClickHelper;
import com.sen5.lib.entity.mode.SecurityMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/30 14:45 1.0
 * @time 2017/9/30 14:45
 * @project secQreNew3.0 com.rrioo.smartlife.ui.main.adapter
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/30 14:45
 */

public class HomeSecModeAdapter extends RecyclerView.Adapter<HomeSecModeAdapter.ViewHolder> {

    private Context mContext;
    private List<SecurityMode> mModes;

    //not position but the SecurityMode.sec_mode
    private int seletedMode;
    private OnSecModeItemClickListener onItemClickListener;

    public HomeSecModeAdapter(Context mContext, List<SecurityMode> mModes, int seleted) {
        this.mContext = mContext;
        this.mModes = mModes;
        this.seletedMode = seleted;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_home_sec_mode, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SecurityMode securityMode = mModes.get(position);

        switch (securityMode.getSec_mode()) {
            case SecurityMode.MODE_AWAY:
                holder.iv.setImageResource(R.drawable.selector_mode_away);
                holder.tv.setText(R.string.mode_away);
                break;
            case SecurityMode.MODE_HOME:
                holder.iv.setImageResource(R.drawable.selector_mode_stay);
                holder.tv.setText(R.string.mode_stay);
                break;
            case SecurityMode.MODE_DISARM:
                holder.iv.setImageResource(R.drawable.selector_mode_disarm);
                holder.tv.setText(R.string.mode_disarm);
                break;

        }
        if (seletedMode == securityMode.getSec_mode()) {
            holder.iv.setSelected(true);
            holder.tv.setSelected(true);
        } else {
            holder.iv.setSelected(false);
            holder.tv.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return mModes.size();
    }

    public void setCurrentMode(int secMode) {
        seletedMode = secMode;
        notifyDataSetChanged();
    }

    public int getCurrentMode() {
        return seletedMode;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_home_mode_adapter_iv)
        ImageView iv;
        @BindView(R.id.id_home_mode_adapter_tv)
        TextView tv;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new ClickHelper.ClickListener() {
                @Override
                public void onClickView(View v) {
                    int adapterPosition = getAdapterPosition();
                    seletedMode = mModes.get(adapterPosition).getSec_mode();
                    notifyDataSetChanged();
                    if (onItemClickListener != null) {
                        onItemClickListener.onSecModeItemClick(adapterPosition,
                                mModes.get(adapterPosition));
                    }
                }
            });
        }
    }

    public OnSecModeItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnSecModeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnSecModeItemClickListener {
        void onSecModeItemClick(int position, SecurityMode mode);
    }
}
