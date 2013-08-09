/**
 * @FILE:DialogHelp.java
 * @AUTHOR:李文响
 * @DATE:2013-5-22 上午10:47:39
 */
package cn.hupoguang.confessionswall.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.WindowManager;

/*******************************************
 * @CLASS:DialogHelp
 * @DESCRIPTION:	
 * @AUTHOR:李文响
 * @VERSION:v1.0
 * @DATE:2013-5-22 上午10:47:39
 *******************************************/
public class DialogHelp {

	private Context context;
	AlertDialog.Builder dialog;
	public DialogHelp(Context context){
		this.context = context;
	}
	
	/**
	 * @description: 弹出对话框
	 * @author:李文响
	 * @return:void
	 * @param title 标题
	 * @param message 信息
	 * @param positiveText 确定按钮显示的文本
	 * @param negativeText 取消按钮显示的文本
	 * @param positiveListener 确定按钮监听器
	 * @param negativeListener 取消按钮监听器
	 */
	
	public void showDialog(String title,String message, String positiveText,
			String negativeText,OnClickListener  positiveListener,OnClickListener negativeListener) {
		dialog = new Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		if(null != positiveListener){ 
			dialog.setPositiveButton(positiveText, positiveListener);
		}
		if(null != negativeListener){
			dialog.setNegativeButton(negativeText, negativeListener);
		} 
		AlertDialog mDialog=dialog.create();   
		mDialog.setCancelable(false);
        mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//设定为系统级警告，关键   
        mDialog.show();   
	}
	
	
}

