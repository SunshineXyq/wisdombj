package utils;

import android.content.Context;

public class CacheUtils {

	public static void setCache(String url,String json,Context ctx) {
		PreUtils.setString(ctx, url, json);
	}
	
	public static String getCache(String key,Context ctx) {
		return PreUtils.getString(ctx, key, "");
	}
}
