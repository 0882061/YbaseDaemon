/**
 * Created By: Comwave Project Team
 * Created Date: 2012-11-28 下午2:05:42
 */
package com.ybase.stock.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ybase.stock.StockContext;
import com.ybase.stock.domain.Stock;
import com.ybase.stock.exception.StockException;
import com.ybase.stock.service.StockService;
import com.ybase.stock.util.StockUtil;
import com.ybase.stock.util.URLConnectUtil;

/**
 * @author Tom Yang
 * @version 1.0
 */
public class StockServiceImpl extends HibernateDaoSupport implements StockService {
	private static final Log logger = LogFactory.getLog(StockServiceImpl.class);
	private static final int BUFFER_MAX_SIZE = 2048;

	private String baseURL;
	private String baseParam;
	private String baseSaveDir;

	public void init() throws StockException {
		baseURL = StockContext.getBaseURL();
		baseParam = StockContext.getBaseParam();
		baseSaveDir = StockContext.getBaseSaveDir();
	}

	/**
	 * 連接URL指定數據接口，取得數據保存至DB中
	 */
	public synchronized void stock() {
		String destURL = baseURL + baseParam;
		try {
			String downStr = downLoadFileToSave(destURL, baseSaveDir);
			if (StockContext.SAVE_FILE_NO.equals(StockContext.getCacheValue(StockContext.IS_SAVE_FILE))) {
				List<Stock> stocks = new ArrayList<Stock>();
				StockUtil.warpRecord(downStr, stocks);
				if (stocks != null && stocks.size() > 0) {
					saveStocks(stocks);
					logger.info("URL(" + destURL + ")data to save DB success!");
				} else {
					logger.info("no record saved!");
				}
			} else {
				parseFileToDB(StockContext.getCacheValue("cvspath"));
				logger.info("parse file(" + StockContext.getCacheValue("cvspath") + ") data to save DB success!");
			}
			StockContext.clearCache();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 解析parseFilePath指定文件，并將解析數據保存至DB
	 * 
	 * @param parseFilePath
	 *            文件解析路經
	 * @throw 當接文件錯誤時，拋出異常
	 */
	private void parseFileToDB(Object parseFilePath) throws Exception {
		String path = (String) parseFilePath;
		List<Stock> stocks = parseFile(path);
		if (stocks != null && stocks.size() > 0) {
			saveStocks(stocks);
		} else {
			logger.debug("no record saved");
		}
		logger.debug("get file(path:" + baseSaveDir + ") data to save DB");
	}

	/**
	 * 保存stock列表
	 * 
	 * @param stock
	 *            列表
	 */
	private void saveStocks(List<Stock> stocks) {
		getHibernateTemplate().saveOrUpdateAll(stocks);
	}

	/**
	 * @param cacheValue
	 * @return Stock
	 */
	private List<Stock> parseFile(String parseFilePath) throws StockException {
		File file = new File(parseFilePath);
		List<String> records = readCVSFile(file);
		List<Stock> stocks = wrapCVSRecod(records);
		return stocks;
	}

	/**
	 * @param records
	 * @return List<Stock>
	 */
	private List<Stock> wrapCVSRecod(List<String> records) throws StockException {
		List<Stock> warpRecords = new ArrayList<Stock>();
		if (records != null && records.size() > 0) {
			for (String record : records) {
				StockUtil.warpRecord(record, warpRecords);
			}
			logger.debug("wrap record total:" + warpRecords.size());
		}
		return warpRecords;
	}

	/**
	 * @param file
	 * @return
	 * @return List<String>
	 * @throws
	 */
	private List<String> readCVSFile(File file) throws StockException {
		List<String> records = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
			String temp;
			while ((temp = reader.readLine()) != null) {
				records.add(temp);
			}
		} catch (Exception e) {
			logger.error("read " + file.getName() + " failed");
			throw new StockException("read " + file.getName() + " failed");
		}
		logger.debug("read " + file.getName() + " success,total " + records.size() + " records!");
		return records;
	}

	/**
	 * @param string
	 * @param baseSaveDir2
	 * @return void
	 * @throws
	 */
	private String downLoadFileToSave(String destURL, String baseSaveDir) throws Exception {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;

		byte[] buffer = new byte[BUFFER_MAX_SIZE];
		int tempSize = 0;

		HttpURLConnection httpURLConn = URLConnectUtil.getHTTPURLConnection(destURL);

		bis = new BufferedInputStream(httpURLConn.getInputStream());

		if (StockContext.getBaseSaveDir() == null || "".equals(StockContext.getBaseSaveDir())) {
			StockContext.addCacheObject(StockContext.IS_SAVE_FILE, StockContext.SAVE_FILE_NO);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
			}
			return bos.toString("GBK");
		}

		StockUtil.createCVSFile(baseSaveDir);
		String cvspath = (String) StockContext.getCacheValue("cvspath");
		fos = new FileOutputStream(new File(cvspath));

		while ((tempSize = bis.read(buffer)) != -1) {
			String tempStr = new String(buffer, "GBK");
			fos.write(tempStr.getBytes("GBK"), 0, tempSize);
		}

		fos.close();
		bis.close();
		URLConnectUtil.closeHttpURLConnection(httpURLConn);
		logger.debug("get URL(" + destURL + ") data to save dest file(path:" + baseSaveDir + ")");
		return "";
	}

	public static void main(String args[]) throws Exception {
		StockServiceImpl ssiImpl = new StockServiceImpl();
		ssiImpl.downLoadFileToSave("http://hq.sinajs.cn/list=sh601006", "D:\\tcbsmsfax\\error\\");
		ssiImpl.parseFile((String) StockContext.getCacheValue("cvspath"));
	}

}
