package com.ls.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by ls on 15-2-10.
 */
public class ParallaxListView extends ListView {
    private int maxHeightZoom;
    private View mHeaderView;
    private int mHeaderViewHeight;
    private int mHeaderViewCurrentHeight;
    private int listViewPaddingBottom;
    private Scroller mScroller;

    public ParallaxListView(Context context) {
        super(context);
        init();
    }


    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
/**初始化Scroller*/
    private void init() {
        mScroller = new Scroller(getContext());

    }

    /**
     * 重写方法，获得下拉上推距离
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                   int maxOverScrollY, boolean isTouchEvent) {
        if (deltaY < 0) {//下拉
            mHeaderViewCurrentHeight += -deltaY;
            if (mHeaderViewCurrentHeight >= maxHeightZoom) mHeaderViewCurrentHeight = maxHeightZoom;
            mHeaderView.getLayoutParams().height = mHeaderViewCurrentHeight;
            mHeaderView.requestLayout();

        } else {//上推
            listViewPaddingBottom += deltaY;
            this.setPadding(0, 0, 0, listViewPaddingBottom);
            requestLayout();
        }
        return true;
    }

    /**
     * 设置下拉的最大距离
     */
    public void setMaxHeightZoom(int maxHeightZoom) {
        this.maxHeightZoom = maxHeightZoom;
        this.mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        this.mHeaderViewCurrentHeight = mHeaderViewHeight;
    }

    /**
     * 设置headerView即ImageView
     */
    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (mHeaderViewCurrentHeight > mHeaderViewHeight) {//上拉恢复
                RecoverAnimation animation = new RecoverAnimation();
                animation.setDuration(600);
                mHeaderView.startAnimation(animation);
                mHeaderViewCurrentHeight = mHeaderViewHeight;
            } else {//下推恢复
                mScroller.startScroll(0, -listViewPaddingBottom, 0, listViewPaddingBottom, 500);
                postInvalidate();
                listViewPaddingBottom = 0;
            }

        }
        return super.onTouchEvent(ev);
    }

    /**
     * 类似绘制死循环，上推恢复
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            setPadding(0, 0, 0, -mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 下拉恢复动画
     */
    class RecoverAnimation extends Animation {
        private int mViewExtendHeight;


        public RecoverAnimation() {

            mViewExtendHeight = mHeaderViewCurrentHeight - mHeaderViewHeight;

        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
//interpolatedTime从0到1
            int newHeight = mHeaderViewHeight + (int) (mViewExtendHeight * (1 - interpolatedTime));
            mHeaderView.getLayoutParams().height = newHeight;
            mHeaderView.requestLayout();

        }
    }
}
