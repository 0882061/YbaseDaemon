/**
 * Created By: YBASE
 * Created Date: 2013-4-26 下午5:13:57
 * Author: Tom Yang
 */
package com.ybase.stock.util;

/**
 * @author Tom Yang
 * @version 1.0
 */
public class StringUtil {

	public static boolean isNotNullOrBlank(String dest) {
		if (dest != null && !"".equals(dest.trim())) {
			return true;
		}
		return false;
	}
}
