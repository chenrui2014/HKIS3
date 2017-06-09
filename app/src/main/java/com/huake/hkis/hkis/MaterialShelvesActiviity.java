package com.huake.hkis.hkis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huake.hkis.hkis.model.MaterialShelves;
import com.huake.hkis.hkis.model.ShelvesDetail;

import java.util.ArrayList;

import in.arjsna.swipecardlib.SwipeCardView;

/**
 * Created by chen on 2017/6/9.
 */

public class MaterialShelvesActiviity extends AppCompatActivity {

    private ArrayList<MaterialShelves> listObj;
    private CardsAdapter arrayAdapter;
    private int i;

    public SwipeCardView swipeCardView;

    private Button confirmBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_store_info);

        listObj =  (ArrayList<MaterialShelves>) getIntent().getSerializableExtra("selectShelvesDetail");
        arrayAdapter = new CardsAdapter(this, listObj );
        swipeCardView = (SwipeCardView) findViewById(R.id.frame);
        swipeCardView.setAdapter(arrayAdapter);
        swipeCardView.setFlingListener(new SwipeCardView.OnCardFlingListener() {
            @Override
            public void onCardExitLeft(Object dataObject) {

            }

            @Override
            public void onCardExitRight(Object dataObject) {

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

                //add more items to adapter and call notifydatasetchanged
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }

            @Override
            public void onCardExitTop(Object dataObject) {

            }

            @Override
            public void onCardExitBottom(Object dataObject) {

            }
        });

        confirmBtn = (Button)findViewById(R.id.bt_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeCardView.throwLeft();
            }
        });

    }
}
