package com.oubowu.stickyitemdecoration;


import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/1/6.
 */

public class StickyHeadContainer extends FrameLayout {

    private int mOffset;
    private int mLastOffset = Integer.MIN_VALUE;
    private int mLastStickyHeadPosition = Integer.MIN_VALUE;

    private int mLeft;
    private int mRight;
    private int mTop;
    private int mBottom;

    private DataCallback mDataCallback;


    public StickyHeadContainer(Context context) {
        this(context, null);
    }

    public StickyHeadContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyHeadContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/1/9 屏蔽点击事件
            }
        });
    }

    public void putTypeView(int type, View view) {

        view.setVisibility(View.INVISIBLE);
        view.setTag(type);
        addView(view);
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int desireHeight;
//        int desireWidth;
//
//        int count = getChildCount();
//
//        mShowChild = getChildVisible();
//        if (mShowChild == null) {
//            return;
//        }
//        // 测量子元素并考虑外边距
//        measureChildWithMargins(mShowChild, widthMeasureSpec, 0, heightMeasureSpec, 0);
//        // 获取子元素的布局参数
//        final MarginLayoutParams lp = (MarginLayoutParams) mShowChild.getLayoutParams();
//        // 计算子元素宽度，取子控件最大宽度
//        desireWidth = mShowChild.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
//        // 计算子元素高度
//        desireHeight = mShowChild.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
//
//        // 考虑父容器内边距
//        desireWidth += getPaddingLeft() + getPaddingRight();
//        desireHeight += getPaddingTop() + getPaddingBottom();
//        // 尝试比较建议最小值和期望值的大小并取大值
//        desireWidth = Math.max(desireWidth, getSuggestedMinimumWidth());
//        desireHeight = Math.max(desireHeight, getSuggestedMinimumHeight());
//        // 设置最终测量值
//        setMeasuredDimension(resolveSize(desireWidth, widthMeasureSpec), resolveSize(desireHeight, heightMeasureSpec));
//    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//        final View child = getChildVisible();
//        if (child == null) {
//            return;
//        }
//        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
//
//        final int paddingLeft = getPaddingLeft();
//        final int paddingTop = getPaddingTop();
//
//        mLeft = paddingLeft + lp.leftMargin;
//        mRight = child.getMeasuredWidth() + mLeft;
//
//        mTop = paddingTop + lp.topMargin + mOffset;
//        mBottom = child.getMeasuredHeight() + mTop;
//
//        child.layout(mLeft, mTop, mRight, mBottom);
//    }

    // 生成默认的布局参数
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

//    // 生成布局参数,将布局参数包装成我们的
//    @Override
//    protected LayoutParams generateLayoutParams(LayoutParams p) {
//        return new MarginLayoutParams(p);
//    }
//
//    // 生成布局参数,从属性配置中生成我们的布局参数
//    @Override
//    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return new MarginLayoutParams(getContext(), attrs);
//    }
//
//    // 查当前布局参数是否是我们定义的类型这在code声明布局参数时常常用到
//    @Override
//    protected boolean checkLayoutParams(LayoutParams p) {
//        return p instanceof MarginLayoutParams;
//    }

    public void scrollChild(int offset, View view) {
        Log.d("scrollChild===", offset + "//////" + mLastOffset);
        if (mLastOffset != offset) {
            mOffset = offset;
            ViewCompat.offsetTopAndBottom(getChildVisible(), mOffset - mLastOffset);
        }
        mLastOffset = mOffset;
    }

    public void scrollChild(int offset) {
        Log.d("scrollChild===", offset + "//////" + mLastOffset + "---" + getChildVisible().getTag());
        if (mLastOffset != offset) {
            mOffset = offset;
            if (getChildVisible() == null) {
                return;
            }
//            ViewCompat.offsetTopAndBottom(getChildVisible(), mOffset - mLastOffset);
            scrollTo(0, -mOffset);
        }
        mLastOffset = mOffset;
    }

    protected int getChildHeight() {
        return getChildVisible() == null ? 0 : getChildVisible().getHeight();
    }

    private View getChildVisible() {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getVisibility() == VISIBLE) {
                return getChildAt(i);
            }
        }
        return getChildAt(0);
    }


    protected void onDataChange(int stickyHeadPosition) {
        if (mDataCallback != null && mLastStickyHeadPosition != stickyHeadPosition) {
            mDataCallback.onDataChange(stickyHeadPosition);
        }
        mLastStickyHeadPosition = stickyHeadPosition;
    }

    public void reset() {
        mLastStickyHeadPosition = Integer.MIN_VALUE;
    }

    public View showStickType(int itemType) {
        int showIndex = -1;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getTag() != null && getChildAt(i).getTag() instanceof Integer && ((Integer) getChildAt(i).getTag()) == itemType) {
                showIndex = i;
                getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                getChildAt(i).setVisibility(View.INVISIBLE);
            }
            Log.d("showStickType===", getChildAt(i).getTag() + "++++" + getChildAt(i).getVisibility() + "+++" + getVisibility());
        }
        return getChildAt(showIndex);
    }

    public interface DataCallback {
        void onDataChange(int pos);
    }

    public void setDataCallback(DataCallback dataCallback) {
        mDataCallback = dataCallback;
    }
}
