package com.rrioo.smartlife.ui.main.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.R;
import com.rrioo.smartlife.SecApp;
import com.rrioo.smartlife.base.BaseFragment;
import com.rrioo.smartlife.base.PermissionResult;
import com.rrioo.smartlife.data.ApiCache;
import com.rrioo.smartlife.helper.SimpleItemDecoration;
import com.rrioo.smartlife.helper.SimpleOnItemClickListener;
import com.rrioo.smartlife.qclCopy.BlurBehind;
import com.rrioo.smartlife.qclCopy.OnBlurCompleteListener;
import com.rrioo.smartlife.ui.main.activity.RoomActivity;
import com.rrioo.smartlife.ui.main.adapter.home.HomeDeviceAdapter;
import com.rrioo.smartlife.ui.main.adapter.home.HomeRoomAdapter;
import com.rrioo.smartlife.ui.main.adapter.home.HomeSceneAdapter;
import com.rrioo.smartlife.ui.main.adapter.home.HomeSecModeAdapter;
import com.rrioo.smartlife.ui.main.adapter.home.SimpleItemTouchListener;
import com.rrioo.smartlife.util.CheckUtil;
import com.rrioo.smartlife.util.DeviceHelper;
import com.rrioo.smartlife.util.NullUtil;
import com.rrioo.smartlife.util.SpUtil;
import com.rrioo.smartlife.util.SystemUtil;
import com.rrioo.smartlife.widget.CustomToast;
import com.rrioo.smartlife.zxing.activity.CaptureActivity;
import com.sen5.lib.api.Api;
import com.sen5.lib.api.event.device.EventDevice;
import com.sen5.lib.api.event.device.EventDeviceList;
import com.sen5.lib.api.event.mode.EventMode;
import com.sen5.lib.api.event.mode.EventModeApply;
import com.sen5.lib.api.event.mode.EventModeList;
import com.sen5.lib.api.event.room.EventRoomList;
import com.sen5.lib.api.event.scene.EventSceneList;
import com.sen5.lib.connection.P2pParameter;
import com.sen5.lib.entity.constant.Constant;
import com.sen5.lib.entity.device.Device;
import com.sen5.lib.entity.device.DeviceStatus;
import com.sen5.lib.entity.mode.SecurityMode;
import com.sen5.lib.entity.room.Room;
import com.sen5.lib.entity.scene.Scene;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/28 17:48 1.0
 * @time 2017/9/28 17:48
 * @project secQreNew3.0 com.rrioo.smartlife.fragment
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/28 17:48
 */
public class HomeFragment extends BaseFragment implements HomeDeviceAdapter.OnDeviceItemClickListener, HomeSecModeAdapter.OnSecModeItemClickListener, HomeSceneAdapter.HomeSceneItemClickListener {
    public static final int REQUEST_CODE = 100;

    private static final int VIEW_TYPE_INIT = 0;
    private static final int VIEW_TYPE_EMPTY = 1;
    private static final int VIEW_TYPE_CONTENT = 2;


    @BindView(R.id.fragment_home_empty)
    View mEmptyView;

    @BindView(R.id.fragment_home_content)
    View mContentView;

    @BindView(R.id.fragment_home_init)
    View mInitView;

    @BindView(R.id.main_mode_rv)
    RecyclerView mModeRv;

    @BindView(R.id.main_scene_rv)
    RecyclerView mSceneRv;

    @BindView(R.id.main_room_rv)
    RecyclerView mRoomRv;

    @BindView(R.id.main_device_rv)
    RecyclerView mDeviceRv;

    @BindView(R.id.main_device_edit_done_tv)
    TextView mDeviceEditTv;

    @BindView(R.id.id_fragment_home_create_scene_tv)
    TextView mCreateSceneTv;

