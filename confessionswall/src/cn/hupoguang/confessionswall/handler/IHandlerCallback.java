package cn.hupoguang.confessionswall.handler;

import android.graphics.drawable.BitmapDrawable;
import cn.hupoguang.confessionswall.bean.AppTheme;

/**
 * 回调
 * @author Administrator
 *
 */
public interface IHandlerCallback {
	void loadTheme(AppTheme appTheme,BitmapDrawable bitmap);
}
