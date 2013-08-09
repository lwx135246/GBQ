/**
 * PCModel.java
 * cn.hupoguang.confessionofwall.model
 * Function： TODO
 *
 * date ：   2013-7-10
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
 */

package cn.hupoguang.confessionswall.model;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cn.hupoguang.confessionswall.bean.Confession;
import cn.hupoguang.confessionswall.thread.PublishConfessionCallable;
import cn.hupoguang.confessionswall.util.ThreadPoolManager;

/**
 * ClassName:PCModel Function: 数据处理
 * 
 * @author 李文响
 * @version 1.0
 * @Date 2013-7-10 下午4:50:24
 * 
 */

public class PCModel {

	/**
	 * publishConfession:(发布告白信息)
	 * 
	 * @param confession
	 *            封装的告白对象
	 * @param context
	 * @return String
	 * @author 李文响
	 * @date 2013-7-10 下午5:25:55
	 */
	public static String publishConfession(Confession confession) {
		Future<Object> future = ThreadPoolManager.getInstance().addCall(
				new PublishConfessionCallable(confession));
		String reString = "";
		try {
			if(future.get() == null){
				return "";
			}
			reString = future.get().toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return reString;
	}

}
