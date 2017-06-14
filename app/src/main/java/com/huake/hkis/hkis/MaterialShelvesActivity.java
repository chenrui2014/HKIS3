package com.huake.hkis.hkis;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.flingswipe.SwipeFlingAdapterView;
import com.huake.hkis.hkis.model.MaterialShelves;
import com.huake.hkis.hkis.model.ShelvesDetail;
import com.huake.hkis.hkis.repository.HKISRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/6/9.
 */

public class MaterialShelvesActivity extends AppCompatActivity implements LifecycleRegistryOwner, SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener {

    private ArrayList<MaterialShelves> listObj;
    private CardsAdapter arrayAdapter;

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private HKISRepository hkisRep;

    private SwipeFlingAdapterView swipeView;

    private Button confirmBtn;

    private MaterialShelves current;

    private TextView titleTv;
    private ImageView backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_store_info);
        titleTv = (TextView) findViewById(R.id.textView4);
        titleTv.setText(getResources().getText(R.string.smd_title));
        backImg = (ImageView) findViewById(R.id.img_back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MaterialShelvesActivity.this, ShelvesMDActivity.class);
                startActivity(intent);
                MaterialShelvesActivity.this.finish();
            }
        });
        listObj = new ArrayList<MaterialShelves>();
        AppModule appModule = new AppModule(getApplication());
        hkisRep = appModule.providesHKISRepository(appModule.providesHKISApi());
        List<ShelvesDetail> shelvesDetailList = (ArrayList<ShelvesDetail>) getIntent().getSerializableExtra("selectShelvesDetail");
        if(shelvesDetailList != null && shelvesDetailList.size() > 0){
            for(ShelvesDetail sd:shelvesDetailList){
                MaterialShelves materialShelves = new MaterialShelves();
                materialShelves.setMaterialNO(sd.getMaterialNO());
                materialShelves.setMaterialDesc(sd.getMaterialDesc());
                materialShelves.setAmount(sd.getAmount());
                materialShelves.setCalculateUnit("");
                materialShelves.setSysRecommendWarehouse(sd.getRecommendStorageSpace());
                listObj.add(materialShelves);
            }
        }
        if(listObj != null && listObj.size() > 0){
            current = listObj.get(0);
        }
        arrayAdapter = new CardsAdapter(this, listObj );
        swipeView = (SwipeFlingAdapterView) findViewById(R.id.frame);
        if (swipeView != null) {
            swipeView.setIsNeedSwipe(true);
            swipeView.setFlingListener(this);
            swipeView.setOnItemClickListener(this);

            swipeView.setAdapter(arrayAdapter);
        }
        confirmBtn = (Button)findViewById(R.id.bt_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(current != null){
                    LiveData<Boolean> state = hkisRep.getMaterialShelves(current);
                    state.observe(MaterialShelvesActivity.this, myState ->{
                        Toast.makeText(getApplicationContext(), "上架成功",Toast.LENGTH_LONG);
                    });
                    swipeView.swipeRight();
                }
            }
        });

    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {

    }

    @Override
    public void removeFirstObjectInAdapter() {

        arrayAdapter.remove(0);
        arrayAdapter.notifyDataSetChanged();
        current = arrayAdapter.getItem(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {
        current = (MaterialShelves)dataObject;
    }

    @Override
    public void onRightCardExit(Object dataObject) {
        current = (MaterialShelves)dataObject;
    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {


    }

    @Override
    public void onScroll(float progress, float scrollXProgress) {

    }
}
