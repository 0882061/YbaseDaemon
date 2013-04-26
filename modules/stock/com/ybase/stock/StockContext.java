/**
 * Created By: Comwave Project Team
 * Created Date: 2012-11-28 下午2:23:40
 */
package com.ybase.stock;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ybase.stock.exception.StockException;

/**
 * @author Tom Yang
 * @version 1.0
 */
public class StockContext {

	private static final Log logger = LogFactory.getLog(StockContext.class);

	private static final String STOCK_CONNECT_CONFIG_FILE = "/stock.properties";
	private static final String STOCK_BASE_URL = "base.url";
	private static final String STOCK_BASE_PARAM = "base.param";
	private static final String STOCK_BASE_SAVE_DIR = "base.save.dir";

	public static final String IS_SAVE_FILE = "isSaveFile";
	public static final String SAVE_FILE_YES = "true";
	public static final String SAVE_FILE_NO = "false";

	private static Map<String, Object> fileCache = new HashMap<String, Object>();

	private static Properties config;

	static {
		try {
			InputStream is = StockContext.class.getResourceAsStream(STOCK_CONNECT_CONFIG_FILE);
			config = new Properties();
			config.load(is);

			logger.info("init configure file:" + STOCK_CONNECT_CONFIG_FILE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static String getConfig(String key) throws StockException {
		String value = (String) config.getProperty(key);
		if (value == null) {
			logger.debug(key + " isn't exist");
		}
		logger.debug("key = " + key + ", value=" + value);
		return value;
	}

	public static String getBaseURL() throws StockException {
		return getConfig(STOCK_BASE_URL);
	}

	public static String getBaseParam() throws StockException {
		return getConfig(STOCK_BASE_PARAM);
	}

	public static String getBaseSaveDir() throws StockException {
		return getConfig(STOCK_BASE_SAVE_DIR);
	}

	public static Object getCacheValue(String key) {
		return fileCache.get(key);
	}

	public static void addCacheObject(String key, Object cahceObj) {
		fileCache.put(key, cahceObj);
	}

	public static void clearCache() {
		fileCache.clear();
	}
}
