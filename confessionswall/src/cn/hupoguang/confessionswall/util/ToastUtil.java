/**
 * ToastUtil.java
 * cn.hupoguang.confessionswall.util
 * Function： TODO
 *
 * date ：   2013-7-18
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.util;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.Toast;

/**
 * ClassName:ToastUtil
 * Function: TODO ADD FUNCTION
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-18	下午3:58:38
 *
 */

public class ToastUtil extends Toast {

	private Context context;
	
	public ToastUtil(Context context) {
		
		super(context);
		this.context = context;
		
	}

	public void showToast(CharSequence text,int duration){
		Toast.makeText(context, text, duration).show();
	}
	
	public  void showToast(View view ,int duration){
		setView(view);
		setDuration(duration);
		show();
	}
	
	

}

