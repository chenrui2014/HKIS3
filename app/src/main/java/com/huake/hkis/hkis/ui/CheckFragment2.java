package com.huake.hkis.hkis.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.huake.hkis.hkis.OnFragmentListener;
import com.huake.hkis.hkis.OnScanListener;
import com.huake.hkis.hkis.R;
import com.huake.hkis.hkis.utils.ViewFindUtils;

import java.util.ArrayList;

/**
 * Created by ysstech on 2017/6/8.
 */

public class CheckFragment2 extends Fragment {
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();
    private SegmentTabLayout tabLayout_2;
    private String[] mTitles_2 = {"明盘", "暗盘", "盲扫"};
    private FragmentManager fm;

    //private mTabLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getActivity().getSupportFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_check, container, false);

        if(fm == null){
            fm = getActivity().getSupportFragmentManager();
        }
        for (int i = 0;i < mTitles_2.length;i++) {
            mFragments2.add(SimpleCardFragment.getInstance(i));
        }
        tabLayout_2 = (SegmentTabLayout)fv.findViewById(R.id.tl_2);
        tabLayout_2.setTabData(mTitles_2);

        final ViewPager vp_3 = (ViewPager)fv.findViewById(R.id.vp_2);
        vp_3.setAdapter(new MyPagerAdapter(fm));
        tabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                vp_3.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vp_3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_3.setCurrentItem(0);
        return fv;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments2.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles_2[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments2.get(position);
        }
    }
}
