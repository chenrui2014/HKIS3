package com.huake.hkis.hkis;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.model.Task;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysstech on 2017/6/9.
 */

public class WareHousingSummaryActivity extends AppCompatActivity implements LifecycleRegistryOwner,MyItemClickListener {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    PtrClassicFrameLayout ptrClassicFrameLayout;
    RecyclerView mRecyclerView;
    private List<Task> mData = new ArrayList<Task>();
    private RecyclerAdapter adapter;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();

    private HKISRepository hkisRep;
    int page = 0;

    private static final int PAGE_SIZE = 5;

    private String taskNO = "2017052511";

    private String documentsType= "入库单";

    private ImageView backImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_up_putin);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) this.findViewById(R.id.recycler_view_frame);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);
        backImg = (ImageView) findViewById(R.id.img_back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(WareHousingSummaryActivity.this, MainActivity.class);
                startActivity(intent);
                WareHousingSummaryActivity.this.finish();
            }
        });
        initData();
        init();
    }

    private void initData(){
        AppModule appModule = new AppModule(getApplication());
        hkisRep = appModule.providesHKISRepository(appModule.providesHKISApi());
        SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
        String userId =sp.getString(Constants.SP_USER_ID_KEY,"");

        Intent intent = getIntent(); //用于激活它的意图对象
        taskNO = intent.getStringExtra("taskNO");
        documentsType = intent.getStringExtra("documentsType");
        documentsType = null;

        LiveData<List<Task>> tasks = hkisRep.getTask(userId,"1",taskNO,documentsType,page,PAGE_SIZE);
        tasks.observe(this,myTasks ->{

            mData = myTasks;
            init();
        });

    }

    private void init() {
        adapter = new RecyclerAdapter(this, mData);
        adapter.setMyItemClickListener(this);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
//        ptrClassicFrameLayout.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                ptrClassicFrameLayout.autoRefresh(true);
//            }
//        }, 150);

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                       if(mData != null &&mData.size() > 0){
                           mData.clear();
                       }

                        SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
                        String userId =sp.getString(Constants.SP_USER_ID_KEY,"");
                        LiveData<List<Task>> tasks = hkisRep.getTask(userId,"1",taskNO,documentsType,page,PAGE_SIZE);
                        tasks.observe(WareHousingSummaryActivity.this,myTasks ->{

                            mData = myTasks;
                        });
                        mAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.refreshComplete();
                        ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                }, 1500);
            }
        });

        /**
         * 加载跟多功能
         */
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
                        String userId =sp.getString(Constants.SP_USER_ID_KEY,"");

                        LiveData<List<Task>> tasks = hkisRep.getTask(userId,"1",taskNO,documentsType,page,PAGE_SIZE);
                        tasks.observe(WareHousingSummaryActivity.this,myTasks ->{

                            mData.addAll(myTasks);
                        });

                        mAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.loadMoreComplete(true);
                        page++;
                        Toast.makeText(WareHousingSummaryActivity.this, "load more complete", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onItemClick(View view, int postion) {
        Task task = mData.get(postion);
        Intent intent = new Intent(WareHousingSummaryActivity.this,ShelvesMaterialDetailActivity.class);
        intent.putExtra("taskNO", task.getTaskNO());
        startActivity(intent);

    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.lifecycleRegistry;
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
        private List<Task> datas;
        private LayoutInflater inflater;

        private MyItemClickListener myItemClickListener;

        public RecyclerAdapter(Context context, List<Task> data) {
            super();
            inflater = LayoutInflater.from(context);
            datas = data;
        }

        public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
            this.myItemClickListener = myItemClickListener;
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ChildViewHolder holder = (ChildViewHolder) viewHolder;
            holder.inDateTv.setText(datas.get(position).getInStorageTime());
            holder.taskNOTV.setText(datas.get(position).getTaskNO());
            holder.wareHouseNumTV.setText(datas.get(position).getResourceStorageSpace());
            holder.taskTypeTv.setText(datas.get(position).getDocumentsType());
            holder.itemView.setTag(position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
            View view = inflater.inflate(R.layout.fragment_up_putin_item, null);
            view.setOnClickListener(this);
            return new ChildViewHolder(view,myItemClickListener);
        }

        @Override
        public void onClick(View v) {
            if (myItemClickListener != null) {
                myItemClickListener.onItemClick(v,(int)v.getTag());
            }
        }
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView inDateTv;
        public TextView taskNOTV;
        public TextView wareHouseNumTV;
        public TextView taskTypeTv;

        private MyItemClickListener mListener;


        public ChildViewHolder(View view,MyItemClickListener mListener) {
            super(view);
            inDateTv = (TextView) view.findViewById(R.id.tv_inDate);
            taskNOTV = (TextView) view.findViewById(R.id.tv_inBills);
            wareHouseNumTV = (TextView) view.findViewById(R.id.tv_wareHouse_NO);
            taskTypeTv = (TextView) view.findViewById(R.id.tv_billType);
            this.mListener = mListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
