package com.example.wetok.view.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Hashtable;

// reference: https://blog.csdn.net/u013836857/article/details/70885446
public class MyLinearLayout extends LinearLayout {
    int mLeft, mRight, mTop, mBottom;
    Hashtable map = new Hashtable();

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, int horizontalSpacing, int verticalSpacing) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mCount = getChildCount();
        int mX = 0;
        int mY = 0;
        mLeft = 0;
        mRight = 0;
        mTop = 5;
        mBottom = 0;

        int j = 0;

        View lastview = null;
        for (int i = 0; i < mCount; i++) {
            final View child = getChildAt(i);

            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int childw = child.getMeasuredWidth();
            int childh = child.getMeasuredHeight();
            mX += childw;

            Position position = new Position();
            mLeft = getPosition(i - j, i);
            mRight = mLeft + child.getMeasuredWidth();
            if (mX >= mWidth) {
                mX = childw;
                mY += childh;
                j = i;
                mLeft = 25;
                mRight = mLeft + child.getMeasuredWidth();
                mTop = mY + 5;
            }
            mBottom = mTop + child.getMeasuredHeight();
            mY = mTop;
            position.left = mLeft;
            position.top = mTop + 5;
            position.right = mRight;
            position.bottom = mBottom;
            map.put(child, position);
        }
        setMeasuredDimension(mWidth, mBottom);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(0, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Position pos = (Position) map.get(child);
            if (pos != null) {
                child.layout(pos.left, pos.top, pos.right, pos.bottom);
            } else {
            }
        }
    }

    private class Position {
        int left, top, right, bottom;
    }

    public int getPosition(int IndexInRow, int childIndex) {
        if (IndexInRow > 0) {
            return getPosition(IndexInRow - 1, childIndex - 1) + getChildAt(childIndex - 1).getMeasuredWidth() + 5;
        }
        return getPaddingLeft();
    }
}
