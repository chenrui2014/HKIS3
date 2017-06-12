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
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.model.ShelvesDetail;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/6/9.
 */

public class ShelvesMaterialDetailActivity extends AppCompatActivity implements LifecycleRegistryOwner, MyItemClickListener{

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    PtrClassicFrameLayout ptrClassicFrameLayout;
    RecyclerView mRecyclerView;
    private List<ShelvesDetail> mData = new ArrayList<ShelvesDetail>();
    private ShelvesMaterialDetailActivity.RecyclerAdapter adapter;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();

    private HKISRepository hkisRep;
    int page = 0;

    private static final int PAGE_SIZE = 5;

    private String taskNO;
    private ImageView backImg;

    private TextView allSelectTv;
    private TextView confirmTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shelves_material_detail);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) this.findViewById(R.id.recycler_view_frame);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);
        backImg = (ImageView) findViewById(R.id.img_back);

        allSelectTv = (TextView)findViewById(R.id.con_con2);
        confirmTv = (TextView)findViewById(R.id.con_con2);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ShelvesMaterialDetailActivity.this, WareHousingSummaryActivity.class);
                startActivity(intent);
                ShelvesMaterialDetailActivity.this.finish();
            }
        });
        initData();
        init();
    }

    private void initData(){
        AppModule appModule = new AppModule(getApplication());
        hkisRep = appModule.providesHKISRepository(appModule.providesHKISApi());
        SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
        String loginName =sp.getString(Constants.SP_USERNAME_KEY,"");

        Intent intent = getIntent(); //用于激活它的意图对象
        taskNO = intent.getStringExtra("taskNO");
        hkisRep.getShelvesDetail(loginName,"1",taskNO);

    }

    private void init() {
        adapter = new ShelvesMaterialDetailActivity.RecyclerAdapter(this, mData,this);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        mData.clear();
                        SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
                        String userId =sp.getString(Constants.SP_USER_ID_KEY,"");

                        LiveData<List<ShelvesDetail>> shelvesDetail = hkisRep.getShelvesDetail(userId,"1",taskNO);
                        shelvesDetail.observe(ShelvesMaterialDetailActivity.this,shelvesDetailList ->{

                            mData.addAll(shelvesDetailList);
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

                        LiveData<List<ShelvesDetail>> shelvesDetail = hkisRep.getShelvesDetail(userId,"1",taskNO);
                        shelvesDetail.observe(ShelvesMaterialDetailActivity.this,shelvesDetailList ->{

                            mData.addAll(shelvesDetailList);
                        });

                        mAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.loadMoreComplete(true);
                        page++;
                        Toast.makeText(ShelvesMaterialDetailActivity.this, "load more complete", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });

        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> selectItems = adapter.getSelectedItems();
                List<ShelvesDetail> selectShelvesDetail = new ArrayList<ShelvesDetail>();
                for(Integer i = 0; i < selectItems.size(); i++){
                    selectShelvesDetail.add(mData.get(selectItems.get(i)));
                }

                Intent intent = new Intent();
                intent.setClass(ShelvesMaterialDetailActivity.this,MaterialShelvesActiviity.class);
                intent.putExtra("selectShelvesDetail",(Serializable) selectShelvesDetail);
                startActivity(intent);
            }
        });

        allSelectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearSelectedState();
                for(int i = 0; i < mData.size(); i++){
                    adapter.switchSelectedState(i);
                }
                allSelectTv.setBackground(getDrawable(R.mipmap.card_select));
            }
        });
    }

    @Override
    public void onItemClick(View view, int postion) {

        adapter.switchSelectedState(postion);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.lifecycleRegistry;
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private SparseBooleanArray selectedItems;
        private List<ShelvesDetail> datas;
        private LayoutInflater inflater;

        private MyItemClickListener mListener;

        public RecyclerAdapter(Context context, List<ShelvesDetail> data,MyItemClickListener mListener) {
            super();
            inflater = LayoutInflater.from(context);
            datas = data;
            this.mListener = mListener;
            selectedItems = new SparseBooleanArray();
        }

        public boolean isSelected(int position) {
            return getSelectedItems().contains(position);
        }

        public void switchSelectedState(int position) {
            if (selectedItems.get(position, false)) {
                selectedItems.delete(position);
            } else {
                selectedItems.put(position, true);
            }
            notifyItemChanged(position);
        }

        public void clearSelectedState() {
            List<Integer> selection = getSelectedItems();
            selectedItems.clear();
            for (Integer i : selection) {
                notifyItemChanged(i);
            }
        }

        public int getSelectedItemCount() {
            return selectedItems.size();
        }

        public List<Integer> getSelectedItems() {
            List<Integer> items = new ArrayList<>(selectedItems.size());
            for (int i = 0; i < selectedItems.size(); ++i) {
                items.add(selectedItems.keyAt(i));
            }
            return items;
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ShelvesMaterialDetailActivity.ChildViewHolder holder = (ShelvesMaterialDetailActivity.ChildViewHolder) viewHolder;
            holder.wlhTv.setText(datas.get(position).getMaterialNum());
            holder.wlmTv.setText(datas.get(position).getMaterialDesc());
            holder.toStayTV.setText(datas.get(position).getAmount());
            holder.sysRecommendWarehouseTv.setText(datas.get(position).getSysRecommendWarehouse());
            if(isSelected(position)){
                holder.selectImg.setImageResource(R.mipmap.card_select);
            }else{
                holder.selectImg.setImageResource(R.mipmap.card_unselect);
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
            View view = inflater.inflate(R.layout.fragment_shelves_material_detail_item, null);
            return new ShelvesMaterialDetailActivity.ChildViewHolder(view,mListener);
        }

    }

    public class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView wlhTv;
        public TextView wlmTv;
        public TextView toStayTV;
        public TextView sysRecommendWarehouseTv;
        public ImageView selectImg;


        private MyItemClickListener myItemClickListener;

        public ChildViewHolder(View view,MyItemClickListener myItemClickListener) {
            super(view);
            wlhTv = (TextView) view.findViewById(R.id.tv_wlh);
            wlmTv = (TextView) view.findViewById(R.id.tv_wlm);
            toStayTV = (TextView) view.findViewById(R.id.tv_toStay);
            selectImg = (ImageView) view.findViewById(R.id.select_img);
            sysRecommendWarehouseTv = (TextView) view.findViewById(R.id.tv_suggest);
            this.myItemClickListener = myItemClickListener;
        }

        @Override
        public void onClick(View v) {

            if(myItemClickListener != null){

                myItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
