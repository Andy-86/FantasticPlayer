package com.example.andy.player.weight;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.andy.player.R;

/**
 * Created by andy on 2017/9/9.
 */

public class DisplayLayout extends RelativeLayout {
    private static final String TAG="DisplayLayout";
    private final int ANIMATION_DURATION = 500;
    private final int INDEX_BACKGROUND = 0;
    private final int INDEX_FOREGROUND = 1;
    /**
     * LayerDrawable[0]: background drawable
     * LayerDrawable[1]: foreground drawable
     */
    private LayerDrawable layerDrawable;
    private ObjectAnimator objectAnimator;
    public DisplayLayout(Context context) {
        super(context);
        Log.d(TAG, "DisplayLayout: 1");
    }

    /**
     * 通过findviewById是调用两个构造函数的
     * @param context
     * @param attrs
     */
    public DisplayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBaground();
        initAnimotion();
        Log.d(TAG, "DisplayLayout: 2");
    }

    public DisplayLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "DisplayLayout: 3");
    }
    private void initBaground()
    {
        //设置默认的底部图片
        Drawable backgroundDrawable = getContext().getDrawable(R.drawable.ic_blackground);
        Drawable[] drawables = new Drawable[2];

        /*初始化时先将前景与背景颜色设为一致*/
        drawables[INDEX_BACKGROUND] = backgroundDrawable;
        drawables[INDEX_FOREGROUND] = backgroundDrawable;

        layerDrawable = new LayerDrawable(drawables);
    }
    /**
     * 这里传入的number就是代表这个view必须要有一个setNumber的方法，通过反射来实现
     * 所以这里就不用使用addUpdateListner来实现了
     */
    private void initAnimotion()
    {

        objectAnimator = ObjectAnimator.ofFloat(this, "number", 0f, 1.0f);
        objectAnimator.setDuration(ANIMATION_DURATION);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /*动画结束后，记得将原来的背景图及时更新*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    layerDrawable.setDrawable(INDEX_BACKGROUND, layerDrawable.getDrawable(
                            INDEX_FOREGROUND));
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    public void initAll()
    {
        initAnimotion();
        initBaground();
    }
    //对外提供方法，用于开始渐变动画
    public void beginAnimation() {
        if(objectAnimator==null)
        {
            initBaground();
            initAnimotion();
        }
        objectAnimator.start();
    }
    public void setForeground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layerDrawable.setDrawable(INDEX_FOREGROUND, drawable);
        }
    }
    public void setNumber(float arg)
    {
        int foregroundAlpha = (int) (arg * 255);
                /*动态设置Drawable的透明度，让前景图逐渐显示*/
        layerDrawable.getDrawable(INDEX_FOREGROUND).setAlpha(foregroundAlpha);
        this.setBackground(layerDrawable);
    }
}
