package com.thrblock.cino;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CinoInitor {
	private CinoInitor(){
	}
	public static AbstractApplicationContext getCinoContext() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("cino-context.xml");
		context.registerShutdownHook();
		return context;
	} 
}
