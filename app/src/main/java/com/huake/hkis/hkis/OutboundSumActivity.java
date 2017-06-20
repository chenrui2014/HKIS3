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
import com.huake.hkis.hkis.model.Task;
import com.huake.hkis.hkis.pullrefreshlayout.PullRefreshLayout;
import com.huake.hkis.hkis.pullrefreshlayout.PullRefreshView;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.utils.Constants;

import java.util.List;

/**
 * Created by ysstech on 2017/6/13.
 */

public class OutboundSumActivity extends AppCompatActivity  implements LifecycleRegistryOwner,OutBoundAdapter.OnItemClickListener {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private static final String TAG = "OutboundSumActivity";
    private List<Task> tasks;
    private PullRefreshLayout refreshLayout;
    private OutBoundAdapter adapter;

    private HKISRepository hkisRep;
    int page = 0;

    private static final int PAGE_SIZE = 5;

    private String taskNO = "2017052511";

    private String documentsType= "出库单";

    private String userId;
    private TextView titleTv;
    private ImageView backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_down_outbound);
        titleTv = (TextView) findViewById(R.id.tv_title);
        backImg = (ImageView) findViewById(R.id.img_back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(OutboundSumActivity.this, MainActivity.class);
                startActivity(intent);
                OutboundSumActivity.this.finish();
            }
        });
        initData();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OutBoundAdapter(this, tasks);
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
                        LiveData<List<Task>> taskData = hkisRep.getTask(userId,"2",taskNO,documentsType,page,PAGE_SIZE);
                        taskData.observe(OutboundSumActivity.this,myTasks ->{
                            tasks.addAll(myTasks);
                            adapter.notifyItemInserted(tasks.size());
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
        taskNO = intent.getStringExtra("taskNO");
        documentsType = intent.getStringExtra("documentsType");

        LiveData<List<Task>> taskData = hkisRep.getTask(userId,"2",taskNO,documentsType,page,PAGE_SIZE);
        taskData.observe(this,myTasks ->{
            tasks = myTasks;
            initRecyclerView();
            initRefreshLayout();
            titleTv.setText(getResources().getText(R.string.down_sum_tv_title) + "(" + myTasks.size() + ")");
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
        Task task = tasks.get(position);
        Intent intent = new Intent(OutboundSumActivity.this,SoldoutMDActivity.class);
        intent.putExtra("taskNO", task.getTaskNO());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
