package fr.nacvs.ied_mediator.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIoc {

	private static final ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
	
	public static <T> T getBean(Class<T> requiredType) {
		return context.getBean(requiredType);
	}
	
	public static <T> T getBean(String beanName, Class<T> castTo) {
		return castTo.cast(context.getBean(beanName));
	}
}
