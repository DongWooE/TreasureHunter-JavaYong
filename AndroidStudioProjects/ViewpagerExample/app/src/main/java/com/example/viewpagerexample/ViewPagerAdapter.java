package com.example.viewpagerexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0: return FragMonday.newInstance();
            case 1: return FragTuesday.newInstance();
            case 2: return FragWednesday.newInstance();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    //상단에 탭 레이아웃 인디케이터 쪽에 텍스트를 뛰워주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0: return "Monday";
            case 1: return "Tuesday";
            case 2: return "Wednesday";
            default: return null;
        }
    }
}
