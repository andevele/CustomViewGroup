package com.zhulf.www.customviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/4/25.
 */

public class ViewGroupExactly extends ViewGroup {
    public ViewGroupExactly(Context context) {
        super(context);
    }

    public ViewGroupExactly(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupExactly(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthAtMost = 0;
        int heightAtMost = 0;

        int childMeasuredWidth = 0;
        int childMeasuredHeight = 0;

        int childCount = getChildCount();
        CustomLayoutParams params = null;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
//            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            params = (CustomLayoutParams) child.getLayoutParams();
//            childMeasuredWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            childMeasuredWidth = child.getMeasuredWidth();
//            childMeasuredHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            childMeasuredHeight = child.getMeasuredHeight();
//给每一个子元素测量大小
            if(i == 0) {
                widthAtMost += childMeasuredWidth + params.leftMargin + params.rightMargin;
                heightAtMost = childMeasuredHeight + params.topMargin;
            }

            if(i == 1) {
                widthAtMost += childMeasuredWidth + params.leftMargin + params.rightMargin;
                heightAtMost = childMeasuredHeight + params.topMargin;
            }

            heightAtMost = Math.max(heightAtMost, childMeasuredHeight);


        }

        int widthMeasure = (widthMode == MeasureSpec.AT_MOST) ? widthAtMost : widthSpec;
        int heightMeasure = (widthMode == MeasureSpec.AT_MOST) ? heightAtMost : heightSpec;
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            CustomLayoutParams params = (CustomLayoutParams) child.getLayoutParams();
            int realWidth = child.getMeasuredWidth();
            int realHeight = child.getMeasuredHeight();
            int cl = 0;
            int ct = 0;
            int cr = 0;
            int cb = 0;
//给每个子元素设置布局
            switch (i) {
                case 0:
                    cl = params.leftMargin;
                    ct = params.topMargin;
                    break;
                case 1:
                    cl = params.leftMargin + 600;
                    ct = params.topMargin;
                    break;
            }
            cr = cl + realWidth;
            cb = ct + realHeight;

            child.layout(cl, ct, cr, cb);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }


    public static class CustomLayoutParams extends MarginLayoutParams {

        public CustomLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CustomLayoutParams(int width, int height) {
            super(width, height);
        }

        public CustomLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CustomLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
