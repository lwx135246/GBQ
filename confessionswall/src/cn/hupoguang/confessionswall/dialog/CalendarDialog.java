package cn.hupoguang.confessionswall.dialog;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.text.format.Time;

public class CalendarDialog extends Builder {

	
	public CalendarDialog(Context context) {
		super(context);
		init();
	}

	String[] yearItems;

	void init() {
		
		Time time = new Time();
		time.setToNow();
		int year = time.year;// 获取当前时间
		this.setTitle("年");
		yearItems = new String[80];// 添加80年的2013-1933
		for (int i = 0; i < yearItems.length; i++) {
			yearItems[i] = year - i + "";
		}
		 
	}
}
