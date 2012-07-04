package de.fzi.ALERT.actor.ActionActuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Create a Service Listener to get the start Event of the Application
 * Reference: http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/beans.html#context-functionality-events
 * @author cibobo
 *
 */
public class ServiceApplicationListener implements ApplicationListener<ContextRefreshedEvent>{

	private AsychMailThreadService mailThread;
	
	@Autowired
	public void setAsychMailThreadService(AsychMailThreadService asychMailThreadService){
		this.mailThread = asychMailThreadService;
	}

	public void onApplicationEvent(ContextRefreshedEvent event) {
		mailThread.start();
		System.out.println("The Mail Thread is start!");  
	}

}
