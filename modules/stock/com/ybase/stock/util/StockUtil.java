/**
 * Created By: Comwave Project Team
 * Created Date: 2012-11-28 下午2:10:49
 */
package com.ybase.stock.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;

import com.ybase.stock.domain.Stock;
import com.ybase.stock.exception.StockException;
import com.ybase.stock.StockContext;

/**
 * @author Tom Yang
 * @version 1.0
 */
public class StockUtil {
	private static final Log logger = LogFactory.getLog(StockUtil.class);

	/**
	 * @param baseSaveDir
	 * @return void
	 * @throws
	 */
	public static void createCVSFile(String baseSaveDir) throws StockException {
		if (baseSaveDir != null) {
			if (!baseSaveDir.endsWith(File.separator)) {
				baseSaveDir += File.separator;
			}
		} else {
			throw new StockException("base.save.dir is null!");
		}

		File saveDir = new File(baseSaveDir);
		createSaveDir(saveDir);
	}

	/**
	 * @param saveDir
	 * @return void
	 * @throws
	 */
	private static void createSaveDir(File saveDir) throws StockException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String cvsName = "STOCK_CVS_" + sdf.format(new Date()) + ".cvs";
		String savePath = saveDir.getAbsolutePath() + File.separator + cvsName;

		if (!saveDir.exists()) {
			if (saveDir.mkdirs()) {
				createSaveFile(savePath);
				StockContext.addCacheObject("cvspath", savePath);
			} else {
				throw new StockException("base.save.dir create failed");
			}
		} else {
			if (saveDir.isDirectory()) {
				createSaveFile(savePath);
				StockContext.addCacheObject("cvspath", savePath);
			} else {
				throw new StockException("base.save.dir isn't a directory!");
			}
		}
	}

	/**
	 * @param savePath
	 * @return void
	 * @throws
	 */
	private static void createSaveFile(String savePath) throws StockException {
		File file = new File(savePath);
		try {
			if (file.exists()) {
				FileUtils.forceDelete(file);
			}
			file.createNewFile();
		} catch (Exception e) {
			throw new StockException("file create failed:" + savePath);
		}
	}

	/**
	 * @param record
	 * @param warpRecords
	 * @return void
	 * @throws
	 */
	public static void warpRecord(String record, List<Stock> warpRecords) throws StockException {
		int type = getRecordType(record);
		if (type != Stock.NONE) {
			String recordStr = getRecordStr(record);
			if (StringUtil.isNotNullOrBlank(recordStr)) {
				List<String> fields = collectField(recordStr);
				if (fields != null && fields.size() == Stock.RECORD_FIELD_NUM) {
					Map<String, Object> fieldMap = mapField(fields);
					Stock stock = mapToStock(fieldMap);
					if (stock != null) {
						warpRecords.add(stock);
					}
				} else {
					throw new StockException("record ilegal!");
				}
			}
		} else {
			throw new StockException("record ilegal!");
		}
	}

	/**
	 * @param fieldMap
	 * @return
	 * @return Stock
	 * @throws
	 */
	private static Stock mapToStock(Map<String, Object> fieldMap) throws StockException {
		Stock stock = new Stock();
		if (fieldMap != null && fieldMap.keySet().size() == Stock.RECORD_FIELD_NUM) {
			try {
				for (String fieldName : fieldMap.keySet()) {
					String ucf = upperCaseFirst(fieldName);
					Class<Stock> sclass = Stock.class;
					Field field = sclass.getDeclaredField(fieldName);
					if (!field.isAccessible()) {
						field.setAccessible(true);
					}
					Method method = sclass.getMethod("set" + ucf, field.getType());
					if (method != null) {
						Object value = castValue(field.getType(), fieldMap.get(fieldName));
						method.invoke(stock, value);
					} else {
						throw new StockException("set" + ucf + " method isn't exist!");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new StockException("reflect field set value failed!");
			}
		} else {
			throw new StockException("record ilegal");
		}
		return stock;
	}

	/**
	 * @param type
	 * @param object
	 * @return
	 * @return Object
	 * @throws
	 */
	private static Object castValue(Class<?> type, Object castBeforeVal) throws StockException {
		Object obj = new Object();
		if (castBeforeVal != null && type != null) {
			if (type.getName() == Integer.class.getCanonicalName() || type.getName() == "int") {
				obj = new Integer(castBeforeVal.toString());
			} else if (type.getName() == Float.class.getCanonicalName() || type.getName() == "float") {
				obj = new Float(castBeforeVal.toString());
			} else if (type.getName() == Double.class.getCanonicalName() || type.getName() == "double") {
				obj = new Double(castBeforeVal.toString());
			} else if (type.getName() == Long.class.getCanonicalName() || type.getName() == "long") {
				obj = new Long(castBeforeVal.toString());
			} else if (type.getName() == String.class.getCanonicalName()) {
				obj = castBeforeVal.toString();
			}else {
				obj = castBeforeVal;
			}
		} else {
			throw new StockException("value:" + castBeforeVal + " to " + type.getName() + " failed");
		}
		return obj;
	}

	/**
	 * @param fieldName
	 * @return
	 * @return String
	 * @throws
	 */
	private static String upperCaseFirst(String fieldName) {
		if (fieldName != null && fieldName.trim() != "") {
			return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		}
		return "";
	}

	/**
	 * @param fields
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	private static Map<String, Object> mapField(List<String> fields) {
		Map<String, Object> fm = new HashMap<String, Object>();
		if (fields != null && fields.size() == Stock.RECORD_FIELD_NUM) {
			Field[] stockField = Stock.class.getDeclaredFields();
			for (int i = 0, j = 0; i < stockField.length; i++) {
				Field field = stockField[i];
				if ("private".equals(Modifier.toString(field.getModifiers())) && !"id".equals(field.getName())) {
					if (!field.isAccessible()) {
						field.setAccessible(true);
					}
					fm.put(field.getName(), fields.get(j));
					j++;
				}
			}
		}
		return fm;
	}

	/**
	 * 提取recordStr字符串中以“，”分割的字符串
	 * 
	 * @param recordStr
	 * @return String[]
	 */
	private static List<String> collectField(String recordStr) {
		List<String> fields = new ArrayList<String>();
		if (StringUtil.isNotNullOrBlank(recordStr) && recordStr.contains(",")) {
			String[] donaStr = recordStr.split(",");
			for (String field : donaStr) {
				fields.add(field);
			}
		}
		return fields;
	}

	/**
	 * @param record
	 * @return
	 * @return String
	 * @throws
	 */
	private static String getRecordStr(String record) {
		if (getRecordType(record) == Stock.FIRST_TYPE) {
			return record.split("\\\"")[1];
		} else if (getRecordType(record) == Stock.FIRST_TYPE) {
			return record;
		} else if (getRecordType(record) == Stock.NONE) {
			return "";
		}
		return "";
	}

	/**
	 * 判斷一條record記錄是否合法
	 * 
	 * @param record
	 * @return 當合法時返回true,不合法時返回false
	 */
	private static int getRecordType(String record) {
		if (StringUtil.isNotNullOrBlank(record)) {
			String[] splitStrs = record.split("\\\"");
			if (splitStrs.length == 3) {
				if ((splitStrs[1].split(",").length == Stock.RECORD_FIELD_NUM)) {
					// TODO isfileTypeLegal();
					return Stock.FIRST_TYPE;
				}
			} else if (!record.contains("\\\"")) {
				if ((record.split(",").length == Stock.RECORD_FIELD_NUM)) {
					// TODO isfileTypeLegal();
					return Stock.SECOND_TYPE;
				}
			}
		}
		return Stock.NONE;
	}

}
