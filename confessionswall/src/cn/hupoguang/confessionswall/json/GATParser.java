/**
 * GATParser.java
 * cn.hupoguang.confessionofwall.json
 * Function：解析主题信息
 *
 * date ：   2013-7-10
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
 */

package cn.hupoguang.confessionswall.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.hupoguang.confessionswall.bean.AppTheme;
import cn.hupoguang.confessionswall.util.ConfessionApplication;

/**
 * ClassName:GATParser Function: 解析主题信息
 * 
 * @author 李文响
 * @version 1.0
 * @Date 2013-7-10 下午4:40:19
 * 
 */

public class GATParser extends BaseParser<Map<String, Object>> {

	@Override
	public Map<String, Object> parseJSON(String paramString) throws JSONException {

		Map<String, Object> mapResult = null;
		String result = checkResponse(paramString);
		if (null != result && result.equals(ConfessionApplication.OK)) {
			mapResult = new HashMap<String, Object>();
			List<AppTheme> list = new ArrayList<AppTheme>();
			JSONObject jsonObject = new JSONObject(paramString);
			mapResult.put("r0", jsonObject.getString("r0"));
			mapResult.put("r1", jsonObject.getString("r1"));
			mapResult.put("r2", jsonObject.getString("r2"));
			JSONArray array = jsonObject.getJSONArray("r3");
			for (int i = 0; i < array.length(); i++) {
				JSONObject config = array.getJSONObject(i);
				AppTheme appTheme = new AppTheme();
				appTheme.setImagePath(config.getString("r3a"));
				appTheme.setDailyContext(config.getString("r3b"));
				appTheme.setColor(config.getString("r3c"));
				appTheme.setDailyEngContext(config.getString("r3d"));
				list.add(appTheme);
			}
			mapResult.put("r3", list);

		}
		return mapResult;
	}

}
