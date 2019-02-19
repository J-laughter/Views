package com.laughter.views.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.laughter.views.R;


/**
 * 作者： 江浩
 * 创建时间： 2019/2/19
 * 描述： com.laughter.view.views
 */

/**
 * 1. 分析效果
 * 2. 确定自定义属性，编写 attrs.xml
 * 3. 在布局中使用
 * 4. 在自定义View中获取自定义属性
 * 5. onMeasure()
 * 6. 画底层弧、表层弧、中间文字
 * 7. 其他思考
 */
public class QQStepView extends View {

    /**
     * 属性定义
     */
    // 底层颜色
    private int mBottomColor;
    // 表层颜色
    private int mSurfaceColor;
    // 进度条宽度
    private float mBorderWidth;
    // 中间文字颜色和大小
    private int mStepTextColor;
    private int mStepTextSize;

    // 当前步数和最大步数
    private int curStepNum;
    private int stepNumMax;

    private int mWidth,mHeight;
    // 默认宽高
    private int defWidth, defHeight;

    private Paint mPaint;
    private Paint mTextPaint;
    private RectF mRectf = new RectF();
    private Rect mTextRect = new Rect();

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mBottomColor = array.getColor(R.styleable.QQStepView_bottomColor, 0xffdbe9ff);
        mSurfaceColor = array.getColor(R.styleable.QQStepView_surfaceColor, 0xff499aff);
        mBorderWidth = array.getDimension(R.styleable.QQStepView_borderWidth, dp2px(context, 8));
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, 64);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, 0x8a000000);
        stepNumMax = array.getColor(R.styleable.QQStepView_stepNumMax, 10000);
        array.recycle();

        // 初始化画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 圆形线冒（即进度条两端弧形）
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mBorderWidth);

        mTextPaint = new Paint();
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);

        defWidth = dp2px(context, 200);
        defHeight = dp2px(context, 200);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取测量值
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        // 若测量模式为 AT_MOST ，即 布局中指定 wrap_content，给定默认值
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST){
            widthSpecSize = defWidth;
        }
        if (MeasureSpec.getMode(heightSpecSize) == MeasureSpec.AT_MOST){
            heightSpecSize = defHeight;
        }
        // 取宽高中的较小值
        mWidth = widthSpecSize > heightSpecSize ? heightSpecSize : widthSpecSize;
        mHeight = widthSpecSize > heightSpecSize ? heightSpecSize : widthSpecSize;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画底层弧
        canvas.translate(mWidth / 2, mHeight / 2);
        int radius = (int)(mWidth / 2 - mBorderWidth / 2);
        mRectf.set(-radius, -radius, radius, radius);
        mPaint.setColor(mBottomColor);
        canvas.drawArc(mRectf, 135, 270, false, mPaint);

        // 画表层弧
        mPaint.setColor(mSurfaceColor);
        float sweepAngle = curStepNum * 1.0f / stepNumMax * 270 > 270 ? 270 :  curStepNum * 1.0f / stepNumMax * 270;
        canvas.drawArc(mRectf,135, sweepAngle, false, mPaint);

        // 画文字
        String stepText = String.valueOf(curStepNum);
        mTextPaint.getTextBounds(stepText, 0, stepText.length(), mTextRect);
        float startX = - mTextRect.width() / 2;
        canvas.drawText(stepText, startX, 0, mTextPaint);
    }

    private int dp2px(Context context, int size) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(size * density + 0.5f);
    }

    public void setCurStepNum(int curStepNum) {
        this.curStepNum = curStepNum;
        if (curStepNum > 0){
            invalidate();
        }
    }

    public void setStepNumMax(int stepNumMax) {
        this.stepNumMax = stepNumMax;
    }

    public void setBottomColor(int mBottomColor) {
        this.mBottomColor = mBottomColor;
    }

    public void setBorderWidth(float mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
    }

    public void setSurfaceColor(int mSurfaceColor) {
        this.mSurfaceColor = mSurfaceColor;
    }

    public void setStepTextColor(int mStepTextColor) {
        this.mStepTextColor = mStepTextColor;
    }

    public void setStepTextSize(int mStepTextSize) {
        this.mStepTextSize = mStepTextSize;
    }
}
