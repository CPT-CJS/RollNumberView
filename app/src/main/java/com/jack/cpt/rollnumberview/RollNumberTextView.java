package com.jack.cpt.rollnumberview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cpt on 2018/8/26.
 */

public class RollNumberTextView extends LinearLayout {

    private List<RollNumberView> itemViews = new ArrayList<>();
    private int deafultDuration = 100;
    private int startDuration = deafultDuration;

    public RollNumberTextView(Context context) {
        this(context, null);
    }

    public RollNumberTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollNumberTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
    }

    public void startAnimator(int startNumber, int endNumber) {
        //reset
        itemViews.clear();
        startDuration = deafultDuration;
        removeAllViews();
        //如果是负数，那就变成默认值  1866986
        if (!isNumber(String.valueOf(startNumber))) {
            startNumber = 1866986;
        }

        //如果是负数，那就变成默认值  Integer.MAX_VALUE
        if (!isNumber(String.valueOf(endNumber))) {
            endNumber = Integer.MAX_VALUE;
        }

        String startStr = String.valueOf(startNumber);
        String endStr = String.valueOf(endNumber);

        if (startStr.length() > endStr.length()) {
            Toast.makeText(getContext(), "startNumber的长度必须小于endNumber", Toast.LENGTH_SHORT).show();
            return;
        }

        RollNumberView rollNumberView;
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemParams.leftMargin = DrawUtils.dip2px(1);
        if (startStr.length() != endStr.length()) {
            //如果位数不同，特殊处理多出来的位数 0 -> x
            int offsetIndex = endStr.length() - startStr.length();
            for (int i = 0; i < offsetIndex; i++) {
                rollNumberView = new RollNumberView(getContext());
                rollNumberView.setNumber(0, endStr.charAt(i) - '0'); //- '0'  是把char类型的数字转化成int类型的数字  不转结果是Ascii
                itemViews.add(rollNumberView);
                if (i != 0) {
                    addView(rollNumberView, itemParams);
                } else {
                    addView(rollNumberView);
                }
            }

            for (int i = 0; i < startStr.length(); i++) {
                rollNumberView = new RollNumberView(getContext());
                rollNumberView.setNumber(startStr.charAt(i) - '0', endStr.charAt(i + offsetIndex) - '0');
                itemViews.add(rollNumberView);
                addView(rollNumberView, itemParams);
            }

        } else {
            for (int i = 0; i < startStr.length(); i++) {
                rollNumberView = new RollNumberView(getContext());
                rollNumberView.setNumber(startStr.charAt(i) - '0', endStr.charAt(i) - '0');
                itemViews.add(rollNumberView);
                if (i != 0) {
                    addView(rollNumberView, itemParams);
                } else {
                    addView(rollNumberView);
                }
            }
        }

        for (RollNumberView item : itemViews) {
            startDuration += 180;
            item.start(startDuration);
        }
    }

    public static boolean isNumber(String str) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
