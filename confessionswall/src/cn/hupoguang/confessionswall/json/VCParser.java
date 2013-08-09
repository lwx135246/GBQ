/**
 * VCParser.java
 * cn.hupoguang.confessionofwall.json
 * Function： 解析查看告白信息
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

import cn.hupoguang.confessionswall.bean.Confession;
import cn.hupoguang.confessionswall.util.ConfessionApplication;

/**
 * ClassName:VCParser
 * Function: 解析查看告白信息
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-10	下午3:45:38
 *
 */

public class VCParser extends BaseParser<Map<String,Object>> {

	@Override
	public Map<String,Object> parseJSON(String paramString) throws JSONException {
		
		Map<String,Object> mapResult = null;
		String result = checkResponse(paramString); 
		if(null != result && result.equals(ConfessionApplication.OK)){
			mapResult = new HashMap<String, Object>();
			List<Confession> list = new ArrayList<Confession>();
			
			JSONObject jsonObject = new JSONObject(paramString);
			mapResult.put("r0", jsonObject.getString("r0"));
			mapResult.put("r1", jsonObject.getString("r1"));
			mapResult.put("r2", jsonObject.getString("r2"));
			mapResult.put("r3", jsonObject.getString("r3"));
			JSONArray array = jsonObject.getJSONArray("r4");
			
			for(int i = 0;i<array.length();i++){
				JSONObject config = array.getJSONObject(i);
				Confession confession = new Confession();
				confession.setPublishUserName(config.getString("r4a"));
				confession.setReceiveUserName(config.getString("r4b"));
				confession.setConfessionId(config.getString("r4c"));
				confession.setConfessionContent(config.getString("r4d"));
				confession.setPraiseCount(config.getString("r4e"));
				confession.setConfessionTime(config.getString("r4f"));
				list.add(confession);
			}
			mapResult.put("r4", list);
		}
		
		return mapResult;
	}

}