    private int mViewType;
    private List<Device> mDevices;
    private List<SecurityMode> mSecModes;
    private List<Scene> mScenes;
    private List<Room> mRooms;
    private int mCurSecMode;
    private boolean mIsDeviceEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, root);
        initViewType();
        initView();
        EventBus.getDefault().register(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventDevice eventDevice) {
        if (eventDevice instanceof EventDeviceList) {
            mDevices = ApiCache.get().getSmartDevices();
            initViewType();
            if (mDevices != null) {
                final HomeDeviceAdapter adapter = new HomeDeviceAdapter(mDevices, getContext());
                mDeviceRv.setAdapter(adapter);
                adapter.setOnItemClickListener(this);
                adapter.setOnDeleteListener(new HomeDeviceAdapter.OnDeleteListener() {
                    @Override
                    public void onDeviceItemDelete(int position, Device device) {
                        RecyclerView.ViewHolder viewHolder = mDeviceRv.findViewHolderForAdapterPosition(position);
                        clearViewHolderAniamtion(mDeviceRv);
                        adapter.getDevices().remove(position);
                        adapter.notifyDataSetChanged();
                        Api.get().getSender().deleteDevice(device.getDev_id());
                    }
                });
            }
        } else {
            if (mDeviceRv.getAdapter() != null)
                mDeviceRv.getAdapter().notifyDataSetChanged();
        }

        MyLog.object(eventDevice);
    }

    private void clearViewHolderAniamtion(RecyclerView recyclerView) {
        if (recyclerView == null) return;
        if (recyclerView.getAdapter() == null) return;
        int c = recyclerView.getAdapter().getItemCount();
        for (int i = 0; i < c; i++) {
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if (holder != null) {
                if (holder.itemView != null) {
                    holder.itemView.clearAnimation();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventMode event) {
        if (event instanceof EventModeList) {
            EventModeList eventModeList = (EventModeList) event;
            mSecModes = ApiCache.get().getSecModes();
            mCurSecMode = eventModeList.getCur_sec_mode();
            if (mSecModes != null) {
                HomeSecModeAdapter adapter = new HomeSecModeAdapter(getContext(), mSecModes, mCurSecMode);
                mModeRv.setAdapter(adapter);
                adapter.setOnItemClickListener(this);
            }
        }

        if (event instanceof EventModeApply) {
            EventModeApply apply = (EventModeApply) event;
            mCurSecMode = apply.getCur_sec_mode();
            RecyclerView.Adapter adapter = mModeRv.getAdapter();
            if (adapter != null && adapter instanceof HomeSecModeAdapter) {
                HomeSecModeAdapter hsmAdapter = (HomeSecModeAdapter) adapter;
                hsmAdapter.setCurrentMode(mCurSecMode);
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventSceneList eventSceneList) {
        mScenes = ApiCache.get().getScenes();
        if (!NullUtil.isEmpty(mScenes)) {
            int lastSceneId = SpUtil.getDefault().getSelectedScene();
            MyLog.i("last scene " + lastSceneId);
            mSceneRv.setVisibility(View.VISIBLE);
            HomeSceneAdapter homeSceneAdapter = new HomeSceneAdapter(getContext(), mScenes, lastSceneId);
            mSceneRv.setAdapter(homeSceneAdapter);
            homeSceneAdapter.setOnItemClickListener(this);
            mCreateSceneTv.setVisibility(View.GONE);
        } else {
            mCreateSceneTv.setVisibility(View.VISIBLE);
            mSceneRv.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventRoomList eventRoomList) {
        mRooms = ApiCache.get().getRooms();
        if (!NullUtil.isEmpty(mRooms)) {
            HomeRoomAdapter adapter = new HomeRoomAdapter(getContext(), mRooms);
            mRoomRv.setAdapter(adapter);
            adapter.setOnItemClickListener(new SimpleOnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView.Adapter adapter, final View itemView, final int position, Object data) {
                    final Room room = (Room) data;
                    BlurBehind.getInstance()
                            .execute(getActivity(), new OnBlurCompleteListener() {
                                @Override
                                public void onBlurComplete() {
                                    Point center = getViewCenterPos(itemView);
                                    RoomActivity.startActivity(getActivity(), room.getRoom_id(), center);
                                    getActivity().overridePendingTransition(0, 0);
                                }

                                private Point getViewCenterPos(View view) {
                                    int location[] = new int[2];
                                    view.getLocationInWindow(location);
                                    return new Point(location[0] + view.getWidth() / 2,
                                            location[1] + view.getHeight() / 2);
                                }
                            });
                }
            });
        }
    }


    @OnClick((R.id.id_fragment_home_pair_btn))
    public void clickPairHome() {
        requestPermission(Manifest.permission.CAMERA, 10, new PermissionResult() {
            @Override
            public void onPermissionGranted(String permisssion, int requestCode) {
                CaptureActivity.startActivity(getActivity(), REQUEST_CODE);
            }

            @Override
            public void onPermissionDenied(String permission, int requestCode) {
                CustomToast.makeText(getContext(), R.string.camera_permission_denied)
                        .show();
            }
        });

    }

    @OnClick(R.id.id_fragment_home_empty_btn)
    public void clickAddDevice() {

    }

    @OnClick(R.id.main_device_edit_done_tv)
    public void clickDeviceEdit() {
        showEditMode(false);
    }


    private void initView() {
        initDeviceRecycleView();
        mDeviceRv.setNestedScrollingEnabled(false);

        mModeRv.setLayoutManager(new GridLayoutManager(getContext(), 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mModeRv.addItemDecoration(new SimpleItemDecoration(getResources().getDimensionPixelSize(R.dimen.dp10)));
        mModeRv.setNestedScrollingEnabled(false);

        mSceneRv.setLayoutManager(new GridLayoutManager(getContext(), 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mSceneRv.addItemDecoration(new SimpleItemDecoration(getResources().getDimensionPixelSize(R.dimen.dp10),
                getResources().getDimensionPixelSize(R.dimen.dp5)));
        mSceneRv.setNestedScrollingEnabled(false);

        mRoomRv.setLayoutManager(new GridLayoutManager(getContext(), 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRoomRv.addItemDecoration(new SimpleItemDecoration(getResources().getDimensionPixelSize(R.dimen.dp5)));
        mRoomRv.setNestedScrollingEnabled(false);
    }

    private void initDeviceRecycleView() {
        mDeviceRv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mDeviceRv.addItemDecoration(new SimpleItemDecoration(getResources().getDimensionPixelSize(R.dimen.dp3)));
        final ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
//                return super.isLongPressDragEnabled();
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.LEFT
                        | ItemTouchHelper.RIGHT
                        | ItemTouchHelper.UP
                        | ItemTouchHelper.DOWN, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter != null && adapter instanceof HomeDeviceAdapter) {
                    int fromPosition = viewHolder.getAdapterPosition();
                    int toPosition = target.getAdapterPosition();
                    HomeDeviceAdapter hdAdapter = (HomeDeviceAdapter) adapter;
                    if (fromPosition < toPosition) {
                        for (int i = fromPosition; i < toPosition; i++) {
                            Collections.swap(hdAdapter.getDevices(), i, i + 1);
                        }
                    } else {
                        for (int i = fromPosition; i > toPosition; i--) {
                            Collections.swap(hdAdapter.getDevices(), i, i - 1);
                        }
                    }
                    adapter.notifyItemMoved(fromPosition, toPosition);
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    SystemUtil.vibrate();
                }
            }

        });
        mDeviceRv.addOnItemTouchListener(new SimpleItemTouchListener(mDeviceRv) {
            @Override
            public void onItemLongClick(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                if (!mIsDeviceEdit) {
                    showEditMode(true);
                } else {
                    helper.startDrag(viewHolder);
                }

            }
        });
        helper.attachToRecyclerView(mDeviceRv);
    }

    private void showEditMode(boolean show) {
        RecyclerView.Adapter adapter = mDeviceRv.getAdapter();
        if (adapter == null) return;
        if (!(adapter instanceof HomeDeviceAdapter)) return;

        HomeDeviceAdapter hdAdapter = (HomeDeviceAdapter) adapter;
        if (show) {
            mDeviceEditTv.setVisibility(View.VISIBLE);
        } else {
            mDeviceEditTv.setVisibility(View.GONE);
        }
        if (!show) {
            clearViewHolderAniamtion(mDeviceRv);
        }
        hdAdapter.setOnEditMode(show);
        this.mIsDeviceEdit = show;
    }

    private void initViewType() {
        if (NullUtil.isEmpty(SecApp.get().getBoxId())) {
            mViewType = VIEW_TYPE_INIT;
        } else if (NullUtil.isEmpty(mDevices)) {
            mViewType = VIEW_TYPE_EMPTY;
        } else {
            mViewType = VIEW_TYPE_CONTENT;
        }
        showViewType();
    }

    private void showViewType() {
        if (mViewType == VIEW_TYPE_INIT) {
            mInitView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            mContentView.setVisibility(View.GONE);
        } else if (mViewType == VIEW_TYPE_EMPTY) {
            mInitView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
            mContentView.setVisibility(View.GONE);
        } else if (mViewType == VIEW_TYPE_CONTENT) {
            mInitView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                if (!NullUtil.isEmpty(result)) {
                    if (CheckUtil.isBoxIdLegal(result)) {
                        SpUtil.getDefault().saveBoxId(result);
                        SecApp.get().setBoxId(result);
                        MyLog.i(result);
                        Api.get().getSender().startConncet(new P2pParameter(result));
                    }
                }
            }
        }
    }

    @Override
    public void onDeviceItemClick(int position, Device device) {
        if (DeviceHelper.isActionDevice(device.getDev_type())
                && !device.getDev_type().equals(Constant.ZLL_ACTION_THERMOSTAT)) {
            List<DeviceStatus> statusList = device.getStatus();
            if (NullUtil.isEmpty(statusList)) return;
            DeviceStatus onOffStatus = findOnOffStatus(statusList);
            if (onOffStatus == null) {
                Api.get().getSender().controlDevice(device.getDev_id(),
                        Constant.STATUS_ID_ON_OFF, new byte[]{Constant.ACTION_ON});
            } else {
                int curStatus = onOffStatus.getParams()[0];
                if (curStatus == Constant.ACTION_ON)
                    curStatus = Constant.ACTION_OFF;
                else if (curStatus == Constant.ACTION_OFF)
                    curStatus = Constant.ACTION_ON;

                Api.get().getSender().controlDevice(device.getDev_id(),
                        Constant.STATUS_ID_ON_OFF, new byte[]{(byte) curStatus});
            }
        }
    }

    @Override
    public void onSecModeItemClick(int position, SecurityMode mode) {
        Api.get().getSender().applySecurityMode(mode.getSec_mode());
        mCurSecMode = mode.getSec_mode();
    }

    private DeviceStatus findOnOffStatus(List<DeviceStatus> statusList) {
        for (DeviceStatus status : statusList) {
            if (status.getId() == Constant.STATUS_ID_ON_OFF)
                return status;
        }
        return null;
    }


    @Override
    public void onSceneClick(int position, Scene scene) {
        Api.get().getSender().applyScene(scene.getScene_id());
        SpUtil.getDefault().saveSelectedScene(scene.getScene_id());
    }

    @Override
    public boolean onBackPressed() {
        if (mIsDeviceEdit){
            showEditMode(false);
            return true;
        }
        return super.onBackPressed();
    }
}
