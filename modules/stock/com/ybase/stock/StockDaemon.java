/**
 * Created By: Comwave Project Team
 * Created Date: 2012-11-28 下午1:48:47
 */
package com.ybase.stock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.ybase.deamon.spring.IDeamonSpringContext;
import com.ybase.stock.service.StockService;

/**
 * @author Tom Yang
 * @version 1.0
 */
public class StockDaemon implements IDeamonSpringContext {
	private static final Log logger = LogFactory.getLog(StockDaemon.class);
	private StockService stockService;

	public void init() {
		logger.info("invoke init...");
	}

	public void doTask() {
		logger.debug("start process...");
		try {
			stockService.stock();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void stop() {
		logger.info("invoke stop...");
	}

	public void init(ApplicationContext context) {
		logger.info("invoke init(ApplicationContext context)...");
		stockService = (StockService) context.getBean("stockService");
	}

}
