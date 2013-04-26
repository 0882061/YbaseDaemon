/**
 * Created By: Comwave Project Team
 * Created Date: 2012-11-28 下午2:58:51
 */
package com.ybase.stock.util;

import java.net.HttpURLConnection;
import java.net.URL;

import com.ybase.stock.exception.StockException;

/**
 * @author Tom Yang
 * @version 1.0
 */
public class URLConnectUtil {
	public static HttpURLConnection getHTTPURLConnection(String connectURL) throws StockException {
		HttpURLConnection httpURLConn = null;
		try {
			URL url = new URL(connectURL);
			httpURLConn = (HttpURLConnection) url.openConnection();
			httpURLConn.connect();
		} catch (Exception e) {
			throw new StockException("cann't connect " + connectURL);
		}
		return httpURLConn;
	}

	public static void closeHttpURLConnection(HttpURLConnection httpURLConn) {
		httpURLConn.disconnect();
	}
}
