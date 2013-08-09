package cn.hupoguang.confessionswall.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.activity.PublishActivtiy;

public class ToPublishGestureListener implements OnGestureListener {
	public ToPublishGestureListener(Context context) {
		this.context = context;
	}

	private Context context;
	private Intent intent;

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int k = (int) ((e1.getY() - e2.getY()) / (e1.getX() - e2.getX()));
		if (Math.abs(k) < 5) {
			return false;
		}
 
		if (e1.getY() - e2.getY() < -200) { // 手指下滑
			intent = new Intent(context, PublishActivtiy.class);
			context.startActivity(intent);
			((Activity) context).overridePendingTransition(R.anim.push_down_in,
					R.anim.push_down_out);
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
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
