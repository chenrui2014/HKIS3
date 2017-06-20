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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.model.CheckDetail;
import com.huake.hkis.hkis.model.ShelvesDetail;
import com.huake.hkis.hkis.pullrefreshlayout.PullRefreshLayout;
import com.huake.hkis.hkis.pullrefreshlayout.PullRefreshView;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysstech on 2017/6/14.
 */

public class InventoryAPMDActivity extends AppCompatActivity  implements LifecycleRegistryOwner,InventoryAPMDAdapter.OnItemClickListener {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private static final String TAG = "InventoryAPMDActivity";
    private List<CheckDetail> checkDetailList;
    private PullRefreshLayout refreshLayout;
    private InventoryAPMDAdapter adapter;

    private HKISRepository hkisRep;
    int page = 0;

    private static final int PAGE_SIZE = 5;

    private String userId;

    private TextView selectTv;
    private TextView confirmTv;

    private ImageView backImg;

    private TextView titleTv;
    
    private String wareHouseNO;

    private String checkNO="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pd_material_detail2);
        titleTv = (TextView) findViewById(R.id.tv_title);
        selectTv = (TextView) findViewById(R.id.con_con2);
        confirmTv = (TextView) findViewById(R.id.con_con3);
        backImg = (ImageView) findViewById(R.id.img_back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InventoryAPMDActivity.this, InStoreSummaryActivity.class);
                startActivity(intent);
                InventoryAPMDActivity.this.finish();
            }
        });

        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> selectItems = adapter.getSelectedItems();
                List<CheckDetail> selectShelvesDetail = new ArrayList<CheckDetail>();
                for(Integer i = 0; i < selectItems.size(); i++){
                    selectShelvesDetail.add(checkDetailList.get(selectItems.get(i)));
                }

                Intent intent = new Intent();
                intent.setClass(InventoryAPMDActivity.this,MaterialShelvesActivity.class);
                intent.putExtra("selectShelvesDetail",(Serializable) selectShelvesDetail);
                startActivity(intent);
            }
        });

        selectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearSelectedState();
                for(int i = 0; i < checkDetailList.size(); i++){
                    adapter.switchSelectedState(i);
                    adapter.notifyDataSetChanged();
                }
                selectTv.setCompoundDrawablesRelative(getDrawable(R.mipmap.card_select),null,null,null);
            }
        });
        initData();
        // initRecyclerView();
        // initRefreshLayout();

    }
    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InventoryAPMDAdapter(this, checkDetailList);
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
                        LiveData<List<CheckDetail>> checkDetailData = hkisRep.getCheckDetail(userId,wareHouseNO,checkNO,page,PAGE_SIZE);
                        checkDetailData.observe(InventoryAPMDActivity.this, checkDetailList1 ->{
                            checkDetailList.addAll(checkDetailList1);
                            adapter.notifyItemInserted(checkDetailList.size());
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

        LiveData<List<CheckDetail>> checkDetailData = hkisRep.getCheckDetail(userId,wareHouseNO,checkNO,page,PAGE_SIZE);
        checkDetailData.observe(this,checkDetailList1 ->{
            checkDetailList = checkDetailList1;
            initRecyclerView();
            initRefreshLayout();
            titleTv.setText(getResources().getText(R.string.smd_title) + "(" + checkDetailList1.size() + ")");
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
        ImageView selectImg = (ImageView) view.findViewById(R.id.select_img);
        if(adapter.isSelected(position)){

            selectImg.setImageResource(R.mipmap.card_select);
        }else{
            selectImg.setImageResource(R.mipmap.card_unselect);
        }

        EditText realEt = (EditText) view.findViewById(R.id.tv_real);
        String real= realEt.getText().toString().trim();
        CheckDetail checkDetail = checkDetailList.get(position);
        checkDetail.setCheckAmount(real);
//        ShelvesDetail shelvesDetail = checkDetailList.get(position);
//        Intent intent = new Intent(ShelvesMDActivity.this,ShelvesMaterialDetailActivity.class);
//        intent.putExtra("taskNO", shelvesDetail.getTaskNO());
//        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
