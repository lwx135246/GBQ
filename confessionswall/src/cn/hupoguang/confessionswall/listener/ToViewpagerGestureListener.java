package cn.hupoguang.confessionswall.listener;


import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.activity.PublishActivtiy;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;

public class ToViewpagerGestureListener implements android.view.GestureDetector.OnGestureListener {
	private Context context;
	private Intent intent;
	
	public ToViewpagerGestureListener(Context context) {
		this.context = context;
	}
	
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getY() - e2.getY() > 200) { // 手指上滑
			((Activity) context).finish();
			((Activity) context).overridePendingTransition(R.anim.push_up_in,
					R.anim.push_up_out);
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	
	

}
