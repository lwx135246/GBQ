/**
 * ConfessionController.java
 * cn.hupoguang.confessionofwall.controller
 * Function： TODO
 *
 * date ：   2013-7-11
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.controller;

import java.util.Map;

import org.json.JSONException;

import cn.hupoguang.confessionswall.bean.Confession;
import cn.hupoguang.confessionswall.json.GATParser;
import cn.hupoguang.confessionswall.json.LCParser;
import cn.hupoguang.confessionswall.json.PCParser;
import cn.hupoguang.confessionswall.json.VCParser;
import cn.hupoguang.confessionswall.model.GATModel;
import cn.hupoguang.confessionswall.model.LCModel;
import cn.hupoguang.confessionswall.model.PCModel;
import cn.hupoguang.confessionswall.model.VCModel;

/**
 * ClassName:ConfessionController
 * Function: 数据处理
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-11	上午10:16:01
 *
 */

public class ConfessionController {
	/**
	 * publishConfession:(发告白信息)
	 * @param confession
	 * @return
	 * @author   李文响
	 * @date 2013-7-11  上午10:21:14
	 */
	public String publishConfession(Confession confession){
		String paramString = PCModel.publishConfession(confession);
		PCParser parser = new PCParser();
		String result = null;
		try {
			result = parser.parseJSON(paramString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * viewConfessions:(查看告白信息)
	 * @param confessionTime 告白时间
	 * @param page 请求页数
	 * @return Map<String,Object>
	 * @author   李文响
	 * @date 2013-7-11  上午10:53:40
	 */
	public Map<String,Object> viewConfessions(String confessionTime,Integer page){
		
		String paramString = VCModel.viewConfession(confessionTime,page);
		VCParser parser = new VCParser();
		Map<String,Object> result = null;
		try {
			result = parser.parseJSON(paramString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * likeConfession:(赞告白)
	 * @param likeUserName 赞的用户名称
	 * @param confessionId 被赞的告白ID
	 * @return Map<String,String>
	 * @author   李文响
	 * @date 2013-7-11  上午11:06:14
	 */
	public Map<String,String> likeConfession(String likeUserName,String confessionId){
		
		String paramString = LCModel.LikeConfession(likeUserName,confessionId);
		LCParser parser = new LCParser();
		Map<String,String> result = null;
		try {
			result = parser.parseJSON(paramString);
		} catch (JSONException e) {
			e.printStackTrace();
			
		}
		return result;
	}
	
	/**
	 * getAppThemes:(获取所有APP主题)
	 * @return Map<String,Object>
	 * @author   李文响
	 * @date 2013-7-11  上午11:12:39
	 */
	public Map<String,Object> getAppThemes(){
		String paramString = GATModel.getAppThemes();
		GATParser parser = new GATParser();
		Map<String,Object> result = null;
		try {
			result = parser.parseJSON(paramString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * getAppTheme:(根据日期获得某个主题)
	 * @param date 日期
	 * @return Map<String,Object>
	 * @author   李文响
	 * @date 2013-7-17  下午3:41:03
	 */
	public Map<String,Object> getAppTheme(String date){
		String paramString = GATModel.getDayTheme(date);
		GATParser parser = new GATParser();
		Map<String,Object> result = null;
		try {
			result = parser.parseJSON(paramString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	} 
}

