package com.huake.hkis.hkis;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.huake.hkis.hkis.ui.ChangeFragment2;
import com.huake.hkis.hkis.ui.CheckFragment2;
import com.huake.hkis.hkis.ui.DownFragment2;
import com.huake.hkis.hkis.ui.UpFragment2;
import com.huake.hkis.hkis.ui.UserFragment2;
import com.huake.hkis.hkis.utils.UIHelper;
import com.pda.scan.DecoderConfigValues;
import com.pda.scan.IHWScan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity implements OnFragmentListener,OnScanListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;

    private HashMap<Integer,Fragment> mainFragment = new HashMap<Integer,Fragment>(5);

    private RadioGroup group;
    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;

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
                String syms = ParaSave.getSymbology(MainActivity.this) ;
                if(syms != null && syms.length() > 0){
                    String[] symArrays = syms.split(",");
                    for(int i = 0 ; i < symArrays.length; i++){
                        iScan.enableSymbology(Integer.valueOf(symArrays[i])) ;
                        Log.e("symbology", "value = " + symArrays[i]) ;
                    }
                }

                iScan.enableSymbology(DecoderConfigValues.SymbologyID.SYM_ALL) ;

//				iScan.disableSymbology(SymbologyID.SYM_ALL) ;//disable all
//				iScan.enableSymbology(DecoderConfigValues.SymbologyID.SYM_QR) ;
//				iScan.enableSymbology(SymbologyID.SYM_CODE128) ;
//				iScan.enableSymbology(SymbologyID.SYM_CODE39) ;
//				iScan.enableSymbology(SymbologyID.SYM_DATAMATRIX) ;
//				iScan.enableSymbology(SymbologyID.SYM_PDF417) ;
//				iScan.enableSymbology(SymbologyID.SYM_MAXICODE) ;
//				iScan.enableSymbology(SymbologyID.SYM_HANXIN) ;//chinese hanxin

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new UpFragment2())
                .commit();
        initData(savedInstanceState);
        initView();
        Util.initSoundPool(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }

    private void initData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("UpFragment2", "DownFragment2", "ChangeFragment2", "CheckFragment2", "UserFragment2"));
        currIndex = 0;
        if(savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }
    }

    private void hideSavedFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    private void initView() {
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_up: currIndex = 0; break;
                    case R.id.foot_bar_down: currIndex = 1; break;
                    case R.id.foot_bar_change: currIndex = 2; break;
                    case R.id.foot_bar_check: currIndex = 3; break;
                    case R.id.foot_bar_user: currIndex = 4; break;
                    default: break;
                }
                showFragment();
            }
        });
        showFragment();
    }

    private void showFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if(f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0: {
                if(mainFragment.containsKey(currIndex)){
                    return mainFragment.get(currIndex);
                }else{
                    UpFragment2 up = new UpFragment2();
                    mainFragment.put(currIndex,up);
                    return up;
                }

            }
            case 1: {
                if(mainFragment.containsKey(currIndex)){
                    return mainFragment.get(currIndex);
                }else{
                    DownFragment2 down = new DownFragment2();
                    mainFragment.put(currIndex,down);
                    return down;
                }
            }
            case 2: {
                if(mainFragment.containsKey(currIndex)){
                    return mainFragment.get(currIndex);
                }else{
                    ChangeFragment2 change = new ChangeFragment2();
                    mainFragment.put(currIndex,change);
                    return change;
                }
            }
            case 3: {
                if(mainFragment.containsKey(currIndex)){
                    return mainFragment.get(currIndex);
                }else{
                    CheckFragment2 check = new CheckFragment2();
                    mainFragment.put(currIndex,check);
                    return check;
                }
            }
            case 4: {
                if(mainFragment.containsKey(currIndex)){
                    return mainFragment.get(currIndex);
                }else{
                    UserFragment2 user = new UserFragment2();
                    mainFragment.put(currIndex,user);
                    return user;
                }
            }
            default: return null;
        }
    }

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

    @Override
    protected void onResume() {
        super.onResume();
        //bind service
//        try{
//            bindScanService();
//        }catch(Exception e){
//            Toast.makeText(getApplicationContext(),"绑定扫描服务",Toast.LENGTH_LONG);
//        }
        IntentFilter filter = new IntentFilter() ;
        filter.addAction("com.scan.RESULT") ;
        registerReceiver(resultReceiver, filter) ;
        //key receiver
        IntentFilter keyfilter = new IntentFilter() ;
        keyfilter.addAction("android.rfid.FUN_KEY") ;
        registerReceiver(keyReceiver, keyfilter) ;
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

    //display barcode
    private void displayResult(final String barcode){

        ChangeFragment2 cf2 = (ChangeFragment2) fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        cf2.setEtScan(barcode);
        Util.play(1, 0);
    }

    @Override
    public void onScanListener() {
        scan();
    }

    @Override
    public void onFragmentAction(Map<String,String> params, Class intentClass) {

        Intent intent = new Intent(MainActivity.this, intentClass);
        if(params != null){
            Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,String> entry = it.next();
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }

        startActivity(intent);
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
