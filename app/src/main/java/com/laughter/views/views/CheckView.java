package com.laughter.views.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.laughter.views.R;
import com.laughter.views.util.DensityUtil;

/**
 * 作者：laughter_jiang
 * 时间：2019/1/3 22:52
 * 描述：com.laughter.view.views
 */
public class CheckView extends View {

    // 动画状态-无
    private static final int ANIM_NULL = 0;
    // 动画状态-开始
    private static final int ANIM_CHECK = 1;
    // 动画状态-结束
    private static final int ANIM_UNCHECK = 2;

    private Context mContext;
    private int mWidth, mHeight;

    private Handler mHandler;
    private Paint mPaint;
    private Bitmap mBitmap;

    // 当前图片序号
    private int animCurPage = -1;
    // 图片总张数
    private int animMaxPage = 13;
    // 动画时长
    private int animDuration = animMaxPage * 50;
    // 动画状态
    private int animState = ANIM_NULL;

    // 是否选中状态
    private boolean isChecked = false;

    // 定义图像选区 和 实际绘制位置
    Rect src, dst;

    // 单张图片边长
    int sideLength;

    public CheckView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public CheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    /**
     * 初始化
     */
    @SuppressLint("HandlerLeak")
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.checkmark);

        // 计算图形边长
        sideLength = mBitmap.getHeight();
        // 初始化图像选区 和 绘制位置
        src = new Rect();
        dst = new Rect();

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("jh1", String.valueOf(animCurPage));
                if (animCurPage < animMaxPage && animCurPage >= -1){
                    invalidate();
                    if (animState == ANIM_NULL){
                        return;
                    }
                    this.sendEmptyMessageDelayed(0, animDuration / animMaxPage);
                }else {
                    if (isChecked){
                        animCurPage = animMaxPage - 1;
                    }else {
                        animCurPage = -1;
                    }
                    animState = ANIM_NULL;
                }
            }
        };
    }

    /**
     * 重写onMeasure方法，支持wrap_content
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * 确定View宽度
     * @param measureSpec
     * @return
     */
    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else {
            result = DensityUtil.dip2px(mContext, 56);
            if (specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 确定View高度
     * @param measureSpec
     * @return
     */
    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else {
            result = DensityUtil.dip2px(mContext, 56);
            if (specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 获取View大小
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        int drawWidth = mWidth - DensityUtil.dip2px(mContext, 8);
        dst.set(-drawWidth/2, -drawWidth/2, drawWidth/2, drawWidth/2);
    }

    /**
     * 绘制图像
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("jh2", String.valueOf(animCurPage));
        // 移动坐标系到画布中央
        canvas.translate(mWidth/2, mHeight/2);
        // 绘制背景圆形
        canvas.drawCircle(0, 0, mWidth/2, mPaint);
        // 设置图像选区
        src.set(animCurPage * sideLength, 0,
                (animCurPage + 1) * sideLength, sideLength);
        // 绘制
        canvas.drawBitmap(mBitmap, src, dst, null);

        // 绘制完成后，将当前图片指定为下一帧
        if (animState == ANIM_CHECK){
            animCurPage ++;
        }else if (animState == ANIM_UNCHECK){
            animCurPage --;
        }
    }

    /**
     * 选择
     */
    public void check() {
        if (animState != ANIM_NULL || isChecked) {
            return;
        }
        animState = ANIM_CHECK;
        animCurPage = 0;
        mHandler.sendEmptyMessageDelayed(0 ,animDuration / animMaxPage);
        isChecked = true;
    }

    /**
     * 取消选择
     */
    public void unCheck() {
        if (animState != ANIM_NULL || (!isChecked)) {
            return;
        }
        animState = ANIM_UNCHECK;
        animCurPage = animMaxPage - 2;
        mHandler.sendEmptyMessageDelayed(0, animDuration / animMaxPage);
        isChecked = false;
    }

    /**
     * 设置动画时长
     * @param animDuration
     */
    public void setAnimDuration(int animDuration) {
        if (animDuration > 0){
            this.animDuration = animDuration;
        }
    }

    /**
     * 设置背景圆形颜色
     * @param color
     */
    public void setThemeColor(int color) {
        mPaint.setColor(color);
    }

    /**
     * 是否选中
     * @return
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * 获取绘制状态
     * @return
     */
    public int getAnimState() {
        return animState;
    }
}
