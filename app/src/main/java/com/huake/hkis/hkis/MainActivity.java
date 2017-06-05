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

import com.huake.hkis.hkis.ui.ChangeFragment;
import com.huake.hkis.hkis.ui.ChangeFragment2;
import com.huake.hkis.hkis.ui.CheckFragment;
import com.huake.hkis.hkis.ui.DownFragment;
import com.huake.hkis.hkis.ui.UpFragment;
import com.huake.hkis.hkis.ui.UpFragment2;
import com.huake.hkis.hkis.ui.UserFragment;
import com.huake.hkis.hkis.utils.UIHelper;
import com.pda.scan.IHWScan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnScanListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;

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
//				iScan.disableSymbology(SymbologyID.SYM_ALL) ;//disable all
//				iScan.enableSymbology(SymbologyID.SYM_QR) ;
//				iScan.enableSymbology(SymbologyID.SYM_CODE128) ;
//				iScan.enableSymbology(SymbologyID.SYM_CODE39) ;
//				iScan.enableSymbology(SymbologyID.SYM_DATAMATRIX) ;
//				iScan.enableSymbology(SymbologyID.SYM_PDF417) ;
//				iScan.enableSymbology(SymbologyID.SYM_MAXICODE) ;
//				iScan.enableSymbology(SymbologyID.SYM_HANXIN) ;//chinese hanxin
//				iScan.enableSymbology(SymbologyID.SYM_ALL) ;
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
                .replace(R.id.fragment_container, MainFragment.newInstance())
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
        fragmentTags = new ArrayList<>(Arrays.asList("UpFragment2", "DownFragment", "ChangeFragment2", "CheckFragment", "UserFragment"));
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
        if (currIndex == 4) {
            UIHelper.showLogin(MainActivity.this);
        }

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
            case 0: return new UpFragment2();
            case 1: return new DownFragment();
            case 2: return new ChangeFragment2();
            case 3: return new CheckFragment();
            case 4: return new UserFragment();
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
        bindScanService();
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
}
