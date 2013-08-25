/**
 * 以九宫格显示 ，将子view以随机旋转的方式，聚在一起
 */
package scut.houzhi.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

public class RotateViewGroup {
	private ViewGroup viewGroup ;
	private Random random = new Random(500);
	
	public RotateViewGroup(ViewGroup viewGroup,boolean init){
		this.viewGroup = viewGroup;
		this.isOpened = init;
	}
	/**
	 * 旋转动画
	 * @author houzhi
	 *
	 */
	 class MyRotateAnimation extends AnimationSet{
		private final long durationMillis = 1000;

		/**
		 * 
		 * @param toXDelta
		 * @param toYDelta
		 * @param xPivot  相对于控件left
		 * @param yPivot 相对于空间top
		 */
		public MyRotateAnimation(float toXDelta,
				float toYDelta,int xPivot,int yPivot){
			super(true);
			
			//初始化动画
			initAnimation(0, toXDelta, 0, toYDelta,xPivot,yPivot);
		}
		
		public MyRotateAnimation(float fromXDelta, float toXDelta,
				float fromYDelta, float toYDelta,int xPivot,int yPivot){
			super(true);
			
			//初始化动画
			initAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta,xPivot,yPivot);
		}
		
		private void initAnimation(float fromXDelta, float toXDelta,
				float fromYDelta, float toYDelta,int xPivot,int yPivot){
			TranslateAnimation translateAnimation 
				= new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
			
			addAnimation(translateAnimation);
			
			int toDegrees = random.nextInt(360);
			Log.i(TAG,toDegrees+"");
			RotateAnimation rotateAnimation = 
					new RotateAnimation(0, toDegrees,
							RotateAnimation.RELATIVE_TO_SELF,0.5f,
							RotateAnimation.RELATIVE_TO_SELF,0.5f);
			addAnimation(rotateAnimation);
			
			setFillAfter(true);
			setDuration(durationMillis);
//			setFillEnabled(true);
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
	
	private static final String TAG = "rotateViewGroup";
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
		int pivotX;
		int pivotY;
	}
	MyRotateAnimation myRotateAnimation ;
	/**
	 * 将子View散开
	 */
	public  void open(){
		if(isOpened)
			return ;
		System.out.println(myRotateAnimation==null?"animaiton isnull":"not null");
		
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
