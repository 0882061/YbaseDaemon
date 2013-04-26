/**
 * Created By: YBASE
 * Created Date: 2013-4-26 下午2:38:30
 * Author: Tom Yang
 */
package com.ybase.deamon.spring;

import org.springframework.context.ApplicationContext;

import com.ybase.deamon.IDeamon;

/**
 * @author Tom Yang
 * @version 1.0
 */
public interface IDeamonSpringContext extends IDeamon{

	public void init(ApplicationContext context);
	
}
