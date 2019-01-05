package com.laughter.views.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.laughter.views.model.PieData;
import com.laughter.views.util.DensityUtil;

import java.util.List;

/**
 * 作者： laughter_jiang
 * 创建时间： 2019/1/2
 * 版权：
 * 描述： com.laughter.views.views
 */

public class PieView extends View {
    // 饼状图颜色表
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000,
            0xFFFF8C69, 0xFF808080, 0xFFE6B800, 0xFF7CFC00};
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据源
    private List<PieData> mDataList;
    // 宽高
    private int mHeight, mWidth;
    // 画笔
    private Paint mPaint;
    // 定义饼状图绘制区域
    private RectF mRectF;

    private Context mContext;

    public PieView(Context context) {
        super(context);
        this.mContext = context;
        initPaint();
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    /**
     *  初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    /**
     *  为了支持AT_MOST 模式，重写omMeasure()方法
     *  当layout_width 和 layout_height 指定为 wrap_content 时，给一个默认宽高240dp
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
//        mWidth = MeasureSpec.getSize(widthMeasureSpec);
//        mHeight = MeasureSpec.getSize(heightMeasureSpec);
//        float radius = (float)(Math.min(mWidth, mHeight) / 2 * 0.8);
//        mRectF = new RectF(-radius, -radius, radius, radius);
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else {
            result = DensityUtil.dip2px(mContext, 160);
            if (specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else {
            result = DensityUtil.dip2px(mContext, 160);
            if (specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     *  在View 宽高发生变化时会被调用
     *  初始化时确定宽高，会被调用一次
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        // 饼状图半径
        float radius = (float)(Math.min(mWidth, mHeight) / 2 * 0.8);
        // 初始化饼状图绘制区域
        mRectF = new RectF(-radius, -radius, radius, radius);
    }

    /**
     *  重写onDraw()方法，执行绘制图形的逻辑
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDataList == null){
            return;
        }
        // 定义当前起始角度
        float currentStartAngle = mStartAngle;
        // 将画布坐标原点移动到中心位置
        canvas.translate(mWidth/2, mHeight/2);

        for (int i=0; i<mDataList.size(); i++){
            PieData mPie = mDataList.get(i);
            mPaint.setColor(mPie.getColor());
            canvas.drawArc(mRectF, currentStartAngle, mPie.getAngle(), true, mPaint);
            currentStartAngle += mPie.getAngle();
            canvas.save();
            canvas.translate(-mWidth / 2, -mHeight / 2);
            canvas.restore();
        }
    }

    /**
     *  设置绘制的起始角度，0°即为坐标系x轴正方向
     *  顺时针方向绘制
     */
    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();
    }

    /**
     *  设置绘制饼状图的数据
     */
    public void setData(List<PieData> mDataList) {
        this.mDataList = mDataList;
        initData();
        invalidate();
    }

    private void initData() {
        if (mDataList == null || mDataList.size()==0){
            return;
        }
        float sumValue = 0;
        for (int i=0; i<mDataList.size(); i++){
            PieData pie = mDataList.get(i);
            // 累计数值和
            sumValue += pie.getValue();
            int index = i % mColors.length;
            // 设置颜色
            pie.setColor(mColors[index]);
        }

        for (int i=0; i<mDataList.size(); i++){
            PieData pie = mDataList.get(i);

            float percentage = pie.getValue() / sumValue;
            pie.setPercentage(percentage);

            float angle = percentage * 360;
            pie.setAngle(angle);
        }
    }
}
