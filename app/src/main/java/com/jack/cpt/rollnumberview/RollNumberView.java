package com.jack.cpt.rollnumberview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cpt on 2018/8/26.
 */

public class RollNumberView extends View {

    private Paint mTextPaint;
    private Paint mPaint;
    private String mText;
    private int mTextWidth;
    private int mTextHeight;
    private Rect mTextRect;
    private List<RectF> mNumberRectfs;
    private List<String> mTextList;
    private int topMargin = DrawUtils.dip2px(10);
    private float offsetValueY;
    private int mStartNumber;
    private int mEndNumer;
    private int mViewWidth;
    private int mViewHeight;

    public RollNumberView(Context context) {
        this(context, null);
    }

    public RollNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "DINPro-Bold.otf");
        mTextPaint.setTypeface(typeFace);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffff4d4d);
        mTextPaint.setTextSize(DrawUtils.sp2px(58f));

        //测量单个数字的高度和宽度
        mText = "9";
        mTextRect = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
        //文字宽
        mTextWidth = mTextRect.width();
        //文字高
        mTextHeight = mTextRect.height();

        mViewWidth = mTextWidth + DrawUtils.dip2px(1);
        mViewHeight = mTextHeight + DrawUtils.dip2px(1);

        mTextPaint.setShader(new LinearGradient(0, mTextHeight / 2, mTextWidth, mTextHeight / 2, 0xffFF4468, 0xffFF4B1F, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mViewWidth, mViewHeight);//加个1dp高度
    }

    public void setNumber(int startNumber, int endNumber) {
        mStartNumber = startNumber;
        mEndNumer = endNumber;
    }

    public String printNumber() {
        return mStartNumber + " " + mEndNumer;
    }

    public void start(int duration) {
        if (String.valueOf(mStartNumber).length() != 1 || String.valueOf(mEndNumer).length() != 1) {
            throw new RuntimeException("RollNumberView只想支持1位数的操作 startNumber=" + mStartNumber + " endNumber=" + mEndNumer);
        }

        if (mStartNumber >= mEndNumer) {
            mEndNumer += 10;//+10让mEndNumer保证大于mStartNumber
        }
        mTextList = new ArrayList<>();
        String tmpStr;
        for (int i = mStartNumber; i <= mEndNumer; i++) {
            tmpStr = String.valueOf(i);
            if (tmpStr.length() > 1) {
                mTextList.add(tmpStr.substring(1));
            } else {
                mTextList.add(tmpStr);
            }
        }

        mNumberRectfs = new ArrayList<>();
        RectF tmpRectF;
        int margin;
        for (int i = 0; i < mTextList.size(); i++) {
            if (i == 0) {
                margin = 0;
            } else {
                margin = topMargin * i;
            }
            tmpRectF = new RectF(0, 0 + (i * mTextHeight) + margin, mTextWidth, mTextHeight + (i * mTextHeight) + margin);
            mNumberRectfs.add(tmpRectF);
        }
        invalidate();

        if (mTextList.size() > 0) {
            float endValue = mNumberRectfs.get(mNumberRectfs.size() - 1).top;
            ValueAnimator animator = ValueAnimator.ofFloat(0, endValue);
            animator.setDuration(duration);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offsetValueY = -(float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTextList != null && mTextList.size() > 0 && mNumberRectfs != null && mNumberRectfs.size() > 0) {
            RectF numberRect = new RectF();
            String numberStr;
            Paint.FontMetricsInt fontMetricsInt;
            float centerY;
            for (int i = 0; i < mNumberRectfs.size(); i++) {
                numberRect.top = mNumberRectfs.get(i).top + offsetValueY;
                numberRect.bottom = mNumberRectfs.get(i).bottom + offsetValueY;
                if ((numberRect.top >= 0 && numberRect.top <= mViewHeight) || (numberRect.bottom >= 0 && numberRect.bottom <= mViewHeight)) {
                    numberRect.left = mNumberRectfs.get(i).left;
                    numberRect.right = mNumberRectfs.get(i).right;
                    numberStr = mTextList.get(i);
                    fontMetricsInt = mTextPaint.getFontMetricsInt();
                    centerY = numberRect.centerY() - fontMetricsInt.top - ((fontMetricsInt.bottom - fontMetricsInt.top) / 2);
                    mTextPaint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(numberStr, numberRect.centerX(), centerY - (mTextHeight * 0.03f), mTextPaint);
                } else {
                    continue;
                }
            }
        }
    }
}
