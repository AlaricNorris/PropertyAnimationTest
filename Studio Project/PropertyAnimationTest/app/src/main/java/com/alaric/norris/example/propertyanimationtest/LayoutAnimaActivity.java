package com.alaric.norris.example.propertyanimationtest;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LayoutAnimaActivity extends Activity implements OnCheckedChangeListener {

    private GridLayout mGridLayout;

    private int mVal;

    private LayoutTransition mTransition;

    private CheckBox mAppear, mChangeAppear, mDisAppear, mChangeDisAppear;

    @InjectView(R.id.id_container)
    LinearLayout mLinearLayout;


    @InjectView(R.id.text)
    TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animator);
        ButterKnife.inject(this);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.id_container);
        mAppear = (CheckBox) findViewById(R.id.id_appear);
        mChangeAppear = (CheckBox) findViewById(R.id.id_change_appear);
        mDisAppear = (CheckBox) findViewById(R.id.id_disappear);
        mChangeDisAppear = (CheckBox) findViewById(R.id.id_change_disappear);
        mAppear.setOnCheckedChangeListener(this);
        mChangeAppear.setOnCheckedChangeListener(this);
        mDisAppear.setOnCheckedChangeListener(this);
        mChangeDisAppear.setOnCheckedChangeListener(this);
        // 创建一个GridLayout
        mGridLayout = new GridLayout(this);
        // 设置每列5个按钮
        mGridLayout.setColumnCount(5);
        mGridLayout.setRowCount(5);
        // 添加到布局中
        viewGroup.addView(mGridLayout);
        // 默认动画全部开启
        mTransition = new LayoutTransition();
        mTransition.setAnimator(LayoutTransition.APPEARING,
                (mAppear.isChecked() ? ObjectAnimator.ofFloat(this, "scaleX", 0, 1) : null));
        mGridLayout.setLayoutTransition(mTransition);
        Spannable span = new SpannableString(mTextView.getText());
        span.setSpan(new AbsoluteSizeSpan(58), 11, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.RED), 11, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new BackgroundColorSpan(Color.YELLOW), 11, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(span);
        anim = AnimatorInflater.loadAnimator(this, R.animator.nrs_scroll_y);
        anim.setTarget(mLinearLayout);
        anim.start();
    }

    Animator anim;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (anim != null && anim.isPaused()) {
                anim.resume();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
            if (anim.isRunning()) {
                anim.pause();
            }
    }

    public void addBtn(View view) {
        final Button button = new Button(this);
        button.setText((++mVal) + "");
        mGridLayout.addView(button, Math.min(1, mGridLayout.getChildCount()));
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				mGridLayout.removeView(button);
                ((Button) v).setText((++mVal) + "");
                runAnimation(v);
            }
        });
    }

    private void runAnimation(View inView) {
//		code2ExecuteAnimation(inView);
        xml2ExecuteAnimation(inView);
    }

    public void rotateY(View inView) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f,
                1.2f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f,
                1.2f, 1f);
        LinearLayout parent = (LinearLayout) inView.getParent();
        for (int i = 0; i < parent.getChildCount(); i++) {
            ObjectAnimator.ofPropertyValuesHolder(parent.getChildAt(i), pvhX, pvhY).setDuration(200).start();
        }


    }


    private void xml2ExecuteAnimation(View inView) {
        Animator anim = AnimatorInflater.loadAnimator(this, R.animator.nrs_rotate_y_scale);
        anim.setTarget(inView);
        anim.start();
    }

    private void code2ExecuteAnimation(View inView) {
        ObjectAnimator mAnimator_RotateY = ObjectAnimator.ofFloat(inView, "rotationY", 0.0F,
                360.0F);
        mAnimator_RotateY.setDuration(100);
        ObjectAnimator mAnimator_ScaleX = ObjectAnimator.ofFloat(inView, "scaleX", 1.0f, 1.2f);
        mAnimator_ScaleX.setDuration(50);
        ObjectAnimator mAnimator_ScaleX_Reverse = ObjectAnimator.ofFloat(inView, "scaleX", 1.2f,
                1.0f);
        mAnimator_ScaleX_Reverse.setDuration(50);
        ObjectAnimator mAnimator_ScaleY = ObjectAnimator.ofFloat(inView, "scaleY", 1.0f, 1.2f);
        mAnimator_ScaleY.setDuration(50);
        ObjectAnimator mAnimator_ScaleY_Reverse = ObjectAnimator.ofFloat(inView, "scaleY", 1.2f,
                1.0f);
        mAnimator_ScaleY_Reverse.setDuration(50);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setInterpolator(new LinearInterpolator());
        //两个动画同时执行
//		animSet.playTogether(mAnimator_ScaleX , mAnimator_RotateY) ;
        animSet.playTogether(mAnimator_ScaleX, mAnimator_ScaleY);
        animSet.playSequentially(mAnimator_ScaleX, mAnimator_ScaleX_Reverse);
        animSet.playTogether(mAnimator_ScaleX_Reverse, mAnimator_ScaleY_Reverse);
        animSet.start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mTransition = new LayoutTransition();
        mTransition.setAnimator(LayoutTransition.APPEARING,
                (mAppear.isChecked() ? ObjectAnimator.ofFloat(this, "scaleX", 0, 1) : null));
        mTransition.setAnimator(
                LayoutTransition.CHANGE_APPEARING,
                (mChangeAppear.isChecked() ? mTransition
                        .getAnimator(LayoutTransition.CHANGE_APPEARING) : null));
        mTransition.setAnimator(LayoutTransition.DISAPPEARING,
                (mDisAppear.isChecked() ? mTransition.getAnimator(LayoutTransition.DISAPPEARING)
                        : null));
        mTransition.setAnimator(
                LayoutTransition.CHANGE_DISAPPEARING,
                (mChangeDisAppear.isChecked() ? mTransition
                        .getAnimator(LayoutTransition.CHANGE_DISAPPEARING) : null));
        mGridLayout.setLayoutTransition(mTransition);
    }
}
