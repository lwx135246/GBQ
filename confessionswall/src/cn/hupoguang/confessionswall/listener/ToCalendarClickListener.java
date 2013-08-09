package cn.hupoguang.confessionswall.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import cn.hupoguang.confessionswall.activity.CalendarActivity;

public class ToCalendarClickListener implements OnClickListener {
	public Context context;
	public ToCalendarClickListener(Context context) {
		this.context=context;
	}
	@Override
	public void onClick(View arg0) {
		Intent intent=new Intent(context,CalendarActivity.class);
		((Activity)context).startActivityForResult(intent, 0);
	}

}
