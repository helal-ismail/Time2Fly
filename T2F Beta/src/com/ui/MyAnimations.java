package com.ui;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.ui.R;

public class MyAnimations {
	
	Context mContext;
	public static Animation mSlideInLeft;
	public static Animation mSlideOutRight;
	public static  Animation mSlideInRight;
	public static  Animation mSlideOutLeft;
	public static  Animation mFade;
	public static  Animation mSlideOutBottom;
	public static  Animation mSlideInBottom;
	public static  Animation mSlideOutTop;
	public static  Animation mSlideInTop;
	
	
	public MyAnimations (Context c){
		mContext = c;
		setUpAnimation();
	}
	
	
	
	public void setUpAnimation() {
		// animation
		mSlideInLeft = AnimationUtils.loadAnimation(mContext,
				R.anim.push_left_in);
		mSlideOutRight = AnimationUtils.loadAnimation(mContext,
				R.anim.push_right_out);
		mSlideInRight = AnimationUtils.loadAnimation(mContext,
				R.anim.push_right_in);
		mSlideOutLeft = AnimationUtils.loadAnimation(mContext,
				R.anim.push_left_out);

		mFade = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);

		mSlideOutBottom = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_out_bottom);

		mSlideInBottom = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_in_bottom);

		mSlideOutTop = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_out_top);

		mSlideInTop = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_in_top);
	}
	
	
	
	
	
	
	// Show right panel
	public void showRightPanel(View sidePanel, View sideTray){
		sidePanel.startAnimation(mSlideInLeft);
		sideTray.startAnimation(mFade);
		sidePanel.setVisibility(View.VISIBLE);
	}
	
	// Hide right Panel
	public void hideRightPanel(View sidePanel, View sideTray){
		sidePanel.startAnimation(mSlideOutLeft);
		sideTray.startAnimation(mFade);
		sidePanel.setVisibility(View.GONE);
	}


}
