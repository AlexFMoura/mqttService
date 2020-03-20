package com.arpit.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.arpit.config.ApplicationConfig;

//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//WebApplicationContext context = getContext();
		//servletContext.addListener(new ContextLoaderListener(context));
		//servletContext.setInitParameter("spring.profiles.active", "dev");
		//ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
		//dispatcher.setLoadOnStartup(1);
		//dispatcher.addMapping("/*");

		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfig.class);
		
		rootContext.setServletContext(servletContext);
		rootContext.getEnvironment().setActiveProfiles("default");
		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
		
		dispatcher.setLoadOnStartup(1);
        //dispatcher.setAsyncSupported(true);
//		dispatcher.addMapping("/");		
	}

	//private AnnotationConfigWebApplicationContext getContext() {
	//	AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
	//	context.setConfigLocation("com.arpit.config");
	//	return context;
	//}
}
