package com.rrioo.smartlife.ui.settings.addDevice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.base.BaseActivity;
import com.rrioo.smartlife.helper.SimpleItemDecoration;
import com.rrioo.smartlife.helper.SimpleOnItemClickListener;
import com.rrioo.smartlife.util.ClickHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/16 11:58 1.0
 * @time 2017/10/16 11:58
 * @project secQreNew3.0 com.rrioo.smartlife.ui.settings.addDevice
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/16 11:58
 */

public class AddDeviceActivity extends BaseActivity {
    @BindView(R.id.id_common_title_back)
    TextView mTitleTv;

    @BindView(R.id.id_activity_add_device_rv)
    RecyclerView mRv;

    public static void startActivity(Context from) {
        from.startActivity(new Intent(from, AddDeviceActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick(R.id.id_common_title_back)
    public void clickBack() {
        finish();
    }

    private void initView() {
        mTitleTv.setText(R.string.add_device);
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRv.addItemDecoration(new SimpleItemDecoration(0));

        final List<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.ic_montion_device_setting, R.string.motion_sensor));
        items.add(new Item(R.drawable.ic_door_device_setting, R.string.door_sensor));
        items.add(new Item(R.drawable.ic_temperature_humidity_device_setting, R.string.humiture_sensor));
        items.add(new Item(R.drawable.ic_leak_device_setting, R.string.leak_sensor));
        items.add(new Item(R.drawable.ic_gas_device_setting, R.string.gas_sensor));
        items.add(new Item(R.drawable.ic_smoke_device_setting, R.string.smoke_sensor));
        items.add(new Item(R.drawable.ic_socket_device_setting, R.string.socket));
        items.add(new Item(R.drawable.ic_rcu_device_setting, R.string.rcu));
        items.add(new Item(R.drawable.btn_add_default_setting, R.string.add_new_device));


        AddDeviceAdapter addDeviceAdapter = new AddDeviceAdapter(items, this);
        mRv.setAdapter(addDeviceAdapter);
        addDeviceAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View itemView, int position, Object data) {
                if (position != items.size() - 1) {
                    Item item = (Item) data;
                    AddDeviceGuideActivity.startActivity(AddDeviceActivity.this,
                            item.strId);
                } else {
                    AddNewDeviceActivity.startActivity(AddDeviceActivity.this);
                }
            }
        });
    }

    static class AddDeviceAdapter extends RecyclerView.Adapter<AddDeviceAdapter.ViewHolder> {

        private List<Item> mItems;
        private Context mContext;
        private SimpleOnItemClickListener onItemClickListener;

        public SimpleOnItemClickListener getOnItemClickListener() {
            return onItemClickListener;
        }

        public void setOnItemClickListener(SimpleOnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public AddDeviceAdapter(List<Item> mItems, Context mContext) {
            this.mItems = mItems;
            this.mContext = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.adapter_add_device, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Item item = mItems.get(position);
            holder.iconIv.setImageResource(item.drawableId);
            holder.typeTv.setText(item.strId);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.id_adapter_add_device_iv)
            ImageView iconIv;

            @BindView(R.id.id_adapter_add_device_tv)
            TextView typeTv;


            public ViewHolder(final View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(new ClickHelper.ClickListener() {
                    @Override
                    public void onClickView(View v) {
                        int position = getAdapterPosition();
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(AddDeviceAdapter.this,
                                    itemView, position, mItems.get(position));
                        }
                    }
                });
            }
        }
    }

    private static class Item {
        int drawableId;
        int strId;

        public Item(int drawableId, int strId) {
            this.drawableId = drawableId;
            this.strId = strId;
        }
    }

}
