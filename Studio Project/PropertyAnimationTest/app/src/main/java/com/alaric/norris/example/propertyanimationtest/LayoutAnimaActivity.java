package com.alaric.norris.example.propertyanimationtest ;

import android.animation.Animator ;
import android.animation.AnimatorInflater ;
import android.animation.AnimatorSet ;
import android.animation.LayoutTransition ;
import android.animation.ObjectAnimator ;
import android.app.Activity ;
import android.os.Bundle ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.view.ViewGroup ;
import android.view.animation.LinearInterpolator ;
import android.widget.Button ;
import android.widget.CheckBox ;
import android.widget.CompoundButton ;
import android.widget.CompoundButton.OnCheckedChangeListener ;
import android.widget.GridLayout ;

public class LayoutAnimaActivity extends Activity implements OnCheckedChangeListener {

	private GridLayout mGridLayout ;

	private int mVal ;

	private LayoutTransition mTransition ;

	private CheckBox mAppear , mChangeAppear , mDisAppear , mChangeDisAppear ;

	@ Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.layout_animator) ;
		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.id_container) ;
		mAppear = (CheckBox) findViewById(R.id.id_appear) ;
		mChangeAppear = (CheckBox) findViewById(R.id.id_change_appear) ;
		mDisAppear = (CheckBox) findViewById(R.id.id_disappear) ;
		mChangeDisAppear = (CheckBox) findViewById(R.id.id_change_disappear) ;
		mAppear.setOnCheckedChangeListener(this) ;
		mChangeAppear.setOnCheckedChangeListener(this) ;
		mDisAppear.setOnCheckedChangeListener(this) ;
		mChangeDisAppear.setOnCheckedChangeListener(this) ;
		// 创建一个GridLayout
		mGridLayout = new GridLayout(this) ;
		// 设置每列5个按钮
		mGridLayout.setColumnCount(5) ;
		mGridLayout.setRowCount(5) ;
		// 添加到布局中
		viewGroup.addView(mGridLayout) ;
		// 默认动画全部开启
		mTransition = new LayoutTransition() ;
		mTransition.setAnimator(LayoutTransition.APPEARING ,
				(mAppear.isChecked() ? ObjectAnimator.ofFloat(this , "scaleX" , 0 , 1) : null)) ;
		mGridLayout.setLayoutTransition(mTransition) ;
	}

	/**
	 * 添加按钮
	 *
	 * @param view
	 */
	public void addBtn(View view) {
		final Button button = new Button(this) ;
		button.setText( ( ++ mVal) + "") ;
		mGridLayout.addView(button , Math.min(1 , mGridLayout.getChildCount())) ;
		button.setOnClickListener(new OnClickListener() {

			@ Override
			public void onClick(View v) {
//				mGridLayout.removeView(button);
				((Button) v).setText( ( ++ mVal) + "") ;
				runAnimation(v) ;
			}
		}) ;
	}

	private void runAnimation(View inView) {
//		code2ExecuteAnimation(inView);
		Animator anim = AnimatorInflater.loadAnimator(this , R.animator.rotate_y_scale) ;
		anim.setTarget(inView) ;
		anim.start() ;
	}

	private void code2ExecuteAnimation(View inView) {
		ObjectAnimator mAnimator_RotateY = ObjectAnimator.ofFloat(inView , "rotationY" , 0.0F ,
				360.0F) ;
		mAnimator_RotateY.setDuration(100) ;
		ObjectAnimator mAnimator_ScaleX = ObjectAnimator.ofFloat(inView , "scaleX" , 1.0f , 1.2f) ;
		mAnimator_ScaleX.setDuration(50) ;
		ObjectAnimator mAnimator_ScaleX_Reverse = ObjectAnimator.ofFloat(inView , "scaleX" , 1.2f ,
				1.0f) ;
		mAnimator_ScaleX_Reverse.setDuration(50) ;
		ObjectAnimator mAnimator_ScaleY = ObjectAnimator.ofFloat(inView , "scaleY" , 1.0f , 1.2f) ;
		mAnimator_ScaleY.setDuration(50) ;
		ObjectAnimator mAnimator_ScaleY_Reverse = ObjectAnimator.ofFloat(inView , "scaleY" , 1.2f ,
				1.0f) ;
		mAnimator_ScaleY_Reverse.setDuration(50) ;
		AnimatorSet animSet = new AnimatorSet() ;
		animSet.setInterpolator(new LinearInterpolator()) ;
		//两个动画同时执行
//		animSet.playTogether(mAnimator_ScaleX , mAnimator_RotateY) ;
		animSet.playTogether(mAnimator_ScaleX , mAnimator_ScaleY) ;
		animSet.playSequentially(mAnimator_ScaleX , mAnimator_ScaleX_Reverse) ;
		animSet.playTogether(mAnimator_ScaleX_Reverse , mAnimator_ScaleY_Reverse) ;
		animSet.start() ;
	}

	@ Override
	public void onCheckedChanged(CompoundButton buttonView , boolean isChecked) {
		mTransition = new LayoutTransition() ;
		mTransition.setAnimator(LayoutTransition.APPEARING ,
				(mAppear.isChecked() ? ObjectAnimator.ofFloat(this , "scaleX" , 0 , 1) : null)) ;
		mTransition.setAnimator(
				LayoutTransition.CHANGE_APPEARING ,
				(mChangeAppear.isChecked() ? mTransition
						.getAnimator(LayoutTransition.CHANGE_APPEARING) : null)) ;
		mTransition.setAnimator(LayoutTransition.DISAPPEARING ,
				(mDisAppear.isChecked() ? mTransition.getAnimator(LayoutTransition.DISAPPEARING)
						: null)) ;
		mTransition.setAnimator(
				LayoutTransition.CHANGE_DISAPPEARING ,
				(mChangeDisAppear.isChecked() ? mTransition
						.getAnimator(LayoutTransition.CHANGE_DISAPPEARING) : null)) ;
		mGridLayout.setLayoutTransition(mTransition) ;
	}
}
