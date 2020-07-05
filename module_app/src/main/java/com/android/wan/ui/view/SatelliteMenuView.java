package com.android.wan.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.android.wan.R;
import com.dq.util.ILog;

public class SatelliteMenuView extends ViewGroup implements View.OnClickListener, View.OnLongClickListener {
    private static final int POS_LEFT_TOP = 0;
    private static final int POS_LEFT_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;

    private Position mPosition = Position.RIGHT_BOTTOM;//菜单位置
    private int mRadius;//菜单半径

    public Status mStatus = Status.CLOSE;//菜单开闭状态

    public View mCButton;//菜单主按钮

    private OnMenuItemClickListener mOnMenuItemClickListener;//菜单点击事件的成员变量

    private OnMainClickListener mainClickListener;//主菜单事件

    /**
     * 菜单状态枚举类
     */
    public enum Status {
        OPEN, CLOSE
    }

    /**
     * 菜单位置枚举类
     */
    private enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    /**
     * 菜单按钮点击事件回调接口
     */
    public interface OnMenuItemClickListener {
        void onClick(View view, int position);
    }

    /**
     * 菜单按钮点击事件回调接口
     */
    public interface OnMainClickListener {
        void onLongClick();

        void onClick();

        void onFinish();
    }

    public SatelliteMenuView(Context context) {
        this(context, null);
    }

    public SatelliteMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SatelliteMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_FRACTION, 60,
                getResources().getDisplayMetrics());//设置菜单半径默认值

        //获取自定义属性值
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SrcMenu, defStyleAttr, 0);

        //获取位置值
        int pos = array.getInt(R.styleable.SrcMenu_position, POS_RIGHT_BOTTOM);
        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
            default:
                break;
        }

        //获取菜单半径值
        mRadius = (int) array.getDimension(R.styleable.SrcMenu_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_FRACTION, 60, getResources().getDisplayMetrics())) / 5 * 4;
        array.recycle();
    }

    /**
     * 设置菜单点击事件
     */
    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    private int type;

    public void setMainOnClickLister(OnMainClickListener mainOnClickLister) {
        this.mainClickListener = mainOnClickLister;
    }

    /**
     * 测量模式+测量值
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //测量 child
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (b) {
            layoutCButton();
            int count = getChildCount();
            for (int j = 0; j < count - 1; j++) {
                View child = getChildAt(j);
                //开始时设置子菜单不可见
                child.setVisibility(GONE);
                //默认按钮位于左上时
                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * j));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * j));
                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();

                //按钮位于左下、右下时
                if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                    ct = getMeasuredHeight() - cHeight - ct;
                }

                //按钮位于右上、右下时
                if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                    cl = getMeasuredWidth() - cWidth - cl;
                }
                child.layout(cl - 60, ct - 100, cl + cWidth - 60, ct + cHeight - 100);
            }
        }
    }

    /**
     * 定位主菜单按钮
     */
    private void layoutCButton() {
        mCButton = getChildAt(3);
        /*mCButton.bringToFront();*/
        mCButton.setOnClickListener(this);
        mCButton.setOnLongClickListener(this);

        int l = 0;
        int t = 0;
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();

        //设置按钮显示的位置
        switch (mPosition) {
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
            default:
                break;
        }
        mCButton.layout(l - 60, t - 100, l + width - 60, t + height - 100);
    }

    @Override
    public void onClick(View view) {
        if (isOpen()) {
            mainClickListener.onClick();
            rotateCButton(view, -90, 0, 200);
            toggleMenu();
        } else {
            mainClickListener.onFinish();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (!isOpen()) {
            mainClickListener.onLongClick();
            rotateCButton(view, 0, -90, 200);
            toggleMenu();
        }
        return true;
    }

    /**
     * 切换菜单
     */
    public void toggleMenu() {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i);
            childView.setVisibility(VISIBLE);

            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            //根据菜单所处位置的不同，设置不同的参数值
            int xFlag = 1;
            int yFlag = 1;

            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                xFlag = -1;
            }

            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
                yFlag = -1;
            }

            //平移动画
            AnimationSet animationSet = new AnimationSet(true);
            Animation animation;

            if (mStatus == Status.CLOSE) {
                //打开按钮的动画
                animation = new TranslateAnimation(xFlag * cl - 00, -00, yFlag * ct - 00, -00);
                childView.setFocusable(true);
                childView.setClickable(true);
                animation.setDuration(300);
            } else {
                //关闭按钮的动画   xFlag * cl    yFlag * ct
                animation = new TranslateAnimation(-00, xFlag * cl - 00, -00, yFlag * ct - 00);
                childView.setFocusable(false);
                childView.setClickable(false);
                animation.setDuration(300);
            }

            animation.setFillAfter(true);
            animation.setStartOffset((i * 100) / count);

            //监听动画状态
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mStatus == Status.CLOSE) {
                        childView.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            //旋转动画
            RotateAnimation rotateAnimation = new RotateAnimation(360, 0,//控制菜单收回的时候选择角度
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setDuration(200);

            //添加动画
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(animation);
            childView.startAnimation(animationSet);

            //为子菜单项添加点击事件
            final int pos = i + 1;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener.onClick(childView, pos);
                        menuItemAnim(pos - 1);
                        changeStatus();
                        rotateCButton(mCButton, -90, 0, 200);
                    }
                }
            });
        }
        changeStatus();
    }

    /**
     * 点击子菜单项的动画
     */
    private void menuItemAnim(int pos) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View childView = getChildAt(i);

            if (i == pos) {
                childView.startAnimation(scaleBigAnim(200));
            } else {
                childView.startAnimation(scaleSmallAnim(200));
            }

            //设置子菜单隐藏
            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    /**
     * 使菜单项变小并消失的动画
     */
    private Animation scaleSmallAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 使菜单项变大，透明度降低的动画
     */
    private Animation scaleBigAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 改变菜单状态值
     */
    private void changeStatus() {
        mStatus = (mStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }

    /**
     * 判断菜单是否打开
     */
    public boolean isOpen() {
        return mStatus == Status.OPEN;
    }

    /**
     * 主按钮旋转动画
     */
    public void rotateCButton(View view, float start, float end, int duration) {
        RotateAnimation animation = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }
}