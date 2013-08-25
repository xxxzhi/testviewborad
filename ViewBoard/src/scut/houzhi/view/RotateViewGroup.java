/**
 * 以九宫格显示 ，将子view以随机旋转的方式，聚在一起
 */
package scut.houzhi.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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

		/**
		 * 
		 * @param toXDelta
		 * @param toYDelta
		 * @param xPivot  相对于控件left
		 * @param yPivot 相对于空间top
		 */
		public MyRotateAnimation(float toXDelta,
				float toYDelta,float xPivot,float yPivot){
			super(true);
			
			//初始化动画
			initAnimation(0, toXDelta, 0, toYDelta,xPivot,yPivot);
		}
		
		public MyRotateAnimation(float fromXDelta, float toXDelta,
				float fromYDelta, float toYDelta,float xPivot,float yPivot){
			super(true);
			
			//初始化动画
			initAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta,xPivot,yPivot);
		}
		
		private void initAnimation(float fromXDelta, float toXDelta,
				float fromYDelta, float toYDelta,float xPivot,float yPivot){
			TranslateAnimation translateAnimation 
				= new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
			
			translateAnimation.setDuration(durationMillis);
//			translateAnimation.setFillEnabled(true);
			translateAnimation.setFillAfter(true);
//			addAnimation(translateAnimation);
			
			float toDegrees = new Random().nextFloat()*360;
			RotateAnimation rotateAnimation = new RotateAnimation(0, toDegrees,xPivot,yPivot);
			
			translateAnimation.setDuration(durationMillis);
			translateAnimation.setFillAfter(true);
			addAnimation(rotateAnimation);
			setFillAfter(true);
			setFillEnabled(true);
		}

		@Override
		public void cancel() {
			// TODO Auto-generated method stub
//			super.cancel();
			for(Animation a:getAnimations()){
				a.cancel();
			}
		}
		
		
	}
	
	
	public boolean isOpened(){
		return isOpened;
	}
	private boolean isOpened = true;
	
	private List<Nums> list = new ArrayList<RotateViewGroup.Nums>();
	/**
	 * 将子View聚在一起
	 */
	public  void close(){
		if(!isOpened)
			return ;
		list.clear();
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
			
			
			Nums nums = new Nums();
			nums.transX = pivotX-(left+width);
			nums.transY = pivotY-(top+height);
			nums.pivotX = child.getWidth()/2;
			nums.pivotY = -child.getHeight()/2;
			myRotateAnimation =new MyRotateAnimation(nums.transX, nums.transY
					
					,nums.pivotX,nums.pivotY);
			list.add(nums);
			child.startAnimation(myRotateAnimation);
		}
		isOpened = false;
	}
	class Nums{
		float transX;
		float transY;
		float pivotX;
		float pivotY;
	}
	MyRotateAnimation myRotateAnimation ;
	/**
	 * 将子View散开
	 */
	public  void open(){
		if(isOpened)
			return ;
		System.out.println(myRotateAnimation==null?"animaiton isnull":"not null");
		if(myRotateAnimation!=null){
			myRotateAnimation.setFillBefore(true);
			myRotateAnimation.cancel();
		}
//		for(int i = 0;i!=viewGroup.getChildCount();++i){
//			View child = viewGroup.getChildAt(i);
//			child.clearAnimation();
//		}
		
		for(int i = 0;i!=viewGroup.getChildCount();++i){
			View child = viewGroup.getChildAt(i);
			
			Nums nums = list.get(i);
			MyRotateAnimation myRotateAnimation =new MyRotateAnimation(
					nums.transX, 0,
					nums.transY,0
					
					,nums.pivotX,nums.pivotY);
			child.startAnimation(myRotateAnimation);
		}
		isOpened = true;
	}
}
