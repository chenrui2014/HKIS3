package com.huake.hkis.hkis;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.flingswipe.SwipeFlingAdapterView;
import com.huake.hkis.hkis.model.MaterialShelves;
import com.huake.hkis.hkis.model.ShelvesDetail;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.pda.scan.DecoderConfigValues;
import com.pda.scan.IHWScan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/6/9.
 */

public class MaterialShelvesActivity extends AppCompatActivity implements LifecycleRegistryOwner,MDOnScanListener, SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener {
    private static final String TAG = MaterialShelvesActivity.class.getSimpleName();

    private ArrayList<MaterialShelves> listObj;
    private CardsAdapter arrayAdapter;

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private HKISRepository hkisRep;

    private SwipeFlingAdapterView swipeView;

    private Button confirmBtn;

    private MaterialShelves current;

    private TextView titleTv;
    private ImageView backImg;

    private EditText barCodeEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_store_info);
        titleTv = (TextView) findViewById(R.id.textView4);

        Util.initSoundPool(this);
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
                materialShelves.setCalculateUnit(sd.getCalculateUnit());
                materialShelves.setMaterBatch(sd.getMaterBatch());
                materialShelves.setSysRecommendWarehouse(sd.getRecommendStorageSpace());
                listObj.add(materialShelves);
            }
        }

        titleTv.setText(getResources().getText(R.string.store_info_tv_title) + "(" + listObj.size() + ")");
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
//        confirmBtn.setEnabled(true);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeView.swipeRight();
                if(current != null){
                    LiveData<Boolean> state = hkisRep.getMaterialShelves(current);
                    state.observe(MaterialShelvesActivity.this, myState ->{
                        if(myState){
                            Toast.makeText(getApplicationContext(), "上架成功",Toast.LENGTH_LONG);
                            titleTv.setText(getResources().getText(R.string.store_info_tv_title) + "(" + listObj.size() + ")");
                        }

                    });
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
    public void onLeftCardExit(Object dataObject)
    {
        if(listObj != null && listObj.size() > 0){

            listObj.remove(0);
        }
        current = (MaterialShelves)dataObject;
    }

    @Override
    public void onRightCardExit(Object dataObject) {
        if(listObj != null && listObj.size() > 0){

            listObj.remove(0);
        }
        current = (MaterialShelves)dataObject;
    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
//        confirmBtn.setEnabled(false);
    }

    @Override
    public void onScroll(float progress, float scrollXProgress) {

    }

    @Override
    public void onScanListener(View view) {
        barCodeEt = (EditText) view;
        scan();
    }
    private IHWScan iScan ;
    private boolean connFlag = false ;
    private int modeBroad = 0 ;
    //is recv
    private boolean isRecved = false ;

    //service connect
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName component) {
            Log.e(TAG, "onServiceDisconnected*****");

        }

        @Override
        public void onServiceConnected(ComponentName component, IBinder ibinder) {
            Log.e(TAG, "onServiceConnected----");
            connFlag = true ;
//			iScan = IScan.Stub.asInterface(ibinder);
            iScan = IHWScan.Stub.asInterface(ibinder) ;
            try {
                iScan.init();
                //set para
                iScan.setInputMode(modeBroad) ;
                //open barcode
                String syms = ParaSave.getSymbology(MaterialShelvesActivity.this) ;
                if(syms != null && syms.length() > 0){
                    String[] symArrays = syms.split(",");
                    for(int i = 0 ; i < symArrays.length; i++){
                        iScan.enableSymbology(Integer.valueOf(symArrays[i])) ;
                        Log.e("symbology", "value = " + symArrays[i]) ;
                    }
                }
//				iScan.disableSymbology(SymbologyID.SYM_ALL) ;//disable all
//				iScan.enableSymbology(DecoderConfigValues.SymbologyID.SYM_QR) ;
//				iScan.enableSymbology(SymbologyID.SYM_CODE128) ;
//				iScan.enableSymbology(SymbologyID.SYM_CODE39) ;
//				iScan.enableSymbology(SymbologyID.SYM_DATAMATRIX) ;
//				iScan.enableSymbology(SymbologyID.SYM_PDF417) ;
//				iScan.enableSymbology(SymbologyID.SYM_MAXICODE) ;
//				iScan.enableSymbology(SymbologyID.SYM_HANXIN) ;//chinese hanxin
                iScan.enableSymbology(DecoderConfigValues.SymbologyID.SYM_ALL) ;
                //iScan.setChar("GBK");

//				iScan.setOnResultListener(new IScanResult.Stub() {
//
//					@Override
//					public void onListener(String barcode)
//							throws RemoteException {
//						//get result, send to handler
//						Log.e(TAG, barcode) ;
//						Message msg = new Message();
//						msg.what = MSG_SERVICE_SCAN_RESULT;
//						barcodeResult = barcode;
//						mHandler.sendMessage(msg);
//					}
//
//				});
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    };
    //display barcode
    private void displayResult(final String barcode){
        if(barCodeEt != null && barCodeEt.getText().toString().isEmpty()){
            barCodeEt.setText(barcode);
        }
        Util.play(1, 0);
    }



    //receive barcode result
    private BroadcastReceiver resultReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //
            String barcode = intent.getStringExtra("barcode") ;
            if(barcode != null){
                displayResult(barcode);
            }
        }
    };

    private BroadcastReceiver keyReceiver  = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            int keyCode = intent.getIntExtra("keyCode", 0) ;
            boolean keyDown = intent.getBooleanExtra("keydown", false) ;
            //F1 F2 F3 F4 F5
            if(keyDown && (keyCode == KeyEvent.KEYCODE_F1 ||keyCode == KeyEvent.KEYCODE_F2 ||keyCode == KeyEvent.KEYCODE_F3 ||
                    keyCode == KeyEvent.KEYCODE_F4 ||keyCode == KeyEvent.KEYCODE_F5 ||keyCode == KeyEvent.KEYCODE_F6 )){
                scan() ;
            }
        }

    } ;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //bind service
    private void bindScanService(){
        final Intent intent = new Intent();
        intent.setAction("com.scan.service");//SCAN ACTION
        final Intent eintent = new Intent(createExplicitFromImplicitIntent(this,intent));
        bindService(eintent, conn, Context.BIND_AUTO_CREATE);
    }

    //start scan
    private void scan(){
        if(connFlag ){
            try {
                isRecved = true ;
                iScan.scan();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * android5.0 无法隐式申明Intent启动service的解决办法
     * @param context
     * @param implicitIntent
     * @return
     */
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //bind service
       // bindScanService();
        IntentFilter filter = new IntentFilter() ;
        filter.addAction("com.scan.RESULT") ;
        registerReceiver(resultReceiver, filter) ;
        //key receiver
        IntentFilter keyfilter = new IntentFilter() ;
        keyfilter.addAction("android.rfid.FUN_KEY") ;
        registerReceiver(keyReceiver, keyfilter) ;
    }

    @Override
    protected void onPause() {
        unregisterReceiver(keyReceiver) ;
        unregisterReceiver(resultReceiver) ;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if(connFlag){
            connFlag = false ;
            try {
                Log.e(TAG, "close----");
                iScan.close();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            unbindService(conn);
        }
        super.onPause();
    }
}
