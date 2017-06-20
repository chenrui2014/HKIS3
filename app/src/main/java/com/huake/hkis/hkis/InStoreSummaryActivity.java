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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.model.Task;
import com.huake.hkis.hkis.pullrefreshlayout.PullRefreshLayout;
import com.huake.hkis.hkis.pullrefreshlayout.PullRefreshView;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chen on 2017/6/14.
 */

public class InStoreSummaryActivity extends AppCompatActivity  implements LifecycleRegistryOwner,SimpleAdapter.OnItemClickListener {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private static final String TAG = "InStoreSummaryActivity";
    private List<Task> tasks;
    private PullRefreshLayout refreshLayout;
    private SimpleAdapter adapter;

    private PopupWindow docTypePopupWindow,whPopupWindow,docNOPopupWindow,inTimePopupWindow;

    private TextView docTypeTV,whNOTv,docNOTv,inTimeTv;

    private PickerView whNOPv,docNOPv,yearPv,monthPv,dayPv;

    private HKISRepository hkisRep;
    int page = 0;

    private static final int PAGE_SIZE = 5;

    private String taskNO = "2017052511";

    private String documentsType= "入库单";

    private String userId;

    private ImageView backImg;

    private TextView titleTv;

    private String inDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_up_putin);

        docTypeTV = (TextView) findViewById(R.id.tv_con_doc);
        whNOTv = (TextView) findViewById(R.id.tv_wh_NO);
        docNOTv = (TextView) findViewById(R.id.tv_bill_NO);
        inTimeTv = (TextView) findViewById(R.id.tv_inDate);

        View popView1 = getLayoutInflater().inflate(R.layout.condition_doctype_pop_window,null);
        docTypePopupWindow = new PopupWindow(popView1, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        View popView2 = getLayoutInflater().inflate(R.layout.condition_whno_pop_window,null);
        whPopupWindow = new PopupWindow(popView2, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        View popView3 = getLayoutInflater().inflate(R.layout.condition_docno_pop_window,null);
        docNOPopupWindow = new PopupWindow(popView3, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        View popView4 = getLayoutInflater().inflate(R.layout.condition_intime_pop_window,null);
        inTimePopupWindow = new PopupWindow(popView4, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        initPopWindow(docTypePopupWindow);
        initPopWindow(whPopupWindow);
        initPopWindow(docNOPopupWindow);
        initPopWindow(inTimePopupWindow);

        docTypeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docTypePopupWindow.showAsDropDown(v);
            }
        });
        docNOTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docNOPopupWindow.showAsDropDown(v);
            }
        });
        whNOTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whPopupWindow.showAsDropDown(v);
            }
        });
        inTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inTimePopupWindow.showAsDropDown(v);
            }
        });

        whNOPv = (PickerView) popView2.findViewById(R.id.whNOList) ;
        docNOPv  = (PickerView) popView2.findViewById(R.id.docNOList) ;
        yearPv  = (PickerView) popView2.findViewById(R.id.pv_year) ;
        monthPv  = (PickerView) popView2.findViewById(R.id.pv_month) ;
        dayPv  = (PickerView) popView2.findViewById(R.id.pv_day) ;

        List<String> yearList = new ArrayList<String>();

        yearList.add("2003");
        yearList.add("2004");
        yearList.add("2005");
        yearList.add("2006");
        yearList.add("2007");
        yearList.add("2008");
        yearList.add("2009");
        yearList.add("2010");
        yearList.add("2011");
        yearList.add("2012");
        yearList.add("2013");
        yearList.add("2014");
        yearList.add("2015");
        yearList.add("2016");
        yearList.add("2017");

        List<String> monthList = new ArrayList<String>();
        monthList.add("1");
        monthList.add("2");
        monthList.add("3");
        monthList.add("4");
        monthList.add("5");
        monthList.add("6");
        monthList.add("7");
        monthList.add("8");
        monthList.add("9");
        monthList.add("10");
        monthList.add("11");
        monthList.add("12");

        List<String> dayList = new ArrayList<String>();

        dayList.add("1");
        dayList.add("2");
        dayList.add("3");
        dayList.add("4");
        dayList.add("5");
        dayList.add("6");
        dayList.add("7");
        dayList.add("8");
        dayList.add("9");
        dayList.add("10");
        dayList.add("11");
        dayList.add("12");
        dayList.add("13");
        dayList.add("14");
        dayList.add("15");
        dayList.add("16");
        dayList.add("17");
        dayList.add("18");
        dayList.add("19");
        dayList.add("20");
        dayList.add("21");
        dayList.add("22");
        dayList.add("23");
        dayList.add("24");
        dayList.add("25");
        dayList.add("26");
        dayList.add("27");
        dayList.add("28");
        dayList.add("29");
        dayList.add("30");
        dayList.add("31");
        yearPv.setData(yearList);
        monthPv.setData(monthList);
        dayPv.setData(dayList);

        Calendar now = Calendar.getInstance();
        yearPv.setSelected(now.get(Calendar.YEAR));
       // monthPv.setSelected();

        yearPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {

            }
        });

        yearPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {

            }
        });

        yearPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {

            }
        });

        backImg = (ImageView) findViewById(R.id.img_back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InStoreSummaryActivity.this, MainActivity.class);
                startActivity(intent);
                InStoreSummaryActivity.this.finish();
            }
        });

        titleTv = (TextView) findViewById(R.id.tv_title);
        initData();
       // initRecyclerView();
       // initRefreshLayout();

    }

    private void initPopWindow(PopupWindow mPopupWindow){
        mPopupWindow.setOutsideTouchable(true);
        //设置可以获取焦点，否则弹出菜单中的EditText是无法获取输入的
        mPopupWindow.setFocusable(true);
        //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //防止虚拟软键盘被弹出菜单遮住
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SimpleAdapter(this, tasks);
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
                        LiveData<List<Task>> taskData = hkisRep.getTask(userId,"1",taskNO,documentsType,null,null,page,PAGE_SIZE);
                        taskData.observe(InStoreSummaryActivity.this,myTasks ->{
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

        LiveData<List<Task>> taskData = hkisRep.getTask(userId,"1",taskNO,documentsType,null,null,page,PAGE_SIZE);
        taskData.observe(this,myTasks ->{
            tasks = myTasks;
            initRecyclerView();
            initRefreshLayout();

            titleTv.setText(getResources().getText(R.string.sum_tv_title) + "(" + myTasks.size() + ")");
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
        Intent intent = new Intent(InStoreSummaryActivity.this,ShelvesMDActivity.class);
        intent.putExtra("taskNO", task.getTaskNO());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
