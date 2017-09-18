package com.stone.autowrap.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhangll on 2017/9/18.
 */

public class AutoWrapLayout extends ViewGroup {

    private int horizontalSpace = 10; // 字块水平之间的间距
    private int verticalSpace = 10; // 行距
    private WrapAdapter adapter;

    public AutoWrapLayout(Context context) {
        this(context, null);
    }

    public AutoWrapLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoWrapLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.autoWrap);
        horizontalSpace = (int) a.getDimension(R.styleable.autoWrap_horizontalSpace, horizontalSpace);
        verticalSpace = (int) a.getDimension(R.styleable.autoWrap_verticalSpace, verticalSpace);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int totalWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int availableWidth = totalWidth - getPaddingLeft() - getPaddingRight();
        // 控件高度，以下逻辑主要是算这个
        int totalHeight = getPaddingTop() + getPaddingBottom();
        int childCount = getChildCount();
        if (childCount > 0) {
            int thisLineWidth = 0;
            int thisLineBeginIndex = 0;
            for (int i = 0; i < childCount; i++) {
                TextView tv = (TextView) getChildAt(i);
                tv.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                int itemWidth = tv.getMeasuredWidth();
                if (i == 0) {
                    // 第一条数据，直接赋值成itemWidth
                    thisLineWidth += itemWidth;
                    totalHeight = totalHeight + tv.getMeasuredHeight();
                } else {
                    // 非第一条数据，需要添加间隔
                    thisLineWidth = thisLineWidth + horizontalSpace + itemWidth;
                }

                if (thisLineWidth > availableWidth && i < childCount - 1) {
                    // 这一行装不下，则，此tv已经身处下一行了
                    totalHeight = totalHeight + verticalSpace + tv.getMeasuredHeight();
                    int extaPadding = availableWidth - (thisLineWidth - itemWidth - horizontalSpace);
                    updateItemPadding(thisLineBeginIndex, i - 1, extaPadding);

                    // 更新一些中间变量
                    thisLineWidth = tv.getMeasuredWidth();
                    thisLineBeginIndex = i;
                }
            }
        }
        setMeasuredDimension(totalWidth, totalHeight);
    }

    private void updateItemPadding(int fromIndex, int toIndex, int extraPadding) {
        int everyExtraPadding = extraPadding / (toIndex - fromIndex + 1) / 2;
        for (int i = fromIndex; i <= toIndex; i++) {
            TextView itemTv = (TextView) getChildAt(i);
            if (i == toIndex) {
                everyExtraPadding = (extraPadding - everyExtraPadding * (toIndex - fromIndex) * 2) / 2;
            }

            itemTv.setPadding(itemTv.getPaddingLeft() + everyExtraPadding,
                    itemTv.getPaddingTop(),
                    itemTv.getPaddingRight() + everyExtraPadding,
                    itemTv.getPaddingBottom());
            itemTv.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int maxRight = getMeasuredWidth() - getPaddingRight();
        int childCount = getChildCount();
        int layoutLeft = getPaddingLeft(), layoutTop = getPaddingTop();
        for (int i = 0; i < childCount; i++) {
            TextView childView = (TextView) getChildAt(i);
            int itemWidth = childView.getMeasuredWidth();

            if (layoutLeft + itemWidth > maxRight) {
                layoutLeft = getPaddingLeft();
                layoutTop = layoutTop + childView.getMeasuredHeight() + verticalSpace;
            }

            int layoutRight = layoutLeft + childView.getMeasuredWidth();
            childView.layout(layoutLeft, layoutTop, layoutRight, layoutTop + childView.getMeasuredHeight());
            layoutLeft = layoutLeft + horizontalSpace + itemWidth;
        }
    }

    public void setAdapter(WrapAdapter adapter) {
        this.adapter = adapter;
        removeAllViews();
        int num = adapter.getItemCount();
        for (int i = 0; i < num; i++) {
            TextView tv = adapter.onCreateTextView(i);
            addView(tv);
        }
    }

    public interface WrapAdapter {
        /**
         * item个数
         */
        int getItemCount();

        /**
         * adapter生成TextView
         */
        TextView onCreateTextView(int index);
    }
}
