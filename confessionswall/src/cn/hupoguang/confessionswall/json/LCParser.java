/**
 * LCParser.java
 * cn.hupoguang.confessionofwall.json
 * Function： 解析赞功能
 *
 * date ：   2013-7-10
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.json;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hupoguang.confessionswall.util.ConfessionApplication;

/**
 * ClassName:LCParser
 * Function: 赞功能解析
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-10	下午4:12:09
 *
 */

public class LCParser extends BaseParser<Map<String,String>> {

	@Override
	public Map<String,String> parseJSON(String paramString) throws JSONException {
		
		Map<String,String> mapResult = null;
		String result = checkResponse(paramString);
		
		if(null != result && result.equals(ConfessionApplication.OK)){
			mapResult = new HashMap<String, String>();
			JSONObject jsonObject = new JSONObject(paramString);
			mapResult.put("r0", jsonObject.getString("r0"));
			mapResult.put("r1", jsonObject.getString("r1"));
			mapResult.put("r2", jsonObject.getString("r2"));
		}
		
		return mapResult;
		
	}

}

