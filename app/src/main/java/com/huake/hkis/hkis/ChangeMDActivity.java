package com.huake.hkis.hkis;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.model.MaterialDetails;
import com.huake.hkis.hkis.model.ShelvesDetail;
import com.huake.hkis.hkis.pullrefreshlayout.PullRefreshLayout;
import com.huake.hkis.hkis.pullrefreshlayout.PullRefreshView;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.utils.Constants;
import com.huake.hkis.hkis.utils.DisplayUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysstech on 2017/6/14.
 */

public class ChangeMDActivity extends AppCompatActivity  implements LifecycleRegistryOwner,ChangeMDAdapter.OnItemClickListener {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private static final String TAG = "ShelvesMDActivity";
    private List<MaterialDetails> materialDetailsList;
    private PullRefreshLayout refreshLayout;
    private ChangeMDAdapter adapter;

    private HKISRepository hkisRep;
    int page = 0;

    private static final int PAGE_SIZE = 5;

    private String wareHouseNO = "T01-001-01-01-01";


    private String userId;

    private TextView selectTv;
    private TextView confirmTv;

    private ImageView backImg;

    private PopupWindow mPopupWindow;

    private EditText targetEt;

    private Button changeBtn;
    private Button cancelBtn;

    private TextView titleTv;
    private TextView wareHouseTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_change_detail);
        View popupView = getLayoutInflater().inflate(R.layout.change_pop_window, null);
        titleTv = (TextView) findViewById(R.id.tv_title);
        wareHouseTv = (TextView) findViewById(R.id.tv_sourceWh);
        backImg = (ImageView) findViewById(R.id.img_back);
        targetEt = (EditText) popupView.findViewById(R.id.et_pos);
        changeBtn = (Button) popupView.findViewById(R.id.tb_confirm);
        cancelBtn = (Button) popupView.findViewById(R.id.bt_cancel);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        mPopupWindow.setOutsideTouchable(true);
        //设置可以获取焦点，否则弹出菜单中的EditText是无法获取输入的
        mPopupWindow.setFocusable(true);
        //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //防止虚拟软键盘被弹出菜单遮住
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        selectTv = (TextView) findViewById(R.id.con_con2);
        confirmTv = (TextView) findViewById(R.id.con_con3);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChangeMDActivity.this, MainActivity.class);
                startActivity(intent);
                ChangeMDActivity.this.finish();
            }
        });

        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics  metric = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metric);
                int xOffset = DisplayUtil.px2dip(getApplication(),metric.widthPixels-mPopupWindow.getWidth());
                int yOffset = DisplayUtil.px2dip(getApplication(),metric.heightPixels-mPopupWindow.getHeight());
                mPopupWindow.showAsDropDown(v,xOffset,yOffset);
            }
        });

        selectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearSelectedState();
                for(int i = 0; i < materialDetailsList.size(); i++){
                    adapter.switchSelectedState(i);
                }
                selectTv.setCompoundDrawablesRelative(getDrawable(R.mipmap.card_select),null,null,null);
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target = targetEt.getText().toString().trim();
                LiveData<Boolean> stateData = hkisRep.updataMdetailed(userId,wareHouseNO,target);
                stateData.observe(ChangeMDActivity.this, state ->{

                    if(state){
                        Toast.makeText(getApplicationContext(),"物料移仓成功！",Toast.LENGTH_LONG).show();
                    }
                });
                mPopupWindow.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        initData();
        // initRecyclerView();
        // initRefreshLayout();

    }
    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChangeMDAdapter(this, materialDetailsList);
        adapter.setOnItemClickLitener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void initRefreshLayout() {
        refreshLayout = (PullRefreshLayout) findViewById(R.id.refreshLayout);
//        refreshLayout.setPullTwinkEnable(false);
        refreshLayout.setAdjustTwinkValue(40);// 值越大回弹效果越明显
//        refreshLayout.setLoadMoreEnable(false);
//        refreshLayout.setRefreshEnable(false);
//        refreshLayout.setAutoLoadingEnable(true);
//        refreshLayout.setDuringAdjustValue(10f);// 动画执行时间调节，越大动画执行越慢
        // 刷新或加载完成后回复动画执行时间，为-1时，根据setDuringAdjustValue（）方法实现
//        refreshLayout.setRefreshBackTime(300);
//        refreshLayout.setPullViewHeight(400);// 设置头部和底部的高度
//        refreshLayout.setDragDampingRatio(0.6f);// 阻尼系数
//        refreshLayout.setPullFlowHeight(400);// 拖拽最大范围，为-1时拖拽范围不受限制
//        refreshLayout.setRefreshEnable(false);
        refreshLayout.setHeaderView(new PullRefreshView(getBaseContext()) {
            TextView tv;

            @Override
            protected void initView() {
                tv = (TextView) findViewById(R.id.title);
            }

            @Override
            protected int contentView() {
                return R.layout.refresh_view;
            }

            @Override
            public void onPullChange(float percent) {
                Log.e(TAG, "onPullChange: refresh " + percent);
            }

            @Override
            public void onPullReset() {
                tv.setText("下拉");
            }

            @Override
            public void onPullHoldTrigger() {
                tv.setText("释放刷新");
            }

            @Override
            public void onPullHoldUnTrigger() {
                tv.setText("下拉");
            }

            @Override
            public void onPullHolding() {
                tv.setText("正在刷新");
            }

            @Override
            public void onPullFinish() {
                tv.setText("刷新完成");
            }
        });

        refreshLayout.setFooterView(new PullRefreshView(getBaseContext()) {
            TextView tv;

            @Override
            protected void initView() {
                tv = (TextView) findViewById(R.id.title);
            }

            @Override
            protected int contentView() {
                return R.layout.load_more_view;
            }

            @Override
            public void onPullChange(float percent) {
                Log.e(TAG, "onPullChange: refresh " + percent);
            }

            @Override
            public void onPullReset() {
                tv.setText("上拉");
            }

            @Override
            public void onPullHoldTrigger() {
                tv.setText("释放加载");
            }

            @Override
            public void onPullHoldUnTrigger() {
                tv.setText("上拉");
            }

            @Override
            public void onPullHolding() {
                tv.setText("正在加载");
            }

            @Override
            public void onPullFinish() {
                tv.setText("加载完成");
            }
        });

        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "refreshLayout onRefresh: ");
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoading() {
                Log.e(TAG, "refreshLayout onLoading: ");

                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.loadMoreComplete();
                        LiveData<List<MaterialDetails>> shelvesDetailData = hkisRep.getMaterialDetails(userId,wareHouseNO,page,PAGE_SIZE);
                        shelvesDetailData.observe(ChangeMDActivity.this, materialDetailsList1 ->{
                            materialDetailsList.addAll(materialDetailsList1);
                            adapter.notifyItemInserted(materialDetailsList.size());
                        });
                    }
                }, 1000);
            }
        });
    }

    protected void initData() {
        AppModule appModule = new AppModule(getApplication());
        hkisRep = appModule.providesHKISRepository(appModule.providesHKISApi());
        SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
        userId =sp.getString(Constants.SP_USER_ID_KEY,"");

        Intent intent = getIntent(); //用于激活它的意图对象
        wareHouseNO = intent.getStringExtra("wareHouseNO");
        wareHouseTv.setText(wareHouseNO);

        LiveData<List<MaterialDetails>> materialDetailsData = hkisRep.getMaterialDetails(userId,wareHouseNO,page,PAGE_SIZE);
        materialDetailsData.observe(ChangeMDActivity.this,materialDetails ->{
            materialDetailsList = materialDetails;
            initRecyclerView();
            initRefreshLayout();
            if(materialDetails != null && materialDetails.size() > 0) {
                titleTv.setText(titleTv.getText().toString().trim() + "(" + materialDetails.size() + ")");
            }
            refreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.autoRefresh();
                }
            }, 150);
        });
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void onItemClick(View view, int position) {
        adapter.switchSelectedState(position);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
