package com.laughter.views.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者： 江浩
 * 创建时间： 2019/2/20
 * 描述： com.laughter.view.views
 */
public class LetterSideBar extends View {

    private static final String[] letters = {"❤", "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    private Paint mBlackPaint, mBluePaint;

    // 默认内边距
    private int defPadding;
    // 每一个字母的高度
    private float itemHeight;
    // 当前字母
    private String curTouchLetter = "❤";

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBlackPaint = new Paint();
        mBlackPaint.setAntiAlias(true);
        mBlackPaint.setTextSize(sp2px(12));
        mBlackPaint.setColor(0x8a000000);

        mBluePaint = new Paint();
        mBluePaint.setAntiAlias(true);
        mBluePaint.setTextSize(sp2px(12));
        mBluePaint.setColor(Color.BLUE);

        defPadding = dp2px(5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 测量字母 A 的宽度
        int textWidth = (int)mBlackPaint.measureText("A");
        // 若未设置左右边距或左右边距小于默认边距，则使用默认边距
        int paddingRight = getPaddingRight() > defPadding ? getPaddingRight() : defPadding;
        // 计算控件的宽高
        int mWidth = getPaddingLeft() + paddingRight + textWidth * 2;
        int mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 定义绘制字母的基准线
        float baseLineX, baseLineY, itemCenterY;
        // 计算上下边距
        int paddingTop = getPaddingTop() > defPadding ? getPaddingTop() : defPadding;
        int paddingBottom = getPaddingBottom() > defPadding ? getPaddingBottom() : defPadding;
        // 计算每一个字母所占据的高度
        itemHeight = (getHeight() - paddingTop - paddingBottom) / letters.length;
        Paint.FontMetrics mFontMetrics = mBlackPaint.getFontMetrics();
        for (int i=0;i<letters.length;i++){
            // 计算绘制每一个字母的基准线
            baseLineX = getWidth() / 2 - mBlackPaint.measureText(letters[i]) / 2;
            itemCenterY = paddingTop + itemHeight * i + itemHeight / 2;
            baseLineY = itemCenterY + (mFontMetrics.descent - mFontMetrics.ascent) / 2;

            //将当前选中字母绘制为高亮
            if (curTouchLetter.equals(letters[i])){
                canvas.drawText(letters[i], baseLineX, baseLineY, mBluePaint);
            }else {
                canvas.drawText(letters[i], baseLineX, baseLineY, mBlackPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // 获取手指当前触摸 Y 值
                float curMoveY = event.getY();
                // 计算当前触摸位置对应 字母
                int curPosition = (int) (curMoveY / itemHeight);
                // 越界处理
                curPosition = curPosition > 0 ? curPosition : 0;
                curPosition = curPosition < letters.length - 1 ? curPosition : letters.length - 1;
                // 设置当前选中字母
                curTouchLetter = letters[curPosition];

                // 触摸事件回调
                if (mListener != null) {
                    mListener.onTouch(curTouchLetter, true);
                }

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                // 松手事件回调
                if (mListener != null) {
                    mListener.onTouch(curTouchLetter, false);
                }
        }
        return true;
    }

    /**
     * 设置当前选中字母，用于结合ListView实现功能
     * @param letter
     */
    public void setCurTouchLetter(String letter) {
        this.curTouchLetter = letter;
        invalidate();
    }

    private LetterTouchListener mListener;
    public void setTouchListener(LetterTouchListener listener) {
        this.mListener = listener;
    }

    public interface LetterTouchListener{
        void onTouch(String letter, boolean isTouching);
    }

    private int sp2px(int val) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, val,
                getResources().getDisplayMetrics());
    }

    private int dp2px(int val) {
        float density = getResources().getDisplayMetrics().density;
        return (int)(density * val +0.5f);
    }
}
