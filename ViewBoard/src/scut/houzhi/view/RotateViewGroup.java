/**
 * 以九宫格显示 ，将子view以随机旋转的方式，聚在一起
 */
package scut.houzhi.view;

import java.util.Random;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class RotateViewGroup {
	private ViewGroup viewGroup ;
	
	
	public RotateViewGroup(ViewGroup viewGroup){
		this.viewGroup = viewGroup;
	}
	/**
	 * 旋转动画
	 * @author houzhi
	 *
	 */
	static class MyRotateAnimation extends AnimationSet{
		private final long durationMillis = 1000;

		
		public MyRotateAnimation(float toXDelta,
				float toYDelta){
			super(true);
			
			//初始化动画
			initAnimation(0, toXDelta, 0, toYDelta);
		}
		
		private void initAnimation(float fromXDelta, float toXDelta,
				float fromYDelta, float toYDelta){
			TranslateAnimation translateAnimation 
				= new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
			
			translateAnimation.setDuration(durationMillis);
			translateAnimation.setFillEnabled(true);
			translateAnimation.setFillAfter(true);
			addAnimation(translateAnimation);
			
			
			float toDegrees = new Random().nextFloat()*360;
			RotateAnimation rotateAnimation = new RotateAnimation(0, toDegrees);
			translateAnimation.setDuration(durationMillis);
			translateAnimation.setFillAfter(true);
			addAnimation(rotateAnimation);
			setFillAfter(true);
			setFillEnabled(true);
		}
		
	}
	
	
	public boolean isOpened(){
		return isOpened;
	}
	private boolean isOpened = true;
	/**
	 * 将子View聚在一起
	 */
	public  void close(){
		if(!isOpened)
			return ;
		isOpened = false;
		for(int i = 0;i!=viewGroup.getChildCount();++i){
			View child = viewGroup.getChildAt(i);
			//左边
			int left = child.getLeft();
			//右边
			int top = child.getTop();
			
			int width = child.getWidth()/2;
			int height = child.getHeight()/2;
			
			//中点
			int pivotX = viewGroup.getWidth()/2;
			int pivotY = viewGroup.getHeight()/2;
			
			child.startAnimation(new MyRotateAnimation(pivotX-(left+width), pivotY-(top+height)));
		}
	}
	
	
	/**
	 * 将子View散开
	 */
	public  void open(){
		if(isOpened)
			return ;
		isOpened = true;
		for(int i = 0;i!=viewGroup.getChildCount();++i){
			View child = viewGroup.getChildAt(i);
			child.clearAnimation();
		}
	}
}
