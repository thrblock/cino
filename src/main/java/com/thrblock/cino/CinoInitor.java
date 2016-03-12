package com.thrblock.cino;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CinoInitor {
	private static AbstractApplicationContext context;
	private CinoInitor(){
	}
	public static AbstractApplicationContext getCinoContext() {
		if(context == null) {
			context = new ClassPathXmlApplicationContext("cino-context.xml");
			context.registerShutdownHook();
		}
		return context;
	} 
}
