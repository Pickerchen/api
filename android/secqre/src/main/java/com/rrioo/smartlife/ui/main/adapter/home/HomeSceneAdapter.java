package com.rrioo.smartlife.ui.main.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.util.ClickHelper;
import com.sen5.lib.entity.scene.Scene;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/30 15:31 1.0
 * @time 2017/9/30 15:31
 * @project secQreNew3.0 com.rrioo.smartlife.ui.main.adapter
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/30 15:31
 */

public class HomeSceneAdapter extends RecyclerView.Adapter<HomeSceneAdapter.ViewHolder> {
    private Context mContext;
    private List<Scene> mScenes;
    private int selectedId;
    private HomeSceneItemClickListener onItemClickListener;

    public HomeSceneAdapter(Context mContext, List<Scene> mScenes, int lastSceneId) {
        this.mContext = mContext;
        this.mScenes = mScenes;
        this.selectedId = lastSceneId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_home_scene, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Scene scene = mScenes.get(position);
        int imgRes = 0;
        int colorRes = 0;
        switch (position) {
            case 0:
                imgRes = R.drawable.ic_scene_red_default;
                colorRes = R.color.color_scene_red;
                break;
            case 1:
                imgRes = R.drawable.ic_scene_green_default;
                colorRes = R.color.color_scene_green;
                break;
            case 2:
                imgRes = R.drawable.ic_scene_yellow_default;
                colorRes = R.color.color_scene_yellow;
                break;
            case 3:
                imgRes = R.drawable.ic_scene_blue_default;
                colorRes = R.color.color_scene_blue;
                break;
            default:
                imgRes = R.drawable.ic_scene_new_defaul;
                colorRes = R.color.color_scene_grey;
                break;
        }
        holder.iv.setImageResource(imgRes);
        holder.line.setBackgroundResource(colorRes);
        holder.tv.setText(scene.getScene_name());
        if (selectedId == scene.getScene_id()) {
            holder.line.setVisibility(View.VISIBLE);
        } else {
            holder.line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mScenes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_home_scene_adapter_iv)
        ImageView iv;
        @BindView(R.id.id_home_scene_adapter_tv)
        TextView tv;
        @BindView(R.id.id_home_scene_adapter_line_view)
        View line;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickHelper.performClick(v);
                    int position = getAdapterPosition();
                    selectedId = mScenes.get(position).getScene_id();
                    notifyDataSetChanged();
                    if (onItemClickListener != null) {
                        onItemClickListener.onSceneClick(position, mScenes.get(position));
                    }
                }
            });
        }
    }

    public HomeSceneItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(HomeSceneItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface HomeSceneItemClickListener {
        void onSceneClick(int position, Scene scene);
    }
}
