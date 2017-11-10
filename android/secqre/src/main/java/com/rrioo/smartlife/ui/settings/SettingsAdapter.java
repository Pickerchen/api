package com.rrioo.smartlife.ui.settings;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.helper.SimpleOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/13 14:27 1.0
 * @time 2017/10/13 14:27
 * @project secQreNew3.0 com.rrioo.smartlife.ui.mSettings
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/13 14:27
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.Holder> {

    private Context mContext;
    private List<SettingsBean> mSettings;

    private SimpleOnItemClickListener onItemClickListener;

    public SettingsAdapter(Context context, List<SettingsBean> settings) {
        this.mContext = context;
        this.mSettings = settings;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_settings, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        SettingsBean settingsBean = mSettings.get(position);
        holder.iconIv.setImageResource(settingsBean.drawable);
        holder.settingTv.setText(settingsBean.text);
        if (settingsBean.bottomIsLine) {
            holder.line.setVisibility(View.VISIBLE);
        } else {
            holder.line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mSettings.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_settings_adapter_iv)
        ImageView iconIv;
        @BindView(R.id.id_settings_adapter_tv)
        TextView settingTv;

        @BindView(R.id.id_settings_adapter_line)
        View line;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setClickable(true);
            itemView.setBackgroundResource(R.drawable.settings_item_ripple);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        SettingsBean settingsBean = mSettings.get(position);
                        onItemClickListener.onItemClick(SettingsAdapter.this,
                                v, position, settingsBean);
                    }
                }
            });
        }
    }

    public static class SettingsBean {
        public static final int TYPE_NOTIFICATION = 0;
        public static final int TYPE_SECURITY = 1;
        public static final int TYPE_SCENE = 2;
        public static final int TYPE_DEVICES_ROOM = 3;
        public static final int TYPE_PAIR_HOME = 4;
        public static final int TYPE_MEMBERS = 5;
        public static final int TYPE_SUPPORT = 6;


        int settingType;

        int drawable;
        int text;
        /**
         * view下方有没有线条
         */
        boolean bottomIsLine;

        /**
         * view间隔
         */
        int bottomGap;

        public SettingsBean(int drawable, int text, boolean bottomIsLine, int bottomGap,int type) {
            this.drawable = drawable;
            this.text = text;
            this.bottomIsLine = bottomIsLine;
            this.bottomGap = bottomGap;
            this.settingType = type;
        }

        public int getSettingType() {
            return settingType;
        }

        public void setSettingType(int settingType) {
            this.settingType = settingType;
        }

        public int getDrawable() {
            return drawable;
        }

        public void setDrawable(int drawable) {
            this.drawable = drawable;
        }

        public int getText() {
            return text;
        }

        public void setText(int text) {
            this.text = text;
        }

        public boolean isBottomIsLine() {
            return bottomIsLine;
        }

        public void setBottomIsLine(boolean bottomIsLine) {
            this.bottomIsLine = bottomIsLine;
        }

        public int getBottomGap() {
            return bottomGap;
        }

        public void setBottomGap(int bottomGap) {
            this.bottomGap = bottomGap;
        }
    }


    public RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            RecyclerView.ViewHolder viewHolder = parent.findContainingViewHolder(view);
            int position = viewHolder.getAdapterPosition();
            if (mSettings != null) {
                SettingsBean settingsBean = mSettings.get(position);
                outRect.set(0, 0, 0, settingsBean.bottomGap);
            }
        }
    };

    public SimpleOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(SimpleOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
