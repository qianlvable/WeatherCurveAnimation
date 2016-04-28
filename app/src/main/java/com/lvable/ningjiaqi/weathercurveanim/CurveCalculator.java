package com.lvable.ningjiaqi.weathercurveanim;

import android.graphics.PointF;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ningjiaqi on 16/4/28.
 */
public class CurveCalculator {
    private float maxRange;
    private float minRange;
    private float mRange;

    public List<PointF> getDisplayPoint(List<Float> data,int width,int height) {
        List<PointF> result = new ArrayList<>();
        if (data != null && data.size() > 0){
            maxRange = data.get(0);
            minRange = data.get(0);
            for (int i = 0; i < data.size(); i++) {
                maxRange = Math.max(data.get(i),maxRange);
                minRange = Math.min(data.get(i),minRange);
            }
            mRange = maxRange - minRange;
            mRange *= 3; // workaround: to smooth the difference
            int offset = (int) (height * 0.6);
            float intervalWidth = (float)width / (data.size()-1);
            float x = 0;
            for (int i = 0; i < data.size(); i++) {
                float y = offset - Math.abs((data.get(i) - minRange) / mRange) * offset;
                if (i == data.size()-1)
                    x = width;
                result.add(new PointF(x,y));
                x += intervalWidth;
            }
        }
        return result;
    }

    public List<PointF> getDisplayPoint(@NonNull List<Float> src, @NonNull List<Float> dst
            , int width, int height, float progress) {
        List<PointF> srcPoints = getDisplayPoint(src,width,height);
        List<PointF> dstPoints = getDisplayPoint(dst,width,height);
        List<PointF> result = new ArrayList<>();
        if (src.size() == dst.size()){
            for (int i = 0; i < src.size(); i++) {
                PointF point = new PointF();
                PointF cur = srcPoints.get(i);
                PointF nxt = dstPoints.get(i);
                point.x = cur.x;
                point.y = (nxt.y - cur.y) * progress + cur.y;
                result.add(point);
            }
        }
        return result;
    }
}
