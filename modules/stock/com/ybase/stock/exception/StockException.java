/**
 * Created By: Comwave Project Team
 * Created Date: 2012-11-28 下午2:10:30
 */
package com.ybase.stock.exception;

/**
 * @author Tom Yang
 * @version 1.0
 */
@SuppressWarnings("serial")
public class StockException extends Exception {

	public StockException(String name) {
		super(name);
	}

	public StockException(String name, Throwable throwable) {
		super(name, throwable);
	}

	public StockException() {
		super();
	}

	public StockException(Throwable throwable) {
		super(throwable);
	}
}
