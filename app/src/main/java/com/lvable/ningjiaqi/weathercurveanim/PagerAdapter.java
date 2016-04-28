package com.lvable.ningjiaqi.weathercurveanim;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by ningjiaqi on 16/4/28.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    String[] locations = {"珠海","柳州","北京"};
    String[] temperature = {"27","31","16"};
    private static final int TOTAL_PAGE_COUNT = 3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        WeatherPageFragment fragment = new WeatherPageFragment();
        fragment.setLocationName(locations[position]);
        fragment.setTemperature(temperature[position]);
        return fragment;
    }

    @Override
    public int getCount() {
        return TOTAL_PAGE_COUNT;
    }

}
