package cn.hupoguang.confessionswall.handler;

import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import cn.hupoguang.confessionswall.bean.AppTheme;

public class LoadThemeHandler extends Handler {
	public static final int LOAD_TEXT=0;	//初始化文本信息
	public static final int LOAD_IMAGE=1;	//初始化图片信息
	
	private IHandlerCallback callback;
	public LoadThemeHandler(IHandlerCallback callback){
		this.callback=callback;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch (msg.what) {
		case 0:
			AppTheme theme = (AppTheme) msg.obj;
			callback.loadTheme(theme, null);
			break;
		case 1:
			BitmapDrawable bd = (BitmapDrawable) msg.obj;
			callback.loadTheme(null, bd);
			break;

		}
	}
	
}
