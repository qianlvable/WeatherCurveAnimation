package com.lvable.ningjiaqi.weathercurveanim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ningjiaqi on 16/4/28.
 */
public class CurveView extends View {
    private List<Float> mData;
    private List<PointF> mPoints;
    private Path mPath;
    private Paint mPaint;
    private Paint mPointPaint;

    private CurveCalculator mCal;
    private int mDotRadius = 8;

    public CurveView(Context context) {
        super(context);
        init();
    }


    public CurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xfffafafa);

        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeWidth(3);
        mPointPaint.setColor(0xfffafafa);

        mPoints = new ArrayList<>();
        mCal = new CurveCalculator();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        if (mData != null && mData.size() >2) {
            if (mPoints == null) {
                mPoints = mCal.getDisplayPoint(mData, getWidth(), getHeight());
            }
            // draw the curve
            mPath.reset();
            mPath.moveTo(0,getHeight());
            mPath.lineTo(0,mPoints.get(0).y);
            for (int i = 0; i < mPoints.size()-1; i++) {
                PointF pre = mPoints.get(i);
                PointF cur = mPoints.get(i+1);
                mPath.quadTo(pre.x,pre.y,(cur.x + pre.x)/2,(pre.y +cur.y)/2);
            }
            // last curve
            PointF last2 = mPoints.get(mPoints.size()-2);
            PointF last1 = mPoints.get(mPoints.size()-1);
            mPath.quadTo((last1.x + last2.x)/2,(last1.y + last2.y)/2,last1.x,last1.y);

            // close the path
            mPath.lineTo(getWidth(),getHeight());
            mPath.lineTo(0,getHeight());
            mPath.lineTo(0,mPoints.get(0).y);

            canvas.drawPath(mPath,mPaint);

            // draw the point
            for (int i = 0; i < mPoints.size(); i++) {
                if ( i != 0 && i != mPoints.size()-1) {
                    PointF p = mPoints.get(i);
                    mPointPaint.setColor(0xfffafafa);
                    mPointPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(p.x, p.y, mDotRadius, mPointPaint);

                    mPointPaint.setColor(0xff217bb4);
                    mPointPaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(p.x,p.y,mDotRadius-3,mPointPaint);
                }
            }
        }
    }

    public void setData(List<Float> data){
        mData = data;
        mPoints = null; // the origin points is dirty should abandon
        invalidate();
    }

    public void setPoints(List<PointF> points) {
        mPoints = points;
        invalidate();
    }


}
