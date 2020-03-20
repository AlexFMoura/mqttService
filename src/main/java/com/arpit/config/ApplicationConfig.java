package com.arpit.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@PropertySources({
//		@PropertySource("classpath:properties/app.properties",
//		@PropertySource("classpath:properties/app-${spring.profiles.active}.properties") })
@ComponentScan(basePackages = "com.arpit")
public class ApplicationConfig {

	//@Bean(name = "keys")
	//public static PropertiesFactoryBean keys() {
	//	PropertiesFactoryBean bean = new PropertiesFactoryBean();
	//	bean.setLocation(new ClassPathResource("key.properties"));
	//	return bean;
	//}

	@Configuration
	@Profile("default")
	@PropertySource("classpath:properties/app.properties")
	static class DefaultProfile {

	}

}
