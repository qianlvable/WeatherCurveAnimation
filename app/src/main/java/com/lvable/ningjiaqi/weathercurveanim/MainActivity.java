package com.lvable.ningjiaqi.weathercurveanim;

import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CurveCalculator calculator;
    int mCurveViewWith;
    int mCurveViewHeight;
    private float mDistanceVal = 0.3f;
    private float mSpeedVal = 0.6f;
    private int[] resId = {R.id.tv_location_name,R.id.number_layout};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        final List<List<Float>> mDataBundle = new ArrayList<>();
        calculator = new CurveCalculator();
        initFakeData(mDataBundle);

        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true,new ParallaxTransformer(mSpeedVal,mDistanceVal,resId,false));
        viewPager.setOffscreenPageLimit(3);

        final CurveView curveView = (CurveView) findViewById(R.id.curve);
        curveView.setData(mDataBundle.get(0));
        final ViewTreeObserver obs = curveView.getViewTreeObserver();
        obs.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                curveView.getViewTreeObserver().removeOnPreDrawListener(this);
                mCurveViewHeight = curveView.getHeight();
                mCurveViewWith = curveView.getWidth();

                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int nextPage = position + 1;
                if (mCurveViewWith != 0 && mCurveViewHeight != 0 && nextPage < 3) {
                    List<PointF> pointFs = calculator.getDisplayPoint(mDataBundle.get(position)
                            ,mDataBundle.get(position+1)
                            ,mCurveViewWith,mCurveViewHeight,positionOffset);
                    curveView.setPoints(pointFs);
                }
            }

            @Override
            public void onPageSelected(int position) {
                 //curveView.setData(mDataBundle.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initFakeData(List<List<Float>> mDataBundle) {
        List<Float> data = new ArrayList<>();
        data.add(19f);
        data.add(22f);
        data.add(24f);
        data.add(23f);
        data.add(19f);
        data.add(17f);
        data.add(14f);
        data.add(15f);
        mDataBundle.add(data);

        List<Float> data1 = new ArrayList<>();
        data1.add(20f);
        data1.add(17f);
        data1.add(24f);
        data1.add(23f);
        data1.add(21f);
        data1.add(14f);
        data1.add(15f);
        data1.add(16f);
        mDataBundle.add(data1);

        List<Float> data2 = new ArrayList<>();
        data2.add(21f);
        data2.add(21f);
        data2.add(24f);
        data2.add(23f);
        data2.add(13f);
        data2.add(17f);
        data2.add(14f);
        data2.add(15f);
        mDataBundle.add(data2);
    }
}
