package com.huake.hkis.hkis;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.model.Check;
import com.huake.hkis.hkis.model.CheckParam;
import com.huake.hkis.hkis.model.Task;
import com.huake.hkis.hkis.pullrefreshlayout.PullRefreshLayout;
import com.huake.hkis.hkis.pullrefreshlayout.PullRefreshView;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.utils.Constants;

import java.util.List;

/**
 * Created by ysstech on 2017/6/14.
 */

public class InventorySummaryActivity extends AppCompatActivity  implements LifecycleRegistryOwner,InventorySummaryAdapter.OnItemClickListener {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private static final String TAG = "PDSummaryActivity";
    private List<Check> checks;
    private PullRefreshLayout refreshLayout;
    private InventorySummaryAdapter adapter;

    private HKISRepository hkisRep;
    int page = 0;

    private static final int PAGE_SIZE = 5;

    private String userId;

    private ImageView backImg;

    private TextView titleTv;

    private String checkType = "1";
    private String checkNO;
    private String wareHouseNO;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pd_sum);

        backImg = (ImageView) findViewById(R.id.img_back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InventorySummaryActivity.this, MainActivity.class);
                startActivity(intent);
                InventorySummaryActivity.this.finish();
            }
        });

        titleTv = (TextView) findViewById(R.id.pd_title);
        initData();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InventorySummaryAdapter(this, checks);
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
                        CheckParam checkParam = new CheckParam();
                        checkParam.setCheckUser(userId);
                        checkParam.setCheckType(checkType);
                        checkParam.setCheckNO(checkNO);
                        checkParam.setWarehouseNum(wareHouseNO);
                        LiveData<List<Check>> checkData = hkisRep.getCheckList(checkParam,page,PAGE_SIZE);
                        checkData.observe(InventorySummaryActivity.this,mychecks ->{
                            checks.addAll(mychecks);
                            adapter.notifyItemInserted(checks.size());
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
        checkType = intent.getStringExtra("checkType");
        checkNO = intent.getStringExtra("checkNO");
        wareHouseNO = intent.getStringExtra("wareHouseNO");

        CheckParam checkParam = new CheckParam();
        checkParam.setCheckUser(userId);
        checkParam.setCheckType(checkType);
        checkParam.setCheckNO(checkNO);
        checkParam.setWarehouseNum(wareHouseNO);

        LiveData<List<Check>> checkData = hkisRep.getCheckList(checkParam,page,PAGE_SIZE);
        checkData.observe(this,mychecks ->{
            checks = mychecks;
            initRecyclerView();
            initRefreshLayout();

            titleTv.setText(getResources().getText(R.string.check_sum_tv_title) + "(" + mychecks.size() + ")");
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
        Check check = checks.get(position);
        Intent intent = new Intent(InventorySummaryActivity.this,ShelvesMDActivity.class);
        intent.putExtra("checkNO", check.getCheckNO());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
